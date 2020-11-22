package com.khubla.kmailsorter.action;

import com.khubla.kmailsorter.action.impl.*;

public class SieveActionFactory {
	public static SieveAction getAction(SieveActionType sieveActionType) {
		switch (sieveActionType) {
			case keep:
				return new KeepSieveActionType();
			case redirect:
				return new RedirectSieveActionType();
			case fileinto:
				return new FileintoSieveActionType();
			case discard:
				return new DiscardSieveActionType();
			default:
				return null;
		}
	}
}
