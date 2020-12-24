package com.khubla.mailcradle.domain;

import java.io.IOException;

import javax.mail.MessagingException;

import com.khubla.mailcradle.imap.IMAPMessageData;

public abstract class Term {
   abstract public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException;
}
