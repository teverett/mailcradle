package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.domain.term.*;

public class SubjectTermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterSubjecterm(mailsortParser.SubjectermContext ctx) {
		term = new SubjectTerm();
	}
}
