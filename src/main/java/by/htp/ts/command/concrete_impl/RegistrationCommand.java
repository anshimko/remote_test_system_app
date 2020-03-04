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

public class RegistrationCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCommand.class);

	private static final String USER = "user";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=welcome";
	private static final String JSP_REGISTRATION = "Controller?command=go_to_registration_page&errorMessage=registration error";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name;
		String surname;
		String login;
		String password;
		String email;
		String birthday;
		String group;
		String role;

		name = request.getParameter(RequestParameter.NAME);
		surname = request.getParameter(RequestParameter.SURNAME);
		login = request.getParameter(RequestParameter.LOGIN);
		password = request.getParameter(RequestParameter.PASSWORD);
		email = request.getParameter(RequestParameter.EMAIL);
		birthday = request.getParameter(RequestParameter.BIRTHDAY);
		group = request.getParameter(RequestParameter.GROUP);
		role = request.getParameter(RequestParameter.ROLE);

		User user = new User(name, surname, login, password, email, birthday, group, role);
		HttpSession session;
		session = request.getSession(true);
		
		session.removeAttribute(GOTO_REQUEST);

		try {
			ServiceProvider provider = ServiceProvider.getInstance();
			UserServiceImpl userService = provider.getUserService();
			userService.registration(user);
			
			session.setAttribute(USER, user);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);

		} catch (ServiceException e) {
			LOGGER.error("Unable to registration user", e);
			session.setAttribute(GOTO_REQUEST, JSP_REGISTRATION);
			response.sendRedirect(JSP_REGISTRATION);
		}
	}
}
