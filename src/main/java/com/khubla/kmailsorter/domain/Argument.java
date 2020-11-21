package com.khubla.kmailsorter.domain;

import java.util.*;

public class Argument {
	private String tag;
	private String string;
	private List<String> stringList;
	private int number;

	public int getNumber() {
		return number;
	}

	public String getString() {
		return string;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public String getTag() {
		return tag;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setString(String string) {
		this.string = string;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
