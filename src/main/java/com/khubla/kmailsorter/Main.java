package com.khubla.kmailsorter;

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
			 * get the sieve file
			 */
			final String sieveFilename = KMailSorterConfiguration.getInstance().getSieveFile();
			if (null != sieveFilename) {
				final File sieveFile = new File(sieveFilename);
				if (sieveFile.exists()) {
					final SieveRunner sieveRunner = new SieveRunner();
					sieveRunner.runSieveFile(sieveFile);
				} else {
					throw new Exception("Sieve file '" + sieveFilename + "' does not exist");
				}
			} else {
				throw new Exception("Sieve file was not supplied");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
