/**
 * 
 */
package agg.xt_basis;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.parser.CriticalPairOption;
import agg.parser.ExcludePairContainer;
import agg.parser.ParserFactory;
import agg.util.Pair;
import agg.xt_basis.csp.CompletionPropertyBits;

/**
 * Constructs a parallel rule based on some rules (as disjoint union).
 * 
 * 
 * @author olga
 * 
 */
public class ParallelRule extends Rule {

	// private Rule r1, r2;
	List<Rule> sources;
	Vector<OrdinaryMorphism> embeddingLeft;
	Vector<OrdinaryMorphism> embeddingRight;
	private final List<OrdinaryMorphism> failedApplConds = new Vector<OrdinaryMorphism>();

	
	public ParallelRule(final TypeSet types, final Rule rule1, final Rule rule2) {
		super(types);

		if (rule1 != null && rule2 != null) {
			sources = new Vector<Rule>();
			embeddingLeft = new Vector<OrdinaryMorphism>();
			embeddingRight = new Vector<OrdinaryMorphism>();

			this.sources.add(rule1);
			this.sources.add(rule2);

			makeParallelRule();
		}
	}

	 public ParallelRule(final TypeSet types, final List<Rule> rules) {
		 super(types);
		
		 if (rules != null && !rules.isEmpty()) {
			 sources = new Vector<Rule>();
			 embeddingLeft = new Vector<OrdinaryMorphism>();
			 embeddingRight = new Vector<OrdinaryMorphism>();
			
			 this.sources.addAll(rules);
			 makeParallelRule();
		 }
	 }

	public boolean isValid() {
		return (sources == null || sources.isEmpty()) ? false : true;
	}

	public List<OrdinaryMorphism> getLeftEmbedding() {
		return this.embeddingLeft;
	}

	public List<OrdinaryMorphism> getRightEmbedding() {
		return this.embeddingRight;
	}

	public OrdinaryMorphism getLeftEmbeddingAtIndex(int indx) {
		return (this.embeddingLeft != null) ? this.embeddingLeft.get(indx)
				: null;
	}

	public OrdinaryMorphism getRightEmbeddingAtIndex(int indx) {
		return (this.embeddingRight != null) ? this.embeddingRight.get(indx)
				: null;
	}

	/**
	 * The specified GraphObject is an object of its LHS graph.
	 * 
	 * @return the embedding morphism where the specified object is an object of
	 *         the target graph
	 */
	public OrdinaryMorphism getLeftEmbeddingOfObject(final GraphObject go) {
		for (int i = 0; i < this.embeddingLeft.size(); i++) {
			if (this.embeddingLeft.get(i).getInverseImage(go).hasMoreElements())
				return this.embeddingLeft.get(i);
		}
		return null;
	}

	/**
	 * The specified GraphObject is an object of its LHS graph.
	 * 
	 * @return the embedding morphism where the specified object is an object of
	 *         the target graph
	 */
	public OrdinaryMorphism getRightEmbeddingOfObject(final GraphObject go) {
		for (int i = 0; i < this.embeddingRight.size(); i++) {
			if (this.embeddingRight.get(i).getInverseImage(go)
					.hasMoreElements())
				return this.embeddingRight.get(i);
		}
		return null;
	}

	/**
	 * Tries to make a valid non-injective match for this parallel rule. In case
	 * of an injective search strategy the objects of the different source rules
	 * may overlap.
	 * 
	 * @param graph
	 * @param strategy
	 * 
	 * @return true when a match was successful, otherwise false
	 */
	public boolean makeMatch(final Graph graph,
			final MorphCompletionStrategy strategy) {

		Match m = this.getMatch();
		if (m == null) {
			m = BaseFactory.theBaseFactory.createMatch(this, graph);
			m.setCompletionStrategy(strategy, true);
		}

		boolean inj = m.getCompletionStrategy().getProperties()
				.get(CompletionPropertyBits.INJECTIVE);
		if (inj)
			m.getCompletionStrategy().removeProperty(GraTraOptions.INJECTIVE);

		while (m.nextCompletion()) {
			// check overlapped objects
			boolean ok = true;

			List<GraphObject> dom = m.getDomainObjects();
			List<GraphObject> dom1 = m.getDomainObjects();
			for (int i = 0; i < dom.size() && ok; i++) {
				GraphObject go = dom.get(i);
				GraphObject img = m.getImage(go);
				GraphObject src = getLeftRuleObjOf(go);

				for (int j = i + 1; j < dom1.size() && ok; j++) {
					GraphObject go1 = dom1.get(j);
					if (img == m.getImage(go1)) {
						GraphObject src1 = getLeftRuleObjOf(go1);

						if (src != null && src1 != null) {
							if (src.getContext() == src1.getContext()) {
								ok = false;
							}
						}
					}
				}
			}

			if (ok && m.isValid()) {
				return true;
			}
		}

		return false;
	}

	private GraphObject getLeftRuleObjOf(GraphObject go) {
		for (int l = 0; l < this.embeddingLeft.size(); l++) {
			OrdinaryMorphism emb = this.embeddingLeft.get(l);
			Enumeration<GraphObject> iter = emb.getInverseImage(go);
			if (iter.hasMoreElements()) {
				return iter.nextElement();
			}
		}
		return null;
	}

	/**
	 * Tries to find a valid match for each rule of the specified rules at the
	 * specified graph using the specified morphism completion strategy. Returns
	 * a list of maps with match mappings of rule.LHS objects to graph objects.
	 * Returns null in case of list.size != rules.size.
	 * 
	 * @param rules
	 * @param graph
	 * @param strategy
	 * @return a list of maps or null
	 */
	public static List<HashMap<GraphObject, GraphObject>> makeMatchOfRule(
			final List<Rule> rules, final Graph graph,
			final MorphCompletionStrategy strategy) {

		final List<HashMap<GraphObject, GraphObject>> result = new Vector<HashMap<GraphObject, GraphObject>>(
				rules.size());

		for (int i = 0; i < rules.size(); i++) {
			Rule r = rules.get(i);
			Match m = BaseFactory.theBaseFactory.createMatch(r, graph);
			m.setCompletionStrategy(strategy, true);
			boolean found = false;

			while (m.nextCompletion()) {
				if (m.isValid()) {
					found = true;
					final HashMap<GraphObject, GraphObject> map = new HashMap<GraphObject, GraphObject>();
					Enumeration<GraphObject> dom = m.getDomain();
					while (dom.hasMoreElements()) {
						GraphObject go = dom.nextElement();
						map.put(go, m.getImage(go));
					}
					result.add(map);
					break;
				}
			}
			if (!found)
				return null;
		}
		//
		if (result.size() != rules.size())
			return null;

		return result;
	}

