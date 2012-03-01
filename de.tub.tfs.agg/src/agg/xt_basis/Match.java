package agg.xt_basis;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

import agg.attribute.AttrMapping;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.AttrTupleManager;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.csp.CompletionPropertyBits;

/**
 * This class is used to represent matches (morphism from the left side graph
 * of a rule into a host graph). Note that not every instance of this class is a
 * valid match in terms of theory, because in theory a match has to be a total
 * morphism satisfying all positive (PACs) and  negative application conditions (NACs) of the
 * corresponding rule. The methods <code>isTotal()</code> and
 * <code>isValid()</code> can be used to check for these additional properties
 * dynamically.
 * 
 * @see agg.xt_basis.Morphism#isTotal()
 * @see agg.xt_basis.Match#isValid()
 * @author $Author: olga $
 * @version $Id: Match.java,v 1.88 2010/11/28 22:16:06 olga Exp $
 */
public class Match extends OrdinaryMorphism implements XMLObject {

	private Rule itsRule;

	private boolean matchValid;
	
	private transient NACStarMorphism itsCurrentNACstar;

	private Hashtable<OrdinaryMorphism, NACStarMorphism> itsNACstars;

//	private transient boolean nacsValid = true;

	private transient PACStarMorphism itsCurrentPACstar;

	private Hashtable<OrdinaryMorphism, PACStarMorphism> itsPACstars;

//	private transient boolean pacsValid = true;

	// Totality, Identification, Dangling, Gluing;
	private boolean condsTIDGchecked = false; 
	
	private boolean ignoreInParam;
	
	
	
	protected Match() {
		super();
	}

	protected Match(final Rule rule, final Graph graph) {
		super(rule.getLeft(), graph);
		
		this.itsAttrContext = this.itsAttrManager.newContext(AttrMapping.MATCH_MAP,
				rule.getAttrContext());
		this.itsRule = rule;
		this.itsName = "MatchOf_" + rule.getName();
		if (!rule.getNACsList().isEmpty()) {
			this.itsNACstars = new Hashtable<OrdinaryMorphism, NACStarMorphism>(rule
					.getNACsList().size());
		}
		if (!rule.getPACsList().isEmpty()) {
			this.itsPACstars = new Hashtable<OrdinaryMorphism, PACStarMorphism>(rule
					.getPACsList().size());
		}
	}

	/**
	 * Remove all graph object mappings and all relations to its source and target graphs.
	 */
	public void dispose() {	
		this.condsTIDGchecked = false;
		
		if (this.itsNACstars != null) {
			Iterator<NACStarMorphism> nacStars = this.itsNACstars.values().iterator();
			while (nacStars.hasNext()) {
				nacStars.next().dispose();
			}
			this.itsNACstars.clear();
			this.itsCurrentNACstar = null;
		}			
		if (this.itsPACstars != null) {
			Iterator<PACStarMorphism> pacStars = this.itsPACstars.values().iterator();
			while (pacStars.hasNext()) {
				pacStars.next().dispose();
			}
			this.itsPACstars.clear();
			this.itsCurrentPACstar = null;
		}		
		this.itsRule = null;
		
		super.dispose();
		
//		System.out.println("Match.dispose()   DONE "+this+"   "+this.getName());
	}
	
	public void finalize() {
	}
	
	/**
	 * Return true if this morphism can be completed basically.
	 */
	public boolean canComplete() {
		// check graph size if injective morphism
		if (this.itsCompleter.getProperties().get(CompletionPropertyBits.INJECTIVE)
				&& !(this.itsRule instanceof ParallelRule) 
				&& (this.itsOrig.getNodesCount() > this.itsImag.getNodesCount()
							|| this.itsOrig.getArcsCount() > this.itsImag.getArcsCount())) {
			return false;
		}
		
		// check types: all types of the orig. graph should be in image, too
		final Vector<Type> origTypes = this.itsOrig.getUsedTypes();
		final Vector<Type> imagTypes = this.itsImag.getUsedAndInheritedTypes();
		for (int i = 0; i < origTypes.size(); i++) {
			if (!imagTypes.contains(origTypes.get(i)))
				return false;
		}
		return true;
	}
	
