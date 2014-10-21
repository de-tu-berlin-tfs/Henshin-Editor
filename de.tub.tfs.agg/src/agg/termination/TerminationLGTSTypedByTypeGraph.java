package agg.termination;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.csp.Completion_InheritCSP;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Rule;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.RulePriority;
import agg.xt_basis.Type;
import agg.xt_basis.RuleLayer;
import agg.xt_basis.Arc;
import agg.xt_basis.TypeGraph;
import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.util.Pair;


/**
 * This class implements termination conditions of Layered Graph Grammar
 * which is typed by a type graph.
 * 
 * @author $Author: olga $
 * @version $Id: TerminationLGTSTypedByTypeGraph.java,v 1.13 2010/09/23 08:26:15 olga Exp $
 */
public class TerminationLGTSTypedByTypeGraph implements TerminationLGTSInterface {

	/** The graph grammar */
	private GraGra grammar;

	private TypeGraph typeGraph;
	
	private List<Rule> listOfRules;
	
	private boolean layered, priority;

	private Vector<Rule> deletionRule;

	private Vector<Rule> nondeletionRule;

	private Vector<Rule> creationRule;

	private Hashtable<Rule, Integer> ruleLayer;

	private Hashtable<GraphObject, Integer> creationLayer;

	private Hashtable<GraphObject, Integer> deletionLayer;

	private boolean generateRuleLayer;

	private Hashtable<Rule, Integer> oldRuleLayer;

	private int maxl;

	private Hashtable<Integer, HashSet<Rule>> invertedRuleLayer;

	private OrderedSet<Integer> orderedRuleLayerSet;

	private Hashtable<Integer, HashSet<Object>> invertedTypeDeletionLayer;

	private OrderedSet<Integer> orderedTypeDeletionLayerSet;

	private Hashtable<Integer, HashSet<Object>> invertedTypeCreationLayer;

	private OrderedSet<Integer> orderedTypeCreationLayerSet;

	private Integer startLayer, startRuleLayer;

	private Vector<Integer> orderedRuleLayer;
	// private Vector<Integer> orderedTypeDeletionLayer;
	// private Vector<Integer> orderedTypeCreationLayer;
	
	private Hashtable<Integer, Pair<Boolean, Vector<Rule>>> resultTypeDeletion;

	private Hashtable<Integer, Pair<Boolean, Vector<Rule>>> resultDeletion;

	private Hashtable<Integer, Pair<Boolean, Vector<Rule>>> resultNonDeletion;

	private Hashtable<Integer, List<String>> errorMsg;
	private Hashtable<Integer, List<String>> errorMsgDeletion1;
	private Hashtable<Integer, List<String>> errorMsgDeletion2;
	private Hashtable<Integer, List<String>> errorMsgNonDeletion;
	
	private Hashtable<Integer, Vector<GraphObject>> deletionType;

	private boolean needCorrection = false;

	/** The error message if termination conditions are not valid */
	private String errMsg;

	/** true if termination conditions are valid */
	private boolean valid;

	
	public TerminationLGTSTypedByTypeGraph() {}

	public void dispose() {
		if (this.grammar != null)
			unsetLayer();
		this.grammar = null;
	}
	
	private void setKind() {
		this.priority = this.grammar.trafoByPriority();
		this.layered = this.grammar.isLayered() || !this.priority;
		this.oldRuleLayer.clear();
		this.saveRuleLayerInto(this.oldRuleLayer);
	}
	
	/**
	 * Initialize a termination layers of the grammar. Initially the termination
	 * conditions are invalid.
	 * 
	 * @param gra
	 *            The graph grammar.
	 */
	public void setGrammar(GraGra gra) {
		if (gra == null) {
			unsetLayer();
			this.grammar = null;
		} else {
			this.grammar = gra;
			this.typeGraph = (TypeGraph) gra.getTypeGraph();
			this.listOfRules = this.getListOfEnabledRules();
			this.errMsg = "";
			this.valid = false;
			this.oldRuleLayer = new Hashtable<Rule, Integer>();			
			setKind();
			initRuleLayer(this.grammar);
			initCreationLayer(this.grammar);
			initDeletionLayer(this.grammar);
			initOrderedRuleLayer(this.grammar);
			this.deletionType = new Hashtable<Integer, Vector<GraphObject>>();
			initResults();
		}
	}

	public void resetGrammar() {
		if (this.grammar != null) {
			this.typeGraph = (TypeGraph) this.grammar.getTypeGraph();			
			this.listOfRules = this.getListOfEnabledRules();
			this.errMsg = "";
			this.valid = false;			
			setKind();
			reinitRuleLayer();
			reinitCreationLayer();
			reinitDeletionLayer();
			reinitOrderedRuleLayer();
			this.deletionType.clear();
			reinitResults();
		}
	}
	
	public GraGra getGrammar() {
		return this.grammar;
	}

	public List<Rule> getListOfEnabledRules() {
		List<Rule> list = new Vector<Rule>();
		for (int i=0; i<this.grammar.getListOfRules().size(); i++) {
			Rule r = this.grammar.getListOfRules().get(i);
			if (r.isEnabled()) {
				list.add(r);			
			}
		}
		return list;
	}
	
	public boolean hasGrammarChanged() {
		if (this.grammar != null) {
			boolean changed = false;
			if (this.grammar.hasRuleChangedEvailability()
					|| (this.layered && this.grammar.trafoByPriority())
					|| (this.priority && this.grammar.isLayered()) 
					|| (this.layered && this.grammar.hasRuleChangedLayer())
					|| (this.priority && this.grammar.hasRuleChangedPriority())
					|| (this.layered && !this.grammar.isLayered()) )  {
				changed = true;
				this.setGrammar(this.grammar);
			}
			
			return changed;
		} 
		return false;
	}
	
	public List<Rule> getListOfRules() {
		return this.listOfRules;
	}
	
	public Hashtable<Integer, HashSet<Rule>> getInvertedRuleLayer() {
		return this.invertedRuleLayer;
	}

	public Vector<Integer> getOrderedRuleLayer() {
		return this.orderedRuleLayer;
	}

	public Hashtable<Integer, HashSet<Object>> getInvertedTypeDeletionLayer() {
		return this.invertedTypeDeletionLayer;
	}

	// public Vector getOrderedTypeDeletionLayer()
	// { return orderedTypeDeletionLayer; }

	public Hashtable<Integer, HashSet<Object>> getInvertedTypeCreationLayer() {
		return this.invertedTypeCreationLayer;
	}

	// public Vector getOrderedTypeCreationLayer()
	// { return orderedTypeCreationLayer; }
	
	public Hashtable<Integer, Vector<Type>> getDeletionType() {
		final Hashtable<Integer, Vector<Type>> delLayerType = new Hashtable<Integer, Vector<Type>>();
		Enumeration<Integer> keys = this.deletionType.keys();
		while (keys.hasMoreElements()) {
			Integer key = keys.nextElement();
			final Vector<GraphObject> typeGOs = this.deletionType.get(key);
			final Vector<Type> typeObjs = new Vector<Type>();
			for (int i=0; i<typeGOs.size(); i++) {
				typeObjs.add(typeGOs.get(i).getType());
			}
			delLayerType.put(key, typeObjs);
		}
		return delLayerType;
	}

	public Hashtable<Integer, Vector<GraphObject>> getDeletionTypeObject() {
		return this.deletionType;
	}
	
	public Hashtable<Integer, Pair<Boolean, Vector<Rule>>> getResultTypeDeletion() {
		return this.resultTypeDeletion;
	}

	public Hashtable<Integer, Pair<Boolean, Vector<Rule>>> getResultDeletion() {
		return this.resultDeletion;
	}

	public Hashtable<Integer, Pair<Boolean, Vector<Rule>>> getResultNondeletion() {
		return this.resultNonDeletion;
	}

