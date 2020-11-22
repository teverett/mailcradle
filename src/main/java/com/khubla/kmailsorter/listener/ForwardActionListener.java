package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.action.*;

public class ForwardActionListener extends AbstractListener {
	public ForwardAction action;

	@Override
	public void enterForwardaction(mailsortParser.ForwardactionContext ctx) {
		action = new ForwardAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setAddress(stringListener.string);
		}
	}
}
