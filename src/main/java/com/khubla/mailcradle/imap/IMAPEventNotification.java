package com.khubla.mailcradle.imap;

import java.io.*;

import javax.mail.*;

public interface IMAPEventNotification {
	void event(Message[] messages) throws MessagingException, IOException;
}
