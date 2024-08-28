<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST"></c:set>

<script>
	const API_KEY = '%2BKApk0ihIn7Any5BM3X7%2BnlJyc2yXA39m%2F%2BXn4uiAkLPB7wbUQENd3NzoFktNEchJlI1pyRmWXXZJhA%2FTNfeIg%3D%3D';
	async function getCData() {
		const url = 'http://apis.data.go.kr/1180000/DaejeonNationalCemetery/Burialinfo042?numOfRows=10&pageNo=1&name=홍길동&serviceKey='+ API_KEY;
		const response = await fetch(url);
		const data = await
		response.json();
		console.log("data", data);
	}
	getCData();
</script>


<%@ include file="../common/head.jspf"%>

<%@ include file="../common/foot.jspf"%>