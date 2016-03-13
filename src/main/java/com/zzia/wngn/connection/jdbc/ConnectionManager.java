package com.zzia.wngn.connection.jdbc;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
	
	private static Map<String, DBConnection> connectionMap = new HashMap<String, DBConnection>();
	
	public static String getJdbcUrl(String dbType,String hostname, String port, String dbName) {
		return connectionMap.get(dbType).getJdbcUrl(hostname, port, dbName);
	}

	static {
		connectionMap.put("mysql", new MysqlConnection());
		connectionMap.put("oracle", new OracleConnection());
		connectionMap.put("hive", new HiveConnection());
	}
	
	public static Connection getConnection(String dbConnetionURL, String userName,String password) throws Exception {
		String type = "";
		for(Map.Entry<String, DBConnection> entry : connectionMap.entrySet()) {
			if(dbConnetionURL.contains(entry.getKey())) {
				type = entry.getKey();
				break;
			}
		}
		
		if(type.equals("")) {
			throw new Exception("数据库不被支持！");
		}
		return connectionMap.get(type).getConnection(dbConnetionURL, userName, password);
	}
}
