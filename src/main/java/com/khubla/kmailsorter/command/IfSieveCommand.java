package com.khubla.kmailsorter.command;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;

public class IfSieveCommand implements SieveCommand {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(IfSieveCommand.class);

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
		final String messagePart = test.getName();
		test.getArguments().getArguments().get(0).getTag();
		final String fieldName = test.getArguments().getArguments().get(1).getString();
		test.getArguments().getArguments().get(2).getString();
		getMessageFieldValue(message, messagePart, fieldName);
		return false;
	}

	private String getMessageFieldValue(Message message, String messagePart, String fieldName) throws MessagingException {
		switch (messagePart) {
			case "header":
				/*
				 * get a field from the header
				 */
				final String[] header = message.getHeader(fieldName);
				if (null != header) {
					return header[0];
				}
				return null;
			case "address":
				return null;
			default:
				logger.error("Unknown message part: " + messagePart);
				return null;
		}
	}
}
