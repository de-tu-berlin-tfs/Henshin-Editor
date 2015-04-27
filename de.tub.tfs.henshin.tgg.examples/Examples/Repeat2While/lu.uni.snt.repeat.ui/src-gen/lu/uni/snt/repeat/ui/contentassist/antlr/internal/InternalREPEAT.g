/*
* generated by Xtext
*/
grammar InternalREPEAT;

options {
	superClass=AbstractInternalContentAssistParser;
	
}

@lexer::header {
package lu.uni.snt.repeat.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package lu.uni.snt.repeat.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import lu.uni.snt.repeat.services.REPEATGrammarAccess;

}

@parser::members {
 
 	private REPEATGrammarAccess grammarAccess;
 	
    public void setGrammarAccess(REPEATGrammarAccess grammarAccess) {
    	this.grammarAccess = grammarAccess;
    }
    
    @Override
    protected Grammar getGrammar() {
    	return grammarAccess.getGrammar();
    }
    
    @Override
    protected String getValueForTokenName(String tokenName) {
    	return tokenName;
    }

}




// Entry rule entryRuleRProgram
entryRuleRProgram 
:
{ before(grammarAccess.getRProgramRule()); }
	 ruleRProgram
{ after(grammarAccess.getRProgramRule()); } 
	 EOF 
;

// Rule RProgram
ruleRProgram
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getRProgramAccess().getFstAssignment()); }
(rule__RProgram__FstAssignment)
{ after(grammarAccess.getRProgramAccess().getFstAssignment()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleStmnt_LST_Elem
entryRuleStmnt_LST_Elem 
:
{ before(grammarAccess.getStmnt_LST_ElemRule()); }
	 ruleStmnt_LST_Elem
{ after(grammarAccess.getStmnt_LST_ElemRule()); } 
	 EOF 
;

// Rule Stmnt_LST_Elem
ruleStmnt_LST_Elem
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getGroup()); }
(rule__Stmnt_LST_Elem__Group__0)
{ after(grammarAccess.getStmnt_LST_ElemAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleRepeat
entryRuleRepeat 
:
{ before(grammarAccess.getRepeatRule()); }
	 ruleRepeat
{ after(grammarAccess.getRepeatRule()); } 
	 EOF 
;

// Rule Repeat
ruleRepeat
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getRepeatAccess().getGroup()); }
(rule__Repeat__Group__0)
{ after(grammarAccess.getRepeatAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleAsg
entryRuleAsg 
:
{ before(grammarAccess.getAsgRule()); }
	 ruleAsg
{ after(grammarAccess.getAsgRule()); } 
	 EOF 
;

// Rule Asg
ruleAsg
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getAsgAccess().getGroup()); }
(rule__Asg__Group__0)
{ after(grammarAccess.getAsgAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleRead
entryRuleRead 
:
{ before(grammarAccess.getReadRule()); }
	 ruleRead
{ after(grammarAccess.getReadRule()); } 
	 EOF 
;

// Rule Read
ruleRead
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getReadAccess().getGroup()); }
(rule__Read__Group__0)
{ after(grammarAccess.getReadAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleComment
entryRuleComment 
:
{ before(grammarAccess.getCommentRule()); }
	 ruleComment
{ after(grammarAccess.getCommentRule()); } 
	 EOF 
;

// Rule Comment
ruleComment
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getCommentAccess().getGroup()); }
(rule__Comment__Group__0)
{ after(grammarAccess.getCommentAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleLog_Expr
entryRuleLog_Expr 
:
{ before(grammarAccess.getLog_ExprRule()); }
	 ruleLog_Expr
{ after(grammarAccess.getLog_ExprRule()); } 
	 EOF 
;

// Rule Log_Expr
ruleLog_Expr
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getLog_ExprAccess().getTypeAssignment()); }
(rule__Log_Expr__TypeAssignment)
{ after(grammarAccess.getLog_ExprAccess().getTypeAssignment()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleLog_Expr_T
entryRuleLog_Expr_T 
:
{ before(grammarAccess.getLog_Expr_TRule()); }
	 ruleLog_Expr_T
{ after(grammarAccess.getLog_Expr_TRule()); } 
	 EOF 
;

// Rule Log_Expr_T
ruleLog_Expr_T
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getLog_Expr_TAccess().getAlternatives()); }
(rule__Log_Expr_T__Alternatives)
{ after(grammarAccess.getLog_Expr_TAccess().getAlternatives()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleLog_Expr_Unary
entryRuleLog_Expr_Unary 
:
{ before(grammarAccess.getLog_Expr_UnaryRule()); }
	 ruleLog_Expr_Unary
{ after(grammarAccess.getLog_Expr_UnaryRule()); } 
	 EOF 
;

// Rule Log_Expr_Unary
ruleLog_Expr_Unary
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getLog_Expr_UnaryAccess().getAlternatives()); }
(rule__Log_Expr_Unary__Alternatives)
{ after(grammarAccess.getLog_Expr_UnaryAccess().getAlternatives()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleLog_Expr_Binary
entryRuleLog_Expr_Binary 
:
{ before(grammarAccess.getLog_Expr_BinaryRule()); }
	 ruleLog_Expr_Binary
{ after(grammarAccess.getLog_Expr_BinaryRule()); } 
	 EOF 
;

// Rule Log_Expr_Binary
ruleLog_Expr_Binary
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getGroup()); }
(rule__Log_Expr_Binary__Group__0)
{ after(grammarAccess.getLog_Expr_BinaryAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleLog_Neg
entryRuleLog_Neg 
:
{ before(grammarAccess.getLog_NegRule()); }
	 ruleLog_Neg
{ after(grammarAccess.getLog_NegRule()); } 
	 EOF 
;

// Rule Log_Neg
ruleLog_Neg
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getLog_NegAccess().getGroup()); }
(rule__Log_Neg__Group__0)
{ after(grammarAccess.getLog_NegAccess().getGroup()); }
)

