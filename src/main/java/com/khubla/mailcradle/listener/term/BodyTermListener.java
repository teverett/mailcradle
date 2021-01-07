package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.domain.term.*;
import com.khubla.mailcradle.listener.*;

public class BodyTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterBodyterm(mailcradleParser.BodytermContext ctx) {
		term = new BodyTerm();
	}
}
