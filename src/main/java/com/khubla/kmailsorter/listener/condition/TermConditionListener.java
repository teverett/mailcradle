package com.khubla.kmailsorter.listener.condition;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.condition.*;
import com.khubla.kmailsorter.listener.*;

public class TermConditionListener extends AbstractListener {
	public TermCondition condition;

	@Override
	public void enterTermcondition(mailsortParser.TermconditionContext ctx) {
		condition = new TermCondition();
		/**
		 * term
		 */
		if (null != ctx.term()) {
			final TermListener termListener = new TermListener();
			termListener.enterTerm(ctx.term());
			condition.setTerm(termListener.term);
		}
		/*
		 * relation
		 */
		if (null != ctx.termrelation()) {
			final TermRelationListener termRelationListener = new TermRelationListener();
			termRelationListener.enterTermrelation(ctx.termrelation());
			condition.setTermRelation(termRelationListener.termRelation);
		}
		/*
		 * value
		 */
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			condition.setValue(stringListener.string);
		}
	}
}
