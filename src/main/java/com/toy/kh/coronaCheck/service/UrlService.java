package com.toy.kh.coronaCheck.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

	public Map<String, String> todayResult(){
		
		Map<String, String> today = new HashMap<>();
		
		try {
	        StringBuilder urlBuilder = new StringBuilder("https://api.corona-19.kr/korea/country/new"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=IpcdjWAi6aBUOQsC5o97tk3uNwymHV2Lr"); /*Service Key*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        
	        StringBuilder sb = new StringBuilder();
	        String line;
	        String result = "";
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	            result = result.concat(line);
	        }
	        
	        // JSON parser 만들어 문자열 데이터를 객체화한다.
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(result);
            	        
            // 객체형태로
            // {"returnType":"json","clearDate":"--",.......},...
            String[] ziyok = {"korea", "seoul", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea", "korea"};
            JSONObject local = (JSONObject) obj.get(ziyok);		// 지역명
            String countryName = (String) local.get("countryName");	// 시도명(지역명)	
            String newCase = (String) local.get("newCase");			// 신규확진환자수
            String totalCase = (String) local.get("totalCase");		// 확진환자수
            String recovered = (String) local.get("recovered");		// 완치자수
            String death = (String) local.get("death");				// 사망자
            String percentage  = (String) local.get("percentage");	// 발생률
            String newCcase  = (String) local.get("newCcase");		// 전일대비증감-해외유입
            String newFcase  = (String) local.get("newFcase");		// 전일대비증감-지역발생
            
            System.out.println("시도명(지역명) : " + countryName);
            System.out.println("신규확진환자수 : " + newCase);
            System.out.println("확진환자수 : " + totalCase);
            System.out.println("완치자수 : " + recovered);
            System.out.println("사망자 : " + death);
            System.out.println("발생률 : " + percentage);
            System.out.println("전일대비증감-해외유입 : " + newCcase);
            System.out.println("전일대비증감-지역발생 : " + newFcase);
            
            today.put("countryName", countryName);
            today.put("newCase", newCase);
            today.put("totalCase", totalCase);
            today.put("recovered", recovered);
            today.put("death", death);
            today.put("percentage", percentage);
            today.put("newCcase", newCcase);
            today.put("newFcase", newFcase);
            
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
	        
	        return today;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return today;
	}
}
