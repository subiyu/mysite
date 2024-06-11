<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items='${list }' var='vo' varStatus="status">
						<tr>
							<td>${vo.no }</td>	
							<td style="text-align:left; padding-left:${20*vo.depth }px">
							<c:if test='${vo.depth > 0 }'>
								<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'>
							</c:if>
								<a href="${pageContext.request.contextPath}/board/view/${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><a href="${pageContext.request.contextPath}/board/delete/${vo.no }" class="del" style="background-image:url(${pageContext.request.contextPath }/assets/images/recycle.png)">삭제</a></td>
						</tr>
					</c:forEach>
				</table>
				
				<%-- 페이지네이션 출력 --%>
				<div class="pager">
					<ul>
						<li><a href="${pageContext.servletContext.contextPath}/board?p=${prevPage }&kwd=${keyword }">◀</a></li>
						<c:forEach begin="${beginPage }" end="${endPage }" step="1" var="i">
							<c:choose>
						        <c:when test="${i <= totalPage }">
						            <c:if test="${i < currentPage}">
						                <li><a href="${pageContext.servletContext.contextPath}/board?p=${i}&kwd=${keyword }">${i}</a></li>
						            </c:if>
						            <c:if test="${i == currentPage}">
						                <li class="selected">${i}</li>
						            </c:if>
						            <c:if test="${i > currentPage}">
						                <li><a href="${pageContext.servletContext.contextPath}/board?p=${i}&kwd=${keyword }">${i}</a></li>
						            </c:if>
						        </c:when>
						        <c:otherwise>
						            <li>${i}</li>
						        </c:otherwise>
						    </c:choose>							
						</c:forEach>
						<li><a href="${pageContext.servletContext.contextPath}/board?p=${nextPage }&kwd=${keyword }">▶</a></li>
					</ul>
				</div>					
				
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/write?isNew=true" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>