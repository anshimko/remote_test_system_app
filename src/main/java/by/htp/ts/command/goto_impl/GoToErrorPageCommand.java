package by.htp.ts.command.goto_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class GoToErrorPageCommand implements Command{
	
	private static final String JSP_ERROR = "error.jsp?&errorMessage=unknow command error";
	
	private static final String GOTO_REQUEST = "gotoRequest";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		session.setAttribute(GOTO_REQUEST, JSP_ERROR);
		response.sendRedirect(JSP_ERROR);
		
	}

}
