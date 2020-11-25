package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class SetHeaderAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(SetHeaderAction.class);
	/**
	 * name
	 */
	private String name;
	/**
	 * value
	 */
	private String value;

	@Override
	public void execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		System.out.println("Setting header " + name + " to value " + value + " on  message " + messageData.getId());
		logger.info("Setting header " + name + " to value " + value + " on  message " + messageData.getId());
		IMAPUtil.getInstance().setHeader(messageData.getId(), name, value);
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
