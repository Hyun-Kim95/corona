<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>dataList</title>
<%@ include file="head.jspf"%>
<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<div class="container mx-auto p-1">

	<div class="">
		<div class="flex text-center shadow rounded bg-white mr-1 mt-3 p-1">
			<div class="flex-grow"></div>
			<div class="text-md font-bold px-10 py-3">확진자:<br><span class="text-red-600">${koreatotalCase}</span></div>
			<div class="text-md font-bold px-10 py-3">완치자:<br><span class="text-blue-600">${korearecovered}</span></div>
			<div class="text-md font-bold px-10 py-3">사망자:<br>${koreadeath}</div>
			<div class="flex-grow"></div>
		</div>
		<!-- 금일 확진자 -->
		<div class="flex shadow rounded bg-white mr-1 mt-3 p-1">
			<div class="text-md font-bold px-10 py-3">신규 확진자<br>${koreanewCase}</div>
			<div>
				<div class="text-md font-bold px-10 py-3"><span class="text-gray-400">vs </span>어제: <span class="text-red-600">${koreanewCcase}</span></div>
				<div class="text-md font-bold px-10 py-3"><span class="text-gray-400">vs </span>1주전: <span class="text-red-600">${onedicideCnt}</span></div>
			</div>
			<div>
				<div class="text-md font-bold px-10 py-3"><span class="text-gray-400">vs </span>2주전: <span class="text-red-600">${twodicideCnt}</span></div>
				<div class="text-md font-bold px-10 py-3"><span class="text-gray-400">vs </span>1달전: <span class="text-red-600">${monthdicideCnt}</span></div>
			</div>
		</div>
		<!-- 그래프 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">누적 그래프</div>
			<button class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" name="daily">일별</button>
			<button class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" name="weekly">주별</button>
			<button class="btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded" name="monthly">월별</button>
			
			<select id="select-choice" class="btn-primary bg-red-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">
				<option value="dicideCnt">확진자</option>
				<option value="deathCnt">사망자</option>
			</select>
			<script>
				$('#select-choice').val(this.value());
				$('#select-choice').change(function() {
					location.href = '?result=' + this.value;
				});
			</script>
			<div class="flex">
				
				<canvas class="w-screen" id="chart1"></canvas>
				<canvas class="w-screen" id="chart2"></canvas>	
			</div>
		</div>
		<!-- 지역별 표시 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-left">
			<div class="text-3xl font-bold px-10 py-3 text-center">지역별 표시</div>
			<div class="text-md font-bold px-10 py-3">서울: ${seoulnewCase}</div>
			<div class="text-md font-bold px-10 py-3">부산: ${busannewCase}</div>
			<div class="text-md font-bold px-10 py-3">대구: ${daegunewCase}</div>
			<div class="text-md font-bold px-10 py-3">인천: ${incheonnewCase}</div>
			<div class="text-md font-bold px-10 py-3">광주: ${gwangjunewCase}</div>
			<div class="text-md font-bold px-10 py-3">대전: ${daejeonnewCase}</div>
			<div class="text-md font-bold px-10 py-3">울산: ${ulsannewCase}</div>
			<div class="text-md font-bold px-10 py-3">세종: ${sejongnewCase}</div>
			<div class="text-md font-bold px-10 py-3">경기: ${gyeongginewCase}</div>
			<div class="text-md font-bold px-10 py-3">강원: ${gangwonnewCase}</div>
			<div class="text-md font-bold px-10 py-3">충북: ${chungbuknewCase}</div>
			<div class="text-md font-bold px-10 py-3">충남: ${chungnamnewCase}</div>
			<div class="text-md font-bold px-10 py-3">전북: ${jeonbuknewCase}</div>
			<div class="text-md font-bold px-10 py-3">전남: ${jeonnamnewCase}</div>
			<div class="text-md font-bold px-10 py-3">경북: ${gyeongbuknewCase}</div>
			<div class="text-md font-bold px-10 py-3">경남: ${gyeongnamnewCase}</div>
			<div class="text-md font-bold px-10 py-3">제주: ${jejunewCase}</div>
		</div>
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
        	label: ["확진자"],
        	backgroundColor: "red",
        	borderColor: "red",
        	data: ["${newcase[6]}", "${newcase[5]}", "${newcase[4]}", "${newcase[3]}", "${newcase[2]}", "${newcase[1]}", "${newcase[0]}"],
      	}
    	],
  	};
  	
  	const dataChart2 = {
		labels: labelsChart,
		datasets: [
		{
        	label: ["사망자"],
        	backgroundColor: "violet",
        	borderColor: "violet",
        	data: ["${death[6]}", "${death[5]}", "${death[4]}", "${death[3]}", "${death[2]}", "${death[1]}", "${death[0]}"],
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
  	const configChart2 = {
		type: "line",
		data: dataChart2,
		options: {
    		responsive: false,
    	}
  	}
  	var chart2 = new Chart(
		document.getElementById("chart2"),
    	configChart2
  	)
</script>
<%@ include file="foot.jspf"%>