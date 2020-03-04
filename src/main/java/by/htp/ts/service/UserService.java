package by.htp.ts.service;

import java.util.List;

import by.htp.ts.bean.User;

public interface UserService {

	User signIn(String login, String password) throws ServiceException;

	void registration(User user) throws ServiceException;
	
	List<User> takeListUsers() throws ServiceException;

}
