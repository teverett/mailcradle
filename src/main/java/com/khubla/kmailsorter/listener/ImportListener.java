package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;

public class ImportListener extends AbstractListener {
	public String filename;

	@Override
	public void enterImport_(mailsortParser.Import_Context ctx) {
		if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			filename = stringListener.string;
		}
	}
}
