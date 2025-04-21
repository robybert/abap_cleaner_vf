package com.sap.adt.abapcleaner.rules.alignment;

public enum DistinctLineSort {
	ALWAYS, 
	ONLY_WITH_ADDITIONS, 
	NEVER,
	KEEP_AS_IS;

	public int getValue() { 
		return this.ordinal(); 
	}

	public static DistinctLineSort forValue(int value) { 
		return values()[value]; 
	}
}
