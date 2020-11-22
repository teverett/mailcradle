package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;

import com.khubla.kmailsorter.domain.*;

public class BodyTerm extends Term {
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

	@Override
	public String[] resolve(Message message) throws MessagingException, IOException {
		return new String[] { getTextFromMessage(message) };
	}
}
