package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.expression.LogicalExpression.*;

public class LogicalListener extends AbstractListener {
	public Logical logical;

	@Override
	public void enterLogical(mailcradleParser.LogicalContext ctx) {
		final String text = ctx.getText();
		switch (text) {
			case "and":
				logical = Logical.and;
				break;
			case "or":
				logical = Logical.or;
				break;
			default:
				logical = null;
		}
	}
}
