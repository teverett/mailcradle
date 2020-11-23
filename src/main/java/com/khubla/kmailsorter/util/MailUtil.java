package com.khubla.kmailsorter.util;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.*;
import com.sun.mail.imap.*;

public class MailUtil {
	/**
	 * FWD
	 */
	private final static String FWD = "fwd: ";
	/**
	 * singleton
	 */
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

	private final Session session;
	private final Store store;

	/**
	 * ctor
	 *
	 * @throws MessagingException MessagingException
	 */
	private MailUtil() throws MessagingException {
		final Properties properties = new Properties();
		properties.put("mail.smtps.host", KMailSorterConfiguration.getInstance().getSmtpHost());
		properties.put("mail.smtps.starttls.enable", "true");
		properties.put("mail.smtps.auth", "true");
		properties.put("mail.smtps.port", KMailSorterConfiguration.getInstance().getSmtpPort());
		session = Session.getDefaultInstance(properties, null);
		store = session.getStore("imaps");
		logger.info("Logging into " + KMailSorterConfiguration.getInstance().getImapHost() + " as " + KMailSorterConfiguration.getInstance().getImapUsername());
		store.connect(KMailSorterConfiguration.getInstance().getImapHost(), KMailSorterConfiguration.getInstance().getImapUsername(), KMailSorterConfiguration.getInstance().getImapPassword());
	}

	/**
	 * forward a message to an address
	 *
	 * @param message Message
	 * @param emailAddress emailAddress
	 * @throws MessagingException MessagingException
	 */
	public void forwardMessage(String uid, String emailAddress) throws MessagingException {
		IMAPFolder inboxFolder = null;
		try {
			logger.info("Forwarding message " + uid + " to address " + emailAddress);
			/*
			 * inbox
			 */
			inboxFolder = getInbox();
			inboxFolder.open(Folder.READ_ONLY);
			/*
			 * message
			 */
			final SearchTerm searchTerm = new MessageIDTerm(uid);
			final Message[] messages = inboxFolder.search(searchTerm);
			if ((null != messages) && (messages.length == 1)) {
				/*
				 * create message
				 */
				final Message forwardMessage = new MimeMessage(session);
				/*
				 * recipient
				 */
				final InternetAddress[] recipients = InternetAddress.parse(emailAddress);
				/*
				 * Fill in header
				 */
				forwardMessage.setRecipients(Message.RecipientType.TO, recipients);
				forwardMessage.setSubject(FWD + messages[0].getSubject());
				forwardMessage.setFrom(new InternetAddress(KMailSorterConfiguration.getInstance().getSmtpFrom()));
				/*
				 * Create the message part
				 */
				final MimeBodyPart messageBodyPart = new MimeBodyPart();
				final Multipart multipart = new MimeMultipart();
				/*
				 * set content
				 */
				messageBodyPart.setContent(messages[0], "message/rfc822");
				multipart.addBodyPart(messageBodyPart);
				/*
				 * Associate multi-part with message
				 */
				forwardMessage.setContent(multipart);
				forwardMessage.saveChanges();
				/*
				 * send
				 */
				smtpSend(forwardMessage, recipients);
			}
		} finally {
			if (null != inboxFolder) {
				if (inboxFolder.isOpen()) {
					inboxFolder.close(true);
				}
				inboxFolder = null;
			}
		}
	}

	/**
	 * get IMAP inbox
	 *
	 * @return Folder
	 * @throws MessagingException
	 */
	private IMAPFolder getInbox() throws MessagingException {
		final IMAPFolder root = getRootFolder();
		return (IMAPFolder) root.getFolder(KMailSorterConfiguration.getInstance().getImapFolder());
	}

	/**
	 * get message count
	 *
	 * @return count
	 * @throws MessagingException MessagingException
	 */
	public int getMessageCount() throws MessagingException {
		logger.info("Getting message count");
		IMAPFolder inboxFolder = null;
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
		IMAPFolder inboxFolder = null;
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
	private IMAPFolder getRootFolder() throws MessagingException {
		return (IMAPFolder) store.getDefaultFolder();
	}

	/**
	 * get all message UIDs
	 *
	 * @return array of UIDs
	 * @throws MessagingException MessagingException
	 */
	public String[] getUIDs() throws MessagingException {
		logger.info("Getting uids");
		IMAPFolder inboxFolder = null;
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
		IMAPFolder rootFolder = null;
		IMAPFolder targetFolder = null;
		IMAPFolder inboxFolder = null;
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
				targetFolder = (IMAPFolder) rootFolder.getFolder(folderName);
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
				 * move message
				 */
				inboxFolder.moveMessages(new Message[] { messages[0] }, targetFolder);
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

	/**
	 * do an SMTP send
	 *
	 * @param message message to send
	 * @param recipients recipients to send to
	 * @throws MessagingException exception
	 */
	private void smtpSend(Message message, InternetAddress[] recipients) throws MessagingException {
		/*
		 * send
		 */
		final Transport transport = session.getTransport("smtps");
		try {
			transport.connect(KMailSorterConfiguration.getInstance().getSmtpUsername(), KMailSorterConfiguration.getInstance().getImapPassword());
			transport.sendMessage(message, recipients);
		} finally {
			if (transport.isConnected()) {
				transport.close();
			}
		}
	}
}
