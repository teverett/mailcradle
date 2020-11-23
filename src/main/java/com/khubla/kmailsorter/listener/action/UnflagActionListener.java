package com.khubla.kmailsorter.listener.action;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.action.*;
import com.khubla.kmailsorter.listener.*;

public class UnflagActionListener extends AbstractListener {
	public UnflagAction action;

	@Override
	public void enterUnflagaction(mailsortParser.UnflagactionContext ctx) {
		action = new UnflagAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setFlag(stringListener.string);
		}
	}
}
