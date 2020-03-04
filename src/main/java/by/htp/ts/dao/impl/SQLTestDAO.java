package by.htp.ts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import by.htp.ts.bean.Question;
import by.htp.ts.bean.Test;
import by.htp.ts.bean.UserHasTestDTO;
import by.htp.ts.bean.UserTestResultDTO;
import by.htp.ts.dao.DAOException;
import by.htp.ts.dao.TestDAO;
import by.htp.ts.dao.connection_pool.CloseConnectionBlockFinally;
import by.htp.ts.dao.connection_pool.ConnectionPool;

public class SQLTestDAO implements TestDAO {

	private static final ReentrantLock lock = new ReentrantLock();

	private final static String SELECT_TEST_BY_TOPIC = "SELECT * FROM test WHERE topic = ?;";
	private final static String SELECT_QUESTION_BY_TEST_ID = "SELECT * FROM question WHERE test_id = ?;";
	private final static String SELECT_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answer WHERE question_id = ?;";
	private final static String SELECT_TESTS = "SELECT topic FROM test;";
	private final static String SELECT_ASSIGNED_TESTS = "SELECT topic FROM test WHERE id IN (SELECT test_id FROM user_has_test WHERE user_id = " + "(SELECT id FROM user WHERE login = ?));";
	private final static String SELECT_RESULT_TESTS = "SELECT user.name, user.surname, user.group_name, test.topic, score FROM user_test_result JOIN user ON user_test_result.user_id = user.id JOIN test ON user_test_result.test_id = test.id ORDER BY user.surname LIMIT ?, ?;";
	private final static String SELECT_COUNT_RECORDS_USER_RESULT_TEST = "SELECT COUNT(*) FROM user_test_result;";
	
	private final static String SELECT_USER_TEST_RESULT = "SELECT * FROM user_test_result WHERE user_id=(SELECT id FROM user WHERE login = ?) AND test_id=(SELECT id FROM test WHERE topic = ?);";
	private final static String SELECT_USER_HAS_TEST = "SELECT * FROM user_has_test WHERE user_id=(SELECT id FROM user WHERE login = ?) AND test_id=(SELECT id FROM test WHERE topic = ?);";
	
	private final static String INSERT_TEST = "INSERT INTO test (topic, min_score, duration) VALUES (?,?,?);";
	private final static String INSERT_QUESTION = "INSERT INTO question (question, test_id) VALUES (?,(SELECT id FROM test WHERE topic = ?));";
	private final static String INSERT_ANSWER = "INSERT INTO answer (answer, is_true, question_id) VALUES (?,?,(SELECT id FROM question WHERE question = ?));";
	private final static String INSERT_USER_HAS_TEST = "INSERT INTO user_has_test (user_id, test_id, date_test_assigned) "
			+ "VALUES ((SELECT id FROM user WHERE login = ?),(SELECT id FROM test WHERE topic = ?),?);";
	private final static String INSERT_USER_TEST_RESULT = "INSERT INTO user_test_result (user_id, test_id, score) "
			+ "VALUES ((SELECT id FROM user WHERE login = ?),(SELECT id FROM test WHERE topic = ?),?);";

	private final static String DELETE_USER_HAS_TEST = "DELETE FROM user_has_test WHERE user_id = (SELECT id FROM user WHERE login = ?) AND test_id = (SELECT id FROM test WHERE topic = ?);";
	private final static String DELETE_TEST_BY_TOPIC = "DELETE FROM test WHERE topic = ?;";
	
	@Override
	public void createTest(Test test) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();

			pst = con.prepareStatement(SELECT_TEST_BY_TOPIC);
			pst.setString(1, test.getTopic());

			lock.lock();

			rs = pst.executeQuery();

