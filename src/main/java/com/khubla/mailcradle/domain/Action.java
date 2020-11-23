package com.khubla.mailcradle.domain;

import javax.mail.*;

import com.khubla.mailcradle.imap.*;

public abstract class Action {
	/**
	 * execute the action on a message
	 *
	 * @param message Message to execute with
	 * @throws MessagingException oops
	 */
	abstract public void execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException;
}
