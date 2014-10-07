package agg.attribute.gui.lang;

import agg.attribute.impl.AttrMsgCode;

/**
 * @version $Id: AttrMessageLang.java,v 1.2 2007/09/10 13:05:53 olga Exp $
 * @author $Author: olga $
 */
public class AttrMessageLang implements AttrMsgCode {

	static protected boolean inited = false;

	static protected String msgTexts[] = new String[20];

	static protected String invalidErr;

	static protected void initIfNeeded() {
		if (!inited) {
			inited = true;
			init();
		}
	}

	static protected void init() {
		invalidErr = "Invalid error code. This is a bug.";
		msgTexts[OK] = "Okay";
		msgTexts[NO_SUCH_TYPE] = "Type not found.";
		msgTexts[NAME_DUPLICATION] = "This name is already declared.";
		msgTexts[BAD_EXPR_TYPE] = "Wrong expression type.";

		msgTexts[EXPR_REQUIRED] = "A non-empty value is required in this context.";
		msgTexts[EXPR_MUST_BE_CONST] = "A constant value is required in this context.";
		msgTexts[EXPR_MUST_BE_CONST_OR_VAR] = "A variable or a constant value is required in this context.";
		msgTexts[VAR_NOT_DECLARED] = "This variable is not declared: ";
		msgTexts[EXPR_PARSE_ERR] = "Parsing error:";
		msgTexts[EXPR_EVAL_ERR] = "Error occured during evaluation.";
		msgTexts[ATTR_DONT_MATCH] = "Attributes don't match.";
		msgTexts[RULE_SOURCES_DIFFER] = "Violation of attribute match conditions for non-injective rules.\n"
				+ "( Where right rule side attribute values are not given,\n"
				+ "  corresponding attributes of all host graph objects must be equal. )";
		msgTexts[MATCH_TARGETS_DIFFER] = "Violation of attribute match conditions for non-injective matches.\n"
				+ "( All corresponding right rule side attribute values must be equal. )\n";
	}

	static public String textForCode(int msgCode) {
		initIfNeeded();
		try {
			return msgTexts[msgCode];
		} catch (ArrayIndexOutOfBoundsException e) {
			return invalidErr;
		}
	}
}
/*
 * $Log: AttrMessageLang.java,v $
 * Revision 1.2  2007/09/10 13:05:53  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:08:07 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
