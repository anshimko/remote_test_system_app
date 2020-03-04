<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create test</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.create_test_page.name.create_test"	var="create_test" />
<fmt:message bundle="${loc}" key="local.create_test_page.name.category"	var="category" />
<fmt:message bundle="${loc}" key="local.create_test_page.name.duration"	var="duration" />
<fmt:message bundle="${loc}" key="local.create_test_page.name.min_score"	var="min_score" />

<fmt:message bundle="${loc}" key="local.locbutton.name.reset" var="reset" />
<fmt:message bundle="${loc}" key="local.locbutton.name.save" var="save" />

</head>
<body>
<div>
	<jsp:include page="header.jsp" flush="true" />

	<fieldset>
		<legend>${create_test}</legend>

		<form action="Controller" method="post">
			<input type="hidden" name="command" value="create_Test" /> <br>
			<table>
			<tr><td>${category}</td> <td><input type="text" name="topic" required /></td> </tr>
			<tr><td>${duration}</td> <td><input type="time" name="duration" value="00:20:00" step="30" required></td> </tr> 
			<tr><td>${min_score}</td> <td><input type="text" name="minScore" required/></td> </tr>
			</table><br>
			<input type="reset" value="${reset}">
			<input type="submit" value="${save}" /><br> <br>
		</form>
		
	</fieldset>
</div>
</body>
</html>