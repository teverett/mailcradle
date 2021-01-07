package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.domain.term.*;
import com.khubla.mailcradle.listener.*;

public class FromTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterFromterm(mailcradleParser.FromtermContext ctx) {
		term = new FromTerm();
	}
}
