package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.action.*;
import com.khubla.mailcradle.listener.*;

public class StopActionListener extends AbstractListener {
	public StopAction action;

	@Override
	public void enterStopaction(mailcradleParser.StopactionContext ctx) {
		action = new StopAction();
	}
}
