<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>dataList</title>
<%@ include file="head.jspf"%>
<div class="container mx-auto p-1">

	<div class="">
		<div class="flex shadow rounded bg-white mr-1 mt-3 p-1">
			<div class="text-md font-bold px-10 py-3">신규 확진자:<br>${koreanewCase}</div>
			<div class="text-md font-bold px-10 py-3">확진자:<br>${koreatotalCase}</div>
			<div class="text-md font-bold px-10 py-3">완치자:<br>${korearecovered}</div>
			<div class="text-md font-bold px-10 py-3">사망자:<br>${koreadeath}</div>
		</div>
		<!-- 금일 확진자 -->
		<div class="flex shadow rounded bg-white mr-1 mt-3 p-1">
			<div class="text-md font-bold px-10 py-3">신규 확진자:<br>${koreanewCase}</div>
			<div class="text-md font-bold px-10 py-3">vs어제:<br>${koreanewFcase}</div>
			<div class="text-md font-bold px-10 py-3">vs1주 전:<br></div>
			<div class="text-md font-bold px-10 py-3">vs2주 전:<br></div>
			<div class="text-md font-bold px-10 py-3">vs1달 전:<br></div>
		</div>
		<!-- 그래프 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-center">
			<div class="text-3xl font-bold px-10 py-3">그래프(확진,사망,검사자 등)</div>
			<canvas class="p-5" id="chartBar"></canvas>
		</div>
		<!-- 지역별 표시 -->
		<div class="shadow rounded bg-white container mr-1 mt-3 p-1 text-left">
			<div class="text-3xl font-bold px-10 py-3 text-center">지역별 표시</div>
			<div class="text-md font-bold px-10 py-3">서울:<br>${seoulnewCase}</div>
			<div class="text-md font-bold px-10 py-3">부산:<br>${busannewCase}</div>
			<div class="text-md font-bold px-10 py-3">대구:<br>${daegunewCase}</div>
			<div class="text-md font-bold px-10 py-3">인천:<br>${incheonnewCase}</div>
			<div class="text-md font-bold px-10 py-3">광주:<br>${gwangjunewCase}</div>
			<div class="text-md font-bold px-10 py-3">대전:<br>${daejeonnewCase}</div>
			<div class="text-md font-bold px-10 py-3">울산:<br>${ulsannewCase}</div>
			<div class="text-md font-bold px-10 py-3">세종:<br>${sejongnewCase}</div>
			<div class="text-md font-bold px-10 py-3">경기:<br>${gyeongginewCase}</div>
			<div class="text-md font-bold px-10 py-3">강원:<br>${gangwonnewCase}</div>
			<div class="text-md font-bold px-10 py-3">충북:<br>${chungbuknewCase}</div>
			<div class="text-md font-bold px-10 py-3">충남:<br>${chungnamnewCase}</div>
			<div class="text-md font-bold px-10 py-3">전북:<br>${jeonbuknewCase}</div>
			<div class="text-md font-bold px-10 py-3">전남:<br>${jeonnamnewCase}</div>
			<div class="text-md font-bold px-10 py-3">경북:<br>${gyeongbuknewCase}</div>
			<div class="text-md font-bold px-10 py-3">경남:<br>${gyeongnamnewCase}</div>
			<div class="text-md font-bold px-10 py-3">제주:<br>${jejunewCase}</div>
		</div>
	</div>
	
</div>
<%@ include file="foot.jspf"%>