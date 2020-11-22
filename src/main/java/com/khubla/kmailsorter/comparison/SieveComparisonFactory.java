package com.khubla.kmailsorter.comparison;

public class SieveComparisonFactory {
	public static SieveComparison getComparison(ComparisonType comparisonType) {
		switch (comparisonType) {
			case is:
				return new IsSieveComparison();
			case over:
				return new OverSieveComparison();
			case matches:
				return new MatchesSieveComparison();
			case contains:
				return new ContainsSieveComparison();
			default:
				return null;
		}
	}
}
