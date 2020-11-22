package com.khubla.kmailsorter.domain;

import java.util.*;

public class Filter {
	private final List<Condition> conditions = new ArrayList<Condition>();
	private final List<Action> actions = new ArrayList<Action>();

	public void addAction(Action action) {
		actions.add(action);
	}

	public void addCondition(Condition condition) {
		conditions.add(condition);
	}

	public List<Action> getActions() {
		return actions;
	}

	public List<Condition> getConditions() {
		return conditions;
	}
}
