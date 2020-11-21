package com.khubla.kmailsorter.listener;

import java.util.*;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.sieveParser.*;

public class StringListListener extends AbstractListener {
	public List<String> strings;

	@Override
	public void enterStringlist(sieveParser.StringlistContext ctx) {
		strings = new ArrayList<String>();
		if (null != ctx.string()) {
			for (final StringContext stringContext : ctx.string()) {
				final StringListener stringListener = new StringListener();
				stringListener.enterString(stringContext);
				strings.add(stringListener.string);
			}
		}
	}
}
