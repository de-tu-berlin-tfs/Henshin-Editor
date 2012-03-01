package agg.xt_basis.csp;

import agg.util.csp.BinaryConstraint;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;

public class Constraint_InheritTarget extends BinaryConstraint {
	
	public Constraint_InheritTarget(Variable tar, Variable arc) {
		super(tar, arc, 3);
	}

	public void clear() {
		this.itsVar1 = null;
		this.itsVar2 = null;
	}
	
	/**
	 * Return true iff the current instance of <code>tar</code> is the target
	 * object of the instance of <code>arc</code>.
	 * <p>
	 * Pre: (1) tar.getInstance(), arc.getInstance() instanceof GraphObject.
	 */
	public final boolean execute() {
		boolean result = (getVar1().getInstance().equals(((Arc) getVar2()
				.getInstance()).getTarget()));
//		if (!result) {
//		 System.out.println("Constraint_InheritTarget.execute:: FAILED "
//		 +((GraphObject)getVar1().getGraphObject()).getType().getName()+"   "
//		 +((Arc) getVar2().getInstance()).getTarget().getType().getName());
//		} else {
//			System.out.println("Constraint_InheritTarget.execute:: DONE "
//					 +((GraphObject)getVar1().getGraphObject()).getType().getName()+"   "
//					 +((Arc) getVar2().getInstance()).getTarget().getType().getName());
//		}
		return result;
	}
}
