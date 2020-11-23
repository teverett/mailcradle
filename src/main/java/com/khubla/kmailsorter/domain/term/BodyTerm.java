package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.imap.*;

public class BodyTerm extends Term {
	@Override
	public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException {
		return new String[] { messageData.getBody() };
	}
}
