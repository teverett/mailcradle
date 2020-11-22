package com.khubla.kmailsorter.domain.condition;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public class TermCondition extends Condition {
	/**
	 * value to compare to
	 */
	String value;
	/**
	 * relation to use
	 */
	TermRelation termRelation;

	@Override
	public boolean evaluate(Message message) throws MessagingException {
		throw new RuntimeException("Not Implemented");
	}

	public TermRelation getTermRelation() {
		return termRelation;
	}

	public String getValue() {
		return value;
	}

	public void setTermRelation(TermRelation termRelation) {
		this.termRelation = termRelation;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
