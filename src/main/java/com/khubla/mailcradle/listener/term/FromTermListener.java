package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.domain.term.FromTerm;
import com.khubla.mailcradle.listener.AbstractListener;

public class FromTermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterFromterm(mailcradleParser.FromtermContext ctx) {
      term = new FromTerm();
   }
}
