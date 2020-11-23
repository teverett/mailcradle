package com.khubla.mailcradle;

import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.*;
import org.apache.commons.configuration2.builder.fluent.*;
import org.apache.commons.configuration2.convert.*;
import org.apache.logging.log4j.*;

public class MailCradleConfiguration {
	/**
	 * singleton
	 */
	private static MailCradleConfiguration instance;
	/**
	 * filename
	 */
	public static String propertiesFile = "mailcradle.properties";
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(MailCradleConfiguration.class);

	/**
	 * singleton getter
	 */
	public static MailCradleConfiguration getInstance() {
		if (null == instance) {
			instance = new MailCradleConfiguration();
			try {
				instance.load(propertiesFile);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * IMAP host
	 */
	private String imapHost;
	/**
	 * IMAP username
	 */
	private String imapUsername;
	/**
	 * IMAP password
	 */
	private String imapPassword;
	/**
	 * mailsort file
	 */
	private String mailsortFile;
	/**
	 * IMAP folder
	 */
	private String imapFolder;
	/**
	 * SMTP host
	 */
	private String smtpHost;
	/**
	 * SMTP username
	 */
	private String smtpUsername;
	/**
	 * SMTP password
	 */
	private String smtpPassword;
	/**
	 * SMTP from
	 */
	private String smtpFrom;
	/**
	 * SMTP port
	 */
	private Integer smtpPort;

	/**
	 * ctor
	 */
	private MailCradleConfiguration() {
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

	public String getSmtpFrom() {
		return smtpFrom;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
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
			smtpHost = configuration.get(String.class, "smtp.host");
			smtpUsername = configuration.get(String.class, "smtp.username");
			smtpPassword = configuration.get(String.class, "smtp.password");
			smtpFrom = configuration.get(String.class, "smtp.from");
			smtpPort = configuration.get(Integer.class, "smtp.port");
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

	public void setSmtpFrom(String smtpFrom) {
		this.smtpFrom = smtpFrom;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}
}