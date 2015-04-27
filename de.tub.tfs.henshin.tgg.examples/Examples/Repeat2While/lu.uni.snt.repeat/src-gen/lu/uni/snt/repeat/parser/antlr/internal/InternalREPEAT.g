/*
* generated by Xtext
*/
grammar InternalREPEAT;

options {
	superClass=AbstractInternalAntlrParser;
	
}

@lexer::header {
package lu.uni.snt.repeat.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package lu.uni.snt.repeat.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import lu.uni.snt.repeat.services.REPEATGrammarAccess;

}

@parser::members {

 	private REPEATGrammarAccess grammarAccess;
 	
    public InternalREPEATParser(TokenStream input, REPEATGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }
    
    @Override
    protected String getFirstRuleName() {
    	return "RProgram";	
   	}
   	
   	@Override
   	protected REPEATGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}
}

@rulecatch { 
    catch (RecognitionException re) { 
        recover(input,re); 
        appendSkippedTokens();
    } 
}




// Entry rule entryRuleRProgram
entryRuleRProgram returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getRProgramRule()); }
	 iv_ruleRProgram=ruleRProgram 
	 { $current=$iv_ruleRProgram.current; } 
	 EOF 
;

// Rule RProgram
ruleRProgram returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0()); 
	    }
		lv_fst_0_0=ruleStmnt_LST_Elem		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getRProgramRule());
	        }
       		set(
       			$current, 
       			"fst",
        		lv_fst_0_0, 
        		"Stmnt_LST_Elem");
	        afterParserOrEnumRuleCall();
	    }

)
)
;





// Entry rule entryRuleStmnt_LST_Elem
entryRuleStmnt_LST_Elem returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getStmnt_LST_ElemRule()); }
	 iv_ruleStmnt_LST_Elem=ruleStmnt_LST_Elem 
	 { $current=$iv_ruleStmnt_LST_Elem.current; } 
	 EOF 
;

// Rule Stmnt_LST_Elem
ruleStmnt_LST_Elem returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
    { 
        newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getRepeatParserRuleCall_0_0()); 
    }
    this_Repeat_0=ruleRepeat
    { 
        $current = $this_Repeat_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getAsgParserRuleCall_0_1()); 
    }
    this_Asg_1=ruleAsg
    { 
        $current = $this_Asg_1.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getReadParserRuleCall_0_2()); 
    }
    this_Read_2=ruleRead
    { 
        $current = $this_Read_2.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getCommentParserRuleCall_0_3()); 
    }
    this_Comment_3=ruleComment
    { 
        $current = $this_Comment_3.current; 
        afterParserOrEnumRuleCall();
    }
)(
(
		{ 
	        newCompositeNode(grammarAccess.getStmnt_LST_ElemAccess().getNextStmnt_LST_ElemParserRuleCall_1_0()); 
	    }
		lv_next_4_0=ruleStmnt_LST_Elem		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getStmnt_LST_ElemRule());
	        }
       		set(
       			$current, 
       			"next",
        		lv_next_4_0, 
        		"Stmnt_LST_Elem");
	        afterParserOrEnumRuleCall();
	    }

)
)?)
;





// Entry rule entryRuleRepeat
entryRuleRepeat returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getRepeatRule()); }
	 iv_ruleRepeat=ruleRepeat 
	 { $current=$iv_ruleRepeat.current; } 
	 EOF 
;

// Rule Repeat
ruleRepeat returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='REPEAT' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getRepeatAccess().getREPEATKeyword_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getRepeatAccess().getStmntStmnt_LST_ElemParserRuleCall_1_0()); 
	    }
		lv_stmnt_1_0=ruleStmnt_LST_Elem		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getRepeatRule());
	        }
       		set(
       			$current, 
       			"stmnt",
        		lv_stmnt_1_0, 
        		"Stmnt_LST_Elem");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_2='UNTIL' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getRepeatAccess().getUNTILKeyword_2());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getRepeatAccess().getExprLog_ExprParserRuleCall_3_0()); 
	    }
		lv_expr_3_0=ruleLog_Expr		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getRepeatRule());
	        }
       		set(
       			$current, 
       			"expr",
        		lv_expr_3_0, 
        		"Log_Expr");
	        afterParserOrEnumRuleCall();
	    }

)
))
;





// Entry rule entryRuleAsg
entryRuleAsg returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getAsgRule()); }
	 iv_ruleAsg=ruleAsg 
	 { $current=$iv_ruleAsg.current; } 
	 EOF 
;

