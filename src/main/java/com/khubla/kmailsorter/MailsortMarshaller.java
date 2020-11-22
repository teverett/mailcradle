package com.khubla.kmailsorter;

import java.io.*;
import java.util.*;

import org.antlr.v4.runtime.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.listener.*;

public class MailsortMarshaller {
	public static Mailsort importRules(InputStream inputStream) throws IOException {
		/*
		 * list of imported files
		 */
		final List<String> imported = new ArrayList<String>();
		/*
		 * import
		 */
		return importRules(inputStream, imported);
	}

	private static Mailsort importRules(InputStream inputStream, List<String> imported) throws IOException {
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
			 * process imports
			 */
			processImports(mailsorttListener.mailsort, imported);
			/*
			 * done
			 */
			return mailsorttListener.mailsort;
		} else {
			return null;
		}
	}

	private static void processImports(Mailsort mailsort, List<String> imported) throws IOException {
		for (final String importName : mailsort.getImports()) {
			if (false == imported.contains(importName)) {
				imported.add(importName);
				final FileInputStream fis = new FileInputStream(importName);
				final Mailsort subFile = importRules(fis, imported);
				mailsort.merge(subFile);
			}
		}
	}
}
