package com.khubla.mailcradle;

import java.io.*;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;

public class MailCradleRunner implements IMAPMessageCallback, IMAPEventNotification {
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
	}

	@Override
	public void event() throws MessagingException, IOException {
		/*
		 * run filters
		 */
		runFilters(inbox);
	}

	/**
	 * run filters on all listed folders
	 *
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void filterAllFolders() throws MessagingException, IOException {
		for (final String folderName : MailCradleConfiguration.getInstance().getImapFolders()) {
			System.out.println("Folder: " + folderName);
			runFilters(folderName);
		}
	}

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

	/**
	 * run the mailcradle, which means processing the folders in the list and then idle-ing on the
	 * INBOX
	 */
	public void run() {
		try {
			/*
			 * run all filters
			 */
			filterAllFolders();
			/*
			 * idle
			 */
			IMAPUtil.getInstance().idle("INBOX", this);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * run all filter commands on a folder
	 *
	 * @param inbox IMAP inbox
	 * @param mailsort mailsort rules
	 * @throws MessagingException potential exception
	 * @throws IOException
	 */
	private void runFilters(String folderName) throws MessagingException, IOException {
		/*
		 * get the uids
		 */
		IMAPUtil.getInstance().iterateMessages(folderName, this);
		System.out.println();
		System.out.println("Done");
	}
}
