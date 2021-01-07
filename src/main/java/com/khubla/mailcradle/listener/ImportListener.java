package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;

public class ImportListener extends AbstractListener {
	public String filename;

	@Override
	public void enterImport_(mailcradleParser.Import_Context ctx) {
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			filename = stringListener.string;
		}
	}
}
