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
		String[] ziyok = {"korea", "seoul", "busan", "daegu", "incheon", "gwangju", "daejeon", "ulsan", "sejong", "gyeonggi", "gangwon", "chungbuk", "chungnam", "jeonbuk", "jeonnam", "gyeongbuk", "gyeongnam", "jeju"};
		String today;
		// 오늘과 관련된 정보들
		for (String zi : ziyok) {
			Map<String, String> results = urlService.todayResult(zi);
			
			req.setAttribute("now", Util.getNowDateStr());
	        req.setAttribute(zi + "countryName", results.get("countryName"));
	        req.setAttribute(zi + "newCase", results.get("newCase"));
	        req.setAttribute(zi + "totalCase", results.get("totalCase"));
	        req.setAttribute(zi + "recovered", results.get("recovered"));
	        req.setAttribute(zi + "death", results.get("death"));
	        req.setAttribute(zi + "percentage", results.get("percentage"));
	        req.setAttribute(zi + "newCcase", results.get("newCcase"));
	        req.setAttribute(zi + "newFcase", results.get("newFcase"));
		}

		// 과거정보 가져올 준비
		Map<String, String> past;
		int yesterday,one,two,month;
		String buho = "";
		
		// 어제 정보 가져옴
		yesterday = Integer.parseInt(req.getAttribute("koreanewFcase").toString().replace(",", ""));
		if(yesterday < 0) {
			buho = "↓";
		}else {
			buho = "↑";
		}
		req.setAttribute("koreanewFcase", yesterday + buho);
		
		// 1주전 정보 가져옴
		String seven = Util.getPastDateStr(-7);
		past = urlService.pastResult(seven);		
		one = Integer.parseInt(((String) req.getAttribute("koreatotalCase")).replace(",", "")) - Integer.parseInt(past.get("dicideCnt").replace(",", ""));
		if(one < 0) {
			buho = "↓";
		}else {
			buho = "↑";
		}
		req.setAttribute("onedicideCnt", one + buho);
		
		// 2주전 정보 가져옴
		String fourteen = Util.getPastDateStr(-14);
		past = urlService.pastResult(fourteen);
		two = Integer.parseInt(((String) req.getAttribute("koreatotalCase")).replace(",", "")) - Integer.parseInt(past.get("dicideCnt").replace(",", ""));
		if(two < 0) {
			buho = "↓";
		}else {
			buho = "↑";
		}
		req.setAttribute("twodicideCnt", two + buho);
		
		// 1달전 정보 가져옴
		String onemonth = Util.getPastMonthStr();
		past = urlService.pastResult(onemonth);
		month = Integer.parseInt(((String) req.getAttribute("koreatotalCase")).replace(",", "")) - Integer.parseInt(past.get("dicideCnt").replace(",", ""));
		if(month < 0) {
			buho = "↓";
		}else {
			buho = "↑";
		}
		req.setAttribute("monthdicideCnt", month + buho);
		
		// 1주일간의 정보 모두 가져옴(차트생성용 데이터)
		// String 을 List<>로 바꿔도 괜찮을 듯, todolist에서 보고 맞춰서 하기
		String[] newcase;
		String[] death;
		Map<String, String> week;
		for(int i = 0;i<7;i++) {
			week = urlService.pastResult(Util.getPastDateStr(i));
			
		}
        return "main";
    }
}
