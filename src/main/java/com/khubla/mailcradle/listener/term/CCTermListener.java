package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.domain.term.*;
import com.khubla.mailcradle.listener.*;

public class CCTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterCcterm(mailcradleParser.CctermContext ctx) {
		term = new CCTerm();
	}
}
