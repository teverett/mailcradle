package com.khubla.mailcradle.imap;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.Message.*;
import javax.mail.internet.*;

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
	}

	public String[] getBcc() {
		return bcc;
	}

	public String getBody() throws MessagingException, IOException {
		if (null != body) {
			/*
			 * get the content
			 */
			final Object content = FolderFactory.getInstance().getFolder(folderName).getMessageContent(uid);
			/*
			 * body
			 */
			body = content.toString();
		}
		return body;
	}

	public String[] getCc() {
		return cc;
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
	/*
	 * private String getTextFromMessage(Object content, String mimeType) throws MessagingException,
	 * IOException { String result = ""; if (message.isMimeType("text/plain")) { result =
	 * message.getContent().toString(); } else if (message.isMimeType("multipart/*")) { final
	 * MimeMultipart mimeMultipart = (MimeMultipart) message.getContent(); result =
	 * getTextFromMimeMultipart(mimeMultipart); } return result; } private String
	 * getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
	 * String result = ""; final int count = mimeMultipart.getCount(); for (int i = 0; i < count;
	 * i++) { final BodyPart bodyPart = mimeMultipart.getBodyPart(i); if (bodyPart.getContent()
	 * instanceof MimeMultipart) { result = result + getTextFromMimeMultipart((MimeMultipart)
	 * bodyPart.getContent()); } else { result = result + "\n" + bodyPart.getContent(); } } return
	 * result; }
	 */

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
