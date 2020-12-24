package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;

public class IdentifierListener extends AbstractListener {
   public String identifier;

   @Override
   public void enterIdentifier(mailcradleParser.IdentifierContext ctx) {
      if (null != ctx.IDENTIFIER()) {
         identifier = ctx.IDENTIFIER().getText();
      }
   }
}
