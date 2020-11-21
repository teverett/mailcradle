package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.sieveParser.*;
import com.khubla.kmailsorter.domain.*;

public class ArgumentsListener extends AbstractListener {
	public Arguments arguments;

	@Override
	public void enterArguments(sieveParser.ArgumentsContext ctx) {
		arguments = new Arguments();
		/*
		 * args
		 */
		if (null != ctx.argument()) {
			for (final ArgumentContext argumentContext : ctx.argument()) {
				final ArgumentListener argumentListener = new ArgumentListener();
				argumentListener.enterArgument(argumentContext);
				arguments.addArgument(argumentListener.argument);
			}
		}
		/*
		 * test
		 */
		if (null != ctx.test()) {
			final TestListener testListener = new TestListener();
			testListener.enterTest(ctx.test());
			arguments.setTest(testListener.test);
		}
		/*
		 * test list
		 */
		if (null != ctx.testlist()) {
			final TestListListener testListListener = new TestListListener();
			testListListener.enterTestlist(ctx.testlist());
			arguments.setTestList(testListListener.testList);
		}
	}
}
