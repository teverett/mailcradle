package com.khubla.kmailsorter.listener;

import org.antlr.v4.runtime.tree.*;

import com.khubla.kmailsorter.*;

public class MultilineliteralListener extends AbstractListener {
	public String string;

	@Override
	public void enterMultilineliteral(sieveParser.MultilineliteralContext ctx) {
		string = "";
		if (null != ctx.OCTETNOTPERIOD()) {
			string += ctx.OCTETNOTPERIOD().getText();
		}
		if (null != ctx.OCTETNOTCRLF()) {
			for (final TerminalNode terminalNode : ctx.OCTETNOTCRLF()) {
				string += terminalNode.getText();
			}
		}
	}
}
