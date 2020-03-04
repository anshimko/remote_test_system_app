package by.htp.ts.command.concrete_impl;

import java.io.IOException;

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

public class DeleteTestCommand implements Command{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTestCommand.class);

	private static final String JSP_ERROR = "error.jsp";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String VIEW_LIST_TEST = "Controller?command=view_list_test";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		TestServiceImpl testService = provider.getTestService();

		String topic;
		
		HttpSession session = request.getSession(true);

		try {
			
			topic = (String) request.getParameter(RequestParameter.TOPIC);
			
			testService.deleteTest(topic);
			
			request.getRequestDispatcher(VIEW_LIST_TEST).forward(request, response);

		} catch (ServiceException e) {
			LOGGER.error("Unable to delete test", e);
			session.setAttribute(GOTO_REQUEST, JSP_ERROR);
			response.sendRedirect(JSP_ERROR);
		}
	}
}