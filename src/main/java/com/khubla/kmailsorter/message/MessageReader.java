package com.khubla.kmailsorter.message;

import javax.mail.*;

import com.khubla.kmailsorter.command.*;

public class MessageReader {
	private final Message message;

	public MessageReader(Message message) {
		super();
		this.message = message;
	}

	public String getMessageFieldValue(TestType testType, String fieldName) throws MessagingException {
		switch (testType) {
			case header:
				/*
				 * get a field from the header
				 */
				final String[] header = message.getHeader(fieldName);
				if (null != header) {
					return header[0];
				}
				return null;
			case allof:
				return null;
			case anyof:
				return null;
			case exists:
				return null;
			case address:
				return null;
			case false_:
				return null;
			case not:
				return null;
			case true_:
				return null;
			case size:
				return Integer.toString(message.getSize());
			case envelope:
				return null;
			default:
				return null;
		}
	}
}
