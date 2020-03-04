package by.htp.ts.command.concrete_impl;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.bean.Test;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.TestServiceImpl;

public class CreateTestCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateTestCommand.class);

	private static final String TEST = "test";
	private static final String ANSWER_COUNT = "answer_count";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_ADD_QUESTION = "Controller?command=go_to_add_question_page";
	private static final String JSP_CREATE_TEST = "Controller?command=go_to_create_test_page&errorMessage=create test error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String topic;
		int minScore;
		Time duration;
		
		String durationTime = request.getParameter(RequestParameter.DURATION);
		
		if(durationTime.length() == 5) {
			durationTime = durationTime + ":00";
		}
		
		topic = request.getParameter(RequestParameter.TOPIC);
		minScore = Integer.parseInt(request.getParameter(RequestParameter.MIN_SCORE));
		duration = Time.valueOf(durationTime);

		Test test = new Test();
		test.setTopic(topic);
		test.setMinScore(minScore);
		test.setDuration(duration);

		HttpSession session = request.getSession(true);

		try {

			ServiceProvider provider = ServiceProvider.getInstance();
			TestServiceImpl testService = provider.getTestService();
			testService.createTest(test);

			session.setAttribute(TEST, test);
			session.setAttribute(ANSWER_COUNT, 1);

			session.setAttribute(GOTO_REQUEST, JSP_ADD_QUESTION);
			response.sendRedirect(JSP_ADD_QUESTION);

		} catch (ServiceException e) {
			LOGGER.error("Unable to create test", e);
			session.setAttribute(GOTO_REQUEST, JSP_CREATE_TEST);
			response.sendRedirect(JSP_CREATE_TEST);
		}
	}
}
