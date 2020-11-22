package com.khubla.kmailsorter.domain.action;

import javax.mail.*;

import org.apache.logging.log4j.*;

import com.khubla.kmailsorter.domain.*;

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
	public void execute(Message message, Mailsort mailsort) throws MessagingException {
		logger.info("Forwarding message " + message.getMessageNumber() + " to address: " + address);
		throw new RuntimeException("Not Implemented");
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
