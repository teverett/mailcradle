package com.khubla.mailcradle.domain.expression;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class ConditionExpression extends Expression {
	private final Condition condition;

	public ConditionExpression(Condition condition) {
		super();
		this.condition = condition;
	}

	@Override
	public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
		return condition.evaluate(messageData, mailsort);
	}

	public Condition getCondition() {
		return condition;
	}
}
