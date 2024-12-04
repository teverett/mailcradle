package com.khubla.mailcradle.domain.expression;

import com.khubla.mailcradle.domain.Expression;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.IMAPMessageData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class LogicalExpression extends Expression {
   private final Expression expression1;
   private final Expression expression2;
   private final Logical logical;

   public LogicalExpression(Expression expression1, Expression expression2, Logical logical) {
      super();
      this.expression1 = expression1;
      this.expression2 = expression2;
      this.logical = logical;
   }

   @Override
   public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException, IOException {
      final boolean b1 = expression1.evaluate(messageData, mailsort, expressionSteps);
      final boolean b2 = expression2.evaluate(messageData, mailsort, expressionSteps);
      switch (logical) {
         case and:
            return (b1 && b2);
         case or:
            return (b1 || b2);
         default:
            // unreachable
            return false;
      }
   }

   public Expression getExpression1() {
      return expression1;
   }

   public Expression getExpression2() {
      return expression2;
   }

   public Logical getLogical() {
      return logical;
   }

   public enum Logical {
      and, or
   }
}
