<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>dataList</title>
<%@ include file="head.jspf"%>
<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</script>
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
				<div class="flex">
					<div class="text-lg font-bold pl-10 py-5">신규 확진자<br><span class="text-3xl">${newCase}</span></div>
					<div>
						<div class="text-md font-bold px-5 py-1"><span class="text-gray-400">vs </span>어제: <span class="text-red-600"><br>${newCcase}</span></div>
						<div class="text-md font-bold px-5 py-1"><span class="text-gray-400">vs </span>1주전: <span class="text-red-600"><br>${onedicideCnt}</span></div>
					</div>
					<div>
						<div class="text-md font-bold px-5 py-1"><span class="text-gray-400">vs </span>2주전: <span class="text-red-600"><br>${twodicideCnt}</span></div>
						<div class="text-md font-bold px-5 py-1"><span class="text-gray-400">vs </span>1달전: <span class="text-red-600"><br>${monthdicideCnt}</span></div>
					</div>
				</div>
			</div>
			<div class="flex-grow"></div>
		</div>
		<div class="flex">
			<div class="flex-grow"></div>
			<!-- 그래프 -->
			<div class="shadow rounded bg-white container mt-3 text-center w-96">
				<div class="text-3xl font-bold px-5 py-3">
					<c:if test="${cycle == 'daily'}">
						일별
					</c:if>
					<c:if test="${cycle == 'weekly'}">
						주별
					</c:if>
					<c:if test="${cycle == 'monthly'}">
						월별
					</c:if>
				누적 그래프</div>
				<a href="/main?result=${result}&cycle=daily&local=${local}&address=${address}" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">일별</a>
				<a href="/main?result=${result}&cycle=weekly&local=${local}&address=${address}" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">주별</a>
				<a href="/main?result=${result}&cycle=monthly&local=${local}&address=${address}" class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">월별</a>
				
				<select id="select-choice" class="btn-primary bg-red-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">
					<option value="1">확진자</option>
					<option value="2">사망자</option>
				</select>
				<script>
					$('#select-choice').val(${result});
					$('#select-choice').change(function() {
						location.href = '?result=' + this.value + '&cycle=${cycle}&local=${local}&address=${address}';
					});
				</script>
				<div class="flex">
					<canvas class="w-screen" id="chart1"></canvas>	
				</div>
			</div>
			<div class="flex-grow"></div>
		</div>
	</div>
	<div class="m-1"></div>
	<div class="rightcomponent flex">
		<div class="flex-grow"></div>
		<!-- 백신 표시 -->
		<div class="shadow rounded bg-white container mt-3 w-96">
			<div class="flex">
				<div class="text-xl font-bold mx-auto px-2 py-3 text-center">백신센터</div>
				<form action="main" method="POST" class="flex my-2 mx-auto">
					<input type="hidden" name="result" value="${result}"/>
					<input type="hidden" name="cycle" value="${cycle}"/>
					<input type="hidden" name="local" value="${local}"/>
					<input name="address" class="mx-1 px-5 appearance-none border rounded w-full text-grey-darker"
						type="text" placeholder="주소를 입력하세요" value="${address}"/>
					<input class="mx-1 btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold px-2 rounded"
						type="submit" value="검색" />
				</form>
			</div>
			<div id="map" style="width:100%;height:400px;"></div>
			<div class="text-center p-2">※검색한 주소의 주변을 표시합니다.</div>
			<script>
				var HOME_PATH = window.HOME_PATH || '.';
				var MARKER_SPRITE_POSITION = new Array();	// 백신센터 이름, 주소 등 담김
				for(const center of ${centerLocal}){		// 위에다 백신센터별로 담음
					MARKER_SPRITE_POSITION.push({...center});
				}
				var map = new naver.maps.Map('map', {		// 가져온 지역의 첫번째 센터를 중심으로 지도가 보이도록
				    center: new naver.maps.LatLng(${mapCenter_y}, ${mapCenter_x}),
				    zoom: 15
				});
	
				var markers = [],
				    infoWindows = [];
	
				for (var index in MARKER_SPRITE_POSITION) {		// 각 포지션 지정 및 표시할 메시지 생성
				    var position = new naver.maps.LatLng(
				    		MARKER_SPRITE_POSITION[index][4], MARKER_SPRITE_POSITION[index][3]);
	
				    var marker = new naver.maps.Marker({
				        map: map,
				        position: position,
				        title: MARKER_SPRITE_POSITION[index][0],
				        zIndex: 100
				    });
	
				    var infoWindow = new naver.maps.InfoWindow({
				    	anchorSkew: true,
				        content: '<div style="text-align:center;padding:1px;">'
				        + '<b>' + MARKER_SPRITE_POSITION[index][0] +'</b><br>'
				        + MARKER_SPRITE_POSITION[index][1] +'<br>'
				        + '<b>'+ MARKER_SPRITE_POSITION[index][2] +'</b>.</div>'
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