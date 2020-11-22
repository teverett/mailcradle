package com.khubla.kmailsorter.action.impl;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.action.*;
import com.khubla.kmailsorter.domain.*;

public class RedirectSieveActionType implements SieveAction {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(RedirectSieveActionType.class);

	@Override
	public void execute(Message message, Command command) throws MessagingException {
		logger.info("Redirecting message: " + message.getMessageNumber());
	}
}
