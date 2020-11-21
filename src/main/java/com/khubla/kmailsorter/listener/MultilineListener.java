package com.khubla.kmailsorter.listener;

import org.antlr.v4.runtime.tree.*;

import com.khubla.kmailsorter.*;
import com.khubla.kmailsorter.sieveParser.*;

public class MultilineListener extends AbstractListener {
	public String string;

	@Override
	public void enterMultiline(sieveParser.MultilineContext ctx) {
		string = "";
		for (final ParseTree parseTree : ctx.children) {
			if (parseTree instanceof MultilinedotstartContext) {
				final MultilinedotstartListener multilinedotstartListener = new MultilinedotstartListener();
				multilinedotstartListener.enterMultilinedotstart((MultilinedotstartContext) parseTree);
				string += multilinedotstartListener.string;
			} else if (parseTree instanceof MultilineliteralContext) {
				final MultilineliteralListener multilineliteralListener = new MultilineliteralListener();
				multilineliteralListener.enterMultilineliteral((MultilineliteralContext) parseTree);
				string += multilineliteralListener.string;
			}
		}
	}
}
