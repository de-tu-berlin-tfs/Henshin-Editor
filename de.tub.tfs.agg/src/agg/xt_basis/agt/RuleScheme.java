/**
 * 
 */
package agg.xt_basis.agt;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrContext;
import agg.attribute.AttrMapping;
import agg.attribute.AttrVariableTuple;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.XMLHelper;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeSet;

/**
 * This class implements an interaction rule scheme 
 * which contains a kernel rule (subrule) 
 * and a set of multi rules (extending rules).
 * The kernel rule is a common subaction of multi rules
 * and it is used for synchronization of parallel application of multi rules. 
 * This kind of rule application is known as amalgamated graph transformation.
 * 
 * @author olga
 */
public class RuleScheme extends Rule //implements Observer 
{

	private String schemeName = "RuleScheme";
	
	private int itsIndex = -1;
	
	private boolean hasInputParameter;
	
	private boolean valid;
	
	private KernelRule kernelRule;
	
	private final List<Rule> multiRules = new Vector<Rule>();

	private AmalgamatedRule amalgamRule;
	
	private Hashtable<GraphObject, GraphObject> amalgamObj2kernelRuleObj;
	
	private boolean parallelKernel;
	
	private boolean disjointMultis = true;
	
	private boolean checkDeleteUseConflict = true;
	
	private boolean atLeastOneMultiMatch;
	
	private boolean shiftDone;
	
	
	/**
	 * Create new rule scheme with an empty kernel rule
	 * and empty set of multi rules.
	 * 
	 * @param aSchemeName
	 * @param types 	
	 */
	public RuleScheme(final String aSchemeName, TypeSet types) {
		super(types);
		super.trimToSize();
		
		this.itsName = aSchemeName;
		this.schemeName = aSchemeName;
		
		this.kernelRule = new KernelRule(types);
		this.kernelRule.setRuleScheme(this);
		
//		this.kernelRule.getLeft().addObserver(this);
//		this.kernelRule.getRight().addObserver(this);		
	}

	/**
	 * Create new rule scheme with the given kernel rule.
	 * The typeSet of this RuleScheme is the TypeSet of the KernelRule.
	 */
	public RuleScheme(final String aSchemeName, KernelRule kernel) {
		super(kernel.getTypeSet());
		super.trimToSize();
		
		this.itsName = aSchemeName;
		this.schemeName = aSchemeName;
		
		this.kernelRule = kernel;
		this.kernelRule.setRuleScheme(this);
		
//		this.kernelRule.getLeft().addObserver(this);
//		this.kernelRule.getRight().addObserver(this);		
	}
	
	/*
	 * Create new rule scheme with a kernel rule based on the specified 
	 * left and right graphs
	 * and empty set of multi rules.
	 * 
	 * @param aSchemeName
	 * @param leftOfKernelRule		left graph of its kernel rule
	 * @param rightOfKernelRule		right graph of its kernel rule
	 *
	public RuleScheme(
			final String aSchemeName, 
			final Graph leftOfKernelRule, 
			final Graph rightOfKernelRule) {
		
		super(leftOfKernelRule.getTypeSet());
		
		this.itsName = aSchemeName;
		this.schemeName = aSchemeName;
		
		this.kernelRule = new KernelRule(leftOfKernelRule, rightOfKernelRule);
		this.kernelRule.setRuleScheme(this);	
		
//		this.kernelRule.getLeft().addObserver(this);
//		this.kernelRule.getRight().addObserver(this);		
	}
*/
	
	
	public boolean hasNestedACs() {
		boolean hasGACs = this.kernelRule.hasNestedACs();
		for (int i=0; i<this.multiRules.size(); i++) {
			hasGACs = hasGACs || this.multiRules.get(i).hasNestedACs();
		}
		return hasGACs;
	}
	
	/**
	 * Creates if needed a match of the kernel rule.
	 * 
	 * @param graph	 host graph to apply the kernel rule
	 * @return	match of the kernel rule
	 */
	public Match getKernelMatch(final Graph graph) {
		if (this.kernelRule.getMatch() == null) {
			this.kernelRule.setMatch(BaseFactory.theFactory().createMatch(this.kernelRule, graph));
		}
		return this.kernelRule.getMatch();
	}
	
	/**
	 * Clears existing match of the kernel and multi rules.
	 */
	public void clearMatches() {	
		if (this.kernelRule.getMatch() != null) {
			this.kernelRule.getMatch().dispose();
			this.kernelRule.setMatch(null);
		}		
		this.clearMatchesOfMultiRules();	
		this.unsetAttrContextVars();
		
		// for super rule of this RuleScheme
		this.clear();		
		((VarTuple) this.getAttrContext().getVariables()).unsetVariables();//InputParameters();
	}
		
	public void unsetAttrContextVars() {
 		((VarTuple) this.kernelRule.getAttrContext().getVariables()).unsetVariables();
		int s = ((VarTuple) this.kernelRule.getAttrContext().getVariables()).getSize();
		for (int j = 0; j < s; j++) {
			(((VarTuple) this.kernelRule.getAttrContext().getVariables()).getVarMemberAt(j)).setExpr(null);
		}
		for (int i=0; i<this.multiRules.size(); i++) {
			 final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
			 ((VarTuple) multiRule.getAttrContext().getVariables()).unsetVariables();//InputParameters();
			 s = ((VarTuple) multiRule.getAttrContext().getVariables()).getSize();
			 for (int j = 0; j < s; j++) {
					(((VarTuple) multiRule.getAttrContext().getVariables()).getVarMemberAt(j)).setExpr(null);
			}
		}
	}
	
	public void showAttrContextVars() {
		((VarTuple) this.kernelRule.getAttrContext().getVariables()).showVariables();
		for (int i=0; i<this.multiRules.size(); i++) {
			 final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
			 ((VarTuple) multiRule.getAttrContext().getVariables()).showVariables();//InputParameters();
		}
	}
	
