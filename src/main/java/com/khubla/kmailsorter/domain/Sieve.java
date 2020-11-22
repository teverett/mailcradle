package com.khubla.kmailsorter.domain;

import java.util.*;

public class Sieve {
	private final List<Command> commands = new ArrayList<Command>();

	public void addCommand(Command command) {
		commands.add(command);
	}

	public List<Command> getCommands() {
		return commands;
	}

	public int size() {
		return commands.size();
	}
}
