package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class UnflagAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(UnflagAction.class);
	/**
	 * flag
	 */
	private String flag;

	@Override
	public void execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		System.out.println("Unflagging message " + messageData.getId() + " from: " + flag);
		logger.info("Unflagging message " + messageData.getId() + " from: " + flag);
		IMAPUtil.getInstance().flagMessage(messageData.getId(), flag, false);
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}