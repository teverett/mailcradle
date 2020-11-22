package com.khubla.kmailsorter.action.impl;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.action.*;
import com.khubla.kmailsorter.domain.*;

public class DiscardSieveActionType implements SieveAction {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(DiscardSieveActionType.class);

	@Override
	public void execute(Message message, Command command) throws MessagingException {
		logger.info("Discarding message: " + message.getMessageNumber());
	}
}
