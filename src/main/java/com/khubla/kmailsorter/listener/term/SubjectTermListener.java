package com.khubla.kmailsorter.listener.term;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.domain.term.*;
import com.khubla.kmailsorter.listener.*;

public class SubjectTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterSubjecterm(mailsortParser.SubjectermContext ctx) {
		term = new SubjectTerm();
	}
}
