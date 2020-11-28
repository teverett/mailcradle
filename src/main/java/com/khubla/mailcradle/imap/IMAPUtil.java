package com.khubla.mailcradle.imap;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.progress.*;
import com.khubla.mailcradle.statefile.*;
import com.sun.mail.imap.*;

public class IMAPUtil {
	/**
	 * FWD
	 */
	private final static String FWD = "fwd: ";
	/**
	 * RE
	 */
	private final static String RE = "re: ";
	/**
	 * singleton
	 */
	private static IMAPUtil instance = null;
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(IMAPUtil.class);
	/**
	 * date keyfor persistent data
	 */
	private static final String DATE_KEY = "Date";

	public static IMAPUtil getInstance() throws MessagingException {
		if (null == instance) {
			instance = new IMAPUtil();
		}
		return instance;
	}

	/**
	 * state
	 */
	private final Statefile statefile = new Statefile();
	/*
	 * javamail session
	 */
	private final Session session;
	/**
	 * javamail store
	 */
	private final Store store;

	/**
	 * ctor
	 *
	 * @throws MessagingException MessagingException
	 */
	private IMAPUtil() throws MessagingException {
		final Properties properties = new Properties();
		/*
		 * SMTP
		 */
		properties.put("mail.smtps.host", MailCradleConfiguration.getInstance().getSmtpHost());
		properties.put("mail.smtps.starttls.enable", MailCradleConfiguration.getInstance().getSmtpTLS());
		properties.put("mail.smtps.port", MailCradleConfiguration.getInstance().getSmtpPort());
		properties.put("mail.smtps.auth", "true");
		/*
		 * IMAP
		 */
		properties.put("mail.imap.host", MailCradleConfiguration.getInstance().getImapPort());
		properties.put("mail.imap.starttls.enable", MailCradleConfiguration.getInstance().getImapTLS());
		properties.put("mail.imap.port", MailCradleConfiguration.getInstance().getImapPort());
		properties.put("mail.imap.auth", "true");
		if (true == MailCradleConfiguration.getInstance().getImapTLS()) {
			properties.put("mail.store.protocol", "imaps");
		} else {
			properties.put("mail.store.protocol", "imap");
		}
		session = Session.getDefaultInstance(properties, null);
		store = session.getStore();
		logger.info("Logging into " + MailCradleConfiguration.getInstance().getImapHost() + " as " + MailCradleConfiguration.getInstance().getImapUsername());
		store.connect(MailCradleConfiguration.getInstance().getImapHost(), MailCradleConfiguration.getInstance().getImapUsername(), MailCradleConfiguration.getInstance().getImapPassword());
	}

	private String DATKey(String folderName) {
		return MailCradleConfiguration.getInstance().getImapHost() + ":" + folderName + ":" + DATE_KEY;
	}

	/**
	 * get an IMAP message from an open IMAPFolder by uid
	 *
	 * @param inboxFolder open IMAP folder
	 * @param uid message uid
	 * @return IMAP message
	 * @throws MessagingException
	 */
	private IMAPMessage findMessageByUID(IMAPFolder inboxFolder, String uid) throws MessagingException {
		final SearchTerm searchTerm = new MessageIDTerm(uid);
		final Message[] messages = inboxFolder.search(searchTerm);
		if ((null != messages) && (messages.length == 1)) {
			if (messages[0] instanceof IMAPMessage) {
				return (IMAPMessage) messages[0];
			}
		}
		return null;
	}

	/**
	 * find all uids since a certain date
	 *
	 * @param inboxFolder folder
	 * @param date search date
	 * @return list of uids
	 * @throws MessagingException exception
	 */
	private Message[] findMessagsSinceDate(IMAPFolder inboxFolder, Date date) throws MessagingException {
		if (null != date) {
			final SearchTerm st = new ReceivedDateTerm(ComparisonTerm.EQ, date);
			return inboxFolder.search(st);
		} else {
			// all messages
			return inboxFolder.getMessages();
		}
	}

