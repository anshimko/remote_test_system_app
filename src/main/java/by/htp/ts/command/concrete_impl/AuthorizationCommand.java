package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.bean.User;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.UserServiceImpl;

public class AuthorizationCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationCommand.class);

	private static final String USER = "user";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=welcome";
	private static final String JSP_SIGN_IN = "Controller?command=go_to_sign_in_page&errorMessage=login or password error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login;
		String password;

		login = request.getParameter(RequestParameter.LOGIN);
		password = request.getParameter(RequestParameter.PASSWORD);

		ServiceProvider provider = ServiceProvider.getInstance();
		UserServiceImpl userService = provider.getUserService();

		User user;
		HttpSession session;
		
		session = request.getSession(true);

		try {
			user = userService.signIn(login, password);

			if (user == null) {
				session.setAttribute(GOTO_REQUEST, JSP_SIGN_IN);
				response.sendRedirect(JSP_SIGN_IN);
				return;
			}
			
			session.setAttribute(USER, user);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);

		} catch (ServiceException e) {
			LOGGER.error("Unable to authorization user", e);
			session.setAttribute(GOTO_REQUEST, JSP_SIGN_IN);
			response.sendRedirect(JSP_SIGN_IN);
		}
	}
}
