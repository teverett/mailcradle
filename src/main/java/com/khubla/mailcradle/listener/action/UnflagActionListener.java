package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.action.UnflagAction;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;

public class UnflagActionListener extends AbstractListener {
   public UnflagAction action;

   @Override
   public void enterUnflagaction(mailcradleParser.UnflagactionContext ctx) {
      action = new UnflagAction();
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         action.setFlag(stringListener.string);
      }
   }
}
