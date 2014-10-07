/**
 * 
 */
package agg.xt_basis;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.handler.AvailableHandlers;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.ruleappl.ObjectFlow;
import agg.util.Pair;

/**
 * Construct a concurrent rule based on two source rules 
 * and already computed dependency critical pair of these rules.
 * Additionally used, the inverse rule of rule1
 * and two help isomorphisms:
 * isoLeft1:  LHS of rule1 --> LHScopy  which can be done by rule1.getLeft().isomorphicCopy(),
 * isoRight1: RHS of rule1 --> RHScopy  which can be done by rule1.getRight().isomorphicCopy().
 * 
 * NOT implemented jet: Shift of General ACs from from rule1 and rule2 to the concurrent rule.
 *  
 * @author olga
 *
 */
public class ConcurrentRule {

//TODO  shift of General ACs
	
	
	// embeddingLeft1: L1 -> Lcr
	protected OrdinaryMorphism embLr1ToLcr; // to compute
	
	// embeddingRight1: R1 -> Rcr
	protected OrdinaryMorphism embRr1ToRcr; // to compute
	 
	// embeddingLeft2: L2 -> Lcr
	protected OrdinaryMorphism embLr2ToLcr;	// to compute  
		
	// embeddingRight2: R2 -> Rcr
	protected OrdinaryMorphism embRr2ToRcr; // to compute
	

	protected Object source1, source2;	// given : Rule | ConcurrentRule
		
	protected int indx1=-1, indx2=-1;
	
	protected OrdinaryMorphism overlap1, overlap2; // given
	
	protected Object source1Concurrent, source2Concurrent; // given
	
	protected Rule concurrentRule;	// to compute
	
	protected int depth;
	
	protected boolean enableEqualVariableNameOfAttrMapping;
	
	protected boolean disjoint;
	
//	protected final Hashtable<GraphObject, GraphObject> 
//	reflectedObjectFlow = new Hashtable<GraphObject, GraphObject>(); // output -> input
	
	protected final Hashtable<GraphObject, GraphObject> 
	reflectedObjectFlowOI = new Hashtable<GraphObject, GraphObject>(); // source output -> other input
	
	protected final Hashtable<GraphObject, GraphObject> 
	reflectedObjectFlowIO = new Hashtable<GraphObject, GraphObject>(); // source input -> other output ->
	
	// (otherRule, ObjectFlow)| ObjectFlow: otherRule.RHS.GraphObject -> this.rule.LHS.GraphObject
	protected final Hashtable<Object, ObjectFlow> inputObjectFlow = new Hashtable<Object, ObjectFlow>(4);
	// (otherRule, ObjectFlow)| ObjectFlow: this.rule.RHS.GraphObject -> otherRule.LHS.GraphObject
	protected final Hashtable<Object, ObjectFlow> outputObjectFlow = new Hashtable<Object, ObjectFlow>(4);
	
	protected int sizeOfInputObjectFlow;
	
	protected boolean injective = true;
	
	private final List<OrdinaryMorphism> failedApplConds = new Vector<OrdinaryMorphism>();
	
	public long freeM, usedM;
	
	
	/**
	 * Construct a concurrent rule based on two source rules 
	 * and already computed dependency critical pair of these rules.
	 * Additionally used, the inverse rule of rule1
	 * and two help isomorphisms:
	 * isoLeft1: LHS of rule1 --> LHScopy of rule1 which can be done by rule1.getLeft().isomorphicCopy(),
	 * isoLeft1: RHS of rule1 --> RHScopy of rule1 which can be done by rule1.getRight().isomorphicCopy().
	 * The target graph of the morphism isoLeft1 is the left graph of the inverse rule,
	 * The target graph of the morphism isoRight1 is the right graph of the inverse rule,
	 */
	public ConcurrentRule(
			final Rule rule1, 
			final Rule inverseRule1, 
			final Rule rule2,
			final OrdinaryMorphism isoLeft1, 
			final OrdinaryMorphism isoRight1,
			final OrdinaryMorphism overlapping1,
			final OrdinaryMorphism overlapping2) {
		
		this.injective = true;
		this.source1 = rule1;
		this.source2 = rule2;
		this.overlap1 = overlapping1;
		this.overlap2 = overlapping2;
		
		this.concurrentRule = makeRule(rule1, inverseRule1, rule2,
				isoLeft1, isoRight1, overlapping1, overlapping2);
		if (this.concurrentRule != null) {
			this.depth++;
			this.concurrentRule.notApplicable = this.concurrentRule.notApplicable
												|| rule1.isNotApplicable()
												|| rule2.isNotApplicable();	
		}
	}
	
	/**
	 * Create disjoint concurrent (parallel) rule based on given rules.
	 * @param rule1
	 * @param rule2
	 */
	public ConcurrentRule(final Rule rule1, 
							final Rule rule2) {
		
		this.injective = true;
		this.source1 = rule1;
		this.source2 = rule2;

		this.concurrentRule = makeRuleByDisjointUnion(rule1, rule2);
		if (this.concurrentRule != null) {
			this.depth++;
			this.concurrentRule.notApplicable = this.concurrentRule.notApplicable
												|| rule1.isNotApplicable()
												|| rule2.isNotApplicable();
		}
	}
		
	public ConcurrentRule(
			final ConcurrentRule rule1, 
			final Rule inverseRule1, 
			final Rule rule2,
			final OrdinaryMorphism isoLeft1, 
			final OrdinaryMorphism isoRight1,
			final OrdinaryMorphism overlapping1,
			final OrdinaryMorphism overlapping2) {
		
		this.injective = true;
		this.source1 = rule1.getRule();
		this.source2 = rule2;
		this.source1Concurrent = rule1;
		this.overlap1 = overlapping1;
		this.overlap2 = overlapping2;
		
		this.concurrentRule = makeRule(rule1.getRule(), inverseRule1, rule2,
				isoLeft1, isoRight1, this.overlap1, this.overlap2);
		if (this.concurrentRule != null) {
			this.depth++;
			this.depth = this.depth + rule1.depth;
			this.concurrentRule.notApplicable = this.concurrentRule.notApplicable
												|| rule1.getRule().isNotApplicable()
												|| rule2.isNotApplicable();
		}
	}
	
	/**
	 * Make a concurrent rule by disjoint union of rule1 and rul2.
	 * @param rule1
	 * @param rule2
	 */
	public ConcurrentRule(
			final ConcurrentRule rule1, 
			final Rule rule2) {
		
		this.injective = true;
		this.source1 = rule1.getRule();
		this.source2 = rule2;
		this.source1Concurrent = rule1;
		
		this.concurrentRule = makeRuleByDisjointUnion(rule1.getRule(), rule2);
		if (this.concurrentRule != null) {
			this.depth++;
			this.depth = this.depth + rule1.depth;
			this.concurrentRule.notApplicable = this.concurrentRule.notApplicable
												|| rule1.getRule().isNotApplicable()
												|| rule2.isNotApplicable();
		}
	}

	public ConcurrentRule(
			final Rule rule1,			
			final ConcurrentRule rule2) {
		
		this.injective = true;
		this.source1 = rule1;
		this.source2 = rule2.getRule();
		this.source2Concurrent = rule2;
		
		this.concurrentRule = makeRuleByDisjointUnion(rule1, rule2.getRule());
		if (this.concurrentRule != null) {
			this.depth++;
			this.depth = this.depth + rule2.depth;
			this.concurrentRule.notApplicable = this.concurrentRule.notApplicable
												|| rule2.getRule().isNotApplicable()
												|| rule1.isNotApplicable();
		}
	}
	
	/*
	private Hashtable<GraphObject, GraphObject> getReflectedObjectFlowToRule(
			final Rule r,
			final List<ObjectFlow> list) {
		
		Hashtable<GraphObject, GraphObject> objmap = new Hashtable<GraphObject, GraphObject>();
		
		for (int i=0; i<list.size(); i++) {
			Hashtable<Object,Object> map = list.get(i).getMapping();
			Enumeration<Object> objs1 = map.keys();
			while (objs1.hasMoreElements()) {
				GraphObject obj1 = (GraphObject) objs1.nextElement();
				GraphObject obj2 = (GraphObject) map.get(obj1); 
				if (obj2.getContext() == r.getLeft()) {
					GraphObject out = this.getRightEmbedding(obj1);
					if (out != null) {
						objmap.put(out, obj2);
					} 					
				}
			}
		}
		return objmap;
	}
	*/
	
	/**
	 * The given Rule <code>r</code> is a pre-rule of this rule 
	 * and the ObjectFlow list contains all its output-input relations.<br>
	 * Collects all output-input relations 
	 * where the output is an object of <code>r.RHS</code>
	 * and the input is an Object of <code>this.getRule().LHS</code>.<br>
	 * 
	 * @param r
	 * @param list
	 * @return map of collected output-input object pairs
	 */
	public Hashtable<GraphObject, GraphObject> getReflectedInputObjectFlowFromRule(
			final Rule r,
			final List<ObjectFlow> list) {
		
		Hashtable<GraphObject, GraphObject> objmap = new Hashtable<GraphObject, GraphObject>();
		
		for (int i=0; i<list.size(); i++) {
			Hashtable<Object,Object> map = list.get(i).getMapping();
			Enumeration<Object> objs1 = map.keys();
			while (objs1.hasMoreElements()) {
				GraphObject obj1 = (GraphObject) objs1.nextElement();
				GraphObject obj2 = (GraphObject) map.get(obj1); 
//				obj1.getContext().getName();
				if (obj1.getContext() == r.getRight()) { // output of pre rule
					GraphObject in = this.getLeftEmbedding(obj2); // input for this rule
					if (in != null) {
						objmap.put(obj1, in);
					} 					
				}
			}
		}
		return objmap;
	}
	
	public Hashtable<GraphObject, GraphObject> getReflectedInputObjectFlowFromGraph(
			final Graph g,
			final List<ObjectFlow> list) {
		
		Hashtable<GraphObject, GraphObject> objmap = new Hashtable<GraphObject, GraphObject>();
		
		for (int i=0; i<list.size(); i++) {
			Hashtable<Object,Object> map = list.get(i).getMapping();
			Enumeration<Object> objs1 = map.keys();
			while (objs1.hasMoreElements()) {
				GraphObject obj1 = (GraphObject) objs1.nextElement();
				GraphObject obj2 = (GraphObject) map.get(obj1); 
//				obj1.getContext().getName();
				if (obj1.getContext() == g) { // output of graph
					GraphObject in = this.getLeftEmbedding(obj2); // input for this rule
					if (in != null) {
						objmap.put(obj1, in);
					} 					
				}
			}
		}
		return objmap;
	}
	
	public int getSizeOfReflectedInputObjectFlow() {
		return this.sizeOfInputObjectFlow;
	}
	
	public void setInjectiveMatchProperty(boolean b) {
		this.injective = b;
	}
	
	public boolean hasInjectiveMatchProperty() {
		return this.injective;
	}
	
