package com.khubla.mailcradle.domain.action;

import com.khubla.mailcradle.domain.Action;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.FolderFactory;
import com.khubla.mailcradle.imap.IMAPMessageData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.util.List;

public class FlagAction extends Action {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(FlagAction.class);
   /**
    * flag
    */
   private String flag;

   @Override
   public boolean execute(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException {
      dumpExpressionSteps(expressionSteps);
      System.out.println("\tFlagging message " + messageData.getId() + " in folder " + messageData.getFolderName() + " with: " + flag);
      logger.info("Flagging message " + messageData.getId() + " in folder " + messageData.getFolderName() + " with: " + flag);
      FolderFactory.getInstance().getFolder(messageData.getFolderName()).flagMessage(messageData.getUid(), flag, true);
      return true;
   }

   public String getFlag() {
      return flag;
   }

   public void setFlag(String flag) {
      this.flag = flag;
   }
}
