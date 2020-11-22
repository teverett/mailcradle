package com.khubla.kmailsorter.domain.condition;

import com.khubla.kmailsorter.domain.*;

public class ListCondition extends Condition {
	String listname;
	ListRelation listRelation;

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
