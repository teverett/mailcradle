package com.khubla.kmailsorter.listener.condition;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.condition.*;
import com.khubla.kmailsorter.listener.*;

public class ListConditionListener extends AbstractListener {
	public ListCondition condition;

	@Override
	public void enterListcondition(mailsortParser.ListconditionContext ctx) {
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
