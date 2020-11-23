package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;
import com.khubla.kmailsorter.imap.*;

public class ForwardAction extends Action {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(ForwardAction.class);
	/**
	 * email address to forward to
	 */
	private String address;

	@Override
	public void execute(IMAPMessageData messageData, Mailsort mailsort) throws MessagingException {
		System.out.println("Forwarding message " + messageData.getId() + " to address: " + address);
		logger.info("Forwarding message " + messageData.getId() + " to address: " + address);
		IMAPUtil.getInstance().forwardMessage(messageData.getId(), address);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
