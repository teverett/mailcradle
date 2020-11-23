package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class ReplyActionListener extends AbstractListener {
	public ReplyAction action;

	@Override
	public void enterReplyaction(mailcradleParser.ReplyactionContext ctx) {
		action = new ReplyAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setReply(stringListener.string);
		}
	}
}
