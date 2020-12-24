package com.khubla.mailcradle.domain.condition;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.khubla.mailcradle.domain.Condition;
import com.khubla.mailcradle.domain.ListRelation;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.domain.StringList;
import com.khubla.mailcradle.imap.IMAPMessageData;

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
   public boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException {
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

   public ListRelation getListRelation() {
      return listRelation;
   }

   public void setListname(String listname) {
      this.listname = listname;
   }

   public void setListRelation(ListRelation listRelation) {
      this.listRelation = listRelation;
   }
}
