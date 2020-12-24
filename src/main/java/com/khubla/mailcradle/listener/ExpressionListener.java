package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Expression;
import com.khubla.mailcradle.domain.expression.ConditionExpression;
import com.khubla.mailcradle.domain.expression.LogicalExpression;
import com.khubla.mailcradle.domain.expression.NotExpression;
import com.khubla.mailcradle.domain.expression.ParentheticalExpression;

public class ExpressionListener extends AbstractListener {
   public Expression expression;

   @Override
   public void enterExpression(mailcradleParser.ExpressionContext ctx) {
      if (ctx.NOT() != null) {
         /*
          * not
          */
         final ExpressionListener expressionListener = new ExpressionListener();
         expressionListener.enterExpression(ctx.expression(0));
         expression = new NotExpression(expressionListener.expression);
      } else if (null != ctx.condition()) {
         /*
          * condition
          */
         final ConditionListener conditionListener = new ConditionListener();
         conditionListener.enterCondition(ctx.condition());
         expression = new ConditionExpression(conditionListener.condition);
      } else if (null != ctx.logical()) {
         final ExpressionListener expressionListener1 = new ExpressionListener();
         expressionListener1.enterExpression(ctx.expression(0));
         final ExpressionListener expressionListener2 = new ExpressionListener();
         expressionListener2.enterExpression(ctx.expression(1));
         final LogicalListener logicalListener = new LogicalListener();
         logicalListener.enterLogical(ctx.logical());
         expression = new LogicalExpression(expressionListener1.expression, expressionListener2.expression, logicalListener.logical);
      } else {
         final ExpressionListener expressionListener = new ExpressionListener();
         expressionListener.enterExpression(ctx.expression(0));
         expression = new ParentheticalExpression(expressionListener.expression);
      }
   }
}
