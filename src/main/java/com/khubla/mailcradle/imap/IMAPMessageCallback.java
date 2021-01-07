package com.khubla.mailcradle.imap;

import java.io.*;

import javax.mail.*;

public interface IMAPMessageCallback {
	void message(IMAPMessageData imapMessageData) throws MessagingException, IOException;
}