	@SuppressWarnings("deprecation")
	public static boolean parallelIndependent(final GraGra gragra,
			final List<Rule> rules,
			// final Graph graph,
			final MorphCompletionStrategy strategy,
			final CriticalPairOption cpOption) {

		ExcludePairContainer pc = new ExcludePairContainer(gragra);

		if (cpOption == null) {
			pc.enableComplete(true);
			pc.enableNACs(true);
			pc.enablePACs(true);
			pc.enableReduce(false); // essential CPs
			pc.enableConsistent(true);
			pc.enableStrongAttrCheck(true);
			pc.enableIgnoreIdenticalRules(true);
			pc.enableReduceSameMatch(true);
			pc.enableDirectlyStrictConfluent(false);
			pc.enableDirectlyStrictConfluentUpToIso(false);
			pc.enableNamedObjectOnly(false);
			pc.enableMaxBoundOfCriticCause(0);
		} else {
			pc.enableComplete(cpOption.completeEnabled());
			pc.enableNACs(cpOption.nacsEnabled());
			pc.enablePACs(cpOption.pacsEnabled());
			pc.enableReduce(cpOption.reduceEnabled()); // essential CPs
			pc.enableConsistent(cpOption.consistentEnabled());
			pc.enableStrongAttrCheck(cpOption.strongAttrCheckEnabled());
			pc.enableIgnoreIdenticalRules(cpOption
					.ignoreIdenticalRulesEnabled());
			pc.enableReduceSameMatch(cpOption.reduceSameMatchEnabled());
			pc.enableDirectlyStrictConfluent(cpOption
					.directlyStrictConflEnabled());
			pc.enableDirectlyStrictConfluentUpToIso(cpOption
					.directlyStrictConflUpToIsoEnabled());
			pc.enableNamedObjectOnly(cpOption.namedObjectEnabled());
			pc.enableMaxBoundOfCriticCause(cpOption.getMaxBoundOfCriticCause());
		}

		pc.setRules(rules);
		pc.setMorphCompletionStrategy(strategy);

		Thread cpThread = ParserFactory.generateCriticalPairs(pc);
		while (cpThread.isAlive()) {
		}

		final Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> conflicts = pc
				.getConflictContainer();

		for (int i = 0; i < rules.size(); i++) {
			Rule r = rules.get(i);
			final Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> ruleConflicts = conflicts
					.get(r);
			final Iterator<Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> iter = ruleConflicts
					.values().iterator();

			while (iter.hasNext()) {
				if (iter.next().first.booleanValue()) {
					// a conflict exists
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Constructs this parallel rule based on two rules (as disjoint union).
	 */
	private void makeParallelRule() {
		int tgCheckLevel = this.getTypeSet().getLevelOfTypeGraphCheck();
		if (tgCheckLevel > TypeSet.ENABLED_MAX)
			this.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED_MAX);

		// rename variables where needed
		Hashtable<String, String> varNames = new Hashtable<String, String>();
		final VarTuple vars = (VarTuple) this.sources.get(0).getAttrContext().getVariables();
		for (int j = 0; j < vars.getNumberOfEntries(); j++) {
			VarMember var = vars.getVarMemberAt(j);
			varNames.put(var.getName(), var.getName());
		}
		for (int i=1; i<this.sources.size(); i++) {
			renameVarsWhereNeeded(i, this.sources.get(i), varNames);
		}
		boolean ok = true;
		for (int i=0; i<this.sources.size() && ok; i++) {
			// extend LHS and RHS, propagate rule morphisms
			ok = extendRuleByDisjointUnion(this.sources.get(i));
		}
		if (ok) {
//			if (handleRuleApplConditions(this.sources.get(0), this.sources.get(1))) {
			if (handleRuleApplConditions()) {	
				handleAttrConditions();
			} else {
				this.notApplicable = true;
			}
		} else {
			this.notApplicable = true;
		}
		// ((VarTuple)this.getAttrContext().getVariables()).showVariables();

		replaceRenamedVars(varNames);
		
		// test
		for (int i=0; i<this.embeddingLeft.size(); i++) {
			this.adjustUnsetAttrsAboveMorph(this.embeddingLeft.get(i));
		}
		
		this.removeUnusedVariableOfAttrContext();
		this.getTypeSet().setLevelOfTypeGraph(tgCheckLevel);
	}

	private void renameVarsWhereNeeded(int i, final Rule r,
			final Hashtable<String, String> varNames) {
		final VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		if (varNames.isEmpty()) {
			for (int j = 0; j < vars.getNumberOfEntries(); j++) {
				VarMember var = vars.getVarMemberAt(j);
				varNames.put(var.getName(), var.getName());
			}
		}
		final List<OrdinaryMorphism> nacs = r.getNACsList();
		final List<OrdinaryMorphism> pacs = r.getPACsList();
		final AttrContext ac = r.getAttrContext();
		final VarTuple varsN = (VarTuple) ac.getVariables();
		for (int j = 0; j < varsN.getNumberOfEntries(); j++) {
			VarMember var = varsN.getVarMemberAt(j);
			// check if already exists
			if (varNames.get(var.getName()) != null) {
				String fromName = var.getName();
				// build a new name
				String toName = fromName.concat(String.valueOf(i));
				// so long as already existent
				while (varNames.get(toName) != null) {
					toName = toName.concat(String.valueOf(i));
				}
				// store new name to old name
				varNames.put(toName, fromName);
				// rename variable
				var.getDeclaration().setName(toName);

				// rename variables in conditions
				BaseFactory.theBaseFactory.renameVariableOfCondition(ac,
						(CondTuple) ac.getConditions(), fromName, toName);

				// rename variables in left/right graphs of morphs
				BaseFactory.theBaseFactory.setAttributeVariable(r.getSource(),
						fromName, toName, ac, varsN);
				BaseFactory.theBaseFactory.setAttributeVariable(r.getTarget(),
						fromName, toName, ac, varsN);

				// rename variables in NACs
				for (int n = 0; n < nacs.size(); n++) {
					OrdinaryMorphism nac = nacs.get(n);
					BaseFactory.theBaseFactory.setAttributeVariable(
							nac.getTarget(), fromName, toName, ac, varsN);
				}

				// rename variables in PACs
				for (int n = 0; n < pacs.size(); n++) {
					OrdinaryMorphism pac = pacs.get(n);
					BaseFactory.theBaseFactory.setAttributeVariable(
							pac.getTarget(), fromName, toName, ac, varsN);
				}
			}
		}
	}

	private void replaceRenamedVars(final Hashtable<String, String> varNames) {
		for (int i = 1; i < this.sources.size(); i++) {
			final Rule r = this.sources.get(i);
			final AttrContext ac = r.getAttrContext();
			final VarTuple varsN = (VarTuple) ac.getVariables();
			final CondTuple condsN = (CondTuple) ac.getConditions();
			final List<OrdinaryMorphism> nacs = r.getNACsList();
			final List<OrdinaryMorphism> pacs = r.getPACsList();

			for (int j = 0; j < varsN.getNumberOfEntries(); j++) {
				VarMember var = varsN.getVarMemberAt(j);
				// check if already stored
				if (varNames.get(var.getName()) != null
						&& !var.getName().equals(varNames.get(var.getName()))) {
					String fromName = var.getName();
					// set old name
					String toName = varNames.get(var.getName());
					// rename variable
					var.getDeclaration().setName(toName);

					// rename variables in conditions
					BaseFactory.theBaseFactory.renameVariableOfCondition(ac,
							condsN, fromName, toName);

					// rename variables in left/right graphs of morphs
					BaseFactory.theBaseFactory.setAttributeVariable(
							r.getSource(), fromName, toName, ac, varsN);
					BaseFactory.theBaseFactory.setAttributeVariable(
							r.getTarget(), fromName, toName, ac, varsN);

					// rename variables in NACs
					for (int n = 0; n < nacs.size(); n++) {
						OrdinaryMorphism nac = nacs.get(n);
						BaseFactory.theBaseFactory.setAttributeVariable(
								nac.getTarget(), fromName, toName, ac, varsN);
					}

					// rename variables in PACs
					for (int n = 0; n < pacs.size(); n++) {
						OrdinaryMorphism pac = pacs.get(n);
						BaseFactory.theBaseFactory.setAttributeVariable(
								pac.getTarget(), fromName, toName, ac, varsN);
					}

				}
			}
		}
	}

	/**
	 * Extends (by disjoint union) this parallel rule by the given Rule rule.
	 */
	private boolean extendRuleByDisjointUnion(final Rule rule) {
		boolean ok = true;
		OrdinaryMorphism leftRuleToLeft = null;
		try {
			leftRuleToLeft = BaseFactory.theFactory().extendGraphByGraph(
					this.getLeft(), rule.getLeft());
		} catch (Exception e) {
			ok = false;
			System.out.println("ParallelRule.extendRuleByDisjointUnion: (LHS) "+e.getLocalizedMessage());
		}

		if (leftRuleToLeft != null) {
			OrdinaryMorphism rightRuleToRight = null;
			try {
				rightRuleToRight = BaseFactory.theFactory().extendGraphByGraph(
						this.getRight(), rule.getRight());
			} catch (Exception e) {
				ok = false;
				System.out.println("ParallelRule.extendRuleByDisjointUnion: (RHS) "+e.getLocalizedMessage());
			}

			if (rightRuleToRight != null) {
				// add morphism mapping over rule
				ok = this.completeDiagram(leftRuleToLeft, rule,
						rightRuleToRight);

				if (ok) {
					this.embeddingLeft.add(leftRuleToLeft);
					this.embeddingRight.add(rightRuleToRight);
				}
			}
		}

		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(rule);
		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(this);

		return ok;
	}

	private OrdinaryMorphism replaceRightByLeftInsideOfGraph(
			final Rule rule, 
			final OrdinaryMorphism embRight, 
			final Graph graph,
			final OrdinaryMorphism r2rl) {
		
		Vector<GraphObject> del = new Vector<GraphObject>();
		Graph g1 = r2rl.getTarget();
		Vector<GraphObject> codom = embRight.getCodomainObjects();
		for (int i=0; i<codom.size(); i++) {
			GraphObject go = r2rl.getImage(codom.get(i));
			if (go.isArc())
				del.add(0, go);
			else
				del.add(go);
		}
		for (int i=0; i<del.size(); i++) {
			GraphObject go = del.get(i);
			if (go.isArc()) {
				try {
					g1.destroyArc((Arc)go, false, true);
				} 
				catch (TypeException ex) {}
			}
			else {
				try {
					g1.destroyNode((Node)go, false, true);
				} 
				catch (TypeException ex) {}
			}
		}
		try {
			OrdinaryMorphism l2rl = BaseFactory.theFactory().extendGraphByGraph(g1, rule.getLeft());
			return l2rl;
		} catch (Exception e) {}
		return null;
	}
	
	
	@SuppressWarnings("unused")
	private boolean handleRuleApplConditionsOLD(final Rule r1, final Rule r2) {		
		boolean ok = true;
		try {
			OrdinaryMorphism rhs1ToE = r1.getRight().isomorphicCopy();
			OrdinaryMorphism lhs2ToE = BaseFactory.theFactory()
					.extendGraphByGraph(rhs1ToE.getTarget(), r2.getLeft());
			OrdinaryMorphism left2 = BaseFactory.theFactory().createMorphism(
					this.itsOrig, lhs2ToE.getTarget());
			if (left2.completeDiagram(this.embeddingLeft.get(0), r1, rhs1ToE)
					&& left2.completeDiagram2(this.embeddingLeft.get(1), lhs2ToE)) {
				// morphism lhs2ToE2 = L2 -> (R1+L2)
				// morphism left2 = (L1+L2) -> (R1+L2)
			}
			
			OrdinaryMorphism rhs2ToE = r2.getRight().isomorphicCopy();
			OrdinaryMorphism lhs1ToE = BaseFactory.theFactory()
					.extendGraphByGraph(rhs2ToE.getTarget(), r1.getLeft());
			OrdinaryMorphism left1 = BaseFactory.theFactory().createMorphism(
					this.itsOrig, lhs1ToE.getTarget());
			if (left1.completeDiagram(this.embeddingLeft.get(1), r2, rhs2ToE)
					&& left1.completeDiagram2(this.embeddingLeft.get(0), lhs1ToE)
					) {
				// morphism lhs1ToE1 = L1 -> (R2+L1)
				// morphism left1 = (L1+L2) -> (R2+L1)
			}
			
			Vector<Evaluable> flist = new Vector<Evaluable>();
			
			// shift appl. conditions over embedding morphism
			ok = shiftCondsOverEmbMorphOLD(
							0, r1, this.embeddingLeft.get(0), flist);
	
			// shift appl. conditions over e-morphism and left
			if (ok)
				ok = shiftCondsOverMorphAndLeftOLD(0, r1, lhs1ToE, left1, this.embeddingLeft.get(0), flist);
			// shift appl. conditions over embedding morphism
			if (ok)
				ok = shiftCondsOverEmbMorphOLD(
						1, r2, this.embeddingLeft.get(1), flist);
	
			// shift appl. conditions over e-morphism and left
			if (ok)
				ok = shiftCondsOverMorphAndLeftOLD(1, r2, lhs2ToE, left2, this.embeddingLeft.get(1), flist);

			if (ok) {
				removeIsomorphicMorph(this.getNACsList());
				removeIsomorphicMorph(this.getPACsList());
			
				ok = shiftGACs(0, r1, this.embeddingLeft.get(0), lhs1ToE, left1, flist)
					&& shiftGACs(1, r2, this.embeddingLeft.get(1), lhs2ToE, left2, flist);
				
				if (ok && !this.getNestedACsList().isEmpty() && !flist.isEmpty()) {
					Formula f = new Formula(flist, Formula.AND);
					this.setFormula(f);
				}
			}
			
			this.getSource().unsetTransientAttrValues();
			this.getTarget().unsetTransientAttrValues();
			setInputParameterIfNeeded(this);

		}  catch (Exception e) {
			ok = false;
		}
		return ok;
	}

	@SuppressWarnings("unused")
	private boolean handleRuleApplConditionsOLD() {
		Vector<Evaluable> flist = new Vector<Evaluable>();
		boolean ok = true;
		for (int i=0; i<this.sources.size() && ok; i++) {
			Rule r = this.sources.get(i);
			OrdinaryMorphism rhsToE = this.getRight().isoCopy();
			OrdinaryMorphism lhsToE = this.replaceRightByLeftInsideOfGraph(
										r, this.embeddingRight.get(i), rhsToE.getTarget(), rhsToE);
			if (lhsToE != null) {
				OrdinaryMorphism left = BaseFactory.theFactory().createMorphism(
											this.itsOrig, lhsToE.getTarget());
				for (int k=0; k<this.sources.size(); k++) {
					if (ok && k!=i)
						ok = left.completeDiagram(this.embeddingLeft.get(k), 
												this.sources.get(k), 
												this.embeddingRight.get(k).compose(rhsToE));	
				}
				ok = left.completeDiagram2(this.embeddingLeft.get(i), lhsToE);
				
				// shift appl. conditions (NACs, PACs) over embedding morphism
				if (ok)
					ok = shiftCondsOverEmbMorphOLD(i, r, this.embeddingLeft.get(i), flist);
				// shift appl. conditions (NACs, PACs) over e-morphism and left
				if (ok) 
					ok = shiftCondsOverMorphAndLeftOLD(i, r, lhsToE, left, this.embeddingLeft.get(i), flist);
				if (ok) {
					removeIsomorphicMorph(this.getNACsList());
					removeIsomorphicMorph(this.getPACsList());
				}
				// shift general appl. conditions over embedding morphism
				ok = shiftGACs(i, r, this.embeddingLeft.get(i), lhsToE, left, flist);
					
				this.getSource().unsetTransientAttrValues();
				this.getTarget().unsetTransientAttrValues();
				setInputParameterIfNeeded(this);
			}
			else {
				rhsToE.dispose();
				return false;
			}
		}
				
		if (ok && !this.getNestedACsList().isEmpty() && !flist.isEmpty()) {
			Formula f = new Formula(flist, Formula.AND);
			this.setFormula(f);
		}		
			
		this.getSource().unsetTransientAttrValues();
		this.getTarget().unsetTransientAttrValues();
		setInputParameterIfNeeded(this);

		return ok;
	}
	
	private boolean handleRuleApplConditions() {
		Vector<Evaluable> flist = new Vector<Evaluable>();
		boolean ok = true;
		for (int i=0; i<this.sources.size() && ok; i++) {
			Rule r = this.sources.get(i);
			OrdinaryMorphism rhsToE = this.getRight().isoCopy();
			OrdinaryMorphism lhsToE = this.replaceRightByLeftInsideOfGraph(
										r, this.embeddingRight.get(i), rhsToE.getTarget(), rhsToE);
			if (lhsToE != null) {
				OrdinaryMorphism left = BaseFactory.theFactory().createMorphism(
											this.itsOrig, lhsToE.getTarget());
				for (int k=0; k<this.sources.size(); k++) {
					if (ok && k!=i)
						ok = left.completeDiagram(this.embeddingLeft.get(k), 
												this.sources.get(k), 
												this.embeddingRight.get(k).compose(rhsToE));	
				}
				ok = left.completeDiagram2(this.embeddingLeft.get(i), lhsToE);
				
				// shift over embedding morphism
				// shift over e-morphism and left 
				if (ok)
					ok = shiftPACs(i, r, this.embeddingLeft.get(i), lhsToE, left, flist);
					if (ok) 
						shiftNACs(i, r, this.embeddingLeft.get(i), lhsToE, left);
				if (ok) {
					removeIsomorphicMorph(this.getNACsList());
					removeIsomorphicMorph(this.getPACsList());
				}
				// shift general appl. conditions over embedding morphism
				ok = shiftGACs(i, r, this.embeddingLeft.get(i), lhsToE, left, flist);
					
				this.getSource().unsetTransientAttrValues();
				this.getTarget().unsetTransientAttrValues();
				setInputParameterIfNeeded(this);
			}
			else {
				rhsToE.dispose();
				return false;
			}
		}
				
		if (ok && !this.getNestedACsList().isEmpty() && !flist.isEmpty()) {
			Formula f = new Formula(flist, Formula.AND);
			this.setFormula(f);
		}
						
		this.getSource().unsetTransientAttrValues();
		this.getTarget().unsetTransientAttrValues();
		setInputParameterIfNeeded(this);

		return ok;
	}
		
	private boolean shiftCondsOverMorphAndLeftOLD(
			int indx,
			final Rule r,
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism left, 
			final OrdinaryMorphism embMorph,
			final Vector<Evaluable> flist) {

		boolean ok = true;
		// shift PACs
		List<Formula> fl = new Vector<Formula>();
		ok = this.shiftPACsOverMorphAndLeftOLD(indx, r, r.getPACs(), morph, left, embMorph, fl);				
		if (ok) {
			if (!fl.isEmpty()) {
				List<Evaluable> evals = new Vector<Evaluable>(fl.size());
				for (int k = 0; k < fl.size(); k++) {
					evals.add(fl.get(k));
				}
				Formula f = new Formula(evals, Formula.AND);
				if (f.isValid())
					flist.add(f);
			}
					
			// shift NACs
			this.shiftNACsOverMorphAndLeft(indx, r, r.getNACs(), morph, left, embMorph);					
		}
		return ok;
	}

	private boolean shiftPACsOverMorphAndLeftOLD(
			int indx,
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph,
			final List<Formula> fl) {

		boolean ok = true;
		while (conds.hasMoreElements() && ok) {
			OrdinaryMorphism cond = conds.nextElement();
			ok = shiftPACOverMorphAndLeftOLD(indx, rule, cond, morph,  left, embMorph, fl);
		}
		return ok;
	}
	
	private boolean shiftPACOverMorphAndLeftOLD(
			int indx,
			final Rule rule,
			OrdinaryMorphism cond,
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph,
			final List<Formula> fl) {

		this.notApplicable = false;
		List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();
			if (cond.getSize() > 0) {
				// here: LHS -> cond mapping is not empty
				List<Pair<OrdinaryMorphism,OrdinaryMorphism>> 
				list = shiftCondOverMorph(rule, cond, morph);
				if (list != null && list.size() > 0) {
					List<OrdinaryMorphism> list2 = new Vector<OrdinaryMorphism>();
					for (int i = 0; i < list.size(); i++) {
						Pair<OrdinaryMorphism,OrdinaryMorphism> p = list.get(i);
						OrdinaryMorphism c = p.second;
						// shift c left
						OrdinaryMorphism 
						lc = BaseFactory.theBaseFactory.shiftApplCondLeft(c, left);
						if (!this.isFalseCond(cond, lc, embMorph)) {
							if (!lc.isRightTotal() 
									|| !lc.doesIgnoreAttrs()) {
								lc.setName(c.getName());
								lc.setEnabled(cond.isEnabled());
								lc.getImage().setAttrContext(this.getLeft().getAttrContext());
								lc.setAttrContext(this.getLeft().getAttrContext());
								BaseFactory.theBaseFactory.declareVariable(
										lc.getTarget(), 
										(VarTuple) this.getAttrContext().getVariables());
								this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
								list2.add(lc);
							}
						}
					}
					if (list2.size() > 0) {
						if (list2.size() > 1) {
							removeIsomorphicMorph(list2);
							// make GACs and formula = GAC1 || GAC2 || ...
							Formula f = BaseFactory.theBaseFactory.replacePACsByGACs(list2);
							fl.add(f);
							// add to Nested ACs
							for (int l = 0; l < list2.size(); l++) {
								this.addNestedAC(list2.get(l));
							}
						} else 
							result.add(list2.get(0));
						list2.clear();
					} else {
						// shift failed, so this rule is not applicable 
						this.notApplicable = true;					
						return false;
					}
					this.disposeMorphs(list);
				}
				else {
					// shift failed, so this rule is not applicable 
					this.notApplicable = true;
					return false; 
				}
			} 

		if (!this.notApplicable) {
			for (int l = 0; l < result.size(); l++) {
				this.addPAC(result.get(l));
			}
		}
		result.clear();
		return !this.notApplicable;
	}

	private List<OrdinaryMorphism> shiftPACOverMorphAndLeft(
			int indx,
			final Rule rule,
			OrdinaryMorphism cond,
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph,
			final List<Formula> fl) {

		this.notApplicable = false;
		List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>();
			if (cond.getSize() > 0) {
				// here: LHS -> cond mapping is not empty
				List<Pair<OrdinaryMorphism,OrdinaryMorphism>> 
				shift = shiftCondOverMorph(rule, cond, morph);
				if (shift != null && shift.size() > 0) {
					for (int i = 0; i < shift.size(); i++) {
						Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
						OrdinaryMorphism c = p.second;
						// shift c left
						OrdinaryMorphism 
						lc = BaseFactory.theBaseFactory.shiftApplCondLeft(c, left);
						if (!this.isFalseCond(cond, lc, embMorph)) {
							if (!lc.isRightTotal() 
									|| !lc.doesIgnoreAttrs()) {
								lc.setName(c.getName());
								lc.setEnabled(cond.isEnabled());
								lc.getImage().setAttrContext(this.getLeft().getAttrContext());
								lc.setAttrContext(this.getLeft().getAttrContext());
								BaseFactory.theBaseFactory.declareVariable(
										lc.getTarget(), 
										(VarTuple) this.getAttrContext().getVariables());
								this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
								list.add(lc);
							}
						}
					}
				}
				else {
					// shift failed, so this rule is not applicable 
					this.notApplicable = true;
					return null; 
				}
			} 
		return list;
	}

	
	private boolean shiftNACsOverMorphAndLeft(
			int indx,
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph) {

		List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();
		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) {
				// here: LHS -> cond is not empty
				List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverMorph(rule, cond, morph);
				List<OrdinaryMorphism> list2 = new Vector<OrdinaryMorphism>(shift.size());
				for (int i = 0; i < shift.size(); i++) {
					Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
					OrdinaryMorphism c = p.second;
					// shift c left
					OrdinaryMorphism lc = BaseFactory.theBaseFactory.shiftApplCondLeft(c, left);
					if (!this.isFalseCond(cond, lc, embMorph)) {
						lc.setName(c.getName());
						lc.setEnabled(cond.isEnabled());
						lc.getTarget().unsetTransientAttrValues();
						this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
						list2.add(lc);
					}
				}			
				result.addAll(list2);
				this.disposeMorphs(shift);
				list2.clear();
			} 
		}
		if (!result.isEmpty()) {	
			removeIsomorphicMorph(result);
			for (int l = 0; l < result.size(); l++) {
				OrdinaryMorphism lc = result.get(l);
				if (!lc.isRightTotal() 
						|| !lc.doesIgnoreAttrs()) {
					lc.getImage().setAttrContext(
								this.getLeft().getAttrContext());
					lc.setAttrContext(this.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(
								lc.getTarget(), 
								(VarTuple) this.getAttrContext().getVariables());
					this.addNAC(lc);
				}
			}
			result.clear();
		}
		return true;
	}

	private List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shiftCondOverMorph(
			final Rule rule,
			final OrdinaryMorphism cond, 
			final OrdinaryMorphism morph) {

		// first check:
		// for all x from rule.lhs with cond.getImage(x) != null also
		// morph.getImage(x) != null

		final Enumeration<GraphObject> dom = cond.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject go = dom.nextElement();
			if (morph.getImage(go) == null) {
				this.failedApplConds.add(cond);
				return null;
			}
		}

		final OrdinaryMorphism condIsom = cond.getTarget().isomorphicCopy();
		if (condIsom == null) {
			this.failedApplConds.add(cond);
			return null;
		}

		final OrdinaryMorphism leftToCond = cond.compose(condIsom);

		final Vector<GraphObject> condDom = cond.getDomainObjects();
		final List<Object> requiredObjs = new Vector<Object>(condDom.size());
		final Hashtable<Object, Object> objmap = new Hashtable<Object, Object>(
				condDom.size());

		for (int j = 0; j < condDom.size(); j++) {
			GraphObject go = condDom.get(j);
			GraphObject go1 = leftToCond.getImage(go);
			GraphObject go2 = morph.getImage(go);
			if (go1 != null && go2 != null) {
				requiredObjs.add(go1);
				objmap.put(go1, go2);
			}
		}

		final Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> overlaps = BaseFactory.theBaseFactory
				.getOverlappingByPartialPredefinedIntersection(
						condIsom.getTarget(), morph.getTarget(), requiredObjs,
						objmap, true);

		final List<Pair<OrdinaryMorphism,OrdinaryMorphism>> list = new Vector<Pair<OrdinaryMorphism,OrdinaryMorphism>>();
		while (overlaps.hasMoreElements()) {
			Pair<OrdinaryMorphism, OrdinaryMorphism> p = overlaps.nextElement();
			if (!p.second.getTarget().isEmpty()) {
				OrdinaryMorphism c = p.second;
				filterObjsOfRightRuleCond(rule, cond, condIsom, p.first, c, morph);	
				c.setName(cond.getName().concat(String.valueOf(list.size())));
				c.setEnabled(cond.isEnabled());
				c.shifted = true;
				OrdinaryMorphism first = condIsom.compose(p.first);
				if (first.getSize() == condIsom.getSize())
					list.add(new Pair<OrdinaryMorphism, OrdinaryMorphism>(first, p.second));
				else
					list.add(p);
			}
		}
		if (list.isEmpty()) {
			this.failedApplConds.add(cond);
		}
		return list;
	}

	private boolean isFalseCond(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism condL,
			final OrdinaryMorphism leftEmbMorph) {

		Iterator<Node> iter = cond.getSource().getNodesSet().iterator();
		while (iter.hasNext()) {
			GraphObject go = iter.next();
			GraphObject go1 = leftEmbMorph.getImage(go);
			if ((cond.getImage(go) == null && condL.getImage(go1) != null)
					|| (cond.getImage(go) != null && condL.getImage(go1) == null)
					) {
				return true;
			}
		}
		Iterator<Arc> iter2 = cond.getSource().getArcsSet().iterator();
		while (iter2.hasNext()) {
			GraphObject go = iter2.next();
			GraphObject go1 = leftEmbMorph.getImage(go);
			if ((cond.getImage(go) == null && condL.getImage(go1) != null)
					|| (cond.getImage(go) != null && condL.getImage(go1) == null)
					) {
				return true;
			}
		}
		
		return false;
	}

	private boolean filterObjsOfRightRuleCond(final Rule r,
			final OrdinaryMorphism condL, final OrdinaryMorphism condIsom,
			final OrdinaryMorphism condTargetToCondCR,
			final OrdinaryMorphism condCR,
			final OrdinaryMorphism leftEmbeddingMorph) {

		boolean ok = true;
		// delete arc to be created or without a mapping
		// from its pre-image into the condL
		List<GraphObject> todelete = new Vector<GraphObject>();
		final Iterator<Arc> arcs = condCR.getTarget().getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc go_condCR = arcs.next();
			Arc goInv_condCR = null;
			if (condCR.getInverseImage(go_condCR).hasMoreElements()) {
				if (condTargetToCondCR.getInverseImage(go_condCR)
						.hasMoreElements())
					goInv_condCR = (Arc) condTargetToCondCR.getInverseImage(
							go_condCR).nextElement();
				if (goInv_condCR == null) {
					todelete.add(go_condCR);
					continue;
				}
				if (!condIsom.getInverseImage(goInv_condCR).hasMoreElements()) {
					todelete.add(go_condCR);
					continue;
				}
				Arc go_leftCR = (Arc) condCR.getInverseImage(go_condCR).nextElement();
				for (int j=0; j<this.sources.size(); j++) {					
					if (r == this.sources.get(j)) {
						for (int k=0; k<this.embeddingLeft.size(); k++) {
							if (k == j) {
								if (this.embeddingLeft.get(k).getInverseImage(go_leftCR)
										.hasMoreElements()) {
									Arc go_left2 = (Arc) this.embeddingLeft.get(k)
											.getInverseImage(go_leftCR).nextElement();
									if (condL.getImage(go_left2) == null) {
										condCR.removeMapping(go_condCR);
									}
								}
							}
							else if (this.embeddingLeft.get(k)
									.getInverseImage(go_leftCR).hasMoreElements()) {
								todelete.add(go_condCR);
							}
						}
					} 
				}
			}
		}
		for (int i = 0; i < todelete.size(); i++) {
			try {
				condCR.getTarget().destroyArc((Arc) todelete.get(i), false,
						false);
			} catch (TypeException ex) {
			}
		}
		todelete.clear();

		// delete node to be created or without a mapping
		// from its pre-image into the condL
		final Iterator<Node> nodes = condCR.getTarget().getNodesSet()
				.iterator();
		while (nodes.hasNext()) {
			Node go_condCR = nodes.next();
			Node goInv_condCR = null;
			if (condCR.getInverseImage(go_condCR).hasMoreElements()) {
				if (condTargetToCondCR.getInverseImage(go_condCR)
						.hasMoreElements())
					goInv_condCR = (Node) condTargetToCondCR.getInverseImage(
							go_condCR).nextElement();
				if (goInv_condCR == null) {
					todelete.add(go_condCR);
					continue;
				}
				if (!condIsom.getInverseImage(goInv_condCR).hasMoreElements()) {
					todelete.add(go_condCR);
					continue;
				}
				Node go_leftCR = (Node) condCR.getInverseImage(go_condCR).nextElement();
				for (int j=0; j<this.sources.size(); j++) {					
					if (r == this.sources.get(j)) {
						for (int k=0; k<this.embeddingLeft.size(); k++) {
							if (k == j) {
								if (this.embeddingLeft.get(k).getInverseImage(go_leftCR)
										.hasMoreElements()) {
									Node go_left2 = (Node) this.embeddingLeft.get(k)
											.getInverseImage(go_leftCR).nextElement();
									if (condL.getImage(go_left2) == null) {
										condCR.removeMapping(go_condCR);
									}
								}
							}
							else if (this.embeddingLeft.get(k)
									.getInverseImage(go_leftCR).hasMoreElements()) {
								todelete.add(go_condCR);
							}
						}
					} 
				}
			}
		}
		for (int i = 0; i < todelete.size(); i++) {
			try {
				condCR.getTarget().destroyNode((Node) todelete.get(i), false,
						false);
			} catch (TypeException ex) {
			}
		}
		return ok;
	}

