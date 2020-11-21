package com.khubla.kmailsorter.domain;

import java.util.*;

public class TestList {
	private final List<Test> tests = new ArrayList<Test>();

	public void addTest(Test test) {
		tests.add(test);
	}

	public List<Test> getTests() {
		return tests;
	}

	public int size() {
		return tests.size();
	}
}
