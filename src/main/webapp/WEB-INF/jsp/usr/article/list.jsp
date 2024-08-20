<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code } LIST"></c:set>
<%@ include file="../common/head.jspf"%>
<hr />
<div class="searchbox relative">
	<select class="select select-bordered w-full max-w-xs mb-2">
		<option disabled selected>게시판 선택</option>
		<option value="1">공지사항</option>
		<option value="2">자유</option>
		<option value="3">Q&A</option>
	</select>
	<label class="input input-bordered flex items-center gap-2 max-w-xs absolute top-full mt-2">
		<input type="text" class="grow" placeholder="Search" />
		<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" class="h-4 w-4 opacity-70">
    <path fill-rule="evenodd"
				d="M9.965 11.026a5 5 0 1 1 1.06-1.06l2.755 2.754a.75.75 0 1 1-1.06 1.06l-2.755-2.754ZM10.5 7a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0Z"
				clip-rule="evenodd" />
  </svg>
	</label>
</div>
<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
		<div>${articlesCount }개</div>
		<table class="table" border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
			<thead>
				<tr>
					<th style="text-align: center;">ID</th>
					<th style="text-align: center;">Registration Date</th>
					<th style="text-align: center;">Title</th>
					<th style="text-align: center;">Writer</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="article" items="${articles}">
					<tr class="hover">
						<td style="text-align: center;">${article.id}</td>
						<td style="text-align: center;">${article.regDate.substring(0,10)}</td>
						<td style="text-align: center;">
							<a class="hover:underline" href="detail?id=${article.id}">${article.title}</a>
						</td>
						<td style="text-align: center;">${article.extra__writer}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty articles}">
					<tr>
						<td colspan="4" style="text-align: center;">게시글이 없습니다</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<!-- 	동적 페이징 -->
	<div class="pagination flex justify-center mt-3">
		<c:set var="paginationLen" value="3" />
		<c:set var="startPage" value="${page -  paginationLen  >= 1 ? page - paginationLen : 1}" />
		<c:set var="endPage" value="${page +  paginationLen  <= pagesCount ? page + paginationLen : pagesCount}" />

		<c:set var="baseUri" value="?boardId=${boardId }"/>
		<c:set var="baseUri" value="${baseUri }&searchKeywordTypeCode=${searchKeywordTypeCode }"/>
		<c:set var="baseUri" value="${baseUri }&searchKeyword=${&searchKeyword }"/>
		

		<c:if test="${startPage > 1 }">
			<a class="btn btn-sm" href="${ baseUri}&page=1">1</a>

		</c:if>
		<c:if test="${startPage > 2 }">
			<button class="btn btn-sm btn-disabled">...</button>
		</c:if>

		<c:forEach begin="${startPage }" end="${endPage }" var="i">
			<a class="btn btn-sm ${param.page == i ? 'btn-active' : '' }" href="${ baseUri}&page=${i }">${i }</a>
		</c:forEach>

		<c:if test="${endPage < pagesCount - 1 }">
			<button class="btn btn-sm btn-disabled">...</button>
		</c:if>

		<c:if test="${endPage < pagesCount }">
			<a class="btn btn-sm" href="${ baseUri}&page=${pagesCount }">${pagesCount }</a>
		</c:if>
	</div>


	<!-- 직관적인 페이징 -->
	<!-- 	<div class="pagination flex justify-center mt-3"> -->
	<!-- 		<div class="btn-group"> -->

	<%-- 			<c:forEach begin="1" end="${pagesCount }" var="i"> --%>
	<%-- 				<a class="btn btn-sm ${param.page == i ? 'btn-active':''}" href="${ baseUri}&page=${i }">${i }</a> --%>
	<%-- 			</c:forEach> --%>
	<!-- 		</div> -->
	<!-- 	</div> -->
</section>

<%@ include file="../common/foot.jspf"%>