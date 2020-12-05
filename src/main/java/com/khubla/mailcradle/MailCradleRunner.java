package com.khubla.mailcradle;

import java.io.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class MailCradleRunner {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailCradleRunner.class);
	/**
	 * the mailsort data
	 */
	private final Mailcradle mailsort;
	/**
	 * inbox
	 */
	private final String inbox;
	/**
	 * message filter
	 */
	private final MessageFilter messageFilter;
	/*
	 * crawler
	 */
	private final MailCradleCrawler mailCradleCrawler;

	public MailCradleRunner(File mailsortFile) throws IOException {
		super();
		/*
		 * get inbox
		 */
		inbox = MailCradleConfiguration.getInstance().getImapINBOX();
		/*
		 * read mailsort file
		 */
		mailsort = MailCradleMarshaller.importRules(mailsortFile);
		if (null != mailsort) {
			System.out.println("Read mailsort file: " + mailsortFile.getAbsolutePath() + " which contains " + mailsort.size() + " filters spanning " + mailsort.totalListItems() + " list items");
		}
		messageFilter = new MessageFilter(mailsort);
		mailCradleCrawler = new MailCradleCrawler(mailsort);
	}

	/**
	 * run the mailcradle, which means processing the folders in the list and then idle-ing on the
	 * INBOX
	 */
	public void run() {
		try {
			/*
			 * run all filters
			 */
			mailCradleCrawler.filterAllFolders();
			/*
			 * idle
			 */
			FolderFactory.getInstance().getFolder(inbox).idle(messageFilter);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}
