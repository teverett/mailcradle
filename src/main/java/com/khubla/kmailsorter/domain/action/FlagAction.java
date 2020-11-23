package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.imap.*;

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
	public void execute(IMAPMessageData messageData, Mailsort mailsort) throws MessagingException {
		System.out.println("Flagging message " + messageData.getId() + " with: " + flag);
		logger.info("Flagging message " + messageData.getId() + " with: " + flag);
		IMAPUtil.getInstance().flagMessage(messageData.getId(), flag, true);
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