	private OrdinaryMorphism shiftGlobalRuleCond(final Rule rule,
			final OrdinaryMorphism cond, final OrdinaryMorphism morph) {

		final OrdinaryMorphism condIsom = cond.getTarget().isomorphicCopy();
		if (condIsom == null)
			return null;

		final OrdinaryMorphism condCR = BaseFactory.theBaseFactory
				.createMorphism(morph.getTarget(), condIsom.getTarget());
		condCR.setName(cond.getName());
		condCR.setEnabled(cond.isEnabled());
		return condCR;
	}

	private void removeIsomorphicMorph(List<OrdinaryMorphism> list) {
		List<OrdinaryMorphism> list1 = new Vector<OrdinaryMorphism>(list);
		for (int i = 0; i < list1.size(); i++) {
			OrdinaryMorphism m1 = list1.get(i);
			if (list.contains(m1)) {
				for (int j = 0; j < list.size(); j++) {
					OrdinaryMorphism m = list.get(j);
					if (m != m1) {
						OrdinaryMorphism iso = m1.getTarget()
								.getIsomorphicWith(m.getTarget());
						if (iso != null) {
							if (m1.isCommutative(m, iso)) {
								list.remove(j);
								j--;
							}
							iso.dispose();
						}
					}
				}
			}
		}
	}

