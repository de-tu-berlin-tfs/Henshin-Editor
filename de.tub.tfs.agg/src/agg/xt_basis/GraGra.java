package agg.xt_basis;

import java.io.File;
import java.util.BitSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import agg.attribute.AttrType;
import agg.attribute.facade.InformationFacade;
import agg.attribute.facade.impl.DefaultInformationFacade;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.impl.javaExpr.JexHandler;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.cons.AtomConstraint;
import agg.cons.ConstraintLayer;
import agg.cons.ConstraintPriority;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.ruleappl.ObjectFlow;
import agg.ruleappl.RuleSequence;
import agg.util.Disposable;
import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.util.Pair;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.agt.RuleScheme;
import agg.xt_basis.csp.CompletionPropertyBits;
import agg.xt_basis.csp.Completion_CSP_NoBJ;


/**
 * This class provides functionality of a graph grammar, consisting of
 * an arbitrary number of graphs (the "host graphs"), an arbitrary number of rules,
 * an arbitrary number of match morphisms from the rules into the host graph.
 * <p>
 * The implementation serves as a factory to create instances of classes 
 * Type, Graph, Rule and Match, AtomConstraint and Formula, 
 * 
 * @author $Author: olga $
 * @version $Id: GraGra.java,v 1.143 2010/12/16 17:31:39 olga Exp $
 */
public class GraGra implements Disposable, XMLObject {

	final protected Vector<Graph> 
	itsGraphs = new Vector<Graph>();

	/**
	 * The set of my rules. Elements are of type <code>Rule</code>.
	 */
	final protected Vector<Rule> 
	itsRules = new Vector<Rule>();

	Hashtable<Integer, List<Rule>> ruleSets;
	
	/**
	 * The set of my atomic graph constraints. 
	 */
	final protected Vector<AtomConstraint> 
	itsAtomics = new Vector<AtomConstraint>(5);

	/**
	 * The set of my constraints ( formulae ).
	 */
	final protected Vector<Formula> 
	itsConstraints = new Vector<Formula>(5);

	/**
	 * The list of my transformation options (only option names)
	 */
	final protected Vector<String> 
	gratraOptions = new Vector<String>();

	/**
	 * The set of my matches. Elements are of type <code>Match</code>.
	 */
	final protected Vector<Match> 
	itsMatches = new Vector<Match>();

	protected RuleSequence itsRuleSequence;
		
	final protected List<RuleSequence> 
	itsRuleSequences = new Vector<RuleSequence>(1);
	
	/**
	 * The set of the used packages of the Java classes. These classes might be
	 * used for attributing. 
	 * The first element of a Pair is the name of an attribute handler,
	 * the second - a list of class names.
	 */
	final protected Vector<Pair<String, List<String>>> 
	itsPackages = new Vector<Pair<String, List<String>>>(5);

	/**
	 * My type set used in graphs and rules. 
	 * It contains types of nodes and edges and evtl. a type graph.
	 */
	protected TypeSet typeSet;	
	
	/**
	 * My host graph. It can be changed.
	 */
	protected Graph itsGraph;

	/**
	 * My start graph. It cannot be changed. At the beginning the start is equal
	 * to the host graph. It will be used to reset my host graph.
	 */
	protected Graph itsStartGraph;		
	
	protected String itsName;
	
	protected String comment = "";
	
	/**
	 * Match completion strategy
	 */
	protected MorphCompletionStrategy strategy;
	
	protected boolean hasRuleApplCond;
	
	/** Error message, if graph constraints have been failed */
	private String consistErrMsg;

	/** Error message, if multiplicity constraints have been failed */
	private int multiplErrKind;
	
	private boolean ruleChangedLayer;
	private boolean ruleChangedPriority;
	private boolean ruleChangedEvailability;
		
	/**
	 * Directory name for saving
	 */
	protected String dirName;

	/**
	 * File name for saving
	 */
	protected String fileName;

		
//	private static final agg.attribute.AttrContext 
//	aGraphContext = agg.attribute.impl.AttrTupleManager.getDefaultManager().newContext(
//														agg.attribute.AttrMapping.GRAPH_MAP);
	
	/** Construct a new graph grammar. */
	protected GraGra() {
		this.dirName = "";
		this.fileName = "";

		this.itsName = "unnamed.gragra";

		this.typeSet = new TypeSet();
		
		this.itsGraph = BaseFactory.theFactory().createGraph(this.typeSet);		
		this.itsGraph.setKind(GraphKind.HOST);
		this.itsGraph.setCompleteGraph(true);
		this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newRightContext(aGraphContext()));
		
		this.itsGraphs.add(this.itsGraph);
		
