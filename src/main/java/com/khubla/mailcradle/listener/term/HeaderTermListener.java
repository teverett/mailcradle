package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.term.HeaderTerm;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;

public class HeaderTermListener extends AbstractListener {
   public HeaderTerm term;

   @Override
   public void enterHeaderterm(mailcradleParser.HeadertermContext ctx) {
      term = new HeaderTerm();
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         term.setHeadername(stringListener.string);
      }
   }
}
