package by.htp.ts.dao;

import by.htp.ts.dao.impl.SQLTestDAO;
import by.htp.ts.dao.impl.SQLUserDAO;

public class DAOFactory {

	private static final DAOFactory INSTANCE = new DAOFactory();

	private final UserDAO sqlUserImpl = new SQLUserDAO();
	private final TestDAO sqlTestImpl = new SQLTestDAO();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public UserDAO getUserDAO() {
		return sqlUserImpl;
	}
	
	public TestDAO getTestDAO() {
		return sqlTestImpl;
	}

}
