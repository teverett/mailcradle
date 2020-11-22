package com.khubla.kmailsorter.listener;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.mailsortParser.*;
import com.khubla.kmailsorter.domain.*;

public class MailsortListener extends AbstractListener {
	public Mailsort mailsort = null;

	@Override
	public void enterMailsort(mailsortParser.MailsortContext ctx) {
		mailsort = new Mailsort();
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
		}
	}
}
