package com.khubla.kmailsorter.domain;

import javax.mail.*;

public abstract class Condition {
	private Term term;

	/**
	 * evaluate the condition on a message
	 *
	 * @param message Message to evaluate on
	 * @throws MessagingException oops
	 */
	abstract public boolean evaluate(Message message) throws MessagingException;

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
