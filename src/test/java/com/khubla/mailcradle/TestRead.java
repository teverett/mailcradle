package com.khubla.mailcradle;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

import com.khubla.mailcradle.*;
import com.khubla.mailcradle.domain.*;

public class TestRead {
	@Test
	public void test1() {
		try {
			final File file = new File("src/test/resources/example1.txt");
			final Mailcradle mailsort = MailsortMarshaller.importRules(file);
			/*
			 * mailsort
			 */
			assertNotNull(mailsort);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		try {
			final File file = new File("src/test/resources/example2.txt");
			final Mailcradle mailsort = MailsortMarshaller.importRules(file);
			/*
			 * mailsort
			 */
			assertNotNull(mailsort);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test3() {
		try {
			final File file = new File("src/test/resources/example3.txt");
			final Mailcradle mailsort = MailsortMarshaller.importRules(file);
			/*
			 * mailsort
			 */
			assertNotNull(mailsort);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test4() {
		try {
			final File file = new File("src/test/resources/example4.txt");
			final Mailcradle mailsort = MailsortMarshaller.importRules(file);
			/*
			 * mailsort
			 */
			assertNotNull(mailsort);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test5() {
		try {
			final File file = new File("src/test/resources/example5.txt");
			final Mailcradle mailsort = MailsortMarshaller.importRules(file);
			/*
			 * mailsort
			 */
			assertNotNull(mailsort);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
