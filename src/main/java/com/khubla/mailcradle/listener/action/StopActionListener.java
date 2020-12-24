package com.khubla.mailcradle.listener.action;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.action.StopAction;
import com.khubla.mailcradle.listener.AbstractListener;

public class StopActionListener extends AbstractListener {
   public StopAction action;

   @Override
   public void enterStopaction(mailcradleParser.StopactionContext ctx) {
      action = new StopAction();
   }
}