	public void clearMatchesOfMultiRules() {		
		for (int i=0; i<this.multiRules.size(); i++) {
			 final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
			 if (multiRule.getMatch() != null) {	
				 multiRule.getMatch().dispose();
				 multiRule.setMatch(null);
			 }
		}
	}

	
	public void disposeMatch() {
		this.clearMatches();
		this.unsetAttrContextVars();
		if (this.amalgamRule != null) {
			if (this.amalgamRule.getMatch() != null)
				this.amalgamRule.getMatch().dispose();
		}
	}
	
	
	/**
	 * Destroys current amalgamated rule and its amalgamated match.
	 */
	public void disposeAmalgamatedRule() {
		this.clearMatches();
		this.unsetAttrContextVars();
		if (this.amalgamRule != null) {
			if (this.amalgamRule.getMatch() != null)
				this.amalgamRule.getMatch().dispose();
			this.amalgamRule.dispose();
			this.amalgamRule = null;
		}
	}
	
	/**
	 * Destroys this RuleScheme instance .
	 */
	public void dispose() {
		super.dispose();		
		this.clearMatches();		
		if (this.amalgamRule != null) {
			this.amalgamRule.dispose();
			this.amalgamRule = null;
		}		
		for (int i=0; i<this.multiRules.size(); i++) {
			this.multiRules.get(i).dispose();
		}				
		this.multiRules.clear();		
		this.kernelRule.dispose();			
	}
	
	/**
	 * Returns a copy of this rule scheme by using its types.
	 */
	public RuleScheme getClone() {
		return BaseFactory.theFactory().cloneRuleScheme(this);
	}
	
	/**
	 * Returns a copy of this rule scheme by using the specified types.
	 */
	public RuleScheme getClone(TypeSet types) {
		return BaseFactory.theFactory().cloneRuleScheme(this, types);
	}
	
	/**
	 * Replicate new rule mapping of the kernel rule.
	 * 
	 * @param leftgo	graph object of the LHS of the kernel rule
	 * @param rightgo	graph object of the RHS of the kernel rule
	 * @throws BadMappingException
	 */
	public void propagateAddRuleMappingToMultiRule(
			final GraphObject leftgo, 
			final GraphObject rightgo) 
	throws BadMappingException {
		for(int i=0; i<this.multiRules.size(); i++) {	      	    			
			final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
			try {
				GraphObject objL = multiRule.getEmbeddingLeft().getImage(leftgo);
				GraphObject objR = multiRule.getEmbeddingRight().getImage(rightgo);
				if (objL != null && objR != null)
					multiRule.addMapping(objL, objR);
			} catch (BadMappingException ex) {
				System.out.println("RuleScheme.propagateCreatedMappingToMultiRule: "
						+ex.getLocalizedMessage());
				throw ex;
			}
		}
    }
    
	/**
	 * Replicate remove rule mapping of the kernel rule.
	 * 
	 * @param go	graph object of the kernel rule
	 * @param left  true if graph object belongs to the LHS of the kernel rule,
	 * otherwise false
	 */
    public void propagateRemoveRuleMappingToMultiRule(
    		final GraphObject go, 
    		boolean left) {
    	for(int i=0; i<this.multiRules.size(); i++) {	      	    			
			final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
			if (left) {	
				GraphObject objL = multiRule.getEmbeddingLeft().getImage(go);
				if (objL != null)
					multiRule.removeMapping(go);				
			} else {	
				GraphObject objR = multiRule.getEmbeddingRight().getImage(go);
				if (objR != null
						&& this.getInverseImage(objR).hasMoreElements())
					multiRule.removeMapping(this.getInverseImage(go).nextElement());				
			}
		}
    }
    
    /**
     * Checks the left, right and rule morphism embedding of the kernel rule.
     * 
     * @return	true 	if embedding holds, otherwise false
     */
	public boolean isAmalgamable() {	
		this.errorMsg = null;
		for(int i=0; i<this.multiRules.size(); i++) {	      	    			
			final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
			if (!multiRule.isLeftEmbeddingValid() 
					|| !multiRule.isRightEmbeddingValid()
					|| !multiRule.isMorphismEmbeddingValid()) {
				this.errorMsg = multiRule.getName();
				return false;
			}
		}
		return true;	   	 
	}  
	
	/**
	 * The methods <code>storeIndexOfRuleList</code> and <code>getStoredIndexOfRuleList</code> used
	 * by GraGra class during save and load procedure of the XML object only.<br>
	 * Stores the index of the current RuleScheme. 
	 */
	public void storeIndexOfRuleList(int i) {
		this.itsIndex = i;
	}
	
