package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class ArgumentListener extends AbstractListener {
	public Argument argument = null;

	@Override
	public void enterArgument(sieveParser.ArgumentContext ctx) {
		argument = new Argument();
		if (null != ctx.TAG()) {
			argument.setTag(ctx.TAG().getText());
		} else if (null != ctx.NUMBER()) {
			argument.setNumber(Integer.parseInt(ctx.NUMBER().getText()));
		} else if (null != ctx.string()) {
			final StringListener stringListener = new StringListener();
			stringListener.enterString(ctx.string());
			argument.setString(stringListener.string);
		} else if (null != ctx.stringlist()) {
			final StringListListener stringListListener = new StringListListener();
			stringListListener.enterStringlist(ctx.stringlist());
			argument.setStringList(stringListListener.strings);
		}
	}
}
