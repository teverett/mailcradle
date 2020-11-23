package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.imap.*;

public class HeaderTerm extends Term {
	private String headername;

	public String getHeadername() {
		return headername;
	}

	@Override
	public String[] resolve(IMAPMessageData messageData) throws MessagingException, IOException {
		return messageData.getHeader(headername);
	}

	public void setHeadername(String headername) {
		this.headername = headername;
	}
}
