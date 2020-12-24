package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.action.ReplyAction;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;

public class ReplyActionListener extends AbstractListener {
   public ReplyAction action;

   @Override
   public void enterReplyaction(mailcradleParser.ReplyactionContext ctx) {
      action = new ReplyAction();
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         action.setReply(stringListener.string);
      }
   }
}
