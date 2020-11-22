package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class ActionListener extends AbstractListener {
	public Action action;

	@Override
	public void enterAction(mailsortParser.ActionContext ctx) {
		/**
		 * move
		 */
		if (null != ctx.moveaction()) {
			final MoveActionListener moveActionListener = new MoveActionListener();
			moveActionListener.enterMoveaction(ctx.moveaction());
			action = moveActionListener.action;
		}
	}
}
