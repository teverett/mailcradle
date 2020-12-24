package com.khubla.mailcradle.imap;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface IMAPEventNotification {
   void event(Message[] messages) throws MessagingException, IOException;
}
