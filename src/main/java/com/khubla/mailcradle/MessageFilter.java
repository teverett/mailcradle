package com.khubla.mailcradle;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class MessageFilter implements IMAPMessageCallback {
	/**
	 * the mailsort data
	 */
	private final Mailcradle mailsort;

	public MessageFilter(Mailcradle mailsort) {
		super();
		this.mailsort = mailsort;
	}

	@Override
	public void message(IMAPMessageData imapMessageData) throws MessagingException, IOException {
		/*
		 * process message
		 */
		if (null != imapMessageData) {
			for (final Filter filter : mailsort.getFilters()) {
				if (false == filter.execute(imapMessageData, mailsort)) {
					break;
				}
			}
		}
	}
}
