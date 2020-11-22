package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.mailsortParser.*;
import com.khubla.kmailsorter.domain.*;

public class FilterListener extends AbstractListener {
	public Filter filter;

	@Override
	public void enterFilter(mailsortParser.FilterContext ctx) {
		filter = new Filter();
		/**
		 * actions
		 */
		if (null != ctx.action()) {
			for (final ActionContext actionContent : ctx.action()) {
				final ActionListener actionListener = new ActionListener();
				actionListener.enterAction(actionContent);
				filter.addAction(actionListener.action);
			}
		}
		/*
		 * conditions
		 */
		if (null != ctx.condition()) {
			for (final ConditionContext conditionContext : ctx.condition()) {
				final ConditionListener conditionListener = new ConditionListener();
				conditionListener.enterCondition(conditionContext);
				filter.addCondition(conditionListener.condition);
			}
		}
	}
}
