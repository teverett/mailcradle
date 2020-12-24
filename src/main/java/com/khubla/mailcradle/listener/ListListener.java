package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.mailcradleParser.StringContext;
import com.khubla.mailcradle.domain.StringList;

public class ListListener extends AbstractListener {
   public StringList stringList;

   @Override
   public void enterList(mailcradleParser.ListContext ctx) {
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
