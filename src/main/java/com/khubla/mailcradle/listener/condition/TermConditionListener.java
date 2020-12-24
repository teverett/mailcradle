package com.khubla.mailcradle.listener.condition;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.condition.TermCondition;
import com.khubla.mailcradle.listener.AbstractListener;
import com.khubla.mailcradle.listener.StringListener;
import com.khubla.mailcradle.listener.TermListener;
import com.khubla.mailcradle.listener.TermRelationListener;

public class TermConditionListener extends AbstractListener {
   public TermCondition condition;

   @Override
   public void enterTermcondition(mailcradleParser.TermconditionContext ctx) {
      condition = new TermCondition();
      /**
       * term
       */
      if (null != ctx.term()) {
         final TermListener termListener = new TermListener();
         termListener.enterTerm(ctx.term());
         condition.setTerm(termListener.term);
      }
      /*
       * relation
       */
      if (null != ctx.termrelation()) {
         final TermRelationListener termRelationListener = new TermRelationListener();
         termRelationListener.enterTermrelation(ctx.termrelation());
         condition.setTermRelation(termRelationListener.termRelation);
      }
      /*
       * value
       */
      if (null != ctx.string()) {
         final StringListener stringListener = new StringListener();
         stringListener.enterString(ctx.string());
         condition.setValue(stringListener.string);
      }
   }
}
