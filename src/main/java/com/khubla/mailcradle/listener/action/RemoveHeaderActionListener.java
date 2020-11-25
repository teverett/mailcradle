package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class RemoveHeaderActionListener extends AbstractListener {
	public RemoveHeaderAction action;

	@Override
	public void enterRemoveheaderaction(mailcradleParser.RemoveheaderactionContext ctx) {
		action = new RemoveHeaderAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setName(stringListener.string);
		}
	}
}
