package by.htp.ts.command;

import java.util.HashMap;
import java.util.Map;

import by.htp.ts.command.concrete_impl.AddQuestionCommand;
import by.htp.ts.command.concrete_impl.AssignTestCommand;
import by.htp.ts.command.concrete_impl.AuthorizationCommand;
import by.htp.ts.command.concrete_impl.ChangeLocaleCommand;
import by.htp.ts.command.concrete_impl.CloseAddQuestionCommand;
import by.htp.ts.command.concrete_impl.CreateTestCommand;
import by.htp.ts.command.concrete_impl.FinishRunTestCommand;
import by.htp.ts.command.concrete_impl.RegistrationCommand;
import by.htp.ts.command.concrete_impl.RunTestCommand;
import by.htp.ts.command.concrete_impl.RunTestNextQuestionCommand;
import by.htp.ts.command.concrete_impl.SignOutCommand;
import by.htp.ts.command.concrete_impl.DeleteTestCommand;
import by.htp.ts.command.concrete_impl.ViewListTestCommand;
import by.htp.ts.command.concrete_impl.ViewListTestForUserCommand;
import by.htp.ts.command.concrete_impl.ViewTestsResultsCommand;
import by.htp.ts.command.goto_impl.GoToAddQuestionPageCommand;
import by.htp.ts.command.goto_impl.GoToAssignTestPageCommand;
import by.htp.ts.command.goto_impl.GoToCreateTestPageCommand;
import by.htp.ts.command.goto_impl.GoToErrorPageCommand;
import by.htp.ts.command.goto_impl.GoToListQuestionByTestPageCommand;
import by.htp.ts.command.goto_impl.GoToMainPageCommand;
import by.htp.ts.command.goto_impl.GoToRegistrationPageCommand;
import by.htp.ts.command.goto_impl.GoToSignInPageCommand;

public final class CommandProvider {

	private Map<CommandName, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
		commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
		commands.put(CommandName.SIGN_OUT, new SignOutCommand());
		commands.put(CommandName.CREATE_TEST, new CreateTestCommand());
		commands.put(CommandName.ADD_QUESTION, new AddQuestionCommand());
		commands.put(CommandName.CLOSE_ADD_QUESTION, new CloseAddQuestionCommand());
		commands.put(CommandName.RUN_TEST, new RunTestCommand());
		commands.put(CommandName.RUN_TEST_NEXT_QUESTION, new RunTestNextQuestionCommand());
		commands.put(CommandName.FINISH_RUN_TEST, new FinishRunTestCommand());
		commands.put(CommandName.ASSIGN_TEST, new AssignTestCommand());
		commands.put(CommandName.VIEW_LIST_TEST, new ViewListTestCommand());
		commands.put(CommandName.VIEW_LIST_TEST_FOR_USER, new ViewListTestForUserCommand());
		commands.put(CommandName.VIEW_LIST_TESTS_RESULTS, new ViewTestsResultsCommand());
		commands.put(CommandName.DELETE_TEST, new DeleteTestCommand());

		commands.put(CommandName.GO_TO_SIGN_IN_PAGE, new GoToSignInPageCommand());
		commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.GO_TO_CREATE_TEST_PAGE, new GoToCreateTestPageCommand());
		commands.put(CommandName.GO_TO_ADD_QUESTION_PAGE, new GoToAddQuestionPageCommand());
		commands.put(CommandName.GO_TO_LIST_QUESTION_BY_TEST_PAGE, new GoToListQuestionByTestPageCommand());
		commands.put(CommandName.GO_TO_ASSIGN_TEST_PAGE, new GoToAssignTestPageCommand());
	}

	public Command getCommand(String name) {

		CommandName commandName;

		if (name == null) {
			commandName = CommandName.GO_TO_ERROR_PAGE;
		} else {
			commandName = CommandName.valueOf(name.toUpperCase());
		}
		
		return commands.get(commandName);
	}
}
