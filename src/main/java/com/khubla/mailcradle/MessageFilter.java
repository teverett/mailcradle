package com.khubla.mailcradle;

import java.io.IOException;

import javax.mail.MessagingException;

import com.khubla.mailcradle.domain.Filter;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.IMAPMessageCallback;
import com.khubla.mailcradle.imap.IMAPMessageData;

public class MessageFilter implements IMAPMessageCallback {
   /**
    * the mailsort data
    */
   private final Mailcradle mailsort;

   public MessageFilter(Mailcradle mailsort) {
      super();
      this.mailsort = mailsort;
   }

   @Override
   public void message(IMAPMessageData imapMessageData) throws MessagingException, IOException {
      /*
       * process message
       */
      if (null != imapMessageData) {
         for (final Filter filter : mailsort.getFilters()) {
            if (false == filter.execute(imapMessageData, mailsort)) {
               break;
            }
         }
      }
   }
}
