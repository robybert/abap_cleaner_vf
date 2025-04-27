package com.sap.adt.abapcleaner.rules.declarations;

import java.time.LocalDate;

import com.sap.adt.abapcleaner.base.ABAP;
import com.sap.adt.abapcleaner.parser.Code;
import com.sap.adt.abapcleaner.parser.Command;
import com.sap.adt.abapcleaner.programbase.IntegrityBrokenException;
import com.sap.adt.abapcleaner.programbase.Program;
import com.sap.adt.abapcleaner.programbase.UnexpectedSyntaxAfterChanges;
import com.sap.adt.abapcleaner.rulebase.ConfigEnumValue;
import com.sap.adt.abapcleaner.rulebase.ConfigValue;
import com.sap.adt.abapcleaner.rulebase.Profile;
import com.sap.adt.abapcleaner.rulebase.RuleForDeclarations;
import com.sap.adt.abapcleaner.rulebase.RuleGroupID;
import com.sap.adt.abapcleaner.rulebase.RuleID;
import com.sap.adt.abapcleaner.rulebase.RuleReference;
import com.sap.adt.abapcleaner.rulebase.RuleSource;
import com.sap.adt.abapcleaner.rulehelpers.Variables;

public class HungarianVariableNotationRule extends RuleForDeclarations {
	private final static RuleReference[] references = new RuleReference[] {
			new RuleReference(RuleSource.VATTENFALL_SPECIFIC, "Declare variables in hungarian notation") };

	@Override
	public RuleID getID() {
		return RuleID.HUNGARIAN_NOTATION;
	}

	@Override
	public RuleGroupID getGroupID() {
		return RuleGroupID.DECLARATIONS;
	}

	@Override
	public String getDisplayName() {
		return "Enforce hungarian notation";
	}

	@Override
	public String getDescription() {
		return "Changes variable names, or adds comments to notify that the variable name is not in the hungarian notation.";
	}

	@Override
	public LocalDate getDateCreated() {
		return LocalDate.of(2025, 4, 27);
	}

	@Override
	public RuleReference[] getReferences() {
		return references;
	}

	@Override
	public RuleID[] getDependentRules() {
		return new RuleID[] { RuleID.CHAIN_OF_ONE, RuleID.UPPER_AND_LOWER_CASE, RuleID.INSET };
	}

	@Override
	public String getExample() {
		// TODO Auto-generated method stub
		return null;
	}

	private static final String[] actionTexts = new String[] { "rename", "add TODO comment", "ignore" };

	final ConfigEnumValue<HungarianNotationAction> configHungarianNotation = new ConfigEnumValue<HungarianNotationAction>(
			this, "MeasureForHungarianNotation", "Action to enforce the hungarian notation:", actionTexts,
			HungarianNotationAction.values(), HungarianNotationAction.ADD_TODO_COMMENT);

	private final ConfigValue[] configValues = new ConfigValue[] { configHungarianNotation };

	@Override
	public ConfigValue[] getConfigValues() {
		return configValues;
	}

	private static final String prefix = "TODO: ";
	private static final String suffix = " (" + Program.PRODUCT_NAME + ")";
	private static final String varMember = prefix + "member variable does not adhere to hungarian notation" + suffix;
	private static final String varLocal = prefix + "local variable does not adhere to hungarian notation" + suffix;
	private static final String structMember = prefix + "member structure does not adhere to hungarian notation"
			+ suffix;
	private static final String structLocal = prefix + "local structure does not adhere to hungarian notation" + suffix;
	private static final String constMember = prefix + "member constant does not adhere to hungarian notation" + suffix;
	private static final String constLocal = prefix + "local constant does not adhere to hungarian notation" + suffix;
	private static final String rangeMember = prefix + "member range does not adhere to hungarian notation" + suffix;
	private static final String rangeLocal = prefix + "local range does not adhere to hungarian notation" + suffix;
	private static final String typeVar = prefix + "variable type does not adhere to hungarian notation" + suffix;
	private static final String typeStruct = prefix + "struct type does not adhere to hungarian notation" + suffix;
	private static final String typeTable = prefix + "table type does not adhere to hungarian notation" + suffix;

	public static final String[] varMessages = new String[] { ABAP.COMMENT_SIGN_STRING + " " + varMember,
			ABAP.COMMENT_SIGN_STRING + " " + varLocal };

	public static final String[] structMessages = new String[] { ABAP.COMMENT_SIGN_STRING + " " + structMember,
			ABAP.COMMENT_SIGN_STRING + " " + structLocal };
	public static final String[] constMessages = new String[] { ABAP.COMMENT_SIGN_STRING + " " + constMember,
			ABAP.COMMENT_SIGN_STRING + " " + constLocal };
	public static final String[] rangeMessages = new String[] { ABAP.COMMENT_SIGN_STRING + " " + rangeMember,
			ABAP.COMMENT_SIGN_STRING + " " + rangeLocal };
	public static final String[] typeMessages = new String[] { ABAP.COMMENT_SIGN_STRING + " " + typeVar,
			ABAP.COMMENT_SIGN_STRING + " " + typeStruct, ABAP.COMMENT_SIGN_STRING + " " + typeTable };

	public HungarianVariableNotationRule(Profile profile) {
		super(profile);
		initializeConfiguration();
	}

	@Override
	protected void executeOn(Code code, Command methodStart, Variables localVariables, int releaseRestriction)
			throws UnexpectedSyntaxAfterChanges, IntegrityBrokenException {
		// TODO: add logic
	}

}
