package com.khubla.kmailsorter.command;

import com.khubla.kmailsorter.command.impl.*;

public class SieveCommandFactory {
	public static SieveCommand getCommand(CommandType commandType) {
		if (null == commandType) {
			return new BlockSieveCommand();
		}
		switch (commandType) {
			case if_:
				return new IfSieveCommand();
			case require:
				return new RequireSieveCommand();
			case stop:
				return new StopSieveCommand();
			default:
				return null;
		}
	}
}
