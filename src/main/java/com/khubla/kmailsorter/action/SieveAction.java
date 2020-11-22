package com.khubla.kmailsorter.action;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public interface SieveAction {
	void execute(Message message, Command command) throws MessagingException;
}
