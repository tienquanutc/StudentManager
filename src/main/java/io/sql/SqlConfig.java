package io.sql;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SqlConfig {
    @JsonProperty("host")
    private String host;
    @JsonProperty("port")
    private int port;
    @JsonProperty("dbname")
    private String dbName;
    @JsonProperty("user")
    private String user;
    @JsonProperty("password")
    private String password;

    public SqlConfig() {

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
