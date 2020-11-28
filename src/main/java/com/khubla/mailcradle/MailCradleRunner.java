package com.khubla.mailcradle;

import java.io.*;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.domain.*;
import com.khubla.mailcradle.imap.*;
import com.khubla.mailcradle.progress.*;

public class MailCradleRunner {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailCradleRunner.class);

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
		System.out.println("Reading Message UIDs");
		final String[] uids = IMAPUtil.getInstance().getUIDs(folderName);
		if (null != uids) {
			/*
			 * process all uids
			 */
			final ProgressCallback progressCallback = new DefaultProgressCallbackImpl(uids.length);
			System.out.println("Processing " + uids.length + " messages");
			if (uids.length > 0) {
				for (final String uid : uids) {
					/*
					 * process message
					 */
					final IMAPMessageData imapMessageData = IMAPUtil.getInstance().getMessageData(folderName, uid);
					for (final Filter filter : mailsort.getFilters()) {
						filter.execute(imapMessageData, mailsort);
					}
					progressCallback.progress();
				}
			}
		}
		System.out.println();
		System.out.println("Done");
	}

	public void runMailsortFile(File mailsortFile) {
		final Folder inbox = null;
		try {
			/*
			 * read mailsort file
			 */
			final Mailcradle mailsort = MailCradleMarshaller.importRules(mailsortFile);
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