	public void resetLayer() {
		this.maxl = 0;		
		setKind();
		initRuleLayer(this.oldRuleLayer);
		initCreationLayer(this.grammar);
		initDeletionLayer(this.grammar);
		initOrderedRuleLayer(this.grammar);
		this.deletionType = new Hashtable<Integer, Vector<GraphObject>>();
		initResults();
	}
	
	private void unsetLayer() {
		this.creationLayer.clear();		
		this.deletionLayer.clear();
		this.deletionType.clear();
		this.deletionRule.clear();
		this.creationRule.clear();
		this.nondeletionRule.clear();
		this.invertedRuleLayer.clear();
//		this.invertedTypeDeletionLayer.clear();
		this.ruleLayer.clear();
		this.oldRuleLayer.clear();
		this.orderedRuleLayerSet.clear();
//		this.orderedTypeDeletionLayerSet.clear();
//		this.invertedTypeCreationLayer.clear();
//		this.orderedTypeCreationLayerSet.clear();
		this.resultDeletion.clear();
		this.resultDeletion.clear();
		this.resultNonDeletion.clear();	
		
		clearErrors();
	}

	private void initRuleLayer(GraGra gragra) {
		this.ruleLayer = new Hashtable<Rule, Integer>();
		this.deletionRule = new Vector<Rule>();
		this.nondeletionRule = new Vector<Rule>();
		this.creationRule = new Vector<Rule>();
		Iterator<Rule> rules = this.listOfRules.iterator();
		while (rules.hasNext()) {
			Rule rule = rules.next();			
				if (this.priority)
					this.ruleLayer.put(rule, Integer.valueOf(rule.getPriority()));
				else 
					this.ruleLayer.put(rule, Integer.valueOf(rule.getLayer()));
				if (isDeleting(rule)) {
					this.deletionRule.add(rule);
//					System.out.println("Deleting rule:  "+rule.getName());
					
//					if (isCreating(rule)) {
//						creationRule.add(rule);
//						System.out.println("Deleting and Creating rule:  "+rule.getName());
//					}
				} else {
					if (isCreating(rule)) {
						this.creationRule.add(rule);
//						System.out.println("Creating rule:  "+rule.getName());
					}
					this.nondeletionRule.add(rule);
				}
		}
	}

	private void reinitRuleLayer() {
		this.ruleLayer.clear();
		this.deletionRule.clear();
		this.nondeletionRule.clear();
		this.creationRule.clear();
		Iterator<Rule> rules = this.listOfRules.iterator();
		while (rules.hasNext()) {
			Rule rule = rules.next();			
			if (this.priority)
				this.ruleLayer.put(rule, Integer.valueOf(rule.getPriority()));
			else if (this.layered)
				this.ruleLayer.put(rule, Integer.valueOf(rule.getLayer()));
			if (isDeleting(rule)) {
				this.deletionRule.add(rule);
//					System.out.println("Deleting rule:  "+rule.getName());					
			} else {
				if (isCreating(rule)) {
					this.creationRule.add(rule);
//						System.out.println("Creating rule:  "+rule.getName());
				}
				this.nondeletionRule.add(rule);
			}
		}
	}
	
	/*
	private void initRuleLayer(int init) {
		for (Enumeration<Rule> keys = ruleLayer.keys(); keys.hasMoreElements();) {
			Rule rule = keys.nextElement();
			if (rule.isEnabled()) {
				ruleLayer.put(rule, Integer.valueOf(init));
			}
		}
	}
*/
	
	public void initRuleLayer(Hashtable<?, Integer> init) {
		for (Enumeration<?> keys = init.keys(); keys.hasMoreElements();) {
			Rule rule = (Rule) keys.nextElement();
			if (rule.isEnabled()) {
				Integer rl = init.get(rule);
				this.ruleLayer.put(rule, Integer.valueOf(rl.intValue()));
			}
		}
	}

	private void initCreationLayer(GraGra gragra) {
		this.creationLayer = new Hashtable<GraphObject, Integer>();
		Iterator<Node> typeNodes = this.typeGraph.getNodesSet().iterator();
		while (typeNodes.hasNext()) {
			Node t = typeNodes.next();
			if (this.startLayer != null)	
				this.creationLayer.put(t, Integer.valueOf(this.startLayer.intValue())); //+1));
			else
				this.creationLayer.put(t, Integer.valueOf(0));
		}
		Iterator<Arc> typeArcs = this.typeGraph.getArcsSet().iterator();
		while (typeArcs.hasNext()) {
			Arc t = typeArcs.next();
			if (this.startLayer != null)			
				this.creationLayer.put(t, Integer.valueOf(this.startLayer.intValue())); //+1));
			else
				this.creationLayer.put(t, Integer.valueOf(0));
		}
	}

	private void reinitCreationLayer() {
		this.creationLayer.clear();
		Iterator<Node> typeNodes = this.typeGraph.getNodesSet().iterator();
		while (typeNodes.hasNext()) {
			Node t = typeNodes.next();
			this.creationLayer.put(t, Integer.valueOf(0));
		}
		Iterator<Arc> typeArcs = this.typeGraph.getArcsSet().iterator();
		while (typeArcs.hasNext()) {
			Arc t = typeArcs.next();
			this.creationLayer.put(t, Integer.valueOf(0));
		}
	}
	
	private void initCreationLayer(int init) {
		for (Enumeration<GraphObject> keys = this.creationLayer.keys(); keys.hasMoreElements();) {
			GraphObject t = keys.nextElement();
			this.creationLayer.put(t, Integer.valueOf(init));
		}
	}

	private void initDeletionLayer(GraGra gragra) {
		this.deletionLayer = new Hashtable<GraphObject, Integer>();
		Iterator<Node> typeNodes = this.typeGraph.getNodesSet().iterator();
		while (typeNodes.hasNext()) {
			Node t = typeNodes.next();
			this.deletionLayer.put(t, Integer.valueOf(0));
		}
		Iterator<Arc> typeArcs = this.typeGraph.getArcsSet().iterator();
		while (typeArcs.hasNext()) {
			Arc t = typeArcs.next();
			this.deletionLayer.put(t, Integer.valueOf(0));
		}
	}

	private void reinitDeletionLayer() {
		this.deletionLayer.clear();
		Iterator<Node> typeNodes = this.typeGraph.getNodesSet().iterator();
		while (typeNodes.hasNext()) {
			Node t = typeNodes.next();
			this.deletionLayer.put(t, Integer.valueOf(0));
		}
		Iterator<Arc> typeArcs = this.typeGraph.getArcsSet().iterator();
		while (typeArcs.hasNext()) {
			Arc t = typeArcs.next();
			this.deletionLayer.put(t, Integer.valueOf(0));
		}
	}
	
	private void initDeletionLayer(int init) {
		for (Enumeration<GraphObject> keys = this.deletionLayer.keys(); keys.hasMoreElements();) {
			GraphObject t = keys.nextElement();
			this.deletionLayer.put(t, Integer.valueOf(init));
		}
	}

