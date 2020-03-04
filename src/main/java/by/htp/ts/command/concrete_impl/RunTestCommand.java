package by.htp.ts.command.concrete_impl;

import java.io.IOException;


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

public class RunTestCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RunTestCommand.class);

	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String COUNT_QUESTION = "countQuestion";
	private static final String COUNT_RIGHT_QUESTION = "countRightQuestion";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=no tests";
	private static final String RUN_TEST = "/WEB-INF/jsp/runTest.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		TestServiceImpl testService = provider.getTestService();

		String topic;
		Test test;
		HttpSession session = request.getSession(true);

		try {
			
			topic = (String) request.getParameter(RequestParameter.TOPIC);
			
			test = testService.takeTest(topic);

			if (test == null) {
				session.setAttribute(GOTO_REQUEST, JSP_MAIN);
				response.sendRedirect(JSP_MAIN);
				return;
			}

			session = request.getSession(true);
			session.setAttribute(RequestParameter.TEST, test);
			session.setAttribute(COUNT_QUESTION, 0);
			session.setAttribute(COUNT_RIGHT_QUESTION, 0);
			
			session.setAttribute(GOTO_REQUEST, RUN_TEST);
			request.getRequestDispatcher(RUN_TEST).forward(request, response);

		} catch (ServiceException e) {
			LOGGER.error("Unable to run test", e);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);
		}
	}
}