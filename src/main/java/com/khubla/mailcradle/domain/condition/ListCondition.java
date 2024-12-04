package com.khubla.mailcradle.domain.condition;

import com.khubla.mailcradle.domain.Condition;
import com.khubla.mailcradle.domain.ListRelation;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.domain.StringList;
import com.khubla.mailcradle.imap.IMAPMessageData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class ListCondition extends Condition {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(ListCondition.class);
   /*
    * name of the list
    */
   private String listname;
   /**
    * relation to use
    */
   private ListRelation listRelation;

   @Override
   public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException, IOException {
      /*
       * get the list
       */
      final StringList stringList = mailsort.getList(listname);
      if (null != stringList) {
         switch (listRelation) {
            case contains:
               final String[] strs = getTerm().resolve(messageData);
               if (null != strs) {
                  for (final String str : strs) {
                     if (null != str) {
                        if (stringList.contains(str.toLowerCase())) {
                           expressionSteps.add("Term 'list contains' Condition '" + stringList.getName() + "' matches '" + str + "'");
                           logger.info("Term 'list contains' Condition '" + stringList.getName() + "' matches '" + str + "'");
                           return true;
                        }
                     }
                  }
               }
               return false;
            default:
               return false;
         }
      } else {
         logger.error("Unable to find list: " + listname);
         return false;
      }
   }

   public String getListname() {
      return listname;
   }

   public void setListname(String listname) {
      this.listname = listname;
   }

   public ListRelation getListRelation() {
      return listRelation;
   }

   public void setListRelation(ListRelation listRelation) {
      this.listRelation = listRelation;
   }
}
