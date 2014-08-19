package agg.parser;

import java.util.Vector;

import agg.cons.Formula;
import agg.util.Pair;
import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

public class PrioritySimpleExcludePair extends SimpleExcludePair {

	/**
	 * Creates a new object to compute critical pairs.
	 */
	public PrioritySimpleExcludePair() {
		super();
	}

	/**
	 * computes if there is a critical pair of a special kind. Remenber if ther
	 * isn null is returned if the pair is not critical otherwiser a object
	 * which can explain in which way this pair is critical. One possible object
	 * can be a <code>Vector</code> of overlaping graphs. If a kind kind is
	 * requested which cannot be computed a
	 * <code>InvalidAlgorithmException</code> is thrown.
	 * 
	 * @param kind
	 *            specifies the kind of critical pair
	 * @param r1
	 *            defines the first part which can be critical
	 * @param r2
	 *            the second part which can be critical
	 * @throws InvalidAlgorithmException
	 *             Thrown if a illegal algorithm is selected.
	 * @return The object which is critic of the two rules
	 */
	public Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> isCritical(int kind, Rule r1, Rule r2)
			throws InvalidAlgorithmException {
		// System.out.println("LayeredSimpleExcludePair.isCritical ");
		if (kind == EXCLUDE || kind == CONFLICTFREE) {
			boolean samePriority = r1.getPriority() == r2.getPriority();
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> result = null;
			if (kind == EXCLUDE) {
				if (samePriority) 
					result = super.isCritical(kind, r1, r2);
			} else if (kind == CONFLICTFREE) {
				if (samePriority) 
					result = super.isCritical(kind, r1, r2);
			}
			return result;
		} 
		throw new InvalidAlgorithmException("No such Algorithm", kind);
	}

	protected boolean checkGraphConsistency(Graph g, int l) {
		Vector<Formula> constraints = this.grammar.getConstraintsForPriority(-1);
		if (this.grammar.checkGraphConsistency(g, constraints)) {
			constraints = this.grammar.getConstraintsForPriority(l);
			if (this.grammar.checkGraphConsistency(g, constraints))
				return true;
			
			return false;
		} 
		return false;
	}
}