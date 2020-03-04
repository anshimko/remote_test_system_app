package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class SignOutCommand implements Command {

	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String USER = "user";
	private static final String JSP_SIGN_IN = "Controller?command=go_to_sign_in_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		session.removeAttribute(USER);

		session.setAttribute(GOTO_REQUEST, JSP_SIGN_IN);
		response.sendRedirect(JSP_SIGN_IN);

	}
}
