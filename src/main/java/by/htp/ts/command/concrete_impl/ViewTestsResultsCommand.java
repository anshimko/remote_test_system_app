package by.htp.ts.command.concrete_impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.bean.UserTestResultDTO;
import by.htp.ts.command.Command;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.ServiceProvider;
import by.htp.ts.service.impl.TestServiceImpl;

public class ViewTestsResultsCommand implements Command {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewTestsResultsCommand.class);

	private static final String GOTO_REQUEST = "gotoRequest";
	private static final String RESULTS_TESTS = "results_tests";
	private static final String CURRENT_PAGE = "currentPage";
	private static final String COUNT_OF_PAGES = "countOfPages";
	private static final String JSP_RESULTS_TESTS = "/WEB-INF/jsp/resultsTests.jsp";
	private static final String JSP_MAIN = "Controller?command=go_to_main_page&message=results_tests_are_empty";
	private static final String NEXT_PAGE = "Controller?command=view_list_tests_results";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<UserTestResultDTO> listUserTestResultDTO;
		int page = 1;
		int recordPerPage = 3;
		int countOfPages = 0;
		
		HttpSession session = request.getSession(true);

		try {
			
			if(request.getParameter("page") != null) {
				page =Integer.parseInt(request.getParameter("page"));
			}
			
			ServiceProvider provider = ServiceProvider.getInstance();
			TestServiceImpl testService = provider.getTestService();
			listUserTestResultDTO = testService.viewTestResult((page - 1) * recordPerPage, recordPerPage);
			
			if(listUserTestResultDTO == null || listUserTestResultDTO.isEmpty()) {
				session.setAttribute(GOTO_REQUEST, JSP_MAIN);
				response.sendRedirect(JSP_MAIN);
			}
			
			countOfPages = testService.getCountOfRecords() / recordPerPage;
			
			request.setAttribute(RESULTS_TESTS, listUserTestResultDTO);
			request.setAttribute(CURRENT_PAGE, page);
			request.setAttribute(COUNT_OF_PAGES, countOfPages);
	
			session.setAttribute(GOTO_REQUEST, NEXT_PAGE);
			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_RESULTS_TESTS);
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			LOGGER.error("Unable to view result of tests", e);
			session.setAttribute(GOTO_REQUEST, JSP_MAIN);
			response.sendRedirect(JSP_MAIN);
		}
	}
}