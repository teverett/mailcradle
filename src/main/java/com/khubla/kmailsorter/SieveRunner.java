package com.khubla.kmailsorter;

import java.io.*;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;

public class SieveRunner {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(SieveRunner.class);

	private Folder getInbox() throws MessagingException {
		final Session session = Session.getDefaultInstance(System.getProperties(), null);
		final Store store = session.getStore("imaps");
		store.connect(KMailSorterConfiguration.getInstance().getImapHost(), KMailSorterConfiguration.getInstance().getImapUsername(), KMailSorterConfiguration.getInstance().getImapPassword());
		return store.getDefaultFolder();
	}

	private void runRules(Folder inbox, Sieve sieve) {
	}

	public void runSieveFile(File sieveFile) {
		Folder inbox = null;
		try {
			/*
			 * read sieve file
			 */
			final Sieve sieve = SieveMarshaller.importSieveRules(new FileInputStream(sieveFile));
			if (null != sieve) {
				System.out.println("Read sieve file: " + sieveFile.getName());
			}
			/*
			 * get inbox
			 */
			inbox = getInbox();
			/*
			 * run rules
			 */
			runRules(inbox, sieve);
		} catch (final Exception e) {
			logger.error(e);
		} finally {
			if (null != inbox) {
				try {
					inbox.close();
				} catch (final Exception e) {
					logger.info(e);
				}
			}
		}
	}
}
