package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

import java.util.List;

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
	public boolean execute(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException {
		dumpExpressionSteps(expressionSteps);
		System.out.println("\tForwarding message " + messageData.getId() + " in folder " + messageData.getFolderName() + " to address: " + address);
		logger.info("Forwarding message " + messageData.getId() + " in folder " + messageData.getFolderName() + " to address: " + address);
		FolderFactory.getInstance().getFolder(messageData.getFolderName()).forwardMessage(messageData.getUid(), address);
		return true;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
