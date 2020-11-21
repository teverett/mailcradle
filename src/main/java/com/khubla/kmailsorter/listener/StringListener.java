package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;

public class StringListener extends AbstractListener {
	public String string;

	@Override
	public void enterString(sieveParser.StringContext ctx) {
		if (null != ctx.QUOTEDSTRING()) {
			string = ctx.QUOTEDSTRING().getText();
		}
		if (null != ctx.multiline()) {
			final MultilineListener multilineListener = new MultilineListener();
			multilineListener.enterMultiline(ctx.multiline());
			string = multilineListener.string;
		}
	}
}
