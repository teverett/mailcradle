package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.ListRelation;

public class ListRelationListener extends AbstractListener {
   public ListRelation listRelation;

   @Override
   public void enterListrelation(mailcradleParser.ListrelationContext ctx) {
      listRelation = ListRelation.valueOf(ctx.getText());
   }
}
