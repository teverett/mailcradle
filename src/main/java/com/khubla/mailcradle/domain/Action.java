package com.khubla.mailcradle.domain;

import com.khubla.mailcradle.imap.IMAPMessageData;

import javax.mail.MessagingException;
import java.util.List;

public abstract class Action {
   /**
    * execute the action on a message
    *
    * @param message Message to execute with
    * @return true if continue processing rules, false if we want to stop processing rules
    * @throws MessagingException oops
    */
   abstract public boolean execute(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException;
}
