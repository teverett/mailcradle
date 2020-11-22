package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public class SubjectTerm extends Term {
	@Override
	public String[] resolve(Message message) throws MessagingException, IOException {
		return new String[] { message.getSubject() };
	}
}
