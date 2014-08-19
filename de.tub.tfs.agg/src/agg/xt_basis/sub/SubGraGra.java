package agg.xt_basis.sub;

import java.util.Enumeration;

import agg.xt_basis.GraGra;
import agg.xt_basis.Match;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;

/**
 * Note: A lot of problems remain unsolved concerning the consistency
 * maintenance of a subgragra:
 * <ul>
 * <li> Since every call to a "create()" method in the subgragra creates a super
 * item in the supergragra as well as a sub item in the subgragra, "destroy()"
 * however only deletes the sub item, an application operating on a subgragra
 * may accumulate garbage super items in the super gragra if it doesn't care.
 * This is especially true for matches, which are frequently created and
 * destroyed. Should we therefore really destroy super items together with their
 * sub items? <b>For the moment, we actually decided to do so.</b>
 * <li> At the moment, sub items aren't removed from a subgragra when their
 * super item in the supergragra is destroyed.
 * 
 * @deprecated not more supported
 */
public class SubGraGra extends GraGra {
	private GraGra itsSuperGraGra;

	protected SubGraGra(GraGra supergragra) {
//		super(supergragra.getGraph().createSubGraph());
		this.itsSuperGraGra = supergragra;
	}

	public final GraGra getSuperGraGra() {
		return this.itsSuperGraGra;
	}

	/*
	 * Die Typen werden ueber itsSuperGraGra manipuliert.
	 */
	public Type createType() {
		return this.itsSuperGraGra.createType();
	}

	public Enumeration<Type> getTypes() {
		return this.itsSuperGraGra.getTypes();
	}

	public void destroyType(Type type) throws TypeException {
		this.itsSuperGraGra.destroyType(type);
	}

	public final Match createMatch(Rule rule) {
		// System.out.println(">>> SubGraGra.createMatch(Rule) ");
		Match aNewMatch = this.itsSuperGraGra.createMatch(((SubRule) rule)
				.getSuperRule());
		SubMatch aNewSubMatch = new SubMatch(aNewMatch, (SubRule) rule,
				(SubGraph) getGraph());
		addMatch(aNewSubMatch);
		return aNewSubMatch;
	}

	public final SubMatch createSubMatch(Rule subRule, Match superMatch) {
		// System.out.println(">>> SubGraGra.createMatch(Match superMatch) ");
		SubMatch aNewSubMatch = new SubMatch(superMatch, (SubRule) subRule,
				(SubGraph) getGraph());
		addMatch(aNewSubMatch);
		// System.out.println("--> aNewSubMatch: "+aNewSubMatch);
		return aNewSubMatch;
	}

	public final void destroyMatch(Match match) {
		removeMatch(match);
		match.dispose();
	}

	public final Rule createRule() {
//		Rule aNewRule = this.itsSuperGraGra.createRule();
//		SubRule aNewSubRule = new SubRule(aNewRule, aNewRule.getLeft()
//				.createSubGraph(), aNewRule.getRight().createSubGraph());
//		addRule(aNewSubRule);
//		return aNewSubRule;
		return null;
	}

	public final void destroyRule(Rule rule) {
		removeRule(rule);
		rule.dispose();
	}

	/**
	 * <b>Pre:</b> <code>sm</code> is submatch of a match of
	 * <code>getSuperGraGra()</code>.
	 */
	public final void addMatch(SubMatch sm) {
		this.itsMatches.add(sm);
	}

	public final boolean removeMatch(Match sm) {
		return this.itsMatches.remove(sm);
	}

	/**
	 * <b>Pre:</b> <code>sr</code> is subrule of a rule of
	 * <code>getSuperGraGra()</code>.
	 */
	public final void addRule(SubRule sr) {
		this.itsRules.add(sr);
	}

	public final boolean removeRule(Rule sr) {
		return this.itsRules.remove(sr);
	}

}// ####################################################################
