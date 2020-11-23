package com.khubla.kmailsorter.domain.condition;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.util.*;

public class TermCondition extends Condition {
	/**
	 * value to compare to
	 */
	private String value;
	/**
	 * relation to use
	 */
	private TermRelation termRelation;

	@Override
	public boolean evaluate(MessageData messageData, Mailsort mailsort) throws MessagingException, IOException {
		final String[] strs = getTerm().resolve(messageData);
		switch (termRelation) {
			case is:
				for (final String str : strs) {
					if (value.compareTo(str) == 0) {
						return true;
					}
				}
				return false;
			case contains:
				for (final String str : strs) {
					if (value.contains(str)) {
						return true;
					}
				}
				return false;
			default:
				return false;
		}
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
