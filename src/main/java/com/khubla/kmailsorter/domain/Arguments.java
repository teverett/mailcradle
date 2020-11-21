package com.khubla.kmailsorter.domain;

import java.util.*;

public class Arguments {
	private final List<Argument> arguments = new ArrayList<Argument>();
	private Test test;
	private TestList testList;

	public void addArgument(Argument argument) {
		arguments.add(argument);
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public Test getTest() {
		return test;
	}

	public TestList getTestList() {
		return testList;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public void setTestList(TestList testList) {
		this.testList = testList;
	}
}
