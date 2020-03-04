package by.htp.ts.command.goto_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToRegistrationPageCommand implements Command {
	
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_REG = "/WEB-INF/jsp/registration.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		session.setAttribute(GOTO_REQUEST, JSP_REG);

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_REG);
		dispatcher.forward(request, response);

	}

}
