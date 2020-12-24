package com.khubla.mailcradle.imap;

import java.io.IOException;

import javax.mail.MessagingException;

public interface IMAPMessageCallback {
   void message(IMAPMessageData imapMessageData) throws MessagingException, IOException;
}
