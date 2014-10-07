package agg.xt_basis.csp;

import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.AttrException;
import agg.attribute.AttrManager;
import agg.attribute.AttrMapping;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;
import agg.util.csp.BinaryConstraint;
import agg.util.csp.InstantiationHook;
import agg.util.csp.Variable;
import agg.xt_basis.GraphObject;

/**
 * Please note: This class is only for internal use of the 
 * critical pair analysis for grammars with node type inheritance.
 * Do not use it for any kind of implementations.
 */

public class Constraint_InheritAttribute extends BinaryConstraint implements
		InstantiationHook {
	private GraphObject itsGraphObj;

	private AttrContext itsAttrContext;

	private AttrManager itsAttrManager;

	private AttrMapping itsAttrMapping;

	private boolean attributed = true;

	public Constraint_InheritAttribute(GraphObject graphobj, Variable var,
			AttrContext ac, AttrManager man) {
		super(var, 0);
		var.addInstantiationHook(this);
		this.itsGraphObj = graphobj;
		this.itsAttrContext = ac;
		this.itsAttrManager = man;

		int fact = getWeightFactor();
		if (fact > 0) {
			this.itsWeight = this.itsWeight + fact;
			var.addWeight(this.itsWeight);
		}
	}

	public void clear() {
		this.itsVar1 = null;
		this.itsGraphObj = null;
	}
	
	// protected void resetAttrContext(Variable var, AttrContext ac) {
	// uninstantiate(var);
	// this.itsAttrContext = ac;
	// }

	private int getWeightFactor() {
		if (this.itsGraphObj == null)
			return 0;
		if (this.itsGraphObj.getAttribute() == null)
			return 0;

		VarTuple vars = null;
		if (this.itsAttrContext != null)
			vars = (VarTuple) this.itsAttrContext.getVariables();
		ValueTuple vt = (ValueTuple) this.itsGraphObj.getAttribute();
		for (int i = 0; i < vt.getSize(); i++) {
			ValueMember vm = vt.getValueMemberAt(i);
			if (vm.isSet()) {
				if (vm.getExpr().isConstant())
					return 2;
				else if (vm.getExpr().isVariable()) {
					if (vars != null) {
						VarMember var = vars.getVarMemberAt(vm.getExprAsText());
						if ((var != null) && var.isInputParameter())
							return 3;
					}
				}
			}
		}
		return 1;
	}

	/**
	 * Return true iff the attributes of <code>graphobj</code> and of the
	 * current instance of <code>var</code> match. (The names correspond to my
	 * constructor arguments.)
	 */
	public final boolean execute() {
		boolean result = false;
		if (!this.attributed && this.itsAttrMapping == null)
			result = true;
		else if (this.attributed && this.itsAttrMapping != null)
			result = true;
		else
			result = false;
//		System.out.println("Constraint_InheritAttribute.execute: "+result);
		return result;
	}

	public final void instantiate(Variable var) {
		if (var.getInstance() instanceof GraphObject) {
			if (this.itsGraphObj.getAttribute() == null
					&& ((GraphObject) var.getInstance()).getAttribute() == null) {
				this.attributed = false;
				return;
			}

			try {
				if (this.itsGraphObj.getAttribute() != null) {
					if (((GraphObject) var.getInstance()).getAttribute() != null) {
						if (this.itsGraphObj.getType().isChildOf(((GraphObject) var.getInstance()).getType())) {
							if (((GraphObject) var.getInstance()).getAttribute().getType().compareTo(
									this.itsGraphObj.getAttribute().getType())) {
//								System.out.println("Constraint_InheritAttribute.instantiate::  isChildOf   TRY!");
								this.itsAttrMapping = ((AttrTupleManager)this.itsAttrManager).newMappingChild2Parent(
											this.itsAttrContext, this.itsGraphObj.getAttribute(),
											((GraphObject) var.getInstance()).getAttribute());
//								System.out.println("Constraint_InheritAttribute.instantiate::  isChildOf   OK");
								this.itsAttrMapping.remove();
							}
						}
						else if (this.itsGraphObj.getAttribute().getType().compareTo(
							((GraphObject) var.getInstance()).getAttribute().getType())) {
							this.itsAttrMapping = this.itsAttrManager.newMapping(
									this.itsAttrContext, this.itsGraphObj.getAttribute(),
									((GraphObject) var.getInstance()).getAttribute());
//							System.out.println("Constraint_InheritAttribute.instantiate::  compareTo  OK");
							this.itsAttrMapping.remove();
						}
					} 
				} 				
				else
					this.itsAttrMapping = null;
			} catch (AttrException exc) {
//				System.out.println("Constraint_InheritAttribute.instantiate:: FAILED! "+exc.getMessage());
				this.itsAttrMapping = null;
			}
		}
	}

	public final void uninstantiate(Variable var) {
		if (var.getInstance() instanceof GraphObject) {
			GraphObject go = (GraphObject) var.getInstance();
			if (this.itsAttrContext != null)
				unsetUsedVariable(go);

			if (this.itsAttrMapping != null) {
				this.itsAttrMapping.remove();
				this.itsAttrMapping = null;
			}
		}
	}

	public GraphObject getGraphObject() {
		return this.itsGraphObj;
	}

	private void unsetUsedVariable(GraphObject go) {
		if (go.getAttribute() == null)
			return;

		Vector<String> attrVars = ((ValueTuple) go.getAttribute())
				.getAllVariableNames();
		VarTuple varTup = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < attrVars.size(); i++) {
			String name = attrVars.elementAt(i);
			VarMember vm = varTup.getVarMemberAt(name);
			if (vm != null)
				vm.setExpr(null);
		}
	}

}