;
finally {
	restoreStackSize(stackSize);
}



// Entry rule entryRuleSym
entryRuleSym 
:
{ before(grammarAccess.getSymRule()); }
	 ruleSym
{ after(grammarAccess.getSymRule()); } 
	 EOF 
;

// Rule Sym
ruleSym
    @init {
		int stackSize = keepStackSize();
    }
	:
(
{ before(grammarAccess.getSymAccess().getSymAssignment()); }
(rule__Sym__SymAssignment)
{ after(grammarAccess.getSymAccess().getSymAssignment()); }
)

;
finally {
	restoreStackSize(stackSize);
}






rule__Stmnt_LST_Elem__Alternatives_0
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getRepeatParserRuleCall_0_0()); }
	ruleRepeat
{ after(grammarAccess.getStmnt_LST_ElemAccess().getRepeatParserRuleCall_0_0()); }
)

    |(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getAsgParserRuleCall_0_1()); }
	ruleAsg
{ after(grammarAccess.getStmnt_LST_ElemAccess().getAsgParserRuleCall_0_1()); }
)

    |(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getReadParserRuleCall_0_2()); }
	ruleRead
{ after(grammarAccess.getStmnt_LST_ElemAccess().getReadParserRuleCall_0_2()); }
)

    |(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getCommentParserRuleCall_0_3()); }
	ruleComment
{ after(grammarAccess.getStmnt_LST_ElemAccess().getCommentParserRuleCall_0_3()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_T__Alternatives
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_TAccess().getLog_Expr_UnaryParserRuleCall_0()); }
	ruleLog_Expr_Unary
{ after(grammarAccess.getLog_Expr_TAccess().getLog_Expr_UnaryParserRuleCall_0()); }
)

    |(
{ before(grammarAccess.getLog_Expr_TAccess().getLog_Expr_BinaryParserRuleCall_1()); }
	ruleLog_Expr_Binary
{ after(grammarAccess.getLog_Expr_TAccess().getLog_Expr_BinaryParserRuleCall_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Unary__Alternatives
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_UnaryAccess().getLog_NegParserRuleCall_0()); }
	ruleLog_Neg
{ after(grammarAccess.getLog_Expr_UnaryAccess().getLog_NegParserRuleCall_0()); }
)

    |(
{ before(grammarAccess.getLog_Expr_UnaryAccess().getSymParserRuleCall_1()); }
	ruleSym
{ after(grammarAccess.getLog_Expr_UnaryAccess().getSymParserRuleCall_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__OperatorAlternatives_2_0
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorANDKeyword_2_0_0()); }

	'AND' 

{ after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorANDKeyword_2_0_0()); }
)

    |(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorORKeyword_2_0_1()); }

	'OR' 

{ after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorORKeyword_2_0_1()); }
)

    |(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorEQKeyword_2_0_2()); }

	'EQ' 

{ after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorEQKeyword_2_0_2()); }
)

;
finally {
	restoreStackSize(stackSize);
}




