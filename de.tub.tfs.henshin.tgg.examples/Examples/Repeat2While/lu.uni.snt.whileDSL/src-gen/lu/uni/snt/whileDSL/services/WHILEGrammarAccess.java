/*
* generated by Xtext
*/
package lu.uni.snt.whileDSL.services;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import java.util.List;

import org.eclipse.xtext.*;
import org.eclipse.xtext.service.GrammarProvider;
import org.eclipse.xtext.service.AbstractElementFinder.*;

import org.eclipse.xtext.common.services.TerminalsGrammarAccess;

@Singleton
public class WHILEGrammarAccess extends AbstractGrammarElementFinder {
	
	
	public class WProgramElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "WProgram");
		private final Assignment cFstAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cFstFgmnt_LST_ElemParserRuleCall_0 = (RuleCall)cFstAssignment.eContents().get(0);
		
		//WProgram:
		//	fst=Fgmnt_LST_Elem;
		public ParserRule getRule() { return rule; }

		//fst=Fgmnt_LST_Elem
		public Assignment getFstAssignment() { return cFstAssignment; }

		//Fgmnt_LST_Elem
		public RuleCall getFstFgmnt_LST_ElemParserRuleCall_0() { return cFstFgmnt_LST_ElemParserRuleCall_0; }
	}

	public class Fgmnt_LST_ElemElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Fgmnt_LST_Elem");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Alternatives cAlternatives_0 = (Alternatives)cGroup.eContents().get(0);
		private final RuleCall cWhileParserRuleCall_0_0 = (RuleCall)cAlternatives_0.eContents().get(0);
		private final RuleCall cVar_DefParserRuleCall_0_1 = (RuleCall)cAlternatives_0.eContents().get(1);
		private final RuleCall cFn_CallParserRuleCall_0_2 = (RuleCall)cAlternatives_0.eContents().get(2);
		private final RuleCall cFn_DefParserRuleCall_0_3 = (RuleCall)cAlternatives_0.eContents().get(3);
		private final RuleCall cCommentParserRuleCall_0_4 = (RuleCall)cAlternatives_0.eContents().get(4);
		private final Assignment cNextAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNextFgmnt_LST_ElemParserRuleCall_1_0 = (RuleCall)cNextAssignment_1.eContents().get(0);
		
		//Fgmnt_LST_Elem:
		//	(While | Var_Def | Fn_Call | Fn_Def | Comment) next=Fgmnt_LST_Elem?;
		public ParserRule getRule() { return rule; }

		//(While | Var_Def | Fn_Call | Fn_Def | Comment) next=Fgmnt_LST_Elem?
		public Group getGroup() { return cGroup; }

		//While | Var_Def | Fn_Call | Fn_Def | Comment
		public Alternatives getAlternatives_0() { return cAlternatives_0; }

		//While
		public RuleCall getWhileParserRuleCall_0_0() { return cWhileParserRuleCall_0_0; }

		//Var_Def
		public RuleCall getVar_DefParserRuleCall_0_1() { return cVar_DefParserRuleCall_0_1; }

		//Fn_Call
		public RuleCall getFn_CallParserRuleCall_0_2() { return cFn_CallParserRuleCall_0_2; }

		//Fn_Def
		public RuleCall getFn_DefParserRuleCall_0_3() { return cFn_DefParserRuleCall_0_3; }

		//Comment
		public RuleCall getCommentParserRuleCall_0_4() { return cCommentParserRuleCall_0_4; }

		//next=Fgmnt_LST_Elem?
		public Assignment getNextAssignment_1() { return cNextAssignment_1; }

		//Fgmnt_LST_Elem
		public RuleCall getNextFgmnt_LST_ElemParserRuleCall_1_0() { return cNextFgmnt_LST_ElemParserRuleCall_1_0; }
	}

	public class WhileElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "While");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWhileKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cExprAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cExprExprParserRuleCall_2_0 = (RuleCall)cExprAssignment_2.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cFgmntAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cFgmntFgmnt_LST_ElemParserRuleCall_5_0 = (RuleCall)cFgmntAssignment_5.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//While:
		//	"while" "(" expr=Expr ")" "{" fgmnt=Fgmnt_LST_Elem "}";
		public ParserRule getRule() { return rule; }

		//"while" "(" expr=Expr ")" "{" fgmnt=Fgmnt_LST_Elem "}"
		public Group getGroup() { return cGroup; }

		//"while"
		public Keyword getWhileKeyword_0() { return cWhileKeyword_0; }

		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }

		//expr=Expr
		public Assignment getExprAssignment_2() { return cExprAssignment_2; }

		//Expr
		public RuleCall getExprExprParserRuleCall_2_0() { return cExprExprParserRuleCall_2_0; }

		//")"
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }

		//"{"
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }

		//fgmnt=Fgmnt_LST_Elem
		public Assignment getFgmntAssignment_5() { return cFgmntAssignment_5; }

		//Fgmnt_LST_Elem
		public RuleCall getFgmntFgmnt_LST_ElemParserRuleCall_5_0() { return cFgmntFgmnt_LST_ElemParserRuleCall_5_0; }

		//"}"
		public Keyword getRightCurlyBracketKeyword_6() { return cRightCurlyBracketKeyword_6; }
	}

	public class Var_DefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Var_Def");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cLeftAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cLeftVarParserRuleCall_0_0 = (RuleCall)cLeftAssignment_0.eContents().get(0);
		private final Keyword cEqualsSignKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cRightAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cRightExprParserRuleCall_2_0 = (RuleCall)cRightAssignment_2.eContents().get(0);
		private final Keyword cSemicolonKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Var_Def:
		//	left=Var "=" right=Expr ";";
		public ParserRule getRule() { return rule; }

		//left=Var "=" right=Expr ";"
		public Group getGroup() { return cGroup; }

		//left=Var
		public Assignment getLeftAssignment_0() { return cLeftAssignment_0; }

		//Var
		public RuleCall getLeftVarParserRuleCall_0_0() { return cLeftVarParserRuleCall_0_0; }

		//"="
		public Keyword getEqualsSignKeyword_1() { return cEqualsSignKeyword_1; }

		//right=Expr
		public Assignment getRightAssignment_2() { return cRightAssignment_2; }

		//Expr
		public RuleCall getRightExprParserRuleCall_2_0() { return cRightExprParserRuleCall_2_0; }

		//";"
		public Keyword getSemicolonKeyword_3() { return cSemicolonKeyword_3; }
	}

	public class Fn_CallElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Fn_Call");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameFAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameFIDTerminalRuleCall_0_0 = (RuleCall)cNameFAssignment_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cSemicolonKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Fn_Call:
		//	nameF=ID "(" ")" ";";
		public ParserRule getRule() { return rule; }

		//nameF=ID "(" ")" ";"
		public Group getGroup() { return cGroup; }

		//nameF=ID
		public Assignment getNameFAssignment_0() { return cNameFAssignment_0; }

		//ID
		public RuleCall getNameFIDTerminalRuleCall_0_0() { return cNameFIDTerminalRuleCall_0_0; }

		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }

		//")"
		public Keyword getRightParenthesisKeyword_2() { return cRightParenthesisKeyword_2; }

		//";"
		public Keyword getSemicolonKeyword_3() { return cSemicolonKeyword_3; }
	}

	public class Fn_DefElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Fn_Def");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cDefKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameFAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameFIDTerminalRuleCall_1_0 = (RuleCall)cNameFAssignment_1.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cBodyAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cBodyFgmnt_LST_ElemParserRuleCall_5_0 = (RuleCall)cBodyAssignment_5.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Keyword cSemicolonKeyword_7 = (Keyword)cGroup.eContents().get(7);
		
		//Fn_Def:
		//	"def" nameF=ID "(" ")" "{" body=Fgmnt_LST_Elem "}" ";";
		public ParserRule getRule() { return rule; }

		//"def" nameF=ID "(" ")" "{" body=Fgmnt_LST_Elem "}" ";"
		public Group getGroup() { return cGroup; }

		//"def"
		public Keyword getDefKeyword_0() { return cDefKeyword_0; }

		//nameF=ID
		public Assignment getNameFAssignment_1() { return cNameFAssignment_1; }

		//ID
		public RuleCall getNameFIDTerminalRuleCall_1_0() { return cNameFIDTerminalRuleCall_1_0; }

		//"("
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }

		//")"
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }

		//"{"
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }

		//body=Fgmnt_LST_Elem
		public Assignment getBodyAssignment_5() { return cBodyAssignment_5; }

		//Fgmnt_LST_Elem
		public RuleCall getBodyFgmnt_LST_ElemParserRuleCall_5_0() { return cBodyFgmnt_LST_ElemParserRuleCall_5_0; }

		//"}"
		public Keyword getRightCurlyBracketKeyword_6() { return cRightCurlyBracketKeyword_6; }

		//";"
		public Keyword getSemicolonKeyword_7() { return cSemicolonKeyword_7; }
	}

	public class CommentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Comment");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSolidusNumberSignKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cCommentAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cCommentSTRINGTerminalRuleCall_1_0 = (RuleCall)cCommentAssignment_1.eContents().get(0);
		private final Keyword cNumberSignSolidusKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//Comment:
		//	"/#" comment=STRING "#/";
		public ParserRule getRule() { return rule; }

		//"/#" comment=STRING "#/"
		public Group getGroup() { return cGroup; }

		//"/#"
		public Keyword getSolidusNumberSignKeyword_0() { return cSolidusNumberSignKeyword_0; }

		//comment=STRING
		public Assignment getCommentAssignment_1() { return cCommentAssignment_1; }

		//STRING
		public RuleCall getCommentSTRINGTerminalRuleCall_1_0() { return cCommentSTRINGTerminalRuleCall_1_0; }

		//"#/"
		public Keyword getNumberSignSolidusKeyword_2() { return cNumberSignSolidusKeyword_2; }
	}

	public class ExprElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Expr");
		private final Assignment cTypeAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTypeExpr_TParserRuleCall_0 = (RuleCall)cTypeAssignment.eContents().get(0);
		
		//Expr:
		//	type=Expr_T;
		public ParserRule getRule() { return rule; }

		//type=Expr_T
		public Assignment getTypeAssignment() { return cTypeAssignment; }

		//Expr_T
		public RuleCall getTypeExpr_TParserRuleCall_0() { return cTypeExpr_TParserRuleCall_0; }
	}

	public class Expr_TElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Expr_T");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cUnaryParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cBinaryParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		
		//Expr_T:
		//	Unary | Binary;
		public ParserRule getRule() { return rule; }

		//Unary | Binary
		public Alternatives getAlternatives() { return cAlternatives; }

		//Unary
		public RuleCall getUnaryParserRuleCall_0() { return cUnaryParserRuleCall_0; }

		//Binary
		public RuleCall getBinaryParserRuleCall_1() { return cBinaryParserRuleCall_1; }
	}

	public class UnaryElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Unary");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cNegParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cVarParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cInputParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//Unary:
		//	Neg | Var | Input;
		public ParserRule getRule() { return rule; }

		//Neg | Var | Input
		public Alternatives getAlternatives() { return cAlternatives; }

		//Neg
		public RuleCall getNegParserRuleCall_0() { return cNegParserRuleCall_0; }

		//Var
		public RuleCall getVarParserRuleCall_1() { return cVarParserRuleCall_1; }

		//Input
		public RuleCall getInputParserRuleCall_2() { return cInputParserRuleCall_2; }
	}

	public class BinaryElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Binary");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cFstAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cFstExprParserRuleCall_1_0 = (RuleCall)cFstAssignment_1.eContents().get(0);
		private final Assignment cOperatorAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Alternatives cOperatorAlternatives_2_0 = (Alternatives)cOperatorAssignment_2.eContents().get(0);
		private final Keyword cOperatorAmpersandAmpersandKeyword_2_0_0 = (Keyword)cOperatorAlternatives_2_0.eContents().get(0);
		private final Keyword cOperatorVerticalLineVerticalLineKeyword_2_0_1 = (Keyword)cOperatorAlternatives_2_0.eContents().get(1);
		private final Keyword cOperatorEqualsSignEqualsSignKeyword_2_0_2 = (Keyword)cOperatorAlternatives_2_0.eContents().get(2);
		private final Assignment cSndAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cSndExprParserRuleCall_3_0 = (RuleCall)cSndAssignment_3.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//Binary:
		//	"(" fst=Expr operator=("&&" | "||" | "==") snd=Expr ")";
		public ParserRule getRule() { return rule; }

		//"(" fst=Expr operator=("&&" | "||" | "==") snd=Expr ")"
		public Group getGroup() { return cGroup; }

		//"("
		public Keyword getLeftParenthesisKeyword_0() { return cLeftParenthesisKeyword_0; }

		//fst=Expr
		public Assignment getFstAssignment_1() { return cFstAssignment_1; }

		//Expr
		public RuleCall getFstExprParserRuleCall_1_0() { return cFstExprParserRuleCall_1_0; }

		//operator=("&&" | "||" | "==")
		public Assignment getOperatorAssignment_2() { return cOperatorAssignment_2; }

		//"&&" | "||" | "=="
		public Alternatives getOperatorAlternatives_2_0() { return cOperatorAlternatives_2_0; }

		//"&&"
		public Keyword getOperatorAmpersandAmpersandKeyword_2_0_0() { return cOperatorAmpersandAmpersandKeyword_2_0_0; }

		//"||"
		public Keyword getOperatorVerticalLineVerticalLineKeyword_2_0_1() { return cOperatorVerticalLineVerticalLineKeyword_2_0_1; }

		//"=="
		public Keyword getOperatorEqualsSignEqualsSignKeyword_2_0_2() { return cOperatorEqualsSignEqualsSignKeyword_2_0_2; }

		//snd=Expr
		public Assignment getSndAssignment_3() { return cSndAssignment_3; }

		//Expr
		public RuleCall getSndExprParserRuleCall_3_0() { return cSndExprParserRuleCall_3_0; }

		//")"
		public Keyword getRightParenthesisKeyword_4() { return cRightParenthesisKeyword_4; }
	}

	public class NegElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Neg");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cExclamationMarkKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cExprAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cExprExprParserRuleCall_1_0 = (RuleCall)cExprAssignment_1.eContents().get(0);
		
		//Neg:
		//	"!" expr=Expr;
		public ParserRule getRule() { return rule; }

		//"!" expr=Expr
		public Group getGroup() { return cGroup; }

		//"!"
		public Keyword getExclamationMarkKeyword_0() { return cExclamationMarkKeyword_0; }

		//expr=Expr
		public Assignment getExprAssignment_1() { return cExprAssignment_1; }

		//Expr
		public RuleCall getExprExprParserRuleCall_1_0() { return cExprExprParserRuleCall_1_0; }
	}

	public class VarElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Var");
		private final Assignment cLabelAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cLabelIDTerminalRuleCall_0 = (RuleCall)cLabelAssignment.eContents().get(0);
		
		//Var:
		//	label=ID;
		public ParserRule getRule() { return rule; }

		//label=ID
		public Assignment getLabelAssignment() { return cLabelAssignment; }

		//ID
		public RuleCall getLabelIDTerminalRuleCall_0() { return cLabelIDTerminalRuleCall_0; }
	}

	public class InputElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Input");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Action cInputAction_0 = (Action)cGroup.eContents().get(0);
		private final Keyword cInputKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cLeftParenthesisKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cRightParenthesisKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Input:
		//	{Input} "input" "(" ")";
		public ParserRule getRule() { return rule; }

		//{Input} "input" "(" ")"
		public Group getGroup() { return cGroup; }

		//{Input}
		public Action getInputAction_0() { return cInputAction_0; }

		//"input"
		public Keyword getInputKeyword_1() { return cInputKeyword_1; }

		//"("
		public Keyword getLeftParenthesisKeyword_2() { return cLeftParenthesisKeyword_2; }

		//")"
		public Keyword getRightParenthesisKeyword_3() { return cRightParenthesisKeyword_3; }
	}

	public class TargetElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "Target");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cWProgramParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cFgmnt_LST_ElemParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cExprParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cExpr_TParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		
		//Target:
		//	WProgram | Fgmnt_LST_Elem | Expr | Expr_T;
		public ParserRule getRule() { return rule; }

		//WProgram | Fgmnt_LST_Elem | Expr | Expr_T
		public Alternatives getAlternatives() { return cAlternatives; }

		//WProgram
		public RuleCall getWProgramParserRuleCall_0() { return cWProgramParserRuleCall_0; }

		//Fgmnt_LST_Elem
		public RuleCall getFgmnt_LST_ElemParserRuleCall_1() { return cFgmnt_LST_ElemParserRuleCall_1; }

		//Expr
		public RuleCall getExprParserRuleCall_2() { return cExprParserRuleCall_2; }

		//Expr_T
		public RuleCall getExpr_TParserRuleCall_3() { return cExpr_TParserRuleCall_3; }
	}
	
	
	private WProgramElements pWProgram;
	private Fgmnt_LST_ElemElements pFgmnt_LST_Elem;
	private WhileElements pWhile;
	private Var_DefElements pVar_Def;
	private Fn_CallElements pFn_Call;
	private Fn_DefElements pFn_Def;
	private CommentElements pComment;
	private ExprElements pExpr;
	private Expr_TElements pExpr_T;
	private UnaryElements pUnary;
	private BinaryElements pBinary;
	private NegElements pNeg;
	private VarElements pVar;
	private InputElements pInput;
	private TargetElements pTarget;
	
	private final Grammar grammar;

	private TerminalsGrammarAccess gaTerminals;

	@Inject
	public WHILEGrammarAccess(GrammarProvider grammarProvider,
		TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("lu.uni.snt.whileDSL.WHILE".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	
	public Grammar getGrammar() {
		return grammar;
	}
	

	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//WProgram:
	//	fst=Fgmnt_LST_Elem;
	public WProgramElements getWProgramAccess() {
		return (pWProgram != null) ? pWProgram : (pWProgram = new WProgramElements());
	}
	
	public ParserRule getWProgramRule() {
		return getWProgramAccess().getRule();
	}

	//Fgmnt_LST_Elem:
	//	(While | Var_Def | Fn_Call | Fn_Def | Comment) next=Fgmnt_LST_Elem?;
	public Fgmnt_LST_ElemElements getFgmnt_LST_ElemAccess() {
		return (pFgmnt_LST_Elem != null) ? pFgmnt_LST_Elem : (pFgmnt_LST_Elem = new Fgmnt_LST_ElemElements());
	}
	
	public ParserRule getFgmnt_LST_ElemRule() {
		return getFgmnt_LST_ElemAccess().getRule();
	}

	//While:
	//	"while" "(" expr=Expr ")" "{" fgmnt=Fgmnt_LST_Elem "}";
	public WhileElements getWhileAccess() {
		return (pWhile != null) ? pWhile : (pWhile = new WhileElements());
	}
	
	public ParserRule getWhileRule() {
		return getWhileAccess().getRule();
	}

	//Var_Def:
	//	left=Var "=" right=Expr ";";
	public Var_DefElements getVar_DefAccess() {
		return (pVar_Def != null) ? pVar_Def : (pVar_Def = new Var_DefElements());
	}
	
	public ParserRule getVar_DefRule() {
		return getVar_DefAccess().getRule();
	}

	//Fn_Call:
	//	nameF=ID "(" ")" ";";
	public Fn_CallElements getFn_CallAccess() {
		return (pFn_Call != null) ? pFn_Call : (pFn_Call = new Fn_CallElements());
	}
	
	public ParserRule getFn_CallRule() {
		return getFn_CallAccess().getRule();
	}

	//Fn_Def:
	//	"def" nameF=ID "(" ")" "{" body=Fgmnt_LST_Elem "}" ";";
	public Fn_DefElements getFn_DefAccess() {
		return (pFn_Def != null) ? pFn_Def : (pFn_Def = new Fn_DefElements());
	}
	
	public ParserRule getFn_DefRule() {
		return getFn_DefAccess().getRule();
	}

	//Comment:
	//	"/#" comment=STRING "#/";
	public CommentElements getCommentAccess() {
		return (pComment != null) ? pComment : (pComment = new CommentElements());
	}
	
	public ParserRule getCommentRule() {
		return getCommentAccess().getRule();
	}

	//Expr:
	//	type=Expr_T;
	public ExprElements getExprAccess() {
		return (pExpr != null) ? pExpr : (pExpr = new ExprElements());
	}
	
	public ParserRule getExprRule() {
		return getExprAccess().getRule();
	}

	//Expr_T:
	//	Unary | Binary;
	public Expr_TElements getExpr_TAccess() {
		return (pExpr_T != null) ? pExpr_T : (pExpr_T = new Expr_TElements());
	}
	
	public ParserRule getExpr_TRule() {
		return getExpr_TAccess().getRule();
	}

	//Unary:
	//	Neg | Var | Input;
	public UnaryElements getUnaryAccess() {
		return (pUnary != null) ? pUnary : (pUnary = new UnaryElements());
	}
	
	public ParserRule getUnaryRule() {
		return getUnaryAccess().getRule();
	}

	//Binary:
	//	"(" fst=Expr operator=("&&" | "||" | "==") snd=Expr ")";
	public BinaryElements getBinaryAccess() {
		return (pBinary != null) ? pBinary : (pBinary = new BinaryElements());
	}
	
	public ParserRule getBinaryRule() {
		return getBinaryAccess().getRule();
	}

	//Neg:
	//	"!" expr=Expr;
	public NegElements getNegAccess() {
		return (pNeg != null) ? pNeg : (pNeg = new NegElements());
	}
	
	public ParserRule getNegRule() {
		return getNegAccess().getRule();
	}

	//Var:
	//	label=ID;
	public VarElements getVarAccess() {
		return (pVar != null) ? pVar : (pVar = new VarElements());
	}
	
	public ParserRule getVarRule() {
		return getVarAccess().getRule();
	}

	//Input:
	//	{Input} "input" "(" ")";
	public InputElements getInputAccess() {
		return (pInput != null) ? pInput : (pInput = new InputElements());
	}
	
	public ParserRule getInputRule() {
		return getInputAccess().getRule();
	}

	//Target:
	//	WProgram | Fgmnt_LST_Elem | Expr | Expr_T;
	public TargetElements getTargetAccess() {
		return (pTarget != null) ? pTarget : (pTarget = new TargetElements());
	}
	
	public ParserRule getTargetRule() {
		return getTargetAccess().getRule();
	}

	//terminal ID:
	//	"^"? ("a".."z" | "A".."Z" | "_") ("a".."z" | "A".."Z" | "_" | "0".."9")*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	} 

	//terminal INT returns ecore::EInt:
	//	"0".."9"+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	} 

	//terminal STRING:
	//	"\"" ("\\" ("b" | "t" | "n" | "f" | "r" | "u" | "\"" | "\'" | "\\") | !("\\" | "\""))* "\"" | "\'" ("\\" ("b" | "t" |
	//	"n" | "f" | "r" | "u" | "\"" | "\'" | "\\") | !("\\" | "\'"))* "\'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	} 

	//terminal ML_COMMENT:
	//	"/ *"->"* /";
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	} 

	//terminal SL_COMMENT:
	//	"//" !("\n" | "\r")* ("\r"? "\n")?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	} 

	//terminal WS:
	//	(" " | "\t" | "\r" | "\n")+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	} 

	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	} 
}