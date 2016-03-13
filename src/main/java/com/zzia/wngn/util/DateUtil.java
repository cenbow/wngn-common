package com.zzia.wngn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public final static String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss SSS";
	public final static String FORMAT_COMMON = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_NUMBER = "yyyyMMdd";
	public final static String FORMAT_TIME = "HH:mm:ss";

	public static String formatDate(Date date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String formatDate(long date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date_ = new Date(date);
		return sdf.format(date_);
	}
	
	public static Date parseDate(String date,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("解析时间错误:"+e.getMessage());
		}
	}
	
	/**
	 * 格式化日期yyyy-MM-dd到yyyyMMdd
	 * 
	 * @param dateString
	 * @return
	 */
	public static Integer formatDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		SimpleDateFormat sdfNumber = new SimpleDateFormat(FORMAT_NUMBER);
		if (date != null && !"".equals(date)) {
			try {
				Date d = sdf.parse(date);
				return Integer.valueOf(sdfNumber.format(d));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("解析时间错误:"+e.getMessage());
			}
		}
		throw new IllegalArgumentException("date cannot be null");
	}

	/**
	 * 日期加一
	 * 
	 * @param dateString
	 *            yyyy-MM-dd
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String dateAdd(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		if (dateString != null && !"".equals(dateString)) {
			try {
				Date date = sdf.parse(dateString);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
				date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
				return sdf.format(date);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("解析时间错误:"+e.getMessage());
			}
		}
		return dateString;
	}

	/**
	 * 日期减一
	 * 
	 * @param dateString
	 *            yyyy-MM-dd
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String dateMinus(String dateString) {
		SimpleDateFormat sdfParse = new SimpleDateFormat(FORMAT_DATE);
		if (dateString != null && !"".equals(dateString)) {
			try {
				Date date = sdfParse.parse(dateString);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
				date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
				return sdfParse.format(date);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("解析时间错误:"+e.getMessage());
			}
		}
		return dateString;

	}

	/**
	 * 获取当前日期字符串 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间字符串 hh:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTimeString() {
		SimpleDateFormat sdfParse = new SimpleDateFormat(FORMAT_TIME);
		return sdfParse.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(System.currentTimeMillis(), DateUtil.FORMAT_COMMON));
	}
}
