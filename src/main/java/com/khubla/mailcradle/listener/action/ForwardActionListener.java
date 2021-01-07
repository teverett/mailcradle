package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class ForwardActionListener extends AbstractListener {
	public ForwardAction action;

	@Override
	public void enterForwardaction(mailcradleParser.ForwardactionContext ctx) {
		action = new ForwardAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setAddress(stringListener.string);
		}
	}
}
