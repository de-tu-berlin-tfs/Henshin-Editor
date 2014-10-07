/**
 * 
 */
package agg.xt_basis.agt;

import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

/**
 * @author olga
 *
 * This class stores some help morphisms which are created during computation
 * of amalgamated rule and match based on an interaction rule scheme.
 * <code>agg.xt_agt.Covering</code> class is the creator class of these help morphisms.
 */
public class AmalgamationRuleData {

	/**
	 * The rule of this data.
	 * It can be KernelRule or MultiRule. 
	 */
	public Rule rule;
	
	/**
	 * An isomorphic morphism of the left graph of the rule 
	 * into a copy of this graph.
	 */
	public OrdinaryMorphism isoCopyLeft;
	
	/**
	 * An isomorphic morphism of the right graph of the rule 
	 * into a copy of this graph.
	 */
	public OrdinaryMorphism isoCopyRight;
	
	/**
	 * A morphism of the left graph of the kernel rule of a rule scheme
	 * into the target graph of a morphism <code>isoCopyLeft</code>. 
	 */
	public OrdinaryMorphism LkernelLinst;
	
	/**
	 * A morphism of the right graph of the kernel rule of a rule scheme
	 * into the target graph of a morphism <code>isoCopyRight</code>. 
	 */
	public OrdinaryMorphism RkernelRinst;

	/**
	 * A morphism of the target graph of the <code>isoCopyLeft</code>
	 * into the target graph of the current match of a rule (kernel or multi).
	 * The target graph of the match is the host graph.
	 */
	public OrdinaryMorphism instMatch;
		
	/**
	 * An instance rule based on match of a rule.
	 */
	public Rule instRule;
		
	/**
	 * A morphism of the target graph of the <code>LkernelLinst</code>
	 * into a colimit graph that will be the left graph of the amalgamated rule.
	 */
	public OrdinaryMorphism leftRequestEdge;
	
	/**
	 * A morphism of the target graph of the <code>RkernelRinst</code>
	 * into a colimit graph that will be the right graph of the amalgamated rule. 
	 */
	public OrdinaryMorphism rightRequestEdge;
	
	/** 
	 * Creates a help data instance of the specified Rule.
	 * All morphisms are not initialized.
	 * The creator class of these morphisms is <code>agg.xt_agt.Covering</code>.
	 * 
	 * @param r Rule of help data
	 */
	public AmalgamationRuleData(final Rule r) {
		this.rule = r;
	}
	
	
}
