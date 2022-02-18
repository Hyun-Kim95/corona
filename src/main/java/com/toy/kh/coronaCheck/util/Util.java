package com.toy.kh.coronaCheck.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
	static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

	// 현재 날짜
	public static String getNowDateStr() {
		Date time = new Date();
		return format1.format(time);
	}

	// 과거 날짜
	public static String getPastDateStr(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day*(-1));
		return format1.format(cal.getTime());
	}
	
	// 한달 전 날짜
	public static String getPastMonthStr(int day) {
		Calendar mon = Calendar.getInstance();
		mon.add(Calendar.MONTH, (-1)*day);
		return format1.format(mon.getTime());
	}
	
	// 몇번째 주인지 확인
	public static String getWeekOfMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day =  Integer.parseInt(dates[2]);
		calendar.set(year, month - 1, day);
		return calendar.get(Calendar.WEEK_OF_MONTH) + "";
	}
	
	// 해당 주 마지막 일자
	public static String getWeekInMonth(String date) {
		Calendar cal = Calendar.getInstance();
		
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int week = Integer.parseInt(getWeekOfMonth(date));
		String result = null;
		
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		
		cal.set(Calendar.WEEK_OF_MONTH, week);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		int endDay = cal.get(Calendar.DAY_OF_MONTH);
		
		if(endDay < 10) {
			result = "0" + endDay;
		}else {
			result = endDay + "";
		}
		
		return result;
	}
	
	// 해당 월 마지막 일자
	public static String getMaximumOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		String[] dates = date.split("-");
		int year = Integer.parseInt(dates[0]);
		int month = Integer.parseInt(dates[1]);
		int day =  Integer.parseInt(dates[2]);
		
		cal.set(year, month-1, day);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
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
	
	// 숫자가 커질 경우에 보기 좋게 하기 위해서
	public static String numberFormat(int num) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		
		return df.format(num);
	}
}