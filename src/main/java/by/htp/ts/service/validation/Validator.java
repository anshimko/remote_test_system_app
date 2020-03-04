package by.htp.ts.service.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import by.htp.ts.command.concrete_impl.RequestParameter;

public final class Validator {

	private static final String LOGIN_REGEX = "[0-9a-zA-Z]{3,50}";
	private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{3,50}";
	private static final String NAME_REGEX = "[a-zA-Z]{2,50}";
	private static final String SURNAME_REGEX = "[a-zA-Z]{2,50}";
	private static final String BIRTHDAY_REGEX = "\\d{4}-\\d{2}-\\d{2}";
	private static final String EMAIL_REGEX = "^.*@.*\\..*$";
	private static final String GROUP_REGEX = ".{1,30}";
	private static final String ROLE_REGEX = "\\w{2,30}";
	private static final String QUESTION_REGEX = ".{3,400}";
	private static final String ANSWER_REGEX = ".{1,400}";
	private static final String TOPIC_REGEX = ".{3,100}";

	private static final Map<String, String> valid = new HashMap<>();

	private Validator() {
		valid.put(RequestParameter.LOGIN, LOGIN_REGEX);
		valid.put(RequestParameter.PASSWORD, PASSWORD_REGEX);
		valid.put(RequestParameter.NAME, NAME_REGEX);
		valid.put(RequestParameter.SURNAME, SURNAME_REGEX);
		valid.put(RequestParameter.BIRTHDAY, BIRTHDAY_REGEX);
		valid.put(RequestParameter.EMAIL, EMAIL_REGEX);
		valid.put(RequestParameter.GROUP, GROUP_REGEX);
		valid.put(RequestParameter.ROLE, ROLE_REGEX);
		valid.put(RequestParameter.QUESTION, QUESTION_REGEX);
		valid.put(RequestParameter.ANSWER, ANSWER_REGEX);
		valid.put(RequestParameter.TOPIC, TOPIC_REGEX);
	}

	public static boolean validatation(String param, String value) {

		if (value == null || value.isEmpty()) {
			return false;
		}

		for (Map.Entry<String, String> entry : valid.entrySet()) {
			if (entry.getKey().equals(param)) {
				if (!Pattern.matches(param, value)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean validatation(Integer value) {

		return (value == null || value < 0 || value > 100) ? false : true;

	}
}
