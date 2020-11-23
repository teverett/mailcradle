package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.util.*;

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
	public void execute(MessageData messageData, Mailsort mailsort) throws MessagingException {
		System.out.println("Unflagging message " + messageData.getId() + " from: " + flag);
		logger.info("Unflagging message " + messageData.getId() + " from: " + flag);
		MailUtil.getInstance().flagMessage(messageData.getId(), flag, false);
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
