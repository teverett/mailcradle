package com.khubla.mailcradle.domain.expression;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class ParentheticalExpression extends Expression {
	private final Expression expression;

	public ParentheticalExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	@Override
	public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
		return expression.evaluate(messageData, mailsort);
	}

	public Expression getExpression() {
		return expression;
	}
}
