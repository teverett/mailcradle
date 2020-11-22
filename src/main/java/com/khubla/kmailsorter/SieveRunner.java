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
		final Folder root = store.getDefaultFolder();
		return root.getFolder("INBOX");
	}

	/**
	 * run a single sieve command
	 *
	 * @param inbox IMAP inbox
	 * @param sieve sieve rules
	 * @throws MessagingException
	 */
	private void runCommand(Message message, Command command) throws MessagingException {
		logger.info("Running command: " + command.getName() + " on message " + message.getMessageNumber());
	}

	/**
	 * run all sieve commands
	 *
	 * @param inbox IMAP inbox
	 * @param sieve sieve rules
	 * @throws MessagingException
	 */
	private void runCommmands(Folder inbox, Sieve sieve) throws MessagingException {
		final int messageCount = inbox.getMessageCount();
		if (messageCount > 0) {
			for (final Message messsage : inbox.getMessages()) {
				for (final Command command : sieve.getCommands()) {
					runCommand(messsage, command);
				}
			}
		}
	}

	public void runSieveFile(File sieveFile) {
		Folder inbox = null;
		try {
			/*
			 * read sieve file
			 */
			final Sieve sieve = SieveMarshaller.importSieveRules(new FileInputStream(sieveFile));
			if (null != sieve) {
				System.out.println("Read sieve file: " + sieveFile.getAbsolutePath() + " which contains " + sieve.size() + " commands");
			}
			/*
			 * get inbox
			 */
			inbox = getInbox();
			if (null != inbox) {
				inbox.open(Folder.READ_WRITE);
				if (inbox.isOpen()) {
					/*
					 * run sieve commands
					 */
					runCommmands(inbox, sieve);
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
