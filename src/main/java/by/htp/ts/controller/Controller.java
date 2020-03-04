package by.htp.ts.controller;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.ts.command.Command;
import by.htp.ts.command.CommandProvider;
import by.htp.ts.dao.connection_pool.ConnectionPool;

public class Controller extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	private static final long serialVersionUID = 1L;
	public static final String COMMAND_NAME = "command";
	private final CommandProvider provider = new CommandProvider();

	public Controller() {
	}

	@Override
	public void init() throws ServletException {
		ConnectionPool.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		Command command;
		String commandName = request.getParameter(COMMAND_NAME);

		LOGGER.debug("Command HHHHHHHHHHHHHHHHHH");
		command = provider.getCommand(commandName);
		try {
			command.execute(request, response);
		} catch (Exception e) {
			LOGGER.error("Problem with JSP", e);
			throw new ServletException(e);
		}

	}

	@Override
	public void destroy() {

		try {
			ConnectionPool.getInstance().dispose();
		} catch (SQLException e) {
			LOGGER.error("Unable to connection to database", e);
		}
	}
}
