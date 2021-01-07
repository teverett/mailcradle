package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

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
	public boolean execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		System.out.println("Replying to message " + messageData.getId() + " in folder " + messageData.getFolderName() + " with: " + reply);
		logger.info("Replying to message " + messageData.getId() + " in folder " + messageData.getFolderName() + " with: " + reply);
		FolderFactory.getInstance().getFolder(messageData.getFolderName()).replyMessage(messageData.getUid(), reply);
		/*
		 * mark as replied
		 */
		FolderFactory.getInstance().getFolder(messageData.getFolderName()).setFlag(messageData.getUid(), javax.mail.Flags.Flag.ANSWERED, true);
		return true;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
}
