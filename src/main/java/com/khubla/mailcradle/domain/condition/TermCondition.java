package com.khubla.mailcradle.domain.condition;

import com.khubla.mailcradle.domain.Condition;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.domain.TermRelation;
import com.khubla.mailcradle.imap.IMAPMessageData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class TermCondition extends Condition {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(TermCondition.class);
   /**
    * value to compare to
    */
   private String value;
   /**
    * relation to use
    */
   private TermRelation termRelation;

   @Override
   public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException, IOException {
      final String[] strs = getTerm().resolve(messageData);
      if (null != strs) {
         switch (termRelation) {
            case is:
               for (final String str : strs) {
                  if (null != str) {
                     if (value.toLowerCase().compareTo(str.toLowerCase()) == 0) {
                        expressionSteps.add("Term 'is' Condition '" + str + "' matches '" + value + "'");
                        logger.info("Term 'is' Condition '" + str + "' matches '" + value + "'");
                        return true;
                     }
                  }
               }
               return false;
            case contains:
               for (final String str : strs) {
                  if (null != str) {
                     if (value.toLowerCase().contains(str.toLowerCase())) {
                        expressionSteps.add("Term 'contains' Condition '" + str + "' matches '" + value + "'");
                        logger.info("Term 'is' Condition '" + str + "' matches '" + value + "'");
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

   public void setTermRelation(TermRelation termRelation) {
      this.termRelation = termRelation;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }
}