	/**
	 * flag or unflag a message
	 *
	 * @param uid message
	 * @param flag flag name
	 * @param set true to set or false to unset
	 * @throws MessagingException exception
	 */
	public void flagMessage(String folderName, String uid, String flagname, boolean set) throws MessagingException {
		IMAPFolder folder = null;
		try {
			if (set) {
				logger.info("Flagging to message " + uid + " with flag " + flagname);
			} else {
				logger.info("Unflagging to message " + uid + " from flag " + flagname);
			}
			/*
			 * inbox
			 */
			folder = getFolder(folderName);
			folder.open(Folder.READ_WRITE);
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(folder, uid);
			if (null != imapMessage) {
				if (set) {
					switch (flagname) {
						case "flagged":
							imapMessage.setFlag(Flags.Flag.FLAGGED, set);
							break;
						default:
							break;
					}
				}
			}
		} finally {
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close(true);
				}
				folder = null;
			}
		}
	}

	/**
	 * forward a message to an address
	 *
	 * @param message Message
	 * @param emailAddress emailAddress
	 * @throws MessagingException MessagingException
	 */
	public void forwardMessage(String folderName, String uid, String emailAddress) throws MessagingException {
		IMAPFolder folder = null;
		try {
			logger.info("Forwarding message " + uid + " to address " + emailAddress);
			/*
			 * inbox
			 */
			folder = getFolder(folderName);
			folder.open(Folder.READ_ONLY);
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(folder, uid);
			if (null != imapMessage) {
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
				forwardMessage.setSubject(FWD + imapMessage.getSubject());
				forwardMessage.setFrom(new InternetAddress(MailCradleConfiguration.getInstance().getSmtpFrom()));
				/*
				 * Create the message part
				 */
				final MimeBodyPart messageBodyPart = new MimeBodyPart();
				final Multipart multipart = new MimeMultipart();
				/*
				 * set content
				 */
				messageBodyPart.setContent(imapMessage, "message/rfc822");
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
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close(true);
				}
				folder = null;
			}
		}
	}

	/**
	 * get IMAP inbox
	 *
	 * @return Folder
	 * @throws MessagingException
	 */
	private IMAPFolder getFolder(String folderName) throws MessagingException {
		final IMAPFolder root = getRootFolder();
		return (IMAPFolder) root.getFolder(folderName);
	}

	private Date getLastDate(String folderName) {
		final String dd = statefile.get(DATKey(folderName));
		if (null != dd) {
			return new Date(Long.parseLong(dd));
		}
		return null;
	}

	/**
	 * get the message content of a message
	 *
	 * @param uid message id
	 * @return content
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Object getMessageContent(String folderName, String uid) throws MessagingException, IOException {
		IMAPFolder inboxFolder = null;
		try {
			logger.info("Getting message: " + uid);
			inboxFolder = getFolder(folderName);
			inboxFolder.open(Folder.READ_ONLY);
			final IMAPMessage imapMessage = findMessageByUID(inboxFolder, uid);
			if (null != imapMessage) {
				return imapMessage.getContent();
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
	 * get message count
	 *
	 * @return count
	 * @throws MessagingException MessagingException
	 */
	public int getMessageCount(String folderName) throws MessagingException {
		logger.info("Getting message count");
		IMAPFolder folder = null;
		try {
			folder = getFolder(folderName);
			folder.open(Folder.READ_ONLY);
			return folder.getMessageCount();
		} finally {
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close();
				}
				folder = null;
			}
		}
	}

	public IMAPMessageData getMessageData(String folderName, String uid) throws MessagingException, IOException {
		IMAPFolder folder = null;
		try {
			logger.info("Getting MessageData for message: " + uid);
			folder = getFolder(folderName);
			folder.open(Folder.READ_ONLY);
			final IMAPMessage imapMessage = findMessageByUID(folder, uid);
			if (null != imapMessage) {
				return new IMAPMessageData(folderName, imapMessage);
			}
			return null;
		} finally {
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close();
				}
				folder = null;
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
	public String[] getUIDs(String folderName) throws MessagingException {
		/*
		 * last time we read the uids
		 */
		final Date lastRead = getLastDate(folderName);
		if (null != lastRead) {
			logger.info("Getting uids since " + lastRead.toString());
		} else {
			logger.info("Getting all uids");
		}
		IMAPFolder folder = null;
		String[] ret = null;
		try {
			folder = getFolder(folderName);
			folder.open(Folder.READ_ONLY);
			final Message[] messages = findMessagsSinceDate(folder, lastRead);
			if (null != messages) {
				System.out.println("Found " + messages.length + " UIDs");
				ret = new String[messages.length];
				int i = 0;
				for (final Message message : messages) {
					ret[i++] = ((IMAPMessage) message).getMessageID();
				}
			}
			/*
			 * save the date
			 */
			setLastDate(folderName);
			/*
			 * done
			 */
			return ret;
		} finally {
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close();
				}
				folder = null;
			}
		}
	}

	/**
	 * Iterate messages
	 *
	 * @param folderName the folder name
	 * @param imapMessageCallback the callback
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void iterateMessages(String folderName, IMAPMessageCallback imapMessageCallback) throws MessagingException, IOException {
		final String[] uids = getUIDs(folderName);
		if (null != uids) {
			/*
			 * process all uids
			 */
			final ProgressCallback progressCallback = new DefaultProgressCallbackImpl(uids.length);
			System.out.println("Processing " + uids.length + " messages");
			if (uids.length > 0) {
				for (final String uid : uids) {
					final IMAPMessageData imapMessageData = getMessageData(folderName, uid);
					imapMessageCallback.message(imapMessageData);
					progressCallback.progress();
				}
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
	public void moveMessage(String folderName, String uid, String targetFolderName) throws MessagingException {
		IMAPFolder rootFolder = null;
		IMAPFolder targetFolder = null;
		IMAPFolder folder = null;
		try {
			logger.info("Moving message " + uid + " to folder " + targetFolderName);
			/*
			 * inbox
			 */
			folder = getFolder(folderName);
			folder.open(Folder.READ_WRITE);
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(folder, uid);
			if (null != imapMessage) {
				/*
				 * target
				 */
				rootFolder = getRootFolder();
				targetFolder = (IMAPFolder) rootFolder.getFolder(targetFolderName);
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
				folder.moveMessages(new Message[] { imapMessage }, targetFolder);
			}
		} finally {
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close(true);
				}
				folder = null;
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
	 * reply to a message
	 *
	 * @param message Message
	 * @param reply reply text
	 * @throws MessagingException MessagingException
	 */
	public void replyMessage(String folderName, String uid, String reply) throws MessagingException {
		IMAPFolder folder = null;
		try {
			logger.info("Replying to message " + uid + " with " + reply);
			/*
			 * inbox
			 */
			folder = getFolder(folderName);
			folder.open(Folder.READ_ONLY);
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(folder, uid);
			if (null != imapMessage) {
				/*
				 * create message
				 */
				final Message replyMessage = new MimeMessage(session);
				/*
				 * recipient
				 */
				final InternetAddress[] recipients = (InternetAddress[]) imapMessage.getFrom();
				/*
				 * Fill in header
				 */
				replyMessage.setRecipients(Message.RecipientType.TO, recipients);
				replyMessage.setSubject(RE + imapMessage.getSubject());
				replyMessage.setFrom(new InternetAddress(MailCradleConfiguration.getInstance().getSmtpFrom()));
				/*
				 * Create the message part
				 */
				final Multipart multipart = new MimeMultipart();
				/*
				 * set reply
				 */
				final MimeBodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(reply, "text/html");
				multipart.addBodyPart(messageBodyPart1);
				/*
				 * set content
				 */
				final MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				messageBodyPart2.setContent(imapMessage, "message/rfc822");
				multipart.addBodyPart(messageBodyPart2);
				/*
				 * Associate multi-part with message
				 */
				replyMessage.setContent(multipart);
				replyMessage.saveChanges();
				/*
				 * send
				 */
				smtpSend(replyMessage, recipients);
			}
		} finally {
			if (null != folder) {
				if (folder.isOpen()) {
					folder.close(true);
				}
				folder = null;
			}
		}
	}

	private void setLastDate(String folderName) {
		/*
		 * write state
		 */
		statefile.set(DATKey(folderName), Long.toString(System.currentTimeMillis()));
		statefile.write();
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
			transport.connect(MailCradleConfiguration.getInstance().getSmtpUsername(), MailCradleConfiguration.getInstance().getImapPassword());
			transport.sendMessage(message, recipients);
		} finally {
			if (transport.isConnected()) {
				transport.close();
			}
		}
	}
}
