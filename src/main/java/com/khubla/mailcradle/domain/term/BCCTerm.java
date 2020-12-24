package com.khubla.mailcradle.domain.term;

import java.io.IOException;

import javax.mail.MessagingException;

import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.imap.IMAPMessageData;

public class BCCTerm extends Term {
   @Override
   public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException {
      return messageData.getBcc();
   }
}
