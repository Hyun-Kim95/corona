package com.toy.kh.coronaCheck.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.toy.kh.coronaCheck.dao.CoronaDao;
import com.toy.kh.coronaCheck.dto.CoronaDto;
import com.toy.kh.coronaCheck.util.Util;

@Service
public class CoronaService {

	@Autowired
	private CoronaDao coronaDao;

	// 오늘, 어제와의 차이 정보확인 가능(신규 확진자 수, 확진자 수, 완치자 수, 사망자 수, 전일대비 증감)
	public Map<String, String> todayResult(String local1) {

		Map<String, String> today = new HashMap<>();

		try {
			StringBuilder urlBuilder = new StringBuilder("https://api.corona-19.kr/korea/country/new"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=IpcdjWAi6aBUOQsC5o97tk3uNwymHV2Lr"); /* Service Key */
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
//	        System.out.println("Response code: " + conn.getResponseCode());

			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
			JSONObject obj = (JSONObject) parser.parse(result);

			String[] ziyok = { "korea", "seoul", "busan", "daegu", "incheon", "gwangju", "daejeon", "ulsan", "sejong",
					"gyeonggi", "gangwon", "chungbuk", "chungnam", "jeonbuk", "jeonnam", "gyeongbuk", "gyeongnam",
					"jeju" };

			String[] ziy = { "korea", "korea" };
			if (!local1.equals("1")) {
				ziy[1] = ziyok[Integer.parseInt(local1) - 1];
			}
			// 객체형태로
			// {"returnType":"json","clearDate":"--",.......},...
			for (String zi : ziy) {
				JSONObject local = (JSONObject) obj.get(zi); // 지역명
				String countryName = (String) local.get("countryName"); // 시도명(지역명)
				String newCase = (String) local.get("newCase"); // 신규확진환자수
				String totalCase = (String) local.get("totalCase"); // 확진환자수
				String recovered = (String) local.get("recovered"); // 완치자수
				String death = (String) local.get("death"); // 사망자
				String percentage = (String) local.get("percentage"); // 발생률
				String newCcase = (String) local.get("newCcase"); // 전일대비증감-해외유입
				String newFcase = (String) local.get("newFcase"); // 전일대비증감-지역발생
				if (zi.equals("korea")) {
					today.put(zi + "countryName", countryName);
					today.put(zi + "newCase", newCase);
					today.put(zi + "totalCase", totalCase);
					today.put(zi + "recovered", recovered);
					today.put(zi + "death", death);
					today.put(zi + "percentage", percentage);
					today.put(zi + "newCcase", newCcase);
					today.put(zi + "newFcase", newFcase);
				}
				today.put("countryName", countryName);
				today.put("newCase", newCase);
				today.put("totalCase", totalCase);
				today.put("recovered", recovered);
				today.put("death", death);
				today.put("percentage", percentage);
				today.put("newCcase", newCcase);
				today.put("newFcase", newFcase);
			}

			rd.close();
			conn.disconnect();

			return today;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return today;
	}

	// xml의 tag값의 정보를 가져오는 메서드
	private static String getTagValue(String tag, Element eElement) {
		if (eElement.getElementsByTagName(tag).item(0) == null) {
			return null;
		}
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	// 과거의 지역별 기록들 확인 가능(확진자 수, 사망자 수) 오늘 + 1달전까지
	public Map<String, String> pastResult() {
		Map<String, String> past = new HashMap<>();

		String day = Util.getNowDateStr();
		String month = Util.getPastMonthStr(7);
		day = day.split("-")[0] + day.split("-")[1] + day.split("-")[2];
		month = month.split("-")[0] + month.split("-")[1] + month.split("-")[2];
		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=Uj7JHVlaJcD5DBtPQECFGH6tf1vBmpRshO6aEpIULQ7qrQ%2FCZiiycs6W6O1AV9pRmCPCiNBrc5SjUQAzqAVjeQ%3D%3D"); /* Service Key */
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 한 페이지 결과 수 */
			urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /* 검색할 생성일 범위의 시작 */
			urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "="	+ URLEncoder.encode(day, "UTF-8")); /* 검색할 생성일 범위의 종료 */
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
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);

				Element eElement = (Element) nNode;
				if (!getTagValue("gubunEn", eElement).equals("Lazaretto")) {
					past.put(getTagValue("createDt", eElement).split(" ")[0] + getTagValue("gubunEn", eElement)
							+ "defCnt", getTagValue("defCnt", eElement));
					past.put(getTagValue("createDt", eElement).split(" ")[0] + getTagValue("gubunEn", eElement)
							+ "deathCnt", getTagValue("deathCnt", eElement));
				}
//				System.out.println("해당일자 : " + getTagValue("stateDt", eElement));
			}

			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return past;
	}

