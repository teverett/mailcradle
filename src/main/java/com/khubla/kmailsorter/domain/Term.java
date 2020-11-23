package com.khubla.kmailsorter.domain;

import java.io.*;

import javax.mail.*;

import com.khubla.kmailsorter.util.*;

public abstract class Term {
	abstract public String[] resolve(MessageData messageData) throws MessagingException, IOException;
}
