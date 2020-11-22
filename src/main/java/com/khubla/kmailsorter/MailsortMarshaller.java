package com.khubla.kmailsorter;

import java.io.*;

import org.antlr.v4.runtime.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.listener.*;

public class MailsortMarshaller {
	public static Mailsort importRules(InputStream inputStream) throws IOException {
		if (null != inputStream) {
			/*
			 * make Lexer
			 */
			final Lexer lexer = new mailsortLexer(CharStreams.fromStream(inputStream));
			/*
			 * get a TokenStream on the Lexer
			 */
			final TokenStream tokenStream = new CommonTokenStream(lexer);
			/*
			 * make a Parser on the token stream
			 */
			final mailsortParser mailsortParser = new mailsortParser(tokenStream);
			/*
			 * listener
			 */
			final MailsortListener mailsorttListener = new MailsortListener();
			/*
			 * parse
			 */
			mailsorttListener.enterMailsort(mailsortParser.mailsort());
			/*
			 * done
			 */
			return mailsorttListener.mailsort;
		} else {
			return null;
		}
	}
}
