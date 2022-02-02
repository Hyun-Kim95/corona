<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>dataList</title>
<%@ include file="head.jspf"%>
<!-- Required chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<div class="container mx-auto p-1">

	<div class="">
		<div class="flex shadow rounded bg-white mr-1 mt-3 p-1">
			<div class="text-md font-bold px-10 py-3">신규 확진자:<br>${koreanewCase}</div>
			<div class="text-md font-bold px-10 py-3">총 확진자:<br>${koreatotalCase}</div>
			<div class="text-md font-bold px-10 py-3">완치자:<br>${korearecovered}</div>
			<div class="text-md font-bold px-10 py-3">사망자:<br>${koreadeath}</div>
		</div>
		<!-- 금일 확진자 -->
		<div class="flex shadow rounded bg-white mr-1 mt-3 p-1">
			<div class="text-md font-bold px-10 py-3">신규 확진자:<br>${koreanewCase}</div>
			<div class="text-md font-bold px-10 py-3">vs어제:<br>${koreanewFcase}</div>
			<div class="text-md font-bold px-10 py-3">vs1주 전:<br>${onedicideCnt}</div>
			<div class="text-md font-bold px-10 py-3">vs2주 전:<br>${twodicideCnt}</div>
			<div class="text-md font-bold px-10 py-3">vs1달 전:<br>${monthdicideCnt}</div>
		</div>
		<!-- 그래프 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">그래프(확진,사망)</div>
			<canvas class="p-5" id="chart"></canvas>
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
  	const dataChart = {
    	labels: labelsChart,
    	datasets: [
      	{
        	label: ["확진자"],
        	yAxisID: "left",
        	backgroundColor: "red",
        	borderColor: "red",
        	data: ["${newcase[6]}", "${newcase[5]}", "${newcase[4]}", "${newcase[3]}", "${newcase[2]}", "${newcase[1]}", "${newcase[0]}"],
      	},
      	{
        	label: ["사망자"],
        	yAxisID: "right",
        	backgroundColor: "violet",
        	borderColor: "violet",
        	data: ["${death[6]}", "${death[5]}", "${death[4]}", "${death[3]}", "${death[2]}", "${death[1]}", "${death[0]}"],
      	},
    	],
  	};
	const configChart = {
   		type: "line",
    	data: dataChart,
  	};
  	var chart = new Chart(
    	document.getElementById("chart"),
    	configChart
  	);

</script>
<%@ include file="foot.jspf"%>