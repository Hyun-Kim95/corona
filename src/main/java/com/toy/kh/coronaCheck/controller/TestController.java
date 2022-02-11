package com.toy.kh.coronaCheck.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.kh.coronaCheck.service.UrlService;
import com.toy.kh.coronaCheck.util.Util;

@Controller
public class TestController {
	@Autowired
	UrlService urlService;

	@RequestMapping("main")
	public String main(HttpServletRequest req, @RequestParam(defaultValue = "1")String result) {

		Map<String, String> results = urlService.todayResult();
		// 오늘과 관련된 정보들
		Set<String> keys = results.keySet();
		for (String key : keys) {
			req.setAttribute(key, results.get(key));
		}
		
		// 과거정보 가져올 준비
		Map<String, String> past;
		past = urlService.pastResult();
		int yesterday, one, two, month;
		String buho = "";

		// 어제 정보 가져옴
		yesterday = Integer.parseInt(req.getAttribute("koreanewCcase").toString().replace(",", ""));
		if (yesterday < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("koreanewCcase", Util.numberFormat(yesterday) + buho);

		// 1주전 정보 가져옴
		String seven = Util.getPastDateStr(7);
		one = Util.getAsInt(req.getAttribute("koreatotalCase").toString().replace(",", ""), 0) - Util.getAsInt(past.get(seven + "dicideCnt"), 0);
		if (one < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("onedicideCnt", Util.numberFormat(one) + buho);

		// 2주전 정보 가져옴
		String fourteen = Util.getPastDateStr(14);
		two = Util.getAsInt(req.getAttribute("koreatotalCase").toString().replace(",", ""), 0) - Util.getAsInt(past.get(fourteen + "dicideCnt"), 0);
		if (two < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("twodicideCnt", Util.numberFormat(two) + buho);

		// 1달전 정보 가져옴
		String onemonth = Util.getPastMonthStr();
		month = Util.getAsInt(req.getAttribute("koreatotalCase").toString().replace(",", ""), 0) - Util.getAsInt(past.get(onemonth + "dicideCnt"), 0);
		if (month < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("monthdicideCnt", Util.numberFormat(month) + buho);

		// 1주일간의 정보 모두 가져옴(차트생성용 데이터)
		int[] newcase = new int[7];
		int[] death = new int[7];
		String[] day = new String[7];
		for (int i = 1; i < 8; i++) {
			day[i-1] = Util.getPastDateStr(i);
			newcase[i-1] = Util.getAsInt(past.get(day[i-1] + "dicideCnt"), 0);
			death[i-1] = Util.getAsInt(past.get(day[i-1] + "deathCnt"), 0);
			
		}
		req.setAttribute("day", day);
		
		if(result.equals("1")) {
			req.setAttribute("val", "확진자");
			req.setAttribute("col", "red");
			req.setAttribute("newcase", newcase);
		} else if(result.equals("2")) {
			req.setAttribute("val", "사망자");
			req.setAttribute("col", "violet");
			req.setAttribute("newcase", death);
		}
		req.setAttribute("result", result);
		
		return "main";
	}
}
