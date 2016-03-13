package com.zzia.wngn.connection.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class JdbcCore {

	public List<Map<String, Object>> queryForList(Connection conn, String sql) {
		Statement state = null;
		ResultSet resultSet = null;
		try {
			state = conn.createStatement();
			resultSet = state.executeQuery(sql);
			return resultSetHandler(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Map<String, Object>> queryForList(Connection conn, String sql, Object[] objects, Class[] calsses) {
		PreparedStatement state = null;
		ResultSet resultSet = null;
		try {
			state = conn.prepareStatement(sql);
			prepareParameter(state, objects, calsses);
			resultSet = state.executeQuery();
			return resultSetHandler(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<ResultContext> query(Connection conn, String sql) {
		Statement state = null;
		ResultSet resultSet = null;
		try {
			state = conn.createStatement();
			resultSet = state.executeQuery(sql);
			return resultContextHandler(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<ResultContext> query(Connection conn, String sql, Object[] objects, Class[] calsses) {
		PreparedStatement state = null;
		ResultSet resultSet = null;
		try {
			state = conn.prepareStatement(sql);
			prepareParameter(state, objects, calsses);
			resultSet = state.executeQuery();
			return resultContextHandler(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int update(Connection conn, String sql) {
		Statement state = null;
		try {
			state = conn.createStatement();
			return state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	protected List<Map<String, Object>> resultSetHandler(ResultSet resultSet) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = null;
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				resultMap = new HashMap<String, Object>();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					resultMap.put(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)));
				}
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	protected List<ResultContext> resultContextHandler(ResultSet resultSet) {
		List<ResultContext> resultList = new ArrayList<ResultContext>();
		ResultContext context = new ResultContext();
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				context = new ResultContext();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					context.put(metaData.getColumnName(i), resultSet.getObject(metaData.getColumnName(i)));
				}
				resultList.add(context);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private void prepareParameter(PreparedStatement ps, Object[] objects, Class[] calsses) throws Exception {
		if (objects.length != calsses.length) {
			throw new Exception("参数对应不匹配");
		}
		for (int i = 0; i < objects.length; i++) {
			prepareParameter(ps, i+1, objects[i], calsses[i]);
		}
	}

	private void prepareParameter(PreparedStatement ps, int index, Object value, Class calss) throws Exception {
		if (calss.getName().equals(String.class.getName())) {
			ps.setString(index, value != null ? value.toString() : null);
		} else if (calss.getName().equals(Integer.class.getName())) {
			ps.setInt(index, (Integer) value);
		} else if (calss.getName().equals(Long.class.getName())) {
			ps.setLong(index, (Long) value);
		} else if (calss.getName().equals(Date.class.getName())) {
			ps.setTimestamp(index, new Timestamp(((Date) value).getTime()));
		} else if (calss.getName().equals(Double.class.getName())) {
			ps.setDouble(index, (Double) value);
		} else if (calss.getName().equals(Float.class.getName())) {
			ps.setFloat(index, (Float) value);
		} else {
			throw new Exception("数据类型不支持");
		}
	}

}
