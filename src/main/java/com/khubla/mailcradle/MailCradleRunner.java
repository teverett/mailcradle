package com.khubla.mailcradle;

import java.io.*;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class MailCradleRunner implements IMAPMessageCallback {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailCradleRunner.class);
	/**
	 * the mailsort data
	 */
	private Mailcradle mailsort;

	@Override
	public void message(IMAPMessageData imapMessageData) throws MessagingException, IOException {
		/*
		 * process message
		 */
		if (null != imapMessageData) {
			for (final Filter filter : mailsort.getFilters()) {
				filter.execute(imapMessageData, mailsort);
			}
		}
	}

	private void runFilters(Mailcradle mailsort) throws MessagingException, IOException {
		for (final String folderName : MailCradleConfiguration.getInstance().getImapFolders()) {
			System.out.println("Folder: " + folderName);
			runFilters(folderName, mailsort);
		}
	}

	/**
	 * run all filter commands
	 *
	 * @param inbox IMAP inbox
	 * @param mailsort mailsort rules
	 * @throws MessagingException potential exception
	 * @throws IOException
	 */
	private void runFilters(String folderName, Mailcradle mailsort) throws MessagingException, IOException {
		/*
		 * get the uids
		 */
		IMAPUtil.getInstance().iterateMessages(folderName, this);
		System.out.println();
		System.out.println("Done");
	}

	public void runMailsortFile(File mailsortFile) {
		final Folder inbox = null;
		try {
			/*
			 * read mailsort file
			 */
			mailsort = MailCradleMarshaller.importRules(mailsortFile);
			if (null != mailsort) {
				System.out.println("Read mailsort file: " + mailsortFile.getAbsolutePath() + " which contains " + mailsort.size() + " filters spanning " + mailsort.totalListItems() + " list items");
			}
			/*
			 * run filters
			 */
			runFilters(mailsort);
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
