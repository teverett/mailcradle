package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.domain.term.*;

public class BodyTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterBodyterm(mailsortParser.BodytermContext ctx) {
		term = new BodyTerm();
	}
}
