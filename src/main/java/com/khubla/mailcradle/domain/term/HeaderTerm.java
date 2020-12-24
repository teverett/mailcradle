package com.khubla.mailcradle.domain.term;

import java.io.IOException;

import javax.mail.MessagingException;

import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.imap.IMAPMessageData;

public class HeaderTerm extends Term {
   private String headername;

   public String getHeadername() {
      return headername;
   }

   @Override
   public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException {
      return messageData.getHeader(headername);
   }

   public void setHeadername(String headername) {
      this.headername = headername;
   }
}
