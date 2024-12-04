package com.khubla.mailcradle.domain.expression;

import com.khubla.mailcradle.domain.Condition;
import com.khubla.mailcradle.domain.Expression;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.IMAPMessageData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ConditionExpression extends Expression {
   private final Condition condition;

   public ConditionExpression(Condition condition) {
      super();
      this.condition = condition;
   }

   @Override
   public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException, IOException {
      return condition.evaluate(messageData, mailsort, expressionSteps);
   }

   public Condition getCondition() {
      return condition;
   }
}