	// 백신센터 정보(DB)
	public List<CoronaDto> vaccineOfDB(String local){
		return coronaDao.getCenterOfLocal(local);
	}

	// 백신센터 정보(api)
	public Map<String, List<String>> vaccine(String local) {
		Map<String, List<String>> center = new HashMap<>();
		try {
			StringBuilder urlBuilder = new StringBuilder(
					"http://apis.data.go.kr/B551182/rprtHospService/getRprtHospService"); /* URL */
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=Uj7JHVlaJcD5DBtPQECFGH6tf1vBmpRshO6aEpIULQ7qrQ%2FCZiiycs6W6O1AV9pRmCPCiNBrc5SjUQAzqAVjeQ%3D%3D"); /*
																															 */
			urlBuilder.append(
					"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
					+ URLEncoder.encode("7500", "UTF-8")); /* 한 페이지 결과 수(수정시점 7157개) */
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url.toString());

//			// Null 값이 없는 센터만 DB에 저장
//			doc.getDocumentElement().normalize();
//			NodeList nList = doc.getElementsByTagName("item");
//			for (int i = 0; i < nList.getLength(); i++) {
//				Node nNode = nList.item(i);
//
//				Element eElement = (Element) nNode;
//				ArrayList<String> tList = new ArrayList<>();
//				tList.add(getTagValue("yadmNm", eElement));
//				tList.add(getTagValue("addr", eElement));
//				tList.add(getTagValue("telno", eElement));
//				tList.add(getTagValue("XPosWgs84", eElement));
//				tList.add(getTagValue("YPosWgs84", eElement));
//				if (tList.contains(null)) {
//					continue;
//				}
//				coronaDao.saveCenter(getTagValue("yadmNm", eElement), getTagValue("sidoCdNm", eElement),
//						getTagValue("addr", eElement), getTagValue("telno", eElement),
//						getTagValue("XPosWgs84", eElement), getTagValue("YPosWgs84", eElement));
//			}
			// 파싱할 tag
			NodeList nList = doc.getElementsByTagName("item");
			for(int i = 0; i < nList.getLength(); i++){
				Node nNode = nList.item(i);

				Element eElement = (Element) nNode;
				if(getTagValue("sidoCdNm", eElement).equals(local)) {
					ArrayList<String> tList = new ArrayList<String>();
					tList.add(getTagValue("addr", eElement));
					tList.add(getTagValue("telno", eElement));
					tList.add(getTagValue("XPosWgs84", eElement));
					tList.add(getTagValue("YPosWgs84", eElement));
					if(tList.contains(null)) {
						continue;
					}
					center.put(getTagValue("yadmNm", eElement), tList);
				}
			}
			rd.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return center;
	}
	
	// 주소 -> 좌표 변환
	public String[] geoCoding(String location) {
		String clientId = "d2pu9x1eno";  //clientId 
        String clientSecret = "eQjtRnD5A18QZUD86BiTizcDRXSanGvZ9wjCWJAv";  //clientSecret 
         
        try {
            String addr = URLEncoder.encode(location, "UTF-8");  //주소입력
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; //json
//            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode.xml?query=" + addr; // xml
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { 
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
//            System.out.println(responseCode);
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            
	        // JSON parser 만들어 문자열 데이터를 객체화한다.
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response.toString());
			
			JSONArray jsonArr = (JSONArray) obj.get("addresses");
			JSONObject jsonObj = (JSONObject) jsonArr.get(0);
			
			String coordx = (String) jsonObj.get("x");
			String coordy = (String) jsonObj.get("y");
			
			String[] coords = new String[2];
			coords[0] = coordx;
			coords[1] = coordy;			
			
            br.close();
            
            return coords;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
	}
}
