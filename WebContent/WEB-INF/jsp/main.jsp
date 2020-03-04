<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Main</title>
	<link href="style.css" type="text/css" rel="stylesheet">
</head>
<body>
<div>
	<jsp:include page = "header.jsp" flush = "true" /> 
	
	<c:if test="${param.message eq 'welcome'}">
    	<p>Добро пожаловать ${user.name} ${user.surname}!</p>
	</c:if>
	
	<c:if test="${param.message eq 'goodbye'}">
    	<p>До скорой встречи!</p>
	</c:if>
	
	<c:if test="${param.message eq 'finishRunTest'}">
    	<p><c:out value="${param.contentMessage}"/></p>
	</c:if>
	
	<c:if test="${param.message eq 'test assigned successful'}">
    	<p>Test assigned successful</p>
	</c:if>
	
	<c:if test="${param.message eq 'no tests'}">
    	<p>Tests didn't assign for you</p>
	</c:if>
	
	<c:if test="${param.message eq 'results_tests_are_empty'}">
    	<p>Results_tests_are_empty</p>
	</c:if>
	
</div>	
</body>
</html>