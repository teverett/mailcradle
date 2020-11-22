package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;

public class StringListener extends AbstractListener {
	public String string;

	@Override
	public void enterString(mailsortParser.StringContext ctx) {
		if (null != ctx.STRING_LITERAL()) {
			string = ctx.STRING_LITERAL().getText();
			string = string.substring(1, string.length() - 1);
		}
	}
}
