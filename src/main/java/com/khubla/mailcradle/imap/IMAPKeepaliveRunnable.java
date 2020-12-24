package com.khubla.mailcradle.imap;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.khubla.mailcradle.MailCradleConfiguration;

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
   private final String folderName;

   public IMAPKeepaliveRunnable(String folderName) {
      this.folderName = folderName;
      keepalive = 1000 * 60 * MailCradleConfiguration.getInstance().getImapKeepaliveMinutes();
   }

   @Override
   public void run() {
      while (!Thread.interrupted()) {
         try {
            logger.info("Keepalive.  Message count: " + FolderFactory.getInstance().getFolder(folderName).getMessageCount());
            Thread.sleep(keepalive);
         } catch (final InterruptedException e) {
            logger.warn("InterruptedException exception while keeping alive the IDLE connection", e);
            /*
             * Ignore, just aborting the thread...
             */
         } catch (final MessagingException e) {
            /*
             * Shouldn't really happen...
             */
            logger.warn("MessagingException exception while keeping alive the IDLE connection", e);
         }
      }
   }
}
