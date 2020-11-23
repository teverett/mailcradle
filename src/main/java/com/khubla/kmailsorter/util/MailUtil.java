package com.khubla.kmailsorter.util;

import java.io.*;

import javax.mail.*;
import javax.mail.search.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.*;
import com.sun.mail.imap.*;

public class MailUtil {
	private static MailUtil instance = null;
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailUtil.class);

	public static MailUtil getInstance() throws MessagingException {
		if (null == instance) {
			instance = new MailUtil();
		}
		return instance;
	}

	final Session session;
	final Store store;

	/**
	 * ctoe
	 *
	 * @throws MessagingException MessagingException
	 */
	private MailUtil() throws MessagingException {
		session = Session.getDefaultInstance(System.getProperties(), null);
		store = session.getStore("imaps");
		logger.info("Logging into  " + KMailSorterConfiguration.getInstance().getImapHost() + " as " + KMailSorterConfiguration.getInstance().getImapUsername());
		store.connect(KMailSorterConfiguration.getInstance().getImapHost(), KMailSorterConfiguration.getInstance().getImapUsername(), KMailSorterConfiguration.getInstance().getImapPassword());
	}

	/**
	 * get IMAP inbox
	 *
	 * @return Folder
	 * @throws MessagingException
	 */
	private Folder getInbox() throws MessagingException {
		final Folder root = getRootFolder();
		return root.getFolder(KMailSorterConfiguration.getInstance().getImapFolder());
	}

	/**
	 * get message count
	 *
	 * @return count
	 * @throws MessagingException MessagingException
	 */
	public int getMessageCount() throws MessagingException {
		logger.info("Getting message count");
		Folder inboxFolder = null;
		try {
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_ONLY);
			return inboxFolder.getMessageCount();
		} finally {
			if (null != inboxFolder) {
				if (inboxFolder.isOpen()) {
					inboxFolder.close();
				}
				inboxFolder = null;
			}
		}
	}

	public MessageData getMessageData(String uid) throws MessagingException, IOException {
		Folder inboxFolder = null;
		try {
			logger.info("Getting MessageData for message: " + uid);
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_ONLY);
			final SearchTerm searchTerm = new MessageIDTerm(uid);
			final Message[] messages = inboxFolder.search(searchTerm);
			if ((null != messages) && (messages.length == 1)) {
				if (messages[0] instanceof IMAPMessage) {
					return new MessageData((IMAPMessage) messages[0]);
				}
			}
			return null;
		} finally {
			if (null != inboxFolder) {
				if (inboxFolder.isOpen()) {
					inboxFolder.close();
				}
				inboxFolder = null;
			}
		}
	}

	/**
	 * get the IMAP root folder
	 *
	 * @return Folder
	 * @throws MessagingException MessagingException
	 */
	private Folder getRootFolder() throws MessagingException {
		return store.getDefaultFolder();
	}

	/**
	 * get all message UIDs
	 *
	 * @return array of UIDs
	 * @throws MessagingException MessagingException
	 */
	public String[] getUIDs() throws MessagingException {
		logger.info("Getting uids");
		Folder inboxFolder = null;
		try {
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_ONLY);
			final int count = inboxFolder.getMessageCount();
			final String[] ret = new String[count];
			for (int i = 1; i < (count + 1); i++) {
				final Message message = inboxFolder.getMessage(i);
				if (message instanceof IMAPMessage) {
					ret[i - 1] = ((IMAPMessage) message).getMessageID();
				}
			}
			return ret;
		} finally {
			if (null != inboxFolder) {
				if (inboxFolder.isOpen()) {
					inboxFolder.close();
				}
				inboxFolder = null;
			}
		}
	}

	/**
	 * move a message to a new folder
	 *
	 * @param message Message
	 * @param folderName name of folder
	 * @throws MessagingException MessagingException
	 */
	public void moveMessage(String uid, String folderName) throws MessagingException {
		Folder rootFolder = null;
		Folder targetFolder = null;
		Folder inboxFolder = null;
		try {
			logger.info("Moving message " + uid + " to folder " + folderName);
			/*
			 * inbox
			 */
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_WRITE);
			/*
			 * message
			 */
			final SearchTerm searchTerm = new MessageIDTerm(uid);
			final Message[] messages = inboxFolder.search(searchTerm);
			if ((null != messages) && (messages.length == 1)) {
				/*
				 * target
				 */
				rootFolder = getRootFolder();
				targetFolder = rootFolder.getFolder(folderName);
				/*
				 * create target if we need to
				 */
				if (false == targetFolder.exists()) {
					targetFolder.create(Folder.HOLDS_MESSAGES);
					targetFolder.setSubscribed(true);
				}
				/*
				 * open target
				 */
				targetFolder.open(Folder.READ_WRITE);
				/*
				 * copy message
				 */
				inboxFolder.copyMessages(new Message[] { messages[0] }, targetFolder);
				/*
				 * delete message
				 */
				messages[0].setFlag(Flags.Flag.DELETED, true);
			}
		} finally {
			if (null != inboxFolder) {
				if (inboxFolder.isOpen()) {
					inboxFolder.close(true);
				}
				inboxFolder = null;
			}
			if (null != targetFolder) {
				if (targetFolder.isOpen()) {
					targetFolder.close(true);
				}
				targetFolder = null;
			}
			if (null != rootFolder) {
				// root does not contain messages so we neither open nor close it
				rootFolder = null;
			}
		}
	}
}
