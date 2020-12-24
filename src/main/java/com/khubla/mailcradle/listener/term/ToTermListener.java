package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.domain.term.ToTerm;
import com.khubla.mailcradle.listener.AbstractListener;

public class ToTermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterToterm(mailcradleParser.TotermContext ctx) {
      term = new ToTerm();
   }
}
