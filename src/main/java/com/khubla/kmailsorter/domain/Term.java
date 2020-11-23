package com.khubla.kmailsorter.domain;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.imap.*;

public abstract class Term {
	abstract public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException;
}
