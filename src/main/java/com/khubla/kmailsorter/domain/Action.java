package com.khubla.kmailsorter.domain;

import javax.mail.*;

import com.khubla.kmailsorter.util.*;

public abstract class Action {
	/**
	 * execute the action on a message
	 *
	 * @param message Message to execute with
	 * @throws MessagingException oops
	 */
	abstract public void execute(MessageData messageData, Mailsort mailsort) throws MessagingException;
}
