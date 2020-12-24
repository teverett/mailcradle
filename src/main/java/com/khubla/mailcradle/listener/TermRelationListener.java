package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.TermRelation;

public class TermRelationListener extends AbstractListener {
   public TermRelation termRelation;

   @Override
   public void enterTermrelation(mailcradleParser.TermrelationContext ctx) {
      termRelation = TermRelation.valueOf(ctx.getText());
   }
}
