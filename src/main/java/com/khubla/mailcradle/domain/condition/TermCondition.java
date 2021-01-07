package com.khubla.mailcradle.domain.condition;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

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
	public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
		final String[] strs = getTerm().resolve(messageData);
		if (null != strs) {
			switch (termRelation) {
				case is:
					for (final String str : strs) {
						if (null != str) {
							if (value.toLowerCase().compareTo(str.toLowerCase()) == 0) {
								return true;
							}
						}
					}
					return false;
				case contains:
					for (final String str : strs) {
						if (null != str) {
							if (value.toLowerCase().contains(str.toLowerCase())) {
								return true;
							}
						}
					}
					return false;
				default:
					return false;
			}
		}
		return false;
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
