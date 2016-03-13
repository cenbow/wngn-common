package com.zzia.wngn.connection.jdbc;

public class HiveConnection extends DBConnection {
    private static final String DRIVER = "org.apache.hadoop.hive.jdbc.HiveDriver";
    public HiveConnection() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    @Override
    public String getJdbcUrl(String hostname, String port, String dbName) {
        return "jdbc:hive://{0}:{1}/{2}".replace("{0}",hostname)
                .replace("{1}",port)
                .replace("{2}",dbName);
    }

}
