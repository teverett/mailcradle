package com.khubla.kmailsorter.action.impl;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.action.*;
import com.khubla.kmailsorter.domain.*;

public class FileintoSieveActionType implements SieveAction {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(FileintoSieveActionType.class);

	@Override
	public void execute(Message message, Command command) throws MessagingException {
		logger.info("Filing message: " + message.getMessageNumber());
	}
}
