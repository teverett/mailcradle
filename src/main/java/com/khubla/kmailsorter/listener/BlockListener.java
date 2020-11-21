package com.khubla.kmailsorter.listener;

import java.util.*;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class BlockListener extends AbstractListener {
	public List<Command> commands;

	@Override
	public void enterBlock(sieveParser.BlockContext ctx) {
		/*
		 * commands
		 */
		if (null != ctx.commands()) {
			final CommandsListener commandsListener = new CommandsListener();
			commandsListener.enterCommands(ctx.commands());
			commands = commandsListener.commands;
		}
	}
}
