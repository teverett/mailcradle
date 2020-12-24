package com.khubla.mailcradle;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.FolderFactory;
import com.khubla.mailcradle.imap.IMAPFolderUtil;

public class MailCradleCrawler {
   /**
    * message filter
    */
   private final MessageFilter messageFilter;

   public MailCradleCrawler(Mailcradle mailsort) {
      super();
      messageFilter = new MessageFilter(mailsort);
   }

   /**
    * run filters on all listed folders
    *
    * @throws MessagingException
    * @throws IOException
    */
   public void filterAllFolders() throws MessagingException, IOException {
      for (final String folderName : MailCradleConfiguration.getInstance().getImapCrawlFolders()) {
         if (folderName.endsWith(".*")) {
            final String fn = folderName.substring(0, folderName.length() - 2);
            runFilters(fn);
            final IMAPFolderUtil imapFolderUtil = FolderFactory.getInstance().getFolder(fn);
            System.out.println("Finding subfolders of: " + fn);
            final List<String> subFolders = imapFolderUtil.getChildFolders();
            if ((null != subFolders) && (subFolders.size() > 0)) {
               System.out.println("Folders to crawl include: ");
               for (final String n : subFolders) {
                  System.out.println(n);
               }
               /*
                * filter the folders
                */
               for (final String name : subFolders) {
                  runFilters(name);
                  /*
                   * delete empty folder?
                   */
                  if (true == MailCradleConfiguration.getInstance().isImapRmoveempty()) {
                     final IMAPFolderUtil thisIMAPFolderUtil = FolderFactory.getInstance().getFolder(name);
                     if (0 == thisIMAPFolderUtil.getMessageCount()) {
                        System.out.println("Deleting empty folder: " + name);
                        thisIMAPFolderUtil.deleteFolder();
                     }
                  }
               }
            }
         } else {
            runFilters(folderName);
         }
      }
      System.out.println("Mail crawl complete");
   }

   /**
    * run all filter commands on a folder
    *
    * @param inbox IMAP inbox
    * @param mailsort mailsort rules
    * @throws MessagingException potential exception
    * @throws IOException
    */
   private void runFilters(String folderName) throws MessagingException, IOException {
      System.out.println("Folder: " + folderName);
      /*
       * get the uids
       */
      final IMAPFolderUtil imapFolderUtil = FolderFactory.getInstance().getFolder(folderName);
      imapFolderUtil.iterateMessages(messageFilter);
      /*
       * on servers that don't support move, we have copy & delete, so need an expunge
       */
      imapFolderUtil.expunge();
      System.out.println();
      System.out.println("Done");
   }
}
