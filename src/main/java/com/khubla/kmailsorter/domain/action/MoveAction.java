package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.util.*;

public class MoveAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MoveAction.class);
	/**
	 * folder name
	 */
	private String folderName;

	@Override
	public void execute(MessageData messageData, Mailsort mailsort) throws MessagingException {
		System.out.println("Moving message " + messageData.getId() + " to folder: " + folderName);
		logger.info("Moving message " + messageData.getId() + " to folder: " + folderName);
		MailUtil.getInstance().moveMessage(messageData.getId(), folderName);
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
