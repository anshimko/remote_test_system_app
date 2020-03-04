package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class ChangeLocaleCommand implements Command{
	
	private static final String LOCALE = "local";
	private static final String LANGUAGE = "language";
	private static final String GOTO_REQUEST = "gotoRequest";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		session.setAttribute(LOCALE, request.getParameter(LANGUAGE));
		
		String gotoRequest = (String)session.getAttribute(GOTO_REQUEST);
		
		if(gotoRequest.endsWith(".jsp")) {
			request.getRequestDispatcher(gotoRequest).forward(request, response);
		} else {
			response.sendRedirect(gotoRequest);
		}
	}
}
