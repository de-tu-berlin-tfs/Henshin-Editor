package agg.xt_basis;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

import agg.util.Change;
import agg.util.Link;
import agg.attribute.AttrContext;
import agg.attribute.AttrMapping;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.AttrImplException;
import agg.attribute.impl.VarTuple;

/**
 * This class implements a direct graph transformation step in the single
 * pushout (SPO) approach to algebraic graph transformation. The transformation
 * is performed <i>in place</i>, i.e. the host graph is modified according to
 * the rule's instructions.
 */
public class Step {

	private boolean computeColimitBasedPushout = false;


	public Step() {}
	
	
	public final  Morphism executeColimBased(final Match match) throws TypeException {
		this.computeColimitBasedPushout = true;
		return execute(match, false, false);
	}
	
	public final  Morphism executeColimBased(
			final Match match, 
			boolean allowAttrVarsInGraph) throws TypeException {
		this.computeColimitBasedPushout = true;
		return execute(match, allowAttrVarsInGraph, false);
	}
	
	public final  Morphism executeColimBased(
			final Match match, 
			boolean allowAttrVarsInGraph,
			boolean wrtEqualAttrVarName) throws TypeException {
		this.computeColimitBasedPushout = true;
		return execute(match, allowAttrVarsInGraph, wrtEqualAttrVarName);
	}
	
	
	/**
	 * Perform an in-place graph transformation step: apply the rule given by
	 * <code>match.getRule()</code> via <code>match</code> on the host graph
	 * given by <code>match.getImage()</code>. The host graph is modified to
	 * represent the result of the rule application.
	 * 
	 * @return the co-match morphism from the right side of the rule into the
	 *         result graph.
	 * @see agg.xt_basis.Morphism#getImage() Return NULL if execute of the step
	 *      is failed.
	 */
	public final  Morphism execute(final Match match) throws TypeException {
		return execute(match, false, false);
	}

	
	/**
	 * Perform an in-place graph transformation step 
	 * with respecting of
	 * allowing usage of variables for values of
	 * attributes of graph objects in a graph to be transformed.
	 */
	public final  Morphism execute(
			final Match match, 
			boolean allowAttrVarsInGraph) throws TypeException {
		
		return execute(match, allowAttrVarsInGraph, false);
	}
	
	/**
	 * Perform an in-place graph transformation step <br> 
 	 * with respecting of allowing usage variables for values of
	 * attributes of objects inside of a graph, <br> 
	 * and when usage of variables is allowed <br>
	 * then do it respecting equal names of variables
	 * inside of graph and the right hand side if of the rule of the given match.
	 */
	public final  Morphism execute(
			final Match match, 
			boolean allowAttrVarsInGraph,
			boolean wrtEqualAttrVarName) throws TypeException {
			
//		int typeLevel = match.getImage().getTypeSet().getLevelOfTypeGraphCheck();
		
		final Rule rule = match.getRule();
		final Graph aHostGraph = match.getImage();

		OrdinaryMorphism 
		aMatchStar = new OrdinaryMorphism(
						rule.getImage(),
						aHostGraph, 
						match.getAttrManager().newContext(
								AttrMapping.PLAIN_MAP));
		
		try {
			match.getAttrContext().freeze();
			
			// use colimits to get pushout
			if (this.computeColimitBasedPushout) {
//				System.out.println("Step: Colimit Based Pushout ... ");
				final ColimDiagram aPushoutDiagram = new ColimDiagram(aHostGraph);
				aPushoutDiagram.addNode(aHostGraph);
				aPushoutDiagram.addNode(rule.getOriginal());
				aPushoutDiagram.addNode(rule.getImage());
				aPushoutDiagram.addEdge(rule);
				aPushoutDiagram.addEdge(match);
				aPushoutDiagram.requestEdge(aMatchStar);
				
				aPushoutDiagram.computeColimit();			
			}			
			// use simplified pushout
			else if (!match.isInjective() && !match.isGluingConditionSet()) {
				aMatchStar = pushoutOfNonInjectiveMatch(rule, match, aMatchStar);
			} else {
				aMatchStar = pushout(rule, match, aMatchStar);
			}
			
			if (aMatchStar == null) {
				throw new TypeException("Step failed!");
			}
			
			aMatchStar.setName("CoMorphOf_"+match.getName());
			
			try {
				computeAttributes(match, aMatchStar, match.getAttrContext(),
									allowAttrVarsInGraph, wrtEqualAttrVarName);
			} catch (AttrImplException ex1) {
				aMatchStar = null;
				match.getAttrContext().defreeze();
				if (match.getTarget().isAttributed()) {
					match.getRule().restoreVariableDeclaration();
					((VarTuple) match.getAttrContext().getVariables())
							.unsetInputParameters();
				}
				throw new TypeException(ex1.getMessage());
			}
			
			match.getAttrContext().defreeze();
			match.setCoMorphism(aMatchStar);
			
		} catch (TypeException ex) {
			aMatchStar = null;
			match.getAttrContext().defreeze();
			if (match.getTarget().isAttributed()) {
				match.getRule().restoreVariableDeclaration();
				((VarTuple) match.getAttrContext().getVariables())
						.unsetInputParameters();
			}
			throw (ex);
		}

		try {
			match.updateAttrMappings();
		} catch (BadMappingException exc) {
		}

		// the variables used in NACs can lose their declaration,
		// if step was successful, so restore the declaration.
		if (match.getTarget().isAttributed()) {
			match.getRule().restoreVariableDeclaration();
			((VarTuple) match.getAttrContext().getVariables())
					.unsetInputParameters();
		}
		return aMatchStar;
	}

