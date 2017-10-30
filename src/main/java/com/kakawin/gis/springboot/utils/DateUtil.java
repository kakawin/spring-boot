package com.kakawin.gis.springboot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final String MONTH_PATTERN = "yyyy-MM";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String MILLIS_PATTERN = "yyyyMMddHHmmssSSS";

	public static Date parseDate(String dateString) {
		try {
			return new SimpleDateFormat(DATE_PATTERN).parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDate(Date date) {
		return new SimpleDateFormat(DATE_PATTERN).format(date);
	}

	public static Date parseDateTime(String dateTimeString) {
		try {
			return new SimpleDateFormat(DATE_TIME_PATTERN).parse(dateTimeString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDateTime(Date date) {
		return new SimpleDateFormat(DATE_TIME_PATTERN).format(date);
	}
	
	public static String formatMonth(Date date) {
		return new SimpleDateFormat(MONTH_PATTERN).format(date);
	}

	public static String formatMillis(Date date) {
		return new SimpleDateFormat(MILLIS_PATTERN).format(date);
	}

}
