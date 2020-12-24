package com.khubla.mailcradle.domain.expression;

import java.io.IOException;

import javax.mail.MessagingException;

import com.khubla.mailcradle.domain.Expression;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.IMAPMessageData;

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
