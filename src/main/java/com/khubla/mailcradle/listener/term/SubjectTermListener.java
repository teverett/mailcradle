package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.mailcradleParser;
import com.khubla.mailcradle.domain.Term;
import com.khubla.mailcradle.domain.term.SubjectTerm;
import com.khubla.mailcradle.listener.AbstractListener;

public class SubjectTermListener extends AbstractListener {
   public Term term;

   @Override
   public void enterSubjecterm(mailcradleParser.SubjectermContext ctx) {
      term = new SubjectTerm();
   }
}
