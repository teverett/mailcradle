package com.khubla.kmailsorter.domain;

import javax.mail.*;

import com.khubla.kmailsorter.imap.*;

public abstract class Action {
	/**
	 * execute the action on a message
	 *
	 * @param message Message to execute with
	 * @throws MessagingException oops
	 */
	abstract public void execute(IMAPMessageData messageData, Mailsort mailsort) throws MessagingException;
}
