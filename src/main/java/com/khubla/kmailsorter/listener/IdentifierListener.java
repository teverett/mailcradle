package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;

public class IdentifierListener extends AbstractListener {
	public String identifier;

	@Override
	public void enterIdentifier(mailsortParser.IdentifierContext ctx) {
		if (null != ctx.IDENTIFIER()) {
			identifier = ctx.IDENTIFIER().getText();
		}
	}
}
