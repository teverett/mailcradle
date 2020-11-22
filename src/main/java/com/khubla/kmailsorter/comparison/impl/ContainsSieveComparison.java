package com.khubla.kmailsorter.comparison.impl;

import javax.mail.*;

import com.khubla.kmailsorter.command.*;
import com.khubla.kmailsorter.comparison.*;
import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.message.*;

public class ContainsSieveComparison implements SieveComparison {
	@Override
	public boolean compare(Test test, Message message) throws MessagingException {
		final TestType testType = TestType.fromString(test.getName());
		final String fieldName = test.getArguments().getArguments().get(1).getString();
		final MessageReader messageReader = new MessageReader(message);
		final String fieldValue = messageReader.getMessageFieldValue(testType, fieldName);
		final String compareValue = test.getArguments().getArguments().get(2).getString();
		return (fieldValue.contains(compareValue));
	}
}
