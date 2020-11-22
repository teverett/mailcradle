package com.khubla.kmailsorter.comparison;

public enum ComparisonType {
	contains, is, over, under, matches;

	public static ComparisonType fromString(String str) {
		switch (str) {
			case ":contains":
				return ComparisonType.contains;
			case ":is":
				return ComparisonType.is;
			case ":over":
				return ComparisonType.over;
			case ":under":
				return ComparisonType.under;
			case ":matches":
				return ComparisonType.matches;
			default:
				return null;
		}
	}
}