	private void removeIsomorphicNestedMorph(List<NestedApplCond> list) {
		List<NestedApplCond> list1 = new Vector<NestedApplCond>(list);
		for (int i = 0; i < list1.size(); i++) {
			OrdinaryMorphism m1 = list1.get(i);
			if (list.contains(m1)) {
				for (int j = 0; j < list.size(); j++) {
					OrdinaryMorphism m = list.get(j);
					if (m != m1) {
						OrdinaryMorphism iso = m1.getTarget()
								.getIsomorphicWith(m.getTarget());
						if (iso != null) {
							if (m1.isCommutative(m, iso)) {
								list.remove(j);
								j--;
							}
							iso.dispose();
						}
					}
				}
			}
		}
	}
	
	private void removeIsomorphicMorph(List<OrdinaryMorphism> list1, List<OrdinaryMorphism> list2) {		
		List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>(list1);		
		list.addAll(list2);
		List<OrdinaryMorphism> list3 = new Vector<OrdinaryMorphism>(list);	
		for (int i = 0; i < list.size(); i++) {
			OrdinaryMorphism m1 = list.get(i);
			if (list3.contains(m1)) {
				for (int j = 0; j < list3.size(); j++) {
					OrdinaryMorphism m = list3.get(j);
					if (m != m1) {
						OrdinaryMorphism iso = m1.getTarget()
								.getIsomorphicWith(m.getTarget());
						if (iso != null) {
							if (m1.isCommutative(m, iso)) {
								list3.remove(j);
								j--;
								list1.remove(m);
								list2.remove(m);
							}
							iso.dispose();
						}
					}
				}
			}
		}
	}
	
