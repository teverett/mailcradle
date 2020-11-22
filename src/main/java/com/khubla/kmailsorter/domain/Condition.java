package com.khubla.kmailsorter.domain;

public abstract class Condition {
	private Term term;

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
