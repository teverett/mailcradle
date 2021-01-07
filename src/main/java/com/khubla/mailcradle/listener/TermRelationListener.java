package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;

public class TermRelationListener extends AbstractListener {
	public TermRelation termRelation;

	@Override
	public void enterTermrelation(mailcradleParser.TermrelationContext ctx) {
		termRelation = TermRelation.valueOf(ctx.getText());
	}
}
