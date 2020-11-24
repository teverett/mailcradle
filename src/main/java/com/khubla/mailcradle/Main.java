package com.khubla.mailcradle;

import java.io.*;

import org.apache.commons.cli.*;

public class Main {
	/**
	 * file option
	 */
	private static final String CONFIG_OPTION = "config";

	public static void main(String[] args) {
		try {
			System.out.println("khubla.com MailCradle");
			/*
			 * options
			 */
			final Options options = new Options();
			final Option oo = Option.builder().argName(CONFIG_OPTION).longOpt(CONFIG_OPTION).type(String.class).hasArg().required(true).desc("configuration properties file").build();
			options.addOption(oo);
			/*
			 * parse
			 */
			final CommandLineParser parser = new DefaultParser();
			CommandLine cmd = null;
			try {
				cmd = parser.parse(options, args);
			} catch (final Exception e) {
				e.printStackTrace();
				final HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("posix", options);
				System.exit(0);
			}
			/*
			 * get the config file
			 */
			final String configFilename = cmd.getOptionValue(CONFIG_OPTION);
			if (null != configFilename) {
				/*
				 * set the name
				 */
				MailCradleConfiguration.propertiesFile = configFilename;
			} else {
				throw new Exception("Config file was not supplied");
			}
			/*
			 * get the mailsort file
			 */
			final String mailcradleFilename = MailCradleConfiguration.getInstance().getMailsortFile();
			if (null != mailcradleFilename) {
				String root = new File(configFilename).getAbsoluteFile().getParentFile().toString();
				final File mailcradleFile = new File(root + File.separator + mailcradleFilename);
				if (mailcradleFile.exists()) {
					final MailCradleRunner mailsortRunner = new MailCradleRunner();
					mailsortRunner.runMailsortFile(mailcradleFile);
				} else {
					throw new Exception("MailCradle file '" + mailcradleFile + "' does not exist");
				}
			} else {
				throw new Exception("MailCradle file was not supplied");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
