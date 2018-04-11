package io.sql.ulti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {
    private static Logger log = LoggerFactory.getLogger(Logger.class);

    public static void closeQuietly(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                log.info("close connection");
                connection.close();
            }
        } catch (SQLException e) {
            log.error("error when closing connection");
        }
    }

    public static void closeQuietly(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                log.info("close statement");
                statement.close();
            }
        } catch (SQLException e) {
            log.error("error when closing statement");
        }
    }

    public static void closeQuietly(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                log.info("close resultSet");
                resultSet.close();
            }
        } catch (SQLException e) {
            log.error("error when closing resultSet");
        }
    }
}
