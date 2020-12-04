package com.khubla.mailcradle.domain;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.imap.*;

public interface Evaluateable {
	/**
	 * evaluate the expression or condition on a message
	 *
	 * @param message Message to evaluate on
	 * @throws MessagingException oops
	 */
	boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException;
}
