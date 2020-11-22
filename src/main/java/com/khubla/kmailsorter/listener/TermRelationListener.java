package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class TermRelationListener extends AbstractListener {
	public TermRelation termRelation;

	@Override
	public void enterTermrelation(mailsortParser.TermrelationContext ctx) {
		termRelation = TermRelation.valueOf(ctx.getText());
	}
}
