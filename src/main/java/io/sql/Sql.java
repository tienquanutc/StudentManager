package io.sql;

import io.sql.ulti.DbUtils;
import io.sql.ulti.ResultSetMapper;
import util.function.ConsumerChecked;
import util.function.ToIntConsumerChecked;
import util.function.ToIntFunctionChecked;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Sql {
    protected static Logger LOG = LoggerFactory.getLogger(Sql.class);

    private String url;

    private ResultSet resultSetCache;
    private Connection connectionCache;

    private final String STRING_TYPE = String.class.getName();
    private final String INTEGER_TYPE = Integer.class.getName();
    private final String SHORT_TYPE = Short.class.getName();
    private final String DATE_TYPE = Date.class.getName();
    private final String TIME = Time.class.getName();
    private final String DATE_TIME_TYPE = Timestamp.class.getName();
    private final String BOOLEAN_TYPE = Boolean.class.getName();
    private final String DECIMAL_TYPE = BigDecimal.class.getName();
    private final String LONG_TYPE = Long.class.getName();

    public Sql(SqlConfig config) {
        DriverManager.setLoginTimeout(10);
        this.url = "jdbc:sqlserver://" + config.getHost() + ":" + config.getPort() +
                ";databaseName=" + config.getDbName() + ";user=" + config.getUser() + ";password=" + config.getPassword();
    }

    public static Sql newInstance(String url) throws SQLException {
        return new Sql(url);
    }

    public Sql(String url) {
        this.url = url;
    }

    public Sql query(String sql) throws SQLException {
        Statement statement = null;
        try {
            createNewConnection();
            statement = getStatement(connectionCache, sql);
            this.resultSetCache = statement.executeQuery(sql);
            return this;
        } catch (SQLException e) {
            LOG.warn("Failed to execute: " + sql + " because: " + e.getMessage());
            closeResources(connectionCache, statement, resultSetCache);
            throw e;
        }
    }

    public Sql query(String sql, List<Object> params) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            createNewConnection();
            preparedStatement = getPreparedStatement(connectionCache, sql);
            mapPreparedStatement(preparedStatement, params);
            this.resultSetCache = preparedStatement.executeQuery();
            return this;
        } catch (SQLException e) {
            LOG.warn("Failed to execute: " + sql + " because: " + e.getMessage());
            closeResources(connectionCache, preparedStatement, resultSetCache);
            throw e;
        }
    }

    public Sql query(String sql, Object... params) throws SQLException {
        return query(sql, Arrays.asList(params));
    }

    public void eachRow(Consumer<ResultSet> consumer) throws SQLException {
        Validate.notNull(resultSetCache, "must be query before execute eachRow method");
        try {
            while (resultSetCache.next()) {
                consumer.accept(resultSetCache);
            }
        } catch (SQLException e) {
            LOG.warn("Failed to accept row : " + resultSetCache + " because: " + e.getMessage());
            throw e;
        }
        closeResources(connectionCache, resultSetCache);
    }

    public <T> void eachRow(Class<T> klazz, Consumer<T> consumer) throws SQLException {
        Validate.notNull(resultSetCache, "must be query before execute eachRow method");
        try {
            while (resultSetCache.next()) {
                consumer.accept(ResultSetMapper.<T>map(resultSetCache, klazz));
            }
        } catch (SQLException e) {
            LOG.warn("Failed to accept row : " + resultSetCache + " because: " + e.getMessage());
            throw e;
        } finally {
            closeResources(connectionCache, resultSetCache);
        }
    }

    public <T> List<T> toModels(Class<T> klazz) throws SQLException {
        Validate.notNull(resultSetCache, "must be query before execute eachRow method");
        List<T> models = new ArrayList<>();
        eachRow(klazz, models::add);
        return models;
    }

    public void eachRow(String sql, ConsumerChecked<ResultSet> row) throws SQLException {
        Statement statement = null;
        ResultSet results = null;
        try {
            createNewConnection();
            statement = getStatement(connectionCache, sql);
            results = statement.executeQuery(sql);
            while (results.next()) {
                row.accept(results);
            }
        } catch (SQLException e) {
            LOG.warn("Failed to execute: " + sql + " because: " + e.getMessage());
            throw e;
        } finally {
            closeResources(connectionCache, statement, results);
        }
    }


    //update
    public int executeUpdate(String query) throws SQLException {
        return executeUpdate(query, statement -> statement.executeUpdate(query));
    }

    public int executeUpdate(String preQuery, List<Object> params) throws SQLException {
        return _executeUpdate(preQuery, params, PreparedStatement::executeUpdate);
    }

    public int executeUpdate(String preQuery, Object... params) throws SQLException {
        return _executeUpdate(preQuery, Arrays.asList(params), PreparedStatement::executeUpdate);
    }

    private int executeUpdate(String preQuery, Object[] params, ToIntFunctionChecked<PreparedStatement> pstiFunc) throws SQLException {
        return _executeUpdate(preQuery, Arrays.asList(params), pstiFunc);
    }


    private int executeUpdate(String query, ToIntConsumerChecked<Statement> consumer) throws SQLException {
        Statement statement = null;
        try {
            createNewConnection();
            statement = getStatement(connectionCache, query);
            return consumer.accept(statement);
        } catch (SQLException e) {
            LOG.warn("Failed execute: " + query + " because " + e.getMessage());
            throw e;
        } finally {
            closeResources(connectionCache, statement);
        }
    }

    private int _executeUpdate(String query, List<Object> params, ToIntFunctionChecked<PreparedStatement> pstiFunc) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            createNewConnection();
            LOG.info("Creating query " + query);
            preparedStatement = getPreparedStatement(connectionCache, query);
            mapPreparedStatement(preparedStatement, params);

            return pstiFunc.applyAsInt(preparedStatement);
        } catch (SQLException e) {
            LOG.warn("Failed execute: " + query + " because " + e.getMessage());
            throw e;
        } finally {
            closeResources(connectionCache, preparedStatement);
        }
    }



    private void mapPreparedStatement(PreparedStatement preparedStatement, Object[] params) throws SQLException {
        mapPreparedStatement(preparedStatement, Arrays.asList(params));
    }

    private void mapPreparedStatement(PreparedStatement preparedStatement, List<Object> objects) throws SQLException {
        try {
            for (int i = 0; i < objects.size(); ++i) {
                preparedStatement.setObject(i + 1, objects.get(i));
            }
            LOG.info("with param " + String.join(", ", objects.stream().map(Object::toString).collect(Collectors.toList())));
        } catch (SQLException e) {
            LOG.warn("Failed map prepared statement because " + e.getMessage());
        }
    }


    private void closeResources(Connection connection, Statement statement, ResultSet results) {
        DbUtils.closeQuietly(results);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(connection);
    }

    private void closeResources(Connection connection, Statement statement) {
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(connection);
    }

    private void closeResources(Connection connection, ResultSet resultSet) {
        Statement statement = null;
        try {
            statement = resultSet.getStatement();
        } catch (SQLException ignore) {
            LOG.warn("Failed to get statement from " + resultSet.toString() + " because " + ignore.getMessage());
            //ignored
        }
        DbUtils.closeQuietly(resultSet);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(connection);
    }

    private void createNewConnection() {
        LOG.info("create connection from " + url);
        try {
            this.connectionCache = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            LOG.error("Failed to connect sql server with url: " + url + " because " + e.getMessage());
            throw new RuntimeException("The TCP/IP connection to the host localhost, port 1401 has failed\nMake sure that an instance of SQL Server is running on the host and accepting TCP/IP connections at the port");
        }
    }

    private Statement getStatement(Connection connection, String sql) throws SQLException {
        LOG.info("create statement: " + sql);
        return connection.createStatement();
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sql) throws SQLException {
        LOG.info("create prepared statement: " + sql);
        return connection.prepareStatement(sql);
    }

    public void close() {
        DbUtils.closeQuietly(this.connectionCache);
    }

}
