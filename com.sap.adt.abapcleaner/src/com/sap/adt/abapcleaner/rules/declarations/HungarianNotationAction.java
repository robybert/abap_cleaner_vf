package com.sap.adt.abapcleaner.rules.declarations;

public enum HungarianNotationAction {
	RENAME,
	ADD_TODO_COMMENT,
	IGNORE;
	
	public int getValue() {
		return this.ordinal();
	}
	
}
