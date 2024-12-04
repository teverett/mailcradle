package com.khubla.mailcradle.domain;

import com.khubla.mailcradle.imap.IMAPMessageData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Filter {
   private final List<Action> actions = new ArrayList<Action>();
   private Expression expression;

   public void addAction(Action action) {
      actions.add(action);
   }

   /**
    * execute this filter on a message
    *
    * @param message current message
    * @throws MessagingException messaging exception
    * @throws IOException
    */
   public boolean execute(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
      // expression steps for debugging
      List<String> expressionSteps = new ArrayList<>();
      /*
       * evaluate expression
       */
      if (expression.evaluate(messageData, mailsort, expressionSteps)) {
         /*
          * actions
          */
         for (final Action action : actions) {
            final boolean continueProcessiing = action.execute(messageData, mailsort, expressionSteps);
            if (!continueProcessiing) {
               // we done!
               return false;
            }
         }
      }
      return true;
   }

   public List<Action> getActions() {
      return actions;
   }

   public Expression getExpression() {
      return expression;
   }

   public void setExpression(Expression expression) {
      this.expression = expression;
   }
}
