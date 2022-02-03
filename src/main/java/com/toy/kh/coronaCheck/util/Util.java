package com.toy.kh.coronaCheck.util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

	// 현재 날짜
	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date time = new Date();
		return format1.format(time);
	}

	// 과거 날짜
	public static String getPastDateStr(int day) {
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day*(-1));
		return format2.format(cal.getTime());
	}
	
	// 한달 전 날짜
	public static String getPastMonthStr() {
		SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");
		Calendar mon = Calendar.getInstance();
		mon.add(Calendar.MONTH, -1);
		return format3.format(mon.getTime());
	}
	
	// Object를 int타입으로 리턴
	public static int getAsInt(Object object, int defaultValue) {
		if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		} else if (object instanceof Double) {
			return (int) Math.floor((double) object);
		} else if (object instanceof Float) {
			return (int) Math.floor((float) object);
		} else if (object instanceof Long) {
			return (int) object;
		} else if (object instanceof Integer) {
			return (int) object;
		} else if (object instanceof String) {
			return Integer.parseInt((String) object);
		}

		return defaultValue;
	}
}