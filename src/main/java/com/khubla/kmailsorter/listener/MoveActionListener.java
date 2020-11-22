package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.action.*;

public class MoveActionListener extends AbstractListener {
	public MoveAction action;

	@Override
	public void enterMoveaction(mailsortParser.MoveactionContext ctx) {
		action = new MoveAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setMailboxname(stringListener.string);
		}
	}
}
