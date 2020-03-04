package by.htp.ts.dao;

import java.util.List;
import by.htp.ts.bean.User;

public interface UserDAO {

	User signIn(String login, String password) throws DAOException;

	void registration(User user) throws DAOException;
	
	List<User> takeListUsers() throws DAOException;

}
