package agg.xt_basis.csp;

import agg.util.csp.BinaryConstraint;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;

public class Constraint_Source extends BinaryConstraint {
	
	public Constraint_Source(Variable src, Variable arc) {
		super(src, arc, 0);
	}

	public void clear() {
		this.itsVar1 = null;
		this.itsVar2 = null;
	}
	
	/**
	 * Return true iff the current instance of <code>src</code> is the source
	 * object of the instance of <code>arc</code>.
	 * <p>
	 * Pre: (1) src.getInstance(), arc.getInstance() instanceof GraphObject.
	 */
	public final boolean execute() {
		boolean result = (getVar1().getInstance().equals(((Arc) getVar2().getInstance()).getSource()));
		// System.out.println("Constraint_Source.execute:: "+result+"
		// "+((GraphObject)getVar1().getGraphObject()).getType().getName()+"
		// "+((Arc) getVar2().getInstance()).getSource().getType().getName());
		return result;
	}
}
