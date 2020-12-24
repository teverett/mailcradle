package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.domain.term.CCTerm;
import com.khubla.mailcradle.listener.AbstractListener;

public class CCTermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterCcterm(mailcradleParser.CctermContext ctx) {
      term = new CCTerm();
   }
}
