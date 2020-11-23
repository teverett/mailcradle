package com.khubla.kmailsorter.listener.action;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.action.*;
import com.khubla.kmailsorter.listener.*;

public class ReplyActionListener extends AbstractListener {
	public ReplyAction action;

	@Override
	public void enterReplyaction(mailsortParser.ReplyactionContext ctx) {
		action = new ReplyAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setReply(stringListener.string);
		}
	}
}
