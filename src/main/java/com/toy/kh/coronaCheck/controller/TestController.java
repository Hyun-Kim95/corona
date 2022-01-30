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
		
		// 오늘과 관련된 정보들
		Map<String, String> results = urlService.todayResult();
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
		
		// 1주전 정보 가져옴
		String seven = Util.getPastDateStr(-7);
		urlService.pastResult(seven);
		
        return "main";
    }
}
