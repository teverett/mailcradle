package com.khubla.mailcradle;

import java.io.*;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.listener.*;

public class MailCradleMarshaller {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailCradleMarshaller.class);

	/**
	 * check that all lists referred to exist
	 *
	 * @param mailsort Mailsort
	 */
	// private static void checkLists(Mailcradle mailsort) {
	// for (final Filter filter : mailsort.getFilters()) {
	// for (final Condition condition : filter.getConditions()) {
	// if (condition instanceof ListCondition) {
	// final String listname = ((ListCondition) condition).getListname();
	// final StringList stringList = mailsort.getList(listname);
	// if (null == stringList) {
	// throw new RuntimeException("List " + listname + " is referred to but does not exist");
	// }
	// }
	// }
	// }
	// }
	public static Mailcradle importRules(File file) throws IOException {
		/*
		 * list of imported files
		 */
		final List<String> imported = new ArrayList<String>();
		/*
		 * import
		 */
		final Mailcradle ret = importRules(file, imported);
		/*
		 * check list names
		 */
		// checkLists(ret);
		/*
		 * done
		 */
		return ret;
	}

	private static Mailcradle importRules(File file, List<String> imported) throws IOException {
		if (null != file) {
			/*
			 * output
			 */
			System.out.println("Reading: " + file.getAbsolutePath());
			/*
			 * make Lexer
			 */
			final Lexer lexer = new mailcradleLexer(CharStreams.fromStream(new FileInputStream(file)));
			/*
			 * get a TokenStream on the Lexer
			 */
			final TokenStream tokenStream = new CommonTokenStream(lexer);
			/*
			 * make a Parser on the token stream
			 */
			final mailcradleParser mailcradleParser = new mailcradleParser(tokenStream);
			/*
			 * listener
			 */
			final MailsortListener mailsorttListener = new MailsortListener();
			/*
			 * parse
			 */
			mailsorttListener.enterMailcradle(mailcradleParser.mailcradle());
			/*
			 * process imports
			 */
			processImports(mailsorttListener.mailsort, imported, file.getAbsoluteFile().getParentFile().toString());
			/*
			 * done
			 */
			return mailsorttListener.mailsort;
		} else {
			return null;
		}
	}

	private static void processImports(Mailcradle mailsort, List<String> imported, String root) throws IOException {
		for (final String importName : mailsort.getImports()) {
			final String filePath = root + File.separator + importName;
			if (false == imported.contains(filePath)) {
				imported.add(importName);
				final File file = new File(filePath);
				if (file.exists()) {
					final Mailcradle subFile = importRules(file, imported);
					mailsort.merge(subFile);
				} else {
					logger.fatal("Unable to import file: " + file.getAbsolutePath());
				}
			}
		}
	}
}