	private boolean shiftCondsOverEmbMorphOLD(
			int indx, final Rule r,
			final OrdinaryMorphism morph, 
			final Vector<Evaluable> flist) {

		boolean result = true;

		// shift PACs
		if (r.getPACs().hasMoreElements()) {
			// shift over left embedding
			List<Formula> fl = new Vector<Formula>();
			result = shiftPACsOverEmbMorphOLD(indx, r, r.getPACs(), morph, fl);
			if (result) {
				if (!fl.isEmpty()) {
					List<Evaluable> evals = new Vector<Evaluable>(fl.size());
					for (int k = 0; k < fl.size(); k++) {
						evals.add(fl.get(k));
					}
					Formula f = new Formula(evals, Formula.AND);
					if (f.isValid())
						flist.add(f);
				}
			}
		}

		// shift NACs
		if (result 
				&& r.getNACs().hasMoreElements()) {
			// shift left condition over left embedding
			shiftNACsOverEmbMorph(indx, r, r.getNACs(), morph);

		}
		
		return result;
	}

	private boolean shiftNACs(
			int indx, 
			final Rule r,
			final OrdinaryMorphism embMorph,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism left) {
		boolean result = true;
		// shift NACs over left embedding
		if (r.getNACs().hasMoreElements()) {
			// shift left condition over left embedding
			shiftNACsOverEmbMorph(indx, r, r.getNACs(), embMorph);
			shiftNACsOverMorphAndLeft(indx, r, r.getNACs(), morph, left, embMorph);
		}
		return result;
	}
	
	private boolean shiftPACs(
			int indx, 
			final Rule r,
			final OrdinaryMorphism embMorph,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism left,
			final Vector<Evaluable> flist) {
		boolean ok = true;
		// shift PACs
		if (r.getPACs().hasMoreElements()) {
			// shift over left embedding
			List<Formula> fl = new Vector<Formula>();
			Enumeration<OrdinaryMorphism> conds = r.getPACs();
			while (conds.hasMoreElements() && ok) {
				OrdinaryMorphism cond = conds.nextElement();
				List<OrdinaryMorphism> list1 = shiftPACOverEmbMorph(indx, r, cond, embMorph, fl);
				List<OrdinaryMorphism> list2 = shiftPACOverMorphAndLeft(indx, r, cond, morph, left, embMorph, fl);
				if (list1 != null && !list1.isEmpty()
						&& list2 != null && !list2.isEmpty()) {	
					this.removeIsomorphicMorph(list1, list2);
					List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>(list1);
					list.addAll(list2);
					if (list.size() > 1) {
						// make GACs and formula = GAC1 || GAC2 || ...
						Formula f = BaseFactory.theBaseFactory.replacePACsByGACs(list);
						fl.add(f);
						// add to Nested ACs
						for (int l = 0; l < list.size(); l++) {
							this.addNestedAC(list.get(l));
						}
					} else 
						this.addPAC(list.get(0));	
					list1.clear();
					list2.clear();
					list.clear();
				}
				else
					ok = false;
			}
			if (ok) {
				if (!fl.isEmpty()) {
					List<Evaluable> evals = new Vector<Evaluable>(fl.size());
					for (int k = 0; k < fl.size(); k++) {
						evals.add(fl.get(k));
					}
					Formula f = new Formula(evals, Formula.AND);
					if (f.isValid())
						flist.add(f);
				}
			}
		}
		return ok;
	}
	
	private boolean shiftGACs(
			int indx, 
			final Rule r,
			final OrdinaryMorphism embMorph,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism left,
			final Vector<Evaluable> flist) {
		// shift GACs and its formulas
		boolean result = true;
		if (r.getNestedACs().hasMoreElements()) {
			Hashtable<String, List<NestedApplCond>> cond2shift = new Hashtable<String, List<NestedApplCond>>();
			
			final Enumeration<OrdinaryMorphism> conds = r.getNestedACs();
			while (conds.hasMoreElements()) {
				NestedApplCond cond = (NestedApplCond) conds.nextElement();
				// shift left condition over left embedding
				List<NestedApplCond> 
				list = shiftGACOverEmbMorph(indx, r, cond, embMorph, cond2shift);
				if (list != null && !list.isEmpty()) {
					List<NestedApplCond> 
					list1 = shiftGACOverMorphAndLeft(indx, r, cond, 
										morph, left, this.embeddingLeft.get(indx), cond2shift);
					if (list1 != null && !list1.isEmpty()) { 
						list.addAll(list1);
						// check of double GACs
						this.removeIsomorphicNestedMorph(list);
						
						for (int j = 0; j < list.size(); j++) {
							NestedApplCond nc = list.get(j);
							if (!nc.getTarget().isEmpty()) {
								nc.getTarget().unsetTransientAttrValues();
								nc.getImage().setAttrContext(
										this.getLeft().getAttrContext());
								nc.setAttrContext(this.getLeft().getAttrContext());
								BaseFactory.theBaseFactory.declareVariable(nc
										.getTarget(), (VarTuple) this.getAttrContext()
										.getVariables());
								this.addNestedAC(nc);
							}
						}				
						// handle rule formula
						List<Evaluable> evals = r.getEnabledGeneralACsAsEvaluable();
						Formula f = new Formula(evals, r.getFormulaStr());
						for (int j = 0; j < evals.size(); j++) {
							NestedApplCond nc = (NestedApplCond) evals.get(j);
							String key = String.valueOf(nc.hashCode() + indx);
							if (cond2shift != null) {
								List<NestedApplCond> shift = cond2shift.get(key);
								if (shift != null) {
									List<Evaluable> shiftEvals = new Vector<Evaluable>(shift.size());
									for (int k = 0; k < shift.size(); k++) {
										shiftEvals.add(shift.get(k));
									}
									f.patchInEvaluableAsDisjunction(cond, shiftEvals);
								}
							}
						}
						if (f.isValid())
							flist.add(f);
						list.clear();
					}
				}
				list = null;
			}
		}
		return result;
	}
	
//	private List<NestedApplCond> shiftGACAlongEmbMorph(
//			int indx, final Rule r,
//			final NestedApplCond cond, 
//			final OrdinaryMorphism morph,
//			final Hashtable<String, List<NestedApplCond>> cond2shift) {
//
//		// shift left condition over left embedding
//		List<NestedApplCond> l = shiftGenCondAlongEmbMorph(indx, r, cond, morph, cond2shift);
//		if (l != null && l.size() > 0) {
//			for (int i = 0; i < l.size(); i++) {
//				OrdinaryMorphism c = l.get(i);
//				if (!BaseFactory.theBaseFactory
//							.isDanglingSatisfied(c, this)) {
//					System.out.println(c.getName()
//								+ "   dangling edge condition failed");
//					this.setErrorMsg(c.getName()
//								+ "   dangling edge condition failed");
//					l.clear();
//					return null;
//				}
//			}
//			// store the local list to be used later in the formula
//			String key = String.valueOf(cond.hashCode() + indx);
//			if (cond2shift.get(key) == null) {
//				cond2shift.put(key, l);
//			} else {
//				cond2shift.get(key).addAll(l);
//			}
//			return l;
//		}
//		return null;
//	}

	
	private void handleAttrConditions() {
		final VarTuple vars = (VarTuple) this.getAttrContext().getVariables();
		final CondTuple conds = (CondTuple) this.getAttrContext()
				.getConditions();

		for (int i = 0; i < this.sources.size(); i++) {
			final Rule r = this.sources.get(i);

			final VarTuple varsN = (VarTuple) r.getAttrContext().getVariables();
			final CondTuple condsN = (CondTuple) r.getAttrContext()
					.getConditions();

			// first check variables
			for (int j = 0; j < varsN.getNumberOfEntries(); j++) {
				VarMember var = varsN.getVarMemberAt(j);
				if (vars.getVarMemberAt(var.getName()) == null) {
					vars.declare(var.getHandler(), var.getDeclaration()
							.getTypeName(), var.getName());
					// System.out.println("Par. Rule::  NEW::  "+var.getDeclaration().getTypeName()+"      "+
					// var.getName());
				}
			}

			// now conditions
			for (int j = 0; j < condsN.getNumberOfEntries(); j++) {
				CondMember cond = condsN.getCondMemberAt(j);
				if (!conds.contains(cond.getExprAsText())) {
					conds.addCondition(cond.getExprAsText());
				}
			}
		}
	}

