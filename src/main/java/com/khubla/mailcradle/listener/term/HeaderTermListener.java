package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.term.*;
import com.khubla.mailcradle.listener.*;

public class HeaderTermListener extends AbstractListener {
	public HeaderTerm term;

	@Override
	public void enterHeaderterm(mailcradleParser.HeadertermContext ctx) {
		term = new HeaderTerm();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			term.setHeadername(stringListener.string);
		}
	}
}
