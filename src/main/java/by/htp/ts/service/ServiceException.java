package by.htp.ts.service;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -2249730031125565127L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message) {
		super(message);
	}

}
