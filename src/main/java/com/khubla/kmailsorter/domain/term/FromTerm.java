package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;

import com.khubla.kmailsorter.domain.*;

public class FromTerm extends Term {
	@Override
	public String[] resolve(Message message) throws MessagingException, IOException {
		final Address[] fromAddresses = message.getFrom();
		final String[] ret = new String[fromAddresses.length];
		int i = 0;
		for (final Address address : fromAddresses) {
			if (address instanceof InternetAddress) {
				ret[i++] = ((InternetAddress) address).getAddress();
			}
		}
		return ret;
	}
}
