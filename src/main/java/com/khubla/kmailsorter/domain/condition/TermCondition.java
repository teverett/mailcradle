package com.khubla.kmailsorter.domain.condition;

import com.khubla.kmailsorter.domain.*;

public class TermCondition extends Condition {
	String value;
	TermRelation termRelation;

	public TermRelation getTermRelation() {
		return termRelation;
	}

	public String getValue() {
		return value;
	}

	public void setTermRelation(TermRelation termRelation) {
		this.termRelation = termRelation;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
