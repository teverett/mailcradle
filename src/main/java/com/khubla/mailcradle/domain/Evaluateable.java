package com.khubla.mailcradle.domain;

import java.io.IOException;

import javax.mail.MessagingException;

import com.khubla.mailcradle.imap.IMAPMessageData;

public interface Evaluateable {
   /**
    * evaluate the expression or condition on a message
    *
    * @param message Message to evaluate on
    * @throws MessagingException oops
    */
   boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort) throws MessagingException, IOException;
}
