/**
 * 
 */
package agg.xt_basis;

import java.util.List;
import java.util.Vector;

import agg.cons.Evaluable;

/**
 * @author olga
 *
 * A shifted PAC (Positive Application Condition) can be created 
 * during creation a concurrent rule of two arbitrary rules.
 * A shifted PAC can contain one or more arbitrary PAC(s).
 * In case of more then one PAC a shifted PAC is satisfied 
 * when at least one of the arbitrary PAC(s) is satisfied.
 */
public class ShiftedPAC implements Evaluable {

	
	final Vector<OrdinaryMorphism> pacs = new Vector<OrdinaryMorphism>();
	
	
	public ShiftedPAC(final List<OrdinaryMorphism> list) {
		this.pacs.addAll(list);
	}
	
	public boolean contains(final OrdinaryMorphism pac) {
		return this.pacs.contains(pac);
	}
	
	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#eval(java.lang.Object)
	 */
	public boolean eval(Object o) {
		if (o instanceof Match) {
			// pacs(0) v pacs(1) v pacs(2) v ....
			for (int i=0; i<this.pacs.size(); i++) {
				OrdinaryMorphism pac = this.pacs.get(i);
				if (!MatchHelper.isDomainOfApplCondEmpty((Match) o, pac)) {
					if (((Match) o).checkPAC(pac) != null) {
						return true;
					}
				} 
			}
		}
		
		return false;
	}

	
	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#eval(java.lang.Object, int)
	 */
	public boolean eval(Object o, int tick) {
		return eval(o);
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#eval(java.lang.Object, boolean)
	 */
	public boolean eval(Object o, boolean negation) {
		return eval(o);
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#eval(java.lang.Object, int, boolean)
	 */
	public boolean eval(Object o, int tick, boolean negation) {
		return eval(o);
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#evalForall(java.lang.Object, int, boolean)
	 */
	public boolean evalForall(Object o, int tick) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		return "ShiftedPAC";
	}

}
