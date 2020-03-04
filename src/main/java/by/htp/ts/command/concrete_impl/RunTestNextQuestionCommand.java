package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class RunTestNextQuestionCommand implements Command {

	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String COUNT_QUESTION = "countQuestion";
	private static final String CHECK_ANSWER = "check_answer";
	private static final String COUNT_RIGHT_ANSWER_OF_QUESTION = "countRightAnswer";
	private static final String COUNT_RIGHT_QUESTION = "countRightQuestion";
	private static final String RUN_TEST = "/WEB-INF/jsp/runTest.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		int countRightAnswerOfQuestionFromDatabase = Integer.parseInt(request.getParameter(COUNT_RIGHT_ANSWER_OF_QUESTION));
		int countRightAnswerOfQuestionFromUser = 0;
		int countWrongAnswerOfQuestionFromUser = 0;

		String[] answerFromUser = request.getParameterValues(CHECK_ANSWER);

		// checking condition if the answer is correct
		for (String i : answerFromUser) {
			if (i.equals("Yes")) {
				countRightAnswerOfQuestionFromUser = countRightAnswerOfQuestionFromUser + 1;
			} else {
				countWrongAnswerOfQuestionFromUser = countWrongAnswerOfQuestionFromUser + 1;
			}
		}

		if ((countRightAnswerOfQuestionFromDatabase == countRightAnswerOfQuestionFromUser) && (countWrongAnswerOfQuestionFromUser == 0)) {
			int countRightQuestion = (Integer) session.getAttribute(COUNT_RIGHT_QUESTION);
			session.setAttribute(COUNT_RIGHT_QUESTION, countRightQuestion + 1);
		} 
		
		int countQuestion = (Integer) session.getAttribute(COUNT_QUESTION);
		session.setAttribute(COUNT_QUESTION, countQuestion + 1);
		
		session.setAttribute(GOTO_REQUEST, RUN_TEST);
		request.getRequestDispatcher(RUN_TEST).forward(request, response);
	}
}