	/*
	 * Here <br>
	 * Rule r = match.getRule() <br>
	 * match:   rule.LHS -> G <br>
	 * comatch: rule.RHS -> G <br>
	 * G is changed after in-place trafo step
	 */
	private  final void computeAttributes(
			final Match match,
			final OrdinaryMorphism comatch, 
			final AttrContext context, 
			boolean allowVariables,
			boolean wrtEqualAttrVarName) throws AttrImplException {
		
		if (!comatch.getTarget().isAttributed())
			return;

		final List<GraphObject> done = new Vector<GraphObject>(match.getSource().getSize());
		final Rule r = match.getRule();
		GraphObject lhsObj, rhsObj, gObj;
		
//		compute attributes of preserved objects
		final Enumeration<GraphObject> dom = match.getDomain();
		while (dom.hasMoreElements()) {		
			lhsObj = dom.nextElement();				
			gObj = match.getImage(lhsObj);
		
			if (!gObj.attrExists() //gObj.getAttribute() == null
					|| done.contains(gObj)) {
				continue;
			}
			done.add(gObj);	
			rhsObj = r.getImage(lhsObj);
			if (rhsObj != null && rhsObj.getAttribute() != null) {
//				if (rhsObj.getAttribute() == null) {
//					throw new AttrImplException("Rule:  "+r.getName()+":  Attribute of RHS preserved object failed (null).");
//				}
				
				final ValueTuple rai = (ValueTuple) rhsObj.getAttribute();
				final ValueTuple ai = (ValueTuple) gObj.getAttribute();
				if (!allowVariables) {
					match.getTarget().propagateChange(
							new Change(Change.WANT_MODIFY_OBJECT, gObj));

					ai.apply(rai, context);
				} else {
					ai.apply(rai, context, allowVariables, wrtEqualAttrVarName);					
				}
			}
		}
		
		// now compute attributes of new objects only
//		Enumeration<GraphObject> anInvImage;
		final Enumeration<GraphObject> anObjIter = comatch.getCodomain();
		while (anObjIter.hasMoreElements()) {
			rhsObj = anObjIter.nextElement();
			if (r.getInverseImage(rhsObj).hasMoreElements())
				continue;
			
			gObj = comatch.getImage(rhsObj);
			if (!gObj.attrExists()) //gObj.getAttribute() == null)
				continue;
			
			if (rhsObj.getAttribute() == null) {
				throw new AttrImplException("Rule:  "+r.getName()+":  Attribute of RHS new object failed (null).");
			}
				
			final ValueTuple rai = (ValueTuple) rhsObj.getAttribute();
			final ValueTuple ai = (ValueTuple) gObj.getAttribute();
			if (!allowVariables) {
				comatch.getTarget().propagateChange(
						new Change(Change.WANT_MODIFY_OBJECT, gObj));
				
				try {
					ai.apply(rai, context);
				} catch (AttrImplException ex) {
					System.out.println("TestStep.computeAttributes:  Rule:  "+r.getName()+" : "+ex.getMessage());
//					throw new AttrImplException("Rule:  "+r.getName()+" : "+ex.getMessage());
				}
			} else {
					ai.apply(rai, context, allowVariables);
			}
		}				
	}
	
	
	/*
	 * Calculates the pushout of two morphisms. L----r--->R and L----m--->G to
	 * get the co-morphism p R----p--->G It returns the resulting co-morphism p.
	 */
	private  synchronized OrdinaryMorphism pushout(
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m,
			final OrdinaryMorphism p) throws TypeException {
		// p is co-match of m
		
		if (!m.isTotal()) {
			return null;
		}
		
		final Hashtable<GraphObject, Link> hashMap = new Hashtable<GraphObject, Link>();
		
		final Graph L = r.getOriginal();		
		final Graph G = m.getTarget();

		boolean sameType = (G.getTypeSet() == r.getTarget().getTypeSet());
		
		fillHashMap(hashMap, r, m, L);
		
		/*
		 * Now we link together all graphobjects which somehow are mapped to
		 * each other. This UNION/FIND structure enables us to find quickly
		 * nodes which must be identified.
		 */
		// first link nodes
		Iterator<Node> nodes = L.getNodesSet().iterator();
		while (nodes.hasNext()) {
			GraphObject lgo = nodes.next();
			GraphObject img1 = r.getImage(lgo);
			GraphObject img2 = m.getImage(lgo);
			Link l = hashMap.get(lgo);		
			if (img1 != null && img2 != null) {
				l.link(hashMap.get(img1)).link(hashMap.get(img2));			
				try {
					p.addMappingFast(img1, img2);					
					(hashMap.get(img1)).set(img2);
				} catch (BadMappingException ex1) {
					throw new TypeException(ex1.getLocalizedMessage());
				}				
			} 
		} 
		
		// now link edges
		Iterator<Arc> arcs = L.getArcsSet().iterator();
		while (arcs.hasNext()) {
			GraphObject lgo = arcs.next();
			GraphObject img1 = r.getImage(lgo);
			GraphObject img2 = m.getImage(lgo);
			Link l = hashMap.get(lgo);			
			if (img1 != null && img2 != null) {
				l.link(hashMap.get(img1)).link(hashMap.get(img2));				
				try {
					p.addMappingFast(img1, img2);
					(hashMap.get(img1)).set(img2);
				} catch (BadMappingException ex1) {
					// eventually a glue edge case, check it!
					if (r.getInverseImageList(img1).size() > 1) {
//					here OrdinaryMorphism.checkEdgeSourceTargetCompatibility(orig, image) sent exception,
//					do ignore it because of glue edges
					} else {
						throw new TypeException(ex1.getLocalizedMessage());
					}
				}				
			} 
		}
		
		// first destroy arcs
		arcs = L.getArcsSet().iterator();
		while (arcs.hasNext()) {
			GraphObject lgo = arcs.next();
			GraphObject img2 = m.getImage(lgo);
			if (r.getImage(lgo) == null && img2 != null) {
				destroyArc((Arc)img2, G);			
			} 			
		}
		
		// destroy nodes 
		nodes = L.getNodesSet().iterator();
		while (nodes.hasNext()) {
			GraphObject lgo = nodes.next();
			GraphObject img2 = m.getImage(lgo);
			if (r.getImage(lgo) == null && img2 != null) {			
				destroyNode((Node)img2, G);
			} 
		}
		
		/* Now we can create new objects in G. We first create nodes. */
		final Iterator<Node> nodesP = p.getOriginal().getNodesSet().iterator(); // RHS nodes
		while (nodesP.hasNext()) {
			Node n = nodesP.next(); // RHS node
			
			Link l = (hashMap.get(n)).find();
			Node n2 = (Node) l.get();
			
			if (n2 == null) {
				try {
					createNode(n, G, p, sameType);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}
			} else {
				// non-injective rule? try to glue nodes of the host graph
				try {
					glueNodesOfSameImageNode(n, r, m, p, G);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}
			}
		}
		
		/* now create new edges in G */
		final Iterator<Arc> arcsP = p.getOriginal().getArcsSet().iterator(); // RHS edges
		while (arcsP.hasNext()) {
			Arc a = arcsP.next();
			Link l = hashMap.get(a).find();
			Arc a2 = (Arc) l.get();
			if (a2 == null) {
				try {
					createArc(a, G, p, sameType);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}
				
			} else {
				// non-injective rule? try to glue arcs of the host graph
				try {
					glueArcsOfSameImageArc(a, r, m, p, G);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}			
			}
		}
		
		return p;
	}

