package com.khubla.kmailsorter.domain.action;

import com.khubla.kmailsorter.domain.*;

public class ForwardAction extends Action {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
