package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.domain.term.BodyTerm;
import com.khubla.mailcradle.listener.AbstractListener;

public class BodyTermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterBodyterm(mailcradleParser.BodytermContext ctx) {
      term = new BodyTerm();
   }
}