rule__Stmnt_LST_Elem__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Stmnt_LST_Elem__Group__0__Impl
	rule__Stmnt_LST_Elem__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Stmnt_LST_Elem__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getAlternatives_0()); }
(rule__Stmnt_LST_Elem__Alternatives_0)
{ after(grammarAccess.getStmnt_LST_ElemAccess().getAlternatives_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Stmnt_LST_Elem__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Stmnt_LST_Elem__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Stmnt_LST_Elem__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getNextAssignment_1()); }
(rule__Stmnt_LST_Elem__NextAssignment_1)?
{ after(grammarAccess.getStmnt_LST_ElemAccess().getNextAssignment_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}






rule__Repeat__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Repeat__Group__0__Impl
	rule__Repeat__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Repeat__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRepeatAccess().getREPEATKeyword_0()); }

	'REPEAT' 

{ after(grammarAccess.getRepeatAccess().getREPEATKeyword_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Repeat__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Repeat__Group__1__Impl
	rule__Repeat__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Repeat__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRepeatAccess().getStmntAssignment_1()); }
(rule__Repeat__StmntAssignment_1)
{ after(grammarAccess.getRepeatAccess().getStmntAssignment_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Repeat__Group__2
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Repeat__Group__2__Impl
	rule__Repeat__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Repeat__Group__2__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRepeatAccess().getUNTILKeyword_2()); }

	'UNTIL' 

{ after(grammarAccess.getRepeatAccess().getUNTILKeyword_2()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Repeat__Group__3
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Repeat__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Repeat__Group__3__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRepeatAccess().getExprAssignment_3()); }
(rule__Repeat__ExprAssignment_3)
{ after(grammarAccess.getRepeatAccess().getExprAssignment_3()); }
)

;
finally {
	restoreStackSize(stackSize);
}










rule__Asg__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Asg__Group__0__Impl
	rule__Asg__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Asg__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getAsgAccess().getLeftAssignment_0()); }
(rule__Asg__LeftAssignment_0)
{ after(grammarAccess.getAsgAccess().getLeftAssignment_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Asg__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Asg__Group__1__Impl
	rule__Asg__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Asg__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getAsgAccess().getColonEqualsSignKeyword_1()); }

	':=' 

{ after(grammarAccess.getAsgAccess().getColonEqualsSignKeyword_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Asg__Group__2
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Asg__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Asg__Group__2__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getAsgAccess().getRightAssignment_2()); }
(rule__Asg__RightAssignment_2)
{ after(grammarAccess.getAsgAccess().getRightAssignment_2()); }
)

;
finally {
	restoreStackSize(stackSize);
}








rule__Read__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Read__Group__0__Impl
	rule__Read__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Read__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getReadAccess().getREADKeyword_0()); }

	'READ' 

{ after(grammarAccess.getReadAccess().getREADKeyword_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Read__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Read__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Read__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getReadAccess().getParamAssignment_1()); }
(rule__Read__ParamAssignment_1)
{ after(grammarAccess.getReadAccess().getParamAssignment_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}






rule__Comment__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Comment__Group__0__Impl
	rule__Comment__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Comment__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0()); }

	'/#' 

{ after(grammarAccess.getCommentAccess().getSolidusNumberSignKeyword_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Comment__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Comment__Group__1__Impl
	rule__Comment__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Comment__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getCommentAccess().getCommentAssignment_1()); }
(rule__Comment__CommentAssignment_1)
{ after(grammarAccess.getCommentAccess().getCommentAssignment_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Comment__Group__2
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Comment__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Comment__Group__2__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getCommentAccess().getNumberSignSolidusKeyword_2()); }

	'#/' 

{ after(grammarAccess.getCommentAccess().getNumberSignSolidusKeyword_2()); }
)

;
finally {
	restoreStackSize(stackSize);
}








rule__Log_Expr_Binary__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Expr_Binary__Group__0__Impl
	rule__Log_Expr_Binary__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getLeftParenthesisKeyword_0()); }

	'(' 

{ after(grammarAccess.getLog_Expr_BinaryAccess().getLeftParenthesisKeyword_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Log_Expr_Binary__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Expr_Binary__Group__1__Impl
	rule__Log_Expr_Binary__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getFstAssignment_1()); }
(rule__Log_Expr_Binary__FstAssignment_1)
{ after(grammarAccess.getLog_Expr_BinaryAccess().getFstAssignment_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Log_Expr_Binary__Group__2
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Expr_Binary__Group__2__Impl
	rule__Log_Expr_Binary__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__Group__2__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAssignment_2()); }
