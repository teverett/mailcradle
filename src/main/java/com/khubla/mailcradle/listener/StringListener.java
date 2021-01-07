package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;

public class StringListener extends AbstractListener {
	public String string;

	@Override
	public void enterString(mailcradleParser.StringContext ctx) {
		if (null != ctx.STRING_LITERAL()) {
			string = ctx.STRING_LITERAL().getText();
			string = string.substring(1, string.length() - 1);
		}
	}
}
