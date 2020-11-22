package com.khubla.kmailsorter.domain.action;

import com.khubla.kmailsorter.domain.*;

public class MoveAction extends Action {
	private String mailboxname;

	public String getMailboxname() {
		return mailboxname;
	}

	public void setMailboxname(String mailboxname) {
		this.mailboxname = mailboxname;
	}
}
