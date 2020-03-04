package by.htp.ts.command.goto_impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToSignInPageCommand implements Command{
	
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String JSP_SIGN_IN = "WEB-INF/jsp/signIn.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute(GOTO_REQUEST, JSP_SIGN_IN);

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_SIGN_IN);
		dispatcher.forward(request, response);
		
	}

}
