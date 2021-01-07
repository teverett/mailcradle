package com.khubla.mailcradle.domain.term;

import java.io.*;

import javax.mail.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class SubjectTerm extends Term {
	@Override
	public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException {
		return new String[] { messageData.getSubject() };
	}
}
