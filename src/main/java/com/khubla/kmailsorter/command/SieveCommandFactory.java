package com.khubla.kmailsorter.command;

public class SieveCommandFactory {
	public static SieveCommand getCommand(String commandName) {
		switch (commandName) {
			case "if":
				return new IfSieveCommand();
			case "require":
				return new RequireSieveCommand();
			default:
				return null;
		}
	}
}
