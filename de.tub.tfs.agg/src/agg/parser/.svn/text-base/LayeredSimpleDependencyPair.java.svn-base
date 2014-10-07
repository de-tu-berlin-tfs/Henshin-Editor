package agg.parser;

import java.util.List;
import java.util.Vector;

import agg.cons.Formula;
import agg.util.Pair;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Graph;

/**
 * These pairs extends the <CODE>SimpleDependencyPairs</CODE> with layers.
 * 
 * @author $Author: olga $
 */
public class LayeredSimpleDependencyPair extends SimpleDependencyPair {

	@SuppressWarnings("deprecation")
	private LayerFunction layer;

	/**
	 * Creates a new object to compute critical pairs.
	 */
	public LayeredSimpleDependencyPair() {
		super();
	}

	/**
	 * Creates a new object to compute critical pairs.
	 * 
	 * @param layer
	 *            The layer function.
	 * @deprecated
	 */
	public LayeredSimpleDependencyPair(LayerFunction layer) {
		super();
		// this.layer = layer;
		// if(layer != null && layer.isValid()) {
		// if((layer instanceof ExtendedLayerFunction)
		// || (layer instanceof WeakExtendedLayerFunction))
		// enableNACs(false);
		// }
	}

	/**
	 * Sets a layer function to layer a certain object.
	 * 
	 * @param layer
	 *            A specific layer function.
	 * @deprecated
	 */
	public void setLayer(LayerFunction layer) {
		this.layer = layer;
	}

	/**
	 * Returns a layer function from a certain object.
	 * 
	 * @return A specific layer function.
	 * @deprecated
	 */
	public LayerFunction getLayer() {
		return this.layer;
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
		// System.out.println("LayeredSimpleDependencyPair.isCritical ");
		if (this.ignoreIdenticalRules && r1 == r2) {
			if (kind == EXCLUDE)
				return null;
			else if (kind == CONFLICTFREE) {
				return null;
			}
			else
				throw new InvalidAlgorithmException("No such algorithm", kind);
		}

		if (kind == EXCLUDE || kind == CONFLICTFREE) {
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> result = null;
			boolean sameLayer = r1.getLayer() == r2.getLayer();

			if (kind == EXCLUDE) {
				if (sameLayer)
					result = super.isCritical(kind, r1, r2);
			} else if (kind == CONFLICTFREE) {
				if (sameLayer) 
					result = super.isCritical(kind, r1, r2);
			}
			return result;
		} 
		throw new InvalidAlgorithmException("No such Algorithm", kind);
	}

	protected boolean checkGraphConsistency(Graph g, int l) {
		List<Formula> constraints = this.grammar.getConstraintsForLayer(-1);
		if (this.grammar.checkGraphConsistency(g, constraints)) {
			constraints = this.grammar.getConstraintsForLayer(l);
			if (this.grammar.checkGraphConsistency(g, constraints))
				return true;
			
			return false;
		} 
		return false;
	}
}