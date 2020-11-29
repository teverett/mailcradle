package com.khubla.mailcradle.imap;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.mailcradle.*;
import com.sun.mail.iap.*;
import com.sun.mail.imap.*;
import com.sun.mail.imap.protocol.*;

/**
 * https://stackoverflow.com/questions/4155412/javamail-keeping-imapfolder-idle-alive
 */
public class IMAPKeepaliveRunnable implements Runnable {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(IMAPFolderUtil.class);
	/**
	 * keepalive time (ms)
	 */
	private final long keepalive;
	/*
	 * folder
	 */
	private final IMAPFolder imapFolder;

	public IMAPKeepaliveRunnable(IMAPFolder imapFolder) {
		this.imapFolder = imapFolder;
		keepalive = 1000 * 60 * MailCradleConfiguration.getInstance().getImapKeepaliveMinutes();
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				logger.info("Keepalive");
				imapFolder.doCommand(new IMAPFolder.ProtocolCommand() {
					@Override
					public Object doCommand(IMAPProtocol p) throws ProtocolException {
						p.simpleCommand("NOOP", null);
						return null;
					}
				});
				Thread.sleep(keepalive);
			} catch (final InterruptedException e) {
				/*
				 * Ignore, just aborting the thread...
				 */
			} catch (final MessagingException e) {
				/*
				 * Shouldn't really happen...
				 */
				logger.warn("Unexpected exception while keeping alive the IDLE connection", e);
			}
		}
	}
}
