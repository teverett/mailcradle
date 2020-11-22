package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class ListRelationListener extends AbstractListener {
	public ListRelation listRelation;

	@Override
	public void enterListrelation(mailsortParser.ListrelationContext ctx) {
		listRelation = ListRelation.valueOf(ctx.getText());
	}
}
