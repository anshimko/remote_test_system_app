package by.htp.ts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import by.htp.ts.bean.User;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.UserDAO;
import by.htp.ts.dao.connection_pool.CloseConnectionBlockFinally;
import by.htp.ts.dao.connection_pool.ConnectionPool;

public class SQLUserDAO implements UserDAO {

	private static final ReentrantLock lock = new ReentrantLock();

	private final static String LOGIN = "login";
	private final static String NAME = "name";
	private final static String SURNAME = "surname";
	private final static String ROLE = "name_role";
	private final static String GROUP_NAME = "group_name";
	private final static String SELECT_SIGN_IN = "SELECT * FROM user JOIN role ON user.role_id = role.id WHERE login = ? AND password = ?";
	private final static String SELECT_LOGIN = "SELECT * FROM user WHERE login = ?";
	private final static String SELECT_ROLE = "SELECT name_role FROM role WHERE name_role = ?";
	private final static String INSERT_ROLE = "INSERT INTO role (name_role) VALUES (?)";
	private final static String INSERT_USER = "INSERT INTO user (name, surname, login, password, email, date_birthday, group_name, role_id) VALUES (?,?,?,?,?,?,?,(SELECT id FROM role WHERE name_role = ?))";
	private final static String SELECT_USERS = "SELECT login, name, surname, group_name FROM user;";

	@Override
	public User signIn(String login, String password) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		User user = null;

		try {
			
			con = ConnectionPool.getInstance().takeConnection();
			
			lock.lock();

			pst = con.prepareStatement(SELECT_SIGN_IN);
			pst.setString(1, login);
			pst.setString(2, password);

			rs = pst.executeQuery();

			if (rs.next()) {

				String name = rs.getString(NAME);
				String surname = rs.getString(SURNAME);
				String role = rs.getString(ROLE);

				user = new User();
				user.setName(name);
				user.setSurname(surname);
				user.setLogin(login);
				user.setRole(role);
			}

		} catch (SQLException |InterruptedException e) {
			throw new DAOException(e);

		} finally {
			lock.unlock();
			CloseConnectionBlockFinally.close(con, pst, rs);
		}
		
		return user;
	}

	@Override
	public void registration(User user) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {

			con = ConnectionPool.getInstance().takeConnection();
			con.setAutoCommit(false);

			lock.lock();

			pst = con.prepareStatement(SELECT_ROLE);
			pst.setString(1, user.getRole());
			rs = pst.executeQuery();

			if (!rs.next()) {
				pst = con.prepareStatement(INSERT_ROLE);
				pst.setString(1, user.getRole());
				pst.executeUpdate();
			}

			pst = con.prepareStatement(SELECT_LOGIN);
			pst.setString(1, user.getLogin());
			rs = pst.executeQuery();

			if (!rs.next()) {
				pst = con.prepareStatement(INSERT_USER);
				pst.setString(1, user.getName());
				pst.setString(2, user.getSurname());
				pst.setString(3, user.getLogin());
				pst.setString(4, user.getPassword());
				pst.setString(5, user.getEmail());
				pst.setDate(6, java.sql.Date.valueOf(user.getBirhday()));
				pst.setString(7, user.getGroup());
				pst.setString(8, user.getRole());
				pst.executeUpdate();
			}

			con.commit();

		} catch (SQLException |InterruptedException e) {

			try {
				con.rollback();
				con.setAutoCommit(true);
			} finally {
				throw new DAOException(e);
			}

		} finally {

			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				throw new DAOException(e);
			}

			lock.unlock();
			CloseConnectionBlockFinally.close(con, pst, rs);
		}
	}

	@Override
	public List<User> takeListUsers() throws DAOException {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<User> listUsers = new ArrayList<>();

		try {
			con = ConnectionPool.getInstance().takeConnection();
			pst = con.prepareStatement(SELECT_USERS);
			rs = pst.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setLogin(rs.getString(LOGIN));
				user.setName(rs.getString(NAME));
				user.setSurname(rs.getString(SURNAME));
				user.setGroup(rs.getString(GROUP_NAME));
				listUsers.add(user);
			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

		return listUsers;

	}
}
