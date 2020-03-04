package by.htp.ts.command.goto_impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.bean.User;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.UserServiceImpl;

public class GoToAssignTestPageCommand implements Command {

	private static final String LIST_USERS = "listUsers";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=no users";
	private static final String JSP_ASSIGN_TEST = "/WEB-INF/jsp/assignTest.jsp";
	
	private static final String GOTO_REQUEST = "gotoRequest";
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<User> listUsers = new ArrayList<>();

		ServiceProvider provider = ServiceProvider.getInstance();
		UserServiceImpl userService = provider.getUserService();

		HttpSession session = request.getSession(true);
		session.setAttribute("topic", request.getParameter("topic"));

		try {
			listUsers = userService.takeListUsers();

			if (listUsers.isEmpty()) {
				session = request.getSession(true);
				session.setAttribute(GOTO_REQUEST, JSP_MAIN);
				response.sendRedirect(JSP_MAIN);
				return;
			}

			session = request.getSession(true);
			session.setAttribute(LIST_USERS, listUsers);

			
			session = request.getSession(true);
			session.setAttribute(GOTO_REQUEST, JSP_ASSIGN_TEST);
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_ASSIGN_TEST);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			// log
			session = request.getSession(true);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);
		}
	}
}
