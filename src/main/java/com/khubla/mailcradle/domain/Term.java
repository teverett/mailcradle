package com.khubla.mailcradle.domain;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.imap.*;

public abstract class Term {
	abstract public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException;
}
