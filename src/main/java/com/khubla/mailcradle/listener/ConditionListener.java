package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.listener.condition.*;

public class ConditionListener extends AbstractListener {
	public Condition condition;

	@Override
	public void enterCondition(mailcradleParser.ConditionContext ctx) {
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
