package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class SetHeaderActionListener extends AbstractListener {
	public SetHeaderAction action;

	@Override
	public void enterSetheaderaction(mailcradleParser.SetheaderactionContext ctx) {
		action = new SetHeaderAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			// name
			stringListener.enterString(ctx.string().get(0));
			action.setName(stringListener.string);
			// value
			stringListener.enterString(ctx.string().get(1));
			action.setValue(stringListener.string);
		}
	}
}
