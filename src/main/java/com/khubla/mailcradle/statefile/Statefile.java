package com.khubla.mailcradle.statefile;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import org.apache.commons.io.*;
import org.apache.logging.log4j.*;

public class Statefile {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(Statefile.class);
	/**
	 * filename
	 */
	private final String FILENAME = "mailcradle.dat";
	/**
	 * =
	 */
	private final String EQUALS = "=";
	/**
	 * state
	 */
	private Map<String, String> state = new HashMap<String, String>();

	public Statefile() {
		super();
		state = readState();
	}

	public String get(String key) {
		return state.get(key);
	}

	private File getFile() {
		final String filename = System.getProperty("user.dir") + File.separator + FILENAME;
		return new File(filename);
	}

	private Map<String, String> readState() {
		try {
			final Map<String, String> ret = new HashMap<String, String>();
			final File file = getFile();
			if (file.exists()) {
				final String data = FileUtils.readFileToString(file, Charset.defaultCharset());
				if (null != data) {
					final String lines[] = data.split("\\r?\\n");
					for (final String line : lines) {
						final String parts[] = line.split(EQUALS);
						if ((null != parts) && (parts.length == 2)) {
							ret.put(parts[0], parts[1]);
						}
					}
				}
			}
			return ret;
		} catch (final Exception e) {
			logger.fatal(e);
			return null;
		}
	}

	public void set(String key, String value) {
		state.put(key, value);
	}

	public void write() {
		writeState(state);
	}

	private void writeState(Map<String, String> state) {
		try {
			if (null != state) {
				final File file = getFile();
				file.createNewFile();
				file.setWritable(true);
				final Writer writer = new FileWriter(file);
				for (final String key : state.keySet()) {
					writer.write(key + EQUALS + state.get(key));
				}
				writer.flush();
				writer.close();
			}
		} catch (final Exception e) {
			logger.fatal(e);
		}
	}
}
