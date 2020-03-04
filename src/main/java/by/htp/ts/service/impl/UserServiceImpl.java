package by.htp.ts.service.impl;

import java.util.List;

import by.htp.ts.bean.User;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.DAOFactory;
import by.htp.ts.dao.UserDAO;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.UserService;
import by.htp.ts.service.validation.Validator;
import by.htp.ts.service.validation.ValidatorParameter;

public class UserServiceImpl implements UserService {

	private static final String EXCEPTION_MESSAGE = "The entered data is incorrect";

	@Override
	public User signIn(String login, String password) throws ServiceException {

		if (!Validator.validatation(ValidatorParameter.LOGIN, login.trim()) || (!Validator.validatation(ValidatorParameter.PASSWORD, password.trim()))) {
			throw new ServiceException(EXCEPTION_MESSAGE);
		}

		User user;

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoObjectFactory.getUserDAO();

			user = userDAO.signIn(login.trim(), password.trim());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		return user;
	}

	@Override
	public void registration(User user) throws ServiceException {

		if (!Validator.validatation(ValidatorParameter.LOGIN, user.getLogin().trim()) 
				|| (!Validator.validatation(ValidatorParameter.PASSWORD, user.getPassword().trim()))
				|| (!Validator.validatation(ValidatorParameter.NAME, user.getName().trim())) 
				|| (!Validator.validatation(ValidatorParameter.SURNAME, user.getSurname().trim()))
				|| (!Validator.validatation(ValidatorParameter.BIRTHDAY, user.getBirhday().trim())) 
				|| (!Validator.validatation(ValidatorParameter.GROUP, user.getGroup().trim()))
				|| (!Validator.validatation(ValidatorParameter.EMAIL, user.getEmail().trim())) 
				|| (!Validator.validatation(ValidatorParameter.ROLE, user.getRole().trim()))) {
			
			throw new ServiceException(EXCEPTION_MESSAGE);
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoObjectFactory.getUserDAO();

			userDAO.registration(user);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<User> takeListUsers() throws ServiceException {

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO userDAO = daoObjectFactory.getUserDAO();

			return userDAO.takeListUsers();

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
