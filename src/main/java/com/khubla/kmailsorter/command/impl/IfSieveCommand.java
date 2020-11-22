package com.khubla.kmailsorter.command.impl;

import java.util.*;

import javax.mail.*;

import com.khubla.kmailsorter.command.*;
import com.khubla.kmailsorter.comparison.*;
import com.khubla.kmailsorter.domain.*;

public class IfSieveCommand implements SieveCommand {
	/**
	 * evaluate a test and return boolean
	 *
	 * @param test Test
	 * @param message Message to execute on
	 * @return true or false result
	 * @throws MessagingException messaging exception
	 */
	private boolean evaluateTest(Test test, Message message) throws MessagingException {
		final ComparisonType comparisonType = ComparisonType.fromString(test.getArguments().getArguments().get(0).getTag());
		final SieveComparison sieveComparison = SieveComparisonFactory.getComparison(comparisonType);
		return sieveComparison.compare(test, message);
	}

	/**
	 * execute a command on a message. beware, recursive
	 */
	@Override
	public void execute(Message message, Command command) throws MessagingException {
		final Arguments arguments = command.getArguments();
		if (null != arguments) {
			final Test test = arguments.getTest();
			final TestList testList = arguments.getTestList();
			if (null != test) {
				evaluateTest(test, message);
				// if (passed) {
				executeActions(command.getCommands(), message);
				// }
			} else if (null != testList) {
				/*
				 * test list
				 */
				for (final Test test2 : testList.getTests()) {
					evaluateTest(test2, message);
				}
			}
		}
	}

	/**
	 * execute the actions of a test
	 *
	 * @param test the test
	 * @param message the message
	 * @throws MessagingException messaging exception
	 */
	private void executeActions(List<Command> commands, Message message) throws MessagingException {
		for (final Command command : commands) {
			SieveCommandFactory.getCommand(command.getCommandType()).execute(message, command);
		}
	}
}
