package com.khubla.kmailsorter.comparison;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;

public interface SieveComparison {
	boolean compare(Test test, Message message) throws MessagingException;
}
