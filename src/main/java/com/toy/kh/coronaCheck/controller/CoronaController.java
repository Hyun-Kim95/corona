package com.toy.kh.coronaCheck.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.toy.kh.coronaCheck.dto.CoronaDto;
import com.toy.kh.coronaCheck.service.UrlService;
import com.toy.kh.coronaCheck.util.Util;

@Controller
public class CoronaController {
	@Autowired
	UrlService urlService;

	@RequestMapping("main")
	public String main(HttpServletRequest req, @RequestParam(defaultValue = "1") String result,
			@RequestParam(defaultValue = "daily") String cycle, @RequestParam(defaultValue = "korea") String place,
			@RequestParam(defaultValue = "1") String local) {

		Map<String, String> results = urlService.todayResult(local);
		// 오늘과 관련된 정보들
		Set<String> keys = results.keySet();
		for (String key : keys) {
			req.setAttribute(key, results.get(key));
		}
		// 지역별 백신센터 정보 가져옴
		String[] vaccineCenter = {"서울", "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종시", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"};
		List<CoronaDto> centerLocal = urlService.vaccineOfDB(vaccineCenter[Integer.parseInt(local) - 1]);
		List<List<String>> centerL = new ArrayList<List<String>>();
		for (CoronaDto coro : centerLocal) {
			List<String> ex = new ArrayList<String>();
			ex.add("\"" + coro.getName() + "\"");
			ex.add("\"" + coro.getAddr() + "\"");
			ex.add("\"" + coro.getTelno() + "\"");
			ex.add(coro.getXposwgs84());
			ex.add(coro.getYposwgs84());
			centerL.add(ex);
		}
		req.setAttribute("centerLocal", centerL);
				
		// 과거정보 가져올 준비
		String[] gubun = { "Total", "Seoul", "Busan", "Daegu", "Incheon", "Gwangju", "Daejeon", "Ulsan", "Sejong",
				"Gyeonggi-do", "Gangwon-do", "Chungcheongbuk-do", "Chungcheongnam-do", "Jeollabuk-do", "Jeollanam-do", "Gyeongsangbuk-do", "Gyeongsangnam-do", "Jeju" };
		
		Map<String, String> past;
		past = urlService.pastResult();
		int yesterday, one, two, month;
		String buho = "";
		// 어제 정보 가져옴
		yesterday = Util.getAsInt(req.getAttribute("newCase").toString().replace(",", ""),0)
				- (Util.getAsInt(past.get(Util.getPastDateStr(1) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0)
						- Util.getAsInt(past.get(Util.getPastDateStr(2) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0));
		if (yesterday < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("newCcase", Util.numberFormat(yesterday) + buho);

		// 1주전 정보 가져옴
		one = Util.getAsInt(req.getAttribute("newCase").toString().replace(",", ""),0)
				- (Util.getAsInt(past.get(Util.getPastDateStr(7) + gubun[Integer.parseInt(local) - 1] + "defCnt"), 0)
						- Util.getAsInt(past.get(Util.getPastDateStr(8) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0));
		if (one < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("onedicideCnt", Util.numberFormat(one) + buho);

		// 2주전 정보 가져옴
		two = Util.getAsInt(req.getAttribute("newCase").toString().replace(",", ""),0)
				- (Util.getAsInt(past.get(Util.getPastDateStr(14) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0)
						- Util.getAsInt(past.get(Util.getPastDateStr(15) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0));
		if (two < 0) {
			buho = "↓";
		} else {
			buho = "↑";
		}
		req.setAttribute("twodicideCnt", Util.numberFormat(two) + buho);

		// 1달전 정보 가져옴
		month = Util.getAsInt(req.getAttribute("newCase").toString().replace(",", ""),0)
				- (Util.getAsInt(past.get(Util.getPastDateStr(30) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0)
						- Util.getAsInt(past.get(Util.getPastDateStr(31) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0));
//		System.out.println("&&&&&&&&&&&&&&&&&&&");
//		System.out.println(Util.getAsInt(req.getAttribute("newCase").toString().replace(",", ""),0));
//		System.out.println(Util.getPastDateStr(30) + gubun[Integer.parseInt(local) - 1] + "defCnt");
//		System.out.println(past.get(Util.getPastDateStr(31) + gubun[Integer.parseInt(local) - 1] + "defCnt"));
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
				String[] dates = Util.getPastDateStr(i).split("-");
				day[i - 1] = dates[1] + "." + dates[2];
				newcase[i - 1] = Util.getAsInt(past.get(Util.getPastDateStr(i) + gubun[Integer.parseInt(local) - 1] + "defCnt"),0);
				death[i - 1] = Util.getAsInt(past.get(Util.getPastDateStr(i) + gubun[Integer.parseInt(local) - 1] + "deathCnt"),0);
			} // 주별 정보
			else if (cycle.equals("weekly")) {
				String[] dates = Util.getPastDateStr(i * 7).split("-");
				String zu = Util.getWeekOfMonth(Util.getPastDateStr(i * 7));
				if (Util.getWeekOfMonth(Util.getPastDateStr(i * 7)).equals(Util
						.getWeekOfMonth(dates[0] + "-" + dates[1] + "-" + Util.getMaximumOfMonth(Util.getPastDateStr(i * 7))))) {
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
						past.get(dates[0] + "-" + dates[1] + "-" + Util.getWeekInMonth(Util.getPastDateStr(i * 7)) + gubun[Integer.parseInt(local) - 1] + "defCnt"));
				death[i - 1] = Integer.parseInt(
						past.get(dates[0] + "-" + dates[1] + "-" + Util.getWeekInMonth(Util.getPastDateStr(i * 7)) + gubun[Integer.parseInt(local) - 1] + "deathCnt"));
			} // 월별 정보
			else if (cycle.equals("monthly")) {
				String[] dates = Util.getPastDateStr(i * 30).split("-");
				day[i - 1] = dates[1] + "월";
				newcase[i - 1] = Util.getAsInt(past.get(
						dates[0] + "-" + dates[1] + "-" + Util.getMaximumOfMonth(Util.getPastDateStr(i * 30)) + gubun[Integer.parseInt(local) - 1] + "defCnt"), 0);
				death[i - 1] = Util.getAsInt(past.get(
						dates[0] + "-" + dates[1] + "-" + Util.getMaximumOfMonth(Util.getPastDateStr(i * 30)) + gubun[Integer.parseInt(local) - 1] + "deathCnt"), 0);
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

		String chk1 = "";
		String chk2 = "";
		if (place.equals("korea")) {
			chk1 = "check1";
			chk2 = "check11";
		} else if (place.equals("world")) {
			chk1 = "check2";
			chk2 = "check22";
		} else {
			chk1 = "check3";
			chk2 = "check33";
		}
		req.setAttribute(chk1, "blue");
		req.setAttribute(chk2, "pointer-events-none cursor-default");
		req.setAttribute("local", local);
		return "main";
	}
}
