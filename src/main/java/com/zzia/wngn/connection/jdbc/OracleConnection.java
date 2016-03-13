package com.zzia.wngn.connection.jdbc;

class OracleConnection extends DBConnection {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	//private static final String URL = "jdbc:mysql://172.16.11.46/test?useUnicode=true&characterEncoding=UTF-8";
	
	public OracleConnection() {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public String getJdbcUrl(String hostname,String port,String dbName) {
		return "jdbc:oracle:thin:@{0}:{1}:{2}".replace("{0}",hostname)
											  .replace("{1}",port)
											  .replace("{2}",dbName);
	}

}
