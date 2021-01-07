package com.khubla.mailcradle.imap;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.Message.*;
import javax.mail.internet.*;

import org.apache.logging.log4j.*;

import com.sun.mail.imap.*;

/**
 * local cached message data
 *
 * @author tom
 */
public class IMAPMessageData {
	public static class HeaderEntry {
		public String key;
		public String value;
	}

	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(IMAPMessageData.class);
	private final String subject;
	private final String[] from;
	private final List<HeaderEntry> headers = new ArrayList<HeaderEntry>();
	private final String id;
	private String body = null;
	private final Date sendDate;
	private final Date receiveDate;
	private final int size;
	private final Flags flags;
	private final String folderName;
	private final String[] to;
	private final String[] cc;
	private final String[] bcc;
	private final long uid;
	private final String contentType;

	/**
	 * ctor
	 *
	 * @param message Javamail message
	 * @throws MessagingException exception
	 * @throws IOException exception
	 */
	public IMAPMessageData(String folderName, long uid, IMAPMessage message) throws MessagingException, IOException {
		/*
		 * folderName
		 */
		this.folderName = folderName;
		/*
		 * uid
		 */
		this.uid = uid;
		/*
		 * id
		 */
		id = message.getMessageID();
		/*
		 * subject
		 */
		subject = message.getSubject();
		/*
		 * from
		 */
		final Address[] fromAddresses = message.getFrom();
		from = toString(fromAddresses);
		/*
		 * to
		 */
		final Address[] toAddresses = message.getRecipients(RecipientType.TO);
		to = toString(toAddresses);
		/*
		 * cc
		 */
		final Address[] ccAddresses = message.getRecipients(RecipientType.CC);
		cc = toString(ccAddresses);
		/*
		 * bcc
		 */
		final Address[] bccAddresses = message.getRecipients(RecipientType.BCC);
		bcc = toString(bccAddresses);
		/*
		 * headers
		 */
		final Enumeration<Header> e = message.getAllHeaders();
		while (e.hasMoreElements()) {
			final Header header = e.nextElement();
			final HeaderEntry headerEntry = new HeaderEntry();
			headerEntry.key = header.getName();
			headerEntry.value = header.getValue();
			headers.add(headerEntry);
		}
		/*
		 * dates
		 */
		sendDate = message.getSentDate();
		receiveDate = message.getReceivedDate();
		/**
		 * size
		 */
		size = message.getSize();
		/*
		 * flags
		 */
		flags = message.getFlags();
		/*
		 * content type
		 */
		contentType = message.getContentType();
	}

	public String[] getBcc() {
		return bcc;
	}

	/*
	 * get the message body <p>reading the message body has the effect of marking the message read.
	 * That is a bit inconvenient, so we read the flag and reset it to what it was</p>
	 */
	public String getBody() throws MessagingException, IOException {
		if (null == body) {
			/*
			 * get read status
			 */
			final boolean isRead = FolderFactory.getInstance().getFolder(folderName).getFlag(uid, javax.mail.Flags.Flag.SEEN);
			/*
			 * get the content
			 */
			final Object content = FolderFactory.getInstance().getFolder(folderName).getMessageContent(uid);
			if (null != content) {
				if (contentType.contains("text/plain")) {
					body = content.toString();
				} else if (contentType.contains("multipart")) {
					body = getTextFromMimeMultipart((MimeMultipart) content);
				} else {
					// unknown content type
					body = content.toString();
				}
			}
			/*
			 * reset flag
			 */
			FolderFactory.getInstance().getFolder(folderName).setFlag(uid, javax.mail.Flags.Flag.SEEN, isRead);
		}
		return body;
	}

	public String[] getCc() {
		return cc;
	}

	public String getContentType() {
		return contentType;
	}

	public Flags getFlags() {
		return flags;
	}

	public String getFolderName() {
		return folderName;
	}

	public String[] getFrom() {
		return from;
	}

	public String[] getHeader(String headerName) {
		final List<String> values = new ArrayList<String>();
		for (final HeaderEntry headerEntry : headers) {
			if (headerEntry.key.compareTo(headerName) == 0) {
				values.add(headerEntry.value);
			}
		}
		final String[] ret = new String[values.size()];
		values.toArray(ret);
		return ret;
	}

	public List<HeaderEntry> getHeaders() {
		return headers;
	}

	public String getId() {
		return id;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public int getSize() {
		return size;
	}

	public String getSubject() {
		return subject;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		try {
			String result = "";
			final int count = mimeMultipart.getCount();
			for (int i = 0; i < count; i++) {
				final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
				Object o = null;
				// this can fail for example with unsupported encoding
				o = bodyPart.getContent();
				if (null != o) {
					if (bodyPart.getContent() instanceof MimeMultipart) {
						result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
					} else {
						result = result + "\n" + bodyPart.getContent();
					}
				}
			}
			return result;
		} catch (final Exception e) {
			logger.error("Exception gettting message content for message " + uid, e);
			return null;
		}
	}

	public String[] getTo() {
		return to;
	}

	public long getUid() {
		return uid;
	}

	private String[] toString(Address[] addresses) {
		if (null != addresses) {
			final String[] ret = new String[addresses.length];
			int i = 0;
			for (final Address address : addresses) {
				if (address instanceof InternetAddress) {
					ret[i++] = ((InternetAddress) address).getAddress();
				}
			}
			return ret;
		}
		return null;
	}
}
