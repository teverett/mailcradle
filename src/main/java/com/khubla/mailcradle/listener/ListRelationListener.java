package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;

public class ListRelationListener extends AbstractListener {
	public ListRelation listRelation;

	@Override
	public void enterListrelation(mailcradleParser.ListrelationContext ctx) {
		listRelation = ListRelation.valueOf(ctx.getText());
	}
}
