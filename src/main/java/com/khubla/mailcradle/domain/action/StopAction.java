package com.khubla.mailcradle.domain.action;

import com.khubla.mailcradle.domain.Action;
import com.khubla.mailcradle.domain.Mailcradle;
import com.khubla.mailcradle.imap.IMAPMessageData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.util.List;

public class StopAction extends Action {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(StopAction.class);

   @Override
   public boolean execute(IMAPMessageData messageData, Mailcradle mailsort, List<String> expressionSteps) throws MessagingException {
      //dumpExpressionSteps(expressionSteps);
      // System.out.println("Stopping rule processing for message " + messageData.getId() + " in
      // folder " + messageData.getFolderName());
      logger.info("\tStopping rule processing for message " + messageData.getId() + " in folder " + messageData.getFolderName());
      return false;
   }
}
