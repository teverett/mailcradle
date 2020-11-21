package com.khubla.kmailsorter.domain;

import java.util.*;

public class Command {
	private String name;
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

	public String getName() {
		return name;
	}

	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
	}

	public void setName(String name) {
		this.name = name;
	}
}
