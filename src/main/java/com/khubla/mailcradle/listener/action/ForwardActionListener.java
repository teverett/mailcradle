package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.action.ForwardAction;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;

public class ForwardActionListener extends AbstractListener {
   public ForwardAction action;

   @Override
   public void enterForwardaction(mailcradleParser.ForwardactionContext ctx) {
      action = new ForwardAction();
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         action.setAddress(stringListener.string);
      }
   }
}
