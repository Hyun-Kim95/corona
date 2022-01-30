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
		
		Map<String, String> results = urlService.todayResult();
		
		req.setAttribute("now", Util.getNowDateStr());
        req.setAttribute("countryName", results.get("countryName"));
        req.setAttribute("newCase", results.get("newCase"));
        req.setAttribute("totalCase", results.get("totalCase"));
        req.setAttribute("recovered", results.get("recovered"));
        req.setAttribute("death", results.get("death"));
        req.setAttribute("percentage", results.get("percentage"));
        req.setAttribute("newCcase", results.get("newCcase"));
        req.setAttribute("newFcase", results.get("newFcase"));
		
        return "main";
    }
}
