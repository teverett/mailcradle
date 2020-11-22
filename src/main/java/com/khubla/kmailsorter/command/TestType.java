package com.khubla.kmailsorter.command;

public enum TestType {
	address, allof, anyof, exists, false_, header, not, size, true_, envelope;

	public static TestType fromString(String str) {
		switch (str) {
			case "address":
				return TestType.address;
			case "allof":
				return TestType.allof;
			case "anyof":
				return TestType.anyof;
			case "exists":
				return TestType.exists;
			case "false":
				return TestType.false_;
			case "header":
				return TestType.header;
			case "not":
				return TestType.not;
			case "size":
				return TestType.size;
			case "true":
				return TestType.true_;
			case "envelope":
				return TestType.envelope;
			default:
				return null;
		}
	}
}