	/*
	 * Calculates the pushout of two morphisms. L----r--->R and L----m--->G to
	 * get the co-morphism p R----p--->G It returns the resulting co-morphism p.
	 * The morphism m is NON-INJECTIVE and gluing condition must not be applied.
	 */
	private  synchronized OrdinaryMorphism pushoutOfNonInjectiveMatch(
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m,
			final OrdinaryMorphism p) throws TypeException {
		// p is co-match of m
		
		if (!m.isTotal()) {
			return null;
		}
				
		final Hashtable<GraphObject, Link> hashMap = new Hashtable<GraphObject, Link>();
		
		final Graph L = r.getOriginal();		
		final Graph G = m.getTarget();

		boolean sameType = (G.getTypeSet() == r.getTarget().getTypeSet());
		
		fillHashMap(hashMap, r, m, L);
		
		/*
		 * Now we link together all graph objects which somehow are mapped to
		 * each other. This UNION/FIND structure enables us to find quickly
		 * nodes which must be identified.
		 */
		// first link nodes
		Iterator<Node> nodes = L.getNodesSet().iterator();
		while (nodes.hasNext()) {
			GraphObject lgo = nodes.next();
			GraphObject img1 = r.getImage(lgo);
			GraphObject img2 = m.getImage(lgo);
			Link l = hashMap.get(lgo);
			if (img1 != null && img2 != null) {
				l.link(hashMap.get(img1)).link(hashMap.get(img2));			
				try {
					p.addMappingFast(img1, img2);					
					(hashMap.get(img1)).set(img2);
				} catch (BadMappingException ex1) {
					throw new TypeException(ex1.getLocalizedMessage());
				}
			} 
		} 
		
		// now link edges
		Iterator<Arc> arcs = L.getArcsSet().iterator();
		while (arcs.hasNext()) {
			GraphObject lgo = arcs.next();
			GraphObject img1 = r.getImage(lgo);
			GraphObject img2 = m.getImage(lgo);
			Link l = hashMap.get(lgo);
			if (img1 != null && img2 != null) {
				l.link(hashMap.get(img1)).link(hashMap.get(img2));				
				try {
					p.addMappingFast(img1, img2);
					(hashMap.get(img1)).set(img2);
				} catch (BadMappingException ex1) {
					// eventually a glue edge case, check it!
					if (r.getInverseImageList(img1).size() > 1) {
//					here OrdinaryMorphism.checkEdgeSourceTargetCompatibility(orig, image) sent exception,
//					do ignore it because of glue edges
					} else {
						throw new TypeException(ex1.getLocalizedMessage());
					}
				}
			}  			
		}
			
		// first destroy arcs
		arcs = L.getArcsSet().iterator();
		while (arcs.hasNext()) {
			GraphObject lgo = arcs.next();
			GraphObject img2 = m.getImage(lgo);
			if (r.getImage(lgo) == null && img2 != null) {				
				if (!m.isIdentificationSet()) {					
					if (r instanceof ParallelRule
							&& m instanceof Match) {	
						if (!canDeleteWhenParallelRule(G, r, m, img2)) 
							throw new TypeException(
							"Step pushout: Cannot finish transformation step. Delete edge of parallel rule failed!");
					}
					else {						
		//				destroyArc((Arc)img2, G);
						G.destroyArcFast((Arc)img2);
					}
				} else {
					boolean canDelete = true;
					final List<GraphObject> lgos = m.getInverseImageList(img2);
					if (lgos.size() > 1) {
						for (int i=0; i<lgos.size(); i++) {
							if (r.getImage(lgos.get(i)) != null) {
								canDelete = false;
								break;
							}
						}
					}
					if (canDelete)
						destroyArc((Arc)img2, G);
				}			
			} 			
		}
		
		// destroy nodes
		nodes = L.getNodesSet().iterator();
		while (nodes.hasNext()) {
			GraphObject lgo = nodes.next();
			GraphObject img2 = m.getImage(lgo);
			if (r.getImage(lgo) == null && img2 != null) {				
				if (!m.isIdentificationSet()) {
					if (r instanceof ParallelRule
							&& m instanceof Match) {	
						if (!canDeleteWhenParallelRule(G, r, m, img2)) 
							throw new TypeException(
							"Step pushout: Cannot finish transformation step. Delete node of parallel rule failed!");
					}
					else {						
						destroyNode((Node)img2, G);
					}
				} else {
					boolean canDelete = true;
					final List<GraphObject> lgos = m.getInverseImageList(img2);
					if (lgos.size() > 1) {
						for (int i=0; i<lgos.size(); i++) {
							if (r.getImage(lgos.get(i)) != null) {
								canDelete = false;
								break;
							}
						}
					}
					if (canDelete)
						destroyNode((Node)img2, G);
				}
			} 
		}
		
		/* Now we can create new objects in G. We first create nodes. */
		final Iterator<Node> nodesP = p.getOriginal().getNodesSet().iterator(); // RHS nodes
		while (nodesP.hasNext()) {
			Node n = nodesP.next(); // RHS node
			
			Link l = (hashMap.get(n)).find();
			Node n2 = (Node) l.get();
			
			if (n2 == null) {
				try {
					createNodeOfNonInjectiveMatch(hashMap, n, G, r, m, p, sameType);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}
			} else {
				// non-injective rule? try to glue nodes of the host graph
				try {
					glueNodesOfSameImageNode(n, r, m, p, G);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}
			}
		}
		
		/* now create new edges in G */
		final Iterator<Arc> arcsP = p.getOriginal().getArcsSet().iterator(); // RHS edges
		while (arcsP.hasNext()) {
			Arc a = arcsP.next();
			
			Link l = hashMap.get(a).find();
			Arc a2 = (Arc) l.get();
			
			if (a2 == null) {
				try {
					createArcOfNonInjectiveMatch(hashMap, a, G, r, m, p, sameType);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}
				
			} else {
				// non-injective rule? try to glue arcs of the host graph
				try {
					glueArcsOfSameImageArc(a, r, m, p, G);
				} catch (TypeException ex) {
					throw new TypeException(ex.getLocalizedMessage());
				}			
			}
		}
		
		return p;
	}
	
