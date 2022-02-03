package com.toy.kh.coronaCheck.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.toy.kh.coronaCheck.service.UrlService;
import com.toy.kh.coronaCheck.util.Util;

@Controller
public class TestController {
	@Autowired
	UrlService urlService;

	@RequestMapping("main")
	public String main(HttpServletRequest req) {
		String[] ziyok = { "korea", "seoul", "busan", "daegu", "incheon", "gwangju", "daejeon", "ulsan", "sejong",
				"gyeonggi", "gangwon", "chungbuk", "chungnam", "jeonbuk", "jeonnam", "gyeongbuk", "gyeongnam", "jeju" };

		Map<String, String> results = urlService.todayResult();
		// 오늘과 관련된 정보들
		for (String zi : ziyok) {
			req.setAttribute("now", Util.getNowDateStr());
			req.setAttribute(zi + "countryName", results.get(zi + "countryName"));
			req.setAttribute(zi + "newCase", results.get(zi + "newCase"));
			req.setAttribute(zi + "totalCase", results.get(zi + "totalCase"));
			req.setAttribute(zi + "recovered", results.get(zi + "recovered"));
			req.setAttribute(zi + "death", results.get(zi + "death"));
			req.setAttribute(zi + "percentage", results.get(zi + "percentage"));
			req.setAttribute(zi + "newCcase", results.get(zi + "newCcase"));
			req.setAttribute(zi + "newFcase", results.get(zi + "newFcase"));
		}

		// 과거정보 가져올 준비
		Map<String, String> past;
		past = urlService.pastResult();
		int yesterday, one, two, month;
		String buho = "";

		// 어제 정보 가져옴
		yesterday = Integer.parseInt(req.getAttribute("koreanewFcase").toString().replace(",", ""));
		if (yesterday < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("koreanewFcase", yesterday + buho);

		// 1주전 정보 가져옴
		String seven = Util.getPastDateStr(7);
		one = Util.getAsInt(req.getAttribute("koreatotalCase").toString().replace(",", ""), 0) - Util.getAsInt(past.get(seven + "dicideCnt"), 0);
		if (one < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("onedicideCnt", one + buho);

		// 2주전 정보 가져옴
		String fourteen = Util.getPastDateStr(14);
		two = Util.getAsInt(req.getAttribute("koreatotalCase").toString().replace(",", ""), 0) - Util.getAsInt(past.get(fourteen + "dicideCnt"), 0);
		if (two < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("twodicideCnt", two + buho);

		// 1달전 정보 가져옴
		String onemonth = Util.getPastMonthStr();
		month = Util.getAsInt(req.getAttribute("koreatotalCase").toString().replace(",", ""), 0) - Util.getAsInt(past.get(onemonth + "dicideCnt"), 0);
		if (month < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("monthdicideCnt", month + buho);

		// 1주일간의 정보 모두 가져옴(차트생성용 데이터)
		// String 을 List<>로 바꿔도 괜찮을 듯, todolist에서 보고 맞춰서 하기
		int[] newcase = new int[7];
		int[] death = new int[7];
		String[] day = new String[7];
		for (int i = 0; i < 7; i++) {
			day[i] = Util.getPastDateStr(i);
			newcase[i] = Util.getAsInt(past.get(day[i] + "dicideCnt"), 0);
			death[i] = Util.getAsInt(past.get(day[i] + "deathCnt"), 0);
		}
		req.setAttribute("day", day);
		req.setAttribute("newcase", newcase);
		req.setAttribute("death", death);
		return "main";
	}
}
