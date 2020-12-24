package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.listener.term.BCCTermListener;
import com.khubla.mailcradle.listener.term.BodyTermListener;
import com.khubla.mailcradle.listener.term.CCTermListener;
import com.khubla.mailcradle.listener.term.FromTermListener;
import com.khubla.mailcradle.listener.term.HeaderTermListener;
import com.khubla.mailcradle.listener.term.SubjectTermListener;
import com.khubla.mailcradle.listener.term.ToTermListener;

public class TermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterTerm(mailcradleParser.TermContext ctx) {
      if (null != ctx.subjecterm()) {
         final SubjectTermListener subjectTermListener = new SubjectTermListener();
         subjectTermListener.enterSubjecterm(ctx.subjecterm());
         term = subjectTermListener.term;
      } else if (null != ctx.bodyterm()) {
         final BodyTermListener bodyTermListener = new BodyTermListener();
         bodyTermListener.enterBodyterm(ctx.bodyterm());
         term = bodyTermListener.term;
      } else if (null != ctx.headerterm()) {
         final HeaderTermListener headerTermListener = new HeaderTermListener();
         headerTermListener.enterHeaderterm(ctx.headerterm());
         term = headerTermListener.term;
      } else if (null != ctx.fromterm()) {
         final FromTermListener fromTermListener = new FromTermListener();
         fromTermListener.enterFromterm(ctx.fromterm());
         term = fromTermListener.term;
      } else if (null != ctx.toterm()) {
         final ToTermListener toTermListener = new ToTermListener();
         toTermListener.enterToterm(ctx.toterm());
         term = toTermListener.term;
      } else if (null != ctx.ccterm()) {
         final CCTermListener ccTermListener = new CCTermListener();
         ccTermListener.enterCcterm(ctx.ccterm());
         term = ccTermListener.term;
      } else if (null != ctx.bccterm()) {
         final BCCTermListener bccTermListener = new BCCTermListener();
         bccTermListener.enterBccterm(ctx.bccterm());
         term = bccTermListener.term;
      }
   }
}
