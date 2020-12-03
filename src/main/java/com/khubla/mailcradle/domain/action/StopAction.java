package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class StopAction extends Action {
	/**
	 * logger
	 */
	// private static final Logger logger = LogManager.getLogger(StopAction.class);
	@Override
	public boolean execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		System.out.println("Stopping rule processing for message " + messageData.getId());
		return false;
	}
}
