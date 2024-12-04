package com.khubla.mailcradle.domain;

import com.khubla.mailcradle.imap.IMAPMessageData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface Evaluateable {
   /**
    * evaluate the expression or condition on a message
    *
    * @param message Message to evaluate on
    * @throws MessagingException oops
    */
   boolean evaluate(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException, IOException;
}
