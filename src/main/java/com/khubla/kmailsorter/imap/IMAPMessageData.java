package com.khubla.kmailsorter.imap;

import java.io.*;
import java.util.*;

import javax.mail.*;
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
	private final String body;
	private final Date sendDate;
	private final Date receiveDate;
	private final int size;
	private final Flags flags;

	/**
	 * ctor
	 *
	 * @param message Javamail message
	 * @throws MessagingException exception
	 * @throws IOException exception
	 */
	public IMAPMessageData(IMAPMessage message) throws MessagingException, IOException {
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
		from = new String[fromAddresses.length];
		int i = 0;
		for (final Address address : fromAddresses) {
			if (address instanceof InternetAddress) {
				from[i++] = ((InternetAddress) address).getAddress();
			}
		}
		/*
		 * body
		 */
		body = getTextFromMessage(message);
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

	public String getBody() {
		return body;
	}

	public Flags getFlags() {
		return flags;
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

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			final MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		final int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			final BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			} else {
				result = result + "\n" + bodyPart.getContent();
			}
		}
		return result;
	}
}
