package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class RemoveHeaderAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(RemoveHeaderAction.class);
	/**
	 * name
	 */
	private String name;

	@Override
	public void execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		System.out.println("Removing header " + name + " from  message " + messageData.getId());
		logger.info("Removing header " + name + " from  message " + messageData.getId());
		IMAPUtil.getInstance().removeHeader(messageData.getId(), name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
