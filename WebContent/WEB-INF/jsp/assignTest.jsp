<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Assign test</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.assign_page.name.who_will_start_test"	var="who_will_start_test" />
<fmt:message bundle="${loc}" key="local.assign_page.name.topic_test"	var="topic_test" />
<fmt:message bundle="${loc}" key="local.locbutton.name.reset" var="reset_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.assign_test" var="assign_test_button" />

</head>
<body>
<div>
	<jsp:include page="header.jsp" flush="true" />
	
	<h2>${topic_test}: ${sessionScope.topic}</h2>

	<form>
		<input type="hidden" name="command" value="assign_test" />

		<fieldset>
			<legend>${who_will_start_test}</legend>

			<select name="login" size="5" multiple required>
				<c:forEach var="user" items="${sessionScope.listUsers}" varStatus="сounter">
				<option value="${user.login}">${user.name} ${user.surname}, group: ${user.group}</option>
				</c:forEach>
			</select>
		</fieldset>

		<br>
		<hr>
		<br> 
		
		<input type="reset" value="${reset_button}">
		<input type="submit" value="${assign_test_button}" />

	</form>
</div>
</body>
</html>