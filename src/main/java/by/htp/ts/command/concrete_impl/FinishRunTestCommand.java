package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.bean.Test;
import by.htp.ts.bean.User;
import by.htp.ts.bean.UserTestResultDTO;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.TestServiceImpl;

public class FinishRunTestCommand implements Command{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinishRunTestCommand.class);
	
	private static final String COUNT_QUESTION = "countQuestion";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String COUNT_RIGHT_QUESTION = "countRightQuestion";
	private static final String CHECK_ANSWER = "check_answer";
	private static final String COUNT_RIGHT_ANSWER_OF_QUESTION = "countRightAnswer";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=finishRunTest";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		HttpSession session = request.getSession(false);
		
		double countQuestion = (Integer) session.getAttribute(COUNT_QUESTION);
		double countRightQuestion = (Integer) session.getAttribute(COUNT_RIGHT_QUESTION);
		
		double scoreTest = (countRightQuestion / countQuestion) * 100.00;
		
		Test test = (Test)session.getAttribute(RequestParameter.TEST);
		int minScore = test.getMinScore();
		
		String contentMessage;
		
		if(scoreTest >= minScore) {
			contentMessage = "You got " + String.format("%.0f", scoreTest) + " points. Congratulations, the test is completed.";
		} else {
			contentMessage = "You got " + String.format("%.0f", scoreTest) + " points. Test failed.";
		}
		
		ServiceProvider provider = ServiceProvider.getInstance();
		TestServiceImpl testService = provider.getTestService();

		try {
			
			User user = (User)session.getAttribute("user");
			String login = user.getLogin();
			String topic = test.getTopic();
			
			UserTestResultDTO userTestResultDTO = new UserTestResultDTO (); 
			userTestResultDTO.setLogin(login);
			userTestResultDTO.setTopic(topic);
			userTestResultDTO.setScore(scoreTest);
			
			testService.saveUserTestResult(userTestResultDTO);

		} catch (ServiceException e) {
			LOGGER.error("Unable to finish test", e);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);
		}
		
		session.removeAttribute(COUNT_QUESTION);
		session.removeAttribute(COUNT_RIGHT_QUESTION);
		session.removeAttribute(CHECK_ANSWER);
		session.removeAttribute(COUNT_RIGHT_QUESTION);
		session.removeAttribute(COUNT_RIGHT_ANSWER_OF_QUESTION);
		session.removeAttribute(RequestParameter.TEST);
		
		session.setAttribute(GOTO_REQUEST, JSP_MAIN + "&contentMessage=" + contentMessage);
		response.sendRedirect(JSP_MAIN + "&contentMessage=" + contentMessage);
	}
}
