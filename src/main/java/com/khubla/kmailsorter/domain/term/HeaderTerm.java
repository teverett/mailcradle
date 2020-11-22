package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public class HeaderTerm extends Term {
	private String headername;

	public String getHeadername() {
		return headername;
	}

	@Override
	public String[] resolve(Message message) throws MessagingException, IOException {
		return message.getHeader(headername);
	}

	public void setHeadername(String headername) {
		this.headername = headername;
	}
}
