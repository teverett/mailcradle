package com.khubla.mailcradle.imap;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.progress.*;
import com.sun.mail.imap.*;

/**
 * a wrapper for an IMAPFolder
 * 
 * @author tom
 */
public class IMAPFolderUtil implements Closeable {
	/**
	 * FWD
	 */
	private final static String FWD = "fwd: ";
	/**
	 * RE
	 */
	private final static String RE = "re: ";
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(IMAPFolderUtil.class);
	/*
	 * javamail session
	 */
	private final Session session;
	/**
	 * javamail store
	 */
	private final Store store;
	/**
	 * foldername
	 */
	private final String folderName;
	/**
	 * folder
	 */
	private IMAPFolder thisFolder = null;

	/**
	 * ctor
	 *
	 * @throws MessagingException MessagingException
	 */
	public IMAPFolderUtil(String folderName) throws MessagingException {
		this.folderName = folderName;
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

	@Override
	public void close() throws IOException {
		if (null != thisFolder) {
			if (thisFolder.isOpen()) {
				try {
					thisFolder.close(true);
				} catch (final Exception e) {
					logger.error(e);
				}
			}
			thisFolder = null;
		}
	}

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
	public void flagMessage(long uid, String flagname, boolean set) throws MessagingException {
		IMAPFolder imapFolder = null;
		if (set) {
			logger.info("Flagging to message " + uid + " in folder " + folderName + " with flag " + flagname);
		} else {
			logger.info("Unflagging to message " + uid + " in folder " + folderName + " from flag " + flagname);
		}
		/*
		 * inbox
		 */
		imapFolder = getFolder();
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
	}

	/**
	 * forward a message to an address
	 *
	 * @param message Message
	 * @param emailAddress emailAddress
	 * @throws MessagingException MessagingException
	 */
	public void forwardMessage(long uid, String emailAddress) throws MessagingException {
		IMAPFolder imapFolder = null;
		logger.info("Forwarding message " + uid + " in folder " + folderName + " to address " + emailAddress);
		/*
		 * inbox
		 */
		imapFolder = getFolder();
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
	}

	/**
	 * get IMAP inbox
	 *
	 * @return Folder
	 * @throws MessagingException
	 */
	private IMAPFolder getFolder() throws MessagingException {
		if (null == thisFolder) {
			final IMAPFolder root = getRootFolder();
			thisFolder = (IMAPFolder) root.getFolder(folderName);
			if (null != thisFolder) {
				if (false == thisFolder.isOpen()) {
					thisFolder.open(Folder.READ_WRITE);
				}
			}
		}
		return thisFolder;
	}

	public String getFolderName() {
		return folderName;
	}

	/**
	 * get the message content of a message
	 *
	 * @param uid message id
	 * @return content
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Object getMessageContent(long uid) throws MessagingException, IOException {
		logger.info("Getting message content for message: " + uid + " in folder " + folderName);
		final IMAPFolder folder = getFolder();
		final IMAPMessage imapMessage = findMessageByUID(folder, uid);
		if (null != imapMessage) {
			return imapMessage.getContent();
		}
		return null;
	}

	/**
	 * get message count
	 *
	 * @return count
	 * @throws MessagingException MessagingException
	 */
	public int getMessageCount() throws MessagingException {
		logger.info("Getting message count for folder: " + folderName);
		IMAPFolder imapFolder = null;
		imapFolder = getFolder();
		imapFolder.open(Folder.READ_ONLY);
		return imapFolder.getMessageCount();
	}

	public IMAPMessageData getMessageData(IMAPMessage imapMessage) throws MessagingException, IOException {
		if (null != imapMessage) {
			return new IMAPMessageData(folderName, getFolder().getUID(imapMessage), imapMessage);
		}
		return null;
	}

	public IMAPMessageData getMessageData(long uid) throws MessagingException, IOException {
		IMAPFolder imapFolder = null;
		logger.info("Getting MessageData for message: " + uid + " in folder " + folderName);
		imapFolder = getFolder();
		final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
		if (null != imapMessage) {
			return getMessageData(imapMessage);
		}
		return null;
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
		IMAPFolder imapFolder = null;
		String[] ret = null;
		imapFolder = getFolder();
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
	}

	/**
	 * idle the thread waiting for events
	 *
	 * @param imapFolder folder for events
	 * @param imapNotification notification callback
	 * @throws MessagingException
	 */
	public void idle(IMAPMessageCallback imapMessageCallback) throws MessagingException {
		IMAPFolder imapFolder = null;
		Thread keepAliveThread = null;
		try {
			/*
			 * folder
			 */
			imapFolder = getFolder();
			/*
			 * Spin the keepAliveThread
			 */
			keepAliveThread = new Thread(new IMAPKeepaliveRunnable(imapFolder));
			keepAliveThread.start();
			/*
			 * listener
			 */
			imapFolder.addMessageCountListener(new IMAPMessageCountListener(this, imapMessageCallback));
			/*
			 * spin on idle
			 */
			while (!Thread.interrupted()) {
				try {
					imapFolder.idle(true);
				} catch (final Exception e) {
					logger.error("Exception during idle", e);
				}
			}
		} finally {
			/*
			 * Shutdown keep alive thread
			 */
			if (keepAliveThread.isAlive()) {
				keepAliveThread.interrupt();
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
	public void iterateMessages(IMAPMessageCallback imapMessageCallback) throws MessagingException, IOException {
		IMAPFolder imapFolder = null;
		/*
		 * folder
		 */
		imapFolder = getFolder();
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
	}

	public void iterateMessages(Message[] messages, IMAPMessageCallback imapMessageCallback) throws MessagingException, IOException {
		for (final Message message : messages) {
			IMAPFolder imapFolder = null;
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
		}
	}

	/**
	 * move a message to a new folder
	 *
	 * @param message Message
	 * @param folderName name of folder
	 * @throws MessagingException MessagingException
	 */
	public void moveMessage(long uid, String targetFolderName) throws MessagingException {
		IMAPFolder imapFolder = null;
		IMAPFolder rootFolder = null;
		IMAPFolder targetFolder = null;
		try {
			logger.info("Moving message " + uid + " in folder " + folderName + " to folder " + targetFolderName);
			/*
			 * inbox
			 */
			imapFolder = getFolder();
			/*
			 * message
			 */
			final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
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
					targetFolder.create(Folder.HOLDS_MESSAGES | Folder.HOLDS_FOLDERS);
					targetFolder.setSubscribed(true);
				}
				/*
				 * open target
				 */
				targetFolder.open(Folder.READ_WRITE);
				/*
				 * move message
				 */
				imapFolder.moveMessages(new Message[] { imapMessage }, targetFolder);
			}
		} finally {
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
	public void replyMessage(long uid, String reply) throws MessagingException {
		logger.info("Replying to message " + uid + " in folder " + folderName + " with " + reply);
		/*
		 * inbox
		 */
		final IMAPFolder imapFolder = getFolder();
		/*
		 * message
		 */
		final IMAPMessage imapMessage = findMessageByUID(imapFolder, uid);
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
