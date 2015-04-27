/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package lu.uni.snt.repeat.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import lu.uni.snt.repeat.rEPEAT.Asg;
import lu.uni.snt.repeat.rEPEAT.Comment;
import lu.uni.snt.repeat.rEPEAT.Log_Expr;
import lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary;
import lu.uni.snt.repeat.rEPEAT.Log_Neg;
import lu.uni.snt.repeat.rEPEAT.REPEATPackage;
import lu.uni.snt.repeat.rEPEAT.RProgram;
import lu.uni.snt.repeat.rEPEAT.Read;
import lu.uni.snt.repeat.rEPEAT.Repeat;
import lu.uni.snt.repeat.rEPEAT.Sym;
import lu.uni.snt.repeat.services.REPEATGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class REPEATSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private REPEATGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == REPEATPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case REPEATPackage.ASG:
				if(context == grammarAccess.getAsgRule()) {
					sequence_Asg(context, (Asg) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSourceRule() ||
				   context == grammarAccess.getStmnt_LST_ElemRule()) {
					sequence_Asg_Stmnt_LST_Elem(context, (Asg) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.COMMENT:
				if(context == grammarAccess.getCommentRule()) {
					sequence_Comment(context, (Comment) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSourceRule() ||
				   context == grammarAccess.getStmnt_LST_ElemRule()) {
					sequence_Comment_Stmnt_LST_Elem(context, (Comment) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.LOG_EXPR:
				if(context == grammarAccess.getLog_ExprRule() ||
				   context == grammarAccess.getSourceRule()) {
					sequence_Log_Expr(context, (Log_Expr) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.LOG_EXPR_BINARY:
				if(context == grammarAccess.getLog_Expr_BinaryRule() ||
				   context == grammarAccess.getLog_Expr_TRule() ||
				   context == grammarAccess.getSourceRule()) {
					sequence_Log_Expr_Binary(context, (Log_Expr_Binary) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.LOG_NEG:
				if(context == grammarAccess.getLog_Expr_TRule() ||
				   context == grammarAccess.getLog_Expr_UnaryRule() ||
				   context == grammarAccess.getLog_NegRule() ||
				   context == grammarAccess.getSourceRule()) {
					sequence_Log_Neg(context, (Log_Neg) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.RPROGRAM:
				if(context == grammarAccess.getRProgramRule() ||
				   context == grammarAccess.getSourceRule()) {
					sequence_RProgram(context, (RProgram) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.READ:
				if(context == grammarAccess.getReadRule()) {
					sequence_Read(context, (Read) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSourceRule() ||
				   context == grammarAccess.getStmnt_LST_ElemRule()) {
					sequence_Read_Stmnt_LST_Elem(context, (Read) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.REPEAT:
				if(context == grammarAccess.getRepeatRule()) {
					sequence_Repeat(context, (Repeat) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSourceRule() ||
				   context == grammarAccess.getStmnt_LST_ElemRule()) {
					sequence_Repeat_Stmnt_LST_Elem(context, (Repeat) semanticObject); 
					return; 
				}
				else break;
			case REPEATPackage.SYM:
				if(context == grammarAccess.getLog_Expr_TRule() ||
				   context == grammarAccess.getLog_Expr_UnaryRule() ||
				   context == grammarAccess.getSourceRule() ||
				   context == grammarAccess.getSymRule()) {
					sequence_Sym(context, (Sym) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (left=Sym right=Sym)
	 */
	protected void sequence_Asg(EObject context, Asg semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=Sym right=Sym next=Stmnt_LST_Elem?)
	 */
	protected void sequence_Asg_Stmnt_LST_Elem(EObject context, Asg semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     comment=STRING
	 */
	protected void sequence_Comment(EObject context, Comment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (comment=STRING next=Stmnt_LST_Elem?)
	 */
	protected void sequence_Comment_Stmnt_LST_Elem(EObject context, Comment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (fst=Log_Expr (operator='AND' | operator='OR' | operator='EQ') snd=Log_Expr)
	 */
	protected void sequence_Log_Expr_Binary(EObject context, Log_Expr_Binary semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     type=Log_Expr_T
	 */
	protected void sequence_Log_Expr(EObject context, Log_Expr semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, REPEATPackage.Literals.LOG_EXPR__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, REPEATPackage.Literals.LOG_EXPR__TYPE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLog_ExprAccess().getTypeLog_Expr_TParserRuleCall_0(), semanticObject.getType());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     expr=Log_Expr
	 */
	protected void sequence_Log_Neg(EObject context, Log_Neg semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, REPEATPackage.Literals.LOG_NEG__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, REPEATPackage.Literals.LOG_NEG__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLog_NegAccess().getExprLog_ExprParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     fst=Stmnt_LST_Elem
	 */
	protected void sequence_RProgram(EObject context, RProgram semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, REPEATPackage.Literals.RPROGRAM__FST) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, REPEATPackage.Literals.RPROGRAM__FST));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRProgramAccess().getFstStmnt_LST_ElemParserRuleCall_0(), semanticObject.getFst());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     param=Sym
	 */
	protected void sequence_Read(EObject context, Read semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (param=Sym next=Stmnt_LST_Elem?)
	 */
	protected void sequence_Read_Stmnt_LST_Elem(EObject context, Read semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (stmnt=Stmnt_LST_Elem expr=Log_Expr)
	 */
	protected void sequence_Repeat(EObject context, Repeat semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (stmnt=Stmnt_LST_Elem expr=Log_Expr next=Stmnt_LST_Elem?)
	 */
	protected void sequence_Repeat_Stmnt_LST_Elem(EObject context, Repeat semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     sym=ID
	 */
	protected void sequence_Sym(EObject context, Sym semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, REPEATPackage.Literals.SYM__SYM) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, REPEATPackage.Literals.SYM__SYM));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSymAccess().getSymIDTerminalRuleCall_0(), semanticObject.getSym());
		feeder.finish();
	}
}
