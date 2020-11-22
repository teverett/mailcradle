package com.khubla.kmailsorter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

import com.khubla.kmailsorter.domain.*;

public class TestRead {
	@Test
	public void test1() {
		try {
			final InputStream inputStream = TestRead.class.getResourceAsStream("/example1.txt");
			assertNotNull(inputStream);
			final Mailsort mailsort = MailsortMarshaller.importRules(inputStream);
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
			final InputStream inputStream = TestRead.class.getResourceAsStream("/example2.txt");
			assertNotNull(inputStream);
			final Mailsort mailsort = MailsortMarshaller.importRules(inputStream);
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
			final InputStream inputStream = TestRead.class.getResourceAsStream("/example3.txt");
			assertNotNull(inputStream);
			final Mailsort mailsort = MailsortMarshaller.importRules(inputStream);
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
			final InputStream inputStream = TestRead.class.getResourceAsStream("/example4.txt");
			assertNotNull(inputStream);
			final Mailsort mailsort = MailsortMarshaller.importRules(inputStream);
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
			final InputStream inputStream = TestRead.class.getResourceAsStream("/example5.txt");
			assertNotNull(inputStream);
			final Mailsort mailsort = MailsortMarshaller.importRules(inputStream);
			/*
			 * mailsort
			 */
			assertNotNull(mailsort);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
