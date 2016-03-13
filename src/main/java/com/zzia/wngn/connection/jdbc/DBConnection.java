package com.zzia.wngn.connection.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

abstract class DBConnection {

    /**
     * 获取连接
     * 
     * @return
     * @throws Exception
     */
    public Connection getConnection(String dbConnetionURL, String userName, String password)
            throws Exception {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbConnetionURL, userName,password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return connection;
    }

    /**
     * 释放资源
     * 
     * @param rs
     * @param stmt
     * @param conn
     */
    public void close(ResultSet rs, CallableStatement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract String getJdbcUrl(String hostname, String port, String dbName);
}
