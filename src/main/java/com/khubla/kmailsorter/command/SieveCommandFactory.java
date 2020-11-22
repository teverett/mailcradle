package com.khubla.kmailsorter.command;

public class SieveCommandFactory {
	public static SieveCommand getCommand(CommandType commandType) {
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
