package by.htp.ts.service;

import java.util.List;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.UserHasTestDTO;
import by.htp.ts.bean.UserTestResultDTO;

public interface TestService {
	
	void createTest(Test test) throws ServiceException;

	void addQuestion(Question question) throws ServiceException;
	
	List<String> viewListTest() throws ServiceException;
	
	List<String> viewListTestForUser(String login) throws ServiceException;
	
	Test takeTest(String topic) throws ServiceException;
	
	void assignTest(UserHasTestDTO userHasTestDTO) throws ServiceException;
	
	void saveUserTestResult(UserTestResultDTO userTestResultDTO) throws ServiceException;
	
	void deleteTest(String topic) throws ServiceException;
	
	List<UserTestResultDTO> viewTestResult(int offset, int recordPerPage) throws ServiceException;
	
	int getCountOfRecords() throws ServiceException;

}
