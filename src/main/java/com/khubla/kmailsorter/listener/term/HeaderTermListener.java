package com.khubla.kmailsorter.listener.term;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.term.*;
import com.khubla.kmailsorter.listener.*;

public class HeaderTermListener extends AbstractListener {
	public HeaderTerm term;

	@Override
	public void enterHeaderterm(mailsortParser.HeadertermContext ctx) {
		term = new HeaderTerm();
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			term.setHeadername(stringListener.string);
		}
	}
}
