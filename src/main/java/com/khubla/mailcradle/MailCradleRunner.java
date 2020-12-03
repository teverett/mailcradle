package com.khubla.mailcradle;

import java.io.*;
import java.util.*;

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

	/**
	 * run filters on all listed folders
	 *
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void filterAllFolders() throws MessagingException, IOException {
		for (final String folderName : MailCradleConfiguration.getInstance().getImapCrawlFolders()) {
			if (folderName.endsWith(".*")) {
				final String fn = folderName.substring(0, folderName.length() - 2);
				runFilters(fn);
				final IMAPFolderUtil imapFolderUtil = FolderFactory.getInstance().getFolder(fn);
				final List<String> subFolders = imapFolderUtil.getChildFolders();
				if (null != subFolders) {
					for (final String name : subFolders) {
						runFilters(name);
					}
				}
			} else {
				runFilters(folderName);
			}
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
			FolderFactory.getInstance().getFolder(inbox).idle(this);
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
		System.out.println("Folder: " + folderName);
		/*
		 * get the uids
		 */
		final IMAPFolderUtil imapFolderUtil = FolderFactory.getInstance().getFolder(folderName);
		imapFolderUtil.iterateMessages(this);
		/*
		 * on servers that don't support move, we have copy & delete, so need an expunge
		 */
		imapFolderUtil.expunge();
		System.out.println();
		System.out.println("Done");
	}
}
