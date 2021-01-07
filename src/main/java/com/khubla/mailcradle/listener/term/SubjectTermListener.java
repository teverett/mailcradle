package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.domain.term.*;
import com.khubla.mailcradle.listener.*;

public class SubjectTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterSubjecterm(mailcradleParser.SubjectermContext ctx) {
		term = new SubjectTerm();
	}
}
