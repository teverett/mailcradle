package com.khubla.kmailsorter.domain;

import java.util.*;

public class Mailsort {
	private final List<Filter> filters = new ArrayList<Filter>();
	private final Map<String, StringList> lists = new HashMap<String, StringList>();

	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public void addList(StringList stringList) {
		lists.put(stringList.getName(), stringList);
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public StringList getList(String name) {
		return lists.get(name);
	}

	public Map<String, StringList> getLists() {
		return lists;
	}

	public int size() {
		return filters.size();
	}
}
