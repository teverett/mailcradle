package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.sieveParser.*;
import com.khubla.kmailsorter.domain.*;

public class TestListListener extends AbstractListener {
	public TestList testList;

	@Override
	public void enterTestlist(sieveParser.TestlistContext ctx) {
		testList = new TestList();
		if (null != ctx.test()) {
			for (final TestContext testContext : ctx.test()) {
				final TestListener testListener = new TestListener();
				testListener.enterTest(testContext);
				testList.addTest(testListener.test);
			}
		}
	}
}
