package com.khubla.kmailsorter.domain;

import java.util.*;

public class StringList {
	private String name;
	private List<String> list = new ArrayList<String>();

	public void addString(String string) {
		list.add(string);
	}

	public List<String> getList() {
		return list;
	}

	public String getName() {
		return name;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void setName(String name) {
		this.name = name;
	}
}
