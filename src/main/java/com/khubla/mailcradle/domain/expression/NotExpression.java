package com.khubla.mailcradle.domain.expression;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class NotExpression extends Expression {
	private final Expression expression;

	public NotExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	@Override
	public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
		return !(expression.evaluate(messageData, mailsort));
	}

	public Expression getExpression() {
		return expression;
	}
}
