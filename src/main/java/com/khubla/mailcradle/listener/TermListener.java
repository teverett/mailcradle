package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.listener.term.*;

public class TermListener extends AbstractListener {
	public Term term;

	@Override
	public void enterTerm(mailcradleParser.TermContext ctx) {
		if (null != ctx.subjecterm()) {
			final SubjectTermListener subjectTermListener = new SubjectTermListener();
			subjectTermListener.enterSubjecterm(ctx.subjecterm());
			term = subjectTermListener.term;
		} else if (null != ctx.bodyterm()) {
			final BodyTermListener bodyTermListener = new BodyTermListener();
			bodyTermListener.enterBodyterm(ctx.bodyterm());
			term = bodyTermListener.term;
		} else if (null != ctx.headerterm()) {
			final HeaderTermListener headerTermListener = new HeaderTermListener();
			headerTermListener.enterHeaderterm(ctx.headerterm());
			term = headerTermListener.term;
		} else if (null != ctx.fromterm()) {
			final FromTermListener fromTermListener = new FromTermListener();
			fromTermListener.enterFromterm(ctx.fromterm());
			term = fromTermListener.term;
		}
	}
}