	/**
	 * Try to shift the specified application condition <code>cond</code>
	 * over the embedding morphism <code>morph</code>. For
	 * given morphisms must hold: cond.getSource() == morph.getSource().
	 * 
	 * @return list of application condition on the graph
	 *         <code>morph.getTarget()</code>
	 */
	private List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shiftCondOverEmbMorph(
			final OrdinaryMorphism cond, 
			final OrdinaryMorphism morph) {

		final List<Pair<OrdinaryMorphism,OrdinaryMorphism>> 
		list = new Vector<Pair<OrdinaryMorphism,OrdinaryMorphism>>();

		// make an iso-copy of the rule LHS
		final OrdinaryMorphism condSrcIsom = cond.getSource().isomorphicCopy();
		if (condSrcIsom == null) 
			return null;

		// extend the target graph of condSrcIsom by elements of the target
		// graph of cond
		final OrdinaryMorphism condExt = BaseFactory.theBaseFactory
				.extendTargetGraph1ByTargetGraph2(condSrcIsom, cond);
//		System.out.println(condExt.getDomainObjects());
		// get the extended result graph
		final Graph dCondGraph = condSrcIsom.getTarget();

		final Vector<GraphObject> condDom = condSrcIsom.getDomainObjects();
		final List<Object> requiredObjs = new Vector<Object>(condDom.size());
		final Hashtable<Object, Object> objmap = new Hashtable<Object, Object>(
				condDom.size());
		// fill a map with objects required
		// for the graph overlappings of dCondGraph and morph.getTarget()
		for (int j = 0; j < condDom.size(); j++) {
			GraphObject go = condDom.get(j);
			GraphObject go1 = condSrcIsom.getImage(go);
			GraphObject go2 = morph.getImage(go);
			if (go1 != null && go2 != null) {
				requiredObjs.add(go1);
				objmap.put(go1, go2);
			}
		}
		// make graph overlappings above required objects
		Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> overlaps = BaseFactory.theBaseFactory
				.getOverlappingByPartialPredefinedIntersection(dCondGraph,
						morph.getTarget(), requiredObjs, objmap, true); // false);
		// add created conditions to the list
		while (overlaps.hasMoreElements()) {
			Pair<OrdinaryMorphism, OrdinaryMorphism> p = overlaps.nextElement();
			if (!p.second.getTarget().isEmpty()) {
				// get an application condition after shifting
				OrdinaryMorphism c = p.second;
				c.setEnabled(cond.isEnabled());
				c.setName(cond.getName());
				c.shifted = true;
				OrdinaryMorphism first = condExt.compose(p.first);
				if (first.getSize() == condExt.getSize()) {
					list.add(new Pair<OrdinaryMorphism, OrdinaryMorphism>(first, p.second));
				}
				else
					list.add(p);
			}
		}
		return (list.isEmpty())? null: list;
	}