	private boolean canDeleteWhenParallelRule(
			final Graph g, 
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m, 
			final GraphObject go) {
		
		boolean canDelete = true;
		if (m instanceof Match
				&& r instanceof ParallelRule) {		
			final List<GraphObject> lgos = m.getInverseImageList(go);
			if (lgos.size() > 1) {
				GraphObject go1 = lgos.get(0);
				GraphObject img_go1 = r.getImage(go1);
				final OrdinaryMorphism embedding = ((ParallelRule)r).getLeftEmbeddingOfObject(go1);
				for (int i=0; i<lgos.size(); i++) {
					GraphObject go2 = lgos.get(i);
					if ((img_go1 != null || r.getImage(go2) != null)
							&& (((ParallelRule)r).getLeftEmbeddingOfObject(go2)
									!= embedding)) {
						canDelete = false;
						break;
					}
				}
			}
		}
		return canDelete;
	}
	
	private  void fillHashMap(
			final Hashtable<GraphObject, Link> hashMap,
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m,
			final Graph left) {
		
		Iterator<?> iter = left.getNodesSet().iterator();
		while (iter.hasNext()) {
			GraphObject go = (GraphObject)iter.next();
			hashMap.put(go, new Link());
			hashMap.put(m.getImage(go), new Link());
		}
		
		iter = left.getArcsSet().iterator();
		while (iter.hasNext()) {
			GraphObject go = (GraphObject) iter.next();
			hashMap.put(go, new Link());
			hashMap.put(m.getImage(go), new Link());
		}
		
		iter = r.getImage().getNodesSet().iterator();
		while (iter.hasNext()) {
			hashMap.put((GraphObject)iter.next(), new Link());
		}
		
		iter = r.getImage().getArcsSet().iterator();
		while (iter.hasNext()) {
			hashMap.put((GraphObject)iter.next(), new Link());
		}
	}
	
