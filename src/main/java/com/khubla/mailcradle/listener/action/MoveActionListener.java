package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.action.MoveAction;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;

public class MoveActionListener extends AbstractListener {
   public MoveAction action;

   @Override
   public void enterMoveaction(mailcradleParser.MoveactionContext ctx) {
      action = new MoveAction();
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         action.setTargetFolderName(stringListener.string);
      }
   }
}
