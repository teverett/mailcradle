package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.imap.*;

public class ReplyAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(ReplyAction.class);
	/**
	 * reply text
	 */
	private String reply;

	@Override
	public void execute(IMAPMessageData messageData, Mailsort mailsort) throws MessagingException {
		System.out.println("Replying to message " + messageData.getId() + " with: " + reply);
		logger.info("Replying to message " + messageData.getId() + " with: " + reply);
		IMAPUtil.getInstance().replyMessage(messageData.getId(), reply);
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
}