	private  void createNode(
			final Node n, 
			final Graph g, 
			final OrdinaryMorphism p,
			boolean sameType) 
	throws TypeException {
		
		try {
			if (sameType) {
	//			long t = System.nanoTime();
				createNodeOfSameType(n, g, p);
	//			System.out.println("node created in time:  "+(System.nanoTime()-t)+"nano");
			}
			else {
				createNodeOfSimilarType(n, g, p);
			}
		} catch (TypeException ex) {
	//		System.out.println(n.getType().getName()+"   FAILED!");
			throw new TypeException(ex.getLocalizedMessage());
		}
	}
	
	private  void createNodeOfNonInjectiveMatch(
			final Hashtable<GraphObject, Link> hashMap,
			final Node n, 
			final Graph g, 
			final OrdinaryMorphism r,
			final OrdinaryMorphism m,
			final OrdinaryMorphism p,
			boolean sameType) 
	throws TypeException {
		
		if (!m.isIdentificationSet()) {
			final Node go = (Node)hashMap.get(n).get();
			if (go != null) {
				final List<GraphObject> lgos = m.getInverseImageList(go);
				if (lgos.size() > 1) {
					for (int i=0; i<lgos.size(); i++) {
						if (r.getImage(lgos.get(i)) == null) {
							return;
						}
					}
				}
			} else if (r.getInverseImage(n).hasMoreElements()) {
				return;
			}
		}
		
		createNode(n, g, p, sameType);
	}
	
