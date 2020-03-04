
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" type="text/css" href="css/style.css">

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.index_page1" var="message1" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.signOut"
	var="signOut_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.main"
	var="main_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.createTest"
	var="createTest_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.assignTest"
	var="assignTest_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.listTest"
	var="listTest_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.resultTests"
	var="resultTests_button" />


<jsp:useBean id="now" class="java.util.Date" />

<h2>
	<c:out value="${message1}" />
</h2>

<table>
	<tr>
		<td>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="change_locale" /> <input
					type="hidden" name="language" value="en" />
				<button type="submit">
					<img src="image/USA.png" alt="${en_button}" width="20"
						height="15">
				</button>
			</form>
		</td>
		<td>
			<form action="Controller" method="post">
				<input type="hidden" name="command" value="change_locale" /> <input
					type="hidden" name="language" value="ru" />
				<button type="submit">
					<img src="image/Russia.png" alt="${ru_button}" width="20"
						height="15">
				</button>
			</form>
		</td>
	</tr>
</table>

<hr />

<nav>
	<%-- <c:if test="${empty sessionScope.user.role}">
		<a href="Controller?command=go_to_sign_in_page">${main_button}</a> |
	</c:if> --%>

	<c:if test="${sessionScope.user.role eq 'tutor'}">
		<a href="Controller?command=go_to_create_test_page">${createTest_button}</a> | 
			<a href="Controller?command=view_list_test">${listTest_button}</a> | 
    </c:if>

	<c:if
		test="${!empty sessionScope.user.role && sessionScope.user.role ne 'tutor'}">
		<a href="Controller?command=view_list_test_for_user">${assignTest_button}</a> | 
    </c:if>
    
    <c:if test="${sessionScope.user.role eq 'tutor'}"> 
		<a href="Controller?command=view_list_tests_results">${resultTests_button}</a> | 
    </c:if>
    
    <c:if test="${!empty sessionScope.user.role}">
		<a href="Controller?command=sign_out">${signOut_button}</a> | 
	</c:if>

	<fmt:formatDate pattern="EEEE, d MMMM yyyy г. H:mm" value="${now}" />
</nav>
<hr />
<br />

