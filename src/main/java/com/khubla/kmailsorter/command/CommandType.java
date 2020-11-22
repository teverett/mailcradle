package com.khubla.kmailsorter.command;

public enum CommandType {
	require, if_, stop;

	public static CommandType fromString(String str) {
		switch (str) {
			case "require":
				return CommandType.require;
			case "if":
				return CommandType.if_;
			case "stop":
				return CommandType.stop;
			default:
				return null;
		}
	}
}
