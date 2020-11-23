package com.khubla.kmailsorter;

import java.io.*;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.util.*;

public class MailsortRunner {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailsortRunner.class);

	/**
	 * run all filter commands
	 *
	 * @param inbox IMAP inbox
	 * @param mailsort mailsort rules
	 * @throws MessagingException potential exception
	 * @throws IOException
	 */
	private void runFilters(Mailsort mailsort) throws MessagingException, IOException {
		final int messageCount = MailUtil.getMessageCount();
		if (messageCount > 0) {
			for (int i = 1; i < (messageCount + 1); i++) {
				final MessageData messageData = MailUtil.getMessageData(i);
				for (final Filter filter : mailsort.getFilters()) {
					filter.execute(messageData, mailsort);
				}
			}
		}
	}

	public void runMailsortFile(File mailsortFile) {
		final Folder inbox = null;
		try {
			/*
			 * read mailsort file
			 */
			final Mailsort mailsort = MailsortMarshaller.importRules(mailsortFile);
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
