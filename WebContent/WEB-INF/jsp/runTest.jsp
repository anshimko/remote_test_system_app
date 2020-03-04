<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Run test</title>
</head>
<body>
<div>
	<%@ include file="header.jsp"%>

	<h2>Тема теста: ${sessionScope.test.topic}</h2>
	<hr>
	<p>
		Продолжительность: ${sessionScope.test.duration}<br> Проходной
		балл: ${sessionScope.test.minScore}
	</p>
	<br>

	<form action="Controller" method="post">
		<input type="hidden" name="command" value="run_test_next_question" />

		<c:set var="countRightAnswer" scope="request" value="0" />

		<hr>
		<br>

		<table>
			<c:forEach var="question" items="${sessionScope.test.listQuestion}"
				begin="${countQuestion}" end="${countQuestion}">

				<b><c:out value="${countQuestion + 1}. ${question.question}" /></b>
				<c:forEach var="answer" items="${question.answer}">
					<tr>
						<td><input type="checkbox" name="check_answer"
							value="${answer.value}"></td>
						<td><c:out value="${answer.key}" /></td>
						<c:if test="${answer.value=='Yes'}">
							<c:set var="countRightAnswer" scope="request" value="${countRightAnswer + 1}" />
						</c:if>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
		
		<input type="hidden" name="countRightAnswer" value="${countRightAnswer}" />

		<br><hr><br> 
		
		
		<c:if test="${countQuestion lt fn:length(sessionScope.test.listQuestion)}">
			<input type="reset" value="Очистить"> 
			<input type="submit" value="Сохранить" />
		</c:if>
	</form>

	<c:if test="${countQuestion eq fn:length(sessionScope.test.listQuestion)}">
		<form action="Controller" method="get">
			<input type="hidden" name="command"
				value="finish_run_test" /> <input type="submit"
				value="Закончить тест" />
		</form>
	</c:if>
</div>
</body>
</html>