// Rule Asg
ruleAsg returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
((
(
		{ 
	        newCompositeNode(grammarAccess.getAsgAccess().getLeftSymParserRuleCall_0_0()); 
	    }
		lv_left_0_0=ruleSym		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAsgRule());
	        }
       		set(
       			$current, 
       			"left",
        		lv_left_0_0, 
        		"Sym");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_1=':=' 
    {
    	newLeafNode(otherlv_1, grammarAccess.getAsgAccess().getColonEqualsSignKeyword_1());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getAsgAccess().getRightSymParserRuleCall_2_0()); 
	    }
		lv_right_2_0=ruleSym		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getAsgRule());
	        }
       		set(
       			$current, 
       			"right",
        		lv_right_2_0, 
        		"Sym");
	        afterParserOrEnumRuleCall();
	    }

)
))
;





// Entry rule entryRuleRead
entryRuleRead returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getReadRule()); }
	 iv_ruleRead=ruleRead 
	 { $current=$iv_ruleRead.current; } 
	 EOF 
;

// Rule Read
ruleRead returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='READ' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getReadAccess().getREADKeyword_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getReadAccess().getParamSymParserRuleCall_1_0()); 
	    }
		lv_param_1_0=ruleSym		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getReadRule());
	        }
       		set(
       			$current, 
       			"param",
        		lv_param_1_0, 
        		"Sym");
	        afterParserOrEnumRuleCall();
	    }

)
))
;





// Entry rule entryRuleComment
entryRuleComment returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getCommentRule()); }
	 iv_ruleComment=ruleComment 
	 { $current=$iv_ruleComment.current; } 
	 EOF 
;

// Rule Comment
ruleComment returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='/#' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0());
    }
(
(
		lv_comment_1_0=RULE_STRING
		{
			newLeafNode(lv_comment_1_0, grammarAccess.getCommentAccess().getCommentSTRINGTerminalRuleCall_1_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getCommentRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"comment",
        		lv_comment_1_0, 
        		"STRING");
	    }

)
)	otherlv_2='#/' 
    {
    	newLeafNode(otherlv_2, grammarAccess.getCommentAccess().getNumberSignSolidusKeyword_2());
    }
)
;





// Entry rule entryRuleLog_Expr
entryRuleLog_Expr returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getLog_ExprRule()); }
	 iv_ruleLog_Expr=ruleLog_Expr 
	 { $current=$iv_ruleLog_Expr.current; } 
	 EOF 
;

// Rule Log_Expr
ruleLog_Expr returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		{ 
	        newCompositeNode(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0()); 
	    }
		lv_type_0_0=ruleLog_Expr_T		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getLog_ExprRule());
	        }
       		set(
       			$current, 
       			"type",
        		lv_type_0_0, 
        		"Log_Expr_T");
	        afterParserOrEnumRuleCall();
	    }

)
)
;





// Entry rule entryRuleLog_Expr_T
entryRuleLog_Expr_T returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getLog_Expr_TRule()); }
	 iv_ruleLog_Expr_T=ruleLog_Expr_T 
	 { $current=$iv_ruleLog_Expr_T.current; } 
	 EOF 
;

// Rule Log_Expr_T
ruleLog_Expr_T returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getLog_Expr_TAccess().getLog_Expr_UnaryParserRuleCall_0()); 
    }
    this_Log_Expr_Unary_0=ruleLog_Expr_Unary
    { 
        $current = $this_Log_Expr_Unary_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getLog_Expr_TAccess().getLog_Expr_BinaryParserRuleCall_1()); 
    }
    this_Log_Expr_Binary_1=ruleLog_Expr_Binary
    { 
        $current = $this_Log_Expr_Binary_1.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleLog_Expr_Unary
entryRuleLog_Expr_Unary returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getLog_Expr_UnaryRule()); }
	 iv_ruleLog_Expr_Unary=ruleLog_Expr_Unary 
	 { $current=$iv_ruleLog_Expr_Unary.current; } 
	 EOF 
;

// Rule Log_Expr_Unary
ruleLog_Expr_Unary returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
    { 
        newCompositeNode(grammarAccess.getLog_Expr_UnaryAccess().getLog_NegParserRuleCall_0()); 
    }
    this_Log_Neg_0=ruleLog_Neg
    { 
        $current = $this_Log_Neg_0.current; 
        afterParserOrEnumRuleCall();
    }

    |
    { 
        newCompositeNode(grammarAccess.getLog_Expr_UnaryAccess().getSymParserRuleCall_1()); 
    }
    this_Sym_1=ruleSym
    { 
        $current = $this_Sym_1.current; 
        afterParserOrEnumRuleCall();
    }
)
;





