package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.term.*;

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
