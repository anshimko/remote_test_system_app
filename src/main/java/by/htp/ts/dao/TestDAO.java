package by.htp.ts.dao;

import java.util.List;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.UserHasTestDTO;
import by.htp.ts.bean.UserTestResultDTO;

public interface TestDAO {

	void createTest(Test test) throws DAOException;

	void addQuestion(Question question) throws DAOException;

	List<String> viewListTest() throws DAOException;

	List<String> viewListTestForUser(String login) throws DAOException;

	Test takeTest(String topic) throws DAOException;

	void assignTest(UserHasTestDTO userHasTestDTO) throws DAOException;

	void saveUserTestResult(UserTestResultDTO userTestResultDTO) throws DAOException;

	void deleteTest(String topic) throws DAOException;

	List<UserTestResultDTO> viewTestResult(int offset, int recordPerPage) throws DAOException;

	int getCountOfRecords() throws DAOException;

}
