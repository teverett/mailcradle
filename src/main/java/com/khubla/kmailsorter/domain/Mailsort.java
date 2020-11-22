package com.khubla.kmailsorter.domain;

import java.util.*;

public class Mailsort {
	private final List<Filter> filters = new ArrayList<Filter>();
	private final List<String> imports = new ArrayList<String>();
	private final Map<String, StringList> lists = new HashMap<String, StringList>();

	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public void addImport(String filename) {
		imports.add(filename);
	}

	public void addList(StringList stringList) {
		lists.put(stringList.getName(), stringList);
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public List<String> getImports() {
		return imports;
	}

	public StringList getList(String name) {
		return lists.get(name);
	}

	public Map<String, StringList> getLists() {
		return lists;
	}

	/**
	 * merge in filters and lists. No need to merge in imports.
	 *
	 * @param subFile Mailsort
	 */
	public void merge(Mailsort subFile) {
		for (final Filter filter : subFile.getFilters()) {
			addFilter(filter);
		}
		for (final StringList stringList : subFile.getLists().values()) {
			addList(stringList);
		}
	}

	public int size() {
		return filters.size();
	}

	/**
	 * total number of list items
	 *
	 * @return total count
	 */
	public int totalListItems() {
		int ret = 0;
		for (final StringList stringList : lists.values()) {
			ret += stringList.getList().size();
		}
		return ret;
	}
}
