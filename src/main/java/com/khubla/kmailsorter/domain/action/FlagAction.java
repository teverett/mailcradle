package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.util.*;

public class FlagAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(FlagAction.class);
	/**
	 * flag
	 */
	private String flag;

	@Override
	public void execute(MessageData messageData, Mailsort mailsort) throws MessagingException {
		System.out.println("Flagging message " + messageData.getId() + " with: " + flag);
		logger.info("Flagging message " + messageData.getId() + " with: " + flag);
		MailUtil.getInstance().flagMessage(messageData.getId(), flag, true);
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
