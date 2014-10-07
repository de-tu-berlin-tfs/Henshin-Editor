package agg.xt_basis.csp;

import agg.util.csp.BinaryConstraint;
import agg.util.csp.Variable;
import agg.xt_basis.GraphObject;

/**
 * Please note: This class is only for internal use of the 
 * critical pair analysis for grammars with node type inheritance.
 * Do not use it for any kind of implementations.
 */
public class Constraint_InheritType extends BinaryConstraint {
	
	private GraphObject itsGraphObj;

	public Constraint_InheritType(GraphObject graphobj, Variable var) {
		super(var, 0);
		this.itsGraphObj = graphobj;
	}

	public void clear() {
		this.itsVar1 = null;
		this.itsGraphObj = null;
	}
	
	/**
	 * Return true iff the current instance of <code>obj</code> is type
	 * compatible with the GraphObject that has been passed to my constructor.
	 * In this case, "type" means an element of the cartesian product of
	 * <code>Type</code> x {Node,Arc}.
	 * <p>
	 * Pre: (1) obj.getInstance() instanceof GraphObject.
	 */
	public final boolean execute() {
		if (getVar1().getInstance() instanceof GraphObject) {
			GraphObject go = (GraphObject) getVar1().getInstance();
			if (this.itsGraphObj.getType().isParentOf(go.getType()))
				return true;
			else if (this.itsGraphObj.getType().isChildOf(go.getType())) {
				return true;
			}
		}
		return false;
	}

	public GraphObject getGraphObject() {
		return this.itsGraphObj;
	}
}