	public void clear() {
		super.clear();
		
		this.condsTIDGchecked = false;
		
		if (this.itsNACstars != null) {
			final Enumeration<OrdinaryMorphism> nacs = this.itsNACstars.keys();
			while (nacs.hasMoreElements()) {
				NACStarMorphism nacStar = this.itsNACstars.get(nacs.nextElement());
//				nacStar.clear();
//				((VarTuple) nacStar.getAttrContext().getVariables())
//											.unsetNotInputVariables();
				nacStar.dispose();
			}
			this.itsNACstars.clear();
		}
		
		if (this.itsPACstars != null) {
			final Enumeration<OrdinaryMorphism> pacs = this.itsPACstars.keys();
			while (pacs.hasMoreElements()) {
				PACStarMorphism pacStar = this.itsPACstars.get(pacs.nextElement());
//				pacStar.clear();	
//				((VarTuple) pacStar.getAttrContext().getVariables())
//											.unsetNotInputVariables();
				pacStar.dispose();
			}
			this.itsPACstars.clear();
		}		
	}
	
	
	public void resetTarget(Graph g) {
		clear();
		this.itsCompleter.resetSolver(false);
		this.itsImag = g;
		this.itsCompleter.resetTypeMap(this.itsImag);
		this.typeObjectsMapChanged = true;
		if (this.itsNACstars != null) {
			final Enumeration<OrdinaryMorphism> keys = this.itsNACstars.keys();
			while (keys.hasMoreElements())
				this.itsNACstars.get(keys.nextElement()).setTarget(this.itsImag);
		}
		if (this.itsPACstars != null) {
			final Enumeration<OrdinaryMorphism> keys = this.itsPACstars.keys();
			while (keys.hasMoreElements())
				this.itsPACstars.get(keys.nextElement()).setTarget(this.itsImag);
		}
	}

	/** Return the rule that I am a match for. */
	public final Rule getRule() {
		return this.itsRule;
	}

	/**
	 * Returns an empty message or null if this match is valid,
	 * otherwise - an error message.
	 */
	public String getErrorMsg() {
		/*
		if (super.errors.size()>0) {
			String result = "[";
			for (int i=0; i<super.errors.size(); i++) {
				if (i<1)
					result = result + super.errors.get(i)+"\n";
				else {
					result = result + "..."+"\n";
					break;
				}
			}
			result = result.substring(0, result.length()-1);
			result = result + "]";
			return result;
		} 
		*/
		return this.errorMsg;
	}

	public String getLastErrorMsg() {
//		if (super.errors.size()>0) 			
//			return super.errors.lastElement();
		
		return this.errorMsg;
	}
	
	public void clearErrorMsg() {
		super.clearErrorMsg();
	}

	/**
	 * Returns FALSE if a node of the specified abstract type is found in the target graph
	 * of this match,
	 * otherwise - TRUE.
	 */
	public boolean checkAbstractGraphObject(final Type abstractNodeType) {
		final Iterator<Node> en = this.itsImag.getNodesSet().iterator();
		while (en.hasNext()) {
			if (this.getTarget().isCompleteGraph()
					&& abstractNodeType.isAbstract() 
					&& en.next().getType().equals(abstractNodeType))
				return false;
		}
		return true;
	}

