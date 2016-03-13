package com.zzia.wngn.connection.jdbc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResultContext {

	protected Map<String, Object> context = new HashMap<String, Object>();

	public int size() {
		return context.size();
	}

	public String[] colunms() {
		String[] columns = new String[context.size()];
		int i = 0;
		for (String key : context.keySet()) {
			columns[i++] = key.toLowerCase();
		}
		return columns;
	}

	public boolean containsKey(String key) {
		return context.containsKey(key.toUpperCase());
	}

	public void put(String key, Object value) {
		context.put(key.toUpperCase(), value);
	}

	public Object get(String key) {
		return context.get(key.toUpperCase());
	}

	public Object getObject(String key) {
		return context.get(key.toUpperCase());
	}

	public int getInt(String key) {
		return (Integer) context.get(key.toUpperCase());
	}

	public long getLong(String key) {
		return (Long) context.get(key.toUpperCase());
	}

	public float getFloat(String key) {
		return (Float) context.get(key.toUpperCase());
	}

	public double getDouble(String key) {
		return (Double) context.get(key.toUpperCase());
	}

	public String getString(String key) {
		return context.get(key.toUpperCase()) != null ? context.get(key.toUpperCase()).toString() : null;
	}

	public Date getDate(String key) {
		return new Date(((Date) context.get(key.toUpperCase())).getTime());
	}

	public Timestamp getTimestamp(String key) {
		return (Timestamp) context.get(key.toUpperCase());
	}

}