(rule__Log_Expr_Binary__OperatorAssignment_2)
{ after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAssignment_2()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Log_Expr_Binary__Group__3
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Expr_Binary__Group__3__Impl
	rule__Log_Expr_Binary__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__Group__3__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getSndAssignment_3()); }
(rule__Log_Expr_Binary__SndAssignment_3)
{ after(grammarAccess.getLog_Expr_BinaryAccess().getSndAssignment_3()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Log_Expr_Binary__Group__4
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Expr_Binary__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__Group__4__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getRightParenthesisKeyword_4()); }

	')' 

{ after(grammarAccess.getLog_Expr_BinaryAccess().getRightParenthesisKeyword_4()); }
)

;
finally {
	restoreStackSize(stackSize);
}












rule__Log_Neg__Group__0
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Neg__Group__0__Impl
	rule__Log_Neg__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Neg__Group__0__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_NegAccess().getNOTKeyword_0()); }

	'NOT' 

{ after(grammarAccess.getLog_NegAccess().getNOTKeyword_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


rule__Log_Neg__Group__1
    @init {
		int stackSize = keepStackSize();
    }
:
	rule__Log_Neg__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Neg__Group__1__Impl
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_NegAccess().getExprAssignment_1()); }
(rule__Log_Neg__ExprAssignment_1)
{ after(grammarAccess.getLog_NegAccess().getExprAssignment_1()); }
)

;
finally {
	restoreStackSize(stackSize);
}







rule__RProgram__FstAssignment
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0()); }
	ruleStmnt_LST_Elem{ after(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Stmnt_LST_Elem__NextAssignment_1
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getStmnt_LST_ElemAccess().getNextStmnt_LST_ElemParserRuleCall_1_0()); }
	ruleStmnt_LST_Elem{ after(grammarAccess.getStmnt_LST_ElemAccess().getNextStmnt_LST_ElemParserRuleCall_1_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Repeat__StmntAssignment_1
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRepeatAccess().getStmntStmnt_LST_ElemParserRuleCall_1_0()); }
	ruleStmnt_LST_Elem{ after(grammarAccess.getRepeatAccess().getStmntStmnt_LST_ElemParserRuleCall_1_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Repeat__ExprAssignment_3
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getRepeatAccess().getExprLog_ExprParserRuleCall_3_0()); }
	ruleLog_Expr{ after(grammarAccess.getRepeatAccess().getExprLog_ExprParserRuleCall_3_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Asg__LeftAssignment_0
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getAsgAccess().getLeftSymParserRuleCall_0_0()); }
	ruleSym{ after(grammarAccess.getAsgAccess().getLeftSymParserRuleCall_0_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Asg__RightAssignment_2
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getAsgAccess().getRightSymParserRuleCall_2_0()); }
	ruleSym{ after(grammarAccess.getAsgAccess().getRightSymParserRuleCall_2_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Read__ParamAssignment_1
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getReadAccess().getParamSymParserRuleCall_1_0()); }
	ruleSym{ after(grammarAccess.getReadAccess().getParamSymParserRuleCall_1_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Comment__CommentAssignment_1
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getCommentAccess().getCommentSTRINGTerminalRuleCall_1_0()); }
	RULE_STRING{ after(grammarAccess.getCommentAccess().getCommentSTRINGTerminalRuleCall_1_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr__TypeAssignment
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0()); }
	ruleLog_Expr_T{ after(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__FstAssignment_1
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getFstLog_ExprParserRuleCall_1_0()); }
	ruleLog_Expr{ after(grammarAccess.getLog_Expr_BinaryAccess().getFstLog_ExprParserRuleCall_1_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__OperatorAssignment_2
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAlternatives_2_0()); }
(rule__Log_Expr_Binary__OperatorAlternatives_2_0)
{ after(grammarAccess.getLog_Expr_BinaryAccess().getOperatorAlternatives_2_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Expr_Binary__SndAssignment_3
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_Expr_BinaryAccess().getSndLog_ExprParserRuleCall_3_0()); }
	ruleLog_Expr{ after(grammarAccess.getLog_Expr_BinaryAccess().getSndLog_ExprParserRuleCall_3_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Log_Neg__ExprAssignment_1
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0()); }
	ruleLog_Expr{ after(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}

rule__Sym__SymAssignment
    @init {
		int stackSize = keepStackSize();
    }
:
(
{ before(grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0()); }
	RULE_ID{ after(grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0()); }
)

;
finally {
	restoreStackSize(stackSize);
}


RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;


