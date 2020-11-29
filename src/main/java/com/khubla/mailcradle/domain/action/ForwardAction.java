package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class ForwardAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(ForwardAction.class);
	/**
	 * email address to forward to
	 */
	private String address;

	@Override
	public void execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		System.out.println("Forwarding message " + messageData.getId() + " to address: " + address);
		logger.info("Forwarding message " + messageData.getId() + " to address: " + address);
		FolderFactory.getInstance().getFolder(messageData.getFolderName()).forwardMessage(messageData.getUid(), address);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
