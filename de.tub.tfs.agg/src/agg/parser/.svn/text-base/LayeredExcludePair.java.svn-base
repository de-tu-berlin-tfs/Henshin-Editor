package agg.parser;

import java.util.List;
import java.util.Vector;

import agg.cons.Formula;
import agg.util.Pair;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Graph;

/**
 * These pairs extends the <CODE>ExcludePairs</CODE> with layers.
 * 
 * @author $Author: olga $
 * @version $Id: LayeredExcludePair.java,v 1.12 2010/09/20 14:30:10 olga Exp $
 */
public class LayeredExcludePair extends ExcludePair // implements
// LayeredCriticalPair
{

	@SuppressWarnings("deprecation")
	private LayerFunction layer; // not more needed

	/**
	 * Creates a new object to compute critical pairs.
	 * 
	 * @param layer
	 *            The layer function.
	 * @deprecated
	 */
	public LayeredExcludePair(LayerFunction layer) {
		super();
		// this.layer = layer;
		// if(layer != null && layer.isValid()) {
		// if((layer instanceof ExtendedLayerFunction)
		// || (layer instanceof WeakExtendedLayerFunction))
		// enableNACs(false);
		// }
	}

	/**
	 * Creates a new object to compute critical pairs.
	 */
	public LayeredExcludePair() {
		super();
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
	 * computes if there is a critical pair of a special kind. Remenber: null is
	 * returned if the pair is not critical, otherwiser an object which can
	 * explain in which way this pair is critical. One possible object can be a
	 * <code>Vector</code> of overlaping graphs. If a kind kind is requested
	 * which cannot be computed a <code>InvalidAlgorithmException</code> is
	 * thrown.
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
		// System.out.println("LayeredExcludePair.isCritical kind: "+kind+"
		// "+r1.getName()+" "+r2.getName());
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
				if (sameLayer) result = 
					super.isCritical(kind, r1, r2);

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
/*
 * $Log: LayeredExcludePair.java,v $
 * Revision 1.12  2010/09/20 14:30:10  olga
 * tuning
 *
 * Revision 1.11  2010/03/08 15:46:42  olga
 * code optimizing
 *
 * Revision 1.10  2008/04/07 09:36:51  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.9  2007/11/05 09:18:22  olga
 * code tuning
 *
 * Revision 1.8  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.6  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2007/06/13 08:32:58 olga
 * Update: V161
 * 
 * Revision 1.4 2007/01/11 10:21:16 olga Optimized Version 1.5.1beta , free for
 * tests
 * 
 * Revision 1.3 2006/12/13 13:33:00 enrico reimplemented code
 * 
 * Revision 1.2 2006/03/01 09:55:46 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2004/09/20 12:52:06 olga Fehler bei loaden von CPs korregiert.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.4 2001/08/16 14:14:08 olga LayerFunction erweitert:
 * ExtendedLayerFunction erbt LayerFunction (checkLayer ueberschrieben)
 * WeakLayerFunction erbt LayerFunction ( checkLayer ueberschrieben)
 * WeakExtendedLayerFunction erbt WeakLayerFunction ( checkLayer ueberschrieben)
 * 
 * Revision 1.3 2001/08/02 15:22:16 olga Error-Meldungen eingebaut in
 * LayerFunction und die Anzeige dieser Meldungen in GUI.
 * 
 * Revision 1.2 2001/03/08 10:44:53 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:55 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 */
