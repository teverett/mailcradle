package com.khubla.kmailsorter.listener.action;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.action.*;
import com.khubla.kmailsorter.listener.*;

public class MoveActionListener extends AbstractListener {
	public MoveAction action;

	@Override
	public void enterMoveaction(mailsortParser.MoveactionContext ctx) {
		action = new MoveAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setFolderName(stringListener.string);
		}
	}
}