			if (!rs.next()) {

				pst = con.prepareStatement(INSERT_TEST);
				pst.setString(1, test.getTopic());
				pst.setInt(2, test.getMinScore());
				pst.setTime(3, test.getDuration());
				pst.executeUpdate();

			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			lock.unlock();
			CloseConnectionBlockFinally.close(con, pst, rs);
		}
	}

	@Override
	public void addQuestion(Question question) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();

			lock.lock();

			con.setAutoCommit(false);
			pst = con.prepareStatement(INSERT_QUESTION);
			pst.setString(1, question.getQuestion());
			pst.setString(2, question.getTopic());
			pst.executeUpdate();

			pst = con.prepareStatement(INSERT_ANSWER);

			for (Map.Entry<String, String> item : question.getAnswer().entrySet()) {
				pst.setString(1, item.getKey());
				pst.setString(2, item.getValue());
				pst.setString(3, question.getQuestion());
				pst.executeUpdate();
			}

			con.commit();

		} catch (SQLException | InterruptedException e) {
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
	public List<String> viewListTest() throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<String> listTest = new ArrayList<String>();

		try {
			con = ConnectionPool.getInstance().takeConnection();
			pst = con.prepareStatement(SELECT_TESTS);
			rs = pst.executeQuery();

			while (rs.next()) {

				listTest.add(rs.getString("topic"));
			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

		return listTest;
	}

	@Override
	public Test takeTest(String topic) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		Test test = new Test();
		List<Question> listQuestion = new ArrayList<Question>();
		Map<String, String> answers = new HashMap<String, String>();
		int testId = 0;

		try {
			con = ConnectionPool.getInstance().takeConnection();

			pst = con.prepareStatement(SELECT_TEST_BY_TOPIC);
			pst.setString(1, topic);
			rs = pst.executeQuery();

			while (rs.next()) {
				test.setTopic(rs.getString("topic"));

				test.setDuration(rs.getTime("duration"));
				test.setMinScore(rs.getInt("min_score"));
				testId = rs.getInt("id");
			}

			pst = con.prepareStatement(SELECT_QUESTION_BY_TEST_ID);
			pst.setInt(1, testId);
			rs = pst.executeQuery();

			while (rs.next()) {
				Question question = new Question();
				question.setQuestion(rs.getString("question"));

				int question_id = rs.getInt("id");
				answers = takeAnswersByQuestionID(question_id);

				question.setAnswer(answers);
				listQuestion.add(question);
			}

			test.setListQuestion(listQuestion);

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

		return test;
	}

	private Map<String, String> takeAnswersByQuestionID(int id) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		Map<String, String> answers = new HashMap<String, String>();

		try {
			con = ConnectionPool.getInstance().takeConnection();
			pst = con.prepareStatement(SELECT_ANSWERS_BY_QUESTION_ID);
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				answers.put(rs.getString("answer"), rs.getString("is_true"));
			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}
		return answers;
	}

	@Override
	public void assignTest(UserHasTestDTO userHasTestDTO) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();
			
			for (String login : userHasTestDTO.getLogin()) {

				pst = con.prepareStatement(SELECT_USER_HAS_TEST);
				pst.setString(1,  login);
				pst.setString(2, userHasTestDTO.getTopic());
				rs = pst.executeQuery();
				
				if (!rs.next()) {
					pst = con.prepareStatement(SELECT_USER_TEST_RESULT);
					pst.setString(1,  login);
					pst.setString(2, userHasTestDTO.getTopic());
					rs = pst.executeQuery();
				}

				if (!rs.next()) {
					pst = con.prepareStatement(INSERT_USER_HAS_TEST);
					pst.setString(1, login);
					pst.setString(2, userHasTestDTO.getTopic());
					pst.setDate(3, userHasTestDTO.getDate());
					pst.executeUpdate();
			}
		}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

	}

	@Override
	public List<String> viewListTestForUser(String login) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<String> listTest = new ArrayList<String>();

		try {
			con = ConnectionPool.getInstance().takeConnection();

			pst = con.prepareStatement(SELECT_ASSIGNED_TESTS);
			pst.setString(1, login);
			rs = pst.executeQuery();

			while (rs.next()) {

				listTest.add(rs.getString("topic"));
			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

		return listTest;
	}

	@Override
	public void saveUserTestResult(UserTestResultDTO userTestResultDTO) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();

			con.setAutoCommit(false);

			pst = con.prepareStatement(INSERT_USER_TEST_RESULT);
			pst.setString(1, userTestResultDTO.getLogin());
			pst.setString(2, userTestResultDTO.getTopic());
			pst.setDouble(3, userTestResultDTO.getScore());
			pst.executeUpdate();

			pst = con.prepareStatement(DELETE_USER_HAS_TEST);
			pst.setString(1, userTestResultDTO.getLogin());
			pst.setString(2, userTestResultDTO.getTopic());
			pst.executeUpdate();

			con.commit();

		} catch (SQLException | InterruptedException e) {
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
			CloseConnectionBlockFinally.close(con, pst, rs);
		}
	}

	@Override
	public void deleteTest(String topic) throws DAOException {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			con = ConnectionPool.getInstance().takeConnection();

			pst = con.prepareStatement(DELETE_TEST_BY_TOPIC);
			pst.setString(1, topic);
			pst.executeUpdate();

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}
	}

	@Override
	public List<UserTestResultDTO> viewTestResult(int offset, int recordPerPage) throws DAOException {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<UserTestResultDTO> listResultTests = new ArrayList<>();

		try {
			con = ConnectionPool.getInstance().takeConnection();

			pst = con.prepareStatement(SELECT_RESULT_TESTS);
			pst.setInt(1, offset);
			pst.setInt(2, recordPerPage);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				
				UserTestResultDTO userTestResult = new UserTestResultDTO();
				
				userTestResult.setName(rs.getString("name"));
				userTestResult.setSurname(rs.getString("surname"));
				userTestResult.setGroup(rs.getString("group_name"));
				userTestResult.setTopic(rs.getString("topic"));
				userTestResult.setScore(rs.getDouble("score"));

				listResultTests.add(userTestResult);
			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

		return listResultTests;
	}

	@Override
	public int getCountOfRecords() throws DAOException {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		int getCountOfRecords = 0;

		try {
			con = ConnectionPool.getInstance().takeConnection();

			pst = con.prepareStatement(SELECT_COUNT_RECORDS_USER_RESULT_TEST);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				getCountOfRecords = rs.getInt(1);
			}

		} catch (SQLException | InterruptedException e) {
			throw new DAOException(e);
		} finally {
			CloseConnectionBlockFinally.close(con, pst, rs);
		}

		return getCountOfRecords;
	}
}
