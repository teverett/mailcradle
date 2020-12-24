package com.khubla.mailcradle.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.khubla.mailcradle.imap.IMAPMessageData;

public class Filter {
   private Expression expression;
   private final List<Action> actions = new ArrayList<Action>();

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
      /*
       * evaluate expression
       */
      if (expression.evaluate(messageData, mailsort)) {
         /*
          * actions
          */
         for (final Action action : actions) {
            final boolean continueProcessiing = action.execute(messageData, mailsort);
            if (false == continueProcessiing) {
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
