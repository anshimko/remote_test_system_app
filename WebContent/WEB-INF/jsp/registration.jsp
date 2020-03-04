<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.registrtion_page.name.name"	var="name" />
<fmt:message bundle="${loc}" key="local.registrtion_page.name.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.registrtion_page.name.login" var="login" />
<fmt:message bundle="${loc}" key="local.registrtion_page.name.password" var="password" />
<fmt:message bundle="${loc}" key="local.registrtion_page.name.birthday" var="birthday" />
<fmt:message bundle="${loc}" key="local.registrtion_page.name.group" var="group" />
<fmt:message bundle="${loc}" key="local.registrtion_page.name.role" var="role" />
<fmt:message bundle="${loc}" key="local.locbutton.name.registration" var="registration_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.backToSignIn" var="back_to_sign_in" />

</head>
<body>
<div>
	<jsp:include page = "header.jsp" flush = "true" /> 

	
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="registration" /> <br />
					<table>	
						<tr><td>${name}</td><td><input type="text" name="name" required /></td></tr>
						<tr><td>${surname}</td><td><input	type="text" name="surname" required /></td></tr>
						<tr><td>${login}</td><td><input type="text" name="login" required /></td></tr>
						<tr><td>${password}</td><td><input type="text" name="password" required /></td></tr> 
						<tr><td>Email</td><td><input type="email" name="email" required /></td></tr> 
						<tr><td>${birthday}</td><td><input type="date" name="birthday" required /></td></tr> 
						<tr><td>${group}</td><td><input type="text" name="group" required /></td></tr>
						<tr><td>${role}</td><td><input type="text" name="role" required /></td></tr> 
					</table><br>
				<input type="submit" value="${registration_button}" />
		</form>
		<br>
		<a href="Controller?command=go_to_sign_in_page">${back_to_sign_in}</a> 
</div>
</body>
</html>