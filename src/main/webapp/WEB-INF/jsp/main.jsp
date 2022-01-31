<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>dataList</title>
<%@ include file="head.jspf"%>
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
			<canvas class="p-5" id="chartBar"></canvas>
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
	const labelsBarChart = [
		"${classification[0]}",
		"${classification[1]}",
		"${classification[2]}",
		"${classification[3]}",
		"${classification[4]}",
		"${classification[5]}",
		"${classification[6]}",
		"${classification[7]}",
	];
  	const dataBarChart = {
    	labels: labelsBarChart,
    	datasets: [
      	{
        	label: ["분류별 실패율"],
        	backgroundColor: "red",
        	borderColor: "red",
        	data: ["${failRate[0]}", "${failRate[1]}", "${failRate[2]}", "${failRate[3]}", "${failRate[4]}", "${failRate[5]}", "${failRate[6]}", "${failRate[7]}"],
      	},
      	{
        	label: ["전체에서 각 분류가 차지하는 비율"],
        	backgroundColor: "violet",
        	borderColor: "violet",
        	data: ["${totalRate[0]}", "${totalRate[1]}", "${totalRate[2]}", "${totalRate[3]}", "${totalRate[4]}", "${totalRate[5]}", "${totalRate[6]}", "${totalRate[7]}"],
      	},
    	],
  	};
	const configBarChart = {
   		type: "bar",
    	data: dataBarChart,
  	};
  	var chartBar = new Chart(
    	document.getElementById("chartBar"),
    	configBarChart
  	);
</script>
<%@ include file="foot.jspf"%>