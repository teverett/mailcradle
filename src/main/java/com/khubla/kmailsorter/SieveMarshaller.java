package com.khubla.kmailsorter;

import java.io.*;

import org.antlr.v4.runtime.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.listener.*;

public class SieveMarshaller {
	public static Sieve importSieveRules(InputStream inputStream) throws IOException {
		if (null != inputStream) {
			/*
			 * make Lexer
			 */
			final Lexer lexer = new sieveLexer(CharStreams.fromStream(inputStream));
			/*
			 * get a TokenStream on the Lexer
			 */
			final TokenStream tokenStream = new CommonTokenStream(lexer);
			/*
			 * make a Parser on the token stream
			 */
			final sieveParser sieveParser = new sieveParser(tokenStream);
			/*
			 * listener
			 */
			final SieveListener sieveListener = new SieveListener();
			/*
			 * parse
			 */
			sieveListener.enterStart(sieveParser.start());
			/*
			 * done
			 */
			return sieveListener.sieve;
		} else {
			return null;
		}
	}
}
