package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.mailcradleParser.*;
import com.khubla.mailcradle.domain.*;

public class FilterListener extends AbstractListener {
	public Filter filter;

	@Override
	public void enterFilter(mailcradleParser.FilterContext ctx) {
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
