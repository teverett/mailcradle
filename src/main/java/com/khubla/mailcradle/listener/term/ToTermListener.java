package com.khubla.mailcradle.listener.term;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.domain.term.*;
import com.khubla.mailcradle.listener.*;

public class ToTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterToterm(mailcradleParser.TotermContext ctx) {
		term = new ToTerm();
	}
}
