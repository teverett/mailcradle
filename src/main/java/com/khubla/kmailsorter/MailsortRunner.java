package com.khubla.kmailsorter;

import java.io.*;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;

public class MailsortRunner {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailsortRunner.class);

	private Folder getInbox() throws MessagingException {
		final Session session = Session.getDefaultInstance(System.getProperties(), null);
		final Store store = session.getStore("imaps");
		store.connect(KMailSorterConfiguration.getInstance().getImapHost(), KMailSorterConfiguration.getInstance().getImapUsername(), KMailSorterConfiguration.getInstance().getImapPassword());
		final Folder root = store.getDefaultFolder();
		return root.getFolder("INBOX");
	}

	/**
	 * run all filter commands
	 *
	 * @param inbox IMAP inbox
	 * @param mailsort mailsort rules
	 * @throws MessagingException
	 */
	private void runFilters(Folder inbox, Mailsort mailsort) throws MessagingException {
		final int messageCount = inbox.getMessageCount();
		if (messageCount > 0) {
			for (final Message messsage : inbox.getMessages()) {
				// for (final Command command : sieve.getCommands()) {
				// runCommand(messsage, command);
				// }
			}
		}
	}

	public void runMailsortFile(File mailsortFile) {
		Folder inbox = null;
		try {
			/*
			 * read mailsort file
			 */
			final Mailsort mailsort = MailsortMarshaller.importRules(new FileInputStream(mailsortFile));
			if (null != mailsort) {
				System.out.println("Read mailsort file: " + mailsortFile.getAbsolutePath() + " which contains " + mailsort.size() + " commands");
			}
			/*
			 * get inbox
			 */
			inbox = getInbox();
			if (null != inbox) {
				inbox.open(Folder.READ_WRITE);
				if (inbox.isOpen()) {
					/*
					 * run filters
					 */
					runFilters(inbox, mailsort);
				} else {
					System.out.println("Could not open inbox at: " + KMailSorterConfiguration.getInstance().getImapHost());
				}
			} else {
				System.out.println("Could not connect to inbox at: " + KMailSorterConfiguration.getInstance().getImapHost());
			}
		} catch (final Exception e) {
			e.printStackTrace();
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
