package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class StopAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(StopAction.class);

	@Override
	public boolean execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		// System.out.println("Stopping rule processing for message " + messageData.getId() + " in
		// folder " + messageData.getFolderName());
		logger.info("Stopping rule processing for message " + messageData.getId() + " in folder " + messageData.getFolderName());
		return false;
	}
}
