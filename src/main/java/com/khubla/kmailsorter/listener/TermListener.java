package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.domain.*;

public class TermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterTerm(mailsortParser.TermContext ctx) {
		if (null != ctx.subjecterm()) {
			final SubjectTermListener subjectTermListener = new SubjectTermListener();
			subjectTermListener.enterSubjecterm(ctx.subjecterm());
			term = subjectTermListener.term;
		} else if (null != ctx.bodyterm()) {
			final BodyTermListener bodyTermListener = new BodyTermListener();
			bodyTermListener.enterBodyterm(ctx.bodyterm());
			term = bodyTermListener.term;
		}
		if (null != ctx.headerterm()) {
			final HeaderTermListener headerTermListener = new HeaderTermListener();
			headerTermListener.enterHeaderterm(ctx.headerterm());
			term = headerTermListener.term;
		}
	}
}
