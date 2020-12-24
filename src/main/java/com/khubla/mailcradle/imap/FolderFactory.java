package com.khubla.mailcradle.imap;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

public class FolderFactory implements Closeable {
   private static FolderFactory instance;

   public static FolderFactory getInstance() {
      if (null == instance) {
         instance = new FolderFactory();
      }
      return instance;
   }

   private final Map<String, IMAPFolderUtil> folders = new HashMap<String, IMAPFolderUtil>();

   private FolderFactory() {
   }

   @Override
   public void close() throws IOException {
      for (final IMAPFolderUtil imapFolderUtil : folders.values()) {
         imapFolderUtil.close();
      }
      folders.clear();
   }

   public IMAPFolderUtil getFolder(String folderName) throws MessagingException {
      IMAPFolderUtil ret = folders.get(folderName);
      if (null == ret) {
         ret = new IMAPFolderUtil(folderName);
         folders.put(folderName, ret);
      }
      return ret;
   }
}
