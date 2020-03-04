package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.bean.UserHasTestDTO;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.TestServiceImpl;

public class AssignTestCommand implements Command{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignTestCommand.class);

	private static final String JSP_ASSIGN_TEST = "/WEB-INF/jsp/assignTest.jsp";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=test assigned successful";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		java.util.Date today = new java.util.Date();
		long date = today.getTime();
		java.sql.Date todaySQL = new java.sql.Date(date);
		
		String[] login = request.getParameterValues(RequestParameter.LOGIN);
		String topic = (String) session.getAttribute(RequestParameter.TOPIC);

		UserHasTestDTO userHasTestDTO = new UserHasTestDTO(login, topic, todaySQL);

		try {

			ServiceProvider provider = ServiceProvider.getInstance();
			TestServiceImpl testService = provider.getTestService();
			testService.assignTest(userHasTestDTO);

			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);

		} catch (ServiceException e) {
			LOGGER.error("Unable to assign test", e);
			session.setAttribute(GOTO_REQUEST, JSP_ASSIGN_TEST);
			response.sendRedirect(JSP_ASSIGN_TEST);
		}
	}
}
