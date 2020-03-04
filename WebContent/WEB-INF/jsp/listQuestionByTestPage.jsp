<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.create_test_page.name.category"	var="category" />
<fmt:message bundle="${loc}" key="local.create_test_page.name.duration"	var="duration" />
<fmt:message bundle="${loc}" key="local.create_test_page.name.min_score"	var="min_score" />

</head>
<body>
<div>
	<%@ include file="header.jsp"%>

	<h2>${category}: ${sessionScope.test.topic}</h2>
	<hr>
	<p>
		${duration}: ${sessionScope.test.duration}<br> 
		${min_score}: ${sessionScope.test.minScore}
	</p>
	<br>

	<c:forEach var="question" items="${sessionScope.test.listQuestion}">
		<fieldset>
			<legend>${question.question}</legend>
			<c:forEach var="answer" items="${question.answer}"
				varStatus="сounter">
				<c:out value="${сounter.count}. ${answer}" />
				<br>
			</c:forEach>
		</fieldset>
		<hr>
	</c:forEach>
</div>
</body>
</html>