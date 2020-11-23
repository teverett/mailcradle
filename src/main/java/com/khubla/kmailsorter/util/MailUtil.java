package com.khubla.kmailsorter.util;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.*;

public class MailUtil {
	/**
	 * get IMAP inbox
	 *
	 * @return Folder
	 * @throws MessagingException
	 */
	public static Folder getInbox() throws MessagingException {
		final Folder root = getRootFolder();
		return root.getFolder(KMailSorterConfiguration.getInstance().getImapFolder());
	}

	/**
	 * get message by id
	 *
	 * @param id id number
	 * @return Message
	 * @throws MessagingException MessagingException
	 */
	public static Message getMessage(int id) throws MessagingException {
		Folder inboxFolder = null;
		try {
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_ONLY);
			return inboxFolder.getMessage(id);
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
	 * get message count
	 *
	 * @return count
	 * @throws MessagingException MessagingException
	 */
	public static int getMessageCount() throws MessagingException {
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

	public static MessageData getMessageData(int id) throws MessagingException, IOException {
		Folder inboxFolder = null;
		try {
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_ONLY);
			final Message m = inboxFolder.getMessage(id);
			return new MessageData(m);
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
	public static Folder getRootFolder() throws MessagingException {
		final Session session = Session.getDefaultInstance(System.getProperties(), null);
		final Store store = session.getStore("imaps");
		store.connect(KMailSorterConfiguration.getInstance().getImapHost(), KMailSorterConfiguration.getInstance().getImapUsername(), KMailSorterConfiguration.getInstance().getImapPassword());
		return store.getDefaultFolder();
	}

	/**
	 * move a message to a new folder
	 *
	 * @param message Message
	 * @param folderName name of folder
	 * @throws MessagingException MessagingException
	 */
	public static void moveMessage(int id, String folderName) throws MessagingException {
		Folder rootFolder = null;
		Folder targetFolder = null;
		Folder inboxFolder = null;
		try {
			/*
			 * inbox
			 */
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_WRITE);
			/*
			 * message
			 */
			final Message message = inboxFolder.getMessage(id);
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
			inboxFolder.copyMessages(new Message[] { message }, targetFolder);
		} finally {
			if (null != inboxFolder) {
				if (inboxFolder.isOpen()) {
					inboxFolder.close();
				}
				inboxFolder = null;
			}
			if (null != targetFolder) {
				if (targetFolder.isOpen()) {
					targetFolder.close();
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
