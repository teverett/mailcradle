package com.khubla.kmailsorter;

import java.io.*;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.listener.*;

public class MailsortMarshaller {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailsortMarshaller.class);

	public static Mailsort importRules(File file) throws IOException {
		/*
		 * list of imported files
		 */
		final List<String> imported = new ArrayList<String>();
		/*
		 * import
		 */
		return importRules(file, imported);
	}

	private static Mailsort importRules(File file, List<String> imported) throws IOException {
		if (null != file) {
			/*
			 * output
			 */
			System.out.println("Reading: " + file.getAbsolutePath());
			/*
			 * make Lexer
			 */
			final Lexer lexer = new mailsortLexer(CharStreams.fromStream(new FileInputStream(file)));
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
				final File file = new File(importName);
				if (file.exists()) {
					final Mailsort subFile = importRules(file, imported);
					mailsort.merge(subFile);
				} else {
					logger.fatal("Unable to import file: " + importName);
				}
			}
		}
	}
}
