package by.htp.ts.command.concrete_impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.TestServiceImpl;


public class ViewListTestCommand implements Command{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewListTestCommand.class);
	
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String LIST_TEST = "listTest";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=no tests";
	private static final String JSP_LIST_TEST = "/WEB-INF/jsp/viewListTest.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		ServiceProvider provider = ServiceProvider.getInstance();
		TestServiceImpl testService = provider.getTestService();

		List<String> listTest;
		HttpSession session = request.getSession(true);

		try {
			listTest = testService.viewListTest();

			if (listTest.isEmpty()) {
				response.sendRedirect(JSP_MAIN);
				return;
			}
			
			session.setAttribute(LIST_TEST, listTest);
			
			session.setAttribute(GOTO_REQUEST, JSP_LIST_TEST);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_LIST_TEST);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			LOGGER.debug("Unable to view list test", e);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);
		}
	}
}