	private  void destroyNode(final Node n, final Graph g) {
//		this.todelete.add(n);
//		destroy node without img1					
		// first destroy all outgoing edges
		Iterator<?> iter = n.getOutgoingArcsSet().iterator();
		while (iter.hasNext()) {
			GraphObject a = (GraphObject)iter.next();
			g.destroyArcFast((Arc) a);
			iter = n.getOutgoingArcsSet().iterator();
		}
		// then destroy all incoming edges
		iter = n.getIncomingArcsSet().iterator();
		while (iter.hasNext()) {
			GraphObject a = (GraphObject)iter.next();
			g.destroyArcFast((Arc) a);
			iter = n.getIncomingArcsSet().iterator();
		}

		// and now destroy the node
		g.destroyNodeFast(n);	
	} 
	
	private  void createArc(
			final Arc a, 
			final Graph g, 
			final OrdinaryMorphism p,
			boolean sameType) 
	throws TypeException {
		
		GraphObject src = p.getImage(a.getSource());
		GraphObject tgt = p.getImage(a.getTarget());		
		if (src != null && tgt != null) {
			try {
				if (sameType) {
//					long t = System.nanoTime();
					createArcOfSameType(a, src, tgt, g, p);
				}
				else {
					createArcOfSimilarType(a, src, tgt, g, p);
				}
			} catch (TypeException ex) {
				throw new TypeException(ex.getLocalizedMessage());
			}
		}
	}
	
	private  void createArcOfNonInjectiveMatch(
			final Hashtable<GraphObject, Link> hashMap,
			final Arc a, 
			final Graph g, 
			final OrdinaryMorphism r,
			final OrdinaryMorphism m,
			final OrdinaryMorphism p,
			boolean sameType) 
	throws TypeException {
		
		if (!m.isIdentificationSet()) {
			final Arc go = (Arc)hashMap.get(a).get();
			if (go != null) {
				final List<GraphObject> lgos = m.getInverseImageList(go);
				if (lgos.size() > 1) {
					for (int i=0; i<lgos.size(); i++) {
						if (r.getImage(lgos.get(i)) == null) {
							return;
						}
					}
				}
			} else if (r.getInverseImage(a).hasMoreElements()) {
				return;
			}			
		}
		
		createArc(a, g, p, sameType);
	}
	
	private  void destroyArc(final Arc a, final Graph g) {
		g.destroyArcFast(a);
	}
	
