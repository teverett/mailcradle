package com.khubla.kmailsorter.listener.term;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.domain.term.*;
import com.khubla.kmailsorter.listener.*;

public class BodyTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterBodyterm(mailsortParser.BodytermContext ctx) {
		term = new BodyTerm();
	}
}
