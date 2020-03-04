package by.htp.ts.command.concrete_impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.htp.ts.command.Command;

public class CloseAddQuestionCommand implements Command{
	
	private static final String TEST = "test";
	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String ANSWER_COUNT = "answer_count";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=Test created seccessfull";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session != null) {
			session.removeAttribute(TEST);
			session.removeAttribute(ANSWER_COUNT);
		}
		session.setAttribute(GOTO_REQUEST, JSP_MAIN);
		response.sendRedirect(JSP_MAIN);
	}
}
