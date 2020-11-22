package com.khubla.kmailsorter.domain;

import java.io.*;
import java.util.*;

import javax.mail.*;

public class Filter {
	private final List<Condition> conditions = new ArrayList<Condition>();
	private final List<Action> actions = new ArrayList<Action>();

	public void addAction(Action action) {
		actions.add(action);
	}

	public void addCondition(Condition condition) {
		conditions.add(condition);
	}

	/**
	 * execute this filter on a message
	 *
	 * @param message current message
	 * @throws MessagingException messaging exception
	 * @throws IOException
	 */
	public void execute(Message message, Mailsort mailsort) throws MessagingException, IOException {
		boolean conditionsPass = true;
		for (final Condition condition : conditions) {
			if (false == condition.evaluate(message, mailsort)) {
				conditionsPass = false;
				break;
			}
		}
		/*
		 * actions
		 */
		if (true == conditionsPass) {
			for (final Action action : actions) {
				action.execute(message, mailsort);
			}
		}
	}

	public List<Action> getActions() {
		return actions;
	}

	public List<Condition> getConditions() {
		return conditions;
	}
}
