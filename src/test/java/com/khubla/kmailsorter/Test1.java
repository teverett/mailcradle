package com.khubla.kmailsorter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.Test;

import com.khubla.kmailsorter.domain.*;

public class Test1 {
	@Test
	public void testRead() {
		try {
			final InputStream inputStream = Test1.class.getResourceAsStream("/example1.txt");
			assertNotNull(inputStream);
			final Sieve sieve = SieveMarshaller.importSieveRules(inputStream);
			/*
			 * sieve
			 */
			assertNotNull(sieve);
			assertTrue(sieve.getCommands().size() == 2);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
