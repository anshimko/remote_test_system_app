package by.htp.ts.service.impl;

import java.util.List;
import java.util.Map;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.UserHasTestDTO;
import by.htp.ts.bean.UserTestResultDTO;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.DAOFactory;
import by.htp.ts.dao.TestDAO;
import by.htp.ts.service.ServiceException;
import by.htp.ts.service.TestService;
import by.htp.ts.service.validation.Validator;
import by.htp.ts.service.validation.ValidatorParameter;

public class TestServiceImpl implements TestService {

	private static final String EXCEPTION_MESSAGE = "The entered data is incorrect";

	@Override
	public void createTest(Test test) throws ServiceException {

		if (!Validator.validatation(ValidatorParameter.TOPIC, test.getTopic()) || (!Validator.validatation(test.getMinScore()))) {
			throw new ServiceException(EXCEPTION_MESSAGE);
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			testDAO.createTest(test);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void addQuestion(Question question) throws ServiceException {

		if (!Validator.validatation(ValidatorParameter.QUESTION, question.getQuestion())) {
			throw new ServiceException(EXCEPTION_MESSAGE);
		}

		for (Map.Entry<String, String> entry : question.getAnswer().entrySet()) {
			if (!Validator.validatation(ValidatorParameter.ANSWER, entry.getKey())) {
				throw new ServiceException(EXCEPTION_MESSAGE);
			}
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			testDAO.addQuestion(question);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<String> viewListTest() throws ServiceException {

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			return testDAO.viewListTest();

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	public Test takeTest(String topic) throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			return testDAO.takeTest(topic);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void assignTest(UserHasTestDTO userHasTestDTO) throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			testDAO.assignTest(userHasTestDTO);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<String> viewListTestForUser(String login) throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			return testDAO.viewListTestForUser(login);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveUserTestResult(UserTestResultDTO userTestResultDTO) throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			testDAO.saveUserTestResult(userTestResultDTO);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteTest(String topic) throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			testDAO.deleteTest(topic);;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<UserTestResultDTO> viewTestResult(int offset, int recordPerPage) throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			return testDAO.viewTestResult(offset, recordPerPage);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int getCountOfRecords() throws ServiceException {
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TestDAO testDAO = daoObjectFactory.getTestDAO();

			return testDAO.getCountOfRecords();

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
