package by.htp.ts.dao;

public class DAOException extends Exception {

	private static final long serialVersionUID = 8115759740574138776L;
	
	public DAOException() {
		super();
	}

	public DAOException(String message, Exception e) {
		super(message, e);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Exception e) {
		super(e);
	}

}
