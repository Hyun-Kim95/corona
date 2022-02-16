<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>dataList</title>
<%@ include file="head.jspf"%>
<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<div class="container mx-auto md:flex justify-center">

	<div class="leftcomponent">
		<div class="flex">
			<div class="flex-grow"></div>
			<div class="flex text-center shadow rounded bg-white mt-3 w-96">
				<div class="flex-grow"></div>
				<div class="text-md font-bold px-5 py-3">확진자:<br><span class="text-red-600">${koreatotalCase}</span></div>
				<div class="text-md font-bold px-5 py-3">완치자:<br><span class="text-blue-600">${korearecovered}</span></div>
				<div class="text-md font-bold px-5 py-3">사망자:<br>${koreadeath}</div>
				<div class="flex-grow"></div>
			</div>
			<div class="flex-grow"></div>
		</div>
		<!-- 금일 확진자 -->
		<div class="flex">
			<div class="flex-grow"></div>
			<div class="shadow rounded bg-white mt-3 w-96">
				<select id="select-local" class="btn-primary hover:bg-blue-dark text-lg m-1 py-1 px-2 rounded border-2">
					<option value="1">전국</option>
					<option value="2">서울</option>
					<option value="3">부산</option>
					<option value="4">대구</option>
					<option value="5">인천</option>
					<option value="6">광주</option>
					<option value="7">대전</option>
					<option value="8">울산</option>
					<option value="9">세종</option>
					<option value="10">경기</option>
					<option value="11">강원</option>
					<option value="12">충북</option>
					<option value="13">충남</option>
					<option value="14">전북</option>
					<option value="15">전남</option>
					<option value="16">경북</option>
					<option value="17">경남</option>
					<option value="18">제주</option>
				</select>
				<script>
					$('#select-local').val(${local});
					$('#select-local').change(function() {
						location.href = '?local=' + this.value;
					});
				</script>
				<div class="flex">
					<div class="text-lg font-bold px-5 py-9">신규 확진자<br><span class="text-3xl">${newCase}</span></div>
					<div>
						<div class="text-md font-bold px-5 pt-3"><span class="text-gray-400">vs </span>어제: <span class="text-red-600"><br>${newCcase}</span></div>
						<div class="text-md font-bold px-5 pt-3"><span class="text-gray-400">vs </span>1주전: <span class="text-red-600"><br>${onedicideCnt}</span></div>
					</div>
					<div>
						<div class="text-md font-bold px-5 pt-3"><span class="text-gray-400">vs </span>2주전: <span class="text-red-600"><br>${twodicideCnt}</span></div>
						<div class="text-md font-bold px-5 pt-3"><span class="text-gray-400">vs </span>1달전: <span class="text-red-600"><br>${monthdicideCnt}</span></div>
					</div>
				</div>
			</div>
			<div class="flex-grow"></div>
		</div>
		<div class="flex">
			<div class="flex-grow"></div>
			<!-- 그래프 -->
			<div class="shadow rounded bg-white container mt-3 text-center w-96">
				<div class="text-3xl font-bold px-5 py-3">누적 그래프</div>
				<a href="/main?result=${result}&cycle=daily" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">일별</a>
				<a href="/main?result=${result}&cycle=weekly" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">주별</a>
				<a href="/main?result=${result}&cycle=monthly" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">월별</a>
				
				<select id="select-choice" class="btn-primary bg-red-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">
					<option value="1">확진자</option>
					<option value="2">사망자</option>
				</select>
				<script>
					$('#select-choice').val(${result});
					$('#select-choice').change(function() {
						location.href = '?result=' + this.value + '&cycle=${cycle}';
					});
				</script>
				<div class="flex">
					<canvas class="w-screen" id="chart1"></canvas>	
				</div>
			</div>
			<div class="flex-grow"></div>
		</div>
	</div>
	<div class="flex">
		<div class="flex-grow"></div>
		<!-- 백신 표시 -->
		<div class="shadow rounded bg-white container md:mx-3 mt-3 text-left w-96">
			<div class="text-3xl font-bold px-10 py-3 text-center">백신 센터</div>
			<div id="map" style="width:100%;height:400px;"></div>

			<script>
			var HOME_PATH = window.HOME_PATH || '.';
			var MARKER_SPRITE_X_OFFSET = 20,
			    MARKER_SPRITE_Y_OFFSET = 10,
			    MARKER_SPRITE_POSITION = {
			        "A0": [0, 0],
			        "B0": [MARKER_SPRITE_X_OFFSET, 0],
			        "C0": [MARKER_SPRITE_X_OFFSET*2, 0],
			        "D0": [MARKER_SPRITE_X_OFFSET*3, 0],
			        "E0": [MARKER_SPRITE_X_OFFSET*4, 0],
			        "F0": [MARKER_SPRITE_X_OFFSET*5, 0],
			        "G0": [MARKER_SPRITE_X_OFFSET*6, 0],
			        "H0": [MARKER_SPRITE_X_OFFSET*7, 0],
			        "I0": [MARKER_SPRITE_X_OFFSET*8, 0],

			        "A1": [0, MARKER_SPRITE_Y_OFFSET],
			        "B1": [MARKER_SPRITE_X_OFFSET, MARKER_SPRITE_Y_OFFSET],
			        "C1": [MARKER_SPRITE_X_OFFSET*2, MARKER_SPRITE_Y_OFFSET],
			        "D1": [MARKER_SPRITE_X_OFFSET*3, MARKER_SPRITE_Y_OFFSET],
			        "E1": [MARKER_SPRITE_X_OFFSET*4, MARKER_SPRITE_Y_OFFSET],
			        "F1": [MARKER_SPRITE_X_OFFSET*5, MARKER_SPRITE_Y_OFFSET],
			        "G1": [MARKER_SPRITE_X_OFFSET*6, MARKER_SPRITE_Y_OFFSET],
			        "H1": [MARKER_SPRITE_X_OFFSET*7, MARKER_SPRITE_Y_OFFSET],
			        "I1": [MARKER_SPRITE_X_OFFSET*8, MARKER_SPRITE_Y_OFFSET],

			        "A2": [0, MARKER_SPRITE_Y_OFFSET*2],
			        "B2": [MARKER_SPRITE_X_OFFSET, MARKER_SPRITE_Y_OFFSET*2],
			        "C2": [MARKER_SPRITE_X_OFFSET*2, MARKER_SPRITE_Y_OFFSET*2],
			        "D2": [MARKER_SPRITE_X_OFFSET*3, MARKER_SPRITE_Y_OFFSET*2],
			        "E2": [MARKER_SPRITE_X_OFFSET*4, MARKER_SPRITE_Y_OFFSET*2],
			        "F2": [MARKER_SPRITE_X_OFFSET*5, MARKER_SPRITE_Y_OFFSET*2],
			        "G2": [MARKER_SPRITE_X_OFFSET*6, MARKER_SPRITE_Y_OFFSET*2],
			        "H2": [MARKER_SPRITE_X_OFFSET*7, MARKER_SPRITE_Y_OFFSET*2],
			        "I2": [MARKER_SPRITE_X_OFFSET*8, MARKER_SPRITE_Y_OFFSET*2]
			    };

			var map = new naver.maps.Map('map', {
			    center: new naver.maps.LatLng(37.3595704, 127.105399),
			    zoom: 10
			});

			var bounds = map.getBounds(),
			    southWest = bounds.getSW(),
			    northEast = bounds.getNE(),
			    lngSpan = northEast.lng() - southWest.lng(),
			    latSpan = northEast.lat() - southWest.lat();

			var markers = [],
			    infoWindows = [];

			for (var key in MARKER_SPRITE_POSITION) {

			    var position = new naver.maps.LatLng(
			        southWest.lat() + latSpan * Math.random(),
			        southWest.lng() + lngSpan * Math.random());

			    var marker = new naver.maps.Marker({
			        map: map,
			        position: position,
			        title: key,
			        icon: {
			            url: HOME_PATH +'/img/example/sp_pins_spot_v3.png',
			            size: new naver.maps.Size(24, 37),
			            anchor: new naver.maps.Point(12, 37),
			            origin: new naver.maps.Point(MARKER_SPRITE_POSITION[key][0], MARKER_SPRITE_POSITION[key][1])
			        },
			        zIndex: 100
			    });

			    var infoWindow = new naver.maps.InfoWindow({
			        content: '<div style="width:150px;text-align:center;padding:10px;">The Letter is <b>"'+ key.substr(0, 1) +'"</b>.</div>'
			    });

			    markers.push(marker);
			    infoWindows.push(infoWindow);
			};

			naver.maps.Event.addListener(map, 'idle', function() {
			    updateMarkers(map, markers);
			});

			function updateMarkers(map, markers) {

			    var mapBounds = map.getBounds();
			    var marker, position;

			    for (var i = 0; i < markers.length; i++) {

			        marker = markers[i]
			        position = marker.getPosition();

			        if (mapBounds.hasLatLng(position)) {
			            showMarker(map, marker);
			        } else {
			            hideMarker(map, marker);
			        }
			    }
			}

			function showMarker(map, marker) {

			    if (marker.setMap()) return;
			    marker.setMap(map);
			}

			function hideMarker(map, marker) {

			    if (!marker.setMap()) return;
			    marker.setMap(null);
			}

			// 해당 마커의 인덱스를 seq라는 클로저 변수로 저장하는 이벤트 핸들러를 반환합니다.
			function getClickHandler(seq) {
			    return function(e) {
			        var marker = markers[seq],
			            infoWindow = infoWindows[seq];

			        if (infoWindow.getMap()) {
			            infoWindow.close();
			        } else {
			            infoWindow.open(map, marker);
			        }
			    }
			}

			for (var i=0, ii=markers.length; i<ii; i++) {
			    naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
			}
			</script>
		</div>
		<div class="flex-grow"></div>
	</div>
</div>
<!-- Chart bar -->
<script>
	const labelsChart = [
		"${day[6]}",
		"${day[5]}",
		"${day[4]}",
		"${day[3]}",
		"${day[2]}",
		"${day[1]}",
		"${day[0]}",
	];
  	const dataChart1 = {
    	labels: labelsChart,
    	datasets: [
      	{
        	label: ["${val}"],
        	backgroundColor: "${col}",
        	borderColor: "${col}",
        	data: ["${newcase[6]}", "${newcase[5]}", "${newcase[4]}", "${newcase[3]}", "${newcase[2]}", "${newcase[1]}", "${newcase[0]}"],
        	borderWidth:1
      	}
    	],
  	};
  	
	const configChart1 = {
   		type: "line",
    	data: dataChart1,
    	options: {
    		responsive: false,
    	}
  	};
  	var chart1 = new Chart(
    	document.getElementById("chart1"),
    	configChart1
  	);
</script>
<%@ include file="foot.jspf"%>