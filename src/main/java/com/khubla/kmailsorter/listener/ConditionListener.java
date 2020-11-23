package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.listener.condition.*;

public class ConditionListener extends AbstractListener {
	public Condition condition;

	@Override
	public void enterCondition(mailsortParser.ConditionContext ctx) {
		/*
		 * term
		 */
		if (null != ctx.termcondition()) {
			final TermConditionListener termConditionListener = new TermConditionListener();
			termConditionListener.enterTermcondition(ctx.termcondition());
			condition = termConditionListener.condition;
		}
		/**
		 * list
		 */
		if (null != ctx.listcondition()) {
			final ListConditionListener listConditionListener = new ListConditionListener();
			listConditionListener.enterListcondition(ctx.listcondition());
			condition = listConditionListener.condition;
		}
	}
}
