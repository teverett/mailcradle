package com.khubla.mailcradle.imap;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.progress.*;
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

	public static IMAPUtil getInstance() throws MessagingException {
		if (null == instance) {
			instance = new IMAPUtil();
		}
		return instance;
	}

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

	/**
	 * get an IMAP message from an open IMAPFolder by uid
	 *
	 * @param inboxFolder open IMAP folder
	 * @param uid message uid
	 * @return IMAP message
	 * @throws MessagingException
	 */
	// private IMAPMessage findMessageByID(IMAPFolder folder, String id) throws MessagingException {
	// final SearchTerm searchTerm = new MessageIDTerm(id);
	// final Message[] messages = folder.search(searchTerm);
	// if ((null != messages) && (messages.length == 1)) {
	// if (messages[0] instanceof IMAPMessage) {
	// return (IMAPMessage) messages[0];
	// }
	// }
	// return null;
	// }
	private IMAPMessage findMessageByUID(IMAPFolder folder, long uid) throws MessagingException {
		final Message message = folder.getMessageByUID(uid);
		if (message instanceof IMAPMessage) {
			return (IMAPMessage) message;
		}
		return null;
	}

	/**
	 * flag or unflag a message
	 *
	 * @param uid message
	 * @param flag flag name
	 * @param set true to set or false to unset
	 * @throws MessagingException exception
	 */
	public void flagMessage(String folderName, long uid, String flagname, boolean set) throws MessagingException {
		IMAPFolder imapFolder = null;
		try {
			if (set) {
				logger.info("Flagging to message " + uid + " in folder " + folderName + " with flag " + flagname);
			} else {
				logger.info("Unflagging to message " + uid + " in folder " + folderName + " from flag " + flagname);
			}
			/*
			 * inbox
			 */
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_WRITE);
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
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
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close(true);
				}
				imapFolder = null;
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
	public void forwardMessage(String folderName, long uid, String emailAddress) throws MessagingException {
		IMAPFolder imapFolder = null;
		try {
			logger.info("Forwarding message " + uid + " in folder " + folderName + " to address " + emailAddress);
			/*
			 * inbox
			 */
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_ONLY);
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
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
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close(true);
				}
				imapFolder = null;
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

	/**
	 * get the message content of a message
	 *
	 * @param uid message id
	 * @return content
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Object getMessageContent(String folderName, long uid) throws MessagingException, IOException {
		IMAPFolder imapFolder = null;
		try {
			logger.info("Getting message content for message: " + uid + " in folder " + folderName);
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_ONLY);
			final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
			if (null != imapMessage) {
				return imapMessage.getContent();
			}
			return null;
		} finally {
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close();
				}
				imapFolder = null;
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
		logger.info("Getting message count for folder: " + folderName);
		IMAPFolder imapFolder = null;
		try {
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_ONLY);
			return imapFolder.getMessageCount();
		} finally {
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close();
				}
				imapFolder = null;
			}
		}
	}

	public IMAPMessageData getMessageData(String folderName, long uid) throws MessagingException, IOException {
		IMAPFolder imapFolder = null;
		try {
			logger.info("Getting MessageData for message: " + uid + " in folder " + folderName);
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_ONLY);
			final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
			if (null != imapMessage) {
				return new IMAPMessageData(folderName, imapFolder.getUID(imapMessage), imapMessage);
			}
			return null;
		} finally {
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close();
				}
				imapFolder = null;
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
		IMAPFolder imapFolder = null;
		String[] ret = null;
		try {
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_ONLY);
			final Message[] messages = imapFolder.getMessages();
			if (null != messages) {
				System.out.println("Found " + messages.length + " UIDs");
				ret = new String[messages.length];
				int i = 0;
				for (final Message message : messages) {
					ret[i++] = ((IMAPMessage) message).getMessageID();
				}
			}
			/*
			 * done
			 */
			return ret;
		} finally {
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close();
				}
				imapFolder = null;
			}
		}
	}

	/**
	 * idle the thread waiting for events
	 *
	 * @param imapFolder folder for events
	 * @param imapNotification notification callback
	 * @throws MessagingException
	 */
	public void idle(String folderName, IMAPEventNotification imapEventNotification) throws MessagingException {
		IMAPFolder imapFolder = null;
		Thread keepAliveThread = null;
		try {
			/*
			 * folder
			 */
			imapFolder = getFolder(folderName);
			/*
			 * Spin the keepAliveThread
			 */
			keepAliveThread = new Thread(new IMAPKeepaliveRunnable(imapFolder));
			keepAliveThread.start();
			/*
			 * listener
			 */
			imapFolder.addMessageCountListener(new MessageCountListener() {
				@Override
				public void messagesAdded(MessageCountEvent messageCountEvent) {
					try {
						imapEventNotification.event(messageCountEvent.getMessages());
					} catch (MessagingException | IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void messagesRemoved(MessageCountEvent messageCountEvent) {
				}
			});
			/*
			 * spin on idle
			 */
			while (!Thread.interrupted()) {
				try {
					if (false == imapFolder.isOpen()) {
						imapFolder.open(Folder.READ_ONLY);
					}
					imapFolder.idle(true);
				} catch (final Exception e) {
					logger.error("Exception during idle", e);
				}
			}
		} finally {
			/*
			 * done w folder
			 */
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close(true);
				}
				imapFolder = null;
			}
			/*
			 * Shutdown keep alive thread
			 */
			if (keepAliveThread.isAlive()) {
				keepAliveThread.interrupt();
			}
		}
	}

	public void iterateMessages(Message[] messages, IMAPMessageCallback imapMessageCallback) throws MessagingException, IOException {
		for (final Message message : messages) {
			IMAPFolder imapFolder = null;
			try {
				/*
				 * folder
				 */
				imapFolder = (IMAPFolder) message.getFolder();
				if (null != imapFolder) {
					if (false == imapFolder.isOpen()) {
						imapFolder.open(Folder.READ_ONLY);
					}
					/*
					 * get messages
					 */
					if (message instanceof IMAPMessage) {
						final IMAPMessageData imapMessageData = new IMAPMessageData(imapFolder.getFullName(), imapFolder.getUID(message), (IMAPMessage) message);
						imapMessageCallback.message(imapMessageData);
					}
				}
			} finally {
				if (null != imapFolder) {
					if (imapFolder.isOpen()) {
						imapFolder.close(true);
					}
					imapFolder = null;
				}
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
		IMAPFolder imapFolder = null;
		try {
			/*
			 * folder
			 */
			imapFolder = getFolder(folderName);
			imapFolder.open(Folder.READ_ONLY);
			/*
			 * get messages
			 */
			final Message[] messages = imapFolder.getMessages();
			if (null != messages) {
				final ProgressCallback progressCallback = new DefaultProgressCallbackImpl(messages.length);
				System.out.println("Processing " + messages.length + " messages");
				for (final Message message : messages) {
					if (message instanceof IMAPMessage) {
						final IMAPMessageData imapMessageData = new IMAPMessageData(folderName, imapFolder.getUID(message), (IMAPMessage) message);
						imapMessageCallback.message(imapMessageData);
						progressCallback.progress();
					}
				}
			}
		} finally {
			if (null != imapFolder) {
				if (imapFolder.isOpen()) {
					imapFolder.close(true);
				}
				imapFolder = null;
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
	public void moveMessage(String folderName, long uid, String targetFolderName) throws MessagingException {
		IMAPFolder rootFolder = null;
		IMAPFolder targetFolder = null;
		IMAPFolder folder = null;
		try {
			logger.info("Moving message " + uid + " in folder " + folderName + " to folder " + targetFolderName);
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
	public void replyMessage(String folderName, long uid, String reply) throws MessagingException {
		IMAPFolder folder = null;
		try {
			logger.info("Replying to message " + uid + " in folder " + folderName + " with " + reply);
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