	private  void glueNodesOfSameImageNode(
			final Node n,
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m,
			final OrdinaryMorphism p,
			final Graph g) throws TypeException {
		
		// non-injective rule? try to glue nodes of the host graph			
		boolean glued = false;				
		final List<GraphObject> origs = r.getInverseImageList(n);
		if (!origs.isEmpty()) {
			final Hashtable<Arc, Arc> arc2arcimg = new Hashtable<Arc, Arc>();
			
			final GraphObject ol1 = origs.get(0);
			final GraphObject og1 = m.getImage(ol1);					
			
			if (og1 != null ) {						
				for (int j=1; j<origs.size(); j++) {
					final Node ol2 = (Node) origs.get(j);
					final Node og2 = (Node) m.getImage(ol2);								
					
					if (og2 != null ) {								
						Iterator<?> iter = ol2.getIncomingArcsSet().iterator();
						while (iter.hasNext()) {
							final Arc arc = (Arc)iter.next();
							if (m.getImage(arc) != null)
								arc2arcimg.put(arc, (Arc) m.getImage(arc)); 
						}
						iter = ol2.getOutgoingArcsSet().iterator();
						while (iter.hasNext()) {
							final Arc arc = (Arc)iter.next();
							if (m.getImage(arc) != null)
								arc2arcimg.put(arc, (Arc) m.getImage(arc));
						}
												
						if (g.glue(og1, og2))  {
							glued = true;
						} else {
							throw new TypeException(
							"Step.pushout: Cannot glue nodes of type  "+og1.getType().getName()+" !");
						}
					} else if (m.isIdentificationSet()){
						throw new TypeException(
								"Step.pushout: Cannot finish transformation step. Identification condition has failed!");
					}
				}
								
				try {
					if (p.getImage(n) == null)
						p.addPlainMapping(n, og1);								
				} catch (BadMappingException ex1) {							
						throw new TypeException(ex1.getLocalizedMessage());
				}
				
				if (glued) {	
					for (int j=1; j<origs.size(); j++) {
						final GraphObject ol2 = origs.get(j);
						try {
							m.addObjectPlainMapping(ol2, og1);	
						} catch (BadMappingException ex1) {							
							throw new TypeException(ex1.getLocalizedMessage());
						}
					}
					// reset mappings of new Source/Target of Edges
					final Enumeration<Arc> inoutarcs = arc2arcimg.keys();
					while (inoutarcs.hasMoreElements()) {
						final Arc arc = inoutarcs.nextElement();
						try {
							m.addObjectPlainMapping(arc, arc2arcimg.get(arc));
						} catch (BadMappingException ex1) {							
							throw new TypeException(ex1.getLocalizedMessage());
						}	
					}																									
				}
			} else if (m.isIdentificationSet()){
				throw new TypeException(
						"Step.pushout: Cannot finish transformation step. Identification condition has failed!");
			}
		}	
	}
	
	private  void glueArcsOfSameImageArc(
			final Arc a,
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m,
			final OrdinaryMorphism p,
			final Graph g) throws TypeException {
		
		// non-injective rule? try to glue arcs of the host graph
		boolean glued = false;
		final List<GraphObject> origs = r.getInverseImageList(a);
		if (!origs.isEmpty()) {
			GraphObject ol1 = origs.get(0);
			GraphObject og1 = m.getImage(ol1);
			if (og1 != null) {
				
				for (int j=1; j<origs.size(); j++) {
					GraphObject ol2 = origs.get(j);
					GraphObject og2 = m.getImage(ol2);
					
					if (og2 != null) {
						if (g.glue(og1, og2)) {
							glued = true;
						}
						else {
							throw new TypeException(
									"Step.pushout: Cannot glue edges of type  "+og1.getType().getName()+" !");
						}
					} else if (m.isIdentificationSet()){
						throw new TypeException(
								"Step.pushout: Cannot finish transformation step. Identification condition has failed!");
					}						
				}
				
				try {
					if (p.getImage(a) == null)
						p.addPlainMapping(a, og1);						
				} catch (BadMappingException ex1) {
					throw new TypeException(ex1.getLocalizedMessage());
				}
				
				if (glued) {	
					for (int j=1; j<origs.size(); j++) {
						final GraphObject ol2 = origs.get(j);
						try {
							m.addObjectPlainMapping(ol2, og1);																						
						} catch (BadMappingException ex1) {							
							throw new TypeException(ex1.getLocalizedMessage());
						}
					}
				} 
			} else if (m.isIdentificationSet()){
				throw new TypeException(
				"Step.pushout: Cannot finish transformation step. Identification condition has failed!");
			}
		}
	}
	
	
	private  void createNodeOfSameType(
			final Node n, 
			final Graph G,
			final OrdinaryMorphism p) throws TypeException {
		try {
			final Type nodetype = n.getType();
			if (nodetype != null) {
				try { 
					final Node n2 = G.newNodeFast(nodetype);
					n2.setContextUsage(n.getContextUsage());
					try {
						p.addMappingFast(n, n2);
					} catch (BadMappingException ex1) {
						throw new TypeException(ex1.getLocalizedMessage());
					}
				} catch (TypeException ex2) {
					throw new TypeException(ex2.getLocalizedMessage());
				}
			} else
				throw new TypeException("Step.pushout: Cannot create node! "
						+ "Node type not found!");
		} catch (TypeException ex) {
			throw new TypeException(ex.getLocalizedMessage());
		}
	}

