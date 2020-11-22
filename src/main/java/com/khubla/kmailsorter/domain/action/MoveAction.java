package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;

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
	public void execute(Message message) throws MessagingException {
		logger.info("Moving message " + message.getMessageNumber() + " to folder: " + folderName);
		throw new RuntimeException("Not Implemented");
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
