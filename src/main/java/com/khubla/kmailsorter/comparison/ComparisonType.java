package com.khubla.kmailsorter.comparison;

public enum ComparisonType {
	contains, is, over, matches;

	public static ComparisonType fromString(String str) {
		switch (str) {
			case ":contains":
				return ComparisonType.contains;
			case ":is":
				return ComparisonType.is;
			case ":over":
				return ComparisonType.over;
			case ":matches":
				return ComparisonType.matches;
			default:
				return null;
		}
	}
}