	/**
	 * Checks existing variables of the attribute context against the attribute
	 * context of its rule. Adjusts the attribute context of this match, if
	 * needed.
	 */
	public void adjustAttrInputParameter(boolean inputParameterOnly) {
		final VarTuple vtR = (VarTuple) this.itsRule.getAttrContext().getVariables();
		final VarTuple vtM = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < vtR.getNumberOfEntries(); i++) {
			final VarMember vmR = vtR.getVarMemberAt(i);
			final VarMember vmM = vtM.getVarMemberAt(vmR.getName());
			if (vmM != null) {
				if (vmR.getExpr() != null) {
					if (inputParameterOnly) {
						if (vmR.isInputParameter())
							vmM.setExprAsText(vmR.getExprAsText());
					} else
						vmM.setExprAsText(vmR.getExprAsText());
				}
				vmM.setInputParameter(vmR.isInputParameter());
			}
		}
	}

	/**
	 * Checks existing attribute conditions of the attribute context against the
	 * attribute context of its rule. Adjusts the attribute conditions of this
	 * match, if needed.
	 */
	public void adjustAttrCondition() {
		final CondTuple ctRule = (CondTuple) this.itsRule.getAttrContext().getConditions();
		final CondTuple ct = (CondTuple) this.itsAttrContext.getConditions();
		for (int i = 0; i < ct.getNumberOfEntries(); i++) {
			final CondMember cm = ct.getCondMemberAt(i);
			final CondMember cmRule = ctRule.getCondMemberAt(i);
			if (cm != null && cmRule != null
					&& cm.getExprAsText().equals(cmRule.getExprAsText())) {
				cm.setMark(cmRule.getMark());
				cm.setEnabled(cmRule.isEnabled());
			}
		}
	}

	/**
	 * Set the value of the variables of the attribute context to null.
	 */
	public void unsetInputParameter(boolean inputParameterOnly) {
		final VarTuple vt = (VarTuple) this.itsAttrContext.getVariables();
		for (int i = 0; i < vt.getNumberOfEntries(); i++) {
			VarMember vm = vt.getVarMemberAt(i);
			if (inputParameterOnly) {
				if (vm.isInputParameter())
					vm.setExpr(null);
			} else
				vm.setExpr(null);
		}
	}

	public void allowToIgnoreInputParameter(boolean b) {
		this.ignoreInParam = b;
	}
	
	public boolean allowedToIgnoreInputParameter() {
		return this.ignoreInParam;
	}
	
	/*
	 * Checks variable declarations and attribute conditions. 
	 * Use method getErrorMsg() to get found error.
	 *
	private boolean isReadyToTransform() {
		final AttrVariableTuple avt = this.itsAttrContext.getVariables();
		final Vector<String> names = avt.getVariableNames();
		final Vector<Pair<String, String>> vars = getVariableDeclarations();
		for (int i = 0; i < vars.size(); i++) {
			final Pair<String, String> p = vars.elementAt(i);
			if (!names.contains(p.second)) {
				if (isClassName(p.second) == null) {
					this.errorMsg = "The variable:  " + p.second + "  isn't declared!";
					this.errors.add(this.errorMsg);
					return false;
				}
			} else {
				final VarMember vm = avt.getVarMemberAt(p.second);
				final String t_vm = vm.getDeclaration().getTypeName();
				if (!p.first.equals(t_vm)) {
					this.errorMsg = "The type of the variable: " + p.second + "  is wrong!";
					this.errors.add(this.errorMsg);
					return false;
				}
			}
		}

		try {
			this.itsAttrContext.getVariables().getAttrManager()
										.checkIfReadyToTransform(this.itsAttrContext);
		} catch (AttrException ex) {
			this.errorMsg = ex.getLocalizedMessage();
			if (this.errorMsg.length()>0)
				this.errors.add(this.errorMsg);
			return false;
		}
		return true;
	}
*/
	
	public void resetVariableDomainOfCompletionStrategy(boolean b) {
		this.itsCompleter.resetVariableDomain(b);
	}

	/**
	 * Set the algorithm of morphism completion. Class
	 * <code>CompletionStrategySelector</code> provides a way to present and
	 * obtain available algorithms.
	 * 
	 * @see agg.xt_basis.CompletionStrategySelector 
	 */
	public void setCompletionStrategy(final MorphCompletionStrategy s,
			boolean rewrite) {
		super.setCompletionStrategy(s, rewrite);
		if (this.itsRule != null) {
			this.itsCompleter.enableParallelSearch(this.itsRule.parallelMatching);
			this.itsCompleter.setStartParallelSearchByFirst(this.itsRule.startParallelMatchByFirstCSPVar);
			
//			this.itsCompleter.setRandomisedDomain(this.itsRule.isRandomizedCSPDomain());
		}
	}

	/**
	 * Set the algorithm of morphism completion. Class
	 * <code>CompletionStrategySelector</code> provides a way to present and
	 * obtain available algorithms.
	 * The given strategy is internally cloned to prevent undesired side effects.
	 * @see agg.xt_basis.CompletionStrategySelector 
	 */
	public void setCompletionStrategy(final MorphCompletionStrategy s) {
		super.setCompletionStrategy(s);
		if (this.itsRule != null) {
			this.itsCompleter.enableParallelSearch(this.itsRule.parallelMatching);
			this.itsCompleter.setStartParallelSearchByFirst(this.itsRule.startParallelMatchByFirstCSPVar);
			
//			this.itsCompleter.setRandomisedDomain(this.itsRule.isRandomizedCSPDomain());
		}
	}
	
	private void adjustCompletionStrategy() {
		if (this.typeObjectsMapChanged) {			
			if (this.itsRule.parallelMatching) {				
				this.itsCompleter.reset();
				this.itsTouchedFlag = false;
				this.itsCompleter.resetSolverVariables();
				this.itsCompleter.resetSolverQuery_Type();				
			} else {
//				System.out.println(this.getName()+"  of   "+this.getRule().getName()+"   "+this.typeObjectsMapChanged);
				this.itsCompleter.resetVariableDomain(true);
				this.itsCompleter.reinitializeSolver(false);
			}
			this.typeObjectsMapChanged = false;			
		} else if (this.itsRule.parallelMatching 
				&& this.itsRule.startParallelMatchByFirstCSPVar) {
			this.itsCompleter.reset();
			this.itsTouchedFlag = false;
			this.itsCompleter.resetSolverVariables();
		}

		if (this.partialMorphCompletion) {
			this.itsCompleter.setPartialMorphism(this);
			this.partialMorphCompletion = false;
		}
	}

	public boolean nextCompletionWithConstantsChecking() {
		adjustCompletionStrategy();
		boolean result = super.nextCompletion();		
//		 the method areTotalityIdentificationDanglingGluingSatisfied()
		// is called now in Completion_CSP.doNext for Match instance!
		return result;
	}

	public boolean nextCompletionWithConstantsAndVariablesChecking() {
		adjustCompletionStrategy();
		boolean result = super.nextCompletion();
//		 the method areTotalityIdentificationDanglingGluingSatisfied()
		// is called now in Completion_CSP.doNext for Match instance!
		return result;
	}

	/**
	 * Check totality, identification, dangling and attribute gluing conditions.
	 */
	public boolean areTotalIdentDanglAttrGluingSatisfied() {
		boolean res = true;
		if (!isTotal()) {
			this.errorMsg = "Match is not total!";
//			this.errors.add(this.errorMsg);
			res = false;
		} else if (!isIdentSatisfied()) {
			this.errorMsg = "Identification condition is violated!";
//			this.errors.add(this.errorMsg);
			res = false;
		} 
		else if (!isDanglingSatisfied()) {
			this.errorMsg = "Dangling condition is violated!";
//			this.errors.add(this.errorMsg);
			res = false;
		} 
		else if (!attributesOfGlueObjectsCorrect()) {
			this.errorMsg = "Non-injective match failed!\n"+this.errorMsg;
//			this.errors.add(this.errorMsg);
			res = false;
		} 
		this.condsTIDGchecked = true;
		this.matchValid = res;
		return res;
	}

	/**
	 * Check totality, identification and dangling  conditions.
	 */
	public boolean areTotalIdentDanglSatisfied() {
		boolean res = true;
		if (!isTotal()) {
			this.errorMsg = "Match is not total!";
//			this.errors.add(this.errorMsg);
			res = false;
		} else if (!isIdentSatisfied()) {
			this.errorMsg = "Identification condition is violated!";
//			this.errors.add(this.errorMsg);
			res = false;
		} 
		else if (!isDanglingSatisfied()) {
			this.errorMsg = "Dangling condition is violated!";
//			this.errors.add(this.errorMsg);
			res = false;
		}  		
		return res;
	}
	
	/*
	 * Checks whether the parallel arcs possible in case the rule of this Match
	 * creates a new arc between the nodes which are preserved.
	 */
	public boolean isParallelArcSatisfied() {
//			final Type arct, final Node src, final Node tar) {
		if (!this.itsOrig.getTypeSet().isArcParallel()
				&& this.itsRule.isCreating()) {
			Iterator<Arc> arcs = this.itsRule.getImage().getArcsSet().iterator();
			while (arcs.hasNext()) {
				Arc a = arcs.next();
				if (this.itsRule.isArcCreating(a)) {
					Enumeration<GraphObject> enSrc = this.itsRule.getInverseImage(a.getSource());
					while (enSrc.hasMoreElements()) {
						Enumeration<GraphObject> enTar = this.itsRule.getInverseImage(a.getTarget());
						while (enTar.hasMoreElements()) {						
							Node src = (Node)enSrc.nextElement();
							Node tar = (Node)enTar.nextElement();
							// check the source node in the this.itsRule.LHS already contains an arc like a
							if (src.hasArc(a.getType(), tar)) {
								// does this.itsRule delete a such arc
								if (this.itsRule.isArcDeleting(src, a.getType(), tar)) {}
								else {
									this.errorMsg = "No parallel edges allowed.";
									return false;
								}
							}
							// check the graph m.getImage() already contains an arc like a
							else if (((Node)this.getImage(src)).hasArc(a.getType(), (Node)this.getImage(tar))) {
									this.errorMsg = "No parallel edges allowed.";
									return false;
							}
							else if (!this.getImage().getTypeSet().isArcDirected()
										&& ((Node)this.getImage(tar)).hasArc(a.getType(), (Node)this.getImage(src))) {
								this.errorMsg = "No parallel edges allowed.";
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 *
	 * Checks if this is a valid match.<br>
	 * There are two cases to use this method:
	 * <ol>
	 * <li>  After the method <code>nextCompletion()</code> is called and returned with TRUE.
	 * In this case the check of conditions:
	 * <code>totality, identification and dangling, NACs and PACs</code>
	 * - is already done and only check of: <code>node resp. edge type multiplicity</code>
	 * and <code>post rule application conditions</code> - will be performed.
	 * </li>
	 * <li>
	 *  The match mapping is set manually and the method <code>nextCompletion()</code> was not called.
	 * Then all checks: <code>totality, identification and dangling, NACs and PACs</code> ,
	 * <code>node resp. edge type multiplicity</code>
	 * and <code>post rule application conditions</code> - will be performed.
	 * </li>
	 * </ol>
	 * <br>
	 * For each condition the appropriate matching option should be set.
	 * <p>
	 * The usage of variables in the work graph is not allowed.
	 * 
	 * @return <code>true</code> iff this is a total morphism and all conditions
	 *         of its rule are satisfied.
	 */
	public final boolean isValid() {
		return isValid(false);
	}

	public boolean nextCompletion() {		
		if (this.getOriginal().getTypeSet().getLevelOfTypeGraphCheck() == TypeSet.ENABLED_MAX_MIN) {
			TypeError error = this.itsRule.checkNewNodeRequiresArc();
			if (error != null) {
				this.errorMsg = error.getMessage();
				return false;
			}
		}
		
		adjustCompletionStrategy();
		
		boolean result = super.nextCompletion();
		// the method areTotalityIdentificationDanglingGluingSatisfied()
		// will be called in Completion_CSP.doNext for Match instance!
		
		return result;
	}

	/**
	 * This method is like the method isValid(), 
	 * additionally, the usage of variables
	 * in a work graph is possible.
	 * 
	 * @return <code>true</code> iff this is a total morphism and all conditions
	 *         of its rule are satisfied.
	 */
	public final boolean isValid(boolean allowVariables) {
		if (!this.condsTIDGchecked) {
			this.matchValid = areTotalIdentDanglAttrGluingSatisfied();
		}	
		
		boolean result = this.matchValid;		
		if (result) {	
			// check multiplicity constraints			
			int typeLevel = getImage().getTypeSet().getLevelOfTypeGraphCheck();			
			if (typeLevel > TypeSet.ENABLED
					&& getRule().getConstraints().isEmpty()) {
				// long time0 = System.currentTimeMillis();
				if (this.itsRule.isInjective() && this.isInjective()) {
					result = isTypeMultiplicitySatisfied();
				}
			}

			// make test step to check (multiplicity)
			// rule post application condition
			if (result && 
					(!getRule().getConstraints().isEmpty()
							|| !this.isInjective() 
							|| !this.itsRule.isInjective())) {
				OrdinaryMorphism isocopy = getImage().isomorphicCopy();
				if (isocopy != null) {
					isocopy.getImage().setName(getImage().getName());
					isocopy.getImage().setCompleteGraph(getImage().isCompleteGraph());
					OrdinaryMorphism com = this.compose(isocopy);
					if (com != null) {
						Match m2 = BaseFactory.theFactory().makeMatch(getRule(), com);
						OrdinaryMorphism comatch = null;
						if (m2 != null) {
							comatch = makeTestStep(m2, allowVariables);
							if (comatch == null) {
								// errorMsg set in makeTestStep 
								result = false;
							} else {// check consistency
								// errorMsg set in isConsistent
								result = result && isConsistent(comatch, m2);
								comatch.dispose();
							}			
							m2.dispose();
						}
						com.dispose();
					} else {
						result = false;
					}
					isocopy.dispose(false, true);
					
					if (typeLevel > TypeSet.ENABLED)
						this.getTarget().getTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED);				
					this.getTarget().getTypeSet().setLevelOfTypeGraphCheck(typeLevel);
				} else {
					result = false;
				}
			}
		}

		this.matchValid = result;
		
		if (this.matchValid)
			clearErrorMsg();
		
		return this.matchValid;
	}

	/**
	 * Checks attribute condition of type <code>CondMember</code> which is enabled,
	 * is marking by <code>CondMember.LHS</code>, all used variables are definite (set).
	 * When at least one of these points failed, returns true.
	 */
	public boolean checkAttrCondition() {
		CondTuple conds = (CondTuple) this.getAttrContext().getConditions();
		for (int i = 0; i < conds.getSize(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
			if (!cond.isEnabled() 
					|| cond.getMark() != CondMember.LHS) {
				continue;
			}
			
			if (cond.isDefinite()
					&& ! cond.isTrue()) {				
				this.errorMsg = "Attribute condition  [ " + cond.getExprAsText()
						+ " ]  failed.";

				((VarTuple) this.getAttrContext().getVariables()).unsetVariables();
				this.removeAllMappings();
				return false;
			}
		}
		return true;
	}
	
	public boolean areGACsSatisfied() {
		return this.itsRule.evalFormula();
	}
	
	public boolean areNACsSatisfied() {
		if (getCompletionStrategy().getProperties().get(
				CompletionPropertyBits.NAC)) {
			final List<OrdinaryMorphism> nacs = this.itsRule.getNACsList();
			for (int l=0; l<nacs.size(); l++) {
				final OrdinaryMorphism nac = nacs.get(l);			
				if (!nac.isEnabled())
					continue;
				if (checkNAC(nac) != null) {
					this.errorMsg = "NAC  \"" + nac.getName() + "\"  is violated!";
//					this.errors.add(this.errorMsg);
					return false;
				}
			}
		}
		return true;
	}

	public boolean arePACsSatisfied() {
		if (getCompletionStrategy().getProperties().get(
				CompletionPropertyBits.PAC)) {
			final List<OrdinaryMorphism> pacs = this.itsRule.getPACsList();
			for (int l=0; l<pacs.size(); l++) {
				final OrdinaryMorphism pac = pacs.get(l);			
				if (!pac.isEnabled())
					continue;
				if (checkPAC(pac) == null) {
					this.errorMsg = "PAC  \"" + pac.getName() + "\"  is violated!";
//					this.errors.add(this.errorMsg);
					return false;
				}
			}
		}
		return true;
	}

	public boolean areNACsSatisfied(boolean withVars) {
		if (withVars) {
			if (getCompletionStrategy().getProperties().get(
					CompletionPropertyBits.NAC)) {
				final List<OrdinaryMorphism> nacs = this.itsRule.getNACsList();
				for (int l=0; l<nacs.size(); l++) {
					final OrdinaryMorphism nac = nacs.get(l);				
					if (!nac.isEnabled())
						continue;
					if (checkNAC(nac, true) != null) {
						this.errorMsg = "NAC  \"" + nac.getName()
								+ "\"  is violated!";
//						this.errors.add(this.errorMsg);
						return false;
					}
				}
			}
			return true;
		} 
		return areNACsSatisfied();
	}

	public boolean arePACsSatisfied(boolean withVars) {
		if (withVars) {
			if (getCompletionStrategy().getProperties().get(
					CompletionPropertyBits.PAC)) {
				final List<OrdinaryMorphism> pacs = this.itsRule.getPACsList();
				for (int l=0; l<pacs.size(); l++) {
					final OrdinaryMorphism pac = pacs.get(l);				
					if (!pac.isEnabled())
						continue;
					if (checkPAC(pac, true) == null) {
						this.errorMsg = "PAC  \"" + pac.getName()
								+ "\"  is violated!";
//						this.errors.add(this.errorMsg);
						return false;
					}
				}
			}
			return true;
		} 
		return arePACsSatisfied();
	}

	/**
	 * Check if this match satisfies the given negative application condition.
	 * 
	 * @return <code>null</code> if <code>nac</code> is satisfied; otherwise
	 *         the morphism between <code>nac.getImage()</code> and
	 *         <code>this.getImage()</code> that shows how <code>nac</code>
	 *         was violated. Note that the returned morphism is only valid until
	 *         the next call of <code>nextCompletion()</code> or
	 *         <code>checkNAC()</code>.
	 * 
	 * <p>
	 * <b>Pre:</b>
	 * <ol>
	 * <li><code>this.isTotal()</code>.
	 * <li><code>nac.isTotal()</code>. (This is what theory demands. The
	 * implementation works for partial NACs as well.)
	 * </ol>
	 */
	public final Morphism checkNAC(final OrdinaryMorphism nac) {
//		nacsValid = true;
//		System.out.println("\nMatch.checkNAC " +nac.getName());
		// 1. variante
//		 destroyNACstar(this.itsCurrentNACstar);
//		 this.itsCurrentNACstar = createNACstar(nac);

		// 2. variante
		this.itsCurrentNACstar = this.itsNACstars.get(nac);
		if (this.itsCurrentNACstar == null) {
			this.itsCurrentNACstar = MatchHelper.createNACstar(nac, this);
			this.itsNACstars.put(nac, this.itsCurrentNACstar);
		} else {
			this.itsCurrentNACstar.reinit(this.getAttrContext());
		}

		// try to construct nacstar morphism such that nacstar after nac
		// commutes with match:
		Morphism m = MatchHelper.checkNACStar(this.itsCurrentNACstar, nac, this, false);
		if (m != null) {
//			nacsValid = false;
			this.errorMsg = "NAC  \"" + nac.getName() + "\"  is violated!";
//			this.errors.add(this.errorMsg);
		}
		// System.out.println("Match.checkNAC " +nac.getName()+" result: "+m);
		return m;
	}

	public final Morphism checkNAC(final OrdinaryMorphism nac, boolean withVars) {
//		nacsValid = true;
		// destroyNACstar(this.itsCurrentNACstar);
		// this.itsCurrentNACstar = createNACstar(nac, withVars);

		if (withVars) // set variable context
			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(true);

		this.itsCurrentNACstar = this.itsNACstars.get(nac);
		if (this.itsCurrentNACstar == null) {
			this.itsCurrentNACstar = MatchHelper.createNACstar(nac, this, withVars);
			this.itsNACstars.put(nac, this.itsCurrentNACstar);
		} else {
			this.itsCurrentNACstar.reinit(this.getAttrContext());
		}

		// try to construct nacstar morphism such that nacstar after nac
		// commutes with match:
		Morphism m = MatchHelper.checkNACStar(this.itsCurrentNACstar, nac, this, withVars);

		if (withVars) // now unset variable context
			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(false);
		if (m != null) {
//			nacsValid = false;
			this.errorMsg = "NAC  \"" + nac.getName() + "\"  is violated!";
//			this.errors.add(this.errorMsg);
		}
		return m;
	}

	/**
	 * Check if this match satisfies the given positive application condition.
	 * 
	 * @return <code>null</code> if <code>pac</code> is not satisfied;
	 *         otherwise the morphism between <code>pac.getImage()</code> and
	 *         <code>this.getImage()</code> that shows how <code>pac</code>
	 *         was satisfied. Note that the returned morphism is only valid
	 *         until the next call of <code>nextCompletion()</code> or
	 *         <code>checkPAC()</code>.
	 * 
	 * <p>
	 * <b>Pre:</b>
	 * <ol>
	 * <li><code>this.isTotal()</code>.
	 * <li><code>pac.isTotal()</code>. (This is what theory demands. The
	 * implementation works for partial PACs as well; maybe this will turn out
	 * useful.)
	 * </ol>
	 */
	public final Morphism checkPAC(final OrdinaryMorphism pac) {
//		pacsValid = true;
		// destroyPACstar(this.itsCurrentPACstar);
		// this.itsCurrentPACstar = createPACstar(pac);

		this.itsCurrentPACstar = this.itsPACstars.get(pac);
		if (this.itsCurrentPACstar == null) {
			this.itsCurrentPACstar = MatchHelper.createPACstar(pac, this);
			this.itsPACstars.put(pac, this.itsCurrentPACstar);
		} else {
			this.itsCurrentPACstar.reinit(this.getAttrContext());
		}

		// try to construct pacstar morphism such that pacstar after pac
		// commutes with match:
		Morphism m = MatchHelper.checkPACStar(this.itsCurrentPACstar, pac, this, false);
		if (m == null) {
//			pacsValid = false;
			this.errorMsg = "PAC  \"" + pac.getName() + "\"  is violated!";
//			this.errors.add(this.errorMsg);
		}
		// System.out.println("Match.checkPAC " +pac.getName()+" result: "+m);
		return m;
	}

	public final Morphism checkPAC(final OrdinaryMorphism pac, boolean withVars) {
//		pacsValid = true;
		// destroyPACstar(this.itsCurrentPACstar);
		// this.itsCurrentPACstar = createPACstar(pac, withVars);

		if (withVars) // set variable context
			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(true);

		this.itsCurrentPACstar = this.itsPACstars.get(pac);
		if (this.itsCurrentPACstar == null) {
			this.itsCurrentPACstar = MatchHelper.createPACstar(pac, this, withVars);
			this.itsPACstars.put(pac, this.itsCurrentPACstar);
		} else {
			this.itsCurrentPACstar.reinit(this.getAttrContext());
		}

		// try to construct pacstar morphism such that pacstar after pac
		// commutes with match:
		Morphism m = MatchHelper.checkPACStar(this.itsCurrentPACstar, pac, this, withVars);

		if (withVars) // now unset variable context
			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(false);
		if (m == null) {
//			pacsValid = false;
			this.errorMsg = "PAC  \"" + pac.getName() + "\"  is violated!";
//			this.errors.add(this.errorMsg);
		}

		return m;
	}


	public void enableInputParameter(final boolean enable) {
		final VarTuple vars = (VarTuple) this.getAttrContext().getVariables();
		for (int i=0; i<vars.getNumberOfEntries(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (vm.isInputParameter()) {
				vm.setEnabled(enable);
				enableAttrConditionWithInputParameter(vm.getName(), enable);
			}
		}
	}
	
	private void enableAttrConditionWithInputParameter(final String ipName, final boolean enable) {
		final CondTuple conds = (CondTuple) this.getAttrContext().getConditions();
		for (int i=0; i<conds.getNumberOfEntries(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
			if (cond.getAllVariables().contains(ipName)) {
				cond.setEnabled(enable);
			}
		}
	}
	
	public void unsetVariables() {
		final VarTuple vars = (VarTuple) this.getAttrContext().getVariables();
		final VarTuple rulevars = (VarTuple) this.itsRule.getAttrContext().getVariables();

		for (int i = 0; i < vars.getSize(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			VarMember rulevm = rulevars.getVarMemberAt(i);
			if (!vm.isInputParameter()) {
				((ContextView)this.getAttrContext()).removeValue(vm.getName());
				((ContextView)this.itsRule.getAttrContext()).removeValue(rulevm.getName());				
			}
		}
	}

	/*
	private void unsetVariablesOfNAC(final AttrContext attrContext, 
			final OrdinaryMorphism nac) {
		final VarTuple vars = (VarTuple)attrContext.getVariables();
		final Vector<String> nacVars = nac.getTarget().getVariableNamesOfAttributes();
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (nacVars.contains(vm.getName())
					&& (vm.getMark() == VarMember.NAC)
					&& !vm.isInputParameter()) {
				((ContextView)attrContext).removeValue(vm.getName());
			}
		}
	}

	private void unsetVariablesOfPAC(final AttrContext attrContext, 
			final OrdinaryMorphism pac) {
		final VarTuple vars = (VarTuple)attrContext.getVariables();
		final Vector<String> pacVars = pac.getTarget().getVariableNamesOfAttributes();
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (pacVars.contains(vm.getName())
					&& (vm.getMark() == VarMember.PAC)
					&& !vm.isInputParameter()) {
				((ContextView)attrContext).removeValue(vm.getName());
			}
		}
	}
*/
	
	public boolean isAttrConditionSatisfied() {
		final CondTuple ct = (CondTuple) getAttrContext().getConditions();
		for (int k = 0; k < ct.getSize(); k++) {
			CondMember cm = ct.getCondMemberAt(k);
			if ((cm.getMark() == CondMember.LHS) && !cm.isTrue()) {
//				((VarTuple)this.getAttrContext().getVariables()).showVariables();
				return false;
			}
		}
		return true;
	}

	public boolean hasInputParamInAttrCondition() {
		Vector<String> names = ((CondTuple) getAttrContext().getConditions()).getAllVariables();
		VarTuple vars = (VarTuple)this.getAttrContext().getVariables();
		for (int k = 0; k < names.size(); k++) {
			String n = names.get(k);
			VarMember var = vars.getVarMemberAt(n);
			if (var != null
					&& var.isInputParameter())
				return true;
		}
		return false;
	}
	
	/**
	 * Check Identification condition for non-injective match objects.
	 * They are must be preserved. 
	 * @param match
	 * @return error message if check failed, otherwise - empty string
	 */
	public boolean isIdentSatisfied() {
		this.errorMsg = MatchHelper.isIdentSatisfied(this);
		return (this.errorMsg == null || this.errorMsg.equals(""));
	}

	/**
	 * Check Dangling condition for nodes to delete.
	 * All edges of the image node must have a pre-image in the match.
	 * @param match
	 * @return error message if check failed, otherwise - empty string
	 */
	public boolean isDanglingSatisfied() {
		this.errorMsg = MatchHelper.isDanglingSatisfied(this);		
		return (this.errorMsg == null || this.errorMsg.equals(""));		
	}

	protected void checkEdgeSourceTargetCompatibility(
			final GraphObject orig, final GraphObject image) 
	throws BadMappingException {
		try {
			MatchHelper.checkSourceTargetCompatibilityOfEdge(this, orig, image);
		} catch (BadMappingException ex) {
			throw ex;
		}	
	}

	/*
	private boolean checkVariableToNullMappping() {
		if (this.getSource().isAttributed())
			return MatchHelper.checkVariableToNullMappping(this);
		else
			return true;
	}
*/
	
	/**
	 * Checks multiplicity of node resp. edge types due to the type graph of
	 * the given match.
	 * 
	 * @return true if type multiplicity satisfied, otherwise false.
	 */
	public boolean isTypeMultiplicitySatisfied() {
		this.errorMsg = MatchHelper.isTypeMultiplicitySatisfied(this);
		return (this.errorMsg == null || this.errorMsg.equals(""));
	}
	
	/**
	 * Checks multiplicity of node resp. edge types due to the type graph of
	 * the given match.
	 * 
	 * @return true if type multiplicity satisfied, otherwise false.
	 */
	public boolean isTypeMaxMultiplicitySatisfied() {
		this.errorMsg = MatchHelper.isTypeMultiplicitySatisfied(this);
		return (this.errorMsg == null || this.errorMsg.equals(""));
	}

	private OrdinaryMorphism makeTestStep(final Match m2, boolean allowVariables) {
		// make test step to check post conditions:
		// - type graph constraints ( edge type multiplicity )
		// - graph constraints

		final OrdinaryMorphism co_match = MatchHelper.makeTestStep(m2, allowVariables, false);
		if (co_match == null) {
			this.errorMsg = MatchHelper.errorMsg;
		}
		return co_match;
	}

	private boolean isConsistent(final OrdinaryMorphism testComatch, 
			final OrdinaryMorphism testMatch) {
		this.errorMsg = MatchHelper.isConsistent(this.itsRule, testComatch, testMatch);
//		this.errors.add(this.errorMsg);
		return (this.errorMsg == null || this.errorMsg.equals(""));
	}

	private boolean attributesOfGlueObjectsCorrect() {
		this.errorMsg = MatchHelper.attributesOfGlueObjectsCorrect(this);
		return (this.errorMsg == null || this.errorMsg.equals(""));
	}	
	


	public void XwriteObject(XMLHelper h) {
		h.openNewElem("Match", this);
		writeMorphism(h);
		h.close();
	}

	public void XreadObject(XMLHelper h) {
		if (h.isTag("Match", this)) {
			readMorphism(h);
			h.close();
		}
	}

}
