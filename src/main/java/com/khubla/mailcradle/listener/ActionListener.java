package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Action;
import com.khubla.mailcradle.listener.action.FlagActionListener;
import com.khubla.mailcradle.listener.action.ForwardActionListener;
import com.khubla.mailcradle.listener.action.MoveActionListener;
import com.khubla.mailcradle.listener.action.ReplyActionListener;
import com.khubla.mailcradle.listener.action.StopActionListener;
import com.khubla.mailcradle.listener.action.UnflagActionListener;

public class ActionListener extends AbstractListener {
   public Action action;

   @Override
   public void enterAction(mailcradleParser.ActionContext ctx) {
      /**
       * move
       */
      if (null != ctx.moveaction()) {
         final MoveActionListener moveActionListener = new MoveActionListener();
         moveActionListener.enterMoveaction(ctx.moveaction());
         action = moveActionListener.action;
      }
      /**
       * forward
       */
      if (null != ctx.forwardaction()) {
         final ForwardActionListener forwardActionListener = new ForwardActionListener();
         forwardActionListener.enterForwardaction(ctx.forwardaction());
         action = forwardActionListener.action;
      }
      /**
       * reply
       */
      if (null != ctx.replyaction()) {
         final ReplyActionListener replyActionListener = new ReplyActionListener();
         replyActionListener.enterReplyaction(ctx.replyaction());
         action = replyActionListener.action;
      }
      /**
       * flag
       */
      if (null != ctx.flagaction()) {
         final FlagActionListener flagActionListener = new FlagActionListener();
         flagActionListener.enterFlagaction(ctx.flagaction());
         action = flagActionListener.action;
      }
      /**
       * unflag
       */
      if (null != ctx.unflagaction()) {
         final UnflagActionListener unflagActionListener = new UnflagActionListener();
         unflagActionListener.enterUnflagaction(ctx.unflagaction());
         action = unflagActionListener.action;
      }
      /**
       * stop
       */
      if (null != ctx.stopaction()) {
         final StopActionListener stopActionListener = new StopActionListener();
         stopActionListener.enterStopaction(ctx.stopaction());
         action = stopActionListener.action;
      }
   }
}
