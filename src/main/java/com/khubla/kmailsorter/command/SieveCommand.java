package com.khubla.kmailsorter.command;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public interface SieveCommand {
	void execute(Message message, Command command);
}
