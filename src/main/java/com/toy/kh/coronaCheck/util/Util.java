package com.toy.kh.coronaCheck.util;

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
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		return format2.format(cal.getTime());
	}
}
