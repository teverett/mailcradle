package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.action.FlagAction;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;

public class FlagActionListener extends AbstractListener {
   public FlagAction action;

   @Override
   public void enterFlagaction(mailcradleParser.FlagactionContext ctx) {
      action = new FlagAction();
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         action.setFlag(stringListener.string);
      }
   }
}