		initMorphismCompletionStrategy();
	}
	
	/** Construct a new graph grammar. */
	public GraGra(boolean withGraph) {
		this.dirName = "";
		this.fileName = "";
		this.itsName = "unnamed.gragra";
		this.typeSet = new TypeSet();
		
		if (withGraph) {
			this.itsGraph = BaseFactory.theFactory().createGraph(this.typeSet);		
			this.itsGraph.setKind(GraphKind.HOST);
			this.itsGraph.setCompleteGraph(true);
			this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
					.getDefaultManager().newRightContext(aGraphContext()));		
			this.itsGraphs.add(this.itsGraph);
		}
		
		initMorphismCompletionStrategy();
	}
	
	/**
	 * Construct a new graph grammar with a type set
	 * and/or type graph specified by the TypeSet newTypeSet
	 */
	public GraGra(TypeSet newTypeSet) {
		this.dirName = "";
		this.fileName = "";

		this.itsName = "unnamed.gragra";

		this.typeSet = newTypeSet;

		this.itsGraph = BaseFactory.theFactory().createGraph(this.typeSet, true);
		this.itsGraph.setKind(GraphKind.HOST);
		this.itsGraph.setCompleteGraph(true);
		this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newRightContext(aGraphContext()));
		
		this.itsGraphs.add(this.itsGraph);
		
		initMorphismCompletionStrategy();
	}

	
	/** Construct a new graph grammar. */
	public GraGra(TypeSet newTypeSet, boolean withGraph) {
		this.dirName = "";
		this.fileName = "";
		this.itsName = "unnamed.gragra";
		
		this.typeSet = newTypeSet;
		
		if (withGraph) {
			this.itsGraph = BaseFactory.theFactory().createGraph(this.typeSet);		
			this.itsGraph.setKind(GraphKind.HOST);
			this.itsGraph.setCompleteGraph(true);
			this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
					.getDefaultManager().newRightContext(aGraphContext()));		
			this.itsGraphs.add(this.itsGraph);
		}
		
		initMorphismCompletionStrategy();
	}
	
	/**
	 * Constructs a graph grammar with the given graph as host graph.
	 */
	public GraGra(Graph g) {
		this.dirName = "";
		this.fileName = "";

		this.itsName = "unnamed.gragra";

		this.itsGraph = g;
		this.itsGraph.setKind(GraphKind.HOST);
		this.itsGraph.setCompleteGraph(true);
		this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newRightContext(aGraphContext()));
		
		this.itsGraphs.add(this.itsGraph);
		
		this.typeSet = g.getTypeSet();
		
		initMorphismCompletionStrategy();
	}

	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.typeSet.trimToSize();
		this.itsGraphs.trimToSize();
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			this.itsGraphs.get(i).trimToSize();
		}
		this.itsRules.trimToSize();
		for (int i=0; i<this.itsRules.size(); i++) {
			this.itsRules.get(i).trimToSize();
		}
		this.itsMatches.trimToSize();
		for (int i=0; i<this.itsMatches.size(); i++) {
			this.itsMatches.get(i).trimToSize();
		}
		this.itsAtomics.trimToSize();
		for (int i=0; i<this.itsAtomics.size(); i++) {
			this.itsAtomics.get(i).trimToSize();
		}
		this.itsConstraints.trimToSize();
		for (int i=0; i<this.itsConstraints.size(); i++) {
			this.itsConstraints.get(i).trimToSize();
		}
		((Vector<RuleSequence>)this.itsRuleSequences).trimToSize();
		for (int i=0; i<this.itsRuleSequences.size(); i++) {
			this.itsRuleSequences.get(i).trimToSize();
		}
		this.itsPackages.trimToSize();	
		this.gratraOptions.trimToSize();
		if (this.itsStartGraph != null)
			this.itsStartGraph.trimToSize();
	}
	
	public Vector<Pair<String, List<String>>> getPackages() {
		return this.itsPackages;
	}

	public void setPackages(final Vector<Pair<String, List<String>>> n) {
		this.itsPackages.clear();
		this.itsPackages.addAll(n);
	}

	public void addPackage(final Pair<String, List<String>> p) {
		if (!this.itsPackages.contains(p)) {
			this.itsPackages.add(p);
		}
	}

	public void removePackage(final Pair<String, Vector<String>> p) {
		this.itsPackages.remove(p);
	}

	private Graph createGraph() {
		final Graph g = BaseFactory.theFactory().createGraph(this.typeSet, true);
		g.setKind(GraphKind.HOST);
		g.setAttrContext(agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newRightContext(aGraphContext()));
		this.itsGraphs.add(g);
		return g;
	}

	public boolean addGraph(final Graph g) {
		boolean result;
		if (this.itsGraphs.contains(g)) {
			result = false;
		} else {
			g.setKind(GraphKind.HOST);
			this.itsGraphs.add(g);
			g.setCompleteGraph(true);
			result = true;
		}
		return result;
	}

	/**
	 * Remove the specified graph from my host graphs and destroy it.
	 * The current host graph and the specified graph have to be different.
	 */
	public void destroyGraph(final Graph g) {
		if (this.itsGraphs.remove(g)) {
			g.dispose();
		}
	}

	/**
	 * Destroy all my host graphs.
	 */
	public void destroyAllGraphs() {
		while (!this.itsGraphs.isEmpty()) {
			final Graph g = this.itsGraphs.get(0);
			this.itsGraphs.remove(0);			
			g.dispose();			
		}
	}
	
	/**
	 * Remove the specified graph from my graphs.
	 * Return FALSE if the specified graph does not belong to my graphs.
	 * The current host graph and the specified graph have to be different.
	 */
	public boolean removeGraph(final Graph g) {
		if(this.itsGraphs.contains(g)) {
			this.itsGraphs.remove(g);
			if (this.itsGraph == g) {
				if (this.itsGraphs.size() == 0) {
					this.itsGraph = null;
				}
			} 
			return true;
		}
		return false;
	}

	/**
	 * Return true, if the Rule r is element of my rules otherwise - false.
	 */
	public boolean isElement(final Rule r) {
		boolean found = false;
		for (int i=0; i<this.itsRules.size(); i++) {
			if (this.itsRules.get(i).equals(r)) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * Return true, if the specified Graph g is once of my host graphs,
	 * otherwise - false.
	 */
	public boolean isElement(final Graph g) {
		return this.itsGraphs.contains(g);
	}

	public boolean trafoByPriority() {
		return this.gratraOptions.contains(GraTraOptions.PRIORITY);
	}

	public boolean trafoByLayer() {
		return this.gratraOptions.contains(GraTraOptions.LAYERED);
	}
	
	public boolean isLayered() {
		return this.gratraOptions.contains(GraTraOptions.LAYERED);
	}

	public boolean trafoByRuleSequence() {
		return this.gratraOptions.contains(GraTraOptions.RULE_SEQUENCE);
	}
	
	/*
	 * An element of the vector to return represents a rule layer number.
	 */
	public Vector<String> getLayers() {
		final RuleLayer layers = new RuleLayer(this.itsRules);
		final Map<Integer, HashSet<Rule>> invRuleLayers = layers.invertLayer();
		Integer currentLayer = layers.getStartLayer();
		final OrderedSet<Integer> ruleLayers = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (final Iterator<Integer> en = invRuleLayers.keySet().iterator(); en.hasNext();) {
			ruleLayers.add(en.next());
		}
		int i=0;
		final Vector<String> v = new Vector<String>();
		while (currentLayer != null) {
			v.add(currentLayer.toString());
			
			i++;
			if (i < ruleLayers.size()) {
				currentLayer = ruleLayers.get(i);
			}
			else {
				currentLayer = null;
			}
		}
		return v;
	}

	public Vector<String> getEnabledLayers() {
		final RuleLayer layers = new RuleLayer(this.getEnabledRules());
		final Map<Integer, HashSet<Rule>> invRuleLayers = layers.invertLayer();
		Integer currentLayer = layers.getStartLayer();
		final OrderedSet<Integer> ruleLayers = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (final Iterator<Integer> en = invRuleLayers.keySet().iterator(); en.hasNext();) {
			ruleLayers.add(en.next());
		}
		int i=0;
		final Vector<String> v = new Vector<String>();
		while (currentLayer != null) {
			v.add(currentLayer.toString());
			
			i++;
			if (i < ruleLayers.size()) {
				currentLayer = ruleLayers.get(i);
			}
			else {
				currentLayer = null;
			}
		}
		return v;
	}
	/*
	 * Returns a Vector with String elements. A String represents the priority
	 * number.
	 */
	public Vector<String> getPriorities() {
		final RulePriority priors = new RulePriority(this.itsRules);
		final Map<Integer, HashSet<Rule>> invRulePriors = priors.invertPriority();
		Integer currentPrior = priors.getStartPriority();
		final OrderedSet<Integer> rulePriors = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (final Iterator<Integer> en = invRulePriors.keySet().iterator(); en.hasNext();) {
			rulePriors.add(en.next());
		}
		int i=0;
		final Vector<String> v = new Vector<String>(5);
		while (currentPrior != null) {
			v.add(currentPrior.toString());
			
			i++;
			if (i < rulePriors.size()) {
				currentPrior = rulePriors.get(i);
			}
			else {
				currentPrior = null;
			}
		}
		return v;
	}
	
	public Vector<String> getEnabledPriorities() {
		final RulePriority priors = new RulePriority(this.getEnabledRules());
		final Map<Integer, HashSet<Rule>> invRulePriors = priors.invertPriority();
		Integer currentPrior = priors.getStartPriority();
		final OrderedSet<Integer> rulePriors = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (final Iterator<Integer> en = invRulePriors.keySet().iterator(); en.hasNext();) {
			rulePriors.add(en.next());
		}
		int i=0;
		final Vector<String> v = new Vector<String>(1);
		while (currentPrior != null) {
			v.add(currentPrior.toString());
			
			i++;
			if (i < rulePriors.size()) {
				currentPrior = rulePriors.get(i);
			}
			else {
				currentPrior = null;
			}
		}
		return v;
	}
	

	/**
	 * Reset my host graph by the Graph g. 
	 * The types of the graph g have to be similar of my types.
	 * The given graph g will be added to my list of graphs if it is not already contained.
	 */
	public boolean resetGraph(final Graph g) {
		if (this.typeSet == null || g.getTypeSet() == null) {
			return false;
		}

		if (this.typeSet.compareTo(g.getTypeSet())) {
			// allow to reset its graph		
			int indx = this.itsGraphs.indexOf(this.itsGraph);
				
			if (!this.itsGraphs.contains(g)) {
				this.itsGraphs.remove(this.itsGraph);
				if (indx == -1) {
					this.itsGraphs.add(g);
				}
				else {
					this.itsGraphs.add(indx, g);					
				}
				g.setAttrContext(agg.attribute.impl.AttrTupleManager
						.getDefaultManager().newRightContext(aGraphContext()));
			}			
			this.itsGraph = g;
			this.itsGraph.graphDidChange();
			
			return  true;
		} 
		return false;
	}

	/**
	 * Reset my host graph by the Graph g. The graph g has not to be in the list of my graphs.
	 * The types of the graph g have to be
	 * similar of my types.
	 */
	public boolean resetGraph(int atIndex, final Graph g) {
		if (this.typeSet == null 
				|| g.getTypeSet() == null
				|| atIndex < 0
				|| atIndex >= this.itsGraphs.size()) {
			return false;	
		} 
		if (this.typeSet.compareTo(g.getTypeSet())) {				
			int indx = this.itsGraphs.indexOf(this.itsGraph);
			if (indx == atIndex) {
				if (!this.itsGraphs.contains(g)) {
					this.itsGraphs.remove(atIndex);
					this.itsGraphs.add(atIndex, g);	
					g.setAttrContext(agg.attribute.impl.AttrTupleManager
							.getDefaultManager().newRightContext(aGraphContext()));							
					g.graphDidChange();
					this.itsGraph = g;
				
					return true;	
				}
			}
		} 
		return false;
	}
	
	/** Reset my host graph by my start graph. */
	public boolean resetGraph() {
		boolean result = false;
		if (this.itsGraph == null
				|| this.typeSet == null
				|| this.itsStartGraph == null
				|| !this.typeSet.equals(this.itsStartGraph.getTypeSet())) {
			result = false;
		} else {
			final int indx = this.itsGraphs.indexOf(this.itsGraph);		
			this.itsGraphs.remove(this.itsGraph);
			this.itsGraph.dispose();
			
			this.itsGraph = this.itsStartGraph.copy();
			if (indx == -1) {
				this.itsGraphs.add(this.itsGraph);				
			}
			else {
				this.itsGraphs.add(indx, this.itsGraph);
			}
			
			this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
						.getDefaultManager().newRightContext(aGraphContext()));

			this.itsGraph.graphDidChange();
			result = true;
		} 
		return result;
	}

	/**
	 * Reset my host graph by the Graph g without type guarantee. The types of
	 * the graph g should be similar to my types.
	 */
	public boolean resetGraphWithoutGuarantee(final Graph g) {
		boolean result = true;
		final int indx = this.itsGraphs.indexOf(this.itsGraph);
		if (indx >= 0) {
			this.itsGraphs.remove(this.itsGraph);
		}
		this.itsGraph = g.copy(this.typeSet);
		if (this.itsGraph == null) {
			result = false;
		} else {
			if (indx == -1) {
				this.itsGraphs.add(this.itsGraph);				
			} else {
				this.itsGraphs.add(indx, this.itsGraph);	
			}

			this.itsGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
						.getDefaultManager().newRightContext(aGraphContext()));
		
			this.itsGraph.graphDidChange();
		}
		return result;
	}

	/**
	 * Adds the specified Graph g to the current type graph if it exists or to a
	 * new created type graph otherwise. The Graph g is a type graph,
	 * g.isTypeGraph() should return true. The type graph check should be
	 * disabled. The new node/edge types of the Graph g are added to the current
	 * types. The current type graph structure and the structure of the Graph g
	 * are united dis-jointly. Double occurrence of the nodes/arcs are possible
	 * and they have to be resolved manually by the user.
	 */
	public boolean importTypeGraph(final Graph g, final boolean rewrite) {
		boolean result = false;
		if (g.isTypeGraph()) {			
			if (this.typeSet.getTypeGraph() == null) {
				this.typeSet.createTypeGraph();
			}
			
			if (rewrite) {
				this.typeSet.adaptTypes(g.getTypeSet(), false);
			}

			final Map<ValueTuple, ValueTuple> valueTable = storeAttrValueOfAttrTypeObserver();
			if (this.typeSet.importTypeGraph(g, rewrite)) {				
				this.typeSet.refreshInheritanceArcs();
				// extend the type graph by the node/arc types which are already used in graphs
				// but are not defined in the type graph
//				this.typeSet.extendTypeGraph(g.getNodesSet().iterator(), g.getArcsSet().iterator());
				
				restoreAttrValueOfObserver(valueTable);
				this.typeSet.getTypeGraph().graphDidChange();
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	private Map<ValueTuple, ValueTuple> storeAttrValueOfAttrTypeObserver() {
//		System.out.println("######  storeAttrValueOfAttrTypeObserver......");
		final Map<ValueTuple, ValueTuple> attrStore = new Hashtable<ValueTuple, ValueTuple>();
		final Enumeration<Type> types = this.typeSet.getTypes();
		while (types.hasMoreElements()) {
			final Type t = types.nextElement();
			if (t.getAttrType() != null) {
			
				final DeclTuple dt = (DeclTuple) t.getAttrType();
						
				final Enumeration<?> 
				observers = ((DeclTuple) t.getAttrType()).getObservers();
				while (observers.hasMoreElements()) {
					final Object obs = observers.nextElement();
					if (obs instanceof ValueTuple) {
					
						final ValueTuple valTuple = (ValueTuple) obs;
						if (!valTuple.isEmpty()) {
															
							final ValueTuple vt = new ValueTuple(
									(AttrTupleManager) AttrTupleManager
										.getDefaultManager(), dt,
										(ContextView) valTuple.getContext());

							for (int i = 0; i < valTuple.getSize(); i++) {
								final ValueMember mem = valTuple.getValueMemberAt(i);
								final ValueMember vm = vt.getValueMemberAt(mem.getName());
								if (vm != null && mem.isSet()) {
									vm.setExprAsText(mem.getExprAsText());
//									System.out.println(vm.getName()+" = "+mem.getExprAsText());
								}
							}
					
							attrStore.put((ValueTuple) obs, vt);
						}
					}
				}
			}
		}
		return attrStore;
	}

	private void restoreAttrValueOfObserver(final Map<ValueTuple, ValueTuple> attrStore) {
//		System.out.println("######  restoreAttrValueOfAttrTypeObserver......");
		final Enumeration<Type> types = this.typeSet.getTypes();
		while (types.hasMoreElements()) {
			final Type t = types.nextElement();
			if (t.getAttrType() == null) {
				continue;
			}
			
			final Enumeration<?> observers = ((DeclTuple) t.getAttrType())
					.getObservers();
			while (observers.hasMoreElements()) {
				final Object obs = observers.nextElement();
				if (obs instanceof ValueTuple) {
					final ValueTuple vt = attrStore.get(obs);
					if (vt != null) {
						final ValueTuple valTuple = (ValueTuple) obs;
						for (int i = 0; i < valTuple.getSize(); i++) {
							final ValueMember mem = valTuple.getValueMemberAt(i);
							final ValueMember vm = vt.getValueMemberAt(mem.getName());
							if (vm != null && vm.isSet()) {
//								System.out.println(vm.getName()+" = "+vm.getExprAsText());
								mem.setExprAsText(vm.getExprAsText());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Reset my host graph by the Graph g. The types of the graph objects of the
	 * Graph g have to be similar to my types.
	 */
	public boolean importGraph(final Graph g) {
		boolean importTried = false;
		boolean result = false;
		if ((g.getTypeSet().getTypeGraph() != null) 
				&& ((this.typeSet.getTypeGraph() == null)
						|| (this.typeSet.getTypeGraph().isEmpty() 
								&& (getLevelOfTypeGraphCheck() == TypeSet.DISABLED)))) {
			importTried = true;
			if (importTypeGraph(g.getTypeSet().getTypeGraph(), true)) {
				result = importGraph(g, false);
			}	
			else {
				result = false;
			}
		} 
		if (!importTried) {
			result = importGraph(g, false);
		}
		return result;
	}

	/**
	 * Reset my host graph by the Graph g. If the parameter <code>adapt</code>
	 * is true, the types of the Graph g are tried to be adapted to my types.
	 * The types with the same name are equal. Otherwise, the types of the Graph
	 * g should be found among my types.
	 */
	public boolean importGraph(final Graph g, final boolean adapt) {
		boolean result;
		final Graph impGraph = doImportGraph(g, adapt);
		if (impGraph == null) {
			result = false;
		}
		else {
			final int indx = this.itsGraphs.indexOf(this.itsGraph);
			if (indx == -1) {
				this.itsGraphs.add(impGraph);				
			} else {
				this.itsGraphs.remove(this.itsGraph);
				this.itsGraphs.add(indx, impGraph);
			}

			this.itsGraph = impGraph;
			this.itsGraph.graphDidChange();
			result = true;
		}
		return result;
	}

	/**
	 * Add a copy of the Graph g to the list of my (host) graphs. The types of
	 * the Graph g have to be similar to my types.
	 */
	public boolean addImportGraph(final Graph g) {
		return addImportGraph(g, false);
	}

	/**
	 * Add a copy of the Graph g to the list of my (host) graphs. If the
	 * parameter <code>adapt</code> is true, the types of the Graph g are
	 * tried to be adapted to my types. The types with the same name are equal.
	 * Otherwise, the types of the Graph g should be found among my types.
	 */
	public boolean addImportGraph(final Graph g, final boolean adapt) {
		boolean result;
		final Graph impGraph = doImportGraph(g, adapt);
		if (impGraph == null) {
			result = false;
		}
		else {
			this.itsGraphs.add(impGraph);
			result = true;
		}
		return result;
	}

	private Graph doImportGraph(final Graph g, final boolean adapt) {
		Graph impGraph = null;
		if ((this.typeSet != null) && (g.getTypeSet() != null)) {			
			final String extStr = "_import";
			if (this.typeSet.isEmpty()
					&& adoptTypes(g.getTypeSet().getTypes())) {
				impGraph = g.copy(this.typeSet); 				
			} else if (this.typeSet.contains(g.getTypeSet())) {
				impGraph = g.copy(this.typeSet);
			} else if ((this.typeSet.getTypeGraph() == null)
					|| (this.typeSet.getLevelOfTypeGraphCheck() == TypeSet.DISABLED)) {
				final Vector<Type> typesToAdopt = new Vector<Type>(1);
				final Enumeration<Type> other = g.getTypeSet().getTypes();
				while (other.hasMoreElements()) {
					final Type tOther = other.nextElement();					
					if (!this.typeSet.containsType(tOther)
							&& g.getElementsOfType(tOther).hasMoreElements()) {
						typesToAdopt.add(tOther);
					}
				}
				if (adapt) {
					this.typeSet.adaptTypes(g.getTypeSet().getTypes(), false);
					impGraph = g.copyLight(this.typeSet);
				} else if (adoptTypes(typesToAdopt.elements())) {
					impGraph = g.copy(this.typeSet);
				} 
			} 
			
			if (impGraph != null) {
				impGraph.setName(g.getName() + extStr);					
				impGraph.setAttrContext(agg.attribute.impl.AttrTupleManager
							.getDefaultManager().newRightContext(aGraphContext()));
				
				this.typeSet.extendTypeGraph(g.getNodesSet().iterator(), g.getArcsSet().iterator());
			}
		}
		return impGraph;
	}

	/**
	 * Add a copy of the Rule r to the list of my rules. The types of the Rule r
	 * have to be similar to my types.
	 */
	public boolean addImportRule(final Rule r) {
		return addImportRule(r, false);
	}

	/**
	 * Add a copy of the Rule r to the list of my rules. If the parameter
	 * <code>adapt</code> is true, the types of the Rule r are tried to be
	 * adapted to my types. The types with the same name are similar.
	 * 
	 * Otherwise, the types of the Rule r should be found among my types.
	 * 
	 */
	public boolean addImportRule(final Rule r, final boolean adapt) {
		boolean result = false;
		if (!this.itsRules.contains(r)) {			
			if (adapt) {
				this.typeSet.adaptTypes(r.getTypeSet(), true);
			}		
			final Rule impR = BaseFactory.theFactory().cloneRule(r, this.typeSet, true);
			if (impR != null) {
				this.itsRules.add(impR);
				result = true;
				
				Vector<Node> nodelist = new Vector<Node>(r.getLeft().getNodesSet());
				nodelist.addAll(r.getRight().getNodesSet());
				
				Vector<Arc> edgelist = new Vector<Arc>(r.getLeft().getArcsSet());
				edgelist.addAll(r.getRight().getArcsSet());
				
				for (int i=0; i<r.getNACsList().size(); i++) {
					OrdinaryMorphism m = r.getNACsList().get(i);
					nodelist.addAll(m.getTarget().getNodesSet());
					edgelist.addAll(m.getTarget().getArcsSet());
				}
				for (int i=0; i<r.getPACsList().size(); i++) {
					OrdinaryMorphism m = r.getPACsList().get(i);
					nodelist.addAll(m.getTarget().getNodesSet());
					edgelist.addAll(m.getTarget().getArcsSet());
				}
				
				this.typeSet.extendTypeGraph(nodelist.iterator(), edgelist.iterator());
			}
		}
		return result;
	}

	/*
	 * The specified types contains elements of type Type and used to create new
	 * types which will be added to my types. Returns false, if at least one of
	 * the new types has failed, otherwise true.
	 */
	public boolean adoptTypes(final Enumeration<Type> types) {
		boolean result = true;
		while (types.hasMoreElements()) {
			final Type t = types.nextElement();
			if (this.typeSet.adoptType(t) == null) {
				result = false;
				break;
			}
		}
		return result;
	}

	public void dispose() {			
		this.typeSet.setLevelOfTypeGraph(TypeSet.DISABLED);
		
		if (this.itsRuleSequences != null)
			this.itsRuleSequences.clear();
		
		this.destroyAllMatches();
		
		this.destroyAllRules();
				
		// formulae
		this.destroyAllConstraints();
		
		this.destroyAllAtomics();
		
		this.destroyAllGraphs();
	
		if (this.itsStartGraph != null) {
			this.itsStartGraph.dispose();
		}
		
		this.typeSet.destroyTypeGraph();
		
		this.typeSet.dispose();
		
		this.typeSet = null;
		this.itsGraph = null;
	}
	
	public void finalize() {
	}
	
	private agg.attribute.AttrContext aGraphContext() {
		agg.attribute.AttrContext 
		aGraphCntxt = agg.attribute.impl.AttrTupleManager.getDefaultManager().newContext(
															agg.attribute.AttrMapping.GRAPH_MAP);	
		return aGraphCntxt;
	}
	
	/**
	 * After this method was called:
	 * - the type graph check is set to <code>DISABLED</code>
	 * - existent rule sequences, matches, rules, graphs, graph constraints, type set are empty
	 */
	private void clear() {			
		this.setLevelOfTypeGraphCheck(TypeSet.DISABLED);
		
		if (this.itsRuleSequences != null) {
			this.itsRuleSequences.clear();
			this.itsRuleSequence = null;	
		}
		
		this.destroyAllMatches();		
		this.destroyAllRules();		
		
		// formulae
		this.destroyAllConstraints();		
		this.destroyAllAtomics();
		
		this.destroyAllGraphs();
		
		if (this.itsStartGraph != null) {
			this.itsStartGraph.dispose();
		}
				
		this.typeSet.dispose();
	}
	
	public boolean addRule(final Rule r) {
		boolean result = true;
		if (this.itsRules.contains(r)) {
			result = false;
		} else {
			this.itsRules.add(r);
			this.addMatch(r.getMatch());
		} 
		return result;
	}

	public boolean addRuleAt(int indx, final Rule r) {
		boolean result = true;
		if (this.itsRules.contains(r)) {
			result = false;
		} else {
			this.itsRules.add(indx, r);
			this.addMatch(r.getMatch());
		} 
		return result;
	}
	
	public boolean removeRule(final Rule r) {
		if (this.itsRules.remove(r)) {
			this.refreshRuleSequences();
			return true;
		}
		return false;
	}

	public boolean hasRuleWithApplCond() {
		this.hasRuleApplCond = false;
		for (int i=0; i<this.itsRules.size(); i++) {		
			if (this.itsRules.get(i).getNACsList().size() > 0
					|| this.itsRules.get(i).getPACsList().size() > 0
					|| this.itsRules.get(i).getNestedACsList().size() > 0) {
				this.hasRuleApplCond = true;
				break;
			}
		}
		return this.hasRuleApplCond;
	}
	
	public void removeTypeGraph() {
		this.typeSet.removeTypeGraph();
	}

	/** Set my name. */
	public final void setName(final String n) {
		this.itsName = n;
	}

	/** Return my name. */
	public final String getName() {
		return this.itsName;
	}// getName

	/** Set textual comments for this grammar. */
	public void setTextualComment(final String text) {
		this.comment = text;
	}

	/** Return textual comments of this grammar. */
	public String getTextualComment() {
		return this.comment;
	}

	/**
	 * Sets the directory name for saving the gragra
	 */
	public void setDirName(final String str) {
		this.dirName = str;
	}

	/**
	 * Gets the directory name for saving the gragra
	 */
	public String getDirName() {
		return this.dirName;
	}

	/**
	 * Sets the file name for saving the gragra
	 */
	public void setFileName(final String str) {
		this.fileName = str;
	}

	/**
	 * Gets the file name for saving the gragra
	 */
	public String getFileName() {
		return this.fileName;
	}

	/** Returns my current host graph. */
	public final Graph getGraph() {
		return this.itsGraph;
	}// getGraph

	/** Returns the index of my current host graph. */
	public int getIndexOfGraph() {
		return this.itsGraphs.indexOf(this.itsGraph);
	}

	/** Returns a host graph with the specified name. */
	public final Graph getGraph(final String name) {
		Graph g = null;
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			if (this.itsGraphs.get(i).getName().equals(name)) {
				g = this.itsGraphs.get(i);				
				break;
			} 
		}

		return g;
	}

	/**
	 * Returns a host graph on the specified index or null.
	 */
	public final Graph getGraph(final int indx) {
		Graph g = null;
		if ((indx >= 0) && (indx < this.itsGraphs.size())) {
			g = this.itsGraphs.get(indx);
		}		
		return g;
	}

	/**
	 * Returns a collection of my host graphs.
	 * 
	 * @deprecated  replaced by <code>getListOfGraphs()</code>
	 */
	public final Enumeration<Graph> getGraphs() {
		return this.itsGraphs.elements();
	}

	public final int getCountOfGraphs() {
		return this.itsGraphs.size();
	}
	
	/**
	 * Returns a list of my host graphs.
	 */
	public final List<Graph> getListOfGraphs() {
		return this.itsGraphs;
	}
	
	
	/**
	 * Returns a Vector of my host graphs.
	 * 
	 * @deprecated replaced by <code>getListOfGraphs()</code>
	 */
	public final Vector<Graph> getGraphsVec() {
		return this.itsGraphs;
	}

	/**
	 * Return my start graph. At the beginning the start graph is similar to my
	 * current host graph. In process the host graph will be changed, the start
	 * graph lieves unchanged.
	 */
	public final Graph getStartGraph() {
		return this.itsStartGraph;
	}

	/**
	 * Sets my start graph to Graph g. The type set of the graph g has to be
	 * similar to my type set.<br>
	 * The start graph should not be confused with a host graph. 
	 * The start graph is a copy of a (mostly first) host graph after a grammar loaded.
	 * A host graph is my current work graph. The start graph can be used to overwrite
	 *  my current host graph <code>this.resetGraph()</code>. 
	 */
	public void setStartGraph(final Graph g) {
		if (g == null) {
			destroyStartGraph();
		}
		else {
			if (g.getTypeSet().equals(this.typeSet)) {
				if (this.itsStartGraph != null) {
					this.itsStartGraph.dispose();
				}
				this.itsStartGraph = g;
				// this.itsStartGraph.setName("StartGraph");
			}
		}
	}

	/*
	 * The start graph will be destroyed. The host graph cannot be reset
	 * anymore.
	 */
	public void destroyStartGraph() {
		this.itsStartGraph.dispose();
	}

	
	/**
	 * Returns list of my rules. 
	 * 
	 * @see agg.xt_basis.Rule
	 */
	public final List<Rule> getListOfRules() {
		return this.itsRules;
	}

	public List<Rule> getListOfEnabledRules() {
		List<Rule> list = new Vector<Rule>();
		for (int i=0; i<this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			if (r.isEnabled()) {
				list.add(r);			
			}
		}
		return list;
	}
	
	public void oneRuleHasChangedLayer() {
		this.ruleChangedLayer = true;
	}
	
	public boolean hasRuleChangedLayer() {
		return this.ruleChangedLayer;
	}
	
	public void oneRuleHasChangedPriority() {
		this.ruleChangedPriority = true;
	}
	
	public boolean hasRuleChangedPriority() {
		return this.ruleChangedPriority;
	}
	
	public void oneRuleHasChangedEvailability() {
		this.ruleChangedEvailability = true;
	}
	
	public boolean hasRuleChangedEvailability() {
		return this.ruleChangedEvailability;
	}
	
	/**
	 * Iterate through all of my rules.
	 * 
	 * @see agg.xt_basis.Rule
	 */
	public final Enumeration<Rule> getRules() {
		return this.itsRules.elements();
	}

	public final Iterator<Rule> getRuleIterator() {
		return this.itsRules.iterator();
	}
	
	/** 
	 * @deprecated  replaced by <code>getListOfRules()</code>
	 */
	public final Vector<Rule> getRulesVec() {
		return this.itsRules;
	}

	public Vector<Rule> getRulesForLayer(final int l) {
		final Vector<Rule> v = new Vector<Rule>(5);
		for (int i = 0; i < this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
			if (r.getLayer() == l) {
				v.add(r);
			}
		}
		return v;
	}

	public void enableRuleLayer(final int l, final boolean enabled) {
		for (int i = 0; i < this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
			if (r.getLayer() == l) {
				r.setEnabled(enabled);
			}
		}
	}

	public boolean isRuleLayerEnabled(final int l) {
		boolean result = false;
		for (int i = 0; i < this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
			if (r.getLayer() == l && r.isEnabled()) {
				result = true;
				break;
			}
		}
		return result;
	}

	public final Rule getRule(final int ruleIndx) {
		Rule r = null;
		if (ruleIndx < this.itsRules.size()) {
			r = this.itsRules.get(ruleIndx);
		}		
		return r;
	}

	public final Rule getRule(final String name) {
		for (int i = 0; i < this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			if (r.getRuleScheme() == null) {
				if (r.getName().equals(name)) {
					return r;
				}
			} else {
				r = r.getRuleScheme().getRule(name);
				if (r != null) {
					return r;					
				}				
			}
		}
		return null;
	}

	public final Rule getRuleByQualifiedName(final String name) {
		for (int i = 0; i < this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			if (r.getRuleScheme() == null) {
				if (r.getQualifiedName().equals(name)) {
					return r;
				}
			} else {
				r = r.getRuleScheme().getRuleByQualifiedName(name);
				if (r != null) {
					return r;					
				}				
			}
		}
		return null;
	}
	
	public MorphCompletionStrategy getMorphismCompletionStrategy() {
		return this.strategy;
	}

	public final List<Evaluable> getListOfAtomicObjects() {
		return getAtomicObjects();
	}

	/**
	 * Iterate through all of my atomic graph constraints.
	 * 
	 * @see agg.cons.AtomConstraint
	 */
	public final Enumeration<AtomConstraint> getAtomics() {
		return this.itsAtomics.elements();
	}

	public final AtomConstraint getAtomic(String name) {
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint ac = this.itsAtomics.get(i);
			if (ac.getAtomicName().equals(name))
				return ac;
		}
		return null;
	}
	
	/**
	 * Returns a list of my atomic graph constraints.
	 * 
	 * @see agg.cons.AtomConstraint
	 */
	public final List<AtomConstraint> getListOfAtomics() {
		return this.itsAtomics;
	}
	
	private final List<String> getAtomicNames() {
		final List<String> names = new Vector<String>(this.itsAtomics.size());
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			names.add(this.itsAtomics.get(i).getAtomicName());
		}
		return names;
	}

	private final List<Evaluable> getAtomicObjects() {
		final List<Evaluable> res = new Vector<Evaluable>(this.itsAtomics.size());
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			res.add(this.itsAtomics.get(i));
		}
		return res;
	}

	
	/**
	 * Returns a list of my constraints (formulae). 
	 * 
	 * @see agg.cons.Formula
	 */
	public final List<Formula> getListOfConstraints() {
		return this.itsConstraints;
	}
	
	/**
	 * Iterate through all of my constraints (formulae). 
	 * 
	 * @see agg.cons.Formula
	 */
	public final Enumeration<Formula> getConstraints() {
		return this.itsConstraints.elements();
	}

	/**
	 * Returns all of my constraints (formulae).
	 * 
	 * @see agg.cons.Formula
	 * @deprecated  replaced by <code>getListOfConstraints()</code>
	 */
	public final Vector<Formula> getConstraintsVec() {
		return this.itsConstraints;
	}

	/**
	 * Returns constraints (formulae) that should be satisfied for all layers of
	 * a layered grammar. In case of a non-layered grammar it returns all its
	 * constraints. Vector elements are of type <code>Formula</code>.
	 * 
	 * @see agg.cons.Formula
	 */
	public List<Formula> getGlobalConstraints() {
		return getConstraintsForLayer(-1);
	}

	/**
	 * Returns constraints (formulae) for the specified layer. Vector elements
	 * are of type <code>Formula</code>.
	 * 
	 * @see agg.cons.Formula
	 */
	public List<Formula> getConstraintsForLayer(final int l) {
		final List<Formula> v = new Vector<Formula>(5);
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			final Formula c = this.itsConstraints.get(i);
			final Vector<Integer> layer = c.getLayer();
			if (l == -1 && layer.isEmpty()) {
				v.add(c);
			} else if ((l > -1) && !layer.isEmpty()) {
				for (int j = 0; j < layer.size(); j++) {
					final Integer I = layer.get(j);
					if (I.intValue() == l) {
						v.add(c);
						break;
					}
				}
			}
		}
		return v;
	}

	/**
	 * Returns constraints (formulae) for the specified rule priority. If p is
	 * -1, returns all constraints. Vector elements are of type
	 * <code>Formula</code>.
	 * 
	 * @see agg.cons.Formula
	 */
	public Vector<Formula> getConstraintsForPriority(final int p) {
		Vector<Formula> v = new Vector<Formula>(5);
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			Formula c = this.itsConstraints.get(i);
			Vector<Integer> prior = c.getPriority();
			if (p == -1 && prior.isEmpty()) {
				v.add(c);
			} else if ((p > -1) && !prior.isEmpty()) {
				for (int j = 0; j < prior.size(); j++) {
					Integer I = prior.get(j);
					if (I.intValue() == p) {
						v.add(c);
						break;
					}
				}
			}
		}
		return v;
	}

	private void refreshConstraints() {
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			Formula c = this.itsConstraints.get(i);
			c.getAsString(getAtomicObjects(), getAtomicNames());
		}
	}

	public void refreshConstraintsForLayer() {
		Vector<String> itsLayers = getLayers();
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			Formula c = this.itsConstraints.get(i);
			Vector<Integer> layer = c.getLayer();
			Enumeration<Integer> e = layer.elements();
			while (e.hasMoreElements()) {
				Integer l = e.nextElement();
				boolean found = false;
				for (int j = 0; j < itsLayers.size(); j++) {
					try {
						Integer I = Integer.valueOf(itsLayers.get(j));
						if (I.intValue() == l.intValue()) {
							found = true;
							break;
						}
					} catch (NumberFormatException ex) {}
				}
				if (!found) {
					layer.remove(l);
					e = layer.elements();
				}
			}
		}
	}

	public void refreshConstraintsForPriority() {
		Vector<String> itsPriors = getPriorities();
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			Formula c = this.itsConstraints.get(i);
			Vector<Integer> prior = c.getPriority();
			Enumeration<Integer> e = c.getPriority().elements();
			while (e.hasMoreElements()) {
				Integer p = e.nextElement();
				boolean found = false;
				for (int j = 0; j < itsPriors.size(); j++) {
					try {
						Integer I = Integer.valueOf(itsPriors.get(j));
						if (I.intValue() == p.intValue()) {
							found = true;
							break;
						}
					} catch (NumberFormatException ex) {}
				}
				if (!found) {
					prior.remove(p);
					e = prior.elements();
				}
			}
		}
	}

	/**
	 * Create an empty rule.
	 */
	public Rule createRule() {
		final Rule aNewRule = new Rule(this.typeSet);		
		this.itsRules.add(aNewRule);
		return aNewRule;
	}

	/**
	 * Create a rule scheme with an empty kernel rule and an empty list of multi rules.
	 * Add the new rule scheme at the end of the rule list.
	 * @return	RuleScheme
	 */
	public RuleScheme createRuleScheme() {
		final RuleScheme rs = new RuleScheme("RuleScheme"+String.valueOf(this.itsRules.size()), this.typeSet);
		this.itsRules.add(rs);
		return rs;
	}
	
	/**
	 * Create a rule scheme with a kernel rule as a copy of the specified rule 
	 * and an empty list of multi rules.
	 * Add the new rule scheme after the given rule in case it belongs to this grammar 
	 * or at the end of the rule list.
	 * @return	RuleScheme or null if creation failed
	 */
	public RuleScheme createRuleScheme(final Rule r) {
		final RuleScheme rs = BaseFactory.theBaseFactory.makeRuleScheme(r);
		if (rs != null) {
			int indx = this.itsRules.indexOf(r);
			if (indx >= 0)
				this.itsRules.add(indx+1, rs);
			else
				this.itsRules.add(rs);
		} 
		return rs;
	}
	
	/**
	 * Add the specified rule scheme to the rule list.
	 */
	public boolean addRuleScheme(final RuleScheme rs) {
		boolean result = true;
		if (this.itsRules.contains(rs)) {
			result = false;
		} else {
			this.itsRules.add(rs);
		} 
		return result;
	}
	
	
	
	/**
	 * Sort rules by rule priority.
	 */
	public void sortRulesByPriority() {
		RulePriority priority = new RulePriority(this.itsRules);
		Integer startPriority = priority.getStartPriority();
		Hashtable<Integer, HashSet<Rule>> invertedRulePriority = priority.invertPriority();

		OrderedSet<Integer> rulePrioritySet = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Enumeration<Integer> en = invertedRulePriority.keys(); en
				.hasMoreElements();) {
			rulePrioritySet.add(en.nextElement());
		}
		int i = 0;

		// empty vector of rules
		this.itsRules.clear();
		// iterate by priority
		Integer currentPriority = startPriority;
		boolean nextPriorityExists = true;
		while (nextPriorityExists && (currentPriority != null)) {
			// set current rules
			HashSet<Rule> rulesForPriority = invertedRulePriority.get(currentPriority);
			Iterator<Rule> en = rulesForPriority.iterator();
			while (en.hasNext()) {
				Rule r = en.next();
				// fill vector of rules
				this.itsRules.add(r);
			}
			// set next priority			
			i++;
			if (i < rulePrioritySet.size()) {
				currentPriority = rulePrioritySet.get(i);
			}
			else {
				nextPriorityExists = false;
			}
		}
	}

	/**
	 * Sort rules by layer.
	 */
	public void sortRulesByLayer() {
		RuleLayer layer = new RuleLayer(this.itsRules);
		Integer startLayer = layer.getStartLayer();
		Map<Integer, HashSet<Rule>> invertedRuleLayer = layer.invertLayer();
		OrderedSet<Integer> ruleLayer = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Iterator<Integer> en = invertedRuleLayer.keySet().iterator(); en.hasNext();) {
			ruleLayer.add(en.next());
		}
		int i = 0;
		
		// empty vector of rules
		this.itsRules.clear();
		// iterate by layer
		Integer currentLayer = startLayer;
		boolean nextLayerExists = true;
		while (nextLayerExists && (currentLayer != null)) {
			// set current rules
			HashSet<?> rulesForLayer = invertedRuleLayer.get(currentLayer);
			Iterator<?> en = rulesForLayer.iterator();
			while (en.hasNext()) {
				Rule r = (Rule) en.next();
				// fill vector of rules
				this.itsRules.add(r);
			}
			// set next layer
			i++;
			if (i < ruleLayer.size()) {
				currentLayer = ruleLayer.get(i);
			}
			else {
				nextLayerExists = false;
			}
		}
	}

	/**
	 * Sort constraints (formulae) by layer.
	 */
	public void sortConstraintsByLayer() {
		ConstraintLayer layer = new ConstraintLayer(this.itsConstraints);
		Integer startLayer = layer.getStartLayer();
		Map<Integer, HashSet<Object>> invLayer = layer.invertLayer();
		OrderedSet<Integer> formulaLayer = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Iterator<Integer> en = invLayer.keySet().iterator(); en.hasNext();) {
			formulaLayer.add(en.next());
		}
		int i=0;
		// empty vector of rules
		this.itsConstraints.clear();
		// iterate by layer
		Integer currentLayer = startLayer;
		boolean nextLayerExists = true;
		while (nextLayerExists && (currentLayer != null)) {
			// set current formulae
			HashSet<?> constraintsForLayer = invLayer.get(currentLayer);
			Iterator<?> en = constraintsForLayer.iterator();
			while (en.hasNext()) {
				Formula f = (Formula) en.next();
				// fill vector of formulae
				this.itsConstraints.add(f);
			}
			// set next layer
			i++;
			if (i < formulaLayer.size()) {
				currentLayer = formulaLayer.get(i);
			}
			else {
				nextLayerExists = false;
			}
		}
	}

	/**
	 * Sort constraints (formulae) by priority.
	 */
	public void sortConstraintsByPriority() {
		ConstraintPriority cons = new ConstraintPriority(this.itsConstraints);
		Integer start = cons.getStartPriority();
		Map<Integer, HashSet<Object>> inverted = cons.invertPriority();
		OrderedSet<Integer> set = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Iterator<Integer> en = inverted.keySet().iterator(); en.hasNext();) {
			set.add(en.next());
		}
		int i=0;
		// empty vector of rules
		this.itsConstraints.clear();
		// iterate by layer
		Integer current = start;
		boolean nextExists = true;
		while (nextExists && (current != null)) {
			// set current formulae
			HashSet<?> constraintsForLayer = inverted.get(current);
			Iterator<?> en = constraintsForLayer.iterator();
			while (en.hasNext()) {
				Formula f = (Formula) en.next();
				// fill vector of formulae
				this.itsConstraints.add(f);
			}
			// set next layer
			i++;
			if (i < set.size()) {
				current = set.get(i);
			}
			else {
				nextExists = false;
			}			
		}
	}

	/**
	 * Create a copy of my host graph.
	 */
	public Graph cloneGraph() {
		return this.itsGraph.graphcopy();
	}

	/**
	 * Returns a copy of the Rule <code>r</code>.
	 */
	public Rule cloneRule(final Rule r) {
		Rule aNewRule = BaseFactory.theFactory().cloneRule(r, this.typeSet, true);
		this.itsRules.add(aNewRule);
		return aNewRule;
	}

	/**
	 * Returns an inverse rule of the Rule <code>r</code>.
	 */
	public Rule reverseRule(final Rule r) {
		final Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		inverseRulePair = BaseFactory.theFactory().reverseRule(r);
		if (inverseRulePair != null) {
			Rule invRule = inverseRulePair.first.first;
			r.disposeInverseConstruct();
			return invRule;
		}
		return null;
	}
	
	/**
	 * Returns an inverse rule scheme of the RuleScheme <code>rs</code>.
	 */
	public RuleScheme reverseRuleScheme(final RuleScheme rs) {
		RuleScheme invRS = BaseFactory.theFactory().reverseRuleScheme(rs);
		return invRS;
	}
	
	public AtomConstraint createAtomic(final String name) {
		final AtomConstraint a = (BaseFactory.theFactory())
									.createAtomic(this.typeSet, name);
		this.itsAtomics.add(a);	
		a.setMorphismCompletionStrategy(this.strategy);
		return a;
	}

	public boolean addAtomic(final AtomConstraint ac) {
		boolean result = true;
		if (this.itsAtomics.contains(ac)) {
			result = false;
		} else {
			ac.setMorphismCompletionStrategy(this.strategy);
			this.itsAtomics.add(ac);
		} 
		return result;
	}

	public boolean removeAtomic(final AtomConstraint ac) {
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			this.itsConstraints.get(i).patchOutEvaluable(ac, true);
		}
		
		return this.itsAtomics.remove(ac);
	}

	public Formula createConstraint(final String name) {
//		clearRuleConstraints();
		Formula f = new Formula(true);
		f.setName(name);
		this.itsConstraints.add(f);
		return f;
	}

	public boolean addConstraint(final Formula f) {
		boolean result = true;
		if (this.itsConstraints.contains(f)) {
			result = false;
		} else {		
//			clearRuleConstraints();
			this.itsConstraints.add(f);
		}
		return result;
	}

	/** Dispose the type graph. */
	public void destroyTypeGraph() {
		this.typeSet.removeTypeGraph();		
	}

	/** Dispose the specified rule. */
	public void destroyRule(final Rule rule) {
		if (this.itsRules.remove(rule)) {
			rule.dispose();
		}
	}

	public void destroyAllRules() {
		while (!this.itsRules.isEmpty()) {
			Rule rule = this.itsRules.get(0);
			this.itsRules.remove(0);
			rule.dispose();		
		}
	}

	public void destroyAtomic(final AtomConstraint a) {
		if (this.itsAtomics.remove(a)) {
			clearRuleConstraints(a);
		
			for (int i = 0; i < this.itsConstraints.size(); i++) {
				this.itsConstraints.get(i).patchOutEvaluable(a, true);
			}
			a.dispose(true, true);
		}
	}

	public void destroyAllAtomics() {
		int n = this.itsAtomics.size()-1;
		while(!this.itsAtomics.isEmpty()) {
			AtomConstraint a = this.itsAtomics.remove(n);
			clearRuleConstraints(a);			
			for (int i = 0; i < this.itsConstraints.size(); i++) {
				this.itsConstraints.get(i).patchOutEvaluable(a, true);
			}
			a.dispose();
			n = this.itsAtomics.size()-1;
		}
	}
	
	public void destroyConstraint(final Formula f) {
		if (this.itsConstraints.remove(f)) {
			clearRuleConstraints(f);
		}
	}

	public void destroyAllConstraints() {
		while(!this.itsConstraints.isEmpty()) {
			Formula f =  this.itsConstraints.get(0);
			this.itsConstraints.remove(0);
			clearRuleConstraints(f);
		}		
	}
	
	public Vector<String> destroyGraphObjectsOfType(final Type t,
			boolean fromTypeGraph) {
		Vector<String> failed = new Vector<String>(5);
		// delete from host graphs
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			Graph g = this.itsGraphs.get(i);
			if (!g.destroyObjectsOfType(t)) {
				failed.add(g.getName());
			}
		}
		// delete from rules
		Iterator<Rule> rules = this.itsRules.iterator();
		while (rules.hasNext()) {
			Rule r = rules.next();
			if (!r.destroyObjectsOfType(t)) {
				failed.add(r.getName());
			}
		}
		// delete from atomic graph constraints
		Enumeration<AtomConstraint> atomics = getAtomics();
		while (atomics.hasMoreElements()) {
			AtomConstraint a = atomics.nextElement();
			a.getSource().destroyObjectsOfType(t);
			Enumeration<AtomConstraint> conclusions = a.getConclusions();
			while (conclusions.hasMoreElements()) {
				AtomConstraint c = conclusions.nextElement();
				if (!c.getTarget().destroyObjectsOfType(t)) {
					failed.add(c.getName());
				}
			}
		}
		// delete from type graph
		if (this.typeSet.getTypeGraph() != null 
				&& fromTypeGraph
				&& !this.typeSet.getTypeGraph().destroyObjectsOfType(t)) {
			failed.add(this.typeSet.getTypeGraph().getName());
		}
		return failed;
	}

	/**
	 * 
	 * @param types
	 * @return null if destroy was successful, otherwise a list with failed types
	 */
	public Vector<String> destroyGraphObjectsOfTypes(final Vector<Type> ts,
			final boolean fromTypeGraph) {
		Vector<String> failed = null;

		if (this.itsStartGraph != null) {
			this.itsStartGraph.destroyObjectsOfTypes(ts);
		}
		
		Vector<String> v = null;
		// delete from host graph
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			Graph g = this.itsGraphs.get(i);
			v = g.destroyObjectsOfTypes(ts);
			if (v != null) {
				if (failed == null)
					failed = new Vector<String>(5);
				failed.addAll(v);
			}
		}

		// delete from rules
		Iterator<Rule> rules = this.itsRules.iterator();
		while (rules.hasNext()) {
			Rule r = rules.next();
			v = r.destroyObjectsOfTypes(ts);
			if (!v.isEmpty()) {
				if (failed == null)
					failed = new Vector<String>(5);
				failed.addAll(v);
			}
		}
		
		// delete from atomic graph constraints
		Enumeration<AtomConstraint> atomics = getAtomics();
		while (atomics.hasMoreElements()) {
			AtomConstraint a = atomics.nextElement();
			v = a.getSource().destroyObjectsOfTypes(ts);
			if (v != null) {
				if (failed == null)
					failed = new Vector<String>(5);
				failed.addAll(v);
			}
			Enumeration<AtomConstraint> conclusions = a.getConclusions();
			while (conclusions.hasMoreElements()) {
				AtomConstraint c = conclusions.nextElement();
				v = c.getTarget().destroyObjectsOfTypes(ts);
				if (v != null) {
					if (failed == null)
						failed = new Vector<String>(5);
					failed.addAll(v);
				}
			}
		}
		
		// delete from type graph
		if (this.typeSet.getTypeGraph() != null && fromTypeGraph) {
			v = this.typeSet.getTypeGraph().destroyObjectsOfTypes(ts);
			if (v != null) {
				if (failed == null)
					failed = new Vector<String>(5);
				failed.addAll(v);
			}
		}
		return failed;
	}

	/**
	 * 
	 * @param types
	 * @return null if destroy was successful, otherwise a list with failed types
	 */
	public boolean destroyGraphObjectsOfTypeFromHostGraph(final Type t) {
		// delete from host graph
		return this.itsGraph.destroyObjectsOfType(t);
	}

	/**
	 * 
	 * @param types
	 * @return null if destroy was successful, otherwise a list with failed types
	 */
	public Vector<String> destroyGraphObjectsOfTypesFromHostGraph(
			final Vector<Type> ts) {
		// delete from host graph
		return this.itsGraph.destroyObjectsOfTypes(ts);
	}

	public Vector<String> destroyGraphObjectsOfTypeFromRules(final Type t) {
		Vector<String> failed = new Vector<String>(5);
		// delete from rules
		Iterator<Rule> rules = this.itsRules.iterator();
		while (rules.hasNext()) {
			Rule r = rules.next();
			if (!r.destroyObjectsOfType(t)) {
				failed.add(r.getName());
			}
		}
		return failed;
	}

	public Vector<String> destroyGraphObjectsOfTypesFromRules(final Vector<Type> ts) {
		Vector<String> failed = new Vector<String>(5);
		// delete from rules
		Iterator<Rule> rules = this.itsRules.iterator();
		while (rules.hasNext()) {
			Rule r = rules.next();
			Vector<String> v = r.destroyObjectsOfTypes(ts);
			if (!v.isEmpty()) {
				failed.addAll(v);
			}
		}
		return failed;
	}

	public Vector<String> destroyGraphObjectsOfTypeFromGraphConstraints(final Type t) {
		Vector<String> failed = new Vector<String>(5);
		// delete from atomic graph constraints
		Enumeration<AtomConstraint> atomics = getAtomics();
		while (atomics.hasMoreElements()) {
			AtomConstraint a = atomics.nextElement();
			a.getSource().destroyObjectsOfType(t);
			Enumeration<AtomConstraint> conclusions = a.getConclusions();
			while (conclusions.hasMoreElements()) {
				AtomConstraint c = conclusions.nextElement();
				if (!c.getTarget().destroyObjectsOfType(t)) {
					failed.add(c.getName());
				}
			}
		}
		return failed;
	}

	/**
	 * 
	 * @param types
	 * @return null if destroy was successful, otherwise a list with failed types
	 */
	public Vector<String> destroyGraphObjectsOfTypesFromGraphConstraints(
			final Vector<Type> ts) {
		Vector<String> failed = null;
		// delete from atomic graph constraints
		Enumeration<AtomConstraint> atomics = getAtomics();
		while (atomics.hasMoreElements()) {
			AtomConstraint a = atomics.nextElement();
			Vector<String> v = a.getSource().destroyObjectsOfTypes(ts);
			if (v != null) {
				if (failed == null)
					failed = new Vector<String>(5);
				failed.addAll(v);
			}
			Enumeration<AtomConstraint> conclusions = a.getConclusions();
			while (conclusions.hasMoreElements()) {
				AtomConstraint c = conclusions.nextElement();
				v = c.getTarget().destroyObjectsOfTypes(ts);
				if (v != null) {
					if (failed == null)
						failed = new Vector<String>(5);
					failed.addAll(v);
				}
			}
		}
		return failed;
	}

	/**
	 * Iterate through all of the matches existing between the given rule and
	 * the start graph. Enumeration elements are of type <code>Match</code>.
	 * 
	 * @see agg.xt_basis.Match
	 */
	public final Enumeration<Match> getMatches(Rule rule) {
		Vector<Match> mtchs = new Vector<Match>();
		for (int i = 0; i < this.itsMatches.size(); i++) {
			Match m = this.itsMatches.get(i);
			if (m.getRule().equals(rule)) {
				mtchs.add(m);
			}
		}
		return mtchs.elements();
	}// getMatches

	/**
	 * Iterate through all of the matches existing between the given rule and
	 * all graphs of this grammar. Returns the match for the specified rule and
	 * graph if such is found, otherwise null.
	 */
	public final Match getMatch(final Rule rule, final Graph g) {
		Match m = null;
		for (int i = 0; i < this.itsMatches.size(); i++) {
			if (this.itsMatches.get(i).getRule() == rule
					&& this.itsMatches.get(i).getTarget() == g) {
				m = this.itsMatches.get(i);
			}
		}
		return m;
	}

	/**
	 * Create an empty match morphism between the left hand side of the given rule
	 * and my current host graph. 
	 * Note that this does not yield a valid match 
	 * (unless the left hand side of the given rule is empty), 
	 * because a match has to be a total morphism.
	 */
	public Match createMatch(Rule rule) {
//		if (rule instanceof RuleScheme) {
//			Match m = ((RuleScheme) rule).getMatch(this.itsGraph);
//			rule.setMatch(m);			
//			this.itsMatches.add(m);
//			return m;
//		} 
//		else 
		{
			Match m = new Match(rule, this.itsGraph);
			rule.setMatch(m);		
			this.itsMatches.add(m);
			return m;
		}
	}

	/**
	 * Create an empty match morphism between the left hand side of the given rule
	 * and the given graph. 
	 * Note that this does not yield a valid match 
	 * (unless the left hand side of the given rule is empty), 
	 * because a match has to be a total morphism.<br>
	 * The given Graph g is set to be the current graph of the grammar.
	 */
	public Match createMatch(Rule rule, Graph g) {
		if (this.resetGraph(g)) {
			Match m = new Match(rule, g);
			rule.setMatch(m);		
			this.itsMatches.add(m);
			return m;
		}
		return null;
	}
	
	public void addMatch(final Match m) {
		if (m != null)
			this.itsMatches.add(m);
	}
	
	/** Dispose the specified match. */
	public void destroyMatch(Match match) {
		if (match != null) {
			if (match.getRule() != null)
				match.getRule().setMatch(null);
			this.itsMatches.remove(match);
			match.dispose();
		}
	}

	public void destroyMatches(Graph g) {
		for (int i = 0; i < this.itsMatches.size(); i++) {
			Match match = this.itsMatches.get(i);
			if (match.getTarget() == g) {
				this.itsMatches.remove(match);
				i--;
				if (match.getRule() != null)
					match.getRule().setMatch(null);
				match.dispose();
			}
		}
	}

	public void destroyAllMatches() {
		while (!this.itsMatches.isEmpty()) {
			Match match = this.itsMatches.remove(0);
			if (match != null) {
				if (match.getRule() != null)
					match.getRule().setMatch(null);
				match.dispose();
			}
		}
	}

	/*
	 * Replace its type set by the specified sharedTypes.
	 * Precondition: this GraGra has to contain at most one empty host graph
	 * and one empty rule. Its type set should be empty, too.
	 * 
	 * @param sharedTypes the type set to share
	 * @return true if setting was successful, otherwise - false
	 */
	/*
	private boolean setSharedTypes(final TypeSet sharedTypes) {
		if ((this.getListOfGraphs().size() == 0
				|| (this.getListOfGraphs().size() == 1
						&& this.getListOfGraphs().get(0).isEmpty()))
				&& (this.getListOfRules().size() == 0
						|| (this.getListOfRules().size() == 1
								&& this.getListOfRules().get(0).isEmptyRule()))
				&& this.getListOfAtomics().size() == 0
				&& this.getListOfConstraints().size() == 0) {
			
			this.typeSet = sharedTypes;
			
			if (this.getListOfGraphs().size() == 1) {
				this.getListOfGraphs().get(0).setTypeSet(this.typeSet);
			}
			if (this.getListOfRules().size() == 1) {
				this.getListOfRules().get(0).getLeft().setTypeSet(this.typeSet);
				this.getListOfRules().get(0).getRight().setTypeSet(this.typeSet);
			}
			return true;
		}
		return false;
	}
	*/
	
	/**
	 * Creates an empty type graph. 
	 * If a type graph was already defined, it will be lost. 
	 */
	public Graph createTypeGraph() {
		return this.typeSet.createTypeGraph();
	}
	
	/**
	 * Returns the type graph or <code>null</code>, 
	 * if no type graph was created before.
	 */
	public Graph getTypeGraph() {
		return this.typeSet.getTypeGraph();
	}
	
	/**
	 * Create a new type for typing of GraphObjects.
	 * 
	 * @deprecated  replaced by 
	 * 		<code>Type createNodeType(boolean withAttributes)</code> for node type
	 * and  
	 * 		<code>Type createArcType(boolean withAttributes)</code> for edge type
	 */
	public Type createType() {
		return this.typeSet.createType();
	}
	
	/*
	 * Create a new type for typing of GraphObjects. 
	 * 
	 * @deprecated  replaced by 
	 * 		<code>Type createNodeType(boolean withAttributes)</code> for node type
	 * and  
	 * 		<code>Type createArcType(boolean withAttributes)</code> for edge type
	 */
	public Type createType(boolean withAttributes) {
		return this.typeSet.createType(withAttributes);
	}
	
	/**
	 * Creates a new node type for typing of Node.
	 * @param withAttributes  if true, then attribute type is also created
	 */
	public Type createNodeType(boolean withAttributes) {
		return this.typeSet.createNodeType(withAttributes);
	}
	
	/**
	 * Creates a new arc type for typing of Arc.
	 * @param withAttributes  if true, then attribute type is also created
	 */
	public Type createArcType(boolean withAttributes) {
		return this.typeSet.createArcType(withAttributes);
	}
	
	
	
	/** Returns <code>true</code> if this gragra uses the specified type. */
	public boolean isUsingType(final GraphObject t) {
		boolean result = graphIsUsingType(t)
							|| ruleIsUsingType(t)
							|| constraintIsUsingType(t);
		return result;
	}

	private boolean graphIsUsingType(final GraphObject t) {
		boolean result = false;
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			if (this.itsGraphs.get(i).isUsingType(t)) {
				result = true;
			}
		}
		if (this.itsStartGraph != null) {
			result = result || this.itsStartGraph.isUsingType(t);
		}
		return result;
	}
	
	private boolean ruleIsUsingType(final GraphObject t) {
		boolean result = false;
		for (int i = 0; i < this.itsRules.size(); i++) {
			if (this.itsRules.get(i).isUsingType(t)) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean constraintIsUsingType(final GraphObject t) {
		boolean result = false;
		Iterator<AtomConstraint> e = this.itsAtomics.iterator();
		while (!result && e.hasNext()) {
			AtomConstraint atom = e.next();
			if (atom.getSource().isUsingType(t)
					|| atom.getTarget().isUsingType(t)) {
				result = true;
			}
		}
		return result;
	}
	
	public void refreshAttributed() {
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			this.itsGraphs.get(i).refreshAttributed();
		}
		if (this.itsStartGraph != null) {
			this.itsStartGraph.refreshAttributed();
		}
		for (int i = 0; i < this.itsRules.size(); i++) {
			this.itsRules.get(i).refreshAttributed();
		}
		for (int i=0; i<this.itsAtomics.size(); i++) {
			this.itsAtomics.get(i).refreshAttributed();
		}
	}
	
	/**
	 * returns the type manger used in this gragra and in all its graphs.
	 */
	public TypeSet getTypeSet() {
		return this.typeSet;
	}

	/**
	 * Iterate through all of the types that may be assigned to GraphObjects.
	 * Enumeration elements are of type <code>Type</code>.
	 * 
	 * @see agg.xt_basis.Type
	 */
	public Enumeration<Type> getTypes() {
		return this.typeSet.getTypes();
	}

	/** Dispose the specified type. */
	public void destroyType(Type type) throws TypeException {
		this.typeSet.destroyType(type);
	}

	/**
	 * changes the behavior of the type graph check and defines, how the type
	 * graph is used. The host graph must satisfies the new level, so it is
	 * checked first. If the host graph satisfies the constraints, an empty
	 * collection will be returned and a collection of
	 * {@link agg.xt_basis.TypeError} if there were problems during the test.
	 * 
	 * @param level
	 *            <table>
	 *            <tr>
	 *            <td>{@link agg.xt_basis.TypeSet#DISABLED}</td>
	 *            <td>The type graph will be ignored, so all graphs can contain
	 *            objects with types undefined in the type graph. Multiplicity
	 *            will be also ignored.</td>
	 *            </tr>
	 *            <tr>
	 *            <td>{@link agg.xt_basis.TypeSet#ENABLED}</td>
	 *            <td>The type graph will be basicaly used, so all graphs can
	 *            only contain objects with types defined in the type graph. But
	 *            the multiplicity will not be checked.</td>
	 *            </tr> } *
	 *            <tr>
	 *            <td>{@link agg.xt_basis.TypeSet#ENABLED_MAX}</td>
	 *            <td>The type graph will be basicaly used, so all graphs can
	 *            only contain objects with types defined in the type graph.
	 *            Multiplicities in all graphs should satisfy the defined
	 *            maximum constraints.</td>
	 *            </tr>
	 *            <tr>
	 *            <td>{@link agg.xt_basis.TypeSet#ENABLED_MAX_MIN}</td>
	 *            <td>The type graph is defined and used, so all graphs can only
	 *            contain objects with types defined in the type graph.
	 *            All graphs must satisfy the defined maximum/minimum multiplicity
	 *            constraints of node and edge types.</td>
	 *            </tr>
	 *            </table>
	 */
	public Collection<TypeError> setLevelOfTypeGraphCheck(int level) {
//		System.out.println("GraGra.setLevelOfTypeGraphCheck :: "+level);
		this.multiplErrKind = -1;
		int oldLevel = this.typeSet.getLevelOfTypeGraphCheck();
		// first check the type graph
		Collection<TypeError> checkResult = this.typeSet
				.setLevelOfTypeGraphCheck(level);
		if (checkResult.isEmpty()) {
			// save the errors of more checks
			Vector<TypeError> errors = new Vector<TypeError>();
			
			// the host graphs
			Iterator<Graph> graphIt = this.itsGraphs.iterator();
			while (graphIt.hasNext()) {
				Graph g = graphIt.next();
				if (!g.isEmpty() || level == TypeSet.ENABLED_MAX_MIN)
					errors.addAll(this.typeSet.checkType(g));
			}
			
			if (level != TypeSet.DISABLED) {
				// if (level == TypeSet.ENABLED_MAX){
				// all rules
				Iterator<Rule> ruleIt = this.itsRules.iterator();
				while (ruleIt.hasNext()) {
					Rule r = ruleIt.next();
					if (r.getRuleScheme() == null)
						errors.addAll(this.typeSet.checkType(r));
					else {
						errors.addAll(this.typeSet.checkType(r.getRuleScheme().getKernelRule()));
						for (int i=0; i<r.getRuleScheme().getMultiRules().size(); i++) {
							errors.addAll(this.typeSet.checkType(r.getRuleScheme().getMultiRules().get(i)));
						}
					}
				}

				// all atomics				
				Iterator<AtomConstraint> en = this.itsAtomics.iterator();
				while (en.hasNext()) {
					errors.addAll(this.typeSet.checkType(en.next()));
				}				
			}

			if (!errors.isEmpty()) {
				if (oldLevel == level) {
					oldLevel = TypeSet.ENABLED;
				}
				this.typeSet.setLevelOfTypeGraphCheck(oldLevel);
			}
			// return errors or empty list
			return errors;
		} 
		if (oldLevel == level) {
			oldLevel = TypeSet.DISABLED;
		}
		return checkResult;
		
	}// setLevelOfTypeGraphCheck

	/**
	 * returns the level of type graph usage in the type set.
	 * 
	 * @return <table>
	 *         <tr>
	 *         <td>{@link agg.xt_basis.TypeSet#DISABLED}</td>
	 *         <td>The type graph will be ignored, so all graphs can contain
	 *         objects with types undefined in the type graph. Multiplicity will
	 *         be also ignored.</td>
	 *         </tr>
	 *         <tr>
	 *         <td>{@link agg.xt_basis.TypeSet#ENABLED}</td>
	 *         <td>The type graph will be basicaly used, so all graphs can only
	 *         contain objects with types defined in the type graph. But the
	 *         multiplicity will not be checked.</td>
	 *         </tr>
	 *         <tr>
	 *         <td>{@link agg.xt_basis.TypeSet#ENABLED_MAX}</td>
	 *         <td>The type graph will be basicaly used, so all graphs can only
	 *         contain objects with types defined in the type graph.
	 *         Multiplicities in all graphs should satisfy the defined maximum
	 *         constraints.</td>
	 *         </tr>
	 *         <tr>
	 *         <td>{@link agg.xt_basis.TypeSet#ENABLED_MAX_MIN}</td>
	 *         <td>The type graph will be used, so all graphs can only contain
	 *         objects with types defined in the type graph. Multiplicities in
	 *         all graphs must satisfy the defined maximum constraints and the
	 *         hosting graph must</td>
	 *         </tr>
	 *         </table>
	 */
	public int getLevelOfTypeGraphCheck() {
		return this.typeSet.getLevelOfTypeGraphCheck();
	}// getLevelOfTypeGraphCheck

	public void setMorphismCompletionStrategyOfGraphConstraints() {
		// System.out.println(this.this.strategy);
		// set morphism completion strategy
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint a = this.itsAtomics.get(i);
			a.setMorphismCompletionStrategy(this.strategy);
		}
	}
	
	private int getMultiplicityErrorKind(int currentKind, TypeError error) {
		if ((currentKind != TypeError.TO_MUCH_NODES)
				&& (error.getErrorNumber() == TypeError.TO_MUCH_NODES)) 
			return error.getErrorNumber();
		else if (currentKind == -1)
			return error.getErrorNumber();
		else
			return currentKind;
	}
	
	/**
	 * Checks all graphs of this GraGra due to node type multiplicity 
	 * of the specified type Node of the current type graph.
	 * 
	 * @param typeNode
	 * @return null
	 * 				if all graphs satisfy multiplicity constraint,
	 * 				otherwise - a string with names of failed graphs
	 */
	public String checkNodeTypeMultiplicity(final Node typeNode) {
		int errorkind = -1;
		final List<String> result = new Vector<String>();
		// check graphs
		for (int i=0; i<this.itsGraphs.size(); i++) {
			final Graph graph = this.itsGraphs.get(i);
			TypeError error = this.typeSet.checkNodeTypeMultiplicity(
															typeNode.getType(), 
															graph, 
															this.getLevelOfTypeGraphCheck());
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);			
				result.add(graph.getName());
			} 
