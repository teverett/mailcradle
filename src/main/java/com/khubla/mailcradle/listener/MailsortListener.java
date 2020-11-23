package com.khubla.mailcradle.listener;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.mailcradleParser.*;
import com.khubla.mailcradle.domain.*;

public class MailsortListener extends AbstractListener {
	public Mailcradle mailsort = null;

	@Override
	public void enterMailcradle(mailcradleParser.MailcradleContext ctx) {
		mailsort = new Mailcradle();
		/*
		 * filters
		 */
		if (null != ctx.filter()) {
			for (final FilterContext filterContext : ctx.filter()) {
				final FilterListener filterListener = new FilterListener();
				filterListener.enterFilter(filterContext);
				mailsort.addFilter(filterListener.filter);
			}
			/**
			 * lists
			 */
			if (null != ctx.list()) {
				for (final ListContext listContext : ctx.list()) {
					final ListListener listListener = new ListListener();
					listListener.enterList(listContext);
					mailsort.addList(listListener.stringList);
				}
			}
			/*
			 * imports
			 */
			if (null != ctx.import_()) {
				for (final Import_Context import_Context : ctx.import_()) {
					final ImportListener importListener = new ImportListener();
					importListener.enterImport_(import_Context);
					mailsort.addImport(importListener.filename);
				}
			}
		}
	}
}