	private void initOrderedRuleLayer(GraGra gragra) {
		if (this.priority) {
			RulePriority layer = new RulePriority(this.listOfRules);
			this.invertedRuleLayer = layer.invertPriority();
			this.startRuleLayer = layer.getStartPriority();
			this.orderedRuleLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = this.invertedRuleLayer.keys(); en.hasMoreElements();)
				this.orderedRuleLayerSet.add(en.nextElement());
		}
		else {
			RuleLayer layer = new RuleLayer(this.listOfRules);
			this.invertedRuleLayer = layer.invertLayer();
			this.startRuleLayer = layer.getStartLayer();
			this.orderedRuleLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = this.invertedRuleLayer.keys(); en.hasMoreElements();)
				this.orderedRuleLayerSet.add(en.nextElement());
		}
	}

	private void reinitOrderedRuleLayer() {
		if (this.priority) {
			RulePriority layer = new RulePriority(this.listOfRules);
			this.invertedRuleLayer = layer.invertPriority();
			this.startRuleLayer = layer.getStartPriority();
			this.orderedRuleLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = this.invertedRuleLayer.keys(); en.hasMoreElements();)
				this.orderedRuleLayerSet.add(en.nextElement());
		}
		else {
			RuleLayer layer = new RuleLayer(this.listOfRules);
			this.invertedRuleLayer = layer.invertLayer();
			this.startRuleLayer = layer.getStartLayer();
			this.orderedRuleLayerSet.clear();
			for (Enumeration<Integer> en = this.invertedRuleLayer.keys(); en.hasMoreElements();)
				this.orderedRuleLayerSet.add(en.nextElement());
		}
	}
	
	private void initOrderedTypeDeletionLayer() {
		TypeLayerOfTypeGraph layer = new TypeLayerOfTypeGraph(this.deletionLayer);
		this.invertedTypeDeletionLayer = layer.invertLayer();
		this.startLayer = layer.getStartLayer();
		this.invertedTypeDeletionLayer = layer.invertLayer();
		this.orderedTypeDeletionLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Enumeration<Integer> en = this.invertedTypeDeletionLayer.keys(); en
				.hasMoreElements();)
			this.orderedTypeDeletionLayerSet.add(en.nextElement());
	}

	private void initOrderedTypeCreationLayer() {
		TypeLayerOfTypeGraph layer = new TypeLayerOfTypeGraph(this.creationLayer);
		this.invertedTypeCreationLayer = layer.invertLayer();
		this.startLayer = layer.getStartLayer();
		this.invertedTypeCreationLayer = layer.invertLayer();
		this.orderedTypeCreationLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Enumeration<Integer> en = this.invertedTypeCreationLayer.keys(); 
				en.hasMoreElements();) {
			this.orderedTypeCreationLayerSet.add(en.nextElement());			
		}
	}
	
	private void initResults() {
		this.orderedRuleLayer = new Vector<Integer>();
		this.resultTypeDeletion = new Hashtable<Integer, Pair<Boolean, Vector<Rule>>>();
		this.resultDeletion = new Hashtable<Integer, Pair<Boolean, Vector<Rule>>>();
		this.resultNonDeletion = new Hashtable<Integer, Pair<Boolean, Vector<Rule>>>();
		
		this.errorMsg = new Hashtable<Integer, List<String>>();
		this.errorMsgDeletion1 = new Hashtable<Integer, List<String>>();
		this.errorMsgDeletion2 = new Hashtable<Integer, List<String>>();
		this.errorMsgNonDeletion = new Hashtable<Integer, List<String>>();
	}

	private void reinitResults() {
		this.orderedRuleLayer.clear();
		this.resultTypeDeletion.clear();
		this.resultDeletion.clear();
		this.resultNonDeletion.clear();
		clearErrors();
	}
	
	public void initAll(boolean generate) {
		if (generate) {
			// initRuleLayer(0);
			initRuleLayer(this.oldRuleLayer);
			if (this.startLayer != null)
				initCreationLayer(this.startLayer.intValue()); //+1);
			else
				initCreationLayer(0);
			initDeletionLayer(0);
			initResults();
			this.maxl = 0;
		} else
			resetLayer();
	}

	private boolean isDeleting(Rule r) {
		return r.isDeleting();
	}

	private boolean isCreating(Rule r) {
		return r.isCreating();
	}

	private GraphObject getTypeGraphObject(GraphObject go) {
		GraphObject t = null;
		if (go.isNode())
			t = this.typeGraph.getTypeSet().getTypeGraphNode(go.getType());
		else
			t = this.typeGraph.getTypeSet().getTypeGraphArc(
					go.getType(), ((Arc) go).getSourceType(), ((Arc) go).getTargetType());
		return t;
	}
	
	public Vector<Object> getCreatedTypesOnDeletionLayer(Integer layer) {
		Vector<Object> types = new Vector<Object>();
		for (Enumeration<Rule> en = this.deletionRule.elements(); en.hasMoreElements();) {
			Rule r = en.nextElement();
			for (Enumeration<GraphObject> elems = r.getRight().getElements(); elems
					.hasMoreElements();) {
				GraphObject go = elems.nextElement();
				if (!r.getInverseImage(go).hasMoreElements()) {
					GraphObject t = getTypeGraphObject(go);
					Integer tLayer = this.creationLayer.get(t);
					if ((tLayer.intValue() == layer.intValue())
							&& !types.contains(t))
						types.add(t);					
				}
			}
		}
		return types;
	}

	private void generateCreationLayer() {
		for (Enumeration<Rule> en = this.nondeletionRule.elements(); en.hasMoreElements();) {
			Rule r = en.nextElement();
			for (Enumeration<GraphObject> types = this.creationLayer.keys(); types
					.hasMoreElements();) {
				GraphObject t = types.nextElement();
				setCreationLayer(r, t);
			}
		}
	}

	private boolean ofSameNodeType(GraphObject t, GraphObject go) {
		if (go.isNode() && t.isNode()) {
//			return t.getType().isRelatedTo(go.getType());	
			return t.getType().isParentOf(go.getType());	
		} 
		return false;
	}
	
	private boolean ofSameArcType(GraphObject t, GraphObject go) {
		if (go.isArc() && t.isArc()) {
			return t.getType().compareTo(go.getType())
				&& ((Arc)t).getSourceType().isRelatedTo(((Arc)go).getSourceType())
				&& ((Arc)t).getTargetType().isRelatedTo(((Arc)go).getTargetType());
		} 
		return false;
	}
		
	private void setCreationLayer(Rule r, GraphObject t) {
		for (Enumeration<GraphObject> en = r.getRight().getElements(); en.hasMoreElements();) {
			GraphObject go = en.nextElement();
			if (!r.getInverseImage(go).hasMoreElements()) {
				if (ofSameNodeType(t, go) || ofSameArcType(t, go)) {
					Integer rl = this.ruleLayer.get(r);
					Integer cl = this.creationLayer.get(t);
					if (cl.intValue() <= rl.intValue()) {
						int l = rl.intValue() + 1;
						this.creationLayer.put(t, Integer.valueOf(l));
						if (l > this.maxl)
							this.maxl = l;
					}
				} 
			}
		}		
		// now for preserved graph objects
		for (Enumeration<GraphObject> en = r.getLeft().getElements(); en.hasMoreElements();) {
			GraphObject go = en.nextElement();
			if (r.getImage(go) != null) {
				if (ofSameNodeType(t, go) || ofSameArcType(t, go)) {
					Integer rl = this.ruleLayer.get(r);
					Integer cl = this.creationLayer.get(t);
					if (cl.intValue() > rl.intValue()) {
						if (this.generateRuleLayer) {
							this.ruleLayer.put(r, Integer.valueOf(cl.intValue()));
							rl = this.ruleLayer.get(r);
							this.needCorrection = true;
						}
						else {
							this.creationLayer.put(t, Integer.valueOf(rl.intValue()));
						}
					}
				}
			}
		}
	}

	/*
	private boolean increaseRuleLayer(Enumeration<Rule> rules, GraphObject t,
			Rule excludedRule) {
		boolean increased = false;
		while (rules.hasMoreElements()) {
			Rule r = rules.nextElement();
			if (!r.equals(excludedRule)) {
				if (usedType(t, r)) {
					if (layered)
						r.setLayer(r.getLayer() + 1);
					else if (priority)
						r.setPriority(r.getPriority() + 1);
					else
						r.setLayer(r.getLayer() + 1);
					increased = true;
				}
			}
		}
		return increased;
	}
*/
	
	/*
	private boolean usedType(GraphObject t, Rule r) {
		for (Enumeration<GraphObject> elems = r.getLeft().getElements(); elems
				.hasMoreElements();) {
			GraphObject go = elems.nextElement();
			if (r.getImage(go) != null) {
				if (ofSameType(t, go))
					return true;
			}
		}
		return false;
	}

	
	private void passCreationLayer() {
		for (Enumeration<Rule> en = creationRule.elements(); en.hasMoreElements();) {
			Rule r = en.nextElement();
			for (Enumeration<GraphObject> types = this.creationLayer.keys(); types
					.hasMoreElements();) {
				GraphObject t = types.nextElement();
				// System.out.print("creation type: <"+t+">");
				Integer rl = ruleLayer.get(r);
				Integer cl = this.creationLayer.get(t);
				if (cl.intValue() <= rl.intValue()) {
					int l = rl.intValue() + 1;
					this.creationLayer.put(t, Integer.valueOf(l));
					cl = this.creationLayer.get(t);
					if (l > maxl)
						maxl = l;
				}
			}
		}
	}
*/
	
	private void generateDeletionLayer() {
		if (this.generateRuleLayer) {
			// set rule layer of deletion rules to maxl first
			for (Enumeration<Rule> en = this.deletionRule.elements(); en.hasMoreElements();) {
				Rule r = en.nextElement();
				Integer rl = getRuleLayer().get(r);
				if (rl.intValue() < this.maxl)
					this.ruleLayer.put(r, Integer.valueOf(this.maxl));
			}
		}

		for (Enumeration<Rule> en = this.deletionRule.elements(); en.hasMoreElements();) {
			Rule r = en.nextElement();
			for (Enumeration<GraphObject> types = this.deletionLayer.keys(); types
					.hasMoreElements();) {
				GraphObject t = types.nextElement();
				setDeletionLayer(r, t);
			}
		}
		// set deletion layer of unused types to maxl
		for (Enumeration<Object> en = getDeletionLayer().keys(); en.hasMoreElements();) {
			GraphObject key = (GraphObject) en.nextElement();
			Integer dl = getDeletionLayer().get(key);
			Integer cl = this.creationLayer.get(key);
			if (dl.intValue() < cl.intValue())
				this.deletionLayer.put(key, Integer.valueOf(cl.intValue()));
		}
	}

	private void setDeletionLayer(Rule r, GraphObject t) {
		for (Enumeration<GraphObject> en = r.getLeft().getElements(); en.hasMoreElements();) {
			// first graph objects to delete
			GraphObject go = en.nextElement();
			if (r.getImage(go) == null) {
				if (ofSameNodeType(t, go) || ofSameArcType(t, go)) {
					Integer rl = this.ruleLayer.get(r);
					Integer cl = this.creationLayer.get(t);
					Integer dl = this.deletionLayer.get(t);

					int l = cl.intValue(); // + 1;
					if (l > this.maxl)
						this.maxl = l;

					if (this.generateRuleLayer && (rl.intValue() < this.maxl)) {
						this.ruleLayer.put(r, Integer.valueOf(this.maxl));
						rl = this.ruleLayer.get(r);
					}

					if (dl.intValue() < cl.intValue()) {
						this.deletionLayer.put(t, Integer.valueOf(this.maxl));
						dl = this.deletionLayer.get(t);
					}
					if ((dl.intValue() > rl.intValue()) && this.generateRuleLayer) {
						this.ruleLayer.put(r, Integer.valueOf(dl.intValue()));
						rl = this.ruleLayer.get(r);
					}
					if (dl.intValue() == 0) {
						this.deletionLayer.put(t, Integer.valueOf(rl.intValue()));
						dl = this.deletionLayer.get(t);
//						System.out.println("setDeletionLayer:: "+r.getName()+"   <"+t.getType().getName()+">   cl: "+cl+"   dl: "+dl);
					}
				}
			}
		}
		// now for new graph objects: update type creation layer
		for (Enumeration<GraphObject> en = r.getRight().getElements(); en.hasMoreElements();) {
			GraphObject go = en.nextElement();
			if (!r.getInverseImage(go).hasMoreElements()) {
				if (ofSameNodeType(t, go) || ofSameArcType(t, go)) {
					Integer rl = this.ruleLayer.get(r);
					Integer cl = this.creationLayer.get(t);
					if (cl.intValue() <= rl.intValue()) {
						this.creationLayer.put(t, Integer.valueOf(rl.intValue() + 1));
						cl = this.creationLayer.get(t);
					}
				}
			}
		}
	}

	private void clearErrors() {
		this.errorMsg.clear();
		this.errorMsgDeletion1.clear();
		this.errorMsgDeletion2.clear();
		this.errorMsgNonDeletion.clear();
	}
	
	/**
	 * Checks layer conditions .
	 * 
	 * @return true if conditions are valid.
	 */
	public boolean checkTermination() {
		clearErrors();
		
		if (this.generateRuleLayer) {
			initAll(this.generateRuleLayer);
		}
		generateCreationLayer();
		generateDeletionLayer();
		int n = this.listOfRules.size();
		while (this.needCorrection && n >= 0) {
			this.needCorrection = false;
			generateCreationLayer();
			generateDeletionLayer();
			n--;
		}

		this.valid = false;
		
		// check totality of rule layer function
		for (Iterator<Rule> en = this.listOfRules.iterator(); en.hasNext();) {
			Rule r = en.next();
			Integer rl = this.ruleLayer.get(r);
			/* layer function must be total*/
			if (rl == null) {
				this.errMsg = "Rule <"+ r.getName() + "> "
							+ "does not satisfy totality of rule layer function.";
				addErrorMessage(this.errorMsg, rl, this.errMsg);				
				return false;
			}
		}
		// check totality of deletion/creation layer functions
		for (Enumeration<GraphObject> en = this.grammar.getTypeGraph().getElements(); en.hasMoreElements();) {
			GraphObject t = en.nextElement();
			Integer dl = this.deletionLayer.get(t);
			Integer cl = this.creationLayer.get(t);
			/* layer function must be total */
			if (cl == null) {
				this.errMsg = "Type <"+ t.getType().getStringRepr() + "> "
						+ "does not satisfy totality of creation layer function.";
				addErrorMessage(this.errorMsg, dl, this.errMsg);
				return false;
			} else if (dl == null) {
				this.errMsg = "Type <"+ t.getType().getStringRepr() + "> "
						+ "does not satisfy totality of deletion layer function.";
				addErrorMessage(this.errorMsg, cl, this.errMsg);
				return false;
			}
		}
		
		boolean result = checkTerminationConditions();
		
		initOrderedTypeDeletionLayer();
		initOrderedTypeCreationLayer();
		
		this.valid = this.setValidResult();
		
		return result;
	}

	private void addErrorMessage(
			final Hashtable<Integer, List<String>> msgContainer, 
			final Integer key, 
			final String msg) {
		List<String> errList = msgContainer.get(key);
		if (errList == null) {
			errList = new Vector<String>();
			msgContainer.put(key, errList);
		}
		errList.add(this.errMsg);
	}
	
	private boolean checkTerminationConditions() {
		Integer currentLayer = this.startRuleLayer; 
		int i=0;
		boolean nextLayerExists = true;
		while (nextLayerExists && (currentLayer != null)) {
			// get rules for layer
			HashSet<Rule> rulesForLayer = this.invertedRuleLayer.get(currentLayer);
			if (rulesForLayer != null) {
				this.orderedRuleLayer.addElement(currentLayer);

				Vector<Rule> currentRules = new Vector<Rule>();
				Iterator<?> en = rulesForLayer.iterator();
				while (en.hasNext()) {
					Rule rule = (Rule) en.next();
					if (rule.isEnabled())
						currentRules.addElement(rule);
				}
			
				boolean checkOK = checkTypeDeletion(currentLayer, currentRules);
				Pair<Boolean, Vector<Rule>> value1 = new Pair<Boolean, Vector<Rule>>(
							Boolean.valueOf(checkOK), currentRules);
				this.resultTypeDeletion.put(currentLayer, value1);			

				checkOK = checkNonDeletionLayer(currentRules);
				Pair<Boolean, Vector<Rule>> value2 = new Pair<Boolean, Vector<Rule>>(
							Boolean.valueOf(checkOK), currentRules);
				this.resultNonDeletion.put(currentLayer, value2);

				checkOK = checkDeletionLayer(currentRules);
				Pair<Boolean, Vector<Rule>> value3 = new Pair<Boolean, Vector<Rule>>(
							Boolean.valueOf(checkOK), currentRules);
				this.resultDeletion.put(currentLayer, value3);
			}
			
//			OrderedSetIterator osi = this.orderedRuleLayerSet.find(currentLayer);
//			if ((osi == null) || osi.atEnd())
//				nextLayerExists = false;
//			else {
//				osi.advance();
//				currentLayer = (Integer) osi.get();
//			}
			i++;
			if (i < orderedRuleLayerSet.size()) {
				currentLayer = orderedRuleLayerSet.get(i);
			}
			else {
				nextLayerExists = false;
			}
		}

		return true;
	}

	/**
	 * Checks first (type) deletion condition of rules on a certain layer .
	 * These rules must to delete at least one node or edge of a certain type.
	 * 
	 * @param rules
	 *            belong to the same rule layer
	 * @return true if condition is satisfied.
	 */
	private boolean checkTypeDeletion(Integer layer, Vector<Rule> rules) {
		// Deletion Layer Conditions (1)
		boolean checkOK = true;
		// 1) check: each rule decreases the number of graph items
		for (int j = 0; j < rules.size(); j++) {
			Rule r = rules.elementAt(j);

			// each rule has to delete
			if (this.deletionRule.contains(r)) {
				if (r.getLeft().getSize() < r.getRight().getSize()) {
					// the rule is deleting, but creating, too
					checkOK = false;
					String test = "Rule <" + r.getName()+ "> does not decrease";
					if (this.errMsg.indexOf(test) < 0) {
						this.errMsg = "Rule <" + r.getName() 
							+ "> does not decrease the number of graph items of one special type.";
						addErrorMessage(this.errorMsgDeletion1, layer, this.errMsg);
					}
					break;
				}
			} 
			else if (/*r.isEmptyRule() && */r.isTriggerOfLayer()) {
				// an special case: a trigger rule is applied once only
			}
			else {
				// the rule is not deleting
				this.errMsg = "Rule <" + r.getName() 
						+ "> does not decrease the number of graph items.";
				addErrorMessage(this.errorMsgDeletion1, layer, this.errMsg);				
				return false;
			}
		}
		if (checkOK) {
			return true;
		}
		// or
		// 2) check: each rule decreases the number of graph items of one
		// special type
		Hashtable<Pair<GraphObject, Object>, Vector<Rule>> deletedType = new Hashtable<Pair<GraphObject, Object>, Vector<Rule>>();

		for (int j = 0; j < rules.size(); j++) {
			Rule r = rules.elementAt(j);
			
			// each rule has to delete is already checked
			// check one special type
			for (Enumeration<GraphObject> en = r.getLeft().getElements(); en
					.hasMoreElements();) {
				GraphObject o = en.nextElement();
				if (r.getImage(o) == null) {
					boolean containsKey = false;	
					GraphObject t = null;
					if (o.isNode()) 
						t = r.getTypeSet().getTypeGraphNode(o.getType());
					else
						t = r.getTypeSet().getTypeGraphArc(o.getType(), 
								((Arc)o).getSourceType(), 
								((Arc)o).getTargetType());
					
					Pair<GraphObject, Object> delt = new Pair<GraphObject, Object>(t, null);
					if (o.isArc())
						delt.second = new Pair<GraphObject, GraphObject>(
								r.getTypeSet().getTypeGraphNode(((Arc) o).getSource().getType()), 
								r.getTypeSet().getTypeGraphNode(((Arc) o).getTarget().getType()));
				
					Pair<GraphObject, Object> t1 = null;
					Enumeration<Pair<GraphObject, Object>> e = deletedType.keys();
					while (e.hasMoreElements()) {
						t1 = e.nextElement();
						if (t.getType().isRelatedTo(t1.first.getType())) {
							if (t1.second == null && delt.second == null) {
								containsKey = true;
								break;
							}
							if (t1.second != null && delt.second != null) {
								Pair<?,?> t1sec = (Pair<?,?>) t1.second;
								Pair<?,?> deltsec = (Pair<?,?>) delt.second;
								if (((GraphObject)deltsec.first).getType()
											.isRelatedTo(((GraphObject)t1sec.first).getType())
										&& (((GraphObject)deltsec.second).getType())
											.isRelatedTo(((GraphObject)t1sec.second).getType())) {
									containsKey = true;
									break;
								}
							}
						}
					}
					if (containsKey) {
						if (!deletedType.get(t1).contains(r))
							deletedType.get(t1).add(r);
					} else {
						Vector<Rule> v = new Vector<Rule>(rules.size());
						v.add(r);
						deletedType.put(delt, v);
					}
				}
			}
		}
		
		Vector<GraphObject> ltypes = new Vector<GraphObject>();
		for (Enumeration<Pair<GraphObject, Object>> en = deletedType.keys(); en.hasMoreElements();) {
			Pair<GraphObject, Object> key = en.nextElement();
			GraphObject t = key.first;
			Vector<Rule> v = deletedType.get(key);		
			if (v.size() == rules.size()) {
				for (int j = 0; j < rules.size(); j++) {
					Rule r = rules.elementAt(j);
					if (key.second == null) { // node type
						if (r.getLeft().getElementsOfTypeAsVector(t.getType()).size() <= r
								.getRight().getElementsOfTypeAsVector(t.getType()).size()) {
							String test = "Rule <" + r.getName()+ "> does not decrease";
							if (this.errMsg.indexOf(test) < 0) {
								this.errMsg = "Rule <" + r.getName()
									+ "> does not decrease the number of graph items of one special node type <"
									+t.getType().getName()+">";
								addErrorMessage(this.errorMsgDeletion1, layer, this.errMsg);
							}
							return false;
						}
					} else { // arc type
						if (r.getLeft().getElementsOfTypeAsVector(t.getType(),
								((GraphObject) ((Pair<?,?>) key.second).first).getType(),
								((GraphObject) ((Pair<?,?>) key.second).second).getType())
								.size() <= r.getRight().getElementsOfTypeAsVector(t.getType(),
										((GraphObject) ((Pair<?,?>) key.second).first).getType(),
										((GraphObject) ((Pair<?,?>) key.second).second).getType())
								.size()) {
							String test = "Rule <" + r.getName()+ "> does not decrease";
							if (this.errMsg.indexOf(test) < 0) {
								this.errMsg = "Rule <" + r.getName()
									+ "> does not decrease the number of graph items of one special edge type <"
									+t.getType().getName()+">";
								addErrorMessage(this.errorMsgDeletion1, layer, this.errMsg);
							}
							return false;
						}
					}
				}
				ltypes.add(t);
			}
		}
		if (ltypes.size() != 0) {
			this.deletionType.put(layer, ltypes);
			return true;
		} 
		this.errMsg = "Rules do not decrease the number of graph items.";
		addErrorMessage(this.errorMsgDeletion1, layer, this.errMsg);
		return false;
	}

	/**
	 * Checks deletion cond. of rules on a certain layer.
	 * 
	 * @return true if condition is satisfied.
	 */
	private boolean checkDeletionLayer(Vector<Rule> rules) {
		// Deletion Layer Conditions (2)
		boolean result = true;
		HashSet<Object> deletionSet = new HashSet<Object>();
		HashSet<Object> creationSet = new HashSet<Object>();
		for (int j = 0; j < rules.size(); j++) {
			deletionSet.clear();
			creationSet.clear();
			Rule rule = rules.elementAt(j);
		
			if (this.deletionRule.contains(rule)) {
				Integer rl = this.ruleLayer.get(rule);

				Graph leftGraph = rule.getLeft();
				Graph rightGraph = rule.getRight();
				/* find all objects to delete */
				for (Enumeration<GraphObject> en = leftGraph.getElements(); 
							en.hasMoreElements();) {
					GraphObject go = en.nextElement();
					if (rule.getImage(go) == null)
						deletionSet.add(go);
				}
				/* 1. is deleting at least one item */
				if (deletionSet.isEmpty()) {
					result = false;
					this.errMsg = "Rule <"+ rule.getName() + ">"
							+"  does not delete at least one graph item.";	
					addErrorMessage(this.errorMsgDeletion2, rl, this.errMsg);
					break;
				}

				/* 2. 0<= cl(l)<=dl(l)<=n */
				for (Enumeration<Object> en = getDeletionLayer().keys(); en
						.hasMoreElements()
						&& result;) {
					GraphObject key = (GraphObject) en.nextElement();
					Integer dl = getDeletionLayer().get(key);
					Integer cl = getCreationLayer().get(key);
					if (!(0 <= cl.intValue())
							|| !(cl.intValue() <= dl.intValue())) {
						result = false;
						this.errMsg = "Type  <"
								+ key.getType().getStringRepr()+ ">"
								+ ", rl = "+rl.intValue()
								+ ", cl = "+cl.intValue()
								+ ", dl = "+ dl.intValue()
								+ "  do not satisfy condition:"
								+ " 0 <= cl <= dl <= rl";
						addErrorMessage(this.errorMsgDeletion2, dl, this.errMsg);
						break;
					}
				}

				/* 3. dl(l) <= rl(r) */
				for (Iterator<?> en = deletionSet.iterator(); en.hasNext()
						&& result;) {
					GraphObject go = (GraphObject) en.next();
					GraphObject t = getTypeGraphObject(go);					
					Integer dl = getDeletionLayer().get(t);
					if (dl.intValue() > rl.intValue()) {
						result = false;
						this.errMsg = "Rule <"+ rule.getName()+ ">, "
								+ "Type <"+ t.getType().getStringRepr()+ ">"
								+ ", dl = "+ dl.intValue()
								+ ", rl = "+rl.intValue()
								+ " do not satisfy condition"
								+ " dl <= rl.";
						addErrorMessage(this.errorMsgDeletion2, rl, this.errMsg);
						break;
					}
				}
				if (!result)
					break;

				/* alle erzeugten Objekte suchen */
				for (Enumeration<GraphObject> en = rightGraph.getElements(); en
						.hasMoreElements();) {
					GraphObject go = en.nextElement();
					if (!rule.getInverseImage(go).hasMoreElements())
						creationSet.add(go);
				}
				
				/* 4. cl(l) > rl(r) */
				for (Iterator<?> en = creationSet.iterator(); en.hasNext()
						&& result;) {
					GraphObject go = (GraphObject) en.next();
					GraphObject t = getTypeGraphObject(go);
					Integer cl = getCreationLayer().get(t);
					if (cl.intValue() <= rl.intValue()) {
						result = false;
						this.errMsg = "Rule <"+ rule.getName()+ ">, "
								+ "Type <"+ t.getType().getStringRepr()+ ">"
								+ ", cl = "+ cl.intValue()
								+ ", rl = "+rl.intValue()
								+ " do not satisfy condition"
								+ " cl > rl.";
						addErrorMessage(this.errorMsgDeletion2, rl, this.errMsg);
						break;
					}
				}
			} else {
				result = false;
				break;
			}
		}

		return result;
	}

	/**
	 * Checks non-deletion cond. of rules on a certain layer.
	 * 
	 * @return true if condition is satisfied.
	 */
	private boolean checkNonDeletionLayer(Vector<Rule> rules) {
		// Creation Layer Conditions (3)
		boolean result = true;
		HashSet<Object> preservedSet = new HashSet<Object>();
		HashSet<Object> creationSet = new HashSet<Object>();
		
		for (int j = 0; j < rules.size(); j++) {
			Rule rule = rules.elementAt(j);
			
			int errKey = rule.getLayer();
			if (this.priority)
				errKey = rule.getPriority();
			
			if (this.nondeletionRule.contains(rule)) {
				
				/* rule is total */
				if (!rule.isTotal()) {
					this.errMsg = "Rule <" + rule.getName()
							+ "> is not total.";
					addErrorMessage(this.errorMsgNonDeletion, new Integer(errKey), this.errMsg);
					return false;
				}
				/* 1. rule is injective */
				if (!rule.isInjective()) {
					this.errMsg = "Rule <" + rule.getName()
							+ "> is not injective.";
					addErrorMessage(this.errorMsgNonDeletion, new Integer(errKey), this.errMsg);
					return false;
				}
				/* 2. rule has a NAC */
				else if (rule.isCreating()
						 && rule.getNACsList().isEmpty()) {
					this.errMsg = "Rule <" + rule.getName()
							+ "> does not have any NAC.";
					addErrorMessage(this.errorMsgNonDeletion, new Integer(errKey), this.errMsg);
					return false;
				}
				/* 2. NAC : L -> N with N -> R injective */
				else if (!this.ruleWithRightInjNAC(errKey, rule)) {						
					return false;
				}
				
				Integer rl = this.ruleLayer.get(rule);
				creationSet.clear();
				preservedSet.clear();
				Graph leftGraph = rule.getLeft();
				Graph rightGraph = rule.getRight();
				/* alle erhaltende Objekte suchen */
				for (Enumeration<GraphObject> en = leftGraph.getElements(); en
						.hasMoreElements();) {
					GraphObject grob = en.nextElement();
					if (rule.getImage(grob) != null)
						preservedSet.add(grob);
				}

				/* alle erzeugten Objekte suchen */
				for (Enumeration<GraphObject> en = rightGraph.getElements(); en
						.hasMoreElements();) {
					GraphObject grob = en.nextElement();
					if (!rule.getInverseImage(grob).hasMoreElements())
						creationSet.add(grob);
				}

				/* 3. for preserved objects: cl(l) <= rl(r) */
				for (Iterator<?> en = preservedSet.iterator(); en.hasNext()
						&& result;) {
					GraphObject grob = (GraphObject) en.next();
					GraphObject t = getTypeGraphObject(grob);
					Integer cl = getCreationLayer().get(t);
					if (cl.intValue() > rl.intValue()) {
						result = false;
						this.errMsg = "Rule <"+ rule.getName()+ ">, "
									+ "Type <"+ t.getType().getStringRepr()+ ">, "
									+ " rl = "+ rl.intValue()
									+ ", cl = "+ cl.intValue()
									+ " does not preserve graph items "
									+ " such that cl <= rl.";
						addErrorMessage(this.errorMsgNonDeletion, rl, this.errMsg);
						break;
					}
				}

				/* 4. for created objects: cl(l) > rl(r) */
				for (Iterator<?> en = creationSet.iterator(); en.hasNext()
						&& result;) {
					GraphObject grob = (GraphObject) en.next();
					GraphObject t = getTypeGraphObject(grob);
					Integer cl = getCreationLayer().get(t);
					if (cl.intValue() <= rl.intValue()) {
						result = false;						
						this.errMsg = "Rule <"+ rule.getName()+ ">, "
								+ "Type <"+ t.getType().getStringRepr()+ ">, "
								+ "rl = "+ rl.intValue()
								+ ", cl = "+ cl.intValue()
								+ " does not create graph items"
								+ " such that cl > rl.";
						addErrorMessage(this.errorMsgNonDeletion, rl, this.errMsg);
						break;
					}
				}
			} else {
				result = false;
				break;
			}
		}
		return result;
	}

	/** exists a NAC : L -> N with N -> R injective 
	 */
	private boolean ruleWithRightInjNAC(int errKey, final Rule rule) {
		/* 2. NAC : L -> N with N -> R injective */
		final List<OrdinaryMorphism> nacs = rule.getNACsList();
		if (nacs.isEmpty()) {
			return false;
		}
		
		List<Pair<OrdinaryMorphism,OrdinaryMorphism>> childNacs = null; 
		boolean result = false;
		for (int l=0; l<nacs.size() && !result; l++) {
			final OrdinaryMorphism nac = nacs.get(l);
			if (nac.isEnabled()) {
				boolean isChildNac = false;
				boolean failed = false;
				OrdinaryMorphism nprime = BaseFactory.theFactory()
						.createMorphism(nac.getTarget(),
								rule.getRight());
				nprime.setCompletionStrategy(new Completion_InjCSP());
				Enumeration<GraphObject> dom = rule.getDomain();
				while (dom.hasMoreElements()) {
					GraphObject grob = dom.nextElement();
					GraphObject nacob = nac.getImage(grob);
					if (nacob != null) {
						try {
							if (nacob.getType().isChildOf(rule.getImage(grob).getType())) {
								isChildNac = true;
								if (childNacs == null)
									childNacs = new Vector<Pair<OrdinaryMorphism,OrdinaryMorphism>>(1);
								childNacs.add(new Pair<OrdinaryMorphism,OrdinaryMorphism>(nac, nprime));
								break;
							}
							else
								nprime.addMapping(nacob, rule.getImage(grob));
						} catch (agg.xt_basis.BadMappingException ex) {
							failed = true;
							break;
						}
					}
				}	
				// at least one NAC exists so that n':N->R injective 
				if (isChildNac) {
					result = false;
					continue;
				}
				else if (!failed) 
					result = nprime.nextCompletionWithConstantsChecking();
			}
		}
		
		if (!result && childNacs != null && !childNacs.isEmpty()) {
			for (int i=0; i<childNacs.size() && !result; i++) {
				result = ruleWithRightInjChildNAC(rule, childNacs.get(i));
			}
		}
		if (!result) { 
			this.errMsg = "Rule <"+ rule.getName()+ "> "
					+ "does not have any right injective NACs.";
			addErrorMessage(this.errorMsgNonDeletion, new Integer(errKey), this.errMsg);
		}
		return result;					
	}
	
	private boolean ruleWithRightInjChildNAC(final Rule rule, Pair<OrdinaryMorphism,OrdinaryMorphism> p) {
		/* 2. NAC : L -> N with N -> R injective */
		boolean result = false;
		boolean oneChildOnly = false;
		boolean failed = false;
		OrdinaryMorphism nac = p.first;	
		OrdinaryMorphism nprime = p.second;
		nprime.setCompletionStrategy(new Completion_InheritCSP());
		Enumeration<GraphObject> dom = rule.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject grob = dom.nextElement();
			GraphObject nacob = nac.getImage(grob);
			if (nacob != null) {
				try {
					if (nacob.getType().isChildOf(rule.getImage(grob).getType())) {
						nprime.addChild2ParentMapping(nacob, rule.getImage(grob));
						if (grob.getType().getChildren().size() == 1)
							oneChildOnly = true;
					}
					else
						nprime.addMapping(nacob, rule.getImage(grob));
				} catch (agg.xt_basis.BadMappingException ex) {
//					this.errMsg = "Rule <"+ rule.getName()+ "> : "
//								+ "Mapping of N': N->R across  N<-L->R  failed.";
//					addErrorMessage(this.errorMsgNonDeletion, new Integer(errKey), this.errMsg);
					failed = true;
					break;
				}
			}
		}
		// at least one NAC exists so that n':N->R injective 
		if (!failed && oneChildOnly)
			result = nprime.nextCompletionWithConstantsChecking();	
		return result;					
	}
	
	
	/**
	 * A fast check on validity.
	 * 
	 * @return true if the layer function is valid.
	 */
	public boolean isValid() {
		return this.valid;
	}
	
	private boolean setValidResult() {
		boolean result = true;
		for (int i = 0; i < this.orderedRuleLayer.size(); i++) {
			Integer currentLayer = this.orderedRuleLayer.elementAt(i);
//			System.out.println("Layer: "+currentLayer.intValue());
			boolean localresult = true; //false;
			
			Pair<Boolean, Vector<Rule>> p = this.resultTypeDeletion.get(currentLayer);
			if (p != null && !p.second.isEmpty()) { 
				localresult = p.first.booleanValue();
			}
			
			if (!localresult) {
				p = this.resultNonDeletion.get(currentLayer);
				localresult = p.first.booleanValue();
				if (!localresult) {
					p = this.resultDeletion.get(currentLayer);
					localresult = p.first.booleanValue();
					if (localresult) {
//						System.out.println("Layer: "+currentLayer.intValue()+"  Deletion_2: "+localresult);
						this.errorMsgDeletion1.remove(currentLayer);
						this.errorMsgDeletion2.remove(currentLayer);
						this.errorMsgNonDeletion.remove(currentLayer);
					}
				} else {
//					System.out.println("Layer: "+currentLayer.intValue()+"  NonDeletion: "+localresult);
					this.errorMsgDeletion1.remove(currentLayer);
					this.errorMsgDeletion2.remove(currentLayer);
					this.errorMsgNonDeletion.remove(currentLayer);
				}
			} else {
//				System.out.println("Layer: "+currentLayer.intValue()+" Deletion_1:  "+localresult);
				this.errorMsgDeletion1.remove(currentLayer);
				this.errorMsgDeletion2.remove(currentLayer);
				this.errorMsgNonDeletion.remove(currentLayer);
			}
			
			result = result && localresult;
		}
		
		return result;
	}

	/**
	 * Returns an error message if the layer function is not valid.
	 * 
	 * @return The error message.
	 */
	public String getErrorMessage() {	
		String str = getErrorOfTypeDeletion(10);
		String str1 = getErrorOfDeletion(10);
		String str2 = getErrorOfNonDeletion(10);
		if (!str1.equals("")) {
			str = str.concat("\n\n");
			str = str.concat(str1);
		}
		if (!str2.equals("")) {
			str = str.concat("\n\n");
			str = str.concat(str2);
		}
		return str;
	}

	private String getErrorOfTypeDeletion(int maxErrors) {
		int n = 0;
		String str0 = "*** (Type) Deletion Layer Condition ( Deletion_1 ) ***";
		String str = "";
		Enumeration<Integer> keys = this.errorMsgDeletion1.keys();
		while (keys.hasMoreElements() && n<=maxErrors) {
			final List<String> list = this.errorMsgDeletion1.get(keys.nextElement());
			for (int i=0; i<list.size(); i++) {
				str = str.concat("\n");
				str = str.concat(list.get(i));
				n++;
				if (n>maxErrors) {
					str = str.concat("\n ... ");
					break;
				}
			}
		}
		if (!str.equals(""))
			str = str0.concat(str);
		
		return str;
	}
	
	private String getErrorOfDeletion(int maxErrors) {
		int n = 0;
		String str0 = "*** Deletion Layer Condition ( Deletion_2 ) ***";
		String str = "";
		Enumeration<Integer> keys = this.errorMsgDeletion2.keys();
		while (keys.hasMoreElements() && n<=maxErrors) {
			final List<String> list = this.errorMsgDeletion2.get(keys.nextElement());
			for (int i=0; i<list.size(); i++) {
				str = str.concat("\n");
				str = str.concat(list.get(i));
				n++;
				if (n>maxErrors) {
					str = str.concat("\n ... ");
					break;
				}
			}
		}
		if (!str.equals(""))
			str = str0.concat(str);
		
		return str;
	}
	
	private String getErrorOfNonDeletion(int maxErrors) {
		int n = 0;
		String str0 = "*** Nondeletion Layer Condition ( Nondeletion ) ***";
		String str = "";
		Enumeration<Integer> keys = this.errorMsgNonDeletion.keys();
		while (keys.hasMoreElements() && n<=maxErrors) {
			final List<String> list = this.errorMsgNonDeletion.get(keys.nextElement());
			for (int i=0; i<list.size(); i++) {
				str = str.concat("\n");
				str = str.concat(list.get(i));
				if (n>maxErrors) {
					str = str.concat("\n ... ");
					break;
				}
			}
		}
		if (!str.equals(""))
			str = str0.concat(str);
		
		return str;
	}
	
	
	/**
	 * Returns the rule layer of the layer function.
	 * 
	 * @return The rule layer.
	 */
	public Hashtable<Rule, Integer> getRuleLayer() {
		int size = this.listOfRules.size();
		
		if (size != this.ruleLayer.size()) {
			initRuleLayer(this.grammar);
			return this.ruleLayer;
		}

		Iterator<Rule> en = this.listOfRules.iterator();
		while (en.hasNext()) {
			Object key = en.next();
			if (!this.ruleLayer.containsKey(key)) {
				initRuleLayer(this.grammar);
				return this.ruleLayer;
			}
		}
		
		return this.ruleLayer;
	}

	public int getRuleLayer(Rule r) {
		if (this.ruleLayer.containsKey(r))
			return this.ruleLayer.get(r).intValue();
		
		return 0;
	}

	/**
	 * Returns the creation layer of the layer function.
	 * 
	 * @return The creation layer.
	 */
	public Hashtable<Object, Integer> getCreationLayer() {
		int size = this.typeGraph.getSize();

		if (size != this.creationLayer.size()) {
			initCreationLayer(this.grammar);			
			return new Hashtable<Object, Integer>(this.creationLayer);
		}

		Enumeration<GraphObject> en = this.typeGraph.getElements();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (!this.creationLayer.containsKey(key)) {
				initCreationLayer(this.grammar);
				return new Hashtable<Object, Integer>(this.creationLayer);
			}
		}
		
		return new Hashtable<Object, Integer>(this.creationLayer);
	}
	
	public int getCreationLayer(GraphObject t) {
		if (this.creationLayer.containsKey(t))
			return this.creationLayer.get(t).intValue();
		
		return 0;
	}

	/**
	 * Returns the deletion layer of the layer function.
	 * 
	 * @return The deletion layer.
	 */
	public Hashtable<Object, Integer> getDeletionLayer() {
		int size = this.typeGraph.getSize();		

		if (size != this.deletionLayer.size()) {
			initDeletionLayer(this.grammar);
			return new Hashtable<Object, Integer>(this.deletionLayer);
		}

		Enumeration<GraphObject> en = this.typeGraph.getElements();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (!this.deletionLayer.containsKey(key)) {
				initDeletionLayer(this.grammar);
				return new Hashtable<Object, Integer>(this.deletionLayer);
			}
		}
		
		return new Hashtable<Object, Integer>(this.deletionLayer);
	}
	
	public int getDeletionLayer(GraphObject t) {
		if (this.deletionLayer.containsKey(t))
			return this.deletionLayer.get(t).intValue();
		
		return 0;
	}

	/**
	 * Returns the smallest layer of the rule layer.
	 * 
	 * @return The smallest layer.
	 */
	public Integer getStartLayer() {
		int startL = Integer.MAX_VALUE;
		Integer result = null;
		for (Enumeration<Rule> keys = this.ruleLayer.keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Integer layer = this.ruleLayer.get(key);
			if (layer.intValue() < startL) {
				startL = layer.intValue();
				result = layer;
			}
		}
		return result;
	}

	/**
	 * Inverts a layer function so that the layer is the key and the value is a
	 * set.
	 * 
	 * @param layer
	 *            The layer function will be inverted.
	 * @return The inverted layer function.
	 */
	public Hashtable<Integer, HashSet<Rule>> invertLayer(
			Hashtable<Rule, Integer> layer) {
		Hashtable<Integer, HashSet<Rule>> inverted = new Hashtable<Integer, HashSet<Rule>>();
		for (Enumeration<Rule> keys = layer.keys(); keys.hasMoreElements();) {
			Rule key = keys.nextElement();
			Integer value = layer.get(key);
			HashSet<Rule> invertedValue = inverted.get(value);
			if (invertedValue == null) {
				invertedValue = new HashSet<Rule>();
				invertedValue.add(key);
				inverted.put(value, invertedValue);
			} else {
				invertedValue.add(key);
			}
		}
		return inverted;
	}

	public void saveRuleLayer() {
		for (Enumeration<Rule> keys = this.ruleLayer.keys(); keys.hasMoreElements();) {
			Rule r = keys.nextElement();
			Integer layer = this.ruleLayer.get(r);
			if (this.layered)
				r.setLayer(layer.intValue());
			else if (this.priority)
				r.setPriority(layer.intValue());
			else
				r.setLayer(layer.intValue());
		}
		saveRuleLayerInto(this.oldRuleLayer);
	}

	private void saveRuleLayerInto(Hashtable<Rule, Integer> table) {
		for (Iterator<Rule> e = this.listOfRules.iterator(); e.hasNext();) {
			Rule r = e.next();			
			if (this.layered)
				table.put(r, Integer.valueOf(r.getLayer()));
			else if (this.priority)
				table.put(r, Integer.valueOf(r.getPriority()));
			else
				table.put(r, Integer.valueOf(r.getLayer()));
		}
	}

	public void setGenerateRuleLayer(boolean b) {
		this.generateRuleLayer = b;
	}

	public void showLayer() {
		System.out.println(" RULE LAYER");
		for (Enumeration<Rule> keys = this.ruleLayer.keys(); keys.hasMoreElements();) {
			Rule r = keys.nextElement();
			Integer layer = this.ruleLayer.get(r);
			System.out.println(layer.intValue()+" "+r.getName());
		}
		System.out.println(" CREATION LAYER");
		for (Enumeration<GraphObject> keys = this.creationLayer.keys(); keys.hasMoreElements();) {
			GraphObject t = keys.nextElement();
			Integer layer = this.creationLayer.get(t);
			System.out.println(layer.intValue()+" "+t.getType().getStringRepr());
		}
		System.out.println(" DELETION LAYER");
		for (Enumeration<GraphObject> keys = this.deletionLayer.keys(); keys.hasMoreElements();) {
			GraphObject t = keys.nextElement();
			Integer layer = this.deletionLayer.get(t);
			System.out.println(layer.intValue()+" "+t.getType().getStringRepr());
		}
	}

	/**
	 * Returns the layer function in a human readable way.
	 * 
	 * @return The text.
	 */
	public String toString() {
		String resultString = super.toString() + " LayerFunction:\n";
		resultString += "\tRuleLayer:\n";
		resultString += getRuleLayer().toString() + "\n";
		resultString += "\tCreationLayer:\n";
		resultString += getCreationLayer().toString() + "\n";
		resultString += "\tDeletionLayer:\n";
		resultString += getDeletionLayer().toString() + "\n";
		return resultString;
	}

	public int getCreationLayer(Type t) {
		return 0;
	}
	
	public int getDeletionLayer(Type t) {		
			return 0;
	}
	
}
