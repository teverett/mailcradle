package com.khubla.kmailsorter.listener;

import java.util.*;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.sieveParser.*;
import com.khubla.kmailsorter.domain.*;

public class CommandsListener extends AbstractListener {
	public List<Command> commands;

	@Override
	public void enterCommands(sieveParser.CommandsContext ctx) {
		commands = new ArrayList<Command>();
		if (null != ctx.command()) {
			for (final CommandContext commandContext : ctx.command()) {
				final CommandListener commandListener = new CommandListener();
				commandListener.enterCommand(commandContext);
				commands.add(commandListener.command);
			}
		}
	}
}
