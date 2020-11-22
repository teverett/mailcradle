package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.command.*;
import com.khubla.kmailsorter.domain.*;

public class CommandListener extends AbstractListener {
	public Command command;

	@Override
	public void enterCommand(sieveParser.CommandContext ctx) {
		command = new Command();
		/*
		 * name
		 */
		String name = null;
		if (null != ctx.IDENTIFIER()) {
			name = ctx.IDENTIFIER().getText();
			command.setCommandType(CommandType.fromString(name));
		}
		/*
		 * args
		 */
		if (null != ctx.arguments()) {
			final ArgumentsListener argumentsListener = new ArgumentsListener();
			argumentsListener.enterArguments(ctx.arguments());
			command.setArguments(argumentsListener.arguments);
		}
		/*
		 * block
		 */
		if (null != ctx.block()) {
			final BlockListener blockListener = new BlockListener();
			blockListener.enterBlock(ctx.block());
			for (final Command subCommand : blockListener.commands) {
				command.addCommand(subCommand);
			}
		}
	}
}
