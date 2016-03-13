package com.zzia.wngn.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class JsonUtil {

	private static Gson gson = new Gson();

	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	public static Object fromJson(String json, Class<?> classes) {
		return gson.fromJson(json, classes);
	}

	public static String objectToJson(Object object) {
		JSONObject fromObject = JSONObject.fromObject(object);
		return fromObject.toString();
	}

	public static Object jsonToObject(String json, Class<?> classes) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, classes);
	}

	public static String listToJson(Object object) {
		if (object.getClass().isArray() || object instanceof List) {
			JSONArray jsonList = JSONArray.fromObject(object);
			return jsonList.toString();
		}
		throw new IllegalArgumentException("Parameter must be an array or list collection.");
	}

	public static List jsonToList(String json) {
		List list = (List) JSONArray.toCollection(JSONArray.fromObject(json));
		return list;
	}

	public static List jsonToList(String json, Class objectClass) {
		if (json == null || json.length() < 2 || !json.startsWith("[") || !json.endsWith("]")) {
			throw new IllegalArgumentException("json format is not correct.");
		}
		if (objectClass == null) {
			List list = (List) JSONArray.toCollection(JSONArray.fromObject(json));
			return list;
		}
		List list = (List) JSONArray.toCollection(JSONArray.fromObject(json), objectClass);
		return list;
	}

	public static Object getValue(String json, String key) {
		JSONObject fromObject = JSONObject.fromObject(json);
		return fromObject.get(key);
	}

	public static void main(String[] args) {
		List<Date> list = new ArrayList<Date>();
		for (int index = 0; index < 10; index++) {
			list.add(new Date());
		}
		System.out.println(listToJson(list));
		System.out.println(jsonToList(listToJson(list), Date.class));
	}

}
