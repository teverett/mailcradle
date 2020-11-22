package com.khubla.kmailsorter.domain;

import java.util.*;

import com.khubla.kmailsorter.command.*;

public class Command {
	private CommandType commandType;
	private final List<Command> commands = new ArrayList<Command>();
	private Arguments arguments;

	public void addCommand(Command command) {
		commands.add(command);
	}

	public Arguments getArguments() {
		return arguments;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public void setCommandType(String commandType) {
		switch (commandType) {
			case "if":
				this.commandType = CommandType.if_;
				break;
			case "require":
				this.commandType = CommandType.require;
				break;
			case "stop":
				this.commandType = CommandType.stop;
				break;
			default:
				this.commandType = null;
		}
	}
}
