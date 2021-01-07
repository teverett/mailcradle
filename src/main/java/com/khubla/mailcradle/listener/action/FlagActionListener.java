package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class FlagActionListener extends AbstractListener {
	public FlagAction action;

	@Override
	public void enterFlagaction(mailcradleParser.FlagactionContext ctx) {
		action = new FlagAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setFlag(stringListener.string);
		}
	}
}
