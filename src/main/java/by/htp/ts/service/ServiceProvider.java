package by.htp.ts.service;

import by.htp.ts.service.impl.TestServiceImpl;
import by.htp.ts.service.impl.UserServiceImpl;

public final class ServiceProvider {

	private final static ServiceProvider INSTANCE = new ServiceProvider();
	private final UserServiceImpl userService = new UserServiceImpl();
	private final TestServiceImpl testService = new TestServiceImpl();

	private ServiceProvider() {
	}

	public static ServiceProvider getInstance() {
		return INSTANCE;
	}

	public UserServiceImpl getUserService() {
		return INSTANCE.userService;
	}
	
	public TestServiceImpl getTestService() {
		return INSTANCE.testService;
	}

}
