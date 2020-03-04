<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>



<meta charset="UTF-8">

<title>Authorization and registration</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.signIn"	var="signIn_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.registration" var="reg_button" />
<fmt:message bundle="${loc}" key="local.login" var="login_button" />
<fmt:message bundle="${loc}" key="local.password" var="pass_button" />

</head>

<body>
<div>
	<jsp:include page = "header.jsp" flush = "true" /> 	
	
	

	<fieldset>
		<legend>${message}</legend>

		<form action="Controller" method="post">
			<input type="hidden" name="command" value="authorization" />
			${login_button}: <input type="text" name="login" required />
			${pass_button}: <input type="password" name="password" required /> <br>
			<br> <input type="submit" value="${signIn_button}" /> <br>
		</form>
	</fieldset>

	<br />
	
	<c:if test="${not empty param.errorMessage }">
		<c:out value="${param.errorMessage}"/>
	</c:if>

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="go_to_registration_page"/>
		<input type="submit" value="${reg_button}" /><br/>
	</form>

	</div>
</body>
</html>