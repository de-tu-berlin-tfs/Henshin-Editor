package agg.xt_basis;

import java.util.BitSet;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;

import agg.attribute.AttrContext;
import agg.util.StrategyProperties;
import agg.util.csp.Variable;
import agg.xt_basis.csp.CompletionPropertyBits;

public class MorphCompletionStrategy implements StrategyProperties,
		CompletionPropertyBits, Cloneable {

	private BitSet itsSupportedProperties;
	
	protected BitSet itsProperties;
	
	protected String itsName = "";
	
	protected boolean randomDomain;
	
	protected boolean parallel;
	
	protected boolean startParallelMatchByFirstCSPVar;
	
	
	public MorphCompletionStrategy() {}

	/** Attach myself to given ALR morphism completion strategy. */
	public MorphCompletionStrategy(BitSet supported_properties) {
		initialize(supported_properties, new BitSet());
	}

	public void dispose() {}
	
	/*
	 * Initialize myself. Workaround for subclasses to be able to perform
	 * argument preparation before superclass initialization. @param
	 * supported_properties specifies which properties I support. @param
	 * active_properties specifies which properties are activated by default.
	 */
	protected void initialize(BitSet supported_properties,
			BitSet active_properties) {
		this.itsSupportedProperties = supported_properties;
		this.itsProperties = active_properties;
	}
	
	public String getName() {
		return this.itsName;
	}
	
	public boolean isRandomisedDomain() {
		return this.randomDomain;
	}
	
	public void setRandomisedDomain(boolean b) {
		this.randomDomain = b;
	}
	
	public boolean parallelSearch() {
		return this.parallel;
	}
	
	public void enableParallelSearch(boolean b) {
		this.parallel = b;
	}
	
	/**
	 * Allows to define for CSP solver that it should always start 
	 * the next completion by the first CSP variable.
	 */
	public void setStartParallelSearchByFirst(boolean b) {
		this.startParallelMatchByFirstCSPVar = b;
	}
	
	public boolean startParallelSearchByFirst() {
		return this.startParallelSearchByFirst();
	}
	
	/**
	 * Return information about what properties I support. A property is
	 * supported if its corresponding bit is set.
	 */
	public final BitSet getSupportedProperties() {
		return (BitSet) this.itsSupportedProperties.clone();
	}

	/**
	 * Return information about what properties are currently activated.
	 * Properties can be activated or deactivated by setting or clearing their
	 * respective bits via the <code>BitSet</code> interface.
	 */
	public final BitSet getProperties() {
		return this.itsProperties;
	}

	public final void setProperty(String propertyName) {
		if (propertyName.equals(GraTraOptions.INJECTIVE)) 
			this.itsProperties.set(CompletionPropertyBits.INJECTIVE);
		else if (propertyName.equals(GraTraOptions.DANGLING)) 
			this.itsProperties.set(CompletionPropertyBits.DANGLING);
		else if (propertyName.equals(GraTraOptions.IDENTIFICATION)) 
			this.itsProperties.set(CompletionPropertyBits.IDENTIFICATION);
		else if (propertyName.equals(GraTraOptions.NACS)) 
			this.itsProperties.set(CompletionPropertyBits.NAC);
		else if (propertyName.equals(GraTraOptions.PACS)) 
			this.itsProperties.set(CompletionPropertyBits.PAC);
		else if (propertyName.equals(GraTraOptions.GACS)) 
			this.itsProperties.set(CompletionPropertyBits.GAC);
	}
	
	public final void removeProperty(String propertyName) {
		if (propertyName.equals(GraTraOptions.INJECTIVE)) 
			this.itsProperties.clear(CompletionPropertyBits.INJECTIVE);
		else if (propertyName.equals(GraTraOptions.DANGLING)) 
			this.itsProperties.clear(CompletionPropertyBits.DANGLING);
		else if (propertyName.equals(GraTraOptions.IDENTIFICATION)) 
			this.itsProperties.clear(CompletionPropertyBits.IDENTIFICATION);
		else if (propertyName.equals(GraTraOptions.NACS)) 
			this.itsProperties.clear(CompletionPropertyBits.NAC);
		else if (propertyName.equals(GraTraOptions.PACS)) 
			this.itsProperties.clear(CompletionPropertyBits.PAC);
		else if (propertyName.equals(GraTraOptions.GACS)) 
			this.itsProperties.clear(CompletionPropertyBits.GAC);
	}
	
	public boolean isInjective() 
	{
		return this.itsProperties.get(CompletionPropertyBits.INJECTIVE);
	}
	
	/**
	 * Return <code>true</code> iff the given object is an instance of the
	 * same concrete strategy class as me and has the same property bits set.
	 */
	public final boolean equals(Object other) {
		return (other instanceof MorphCompletionStrategy && this.itsProperties
				.equals(((MorphCompletionStrategy) other).getProperties()));
	}

	/**
	 * Reset my internal state, so that the forthcoming invocation of
	 * <code>next()</code> computes the first completion of the given
	 * morphism.
	 */
	public void reset() {}

	public void resetSolverQuery_Type() {}
	
	public void initialize(OrdinaryMorphism morph) {}
	
	/**
	 * Compute the next completion of <code>morph</code>. Invoke this method
	 * successively with the same argument to get all completions of a morphism.
	 * 
	 * @param morph
	 *            the morphism to totalize.
	 * @return <code>false</code> if there are no more completions.
	 */
	public boolean next(OrdinaryMorphism morph) {
		return false;
	}

	/**
	 * Compute the next completion of <code>morph</code> for the nodes and
	 * edges specified by Vector varnodes and Vector varedges. Invoke this
	 * method successively with the same arguments to get all completions of a
	 * morphism.
	 * 
	 * @return <code>false</code> if there are no more completions.
	 */
	public boolean next(OrdinaryMorphism morph, 
			Collection<Node> varnodes,
			Collection<Arc> varedges) {
//			Enumeration<Node> varnodes,
//			Enumeration<Arc> varedges) {
		return false;
	}

	/** Return a clone of myself. My property bitset is cloned as well. */
	public Object clone() {
		try {
			MorphCompletionStrategy aClone = (MorphCompletionStrategy) super
					.clone();
			aClone.itsProperties = (BitSet) this.itsProperties.clone();
			aClone.itsName = this.itsName;
			aClone.randomDomain = this.randomDomain;
			aClone.parallel = this.parallel;
			aClone.startParallelMatchByFirstCSPVar = this.startParallelMatchByFirstCSPVar;
			
			return aClone;
			
		} catch (CloneNotSupportedException exc) {
			throw new RuntimeException(exc.getMessage());
		}
	}

	public void forceBackState() {
	}

	public int getSize() {
		return 0;
	}

	public AttrContext getAttrContext() {
		return null;
	}

	public void showProperties() {
		System.out.println("*** Strategy  Properties of  " + this);
		BitSet activebits = this.itsProperties;
		for (int i = 0; i < CompletionPropertyBits.BITNAME.length; i++) {
			if (activebits.get(i))
				System.out.print(CompletionPropertyBits.BITNAME[i]);
			System.out.print("   ");
		}
		System.out.print("non-deterministically: "+this.randomDomain);
		System.out.println("\n==================================");
	}

	// extentions needed for CSP optimization

	public void resetSolver(boolean doUpdateQueries) {
	}

	public void reinitializeSolver(boolean doUpdateQueries) {
	}

	public void resetSolverVariables() {
	}

	public void removeFromObjectVarMap(GraphObject anObj) {
	}
	
	public void removeFromTypeObjectsMap(GraphObject anObj) {
	}
	
	public void resetTypeMap(Graph g) {
	}

	public void resetTypeMap(Hashtable<String, HashSet<GraphObject>> typemap) {
	}

	public void resetVariableDomain(boolean instanceNull) {
	}

	public void resetVariableDomain(GraphObject go) {
	}

	protected void unsetAttrContextVariable() {
	}

	public void setPartialMorphism(OrdinaryMorphism morph) {
	}

	public boolean isDomainOfTypeEmpty(Type t) {
		return false;
	}

	public boolean isDomainOfTypeEmpty(Type t, Type src, Type tar) {
		return false;
	}

	public void setRelatedInstanceVarMap(
			Dictionary<Object, Variable> relatedVarMap) {
	}

	public boolean hasRelatedInstanceVarMap() {
		return false;
	}

	public Dictionary<Object, Variable> getInstanceVarMap() {
		return null;
	}

//	public void resetSolverVariablesFromIndex(int startIndx) {
//	}

	/**
	 * An additional object name constraint will be added for the CSP variable
	 * of the given GraphObject anObj. This constraint requires equality of the object names.<br> 
	 * Each subclass should implement this method. 
	 */
	public void addObjectNameConstraint(GraphObject anObj) {		
	}
	
	/**
	 * Removes the object name constraint for the CSP variable
	 * of the given GraphObject anObj.
	 * Each subclass should implement this method. 
	 */
	public void removeObjectNameConstraint(GraphObject anObj) {
	}
}

