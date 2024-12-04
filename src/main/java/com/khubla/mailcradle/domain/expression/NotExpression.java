package com.khubla.mailcradle.domain.expression;

import com.khubla.mailcradle.domain.Expression;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.IMAPMessageData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class NotExpression extends Expression {
   private final Expression expression;

   public NotExpression(Expression expression) {
      super();
      this.expression = expression;
   }

   @Override
   public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException, IOException {
      return !(expression.evaluate(messageData, mailsort, expressionSteps));
   }

   public Expression getExpression() {
      return expression;
   }
}
