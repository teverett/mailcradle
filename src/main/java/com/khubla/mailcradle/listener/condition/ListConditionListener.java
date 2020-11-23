package com.khubla.mailcradle.listener.condition;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.condition.*;
import com.khubla.mailcradle.listener.*;

public class ListConditionListener extends AbstractListener {
	public ListCondition condition;

	@Override
	public void enterListcondition(mailcradleParser.ListconditionContext ctx) {
		condition = new ListCondition();
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
		if (null != ctx.listrelation()) {
			final ListRelationListener listRelationListener = new ListRelationListener();
			listRelationListener.enterListrelation(ctx.listrelation());
			condition.setListRelation(listRelationListener.listRelation);
		}
		/*
		 * identifier
		 */
		if (null != ctx.identifier()) {
			final IdentifierListener identifierListener = new IdentifierListener();
			identifierListener.enterIdentifier(ctx.identifier());
			condition.setListname(identifierListener.identifier);
		}
	}
}
