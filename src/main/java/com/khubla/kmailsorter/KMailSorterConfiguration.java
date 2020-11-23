package com.khubla.kmailsorter;

import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.*;
import org.apache.commons.configuration2.builder.fluent.*;
import org.apache.commons.configuration2.convert.*;
import org.apache.logging.log4j.*;

public class KMailSorterConfiguration {
	/**
	 * singleton
	 */
	private static KMailSorterConfiguration instance;
	/**
	 * filename
	 */
	public static String propertiesFile = "kmailsorter.properties";
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(KMailSorterConfiguration.class);

	/**
	 * singleton getter
	 */
	public static KMailSorterConfiguration getInstance() {
		if (null == instance) {
			instance = new KMailSorterConfiguration();
			try {
				instance.load(propertiesFile);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * imap host
	 */
	private String imapHost;
	/**
	 * imap username
	 */
	private String imapUsername;
	/**
	 * imap password
	 */
	private String imapPassword;
	/**
	 * mailsort file
	 */
	private String mailsortFile;
	/**
	 * folder
	 */
	private String imapFolder;

	/**
	 * ctor
	 */
	private KMailSorterConfiguration() {
	}

	public String getImapFolder() {
		return imapFolder;
	}

	public String getImapHost() {
		return imapHost;
	}

	public String getImapPassword() {
		return imapPassword;
	}

	public String getImapUsername() {
		return imapUsername;
	}

	public String getMailsortFile() {
		return mailsortFile;
	}

	private void load(String propertiesFile) throws Exception {
		try {
			final Parameters params = new Parameters();
			final FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
					.configure(params.properties().setFileName(propertiesFile).setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
			/*
			 * get config
			 */
			final Configuration configuration = builder.getConfiguration();
			/*
			 * parameters
			 */
			imapHost = configuration.get(String.class, "imap.host");
			imapUsername = configuration.get(String.class, "imap.username");
			imapPassword = configuration.get(String.class, "imap.password");
			imapFolder = configuration.get(String.class, "imap.folder");
			mailsortFile = configuration.get(String.class, "mailsortFile");
		} catch (final Exception e) {
			logger.error(e);
			throw e;
		}
	}

	public void setImapFolder(String imapFolder) {
		this.imapFolder = imapFolder;
	}

	public void setImapHost(String imapHost) {
		this.imapHost = imapHost;
	}

	public void setImapPassword(String imapPassword) {
		this.imapPassword = imapPassword;
	}

	public void setImapUsername(String imapUsername) {
		this.imapUsername = imapUsername;
	}

	public void setMailsortFile(String mailsortFile) {
		this.mailsortFile = mailsortFile;
	}
}