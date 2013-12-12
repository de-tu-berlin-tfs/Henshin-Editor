package lu.uni.snt.whileDSL.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import lu.uni.snt.whileDSL.services.WHILEGrammarAccess;
import lu.uni.snt.whileDSL.wHILE.Binary;
import lu.uni.snt.whileDSL.wHILE.Comment;
import lu.uni.snt.whileDSL.wHILE.Expr;
import lu.uni.snt.whileDSL.wHILE.Fn_Call;
import lu.uni.snt.whileDSL.wHILE.Fn_Def;
import lu.uni.snt.whileDSL.wHILE.Input;
import lu.uni.snt.whileDSL.wHILE.Neg;
import lu.uni.snt.whileDSL.wHILE.Var;
import lu.uni.snt.whileDSL.wHILE.Var_Def;
import lu.uni.snt.whileDSL.wHILE.WHILEPackage;
import lu.uni.snt.whileDSL.wHILE.WProgram;
import lu.uni.snt.whileDSL.wHILE.While;
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
public class WHILESemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private WHILEGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == WHILEPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case WHILEPackage.BINARY:
				if(context == grammarAccess.getBinaryRule() ||
				   context == grammarAccess.getExpr_TRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Binary(context, (Binary) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.COMMENT:
				if(context == grammarAccess.getCommentRule()) {
					sequence_Comment(context, (Comment) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFgmnt_LST_ElemRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Comment_Fgmnt_LST_Elem(context, (Comment) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.EXPR:
				if(context == grammarAccess.getExprRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Expr(context, (Expr) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.FN_CALL:
				if(context == grammarAccess.getFgmnt_LST_ElemRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Fgmnt_LST_Elem_Fn_Call(context, (Fn_Call) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFn_CallRule()) {
					sequence_Fn_Call(context, (Fn_Call) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.FN_DEF:
				if(context == grammarAccess.getFgmnt_LST_ElemRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Fgmnt_LST_Elem_Fn_Def(context, (Fn_Def) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFn_DefRule()) {
					sequence_Fn_Def(context, (Fn_Def) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.INPUT:
				if(context == grammarAccess.getExpr_TRule() ||
				   context == grammarAccess.getInputRule() ||
				   context == grammarAccess.getTargetRule() ||
				   context == grammarAccess.getUnaryRule()) {
					sequence_Input(context, (Input) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.NEG:
				if(context == grammarAccess.getExpr_TRule() ||
				   context == grammarAccess.getNegRule() ||
				   context == grammarAccess.getTargetRule() ||
				   context == grammarAccess.getUnaryRule()) {
					sequence_Neg(context, (Neg) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.VAR:
				if(context == grammarAccess.getExpr_TRule() ||
				   context == grammarAccess.getTargetRule() ||
				   context == grammarAccess.getUnaryRule() ||
				   context == grammarAccess.getVarRule()) {
					sequence_Var(context, (Var) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.VAR_DEF:
				if(context == grammarAccess.getFgmnt_LST_ElemRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Fgmnt_LST_Elem_Var_Def(context, (Var_Def) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getVar_DefRule()) {
					sequence_Var_Def(context, (Var_Def) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.WPROGRAM:
				if(context == grammarAccess.getTargetRule() ||
				   context == grammarAccess.getWProgramRule()) {
					sequence_WProgram(context, (WProgram) semanticObject); 
					return; 
				}
				else break;
			case WHILEPackage.WHILE:
				if(context == grammarAccess.getFgmnt_LST_ElemRule() ||
				   context == grammarAccess.getTargetRule()) {
					sequence_Fgmnt_LST_Elem_While(context, (While) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getWhileRule()) {
					sequence_While(context, (While) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (fst=Expr (operator='&&' | operator='||' | operator='==') snd=Expr)
	 */
	protected void sequence_Binary(EObject context, Binary semanticObject) {
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
	 *     (comment=STRING next=Fgmnt_LST_Elem?)
	 */
	protected void sequence_Comment_Fgmnt_LST_Elem(EObject context, Comment semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     type=Expr_T
	 */
	protected void sequence_Expr(EObject context, Expr semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, WHILEPackage.Literals.EXPR__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WHILEPackage.Literals.EXPR__TYPE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getExprAccess().getTypeExpr_TParserRuleCall_0(), semanticObject.getType());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (nameF=ID next=Fgmnt_LST_Elem?)
	 */
	protected void sequence_Fgmnt_LST_Elem_Fn_Call(EObject context, Fn_Call semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (nameF=ID body=Fgmnt_LST_Elem next=Fgmnt_LST_Elem?)
	 */
	protected void sequence_Fgmnt_LST_Elem_Fn_Def(EObject context, Fn_Def semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (left=Var right=Expr next=Fgmnt_LST_Elem?)
	 */
	protected void sequence_Fgmnt_LST_Elem_Var_Def(EObject context, Var_Def semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (expr=Expr fgmnt=Fgmnt_LST_Elem next=Fgmnt_LST_Elem?)
	 */
	protected void sequence_Fgmnt_LST_Elem_While(EObject context, While semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     nameF=ID
	 */
	protected void sequence_Fn_Call(EObject context, Fn_Call semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (nameF=ID body=Fgmnt_LST_Elem)
	 */
	protected void sequence_Fn_Def(EObject context, Fn_Def semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {Input}
	 */
	protected void sequence_Input(EObject context, Input semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     expr=Expr
	 */
	protected void sequence_Neg(EObject context, Neg semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, WHILEPackage.Literals.NEG__EXPR) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WHILEPackage.Literals.NEG__EXPR));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getNegAccess().getExprExprParserRuleCall_1_0(), semanticObject.getExpr());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (left=Var right=Expr)
	 */
	protected void sequence_Var_Def(EObject context, Var_Def semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     label=ID
	 */
	protected void sequence_Var(EObject context, Var semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, WHILEPackage.Literals.VAR__LABEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WHILEPackage.Literals.VAR__LABEL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getVarAccess().getLabelIDTerminalRuleCall_0(), semanticObject.getLabel());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     fst=Fgmnt_LST_Elem
	 */
	protected void sequence_WProgram(EObject context, WProgram semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, WHILEPackage.Literals.WPROGRAM__FST) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, WHILEPackage.Literals.WPROGRAM__FST));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getWProgramAccess().getFstFgmnt_LST_ElemParserRuleCall_0(), semanticObject.getFst());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (expr=Expr fgmnt=Fgmnt_LST_Elem)
	 */
	protected void sequence_While(EObject context, While semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