	private  void createNodeOfSimilarType(
			final Node n, 
			final Graph G,
			final OrdinaryMorphism p) throws TypeException {
		
		try {
			final Type nodetype = G.getTypeSet().getSimilarType(n.getType());
			if (nodetype != null) {
				try {
					final Node n2 = G.createNode(nodetype);
					n2.setContextUsage(n.getContextUsage());
					try {
						p.addMapping(n, n2);
					} catch (BadMappingException ex1) {
						throw new TypeException(ex1.getLocalizedMessage());
					}
				}catch (TypeException ex2) {
					throw new TypeException(ex2.getLocalizedMessage());
				}
			} else
				throw new TypeException("Step.pushout: Cannot create node! "
						+ "Node type not found!");
		} catch (TypeException ex) {
			throw new TypeException(ex.getLocalizedMessage());
		}
	}

	private  void createArcOfSameType(
			final Arc a, 
			final GraphObject src, 
			final GraphObject tar,
			final Graph G,
			final OrdinaryMorphism p) throws TypeException {
		
		try {
			final Type arctype = a.getType();
			if (arctype != null) {
//				if (G.isParallelArcAllowed(arctype, (Node)src, (Node)tar)) {
					final Arc a2 = G.newArcFast(arctype, (Node)src, (Node)tar);
					a2.setContextUsage(a.getContextUsage());
					try {
						p.addMappingFast(a, a2);
					} catch (BadMappingException ex1) {
						throw new BadMappingException(ex1.getLocalizedMessage());
					}
//				} else {
//					throw new TypeException("Step pushout: Cannot create edge! "
//							+ "No parallel edges allowed.");
//				}
			} else {
				throw new TypeException("Step.pushout: Cannot create edge! "
						+ "Edge type not found.");
			}
		} catch (TypeException ex) {
			throw new TypeException(ex.getLocalizedMessage());
		}
	}

	private  void createArcOfSimilarType(
			final Arc a, 
			final GraphObject src, 
			final GraphObject tgt,
			final Graph G,
			final OrdinaryMorphism p) throws TypeException {
		
		try {
			final Type arctype = G.getTypeSet().getSimilarType(a.getType());
			if (arctype != null) {
				try {
					final Arc a2 = G.createArc(arctype, (Node) src, (Node) tgt);
					a2.setContextUsage(a.getContextUsage());
					try {
						p.addMapping(a, a2);
					} catch (BadMappingException ex1) {
						throw new BadMappingException(ex1.getLocalizedMessage());
					}
				} catch (TypeException ex2) {
					throw new TypeException(ex2.getLocalizedMessage());
				}
			} else
				throw new TypeException("Step.pushout: Cannot create edge! "
						+ "Edge type not found.");
		} catch (TypeException ex) {
			throw new TypeException(ex.getLocalizedMessage());
		}
	}

	public  List<GraphObject> getCreatedNodes(
			final Rule r, 
			final Morphism comatch) {

		final List<GraphObject> list = new Vector<GraphObject>();
		if (r.getRight() == ((OrdinaryMorphism)comatch).getSource()) {
			Enumeration<GraphObject> dom = comatch.getDomain();
			while (dom.hasMoreElements()) {
				GraphObject go = dom.nextElement();
				if (go.isNode() && !r.getInverseImage(go).hasMoreElements()) {
					list.add(go);
				}
			}
		}
		return list;
	}
	
	public  List<GraphObject> getCreatedObjects(
			final Rule r, 
			final Morphism comatch) {

		final List<GraphObject> list = new Vector<GraphObject>();
		if (r.getRight() == ((OrdinaryMorphism)comatch).getSource()) {
			Enumeration<GraphObject> dom = comatch.getDomain();
			while (dom.hasMoreElements()) {
				GraphObject go = dom.nextElement();
				if (!r.getInverseImage(go).hasMoreElements()) {
					list.add(go);
				}
			}
		}
		return list;
	}
	

}
