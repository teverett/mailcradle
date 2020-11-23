package com.khubla.kmailsorter.listener.action;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.action.*;
import com.khubla.kmailsorter.listener.*;

public class FlagActionListener extends AbstractListener {
	public FlagAction action;

	@Override
	public void enterFlagaction(mailsortParser.FlagactionContext ctx) {
		action = new FlagAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setFlag(stringListener.string);
		}
	}
}
