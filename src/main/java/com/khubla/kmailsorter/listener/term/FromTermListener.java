package com.khubla.kmailsorter.listener.term;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.domain.term.*;
import com.khubla.kmailsorter.listener.*;

public class FromTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterFromterm(mailsortParser.FromtermContext ctx) {
		term = new FromTerm();
	}
}
