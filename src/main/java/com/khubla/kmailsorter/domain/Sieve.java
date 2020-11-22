package com.khubla.kmailsorter.domain;

import java.util.*;

public class Sieve {
	private final Map<String, Command> commands = new HashMap<String, Command>();

	public void addCommand(Command command) {
		commands.put(command.getName(), command);
	}

	public Command getCommand(String name) {
		return commands.get(name);
	}

	public Map<String, Command> getCommands() {
		return commands;
	}

	public int size() {
		return commands.size();
	}
}
