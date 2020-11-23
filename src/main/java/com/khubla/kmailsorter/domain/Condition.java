package com.khubla.kmailsorter.domain;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.util.*;

public abstract class Condition {
	private Term term;

	/**
	 * evaluate the condition on a message
	 *
	 * @param message Message to evaluate on
	 * @throws MessagingException oops
	 */
	abstract public boolean evaluate(MessageData messageData, Mailsort mailsort) throws MessagingException, IOException;

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