	public Hashtable<GraphObject, GraphObject> applyReflectedObjectFlowToMatchMap(
			final Graph graph) {
		
		Hashtable<GraphObject, GraphObject> map = new Hashtable<GraphObject, GraphObject>();
		Enumeration<GraphObject> keys = this.reflectedObjectFlowIO.keys();
		while (keys.hasMoreElements()) {			
			GraphObject input = keys.nextElement();
			GraphObject output = this.reflectedObjectFlowIO.get(input);
			
			if (output.getContext() == graph) {
				GraphObject ownInput1 = this.embLr1ToLcr.getImage(input);
				if (ownInput1 != null) {
					map.put(ownInput1, output);
				} else {
					GraphObject ownInput2 = this.embLr2ToLcr.getImage(input);
					if (ownInput2 != null) {
						map.put(ownInput2, output);
					} else {
						// second source rule is ConcurrentRule
						if (this.source2Concurrent instanceof ConcurrentRule) {						
							ownInput2 = ((ConcurrentRule) this.source2Concurrent).getFirstLeftEmbedding().getImage(input);
							if (ownInput2 != null) {
								map.put(ownInput2, output);
							} else {
								ownInput2 = ((ConcurrentRule) this.source2Concurrent).getSecondLeftEmbedding().getImage(input);
								if (ownInput2 != null) {
									map.put(ownInput2, output);
								}
							}
						}
					}
				}
			} 			
			else {
				//TODO
//				if (this.source1 instanceof Rule
//					&& output.getContext() == ((Rule)this.source1).getRight()) {
//					if (((Rule)this.source1).getInverseImage(output).hasMoreElements()) {
//						GraphObject obj = ((Rule)this.source1).getInverseImage(output).nextElement();
//						//TODO
//					}
//				} else if (this.source2 instanceof Rule
//						&& output.getContext() == ((Rule)this.source2).getRight()) {
//					if (((Rule)this.source2).getInverseImage(output).hasMoreElements()) {
//						GraphObject obj = ((Rule)this.source2).getInverseImage(output).nextElement();
//						//TODO
//					}
//				}
			}
		}
		return map;
	}
		
	public boolean reflectObjectFlow(final List<ObjectFlow> objFlowOfSource) {
		if (objFlowOfSource == null || objFlowOfSource.isEmpty()) {
			return true;
		}
		final Iterator<ObjectFlow> iterator = objFlowOfSource.iterator();
		while (iterator.hasNext()) {
			ObjectFlow objFlow = iterator.next();
			Hashtable<Object, Object> map = objFlow.getMapping();
			Enumeration<Object> outputs = map.keys();
			while (outputs.hasMoreElements()) {
				Object output = outputs.nextElement();
				Object input = map.get(output);
				
				GraphObject input_cr = null;
				GraphObject output_cr = null;
				
//				if (this.source1Concurrent instanceof ConcurrentRule) {
//					//TODO ???
//				} else
				if (this.source1 instanceof Rule) {	
					input_cr = this.embLr1ToLcr.getImage((GraphObject) input);
					if (input_cr != null){
						this.reflectedObjectFlowIO.put((GraphObject) input, (GraphObject) output);
						this.sizeOfInputObjectFlow++;
					} else {
						output_cr = this.embRr1ToRcr.getImage((GraphObject) output);
						if (output_cr != null)
							this.reflectedObjectFlowOI.put((GraphObject) output, (GraphObject)input);
					}
				}
					
				if (this.source2Concurrent instanceof ConcurrentRule) {
					input_cr = ((ConcurrentRule) this.source2Concurrent).getFirstLeftEmbedding().getImage((GraphObject) input);
					if (this.embLr2ToLcr.getImage(input_cr) != null) {
						this.reflectedObjectFlowIO.put(input_cr, (GraphObject) output);
						this.sizeOfInputObjectFlow++;
					} else {
						input_cr = ((ConcurrentRule) this.source2Concurrent).getSecondLeftEmbedding().getImage((GraphObject) input);
						if (this.embLr2ToLcr.getImage(input_cr) != null) {
							this.reflectedObjectFlowIO.put(input_cr, (GraphObject) output);
							this.sizeOfInputObjectFlow++;
						} 
					}
				} else
				if (this.source2 instanceof Rule) {
					input_cr = this.embLr2ToLcr.getImage((GraphObject) input);	
					if (input_cr != null){
						if (((Rule)this.source1).getTarget().isElement((GraphObject) output)
								&& this.embLr1ToLcr.getInverseImage(input_cr).hasMoreElements()) {
						} else {
							this.reflectedObjectFlowIO.put((GraphObject) input, (GraphObject) output);
							this.sizeOfInputObjectFlow++;
						}
					} else {			
						output_cr = this.embRr2ToRcr.getImage((GraphObject) output);
						if (output_cr != null)
							this.reflectedObjectFlowOI.put((GraphObject) output, (GraphObject)input);
					}
				}
			}
		}
		return true;
	}
	
	/*
	private void connectOwnInputToOtherOutput(final Object input, final Object output) {
		this.reflectedObjectFlowIO.put((GraphObject) input, (GraphObject) output);
	}
	
	
	private GraphObject getKeyOfValue(
			final Hashtable<GraphObject, GraphObject> map, 
			final GraphObject val) {
		final Enumeration<GraphObject> keys = map.keys();
		while (keys.hasMoreElements()) {
			GraphObject key = keys.nextElement();
			if (map.get(key) == val)
				return key;
		}
		return null;
	}
		*/
	
	public void setFirstSourceConcurrentRule(final ConcurrentRule cr) {
		this.source1Concurrent = cr;
		this.depth = this.depth + cr.depth;
	}
	
	public ConcurrentRule getFirstSourceConcurrentRule() {
		if (this.source1Concurrent instanceof ConcurrentRule)
			return (ConcurrentRule) this.source1Concurrent;
		
		return null;
	}
	
	public Rule getFirstSourceRule() {
		if (this.source1 instanceof Rule)
			return (Rule) this.source1;
		
		return null;
	}
	
	public void setIndexOfFirstSourceRule(int indx) {
		this.indx1 = indx;
	}
	
	public int getIndexOfFirstSourceRule() {
		return this.indx1;
	}
	
	public void setSecondSourceConcurrentRule(final ConcurrentRule cr) {
		this.source2Concurrent = cr;
		this.depth = this.depth + cr.depth;
	}
	
	public ConcurrentRule getSecondSourceConcurrentRule() {
		if (this.source2Concurrent instanceof ConcurrentRule)
			return (ConcurrentRule) this.source2Concurrent;
		
		return null;
	}
	
	public Rule getSecondSourceRule() {
		if (this.source2 instanceof Rule)
			return (Rule) this.source2;
		
		return null;
	}
	
	public void setIndexOfSecondSourceRule(int indx) {
		this.indx2 = indx;
	}
	
	public int getIndexOfSecondSourceRule() {
		return this.indx2;
	}
	
	public Rule getLastSecondSourceRule() {
		if (this.source2Concurrent != null) {
			ConcurrentRule cr = (ConcurrentRule) this.source2Concurrent;
			while (cr.getSecondSourceConcurrentRule() != null) {
				cr = cr.getSecondSourceConcurrentRule();
			}
			return cr.getSecondSourceRule();
		} 
		return (Rule) this.source2;
	}
	
	public OrdinaryMorphism getSecondOverlappingOfDependencyPair() {
		return this.overlap2;
	}
	
