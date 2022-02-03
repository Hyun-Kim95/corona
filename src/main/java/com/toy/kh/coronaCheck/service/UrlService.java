package com.toy.kh.coronaCheck.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.toy.kh.coronaCheck.util.Util;

@Service
public class UrlService {

	// 오늘, 어제와의 차이 정보확인 가능(신규 확진자 수, 확진자 수, 완치자 수, 사망자 수, 전일대비 증감)
	public Map<String, String> todayResult(){
		
		Map<String, String> today = new HashMap<>();
		
		try {
	        StringBuilder urlBuilder = new StringBuilder("https://api.corona-19.kr/korea/country/new"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=IpcdjWAi6aBUOQsC5o97tk3uNwymHV2Lr"); /*Service Key*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
//	        System.out.println("Response code: " + conn.getResponseCode());
	        
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
         
            String[] ziyok = { "korea", "seoul", "busan", "daegu", "incheon", "gwangju", "daejeon", "ulsan", "sejong",
    				"gyeonggi", "gangwon", "chungbuk", "chungnam", "jeonbuk", "jeonnam", "gyeongbuk", "gyeongnam", "jeju" };
            
            // 객체형태로
            // {"returnType":"json","clearDate":"--",.......},...
            for (String zi : ziyok) {
            	JSONObject local = (JSONObject) obj.get(zi);		// 지역명
                String countryName = (String) local.get("countryName");	// 시도명(지역명)	
                String newCase = (String) local.get("newCase");			// 신규확진환자수
                String totalCase = (String) local.get("totalCase");		// 확진환자수
                String recovered = (String) local.get("recovered");		// 완치자수
                String death = (String) local.get("death");				// 사망자
                String percentage  = (String) local.get("percentage");	// 발생률
                String newCcase  = (String) local.get("newCcase");		// 전일대비증감-해외유입
                String newFcase  = (String) local.get("newFcase");		// 전일대비증감-지역발생
                
                today.put(zi + "countryName", countryName);
                today.put(zi + "newCase", newCase);
                today.put(zi + "totalCase", totalCase);
                today.put(zi + "recovered", recovered);
                today.put(zi + "death", death);
                today.put(zi + "percentage", percentage);
                today.put(zi + "newCcase", newCcase);
                today.put(zi + "newFcase", newFcase);
			}
            
	        rd.close();
	        conn.disconnect();
	        
	        return today;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return today;
	}
	
	// 과거의 기록들 확인 가능(확진자 수, 사망자 수) 오늘 + 1달전까지
	public Map<String, String> pastResult(){
		Map<String, String> past = new HashMap<>();

		String day = Util.getNowDateStr();
		String month = Util.getPastMonthStr();
		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Uj7JHVlaJcD5DBtPQECFGH6tf1vBmpRshO6aEpIULQ7qrQ%2FCZiiycs6W6O1AV9pRmCPCiNBrc5SjUQAzqAVjeQ%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(day, "UTF-8")); /*검색할 생성일 범위의 시작*/
	        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /*검색할 생성일 범위의 종료*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
//	        System.out.println("Response code: " + conn.getResponseCode());

	        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url.toString());

			// 제일 첫번째 태그
			doc.getDocumentElement().normalize();

			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("item");
			for(int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);

				Element eElement = (Element) nNode;
				
//				System.out.println("해당일자 : " + getTagValue("stateDt", eElement));

				past.put(getTagValue("stateDt", eElement) + "dicideCnt", getTagValue("decideCnt", eElement));
				past.put(getTagValue("stateDt", eElement) + "deathCnt", getTagValue("deathCnt", eElement));
			}

	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }

	        rd.close();
	        conn.disconnect();
//	        System.out.println(sb.toString());

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return past;
	}

	// xml의 tag값의 정보를 가져오는 메서드(과거기록 확인할때 사용)
	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		if(nValue == null)
			return null;
		return nValue.getNodeValue();
	}
}
