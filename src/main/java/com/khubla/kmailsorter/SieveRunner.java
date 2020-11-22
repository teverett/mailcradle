package com.khubla.kmailsorter;

import java.io.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;

public class SieveRunner {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(SieveRunner.class);

	public void runSieveFile(File sieveFile) {
		try {
			/*
			 * read sieve file
			 */
			final Sieve sieve = SieveMarshaller.importSieveRules(new FileInputStream(sieveFile));
			if (null != sieve) {
				System.out.println("Read sieve file: " + sieveFile.getName());
			}
		} catch (final Exception e) {
			logger.error(e);
		}
	}
}
