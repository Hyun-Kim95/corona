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
	public String main(HttpServletRequest req, @RequestParam(defaultValue = "1") String result,
			@RequestParam(defaultValue = "daily") String cycle) {

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
		yesterday = Integer.parseInt(req.getAttribute("koreanewCase").toString().replace(",", ""))
				- (Integer.parseInt(past.get(Util.getPastDateStr(1) + "decideCnt").toString())
						- Integer.parseInt(past.get(Util.getPastDateStr(2) + "decideCnt").toString()));
		if (yesterday < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("koreanewCcase", Util.numberFormat(yesterday) + buho);

		// 1주전 정보 가져옴
		one = Integer.parseInt(req.getAttribute("koreanewCase").toString().replace(",", ""))
				- (Integer.parseInt(past.get(Util.getPastDateStr(7) + "decideCnt").toString())
						- Integer.parseInt(past.get(Util.getPastDateStr(8) + "decideCnt").toString()));
		if (one < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("onedicideCnt", Util.numberFormat(one) + buho);

		// 2주전 정보 가져옴
		two = Integer.parseInt(req.getAttribute("koreanewCase").toString().replace(",", ""))
				- (Integer.parseInt(past.get(Util.getPastDateStr(14) + "decideCnt").toString())
						- Integer.parseInt(past.get(Util.getPastDateStr(15) + "decideCnt").toString()));
		if (two < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("twodicideCnt", Util.numberFormat(two) + buho);

		// 1달전 정보 가져옴
		month = Integer.parseInt(req.getAttribute("koreanewCase").toString().replace(",", ""))
				- (Integer.parseInt(past.get(Util.getPastDateStr(30) + "decideCnt").toString())
						- Integer.parseInt(past.get(Util.getPastDateStr(31) + "decideCnt").toString()));
		if (month < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("monthdicideCnt", Util.numberFormat(month) + buho);

		// cycle 정보 가져옴(차트생성용 데이터)
		int[] newcase = new int[7];
		int[] death = new int[7];
		String[] day = new String[7];
		for (int i = 1; i < 8; i++) {
			// 일별 정보
			if (cycle.equals("daily")) {
				String[] dates = Util.yearMonthDay(Util.getPastDateStr(i));
				day[i - 1] = dates[1] + "." + dates[2];
				newcase[i - 1] = Integer.parseInt(past.get(Util.getPastDateStr(i) + "decideCnt"));
				death[i - 1] = Integer.parseInt(past.get(Util.getPastDateStr(i) + "deathCnt"));
			}	// 주별 정보 
			else if (cycle.equals("weekly")) {
				String[] dates = Util.yearMonthDay(Util.getPastDateStr(i * 7));
				String zu = Util.getWeekOfMonth(Util.getPastDateStr(i * 7));
				if (Util.getWeekOfMonth(Util.getPastDateStr(i * 7)).equals(Util
						.getWeekOfMonth(dates[0] + dates[1] + Util.getMaximumOfMonth(Util.getPastDateStr(i * 7))))) {
					if (Integer.parseInt(dates[1]) < 12) {
						if (Integer.parseInt(dates[1]) + 1 < 10) {
							dates[1] = "0" + (Integer.parseInt(dates[1]) + 1);
						} else {
							dates[1] = (Integer.parseInt(dates[1]) + 1) + "";
						}
						dates[2] = "01";
					} else {
						dates[0] = (Integer.parseInt(dates[0]) + 1) + "";
						dates[1] = "01";
						dates[2] = "01";
					}
					zu = "1";
				}
				day[i - 1] = dates[1] + "월 " + zu + "주차";
				newcase[i - 1] = Integer.parseInt(
						past.get(dates[0] + dates[1] + Util.getWeekInMonth(Util.getPastDateStr(i * 7)) + "decideCnt"));
				death[i - 1] = Integer.parseInt(
						past.get(dates[0] + dates[1] + Util.getWeekInMonth(Util.getPastDateStr(i * 7)) + "deathCnt"));
			} // 월별 정보
			else if (cycle.equals("monthly")) {
				String[] dates = Util.yearMonthDay(Util.getPastDateStr(i * 30));
				day[i - 1] = dates[1] + "월";
				newcase[i - 1] = Util.getAsInt(past.get(dates[0] + dates[1] + Util.getMaximumOfMonth(dates[0] + dates[1] + dates[2]) + "decideCnt"), 0);
				death[i - 1] = Util.getAsInt(past.get(dates[0] + dates[1] + Util.getMaximumOfMonth(dates[0] + dates[1] + dates[2]) + "deathCnt"), 0);
			}

		}
		req.setAttribute("day", day);
		req.setAttribute("cycle", cycle);

		if (result.equals("1")) {
			req.setAttribute("val", "확진자");
			req.setAttribute("col", "red");
			req.setAttribute("newcase", newcase);
		} else if (result.equals("2")) {
			req.setAttribute("val", "사망자");
			req.setAttribute("col", "violet");
			req.setAttribute("newcase", death);
		}
		req.setAttribute("result", result);

		return "main";
	}
}
