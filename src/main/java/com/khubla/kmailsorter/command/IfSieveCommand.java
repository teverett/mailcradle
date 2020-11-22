package com.khubla.kmailsorter.command;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public class IfSieveCommand implements SieveCommand {
	@Override
	public void execute(Message message, Command command) throws MessagingException {
		if (null != command.getArguments().getTest()) {
			/*
			 * single test
			 */
			executeTest(command.getArguments().getTest(), message);
		} else if (null != command.getArguments().getTestList()) {
			/*
			 * test list
			 */
			for (final Test test : command.getArguments().getTestList().getTests()) {
				executeTest(test, message);
			}
		}
	}

	private boolean executeTest(Test test, Message message) throws MessagingException {
		final TestType testType = TestType.fromString(test.getName());
		// ComparisonType comparisonType =
		// ComparisonType.fromString(test.getArguments().getArguments().get(0).getTag());
		final String fieldName = test.getArguments().getArguments().get(1).getString();
		test.getArguments().getArguments().get(2).getString();
		getMessageFieldValue(message, testType, fieldName);
		return false;
	}

	private String getMessageFieldValue(Message message, TestType testType, String fieldName) throws MessagingException {
		switch (testType) {
			case header:
				/*
				 * get a field from the header
				 */
				final String[] header = message.getHeader(fieldName);
				if (null != header) {
					return header[0];
				}
				return null;
			case address:
				return null;
			default:
				return null;
		}
	}
}
