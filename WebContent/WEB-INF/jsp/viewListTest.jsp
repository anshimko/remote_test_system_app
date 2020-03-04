<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View list tests</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.list_test_page.name.list_tests"	var="list_tests" />
<fmt:message bundle="${loc}" key="local.locbutton.name.start_test" var="start_test_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.delete_test" var="delete_test_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.assign_test" var="assign_test_button" />

</head>
<body>
<div>
	<%@ include file="header.jsp"%>
	
	<h2>${list_tests}</h2><hr>

	<p>
		<c:forEach var="test" items="${sessionScope.listTest}" varStatus="сounter">
			<table>
				<tr>
				<c:out value="${сounter.count}. ${test}" />
		
					<c:if test = "${!empty sessionScope.user.role && sessionScope.user.role ne 'tutor'}">
						<td>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="run_test" /> 
								<input type="hidden" name="topic" value="${test}" />
								<input type="submit" value="${start_test_button}" /><br>
							</form>
						</td>
					</c:if>
				
					<c:if test = "${sessionScope.user.role eq 'tutor'}">
						<td>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="delete_test" /> 
								<input type="hidden" name="topic" value="${test}" />
								<input type="submit" value="${delete_test_button}" />
							</form>
						</td>
					
						<td>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="go_to_assign_test_page" /> 
								<input type="hidden" name="topic" value="${test}" />
								<input type="submit" value="${assign_test_button}" />
							</form>
						</td>
					</c:if>
				</tr>
			</table>
			<hr>
		</c:forEach>
	</p>
</div>
</body>
</html>