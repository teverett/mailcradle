package com.khubla.kmailsorter.domain;

import javax.mail.*;

public abstract class Action {
	/**
	 * execute the action on a message
	 *
	 * @param message Message to execute with
	 * @throws MessagingException oops
	 */
	abstract public void execute(Message message) throws MessagingException;
}
