package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.domain.term.*;

public class FromTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterFromterm(mailsortParser.FromtermContext ctx) {
		term = new FromTerm();
	}
}
