package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.domain.term.BCCTerm;
import com.khubla.mailcradle.listener.AbstractListener;

public class BCCTermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterBccterm(mailcradleParser.BcctermContext ctx) {
      term = new BCCTerm();
   }
}
