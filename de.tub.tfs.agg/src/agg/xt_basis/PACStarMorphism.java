package agg.xt_basis;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

//import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.attribute.AttrContext;
//import agg.attribute.AttrMapping;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;

/**
 * This class is just a workaround for lacking AttrContext functionality. In
 * class Completion_CSP, we need a purified copy of a morphism's AttrContext. At
 * the moment, we can only create a new one, immitating the way the morphism's
 * original AttrContext was created. Therefore, we need the parent context which
 * was used for the context's creation; this is what getRelatedMatchContext()
 * provides for the special case of a pacstar morphism.
 */
public class PACStarMorphism extends OrdinaryMorphism {
	
	AttrContext itsRelatedMorphContext;

	OrdinaryMorphism itsRelatedMorph;

	OrdinaryMorphism itsPAC;
	
	final Hashtable<String, String> valMembeHashcode2Expr = new Hashtable<String, String>();

	public PACStarMorphism(final Graph orig, final Graph imag, final AttrContext ac) {
		super(orig, imag, ac);
		
		this.itsRelatedMorphContext = ac;
		this.typeObjectsMapChanged = true;
		
		propagateValueFromParentAsInputParameter((VarTuple) this.itsRelatedMorphContext.getVariables(), true);
	}

	public PACStarMorphism(final Graph orig, final Graph imag, final AttrContext ac,
			final OrdinaryMorphism relatedMorph) {
		super(orig, imag, ac);
		
		this.itsRelatedMorphContext = ac;
		this.itsRelatedMorph = relatedMorph;
		this.typeObjectsMapChanged = true;
		
		propagateValueFromParentAsInputParameter((VarTuple) this.itsRelatedMorphContext.getVariables(), true);
	}

	protected void propagateValueFromParentAsInputParameter(final VarTuple parentVar, boolean setAsInputParam) {
		if (parentVar != null) {	
			for (int i = 0; i < parentVar.getSize(); i++) {
				final VarMember pm = (VarMember) parentVar.getMemberAt(i);				
				if (pm.isSet()) {					
					if (pm.getMark() == VarMember.LHS
							|| pm.getMark() == VarMember.RHS) {
						final VarMember m = (VarMember) this.getAttrContext().getVariables().getMemberAt(pm.getName());
						m.setInputParameter(setAsInputParam);
					}
				}
			}			
		}
	}
	
	public AttrContext getRelatedMorphContext() {
		return this.itsRelatedMorphContext;
	}

	public OrdinaryMorphism getRelatedMorph() {
		return this.itsRelatedMorph;
	}
	
	protected boolean tryToApplyAttrExpr() {
		return this.tryToApplyAttrExpr(this.valMembeHashcode2Expr);
	}

	protected void resetAttrValueAsExpr() {
		this.resetAttrValueAsExpr(this.valMembeHashcode2Expr);
		this.valMembeHashcode2Expr.clear();
	}
	
	
	public boolean nextCompletion() {
		this.itsCompleter.setRelatedInstanceVarMap(
				this.itsRelatedMorph.getCompletionStrategy().getInstanceVarMap());
		if (this.typeObjectsMapChanged) {
			this.itsCompleter.resetVariableDomain(true);
			this.itsCompleter.reinitializeSolver(false);
			this.typeObjectsMapChanged = false;
		}
		if (this.partialMorphCompletion) {			
			this.itsCompleter.setPartialMorphism(this);
			this.partialMorphCompletion = false;
		}
		return super.nextCompletion();
	}

	public boolean nextCompletionWithConstantsAndVariablesChecking() {
		if (this.typeObjectsMapChanged) {
			this.itsCompleter.resetVariableDomain(true);
			this.itsCompleter.reinitializeSolver(false);
			this.typeObjectsMapChanged = false;
		}
		if (this.partialMorphCompletion) {
			this.itsCompleter.setPartialMorphism(this);
			this.partialMorphCompletion = false;
		}
		return super.nextCompletionWithConstantsAndVariablesChecking();
	}

	public boolean nextCompletionWithConstantsChecking() {
		if (this.typeObjectsMapChanged) {
			this.itsCompleter.resetVariableDomain(true);
			this.itsCompleter.reinitializeSolver(false);
			this.typeObjectsMapChanged = false;
		}
		if (this.partialMorphCompletion) {
			this.itsCompleter.setPartialMorphism(this);
			this.partialMorphCompletion = false;
		}
		return super.nextCompletionWithConstantsChecking();
	}

	public void clear() {		
		removeAttrMappings();
		
//		unset its own variables only
		unsetVariablesOfPAC();
				
		this.itsDomObjects.clear();
		this.itsCodomObjects.clear();		
		
		this.itsTouchedFlag = true;
		this.itsInteractiveFlag = true;

		clearErrorMsg();
//		this.errors.clear();
		
		this.partialMorphCompletion = false;
		
		this.itsCompleter.resetVariableDomain(true);
		this.itsCompleter.reinitializeSolver(false);
	}

	public void reinit(AttrContext ac) {
		clear();
		this.itsRelatedMorphContext = ac;
//		reinitAttrVars();  //test!!
		this.typeObjectsMapChanged = true;
		
		propagateValueFromParentAsInputParameter((VarTuple) this.itsRelatedMorphContext.getVariables(), true);
	}

	/*
	private void reinitAttrVars() {
//		((VarTuple) this.itsRelatedMatchContext.getVariables()).showVariables();
//		((VarTuple) this.getAttrContext().getVariables()).showVariables();
		
		final Vector<String> nacVars = this.itsOrig.getVariableNamesOfAttributes();
		final VarTuple vt = (VarTuple) itsRelatedMatchContext.getVariables();
		for (int i = 0; i < vt.getNumberOfEntries(); i++) {
			final VarMember vm = (VarMember) vt.getEntryAt(i);
			if (nacVars.contains(vm.getName())
					&& (vm.getMark() == VarMember.LHS)
					&& vm.isSet()) {		
				final VarMember itsvm = ((VarTuple) this.itsAttrContext.getVariables()).getVarMemberAt(vm.getName());
				itsvm.setExprAsText(vm.getExprAsText());
				itsvm.setInputParameter(true);
			}
		}
		
//		((VarTuple) this.getAttrContext().getVariables()).showVariables();
	}
	*/
	
	private void unsetVariablesOfPAC() {
		final Vector<String> pacVars = this.itsOrig.getVariableNamesOfAttributes();
		final VarTuple vt = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < vt.getNumberOfEntries(); i++) {
			final VarMember vm = (VarMember) vt.getEntryAt(i);
			if (pacVars.contains(vm.getName())
					&& (vm.getMark() == VarMember.PAC)) {
				if (!vm.isInputParameter()) {
					((ContextView)this.itsAttrContext).removeValue(vm.getName());					
				}
			}
		}
	}

}
