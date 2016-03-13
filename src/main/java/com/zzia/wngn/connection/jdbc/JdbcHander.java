package com.zzia.wngn.connection.jdbc;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class JdbcHander {

	protected static JdbcCore jdbcCore = new JdbcCore();

	public static List<Map<String, Object>> queryForList(Connection conn, String sql) {
		return jdbcCore.queryForList(conn, sql);
	}

	public static List<Map<String, Object>> queryForList(Connection conn, String sql, Object[] objects, Class[] calsses) {
		return jdbcCore.queryForList(conn, sql, objects, calsses);
	}

	public static List<ResultContext> query(Connection conn, String sql) {
		return jdbcCore.query(conn, sql);
	}

	public static List<ResultContext> query(Connection conn, String sql, Object[] objects, Class[] calsses) {
		return jdbcCore.query(conn, sql, objects, calsses);
	}

	public static int update(Connection conn, String sql) {
		return jdbcCore.update(conn, sql);
	}

}
