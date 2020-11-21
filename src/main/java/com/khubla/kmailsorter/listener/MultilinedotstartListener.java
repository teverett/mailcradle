package com.khubla.kmailsorter.listener;

import org.antlr.v4.runtime.tree.*;

import com.khubla.kmailsorter.*;

public class MultilinedotstartListener extends AbstractListener {
	public String string;

	@Override
	public void enterMultilinedotstart(sieveParser.MultilinedotstartContext ctx) {
		string = "";
		if (null != ctx.OCTETNOTCRLF()) {
			for (final TerminalNode terminalNode : ctx.OCTETNOTCRLF()) {
				string += terminalNode.getText();
			}
		}
	}
}
