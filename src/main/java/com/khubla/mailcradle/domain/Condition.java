package com.khubla.mailcradle.domain;

public abstract class Condition implements Evaluateable {
	private Term term;

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
}
