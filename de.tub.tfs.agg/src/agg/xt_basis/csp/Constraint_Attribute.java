package agg.xt_basis.csp;

import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.AttrException;
import agg.attribute.AttrInstance;
import agg.attribute.AttrManager;
import agg.attribute.AttrMapping;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.csp.BinaryConstraint;
import agg.util.csp.InstantiationHook;
import agg.util.csp.Variable;
import agg.xt_basis.GraphObject;

public class Constraint_Attribute extends BinaryConstraint implements
		InstantiationHook {
	
	private GraphObject itsGraphObj;

	private AttrContext itsAttrContext;

	private AttrManager itsAttrManager;

	private AttrMapping itsAttrMapping;

	private boolean attributed = true;
	
	// pablo -->
	/**
	 * State of instantiation/uninstantiation process.
	 * Avoids unnecessary calls of instantiation_intern.
	 */
	private int state = NOTHING;

	/**
	 * Do nothing.
	 */
	private static final int NOTHING = -1;

	/**
	 * Instantiate, if execute()-method is called.
	 */
	private static final int INSTANTIATE = 0;

	/**
	 * Uninstantiate, if execute()-method was called.
	 */
	private static final int UNINSTANTIATE = 1;

	/**
	 * Variable, which the constraint should be instantiated with.
	 */
	private Variable instantiateVariable = null;
	// pablo >
	
	
	public Constraint_Attribute(GraphObject graphobj, Variable var,
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
	
	private int getWeightFactor() {
		if (this.itsGraphObj == null
			|| this.itsGraphObj.getAttribute() == null) {
			return 0;
		}
		
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
		// pablo -->
		if(this.state == INSTANTIATE) {
			instantiate_intern(this.instantiateVariable);
			this.state = UNINSTANTIATE;
		}
		// pablo >
		
		if ( (!this.attributed && this.itsAttrMapping == null)
			|| (this.attributed && this.itsAttrMapping != null) )
			return true;
		
		return false;
	}

	// pablo -->
	public final void instantiate(Variable var) {
		this.state = INSTANTIATE;
		this.instantiateVariable = var;
	}
	
	public final void uninstantiate(Variable var) {
		if(this.state == UNINSTANTIATE)
			uninstantiate_intern(var);		

		this.state = NOTHING;
		this.instantiateVariable = null;
	}
	
	public final void instantiate_intern(Variable var) {
		if (!(var.getInstance() instanceof GraphObject))
			return;
		
		if (!this.itsGraphObj.attrExists()) {
			this.attributed = false;
			return;
		}
		
		AttrInstance origAttr = this.itsGraphObj.getAttribute();
		AttrInstance instAttr = ((GraphObject) var.getInstance()).getAttribute();		
		
		try {
			if (origAttr != null && instAttr != null) {
				this.itsAttrMapping = this.itsAttrManager.newMapping(
						this.itsAttrContext, this.itsGraphObj.getAttribute(),
						((GraphObject) var.getInstance()).getAttribute());				
				this.itsAttrMapping.remove();
			}
		} catch (AttrException exc) {
			if (this.itsAttrMapping != null) {
				this.itsAttrMapping.remove();
				this.itsAttrMapping = null;
			}
		}
	}
	
	
	public final void uninstantiate_intern(Variable var) {
		if (var.getInstance() instanceof GraphObject) {
			GraphObject go = (GraphObject) var.getInstance();
			if (this.itsAttrContext != null 
					&& go.getAttribute() != null) {
				unsetUsedVariable(go);
			}
			
			if (this.itsAttrMapping != null) {
				this.itsAttrMapping.remove();
				this.itsAttrMapping = null;
			}
		}
	}
	// pablo >
	
	public GraphObject getGraphObject() {
		return this.itsGraphObj;
	}

	private void unsetUsedVariable(GraphObject go) {
		final Vector<String> attrVars = ((ValueTuple) go.getAttribute())
				.getAllVariableNames();
		if (attrVars.size() > 0) {
			final VarTuple varTup = (VarTuple) this.itsAttrContext.getVariables();
			for (int i = 0; i < attrVars.size(); i++) {
				final String name = attrVars.elementAt(i);
				final VarMember vm = varTup.getVarMemberAt(name);
				if (vm != null)
					vm.setExpr(null);
			}
		}
	}
	
	/*
	private void showUsedVariable(GraphObject go) {
		if (go.getAttribute() == null)
			return;

		Vector<String> attrVars = ((ValueTuple) go.getAttribute())
				.getAllVariableNames();
		VarTuple varTup = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < attrVars.size(); i++) {
			String name = attrVars.elementAt(i);
			VarMember vm = varTup.getVarMemberAt(name);
			System.out.println("Variable: "+name+" = "+vm.getExpr()+"  hash: "+vm.hashCode()+"    "+this.itsAttrContext.hashCode());
		}
	}
*/
}
