package com.khubla.kmailsorter.domain;

import java.io.*;

import javax.mail.*;

public abstract class Term {
	abstract public String[] resolve(Message message) throws MessagingException, IOException;
}
