package com.khubla.kmailsorter.domain.term;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.util.*;

public class FromTerm extends Term {
	@Override
	public String[] resolve(MessageData messageData) throws MessagingException, IOException {
		return messageData.getFrom();
	}
}
