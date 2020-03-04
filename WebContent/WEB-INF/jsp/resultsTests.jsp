<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View results tests</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.result_tests_page.name.result_tests"	var="result_tests" />
<fmt:message bundle="${loc}" key="local.result_tests_page.name.name_surname"	var="name_surname" />
<fmt:message bundle="${loc}" key="local.result_tests_page.name.test"	var="test" />
<fmt:message bundle="${loc}" key="local.result_tests_page.name.score"	var="score" />
<fmt:message bundle="${loc}" key="local.result_tests_page.name.prev"	var="prev" />
<fmt:message bundle="${loc}" key="local.result_tests_page.name.next"	var="next" />

</head>
<body>
	<div>
		<%@ include file="header.jsp"%>

		<h2>${result_tests}</h2>
		<hr>

		<table class="test_result">
			<tr>
				<th>${name_surname}</th>
				<th>${test}</th>
				<th>${score}</th>
			</tr>

			<c:forEach var="test" items="${results_tests}"
				varStatus="сounter">

				<tr>
					<td><c:out value="${test.surname} ${test.name}" /></td>
					<td><c:out value="${test.topic}" /></td>
					<td><c:out value="${test.score}" /></td>
				</tr>
			</c:forEach>
		</table>
		
		<table class="pagination">
			<tr>
				<c:if test="${currentPage != 1}">
						<td><a href="Controller?command=view_list_tests_results&page=${currentPage - 1}">${prev}</a></td>
				</c:if>
				
				<c:forEach var = "i" begin = "1" end = "${countOfPages}">
					<c:choose>
					
						<c:when test="${currentPage eq i}">
							<td>${i}</td>
						</c:when>
						
						<c:otherwise>
							<td><a href="Controller?command=view_list_tests_results&page=${i}">${i}</a></td>
						</c:otherwise>
						
					</c:choose>
				</c:forEach>
				
				<c:if test="${currentPage lt countOfPages}">
						<td><a href="Controller?command=view_list_tests_results&page=${currentPage + 1}">${next}</a></td>
				</c:if>
			</tr>
		</table>

	</div>
</body>
</html>