	/**
	 * The methods <code>storeIndexOfRuleList</code> and <code>getStoredIndexOfRuleList</code> used
	 * by GraGra class during save and load procedure of the XML object only.<br>
	 * Returns loaded index of the current RuleScheme 
	 * which is the index inside the rule list after the XML object loaded.<br>
	 */
	public int getStoredIndexOfRuleList() {
		return this.itsIndex;
	}
	
	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.kernelRule.trimToSize();
		for(int i=0; i<this.multiRules.size(); i++) {
			this.multiRules.get(i).trimToSize();
		}
	}
	
	public void setName(String aName) {
		this.schemeName = aName;
		this.itsName = this.schemeName;
	}
	
	public void setSchemeName(final String aName) {
		this.schemeName = aName;
		this.itsName = this.schemeName;
	}
	
	public String getSchemeName() {
		return this.schemeName;
	}
	
	/**
	 * Sets its layer. The layer is used by layered grammar.
	 */
	public void setLayer(int l) {
		this.layer = l;
		this.kernelRule.setLayer(l);
		for (int i=0; i<this.multiRules.size(); i++) {
			this.multiRules.get(i).setLayer(l);
		}
	}
	
	public void setCheckDeleteUseConflictRequired(boolean b) {
		this.checkDeleteUseConflict = b;
	}
	
	public boolean checkDeleteUseConflictRequired() {
		return this.checkDeleteUseConflict;
	}
	
	public void setAtLeastOneMultiMatchRequired(boolean b) {
		this.atLeastOneMultiMatch = b;
	}
	
	public boolean atLeastOneMultiMatchRequired() {
		return this.atLeastOneMultiMatch;
	}
	
	/**
	 * Requires that all matches of the multi rules are disjoint.
	 * If false, then the multi rules have to be conflict free.
	 * 
	 * @param b	true if disjoint, otherwise false
	 */
	public void setDisjointMultiMatches(boolean b) {
		this.disjointMultis = b;
	}
	
	/**
	 * @return	true if disjoint, otherwise false
	 */
	public boolean disjointMultiMatches() {
		return this.disjointMultis;
	}
	
	/**
	 * Allows to construct an amalgamated rule based on all possible disjoint
	 * matches of the kernel rule.
	 * @param b		true in case of all possible disjoint matches of the kernel rule,
	 * 				false in case of a single match of the kernel rule
	 */
	public void setParallelKernelMatch(boolean b) {
		this.parallelKernel = b;
	}
	
	/**
	 * 
	 * @return	true if parallel matches of the kernel rule, otherwise false
	 */
	public boolean parallelKernelMatch() {
		return this.parallelKernel;
	}
	
	public RuleScheme getRuleScheme() {
		return this;
	}
	
	public Rule getKernelRule() {
		return this.kernelRule;
	}
	
	public MultiRule getMultiRule(final Graph g) {
    	for (int i = 0; i<this.multiRules.size(); i++) {
    		final Rule multiRule = this.multiRules.get(i);
    		if (multiRule.getLeft() == g
    				|| multiRule.getRight() == g)
    			return (MultiRule)multiRule;
    	}
    	return null;
    }
	
	public MultiRule getMultiRule(int index) {
		if (index >= 0 && index < this.multiRules.size())
			return (MultiRule) this.multiRules.get(index);
		
		return null;
	}
	
	public MultiRule getLastMultiRule() {
		return (MultiRule) this.multiRules.get(this.multiRules.size()-1);
	}
	
	public boolean isLastMultiRule(final Rule r) {
		return (this.multiRules.get(this.multiRules.size()-1) == r);
	}
	
	public Rule getRule(final String rulename) {
		if (this.getName().equals(rulename))
			return this;
		else if (this.kernelRule.getName().equals(rulename)
				|| rulename.equals(this.itsName+"."+this.kernelRule.getName()))
			return this.kernelRule;
		else {
			for(int i=0; i<this.multiRules.size(); i++) {	      	    			
				final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
				if (multiRule.getName().equals(rulename)
						|| rulename.equals(this.itsName+"."+multiRule.getName()))
					return multiRule;
			}
		}
		return null;
	}
	
	
	public Rule getRuleByQualifiedName(final String rname) {
		if (this.getName().equals(rname))
			return this;
		else if (this.kernelRule.getName().equals(rname)
				|| rname.equals(this.itsName+"."+this.kernelRule.getName()))
			return this.kernelRule;
		else {
			for(int i=0; i<this.multiRules.size(); i++) {	      	    			
				final MultiRule multiRule = (MultiRule) this.multiRules.get(i);
				if (multiRule.getName().equals(rname)
						|| rname.equals(this.itsName+"."+multiRule.getName()))
					return multiRule;
			}
		}
		return null;
	}
	
	public List<Rule> getMultiRules() {
		return this.multiRules;
	}
	
	public int getCountOfMultiRules() {
		return this.multiRules.size();
	}
	
	public Rule addMultiRule(final String name) {
		final MultiRule r = this.createMultiRule(name);
		
		this.kernelRule.getLeft().addObserver(r);
		this.kernelRule.getRight().addObserver(r);
		this.kernelRule.addObserver(r);
		
		return r;
	}
	
	public boolean isRuleOfScheme(final Rule r) {
		if (this == r)
			return true;
		else if (this.amalgamRule == r)
			return true;
		else if (this.kernelRule == r)
			return true;
		else {
			for(int i=0; i<this.multiRules.size(); i++) {	      	    			
				if (this.multiRules.get(i) == r) 
					return true;
			}
		} 
		return false;
	}
	
	public void setAmalgamatedRule(final AmalgamatedRule amalgamRule) {
		this.amalgamRule = amalgamRule;
		if (this.amalgamRule != null)
			this.amalgamRule.setRuleScheme(this);
	}
	
	private AmalgamatedRule createAmalgamatedRule(final Graph g, final MorphCompletionStrategy s) {
		final Covering cov = new Covering(this, g, s);      
		if (cov.amalgamate()) { 
			this.errorMsg = "";
			this.amalgamObj2kernelRuleObj = cov.getMappingAmalgamToKernelRule();			
			return cov.getAmalgamatedRule();
		} 
		this.errorMsg = cov.getErrorMessage();
		return null;
	}
	
	
	public GraphObject getKernelOfAmalgamRuleObject(final GraphObject amalgamObj) {
		return this.amalgamObj2kernelRuleObj.get(amalgamObj);
	}
	
	/**
	 * Constructs amalgamated rule of the specified host graph.
	 * 
	 * @param g		host graph
	 * @return	amalgamated rule
	 */
	public AmalgamatedRule getAmalgamatedRule(final Graph g, final MorphCompletionStrategy s) {
		this.amalgamRule = createAmalgamatedRule(g, s);
		return this.amalgamRule;
	} 
	
	/**
	 * Returns existing amalgamated rule, otherwise null.
	 */
	public AmalgamatedRule getAmalgamatedRule() {
		return this.amalgamRule;
	}

	public void removeAmalgamatedRule() {
		if (this.amalgamRule != null) {
			this.amalgamRule.setRuleScheme(null);
			this.amalgamRule.dispose();
			this.amalgamRule = null;
		}
	}
	
	/**
	 * Checks the kernel rule and all enabled multi rules are ready to transform.
	 */
	public boolean isValid() {
		this.valid = this.isReadyToTransform();	   
		return this.valid;
	}
	
	public boolean isElement(Graph g) {
		if (this.kernelRule.isElement(g)) {
			return true;
		} 
		for (int i=0; i<this.multiRules.size(); i++) {
			Rule r = this.multiRules.get(i);
			if (r.isElement(g))
				return true;
		}
		
		return false;
	}
	
	public void refreshAttributed() {
		this.kernelRule.refreshAttributed();
		for (int i=0; i<this.multiRules.size(); i++) {
			this.multiRules.get(i).refreshAttributed();
		}
	}
	
	public boolean isUsingType(GraphObject typeObj) {
		if (this.kernelRule.isUsingType(typeObj)) {
			return true;
		} 
		for (int i=0; i<this.multiRules.size(); i++) {
			if (this.multiRules.get(i).isUsingType(typeObj))
				return true;
		}
		
		return false;		
	}
	
	/**
	 * Returns true if the kernel rule or ones of multi rules does require an input parameter,
	 * otherwise false.
	 */
	public boolean hasInputParameter() {
		return this.hasInputParameter;
	}
	
	/**
	 * If possible, creates amalgamated rule and match of this rule scheme
	 * at the specified host graph.
	 * @param g		host graph 
	 * @return	amalgamated match, otherwise null
	 */
	public Match getMatch(final Graph g, final MorphCompletionStrategy s) {
		if (this.hasInputParameter) {
			applyValueOfInputParameter();
		}
		
		this.amalgamRule = createAmalgamatedRule(g, s);
		if (this.amalgamRule != null) {
			this.amalgamRule.setWaitBeforeApplyEnabled(this.isWaitBeforeApplyEnabled());
			return this.amalgamRule.getMatch();		
		}
		return null;
	}
	
	
	/**
	 * Returns existing amalgamated match, otherwise null.
	 */
	public Match getMatch() {
		if (this.amalgamRule != null) {
			this.amalgamRule.setWaitBeforeApplyEnabled(this.isWaitBeforeApplyEnabled());
			return this.amalgamRule.getMatch();
		}
		
		return null;
	}
	
	/**
	 * Returns true, if its kernel rule or one of the multi rules
	 *  contains enabled nested application conditions.
	 */
	public boolean hasEnabledACs(boolean checkBefore) {
		if (checkBefore) {
			this.hasEnabledGACs = this.kernelRule.hasEnabledACs(checkBefore);
			if (!this.hasEnabledGACs) {
				for (int i=0; i<this.multiRules.size(); i++) {
					if (this.multiRules.get(i).isEnabled()
							&& this.multiRules.get(i).hasEnabledACs(checkBefore)) {
						this.hasEnabledGACs = true;
						break;	
					}
				}
			}
		}
		return this.hasEnabledGACs;
	}
	
	/**
	 * Checks the kernel rule and all enabled multi rules be ready to transform.
	 */
	public boolean isReadyToTransform() {
		this.valid = false;
		if (!this.kernelRule.isReadyToTransform()) {
			return false;
		}
		for (int i=0; i<this.multiRules.size(); i++) {
			if (this.multiRules.get(i).isEnabled()
					&& !this.multiRules.get(i).isReadyToTransform())
				return false;					
		}
		this.valid = true;
		return true;
	}
	
	/**
	 * Checks whether its kernel rule is applicable at the specified graph by the
	 * specified matching strategy or not.
	 */
	public boolean isApplicable(
			final Graph g,
			final MorphCompletionStrategy strategy,
			final boolean doCheckIfReadyToTransform) {

		boolean result = this.enabled;
		
		if (result && doCheckIfReadyToTransform) {
			result = this.isReadyToTransform();
		}

		if (result) {			
			result = false;												
			Match m = BaseFactory.theFactory().createMatch(this.kernelRule, g);
			if (m != null) {
				m.setCompletionStrategy(strategy, true);
				m.enableInputParameter(false);
				
//				((VarTuple) this.getAttrContext().getVariables()).showVariables();
//				((VarTuple) m.getAttrContext().getVariables()).showVariables();
				
				if (m.nextCompletion()) {
					result = true;
				} 
				m.dispose();
			}
		}
		return result;
	}
	
	public void setApplicable(boolean appl) {
		this.applicable = appl;
		this.kernelRule.setApplicable(appl);
	}
	
	public boolean isInputParameterSet(boolean left) {
		Rule r = this.kernelRule;
		this.addToAttrContext((VarTuple) r.getAttrContext().getVariables());
	
		if (r.getMatch() != null) {
			this.adaptAttrContextValues(r.getMatch().getAttrContext());
		} else {
			this.adaptAttrContextValues(r.getAttrContext());
		}
		
//		((VarTuple) this.getAttrContext().getVariables()).showVariables();
		
		for (int i=0; i<this.multiRules.size(); i++) {
			r = this.multiRules.get(i);
			if (r.isEnabled()) {
				this.addToAttrContext((VarTuple) r.getAttrContext().getVariables());
				if (r.getMatch() != null) {
					this.adaptAttrContextValues(r.getMatch().getAttrContext());
				}
			}
		}
//		((VarTuple) this.getAttrContext().getVariables()).showVariables();
		
		return isInputParameterSet(this.getAttrContext(), left);
	}
	
	
	
	/**
	 * If the kernel rule is using an input parameter and its value is already set
	 * do propagate this value to the attribute context of multi rules.
	 */
	public void applyValueOfInputParameter() {
		final VarTuple vars = (VarTuple) this.getAttrContext().getVariables();
		
		for (int i = 0; i < vars.getNumberOfEntries(); i++) {
			final VarMember v = vars.getVarMemberAt(i);
			if (v.isInputParameter() && v.isSet()) {
				VarMember vm = ((VarTuple) this.kernelRule.getAttrContext().getVariables())
											.getVarMemberAt(v.getName());
				if (vm != null 
						&& (vm.getExpr() == null 
								|| !v.getExprAsText().equals(vm.getExprAsText()))) {
//					vm.setExprAsText(v.getExprAsText());
					vm.setExpr(v.getExpr());
				}
				
				for (int j=0; j<this.multiRules.size(); j++) {
					vm = ((VarTuple) this.multiRules.get(j).getAttrContext().getVariables())
											.getVarMemberAt(v.getName());
					if (vm != null 
							&& (vm.getExpr() == null 
									|| !v.getExprAsText().equals(vm.getExprAsText()))) {
//						vm.setExprAsText(v.getExprAsText());
						vm.setExpr(v.getExpr());
					}
				}
			}
		}
		
//		System.out.println("applyValueOfInputParameter...  "+this.kernelRule.getName());
//		((VarTuple) this.kernelRule.getAttrContext().getVariables()).showVariables();
//		for (int j=0; j<this.multiRules.size(); j++) {
//			System.out.println("applyValueOfInputParameter...  "+this.multiRules.get(j).getName());
//			((VarTuple) this.multiRules.get(j).getAttrContext().getVariables()).showVariables();
//		}
	}
	
	private boolean leftGraphIsUsingVariable(final VarMember var) {
		if (this.kernelRule.getLeft().isUsingVariable(var)) {
			return true;
		} else if (nacIsUsingVariable(var, 
				this.kernelRule.getAttrContext().getConditions(),
				this.kernelRule.getNACsList())) {
			return true;
		} else if (pacIsUsingVariable(var, 
				this.kernelRule.getAttrContext().getConditions(),
				this.kernelRule.getPACsList())) {
			return true;
		}
		
		for (int i=0; i<this.multiRules.size(); i++) {
			if (this.multiRules.get(i).getLeft().isUsingVariable(var)) {
				return true;
			} else if (nacIsUsingVariable(var, 
					this.multiRules.get(i).getAttrContext().getConditions(),
					this.multiRules.get(i).getNACsList())) {
				return true;
			} else if (pacIsUsingVariable(var, 
					this.multiRules.get(i).getAttrContext().getConditions(),
					this.multiRules.get(i).getPACsList())) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean nacIsUsingVariable(
			final VarMember var, 
			final AttrConditionTuple act,
			final List<OrdinaryMorphism> nacs) {
		
		for (int l=0; l<nacs.size(); l++) {
			final OrdinaryMorphism nac = nacs.get(l);					
			if (nac.getTarget().isUsingVariable(var)) {
				return true;
			} 
			Vector<String> nacVars = nac.getTarget()
					.getVariableNamesOfAttributes();
			for (int j = 0; j < nacVars.size(); j++) {
				String varName = nacVars.get(j);
				for (int k = 0; k < act.getNumberOfEntries(); k++) {
					CondMember cond = (CondMember) act.getMemberAt(k);
					Vector<String> condVars = cond.getAllVariables();
					if (condVars.contains(varName)
							&& condVars.contains(var.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean pacIsUsingVariable(
			final VarMember var, 
			final AttrConditionTuple act,
			final List<OrdinaryMorphism> pacs) {
		
		for (int l=0; l<pacs.size(); l++) {
			final OrdinaryMorphism pac = pacs.get(l);					
			if (pac.getTarget().isUsingVariable(var)) {
				return true;
			} 
				
			Vector<String> pacVars = pac.getTarget()
					.getVariableNamesOfAttributes();
			for (int j = 0; j < pacVars.size(); j++) {
				String varName = pacVars.get(j);
				for (int k = 0; k < act.getNumberOfEntries(); k++) {
					CondMember cond = (CondMember) act.getMemberAt(k);
					Vector<String> condVars = cond.getAllVariables();
					if (condVars.contains(varName)
							&& condVars.contains(var.getName())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean rightGraphIsUsingVariable(final VarMember var) {
		if (this.kernelRule.getRight().isUsingVariable(var)) {
			return true;
		}
		for (int i=0; i<this.multiRules.size(); i++) {
			if (this.multiRules.get(i).getRight().isUsingVariable(var)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isInputParameterSet(final AttrContext attrContext, boolean left) {
		AttrVariableTuple avt = attrContext.getVariables();
		for (int i = 0; i < avt.getNumberOfEntries(); i++) {
			VarMember v = avt.getVarMemberAt(i);
			if (v.isInputParameter()) {
				this.hasInputParameter = true;
				if (!v.isSet()) {
					if (left && leftGraphIsUsingVariable(v)) {
						return false;
					} 
					else if (!left && rightGraphIsUsingVariable(v)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/* Create an empty multi rule.
	 */ 
	protected MultiRule createEmptyMultiRule() {		    
		MultiRule multiRule = new MultiRule(this.kernelRule.getTypeSet());
		
		multiRule.setEmbeddingLeft(new OrdinaryMorphism(
				this.kernelRule.getLeft(), multiRule.getLeft(),
				agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newContext(AttrMapping.PLAIN_MAP)));
		
		multiRule.setEmbeddingRight(new OrdinaryMorphism(
				this.kernelRule.getRight(), multiRule.getRight(),
				agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newContext(AttrMapping.PLAIN_MAP)));
		
		multiRule.setRuleScheme(this);
		
		this.multiRules.add(multiRule);
		
		
	    return multiRule;	  
	}
	
	/** Create new multi rule with embedding of the kernel rule.
	 */ 	  
	public MultiRule createMultiRule(final String ruleName) {
		OrdinaryMorphism embL = this.kernelRule.getLeft().plainCopy();
		if (embL == null) {
			embL = new OrdinaryMorphism(
							this.kernelRule.getLeft(), 
							BaseFactory.theFactory().createGraph(this.kernelRule.getLeft().getTypeSet(), false), 
							agg.attribute.impl.AttrTupleManager
								.getDefaultManager().newContext(AttrMapping.PLAIN_MAP));
		}
		OrdinaryMorphism embR = this.kernelRule.getRight().plainCopy();
		if (embR == null) {
			embR = new OrdinaryMorphism(
					this.kernelRule.getRight(), 
					BaseFactory.theFactory().createGraph(this.kernelRule.getRight().getTypeSet(), false), 
					agg.attribute.impl.AttrTupleManager
						.getDefaultManager().newContext(AttrMapping.PLAIN_MAP));
		}
		MultiRule multiRule = new MultiRule(this.kernelRule, embL, embR); 
		// add only variables of LHS and RHS	
		if (this.kernelRule.hasNACs() 
				|| this.kernelRule.hasPACs()) {		
			final Vector<String> list = this.kernelRule.getLeft().getVariableNamesOfAttributes();
			list.addAll(this.kernelRule.getRight().getVariableNamesOfAttributes());
			multiRule.addToAttrContextAccordingList(
					(VarTuple)this.kernelRule.getAttrContext().getVariables(),
					list);		
		} else {
			multiRule.addToAttrContext(
					(VarTuple)this.kernelRule.getAttrContext().getVariables());
		}
		
		multiRule.setRuleScheme(this);
		multiRule.setName(ruleName);
		multiRule.getLeft().setKind(GraphKind.LHS);
		multiRule.getRight().setKind(GraphKind.RHS);
		
		this.multiRules.add(multiRule); 
	     
		this.kernelRule.getLeft().addObserver(multiRule);
		this.kernelRule.getRight().addObserver(multiRule);
		this.kernelRule.addObserver(multiRule);
		
	    this.kernelRule.setChanged(false);
	       
	    return multiRule;	  
	}
	 
	/** Remove all multi rules. */   
	public void removeMultiRules(){
		this.multiRules.clear();
	}

	/** Remove the specified multi rule. */  
	public void removeMultiRule(Rule r) {	    
		if (this.multiRules.contains(r)) {	      
			this.multiRules.remove(r);
	    } 	 
	}
	
	/**
	 * Propagate application conditions (NAC / PAC) of the kernel rule along embedding morphism of each multi rule
	 * (left embedding: kernel.LHS -> multi.LHS, right embedding: kernel.RHS -> multi.RHS). 
	 * Propagated application conditions are added to the list of the own 
	 * application conditions of each multi rule.
	 */	
	public void propagateApplCondsOfKernelToMultiRules() {
		if (!this.shiftDone) {
			for (int j=0; j<this.multiRules.size(); j++) {
				Rule mRule = this.multiRules.get(j);
				if (mRule.isEnabled()) {
					shiftApplCondsOfKernelToMultiRule((MultiRule)mRule);
				}
			}
			this.shiftDone = true;
		}
	}
	
	/**
	 * Shift application conditions (NAC / PAC) of the kernel rule along embedding morphism of the given multi rule
	 * (left embedding: kernel.LHS -> multi.LHS, right embedding: kernel.RHS -> multi.RHS). 
	 * Shifted application condition is added to the list of the own 
	 * application conditions of the multi rule.
	 */	
	private boolean shiftApplCondsOfKernelToMultiRule(final MultiRule multiRule) {
		// shift PACS
		List<OrdinaryMorphism> kernConds = this.kernelRule.getPACsList();
		for (int i=0; i<kernConds.size(); i++) {
			OrdinaryMorphism kernCond = kernConds.get(i);
			if (kernCond.isEnabled()) {
				OrdinaryMorphism shiftCond = this.shiftApplCondAlongMorph(
															kernCond, multiRule.getEmbeddingLeft());
				if (shiftCond != null) {					
					shiftCond.setName(kernCond.getName().concat("(shifted)"));
					shiftCond.setEnabled(kernCond.isEnabled());
					
					multiRule.addShiftedKernelApplCond(shiftCond, true);
					this.shiftDone = true;
				} 
			}
		}
		// shift NACs
		kernConds = this.kernelRule.getNACsList();
		for (int i=0; i<kernConds.size(); i++) {
			OrdinaryMorphism kernCond = kernConds.get(i);
			if (kernCond.isEnabled()) {
				OrdinaryMorphism shiftCond = this.shiftApplCondAlongMorph(
															kernCond, multiRule.getEmbeddingLeft());
				if (shiftCond != null) {
					shiftCond.setName(kernCond.getName().concat("(shifted)"));
					shiftCond.setEnabled(kernCond.isEnabled());
					
					multiRule.addShiftedKernelApplCond(shiftCond, false);
					this.shiftDone = true;
				} 
			}
		}
		
		// shift NestedACs
		kernConds = this.kernelRule.getNestedACsList();
		for (int i=0; i<kernConds.size(); i++) {
			if (kernConds.get(i) instanceof NestedApplCond) {
				NestedApplCond kernCond = (NestedApplCond) kernConds.get(i);
				if (kernCond.isEnabled()) {
					NestedApplCond shiftCond = this.shiftNestedApplCondAlongEmbMorphism(
																kernCond, 
																multiRule.getEmbeddingLeft(),
																this.kernelRule.getRight().getAttrContext());
					if (shiftCond != null) {
						shiftCond.setName(kernCond.getName().concat("(shifted)"));
						shiftCond.setEnabled(kernCond.isEnabled());
						
						multiRule.addShiftedKernelNestedApplCond(shiftCond);
						this.shiftDone = true;
					} 
				}
			} else
				break;
		}
		
		addAttrCondsOfKernelToMultiRule(multiRule);
		
		return true;
	}
		
	public void removeShiftedApplConditionsFromMultiRules() {
		for (int i=0; i<this.multiRules.size(); i++) {
			Rule mRule = this.multiRules.get(i);
			
			((MultiRule) mRule).removeShiftedKernelApplConds();
					
			this.removeAttrCondsOfKernelFromMultiRule(mRule);
		}
		this.shiftDone = false;
	}
	
	private void addAttrCondsOfKernelToMultiRule(final Rule multiRule) {
		CondTuple kernConds = (CondTuple)this.kernelRule.getAttrContext().getConditions();
		CondTuple multiConds = (CondTuple)multiRule.getAttrContext().getConditions();
		for (int i=0; i<kernConds.getNumberOfEntries(); i++) {
			String kernCond = kernConds.getCondMemberAt(i).getExprAsText();
			if (!multiConds.contains(kernCond)) {
				CondMember cm = (CondMember)multiConds.addCondition(0, kernCond);
				cm.setShifted(true);
			}
		}
	}
	
	private void removeAttrCondsOfKernelFromMultiRule(final Rule multiRule) {
		CondTuple conds = (CondTuple)multiRule.getAttrContext().getConditions();
		for (int i=0; i<conds.getNumberOfEntries(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
			if (cond.isShifted()) {
				conds.getTupleType().deleteMemberAt(i);				
				i--;
			}
		}
	}
	
	/** Create mapping pairs of objects of the embedding morphisms. */   
	private void mapKernel2MultiObject(final MultiRule multiRule) {		
		final OrdinaryMorphism embLeft = multiRule.getEmbeddingLeft();
		final Enumeration<GraphObject> domLeft = embLeft.getDomain();
		while (domLeft.hasMoreElements()) {
			final GraphObject kern = domLeft.nextElement();
			multiRule.mapKernel2MultiObject(kern, embLeft.getImage(kern));
		}
//		final Enumeration<GraphObject> enLeft = multiRule.getLeft().getElements();
//	  	while (enLeft.hasMoreElements()) {	         
//	  		final GraphObject obj = enLeft.nextElement();	         
//	       	if (embLeft.getInverseImage(obj).hasMoreElements()){
//	       		multiRule.mapKernel2MultiObject(embLeft.getInverseImage(obj).nextElement(), obj);	         	         	
//	       	}	        
//	  	}    
		
	  	final OrdinaryMorphism embRight = multiRule.getEmbeddingRight();  
	  	final Enumeration<GraphObject> domRight = embRight.getDomain();
		while (domRight.hasMoreElements()) {
			final GraphObject kern = domRight.nextElement();
			multiRule.mapKernel2MultiObject(kern, embRight.getImage(kern));
		}
//	  	final Enumeration<GraphObject> enRight = multiRule.getRight().getElements();
//	  	while (enRight.hasMoreElements()) {	         
//	  		final GraphObject obj = enRight.nextElement();	         
//	  		if (embRight.getInverseImage(obj).hasMoreElements()) {
//	  			multiRule.mapKernel2MultiObject(embRight.getInverseImage(obj).nextElement(), obj);		         	         	
//	  		}	        
//	  	} 	        	  
	}
	
	public void createAttrInstanceWhereNeeded() {
		this.kernelRule.createAttrInstanceWhereNeeded();
		
		for (int i=0; i<this.multiRules.size(); i++) {
			this.multiRules.get(i).createAttrInstanceWhereNeeded();
		}
	}
	
	public void createAttrInstanceOfTypeWhereNeeded(final Type t) {
		this.kernelRule.createAttrInstanceOfTypeWhereNeeded(t);
		
		for (int i=0; i<this.multiRules.size(); i++) {
			this.multiRules.get(i).createAttrInstanceOfTypeWhereNeeded(t);
		}
	}
	
	
	/**
	 * Shift the specified application condition (NAC / PAC / General AC) along the specified embedding morphism.
	 * Required:<br>
	 * cond.getSource() == embedding.getSource()<br>
	 * Result morphism:<br>
	 * embedding.getTarget() -> copy of cond.getSource()
	 * 
	 * @param cond	an application condition
	 * @param morph 	an embedding morphism
	 * @return	shifted application condition,  Returns null if shifting failed.
	 */
	private OrdinaryMorphism shiftApplCondAlongMorph(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph) {
		
		if (cond.getSource() == morph.getSource()) {
			final OrdinaryMorphism condIso = cond.getTarget().isomorphicCopy();
			if (condIso == null)
				return null;
			
			final OrdinaryMorphism 
			shiftCond = (cond instanceof NestedApplCond)? 
					BaseFactory.theFactory().createGeneralMorphism(morph.getTarget(), condIso.getTarget())
					: BaseFactory.theFactory().createMorphism(morph.getTarget(), condIso.getTarget());
					
			final Enumeration<GraphObject> condDom = cond.getDomain();
			while (condDom.hasMoreElements()) {
				GraphObject go = condDom.nextElement();
				GraphObject condImg = cond.getImage(go);
				if (condImg != null) {
					GraphObject embedImg = morph.getImage(go);
					GraphObject isoImg = condIso.getImage(condImg);
					if (embedImg != null && isoImg != null) {
						try {
							shiftCond.addMapping(embedImg, isoImg);
						} catch (BadMappingException ex) {
							shiftCond.dispose();
							condIso.dispose(false, true);
							return null;
						}
					} else {
						shiftCond.dispose();
						condIso.dispose(false, true);
						return null;
					}
				}
			}
			return shiftCond;
		} 		
		return null;
	}
	
	
	private NestedApplCond shiftNestedApplCondAlongEmbMorphism(
			final NestedApplCond cond,
			final OrdinaryMorphism embedding,
			final AttrContext ac) {
		
		if (cond.getSource() == embedding.getSource()) {
			final OrdinaryMorphism condIso = cond.getTarget().isomorphicCopy();
			if (condIso == null)
				return null;
			
			final NestedApplCond shiftCond = new NestedApplCond(
					embedding.getTarget(), condIso.getTarget(), ac);
			if (this.propagateMapping(cond, shiftCond, embedding, condIso)) {
				
				for (int i=0; i<cond.getNestedACs().size(); i++) {
					NestedApplCond nc = cond.getNestedACAt(i);
					final NestedApplCond shiftnc = shiftNestedApplCondAlongEmbMorphism(
								nc, condIso, cond.getAttrContext());
					if (shiftnc != null) {
						shiftnc.setName(nc.getName());
						shiftnc.setEnabled(nc.isEnabled());
						shiftCond.addNestedAC(shiftnc);
					}
					else
						return null;
				}
				
				return shiftCond;
			}
			
			shiftCond.dispose();
			condIso.dispose();
		} 		
		return null;
	}
	
	private boolean propagateMapping(
			final OrdinaryMorphism from,
			final OrdinaryMorphism to,
			final OrdinaryMorphism above1,
			final OrdinaryMorphism above2) {
		
		final Enumeration<GraphObject> condDom = from.getDomain();
		while (condDom.hasMoreElements()) {
			GraphObject go = condDom.nextElement();
			GraphObject condImg = from.getImage(go);
			if (condImg != null) {
				GraphObject embedImg = above1.getImage(go);
				GraphObject isoImg = above2.getImage(condImg);
				if (embedImg != null && isoImg != null) {
					try {
						to.addMapping(embedImg, isoImg);
					} catch (BadMappingException ex) {
						return false;
					}
				} 
//				else
//					return false;
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 *
	public void update(Observable o, Object arg) {
		
	}
	*/
	
	
	/** Save the rule scheme.  
	  * @param h	AGG XML helper
	  */	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("RuleScheme", this);
		h.addAttr("name", this.schemeName);
		if (!this.enabled)
			h.addAttr("enabled", "false");
		
		h.addAttr("disjointMultis", String.valueOf(this.disjointMultis));
		h.addAttr("parallelKernel", String.valueOf(this.parallelKernel));
		h.addAttr("checkConflict", String.valueOf(this.checkDeleteUseConflict));
		h.addAttr("atLeastOneMultiMatch", String.valueOf(this.atLeastOneMultiMatch));
		h.addAttr("index", this.itsIndex);
//		String namestr = this.schemeName;
		h.openSubTag("Kernel");	  
		h.addObject("", this.kernelRule, true);	  
		h.close();
				
		for (int i=0; i<this.multiRules.size(); i++) {
			h.openSubTag("Multi");	
			final MultiRule r = (MultiRule) this.multiRules.get(i);
			h.addObject("", r, true);
			
			h.openSubTag("EmbeddingLeft");
		    r.getEmbeddingLeft().writeMorphism(h);		     
		    h.close();		    
		      
		    h.openSubTag("EmbeddingRight");		      
		    r.getEmbeddingRight().writeMorphism(h);		     
		    h.close();
		    
			h.close();
		}
		
		if (this.amalgamRule != null) {
			h.openSubTag("Amalgamated");	  
			h.addObject("", this.amalgamRule, true);	  
			h.close();
			
//			if (this.amalgamRule.getMatch() != null) {
//				this.amalgamRule.getMatch().setName("MatchOf_" + this.amalgamRule.getName());
//				h.openSubTag("MatchOf");
//				h.addObject("Rule", this.amalgamRule, false);
//				h.addObject("", this.amalgamRule.getMatch(), true);
//				h.close();
//			}
		}
		
		// TaggedValue layer
		h.openSubTag("TaggedValue");
		h.addAttr("Tag", "layer");
		h.addAttr("TagValue", this.layer);
		h.close();

		// TaggedValue priority
		h.openSubTag("TaggedValue");
		h.addAttr("Tag", "priority");
		h.addAttr("TagValue", this.priority);
		h.close();
		
		h.close();	
		
		
	}

	/** Load the rule scheme.  
	  * @param h	AGG XML helper
	  */
	public void XreadObject(XMLHelper h) {	  
		if (h.isTag("RuleScheme", this)) {
			Object attr_str = "";
			
//			setSchemeName(h.readAttr("name"));
			
			attr_str = h.readAttr("atLeastOneMultiMatch");
			if (!"".equals(attr_str)) {
				this.atLeastOneMultiMatch = Boolean.valueOf((String) attr_str).booleanValue();
			}
			
			attr_str = h.readAttr("checkConflict");
			if (!"".equals(attr_str)) {
				this.checkDeleteUseConflict = Boolean.valueOf((String) attr_str).booleanValue();
			}
			
			attr_str = h.readAttr("disjointMultis");
			if (!"".equals(attr_str)) {
				this.disjointMultis = Boolean.valueOf((String) attr_str).booleanValue();
			}
			
			attr_str = h.readAttr("enabled");
			if (!"".equals(attr_str)) {
				this.enabled = Boolean.valueOf((String) attr_str).booleanValue();
			}
			
			attr_str = h.readAttr("index");
			if (!"".equals(attr_str)) {
				this.itsIndex = Integer.valueOf((String) attr_str).intValue();
			}
			
			attr_str = h.readAttr("name");
			if (!"".equals(attr_str)) {
				setSchemeName((String) attr_str);
			}
			
			attr_str = h.readAttr("parallelKernel");
			if (!"".equals(attr_str)) {
				this.parallelKernel = Boolean.valueOf((String) attr_str).booleanValue();
			}
//			String namestr = this.schemeName;
			if (h.readSubTag("Kernel")) {
				this.kernelRule.getLeft().setKind(GraphKind.LHS);
				this.kernelRule.getRight().setKind(GraphKind.RHS);
				this.kernelRule.setRuleScheme(this);	      	     					
				h.getObject("", this.kernelRule, true);
				h.close();
			}
			
			while (h.readSubTag("Multi")) {	     				
				MultiRule mr = createEmptyMultiRule();
				mr.getLeft().setKind(GraphKind.LHS);
				mr.getRight().setKind(GraphKind.RHS);				
				mr.setRuleScheme(this);	
				
				h.getObject("", mr, true);
				
				if (h.readSubTag("EmbeddingLeft")) {
			         mr.getEmbeddingLeft().readMorphism(h);	
			         h.close();				        
				}
			          
				if(h.readSubTag("EmbeddingRight")) {			         
					mr.getEmbeddingRight().readMorphism(h);				        
					h.close();				        
				}
				h.close();
				
				mr.applyEmbeddedRuleMapping(this.kernelRule);								
				mapKernel2MultiObject(mr);
				
				this.kernelRule.getLeft().addObserver(mr);
				this.kernelRule.getRight().addObserver(mr);
			}	
			
			this.kernelRule.setChanged(false);
			
//			if (h.readSubTag("Amalgamated")) {
//				this.amalgamRule = new AmalgamatedRule(this.getTypeSet());
//				h.getObject("", amalgamRule, true);	      
//				h.close();	
//				this.amalgamRule.setRuleScheme(this);	
//				
//				if (h.readSubTag("MatchOf")) {
//					Object obj = h.getObject("Rule", null, false);
//					if (obj instanceof Rule) {
//						Rule r = (Rule) obj;
//						Match m = createMatch(r);
//						h.getObject("Match", m, true);
//						if (m.getSize() > 0)
//							m.setPartialMorphismCompletion(true);
//					} 
//					h.close();
//				} 
//			}
			
			// read layer
			if (h.readSubTag("TaggedValue")) {
				int v = 0;
				String t = h.readAttr("Tag");				
				// read new attribute
				int v2 = h.readIAttr("TagValue");				
				if (v2 > 0)
					v = v2;
				if (t.equals("layer"))
					this.layer = v;
				h.close();
			}

			// read priority
			if (h.readSubTag("TaggedValue")) {
				int v = 0;
				String t = h.readAttr("Tag");				
				// read new attribute
				int v2 = h.readIAttr("TagValue");
				if (v2 > 0)
					v = v2;
				if (t.equals("priority"))
					this.priority = v;
				h.close();
			}
			h.close();
		}
	}
	
	
}
