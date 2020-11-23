package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class MoveActionListener extends AbstractListener {
	public MoveAction action;

	@Override
	public void enterMoveaction(mailcradleParser.MoveactionContext ctx) {
		action = new MoveAction();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			action.setFolderName(stringListener.string);
		}
	}
}
