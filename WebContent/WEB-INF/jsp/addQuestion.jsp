<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add question</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="main.resources.local" var="loc" />

<fmt:message bundle="${loc}" key="local.locbutton.name.clean"	var="clean_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.save" var="save_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.addQuestion" var="addQuestion_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.stopCreateTest" var="stopCreateTest_button" />
<fmt:message bundle="${loc}" key="local.addQuestion_page.name.createTestTopic" var="createTestTopic" />
<fmt:message bundle="${loc}" key="local.addQuestion_page.name.question" var="question" />
<fmt:message bundle="${loc}" key="local.addQuestion_page.name.kindAnswer" var="kindAnswer" />

</head>
<body>
<div>
	<jsp:include page="header.jsp" flush="true" />

	<h3>${createTestTopic}: ${sessionScope.test.topic}</h3>

	<h4>${question} №${sessionScope.answer_count}</h4>

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="add_question" />

		<p>
			<b><input id="question" type="text" name="question" required></b>
		</p>

		<hr><br>

		<fieldset>
			<legend>${kindAnswer}</legend>
			
			<select name="check_answer">
				<option>No</option>
				<option>Yes</option>
			</select> 
			<input class = "answer" type="text" name="answer" required /><br>
			
			<select name="check_answer">
				<option>No</option>
				<option>Yes</option>
			</select> 
			<input class = "answer" type="text" name="answer" required /><br>
			
			<select	name="check_answer">
				<option>No</option>
				<option>Yes</option>
			</select> 
			<input class = "answer" type="text" name="answer" required /><br>
			
			<select	name="check_answer">
				<option>No</option>
				<option>Yes</option>
			</select> 
			<input class = "answer" type="text" name="answer" required /><br>
		</fieldset>

		<br><hr><br>
		
		<input type="reset" value="${clean_button}"> 
		<input type="submit" value="${addQuestion_button}" /> 
	</form>
	<br>
	<form action="Controller" method="get">
		<input type="hidden" name="command" value="go_to_list_question_by_test_page" />
		<input type="submit" value="${stopCreateTest_button}" />
	</form>
</div>
</body>
</html>