//			else {
//				error = graph.checkNodeRequiresArc();
//				errorkind = getMultiplicityErrorKind(errorkind, error);			
//				result.add(graph.getName());
//			}
		}
		// check graphs of rules
		for (int i=0; i<this.itsRules.size(); i++) {
			final Rule rule = this.itsRules.get(i);
			TypeError error = this.typeSet.checkNodeTypeMultiplicity(
					typeNode.getType(), 
					rule.getLeft(), 
					TypeSet.ENABLED_MAX);
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);		
				result.add(rule.getLeft().getName());
			}
			
			error = this.typeSet.checkNodeTypeMultiplicity(
					typeNode.getType(), 
					rule.getRight(), 
					TypeSet.ENABLED_MAX);
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);	
				result.add(rule.getRight().getName());
			}
			// check NACs
			final List<OrdinaryMorphism> nacs = rule.getNACsList();
			for (int l=0; l<nacs.size(); l++) {
				final Graph nacgraph = nacs.get(l).getTarget();
				error = this.typeSet.checkNodeTypeMultiplicity(
						typeNode.getType(), 
						nacgraph, 
						TypeSet.ENABLED_MAX);
				if (error != null) {
					errorkind = getMultiplicityErrorKind(errorkind, error);	
					result.add(nacgraph.getName());
				}
			}
			// check PACs
			final List<OrdinaryMorphism> pacs = rule.getPACsList();
			for (int l=0; l<pacs.size(); l++) {			
				final Graph pacgraph = pacs.get(l).getTarget();
				error = this.typeSet.checkNodeTypeMultiplicity(
						typeNode.getType(), 
						pacgraph, 
						TypeSet.ENABLED_MAX);
				if (error != null) {
					errorkind = getMultiplicityErrorKind(errorkind, error);	
					result.add(pacgraph.getName());
				}
			}
		}
		// check atomic graph constraints
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			final AtomConstraint a = this.itsAtomics.get(i);
			final Graph pgraph = a.getConclusion(0).getSource();
			TypeError error = this.typeSet.checkNodeTypeMultiplicity(
					typeNode.getType(), 
					pgraph, 
					TypeSet.ENABLED_MAX);
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);	
				result.add(pgraph.getName());
			}
			final Enumeration<AtomConstraint> concls = a.getConclusions();
			while (concls.hasMoreElements()) {
				final Graph cgraph = concls.nextElement().getTarget();
				error = this.typeSet.checkNodeTypeMultiplicity(
						typeNode.getType(), 
						cgraph, 
						TypeSet.ENABLED_MAX);
				if (error != null) {
					errorkind = getMultiplicityErrorKind(errorkind, error);	
					result.add(cgraph.getName());
				}
			}
		}
		if (!result.isEmpty()) {
			this.multiplErrKind = errorkind;
		}
		
		return (result.isEmpty())? null: result.toString();
	}
	
	/**
	 * Checks all graphs of this GraGra due to edge type multiplicity 
	 * of the specified type Arc of the current type graph.
	 * 
	 * @param typeArc
	 * @return null
	 * 				if all graphs satisfy multiplicity constraint,
	 * 				otherwise - a string with names of failed graphs
	 */
	public String checkEdgeTypeMultiplicity(final Arc typeArc) {
		int errorkind = -1;
		final List<String> result = new Vector<String>();
		// check graphs
		for (int i=0; i<this.itsGraphs.size(); i++) {
			final Graph graph = this.itsGraphs.get(i);
			TypeError error = this.typeSet.checkEdgeTypeMultiplicity(
										typeArc, 
										graph, 
										this.getLevelOfTypeGraphCheck());
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);	
				result.add(graph.getName());
			}			
		}
		// check graphs of rules
		for (int i=0; i<this.itsRules.size(); i++) {
			final Rule rule = this.itsRules.get(i);
			TypeError error = this.typeSet.checkEdgeTypeMultiplicity(
					typeArc, 
					rule.getLeft(), 
					TypeSet.ENABLED_MAX);
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);	
				result.add(rule.getLeft().getName());
			}
			error = this.typeSet.checkEdgeTypeMultiplicity(
					typeArc, 
					rule.getRight(), 
					TypeSet.ENABLED_MAX);
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);	
				result.add(rule.getRight().getName());
			}
			// check NACs
			final List<OrdinaryMorphism> nacs = rule.getNACsList();
			for (int l=0; l<nacs.size(); l++) {
				final Graph nacgraph = nacs.get(l).getTarget();
				error = this.typeSet.checkEdgeTypeMultiplicity(
						typeArc, 
						nacgraph, 
						TypeSet.ENABLED_MAX);
				if (error != null) {
					errorkind = getMultiplicityErrorKind(errorkind, error);	
					result.add(nacgraph.getName());
				}
			}
			// check PACs
			final List<OrdinaryMorphism> pacs = rule.getPACsList();
			for (int l=0; l<pacs.size(); l++) {
				final Graph pacgraph = pacs.get(l).getTarget();
				error = this.typeSet.checkEdgeTypeMultiplicity(
						typeArc, 
						pacgraph, 
						TypeSet.ENABLED_MAX);
				if (error != null) {
					errorkind = getMultiplicityErrorKind(errorkind, error);	
					result.add(pacgraph.getName());
				}
			}
		}
		// check atomic graph constraints
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			final AtomConstraint a = this.itsAtomics.get(i);
			final Graph pgraph = a.getConclusion(0).getSource();
			TypeError error = this.typeSet.checkEdgeTypeMultiplicity(
					typeArc, 
					pgraph, 
					TypeSet.ENABLED_MAX);
			if (error != null) {
				errorkind = getMultiplicityErrorKind(errorkind, error);	
				result.add(pgraph.getName());
			}
			final Enumeration<AtomConstraint> concls = a.getConclusions();
			while (concls.hasMoreElements()) {
				final Graph cgraph = concls.nextElement().getTarget();
				error = this.typeSet.checkEdgeTypeMultiplicity(
						typeArc, 
						cgraph, 
						TypeSet.ENABLED_MAX);
				if (error != null) {
					errorkind = getMultiplicityErrorKind(errorkind, error);	
					result.add(cgraph.getName());
				}
			}
		}
		if (!result.isEmpty()) {
			this.multiplErrKind = errorkind;
		}
		
		return (result.isEmpty())? null: result.toString();		
	}
	
	public int getMultiplicityErrorKind() {
		return this.multiplErrKind;
	}
	
	/**
	 * This method checks if all graph constraints (formulas) are valid and then
	 * when parameter validity is TRUE if the host graph is consistent.
	 */
	public boolean checkGraphConstraints(boolean validity) {
		boolean all_valid = true;
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint a = this.itsAtomics.get(i);
			a.setMorphismCompletionStrategy(this.strategy);
			if (!a.isValid()) {
				this.consistErrMsg = this.consistErrMsg + "   "
						+ a.getAtomicName() + "   ";
				all_valid = false;
			}
		}
		if (!all_valid) {
			return false;
		}
		
		boolean all_good = true;
		this.consistErrMsg = "";
		Vector<Evaluable> atomics = new Vector<Evaluable>();
		atomics.addAll(this.itsAtomics);
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			Formula f = this.itsConstraints.get(i);
			if (!f.isEnabled()) {
				continue;
			}
			if (!f.isValid()) {				
				// String fn = f.getAsString(this.itsAtomics, getAtomicNames());
				String fn = f.getAsString(atomics, getAtomicNames());
				if (fn.indexOf('[') == -1) {
					this.consistErrMsg = this.consistErrMsg + "   " + f.getName()
					+ " : " + fn.substring(1, fn.length() - 1) + "   ";
				} else {
					this.consistErrMsg = this.consistErrMsg + "   " + f.getName()
							+ " : " + fn.substring(1, fn.length() - 1) + "   ";
				}				
				all_good = false;
			}

			if (!validity && !f.eval(this.itsGraph)) {
				// String fn = f.getAsString(this.itsAtomics, getAtomicNames());
				String fn = f.getAsString(atomics, getAtomicNames());
				if (fn.indexOf('[') == -1) {
					this.consistErrMsg = this.consistErrMsg + "   "
						+ f.getName() + " : "
						+ fn.substring(0, fn.length()) + "   ";
				} else {
					this.consistErrMsg = this.consistErrMsg + "   "
								+ f.getName() + " : "
								+ fn.substring(1, fn.length() - 1) + "   ";
				}					
				all_good = false;
			}
		}
		return all_good;
	}

	/**
	 * Returns TRUE if the graph g satisfies all graph constraints of this graph
	 * grammar. Pre-condition: The graph constraints have to be valid and the
	 * graph g is a graph of the graph set defined with this grammar.
	 */
	public boolean checkGraphConsistency(Graph g) {
		return checkGraphConsistency(g, this.itsConstraints);
	}

	/**
	 * Returns TRUE if the graph g satisfies the specified container with graph
	 * constraints of this graph grammar. If the parameter constraints is null,
	 * all constraints of this grammar should be satisfied. Pre-condition: The
	 * graph constraints have to be valid and the graph g is a graph of the
	 * graph set defined with this grammar.
	 */
	public boolean checkGraphConsistency(Graph g, final List<Formula> constraints) {
//		System.out.println("GraGra.checkGraphConsistency(Graph, Vector constraints");
		this.consistErrMsg = "";
		boolean all_valid = true;
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint a = this.itsAtomics.get(i);
			a.setMorphismCompletionStrategy(this.strategy);
			if (!a.isValid()) {
				this.consistErrMsg = this.consistErrMsg + "   "
						+ a.getAtomicName() + "   ";
				all_valid = false;
			}
		}
		if (!all_valid) {
			return false;
		}
		
		Vector<Evaluable> atomics = new Vector<Evaluable>();
		atomics.addAll(this.itsAtomics);
		for (int i = 0; i < constraints.size(); i++) {
			Formula f = constraints.get(i);
			@SuppressWarnings("unused")
			String fn = f.getAsString(atomics, getAtomicNames());
			if (!f.isEnabled()) {
				continue;
			} else if (!f.isValid()) {
				this.consistErrMsg = this.consistErrMsg + "   "
				+ f.getName() + "   ";
				return false;
			} else if (!f.isEvaluable()) {
				this.consistErrMsg = this.consistErrMsg + "   "
				+ f.getName() + "   ";
				return true;
			} else if (!f.eval(g)) {
				this.consistErrMsg = this.consistErrMsg + "   "
				+ f.getName() + "   ";
				
				return false;
			}
		}
		return true;
	}

	public boolean checkAtomics(boolean checkAtomicValidityOnly) {
		this.consistErrMsg = "";
		boolean all_valid = true;
		boolean all_satisfied = true;
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint a = this.itsAtomics.get(i);
			a.setMorphismCompletionStrategy(this.strategy);
			if (!a.isValid()) {
				this.consistErrMsg = this.consistErrMsg + "   "
						+ a.getAtomicName() + "   ";
				all_valid = false;
			}

			if (all_valid && !checkAtomicValidityOnly && !a.eval(this.itsGraph)) {
				this.consistErrMsg = this.consistErrMsg + "   "
							+ a.getAtomicName() + "   ";
				all_satisfied = false;				
			}
		}

		if (checkAtomicValidityOnly) {
			return all_valid;
		}
		
		return all_satisfied;
		
	}

	public String getConsistencyErrorMsg() {
		return this.consistErrMsg;
	}

	
	/**
	 * Converts all constraints (formulas) to post application conditions for
	 * all rules. Returns empty message if all atomic graph constraints are
	 * valid and converting for each rule was successful, otherwise - error
	 * message.
	 */
	public String convertConstraints() {
		String msg = "";
		if (this.itsAtomics.size() <= 0 || this.itsConstraints.size() <= 0) {
			msg = "Atomics or constraints do not exist.";
			return msg;
		}
		Vector<Rule> rs = new Vector<Rule>();
		rs.addAll(this.itsRules);
		
		for (int i = 0; i < rs.size(); i++) {
			// check atomics
			for (int j = 0; j < this.itsAtomics.size(); j++) {
				AtomConstraint a = this.itsAtomics.get(j);
				if (!a.isValid()) {
					msg = "Atomic  \"" + a.getAtomicName() + "\"  is not valid";
					return msg;
				}
			}

			// convert
			Rule r = rs.get(i);
			r.clearConstraints();
			r.setUsedFormulas(this.itsConstraints);
			String msg0 = r.convertUsedFormulas();
			if (!"".equals(msg0)) {
				msg = "Rule  \""
						+ r.getName()
						+ "\" : \n"
						+ msg0
						+ "\nConverting constraints to post application conditions failed.";
				return msg;
			}
		}
		return msg;
	}

	/**
	 * Clear such post application constraints of rules which contain the specified atomic graph constraint. 
	 */
	public void clearRuleConstraints(AtomConstraint ac) {
		Iterator<Rule> en = this.itsRules.iterator();
		while (en.hasNext()) {
			en.next().clearConstraints(ac);
		}
	}
	
	/**
	 * Clear such post application constraints of rules which contain the specified formula. 
	 */
	public void clearRuleConstraints(Formula f) {
		Iterator<Rule> en = this.itsRules.iterator();
		while (en.hasNext()) {
			en.next().clearConstraints(f);
		}
	}
	
	/**
	 * Clear all post application constraints of all rules. 
	 */
	public void clearRuleConstraints() {
		Iterator<Rule> en = this.itsRules.iterator();
		while (en.hasNext()) {
			en.next().clearConstraints();
		}
	}


	/** Sets my rule layer */
	public void setRuleLayer(final RuleLayer rl) {
		for (Iterator<Rule> keys = this.getListOfRules().iterator(); keys.hasNext();) {
			Object key = keys.next();
			Integer layer = rl.getRuleLayer().get(key);
			if (layer != null) {
				((Rule) key).setLayer(layer.intValue());
			}
		}
	}

	public void unsetLayerTriggerRule() {
		for (int i = 0; i < this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			r.setTriggerForLayer(false);
		}
	}

	public Vector<String> getGraTraOptions() {
		return this.gratraOptions;
	}

	public void setGraTraOptions(Vector<String> opts) {
		this.gratraOptions.clear();
		this.gratraOptions.addAll(opts);
	}
	
	private void initMorphismCompletionStrategy() {
		this.strategy = CompletionStrategySelector.getDefault();
		this.strategy.setRandomisedDomain(true);
		
		this.gratraOptions.add(GraTraOptions.CSP);
		this.gratraOptions.add(GraTraOptions.INJECTIVE);
		this.gratraOptions.add(GraTraOptions.DANGLING);
		this.gratraOptions.add(GraTraOptions.IDENTIFICATION);
		this.gratraOptions.add(GraTraOptions.NACS);
		this.gratraOptions.add(GraTraOptions.PACS);
		this.gratraOptions.add(GraTraOptions.GACS);
	}
	
	/**
	 * set morphism completion strategy based on loaded gratra options.
	 */
	private void setMorphismCompletionStrategy() {
		// set strategy
		if (this.gratraOptions.contains(GraTraOptions.CSP)) {
			this.strategy = new Completion_NAC(new Completion_InjCSP());
		}
		else if (this.gratraOptions.contains("CSP w/o BJ")) {
			this.strategy = new Completion_NAC(new Completion_CSP_NoBJ()); 
		}

		if (this.strategy == null) {
			initMorphismCompletionStrategy();
		} else {
			// set this.strategy properties
			if (!this.gratraOptions.contains(GraTraOptions.INJECTIVE)) {
				this.strategy.getProperties()
						.clear(CompletionPropertyBits.INJECTIVE);
			}
			if (!this.gratraOptions.contains(GraTraOptions.DANGLING)) {
				this.strategy.getProperties()
						.clear(CompletionPropertyBits.DANGLING);
			}
			if (!this.gratraOptions.contains(GraTraOptions.IDENTIFICATION)) {
				this.strategy.getProperties()
						.clear(CompletionPropertyBits.IDENTIFICATION);
			}
			if (!this.gratraOptions.contains(GraTraOptions.NACS)) {
				this.strategy.getProperties()
						.clear(CompletionPropertyBits.NAC);
			}
			if (!this.gratraOptions.contains(GraTraOptions.PACS)) {
				this.strategy.getProperties()
						.clear(CompletionPropertyBits.PAC);
			}
			if (!this.gratraOptions.contains(GraTraOptions.GACS)) {
				this.strategy.getProperties()
						.clear(CompletionPropertyBits.GAC);
			}
			if (this.gratraOptions.contains(GraTraOptions.DETERMINED_CSP_DOMAIN)) {
				this.strategy.setRandomisedDomain(false);
			}
		}

		setMorphismCompletionStrategyOfGraphConstraints();
	}

	public void addGraTraOption(String opt) {
		if (!this.gratraOptions.contains(opt)) {
			this.gratraOptions.add(opt); 
			if (opt.equals(GraTraOptions.DETERMINED_CSP_DOMAIN))
				this.strategy.setRandomisedDomain(false);
			else
				this.strategy.setProperty(opt);
			
//			this.strategy.showProperties();	
		}
	}

	public void removeGraTraOption(String opt) {
		if (this.gratraOptions.contains(opt)) {
			this.gratraOptions.remove(opt);	
			if (opt.equals(GraTraOptions.DETERMINED_CSP_DOMAIN))
				this.strategy.setRandomisedDomain(true);
			else
				this.strategy.removeProperty(opt);
			
//			this.strategy.showProperties();	
		}
	}

	/**
	 * Set morphism completion strategy by the specified strategy.
	 */
	public void setMorphismCompletionStrategy(MorphCompletionStrategy strat) {
		setGraTraOptions(strat);
	}
	
	/**
	 * Set morphism completion strategy by the specified strategy.
	 */
	public void setGraTraOptions(final MorphCompletionStrategy strat) {
		if (strat == null) {			
			return;
		}
		
		this.gratraOptions.clear();

		this.strategy = strat;
		String stratName = CompletionStrategySelector.getName(this.strategy);
		this.gratraOptions.add(stratName);

		// get match conditions
		BitSet activebits = this.strategy.getProperties();
		for (int j = 0; j < CompletionPropertyBits.BITNAME.length; j++) {
			String bitName = CompletionPropertyBits.BITNAME[j];
			if (activebits.get(j)) {
				this.gratraOptions.add(bitName);
			}
		}
		if (!this.strategy.isRandomisedDomain())
			this.gratraOptions.add(GraTraOptions.DETERMINED_CSP_DOMAIN);
			
//		this.strategy.showProperties();
		
		setMorphismCompletionStrategyOfGraphConstraints();
	}

	
	public boolean compareTo(final GraGra gragra, final boolean transOption) {
		if (gragra == null) {
			return false;
		}
		boolean checkLayer = false;
//		boolean checkPriority = false;
		if (transOption) {
			for (int i = 0; i < this.gratraOptions.size(); i++) {
				String op = this.gratraOptions.get(i);
				if (!gragra.getGraTraOptions().contains(op)) {
					return false;
				}
				// else if(op.equals("layered"))
				// checkLayer = true;
				// else if(op.equals("priority"))
				// checkPriority = true;
			}
		}

		if (!compareRulesTo(gragra, checkLayer)
				|| !compareConstraintsTo(gragra)) {
			return false;
		}
		
		return true;
	}

	public boolean compareTo(GraGra gragra) {
		if (!getName().equals(gragra.getName())) {
			return false;
		}
		// compare type set inclusive type graph
		if (!this.getTypeSet().compareTo(gragra.getTypeSet())) {
			return false;
		}

		// compare graph
		if (!this.itsGraph.compareTo(gragra.getGraph())) {
			return false;
		}
		// compare rules
		Vector<Object> another = new Vector<Object>();
		another.add(gragra.getListOfRules());
		
		if (this.itsRules.size() != another.size()) {
			return false;
		}
		for (int i = 0; i < this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			Rule r1 = gragra.getRule(r.getName());
			if (r1 != null && r.compareTo(r1)) {
				another.remove(r1);
			}
		}
		if (!another.isEmpty()) {
			return false;
		}
		
		// compare atomics
		Enumeration<?> e = gragra.getAtomics();
		another = new Vector<Object>();
		while (e.hasMoreElements()) {
			another.add(e.nextElement());
		}
		if (this.itsAtomics.size() != another.size()) {
			return false;
		}
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint a = this.itsAtomics.get(i);
			for (int j = another.size() - 1; j >= 0; j--) {
				AtomConstraint a1 = (AtomConstraint) another.get(j);
				if (a.compareTo(a1)) {
					another.remove(a1);
					break;
				}
			}
		}
		if (!another.isEmpty()) {
			return false;
		}
		
		// compare formulas
		e = gragra.getConstraints();
		another = new Vector<Object>();
		while (e.hasMoreElements()) {
			another.add(e.nextElement());
		}
		if (this.itsConstraints.size() != another.size()) {
			return false;
		}
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			Formula f = this.itsConstraints.get(i);
			for (int j = another.size() - 1; j >= 0; j--) {
				Formula f1 = (Formula) another.get(j);
				if (f.compareTo(f1)) {
					another.remove(f1);
					break;
				}
			}
		}
		if (!another.isEmpty()) {
			return false;
		}

		return true;
	}

	public boolean compareRulesTo(GraGra gragra, boolean checkLayer) {
		// compare rules
		Vector<Rule> another = new Vector<Rule>();
		another.addAll(gragra.getListOfRules());
		
		if (this.itsRules.size() != another.size()) {
			return false;
		}
		
		Rule r = null;
		for (int i = 0; i < this.itsRules.size(); i++) {
			r = this.itsRules.get(i);
			Rule r1 = gragra.getRule(r.getName());
			if (r1 != null) {
				if (r.compareTo(r1))
					another.remove(r1);
			
				if (checkLayer && r.getLayer() != r1.getLayer()) {
					return false;
				}
			}
		}
		if (!another.isEmpty() && r != null) {
			return false;
		}
		
		return true;
	}

	public boolean compareConstraintsTo(GraGra gragra) {
		// compare atomics
		Vector<AtomConstraint> another = new Vector<AtomConstraint>();
		Enumeration<AtomConstraint> e = gragra.getAtomics();
		while (e.hasMoreElements()) {
			another.add(e.nextElement());
		}
		if (this.itsAtomics.size() != another.size()) {
			return false;
		}
		AtomConstraint a = null;
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			a = this.itsAtomics.get(i);
			for (int j = another.size() - 1; j >= 0; j--) {
				AtomConstraint a1 = another.get(j);
				if (a.compareTo(a1)) {
					another.remove(a1);
					break;
				}
			}
		}
		if (!another.isEmpty() && a != null) {
			return false;
		}
		// compare formulas
		Enumeration<Formula> eF = gragra.getConstraints();
		Vector<Formula> anotherF = new Vector<Formula>();
		while (eF.hasMoreElements()) {
			anotherF.add(eF.nextElement());
		}
		if (this.itsConstraints.size() != anotherF.size()) {
			return false;
		}
		Formula f = null;
		for (int i = 0; i < this.itsConstraints.size(); i++) {
			f = this.itsConstraints.get(i);
			for (int j = anotherF.size() - 1; j >= 0; j--) {
				Formula f1 = anotherF.get(j);
				if (f.compareTo(f1)) {
					anotherF.remove(f1);
					break;
				}
			}
		}
		if (!anotherF.isEmpty() && f != null) {
			return false;
		}
		return true;
	}

	public Vector<Rule> getNonInjectiveRules() {
		final Vector<Rule> result = new Vector<Rule>(1);
		for (int i=0; i<this.itsRules.size(); i++) {		
			Rule r = this.itsRules.get(i);
			if (r.isEnabled() && !r.isInjective()) {
				result.add(r);
			}
		}
		return result;
	}

	public boolean isGraphReadyForTransform() {
		return this.itsGraph.isReadyForTransform();
	}

	/**
	 * Prepares info about rule:
	 * does rule create, delete, change something. 
	 */
	public void prepareRuleInfo() {
		for (int i=0; i<this.itsRules.size(); i++) {		
			this.itsRules.get(i).prepareRuleInfo();			
		}
	}
	
	/**
	 * Checks the types, rules and graph constraints of this grammar.
	 * Prepares infos of rules if <code>prepareRuleInfo</code> is true.
	 * @return Pair object or null. A Pair object contains the failed element of
	 *         the grammar. This element is the Pair.first object which can be
	 *         of type agg.xt_basis.Type, agg.xt_basis.Rule or
	 *         agg.cons.AtomConstraint. The second object is a message of type
	 *         String which is a text about failed object.
	 */
	public Pair<Object, String> isReadyToTransform(boolean prepareRuleInfo) {
		Pair<Object, String> result = this.isReadyToTransform();
		if (result == null) {
			this.prepareRuleInfo();
		}
		return result;
	}
	
	/**
	 * Checks the types, rules and graph constraints of this grammar.
	 * 
	 * @return Pair object or null. A Pair object contains the failed element of
	 *         the grammar. This element is the Pair.first object which can be
	 *         of type agg.xt_basis.Type, agg.xt_basis.Rule or
	 *         agg.cons.AtomConstraint. The second object is a message of type
	 *         String which is a text about failed object.
	 */
	public Pair<Object, String> isReadyToTransform() {
		String msg = "";
		// check attr. types exist
		Enumeration<Type> e = this.typeSet.getTypes();
		while (e.hasMoreElements()) {
			Type t = e.nextElement();
			if (!doesAttrTypeExist(t)) {
				msg = "Not all attribute members of the type :  \""
						+ t.getName() + "\"  are declared correctly.";
				return new Pair<Object, String>(t, msg);
			}
		}
		
		Pair<Object, String> p = checkInheritedAttributesValid();
		if (p != null) {
			return p;
		}
		
		// check graphs
		for (int i=0; i<this.itsGraphs.size(); i++) {
			Graph g = this.itsGraphs.get(i);
			if (!g.isReadyForTransform()) {
				msg = "Graph  \"" + g.getName() + "\" : " + "not all attribute set"; //g.getErrorMsg();
				return new Pair<Object, String>(g, msg);
			}						
		}
		
		// check rules
		for (int i=0; i<this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			if (!r.isReadyToTransform()) {
				msg = "Rule  \"" + r.getName() + "\" : " + r.getErrorMsg();
				return new Pair<Object, String>(r, msg);
			}						
		}
		
		// check atomic graph constraints
		return isGraphConstraintReadyForTransform();
	}

	/**
	 * Checks atomic graph constraints.
	 * 
	 * @return The first failed agg.cons.AtomConstraint object or null, if all
	 *         graph constraints were OK.
	 */
	public Pair<Object, String> isGraphConstraintReadyForTransform() {
		for (int i = 0; i < this.itsAtomics.size(); i++) {
			AtomConstraint a = this.itsAtomics.get(i);			
			if (!a.isValid()) {
				String msg = "Atomic graph constraint  \""
						+ a.getAtomicName()
						+ "\" is not valid. "
						+ "\nPlease check: "
						+ "\n  - graph morphism ( injective and total )  "
						+ "\n  - attribute context ( variable and condition declarations ).";
				return new Pair<Object, String>(a, msg);
			}
		}
		return null;
	}

	/*
	 * Returns a type which contains an attribute member of type does not
	 * exist, otherwise null.
	 */
	public Type doAttrTypesExist() {
		// check attr. types exist
		Enumeration<Type> e = this.typeSet.getTypes();
		while (e.hasMoreElements()) {
			Type t = e.nextElement();
			if (!doesAttrTypeExist(t))
				return t;
		}
		return null;
	}

	/*
	 * Returns false if the specified Type type contains an attribute member
	 * which type does not exist, otherwise true.
	 */
	private boolean doesAttrTypeExist(Type type) {
		if (type.getAttrType() == null) {
			return true;
		}
		boolean isClass = false;
		DeclTuple declTupl = (DeclTuple) type.getAttrType();
		for (int i = 0; i < declTupl.getSize(); i++) {
			DeclMember decl = (DeclMember) declTupl.getMemberAt(i);
			String t = decl.getTypeName();
			if (isPrimitiveType(t)) {
				continue;
			}
			try {
				Class.forName(t);
				isClass = true;
			} catch (ClassNotFoundException ex) {}
			if (isClass) { 
				continue;
			} 
			// construct class name as package+class
			agg.attribute.handler.AttrHandler attrHandlers[] = agg.attribute.impl.AttrTupleManager
						.getDefaultManager().getHandlers();
			for (int h = 0; h < attrHandlers.length; h++) {
				agg.attribute.handler.AttrHandler attrh = attrHandlers[h];
				Vector<String> packs = ((agg.attribute.handler.impl.javaExpr.JexHandler) attrh)
							.getClassResolver().getPackages();
				for (int pi = 0; pi < packs.size(); pi++) {
					String pack = packs.get(pi);
					// check if class exists
					try {
						Class.forName(pack + "." + t);
						isClass = true;
						break;
					} catch (ClassNotFoundException ex) {}
				}
				if (isClass)
					break;
			}
		}
		return true;
	}

	private boolean isPrimitiveType(String t) {
		return "int".equals(t) 
				|| "long".equals(t) 
				|| "short".equals(t)
				|| "double".equals(t) 
				|| "float".equals(t)
				|| "boolean".equals(t)
				|| "char".equals(t) 
				|| "byte".equals(t);
	}

	public Pair<Object, String> checkInheritedAttributesValid() {
		if (this.typeSet.getLevelOfTypeGraphCheck() <= TypeSet.DISABLED
				&& this.typeSet.hasInheritance()) {
			Enumeration<Type> e = this.typeSet.getTypes();
			while (e.hasMoreElements()) {
				Type t = e.nextElement();
				// hier multiple inheritance!!!
				for (int i = 0; i < t.getParents().size(); i++) {
					Type p = t.getParents().get(i);
					AttrType attrType = p.getAttrType();
					if (attrType != null
							&& attrType.getNumberOfEntries() != 0) {
						String msg = "Type  \""
									+ t.getName()
									+ "\" - inheritance conflict: inherited attributes by disabled type graph.";
						return new Pair<Object, String>(t, msg);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returned list contains plain rules and plain multi rules of the rule schemes.
	 * The plain multi rules are used for computing critical pairs of the CPA.
	 * This work is still in progress.
	 * 
	 * @return	extended list of rules
	 */
	public List<Rule> getRulesWithIntegratedMultiRulesOfRuleScheme() {
		final List<Rule> list = new Vector<Rule>();
		for (int i=0; i<this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
			if (r.isEnabled()) {
				if (r.getRuleScheme() == null) {
					list.add(r);
				} else {
					final RuleScheme rs = r.getRuleScheme();
					rs.propagateApplCondsOfKernelToMultiRules();
					for (int j=0; j<rs.getMultiRules().size(); j++) {
						final Rule mRule = rs.getMultiRules().get(j);
						list.add(mRule);	
					}
				} 
			}
		}
		return list;
	}
	
	/**
	 * Returned list contains plain rules and plain kernel and multi rules of the rule schemes.
	 * The plain kernel and multi rules are used for computing critical pairs of the CPA.
	 * This work is still in progress.
	 * 
	 * @return	extended list of rules
	 */
	public List<Rule> getRulesWithIntegratedRulesOfRuleScheme() {
		final List<Rule> list = new Vector<Rule>();
		for (int i=0; i<this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
			if (r.isEnabled()) {
				if (r.getRuleScheme() == null) {
					list.add(r);
				} else {
					final RuleScheme rs = r.getRuleScheme();
					rs.propagateApplCondsOfKernelToMultiRules();
					list.add(rs.getKernelRule());
					for (int j=0; j<rs.getMultiRules().size(); j++) {
						final Rule mRule = rs.getMultiRules().get(j);
						list.add(mRule);	
					}
				} 
			}
		}
		return list;
	}
	
	public void removeShiftedApplConditionsFromMultiRules() {
		for (int k=0; k<this.itsRules.size(); k++) {
			final Rule r = this.itsRules.get(k);
			if (r.getRuleScheme() != null) {
				RuleScheme rs = r.getRuleScheme();
				rs.removeShiftedApplConditionsFromMultiRules();
			}
		}
	}

	public List<Rule> getEnabledRules() {
		final Vector<Rule> v = new Vector<Rule>();
		for (int i = 0; i < this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
			if (r.isEnabled()) {
				v.add(r);
			}
		}
		return v;
	}
	
	/** 
	 * Returns applicable rules due to the specified morphism completion strategy 
	 * and current host graph.
	 */
	public Vector<Rule> getApplicableRules(MorphCompletionStrategy aStrategy) {
		return getApplicableRules(this.itsGraph, aStrategy);
	}

	/** 
	 * Returns applicable rules due to the specified host graph
	 * and morphism completion strategy.
	 */
	public Vector<Rule> getApplicableRules(Graph g,
			MorphCompletionStrategy aStrategy) {
		
		final Vector<Rule> applicableRules = new Vector<Rule>();
		
		if (!this.itsGraphs.contains(g) && !g.isReadyForTransform()) {
			return applicableRules;
		}
				
		for (int i = 0; i < this.itsRules.size(); i++) {
			final Rule r = this.itsRules.get(i);
//			if (r.isReadyToTransform()) 
			{
				if (r.isApplicable(g, aStrategy, false)) {
					r.setApplicable(true);
					applicableRules.add(r);
				} else {
					r.setApplicable(false);
				}
			} 
//			else {
//				r.setApplicable(false);
//			}
		}
		return applicableRules;
	}

	/**
	 * Reset applicability attribute of all rules by <code>true</code>.
	 */
	public void dismissRuleApplicability() {
		for (int i = 0; i < this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			r.setApplicable(true);
		}
	}

	/*
	 * Returns TRUE if the image graph of the Match m satisfies all graph
	 * constraints of this grammar. Pre-condition: The graph constraints have to
	 * be valid and the image graph is a graph of the graph set defined with
	 * this grammar. The match m has to be a valid match.
	 */
//	private boolean checkGraphConsistency(Match m) {
//		boolean result = true;
//		BaseFactory bf = BaseFactory.theFactory();
//		OrdinaryMorphism copy = m.getImage().isoToCopy();
//		copy.getImage().setCompleteGraph(true);
//		OrdinaryMorphism com = m.compose(copy);
//		Match m2 = bf.makeMatch(m.getRule(), com);		
//		if (m2 != null) {
//			m2.adaptAttrContextValues(m.getAttrContext());
//			Step s = new Step();
//			try {
//				OrdinaryMorphism co_match = (OrdinaryMorphism) s.execute(m2,
//						true);
//				if (co_match != null) {
//					if (checkGraphConsistency(m2.getImage()))
//						result = true;
//					else
//						result = false;
//					co_match.dispose();
//				} else
//					result = false;
//			} catch (TypeException e) {
//				result = false;
//			}
//			bf.destroyMatch(m2);
//			m2 = null;
//		} else
//			result = false;
//		com.dispose();
//		copy.dispose(false, true);
//		return result;
//	}

	/*
	private boolean checkGraphConsistency(Match m, final List<Formula> constraints) {
		// if(typeSet.hasInheritance()) return true;
		boolean result = true;
		BaseFactory bf = BaseFactory.theFactory();
		OrdinaryMorphism copy = m.getImage().isomorphicCopy();
		copy.getImage().setCompleteGraph(true);
		OrdinaryMorphism com = m.compose(copy);
		Match m2 = bf.makeMatch(m.getRule(), com);		
		if (m2 == null) {
			result = false;
		} else {
			m2.adaptAttrContextValues(m.getAttrContext());
//			TestStep s = new TestStep();
			try {
				OrdinaryMorphism 
				co_match = (OrdinaryMorphism) TestStep.execute(m2, true);
				if (co_match == null) {
					result = false;
				} else {
					if (isLayered()) {
						List<Formula> commonConstraints = getConstraintsForLayer(-1);
						if (checkGraphConsistency(m2.getImage(),
								commonConstraints)) {
							result = true;
						}
						else {
							result = false;
						}
					}
					if (checkGraphConsistency(m2.getImage(), constraints)) {
						result = true;
					}
					else {
						result = false;
					}
					co_match.dispose();					
				} 
			} catch (TypeException e) {
				result = false;
			}
			m2.dispose();
		} 
		com.dispose();
		copy.dispose(false, true);
		return result;
	}
*/
	
	public void storeUsedClassPackages() {
		this.itsPackages.clear();
		InformationFacade info = DefaultInformationFacade.self();
		// AttrHandler javaHandler = info.getJavaHandler();
		AttrHandler[] attrHandlers = info.getAttrManager().getHandlers();
		for (int i = 0; i < attrHandlers.length; i++) {
			AttrHandler attrHandler = attrHandlers[i];
			Pair<String, List<String>> p = new Pair<String, List<String>>(
					attrHandler.getName(), ((JexHandler) attrHandler)
							.getClassResolver().getPackages());
			addPackage(p);
		}
	}

	public void setUsedClassPackages() {
		// Get an attribute handler
		InformationFacade info = DefaultInformationFacade.self();
		// AttrHandler javaHandler = info.getJavaHandler();
		AttrHandler[] attrHandlers = info.getAttrManager().getHandlers();
		for (int i = 0; i < this.itsPackages.size(); i++) {
			Pair<String, List<String>> p = this.itsPackages.get(i);
			String handler = p.first;
			boolean found = false;
			AttrHandler attrHandler = null;
			Vector<String> hPacks = new Vector<String>(0);
			for (int h = 0; h < attrHandlers.length; h++) {
				attrHandler = attrHandlers[h];
				String hName = attrHandler.getName();
				hPacks = ((JexHandler) attrHandler).getClassResolver()
						.getPackages();
				if (hName.equals(handler)) {
					found = true;
				}
			}
			if (found && attrHandler != null) {
				List<String> packs = p.second;
				for (int j = 0; j < packs.size(); j++) {
					String pack = packs.get(j);
					if (!hPacks.contains(pack)) {
						((JexHandler) attrHandler).appendPackage(pack);
					}
				}
			}
		}
	}

	/**
	 * Set rule subsequences of the current rule sequence.
	 * The first Vector element of a Pair is a rule subsequence.
	 * The first String element of a Pair is a rule name.
	 * The second element of a Pair represents
	 * the iteration count of a rule subsequence or of an individual rule.
	 * The value for the iteration count may be "*" or a decimal > 0.
	 * The current rule sequence can be used for graph transformation.
	 */
	public void setSubsequencesOfCurrentRuleSequence(
			final List<Pair<List<Pair<String, String>>, String>> sequences) {
		this.itsRuleSequence.setSubsequenceList(sequences);	
	}

	public void setCurrentRuleSequence(final RuleSequence seq) {
		if (this.itsRuleSequences.contains(seq)) {
			this.itsRuleSequence = seq;
		}
	}
	
	public RuleSequence getCurrentRuleSequence() {
		return this.itsRuleSequence;
	}
	
	public int getIndexOfCurrentRuleSequence() {
		if (this.itsRuleSequence != null)
			return this.itsRuleSequences.indexOf(this.itsRuleSequence);
		
		return -1;
	}
	
	public void addRuleSequence(final RuleSequence seq) {
		this.itsRuleSequences.add(seq);
		this.itsRuleSequence = seq;		
	}
	
	public void removeRuleSequence(final RuleSequence seq) {
		if (this.itsRuleSequence == seq) {
			this.itsRuleSequence = null;	
		}
		this.itsRuleSequences.remove(seq);
	}
	
	/**
	 * If true, the ApplRuleSequencesGraTra -
	 * a transformation by validated rule sequence - will be started.<br>
	 * Precondition: the current rule sequence exists and its applicability is checked.
	 * 
	 */
	public boolean trafoByApplicableRuleSequence() {
		return (this.itsRuleSequence != null) && this.itsRuleSequence.isTrafoByARS();
	}
	
	public void setTrafoByApplicableRuleSequence(boolean b) {
		if (this.itsRuleSequence != null)
			this.itsRuleSequence.setTrafoByARS(b);
	}
	
	public boolean trafoByRuleSequenceWithObjectFlow() {
		return (this.itsRuleSequence != null) && this.itsRuleSequence.isTrafoByObjFlow();
	}
	
	public void setTrafoByRuleSequenceWithObjectFlow(boolean b) {
		if (this.itsRuleSequence != null)
			this.itsRuleSequence.setTrafoByObjFlow(b);
	}
	
	public void clearRuleSequences() {
		this.itsRuleSequences.clear();
	}
	
	public List<RuleSequence> getRuleSequences() {
		return this.itsRuleSequences;
	}
	
	/**
	 * Returns the rule sequences. 
	 * The first Vector element of a Pair is a rule subsequence.
	 * The first String element of a Pair is a rule name.
	 * The second element of a Pair represents
	 * the iteration count of a rule subsequence or of an individual rule.
	 * The value for the iteration count may be "*" or a decimal.
	 */
	public List<Pair<List<Pair<String, String>>, String>> getRuleSequenceList() {
		if (this.itsRuleSequence != null) {
			return this.itsRuleSequence.getSubSequenceList();
		} 
		return null;
	}

	
	private void refreshRuleSequences() {
		if (this.itsRuleSequences != null) {
			for (int i = 0; i < this.itsRuleSequences.size(); i++) {
				RuleSequence ruleSeq = this.itsRuleSequences.get(i);
				boolean dorefresh = false;
				for (int j=0; j<ruleSeq.getSubSequenceList().size(); j++) {
					Pair<List<Pair<String, String>>, String> grp = ruleSeq.getSubSequenceList().get(j);
					List<Pair<String, String>> grpRules = grp.first;
					for (int k = 0; k < grpRules.size(); k++) {
						Pair<String, String> p = grpRules.get(k);
						String rulename = p.first;
						if (this.getRule(rulename) == null) {
							grpRules.remove(k);
							k--;
							dorefresh = true;
						}			
					}
				}
				if (dorefresh) {
					ruleSeq.refresh();
					ruleSeq.uncheck();
				}
			}
		}
	}

	
	private void updateTypeObjectsMapOfGraphs() {
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			Graph g = this.itsGraphs.get(i);
			g.updateTypeObjectsMap();
		}
	}

	public void createAttrInstanceWhereNeeded() {
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			this.itsGraphs.get(i).createAttrInstanceWhereNeeded();
		}
		for (int i=0; i<this.itsRules.size(); i++) {
			this.itsRules.get(i).createAttrInstanceWhereNeeded();
		}
		for (int i=0; i<this.itsAtomics.size(); i++) {
			this.itsAtomics.get(i).createAttrInstanceWhereNeeded();
		}
		this.itsStartGraph.createAttrInstanceWhereNeeded();
	}
	
	public void createAttrInstanceOfTypeWhereNeeded(final Type t) {
		for (int i = 0; i < this.itsGraphs.size(); i++) {
			this.itsGraphs.get(i).createAttrInstanceOfTypeWhereNeeded(t);
		}
		for (int i=0; i<this.itsRules.size(); i++) {
			this.itsRules.get(i).createAttrInstanceOfTypeWhereNeeded(t);
		}
		for (int i=0; i<this.itsAtomics.size(); i++) {
			this.itsAtomics.get(i).createAttrInstanceOfTypeWhereNeeded(t);
		}	
		this.itsStartGraph.createAttrInstanceOfTypeWhereNeeded(t);
	}
	
	private void initRuleSubsets() {
		if (this.ruleSets != null) {
			this.ruleSets.clear();
		}
		this.ruleSets = new Hashtable<Integer, List<Rule>>();
		Vector<Rule> set = new Vector<Rule>(this.itsRules);
		this.ruleSets.put(Integer.valueOf(1), set);
	}
	
	public void removeRuleSubsets() {
		if (this.ruleSets != null) {
			this.ruleSets.clear();
			this.ruleSets = null;
		}
	}
	
	public void addRuleSubset(List<Rule> set) {
		if (this.ruleSets == null) {
			initRuleSubsets();
		}
		this.ruleSets.put(Integer.valueOf(this.ruleSets.size()), set);
	}
	
	public Hashtable<Integer, List<Rule>> getRuleSubsets() {
		return this.ruleSets;
	}
	
	public void save(String filename) {
		String ggx = ".ggx";
		storeUsedClassPackages();
		String outfileName = "";
		if ("".equals(filename)) {
			outfileName = this.itsName + "_out.ggx";
		} else {
			outfileName = filename;
			if (outfileName.indexOf(ggx) == -1) {
				outfileName = outfileName.concat(ggx);
			}
		}
		if (outfileName.endsWith(ggx)) {
			XMLHelper xmlh = new XMLHelper();
			// outfileName = XMLHelper.replaceGermanSpecialCh(outfileName);
			xmlh.addTopObject(this);
			xmlh.save_to_xml(outfileName);

			File f = new File(outfileName);
			if (f.exists()) {
				this.fileName = f.getName();
				if (f.getParent() == null) {
					this.dirName = "." + File.separator;
				} else {
					this.dirName = f.getParent() + File.separator;
				}				
			}
		}
	}

	public void load(String filename) throws Exception {
		File f = new File(filename);
		if (f.exists()) {
			if (filename.endsWith(".ggx")) {
				XMLHelper h = new XMLHelper();
				/*
				 * if(XMLHelper.hasGermanSpecialCh(filename)){
				 * System.out.println("Read file name exception occurred! "
				 * +"\nMaybe the German umlaut like ä, ö, ü or ß were used. "
				 * +"\nPlease replace it by ae, oe, ue or ss " +"\nand try
				 * again."); return; }
				 */
				
				if (h.read_from_xml(filename)) {
					h.getTopObject(this);				
				
					this.fileName = f.getName();
					if (f.getParent() == null) {
						this.dirName = "." + File.separator;
					} else {
						this.dirName = f.getParent() + File.separator;
					}
					
				} else {
					throw new Exception("File  \"" + filename  
							+ "\"  is not an  AGG  file!");
				}
			} else {
				throw new Exception("File  \"" + filename  
						+ "\"  is not a  \".ggx\"  file!");
			}			
		} else {
			throw new Exception("File  \"" + filename  
					+ "\"  doesn't exist!");
		}
	}

	public void read(String filename) throws Exception {
		File f = new File(filename);
		if (f.exists()) {
			if (filename.endsWith(".ggx")) {
				XMLHelper h = new XMLHelper();
				/*
				 * if(XMLHelper.hasGermanSpecialCh(filename)){
				 * System.out.println("Read file name exception occurred! "
				 * +"\nMaybe the German umlaut like ä, ö, ü or ß were used. "
				 * +"\nPlease replace it by ae, oe, ue or ss " +"\nand try
				 * again."); return; }
				 */
				
				if (h.read_from_xml(filename)) {
					h.getTopObject(this);
	
					this.fileName = f.getName();
					if (f.getParent() != null) {
						this.dirName = f.getParent() + File.separator;
					}
					else {
						this.dirName = "." + File.separator;
					}
	
					setUsedClassPackages();
				} else {
					throw new Exception("File  \"" + filename  
							+ "\"  is not an AGG file!");
				}
			} else {
				throw new Exception("File  \"" + filename  
						+ "\"  is not a  \".ggx\"  file!");
			}
		} else {
			throw new Exception("File  \"" + filename + "\"  does not exist!");
		}
	}

	/**
	 * saves the properties and values of all elements of this gragra in the
	 * open element of the given XMLHelper. If the gragra should also create its
	 * own element use XwriteObject.
	 * 
	 * @param h
	 *            an XMLHelper with an open Element for this gragra
	 * @see agg.xt_basis.GraGra#XwriteObject
	 */
	private void saveXML(XMLHelper h) {
		// save packages
		storeUsedClassPackages();
		if ((this.itsPackages != null) && !this.itsPackages.isEmpty()) {
			for (int i = 0; i < this.itsPackages.size(); i++) {
				Pair<String, List<String>> p = this.itsPackages.get(i);
				String attrHand = p.first;
				h.openSubTag("TaggedValue");
				h.addAttr("Tag", "AttrHandler");
				h.addAttr("TagValue", attrHand);
				List<String> v = p.second;
				for (int j = 0; j < v.size(); j++) {
					String n = v.get(j);
					h.openSubTag("TaggedValue");
					h.addAttr("Tag", "Package");
					h.addAttr("TagValue", n);
					h.close();
				}
				h.close();
			}// for i
		}
		// save options
		for (int i = 0; i < this.gratraOptions.size(); i++) {
			h.openSubTag("TaggedValue");
			h.addAttr("Tag", this.gratraOptions.get(i));
			h.addAttr("TagValue", "true");
			h.close(); // TaggedValue
		}

		// save types and the type graph
		h.openSubTag("TaggedValue");
		h.addAttr("Tag", "TypeGraphLevel");
		switch (this.typeSet.getLevelOfTypeGraphCheck()) {
		case TypeSet.ENABLED:
			h.addAttr("TagValue", "ENABLED");
			break;
		case TypeSet.ENABLED_MAX:
			h.addAttr("TagValue", "ENABLED_MAX");
			break;
		case TypeSet.ENABLED_MAX_MIN:
			h.addAttr("TagValue", "ENABLED_MAX_MIN");
			break;
		case TypeSet.DISABLED:
		default:
			h.addAttr("TagValue", "DISABLED");
		}
		h.close(); // TaggedValue

		// <Types>
		h.openSubTag("Types");

		// a <Type> tag for each type
		h.addEnumeration("", getTypes(), true);

		// a <Graph> tag for the type graph
		// the types must defined here, because the XwriteObject
		// method of Graph use them
		h.addObject("", this.typeSet.getTypeGraph(), true);

		// </Types>
		h.close();

		// save host graphs
		for (int i=0; i<this.itsGraphs.size(); i++) {
			this.itsGraphs.get(i).setKind(GraphKind.HOST);
		}
		
		h.addList("", this.itsGraphs, true);

		// save graph constraints (atomics graph constraints and formulas
		if (!this.itsAtomics.isEmpty()) {
			h.openSubTag("Constraints");
			
			// save atomic constraints
			h.addList("", getListOfAtomics(), true);

			// save formulas
			if (!this.itsConstraints.isEmpty()) {
				Iterator<Formula> en = this.itsConstraints.iterator();
				while (en.hasNext()) {
					Formula f = en.next();

					List<Evaluable> atomics = new Vector<Evaluable>();
					atomics.addAll(getListOfAtomicObjects());
					String s = f.getAsString(atomics);

					h.addObjectSub(f);
					h.peekObject(f, null);
					h.addAttr("f", s);			
					h.close();
				}// while hasMoreElements
			}// if hasMoreElements
			h.close(); // constraints
		}

		// save rules
		for (int i=0; i<this.itsRules.size(); i++) {
			Rule r = this.itsRules.get(i);
			r.getSource().setKind(GraphKind.LHS);
			r.getTarget().setKind(GraphKind.RHS);
			if (r instanceof RuleScheme) {
				((RuleScheme)r).storeIndexOfRuleList(i);
			}
		}
		h.addList("", this.itsRules, true);

		// save matches
		if (!this.itsMatches.isEmpty()) {
			h.openSubTag("Matches");
			for (int i = 0; i < this.itsMatches.size(); i++) {
				Match m = this.itsMatches.get(i);
				if (m != null 
						&& m.getRule() != null
						&& !m.isEmpty()) {
					m.setName("MatchOf_" + m.getRule().getName());
					h.openSubTag("MatchOf");
					h.addObject("Rule", m.getRule(), false);
					h.addObject("", m, true);
					h.close();
				}
			}
			h.close();
		}

		// save rule sequences
		if (this.itsRuleSequences != null && !this.itsRuleSequences.isEmpty()) {
			h.openSubTag("RuleSequences");
			
			for (int i = 0; i < this.itsRuleSequences.size(); i++) {
				final RuleSequence seq = this.itsRuleSequences.get(i);
				h.openSubTag("Sequence");
				h.addAttr("name", seq.getName());
				
				if (seq.isTrafoByARS())
					h.addAttr(RuleSequence.TRAFO_BY_ARS, "true");
				
				if (seq.isTrafoByObjFlow())
					h.addAttr(RuleSequence.TRAFO_BY_OBJECT_FLOW, "true");
				
				if (seq.getGraph() != null) {
					h.openSubTag("Graph");
					h.addObject("id", seq.getGraph(), false);
					h.close();
				}
				
				List<Pair<List<Pair<String, String>>, String>> seqList = seq.getSubSequenceList();
				for (int j = 0; j < seqList.size(); j++) {
					Pair<List<Pair<String, String>>, String> pi = seqList.get(j);
					h.openSubTag("Subsequence");
					
					h.addAttr("iterations", pi.second);
					List<Pair<String, String>> v = pi.first;
					for (int k = 0; k < v.size(); k++) {
						Pair<String, String> pj = v.get(k);
						h.openSubTag("Item");
						h.addAttr("rule", pj.first);
						h.addAttr("iterations", pj.second);
						h.close();
					}					
					h.close();
				}
				
				// write object flow : rule1.RHS -> rule2.LHS			
				if (seq.isObjFlowDefined()) {
					Enumeration<String> keys = seq.getObjectFlow().keys();
					while (keys.hasMoreElements()) {
						String key = keys.nextElement();
						ObjectFlow objFlow = seq.getObjectFlow().get(key);
						h.openSubTag("ObjectFlow");
						h.addAttr("enabled", String.valueOf(seq.isObjFlowEnabled()));
						h.addAttr("index", key);
						if (objFlow.isGraphExtended() && objFlow.getIndexOfOutput() == 0) {								
							h.addAttr("output", ((Graph)objFlow.getSourceOfOutput()).getName());
						} else {
							if (((Rule)objFlow.getSourceOfOutput()).getRuleScheme() == null)
								h.addAttr("output", ((Rule)objFlow.getSourceOfOutput()).getName());
							else {
								h.addAttr("output", 
										((Rule)objFlow.getSourceOfOutput()).getRuleScheme().getName()
										+"."
										+((Rule)objFlow.getSourceOfOutput()).getName());
							}
						}
						if (((Rule)objFlow.getSourceOfInput()).getRuleScheme() == null)
							h.addAttr("input", ((Rule)objFlow.getSourceOfInput()).getName());
						else {
							h.addAttr("input", 
									((Rule)objFlow.getSourceOfInput()).getRuleScheme().getName()
									+"."
									+((Rule)objFlow.getSourceOfInput()).getName());
						}
						
						Enumeration<Object> elems = objFlow.getMapping().keys();
						while (elems.hasMoreElements()) {
							GraphObject o = (GraphObject) elems.nextElement();

							h.openSubTag("Mapping");
							h.addObject("orig", o, false);
							h.addObject("image", (GraphObject) objFlow.getMapping().get(o), false);
							h.close();
						}
						h.close();
					}
				}
				h.close();
			}
			h.close();
		}

		updateTypeObjectsMapOfGraphs();

		// do copy of saved host graph
		int tgl_check = this.typeSet.getLevelOfTypeGraphCheck();
		if (this.typeSet.getLevelOfTypeGraphCheck() == TypeSet.ENABLED_MAX_MIN)
			this.typeSet.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
		this.itsStartGraph = cloneGraph();
		this.typeSet.setLevelOfTypeGraphCheck(tgl_check);

	}// saveXML

	/**
	 * reads the properties and values of all elements of this gragra from the
	 * open element of the given XMLHelper. If the gragra element should be
	 * searched and open use XreadObject.
	 * 
	 * @param h
	 *            an XMLHelper with an open Element for this gragra
	 * @see agg.xt_basis.GraGra#XreadObject
	 */
	private void loadXML(XMLHelper h) {		
		// the level of type graph check
		int loadedLevel = TypeSet.DISABLED;
		boolean tgl_ENABLED_MAX_MIN = false;
		this.gratraOptions.clear();		
		
		String v = "";
		String v1 = "";
		String tag = "";
		boolean taggedvalue = true;
		while (taggedvalue) {
			if (tag.equals("AttrHandler")) {
				v = h.readAttr("TagValue");
				Vector<String> packs = new Vector<String>(0);
				boolean isPackage = true;
				while (isPackage && h.readSubTag("TaggedValue")) {
					String tag1 = h.readAttr("Tag").trim();
					if (tag1.equals("Package")) {
						v1 = h.readAttr("TagValue");
						if (!v1.isEmpty()) {
							packs.add(v1.trim());
						}
						h.close();
					} else {
						isPackage = false;
					}
				}
				packs.remove("genged.alphabet.datatypes");
				packs.remove("com.objectspace.jgl");
				Pair<String, List<String>> 
				p = new Pair<String, List<String>>(v, packs);
				this.itsPackages.add(p);
				h.close();
				setUsedClassPackages();
			}
			
			if (h.readSubTag("TaggedValue")) {
				v = null;
				tag = h.readAttr("Tag").trim();
				v1 = h.readAttr("TagValue");
				if (!v1.isEmpty()) {
					v = v1.trim();
				}
				if (!tag.equals("AttrHandler")) {
					if (tag.equalsIgnoreCase("TypeGraphLevel")) {
						if (v != null && !"".equals(v)) {
							if (v.equalsIgnoreCase("ENABLED")) {
								loadedLevel = TypeSet.ENABLED;
							}
							else if (v.equalsIgnoreCase("ENABLED_MAX")) {
								loadedLevel = TypeSet.ENABLED_MAX;
							}
							else if (v.equalsIgnoreCase("ENABLED_MAX_MIN")) {
								loadedLevel = TypeSet.ENABLED_MAX;
								tgl_ENABLED_MAX_MIN = true;
							} else if (v.equalsIgnoreCase("DISABLED")) {
								loadedLevel = TypeSet.DISABLED;
							}
						} else {
							loadedLevel = TypeSet.DISABLED;
						}
					} else {
//						System.out.println(tag);
						if (v != null && !"".equals(v)) {
							if (v.equalsIgnoreCase("true")) {
								this.gratraOptions.add(tag);
							} 
						} else {
							this.gratraOptions.add(tag);
						}
					}
					h.close();
				}
			} else {
				taggedvalue = false;
			}
		}// while
					
//		long time0 = System.currentTimeMillis();
		boolean attributed = false;
		// read the <Types> tag with the types and the type graph
		if (h.readSubTag("Types")) {
			Type t = null;
			// read all Types
			// first the unspecified types (old format)
			Enumeration<?> en = h.getEnumeration("", null, true, "Type");
			while (en.hasMoreElements()) {
				System.out.println(this.itsName+"   old format  Type  !");
				h.peekElement(en.nextElement());
				t = createType();
				h.loadObject(t);
				h.close();
				if (t.getAttrType() != null
						&& t.getAttrType().getNumberOfEntries() != 0) {
					attributed = true;
				}
			}// while hasMoreElements

			// the node types
			en = h.getEnumeration("", null, true, "NodeType");
			while (en.hasMoreElements()) {
				h.peekElement(en.nextElement());
//				t = createType(false);
				t = createNodeType(false);
				h.loadObject(t);
				h.close();
				if (t.getAttrType() != null
						&& t.getAttrType().getNumberOfEntries() != 0) {
					attributed = true;
				}
				if (t.getAdditionalRepr().equals("")
						|| t.getAdditionalRepr().indexOf("Color") == -1) {
					t.setAdditionalRepr("NODE");
				}
			}// while hasMoreElements

			// the edge type
			en = h.getEnumeration("", null, true, "EdgeType");
			while (en.hasMoreElements()) {
				h.peekElement(en.nextElement());
//				t = createType(false);
				t = createArcType(false);
				h.loadObject(t);
				h.close();
				if (t.getAttrType() != null
						&& t.getAttrType().getNumberOfEntries() != 0) {
					attributed = true;
				}
				if (t.getAdditionalRepr().equals("")
						|| t.getAdditionalRepr().indexOf("Color") == -1) {
					t.setAdditionalRepr("EDGE");
				}
			}// while hasMoreElements

			// now construct the type graph
			// the types used there must have defined before
			if (h.readSubTag("Graph")) {
				h.loadObject(this.typeSet.createTypeGraph());
				h.close();

				// mark type graph as unused first
				this.typeSet.setLevelOfTypeGraph(TypeSet.DISABLED);

				this.getTypeGraph().attributed = attributed;
				this.getTypeGraph().setKind(GraphKind.TG);
			}// if readSubTag("Graph")

			// close the <Types> tag
			h.close();

			this.typeSet.refreshInheritanceArcs();
		}// if readSubTag("Types")

		if (this.typeSet.getTypeGraph() == null) {
			this.typeSet.setLevelOfTypeGraph(TypeSet.DISABLED);
		}
		else if (loadedLevel != TypeSet.DISABLED
				&& loadedLevel != TypeSet.UNDEFINED) {
			this.typeSet.setLevelOfTypeGraph(TypeSet.ENABLED);
		}
//		System.out.println("(Base) Grammar  Types: "
//				+ (System.currentTimeMillis() - time0) + "ms");
		
		// read the graphs at <Graph> tags
//		time0 = System.currentTimeMillis();
		this.itsGraphs.clear();
		List<?> graphList = h.getList("", null, true, "Graph");
		Iterator<?> graphIter = graphList.iterator();
		while (graphIter.hasNext()) {
			h.peekElement(graphIter.next());
			Graph g = createGraph();
			h.loadObject(g);
			h.close();
			g.attributed = attributed;
		}
		if (this.itsGraphs.isEmpty()) {
			this.itsGraphs.add(BaseFactory.theFactory().createGraph(this.typeSet));
		}
		this.itsGraph = this.itsGraphs.get(0);
//		System.out.println("(Base) Grammar  Graphs: "
//				+ (System.currentTimeMillis() - time0) + "ms");
		
		// first try to read Graphconstraint_Atomic and Formula of AGG V1.2.0b
		boolean gcAtomicsLoaded = false;
		// read atomic constraints
		Enumeration<?> en = h.getEnumeration("", null, true,
				"Graphconstraint_Atomic");
		if (en.hasMoreElements()) {
			gcAtomicsLoaded = true;
		}
		while (en.hasMoreElements()) {
			h.peekElement(en.nextElement());
			AtomConstraint ac = createAtomic("");
			h.loadObject(ac);
			h.close();
			ac.getSource().setKind(GraphKind.PREMISE);
			ac.getTarget().setKind(GraphKind.CONCLUSION);
		}
		if (gcAtomicsLoaded) {
			if (h.readSubTag("Constraints")) {
				// read formulas
				en = h.getEnumeration("", null, true, "Formula");
				while (en.hasMoreElements()) {
					h.peekElement(en.nextElement());
					Formula f;
					f = (Formula) h.loadObject(createConstraint(""));
					if (f != null) {
						String s = h.readAttr("f");
						f.setFormula(this.getListOfAtomicObjects(), s);
						s = h.readAttr("name");
						f.setName(s);
					}
					h.close();
				}
				h.close();
			}
		} else {
			// try to read Graphconstraint_Atomic and Formula of current AGG
			// read constraint in from <Constraints> tag
			if (h.readSubTag("Constraints")) {
				// read atomic constraints
				en = h.getEnumeration("", null, true, "Graphconstraint_Atomic");
				while (en.hasMoreElements()) {
					h.peekElement(en.nextElement());
					h.loadObject(createAtomic(""));
					h.close();
				}
				this.itsAtomics.trimToSize();
				// read formulas
				en = h.getEnumeration("", null, true, "Formula");
				while (en.hasMoreElements()) {
					h.peekElement(en.nextElement());
					Formula f;
					f = (Formula) h.loadObject(createConstraint(""));
					if (f != null) {
						String s = h.readAttr("f");
						f.setFormula(getListOfAtomicObjects(), s);
					}
					h.close();
				}
				h.close();
			}
		}

		// read rule from <Rule> tag
//		long time0 = System.currentTimeMillis();
		boolean hasGACs = false;
		List<Rule> rulesWithGC = new Vector<Rule>();
		this.itsRules.clear();
		Enumeration<?> en1 = h.getEnumeration("", null, true, "Rule");
		while (en1.hasMoreElements()) {
			h.peekElement(en1.nextElement());
			Rule r = createRule();
			h.loadObject(r);
			hasGACs = hasGACs || r.hasNestedACs();
			if (!r.getUsedAtomics().isEmpty())
				rulesWithGC.add(r);			
			h.close();
		}		
		Enumeration<?> en2 = h.getEnumeration("", null, true, "RuleScheme");
		while (en2.hasMoreElements()) {
			h.peekElement(en2.nextElement());
			RuleScheme rs = createRuleScheme();
			h.loadObject(rs);
			hasGACs = hasGACs || rs.hasNestedACs();
			h.close();
			
			if ((rs.getStoredIndexOfRuleList() >= 0) 
					&& (rs.getStoredIndexOfRuleList() < this.itsRules.size()-1)) {
				this.itsRules.remove(rs);
				this.itsRules.add(rs.getStoredIndexOfRuleList(), rs);
			}
		}	
		this.itsRules.trimToSize();
		
		if (!hasGACs) {
			this.gratraOptions.add(GraTraOptions.GACS);
		}
//		System.out.println("(Base) Grammar  Rules: "
//				+ (System.currentTimeMillis() - time0) + "ms");
		
		// read match from <Match> tag
		this.itsMatches.clear();
		if (h.readSubTag("Matches")) {
			boolean nextMatch = true;
			while (nextMatch) {
				if (h.readSubTag("MatchOf")) {
					Object obj = h.getObject("Rule", null, false);
					if (obj instanceof Rule) {
						Rule r = (Rule) obj;
						Match m = createMatch(r);
						h.getObject("Match", m, true);
						if (m.getSize() > 0)
							m.setPartialMorphismCompletion(true);
					} 
					h.close();
				} else {
					nextMatch = false;
				}
			}
			h.close();
		}

		
		// read rule sequences
		this.itsRuleSequences.clear();	
		if (h.readSubTag("RuleSequences")) {
			boolean newformat = false;	
			
			while (h.readSubTag("Sequence")) {	
				this.itsRuleSequence = new RuleSequence(this, "RuleSequence");
				this.itsRuleSequences.add(this.itsRuleSequence);
				
				String strName = h.readAttr("name");
				
				if ("true".equals(h.readAttr(RuleSequence.TRAFO_BY_ARS))) {
					this.itsRuleSequence.setTrafoByARS(true);
				}
				
				if ("true".equals(h.readAttr(RuleSequence.TRAFO_BY_OBJECT_FLOW))) {
					this.itsRuleSequence.setTrafoByObjFlow(true);
				}
				
				while (h.readSubTag("Subsequence")) {
					newformat = true;
					Vector<Pair<String, String>> vec = new Vector<Pair<String, String>>();
					String iters = h.readAttr("iterations");
					while (h.readSubTag("Item")) {
						String ruleName = h.readAttr("rule");
						String ruleIters = h.readAttr("iterations");
						vec.add(new Pair<String, String>(ruleName, ruleIters));
						h.close();
					}
					try {
						this.itsRuleSequence.getSubSequenceList().add(new Pair<List<Pair<String, String>>, String>(
								vec, iters));	
					} catch (java.lang.NumberFormatException ex) {}
					
					h.close();
				}
				
				if (newformat) {
					if (h.readSubTag("Graph")) {
						Graph g = (Graph) h.getObject("id", null, false);
						if (g != null) {
							this.itsRuleSequence.setGraph(g);
							this.itsRuleSequence.setCheckAtGraph(true);
						}
						h.close();
					} 
					
					if (!strName.equals(""))
						this.itsRuleSequence.setName(strName);
				
					// read object flow : rule1.RHS -> rule2.LHS			
					while (h.readSubTag("ObjectFlow")) {
						String enabledStr = h.readAttr("enabled");
						if (!"".equals(enabledStr))
							this.itsRuleSequence.enableObjFlow(Boolean.valueOf(enabledStr).booleanValue());
						
						String indxStr = h.readAttr("index");
						String[] indx = indxStr.split(":");
						int indx1 = -1;
						try {
							indx1 = Integer.valueOf(indx[0]).intValue();
						} catch (java.lang.NumberFormatException ex) {}
						int indx2 = -1;
						try {
							indx2 = Integer.valueOf(indx[1]).intValue();
						} catch (java.lang.NumberFormatException ex) {}
							
						String sourceOutputName = h.readAttr("output");
						Object  sourceOutput = this.getRule(sourceOutputName);
						if (sourceOutput != null) {
							// source of output is Rule (RHS), so indx1 != 0
							if (indx1 == 0 && this.itsRuleSequence.getGraph() != null)
								sourceOutput = null;
						} else {
							sourceOutput = this.getGraph(sourceOutputName);
							// source of output is Host Graph, so indx1 = 0
							if (indx1 != 0)
								sourceOutput = null;
						}
							
						String sourceInputName = h.readAttr("input");
						Object  sourceInput = this.getRule(sourceInputName);
						if (sourceInput != null) {
							// source of input is Rule (LHS), so indx1 != 0
							if (indx2 == 0)
								sourceInput = null;
						}
//						System.out.println(sourceOutputName+"  :  "+sourceInputName+"   "+indx1+"  :  "+indx2);
						if (sourceOutput != null && sourceInput != null
								&& indx1 != -1 && indx2 != -1) {
							ObjectFlow objFlow = new ObjectFlow(sourceOutput, sourceInput, indx1, indx2);
								
							while (h.readSubTag("Mapping")) {
								Object o1 = h.getObject("orig", null, false);					
								Object o2 = h.getObject("image", null, false);
								if (o1 != null && o2 != null) {
									objFlow.addMapping(o1, o2);
								}
								h.close();
							}
							if (!objFlow.isEmpty()) {
								this.itsRuleSequence.addObjFlow(objFlow);												
							}
						}
						h.close();
					}

				} else {
					// old format
					String iters = h.readAttr("iterations");
					if (!"".equals(iters)) {
						Vector<Pair<String, String>> vec = new Vector<Pair<String, String>>();
						while (h.readSubTag("Item")) {
							String ruleName = h.readAttr("rule");
							String ruleIters = h.readAttr("iterations");
							vec.add(new Pair<String, String>(ruleName, ruleIters));
							h.close();
						}
						try {
							this.itsRuleSequence.getSubSequenceList().add(new Pair<List<Pair<String, String>>, String>(
									vec, iters));
						} catch (java.lang.NumberFormatException ex) {}
					}
					this.itsRuleSequence.setGraph(this.getGraph());
					this.itsRuleSequence.setCheckAtGraph(true);
				}
				
				h.close();
				
				this.itsRuleSequence.getMatchSequence().reinit(this.itsRuleSequence);
				this.itsRuleSequence.makeFlatSequence();
			}
			h.close();				
			
			// set current sequence
			if (!this.itsRuleSequences.isEmpty()) {
				this.itsRuleSequence = this.itsRuleSequences.get(0);
			}
		}

		
		// set level of type graph check
		if (tgl_ENABLED_MAX_MIN) {
			loadedLevel = TypeSet.ENABLED_MAX_MIN;
		}
		if (loadedLevel == TypeSet.ENABLED_MAX
				|| loadedLevel == TypeSet.ENABLED_MAX_MIN) {
			setLevelOfTypeGraphCheck(loadedLevel);
		}
		else {
			this.typeSet.setLevelOfTypeGraph(loadedLevel);
		}
		
		setMorphismCompletionStrategy();

		refreshConstraints();

		for (int j = 0; j < this.itsAtomics.size(); j++) {
			AtomConstraint a = this.itsAtomics.get(j);
			a.isValid();
		}
		// make post application condition of rule
		for (int i=0; i<rulesWithGC.size(); i++) {
			Rule r = this.itsRules.get(i);
			if (!r.getUsedAtomics().isEmpty()) {
				r.convertUsedFormulas();
			}
		}
		rulesWithGC.clear();
	}

	/**
	 * Save the properties and values of all elements of this gragra in an own
	 * element in the XML file saved by the given XMLHelper.
	 * 
	 * @param h
	 *            an XMLHelper, without an open element for this gragra.
	 * @see agg.xt_basis.GraGra#saveXML
	 */
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("GraphTransformationSystem", this);
		h.addAttr("name", this.itsName);
		h.addAttr("directed", String.valueOf(this.typeSet.isArcDirected()));
		h.addAttr("parallel", String.valueOf(this.typeSet.isArcParallel()));
		if (!"".equals(this.comment)) {
			h.addAttr("comment", this.comment);
		}
		saveXML(h);
		h.close();
	}

	/**
	 * reads the properties and values of all elements of this gragra from an
	 * element called <CODE>&lt;GraphTransformationSystem&gt;</CODE> in the
	 * XML file opened by the given XMLHelper.
	 * 
	 * @param h
	 *            an XMLHelper, with an not yet opened element for this gragra.
	 * @see agg.xt_basis.GraGra#loadXML
	 */
	public void XreadObject(XMLHelper h) {	
		// clear eventually existent grammar elements
		this.clear();
		
		// read the grammar from filename.ggx
		if (h.isTag("GraphTransformationSystem", this)) {
			String str = h.readAttr("name");
			setName(str.replaceAll(" ", ""));
			
			str = h.readAttr("directed");
			if (!"".equals(str)) {
				this.typeSet.setArcDirected(Boolean.valueOf(str).booleanValue());
			}
			
			str = h.readAttr("parallel");
			if (!"".equals(str)) {
				this.typeSet.setArcParallel(Boolean.valueOf(str).booleanValue());
			}
			
			str = h.readAttr("comment");
			if (!"".equals(str)) {
				this.comment = str;
			}
			
			loadXML(h);
			h.close();
	
//			long time0 = System.currentTimeMillis();
			this.isReadyToTransform();
//			System.out.println("(Base) Grammar  isReadyToTransform info check: "
//					+ (System.currentTimeMillis() - time0) + "ms");
			return;
		}
		
//		 try to read the grammar of critical pairs from CPA file ( filename.cpx )
		// which should be renamed to filename.ggx before
		if (h.isTag("CriticalPairs", this)) {
			if (h.readSubTag("GraphTransformationSystem")) {
				String str = h.readAttr("name");
				setName(str.replaceAll(" ", ""));

				str = h.readAttr("comment");
				if (!"".equals(str)) {
					this.comment = str;
				}

				loadXML(h);
				h.close();
				
				this.trimToSize();				
				this.isReadyToTransform();
			}			
		}		
		
	}

		
	/*
	 * Create and return a new subgragra. It is automatically added to my set of
	 * subgragras.
	 * 
	 * @see agg.xt_basis.GraGra#getSubGraGras
	 */
//	public final SubGraGra createSubGraGra() {
//		SubGraGra anSG = new SubGraGra(this);
//		if (itsSubGraGras == null)
//			itsSubGraGras = new Vector<SubGraGra>(5, 1);
//		itsSubGraGras.add(anSG);
//		return anSG;
//	}

	/*
	 * Remove a subgragra from my set of subgragras.
	 * 
	 * @return <code>false</code> iff <code>sg</code> was not an element of
	 *         my set of subgragras.
	 */
//	public final boolean destroySubGraGra(SubGraGra sg) {
//		if (sg != null) {
//			if (itsSubGraGras.remove(sg)) {
//				sg.dispose();
//				return true;
//			}
//		}
//		return false;
//	}

	/*
	 * Return an Enumeration of all of my subgragras (not including myself).
	 * Enumeration elements are of type <code>SubGraGra</code>.
	 */
//	public final Enumeration<SubGraGra> getSubGraGras() {
//		if (itsSubGraGras != null)
//			return itsSubGraGras.elements();
//		else
//			return (new Vector<SubGraGra>(0)).elements();
//	}

}
