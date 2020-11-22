package com.khubla.kmailsorter.domain.condition;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public class ListCondition extends Condition {
	/*
	 * name of the list
	 */
	String listname;
	/**
	 * relation to use
	 */
	ListRelation listRelation;

	@Override
	public boolean evaluate(Message message) throws MessagingException {
		throw new RuntimeException("Not Implemented");
	}

	public String getListname() {
		return listname;
	}

	public ListRelation getListRelation() {
		return listRelation;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public void setListRelation(ListRelation listRelation) {
		this.listRelation = listRelation;
	}
}
