package com.khubla.mailcradle.imap;

import java.io.*;

import javax.mail.*;
import javax.mail.event.*;

import org.apache.logging.log4j.*;

import com.sun.mail.imap.*;

public class IMAPMessageCountListener implements MessageCountListener {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(IMAPFolderUtil.class);
	/**
	 * call back
	 */
	private final IMAPMessageCallback imapMessageCallback;
	/**
	 * parent folder
	 */
	private final String folderName;

	public IMAPMessageCountListener(String folderName, IMAPMessageCallback imapMessageCallback) {
		super();
		this.imapMessageCallback = imapMessageCallback;
		this.folderName = folderName;
	}

	@Override
	public void messagesAdded(MessageCountEvent messageCountEvent) {
		try {
			if (null != messageCountEvent) {
				/*
				 * reconnect is necessary
				 */
				final IMAPFolderUtil imapFolderUtil = FolderFactory.getInstance().getFolder(folderName);
				imapFolderUtil.connect();
				for (final Message message : messageCountEvent.getMessages()) {
					final IMAPMessageData imapMessageData = imapFolderUtil.getMessageData((IMAPMessage) message);
					if (null != imapMessageData) {
						imapMessageCallback.message(imapMessageData);
					} else {
						logger.error("Unable to find message " + message + " in folder " + imapFolderUtil.getFolderName());
					}
				}
			}
		} catch (MessagingException | IOException e) {
			logger.error(e);
		}
	}

	@Override
	public void messagesRemoved(MessageCountEvent messageCountEvent) {
	}
}
