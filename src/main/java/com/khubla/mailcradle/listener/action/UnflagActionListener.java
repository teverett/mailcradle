package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class UnflagActionListener extends AbstractListener {
	public UnflagAction action;

	@Override
	public void enterUnflagaction(mailcradleParser.UnflagactionContext ctx) {
		action = new UnflagAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setFlag(stringListener.string);
		}
	}
}
