package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class TestListener extends AbstractListener {
	public Test test;

	@Override
	public void enterTest(sieveParser.TestContext ctx) {
		test = new Test();
		if (null != ctx.IDENTIFIER()) {
			test.setName(ctx.IDENTIFIER().getText());
		}
		if (null != ctx.arguments()) {
			final ArgumentsListener argumentsListener = new ArgumentsListener();
			argumentsListener.enterArguments(ctx.arguments());
			test.setArguments(argumentsListener.arguments);
		}
	}
}
