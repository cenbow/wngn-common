package com.zzia.wngn.connection.jdbc;

class MysqlConnection extends DBConnection {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	public MysqlConnection() {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	@Override
	public String getJdbcUrl(String hostname, String port, String dbName) {
		return null;
	}

}
