package com.khubla.mailcradle.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class MoveAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MoveAction.class);
	/**
	 * folder name
	 */
	private String targetFolderName;

	@Override
	public boolean execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException {
		if (targetFolderName.trim().compareTo(messageData.getFolderName().trim()) != 0) {
			System.out.println("Moving message " + messageData.getId() + " in folder " + messageData.getFolderName() + " to folder: " + targetFolderName);
			logger.info("Moving message " + messageData.getId() + " in folder " + messageData.getFolderName() + " to folder: " + targetFolderName);
			FolderFactory.getInstance().getFolder(messageData.getFolderName()).moveMessage(messageData.getUid(), targetFolderName);
		} else {
			// System.out.println("Message " + messageData.getId() + " is already in folder " +
			// messageData.getFolderName());
			logger.info("Message " + messageData.getId() + " is already in folder " + messageData.getFolderName());
		}
		return true;
	}

	public String getTargetFolderName() {
		return targetFolderName;
	}

	public void setTargetFolderName(String targetFolderName) {
		this.targetFolderName = targetFolderName;
	}
}
