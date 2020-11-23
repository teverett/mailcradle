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
			System.out.println("khubla.com kMailSorter");
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
				KMailSorterConfiguration.propertiesFile = configFilename;
			} else {
				throw new Exception("Config file was not supplied");
			}
			/*
			 * get the mailsort file
			 */
			final String mailsortFilename = KMailSorterConfiguration.getInstance().getMailsortFile();
			if (null != mailsortFilename) {
				final File mailsortFile = new File(mailsortFilename);
				if (mailsortFile.exists()) {
					final MailsortRunner mailsortRunner = new MailsortRunner();
					mailsortRunner.runMailsortFile(mailsortFile);
				} else {
					throw new Exception("Mailsort file '" + mailsortFile + "' does not exist");
				}
			} else {
				throw new Exception("Mailsort file was not supplied");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
