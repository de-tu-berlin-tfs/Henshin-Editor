package agg.xt_basis.sub;

import agg.xt_basis.BadMappingException;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

/**
 * Note: A lot of NAC problems remain unsolved with respect to subrules, even on
 * the ALR level:
 * <ul>
 * <li>- Fact: NAC created by a subrule has to be added to the superrule as
 * well (ok).
 * <li><code>-></code> superrule NACs can't be added ("addNAC()") to a
 * subrule because the left side of the subrule is only a subgraph of the
 * superrule.
 * <li><code>-></code> what happens if a NAC created by a subrule is edited
 * via the superrule? The additional parts of the superrule's left side should
 * never be involved! <code>=></code> read only mode?
 * <li><code>-></code> what happens if a NAC is removed from a subrule -
 * should it also be removed from the superrule?
 * <li><code>-></code> what happens if a NAC created by a subrule is removed
 * from the super- rule? It will have to be removed from the subrule as well!
 * </ul>
 * 
 * @deprecated not more supported
 */
public class SubRule extends Rule {
	private Rule itsSuperRule;

	private OrdinarySubMorphism itsSubRuleMorph;

	protected SubRule(Rule superrule) {
		this(superrule, null, null);
	}

	protected SubRule(Rule superrule, SubGraph left, SubGraph right) {
		super(left, right);

		// System.out.println("SubRule this : "+ this);
		// System.out.println("SubRule Left : "+ left.getSuperGraph()+"
		// "+superrule.getLeft());
		// System.out.println("SubRule Right : "+ right.getSuperGraph()+"
		// "+superrule.getRight());

//		itsSubRuleMorph = superrule.createSubMorphism(left, right);
//		itsSuperRule = superrule;

		// System.out.println("SubRule end");
	}

	public void dispose() {
		this.itsSubRuleMorph = null;
		this.itsSuperRule = null;
	}

	public final OrdinarySubMorphism getSubMorphism() {
		return this.itsSubRuleMorph;
	}

	public final Rule getSuperRule() {
		return this.itsSuperRule;
	}

	public OrdinaryMorphism createNAC() {
		OrdinaryMorphism aNAC = super.createNAC();
		this.itsSuperRule.addNAC(aNAC);
		return aNAC;
	}

	public Match getMatch() {
		if (((SubMatch) super.getMatch()) != null)
			return super.getMatch();
		
		return null;
	}

	public final void addMapping(GraphObject o, GraphObject i)
			throws BadMappingException {
		this.itsSubRuleMorph.addMapping(o, i);
	}

	public final void removeMapping(GraphObject o) {
		this.itsSubRuleMorph.removeMapping(o);
	}

}

