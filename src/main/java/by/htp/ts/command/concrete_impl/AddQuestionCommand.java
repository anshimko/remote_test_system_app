package by.htp.ts.command.concrete_impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.TestServiceImpl;

public class AddQuestionCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AddQuestionCommand.class);

	private static final String ANSWER_COUNT = "answer_count";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_ADD_QUESTION = "Controller?command=go_to_add_question_page";
	private static final String JSP_ERROR = "error.jsp?&errorMessage=add question error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Map<String, String> answer = new HashMap<String, String>();

		Test test = (Test) request.getSession(false).getAttribute(RequestParameter.TEST);

		String topic = test.getTopic();
		String nameQuestion = request.getParameter(RequestParameter.QUESTION);

		String[] answers = request.getParameterValues(RequestParameter.ANSWER);
		String[] check_answers = request.getParameterValues(RequestParameter.CHECK_ANSWER);

		for (int i = 0; i < answers.length; i++) {
			answer.put(answers[i], check_answers[i]);
		}

		Question question = new Question(topic, nameQuestion, answer);
		test.getListQuestion().add(question);
		
		HttpSession session = request.getSession(true);

		try {

			ServiceProvider provider = ServiceProvider.getInstance();
			TestServiceImpl testService = provider.getTestService();
			testService.addQuestion(question);

			Integer count = (Integer) session.getAttribute(ANSWER_COUNT);

			if (count == null || count == 0) {
				count = 1;
			} else {
				count = count + 1;
			}

			session.setAttribute(ANSWER_COUNT, count);
			session.setAttribute(GOTO_REQUEST, JSP_ADD_QUESTION);
			response.sendRedirect(JSP_ADD_QUESTION);

		} catch (ServiceException e) {
			LOGGER.error("Unable to add question", e);
			session.setAttribute(GOTO_REQUEST, JSP_ERROR);
			response.sendRedirect(JSP_ERROR);
		}
	}
}