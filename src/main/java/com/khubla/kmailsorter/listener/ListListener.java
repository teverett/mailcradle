package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.mailsortParser.*;
import com.khubla.kmailsorter.domain.*;

public class ListListener extends AbstractListener {
	public StringList stringList;

	@Override
	public void enterList(mailsortParser.ListContext ctx) {
		stringList = new StringList();
		/*
		 * name
		 */
		if (null != ctx.identifier()) {
			final IdentifierListener identifierListener = new IdentifierListener();
			identifierListener.enterIdentifier(ctx.identifier());
			stringList.setName(identifierListener.identifier);
		}
		/*
		 * list
		 */
		if (null != ctx.string()) {
			for (final StringContext stringContext : ctx.string()) {
				final StringListener stringListener = new StringListener();
				stringListener.enterString(stringContext);
				stringList.addString(stringListener.string);
			}
		}
	}
}