	/**
	 * Returns a list with overlapping objects of the second rule (LHS)
	 */
	public List<GraphObject> getOverlappingObjectsOfSecondRule() {		
		if (this.overlap1 == null || this.overlap2 == null)
			return null;
		
		List<GraphObject> list = new Vector<GraphObject>();
		Iterator<?> elems = this.overlap1.getTarget().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (this.overlap1.getInverseImage(go).hasMoreElements()
					&& this.overlap2.getInverseImage(go).hasMoreElements()) {
				list.add(this.overlap2.getInverseImage(go).nextElement());
			}
		}
		elems = this.overlap1.getTarget().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (this.overlap1.getInverseImage(go).hasMoreElements()
					&& this.overlap2.getInverseImage(go).hasMoreElements()) {
				list.add(this.overlap2.getInverseImage(go).nextElement());
			}
		}
		
		return list;
	}
	
	/**
	 * Returns a list with overlapping objects of the first rule (RHS)
	 */
	public List<GraphObject> getOverlappingObjectsOfFirstRule() {		
		if (this.overlap1 == null || this.overlap2 == null)
			return null;
		
		List<GraphObject> list = new Vector<GraphObject>();
		Iterator<?> elems = this.overlap1.getTarget().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (this.overlap1.getInverseImage(go).hasMoreElements()
					&& this.overlap2.getInverseImage(go).hasMoreElements()) {
				list.add(this.overlap1.getInverseImage(go).nextElement());
			}
		}
		elems = this.overlap1.getTarget().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (this.overlap1.getInverseImage(go).hasMoreElements()
					&& this.overlap2.getInverseImage(go).hasMoreElements()) {
				list.add(this.overlap1.getInverseImage(go).nextElement());
			}
		}
		return list;
	}
	
	/**
	 * Returns a map with overlapping pair (RHS1 object, LHS2 object) 
	 */
	public Hashtable<GraphObject, GraphObject> getOverlappingObjects() {		
		if (this.overlap1 == null || this.overlap2 == null)
			return null;
		
		Hashtable<GraphObject, GraphObject> map = new Hashtable<GraphObject,GraphObject>();
		Iterator<?> elems = this.overlap1.getTarget().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (this.overlap1.getInverseImage(go).hasMoreElements()
					&& this.overlap2.getInverseImage(go).hasMoreElements()) {
				map.put(this.overlap1.getInverseImage(go).nextElement(), 
						this.overlap2.getInverseImage(go).nextElement());
			}
		}
		elems = this.overlap1.getTarget().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (this.overlap1.getInverseImage(go).hasMoreElements()
					&& this.overlap2.getInverseImage(go).hasMoreElements()) {
				map.put(this.overlap1.getInverseImage(go).nextElement(), 
						this.overlap2.getInverseImage(go).nextElement());
			}
		}
		return map;
	}
	
	public GraphObject getLeftEmbedding(final GraphObject obj) {
		GraphObject go = this.embLr1ToLcr.getImage(obj);
		if (go == null) {
			go = this.embLr2ToLcr.getImage(obj);
			if (go == null) {
				if (this.source2Concurrent != null) {				
					GraphObject go1 = ((ConcurrentRule) this.source2Concurrent).getLeftEmbedding(obj);
					if (go1 != null)
						go = this.getLeftEmbedding(go1);
				} 
			}
		} 
		
		return go;
	}
	
	
	public GraphObject getRightEmbedding(final GraphObject obj) {
		GraphObject go = this.embRr1ToRcr.getImage(obj);
		if (go == null) {
			go = this.embRr2ToRcr.getImage(obj);
			if (go == null) {
				if (this.source2Concurrent != null) {				
					GraphObject go1 = ((ConcurrentRule) this.source2Concurrent).getRightEmbedding(obj);
					if (go1 != null)
						go = this.getRightEmbedding(go1);
				} 
			}
		}
		return go;
	}

	public OrdinaryMorphism getFirstLeftEmbedding() {
		return this.embLr1ToLcr; 
	}
	
	public OrdinaryMorphism getSecondLeftEmbedding() {
		return this.embLr2ToLcr;
	}
	
	
	public OrdinaryMorphism getFirstRightEmbedding() {
		return this.embRr1ToRcr;
	}
	
	public OrdinaryMorphism getSecondRightEmbedding() {
		return this.embRr2ToRcr;
	}
	
	
	/**
	 * Returns constructed concurrent rule.
	 * @return concurrent rule
	 */
	public Rule getRule() {
		return this.concurrentRule;
	}
		
	public boolean isReadyToTransform() {
		if (this.concurrentRule != null) {
			boolean result = true;				
			BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(this.concurrentRule);		
			result = result && this.concurrentRule.isReadyToTransform();		
			return result;
		}
		return false;
	}
	
	
	public int getDepth() {
		return this.depth;
	}
	
	public void enableEqualVariableNameOfAttrMapping(boolean enable) {
		this.enableEqualVariableNameOfAttrMapping = enable;
	}
	
	/**
	 * Forwards match mappings from the first plain source rule to the concurrent rule.
	 * 
	 * @param map  match mapping of the first source rule
	 * @return	true if all mappings forwarded successfully, otherwise false
	 */
	public boolean forwardMatchMappingOfFirstSourceRule(
			final Hashtable<GraphObject, GraphObject> map, 
			final Graph g) {
		
		boolean result = false;
		Match m = BaseFactory.theFactory().createMatch(this.concurrentRule, g);
		if (m != null) {
			this.concurrentRule.setMatch(m);
			Enumeration<GraphObject> objs = map.keys();
			while (objs.hasMoreElements()) {
				final GraphObject obj = objs.nextElement();
				if (obj.isNode()) {
					final GraphObject img = map.get(obj);
					if (this.embLr1ToLcr != null
							&& obj.getContext() == this.embLr1ToLcr.getSource()) {
						final GraphObject obj_cr = this.embLr1ToLcr.getImage(obj);
						if (obj_cr != null) {
							try {
								m.addMapping(obj_cr, img);	
								result = true;
							} catch (BadMappingException ex) {
								return false;
							}
						}
					}
				}
			}
			objs = map.keys();
			while (objs.hasMoreElements()) {
				final GraphObject obj = objs.nextElement();
				if (obj.isArc()) {
					final GraphObject img = map.get(obj);
					if (this.embLr1ToLcr != null
							&& obj.getContext() == this.embLr1ToLcr.getSource()) {
						final GraphObject obj_cr = this.embLr1ToLcr.getImage(obj);
						if (obj_cr != null) {
							try {
								m.addMapping(obj_cr, img);	
								result = true;
							} catch (BadMappingException ex) {
								return false;
							}
						}
					}
				}
			}
		}
		return result;
	}
	
 	/**
	 * Constructs a concurrent rule based on given rules rule1, rule2.
	 * This concurrent rule is like a parallel rule:
	 * its LHS is the disjoint union of rule1.LHS and rule2.LHS,
	 * its RHS is the disjoint union of rule1.RHS and rule2.RHS.
	 * 
	 * @param rule1 the first source rule of concurrent rule
	 * @param rule2	the second source rule of concurrent rule
     *
	 * @return constructed concurrent rule
	 */
	private Rule makeRuleByDisjointUnion(
			final Rule rule1,
			final Rule rule2) {
		
//		System.out.println("ConcurrentRule.makeRuleByDisjointUnion "+rule1.getName()+" + "+rule2.getName());
		this.failedApplConds.clear();
		Rule cr = null;
		this.disjoint = true;
		
		// make left and right graphs
		OrdinaryMorphism lhs1ToLHS = rule1.getLeft().isomorphicCopy();
		if (lhs1ToLHS == null) {
			return null;
		}
		OrdinaryMorphism rhs1ToRHS = rule1.getRight().isomorphicCopy();	
		if (rhs1ToRHS == null) {
			lhs1ToLHS.dispose(); lhs1ToLHS = null;
			return null;
		}
		
		OrdinaryMorphism lhs2ToLHS = null;
		try {
			lhs2ToLHS = BaseFactory.theFactory().extendGraphByGraph(lhs1ToLHS.getTarget(), rule2.getLeft());
			// lhs1ToLHS.getTarget() == lhs2ToLHS.getTarget()
		} catch (Exception e) { 
			lhs1ToLHS.dispose(); lhs1ToLHS = null;
			rhs1ToRHS.dispose(); rhs1ToRHS = null;
			return null; 
		}
		
		if (lhs2ToLHS != null) {
			OrdinaryMorphism rhs2ToRHS = null;
			try {
				rhs2ToRHS = BaseFactory.theFactory().extendGraphByGraph(rhs1ToRHS.getTarget(), rule2.getRight());
				// rhs1ToRHS.getTarget() == rhs2ToRHS.getTarget()
			} catch (Exception e) {
				lhs1ToLHS.dispose(); lhs1ToLHS = null;
				rhs1ToRHS.dispose(); rhs1ToRHS = null;
				return null; 
			}
			
			if (rhs2ToRHS != null) {
				boolean ok = true;
				// get graphs
				final Graph lhs = lhs2ToLHS.getTarget();
				final Graph rhs = rhs2ToRHS.getTarget();
				// create morphism 
				final OrdinaryMorphism morphCR = BaseFactory.theFactory().createMorphism(lhs, rhs);	
				
				// add morphism mapping over rule1
				// add morphism mapping over rule2
				if (morphCR.completeDiagram(lhs1ToLHS, rule1, rhs1ToRHS)
						&& morphCR.completeDiagram(lhs2ToLHS, rule2, rhs2ToRHS)) {
					// make concurrent rule
					cr = BaseFactory.theFactory().constructRuleFromMorph(morphCR);
					cr.setName(rule1.getName()+"+"+rule2.getName());
						
					// store help data
					this.embLr1ToLcr = lhs1ToLHS;
					this.embRr1ToRcr = rhs1ToRHS;
						
					this.embLr2ToLcr = lhs2ToLHS; 
					this.embRr2ToRcr = rhs2ToRHS;
						
					// handle PACs and NACs
	
					this.shiftCondsOfRuleOverEmbMorph(cr, rule1, this.embLr1ToLcr, true);
					this.shiftCondsOfRuleOverEmbMorph(cr, rule2, this.embLr2ToLcr, true);
					
					OrdinaryMorphism rhs1ToE = rule1.getRight().isomorphicCopy();
					try {
						OrdinaryMorphism lhs2ToE = BaseFactory.theFactory().extendGraphByGraph(
													rhs1ToE.getTarget(), rule2.getLeft());						
						OrdinaryMorphism left = BaseFactory.theFactory().createMorphism(lhs, lhs2ToE.getTarget());
						if (left.completeDiagram(lhs1ToLHS, rule1, rhs1ToE)
							 && left.completeDiagram2(this.embLr2ToLcr, lhs2ToE)) {
							// left: L1+L2 -> R1+L2
							this.shiftCondsOfRuleOverMorphAndLeft(cr, rule2, lhs2ToE, left, this.embLr2ToLcr, true);
						}
						else
							ok = false;
					} catch (Exception e) {
						ok = false;
					}

					if (ok) {
						OrdinaryMorphism rhs2ToE = rule2.getRight().isomorphicCopy();
						try {
							OrdinaryMorphism lhs1ToE = BaseFactory.theFactory().extendGraphByGraph(
														rhs2ToE.getTarget(), rule1.getLeft());						
							OrdinaryMorphism left = BaseFactory.theFactory().createMorphism(lhs, lhs1ToE.getTarget());
							if (left.completeDiagram(lhs2ToLHS, rule2, rhs2ToE)
									&& left.completeDiagram2(this.embLr1ToLcr, lhs1ToE)) {
								// left: L1+L2 -> R2+L1
								this.shiftCondsOfRuleOverMorphAndLeft(cr, rule1, lhs1ToE, left, this.embLr1ToLcr, true);
							}
							else
								ok = false;
						} catch (Exception e) {
							ok = false;
						}
					}
						
					removeIsomorphicMorph(cr.getNACsList());
					removeIsomorphicMorph(cr.getPACsList());
						
					// add attribute conditions
					addAttrConditionFromTo(rule1, cr);
					addAttrConditionFromTo(rule2, cr);	
						
					addUndeclaredVariableOfExpression(cr);												
					BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(cr);
							
					// test
					this.adjustUnsetAttrsAboveMorph(this.embLr1ToLcr);
					this.adjustUnsetAttrsAboveMorph(this.embLr2ToLcr);
					
					cr.removeUnusedVariableOfAttrContext();
					setInputParameterIfNeeded(cr);
					cr.isReadyToTransform();
						
//					((VarTuple)cr.getAttrContext().getVariables()).showVariables();
//					((VarTuple)cr.getLeft().getAttrContext().getVariables()).showVariables();
//					((VarTuple)cr.getRight().getAttrContext().getVariables()).showVariables();

//					System.out.println("ConcurrentRule.makeRuleByDisjointUnion  ===>  "+cr.getName()+"     DONE  ( " +cr.getErrorMsg());				
				}
				morphCR.dispose();
			}
			else {
				lhs1ToLHS.dispose(); lhs1ToLHS = null;
				rhs1ToRHS.dispose(); rhs1ToRHS = null;
				lhs2ToLHS.dispose(); lhs2ToLHS = null;
			}
		}
		
		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(rule1);	
		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(rule2);
		
		return cr;
	}


	/**
	 * Constructs a concurrent rule based on given parameters.
	 * 
	 * @param rule1 the first source rule of concurrent rule
	 * @param inverseRule1 inverse rule of the first source rule
	 * @param rule2	the second source rule of concurrent rule
	 * @param isoLeft1	isomorphism: rule1.LHS --> inverseRule1.RHS
	 * @param isoRight1	isomorphism: rule1.RHS --> inverseRule1.LHS
	 * @param overlapping1 : inverseRule1.LHS -> graph
	 * @param overlapping2 : rule2.LHS -> graph
	 * 
	 * @return constructed concurrent rule
	 */
	private Rule makeRule(
			final Rule rule1,
			final Rule inverseRule1,
			final Rule rule2,
			final OrdinaryMorphism isoLeft1, 
			final OrdinaryMorphism isoRight1,			
			final OrdinaryMorphism overlapping1,
			final OrdinaryMorphism overlapping2) {
//		System.out.println("ConcurrentRule.makeRule   "+rule1.getName()+" & "+rule2.getName());
		this.failedApplConds.clear();
		Rule cr = null;
		this.disjoint = false;
		
		// graphE is the overlapping graph
		final Graph graphE = overlapping1.getTarget();
		// create isom of the overlapping graph graphE
		final OrdinaryMorphism iso1 = graphE.isomorphicCopy();
		if (iso1 == null)
			return null;
		// first create morphism of concurrent rule
		final OrdinaryMorphism crMorph = iso1.getTarget().isomorphicCopy();
		if (crMorph == null) {
			iso1.dispose();
			return null;
		}
		
		final Graph leftCR = crMorph.getSource();
		final Graph rightCR = crMorph.getTarget();					
				
		// match1: inverseRule1.LHS -> leftCR == iso1.target
		final Match match1 = constructMatch1(leftCR, inverseRule1, overlapping1, iso1);
		// match2: rule2.LHS -> rightCR == iso1.target == iso2.source
		final Match match2 = constructMatch2(rightCR, rule2, overlapping2, iso1, crMorph);
		
		final OrdinaryMorphism lhs1ToLHS = constructLHSbyPO(match1);
		// lhs1ToLHS: inverseRule1.LHS -> leftCR == iso1.target == iso2.source

		boolean failed = (lhs1ToLHS == null);		
		if (!failed) {
			final OrdinaryMorphism rhs2ToRHS = constructRHSbyPO(match2/*, crMorph*/);
			// rhs2ToRHS: rule2.RHS -> rightCR == iso2.target

			failed = (rhs2ToRHS == null);			
			if (!failed) {
				// make concurrent rule
				cr = BaseFactory.theFactory().constructRuleFromMorph(crMorph);
				cr.setName(rule1.getName()+"+"+rule2.getName());
				
				// save help data
				this.embLr1ToLcr = isoLeft1.compose(lhs1ToLHS);
				this.embRr1ToRcr = isoRight1.compose(overlapping1).compose(iso1).compose(crMorph);
								
				this.embLr2ToLcr = overlapping2.compose(iso1);
				this.embRr2ToRcr = rhs2ToRHS;
				
//				System.out.println("*******  Concurrent rule: "+cr.getName());
				
				boolean includedPACs = true;
//				if (checkCorrespondingAttrsOfPACs(rule1)) {
					// extend LHS of concurrent rule by PACs of rule1
//					includedPACs = extendLeftRightGraphsByPACsOfRule(cr, rule1, this.embLr1ToLcr);
//				}
				
//				System.out.println("*******  try add NACs (PACs)");
				// extend concurrent rule by NACs and PACs of its first source rule1
				// this.embLr1ToLcr is n1: LHS_r1 --> LHS_rc

				// if add of a PAC of first rule failed, the concurrent rule is not applicable!
				cr.notApplicable = !shiftCondsOfRuleOverEmbMorph(cr, rule1, 
														this.embLr1ToLcr,
														includedPACs);
								
				includedPACs = true;
//				if (checkCorrespondingAttrsOfPACs(rule2)) {
					// extend LHS of concurrent rule by PACs of rule2
//					includedPACs = extendLeftRightGraphsByPACsOfRule(cr, rule2, this.embLr2ToLcr);
//				} 
				
				// if add of PAC of second rule failed, the concurrent rule is not applicable!
				cr.notApplicable = !shiftCondsOfRuleOverMorphAndRight(
													cr, 
													rule2, 
													overlapping2,
													iso1,
													this.embLr2ToLcr,
													includedPACs);
				
				removeIsomorphicMorph(cr.getNACsList());
				removeIsomorphicMorph(cr.getPACsList());
				
				// add attribute conditions
				addAttrConditionFromTo(rule1, cr);
				addAttrConditionFromTo(rule2, cr);
						
				final OrdinaryMorphism rule1LHS2leftCR = isoLeft1.compose(lhs1ToLHS);	
				// adjust attr expressions and conditions	
				adjustLeftMappedAttrs(cr, rule1, rule1LHS2leftCR);
						
				setAttrExpressionOfConcurrentRule(match2.getRule(), match2, rhs2ToRHS, cr);										
				addUndeclaredVariableOfExpression(cr);				
				BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(cr);				
				cr.removeUnusedVariableOfAttrContext();	
				
				// test
				this.adjustUnsetAttrsAboveMorph(this.embLr1ToLcr);
				
				setInputParameterIfNeeded(cr);	
				cr.isReadyToTransform();
								
//				System.out.println("ConcurrentRule.makeRule  ===>  "+cr.getName()+"     DONE  ( " +cr.getErrorMsg());				
								
				rule1LHS2leftCR.dispose();	
			}
		}
		
		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(rule1);	
		BaseFactory.theFactory().unsetAllTransientAttrValuesOfRule(rule2);
		
		return cr;
	}
		
	/**
	 * Constructs the first match to produce the left graph of the concurrent rule by PO1.
	 * 
	 * @param leftCR left graph of the concurrent rule
	 * @param inverseRule1 inverse rule1 to apply at the left graph on the constructed match
	 * @param m1 the first morphism of the used dependency critical pair
	 * @param iso1	isomorphism from the overlapping graph of the used dependency critical pair
	 * to a copy of this graph
	 * 
	 * @return the first match
	 */
	private Match constructMatch1(
			final Graph leftCR, 
			final Rule inverseRule1,
			final OrdinaryMorphism m1,
			final OrdinaryMorphism iso1) {
		// here:
//		m1 = criticalPair.first;		
//		iso1 = graphE.isomorphicCopy();
//		graphE = criticalPair.first.getTarget();
		
		boolean failed = false;
		// restore match1 to apply inverseRule1 on Graph leftCR
		Match match1 = BaseFactory.theFactory().createMatch(inverseRule1, leftCR, true);	
		match1.setCompletionStrategy(new Completion_InjCSP());
		match1.getTarget().setCompleteGraph(false);
		// set mappings of match1 similar to m1	
		Enumeration<GraphObject> dom1 = m1.getDomain();
		while (dom1.hasMoreElements() && !failed) {
			GraphObject obj = dom1.nextElement();
			try {
				match1.addMapping(obj, iso1.getImage(m1.getImage(obj)));
			} catch (Exception ex) {
//				System.out.println("ConcurrentRule.constructMatch1:: match1 FAILED!  "+ex.getMessage());
				failed = true;
			}
		}		
		return match1;
	}
	
	/**
	 * Computes the left graph of the concurrent rule by PO 
	 * via the specified match. 
	 * @param match the match to apply
	 * 
	 * @return the co-match if PO exists, otherwise null
	 */
	private OrdinaryMorphism constructLHSbyPO(final Match match) {
		// here:
		//match: inverseRule.LHS -> concurrent.LHS
		// apply match to get LHS graph of concurrent rule

		OrdinaryMorphism comatch = null;
		try {						
			comatch = (OrdinaryMorphism) TestStep.execute(match, true, this.enableEqualVariableNameOfAttrMapping);
		} catch (Exception ex) {
//			System.out.println("ConcurrentRule.constructLHSbyPO:: comatch FAILED!  "+ex.getMessage());
		}
		return comatch;
	}
	
	/**
	 * Constructs the second match to produce the right graph of the concurrent rule by PO2.
	 * 
	 * @param rightCR right graph of the concurrent rule
	 * @param rule2 rule to apply at the right graph on the constructed match
	 * @param m2 the second morphism of the used dependency critical pair
	 * @param iso1	isomorphism from the overlapping graph of the used dependency critical pair
	 * to a copy of this graph. The target graph of the morphism iso1 is the left graph of the concurrent rule
	 * @param iso2	isomorphism from the target graph of the morphism iso1 
	 * to a copy of this graph. The target graph of the morphism iso2 is the right graph of the concurrent rule
	 * 
	 * @return the first match
	 */
	private Match constructMatch2(
			final Graph rightCR, 
			final Rule rule2,
			final OrdinaryMorphism m2,
			final OrdinaryMorphism iso1,
			final OrdinaryMorphism iso2) {
		// here:
//		 m2 = criticalPair.second;
//		iso1 = graphE.isomorphicCopy();
//		iso2 = iso1.getTarget().isomorphicCopy();
//		graphE = criticalPair.first.getTarget();
	
		boolean failed = false;
		// restore match2 to apply rule2 on Graph rightCR
		Match match2 = BaseFactory.theFactory().createMatch(rule2, rightCR, true);		
		match2.setCompletionStrategy(new Completion_InjCSP());
		match2.getTarget().setCompleteGraph(false);
		// set mappings of match2 similar to m2	
		Enumeration<GraphObject> dom2 = m2.getDomain();
		while (!failed && dom2.hasMoreElements()) {
			GraphObject obj = dom2.nextElement();
//			System.out.println("*** m2.getImage(obj): "+m2.getImage(obj));
			try {
				match2.addMapping(obj, iso2.getImage(iso1.getImage(m2.getImage(obj))));
			} catch (BadMappingException ex) {				
//				System.out.println("ConcurrentRule.constructMatch2:: match2 FAILED!  "+ex.getMessage());					
				failed = true;
			}
		}
//		System.out.println("****** match2: "+match2);
		return match2;
	}
	
	/**
	 * Computes the right graph of the concurrent rule by PO 
	 * via  the specified match. 
	 * @param match the match to apply
	 * 
	 * @return the co-match if PO exists, otherwise null
	 */
	private OrdinaryMorphism constructRHSbyPO(final Match match/*, 
			final OrdinaryMorphism concurrentMorph*/) {
		// here:
		// match2: rule2.LHS -> concurrentRule.RHS
		// apply match2 to get RHS graph of concurrent rule

		OrdinaryMorphism comatch = null;
		try {
			comatch = (OrdinaryMorphism) TestStep.execute(match, true, this.enableEqualVariableNameOfAttrMapping);
			
//			setAttrExpressionOfConcurrentRule(match.getRule(), match, comatch, concurrentMorph);
		} catch (Exception ex) {
//			System.out.println("ConcurrentRule.constructRHSbyPO:: comatch FAILED!  "+ex.getMessage());						
		}
		return comatch;
	}
	
	/**
	 * Shift NACs and PACs of the rule over morphism 
	 * lhs11ToLHS: rule1.LHS -> cr.LHS.
	 * 
	 * @param cr	the concurrent rule 
	 * @param rule	the a source rule
	 * @param lhsToLHS the morphism: rule.LHS --> cr.LHS
	 * 
	 * @return false, if construction of at least one PAC failed, otherwise return true.
	 */
	private boolean shiftCondsOfRuleOverEmbMorph(
			final Rule cr,
			final Rule rule,
			final OrdinaryMorphism lhsToLHS,
			boolean alsoPACs) {
						
		boolean ok = true;
		if (alsoPACs && rule.getPACs().hasMoreElements()) {
			List<OrdinaryMorphism> 
			condList = shiftPACsOverEmbMorph(cr, rule, rule.getPACs(), lhsToLHS);
			if (cr.notApplicable) {
				return false;				
			}
			if (condList != null && !condList.isEmpty()) {
				removeIsomorphicMorph(condList);
				for (int l=0; l<condList.size(); l++) {
					OrdinaryMorphism condCR = condList.get(l);
					condCR.getImage().setAttrContext(cr.getLeft().getAttrContext());
					condCR.setAttrContext(cr.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(condCR.getTarget(), (VarTuple)cr.getAttrContext().getVariables());
					if (!condCR.isRightTotal()
							|| !condCR.doesIgnoreAttrs()) {
						cr.addPAC(condCR);
					}
				}
			}
		}
		
		if (ok && rule.getNACs().hasMoreElements()) {
			List<OrdinaryMorphism> 
			condList = shiftNACsOverEmbMorph(rule, rule.getNACs(), lhsToLHS);
			if (condList != null && !condList.isEmpty()) {
				removeIsomorphicMorph(condList);
				for (int l=0; l<condList.size(); l++) {
					OrdinaryMorphism condCR = condList.get(l);
					condCR.getImage().setAttrContext(cr.getLeft().getAttrContext());
					condCR.setAttrContext(cr.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(condCR.getTarget(), (VarTuple)cr.getAttrContext().getVariables());
					if (!condCR.isRightTotal()
							|| !condCR.doesIgnoreAttrs()) {
						cr.addNAC(condCR);
//						condCR.setName(condCR.getName()+"-EM-"+rule.getName());
					}
				}
			}
		}
		
		return ok;
	}
	

	private void removeIsomorphicMorph(List<OrdinaryMorphism> list) {
		if (list.size() >= 2) {
			List<OrdinaryMorphism> list1 = new Vector<OrdinaryMorphism>(list);
			for (int i=0; i<list1.size(); i++) {
				OrdinaryMorphism m1 = list1.get(i);
				if (list.contains(m1)) {
					for (int j=0; j<list.size(); j++) {
						OrdinaryMorphism m = list.get(j);
						if (m != m1) {
							OrdinaryMorphism iso = m1.getTarget().getIsomorphicWith(m.getTarget());
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
	}
	
	@SuppressWarnings("unused")
	private boolean extendLeftRightGraphsByPACsOfRule(
			final Rule concurRule, 
			final Rule rule, 
			final OrdinaryMorphism leftRuleToLeftCR) {
		
		boolean ok = true;
		final List<OrdinaryMorphism> pacs = rule.getPACsList();
		for(int i=0; i<pacs.size() && ok; i++) {
			OrdinaryMorphism pac = pacs.get(i);
			// extend left graph of concurrent rule
			OrdinaryMorphism pacGraph2concurRuleLHS = this.extendTargetGraph(leftRuleToLeftCR, pac, concurRule);
			
			// extend right graph of concurrent rule,
			// add morphism mapping
			Hashtable<Node, Node> tmp = new Hashtable<Node, Node>(5);			
			final Iterator<Node> en = pac.getTarget().getNodesSet().iterator();
			while (en.hasNext() && ok) {
				GraphObject o = en.next();
				if (!pac.getInverseImage(o).hasMoreElements()) {
					Node lhsNode = (Node) pacGraph2concurRuleLHS.getImage(o);
					try {
						Node rhsNode = concurRule.getRight().copyNode(lhsNode);
						rhsNode.setContextUsage(o.hashCode());
						tmp.put(lhsNode, rhsNode);
						try {
							concurRule.addMapping(lhsNode, rhsNode);
						} catch (BadMappingException exc) {ok = false;}
					} catch (TypeException ex) {ok = false;}
				}
			}
			final Iterator<Arc> ea = pac.getTarget().getArcsSet().iterator();
			while (ea.hasNext() && ok) {
				GraphObject o = ea.next();
				if (!pac.getInverseImage(o).hasMoreElements()) {
					Arc lhsArc = (Arc) pacGraph2concurRuleLHS.getImage(o);
					// here src, tar of lhsArc must be preserved!
					// TODO:  allow src, tar to be deleted
					if (lhsArc != null
							&& concurRule.getImage(lhsArc.getSource()) != null
							&& concurRule.getImage(lhsArc.getTarget()) != null) {
						Node src = (Node) concurRule.getImage(lhsArc.getSource());					
						Node tar = (Node) concurRule.getImage(lhsArc.getTarget());
						if (src != null && tar != null) {
							try {
								Arc rhsArc = concurRule.getRight().copyArc(lhsArc, src, tar);
								try {
									concurRule.addMapping(lhsArc, rhsArc);
								} catch (BadMappingException exc) {ok = false;}
							} catch (TypeException ex) {ok = false;}
						}
					} else {
						ok = false;
					}
				}
			}
		}
		
		return ok;		
	}
	
	
	@SuppressWarnings("unused")
	private boolean checkCorrespondingAttrsOfPACs(final Rule rule) {
		boolean ok = true;
		final List<OrdinaryMorphism> pacs = rule.getPACsList();
		for(int i=0; i<pacs.size() && ok; i++) {
			OrdinaryMorphism pac = pacs.get(i);
			ok = checkCorrespondingAttrsOfApplCondition(rule, pac);
		}		
		return ok;	
	}
	
	/**
	 * Check whether each attribute member of an object in the LHS of the given rule and
	 * its corresponding attribute member of the mapped object of the given 
	 * application condition (e.g. PAC)
	 * do contain equal value or one of them is not set.
	 * 
	 * @param rule
	 * 		A rule to check
	 * @param applCond
	 * 		An application condition to check
	 * 
	 * @return
	 * 		<code>true</code> if check was successful, otherwise - <code>false</code>	
	 */
	private boolean checkCorrespondingAttrsOfApplCondition(final Rule rule, final OrdinaryMorphism applCond) {
		boolean ok = true;
		final Enumeration<GraphObject> dom = applCond.getDomain();
		while (dom.hasMoreElements() && ok) {
			GraphObject lhsObj = dom.nextElement();
			GraphObject acObj = applCond.getImage(lhsObj);
			if (acObj != null 
					&& acObj.getAttribute() != null) {
				final ValueTuple acVal = (ValueTuple) acObj.getAttribute();
				final ValueTuple lhsVal = (ValueTuple) lhsObj.getAttribute();
				for (int i=0; i<lhsVal.getNumberOfEntries(); i++) {
					ValueMember lhsMem = lhsVal.getEntryAt(i);
					ValueMember acMem = acVal.getEntryAt(lhsMem.getName());
					if (!lhsMem.isSet() || (acMem != null && !acMem.isSet())
							|| lhsMem.getExprAsText().equals(acMem.getExprAsText())) {
						continue;
					} 
					ok = false;
					break;
				}
			}
		}
		return ok;
	}
	
	/**
	 * Constructs and adds NACs and PACs to the concurrent rule based on NACs and PACs of the rule.
	 * 
	 * @param cr	the concurrent rule 
	 * @param rule	the source rule
	 * @param morph	the second morphism of the used dependency pair
	 * @param right  isomorphism of the target graph of the used dependency pair
	 * @param embMorph  left embedding morphism of the rule into concurrent rule
	 * @param alsoPACs consider PACs or not
	 * 
	 * @return	<true>, if construction was successful, otherwise return <code>false</code>.
	 */
	private boolean shiftCondsOfRuleOverMorphAndRight(
			final Rule cr,
			final Rule rule,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism right,
			final OrdinaryMorphism embMorph,
			boolean alsoPACs) {
		
		boolean ok = true;
		
		if (alsoPACs) {
			List<OrdinaryMorphism> 
			condList = shiftPACsOverMorphAndRight(cr, rule, rule.getPACs(), morph, right, embMorph);
			if (cr.notApplicable) {
				return false;				
			}
			if (condList != null && !condList.isEmpty()) {
				removeIsomorphicMorph(condList);
				for (int l=0; l<condList.size(); l++) {
					OrdinaryMorphism condCR = condList.get(l);
					condCR.getImage().setAttrContext(cr.getLeft().getAttrContext());
					condCR.setAttrContext(cr.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(condCR.getTarget(), (VarTuple)cr.getAttrContext().getVariables());
					if (!condCR.isRightTotal()
							|| !condCR.doesIgnoreAttrs()) {
						cr.addPAC(condCR);
					}
				}
			}
		}		
		if (ok) {
			List<OrdinaryMorphism> 
			condList = shiftNACsOverMorphAndRight(cr, rule, rule.getNACs(), morph, right, embMorph);
			if (condList != null && !condList.isEmpty()) {
				removeIsomorphicMorph(condList);
				for (int l=0; l<condList.size(); l++) {
					OrdinaryMorphism condCR = condList.get(l);
					condCR.getImage().setAttrContext(cr.getLeft().getAttrContext());
					condCR.setAttrContext(cr.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(condCR.getTarget(), (VarTuple)cr.getAttrContext().getVariables());
					if (!condCR.isRightTotal()
							|| !condCR.doesIgnoreAttrs()) {
						cr.addNAC(condCR);
					}
				}
			}
		}		
		return ok;
	}

	private boolean shiftCondsOfRuleOverMorphAndLeft(
			final Rule cr,
			final Rule rule,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph,
			boolean alsoPACs) {
		
		boolean ok = true;
		
		if (alsoPACs) {
			List<OrdinaryMorphism> 
			condList = shiftPACsOverMorphAndLeft(cr, rule, rule.getPACs(), morph, left, embMorph);
			if (cr.notApplicable) {
				return false;				
			}
			if (condList != null && !condList.isEmpty()) {
				removeIsomorphicMorph(condList);
				for (int l=0; l<condList.size(); l++) {
					OrdinaryMorphism condCR = condList.get(l);
					condCR.getImage().setAttrContext(cr.getLeft().getAttrContext());
					condCR.setAttrContext(cr.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(condCR.getTarget(), (VarTuple)cr.getAttrContext().getVariables());
					if (!condCR.isRightTotal()
							|| !condCR.doesIgnoreAttrs()) {
						cr.addPAC(condCR);
					}
				}
			}
		}		
		if (ok) {
			List<OrdinaryMorphism> 
			condList = shiftNACsOverMorphAndLeft(cr, rule, rule.getNACs(), morph, left, embMorph);
			if (condList != null && !condList.isEmpty()) {
				removeIsomorphicMorph(condList);
				for (int l=0; l<condList.size(); l++) {
					OrdinaryMorphism condCR = condList.get(l);
					condCR.getImage().setAttrContext(cr.getLeft().getAttrContext());
					condCR.setAttrContext(cr.getLeft().getAttrContext());
					BaseFactory.theBaseFactory.declareVariable(condCR.getTarget(), (VarTuple)cr.getAttrContext().getVariables());
					if (!condCR.isRightTotal()
							|| !condCR.doesIgnoreAttrs()) {
						cr.addNAC(condCR);
//						condCR.setName(condCR.getName()+"-LEFT-"+rule.getName());
					}
				}
			}
		}		
		return ok;
	}
	
	private List<OrdinaryMorphism> shiftPACsOverEmbMorph(
			final Rule cr,
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph) {
		
		List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();	
		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) { // morphism mapping exists
				// here: LHS -> cond is not empty
				List<OrdinaryMorphism> list = shiftCondOverEmbMorph(cond, morph);
				if (list != null && list.size() > 0) {
					for (int i=0; i<list.size(); i++) {
						OrdinaryMorphism lc = list.get(i);
						filterCondL(cond, lc, morph);
						this.filterNotNeededObjs(lc, morph);
						if (lc.getTarget().getSize() >= cond.getTarget().getSize()
								&& !this.isFalseCond(cond, lc, morph)) {
							this.adjustUnsetAttrsAboveMorphs(cond, morph, lc);
							result.add(lc);
						} 
						else {
							list.remove(lc);
							i--;
						}
					}
					if (list.size() > 1) {					
						for (int i=1; i<list.size(); i++) {
							OrdinaryMorphism lc = list.get(i);
							lc.setName(lc.getName().concat("(OR)"));
						}
						cr.addShiftedPAC(list);
					}
				}
				else {
					// shift failed, so the concurRule is not applicable because of failed PAC
					cr.notApplicable = true;
					result.clear();
					result = null;
					break;
				}				
			} else {
				// cond is global
				OrdinaryMorphism lc = shiftGlobalRuleCond1(rule, cond, morph);
				if (lc != null) {
					this.adjustUnsetAttrsAboveMorphs(cond, morph, lc);
					result.add(lc);
				}
			}
		}
		return result;
	}

	private List<OrdinaryMorphism> shiftNACsOverEmbMorph(
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph) {
		
		final List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();	
		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) {
				// here: LHS -> cond mapping not empty			
				List<OrdinaryMorphism> list = shiftCondOverEmbMorph(cond, morph);				
				if (list != null && list.size() > 0) {
					for (int i=0; i<list.size(); i++) {
						OrdinaryMorphism lc = list.get(i);
						filterCondL(cond, lc, morph);
						this.filterNotNeededObjs(lc, morph);
						if (lc.getTarget().getSize() >= cond.getTarget().getSize()
								&& !this.isFalseCond(cond, lc, morph)) {
							this.adjustUnsetAttrsAboveMorphs(cond, morph, lc);
							result.add(lc);
						}
					}
					list.clear();
				}				
			} else {
				// cond is global
				OrdinaryMorphism lc = shiftGlobalRuleCond1(rule, cond, morph);
				if (lc != null) {
					this.adjustUnsetAttrsAboveMorphs(cond, morph, lc);
					result.add(lc);
				}
			}
		}
		return result;
	}

	
	
	private List<OrdinaryMorphism> shiftPACsOverMorphAndRight(
			final Rule cr,
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism right,
			final OrdinaryMorphism embMorph) {
		
		List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();	
		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) {
				// here: LHS -> cond mapping is not empty
				List<OrdinaryMorphism> list = shiftCondOverMorph(rule, cond, morph);
				List<OrdinaryMorphism> list2 = null;
				if (list != null && list.size() > 0) {
					list2 = new Vector<OrdinaryMorphism>();
					for (int i=0; i<list.size(); i++) {
						OrdinaryMorphism c = list.get(i);
						// shift c left
						OrdinaryMorphism lc = BaseFactory.theBaseFactory.createMorphism(right.getTarget(), c.getTarget());
						Enumeration<GraphObject> dom = c.getDomain();
						while (dom.hasMoreElements()) {
							GraphObject go = dom.nextElement();
							GraphObject go1 = c.getImage(go);
							GraphObject go2 = right.getImage(go);
							if (go2 != null) {
								try {
									lc.addMapping(go2, go1);
								} catch (BadMappingException ex) {
									System.out.println("########### Shift Left FAILED!  Rule: "
											+rule.getName()+"   AC: "+cond.getName()+"  cr: "+cr.getName());
								}
							}						
						}
						if (lc.getTarget().getSize() >= cond.getTarget().getSize()
								&& !this.isFalseCond(cond, lc, embMorph)) {
							lc.setName(c.getName());
							lc.setEnabled(cond.isEnabled());
							this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
							list2.add(lc);
						}
					}
				}
				
				if (list2 != null && list2.size() > 0) {
					result.addAll(list2);
					if (list2.size() > 1) {					
						cr.addShiftedPAC(list2);
					}
				}
				else {
					// shift failed, so the concurRule is not applicable because of failed PAC
					cr.notApplicable = true;
					result.clear();
					result = null;
					break;
				}				
			} else {
				// cond is global
				OrdinaryMorphism lc = shiftGlobalRuleCond1(rule, cond, morph.compose(right));
				if (lc != null) {
					this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
					result.add(lc);
				}
			}
		}
		return result;
	}
	
	private List<OrdinaryMorphism> shiftNACsOverMorphAndRight(
			final Rule cr,
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism right,
			final OrdinaryMorphism embMorph) {
		
		List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();	
		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) { // morphism mapping exists
				// here: LHS -> cond is not empty
				List<OrdinaryMorphism> list = shiftCondOverMorph(rule, cond, morph);
				
				List<OrdinaryMorphism> list2 = new Vector<OrdinaryMorphism>();
				for (int i=0; i<list.size(); i++) {
					OrdinaryMorphism c = list.get(i);
					// shift c left
					OrdinaryMorphism lc = BaseFactory.theBaseFactory.createMorphism(right.getTarget(), c.getTarget());
					// set mapping
					Enumeration<GraphObject> dom = c.getDomain();
					while (dom.hasMoreElements()) {
						GraphObject go = dom.nextElement();
						GraphObject go1 = c.getImage(go);
						GraphObject go2 = right.getImage(go);
						if (go2 != null) {
							try {
								lc.addMapping(go2, go1);
							} catch (BadMappingException ex) {
								System.out.println("########### Shift Left FAILED!  Rule: "
										+rule.getName()+"   AC: "+cond.getName()+"  cr: "+cr.getName());
							}
						}						
					}
					if (lc.getTarget().getSize() >= cond.getTarget().getSize()
							&& !this.isFalseCond(cond, lc, embMorph)) {
						lc.setName(c.getName());
						lc.setEnabled(cond.isEnabled());
						this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
						list2.add(lc);
					}
				}
				
				if (list2 != null && list2.size() > 0) {
					result.addAll(list2);
				}
			} else {
				// cond is global
				OrdinaryMorphism lc = shiftGlobalRuleCond1(rule, cond, morph.compose(right));
				if (lc != null) {
					this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
					result.add(lc);
				}
			}
		}
		return result;
	}
	
	private List<OrdinaryMorphism> shiftPACsOverMorphAndLeft(
			final Rule cr,
			final Rule rule,
			final Enumeration<OrdinaryMorphism> conds,
			final OrdinaryMorphism morph,
			final OrdinaryMorphism left,
			final OrdinaryMorphism embMorph) {
		
		List<OrdinaryMorphism> result = new Vector<OrdinaryMorphism>();	
		while (conds.hasMoreElements()) {
			OrdinaryMorphism cond = conds.nextElement();
			if (cond.getSize() > 0) {
				// here: LHS -> cond mapping is not empty
				List<OrdinaryMorphism> list = shiftCondOverMorph(rule, cond, morph);
				List<OrdinaryMorphism> list2 = null;
				if (list != null && list.size() > 0) {
					list2 = new Vector<OrdinaryMorphism>();
					for (int i=0; i<list.size(); i++) {
						OrdinaryMorphism c = list.get(i);
						// shift c left
						OrdinaryMorphism lc = BaseFactory.theBaseFactory.shiftApplCondLeft(c, left);
						//TEST
						filterCondL(cond, lc, embMorph);
						this.filterNotNeededObjs(lc, embMorph);
						//						
						if (lc.getTarget().getSize() >= cond.getTarget().getSize()
								&& !this.isFalseCond(cond, lc, embMorph)) {
							lc.setName(c.getName());
							lc.setEnabled(cond.isEnabled());
							this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
							list2.add(lc);
						}
					}
				}
				
				if (list2 != null && list2.size() > 0) {
					result.addAll(list2);
					if (list2.size() > 1) {					
						for (int i=1; i<list2.size(); i++) {
							OrdinaryMorphism lc = list2.get(i);
							lc.setName(lc.getName().concat("(OR)"));
						}
						cr.addShiftedPAC(list2);
					}
				}
				else {
					// shift failed, so the concurRule is not applicable because of failed PAC
					cr.notApplicable = true;
					result.clear();
					result = null;
					break;
				}				
			} else {
				// cond is global
				OrdinaryMorphism lc = this.shiftGlobalRuleCond2(rule, cond, left);
				if (lc != null) {
					this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
					result.add(lc);
				}
			}
		}
		return result;
	}
	
	private List<OrdinaryMorphism> shiftNACsOverMorphAndLeft(
			final Rule cr,
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
				List<OrdinaryMorphism> list = shiftCondOverMorph(rule, cond, morph);
				
				List<OrdinaryMorphism> list2 = new Vector<OrdinaryMorphism>();
				for (int i=0; i<list.size(); i++) {
					OrdinaryMorphism c = list.get(i);
					// shift c left
					OrdinaryMorphism lc = BaseFactory.theBaseFactory.shiftApplCondLeft(c, left);
					//TEST
					filterCondL(cond, lc, embMorph);
					this.filterNotNeededObjs(lc, embMorph);
					//
					if (lc.getTarget().getSize() >= cond.getTarget().getSize()
							&& !this.isFalseCond(cond, lc, embMorph)) {
						lc.setName(c.getName());
						lc.setEnabled(cond.isEnabled());
						this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
						list2.add(lc);
					}
				}
				
				if (list2 != null && list2.size() > 0) {
					result.addAll(list2);
				}
			} else {
				// cond is global
				OrdinaryMorphism lc = this.shiftGlobalRuleCond2(rule, cond, left);
				if (lc != null) {
					this.adjustUnsetAttrsAboveMorphs(cond, embMorph, lc);
					result.add(lc);
				}
			}
		}
		return result;
	}
	
	
	private void filterNotNeededObjs(
			final OrdinaryMorphism cond,
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
		for (int i=0; i<delete.size(); i++) {
			Arc arc = (Arc) delete.get(i);
			try {
				cond.removeMapping(arc);
				try {							
					cond.getTarget().destroyArc(arc, false, false);
				} catch (TypeException ex) {}			
			} catch (BadMappingException ex1) {}
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
		for (int i=0; i<delete.size(); i++) {
			Node node = (Node) delete.get(i);
			try {
				cond.removeMapping(node);
				try {							
					cond.getTarget().destroyNode(node, false, false);
				} catch (TypeException ex) {}			
			} catch (BadMappingException ex1) {}
		}
		delete.clear();
		delete = null;
	}
	
	
	private boolean similarAttribute(final GraphObject go1, final GraphObject go2) {
		if (go1.getAttribute() != null && go2.getAttribute() != null) {
			final ValueTuple val1 = (ValueTuple) go1.getAttribute();
			final ValueTuple val2 = (ValueTuple) go2.getAttribute();			
			for (int i=0; i<val2.getNumberOfEntries(); i++) {
				ValueMember vm2 = val2.getValueMemberAt(i);
				ValueMember vm1 = val1.getValueMemberAt(vm2.getName());
				if (vm2.isSet()) {
					// mem2 is set, check mem1
					if (vm1 != null && vm1.isSet()) {
						if (//!vm2.isTransient() && !vm2.isTransient() &&
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
	
	private OrdinaryMorphism shiftGlobalRuleCond1(
			final Rule rule,
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph) {
	
		final OrdinaryMorphism condIsom = cond.getTarget().isomorphicCopy();
		if (condIsom == null)
			return null;
		
		final OrdinaryMorphism condCR = BaseFactory.theBaseFactory
										.createMorphism(morph.getTarget(), condIsom.getTarget());
		condCR.setName(cond.getName());
		condCR.setEnabled(cond.isEnabled());
		return condCR;
	}
	
	private OrdinaryMorphism shiftGlobalRuleCond2(
			final Rule rule,
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph) {
	
		final OrdinaryMorphism condIsom = cond.getTarget().isomorphicCopy();
		if (condIsom == null)
			return null;
		
		final OrdinaryMorphism condCR = BaseFactory.theBaseFactory
										.createMorphism(morph.getSource(), condIsom.getTarget());
		condCR.setName(cond.getName());
		condCR.setEnabled(cond.isEnabled());
		return condCR;
	}
	
	/**
	 * Try to shift the specified application condition <code>cond</code>
	 * over the embedding morphism <code>morph</code>. For
	 * given morphisms must hold: cond.getSource() == morph.getSource().
	 * 
	 * @return list of application condition on the graph
	 *         <code>morph.getTarget()</code>
	 */
	private List<OrdinaryMorphism> shiftCondOverEmbMorph(
			final OrdinaryMorphism cond, 
			final OrdinaryMorphism morph) {

		final List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>();

		// make an iso-copy of the rule LHS
		final OrdinaryMorphism condSrcIsom = cond.getSource().isomorphicCopy();
		if (condSrcIsom == null) {
			return null;
		}

		// extend the target graph of condSrcIsom by elements of the target
		// graph of cond
//		final OrdinaryMorphism condExt = 
		BaseFactory.theBaseFactory.extendTargetGraph1ByTargetGraph2(condSrcIsom, cond);
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
				list.add(c);
			}
		}
		return (list.isEmpty())? null: list;
	}
	
	private List<OrdinaryMorphism> shiftCondOverMorph(
				final Rule rule,
				final OrdinaryMorphism cond,
				final OrdinaryMorphism morph) {
		
		// first check: 
		// for all x from rule.lhs with cond.getImage(x) != null also morph.getImage(x) != null
		
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
		final Hashtable<Object,Object> objmap = new Hashtable<Object,Object>(condDom.size());
		
		for (int j=0; j<condDom.size(); j++) {
			GraphObject go = condDom.get(j);
			GraphObject go1 = leftToCond.getImage(go);	
			GraphObject go2 = morph.getImage(go);
			if (go1 != null && go2 != null) {
				requiredObjs.add(go1);
				objmap.put(go1, go2);
			}
		}
						
		final Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		overlaps = BaseFactory.theBaseFactory.getOverlappingByPartialPredefinedIntersection(
										condIsom.getTarget(), 
										morph.getTarget(), 
										requiredObjs, 
										objmap,
										true);
		
		final List<OrdinaryMorphism> list = new Vector<OrdinaryMorphism>();							
		while (overlaps.hasMoreElements()) {
			Pair<OrdinaryMorphism, OrdinaryMorphism> p = overlaps.nextElement();
			OrdinaryMorphism condCR = p.second;
			if (!p.second.getTarget().isEmpty()) {
				filterObjsOfRightRuleCond(rule, cond, condIsom, p.first, condCR, morph);
				condCR.setName(cond.getName().concat(String.valueOf(list.size())));
				condCR.setEnabled(cond.isEnabled());	
				condCR.shifted = true;
				list.add(condCR);
			}
		}
		if (list.isEmpty()) {
			this.failedApplConds.add(cond);
		}
		return list;
	}
	
	private boolean filterObjsOfRightRuleCond(
			final Rule r,
			final OrdinaryMorphism condL,
			final OrdinaryMorphism condIsom,
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
				if (condTargetToCondCR.getInverseImage(go_condCR).hasMoreElements())
					goInv_condCR = (Arc) condTargetToCondCR.getInverseImage(go_condCR).nextElement();
				if (goInv_condCR == null) {
					todelete.add(go_condCR);
					continue;
				}
				if (!condIsom.getInverseImage(goInv_condCR).hasMoreElements()) {
					todelete.add(go_condCR);
					continue;
				}
				Arc go_leftCR = (Arc) condCR.getInverseImage(go_condCR).nextElement();
				if (r == this.source2) {
					if (this.embLr2ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {
						Arc go_left2 = (Arc)this.embLr2ToLcr.getInverseImage(go_leftCR).nextElement();
						if (condL.getImage(go_left2) == null) {	
							condCR.removeMapping(go_condCR);
						}
					} 					
					else if (this.embLr1ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {						
						todelete.add(go_condCR);
					}
				} else if (r == this.source1) {
					if (this.embLr1ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {
						Arc go_left1 = (Arc)this.embLr1ToLcr.getInverseImage(go_leftCR).nextElement();
						if (condL.getImage(go_left1) == null) {	
							condCR.removeMapping(go_condCR);
						}
					} 					
					else if (this.embLr2ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {						
						todelete.add(go_condCR);
					}
				}
			}
		}
		for (int i=0; i<todelete.size(); i++) {
			try {
				condCR.getTarget().destroyArc((Arc)todelete.get(i), false, false);
			} catch (TypeException ex) {}
		}
		todelete.clear();
		
		// delete node to be created or without a mapping
		// from its pre-image into the condL	
 		final Iterator<Node> nodes = condCR.getTarget().getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node go_condCR = nodes.next();
			Node goInv_condCR = null;
			if (condCR.getInverseImage(go_condCR).hasMoreElements()) {
				if (condTargetToCondCR.getInverseImage(go_condCR).hasMoreElements())
					goInv_condCR = (Node) condTargetToCondCR.getInverseImage(go_condCR).nextElement();
				if (goInv_condCR == null) {
					todelete.add(go_condCR);
					continue;
				}
				if (!condIsom.getInverseImage(goInv_condCR).hasMoreElements()) {
					todelete.add(go_condCR);
					continue;
				}
				Node go_leftCR = (Node) condCR.getInverseImage(go_condCR).nextElement();
				if (r == this.source2) {
					if (this.embLr2ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {
						Node go_left2 = (Node)this.embLr2ToLcr.getInverseImage(go_leftCR).nextElement();
						if (condL.getImage(go_left2) == null) {	
							condCR.removeMapping(go_condCR);
						}
					} 					
					else if (this.embLr1ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {						
						todelete.add(go_condCR);
					}
				} else if (r == this.source1) {
					if (this.embLr1ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {
						Node go_left1 = (Node)this.embLr1ToLcr.getInverseImage(go_leftCR).nextElement();
						if (condL.getImage(go_left1) == null) {	
							condCR.removeMapping(go_condCR);
						}
					} 					
					else if (this.embLr2ToLcr.getInverseImage(go_leftCR).hasMoreElements()) {						
						todelete.add(go_condCR);
					}
				}
			}
		}
		for (int i=0; i<todelete.size(); i++) {
			try {
				condCR.getTarget().destroyNode((Node) todelete.get(i), false, false);
			} catch (TypeException ex) {}
		}
		return ok;
	}
	
		
	/**
	 * Extends the target graph of the specified morphism isom by the graph elements
	 * of the target graph of the specified morphism cond.
	 * 
	 * @param isom isomorphism of a graph
	 * @param cond	is a NAC resp. PAC, 
	 * where the source graph is the source graph of the isom
	 * 
	 * @return morphism, where the source graph is the target graph of the condition
	 * and the target graph is the target graph of the isom 
	 */
	private OrdinaryMorphism extendTargetGraph(
			final OrdinaryMorphism isom,
			final OrdinaryMorphism cond,
			final Rule concurRule) {

		Graph extTarget = isom.getTarget();
		OrdinaryMorphism morph = BaseFactory.theFactory().createMorphism(
				cond.getTarget(), extTarget);
		
		Hashtable<Node, Node> tmp = new Hashtable<Node, Node>(5);
		
		final Iterator<Node> en = cond.getTarget().getNodesSet().iterator();
		while (en.hasNext()) {
			GraphObject o = en.next();
			if (!cond.getInverseImage(o).hasMoreElements()) {
				try {
					Node n = extTarget.copyNode((Node) o);
					n.setContextUsage(o.hashCode());
					tmp.put((Node) o, n);
					try {
						morph.addMapping(o, n);
					} catch (BadMappingException exc) {}
				} catch (TypeException ex) {}
			} 
			else if (isom.getImage(cond.getInverseImage(o).nextElement()) != null) {
				try {
					Node n = (Node) isom.getImage(cond
							.getInverseImage(o).nextElement());
					n.setObjectName(o.getObjectName());
					this.adjustAttrsFromTo(o, n, concurRule);	

					morph.addMapping(o, isom.getImage(cond
							.getInverseImage(o).nextElement()));
				} catch (BadMappingException exc) {}
			} 
		}
		final Iterator<Arc> ea = cond.getTarget().getArcsSet().iterator();
		while (ea.hasNext()) {
			GraphObject o = ea.next();
			if (!cond.getInverseImage(o).hasMoreElements()) {
				Node src = tmp.get(((Arc) o).getSource());
				if (src == null) {
					src = (Node) isom.getImage(cond.getInverseImage(
							((Arc) o).getSource()).nextElement());
				}
				Node tar = tmp.get(((Arc) o).getTarget());
				if (tar == null) {
					tar = (Node) isom.getImage(cond.getInverseImage(
							((Arc) o).getTarget()).nextElement());
				}
				try {
					Arc a = extTarget.copyArc((Arc) o, src, tar);
					a.setContextUsage(o.hashCode());
					try {
						morph.addMapping(o, a);
					} catch (BadMappingException exc) {}
				} catch (TypeException ex) {}
			} 
			else if (isom.getImage(cond.getInverseImage(o).nextElement()) != null) {
				try {
					Arc a = (Arc) isom.getImage(cond
							.getInverseImage(o).nextElement());
					a.setObjectName(o.getObjectName());
					this.adjustAttrsFromTo(o, a, concurRule);
					morph.addMapping(o, isom.getImage(
							cond.getInverseImage(o).nextElement()));
				} catch (BadMappingException exc) {}
			}
		}
		return morph;
	}

	private void adjustAttrsFromTo(
			final GraphObject from,
			final GraphObject to,
			final OrdinaryMorphism morph) {
		
		if (to != null) {
			if (from.getAttribute() != null) {
				ValueTuple vt_from = (ValueTuple) from.getAttribute();
				ValueTuple vt_to = (ValueTuple) to.getAttribute();
				for (int i=0; i<vt_from.getNumberOfEntries(); i++) {
					ValueMember vm = vt_from.getValueMemberAt(i);
					if (vm.isSet()) {
						if (vm.getExpr().isVariable()) {
							if (((VarTuple) morph.getAttrContext().getVariables())
									.getVarMemberAt(vm.getExprAsText()) == null) {
								((VarTuple) morph.getAttrContext().getVariables())
										.getTupleType().addMember(vm.getHandler(), vm.getDeclaration().getTypeName(), vm.getExprAsText());
								((VarTuple) morph.getAttrContext().getVariables()).getVarMemberAt(vm.getExprAsText()).setTransient(false);
							}
						}
						ValueMember vm_to = vt_to.getValueMemberAt(vm.getName());
						if (vm_to != null) {
							vm_to.setExprAsText(vm.getExprAsText());
							vm_to.setTransient(false);
						}
					}								
				}						
			}
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
	
	private void addAttrConditionFromTo(final Rule fromRule, final Rule toRule) {	
		CondTuple condsFrom = (CondTuple) fromRule.getAttrContext().getConditions();
		if (condsFrom.isEmpty())
			return;
						
		CondTuple conds = (CondTuple) toRule.getAttrContext().getConditions();		
		
		for (int i=0; i<condsFrom.getNumberOfEntries(); i++) {
			CondMember cond = condsFrom.getCondMemberAt(i);
			if (this.isAttrCondRelevant(fromRule, cond)) {
				conds.addCondition(cond.getExprAsText());	
			}
		}
	}
	
	private boolean isAttrCondRelevant(final Rule r, final CondMember cond) {
		Vector<String> condvars = cond.getAllVariables();
		for (int i=0; i<this.failedApplConds.size(); i++) {
			OrdinaryMorphism morph = this.failedApplConds.get(i);
			if (r.getLeft() == morph.getSource()) {
				Vector<String> vars = morph.getTarget().getVariableNamesOfAttributes();
				for (int j=0; j<condvars.size(); j++) {
					if (vars.contains(condvars.get(j)))
						return false;
				}
			}
		}
		return true;
	}
	
	private List<String> adjustLeftMappedAttrs(
			final Rule cr, 
			final Rule rule, 
			final OrdinaryMorphism ruleLeftt2leftCR) {
//		System.out.println("ConcurrentRule.adjustLeftMappedAttrs::  "+cr.getName()+"        "+rule.getName());
		List<String> varToDelete = new Vector<String>();
		
		Enumeration<GraphObject> dom1 = rule.getDomain();
		while (dom1.hasMoreElements()) {
			GraphObject obj = dom1.nextElement();
			if (obj.getAttribute() != null) {
				GraphObject img = rule.getImage(obj);
				ValueTuple vt = (ValueTuple) obj.getAttribute();
				for (int i=0; i<vt.getNumberOfEntries(); i++) {
					ValueMember vm = vt.getValueMemberAt(i);						
					if (vm.isSet() && vm.getExpr().isVariable()) {
						ValueMember vmImg = ((ValueTuple) img.getAttribute()).getValueMemberAt(vm.getName());
						if (vmImg != null && vmImg.isSet() && vmImg.getExpr().isVariable()
								&& vm.getExprAsText().equals(vmImg.getExprAsText())) {
//							System.out.println(vm.getExprAsText()+"  ==  "+vmImg.getExprAsText());
							GraphObject crLeftObj = ruleLeftt2leftCR.getImage(obj);
							if (crLeftObj != null) {
//								System.out.println("crLeftObj  found");
								GraphObject crRightObj = cr.getImage(crLeftObj);
								if (crRightObj != null) {
//									System.out.println("crRightObj  found");
									ValueMember vm_crLeftObj = ((ValueTuple) crLeftObj.getAttribute()).getValueMemberAt(vm.getName());
									ValueMember vm_crRightObj = ((ValueTuple) crRightObj.getAttribute()).getValueMemberAt(vm.getName());
									if (vm_crLeftObj.isSet() && vm_crLeftObj.getExpr().isVariable()
											&& vm_crRightObj.isSet() && vm_crRightObj.getExpr().isVariable()
											&& !vm_crLeftObj.getExprAsText().equals(vm_crRightObj.getExprAsText())) {
//										System.out.println(vm_crLeftObj.getExprAsText()+"  ?=  "+vm_crRightObj.getExprAsText());
//										if (!varToDelete.contains(vm_crLeftObj.getExprAsText())) {
//											varToDelete.add(vm_crLeftObj.getExprAsText());
//										}
										vm_crLeftObj.setExpr(null);
										vm_crLeftObj.setExprAsText(vm_crRightObj.getExprAsText());
										if (!vm_crLeftObj.isTransient())
											vm_crLeftObj.setTransient(vm_crRightObj.isTransient()); // NEW
									}
								}
							}
						}
					}
				}
			}
		}
		return varToDelete;
	}
	
	/**
	 * Adjusts attribute expressions of the RHS of the concurrent rule
	 * and also its attribute conditions  according the second rule.
	 * 
	 * @param rule2 the second rule of the concurrent rule
	 * @param match2 match of PO2
	 * @param comatch2 co-match of PO2
	 * @param concurrentMorph morphism to be a concurrent rule morphism
	 */
	private void setAttrExpressionOfConcurrentRule(
			final Rule rule2, 
			final OrdinaryMorphism match2,
			final OrdinaryMorphism comatch2,			
			final OrdinaryMorphism concurrentMorph)	{

		doSetAttrExpressionOfConcurrentRule(rule2,  match2, comatch2, concurrentMorph,
				match2.getSource().getNodesSet().iterator());
		doSetAttrExpressionOfConcurrentRule(rule2,  match2, comatch2, concurrentMorph,
				match2.getSource().getArcsSet().iterator());
	}
	
	private void doSetAttrExpressionOfConcurrentRule(
			final Rule rule2, 
			final OrdinaryMorphism match2,
			final OrdinaryMorphism comatch2,			
			final OrdinaryMorphism concurrentMorph,
			Iterator<?> elems)	{

		while (elems.hasNext()) {
			GraphObject obj = (GraphObject) elems.next();
			GraphObject img = match2.getImage(obj);
			if (img != null) {
				if (obj.getAttribute() != null) {
					ValueTuple vt = (ValueTuple) obj.getAttribute();
					for (int i=0; i<vt.getNumberOfEntries(); i++) {
						ValueMember vm = vt.getValueMemberAt(i);						
						if (vm.isSet() && vm.getExpr().isVariable()) {
							ValueTuple vtImg = (ValueTuple) img.getAttribute();
							ValueMember vmImg = vtImg.getValueMemberAt(vm.getName());
							if (vmImg != null && vmImg.isSet() && vmImg.getExpr().isVariable()) {
//								System.out.println("****   "+vmImg.getExprAsText()+"  ?=  "+vm.getExprAsText());
								if (!vmImg.getExprAsText().equals(vm.getExprAsText())) {
//									System.out.println("**** setAttrExpressionOfConcurrentRule:: "+vm.getExprAsText()+"  replace by  "+ vmImg.getExprAsText());
									replaceVariable(vm.getExprAsText(), vmImg.getExprAsText(), 
											rule2, comatch2, concurrentMorph);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void replaceVariable(final String from, final String to, 
			final Rule rule2, 
			final OrdinaryMorphism comatch2,
			final OrdinaryMorphism concurrentMorph) {
		
		doReplaceVariable(from, to, concurrentMorph.getAttrContext(),
					concurrentMorph.getTarget().getNodesSet().iterator());
		doReplaceVariable(from, to, concurrentMorph.getAttrContext(),
				concurrentMorph.getTarget().getArcsSet().iterator());
		
		BaseFactory.theFactory().renameVariableOfCondition(
				concurrentMorph.getAttrContext(),
				(CondTuple)concurrentMorph.getAttrContext().getConditions(), 
				from, to);
		
		removeVariableOfAttrContext(concurrentMorph.getAttrContext(), from);
	}
	
	private void doReplaceVariable(
			final String from, 
			final String to, 
			final AttrContext ac,
			final Iterator<?> elems) {
		
		while (elems.hasNext()) {
			GraphObject obj = (GraphObject) elems.next();
			if (obj.getAttribute() != null) {
				ValueTuple vt = (ValueTuple) obj.getAttribute();
				for (int i=0; i<vt.getNumberOfEntries(); i++) {
					ValueMember vm = vt.getValueMemberAt(i);					
					if (vm.isSet()) {
						if (vm.getExpr().isVariable()) {
							if (vm.getExprAsText().equals(from)) {
								vm.setExpr(null);
								vm.setExprAsText(to);
								
//								System.out.println("attr variable after replace variable:  "+vm.getExprAsText());
								VarMember var = addVariableToAttrContext(ac, from, to);
								
								if (!vm.isTransient() && var != null)
									vm.setTransient(true);
							}
						} else if (vm.getExpr().isComplex()) {						
							Vector<String> v = new Vector<String>();
							vm.getExpr().getAllVariables(v);
							if (v.contains(from)) {
								BaseFactory.theFactory().renameVariableOfExpression(ac, vt, from, to);
//								System.out.println("attr expression after replace variable:  "+vm.getExprAsText());
								addVariableToAttrContext(ac, from, to);
							}
						}
					}
				}
			}			
		}
	}
	
	private void addUndeclaredVariableOfExpression(final OrdinaryMorphism concurrentMorph) {
		doAddUndeclaredVariableOfExpression( concurrentMorph,
				concurrentMorph.getTarget().getNodesSet().iterator());
		doAddUndeclaredVariableOfExpression( concurrentMorph,
				concurrentMorph.getTarget().getArcsSet().iterator());
	}
	
	private void doAddUndeclaredVariableOfExpression(
			final OrdinaryMorphism concurrentMorph,
			final Iterator<?> elems) {
		final VarTuple vars = (VarTuple) concurrentMorph.getAttrContext().getVariables();
		while (elems.hasNext()) {
			GraphObject obj = (GraphObject) elems.next();
			if (obj.getAttribute() != null) {
				ValueTuple vt = (ValueTuple) obj.getAttribute();
				for (int i=0; i<vt.getNumberOfEntries(); i++) {
					ValueMember vm = vt.getValueMemberAt(i);					
					if (vm.isSet()) {
						if (vm.getExpr().isComplex()) {								
							Vector<String> v = new Vector<String>();
							vm.getExpr().getAllVariables(v);
							for (int j=0; j<v.size(); j++) {
								if (vars.getVarMemberAt(v.get(j)) == null) {
									addVariableToAttrContext(concurrentMorph.getAttrContext(), null, v.get(j));
								}
							}
						}
					}
				}
			}			
		}
	}
	
	private VarMember addVariableToAttrContext(final AttrContext attrContext, 
			final String from, final String to) {
		VarTuple vars = (VarTuple) attrContext.getVariables();
		VarMember v = null;
		if (vars.getVarMemberAt(to) == null) {
			if (from != null) {
				vars.getTupleType().addMember(vars.getVarMemberAt(from).getDeclaration().getHandler(),
					vars.getVarMemberAt(from).getDeclaration().getTypeName(), 
					to);
			} else {
				vars.getTupleType().addMember(AvailableHandlers.newInstances()[0],
						"String", 
						to);
			}
			v = vars.getVarMemberAt(to);
			v.setTransient(true);
		}
		return v;
	}
	
	private void removeVariableOfAttrContext(
			final AttrContext attrContext, 
			final String var) {
		if (this.concurrentRule != null) {
			VarTuple vars = (VarTuple) attrContext.getVariables();
			if (vars.getVarMemberAt(var) != null) {
				if (!this.concurrentRule.getLeft().getVariableNamesOfAttributes().contains(var)) {
					if (!this.concurrentRule.getRight().getVariableNamesOfAttributes().contains(var)) {					
						if (!isUsedInGraph(this.concurrentRule.getNACs(), var)) {
							if (!isUsedInGraph(this.concurrentRule.getPACs(), var)) {												
								vars.getTupleType().deleteMemberAt(var);
//								System.out.println("ConcurrentRule.removeVariableOfAttrContext::  removed: "+var);
							}
						} 
					} 
				}
			}
		}
	}
	
	public boolean isDisjoint() {
		return this.disjoint;
	}
	
	private void setInputParameterIfNeeded(final Rule concurRule) {
//		final Hashtable<Graph, Vector<String>> 
//		graph2Varnames = new Hashtable<Graph, Vector<String>>();
		
		VarTuple vars = (VarTuple) concurRule.getAttrContext().getVariables();
		Vector<String> 
		varNamesRHS = concurRule.getTarget().getVariableNamesOfAttributes();
		Vector<String> 
		varNamesLHS = concurRule.getSource().getVariableNamesOfAttributes();
		
		for (int i=0; i<vars.getNumberOfEntries(); i++) {			
			VarMember var = vars.getVarMemberAt(i);
			if (varNamesRHS.contains(var.getName())) {
				boolean found = false;				
				if (varNamesLHS.contains(var.getName())) {
					found = true;
				} else if (!var.isTransient()) {
					if (!found) {
						var.setInputParameter(true);	
					}
					/*
					final List<OrdinaryMorphism> nacs = concurRule.getNACsList();
					for(int j=0; j<nacs.size() && !found; j++) {
						OrdinaryMorphism morph = nacs.get(j);
						if (graph2Varnames.get(morph.getTarget()) == null) {
							graph2Varnames.put(morph.getTarget(), morph.getTarget().getVariableNamesOfAttributes());
						}
						Vector<String> 
						varNames = graph2Varnames.get(morph.getTarget());
						if (varNames.contains(var.getName())) {
							found = true;
						}					
					}				
					if (!found) {	
						final List<OrdinaryMorphism> pacs = concurRule.getPACsList();
						for(int j=0; j<pacs.size() && !found; j++) {
							OrdinaryMorphism morph = pacs.get(j);
						
							if (graph2Varnames.get(morph.getTarget()) == null) {
								graph2Varnames.put(morph.getTarget(), morph.getTarget().getVariableNamesOfAttributes());
							}
							Vector<String> 
							varNames = graph2Varnames.get(morph.getTarget());
							if (varNames.contains(var.getName())) {
								found = true;
							}
						}
					}
					*/
				}
			}
		}		
	}
	
	private boolean isUsedInGraph(Enumeration<OrdinaryMorphism> list, String varName) {
		while (list.hasMoreElements()) { 
			Graph g = list.nextElement().getTarget();
			if (g.getVariableNamesOfAttributes().contains(varName)) {
//				System.out.println(g.getName()+"   uses  "+varName);
				return true;
			}
		}
		return false;
	}
	
	private boolean isFalseCond(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism condL,
			final OrdinaryMorphism leftEmbMorph) {

		Iterator<Node> iter = cond.getSource().getNodesSet().iterator();
		while (iter.hasNext()) {
			GraphObject go = iter.next();
			GraphObject go1 = leftEmbMorph.getImage(go);
			if (cond.getImage(go) == null
					&& condL.getImage(go1) != null) {
				return true;
			}
		}
		Iterator<Arc> iter2 = cond.getSource().getArcsSet().iterator();
		while (iter2.hasNext()) {
			GraphObject go = iter2.next();
			GraphObject go1 = leftEmbMorph.getImage(go);
			if (cond.getImage(go) == null
					&& condL.getImage(go1) != null) {
				return true;
			}
		}
		return false;
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
	
}
