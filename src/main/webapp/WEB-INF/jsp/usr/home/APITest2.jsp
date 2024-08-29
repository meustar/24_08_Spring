<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST2"></c:set>

<%@ include file="../common/head.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>지도 생성하기</title>

</head>
<body>
	<!-- 지도를 표시할 div 입니다 -->
	<div id="map" style="width: 100%; height: 350px;"></div>
	<p>
		<button onclick="setCenter()">지도 중심좌표 이동시키기</button>
		<button onclick="panTo()">지도 중심좌표 부드럽게 이동시키기</button>
	</p>

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5a91e73772661f290013c4764cf9017a"></script>
	<script>
		var lat;
		var lon;
		async function getCData2() {
			const API_KEY = '%2BKApk0ihIn7Any5BM3X7%2BnlJyc2yXA39m%2F%2BXn4uiAkLPB7wbUQENd3NzoFktNEchJlI1pyRmWXXZJhA%2FTNfeIg%3D%3D';
			const url = 'https://apis.data.go.kr/6300000/openapi2022/tasuInfo/gettasuInfo?pageNo=1&numOfRows=10&serviceKey='
					+ API_KEY;
			const response = await
			fetch(url);
			const data = await
			response.json();
			console.log("data", data);
			console.log("data.response", data.response);
			console.log("data.response.body", data.response.body);
			console.log("data.response.body.items", data.response.body.items);
			console.log("data.response.body.items[0]",
					data.response.body.items[0]);
			console.log("data.response.body.items[0].loCrdnt",
					data.response.body.items[0].loCrdnt);
			console.log("data.response.body.items[0].laCrdnt",
					data.response.body.items[0].laCrdnt);
			lat = data.response.body.items[0].laCrdnt;
			lon = data.response.body.items[0].loCrdnt;
		}
		getCData2();
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};
		// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);
		function setCenter() {
			// 이동할 위도 경도 위치를 생성합니다 
			var moveLatLon = new kakao.maps.LatLng(33.452613, 126.570888);
			// 지도 중심을 이동 시킵니다
			map.setCenter(moveLatLon);
		}
		function panTo() {
			// 이동할 위도 경도 위치를 생성합니다 
			var moveLatLon = new kakao.maps.LatLng(lat, lon);
			// 지도 중심을 부드럽게 이동시킵니다
			// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
			map.panTo(moveLatLon);
		}
	</script>
</body>
</html>

<%@ include file="../common/foot.jspf"%>