	/**
	 * Shift NACs of the Rule r over its embedding morphism to this rule.
	 * 
	 */
	private boolean shiftNACsOverEmbMorph(
			int indx, final Rule r,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph) {

		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) {
				// shift condition cond over emb. morphism
				List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverEmbMorph(cond, morph);
				if (shift != null && shift.size() > 0) {
					for (int i = 0; i < shift.size(); i++) {
						Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
						OrdinaryMorphism lc = p.second;
						if (!lc.getTarget().isEmpty()) {
//							lc.getSource().unsetTransientAttrValues();
							lc.getTarget().unsetTransientAttrValues();
							for (int j=0; j<this.embeddingLeft.size(); j++) {
								this.filterNotNeededObjs(lc, this.embeddingLeft.get(j));
							}
							if (lc.getTarget().getSize() >= cond.getTarget().getSize()
									&& !this.isFalseCond(cond, lc, morph)) {							
								lc.setName(cond.getName() + "_" 
											+ String.valueOf(indx)+ String.valueOf(i));
								lc.setEnabled(cond.isEnabled());
								this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
								this.addNAC(lc);
							}
						}
					}
					shift.clear();
				}				
			}
			else if (cond.getSize() == 0) {
				OrdinaryMorphism lc = this.shiftGlobalRuleCond(r, cond, morph);
				if (lc != null) {
					if (!lc.getTarget().isEmpty()) {
						lc.setName(cond.getName());
						lc.setEnabled(cond.isEnabled());
						lc.getTarget().unsetTransientAttrValues();
						lc.getImage().setAttrContext(
								this.getLeft().getAttrContext());
						lc.setAttrContext(this.getLeft().getAttrContext());
						BaseFactory.theBaseFactory.declareVariable(cond
								.getTarget(), (VarTuple) this.getAttrContext()
								.getVariables());
						this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
						this.addNAC(lc);
					}
				}
			}
		}
		return true;
	}



	/**
	 * Shift PACs of the Rule r over embedding morphism to this rule.
	 * 
	 * Returns list = Shift(iL, cond) + L(this, Shift(iR, R(r, cond)))
	 * containing GACs.<br>
	 * NOTE: Each created shift PAC is replaced by a GAC. <br>
	 * Moreover, a Formula over GACs defined as<br>
	 * f = V{ci} (ci an element of list) as disjunction of the GACs.
	 */
	private boolean shiftPACsOverEmbMorphOLD(
			int indx, final Rule r,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph, 
			final List<Formula> fl) {

		boolean ok = true;
		while (conds.hasMoreElements() && ok) {
			OrdinaryMorphism cond = conds.nextElement();
			ok = shiftPACOverEmbMorphOLD(indx, r, cond, morph, fl);
		}
		return ok;
	}
	
	private boolean shiftPACOverEmbMorphOLD(
			int indx, final Rule r,
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph, 
			final List<Formula> fl) {

		if (cond.getSize() > 0) {
				// shift condition cond over morphism iL
				List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverEmbMorph(cond, morph);
				if (shift != null && shift.size() > 0) {
					List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>();
					for (int i = 0; i < shift.size(); i++) {
						Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
						OrdinaryMorphism lc = p.second;
						if (!BaseFactory.theBaseFactory.isDanglingSatisfied(lc, this)) {							
							System.out.println(lc.getName()
									+ "   dangling edge condition failed");
							this.setErrorMsg(lc.getName()
									+ "   dangling edge condition failed");
							this.disposeMorphs(shift);
							// shift failed, so this rule is not applicable 
							this.notApplicable = true;
							return false;
						}
						if (!lc.getTarget().isEmpty()) {
//							lc.getSource().unsetTransientAttrValues();
							lc.getTarget().unsetTransientAttrValues();
							for (int j=0; j<this.embeddingLeft.size(); j++) {
								this.filterNotNeededObjs(lc, this.embeddingLeft.get(j));
							}
							if (lc.getTarget().getSize() >= cond.getTarget().getSize()
									&& !this.isFalseCond(cond, lc, morph)) {
								lc.setEnabled(cond.isEnabled());
								lc.setName(cond.getName() + "_" + String.valueOf(indx)
										+ String.valueOf(i));
								lc.getImage().setAttrContext(
										this.getLeft().getAttrContext());
								lc.setAttrContext(this.getLeft().getAttrContext());
								BaseFactory.theBaseFactory.declareVariable(
										lc.getTarget(), 
										(VarTuple) this.getAttrContext().getVariables());
								this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
								list.add(lc);
							}
						}
					}
					// make formula if more then one shifted PAC created
					if (list.size() > 1) {
						removeIsomorphicMorph(list);
						// replace PACs by GACs and formula = GAC1 || GAC2 || ...
						Formula f = BaseFactory.theBaseFactory.replacePACsByGACs(list);
						fl.add(f);
						// add to Nested ACs
						for (int l = 0; l < list.size(); l++) {
							this.addNestedAC(list.get(l));
						}
						this.disposeMorphs(shift);	
					}
					else {
						// add to PACs
						this.addPAC(list.get(0));
						shift.clear();
					}
					list.clear();
				}
			}
		else if (cond.getSize() == 0) {
			OrdinaryMorphism lc = this.shiftGlobalRuleCond(r, cond, morph);
			if (lc != null) {
				lc.setName(cond.getName());
				lc.setEnabled(cond.isEnabled());
				lc.getTarget().unsetTransientAttrValues();
				lc.getImage().setAttrContext(
						this.getLeft().getAttrContext());
				lc.setAttrContext(this.getLeft().getAttrContext());
				BaseFactory.theBaseFactory.declareVariable(
							lc.getTarget(),
							(VarTuple) this.getAttrContext().getVariables());
				this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
				this.addPAC(lc);
			}
		}
		return true;
	}
	
	private List<OrdinaryMorphism> shiftPACOverEmbMorph(
			int indx, final Rule r,
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph, 
			final List<Formula> fl) {
		
		List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>();
		if (cond.getSize() > 0) {
				// shift condition cond over morphism iL
				List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverEmbMorph(cond, morph);
				if (shift != null && shift.size() > 0) {
					for (int i = 0; i < shift.size(); i++) {
						Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
						OrdinaryMorphism lc = p.second;
						if (!BaseFactory.theBaseFactory.isDanglingSatisfied(lc, this)) {							
							System.out.println(lc.getName()
									+ "   dangling edge condition failed");
							this.setErrorMsg(lc.getName()
									+ "   dangling edge condition failed");
							this.disposeMorphs(shift);
							// shift failed, so this rule is not applicable 
							this.notApplicable = true;
							return null;
						}
						if (!lc.getTarget().isEmpty()) {
//							lc.getSource().unsetTransientAttrValues();
							lc.getTarget().unsetTransientAttrValues();
							for (int j=0; j<this.embeddingLeft.size(); j++) {
								this.filterNotNeededObjs(lc, this.embeddingLeft.get(j));
							}
							if (!this.isFalseCond(cond, lc, morph)) {
								lc.setEnabled(cond.isEnabled());
								lc.setName(cond.getName() + "_" + String.valueOf(indx)
										+ String.valueOf(i));
								lc.getImage().setAttrContext(
										this.getLeft().getAttrContext());
								lc.setAttrContext(this.getLeft().getAttrContext());
								BaseFactory.theBaseFactory.declareVariable(
										lc.getTarget(), 
										(VarTuple) this.getAttrContext().getVariables());
								this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
								list.add(lc);
							}
						}
					}
				}
			}
		else if (cond.getSize() == 0) {
			OrdinaryMorphism lc = this.shiftGlobalRuleCond(r, cond, morph);
			if (lc != null) {
				lc.setName(cond.getName());
				lc.setEnabled(cond.isEnabled());
				lc.getTarget().unsetTransientAttrValues();
				lc.getImage().setAttrContext(
						this.getLeft().getAttrContext());
				lc.setAttrContext(this.getLeft().getAttrContext());
				BaseFactory.theBaseFactory.declareVariable(
							lc.getTarget(),
							(VarTuple) this.getAttrContext().getVariables());
				this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
				list.add(lc);
			}
		}
		return list;
	}
	
	
	private List<NestedApplCond> shiftGACOverEmbMorph(
			int indx, 
			final Rule r,
			final NestedApplCond cond,
			final OrdinaryMorphism morph,
			final Hashtable<String, List<NestedApplCond>> cond2shift) {

		// shift condition over morphism mo
		List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverEmbMorph(cond, morph);
		if (shift != null && shift.size() > 0) {
			Vector<NestedApplCond> left = new Vector<NestedApplCond>(shift.size());
			for (int i = 0; i < shift.size(); i++) {
				Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
				OrdinaryMorphism c = p.second;
				for (int j=0; j<this.embeddingLeft.size(); j++) {
					this.filterNotNeededObjs(c, this.embeddingLeft.get(j));					
					this.filterCondL(cond, c, this.embeddingLeft.get(j));
				}
				if ((cond.getSize() > 0 && c.getSize() == 0)) {
					c.dispose();
					shift.remove(i);
					i--;
					continue;
				}
				else if (!BaseFactory.theBaseFactory.isDanglingSatisfied(c, this)) {
					System.out.println(c.getName()+ "   dangling edge condition failed");
					this.setErrorMsg(c.getName()+ "   dangling edge condition failed");
					this.disposeMorphs(shift);						
					shift.clear();
					shift = null;
					return null;
				}
				else if (c.getTarget().getSize() >= cond.getTarget().getSize()
						&& !this.isFalseCond(cond, c, morph)) {
					NestedApplCond lc = BaseFactory.theBaseFactory
							.createGeneralMorphism(c.getSource(), c.getTarget());
					lc.getDomainObjects().addAll(c.getDomainObjects());
					lc.getCodomainObjects().addAll(c.getCodomainObjects());
					BaseFactory.theBaseFactory.unsetAllTransientAttrValues(lc);
									
					lc.setEnabled(c.isEnabled());
					lc.setName(cond.getName() + "_" + String.valueOf(indx)
								+ String.valueOf(i));
					this.adjustUnsetAttrsAboveMorphs(cond, this.embeddingLeft.get(indx), lc);
					left.add(lc);
						
					if (!cond.getNestedACs().isEmpty()) {						
						shiftNestedCondOverMorph(cond, p.first, lc);
						
//						OrdinaryMorphism mo1 = BaseFactory.theBaseFactory
//							.createMorphism(cond.getTarget(), lc.getTarget());							
//						if (mo1.completeDiagram(cond, morph, lc)) {
//							shiftNestedCondAlongMorph(cond, mo1, lc);
//						}
					}
				}
				c.dispose();
			}
			shift.clear();
			shift = null;
			
			// store the local list to be used later in the formula
			String key = String.valueOf(cond.hashCode() + indx);
			if (cond2shift.get(key) == null) {
				cond2shift.put(key, left);
			} else {
				cond2shift.get(key).addAll(left);
			}
			left.trimToSize();
			return left;
		}
		return null;
	}
	
	private List<NestedApplCond> shiftGACOverMorphAndLeft(
			int indx,
			final Rule r, 
			final NestedApplCond cond,
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph,
			final Hashtable<String, List<NestedApplCond>> cond2shift) {

		// shift condition over morph
		List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverMorph(r, cond, morph);
		if (shift != null && shift.size() > 0) {
			final Vector<NestedApplCond> list = new Vector<NestedApplCond>(shift.size());
			for (int i = 0; i < shift.size(); i++) {
				Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(i);
				OrdinaryMorphism c = p.second;
				if (cond.getSize() > 0 && c.getSize() == 0) {
					c.dispose();
					shift.remove(i);
					i--;
					continue;
				}
				// shift c left over this
				OrdinaryMorphism 
				lc = BaseFactory.theBaseFactory.shiftApplCondLeft(c, left);
				if (lc != null) {
					if ((cond.getSize() > 0 && lc.getSize() == 0)
							|| lc.getTarget().getSize() < cond.getTarget().getSize()) {
						c.dispose();
						lc.dispose();
						shift.remove(i);
						i--;
						continue;
					}
					this.filterNotNeededObjs(lc, embMorph);
					this.filterCondL(c, lc, embMorph);
					NestedApplCond lnc = BaseFactory.theBaseFactory
								.createGeneralMorphism(lc.getSource(),
										lc.getTarget());
					lnc.getDomainObjects().addAll(lc.getDomainObjects());
					lnc.getCodomainObjects().addAll(lc.getCodomainObjects());
					lnc.getTarget().unsetTransientAttrValues();
					this.filterNotNeededObjs(lnc, morph);
					if (!this.isFalseCond(cond, lnc, embMorph)) {
						lnc.setEnabled(c.isEnabled());
						lnc.setName(cond.getName() + "_" + String.valueOf(indx)
														+ String.valueOf(i) + "_");
						this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
						
						boolean ok = cond.getNestedACs().isEmpty(); 
						if (!cond.getNestedACs().isEmpty()) {
							ok = shiftNestedCondOverMorph(cond, p.first, lnc);
						}
						if (ok)
							list.add(lnc);
						else {
							c.dispose();
							lc.dispose();
							lnc.dispose();
							shift.remove(i);
							i--;
							continue;
						}
					}
				}
				c.dispose();
				lc.dispose();
			}
			shift.clear();
			shift = null;
			list.trimToSize();
			
			// store the local list to be used later in the formula
			String key = String.valueOf(cond.hashCode() + indx);
			if (cond2shift.get(key) == null) {
				cond2shift.put(key, list);
			} else {
				cond2shift.get(key).addAll(list);
			}
			return list;
		}
		return null;
	}


	private boolean shiftNestedCondOverMorph(
			final NestedApplCond cond,
			final OrdinaryMorphism mo1, 
			final NestedApplCond lshiftCond) {
		List<Evaluable> evals = cond.getEnabledGeneralACsAsEvaluable();
		Formula f = new Formula(evals, cond.getFormulaStr());

		for (int i = 0; i < cond.getNestedACs().size(); i++) {
			NestedApplCond nc = cond.getNestedACs().get(i);
			// shift nc over morphism mo1
			final List<Pair<OrdinaryMorphism,OrdinaryMorphism>> shift = shiftCondOverEmbMorph(nc, mo1);
			if (shift != null && shift.size() > 0) {
				List<NestedApplCond> l = new Vector<NestedApplCond>(shift.size());
				for (int j = 0; j < shift.size(); j++) {
					Pair<OrdinaryMorphism,OrdinaryMorphism> p = shift.get(j);
					OrdinaryMorphism sc = p.second;
//					this.filterNotNeededObjs(sc, mo1);
					this.filterCondL(nc, sc, mo1);
					if ((nc.getSize() > 0 && sc.getSize() == 0)
							|| (sc.getTarget().getSize() < nc.getTarget().getSize())	
							|| this.isFalseCond(nc, sc, mo1)
							) {
						sc.dispose();
						shift.remove(j);
						j--;
						continue;
					}
					BaseFactory.theBaseFactory.unsetAllTransientAttrValues(sc);
					NestedApplCond nsc = BaseFactory.theBaseFactory
							.createGeneralMorphism(sc.getSource(),
									sc.getTarget());
					nsc.getDomainObjects().addAll(sc.getDomainObjects());
					nsc.getCodomainObjects().addAll(sc.getCodomainObjects());
					// set and adjust attr context
					BaseFactory.theBaseFactory.declareVariable(nsc.getTarget(),
							(VarTuple) nsc.getAttrContext().getVariables());
					BaseFactory.theBaseFactory
							.adjustAttributeValueAlongMorphismMapping(nsc);
					nsc.setName(nc.getName() + "_" + String.valueOf(j));

					lshiftCond.addNestedAC(nsc);
					l.add(nsc);

					if (!nc.getNestedACs().isEmpty()) {
						OrdinaryMorphism mo2 = BaseFactory.theBaseFactory
								.createMorphism(nc.getTarget(), nsc.getTarget());
						if (mo2.completeDiagram(nc, mo1, nsc)) {
							shiftNestedCondOverMorph(nc, mo2, nsc);
						}
					}
				}

				List<Evaluable> shiftEvals = new Vector<Evaluable>(l.size());
				for (int k = 0; k < l.size(); k++) {
					shiftEvals.add(l.get(k));
				}
				f.patchInEvaluableAsDisjunction(nc, shiftEvals);
			}
		}
		if (cond.getNestedACs().size() > 0 && lshiftCond.getNestedACs().size() == 0) {
			return false;
		}
		if (f.isValid())
			lshiftCond.setFormula(f);
		return true;
	}

	private void filterNotNeededObjs(final OrdinaryMorphism cond,
			final OrdinaryMorphism iL) {
		List<GraphObject> delete = new Vector<GraphObject>();
		// delete mapped arcs
		final Iterator<Arc> arcs = cond.getTarget().getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc arc = arcs.next();
			Enumeration<GraphObject> inv = cond.getInverseImage(arc);
			if (inv.hasMoreElements()) {
				GraphObject lgo = inv.nextElement();
				if (!iL.getInverseImage(lgo).hasMoreElements()) {
					if (similarAttribute(lgo, arc))
						delete.add(arc);
				}
			}
		}
		for (int i = 0; i < delete.size(); i++) {
			Arc arc = (Arc) delete.get(i);
			try {
				cond.removeMapping(arc);
				try {
					cond.getTarget().destroyArc(arc, false, false);
				} catch (TypeException ex) {
				}
			} catch (BadMappingException ex1) {
			}
		}
		delete.clear();

		// delete mapped free nodes
		final Iterator<Node> nodes = cond.getTarget().getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node node = nodes.next();
			if (!node.getOutgoingArcs().hasNext()
					&& !node.getIncomingArcs().hasNext()) {
				Enumeration<GraphObject> inv = cond.getInverseImage(node);
				if (inv.hasMoreElements()) {
					GraphObject lgo = inv.nextElement();
					if (!iL.getInverseImage(lgo).hasMoreElements()) {
						if (similarAttribute(lgo, node))
							delete.add(node);
					}
				}
			}
		}
		for (int i = 0; i < delete.size(); i++) {
			Node node = (Node) delete.get(i);
			try {
				cond.removeMapping(node);
				try {
					cond.getTarget().destroyNode(node, false, false);
				} catch (TypeException ex) {
				}
			} catch (BadMappingException ex1) {
			}
		}
		delete.clear();
		delete = null;
	}

	private void filterCondL(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism condL,
			final OrdinaryMorphism leftEmbMorph) {

		Vector<GraphObject> del = new Vector<GraphObject>();
		Iterator<Arc> iter2 = cond.getSource().getArcsSet().iterator();
		while (iter2.hasNext()) {
			GraphObject go = iter2.next();
			GraphObject go1 = leftEmbMorph.getImage(go);
			GraphObject go2 = condL.getImage(go1);
			if (cond.getImage(go) == null
					&& go2 != null) {				
				if (similarAttribute(go2, go1)) {
					del.add(go2);
				}
			}
		}		
		Iterator<Node> iter = cond.getSource().getNodesSet().iterator();
		while (iter.hasNext()) {
			GraphObject go = iter.next();
			GraphObject go1 = leftEmbMorph.getImage(go);
			GraphObject go2 = condL.getImage(go1);
			if (cond.getImage(go) == null
					&& go2 != null) {
				if (similarAttribute(go2, go1)) {
					del.add(go2);
				}
			}
		}
		for (int i=0; i<del.size(); i++) {
			GraphObject go = del.get(i);
			if (go.isArc()) {
				try {
					condL.removeMapping(go);
					try {							
						condL.getTarget().destroyArc((Arc)go, false, false);
					} catch (TypeException ex) {}			
				} catch (BadMappingException ex1) {}
			}
			else {
				try {
					condL.removeMapping(go);
					try {							
						condL.getTarget().destroyNode((Node)go, false, false);
					} catch (TypeException ex) {}			
				} catch (BadMappingException ex1) {}
			}
		}
		del.clear();
	}
	
	private boolean similarAttribute(final GraphObject go1,
			final GraphObject go2) {
		if (go1.getAttribute() != null && go2.getAttribute() != null) {
			final ValueTuple val1 = (ValueTuple) go1.getAttribute();
			final ValueTuple val2 = (ValueTuple) go2.getAttribute();
			for (int i = 0; i < val2.getNumberOfEntries(); i++) {
				ValueMember vm2 = val2.getValueMemberAt(i);
				ValueMember vm1 = val1.getValueMemberAt(vm2.getName());
				if (vm2.isSet()) {
					// mem2 is set, check mem1
					if (vm1 != null && vm1.isSet()) {
						if (// !vm2.isTransient() && !vm2.isTransient() &&
						!vm2.getExprAsText().equals(vm1.getExprAsText())) {
							// mem1 is set by another value
							return false;
						}
					} else {
						// mem1 does not exist or unset
						return false;
					}
				} else if (vm1.isSet()) {
					return false;
				}
			}
		}
		return true;
	}

	private void setInputParameterIfNeeded(final Rule r) {
//		final Hashtable<Graph, Vector<String>> graph2Varnames = new Hashtable<Graph, Vector<String>>();

		final VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		final Vector<String> varNamesRHS = r.getTarget()
				.getVariableNamesOfAttributes();
		final Vector<String> varNamesLHS = r.getSource()
				.getVariableNamesOfAttributes();

		for (int i = 0; i < vars.getNumberOfEntries(); i++) {
			VarMember var = vars.getVarMemberAt(i);
			if (varNamesRHS.contains(var.getName())) {
				if (!varNamesLHS.contains(var.getName())) {
					var.setInputParameter(true);
				}
			}
		}
	}
	
	private void disposeMorphs(final List<Pair<OrdinaryMorphism,OrdinaryMorphism>> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Pair<OrdinaryMorphism,OrdinaryMorphism> p = list.get(i);
				p.first.dispose();
				p.second.dispose();
			}
			list.clear();
		}
	}

	@SuppressWarnings("unused")
	private void disposeMorphs2(final List<NestedApplCond> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).dispose();
			}
			list.clear();
		}
	}
	
	private void adjustUnsetAttrsAboveMorph(final OrdinaryMorphism morph) {	
		Enumeration<GraphObject> dom = morph.getDomain();
		while (dom.hasMoreElements()) {
			final GraphObject from = dom.nextElement();
			final GraphObject to = morph.getImage(from);
			if (from.getAttribute() != null && to.getAttribute() != null) {
				ValueTuple vt_from = (ValueTuple) from.getAttribute();
				ValueTuple vt_to = (ValueTuple) to.getAttribute();
				for (int i=0; i<vt_from.getNumberOfEntries(); i++) {
					ValueMember vm_from = vt_from.getValueMemberAt(i);
					if (!vm_from.isSet()) {
						ValueMember vm_to = vt_to.getValueMemberAt(vm_from.getName());
						if (vm_to != null && vm_to.isSet()) {
							vm_to.setExpr(null);
							vm_to.setTransient(false);
						}
					}
//					else {
//						if (vm_from.getExpr().isVariable()) {
//							if (((VarTuple) morph.getAttrContext().getVariables())
//										.getVarMemberAt(vm_from.getExprAsText()) == null) {
//								((VarTuple) morph.getAttrContext().getVariables())
//											.getTupleType().addMember(vm_from.getHandler(), vm_from.getDeclaration().getTypeName(), vm_from.getExprAsText());
//								((VarTuple) morph.getAttrContext().getVariables()).getVarMemberAt(vm_from.getExprAsText()).setTransient(false);
//							}
//						}
//						ValueMember vm_to = vt_to.getValueMemberAt(vm_from.getName());
//						if (vm_to != null) {
//							vm_to.setExprAsText(vm_from.getExprAsText());
//							vm_to.setTransient(false);
//						}
//					} 
				}						
			}
		}
	}
	
	private void adjustUnsetAttrsAboveMorphs(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism shifted) {	
		Enumeration<GraphObject> dom = morph.getDomain();
		while (dom.hasMoreElements()) {
			final GraphObject obj = dom.nextElement();
			final GraphObject img = morph.getImage(obj);
			final GraphObject from = cond.getImage(obj);
			final GraphObject to = shifted.getImage(img);
			if (from != null && to != null) {
				if (from.getAttribute() != null && to.getAttribute() != null) {
					ValueTuple vt_from = (ValueTuple) from.getAttribute();
					ValueTuple vt_to = (ValueTuple) to.getAttribute();
					for (int i=0; i<vt_from.getNumberOfEntries(); i++) {
						ValueMember vm_from = vt_from.getValueMemberAt(i);
						if (!vm_from.isSet()) {
							ValueMember vm_to = vt_to.getValueMemberAt(vm_from.getName());
							if (vm_to != null && vm_to.isSet()) {
								vm_to.setExpr(null);
								vm_to.setTransient(false);
							}
						}
//						else {
	//						if (vm_from.getExpr().isVariable()) {
	//							if (((VarTuple) morph.getAttrContext().getVariables())
	//										.getVarMemberAt(vm_from.getExprAsText()) == null) {
	//								((VarTuple) morph.getAttrContext().getVariables())
	//											.getTupleType().addMember(vm_from.getHandler(), vm_from.getDeclaration().getTypeName(), vm_from.getExprAsText());
	//								((VarTuple) morph.getAttrContext().getVariables()).getVarMemberAt(vm_from.getExprAsText()).setTransient(false);
	//							}
	//						}
	//						ValueMember vm_to = vt_to.getValueMemberAt(vm_from.getName());
	//						if (vm_to != null) {
	//							vm_to.setExprAsText(vm_from.getExprAsText());
	//							vm_to.setTransient(false);
	//						}
//						} 
					}						
				}
			}
		}
	}
	
}