// Entry rule entryRuleLog_Expr_Binary
entryRuleLog_Expr_Binary returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getLog_Expr_BinaryRule()); }
	 iv_ruleLog_Expr_Binary=ruleLog_Expr_Binary 
	 { $current=$iv_ruleLog_Expr_Binary.current; } 
	 EOF 
;

// Rule Log_Expr_Binary
ruleLog_Expr_Binary returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='(' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getLog_Expr_BinaryAccess().getLeftParenthesisKeyword_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getLog_Expr_BinaryAccess().getFstLog_ExprParserRuleCall_1_0()); 
	    }
		lv_fst_1_0=ruleLog_Expr		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getLog_Expr_BinaryRule());
	        }
       		set(
       			$current, 
       			"fst",
        		lv_fst_1_0, 
        		"Log_Expr");
	        afterParserOrEnumRuleCall();
	    }

)
)(
(
(
		lv_operator_2_1=	'AND' 
    {
        newLeafNode(lv_operator_2_1, grammarAccess.getLog_Expr_BinaryAccess().getOperatorANDKeyword_2_0_0());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getLog_Expr_BinaryRule());
	        }
       		setWithLastConsumed($current, "operator", lv_operator_2_1, null);
	    }

    |		lv_operator_2_2=	'OR' 
    {
        newLeafNode(lv_operator_2_2, grammarAccess.getLog_Expr_BinaryAccess().getOperatorORKeyword_2_0_1());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getLog_Expr_BinaryRule());
	        }
       		setWithLastConsumed($current, "operator", lv_operator_2_2, null);
	    }

    |		lv_operator_2_3=	'EQ' 
    {
        newLeafNode(lv_operator_2_3, grammarAccess.getLog_Expr_BinaryAccess().getOperatorEQKeyword_2_0_2());
    }
 
	    {
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getLog_Expr_BinaryRule());
	        }
       		setWithLastConsumed($current, "operator", lv_operator_2_3, null);
	    }

)

)
)(
(
		{ 
	        newCompositeNode(grammarAccess.getLog_Expr_BinaryAccess().getSndLog_ExprParserRuleCall_3_0()); 
	    }
		lv_snd_3_0=ruleLog_Expr		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getLog_Expr_BinaryRule());
	        }
       		set(
       			$current, 
       			"snd",
        		lv_snd_3_0, 
        		"Log_Expr");
	        afterParserOrEnumRuleCall();
	    }

)
)	otherlv_4=')' 
    {
    	newLeafNode(otherlv_4, grammarAccess.getLog_Expr_BinaryAccess().getRightParenthesisKeyword_4());
    }
)
;





// Entry rule entryRuleLog_Neg
entryRuleLog_Neg returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getLog_NegRule()); }
	 iv_ruleLog_Neg=ruleLog_Neg 
	 { $current=$iv_ruleLog_Neg.current; } 
	 EOF 
;

// Rule Log_Neg
ruleLog_Neg returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(	otherlv_0='NOT' 
    {
    	newLeafNode(otherlv_0, grammarAccess.getLog_NegAccess().getNOTKeyword_0());
    }
(
(
		{ 
	        newCompositeNode(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0()); 
	    }
		lv_expr_1_0=ruleLog_Expr		{
	        if ($current==null) {
	            $current = createModelElementForParent(grammarAccess.getLog_NegRule());
	        }
       		set(
       			$current, 
       			"expr",
        		lv_expr_1_0, 
        		"Log_Expr");
	        afterParserOrEnumRuleCall();
	    }

)
))
;





// Entry rule entryRuleSym
entryRuleSym returns [EObject current=null] 
	:
	{ newCompositeNode(grammarAccess.getSymRule()); }
	 iv_ruleSym=ruleSym 
	 { $current=$iv_ruleSym.current; } 
	 EOF 
;

// Rule Sym
ruleSym returns [EObject current=null] 
    @init { enterRule(); 
    }
    @after { leaveRule(); }:
(
(
		lv_sym_0_0=RULE_ID
		{
			newLeafNode(lv_sym_0_0, grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0()); 
		}
		{
	        if ($current==null) {
	            $current = createModelElement(grammarAccess.getSymRule());
	        }
       		setWithLastConsumed(
       			$current, 
       			"sym",
        		lv_sym_0_0, 
        		"ID");
	    }

)
)
;







RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;


