package com.khubla.mailcradle.domain.condition;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.domain.action.MoveAction;
import com.khubla.mailcradle.imap.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TermCondition extends Condition {
	/**
	 * value to compare to
	 */
	private String value;
	/**
	 * relation to use
	 */
	private TermRelation termRelation;
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(TermCondition.class);

	@Override
	public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
		final String[] strs = getTerm().resolve(messageData);
		if (null != strs) {
			switch (termRelation) {
				case is:
					for (final String str : strs) {
						if (null != str) {
							if (value.toLowerCase().compareTo(str.toLowerCase()) == 0) {
								System.out.print("Term 'is' Condition  '"+str+"' matches '"+value+"'");
								logger.info("Term 'is' Condition  '"+str+"' matches '"+value+"'");
								return true;
							}
						}
					}
					return false;
				case contains:
					for (final String str : strs) {
						if (null != str) {
							if (value.toLowerCase().contains(str.toLowerCase())) {
								System.out.print("Term 'contains' Condition  '"+str+"' matches '"+value+"'");
								logger.info("Term 'is' Condition  '"+str+"' matches '"+value+"'");
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
