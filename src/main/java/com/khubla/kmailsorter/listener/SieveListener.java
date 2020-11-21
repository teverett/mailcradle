package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class SieveListener extends AbstractListener {
	public Sieve sieve = null;

	@Override
	public void enterStart(sieveParser.StartContext ctx) {
		sieve = new Sieve();
		/*
		 * commands
		 */
		if (null != ctx.commands()) {
			final CommandsListener commandsListener = new CommandsListener();
			commandsListener.enterCommands(ctx.commands());
			for (final Command command : commandsListener.commands) {
				sieve.addCommand(command);
			}
		}
	}
}
