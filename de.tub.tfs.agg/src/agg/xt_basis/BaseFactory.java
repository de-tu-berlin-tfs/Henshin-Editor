package agg.xt_basis;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrMapping;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.impl.javaExpr.JexExpr;
import agg.attribute.parser.javaExpr.ASTId;
import agg.attribute.parser.javaExpr.SimpleNode;
import agg.attribute.parser.javaExpr.ASTPrimaryExpression;
import agg.attribute.handler.SymbolTable;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.ContextView;
import agg.attribute.facade.impl.DefaultInformationFacade;
import agg.cons.AtomConstraint;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.ruleappl.ApplicabilityChecker;
import agg.ruleappl.ObjectFlow;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;
import agg.util.Triple;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.xt_basis.agt.RuleScheme;
import agg.xt_basis.csp.Completion_InheritCSP;

/**
 * A factory class for Graphs, Morphisms, Rules, Matches.
 */
public class BaseFactory {
	/** The factory models GraGras. */

	private final List<GraGra> itsGraGras = new Vector<GraGra>();

	protected static BaseFactory theBaseFactory;

	public static BaseFactory theFactory() {
		if (theBaseFactory != null)
			return (theBaseFactory);
		
		theBaseFactory = new BaseFactory();
		return (theBaseFactory);
	}

	/** Create a new gragra with its own type set and a host graph inclusive. */
	public GraGra createGraGra() {
		GraGra gg = new GraGra(new TypeSet());
		this.itsGraGras.add(gg);
		return (gg);
	}

	/** Create a new gragra with its own type set  and a host graph optionally. */
	public GraGra createGraGra(boolean withGraph) {
		GraGra gg = new GraGra(withGraph);
		this.itsGraGras.add(gg);
		return (gg);
	}
	
	/** Create a new gragra with its own type set and a host graph optionally. 
	 * The second parameter defines whether the graphs are directed or not.
	 */
	public GraGra createGraGra(boolean withGraph, boolean directedArcs, boolean parallelArcs) {
		GraGra gg = new GraGra(new TypeSet(directedArcs, parallelArcs), withGraph);
		this.itsGraGras.add(gg);
		return (gg);
	}
	
	/**
	 * Dispose the specified gragra and remove from the gragra list.
	 */
	public void destroyGraGra(GraGra gg) {
		if (this.itsGraGras.contains(gg)) {
			this.itsGraGras.remove(gg);
			gg.dispose();
		}
	}

	/**
	 * Remove the specified gragra from the gragra list.
	 */
	public void removeGraGra(GraGra gg) {
		if (this.itsGraGras.contains(gg)) {
			this.itsGraGras.remove(gg);
		}
	}

	public Enumeration<GraGra> getGraGras() {
		return ((Vector<GraGra>) this.itsGraGras).elements();
	}

	public int getCountOfGraGras() {
		return this.itsGraGras.size();
	}

	public void notify(GraGra gg) {
		if (!this.isElement(gg))
			(this.itsGraGras).add(gg);
	}

	private boolean isElement(GraGra gg) {
		if (this.itsGraGras.contains(gg))
			return true;
		
		return false;
	}

	/** Create a new graph transformation unit GraTra */
	public GraTra createGraTra() {
		return (new DefaultGraTraImpl());
	}

	/** Create a new graph */
	public final Graph createGraph() {
		return new Graph();
	}

	/**
	 * Create a new graph
	 */
	public final Graph createGraph(TypeSet types) {
//		return new Graph(types);
		return types.isArcDirected()? new Graph(types): new UndirectedGraph(types);
	}

	/**
	 * Create a new graph
	 */
	public final Graph createGraph(TypeSet types, boolean complete) {
		return types.isArcDirected()? new Graph(types, complete): 
									new UndirectedGraph(types, complete);
	}
	
	/** Create a new type manager and container for the type graph */
	public final TypeSet createTypeSet() {
		return new TypeSet();
	}

		
	/** Dispose the specified graph. */
	public final void destroyGraph(Graph graph) {
		graph.dispose();
	}

	/**
	 * There are graphs L, R, G and given morphisms
	 * r: L --> R and m: L --> G.<br>
	 * Computes  PO as the colimit graph H with morphisms
	 * f: G --> H and g: R --> H.<br>
	 * 
	 * The attributes of nodes and edges of the graph L are still unset.
	 * 
	 * @return a pair (f,g) or NULL
	 */
	public final Pair<OrdinaryMorphism,OrdinaryMorphism> makePO(
			final OrdinaryMorphism r, 
			final OrdinaryMorphism m,
			boolean allowAttrVarsInGraph,
			boolean wrtEqualAttrVarName) {
		try {
			Pair<OrdinaryMorphism,OrdinaryMorphism> 
			PO = StaticStep.executeColim(r, m, allowAttrVarsInGraph, wrtEqualAttrVarName);
			return PO;
		} catch (TypeException ex) {}
		return null;
	}
	
	/**
	 * There are graphs K, L, G and given morphisms
	 * l: K --> L and g: L --> G.<br>
	 * Computes PO-complement: the graph C with morphisms
	 * k: K --> C and c: C --> G.<br>
	 * 
	 * @return a pair (c,k) or NULL
	 */
	public final Pair<OrdinaryMorphism,OrdinaryMorphism> makePOComplement(
			final OrdinaryMorphism l, 
			final OrdinaryMorphism g) {
		
		final Graph K = l.getSource();
		final Graph L = l.getTarget();
		final Graph G = g.getTarget();
		final OrdinaryMorphism c = G.inverseIsoCopy();
		if (c == null)
			return null;
		
		final Graph C = c.getSource();
		final OrdinaryMorphism k = new OrdinaryMorphism(K, C, 
									g.getAttrManager().newContext(AttrMapping.PLAIN_MAP));
		
		List<Node> del = new Vector<Node>();
		Iterator<Node> nodes = L.getNodesCollection().iterator();
		while (nodes.hasNext()) {
			Node nL = nodes.next();
			Node nC = (Node)c.getInverseImage(g.getImage(nL)).nextElement();	
			if (l.getInverseImage(nL).hasMoreElements()) {
				Node nK = (Node)l.getInverseImage(nL).nextElement(); 
				try {
					k.addMapping(nK, nC);
				} catch (BadMappingException ex) {
					System.out.println("BF.makePOComplement: "+ex.getMessage());
					return null;
				}
			} else {
				del.add(nC);
			}
		}
		Iterator<Arc> arcs = L.getArcsCollection().iterator();
		while (arcs.hasNext()) {
			Arc aL = arcs.next();
			Arc aC = (Arc)c.getInverseImage(g.getImage(aL)).nextElement();
			if (l.getInverseImage(aL).hasMoreElements()) {
				Arc aK = (Arc)l.getInverseImage(aL).nextElement();
				try {
					k.addMapping(aK, aC);
				} catch (BadMappingException ex) {
					System.out.println("BF.makePOComplement: "+ex.getMessage());
					return null;
				}
			} else {
				try {
					c.removeMapping(aC);
				} catch (BadMappingException ex) {
					System.out.println("BF.makePOComplement: "+ex.getMessage());
					return null;
				}
				try {
					C.destroyArc(aC, false, true);
				} catch (TypeException ex) {
					System.out.println("BF.makePOComplement: "+ex.getMessage());
					return null;
				}
			}
		}
		for (int i=0; i<del.size(); i++) {
			try {
				c.removeMapping(del.get(i));
			} catch (BadMappingException ex) {
				System.out.println("BF.makePOComplement: "+ex.getMessage());
				return null;
			}
			try {
				C.destroyNode(del.get(i), false, true);
			} catch (TypeException ex) {
				System.out.println("BF.makePOComplement: "+ex.getMessage());
				return null;
			}
		}
		
//		arcs = G.getArcsCollection().iterator();
//		while (arcs.hasNext()) {
//			Arc aG = arcs.next();
//			GraphObject sG = aG.getSource();
//			GraphObject tG = aG.getTarget();
//			if (g.getInverseImage(sG).hasMoreElements()) {
//				GraphObject sL = g.getInverseImage(sG).nextElement();
//				if (!l.getInverseImage(sL).hasMoreElements()) {
//					System.out.println("BF.makePOComplement: edge source conflict!!");
//					return null;
//				}
//			} else if (g.getInverseImage(tG).hasMoreElements()) {
//				GraphObject sL = g.getInverseImage(tG).nextElement();
//				if (!l.getInverseImage(sL).hasMoreElements()) {
//					System.out.println("BF.makePOComplement: edge source conflict!!");
//					return null;
//				}
//			}
//		}
		
		return new Pair<OrdinaryMorphism,OrdinaryMorphism>(c, k);
	}
	
	private boolean checkDelDueToMerge(final OrdinaryMorphism t,
										final OrdinaryMorphism l, 
										final OrdinaryMorphism g) {
		final Graph G = g.getTarget();
		Iterator<Arc> arcs = G.getArcsCollection().iterator();
		while (arcs.hasNext()) {
			Arc aG = arcs.next();
			GraphObject sG = aG.getSource();
			GraphObject tG = aG.getTarget();
			if (g.getInverseImage(sG).hasMoreElements()
					&& !t.getInverseImage(g.getInverseImage(sG).nextElement()).hasMoreElements()) {
				// aG is a new edge
				GraphObject sL = g.getInverseImage(sG).nextElement();
				if (!l.getInverseImage(sL).hasMoreElements()) {
//					System.out.println("BF.makePOComplement: edge source conflict!!");
					return false;
				}
			} else if (g.getInverseImage(tG).hasMoreElements()) {
				GraphObject sL = g.getInverseImage(tG).nextElement();
				if (!l.getInverseImage(sL).hasMoreElements()) {
//					System.out.println("BF.makePOComplement: edge source conflict!!");
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*
	 * There are graphs K, L, G and given morphisms
	 * l: K --> L and g: L --> G.<br>
	 * Computes PO-complement: the graph C with morphisms
	 * k: K --> C and c: C --> G.<br>
	 * 
	 * @return a pair (c,k) or NULL
	 *
	private final Pair<OrdinaryMorphism,OrdinaryMorphism> makePOComplement2(
			final OrdinaryMorphism l, 
			final OrdinaryMorphism g) {
		
		final Graph K = l.getSource();
		final OrdinaryMorphism k = K.isoGraph();
		final Graph C = k.getTarget();
		final Graph G = g.getTarget();
		final OrdinaryMorphism c = new OrdinaryMorphism(C, G, 
									g.getAttrManager().newContext(AttrMapping.PLAIN_MAP));
		
		final Hashtable<Object,Object> o2o = new Hashtable<Object,Object>();
		Enumeration<GraphObject> dom = l.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject goK = dom.nextElement();
			try {
				GraphObject goC = k.getImage(goK);
				GraphObject goG = g.getImage(l.getImage(goK));
				goC.getAttribute().copyEntries(goG.getAttribute());
				c.addMapping(goC, goG);
				if (goK.isNode()) {
					o2o.put(goG, goC);
				}
			} catch (BadMappingException ex) {
				System.out.println("BF.makePOComplement: "+ex.getMessage());
				return null;
			}
		}	
		
		Iterator<Node> nodes = G.getNodesCollection().iterator();
		while (nodes.hasNext()) {
			Node nG = nodes.next();
			if (!g.getInverseImage(nG).hasMoreElements()) {
				try {
					Node nC = C.createNode(nG.getType());
					nC.getAttribute().copyEntries(nG.getAttribute());
					o2o.put(nG, nC);
					try {
						c.addMapping(nC, nG);
					} catch (BadMappingException ex) {
						System.out.println("BF.makePOComplement2: "+ex.getMessage());
						return null;
					}
				} catch (TypeException ex) {
					System.out.println("BF.makePOComplement2: "+ex.getMessage());
					return null;
				}
			}
		}
		Iterator<Arc> arcs = G.getArcsCollection().iterator();
		while (arcs.hasNext()) {
			Arc aG = arcs.next();
			if (!g.getInverseImage(aG).hasMoreElements()) {
				try {
					Node sC = (Node)o2o.get(aG.getSource());
					Node tC = (Node)o2o.get(aG.getTarget());
					Arc aC = C.createArc(aG.getType(), sC, tC);
					aC.getAttribute().copyEntries(aG.getAttribute());
					try {
						c.addMapping(aC, aG);
					} catch (BadMappingException ex) {
						System.out.println("BF.makePOComplement2: "+ex.getMessage());
						return null;
					}
				} catch (TypeException ex) {
					System.out.println("BF.makePOComplement2: "+ex.getMessage());
					return null;
				}
			}
		}
	
		return new Pair<OrdinaryMorphism,OrdinaryMorphism>(c, k);
	}
	*/
	
	/*
	 * There are graphs R, G, H with given morphisms
	 * g: R --> H and f: G --> H.<br>
	 * Computes the PB as the limit graph L with morphisms
	 * r: L --> R and m: L --> G.<br>
	 * 
	 * The attributes of nodes and edges of the graph L are still unset.
	 * 
	 * @return a pair (r,m) or NULL
	 */
	public final Pair<OrdinaryMorphism,OrdinaryMorphism> makePB(
			final OrdinaryMorphism f, 
			final OrdinaryMorphism g) {
		final Graph L = BaseFactory.theFactory().createGraph(f.getTarget().getTypeSet());
		final Graph R = g.getSource();
		final Graph G = f.getSource();
		final Graph H = f.getTarget();
		final OrdinaryMorphism r = new OrdinaryMorphism(L, R, 
													g.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		final OrdinaryMorphism m = new OrdinaryMorphism(L, G, 
													f.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		Hashtable<Object,Object> n2n = new Hashtable<Object,Object>();
		Iterator<Node> nodes = H.getNodesCollection().iterator();
		while (nodes.hasNext()) {
			Node n = nodes.next();
			if (g.getInverseImage(n).hasMoreElements()
					&& f.getInverseImage(n).hasMoreElements()) {
				Node n_f = (Node) f.getInverseImage(n).nextElement();
				Node n_g = (Node) g.getInverseImage(n).nextElement();
				try {
					Node nn = L.createNode(n.getType());
					try {
						r.addMapping(nn, n_g);
						m.addMapping(nn, n_f);
						n2n.put(n_g, nn);
					} catch (BadMappingException ex1) {}
				} catch (TypeException ex) {}
			}
		}
		Iterator<Arc> arcs = H.getArcsCollection().iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (g.getInverseImage(a).hasMoreElements()
					&& f.getInverseImage(a).hasMoreElements()) {
				Arc a_f = (Arc) f.getInverseImage(a).nextElement();
				Arc a_g = (Arc) g.getInverseImage(a).nextElement();
				Node src = (Node)n2n.get(a_g.getSource());
				Node tar = (Node)n2n.get(a_g.getTarget());
				try {
					Arc na = L.createArc(a.getType(), src, tar);
					try {
						r.addMapping(na, a_g);
						m.addMapping(na, a_f);
					} catch (BadMappingException ex1) {}
				} catch (TypeException ex) {}
			}
		}
		return new Pair<OrdinaryMorphism,OrdinaryMorphism>(r, m);
	}
	
	/*
	 * Given morphism g: D --> G.<br>
	 * Computes Initial Pushout (IPO) as a triple of morphisms:
	 * b1: B --> D, b2: B --> L, b3: L --> G. 
	 * 
	 * The attributes of nodes and edges of the graphs B and L are still unset.
	 * 
	 * @return a triple of morphisms or NULL
	 */
	public final Triple<OrdinaryMorphism,OrdinaryMorphism,OrdinaryMorphism> makeIPO(final OrdinaryMorphism g) {
		final Graph B = BaseFactory.theFactory().createGraph(g.getTarget().getTypeSet());
		final Graph D = g.getSource();
		final Graph L = BaseFactory.theFactory().createGraph(g.getTarget().getTypeSet());
		final Graph G = g.getTarget();
		
		final OrdinaryMorphism b1 = new OrdinaryMorphism(B, D, 
													g.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		final OrdinaryMorphism b2 = new OrdinaryMorphism(B, L, 
													g.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		final OrdinaryMorphism b3 = new OrdinaryMorphism(L, G, 
													g.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		
		Hashtable<Object,Object> n2n_L = new Hashtable<Object,Object>();
		Hashtable<Object,Object> n2n_B = new Hashtable<Object,Object>();
		// an edge in G but not in D add with source and target to L, add mappings to b3
		// source/target in G and in D add to B, add mappings to b1 and b2
		Iterator<Arc> arcs = G.getArcsCollection().iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (!g.getInverseImage(a).hasMoreElements()) {
				// edge is in G but not in D
				try {
					// add edge to L
					Node s_L = (n2n_L.get(a.getSource()) == null 
										|| !(n2n_L.get(a.getSource()) instanceof Node))? 
									L.createNode(a.getSourceType()): 
										(Node)n2n_L.get(a.getSource());
					Node t_L = (n2n_L.get(a.getTarget()) == null 
										|| !(n2n_L.get(a.getTarget()) instanceof Node))? 
									L.createNode(a.getTargetType()):
										(Node)n2n_L.get(a.getTarget());
					Arc a_L = L.createArc(a.getType(), s_L, t_L);
					n2n_L.put(a.getSource(), s_L);
					n2n_L.put(a.getTarget(), t_L);
					try {
						b3.addMapping(s_L, a.getSource());
						b3.addMapping(t_L, a.getTarget());
						b3.addMapping(a_L, a);
					} catch (BadMappingException ex1) {}
					
					if (g.getInverseImage(a.getSource()).hasMoreElements()) {
						// edge's source is in D, so add it to B
						if (n2n_B.get(a.getSource()) == null) {
							Node s_B = B.createNode(a.getSourceType());
							n2n_B.put(a.getSource(), s_B);
							try {
								b1.addMapping(s_B, g.getInverseImage(a.getSource()).nextElement());
								b2.addMapping(s_B, s_L);
							} catch (BadMappingException ex1) {}
						}
					}
					if (g.getInverseImage(a.getTarget()).hasMoreElements()) {
						// edge's target is in D, so add it to B
						if (n2n_B.get(a.getTarget()) == null) {
							Node t_B = B.createNode(a.getTargetType());
							n2n_B.put(a.getTarget(), t_B);
							try {
								b1.addMapping(t_B, g.getInverseImage(a.getTarget()).nextElement());
								b2.addMapping(t_B, t_L);
							} catch (BadMappingException ex1) {}
						}
					}
				} catch (TypeException ex) {}
			} else {
				if (n2n_L.get(a.getSource()) == null)
					n2n_L.put(a.getSource(), a);
				if (n2n_L.get(a.getTarget()) == null)
					n2n_L.put(a.getTarget(), a);
			}
		}
		
		Iterator<Node> nodes = G.getNodesCollection().iterator();
		// single node in G but not in D add to L, add mappings to b3
		while (nodes.hasNext()) {
			Node n = nodes.next();			
			if (n2n_L.get(n) == null
					&& !g.getInverseImage(n).hasMoreElements()) {
				try {
					Node n_L = L.createNode(n.getType());
					try {
						b3.addMapping(n_L, n);
					} catch (BadMappingException ex1) {}
				} catch (TypeException ex) {}
			}
		}

		return new Triple<OrdinaryMorphism,OrdinaryMorphism,OrdinaryMorphism>(b1, b2, b3);
	}
	
	/*
	 * Given morphism t: G --> H.<br>
	 * Computes the span G <-- D --> H with g: D --> G and f: D --> H.
	 * 
	 * The attributes of nodes and edges of the graph D are still unset.
	 * 
	 * @return a pair (g,f) or NULL
	 */
	public final Pair<OrdinaryMorphism,OrdinaryMorphism> makeSpan(final OrdinaryMorphism t) {
		final Graph D = BaseFactory.theFactory().createGraph(t.getTarget().getTypeSet());
		final Graph G = t.getSource();
		final Graph H = t.getTarget();
		final OrdinaryMorphism g = new OrdinaryMorphism(D, G, 
													t.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		final OrdinaryMorphism f = new OrdinaryMorphism(D, H, 
													t.getAttrManager().newContext(
													AttrMapping.PLAIN_MAP));
		Hashtable<Object,Object> n2n = new Hashtable<Object,Object>();
		Iterator<Node> nodes = G.getNodesCollection().iterator();
		while (nodes.hasNext()) {
			Node n = nodes.next();
			if (t.getImage(n) != null) {
				try {
					Node nn = D.createNode(n.getType());
					try {
						f.addMapping(nn, t.getImage(n));
						g.addMapping(nn, n);
						n2n.put(n, nn);
					} catch (BadMappingException ex1) {}
				} catch (TypeException ex) {}
			}
		}
		Iterator<Arc> arcs = G.getArcsCollection().iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (t.getImage(a) != null) {
				Node src = (Node)n2n.get(a.getSource());
				Node tar = (Node)n2n.get(a.getTarget());
				try {
					Arc na = D.createArc(a.getType(), src, tar);
					try {
						f.addMapping(na, t.getImage(a));
						g.addMapping(na, a);
					} catch (BadMappingException ex1) {}
				} catch (TypeException ex) {}
			}
		}
		return new Pair<OrdinaryMorphism,OrdinaryMorphism>(g, f);
	}
	
	/*
	 * Given transformation G --t-> H.<br>
	 * Computes the graph modification G <-g-- D --h-> H and then
	 * the minimal rule L --r->R with induced match L --m-> G.
	 * 
	 * @return the minimal rule or NULL
	 */
	public final Rule makeMinimalRule(final OrdinaryMorphism t) {
		Pair<OrdinaryMorphism,OrdinaryMorphism> span = this.makeSpan(t);
		Triple<OrdinaryMorphism,OrdinaryMorphism,OrdinaryMorphism> IPO1 = this.makeIPO(span.first);
		Triple<OrdinaryMorphism,OrdinaryMorphism,OrdinaryMorphism> IPO2 = this.makeIPO(span.second);
		Pair<OrdinaryMorphism,OrdinaryMorphism> PB = this.makePB(IPO1.first, IPO2.first);
		Pair<OrdinaryMorphism,OrdinaryMorphism> PO4 = this.makePO(PB.first, PB.second, true, false);
		Pair<OrdinaryMorphism,OrdinaryMorphism> PO3 = null;
		Pair<OrdinaryMorphism,OrdinaryMorphism> PO5 = null;
		if (IPO1.second.getSource() == PO4.first.getSource()) {
			// this is the case!
			PO3 = this.makePO(IPO1.second, PO4.first, true, false);
			PO5 = this.makePO(IPO2.second, PO4.second, true, false);						
		} else if (IPO1.second.getSource() == PO4.second.getSource()) {
			PO3 = this.makePO(IPO1.second, PO4.second, true, false);
			PO5 = this.makePO(IPO2.second, PO4.first, true, false);
		}
		if (PO3 != null && PO5 != null) {
			
			final OrdinaryMorphism mKD = new OrdinaryMorphism(
					PO4.first.getTarget(), IPO1.first.getTarget(), 
					t.getAttrManager().newContext(
					AttrMapping.PLAIN_MAP));
			if (mKD.makeDiagram(PO4.first, IPO1.first)
					&& mKD.makeDiagram(PO4.second, IPO2.first)) {
				
				final Rule r = new Rule(PO3.first.getTarget(), PO5.first.getTarget());
				if (r.makeDiagram(PO3.first, PO5.first)) {
				
					final OrdinaryMorphism mL = new OrdinaryMorphism(
								r.getLeft(), t.getSource(), 
								t.getAttrManager().newContext(AttrMapping.PLAIN_MAP));
					final OrdinaryMorphism mR = new OrdinaryMorphism(
								r.getRight(), t.getTarget(), 
								t.getAttrManager().newContext(AttrMapping.PLAIN_MAP));
						
					if (mL.makeDiagram(PO3.first, mKD, span.first)
								&& mR.makeDiagram(PO5.first, mKD, span.second)) {
						changedAttr2Var(r, t, mL, mR);
							
						Match m = this.createMatch(r, t.getSource());
						if (m.makeDiagram(PO3.first, mKD, span.first)
								&& m.makeDiagram(PO3.second, IPO1.third)) {
							r.setMatch(m);
							return r;
						}
					}					
				}
			}
		}
		return null;
	}
	
	/*
	 * Given two transformations G --t1-> H1 and G --t2-> H2. <br>
	 * Computes two graph modifications G <-g1-- D1 --h1-> H1 and G <-g2-- D2 --h2-> H2 and then <br>
	 * the merge graph X and the merge graph modification G <-d-- D --x-> X.
	 * 
	 * @return a pair (d,x) or NULL
	 */
	public final Pair<OrdinaryMorphism,OrdinaryMorphism> makeMerge(
			final OrdinaryMorphism t1,
			final OrdinaryMorphism t2) {
		
		if (t1.getSource() != t2.getSource())
			return null;
		
		Pair<OrdinaryMorphism,OrdinaryMorphism> span1 = this.makeSpan(t1);
		Pair<OrdinaryMorphism,OrdinaryMorphism> span2 = this.makeSpan(t2);
		Pair<OrdinaryMorphism,OrdinaryMorphism> pb = this.makePB(span1.first, span2.first);
		if (pb != null) {
			if (this.checkDelDueToMerge(t1, pb.second, span1.second)
					&& this.checkDelDueToMerge(t2, pb.first, span2.second)) 
			{
				Pair<OrdinaryMorphism,OrdinaryMorphism> poc1 = this.makePOComplement(pb.second, span1.second);
				Pair<OrdinaryMorphism,OrdinaryMorphism> poc2 = this.makePOComplement(pb.first, span2.second);
				if (poc1 != null && poc2 != null) {
					Pair<OrdinaryMorphism,OrdinaryMorphism> po3 = this.makePO(poc2.second, poc1.second, false, false);
					if (po3 != null) {
						OrdinaryMorphism d = pb.second.compose(span1.first);
						OrdinaryMorphism x = poc1.second.compose(po3.second);
						if (d != null && x != null) {
							return new Pair<OrdinaryMorphism,OrdinaryMorphism>(d, x);
						}
					}
				}	
			}
		}
		return null;
	}
	
	
	private void changedAttr2Var(final Rule r, final OrdinaryMorphism t,
			final OrdinaryMorphism mL, final OrdinaryMorphism mR) {
		int indx = 1;
		Enumeration<GraphObject> dom = r.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject goL = dom.nextElement();
			GraphObject goR = r.getImage(goL);
			GraphObject goG = mL.getImage(goL);
			GraphObject goH = mR.getImage(goR);
			if (goG == null || goH == null)
				continue;
						
			ValueTuple vtL = (ValueTuple)goL.getAttribute();
			ValueTuple vtR = (ValueTuple)goR.getAttribute();
			ValueTuple vtH = (ValueTuple)goH.getAttribute();
			ValueTuple vtG = (ValueTuple)goG.getAttribute();
			
			for (int i=0; i<vtG.getSize(); i++) {
				ValueMember vmG = vtG.getValueMemberAt(i);
				ValueMember vmH = vtH.getValueMemberAt(vmG.getName());
				if (vmG.isSet() && vmH.isSet()
						&& !vmG.getExprAsText().equals(vmH.getExprAsText())) {
					ValueMember vmL = vtL.getValueMemberAt(vmG.getName());
					vmL.setExprAsText("x".concat(String.valueOf(indx)));
					
					ValueMember vmR = vtR.getValueMemberAt(vmG.getName());
					vmR.setExprAsText("x".concat(String.valueOf(indx)));
					indx++;
				}
			}
		}
	}
	
	/**
	 * Construct a new rule from the given morphism h. The left graph of the
	 * rule is the source graph and the right
	 * graph of the rule is the target graph of the morphism h,
	 * the object mappings are similar to the mappings of the morphism h, too.
	 */
	public Rule constructRuleFromMorph(OrdinaryMorphism h) {
		Rule rule = new Rule(h.getOriginal(), h.getImage());
				
		Enumeration<GraphObject> dom = h.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			try {
				rule.addMapping(obj, h.getImage(obj));
			} catch (BadMappingException ex) {}
		}

		// set variables and conditions
		VarTuple vars = (VarTuple) rule.getAttrContext().getVariables();
		this.declareVariable(rule.getLeft(), vars);
		this.declareVariable(rule.getRight(), vars);
		
		VarTuple varsMorph = (VarTuple) h.getAttrContext().getVariables();
		for (int j = 0; j < varsMorph.getSize(); j++) {
			VarMember vm = varsMorph.getVarMemberAt(j);
			DeclMember dm = (DeclMember) vm.getDeclaration();
			if (dm.getTypeName() != null && dm.getName() != null) { 
				if (!vars.isDeclared(dm.getTypeName(), dm.getName())) {
					vars.declare(dm.getHandler(), dm.getTypeName(), dm.getName());
					vars.getVarMemberAt(dm.getName()).setInputParameter(vm.isInputParameter());
				}
			}
			else {
				varsMorph.getTupleType().deleteMemberAt(j);
				j--;
			}
		}
		
		// declare more variables 
//		this.declareVariable(rule.getLeft(), vars);
//		this.declareVariable(rule.getRight(), vars);
		
		CondTuple condsMorph = (CondTuple) h.getAttrContext().getConditions();
		CondTuple conds = (CondTuple) rule.getAttrContext().getConditions();
		for (int j = 0; j < condsMorph.getSize(); j++) {
			CondMember cm = condsMorph.getCondMemberAt(j);
			if (!cm.getExprAsText().equals("")) {
				conds.addCondition(cm.getExprAsText());
			}
		}
		// check attr. setting in RHS and evntl. fill with variable
		String exprMsg = "";
		vars = (VarTuple) rule.getAttrContext().getVariables();
		int count = vars.getSize();
		String mark = "r";
		Iterator<?> objs = rule.getRight().getNodesSet().iterator();
		while (objs.hasNext()) {
			GraphObject o = (GraphObject) objs.next();
			if (o.getAttribute() == null)
				continue;
			Enumeration<GraphObject> inverseImg = rule.getInverseImage(o);
			if (!inverseImg.hasMoreElements()) {
				ValueTuple value = (ValueTuple) o.getAttribute();
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember vm = value.getValueMemberAt(i);
					if (!vm.isSet()) {					
						exprMsg = "attribute member:  ".concat(vm.getName());
						String t = vm.getName() + String.valueOf(count) + mark;
						// declareVariable(vm.getDeclaration().getHandler(),
						// vm.getDeclaration().getTypeName(), t, vars);
						count++;
						vm.setExprAsText(t);
						vm.setTransient(true);
						exprMsg = exprMsg.concat("  set by a new variable:  ").concat(t).concat(" ;  ");
					}
				}
			}
		}
		objs = rule.getRight().getArcsSet().iterator();
		while (objs.hasNext()) {
			GraphObject o = (GraphObject) objs.next();
			if (o.getAttribute() == null)
				continue;
			Enumeration<GraphObject> inverseImg = rule.getInverseImage(o);
			if (!inverseImg.hasMoreElements()) {
				ValueTuple value = (ValueTuple) o.getAttribute();
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember vm = value.getValueMemberAt(i);
					if (!vm.isSet()) {
						exprMsg = "attribute member:  ".concat(vm.getName());
						String t = vm.getName() + String.valueOf(count) + mark;
						// declareVariable(vm.getDeclaration().getHandler(),
						// vm.getDeclaration().getTypeName(), t, vars);
						count++;
						vm.setExprAsText(t);
						vm.setTransient(true);
						exprMsg = exprMsg.concat("  set by a new variable:  ").concat(t).concat(" ;  ");
					}
				}
			}
		}
		
		String warning = rule.getErrorMsg();
		if (!exprMsg.equals(""))
			warning = warning.concat(exprMsg).concat(" ;  ");
		rule.setErrorMsg(warning);
		
		return rule;
	}

	
	/**
	 * Construct a rule r out of the given morphism h. The left graph of the
	 * rule r is a graph isomorphic to the source graph of the morphism h, the
	 * right graph of the rule r is a graph isomorphic to the target graph of
	 * the morphism h, the object mappings are identical to the mappings of the
	 * morphism h. Returns a Pair with : the first element is the new rule, the
	 * second element is a Pair with two elements of OrdinaryMorphism: an
	 * isomorphic copy of the source graph of the morphism h and an isomorphic
	 * copy of the target graph of the morphism h.
	 */
	public Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> constructIsomorphicRule(
			final OrdinaryMorphism h) {
		
		return constructIsomorphicRule(h, true, false);		
	}
	
	/**
	 * Construct a rule based of the given morphism <code>h</code>. The left graph of the
	 * rule is isomorphic to the source graph of <code>h</code>, the
	 * right graph of the rule is isomorphic to the target graph of <code>h</code>,
	 * the object mappings are identical to the mappings of <code>h</code>,
	 * Returns a Pair with the first element is the new rule, the
	 * second element is a Pair with two isomorphic morphism.
	 */
	public Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> constructIsomorphicRule(
			final OrdinaryMorphism h,
			boolean replaceExpressionByVar,
			boolean replaceConstantByVar) {
 
		final OrdinaryMorphism isoL = h.getSource().isomorphicCopy();
		if (isoL == null) {
			return null;
		}
		final OrdinaryMorphism isoR = h.getTarget().isomorphicCopy();
		if (isoR == null) {
			isoL.dispose(false, true);
			return null;
		}
		
		final Rule rule = new Rule(isoL.getTarget(), isoR.getTarget());
		final Enumeration<GraphObject> dom = h.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			GraphObject img = h.getImage(obj);
			try {
				rule.addMapping(isoL.getImage(obj), isoR.getImage(img));
			} catch (BadMappingException ex) {}
		}
		final Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		p = new Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>>(
							rule, 
							new Pair<OrdinaryMorphism, OrdinaryMorphism>(isoL, isoR));

		rule.putVarToAttrContext();
		
		// set variables and conditions
		final VarTuple vars = (VarTuple) rule.getAttrContext().getVariables();
		final VarTuple varsMorph = (VarTuple) h.getAttrContext().getVariables();
		for (int j = 0; j < varsMorph.getSize(); j++) {
			VarMember vm = varsMorph.getVarMemberAt(j);
			DeclMember dm = (DeclMember) vm.getDeclaration();
			if (!vars.isDeclared(dm.getTypeName(), dm.getName())) {
				vars.declare(dm.getHandler(), dm.getTypeName(), dm.getName());
			}
		}
		
		final CondTuple condsMorph = (CondTuple) h.getAttrContext().getConditions();
		final CondTuple conds = (CondTuple) rule.getAttrContext().getConditions();
		for (int j = 0; j < condsMorph.getSize(); j++) {
			CondMember cm = condsMorph.getCondMemberAt(j);
			if (!cm.getExprAsText().equals("")) {
				conds.addCondition(cm.getExprAsText());
			}
		}

		// check attr. setting in RHS and evntl. fill with variable
		int count = 1;
		count = doCheckAndFillUnsetAttrs(rule, vars, count, 
										replaceExpressionByVar, 
										rule.getRight().getNodesSet().iterator());
		count = doCheckAndFillUnsetAttrs(rule, vars, count, 
										replaceExpressionByVar, 
										rule.getRight().getArcsSet().iterator());

		return p;
	}

	/*
	 * Check attributes of the specified elements:<br>
	 * - replace expression value by a variable<br>
	 * - set unset attribute by a variable<br> 
	 */
	private int doCheckAndFillUnsetAttrs(
			final Rule rule,
			final VarTuple vars,
			int startCount,
			boolean replaceExpressionByVar,
			final Iterator<?> elems) {
		
		// check attr. setting in RHS and evntl. fill with variable
		int count = startCount;
		while (elems.hasNext()) {
			GraphObject o =(GraphObject) elems.next();
			boolean inverseImageExists = rule.getInverseImage(o).hasMoreElements();			
			if (o.getAttribute() == null) {
				continue;
			}
			ValueTuple value = (ValueTuple) o.getAttribute();
			for (int i = 0; i < value.getSize(); i++) {
				ValueMember vm = value.getValueMemberAt(i);
				if (!vm.isSet()) {
					if (!inverseImageExists) {	
						String t = vm.getName() + count;
						// declareVariable(vm.getDeclaration().getHandler(),
						// vm.getDeclaration().getTypeName(), t, vars);
						vm.setExprAsText(t);
						vm.setTransient(true);
						count++;
					}
				} else if (vm.getExpr().isComplex() && replaceExpressionByVar) {
					String t = "expr" + count;
					// declareVariable(vm.getDeclaration().getHandler(),
					// vm.getDeclaration().getTypeName(), t, vars);
					vm.setExprAsText(t);
					vm.setTransient(true);
					count++;
				}
//				else if (vm.getExpr().isConstant() && replaceConstantByVar) {
					// declareVariable(vm.getDeclaration().getHandler(),
					// vm.getDeclaration().getTypeName(), t, vars);
//					vm.setExprAsText(t);
//					vm.setTransient(true);
//					count++;
//				}
			}
		}
		return count;
	}
	
	/**
	 * Construct a new rule from the given morphism h. The left graph of the
	 * rule is a copy of the source graph and the
	 * right graph of the rule r is a copy of the target graph of
	 * the morphism h, the object mappings are similar to the 
	 * mappings of the morphism h.
	 */
	public Rule constructRule(OrdinaryMorphism h) {
		// System.out.println("BaseFactory.constructRule");
		Rule rule = new Rule(h.getOriginal().getTypeSet());
		Graph lgraph = h.getOriginal();
		Graph rgraph = h.getImage();
		Graph left = rule.getLeft();
		Graph right = rule.getRight();
		Hashtable<GraphObject, GraphObject> ltable = new Hashtable<GraphObject, GraphObject>();
		Hashtable<GraphObject, GraphObject> rtable = new Hashtable<GraphObject, GraphObject>();
		Iterator<Node> rnodes = rgraph.getNodesSet().iterator();
		while (rnodes.hasNext()) {
			Node rNode = rnodes.next();
			Node itsRNode = null;
			try {
				itsRNode = right.copyNode(rNode);
				itsRNode.setContextUsage(rNode.getContextUsage());
				rtable.put(rNode, itsRNode);
			} catch (TypeException e) {
				// If the given morphism is well typed,
				// the resulting rule graphs should be also well typed
				e.printStackTrace();
			}
		}
		Iterator<Node> lnodes = lgraph.getNodesSet().iterator();
		while (lnodes.hasNext()) {
			Node lNode = lnodes.next();
			Node itsLNode = null;
			try {
				itsLNode = left.copyNode(lNode);
				itsLNode.setContextUsage(lNode.getContextUsage());
				ltable.put(lNode, itsLNode);
			} catch (TypeException e) {
				// If the given morphism is well typed,
				// the resulting rule graphs should be also well typed
				e.printStackTrace();
			}
			GraphObject rn = h.getImage(lNode);
			if (rn != null) {
				try {
					rule.addMapping(itsLNode, rtable.get(rn));
				} catch (BadMappingException ex) {}
			}
		}
		Iterator<Arc> rarcs = rgraph.getArcsSet().iterator();
		while (rarcs.hasNext()) {
			Arc rArc = rarcs.next();
			Node itsRSource = (Node) rtable.get(rArc.getSource());
			Node itsRTarget = (Node) rtable.get(rArc.getTarget());
			Arc itsRArc = null;
			try {
				itsRArc = right.copyArc(rArc, itsRSource, itsRTarget);
				itsRArc.setContextUsage(rArc.getContextUsage());
				rtable.put(rArc, itsRArc);
			} catch (TypeException ex) {
			}
		}
		Iterator<Arc> larcs = lgraph.getArcsSet().iterator();
		while (larcs.hasNext()) {
			Arc lArc = larcs.next();
			Node itsLSource = (Node) ltable.get(lArc.getSource());
			Node itsLTarget = (Node) ltable.get(lArc.getTarget());
			Arc itsLArc = null;
			try {
				itsLArc = left.copyArc(lArc, itsLSource, itsLTarget);
				itsLArc.setContextUsage(lArc.getContextUsage());
				ltable.put(lArc, itsLArc);
			} catch (TypeException ex) {
			}
			GraphObject ra = h.getImage(lArc);
			if (ra != null) {
				try {
					rule.addMapping(itsLArc, rtable.get(ra));
				} catch (BadMappingException ex) {}
			}
		}
		// check attr. setting in RHS and evntl. fill with variable
		VarTuple vars = (VarTuple) rule.getAttrContext().getVariables();
		int count = vars.getSize();
		String mark = "r";
		Iterator<?> objs = right.getNodesSet().iterator();
		while (objs.hasNext()) {
			GraphObject o = (GraphObject) objs.next();
			Enumeration<GraphObject> inverseImg = rule.getInverseImage(o);
			if (!inverseImg.hasMoreElements()) {
				if (o.getAttribute() == null)
					continue;
				ValueTuple value = (ValueTuple) o.getAttribute();
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember vm = value.getValueMemberAt(i);
					if (!vm.isSet()) {
						String t = vm.getName() + String.valueOf(count) + mark;
						// declareVariable(vm.getDeclaration().getHandler(),
						// vm.getDeclaration().getTypeName(), t, vars);
						count++;
						vm.setExprAsText(t);
						vm.setTransient(true);
					}
				}
			}
		}
		objs = right.getArcsSet().iterator();
		while (objs.hasNext()) {
			GraphObject o = (GraphObject) objs.next();
			Enumeration<GraphObject> inverseImg = rule.getInverseImage(o);
			if (!inverseImg.hasMoreElements()) {
				if (o.getAttribute() == null)
					continue;
				ValueTuple value = (ValueTuple) o.getAttribute();
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember vm = value.getValueMemberAt(i);
					if (!vm.isSet()) {
						String t = vm.getName() + String.valueOf(count) + mark;
						// declareVariable(vm.getDeclaration().getHandler(),
						// vm.getDeclaration().getTypeName(), t, vars);
						count++;
						vm.setExprAsText(t);
						vm.setTransient(true);
					}
				}
			}
		}
		
		ltable.clear();
		ltable = null;
		rtable.clear();
		rtable = null;
		return (rule);
	}

	public Pair<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> reverseMorphism(
			final OrdinaryMorphism morph) {
		
//		System.out.println("BF.reverseMorphism:   of  "+morph.getSource().getName()+" --> "+morph.getTarget().getName());
		// check if morph is injective
		if (!morph.isInjective()) {
			return null;
		}
		
		String warning = "";
		
		// make LHS
		OrdinaryMorphism isoRight = morph.getTarget().isomorphicCopy();
		// make RHS
		OrdinaryMorphism isoLeft = morph.getSource().isomorphicCopy();
		if (isoRight == null || isoLeft == null) {
			return null;
		}
		
		Graph left = isoRight.getTarget();
		Graph right = isoLeft.getTarget();
		// make inverse morphism
		OrdinaryMorphism inverseMorph = createMorphism(left, right);

		VarTuple vars = (VarTuple) inverseMorph.getAttrContext().getVariables();
		
//		new LHS: replace attr. expression by variable
		String warning1 = replaceAttrExpressionByVariable(vars, left.getNodesSet().iterator(), true, null); //false, null);
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
		
		warning1 = replaceAttrExpressionByVariable(vars, left.getArcsSet().iterator(), true, null); //false, null);
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
		
		// set mappings
		final Enumeration<GraphObject> dom = morph.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			GraphObject img = morph.getImage(obj);
			GraphObject img1 = isoLeft.getImage(obj);
			GraphObject obj1 = isoRight.getImage(img);
			try {
				inverseMorph.addMapping(obj1, img1);
			} catch (BadMappingException ex) {
				warning = warning.concat(ex.getMessage()).concat(" ;  ");
			}
			// set left attr. members by values of the right attr. members
			if (obj1.getAttribute() != null && img1.getAttribute() != null) {
				ValueTuple valueLeft = (ValueTuple) obj1.getAttribute();
				ValueTuple valueRight = (ValueTuple) img1.getAttribute();
				for (int i = 0; i < valueLeft.getSize(); i++) {
					ValueMember mLeft = valueLeft.getValueMemberAt(i);
					ValueMember mRight = valueRight.getValueMemberAt(mLeft.getName());
					if (!mLeft.isSet() && mRight.isSet()) {
						mLeft.setExprAsText(mRight.getExprAsText());
						mLeft.setTransient(mRight.isTransient()); //(true); 
					}
				}
			}
		}

//		new RHS: replace empty attr of nodes by variable
		warning1 = replaceEmptyAttrByVariable(vars, right.getNodesSet().iterator(), inverseMorph);
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
//		new RHS: replace empty attr of arcs by variable		
		warning1 = replaceEmptyAttrByVariable(vars, right.getArcsSet().iterator(), inverseMorph);	
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
		
		inverseMorph.setErrorMsg(warning);
		
		return new Pair<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>>(
				inverseMorph, new Pair<OrdinaryMorphism, OrdinaryMorphism>(
						isoLeft, isoRight));
	}
	
	private boolean reverseMorphismInto(
			final OrdinaryMorphism srcMorph, 
			OrdinaryMorphism tarMorph,
			final Hashtable<GraphObject, GraphObject> table) {
		
		if (!srcMorph.isInjective()) {
			return false;
		}
		
		String warning = "";
		Graph left = null;
		Graph right = null;
		// make LHS
		if (tarMorph == null) {
			left = srcMorph.getTarget().copy(table);
		}
		else {
			this.copyGraph(srcMorph.getTarget(), tarMorph.getSource(), table);
			left = tarMorph.getSource();
		}
		// make RHS
		if (tarMorph == null) {
			right = srcMorph.getSource().copy(table);
		}
		else {
			this.copyGraph(srcMorph.getSource(), tarMorph.getTarget(), table);
			right = tarMorph.getTarget();		
		}
		if (left == null || right == null) {
			return false;
		}
		

		// make inverse morphism
		if (tarMorph == null)
			tarMorph = createMorphism(left, right);

		VarTuple vars = (VarTuple) tarMorph.getAttrContext().getVariables();
		
//		new LHS: replace attr. expression by variable
		String warning1 = replaceAttrExpressionByVariable(vars, left.getNodesSet().iterator(), true, null); //false, null);
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
		
		warning1 = replaceAttrExpressionByVariable(vars, left.getArcsSet().iterator(), true, null); //false, null);
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
		
		// set mappings
		final Enumeration<GraphObject> dom = srcMorph.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			GraphObject img = srcMorph.getImage(obj);
			GraphObject img1 = table.get(obj);
			GraphObject obj1 = table.get(img);
			try {
				tarMorph.addMapping(obj1, img1);
				
			} catch (BadMappingException ex) {
				warning = warning.concat(ex.getMessage()).concat(" ;  ");
			}
			// set left attr. members by values of the right attr. members
			if (obj1.getAttribute() != null && img1.getAttribute() != null) {
				ValueTuple valueLeft = (ValueTuple) obj1.getAttribute();
				ValueTuple valueRight = (ValueTuple) img1.getAttribute();
				for (int i = 0; i < valueLeft.getSize(); i++) {
					ValueMember mLeft = valueLeft.getValueMemberAt(i);
					ValueMember mRight = valueRight.getValueMemberAt(mLeft.getName());
					if (!mLeft.isSet() && mRight.isSet()) {
						mLeft.setExprAsText(mRight.getExprAsText());
						mLeft.setTransient(mRight.isTransient()); //(true); 
					}
				}
			}
		}

//		new RHS: replace empty attr of nodes by variable
		warning1 = replaceEmptyAttrByVariable(vars, right.getNodesSet().iterator(), tarMorph);
		if (!warning1.equals(""))
			warning = warning.concat(warning1);
//		new RHS: replace empty attr of arcs by variable		
		warning1 = replaceEmptyAttrByVariable(vars, right.getArcsSet().iterator(), tarMorph);	
		if (!warning1.equals("")) {
			warning = warning.concat(warning1);	
		}
		tarMorph.setErrorMsg(warning);
		return true;
	}
	
	/**
	 * Returns an inverse rule construction of the given rule by success, otherwise null.<br>
	 * The rule of the result is the inverse rule r_1 with:<br>
	 * - r_1.LHS is a copy of this.RHS,<br> 
	 * - r_1.RHS is a copy of this.LHS, <br>
	 * - r_1 morphism is the converted rule morphism. <br>
	 * 
	 * The Boolean value is true, when no application conditions (NACs, PACs, GACs, attribute conditions)
	 * of the original rule exist, otherwise false<br>
	 * 
	 * Note: the specified Rule r has to be injective, otherwise returns null.<br>
	 * The first morphism of the second pair is r.LHS -> r_1.RHS,<br>
	 * The second morphism of the second pair is r.RHS -> r_1.LHS. <br>
	 */
	public Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> makeAbstractInverseRule(
			final Rule r) {
		
		final Pair<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		pair = this.reverseMorphism(r);
		
		if (pair != null) {
			final Rule absInverseRule = BaseFactory.theFactory().constructRuleFromMorph(pair.first);
			absInverseRule.setName(r.getName() + "_INV");
			reflectInputParameter(r, absInverseRule);
			
			String warning = pair.first.getErrorMsg().concat(absInverseRule.getErrorMsg());
			if (!warning.isEmpty())
				absInverseRule.setErrorMsg(warning);
			
			if (!r.getNACs().hasMoreElements() 
					&& !r.getPACs().hasMoreElements()
					&& !r.getNestedACs().hasMoreElements()
					&& r.getAttrContext().getConditions().getNumberOfEntries() == 0) {
				return new Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>>(
						new Pair<Rule,Boolean>(absInverseRule, Boolean.TRUE), pair.second);
			}
			else {
				return new Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>>(
						new Pair<Rule,Boolean>(absInverseRule, Boolean.FALSE), pair.second);
			}
		}		
		return null;
	}

	/**
	 * Returns an inverse rule construction of the given rule by success, otherwise null.<br>
	 * The rule of the result is the inverse rule r_1 with:<br>
	 * - r_1.LHS is a copy of this.RHS,<br> 
	 * - r_1.RHS is a copy of this.LHS, <br>
	 * - r_1 morphism is the converted rule morphism. <br>
	 * 
	 * The Boolean value is true, when no application conditions (NACs, PACs, GACs, attribute conditions)
	 * of the original rule exist, 
	 * or they are exist and converted to the inverse rule, otherwise false<br>
	 * 
	 * Note: the specified Rule r has to be injective, otherwise returns null.<br>
	 * The first morphism of the second pair is r.LHS -> r_1.RHS,<br>
	 * The second morphism of the second pair is r.RHS -> r_1.LHS. <br>
	 */
	public Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> reverseRule(final Rule r) {
		boolean failed = false;
		Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>>
		result = makeAbstractInverseRule(r);		
		if (result != null) {			
			final Rule inverseRule = result.first.first;
			boolean needExtend = !result.first.second.booleanValue();
			if (needExtend) {
				OrdinaryMorphism isoRight = result.second.second;	
				
				// first converting PACs from LHS to RHS
				if (this.convertPACsLeft2Right(r, inverseRule, isoRight)) {	
					
					// converting GACs from LHS to RHS
					if (this.convertRuleGACsLeft2Right(r, inverseRule, isoRight)) {				
					
						// converting NACs from LHS to RHS
						this.convertNACsLeft2Right(r, inverseRule, isoRight);				
						// convert attr conditions
						this.convertAttrConditionLeft2Right(r, inverseRule);
						
						result.first.second = Boolean.TRUE;
					}
				}
				else
					failed = true;
			}
			if (failed) {
				result.first.first.dispose();
				result.second.first.dispose();
				result.second.second.dispose();
				result.first = null;
				result.second = null;
				result = null;
			}
			else
				inverseRule.isReadyToTransform();
		}	
		return result;
	}
	
	public void replaceExprByVarInApplConds(
			final List<Rule> rules,
			final Hashtable<ValueMember, Pair<String, String>> storeMap) {
		
		for (int i=0; i<rules.size(); i++) {
			Rule r = rules.get(i);
			
			Enumeration<OrdinaryMorphism> applConds = r.getNACs();
			while (applConds.hasMoreElements()) {
				OrdinaryMorphism morph = applConds.nextElement();
				VarTuple vars = (VarTuple) morph.getAttrContext().getVariables();
				
				replaceAttrExpressionByVariable(
						vars, 
						morph.getTarget().getNodesSet().iterator(),
						true,
						storeMap);
				
				replaceAttrExpressionByVariable(
						vars, 
						morph.getTarget().getArcsSet().iterator(),
						true,
						storeMap);
//				((VarTuple) morph.getAttrContext().getVariables()).showVariables();
			}
			
			applConds = r.getPACs();
			while (applConds.hasMoreElements()) {
				OrdinaryMorphism morph = applConds.nextElement();
				VarTuple vars = (VarTuple) morph.getAttrContext().getVariables();
				
				replaceAttrExpressionByVariable(
						vars, 
						morph.getTarget().getNodesSet().iterator(),
						true,
						storeMap);
				
				replaceAttrExpressionByVariable(
						vars, 
						morph.getTarget().getArcsSet().iterator(),
						true,
						storeMap);
//				((VarTuple) morph.getAttrContext().getVariables()).showVariables();
			}
		}
	}
	
	/**
	 * Replace expressions in the attributes of the specified elements by a new variable
	 * which is added to the specified variable tuple.
	 *
	 * @param vars
	 * @param elems
	 * @return 	a warning message (not an error!) about replacement
	 */
	public String replaceAttrExpressionByVariable(
			final VarTuple vars, 
			final Iterator<?> elems,
			boolean setTransient,
			final Hashtable<ValueMember, Pair<String, String>> storeMap) {
	
		int nn = -1;
		String exprMsg = "";
		while (elems.hasNext()) {
			GraphObject grob = (GraphObject) elems.next();
			nn++;
			if (grob.getAttribute() == null)
				continue;
			ValueTuple value = (ValueTuple) grob.getAttribute();
			for (int i = 0; i < value.getSize(); i++) {
				String nm = (i == 0) ? "" : ("" + i);
				ValueMember val = value.getValueMemberAt(i);
				if (val.isSet()) {
					if (val.getExpr().isComplex()) {
						exprMsg = "Attr. expression   ".concat(val.getExprAsText());					
						String varname = "expr" + nn + nm;
						
						if (storeMap != null) {
							storeMap.put(val, new Pair<String,String>(varname, val.getExprAsText()));
						}
						
						val.setExpr(null);
						
						vars.declare(DefaultInformationFacade.self()
								.getJavaHandler(), val.getDeclaration()
								.getTypeName(), varname);
						vars.getEntryAt(varname).setTransient(setTransient);
						
						val.setExprAsText(varname);
						val.setTransient(setTransient);
						
						exprMsg = exprMsg.concat("   replaced by a new variable   ").concat(varname).concat(" ;  ");
//						System.out.println(exprMsg);
					} 
					// additionally check undeclared variable
					else if (val.getExpr().isVariable()) {
						if (vars.getVarMemberAt(val.getExprAsText()) == null) {
							String varname = val.getExprAsText();
							vars.declare(DefaultInformationFacade.self()
									.getJavaHandler(), val.getDeclaration()
									.getTypeName(), varname);
							vars.getEntryAt(varname).setTransient(true);
							val.setTransient(setTransient);
						}
					}
				}
			}
		}
		return exprMsg;
	}
	
	public void restoreExprByVarInApplConds(
			final List<Rule> rules,
			final Hashtable<ValueMember, Pair<String, String>> storeMap) {
		
		if (storeMap == null || storeMap.isEmpty()) {
			return;
		}
		
		for (int i=0; i<rules.size(); i++) {
			Rule r = rules.get(i);
			
			Enumeration<OrdinaryMorphism> applConds = r.getNACs();
			while (applConds.hasMoreElements()) {
				OrdinaryMorphism morph = applConds.nextElement();
				VarTuple vars = (VarTuple) morph.getAttrContext().getVariables();
				
				this.restoreAttrExpressionReplacedByVariable(
						vars, 
						morph.getTarget().getNodesSet().iterator(),
						storeMap);
				
				this.restoreAttrExpressionReplacedByVariable(
						vars, 
						morph.getTarget().getArcsSet().iterator(),
						storeMap);
//				((VarTuple) morph.getAttrContext().getVariables()).showVariables();
			}
			
			applConds = r.getPACs();
			while (applConds.hasMoreElements()) {
				OrdinaryMorphism morph = applConds.nextElement();
				VarTuple vars = (VarTuple) morph.getAttrContext().getVariables();
				
				this.restoreAttrExpressionReplacedByVariable(
						vars, 
						morph.getTarget().getNodesSet().iterator(),
						storeMap);
				
				this.restoreAttrExpressionReplacedByVariable(
						vars, 
						morph.getTarget().getArcsSet().iterator(),
						storeMap);
//				((VarTuple) morph.getAttrContext().getVariables()).showVariables();
			}
		}
	}
	
	
	private void restoreAttrExpressionReplacedByVariable(
			final VarTuple vars, 
			final Iterator<?> elems,
			final Hashtable<ValueMember, Pair<String, String>> storeMap) {
	
		while (elems.hasNext()) {
			GraphObject grob = (GraphObject) elems.next();
//			System.out.println("BF.replaceAttrExpressionByVariable:   inside of  "+grob.getContext().getName());
			if (grob.getAttribute() == null)
				continue;
			
			ValueTuple value = (ValueTuple) grob.getAttribute();
			for (int i = 0; i < value.getSize(); i++) {
				ValueMember val = value.getValueMemberAt(i);				
				Pair<String,String> p = storeMap.get(val);
				if (p != null) {				
					String var = p.first;
					String expr = p.second;							
					val.setExprAsText(expr);
					val.setTransient(false);						
					vars.getTupleType().deleteMemberAt(var);
				} 
			}
		}
	}
	

	public Rule checkApplCondsOfRules(final List<Rule> rules) {
		for (int i=0; i<rules.size(); i++) {
			if (!rules.get(i).areApplCondsValid())
				return rules.get(i);
		}
		return null;
	}
	
	/*
	 * Returns true if the Match m1 does not delete or changed objects of the Match m2,
	 * or vice versa. Otherwise returns false.<br>
	 * The given matches must be complete and valid.<br> 
	 * Potential produce-forbid conflicts would not be checked. 
	 */
	public boolean checkWeakParallelMatches(final Match m1, final Match m2) {
		if (m1 != null && m1.isValid()
				&& m2 != null && m2.isValid()) {
			Enumeration<GraphObject> dom1 = m1.getDomain();
			while (dom1.hasMoreElements()) {
				GraphObject go1 = dom1.nextElement();
				GraphObject go = m1.getImage(go1);
				if (m2.hasInverseImage(go)) {
					GraphObject go2 = m2.getInverseImage(go).nextElement();
					// check r2 delete
					if (m2.getRule().getImage(go2) == null) {
						return false;
					}
					// check r1 delete
					else if (m1.getRule().getImage(go1) == null) {
						return false;
					}
					else {
						GraphObject img1 = m1.getRule().getImage(go1);
						GraphObject img2 = m2.getRule().getImage(go2);
						// check change attribute
						if (img1.getAttribute() != null && img2.getAttribute() != null) {
							ValueTuple vt1l = (ValueTuple) go1.getAttribute();
							ValueTuple vt2l = (ValueTuple) go2.getAttribute();
							ValueTuple vt1r = (ValueTuple) img1.getAttribute();
							ValueTuple vt2r = (ValueTuple) img2.getAttribute();
							for (int i=0; i<vt1r.getNumberOfEntries(); i++) {
								ValueMember vm1r = vt1r.getEntryAt(i);
								ValueMember vm1l = vt1l.getEntryAt(vm1r.getName());
								// check r1 change attribute
								if (vm1r.isSet()
										&& (!vm1l.isSet()
												|| !vm1r.getExprAsText().equals(vm1l.getExprAsText()))) {
									return false;
								}
								else {
									ValueMember vm2r = vt2r.getEntryAt(vm1r.getName());
									ValueMember vm2l = vt2l.getEntryAt(vm1r.getName());
									// check r2 change attribute
									if (vm2r.isSet()
										&& (!vm2l.isSet()
												|| !vm2r.getExprAsText().equals(vm2l.getExprAsText()))) {
										return false;
									}
								}
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public ParallelRule makeParallelRule(final TypeSet types, final List<Rule> rules) {
		if (rules.size() == 2)
			return new ParallelRule(types, rules.get(0), rules.get(1));
		else
			return null;
	}
	
	/**
	 * Creates a new concurrent rule which is constructed as 
	 * a disjoint union of LHS1 and LHS2 (resp. RHS1 and RHS2) 
	 * of the given two rules.<br> 
	 * The application conditions (NACs, PACs, attr. condition) of the input rules
	 * will be shifted to the new concurrent rule.<br>
	 * @param r1 	first rule
	 * @param r2	second rule
	 * @return ConcurrentRule
	 */
	private ConcurrentRule makeConcurrentRuleByDisjointUnion(final Rule r1, final Rule r2) {
		final Hashtable<String, String> storeNewName2OldName = new Hashtable<String, String>();
		if (r1 != r2) {
			BaseFactory.theFactory().renameSimilarVariable(r2, r1, "r1_", storeNewName2OldName);
//			((VarTuple) r1.getAttrContext().getVariables()).showVariables();
//			((CondTuple) r1.getAttrContext().getConditions()).showConditions();
		}
		
		final ConcurrentRule cr = new ConcurrentRule(r1, r2);
		
		if (!storeNewName2OldName.isEmpty()) {
			BaseFactory.theFactory().restoreVariableNameOfRule(r1, storeNewName2OldName);
		}
		return cr;
	}
	
	/**
	 * Creates a new jointly concurrent rule. The given object flow
	 * (r1.RHS.object -> r2.LHS2.object) defines the jointly usable 
	 * objects of the RHS of the first rule
	 * and the LHS of the second rule.<br> 
	 * The application conditions (NACs, attr. condition) of the input rules
	 * will be shifted to the new concurrent rule.<br>
	 * The PACs of rules r1 and r2 are integrated in the r1.LHS 
	 * resp. r2.LHS at the begin of the creating process.<br> 
	 * The application conditions (NACs, attr. condition) of the input rules
	 * will be shifted to the new concurrent rule.<br>
	 * @param r1 	first rule
	 * @param r2	second rule
	 * @param objFlow a map with (r1.RHS.object -> r2.LHS.object)
	 * @return ConcurrentRule
	 */
	private ConcurrentRule makeConcurrentRuleByObjectFlow(
			final Rule r1, 
			final Rule r2,
			final Hashtable<Object, Object> objFlow) {
				
		if (objFlow.isEmpty()) {
			return this.makeConcurrentRuleByDisjointUnion(r1, r2);
		}
		
		ConcurrentRule cr = null;
		// rename similar variables of rule1
		final Hashtable<String, String> storeNewName2OldName = new Hashtable<String, String>();
		if (r1 != r2) {
			BaseFactory.theFactory().renameSimilarVariable(r2, r1, "r1_", storeNewName2OldName);
//			((VarTuple) r1.getAttrContext().getVariables()).showVariables();
//			((CondTuple) r1.getAttrContext().getConditions()).showConditions();
		}
				
		Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		inverseRulePair = BaseFactory.theFactory().reverseRule(r1);
		Rule inverseRule1 = inverseRulePair.first.first;		
				
		int maxsize = r2.getLeft().getSize();
		if (!objFlow.isEmpty())
			maxsize = objFlow.size();
		
		if (maxsize > 0) {
			long freeM = Runtime.getRuntime().freeMemory();
//			boolean disjoint_union = false;
//			boolean withIsomorphic = true; //false;
			
			// matchmap inverse:: keys to values, values to keys, because of inverse r1
			final Hashtable<Object, Object> inversematchmap = new Hashtable<Object, Object>();
			Enumeration<?> keys = objFlow.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				// r2.lhs.object -> r1inverse.rhs.object
				inversematchmap.put(objFlow.get(key), 
						inverseRulePair.second.second.getImage((GraphObject)key));
			}
			
			Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
			enums = BaseFactory.theFactory().getOverlappingByPredefinedIntersection(
						r2.getLeft(), inverseRule1.getLeft(), inversematchmap);						
			
			if (enums != null 
					&& enums.hasMoreElements()) {			
				final Pair<OrdinaryMorphism, OrdinaryMorphism> overlapping = enums.nextElement();															
//				if (this.checkIntersectionDuetoObjectFlow(overlapping, objFlow)) 
				{						
					cr = new ConcurrentRule(r1, inverseRulePair.first.first, r2,
								inverseRulePair.second.first, 
								inverseRulePair.second.second,
								overlapping.second,
								overlapping.first);
						
					if (cr.getRule() != null) {									
						System.out.println("=== >>>  Concurrent rule: "
										+cr.getRule().getName()
										+"  has NACs: "+cr.getRule().getNACs().hasMoreElements()
										+", has PACs: "+cr.getRule().getPACs().hasMoreElements());
//					((VarTuple) cr.getRule().getAttrContext().getVariables()).showVariables();
						
						cr.usedM = freeM - Runtime.getRuntime().freeMemory();
					}				
				}
			}
		}
				
		if (!storeNewName2OldName.isEmpty()) {
			BaseFactory.theFactory().restoreVariableNameOfRule(r1, storeNewName2OldName);
		}
		return cr;
	}
	
	/**
	 * Creates a concurrent rule based on the given RuleSequence.<br>
	 * If the second parameter is <code>false</code>, the left and right graphs 
	 * of the returned concurrent rule are constructed as disjoint unions of the left 
	 * and right graphs of the rules of the sequence.<br>
	 * If the second parameter is <code>true</code> and an ObjectFlow of the given rule sequence
	 * is defined, the left and right graphs of the concurrent rule are overlapping graphs above
	 * the ObjectFlow,<br>
	 * otherwise it returns null.<br>
	 * The backward construction starts with the two last rules of the RuleSequence.
	 * The next previous rule will be used at the next building step.
	 * 
	 * @param sequence  is a RuleSequence
	 * @param byObjectFlow
	 * @return a ConcurrentRule based on the disjoint union or the ObjectFlow of the RuleSequence
	 */
	public ConcurrentRule makeConcurrentRuleOfRuleSeqBackwards(
			final RuleSequence sequence,
			boolean byObjectFlow) {

		long freeM = 0;
		ConcurrentRule cr = null;
		boolean ok = true;
		
		if (byObjectFlow) {
			sequence.makeFlatSequence();
			sequence.tryCompleteObjFlowTransClosure();
		}
		
		RuleSequence rs = sequence.getCopy();		
//		RuleSequence.printObjFlow(rs);
		
		int size = rs.getSize();
		int i2 = -1;
		int i1 = -1;
		while (size>1 && ok) {
			i2 = size-1;
			i1 = i2-1;
			Rule r1 = rs.getRule(i1);
			Rule r2 = rs.getRule(i2);
			
			if (byObjectFlow) {
				ObjectFlow objFlow_r1r2 = rs.getObjFlowForRules(r1, i1, r2, i2);
//				System.out.println(objFlow_r1r2.getKey());
				if (objFlow_r1r2 != null 		
						&& !objFlow_r1r2.isEmpty()) {
					cr = this.makeConcurrentRuleByObjectFlow(r1, r2, objFlow_r1r2.getMapping());
				} else {
					cr = this.makeConcurrentRuleByDisjointUnion(r1, r2);
				}
			}
			else {
				cr = this.makeConcurrentRuleByDisjointUnion(r1, r2);
			} 
			
			if (cr == null || cr.getRule() == null) {
				ok = false;
			} 
			else {
				freeM = Runtime.getRuntime().freeMemory();
				
				cr.setIndexOfFirstSourceRule(i1);
				cr.setIndexOfSecondSourceRule(i2);

				List<ObjectFlow> newObjFlows = new Vector<ObjectFlow>();
				if (byObjectFlow) {
					List<ObjectFlow> r1ObjFlow = rs.getObjFlowForRule(r1, i1);
					List<ObjectFlow> r2ObjFlow = rs.getObjFlowForRule(r2, i2);
					
					cr.reflectObjectFlow(r1ObjFlow);
					cr.reflectObjectFlow(r2ObjFlow);
									
					for (int j=0; j<i1; j++) {
						Rule rj = rs.getRule(j);
	
						List<ObjectFlow> rjObjFlow = rs.getObjFlowFromRule(rj, j);
						if (!rjObjFlow.isEmpty()) {
							Hashtable<Object, Object> 
							rjOutIn = new Hashtable<Object, Object>(cr.getReflectedInputObjectFlowFromRule(rj, rjObjFlow));
							if (!rjOutIn.isEmpty()) {
								int indx1 = j;
								int indx2 = i1;
								if (rs.getGraph() != null) {
									indx1++;
									indx2++;
								}
								ObjectFlow objFlow = new ObjectFlow(rj, cr.getRule(), 
																indx1, indx2, rjOutIn);
								newObjFlows.add(objFlow);
							}
						}
					}
					
					List<ObjectFlow> gObjFlow = rs.getObjFlowFromGraph();
					if (!gObjFlow.isEmpty()) {
						Hashtable<Object, Object> 
						gOutIn = new Hashtable<Object, Object>(cr.getReflectedInputObjectFlowFromGraph(rs.getGraph(), gObjFlow));
						if (!gOutIn.isEmpty()) {
							int indx2 = i1;
							if (rs.getGraph() != null) {
								indx2++;
							}
							ObjectFlow objFlow = new ObjectFlow(rs.getGraph(), cr.getRule(), 
															0, indx2, gOutIn);
							newObjFlows.add(objFlow);
						}
					}
				}
				
				rs.removeRule(i2);
				rs.removeRule(i1);
				
				rs.addRule(cr.getRule());	
				
				for (int l=0; l<newObjFlows.size(); l++) {
					rs.addObjFlow(newObjFlows.get(l));
				}
				rs.tryCompleteObjFlowTransClosure();
//				RuleSequence.printObjFlow(rs);
				
				size = rs.getSize();
			}					
		}
		
		if (cr != null && rs.getSize() == 1 && cr.getRule() == rs.getRule(0)) {
			List<ObjectFlow> gOF = rs.getObjFlowFromGraph();
			if (gOF != null) {
				Match m = this.createMatch(cr.getRule(), rs.getGraph());
				cr.getRule().setMatch(m);
				for (int i=0; i<gOF.size(); i++) {
					ObjectFlow of = gOF.get(i);
					List<Object> inpts = of.getInputs();
					for (int j=0; j<inpts.size(); j++) {
						GraphObject go = (GraphObject) inpts.get(j);
						if (go.isNode() && go.getContext() == cr.getRule().getLeft()) {
							try {
								m.addMapping(go, (GraphObject)of.getOutput(go));
							} catch (BadMappingException ex) {}
						}
					}
					inpts = of.getInputs();
					for (int j=0; j<inpts.size(); j++) {
						GraphObject go = (GraphObject) inpts.get(j);
						if (go.isArc() && go.getContext() == cr.getRule().getLeft()) {
							try {
								m.addMapping(go, (GraphObject)of.getOutput(go));
							} catch (BadMappingException ex) {}
						}
					}
				}
			}
			cr.usedM = cr.usedM + freeM - Runtime.getRuntime().freeMemory();
		}
		
		return cr;
	}
	
	public List<ConcurrentRule> makeConcurrentRuleOfRuleSeqForwards(
			final RuleSequence sequence,
			final GraGra gra,
			boolean completeConcurrency) {
				
		final RuleSequence rs = sequence.getCopy();
		final ApplicabilityChecker applChecker = new ApplicabilityChecker(rs, gra);
		applChecker.setCompleteConcurrency(completeConcurrency);
		List<ConcurrentRule> crs = applChecker.buildPlainConcurrentRule(rs.getRules(), null);
		return crs;
	}
	
	private String replaceEmptyAttrByVariable(final VarTuple vars,
			final Iterator<?> e, 
			final OrdinaryMorphism morph) {
		String exprMsg = "";
		while (e.hasNext()) {
			GraphObject grob = (GraphObject) e.next();
			if (grob.getAttribute() == null)
				continue;
			if (!morph.getInverseImage(grob).hasMoreElements()) {
				ValueTuple value = (ValueTuple) grob.getAttribute();
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember val = value.getValueMemberAt(i);
					if (!val.isSet()) {
						exprMsg = "attribute member:  ".concat(val.getName());
						String varname = "r" + i;
						vars.declare(DefaultInformationFacade.self()
								.getJavaHandler(), val.getDeclaration()
								.getTypeName(), varname);
						val.setExprAsText(varname);
						val.setTransient(true);
						vars.getEntryAt(varname).setTransient(true); 
						exprMsg = exprMsg.concat("  set by a new variable:  ").concat(varname).concat(" ;  ");
					} else if (val.getExpr().isVariable()) {
						if (vars.getVarMemberAt(val.getExprAsText()) == null) {
							String varname = val.getExprAsText();
							vars.declare(DefaultInformationFacade.self()
									.getJavaHandler(), val.getDeclaration()
									.getTypeName(), varname);
							vars.getEntryAt(varname).setTransient(true); 
						}
					}
				}
			}
		}
		return exprMsg;
	}

	public void replaceTransientTarVarBySrcVar(final OrdinaryMorphism morph) {
		replaceTransTarVarBySrcVar(morph.getTarget().getNodesCollection().iterator(), 
									morph, 
									(VarTuple)morph.getAttrContext().getVariables());

		replaceTransTarVarBySrcVar(morph.getTarget().getArcsCollection().iterator(), 
									morph, 
									(VarTuple)morph.getAttrContext().getVariables());
	}
	
	private void replaceTransTarVarBySrcVar(
			final Iterator<?> e, 
			final OrdinaryMorphism morph,
			final VarTuple vars) {
		
		while (e.hasNext()) {
			GraphObject tar = (GraphObject) e.next();
			if (!tar.attrExists())
				continue;
			if (morph.getInverseImage(tar).hasMoreElements()) {
				GraphObject src = morph.getInverseImage(tar).nextElement();	
				if (!src.attrExists())
					continue;
				ValueTuple srcVal = (ValueTuple) src.getAttribute();
				ValueTuple tarVal = (ValueTuple) tar.getAttribute();
				for (int i = 0; i < tarVal.getSize(); i++) {
					ValueMember tarM = tarVal.getValueMemberAt(i);
					if (tarM.isSet() && tarM.isTransient()) {
						ValueMember srcM = srcVal.getValueMemberAt(tarM.getName());
						if (srcM != null && srcM.isSet() 
								&& srcM.getExpr().isVariable()) {
							VarMember v = vars.getVarMemberAt(srcM.getExprAsText());
							if (v != null && !v.isTransient()) {
								tarM.setExprAsText(v.getName());
								tarM.setTransient(false);
							}
						}
					} 
				}
			}
		}
	}
	
	public void reflectInputParameter(Rule r, Rule absInvertRule) {
		VarTuple varsOfInvertRule = (VarTuple) absInvertRule.getAttrContext().getVariables();

		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		for (int i=0; i<vars.getNumberOfEntries(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (vm.isInputParameter()) {
				VarMember vmOfInvertRule = varsOfInvertRule.getVarMemberAt(vm.getName());
				if (vmOfInvertRule != null)
					vmOfInvertRule.setInputParameter(true);
			}
		}
	} 
	
	private void convertAttrConditionLeft2Right(Rule r, Rule inverseR) {	
		CondTuple conds = (CondTuple) r.getAttrContext().getConditions();
		if (conds.isEmpty())
			return;
						
		CondTuple condsOfInverseR = (CondTuple) inverseR.getAttrContext().getConditions();		
		
		for (int i=0; i<conds.getNumberOfEntries(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
//			Vector<String> condVars = cond.getAllVariables();			
			condsOfInverseR.addCondition(cond.getExprAsText());
		}
	}
	
	/*
	private void convertAttrConditionFromLeft2Right(Rule r, Rule inverseR) {	
		CondTuple conds = (CondTuple) r.getAttrContext().getConditions();
		if (conds.isEmpty())
			return;
				
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		
		CondTuple condsOfInverseR = (CondTuple) inverseR.getAttrContext().getConditions();		
		VarTuple varsOfInverseR = (VarTuple) inverseR.getAttrContext().getVariables();
		
		Hashtable<VarMember, Boolean>
		varLeftRight = new Hashtable<VarMember, Boolean>();
		for (int i=0; i<vars.getNumberOfEntries(); i++) {
			VarMember var = vars.getVarMemberAt(i);
			if (var.getMark() == VarMember.LHS) {
				if (variableUsed(var, r.getLeft()) ){
					VarMember varInverse = varsOfInverseR.getVarMemberAt(var.getName());
					if (variableUsed(var, r.getRight())) {
						varLeftRight.put(var, Boolean.valueOf(true));
						varInverse.setMark(VarMember.LHS);
					}
					else {
						varLeftRight.put(var, Boolean.valueOf(false));
						varInverse.setMark(VarMember.RHS);
					}
				}
			}
		}
		
		for (int i=0; i<conds.getNumberOfEntries(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
			Vector<String> condVars = cond.getAllVariables();
			boolean varsOK = true;
			for (int j=0; j<condVars.size(); j++) {
				String varN = condVars.get(j);
				VarMember var = vars.getVarMemberAt(varN);
				if (var.getMark() == VarMember.LHS) {
					if (!varLeftRight.get(var).booleanValue()) 
						varsOK = false;
				}
			}
			if (varsOK) {
				condsOfInverseR.addCondition(cond.getExprAsText());
			}
		}
	}

	
	private boolean variableUsed(VarMember var, Graph g) {
		return varUsed(var, g.getNodesSet().iterator())
					|| varUsed(var, g.getArcsSet().iterator());
	}
	
	
	private boolean varUsed(VarMember var, Iterator<?> elems) {
		while (elems.hasNext()) {
			GraphObject obj = (GraphObject)elems.next();
			if (obj.getAttribute() == null) 
				continue;
			ValueTuple val = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < val.getSize(); i++) {
				ValueMember vm = val.getValueMemberAt(i);
				if (vm.isSet() && vm.getExpr().isVariable()) {
					if (vm.getExprAsText().equals(var.getName()))
							return true;
				}
			}			
		}
		return false;
	}
	*/
	
	public Pair<OrdinaryMorphism, OrdinaryMorphism> extendRightGraphByNAC(
			final Rule r,
			final OrdinaryMorphism nacL) {
				 
		OrdinaryMorphism isoLHS = r.getLeft().isomorphicCopy();
		if (isoLHS == null) {
			return null;
		}
		// extLeft: L -> Lcopy
		OrdinaryMorphism extLeft = extendTargetGraph1ByTargetGraph2(isoLHS, nacL);
		if (extLeft == null)
			return null;
		
		Graph extLeftGraph = extLeft.getTarget();
		Match m = (BaseFactory.theFactory()).createMatch(r, extLeftGraph, true, "1");
		// extLeftGraph is the graph m.getTarget() 
		m.getTarget().setCompleteGraph(false);
//		m.setCompletionStrategy(new Completion_InjCSP(), true);
		// set match mapping
		Iterator<Node> lhsNodes = r.getLeft().getNodesSet().iterator();
		while (lhsNodes.hasNext()) {
			Node n = lhsNodes.next();
			Node img = (Node) isoLHS.getImage(n);
			if (img != null) {
				try {
					m.addMapping(n, img);
				} catch (BadMappingException ex) {
					System.out.println("BaseFactory.extendRightGraphByNAC:  "+n+"  "+ex);
					return null;
				}
			}
		}
		Iterator<Arc> lhsArcs = r.getLeft().getArcsSet().iterator();
		while (lhsArcs.hasNext()) {
			Arc a = lhsArcs.next();
			Arc img = (Arc) isoLHS.getImage(a);
			if (img != null) {
				try {
					m.addMapping(a, img);
				} catch (BadMappingException ex) {
					System.out.println("extendRightGraphByNAC:  "+a+"  "+ex);
					return null;
				}
			}
		}
		if (m.isTotal()) {
			if (!m.isDanglingSatisfied()) {
				System.out.println("extendRightGraphByNAC:  isDanglingConditionSatisfied  FAILED!");
				return null;
			}
			
//			final TestStep s = new TestStep();
			try {
				OrdinaryMorphism R2R_NAC = (OrdinaryMorphism) TestStep.execute(m, true);
				R2R_NAC.setName("RHS_"+nacL.getName());
//				System.out.println("extendRightGraphByNAC: right NAC: "+nacR.getTarget());
				
				return new Pair<OrdinaryMorphism, OrdinaryMorphism>(R2R_NAC, extLeft);				

			} catch (TypeException tex) {
				System.out.println("extendRightGraphByNAC:  s.execute:  "+tex);
				return null;
			}
		} 
		System.out.println("extendRightGraphByNAC:  m  is NOT TOTAL! FAILED!");
		
		return null;		
	}
	
	private void convertNACsLeft2Right(
			final Rule r, 
			final Rule inverseRule, 
			final OrdinaryMorphism isoRHS) {
		
		final List<OrdinaryMorphism> nacs = r.getNACsList();
		for(int i=0; i<nacs.size(); i++) {
			OrdinaryMorphism acL = nacs.get(i);OrdinaryMorphism acR = null;
			if (r.isACShiftPossible(acL)) {
				int tglevelcheck = r.getTypeSet().getLevelOfTypeGraphCheck();
				r.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);
				acR = convertACLeft2Right(r, acL);
				if (acR != null) {
					r.getTypeSet().setLevelOfTypeGraph(tglevelcheck);
					Collection<TypeError> error = r.getTypeSet().checkType(acR.getTarget());
					if (error != null && !error.isEmpty()) {
						acR.dispose(false, true);
						acR = null;
					}
				}
			}
			boolean ok = false;
			if (acR != null) {
				OrdinaryMorphism ac = BaseFactory.theFactory().createMorphism(
						inverseRule.getLeft(),
						acR.getTarget());
				ok = acR.completeDiagram(isoRHS, ac);
				if (ok) {
					if (!ac.isRightTotal()
							|| !ac.doesIgnoreAttrs()) {
						this.unsetAllTransientAttrValues(ac);
						// set and adjust attr context
						ac.setAttrContext(inverseRule.getLeft().getAttrContext());
						this.declareVariable(ac.getTarget(), (VarTuple)inverseRule.getAttrContext().getVariables());
						adjustAttributeValueAlongMorphismMapping(ac);					
						ac.setName(acR.getName());
						ac.setEnabled(acL.isEnabled());
						inverseRule.addNAC(ac);
					}
				}
			} 
			if (!ok) {
				String warning = inverseRule.getErrorMsg();
				String warning1 = "NAC: ".concat(acL.getName()).concat("  could not be converted");
				warning = warning.concat(warning1).concat(" ;  ");					
				inverseRule.setErrorMsg(warning);
			}
		}
	}
	
	private boolean convertNACsLeft2Right(
			final Rule r, 
			final Rule inverseRule, 
			final Hashtable<GraphObject,GraphObject> isoRight) {
		
		final List<OrdinaryMorphism> acs = r.getNACsList();
		for(int i=0; i<acs.size(); i++) {
			OrdinaryMorphism acL = acs.get(i);
			OrdinaryMorphism acR = null;
			if (r.isACShiftPossible(acL)) {
				int tglevelcheck = r.getTypeSet().getLevelOfTypeGraphCheck();
				r.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);
				acR = convertACLeft2Right(r, acL);
				r.getTypeSet().setLevelOfTypeGraph(tglevelcheck);
				Collection<TypeError> error = r.getTypeSet().checkType(acR.getTarget());
				if (error != null && !error.isEmpty()) {
					acR.dispose(false, true);
					acR = null;
				}
			}
			boolean ok = false;
			if (acR != null) {
				OrdinaryMorphism ac = BaseFactory.theFactory().createMorphism(
										inverseRule.getLeft(),
										acR.getTarget());
				ok = ac.completeDiagram(isoRight, acR);
				if (ok){
					if (!ac.isRightTotal()
							|| !ac.doesIgnoreAttrs()) {
						this.unsetAllTransientAttrValues(ac);
						// set and adjust attr context
						ac.setAttrContext(inverseRule.getLeft().getAttrContext());
						this.declareVariable(ac.getTarget(), (VarTuple)inverseRule.getAttrContext().getVariables());
						adjustAttributeValueAlongMorphismMapping(ac);
						ac.setEnabled(acL.isEnabled());
						ac.setName(acR.getName());
						inverseRule.addNAC(ac);
					}
				}
			} 
			if (!ok) {
				String warning = inverseRule.getErrorMsg();
				String warning1 = "NAC: ".concat(acL.getName()).concat("  could not be shifted from left to right");
				warning = warning.concat(warning1).concat(" ;  ");					
				inverseRule.setErrorMsg(warning);
			}
		}
		return true;
	}
	
	private boolean convertRuleGACsLeft2Right(
			final Rule r, 
			final Rule inverseRule, 
			final OrdinaryMorphism isoRight) {
				
		boolean failed = false;
//		Vector<OrdinaryMorphism> racs = new Vector<OrdinaryMorphism>();
		
		final List<OrdinaryMorphism> acs = r.getNestedACsList();
		for(int i=0; i<acs.size() && !failed; i++) {
			NestedApplCond acL = (NestedApplCond)acs.get(i);
			OrdinaryMorphism acR = null; 
			Pair<OrdinaryMorphism,OrdinaryMorphism> pairPO = null;
			if (r.isACShiftPossible(acL)) {
				int tglevelcheck = r.getTypeSet().getLevelOfTypeGraphCheck();
				r.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);
				pairPO = convertNestedACLeft2Right(r, acL);
				r.getTypeSet().setLevelOfTypeGraph(tglevelcheck);
				if (pairPO != null) {
					acR = pairPO.second;
					Collection<TypeError> error = r.getTypeSet().checkType(acR.getTarget());
					if (error != null && !error.isEmpty()) {
						acR.dispose(false, true);
						acR = null;
					}
				}
			}			
			if (acR != null) {
				NestedApplCond ac = new NestedApplCond(inverseRule.getLeft(), acR.getTarget(),
						inverseRule.getRight().getAttrContext());
				ac.completeDiagram2(isoRight, acR);
				this.unsetAllTransientAttrValues(ac);
				// set and adjust attr context
				ac.getTarget().setAttrContext(ac.getAttrContext());
				this.declareVariable(ac.getTarget(), (VarTuple)inverseRule.getAttrContext().getVariables());
				adjustAttributeValueAlongMorphismMapping(ac);					
				ac.setName(acL.getName());
				
//				racs.add(ac);
				inverseRule.addNestedAC(ac);
					
				if (!acL.getNestedACs().isEmpty()) {
					if (!convertNestedACsLeft2Right(pairPO.first, acL, acR, ac, inverseRule)) {
						//TODO check formula: replace !ac by TRUE, otherwise formula invalid ???
						//
					}
				}				
			} 
			else {
				//TODO check formula: replace !ac by TRUE, otherwise formula invalid ???
				//
				String warning = inverseRule.getErrorMsg();
				String warning1 = "General AC: ".concat(acL.getName()).concat("  could not be converted");
				warning = warning.concat(warning1).concat(" ;  ");					
				inverseRule.setErrorMsg(warning);
				failed = true;
			}
		}
		if (!failed) {
//			for (int i=0; i<racs.size(); i++) {
//				inverseRule.addNestedAC(racs.get(i));
//			}
			inverseRule.setFormula(r.getFormulaStr());
		}
//		else {
//			for (int i=0; i<racs.size(); i++) {
//				((NestedApplCond)racs.get(i)).dispose();
//				inverseRule.addNestedAC(racs.get(i));					
//			}
//		}
//		racs.clear(); racs = null;
		return !failed;
	}
	
	private boolean convertGACsLeft2Right(
			final Rule r, 
			final Rule inverseRule, 
			final Hashtable<GraphObject,GraphObject> isoRight) {
				
		boolean failed = false;
//		Vector<OrdinaryMorphism> racs = new Vector<OrdinaryMorphism>();
		
		final List<OrdinaryMorphism> acs = r.getNestedACsList();
		for(int i=0; i<acs.size() && !failed; i++) {
			NestedApplCond acL = (NestedApplCond)acs.get(i);
			OrdinaryMorphism acR = null; 
			Pair<OrdinaryMorphism,OrdinaryMorphism> pairPO = convertNestedACLeft2Right(r, acL);
			if (pairPO != null) {
				acR = pairPO.second;			
				NestedApplCond ac = new NestedApplCond(inverseRule.getLeft(), acR.getTarget(),				
							agg.attribute.impl.AttrTupleManager
							.getDefaultManager().newContext(AttrMapping.PLAIN_MAP));
				ac.completeDiagram(isoRight, acR);
				this.unsetAllTransientAttrValues(ac);
				// set and adjust attr context
				ac.setAttrContext(inverseRule.getLeft().getAttrContext());
				this.declareVariable(ac.getTarget(), (VarTuple)inverseRule.getAttrContext().getVariables());
				adjustAttributeValueAlongMorphismMapping(ac);					
				ac.setName(acL.getName());
				
//				racs.add(ac);
				inverseRule.addNestedAC(ac);
					
				if (!acL.getNestedACs().isEmpty()) {
					if (!convertNestedACsLeft2Right(pairPO.first, acL, acR, ac, inverseRule)) {
						//TODO check formula: replace !ac by TRUE, otherwise formula invalid ???
						//
					}
				}				
			} 
			else {
				//TODO check formula: replace !ac by TRUE, otherwise formula invalid ???
				//
				String warning = inverseRule.getErrorMsg();
				String warning1 = "General AC: ".concat(acL.getName()).concat("  could not be converted");
				warning = warning.concat(warning1).concat(" ;  ");					
				inverseRule.setErrorMsg(warning);
				failed = true;
			}
		}
		if (!failed) {
//			for (int i=0; i<racs.size(); i++) {
//				inverseRule.addNestedAC(racs.get(i));
//			}
			inverseRule.setFormula(r.getFormulaStr());
		}
//		else {
//			for (int i=0; i<racs.size(); i++) {
////				((NestedApplCond)racs.get(i)).dispose();
//				inverseRule.addNestedAC(racs.get(i));
//			}
//		}
//		racs.clear(); racs = null;
		return !failed;
	}
	
	private boolean convertNestedACsLeft2Right(
			final OrdinaryMorphism rm,
			final NestedApplCond acL, 
			final OrdinaryMorphism acR, 
			final NestedApplCond ac,
			final Rule inverseRule) {
		
		boolean failed = false;
//		Vector<OrdinaryMorphism> racs = new Vector<OrdinaryMorphism>();
		
		for (int i=0; i<acL.getNestedACs().size() /*&& !failed*/; i++) {
			NestedApplCond ncL = acL.getNestedACs().get(i);
			Pair<OrdinaryMorphism,OrdinaryMorphism> 
			pairPO = convertNestedACLeft2Right(rm, ncL);
			if (pairPO != null) {
				OrdinaryMorphism ncR = pairPO.second;
				NestedApplCond nc = new NestedApplCond(ncR.getSource(), ncR.getTarget(), ncR.getAttrContext());
				nc.getDomainObjects().addAll(ncR.getDomainObjects());
				nc.getCodomainObjects().addAll(ncR.getCodomainObjects());	 
				this.unsetAllTransientAttrValues(nc);
				// set and adjust attr context
				nc.getTarget().setAttrContext(nc.getAttrContext());
				this.declareVariable(nc.getTarget(), (VarTuple)ncL.getAttrContext().getVariables());
				adjustAttributeValueAlongMorphismMapping(nc);				
				nc.setName(ncL.getName());	
				
//				racs.add(nc);
				ac.addNestedAC(nc);
				
				if (!ncL.getNestedACs().isEmpty()) {
					if (!convertNestedACsLeft2Right(pairPO.first, ncL, ncR, nc, inverseRule)) {
						//TODO check formula: replace ac by FALSE ???						
					}
				}
			} else {
				//TODO check formula: replace ac by FALSE ???	
				//
				String warning = inverseRule.getErrorMsg();
				String warning1 = "Nested AC: ".concat(acL.getName()).concat("  could not be converted");
				warning = warning.concat(warning1).concat(" ;  ");					
				inverseRule.setErrorMsg(warning);
				failed = true;
			}
		}
		if (!failed) {
//			for (int i=0; i<racs.size(); i++) {
//				ac.addNestedAC((NestedApplCond)racs.get(i));
//			}
			ac.setFormula(acL.getFormulaStr());
		} 
//		else {
//			for (int i=0; i<racs.size(); i++) {
////				((NestedApplCond)racs.get(i)).dispose();
//				ac.addNestedAC((NestedApplCond)racs.get(i));
//			}
//		}
//		racs.clear(); racs = null;
		return !failed;
	}
	
	public Pair<OrdinaryMorphism,OrdinaryMorphism>  convertNestedACLeft2Right(
			final OrdinaryMorphism rm,
			final NestedApplCond ncL) {

		OrdinaryMorphism ncR = null;
		OrdinaryMorphism extLeft = null;
		OrdinaryMorphism ncL1 = null;
		Pair<OrdinaryMorphism,OrdinaryMorphism> pairPO = null;
		
		boolean needHelp = !ncL.isTotal();
		if (needHelp) {
			ncL1 = rm.getSource().isomorphicCopy();
			if (ncL1 == null) {
				return null;
			}
			// extLeft: acL -> (L+acL)
			extLeft = extendTargetGraph1ByTargetGraph2(ncL1, ncL);
			if (extLeft == null) {
				ncL1.dispose();
				return null;
			}
			if (ncL1.isTotal()) {
				if (rm instanceof Rule) {
					if (this.isDanglingSatisfied(ncL1, rm)) 				
						pairPO = this.makePO(rm, ncL1, true, false);
//					else 
//						System.out.println("########## "+this.getClass().getName()+"   convert nested AC::  Dangling FAILED");										
				}
				else 
					pairPO = this.makePO(rm, ncL1, true, false);
			}
		} 
		else {
			if (rm instanceof Rule) {
				if (this.isDanglingSatisfied(ncL, rm)) 
					pairPO = this.makePO(rm, ncL, true, false);					
//				else 
//					System.out.println("########## "+this.getClass().getName()+"   convert nested AC::  Dangling FAILED");
			}
			else 
				pairPO = this.makePO(rm, ncL, true, false);
		}
		if (pairPO != null) {
			OrdinaryMorphism rStar = null;
			if (needHelp) {
				ncR = pairPO.second;
				rStar = extLeft.compose(pairPO.first);			
				if (rStar != null && filterObjectsOfRightCondition(rm, ncL, ncR)) {					
					return new Pair<OrdinaryMorphism,OrdinaryMorphism> (rStar, ncR);
				}
			} else {
				return pairPO;
			}
		}
		return null;
	}

	private boolean convertPACsLeft2Right(
			final Rule r, 
			final Rule inverseRule, 
			final Hashtable<GraphObject,GraphObject> isoRight) {
		
		final List<OrdinaryMorphism> acs = r.getPACsList();
		for(int i=0; i<acs.size(); i++) {
			OrdinaryMorphism acL = acs.get(i);
			OrdinaryMorphism acR = null;
			if (r.isACShiftPossible(acL)) {
				int tglevelcheck = r.getTypeSet().getLevelOfTypeGraphCheck();
				r.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);
				acR = convertACLeft2Right(r, acL);
				r.getTypeSet().setLevelOfTypeGraph(tglevelcheck);
				Collection<TypeError> error = r.getTypeSet().checkType(acR.getTarget());
				if (error != null && !error.isEmpty()) {
					acR.dispose(false, true);
					acR = null;
				}
			}
			boolean ok = false;
			if (acR != null) {
				OrdinaryMorphism ac = BaseFactory.theFactory().createMorphism(
										inverseRule.getLeft(),
										acR.getTarget());
				ok = ac.completeDiagram(isoRight, acR);
				if (ok) {
					if (!ac.isRightTotal()
							|| !ac.doesIgnoreAttrs()) {
						this.unsetAllTransientAttrValues(ac);
						// set and adjust attr context
						ac.setAttrContext(inverseRule.getLeft().getAttrContext());
						this.declareVariable(ac.getTarget(), (VarTuple)inverseRule.getAttrContext().getVariables());
						adjustAttributeValueAlongMorphismMapping(ac);
						ac.setEnabled(acL.isEnabled());
						ac.setName(acR.getName());
						inverseRule.addPAC(ac);
					}
				}
			} 
			if (!ok) {
				String warning = inverseRule.getErrorMsg();
				String warning1 = "AC: ".concat(acL.getName()).concat("  could not be converted");
				warning = warning.concat(warning1).concat(" ;  ");					
				inverseRule.setErrorMsg(warning);
				return false;
			}
		}
		return true;
	}
	
	public void adjustAttributeValueAlongMorphismMapping(
			final OrdinaryMorphism morph) {
		final Enumeration<GraphObject> dom = morph.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			if (obj.getAttribute() != null) {
				GraphObject img = morph.getImage(obj); 
				if (img.getAttribute() != null) {
					ValueTuple vt_obj = (ValueTuple) obj.getAttribute();
					ValueTuple vt_img = (ValueTuple) img.getAttribute();
					for (int i=0; i<vt_obj.getNumberOfEntries(); i++) {
						ValueMember vm_obj = vt_obj.getValueMemberAt(i);
						ValueMember vm_img = vt_img.getValueMemberAt(vm_obj.getName());
						if (vm_obj.isSet() 
								&& vm_img != null
								&& vm_img.isSet()
								&& !vm_img.getExprAsText().equals(vm_obj.getExprAsText())) {
							vm_img.setExprAsText(vm_obj.getExprAsText());
						}								
					}
				}
			}
		}
	}
	

	private OrdinaryMorphism convertACLeft2Right(
			final Rule r,
			final OrdinaryMorphism acL) {
		// isoLHS: L -> Lcopy
		final OrdinaryMorphism isoLHS = r.getLeft().isomorphicCopy();
		if (isoLHS == null) {
			return null;
		}
		// extLeft: acL -> (Lcopy+acL)
		final OrdinaryMorphism extLeft = extendTargetGraph1ByTargetGraph2(isoLHS, acL);
		if (extLeft == null) {
			isoLHS.dispose();
			return null;
		}
		boolean ok = false;
		OrdinaryMorphism acR = null;
		final Match m = (BaseFactory.theFactory()).createMatch(r, extLeft.getTarget(), true, "1");
		// extLeftGraph == m.getTarget() 
		m.getTarget().setCompleteGraph(false);
		m.setCompletionStrategy(new Completion_InjCSP(), true);				
		if (this.setMappingAlongMorphism(m, isoLHS)
				&& m.isTotal() 
				&& this.isDanglingSatisfied(m, r)) {
			try {
				int levelOfTypeGraphCheck = r.getTypeSet().getLevelOfTypeGraphCheck();
				r.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);			
				// apply rule to extend the graph (LHS+AC) by objects of the RHS			
				acR = (OrdinaryMorphism) TestStep.execute(m, true);
				// now the extended graph (RHS+AC) 
				r.getTypeSet().setLevelOfTypeGraph(levelOfTypeGraphCheck);
				// adjust usage of variables in the attributes of acR
				adjustVariablesInAttributeOfRightRuleCondition(r, acL, acR);						
				// prepare right AC graph :
				// delete objects to be created 
				// or without a mapping from its preimage into the acL
				ok = filterObjectsOfRightCondition(r, acL, acR);
				if (ok) {
					acR.setName(acL.getName());	
				} else {
					acR.dispose();
				}
			} catch (TypeException tex) {}
		}
		r.setMatch(null);
		extLeft.dispose();
		isoLHS.dispose();
		return acR;		
	}
	
	public boolean isDanglingSatisfied(final OrdinaryMorphism m, final OrdinaryMorphism rm) {
		final Iterator<Node> objects = rm.getSource().getNodesSet().iterator();
		while (objects.hasNext()) {
			final Node x = objects.next();
			if (rm.getImage(x) == null) {
				final Node y = (Node) m.getImage(x);
				if (y != null
						&& x.getNumberOfArcs() != y.getNumberOfArcs()) {	
					System.out.println("BF.isDanglingSatisfied::  "+rm.getName()+"  x: "+x+"  !=   y: "+y);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Given a complete morphism g: A->B with the plain morphism context of object mapping and <br> 
	 * an empty morphism f: A->B with the Match context of object mapping.<br> 
	 * Try to set mappings of f along g.
	 * @param f
	 * @param g
	 */
	private boolean setMappingAlongMorphism(
			final OrdinaryMorphism f,
			final OrdinaryMorphism g) {
		
		// set match mapping
		final Iterator<Node> nodes = g.getSource().getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node n = nodes.next();
			Node img = (Node) g.getImage(n);
			if (img != null) {
				try {
					if (n.getType().isParentOf(img.getType())) {
						f.addMapping(n, img);
					}
					else if (n.getType().isChildOf(img.getType())){
						f.addChild2ParentMapping(n, img);							
					}
				} catch (BadMappingException ex) {
					return false;
				}
			}
		}
		final Iterator<Arc> arcs = g.getSource().getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			Arc img = (Arc) g.getImage(a);
			if (img != null) {
				try {
					f.addMapping(a, img);
				} catch (BadMappingException ex) {
					return false;
				}
			}
		}
		return true;
	}

	
	private void adjustVariablesInAttributeOfRightRuleCondition(
			final Rule r,
			final OrdinaryMorphism condL,
			final OrdinaryMorphism condR) {

		// adjust usage of variables in the attributes of nacR
		final Enumeration<GraphObject> nacLCoDom = condL.getCodomain();
		while (nacLCoDom.hasMoreElements()) {
			GraphObject condObj = nacLCoDom.nextElement();
			if (condObj.getAttribute() != null) {					
				GraphObject lhsObj = condL.getInverseImage(condObj).nextElement();
				GraphObject rhsObj = r.getImage(lhsObj);
				if (rhsObj != null) {
					GraphObject condRObj = condR.getImage(rhsObj);						
					if (condRObj != null) {							
						ValueTuple lhsVal = (ValueTuple)lhsObj.getAttribute();
						ValueTuple condLVal = (ValueTuple)condObj.getAttribute();
						ValueTuple rhsVal = (ValueTuple)rhsObj.getAttribute();
						ValueTuple condRVal = (ValueTuple)condRObj.getAttribute();
						
						for (int i=0; i<lhsVal.getNumberOfEntries(); i++) {
							ValueMember vm = lhsVal.getEntryAt(i);
							if (vm.isSet()) {
								ValueMember vmcondL = condLVal.getEntryAt(vm.getName());
								ValueMember vmcondR = condRVal.getEntryAt(vm.getName());
								if (vmcondL.isSet()) {
									if (vm.getExprAsText().equals(vmcondL.getExprAsText())) {
										ValueMember vmRHS = rhsVal.getEntryAt(vm.getName());										
										if (vmRHS.isSet() && vmcondR != null) {
											vmcondR.setExprAsText(vmRHS.getExprAsText());
										}
									} 
								} else {
									if (vmcondR != null && vmcondR.isSet()) {
										vmcondR.setExpr(null);
									}
								}
							} 
						}
					}
				}
			}
		}

	}
	
	private boolean filterObjectsOfRightCondition(
			final OrdinaryMorphism r,
			final OrdinaryMorphism condL,
			final OrdinaryMorphism condR) {
		
		boolean ok = true;
		// delete arc to be created 
		// or without a mapping from its preimage into the condL
		final Iterator<Arc> rhsArcs = r.getTarget().getArcsSet().iterator();
		while (rhsArcs.hasNext() && ok) {
			Arc a = rhsArcs.next();
			Arc aImg = (Arc) condR.getImage(a);
			if (!r.getInverseImage(a).hasMoreElements()
					|| (condL.getImage(r.getInverseImage(a).nextElement()) == null)) {
				try {
					condR.getTarget().destroyArc(aImg, false, false);
				} catch (TypeException tex) {
					ok = false;
				}
			} 
		}
		// delete node to be created 
		// or without a mapping from its pre-image into the condL				
		final Iterator<Node> rhsNodes = r.getTarget().getNodesSet().iterator();
		while (rhsNodes.hasNext() && ok) {
			Node n = rhsNodes.next();
			Node nImg = (Node) condR.getImage(n);
			if (!r.getInverseImage(n).hasMoreElements()
					|| (condL.getImage(r.getInverseImage(n).nextElement()) == null)) {
				try {
					condR.getTarget().destroyNode(nImg, false, false);
				} catch (TypeException tex) {
					ok = false;
				}	
			}
		}			
		return ok;
	}
	
	protected boolean convertPACsLeft2Right (
			final Rule r, 
			final Rule inverseRule,
			final OrdinaryMorphism isoRHS) {
		
		final List<OrdinaryMorphism> pacs = r.getPACsList();
		for(int i=0; i<pacs.size(); i++) {			
			OrdinaryMorphism acL = pacs.get(i);		
			OrdinaryMorphism acR = null;
			if (r.isACShiftPossible(acL)) {
				int tglevelcheck = r.getTypeSet().getLevelOfTypeGraphCheck();
				r.getTypeSet().setLevelOfTypeGraph(TypeSet.ENABLED);
				acR = convertACLeft2Right(r, acL);
				r.getTypeSet().setLevelOfTypeGraph(tglevelcheck);
				Collection<TypeError> error = r.getTypeSet().checkType(acR.getTarget());
				if (error != null && !error.isEmpty()) {
					acR.dispose(false, true);
					acR = null;
				}
			}
			boolean ok = false;
			if (acR != null) {
				OrdinaryMorphism ac = BaseFactory.theFactory().createMorphism(
						inverseRule.getLeft(),
						acR.getTarget());	
				ok = acR.completeDiagram(isoRHS, ac);
				if (ok) {
					if (!ac.isRightTotal()
							|| !ac.doesIgnoreAttrs()) {
						this.unsetAllTransientAttrValues(ac);
						// set attr context
						ac.setAttrContext(inverseRule.getLeft().getAttrContext());
						this.declareVariable(ac.getTarget(), (VarTuple)inverseRule.getAttrContext().getVariables());
						adjustAttributeValueAlongMorphismMapping(ac);
						ac.setEnabled(acL.isEnabled());
						ac.setName(acR.getName());
						inverseRule.addPAC(ac);
					}
				}
			}
			if (!ok) {
				String warning = inverseRule.getErrorMsg();
				String warning1 = "PAC: ".concat(acL.getName()).concat("  could not be converted");
				warning = warning.concat(warning1).concat(" ;  ");				
				inverseRule.setErrorMsg(warning);
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Copies the value (which is not null) of an attribute of the given GraphObject <code>from</code>
	 * to the value of the corresponding attribute of the given GraphObject <code>to</code>.
	 * 
	 * @param from
	 * @param to
	 */
	private void adjustAttributesFromTo(
			final GraphObject from,
			final GraphObject to) {
		
		if (to != null
				&& from.getAttribute() != null 
				&& to.getAttribute() != null) {
			ValueTuple vt_from = (ValueTuple) from.getAttribute();
			ValueTuple vt_to = (ValueTuple) to.getAttribute();
			for (int i=0; i<vt_from.getNumberOfEntries(); i++) {
				ValueMember vm = vt_from.getValueMemberAt(i);
				ValueMember vm_to = vt_to.getValueMemberAt(vm.getName());
				if (vm.isSet() 
						&& vm_to != null) {
					vm_to.setExprAsText(vm.getExprAsText());
					vm_to.setTransient(vm.isTransient());
//				System.out.println("BF.adjustAttributesFromTo::   "+vm_to);
				}								
			}						
		}
	}
	
	private void setEmptyAttrsByDummy(final GraphObject go) {		
		if (go != null && go.getAttribute() != null) {
			ValueTuple vt = (ValueTuple) go.getAttribute();
			for (int i=0; i<vt.getNumberOfEntries(); i++) {
				ValueMember vm = vt.getValueMemberAt(i);
				if (!vm.isSet()) {
					vm.setExprAsText(String.valueOf(vm.hashCode()));
					vm.setTransient(true);
				}								
			}						
		}
	}
	
	public Pair<OrdinaryMorphism, OrdinaryMorphism> extendLeftGraphByNAC(
			final Rule r,
			final OrdinaryMorphism nac) {
		OrdinaryMorphism L2L_NAC = r.getLeft().isomorphicCopy();
		if (L2L_NAC == null) {
			return null;
		}
		OrdinaryMorphism NAC2L_NAC = extendTargetGraph1ByTargetGraph2(L2L_NAC, nac);
		return new Pair<OrdinaryMorphism, OrdinaryMorphism>(L2L_NAC, NAC2L_NAC);
	}
	
	/**
	 * Creates a new morphism from the target graph of the given morphism morph2 
	 * to the target graph of the given morphism morph1.
	 * Extends the target graph of the given morphism morph1 by the objects
	 * of the given morphism morph2 by respecting existing object mappings.
	 * 
	 * @param morph1
	 * @param morph2
	 * @return  	a new morphism
	 */
	public OrdinaryMorphism extendTargetGraph1ByTargetGraph2(
			final OrdinaryMorphism morph1,
			final OrdinaryMorphism morph2) {
		
		if (morph1 == null || morph2 == null)
			return null;
		
		Graph extLeft = morph1.getTarget();
		OrdinaryMorphism morph = BaseFactory.theFactory().createMorphism(
				morph2.getTarget(), extLeft);
		((ContextView) morph.getAttrContext()).changeAllowedMapping(AttrMapping.MATCH_MAP);
		
		Hashtable<Node, Node> tmp = new Hashtable<Node, Node>(5);
		Iterator<?> e = morph2.getTarget().getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			if (!morph2.getInverseImage(o).hasMoreElements()) {
				try {
					Node n = extLeft.copyNode((Node) o);
					this.setEmptyAttrsByDummy(n);
					n.setContextUsage(o.hashCode());
					tmp.put((Node) o, n);
					try {
						morph.addPlainMapping(o, n);
					} catch (BadMappingException exc) {
						System.out.println("BF.extendTargetGraph1ByTargetGraph2:  copyNode: "+exc.getLocalizedMessage());
					}
				} catch (TypeException ex) {
					System.out.println(ex.getLocalizedMessage());
				}
				
			} else if (morph1.getImage(morph2.getInverseImage(o).nextElement()) != null) {
				Node n = (Node) morph1.getImage(morph2
						.getInverseImage(o).nextElement());
				n.setContextUsage(o.hashCode());
				n.setObjectName(o.getObjectName());
				this.adjustAttributesFromTo(o, n);
				this.setEmptyAttrsByDummy(n);
				try {
					if (o.getType().isParentOf(n.getType()))
						morph.addMapping(o, n);					
					else if (o.getType().isChildOf(n.getType()))
						morph.addChild2ParentMapping(o, n);
				} catch (BadMappingException exc) {
					System.out.println("BF.extendTargetGraph1ByTargetGraph2:  "+exc.getLocalizedMessage());
				}
			}
		}
		
		e = morph2.getTarget().getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			if (!morph2.getInverseImage(o).hasMoreElements()) {
				Node src = tmp.get(((Arc) o).getSource());
				if (src == null) {
					src = (Node) morph1.getImage(morph2.getInverseImage(
							((Arc) o).getSource()).nextElement());
				}
				Node tar = tmp.get(((Arc) o).getTarget());
				if (tar == null) {
					tar = (Node) morph1.getImage(morph2.getInverseImage(
							((Arc) o).getTarget()).nextElement());
				}
				try {
					Arc a = extLeft.copyArc((Arc) o, src, tar);
					this.setEmptyAttrsByDummy(a);
					a.setContextUsage(o.hashCode());
					try {
						morph.addPlainMapping(o, a);
					} catch (BadMappingException exc) {
						System.out.println("BF.extendTargetGraph1ByTargetGraph2:  copyArc: "+exc.getLocalizedMessage());
					}
				} catch (TypeException ex) {}
			} else if (morph1.getImage(morph2.getInverseImage(o).nextElement()) != null) {
				Arc a = (Arc) morph1.getImage(morph2
						.getInverseImage(o).nextElement());
				a.setContextUsage(o.hashCode());
				a.setObjectName(o.getObjectName());
				this.adjustAttributesFromTo(o, a);
				this.setEmptyAttrsByDummy(a);
				try {
					morph.addMapping(o, morph1.getImage( morph2
							.getInverseImage(o).nextElement()));
				} catch (BadMappingException exc) {
					System.out.println("BF.extendTargetGraph1ByTargetGraph2: "+exc.getLocalizedMessage());
				}
			}
		}
		// replace parent node by a copy of child node when needed
		this.replaceParentByChild(morph, morph1);		
		return morph;
	}

	public void unsetTransientAttrValue(
			final VarTuple vars,
			final Iterator<?> elems) {
		
		while (elems.hasNext()) {
			GraphObject obj = (GraphObject) elems.next();
			if (obj.getAttribute() == null)
				continue;
			ValueTuple value = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < value.getNumberOfEntries(); i++) {
				ValueMember valuem = value.getValueMemberAt(i);
				if ((valuem.getExpr() != null && valuem.isTransient())) {
					if (valuem.getExpr().isVariable()) {
						String tmpname = valuem.getExprAsText();
						if (vars.getMemberAt(tmpname) != null)
							vars.getTupleType().deleteMemberAt(tmpname);
					}
					valuem.setExpr(null);
					valuem.setTransient(false);
				}
			}
		}
	}
	
	
	public void unsetAllTransientAttrValues(final OrdinaryMorphism morph) {
		final VarTuple vars = (VarTuple)morph.getAttrContext().getVariables();
		
		if (morph.getSource().isAttributed()) {
			
			Iterator<?> elems = morph.getSource().getNodesSet().iterator();
			unsetTransientAttrValue(vars, elems); 
			
			elems = morph.getSource().getArcsSet().iterator();
			unsetTransientAttrValue(vars, elems);
		}
		
		if (morph.getTarget().isAttributed()) {
			
			Iterator<?> elems1 = morph.getTarget().getNodesSet().iterator();
			unsetTransientAttrValue(vars, elems1);
			
			elems1 = morph.getTarget().getArcsSet().iterator();
			unsetTransientAttrValue(vars, elems1);
		}
	}
	
	
	public void unsetAllTransientAttrValuesOfRule(final Rule r) {
		final VarTuple vars = (VarTuple)r.getAttrContext().getVariables();
		
		// LHS and RHS
		this.unsetAllTransientAttrValues(r);
		// NACs
		final List<OrdinaryMorphism> nacs = r.getNACsList();
		for(int j=0; j<nacs.size(); j++) {
			OrdinaryMorphism nac = nacs.get(j);	
			if (nac.getTarget().isAttributed()) {
				Iterator<?> e1 = nac.getTarget().getNodesSet().iterator();
				this.unsetTransientAttrValue(vars, e1);
				
				e1 = nac.getTarget().getArcsSet().iterator();
				this.unsetTransientAttrValue(vars, e1);
			}
		}
		// PACs
		final List<OrdinaryMorphism> pacs = r.getPACsList();
		for(int j=0; j<pacs.size(); j++) {
			OrdinaryMorphism pac = pacs.get(j);		
			if (pac.getTarget().isAttributed()) {
				Iterator<?> e1 = pac.getTarget().getNodesSet().iterator();
				this.unsetTransientAttrValue(vars, e1);
				
				e1 = pac.getTarget().getArcsSet().iterator();
				this.unsetTransientAttrValue(vars, e1);
			}
		}
	}

	/** Returns a clone of the graph. */
	public Graph cloneGraph(Graph graph) {
		return graph.copy();
	}

	/** Returns a clone of the rule. */
	private KernelRule copyKernelRule(
			final Rule rule,
			final TypeSet types,
			final Hashtable<GraphObject, GraphObject> table) {
		
		KernelRule kr = new KernelRule(types);
		copyRule(rule, kr, table, true);
		kr.setName(rule.getName());
		return kr;
	}

	/** Returns a clone of the rule scheme. */
	public RuleScheme cloneRuleScheme(RuleScheme ruleScheme) {
		return this.cloneRuleScheme(ruleScheme, ruleScheme.getKernelRule().getTypeSet());
	}
	
	/** Returns a clone of the rule scheme. */
	public RuleScheme cloneRuleScheme(RuleScheme ruleScheme, TypeSet types) {
		final Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
		RuleScheme rs = new RuleScheme(ruleScheme.getName(), 
						copyKernelRule(ruleScheme.getKernelRule(), types, table));						
		for (int i=0; i<ruleScheme.getMultiRules().size(); i++) {
			MultiRule mr = (MultiRule) ruleScheme.getMultiRules().get(i);			
			MultiRule mrule = rs.createMultiRule(mr.getName());
			copyMultiRule(mr, mrule, table);
		}
		rs.setLayer(ruleScheme.getLayer());
		rs.setPriority(ruleScheme.getPriority());
		rs.setParallelKernelMatch(ruleScheme.parallelKernelMatch());
		rs.setCheckDeleteUseConflictRequired(ruleScheme.checkDeleteUseConflictRequired());
		rs.setDisjointMultiMatches(ruleScheme.disjointMultiMatches());
		rs.setAtLeastOneMultiMatchRequired(ruleScheme.atLeastOneMultiMatchRequired());
		table.clear();
		return rs;
	}

	/**
	 * Creates a rule scheme with a kernel rule as a copy of the specified rule 
	 * and an empty list of multi rules.
	 * @return	RuleScheme  or null if creation failed
	 */
	public RuleScheme makeRuleScheme(final Rule r) {
		KernelRule kr = new KernelRule(r.getTypeSet());
		if (this.cloneRule(r, kr)) {
			kr.setName(r.getName());
			final RuleScheme rs = new RuleScheme(kr.getName()+"_RS", kr);
			rs.setLayer(r.getLayer());
			rs.setPriority(r.getPriority());
			return rs;
		} 
		else {
			kr.dispose(true, true);
			return null;
		}
	}
	
	private KernelRule reverseKernelRule(
			final KernelRule kern,
			final Hashtable<GraphObject, GraphObject> table) {
		KernelRule invKern = new KernelRule(kern.getTypeSet());
		this.reverseMorphismInto(kern, invKern, table);
		this.reflectInputParameter(kern, invKern);
					
		// converting PACs from LHS to RHS
		if (this.convertPACsLeft2Right(kern, invKern, table)) {	
		
			// converting NACs from LHS to RHS
			this.convertNACsLeft2Right(kern, invKern, table);	
			
			// converting GACs from LHS to RHS
			this.convertGACsLeft2Right(kern, invKern, table);
			
			// convert attr conditions
			this.convertAttrConditionLeft2Right(kern, invKern);				
			invKern.setName(kern.getName());
			return invKern;
		}
		
		invKern = null;
		return null;
	}
	
	/** Returns an inverse rule scheme of the specified rule scheme. */
	public RuleScheme reverseRuleScheme(RuleScheme ruleScheme) {
		Hashtable<GraphObject, GraphObject> kernTable = new Hashtable<GraphObject, GraphObject>();
		Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();	
		// reverte the kernel rule
		KernelRule invKern = this.reverseKernelRule((KernelRule)ruleScheme.getKernelRule(), kernTable);		
		// new rule scheme	
		if (invKern != null) {
			RuleScheme rs = new RuleScheme(ruleScheme.getName().concat("_INV"), invKern);	
			if (!"".equals(invKern.getErrorMsg())) {
				rs.setErrorMsg(invKern.getErrorMsg());
			}
			if (!invKern.isReadyToTransform()) {
				System.out.println(this.getClass().getName()+"    "+invKern.getName()+"  ::  "+invKern.getErrorMsg());
			}
			
			for (int i=0; i<ruleScheme.getMultiRules().size(); i++) {
				table.putAll(kernTable);
				MultiRule multi = (MultiRule) ruleScheme.getMultiRules().get(i);			
				MultiRule invMulti = rs.createMultiRule(multi.getName());			
				if (reverseMultiRule(multi, invMulti, table)) {
					invMulti.setName(multi.getName());
					if (!"".equals(invMulti.getErrorMsg())) {					
						rs.setErrorMsg(rs.getErrorMsg().concat(" ;  ").concat(invMulti.getErrorMsg()));
					}
					if (!invMulti.isReadyToTransform()) {
						System.out.println(this.getClass().getName()+"    "+invMulti.getName()+"  ::  "+invMulti.getErrorMsg());
					}
				} else {
					ruleScheme.getMultiRules().remove(i);
					i--;
					invMulti = null;
					//TODO  was ist in diesem Fall mit inverse RuleScheme??
				}
				table.clear();
			}
			rs.setLayer(ruleScheme.getLayer());
			rs.setPriority(ruleScheme.getPriority());
			rs.setParallelKernelMatch(ruleScheme.parallelKernelMatch());
			rs.setCheckDeleteUseConflictRequired(ruleScheme.checkDeleteUseConflictRequired());
			rs.setDisjointMultiMatches(ruleScheme.disjointMultiMatches());
			rs.setAtLeastOneMultiMatchRequired(ruleScheme.atLeastOneMultiMatchRequired());
			kernTable.clear();
			kernTable = null;
			table = null;
			return rs;
		}
		kernTable.clear();
		kernTable = null;
		table.clear();
		table = null;
		return null;
	}
	
	/**
	 * Makes the minimal rule from the given rule.
	 * A minimal rule comprises the effects of a given rule in a minimal context.
	 */
	public Rule makeMinimalOfRule(Rule r) {
		Rule minRule = new Rule(r.getOriginal().getTypeSet());
		final Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
		copyRule(r, minRule, table, false);
		minRule.setLayer(r.getLayer());
		minRule.setPriority(r.getPriority());
		table.clear();
		// remove preserved unchanged objects from LHS and RHS
		removePreservedUnchangedObjs(minRule);
		return minRule;
		
	}
	
	/**
	 * Returns true if at least one object ia removed.
	 */
	public boolean removePreservedUnchangedObjs(final Rule r) {
		boolean removed = false;
		Vector<GraphObject> del = new Vector<GraphObject>();
		// remove preserved unchanged objects from LHS and RHS
		Enumeration<GraphObject> dom = r.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			GraphObject img = r.getImage(obj);
			if (obj.isArc() 
					&& ((obj.getAttribute() == null  && img.getAttribute() == null)
							|| (obj.getAttribute() != null  
									&& img.getAttribute() != null 
									&& obj.getAttribute().compareTo(img.getAttribute())))) {
				del.add(obj);
				del.add(img);
			}
		}
		for (int i=0; i<del.size()-1; i=i+2) {
			GraphObject obj = del.get(i);
			GraphObject img = del.get(i+1);
			if (obj.isArc()) {
				r.removeMapping((Arc)obj, (Arc)img);
				try {
					r.getLeft().destroyArc((Arc)obj);
					r.getRight().destroyArc((Arc)img);
					removed = true;
				} catch (TypeException ex) {}
			}
		}
		del.clear();
		dom = r.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			GraphObject img = r.getImage(obj);
			if (obj.isNode() 
					&& ((Node)obj).getNumberOfArcs() == 0
					&& ((Node)img).getNumberOfArcs() == 0
					&& ((obj.getAttribute() == null  && img.getAttribute() == null)
							|| (obj.getAttribute() != null  
									&& img.getAttribute() != null 
									&& obj.getAttribute().compareTo(img.getAttribute())))) {
				del.add(obj);
				del.add(img);
			}
		}
		for (int i=0; i<del.size()-1; i=i+2) {
			GraphObject obj = del.get(i);
			GraphObject img = del.get(i+1);
			if (obj.isNode()) {
				r.removeMapping((Node)obj, (Node)img);
				try {
				r.getLeft().destroyNode((Node)obj);
				r.getRight().destroyNode((Node)img);
				removed = true;
				} catch (TypeException ex) {}
			}
		}
		return removed;
	}
	
	/** Returns a clone of the rule. */
	public Rule cloneRule(final Rule rule) {
		Rule ruleClone = new Rule(rule.getOriginal().getTypeSet());
		final Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
		copyRule(rule, ruleClone, table, true);
		ruleClone.setLayer(rule.getLayer());
		ruleClone.setPriority(rule.getPriority());
		table.clear();
		return ruleClone;
	}
	
	/** Returns a clone of the rule. */
	public boolean cloneRule(final Rule from, final Rule to) {
		final Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
		copyRule(from, to, table, true);
		table.clear();
		return true;
	}
	
	/** Returns a clone of the given rule using specified types. */
	public Rule cloneRule(Rule rule, TypeSet types, boolean withApplConds) {
		Rule ruleClone = new Rule(types);
		final Hashtable<GraphObject, GraphObject> ltable = new Hashtable<GraphObject, GraphObject>();
		final Hashtable<GraphObject, GraphObject> rtable = new Hashtable<GraphObject, GraphObject>();
		final Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
		copyRule(rule, ruleClone, table, withApplConds);
		ruleClone.setLayer(rule.getLayer());
		ruleClone.setPriority(rule.getPriority());
		ltable.clear();
		rtable.clear();
		return ruleClone;
	}
	
	/**
	 *  Copies the given rule into the given ruleClone.
	 *  The TypeSet of the given rules must be the same set.
	 */
	private Rule copyRule(
			final Rule rule, 
			final Rule ruleClone,
			final Hashtable<GraphObject, GraphObject> table,
			boolean withApplConds) {
		
		ruleClone.setName(rule.getName());
		
		copyAttrContextFromTo(rule.getAttrContext(), ruleClone.getAttrContext());

//		Graph lgraph = rule.getLeft();
//		Graph rgraph = rule.getRight();
//		Graph left = ruleClone.getLeft();
//		Graph right = ruleClone.getRight();
		
		// copy LHS and RHS
		this.copyGraph(rule.getLeft(), ruleClone.getLeft(), table);
		this.copyGraph(rule.getRight(), ruleClone.getRight(), table);
		// copy rule morphism
		this.copyMorph(rule, ruleClone, table);
		
		if (withApplConds) {
			// copy GAGs		
			for(int i=0; i<rule.getNestedACsList().size(); i++) {
				NestedApplCond ac = (NestedApplCond) rule.getNestedACsList().get(i);		
				NestedApplCond acClone = ruleClone.createNestedAC();
				acClone.getImage().setName(ac.getImage().getName());
				acClone.setName(ac.getName());
				
				copyGraph(ac.getImage(), acClone.getImage(), table);
				copyMorph(ac, acClone, table);
				
				copyNestedAC(ac, acClone, table);
			}
			ruleClone.setFormula(rule.getFormulaStr());
			
			// copy NACs
			for(int i=0; i<rule.getNACsList().size(); i++) {
				OrdinaryMorphism ac = rule.getNACsList().get(i);
				OrdinaryMorphism acClone = ruleClone.createNAC();
				acClone.getImage().setName(ac.getImage().getName());
				acClone.setName(ac.getName());
				
				copyGraph(ac.getImage(), acClone.getImage(), table);
				copyMorph(ac, acClone, table);
			}
			// copy PACs
			for(int i=0; i<rule.getPACsList().size(); i++) {
				OrdinaryMorphism ac = rule.getPACsList().get(i);		
				OrdinaryMorphism acClone = ruleClone.createPAC();
				acClone.getImage().setName(ac.getImage().getName());
				acClone.setName(ac.getName());
				
				copyGraph(ac.getImage(), acClone.getImage(), table);
				copyMorph(ac, acClone, table);
			}
		}
		return ruleClone;
	}
	
	public void copyGraph(Graph from, Graph to,
			Hashtable<GraphObject, GraphObject> table) {
		// copy nodes
		Iterator<Node> nodes = from.getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node bn1 = nodes.next();
			Node bn2 = null;
			try {
				bn2 = to.copyNode(bn1);
				bn2.setContextUsage(bn1.getContextUsage());
				table.put(bn1, bn2);
			} catch (TypeException e) {
				e.printStackTrace();
			}
		}
		// copy arcs
		Iterator<Arc> arcs = from.getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc ba1 = arcs.next();
			Node src = (Node) table.get(ba1.getSource());
			Node tar = (Node) table.get(ba1.getTarget());
			Arc ba2 = null;
			try {
				ba2 = to.copyArc(ba1, src, tar);
				ba2.setContextUsage(ba1.getContextUsage());
				table.put(ba1, ba2);
				// add a new EdArc
			} catch (TypeException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void copyMorph(OrdinaryMorphism from, OrdinaryMorphism to,
			Hashtable<GraphObject, GraphObject> table) {
		
		Iterator<GraphObject> dom = from.getDomainObjects().iterator();
		while (dom.hasNext()) {
			GraphObject lgo = dom.next();
			GraphObject rgo = from.getImage(lgo);
			GraphObject lgo1 = table.get(lgo);
			GraphObject rgo1 = table.get(rgo);
			try {
				to.addMapping(lgo1, rgo1);
			} catch (agg.xt_basis.BadMappingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public AtomConstraint cloneAtomConstraint(
			final AtomConstraint ac,
			final TypeSet types) {
		
		final Hashtable<GraphObject, GraphObject> commonTable = new Hashtable<GraphObject, GraphObject>();

		Graph g1 = BaseFactory.theFactory().createGraph(types);
		g1.setKind(GraphKind.PREMISE);
		Graph g2 = BaseFactory.theFactory().createGraph(types);
		g2.setKind(GraphKind.CONCLUSION);
		AtomConstraint atom = new AtomConstraint(g1, g2, agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newContext(AttrMapping.PLAIN_MAP), ac.getAtomicName());
		copyAttrContextFromTo(ac.getConclusion(0).getAttrContext(), 
								atom.getConclusion(0).getAttrContext());
		copyGraph(ac.getSource(), g1, commonTable);
		copyGraph(ac.getConclusion(0).getTarget(), g2, commonTable);
		copyMorph(ac.getConclusion(0), atom.getConclusion(0), commonTable);
		atom.getConclusion(0).setName(ac.getConclusion(0).getName());
		for (int i=1; i<ac.getConclusionsSize(); i++) {
			AtomConstraint from = ac.getConclusion(i);
			Graph g = BaseFactory.theFactory().createGraph(types);
			g.setKind(GraphKind.CONCLUSION);
			AtomConstraint to = atom.createNextConclusion(g);
			to.setName(from.getName());
			copyAttrContextFromTo(from.getAttrContext(), to.getAttrContext());
			copyGraph(from.getTarget(), g, commonTable);
			copyMorph(from, to, commonTable);
		}
		return atom;
	}
	
	public void copyNestedAC(
			final NestedApplCond from, 
			final NestedApplCond to, 
			final Hashtable<GraphObject, GraphObject> table) {
		
		for(int i=0; i<from.getNestedACs().size(); i++) {
			NestedApplCond ac = from.getNestedACs().get(i);		
			NestedApplCond acClone = to.createNestedAC();
			acClone.getImage().setName(ac.getImage().getName());
			acClone.setName(ac.getName());	
			
			copyGraph(ac.getImage(), acClone.getImage(), table);
			copyMorph(ac, acClone, table);
			
			copyNestedAC(ac, acClone, table);
		}
		to.setFormula(from.getFormulaStr());
	}
	
	/**
	 *  Copies the given rule into the given ruleClone.
	 *  The TypeSet of the given rules must be the same set.
	 *  The kernel part of the given ruleClone is already contained.
	 */
	private MultiRule copyMultiRule(
			final MultiRule rule, 
			final MultiRule ruleClone, 
			final Hashtable<GraphObject, GraphObject> table) {
		
		ruleClone.setName(rule.getName());
		
		copyAttrContextFromTo(rule.getAttrContext(), ruleClone.getAttrContext());

		Graph lgraph = rule.getLeft();
		Graph rgraph = rule.getRight();
		Graph left = ruleClone.getLeft();
		Graph right = ruleClone.getRight();
		// copy RHS nodes
		Iterator<Node> rnodes = rgraph.getNodesSet().iterator();
		while (rnodes.hasNext()) {
			Node rNode = rnodes.next();
			Node itsRNode = null;
			if (!rule.isTargetOfEmbeddingRight(rNode)) {
				try {
					itsRNode = right.copyNode(rNode);
					table.put(rNode, itsRNode);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
			} else {
				Node n = (Node)table.get(rule.getEmbeddingRight().getInverseImage(rNode).nextElement());
				itsRNode = (Node)ruleClone.getEmbeddingRight().getImage(n);
				table.put(rNode, itsRNode);
			}
		}
		// copy LHS nodes
		Iterator<Node> lnodes = lgraph.getNodesSet().iterator();
		while (lnodes.hasNext()) {
			Node lNode = lnodes.next();
			Node itsLNode = null;
			if (!rule.isTargetOfEmbeddingLeft(lNode)) {
				try {
					itsLNode = left.copyNode(lNode);
					table.put(lNode, itsLNode);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
				GraphObject rn = rule.getImage(lNode);
				// set mapping
				if (rn != null) {
					try {
						ruleClone.addMapping(itsLNode, table.get(rn));
					} catch (BadMappingException ex) {
						System.out.println(ex.getMessage());
					}
				}
			} else {
				Node n = (Node)table.get(rule.getEmbeddingLeft().getInverseImage(lNode).nextElement());
				itsLNode = (Node)ruleClone.getEmbeddingLeft().getImage(n);
				table.put(lNode, itsLNode);
			}
		}
		// copy RHS arcs
		Iterator<Arc> rarcs = rgraph.getArcsSet().iterator();
		while (rarcs.hasNext()) {
			Arc rArc = rarcs.next();
			Arc itsRArc = null;
			if (!rule.isTargetOfEmbeddingRight(rArc)) {
				Node itsRSource = (Node) table.get(rArc.getSource());
				Node itsRTarget = (Node) table.get(rArc.getTarget());
				try {
					itsRArc = right.copyArc(rArc, itsRSource, itsRTarget);
					table.put(rArc, itsRArc);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
			} else {
				Arc a = (Arc)table.get(rule.getEmbeddingRight().getInverseImage(rArc).nextElement());
				itsRArc = (Arc)ruleClone.getEmbeddingRight().getImage(a);
				table.put(rArc, itsRArc);
			}
		}
		// copy LHS arcs
		Iterator<Arc> larcs = lgraph.getArcsSet().iterator();
		while (larcs.hasNext()) {
			Arc lArc = larcs.next();
			Arc itsLArc = null;
			if (!rule.isTargetOfEmbeddingLeft(lArc)) {
				Node itsLSource = (Node) table.get(lArc.getSource());
				Node itsLTarget = (Node) table.get(lArc.getTarget());
				try {
					itsLArc = left.copyArc(lArc, itsLSource, itsLTarget);
					table.put(lArc, itsLArc);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
				GraphObject ra = rule.getImage(lArc);
				// set mapping
				if (ra != null) {
					try {
						ruleClone.addMapping(itsLArc, table.get(ra));
					} catch (BadMappingException ex) {
						System.out.println(ex.getMessage());
					}
				}
			} else {
				Arc a = (Arc)table.get(rule.getEmbeddingLeft().getInverseImage(lArc).nextElement());
				itsLArc = (Arc)ruleClone.getEmbeddingLeft().getImage(a);
				table.put(lArc, itsLArc);
			}
		}
		// copy GAGs
		for(int i=0; i<rule.getNestedACsList().size(); i++) {
			NestedApplCond ac = (NestedApplCond) rule.getNestedACsList().get(i);		
			NestedApplCond acClone = ruleClone.createNestedAC();
			acClone.getImage().setName(ac.getImage().getName());
			acClone.setName(ac.getName());
			
			copyGraph(ac.getImage(), acClone.getImage(), table);
			copyMorph(ac, acClone, table);
			
			copyNestedAC(ac, acClone, table);
		}
		ruleClone.setFormula(rule.getFormulaStr());
		// copy NACs
		final List<OrdinaryMorphism> nacs = rule.getNACsList();
		for(int i=0; i<nacs.size(); i++) {
			OrdinaryMorphism ac = nacs.get(i);
			OrdinaryMorphism acClone = ruleClone.createNAC();
			acClone.getImage().setName(ac.getImage().getName());
			acClone.setName(ac.getName());
			copyGraph(ac.getImage(), acClone.getImage(), table);
			copyMorph(ac, acClone, table);
		}
		// copy PACs
		final List<OrdinaryMorphism> pacs = rule.getPACsList();
		for(int i=0; i<pacs.size(); i++) {
			OrdinaryMorphism ac = pacs.get(i);		
			OrdinaryMorphism acClone = ruleClone.createPAC();
			acClone.getImage().setName(ac.getImage().getName());
			acClone.setName(ac.getName());
			copyGraph(ac.getImage(), acClone.getImage(), table);
			copyMorph(ac, acClone, table);
		}
		return ruleClone;
	}
	
	/**
	 *  Copies the given rule into the given ruleClone.
	 *  The TypeSet of the given rules must be the same set.
	 *  The kernel part of the given ruleClone is already contained.
	 */
	private boolean reverseMultiRule(
			final MultiRule rule, 
			final MultiRule invRule, 
			final Hashtable<GraphObject, GraphObject> table) {
				
		copyAttrContextFromTo(rule.getAttrContext(), invRule.getAttrContext());

		Graph left = rule.getLeft();
		Graph right = rule.getRight();
		Graph invLeft = invRule.getLeft();
		Graph invRight = invRule.getRight();
		// copy LHS nodes to RHS nodes
		Iterator<Node> lnodes = left.getNodesSet().iterator();
		while (lnodes.hasNext()) {
			Node lNode = lnodes.next();
			Node invRNode = null;
			if (!rule.isTargetOfEmbeddingLeft(lNode)) {
				try {
					invRNode = invRight.copyNode(lNode);
					table.put(lNode, invRNode);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
			} else {
				Node n = (Node)table.get(rule.getEmbeddingLeft().getInverseImage(lNode).nextElement());
				invRNode = (Node)invRule.getEmbeddingRight().getImage(n);
				table.put(lNode, invRNode);
				
			}
		}
		// copy RHS nodes to LHS nodes
		Iterator<Node> rnodes = right.getNodesSet().iterator();
		while (rnodes.hasNext()) {
			Node rNode = rnodes.next();
			Node invLNode = null;
			if (!rule.isTargetOfEmbeddingRight(rNode)) {
				try {
					invLNode = invLeft.copyNode(rNode);
					table.put(rNode, invLNode);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
				Enumeration<GraphObject> en = rule.getInverseImage(rNode);
				if (en.hasMoreElements()) {
					GraphObject ln = en.nextElement();
					// set mapping
					try {
						invRule.addMapping(invLNode, table.get(ln));
					} catch (BadMappingException ex) {
						System.out.println(ex.getMessage());
					}
				}
			} else {
				Node n = (Node)table.get(rule.getEmbeddingRight().getInverseImage(rNode).nextElement());
				invLNode = (Node)invRule.getEmbeddingLeft().getImage(n);
				 table.put(rNode, invLNode);
			}
		}

		// copy LHS arcs to RHS arcs
		Iterator<Arc> larcs = left.getArcsSet().iterator();
		while (larcs.hasNext()) {
			Arc lArc = larcs.next();
			Arc invRArc = null;
			if (!rule.isTargetOfEmbeddingLeft(lArc)) {
				Node itsRSource = (Node) table.get(lArc.getSource());
				Node itsRTarget = (Node) table.get(lArc.getTarget());
				try {					
					if (itsRSource != null && itsRTarget != null) {
						invRArc = invRight.copyArc(lArc, itsRSource, itsRTarget);
						table.put(lArc, invRArc);
					}
				} catch (TypeException e) {
					System.out.println(e.getLocalizedMessage());
				}
			} else {
				Arc a = (Arc)table.get(rule.getEmbeddingLeft().getInverseImage(lArc).nextElement());
				invRArc = (Arc)invRule.getEmbeddingRight().getImage(a);
				table.put(lArc, invRArc);
			}
		}
		// copy RHS arcs to LHS arcs
		Iterator<Arc> rarcs = right.getArcsSet().iterator();
		while (rarcs.hasNext()) {
			Arc rArc = rarcs.next();
			Arc invLArc = null;
			if (!rule.isTargetOfEmbeddingRight(rArc)) {
				Node itsLSource = (Node) table.get(rArc.getSource());
				Node itsLTarget = (Node) table.get(rArc.getTarget());
				try {
					invLArc = invLeft.copyArc(rArc, itsLSource, itsLTarget);
					table.put(rArc, invLArc);
				} catch (TypeException e) {
					e.getLocalizedMessage();
				}
				Enumeration<GraphObject> en = rule.getInverseImage(rArc);
				if (en.hasMoreElements()) {
					GraphObject la = en.nextElement();
					// set mapping
					try {
						invRule.addMapping(invLArc, table.get(la));
					} catch (BadMappingException ex) {
						System.out.println(ex.getMessage());
					}
				}
			} else {
				Arc a = (Arc)table.get(rule.getEmbeddingRight().getInverseImage(rArc).nextElement());
				invLArc = (Arc)invRule.getEmbeddingLeft().getImage(a);
				table.put(rArc, invLArc);
			}
		}
		boolean ok = true;
		// converting PACs from LHS to RHS
		if (this.convertPACsLeft2Right(rule, invRule, table)) {	
			
			// converting NACs from LHS to RHS
			this.convertNACsLeft2Right(rule, invRule, table);	
			
			// converting GACs from LHS to RHS
			this.convertGACsLeft2Right(rule, invRule, table);		
	
			// convert attr conditions
			this.convertAttrConditionLeft2Right(rule, invRule);	
		}
		else 
			ok = false;
		
		return ok;
	}
	
	private void copyAttrContextFromTo(AttrContext from, AttrContext to) {
		// copy attr context : variables
		VarTuple avt = (VarTuple) to.getVariables();
		VarTuple avtOther = (VarTuple) from.getVariables();
		for (int i = 0; i < avtOther.getSize(); i++) {
			VarMember varOther = avtOther.getVarMemberAt(i);
			// System.out.println(varOther.getName() +" mark:
			// "+varOther.getMark());
			VarMember var = avt.getVarMemberAt(varOther.getName());
			if (var == null) {
				avt.declare(agg.attribute.facade.impl.DefaultInformationFacade
						.self().getJavaHandler(), varOther.getDeclaration()
						.getTypeName(), varOther.getDeclaration().getName());
				var = avt.getVarMemberAt(varOther.getName());
			}
			if (var != null) {
				var.setMark(varOther.getMark());
				var.setInputParameter(varOther.isInputParameter());
			}
		}
		// copy attr conditions
		CondTuple act = (CondTuple) to.getConditions();
		CondTuple actOther = (CondTuple) from.getConditions();
		for (int i = 0; i < actOther.getSize(); i++) {
			CondMember condOther = actOther.getCondMemberAt(i);
			// System.out.println(condOther+" mark: "+condOther.getMark());
			CondMember cond = (CondMember) act.addCondition(condOther
					.getExprAsText());
			cond.setMark(condOther.getMark());
			// System.out.println(cond+" mark: "+cond.getMark());
		}
	}
		
	/**
	 * Creates a morphism from the given Graph <code>g</code> to the given Graph <code>gToExtend</code>.
	 * For each object of <code>g</code> a copie is created into <code>gToExtend</code>
	 * and a mapping set from an object of the Graph <code>g</code> to its copy 
	 * in the Graph <code>gToExtend</code>.
	 * 
	 * @param gToExtend
	 * @param g
	 * @return morphism from <code>g</code> to <code>gToExtend</code>
	 * @throws Exception
	 */
	public  OrdinaryMorphism extendGraphByGraph(final Graph gToExtend, final Graph g) 
	 throws Exception{
//		System.out.println("BF.extendGraphByGraph:: "+gToExtend.getName()+"  by  "+g.getName());
		final OrdinaryMorphism g2extendedg = this.createMorphism(g, gToExtend);
		Iterator<Node> nodes = g.getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node elem= nodes.next();
			try {
				Node copy = gToExtend.copyNode(elem);
				g2extendedg.addMapping(elem, copy);
			} 
			catch (TypeException ex) {
//				System.out.println("BF.extendGraphByGraph:: Node copy failed: "+ex.getMessage());
				throw ex;
			}	
			catch (BadMappingException e) {
//				System.out.println("BF.extendGraphByGraph:: Node copy failed: "+e.getMessage());
				throw e;
			}
		}
		Iterator<Arc> arcs = g.getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc elem = arcs.next();
			try {
				Node s = (Node) g2extendedg.getImage(elem.getSource());
				Node t = (Node) g2extendedg.getImage(elem.getTarget());
				Arc copy = gToExtend.copyArc(elem, s, t);
				g2extendedg.addMapping(elem, copy);
			} 
			catch (TypeException ex) {
//				System.out.println("BF.extendGraphByGraph:: Arc copy failed: "+ex.getMessage());
				throw ex;
			}	
			catch (BadMappingException e) {
//				System.out.println("BF.extendGraphByGraph:: Arc copy failed: "+e.getMessage());
				throw e;
			}
		}
		return g2extendedg;
	}

	/**
	 * Create an parent atomic to construct an atomic graph constraint.
	 */
	public final AtomConstraint createAtomic(TypeSet types, String name) {
		Graph g1 = createGraph(types);
		g1.setKind(GraphKind.PREMISE);
		Graph g2 = createGraph(types);
		g2.setKind(GraphKind.CONCLUSION);
		return new AtomConstraint(g1, g2, agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newContext(AttrMapping.PLAIN_MAP), name);
	}

	/** Create an ordinary morphism. */
	public final OrdinaryMorphism createMorphism(final Graph orig, final Graph img) {
		AttrContext context = agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newContext(AttrMapping.PLAIN_MAP);
		
		OrdinaryMorphism output = new OrdinaryMorphism(orig, img, context);
		
		// hier nicht!! seiten effekt: attribute (type) konflikt 
//		AttrContext aLeftContext =
//		agg.attribute.impl.AttrTupleManager.getDefaultManager().newLeftContext(context);
//		AttrContext aRightContext =
//		agg.attribute.impl.AttrTupleManager.getDefaultManager().newRightContext(context);		
//		output.getSource().setAttrContext(aLeftContext);
//		output.getTarget().setAttrContext(aRightContext);
		//
		declareVariable(output.getSource(), (VarTuple) output.getAttrContext()
				.getVariables());
		declareVariable(output.getTarget(), (VarTuple) output.getAttrContext()
				.getVariables());
		return output;
	}

	/** Create an general (nested) morphism. */
	public final NestedApplCond createGeneralMorphism(Graph orig, Graph img) {
		AttrContext context = agg.attribute.impl.AttrTupleManager
				.getDefaultManager().newContext(AttrMapping.PLAIN_MAP);
		
		AttrContext aLeftContext =
		agg.attribute.impl.AttrTupleManager.getDefaultManager().newLeftContext(context);
		AttrContext aRightContext =
		agg.attribute.impl.AttrTupleManager.getDefaultManager().newRightContext(context);
		 
		NestedApplCond output = new NestedApplCond(orig, img, context);
		// new
		output.getSource().setAttrContext(aLeftContext);
		output.getTarget().setAttrContext(aRightContext);
		//
		declareVariable(output.getSource(), (VarTuple) output.getAttrContext()
				.getVariables());
		declareVariable(output.getTarget(), (VarTuple) output.getAttrContext()
				.getVariables());
		return output;
	}
	
	/**
	 * Create an ordinary morphism. The original or image graph objects can have
	 * unset attribute members. If implicit is TRUE a variable will be used as
	 * value of those attribute members.
	 * 
	 * @param orig
	 *            The original graph.
	 * @param img
	 *            The image graph.
	 * @param implicit
	 *            If true, all unset attributes of the target graph will get a
	 *            variable as value.
	 * @return The new ordinary morphism.
	 */
	public final OrdinaryMorphism createMorphism(final Graph orig, final Graph img, boolean implicit) {
		return createMorphism(orig, img, implicit, "");
	}

	/**
	 * Create an ordinary morphism. The original or image graph objects can have
	 * unset attribute members. If implicit is TRUE a variable will be used as
	 * value of those attribute members.
	 * 
	 * @param orig
	 *            The original graph.
	 * @param img
	 *            The image graph.
	 * @param implicit
	 *            If true, all unset attributes of the target graph will get a
	 *            variable as value.
	 * @param helpMarkOfVars
	 *            The help name of the implicitly set variables.
	 * @return The new ordinary morphism.
	 */
	public final OrdinaryMorphism createMorphism(
			final Graph orig, 
			final Graph img,
			boolean implicit, 
			String helpMarkOfVars) {
		
		OrdinaryMorphism m = createMorphism(orig, img);
		
		int count = m.getAttrContext().getVariables().getSize();
//		VarTuple vars = (VarTuple) m.getAttrContext().getVariables();
		if (implicit) {
			String mark = "_" + helpMarkOfVars;
			Iterator<?> elements = img.getNodesSet().iterator();
			while (elements.hasNext()) {
				GraphObject grob = (GraphObject) elements.next();
				if (grob.getAttribute() == null)
					continue;
				agg.attribute.AttrInstance attrs = grob.getAttribute();
				agg.attribute.impl.ValueTuple values = (agg.attribute.impl.ValueTuple) attrs;
				for (int i = 0; i < values.getNumberOfEntries(); i++) {
					agg.attribute.impl.ValueMember vm = values
							.getValueMemberAt(i);
					if (!vm.isSet()) {
						String t = vm.getName() + String.valueOf(count) + mark;
						vm.setExprAsText(t);
						vm.setTransient(true);
						count++;
//						System.out.println("BaseFactory.createMorphism::  Target graph: "+vm.getExprAsText());
					}
					// System.out.println("BF.createMorphism:Target graph: attr.
					// var: "+vm.getName()+" : "+vm.getExprAsText());
				}
			}
			elements = img.getArcsSet().iterator();
			while (elements.hasNext()) {
				GraphObject grob = (GraphObject) elements.next();
				if (grob.getAttribute() == null)
					continue;
				agg.attribute.AttrInstance attrs = grob.getAttribute();
				agg.attribute.impl.ValueTuple values = (agg.attribute.impl.ValueTuple) attrs;
				for (int i = 0; i < values.getNumberOfEntries(); i++) {
					agg.attribute.impl.ValueMember vm = values
							.getValueMemberAt(i);
					if (!vm.isSet()) {
						String t = vm.getName() + String.valueOf(count) + mark;
						vm.setExprAsText(t);
						vm.setTransient(true);
						count++;
						// System.out.println("BaseFactory.createMorphism::
						// Target graph: "+vm.getExprAsText());
					}
					// System.out.println("BF.createMorphism:Target graph: attr.
					// var: "+vm.getName()+" : "+vm.getExprAsText());
				}
			}
		}
		return m;
	}

	public VarMember declareVariable(
			AttrHandler attrHandler, String typeName,
			String name, VarTuple tuple) {
		VarMember var = tuple.getVarMemberAt(name);
		if (var == null) {
			tuple.declare(attrHandler, typeName, name);
			var = (VarMember) tuple.getEntryAt(name);
		} 
		else if (!var.getDeclaration().getTypeName().equals(typeName)) {
			String name1 = name.concat(String.valueOf(tuple.getNumberOfEntries()));
			tuple.declare(attrHandler, typeName, name1);
			var = tuple.getVarMemberAt(name1);
		} 
		return var;
	}

	/**
	 * Adds not declared variable of attributes to the specified variable tuple. 
	 * @param g  graph which nodes and edges are searched for not declared variables
	 * @param tuple variable tuple to declare new variables
	 */
	public void declareVariable(Graph g, VarTuple tuple) {
		for (Iterator<Node> elements = g.getNodesSet().iterator(); elements.hasNext();) {
			GraphObject grob = elements.next();
			if (grob.getAttribute() == null)
				continue;
			AttrInstance attrs = grob.getAttribute();
			ValueTuple values = (agg.attribute.impl.ValueTuple) attrs;
			for (int i = 0; i < values.getNumberOfEntries(); i++) {
				ValueMember vm = values.getValueMemberAt(i);
				if (vm.isSet() && vm.getExpr().isVariable()) {
//					System.out.println(">>>>> "+g.getName()+"  >>>  "+vm.getExprAsText()+"    "+vm.isTransient());
					declareVariable(vm.getDeclaration().getHandler(), 
									vm.getDeclaration().getTypeName(),
									vm.getExprAsText(), tuple);					
				}
			}
		}
		for (Iterator<Arc> elements = g.getArcsSet().iterator(); elements.hasNext();) {
			GraphObject grob = elements.next();
			if (grob.getAttribute() == null)
				continue;
			AttrInstance attrs = grob.getAttribute();
			ValueTuple values = (agg.attribute.impl.ValueTuple) attrs;
			for (int i = 0; i < values.getNumberOfEntries(); i++) {
				ValueMember vm = values.getValueMemberAt(i);
				if (vm.isSet() && vm.getExpr().isVariable()) {
					declareVariable(vm.getDeclaration().getHandler(), 
									vm.getDeclaration().getTypeName(),
									vm.getExprAsText(), tuple);
				}
			}
		}
	}

	public final OrdinaryMorphism createMatchfromMorph(
			final OrdinaryMorphism base,
			final AttrContext base_context) {
		
		OrdinaryMorphism match = new OrdinaryMorphism(
				base.getOriginal(), 
				base.getImage(), 
				base.getAttrManager().newContext(AttrMapping.MATCH_MAP, base_context));

		declareVariable(match.getOriginal(), (VarTuple) match.getAttrContext()
				.getVariables());
		declareVariable(match.getImage(), (VarTuple) match.getAttrContext()
				.getVariables());

		// set mappings
		Iterator<?> elems = match.getOriginal().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			GraphObject i = base.getImage(o);
			if (i != null) {
				try {
					match.addMapping(o, i);
				} catch (BadMappingException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		elems = match.getOriginal().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			GraphObject i = base.getImage(o);
			if (i != null) {
				try {
					match.addMapping(o, i);
				} catch (BadMappingException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		return match;
	}

	public final boolean createMatchfromMorph(
			final OrdinaryMorphism baseMorph,
			final OrdinaryMorphism targetMatch, 
			final AttrContext base_context) {
		
		targetMatch.setSource(baseMorph.getOriginal());
		targetMatch.setTarget(baseMorph.getImage());
		targetMatch.setAttrContext(baseMorph.getAttrManager().newContext(
										AttrMapping.MATCH_MAP, base_context));

		declareVariable(targetMatch.getOriginal(), (VarTuple) targetMatch
				.getAttrContext().getVariables());
		declareVariable(targetMatch.getImage(), (VarTuple) targetMatch
				.getAttrContext().getVariables());

		// set mappings
		Iterator<?> elems = targetMatch.getOriginal().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			GraphObject i = baseMorph.getImage(o);
			if (i != null) {
				try {
					targetMatch.addMapping(o, i);
				} catch (BadMappingException ex) {
					targetMatch.clear();
					return false;
				}
			}
		}
		elems = targetMatch.getOriginal().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			GraphObject i = baseMorph.getImage(o);
			if (i != null) {
				try {
					targetMatch.addMapping(o, i);
				} catch (BadMappingException ex) {
					targetMatch.clear();
					return false;
				}
			}
		}
		return true;
	}

	public final OrdinaryMorphism createMorphfromMorph(
			final OrdinaryMorphism base,
			final AttrContext base_context) {
		// System.out.println("BaseFactory.createMorphfromMorph");
		OrdinaryMorphism match = new OrdinaryMorphism(
								base.getOriginal(), 
								base.getImage(), 
								base.getAttrManager().newContext(AttrMapping.PLAIN_MAP, base_context));

		/* set mappings */
		Iterator<?> elems = match.getOriginal().getNodesSet().iterator();
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			GraphObject i = base.getImage(o);
			if (i != null) {
				try {
					match.addMapping(o, i);
				} catch (BadMappingException ex) {
				}
			}
		}
		elems = match.getOriginal().getArcsSet().iterator();
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			GraphObject i = base.getImage(o);
			if (i != null) {
				try {
					match.addMapping(o, i);
				} catch (BadMappingException ex) {
				}
			}
		}
		return match;
	}

	/** Dispose the specified morphism. */
	public final void destroyMorphism(OrdinaryMorphism morph) {
		if (morph != null)
			morph.dispose();
	}

	/**
	 * Create an empty match morphism between the left side of the given rule
	 * and my start graph. Note that this does not yield a valid match (unless
	 * the left side of the given rule is empty), because matches have to be
	 * total morphisms.
	 * 
	 * @param rule
	 *            The rule.
	 * @param graph
	 *            The graph.
	 * @return The new match.
	 */
	public final Match createMatch(Rule rule, Graph graph) {
		final Match match = new Match(rule, graph);
		if (match.getImage().getVariableNamesOfAttributes().size() != 0)
			((ContextView) match.getAttrContext()).setVariableContext(true);
		return match;
	}

	/**
	 * Create an empty match between the left side of the given rule and a start
	 * graph. Note that this does not yield a valid match (unless the left side
	 * of the given rule is empty), because matches have to be total morphisms.
	 * The graph objects can have unset attribute members. If implicit is TRUE a
	 * variable will be used as value of those attribute members.
	 * 
	 * @param rule
	 *            The rule.
	 * @param graph
	 *            The graph.
	 * @return The new match.
	 */
	public final Match createMatch(Rule rule, Graph graph, boolean implicit) {
		return createMatch(rule, graph, implicit, "");
	}

	/**
	 * Create an empty match between the left side of the given rule and a start
	 * graph. Note that this does not yield a valid match (unless the left side
	 * of the given rule is empty), because matches have to be total morphisms.
	 * The graph objects can have unset attribute members. If implicit is TRUE a
	 * variable will be used as value of those attribute members.
	 * 
	 * @param rule
	 *            The rule.
	 * @param graph
	 *            The graph.
	 * @return The new match.
	 */
	public final Match createMatch(
			final Rule rule, 
			final Graph graph, 
			boolean implicit,
			final String helpMarkOfVars) {

		Match match = createMatch(rule, graph);
		int count = match.getAttrContext().getVariables().getSize();
		VarTuple vars = (VarTuple) match.getAttrContext().getVariables();
		if (implicit) {
			String mark = "_" + helpMarkOfVars;
			Iterator<Node> nodes = graph.getNodesSet().iterator();
			while (nodes.hasNext()) {
				GraphObject grob = nodes.next();
				if (grob.getAttribute() != null) {
					ValueTuple values = (ValueTuple) grob.getAttribute();
					for (int i = 0; i < values.getNumberOfEntries(); i++) {
						ValueMember vm = values.getValueMemberAt(i);
						if (!vm.isSet()) {
							String t = vm.getName() + String.valueOf(count) + mark;														
							VarMember var = declareVariable(vm.getDeclaration().getHandler(), 
															vm.getDeclaration().getTypeName(), 
															t, 
															vars);							
							var.setTransient(true);
							count++;
							vm.setTransient(true);
							vm.setExprAsText(var.getName());
						}
					}
				}
			}
			
			Iterator<Arc> arcs = graph.getArcsSet().iterator();
			while (arcs.hasNext()) {
				GraphObject grob = arcs.next();
				if (grob.getAttribute() != null) {				
					ValueTuple values = (ValueTuple) grob.getAttribute();
					for (int i = 0; i < values.getNumberOfEntries(); i++) {
						ValueMember vm = values.getValueMemberAt(i);
						if (!vm.isSet()) {
							String t = vm.getName() + String.valueOf(count) + mark;
							VarMember var = declareVariable(vm.getDeclaration().getHandler(), 
															vm.getDeclaration().getTypeName(),
															t,
															vars);
							var.setTransient(true);
							count++;
							vm.setTransient(true);
							vm.setExprAsText(var.getName());
						}
					}
				}
			}
		}
		return match;
	}

	/**
	 * Makes a match for a rule and a morphism from the left hand side to a
	 * graph. The mapping of the morphism will be to a mapping of the match. The
	 * graph objects can have unset attribute members. A variable will be used
	 * as value of those attribute members.
	 * 
	 * @param rule
	 *            The rule.
	 * @param morph
	 *            The morphism.
	 * @return The new match.
	 */
	public Match makeMatch(Rule rule, OrdinaryMorphism morph) {
		return makeMatch(rule, morph, "");
	}

	/**
	 * Makes a match for a rule and a morphism from the left hand side of a rule
	 * to the target graph of a morphism. The mapping of the morphism will be to
	 * a mapping of the match. The graph objects can have unset attribute
	 * members. A variable will be used as value of those attribute members.
	 * 
	 * @param rule
	 *            The rule.
	 * @param morph
	 *            The morphism.
	 * @return The new match.
	 */
	public Match makeMatch(
			final Rule rule, 
			final OrdinaryMorphism morph,
			final String helpMarkOfVars) {
		String mark = "";
		if (!helpMarkOfVars.equals(""))
			mark = "_" + helpMarkOfVars;

		Match m = createMatch(rule, morph.getImage(), true);
		boolean variableContext = false;
		if ((m.getImage().getVariableNamesOfAttributes().size() != 0)
				|| ((ContextView) morph.getAttrContext()).isVariableContext()) {
			((ContextView) m.getAttrContext()).setVariableContext(true);
			variableContext = true;
		}

		VarTuple vars = (VarTuple) m.getAttrContext().getVariables();
		int count = vars.getSize();
		Enumeration<GraphObject> elements = morph.getDomain();
		while (elements.hasMoreElements()) {
			GraphObject obj = elements.nextElement();
			GraphObject img = morph.getImage(obj);
			if (img != null) {
				if (obj.getAttribute() == null && img.getAttribute() == null) {
					try {
						m.addMapping(obj, img);
					} catch (BadMappingException e) {
//						System.out.println("BaseFactory.makeMatch: (no attrs) "+ e.getMessage());
						destroyMatch(m);
						m = null;
						return null;
					}
					continue;
				}
				if (variableContext 
						&& obj.getAttribute() != null 
						&& img.getAttribute() != null) {
					AttrInstance objAttrs = obj.getAttribute();
					ValueTuple objValues = (agg.attribute.impl.ValueTuple) objAttrs;
					AttrInstance imgAttrs = img.getAttribute();
					ValueTuple imgValues = (agg.attribute.impl.ValueTuple) imgAttrs;

					for (int i = 0; i < imgValues.getNumberOfEntries(); i++) {
						ValueMember imgvm = imgValues.getValueMemberAt(i);						
						ValueMember objvm = objValues.getValueMemberAt(imgvm.getName());
						
						if (objvm != null) {
							if (objvm.isSet()) {
								if (!imgvm.isSet()) {
									if (objvm.getExpr().isVariable()) {
										String t = imgvm.getName() + String.valueOf(count)
												+ mark;
										declareVariable(imgvm.getDeclaration()
												.getHandler(), imgvm.getDeclaration()
												.getTypeName(), t, vars);
										count++;
										imgvm.setExprAsText(t);
										imgvm.setTransient(true);
									} else if (objvm.getExpr().isConstant()) {									
										imgvm.setExprAsObject(objvm.getExpr().getCopy());
										imgvm.setTransient(true);
									}
								}
							}
							else {
								if (!imgvm.isSet()) {
									String t = imgvm.getName() + String.valueOf(count)
											+ mark;
									declareVariable(imgvm.getDeclaration().getHandler(),
											imgvm.getDeclaration().getTypeName(), t, vars);
									count++;
									imgvm.setExprAsText(t);
									imgvm.setTransient(true);
								}
							}
						}
					}
				}
				
				try {
					m.addMapping(obj, img);
				} catch (BadMappingException e) {
					destroyMatch(m);
					m = null;
					return null;
				}
			}
		}
		return m;
	}

	/** Dispose the specified match morphism. */
	public final void destroyMatch(OrdinaryMorphism match) {
		if (match != null)
			match.dispose();
	}

	/**
	 * Here:<br> 
	 * cond.getSource() == morph.getSource()<br>
	 * Returns morphism :<br>
	 * m =  morph.getTarget() -> cond.getTargetCopy()
	 */
	public OrdinaryMorphism shiftApplCondRight(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph) {
		
		if (cond.getSource() == morph.getSource()) {
			final OrdinaryMorphism condIso = cond.getTarget().isomorphicCopy();
			if (condIso == null)
				return null;
			
			OrdinaryMorphism shiftCond = (cond instanceof NestedApplCond)? 
					BaseFactory.theFactory().createGeneralMorphism(morph.getTarget(), condIso.getTarget())
					: BaseFactory.theFactory().createMorphism(morph.getTarget(), condIso.getTarget());
								
			Enumeration<GraphObject> condDom = cond.getDomain();
			while (condDom.hasMoreElements()) {
				GraphObject go = condDom.nextElement();
				if (go.isNode()) {
					GraphObject condImg = cond.getImage(go);
					if (condImg != null) {
						GraphObject morphImg = morph.getImage(go);
						GraphObject isoImg = condIso.getImage(condImg);
						if (morphImg != null && isoImg != null) {
							try {
								shiftCond.addMapping(morphImg, isoImg);
							} catch (BadMappingException ex) {}
						} 
					}
				}
			}
			condDom = cond.getDomain();
			while (condDom.hasMoreElements()) {
				GraphObject go = condDom.nextElement();
				if (go.isArc()) {
					GraphObject condImg = cond.getImage(go);
					if (condImg != null) {
						GraphObject morphImg = morph.getImage(go);
						GraphObject isoImg = condIso.getImage(condImg);
						if (morphImg != null) {
							if (isoImg != null) {						
								try {
									shiftCond.addMapping(morphImg, isoImg);
								} catch (BadMappingException ex) {}
							}
						} 
						else {
							try {
								shiftCond.getTarget().destroyArc((Arc)isoImg);
							} catch (TypeException ex) {}
						}
					}
				}
			}
			condDom = cond.getDomain();
			while (condDom.hasMoreElements()) {
				GraphObject go = condDom.nextElement();
				if (go.isNode()) {
					GraphObject condImg = cond.getImage(go);
					if (condImg != null) {
						GraphObject morphImg = morph.getImage(go);
						GraphObject isoImg = condIso.getImage(condImg);
						if (morphImg == null && isoImg != null) {
							try {
								shiftCond.getTarget().destroyNode((Node)isoImg);
							} catch (TypeException ex) {}
						} 
					}
				}
			}
			return shiftCond;
		} 		
		return null;
	}
	
	/**
	 * Here:<br> 
	 * cond.getSource() == morph.getTarget()<br>
	 * Returns morphism : morph.getSource() -> cond.getTarget()
	 */
	public OrdinaryMorphism shiftApplCondLeft(
			final OrdinaryMorphism cond,
			final OrdinaryMorphism morph) {
		
		if (cond.getSource() == morph.getTarget()) {
			OrdinaryMorphism shiftCond = (cond instanceof NestedApplCond)? 
					BaseFactory.theFactory().createGeneralMorphism(morph.getSource(), cond.getTarget())
					: BaseFactory.theFactory().createMorphism(morph.getSource(), cond.getTarget());
			
			if (shiftCond.completeDiagram3(morph, cond)) {
				return shiftCond;
			} 
			else {
				shiftCond.dispose();
				shiftCond = null;
			}
		} 		
		return null;
	}
	

	/**
	 * Given the list with PACs. Replace each PAC by a General AC.
	 * Returns a Formula over GACs defined as<br>
	 * f = OR{ci} (ci an element of the list) as disjunction of the GACs.
	 * The given list contains the GACs now and the PACs are disposed.
	 * @param list
	 * @return
	 */
	public Formula replacePACsByGACs(final List<OrdinaryMorphism> list) {
		// replace PACs by GACs and formula = GAC1 || GAC2 || ... 
		List<Evaluable> shiftEvals = new Vector<Evaluable>(list.size());
		for (int k=0; k<list.size(); k++) {
			OrdinaryMorphism c = list.get(k);
			NestedApplCond nc = BaseFactory.theBaseFactory.createGeneralMorphism(c.getSource(), c.getTarget());
			nc.getDomainObjects().addAll(c.getDomainObjects());
			nc.getCodomainObjects().addAll(c.getCodomainObjects());	 
			BaseFactory.theBaseFactory.unsetAllTransientAttrValues(nc);
			// set and adjust attr context
			BaseFactory.theBaseFactory.declareVariable(nc.getTarget(), (VarTuple)nc.getAttrContext().getVariables());
			BaseFactory.theBaseFactory.adjustAttributeValueAlongMorphismMapping(nc);
			nc.setEnabled(c.isEnabled());
			nc.setName(c.getName());
			if (nc.isEnabled())
				shiftEvals.add(nc);
				
			list.remove(k);
			list.add(k, nc);
			c.dispose();
		}
		Formula f = new Formula(shiftEvals, Formula.OR);
		return f;
	}
	
	/**
	 * Given the list with NACs. Replace each NAC by a General AC.
	 * Returns a Formula over GACs defined as<br>
	 * f = NOT(OR{ci}) (ci an element of the list) as negation of the disjunction of the GACs.
	 * The given list contains the GACs now and the NACs are disposed.
	 * @param list
	 * @return
	 */
	public Formula replaceNACsByGACs(final List<OrdinaryMorphism> list) {
		// replace NACs by GACs and formula = !(GAC1 || GAC2 || ...) 
		List<Evaluable> shiftEvals = new Vector<Evaluable>(list.size());
		for (int k=0; k<list.size(); k++) {
			OrdinaryMorphism c = list.get(k);
			NestedApplCond nc = BaseFactory.theBaseFactory.createGeneralMorphism(c.getSource(), c.getTarget());
			nc.getDomainObjects().addAll(c.getDomainObjects());
			nc.getCodomainObjects().addAll(c.getCodomainObjects());	 
			BaseFactory.theBaseFactory.unsetAllTransientAttrValues(nc);
			// set and adjust attr context
			BaseFactory.theBaseFactory.declareVariable(nc.getTarget(), (VarTuple)nc.getAttrContext().getVariables());
			BaseFactory.theBaseFactory.adjustAttributeValueAlongMorphismMapping(nc);
			nc.setEnabled(c.isEnabled());
			nc.setName(c.getName());
			if (nc.isEnabled())
				shiftEvals.add(nc);
				
			list.remove(k);
			list.add(k, nc);
			c.dispose();
		}
		Formula f = new Formula(Formula.NOT, new Formula(shiftEvals, Formula.OR), null);
		return f;
	}
		
	
	public void extendAttrContextVariableByPrefix(final Rule rule, final String prefix, final String oppositePrefix) {
		VarTuple varsm = (VarTuple) rule.getAttrContext().getVariables();		
		for (int i = 0; i < varsm.getSize(); i++) {
			final VarMember vm = varsm.getVarMemberAt(i);
			final String from = vm.getName();
			if (from.startsWith(prefix)
					|| from.startsWith(oppositePrefix)) {
				continue;
			}
			
			final String to = prefix+from;
//			System.out.println(from+"   "+to);
			vm.getDeclaration().setName(to);
			
			// rename variables in left/right graphs of morphs
			setAttributeVariable(rule.getSource(), from, to, rule.getAttrContext(), varsm);
			setAttributeVariable(rule.getTarget(), from, to, rule.getAttrContext(), varsm);
			// rename variables in conditions
			final CondTuple conds = (CondTuple) rule.getAttrContext().getConditions();
			renameVariableOfCondition(rule.getAttrContext(), conds, from, to);
			
			// rename variables in NACs
			final List<OrdinaryMorphism> nacs = rule.getNACsList();
			for(int j=0; j<nacs.size(); j++) {
				OrdinaryMorphism nac = nacs.get(j);
				setAttributeVariable(nac.getTarget(), from, to, rule.getAttrContext(), varsm);
			}
			// rename variables in PACs
			final List<OrdinaryMorphism> pacs = rule.getPACsList();
			for(int j=0; j<pacs.size(); j++) {
				OrdinaryMorphism pac = pacs.get(j);
				setAttributeVariable(pac.getTarget(), from, to, rule.getAttrContext(), varsm);
			}
		}
	}

	public void trimAttrContextVariableByPrefix(final Rule rule, final String prefix, final String oppositePrefix) {
		VarTuple varsm = (VarTuple) rule.getAttrContext().getVariables();		
		for (int i = 0; i < varsm.getSize(); i++) {
			VarMember vm = varsm.getVarMemberAt(i);			
			String from = vm.getName();	
			if (from.startsWith(prefix)
					|| from.startsWith(oppositePrefix)) {				
				String to = from.substring(prefix.length());
				vm.getDeclaration().setName(to);
//				System.out.println(from+"   "+to);
				
				// rename variables in left/right graphs of morphs
				setAttributeVariable(rule.getSource(), from, to, rule.getAttrContext(), varsm);
				setAttributeVariable(rule.getTarget(), from, to, rule.getAttrContext(), varsm);
				// rename variables in conditions
				CondTuple conds = (CondTuple) rule.getAttrContext().getConditions();
				renameVariableOfCondition(rule.getAttrContext(), conds, from, to);
				
				// rename variables in NACs
				final List<OrdinaryMorphism> nacs = rule.getNACsList();
				for(int j=0; j<nacs.size(); j++) {
					OrdinaryMorphism nac = nacs.get(j);				
					setAttributeVariable(nac.getTarget(), from, to, rule.getAttrContext(), varsm);
				}
				// rename variables in PACs
				final List<OrdinaryMorphism> pacs = rule.getPACsList();
				for(int j=0; j<pacs.size(); j++) {
					OrdinaryMorphism pac = pacs.get(j);				
					setAttributeVariable(pac.getTarget(), from, to, rule.getAttrContext(), varsm);
				}
			}
		}
	}
	
	/**
	 * Rename variable in the attribute context of the Rule r2, if a similar
	 * variable is already used in the attribute context of the Rule r1.
	 * Use defined prefix to rename variable.
	 * Store old name by new name into defined store container.
	 */
	public void renameSimilarVariable(
			final Rule r1, 
			final Rule r2,
			final String prefix,
			final Hashtable<String, String> storeNewName2OldName) {
		
		int index = 1;
		String mark = String.valueOf(index);
		
		VarTuple varsm1 = (VarTuple) r1.getAttrContext().getVariables();
		VarTuple varsm2 = (VarTuple) r2.getAttrContext().getVariables();
		for (int i = 0; i < varsm1.getSize(); i++) {
			VarMember vm1 = varsm1.getVarMemberAt(i);
			VarMember vm2 = varsm2.getVarMemberAt(vm1.getName());
			if (vm2 != null) {
				String from = vm2.getName();
				String to = prefix.concat(vm2.getName());
				
				while (varsm2.getVarMemberAt(to) != null
						|| varsm1.getVarMemberAt(to) != null) {
					to = prefix.concat(vm2.getName()).concat(mark);
					mark = String.valueOf(index++);
				}
				
				if (storeNewName2OldName != null)
					storeNewName2OldName.put(to, from);				
				
				vm2.getDeclaration().setName(to);
				
				// rename variables in left/right graphs of morphs
				setAttributeVariable(r2.getSource(), from, to, r2.getAttrContext(), varsm2);
				setAttributeVariable(r2.getTarget(), from, to, r2.getAttrContext(), varsm2);
				
				// rename variables in NACs
				final List<OrdinaryMorphism> nacs = r2.getNACsList();
				for(int j=0; j<nacs.size(); j++) {
					OrdinaryMorphism nac = nacs.get(j);				
					setAttributeVariable(nac.getTarget(), from, to, r2.getAttrContext(), varsm2);
				}
				// rename variables in PACs
				final List<OrdinaryMorphism> pacs = r2.getPACsList();
				for(int j=0; j<pacs.size(); j++) {
					OrdinaryMorphism pac = pacs.get(j);				
					setAttributeVariable(pac.getTarget(), from, to, r2.getAttrContext(), varsm2);
				}
				
				// rename variables in conditions
				CondTuple conds = (CondTuple) r2.getAttrContext()
						.getConditions();
				renameVariableOfCondition(r2.getAttrContext(), conds, from, to);
			}
		}
	}
	
	/**
	 * Restores renamed variable of the defined rule.
	 * 
	 * @param r
	 * @param storeNewName2OldName
	 */
	public void restoreVariableNameOfRule(
			final Rule r,
			final Hashtable<String, String> storeNewName2OldName) {

		VarTuple varsm2 = (VarTuple) r.getAttrContext().getVariables();
		for (int i = 0; i < varsm2.getSize(); i++) {
			VarMember vm2 = varsm2.getVarMemberAt(i);
			String from = vm2.getName();
			String to = storeNewName2OldName.get(from);
			if (to != null) {				
				vm2.getDeclaration().setName(to);

				// rename variables in left/right graphs of morphs
				setAttributeVariable(r.getSource(), from, to, r.getAttrContext(), varsm2);
				setAttributeVariable(r.getTarget(), from, to, r.getAttrContext(), varsm2);
				
				// rename variables in NACs
				final List<OrdinaryMorphism> nacs = r.getNACsList();
				for(int j=0; j<nacs.size(); j++) {
					OrdinaryMorphism nac = nacs.get(j);				
					setAttributeVariable(nac.getTarget(), from, to, r.getAttrContext(), varsm2);
				}
				// rename variables in PACs
				final List<OrdinaryMorphism> pacs = r.getPACsList();
				for(int j=0; j<pacs.size(); j++) {
					OrdinaryMorphism pac = pacs.get(j);				
					setAttributeVariable(pac.getTarget(), from, to, r.getAttrContext(), varsm2);
				}
				
				// rename variables in conditions
				CondTuple conds = (CondTuple) r.getAttrContext()
						.getConditions();
				renameVariableOfCondition(r.getAttrContext(), conds, from, to);
			}
		}
	}
	
	/**
	 * Rename variable in the attribute context of the Rule r2, if a similar
	 * variable is already used in the attribute context of the Rule r1.
	 */
	public void renameSimilarVariable(Rule r1, Rule r2) {
		int index = 1;
		String mark = String.valueOf(index);
		VarTuple varsm1 = (VarTuple) r1.getAttrContext().getVariables();
		VarTuple varsm2 = (VarTuple) r2.getAttrContext().getVariables();
		for (int i = 0; i < varsm1.getSize(); i++) {
			VarMember vm1 = varsm1.getVarMemberAt(i);
			VarMember vm2 = varsm2.getVarMemberAt(vm1.getName());
			if ((vm2 != null)
			/*
			 * &&
			 * vm1.getDeclaration().getTypeName().equals(vm2.getDeclaration().getTypeName())
			 */) {
				String from = vm2.getName();
				String to = vm2.getName() + mark;
				while (varsm2.getVarMemberAt(to) != null) {
					mark = String.valueOf(index++);
					to = vm2.getName() + mark;
				}
				vm2.getDeclaration().setName(to);
				// System.out.println(" variable "+from+" renamed to :
				// "+vm2.getName());
				
				// rename variables in left/right graphs of morphs
				setAttributeVariable(r2.getSource(), from, to, r2.getAttrContext(), varsm2);
				setAttributeVariable(r2.getTarget(), from, to, r2.getAttrContext(), varsm2);
				// System.out.println("--------rename variables in
				// conditions--------");
				
				// rename variables in conditions
				CondTuple conds = (CondTuple) r2.getAttrContext()
						.getConditions();
				renameVariableOfCondition(r2.getAttrContext(), conds, from, to);
				
				// rename variables in NACs
				final List<OrdinaryMorphism> nacs = r2.getNACsList();
				for(int j=0; j<nacs.size(); j++) {
					OrdinaryMorphism nac = nacs.get(j);				
					setAttributeVariable(nac.getTarget(), from, to, r2.getAttrContext(), varsm2);
				}
				
				// rename variables in PACs
				final List<OrdinaryMorphism> pacs = r2.getPACsList();
				for(int j=0; j<pacs.size(); j++) {
					OrdinaryMorphism pac = pacs.get(j);				
					setAttributeVariable(pac.getTarget(), from, to, r2.getAttrContext(), varsm2);
				}
			}
		}
	}

	/**
	 * Rename variable in the attribute context of the OrdinaryMorphism m2, if a
	 * similar variable is already used in the attribute context of the
	 * OrdinaryMorphism m1.
	 */
	public void renameSimilarVariable(OrdinaryMorphism m1, OrdinaryMorphism m2) {
		int index = 1;
		String mark = String.valueOf(index);
		VarTuple varsm1 = (VarTuple) m1.getAttrContext().getVariables();
		VarTuple varsm2 = (VarTuple) m2.getAttrContext().getVariables();
		for (int i = 0; i < varsm1.getSize(); i++) {
			VarMember vm1 = varsm1.getVarMemberAt(i);
			VarMember vm2 = varsm2.getVarMemberAt(vm1.getName());
			if ((vm2 != null)
					&& vm1.getDeclaration().getTypeName().equals(
							vm2.getDeclaration().getTypeName())) {
				String from = vm2.getName();
				String to = vm2.getName().concat(mark);
				while (varsm2.getVarMemberAt(to) != null) {
					mark = String.valueOf(index++);
					to = vm2.getName().concat(mark);
				}
				vm2.getDeclaration().setName(to);
				// System.out.println(" variable "+from+" renamed to :
				// "+vm2.getName());
				// rename variables in left/right graphs of morphs
				setAttributeVariable(m2.getSource(), from, to, m2.getAttrContext(), varsm2);
				setAttributeVariable(m2.getTarget(), from, to, m2.getAttrContext(), varsm2);
				// System.out.println("--------rename variables in
				// conditions--------");
				CondTuple conds = (CondTuple) m2.getAttrContext()
						.getConditions();
				renameVariableOfCondition(m2.getAttrContext(), conds, from, to);
			}
		}
	}

	public void renameVariableOfCondition(
			final AttrContext ac,
			CondTuple conds, 
			String from,
			String to) {
		// rename variables in conditions
		for (int j = 0; j < conds.getSize(); j++) {
			CondMember cm = conds.getCondMemberAt(j);
			Vector<String> v1 = cm.getAllVariables();
			if (v1.contains(from)) {				
				JexExpr oldExpr = (JexExpr) cm.getExpr();
//				System.out.println(cm.getExpr());
				// test output
				// System.out.println("ast: "+oldExpr.getAST());
				// System.out.println("Children of ast:
				// "+oldExpr.getAST().jjtGetNumChildren());
//				Vector<String> variables = new Vector<String>();
//				oldExpr.getAllVariables(variables);
				/*
				 * System.out.println("Variables of ast: "+variables.size());
				 * for(int ii=0; ii<variables.size(); ii++) {
				 * System.out.println("ast variable:
				 * "+(String)variables.get(ii)); }
				 */
				//
				findPrimaryAndReplace((SimpleNode) oldExpr.getAST(), from, to, ac,
						null);
//				System.out.println(cm.getExpr());
//				System.out.println("--------------------------");
			}
		}
	}

	public void renameVariableOfExpression(
			final AttrContext ac,
			ValueTuple value, String from,
			String to) {
		// rename variables in conditions
		for (int j = 0; j < value.getSize(); j++) {
			ValueMember vm = value.getValueMemberAt(j);
			Vector<String> v1 = vm.getAllVariableNamesOfExpression();
			if (v1.contains(from)) {				
				JexExpr oldExpr = (JexExpr) vm.getExpr();
				// test output
				// System.out.println("ast: "+oldExpr.getAST());
				// System.out.println("Children of ast:
				// "+oldExpr.getAST().jjtGetNumChildren());
//				Vector<String> variables = new Vector<String>();
//				oldExpr.getAllVariables(variables);
				/*
				 * System.out.println("Variables of ast: "+variables.size());
				 * for(int ii=0; ii<variables.size(); ii++) {
				 * System.out.println("ast variable:
				 * "+(String)variables.get(ii)); }
				 */
				//
				findPrimaryAndReplace((SimpleNode) oldExpr.getAST(), from, to, ac,
						null);
				// System.out.println("--------------------------");
			}
		}
	}
	
	protected void setAttributeVariable(
			final Graph g, 
			final String from, 
			final String to,
			final AttrContext ac,
			final VarTuple vars) {
		// System.out.println("OrdinaryMorphism.setAttributeVariable ...");
		Iterator<?> e = g.getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject obj = (GraphObject) e.next();
			if (obj.getAttribute() == null)
				continue;
			ValueTuple fromObj = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < fromObj.getSize(); i++) {
				ValueMember fromVM = fromObj.getValueMemberAt(i);
				if (fromVM.isSet()) {
					if (fromVM.getExpr().isVariable()) {
						if (fromVM.getExprAsText().equals(from)
								&& (vars.getVarMemberAt(to) != null)) {
							fromVM.setExprAsText(to);
							if (!fromVM.isTransient())
								fromVM.setTransient(vars.getVarMemberAt(to).isTransient()); // NEW
//							System.out
//									.println(">>>>>BaseFactory::  set Attribute expr (is Variable) :  "
//											+ from + "  to   " + to);
						}
					} else if (fromVM.getExpr().isComplex()) {
						// System.out.println("\n--------- set Attribute (is
						// Complex) : "+fromVM.getName()+" =
						// "+fromVM.getExprAsText());
//						VarMember toVM = vars.getVarMemberAt(to);
						// System.out.println("(to) VarMember :
						// "+toVM.getName()+" "+toVM);
						JexExpr oldExpr = (JexExpr) fromVM.getExpr();
						// System.out.println("ast: "+oldExpr.getAST());
						// System.out.println("Children of ast:
						// "+oldExpr.getAST().jjtGetNumChildren());
						Vector<String> variables = new Vector<String>();
						oldExpr.getAllVariables(variables);
						/*
						 * System.out.println("Variables of ast:
						 * "+variables.size()); for(int ii=0; ii<variables.size();
						 * ii++) { System.out.println("ast variable:
						 * "+(String)variables.get(ii)); }
						 */
						findPrimaryAndReplace((SimpleNode) oldExpr.getAST(),
								from, to, ac, vars);
						fromVM.setTransient(true); // NEW
						// System.out.println("------------------------------");
					}
				}
			}
		}
		e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject obj = (GraphObject) e.next();
			if (obj.getAttribute() == null)
				continue;
			ValueTuple fromObj = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < fromObj.getSize(); i++) {
				ValueMember fromVM = fromObj.getValueMemberAt(i);
				if (fromVM.isSet()) {
					if (fromVM.getExpr().isVariable()) {
						if (fromVM.getExprAsText().equals(from)
								&& (vars.getVarMemberAt(to) != null)) {
							fromVM.setExprAsText(to);
//							System.out
//									.println(">>>>>BaseFactory::  set Attribute expr (is Variable) :  "
//											+ from + "  to   " + to);
						}
					} else if (fromVM.getExpr().isComplex()) {
						// System.out.println("\n--------- set Attribute (is
						// Complex) : "+fromVM.getName()+" =
						// "+fromVM.getExprAsText());
//						VarMember toVM = vars.getVarMemberAt(to);
						// System.out.println("(to) VarMember :
						// "+toVM.getName()+" "+toVM);
						JexExpr oldExpr = (JexExpr) fromVM.getExpr();
						// System.out.println("ast: "+oldExpr.getAST());
						// System.out.println("Children of ast:
						// "+oldExpr.getAST().jjtGetNumChildren());
						Vector<String> variables = new Vector<String>();
						oldExpr.getAllVariables(variables);
						/*
						 * System.out.println("Variables of ast:
						 * "+variables.size()); for(int ii=0; ii<variables.size();
						 * ii++) { System.out.println("ast variable:
						 * "+(String)variables.get(ii)); }
						 */
						findPrimaryAndReplace((SimpleNode) oldExpr.getAST(),
								from, to, ac, vars);
						// System.out.println("------------------------------");
					}
				}
			}
		}
	}

	private void findPrimaryAndReplace(
			final SimpleNode node, 
			final String from, 
			final String to,
			final AttrContext ac,
			final VarTuple vars) {
		// System.out.println("OrdinaryMorphism.findPrimaryAndChange: in  "+node);
		SymbolTable symbs = ac;
		
		for (int j = 0; j < node.jjtGetNumChildren(); j++) {
			SimpleNode n = (SimpleNode) node.jjtGetChild(j);
			// System.out.println(j+" Child of ast: "+n+" is "+n.getString());
			if (n instanceof ASTPrimaryExpression
					|| n instanceof ASTId) {
				// System.out.println("OrdinaryMorphism.findPrimaryAndChange:
				// ASTPrimaryExpression: "+n+" NumChildren: " +n.jjtGetNumChildren());
				
				/*
				for (int j1 = 0; j1 < n.jjtGetNumChildren(); j1++) {
					SimpleNode n1 = (SimpleNode) n.jjtGetChild(j1);
					if (n1 instanceof ASTExpression) {
						findPrimaryAndReplace(n1, from, to, ac, vars);
					}
				}
				 */
				
//				String ident = ((ASTPrimaryExpression) n).getIdentifier();
				// System.out.println("Identifier: "+ ident+" "+
				// ((ASTPrimaryExpression) n).getString());
				if (n.getString().equals(from)) {
					// System.out.println("from is found: getString()=
					// "+n.getString()+" toString()= "+n.toString()+"
					// hasStringType()= "+n.hasStringType()+" getSymbolTable()=
					// "+n.getSymbolTable());
					
//					SymbolTable symbs = SimpleNode.getSymbolTable();
					
					// System.out.println("SymbolTable: "+from+" type=
					// "+symbs.getType(from)+" expr= "+ symbs.getExpr(from));
					// System.out.println("SymbolTable: "+to+" type=
					// "+symbs.getType(to)+" expr= "+ symbs.getExpr(to));
					boolean to_found = false;
					ContextView context = (ContextView) symbs;
					VarTuple vt = (VarTuple) context.getVariables();
					for (int i = 0; i < vt.getSize(); i++) {
						VarMember vm = vt.getVarMemberAt(i);
						// System.out.println(vm.getName()+" "+vm);
						if (vm.getName().equals(to)) {
							to_found = true;
							// System.out.println(to+" exists in SymbolTable ::
							// "+vm.getName()+" "+vm);
							HandlerType t = vm.getDeclaration().getType();
							try {
								HandlerExpr expression = vm.getHandler()
										.newHandlerExpr(t, to);
								// System.out.println(expression.getAST());
								SimpleNode test = (SimpleNode) expression
										.getAST().jjtGetChild(0);
								// System.out.println(test);
								node.replaceChild(n, test);
								// System.out.println("child replaced:
								// getString()=
								// "+node.jjtGetChild(0).getString()+"
								// "+node.jjtGetChild(0).toString());
							} catch (AttrHandlerException ex) {}
						}
					}
					if (!to_found) {
						// System.out.println("Something wrong: "+to+" NOT FOUND
						// in SymbolTable! Try to replace if variable exists in
						// VarTuple.");
						for (int i = 0; i < vars.getSize(); i++) {
							VarMember vm = vars.getVarMemberAt(i);
							// System.out.println(vm.getName()+" "+vm);
							if (vm.getName().equals(to)) {
								to_found = true;
								// System.out.println(to+" exists in vars
								// "+vm.getName()+" "+vm);
								HandlerType t = vm.getDeclaration().getType();
								try {
									HandlerExpr expression = vm.getHandler()
											.newHandlerExpr(t, to);
									// System.out.println(expression.getAST());
									SimpleNode test = (SimpleNode) expression
											.getAST().jjtGetChild(0);
									// System.out.println(test);
									node.replaceChild(n, test);
									// System.out.println("child replaced:
									// getString()=
									// "+node.jjtGetChild(0).getString()+"
									// "+node.jjtGetChild(0).toString());
								} catch (AttrHandlerException ex) {}
							}
						}
					}
				} else if (n.getString().contains(from)) {
//					 System.out.println("Take Child of ast:  "+n);
					 findPrimaryAndReplace(n, from, to, ac, vars);
				}
			} else {
				// System.out.println("Take Child of ast: "+n);
				findPrimaryAndReplace(n, from, to, ac, vars);
			}
		}
	}

	
	
	/*  Graph overlappings */
	
	/**
	 * Computes possible overlappings with defined size of inclusion of this and
	 * another graph g. The return value is an enumeration of pairs of morphisms.
	 * Each pair consists of a morphism from thisGraph to the overlap graph and a
	 * morphism from the other graph to the overlap graph.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> overlappingSet(
			final Graph thisGraph,
			final Graph g, 
			final int sizeOfInclusion,
			final boolean union,
			final boolean withIsomorphic) {
		
		final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> oSet = new Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		Vector<OrdinaryMorphism> subs = new Vector<OrdinaryMorphism>();
		final Enumeration<GraphObject> itsGOs = thisGraph.getElements();
		final Vector<GraphObject> itsGOSet = new Vector<GraphObject>();
		int size = 0;
		int minGraphSize;

		if (union)
			minGraphSize = 0;
		else
			minGraphSize = 1;
		while (itsGOs.hasMoreElements()) {
			itsGOSet.addElement(itsGOs.nextElement());
			size++;
		}

		if (sizeOfInclusion == -1) {
			// compute all possible inclusions
			for (int i = minGraphSize; i <= size; i++) {
				subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph, i, itsGOSet,
						subs, withIsomorphic, null, false);
			}
		} else if (sizeOfInclusion >= minGraphSize) {
			subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph, sizeOfInclusion,
					itsGOSet, subs, withIsomorphic, null, false);
		} else
			return oSet.elements();

		makeOverlappingPairs(thisGraph, g, subs, oSet);
		subs.clear();
		
		return (oSet.elements());
	}

	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> overlappingSet(
			final Graph thisGraph,
			final Graph g, 
			final int sizeOfInclusion,
			final Hashtable<Object, Object> objectMap,
			final boolean union,
			final boolean withIsomorphic) {
		
		final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> oSet = new Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		Vector<OrdinaryMorphism> subs = new Vector<OrdinaryMorphism>();
		final Enumeration<GraphObject> itsGOs = thisGraph.getElements();
		final Vector<GraphObject> itsGOSet = new Vector<GraphObject>();
		int size = 0;
		int minGraphSize;
		
		if (union)
			minGraphSize = 0;
		else
			minGraphSize = 1;
		while (itsGOs.hasMoreElements()) {
			itsGOSet.addElement(itsGOs.nextElement());
			size++;
		}

		List<Object> requiredInsideSubgraphs = null;
		if (objectMap != null && !objectMap.isEmpty())
			requiredInsideSubgraphs = new Vector<Object>(objectMap.keySet());

		if (sizeOfInclusion == -1) {
			// compute all possible inclusions
			for (int i = minGraphSize; i <= size; i++) {
				subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph, i, itsGOSet,
						subs, withIsomorphic, requiredInsideSubgraphs, true);
			}
		} else if (sizeOfInclusion >= minGraphSize) {
			subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph, sizeOfInclusion,
					itsGOSet, subs, withIsomorphic, requiredInsideSubgraphs, true);
		} else
			return oSet.elements();

		makeOverlappingPairs(thisGraph, g, subs, oSet, objectMap);
		subs.clear();
		
		return (oSet.elements());
	}
	
	
	private void makeOverlappingPairs(
			final Graph thisGraph,
			final Graph g,
			final Vector<OrdinaryMorphism> subs, 
			final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> oSet,
			final Hashtable<Object, Object> objectMap) {
		
		if (objectMap == null || objectMap.isEmpty()) {
			makeOverlappingPairs(thisGraph, g, subs, oSet);
			return;
		}
		
		// make mapping with respect to required object map		
		MorphCompletionStrategy strategy = (thisGraph.getTypeSet().hasInheritance())?
					(new Completion_InheritCSP()): (new Completion_InjCSP());

		for (int i = 0; i < subs.size(); i++) {
			OrdinaryMorphism h = subs.elementAt(i);
			Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
			rulePair = (BaseFactory.theFactory()).constructIsomorphicRule(h, true, false);
			if (rulePair == null)
				continue;
			
//			Rule r = rulePair.first;
			Match match = (BaseFactory.theFactory()).createMatch(rulePair.first, g, true);
			match.setCompletionStrategy(strategy, true);
			
			while (match.nextCompletion()) {
				boolean allOfObjectFlow = true;
				int count = 0;
				Enumeration<GraphObject> dom = match.getDomain();
				while (dom.hasMoreElements()) {
					// obj corresponds to an object of L2 which is key of the objectMap
					final GraphObject obj = dom.nextElement();						
					if (rulePair.second.first.getInverseImage(obj).hasMoreElements()) {	
						// obj1 is equivalent to obj
						final GraphObject obj1 = rulePair.second.first.getInverseImage(obj).nextElement();
						// objL2 is the object of L2 which can be a key of the objectMap
						final GraphObject objL2 = h.getImage(obj1);							
						// obj2 is the value of the key (objL2) of the objectMap
						final GraphObject obj2 = (GraphObject)objectMap.get(objL2);
						if (obj2 != null) {	
							// objR1 is the object of R1 
							final GraphObject objR1 = match.getImage(obj);	
							if (obj2 == objR1) {
								count++;
							}
						} 
					} else {
						allOfObjectFlow = false; 
						break;
					}
				}				
				allOfObjectFlow = allOfObjectFlow && (count == objectMap.size());
				if (allOfObjectFlow && match.isValid(true)) {
					// constract the overlapping graph
					OrdinaryMorphism rStar = g.isomorphicCopy();
					if (rStar != null) {
						Match m = (BaseFactory.theFactory()).createMatch(
										rulePair.first, rStar.getImage(), true);
						if (m.doCompose(match, rStar)) {
							// try execute step; it may throw an exception
							// when multiplicity constraint failed
							try {
								// Variables in graph are allowed
								OrdinaryMorphism ms = (OrdinaryMorphism) TestStep.execute(m, true);
								if (ms != null) {
									OrdinaryMorphism mStar = rulePair.second.second.compose(ms);
									if (mStar != null) {
										// make result overlapping pair
										final Pair<OrdinaryMorphism, OrdinaryMorphism> 
										p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
												mStar, rStar);
										oSet.addElement(p);
									}
									ms.dispose();
									ms = null;
								} else {
									rStar.dispose();
									rStar = null;
								}
							} catch (TypeException e) {}
						} else {
							rStar.dispose();
							rStar = null;
						}
						m.dispose();
						m = null;
					}
				}
			}
			
			// dispose helpers
			match.dispose();
			rulePair.second.first.dispose();
			rulePair.second.second.dispose();
			rulePair.first.disposeSuper();
			h.dispose(true, false);
			rulePair.first = null;
			h = null;
			rulePair.second.first = null;
			rulePair.second.second = null;
			rulePair = null;
			// System.gc();
		}
	}

	private void makeOverlappingPairs(
			final Graph thisGraph,
			final Graph g,
			final Vector<OrdinaryMorphism> subs, 
			final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> oSet) {
		
		MorphCompletionStrategy strategy = (thisGraph.getTypeSet().hasInheritance())?
				(new Completion_InheritCSP()): (new Completion_InjCSP());
				
		for (int i = 0; i < subs.size(); i++) {
			OrdinaryMorphism h = subs.elementAt(i);			
			makeOverlappingPair(thisGraph, g, h, strategy, oSet);			
			h.dispose(true, false);
			h = null;
		}
	}
	
	private void makeOverlappingPair(
			final Graph thisGraph,
			final Graph g,
			final OrdinaryMorphism inclusion, 
			final MorphCompletionStrategy strategy,
			final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> oSet) {
		
		Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		rulePair = (BaseFactory.theFactory()).constructIsomorphicRule(inclusion, true, false);
		if (rulePair == null)
			return;
		
//		Rule r = rulePair.first;
			
		Match match = (BaseFactory.theFactory()).createMatch(rulePair.first, g, true);				
		match.setCompletionStrategy(strategy, true);
		if (match.getCompletionStrategy() instanceof Completion_InheritCSP) {
			((Completion_InheritCSP)match.getCompletionStrategy()).initialize(match);
			match.getTarget().getTypeObjectsMap().clear();
			match.getCompletionStrategy().resetTypeMap(g);
		}
		
		boolean nextMatch = true;	
		while (nextMatch) {
			nextMatch = match.nextCompletion();	
			if (nextMatch && match.isValid(true)) {	
				OrdinaryMorphism rStar = g.isomorphicCopy();
				if (rStar == null)
					break;
				
				Match m = (BaseFactory.theFactory()).createMatch(rulePair.first, rStar.getTarget(), true);
				boolean compose = false;
				if (match.getCompletionStrategy() instanceof Completion_InheritCSP) {
					compose = m.doComposeInherit(match, rStar);
				} else {
					compose = m.doCompose(match, rStar);
				}
				if (compose) {
					if (!(match.getCompletionStrategy() instanceof Completion_InheritCSP)
							|| BaseFactory.theFactory().replaceParentByChild(rulePair.first, m, rStar)) {
	//					m.adaptAttrContextValues(match.getAttrContext());
						// try execute step; it may throw an exception
						// when multiplicity constraint failed
						try {
							// Variables in graph are allowed
							OrdinaryMorphism ms = (OrdinaryMorphism) TestStep.execute(m, true);
							if (ms != null) {
								OrdinaryMorphism mStar = rulePair.second.second.compose(ms);
								if (mStar != null) {
									final Pair<OrdinaryMorphism, OrdinaryMorphism> 
									p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
												mStar, rStar);
									oSet.addElement(p);
								} else {
									rStar.dispose();
									rStar = null;
								}
								ms.dispose();
								ms = null;
							}
						} catch (TypeException e) {}
					}
				} else {
					rStar.dispose();
					rStar = null;
				}
				m.dispose();
				m = null;
			} 
		}
			
		// dispose helpers
		match.dispose();
		rulePair.second.first.dispose();
		rulePair.second.second.dispose();
		rulePair.first.disposeSuper();
		rulePair.second.first = null;
		rulePair.second.second = null;
		rulePair.first = null;
		rulePair = null;
	}
	
	private void makeOverlappingPairByPredefinedIntersection(
			final Graph thisGraph,
			final Graph g,
			final Hashtable<Object, Object> intersection, // thisGraph -> g
			final OrdinaryMorphism inclusion, // subgraph -> thisGraph
			final MorphCompletionStrategy strategy,
			final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> oSet) {
		
//		System.out.println("===) BaseFactory.makeOverlappingPair");
		Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		rulePair = (BaseFactory.theFactory()).constructIsomorphicRule(inclusion, true, false);
		if (rulePair == null)
			return;
		
//		Rule r = rulePair.first;
			
		Match match = (BaseFactory.theFactory()).createMatch(rulePair.first, g, true);				
		match.setCompletionStrategy(strategy, true);
			
		// set match mapping:  first of nodes
		boolean mappingOK = true;
		Enumeration<Object> keys = intersection.keys();
		while (keys.hasMoreElements() && mappingOK) {
			Object thisGraph_obj = keys.nextElement();
			if (thisGraph_obj instanceof Node) {
				Object g_obj = intersection.get(thisGraph_obj);
				if (inclusion.getInverseImage((GraphObject) thisGraph_obj).hasMoreElements()) {
					GraphObject sub_obj = inclusion.getInverseImage((GraphObject) thisGraph_obj).nextElement();
					GraphObject lhs_obj = rulePair.second.first.getImage(sub_obj);
					if (lhs_obj != null && g_obj != null) {
						try {
//							match.addMapping(lhs_obj, (GraphObject)g_obj);
							match.addChild2ParentMapping(lhs_obj, (GraphObject)g_obj);
						} catch (BadMappingException ex) { 
							mappingOK = false;
						}
					} else {
						mappingOK = false;
					}		
				} else {
					mappingOK = false;
				}
			}
		}
		// set match mapping:  now of edges
		keys = intersection.keys();
		while (keys.hasMoreElements() && mappingOK) {
			Object thisGraph_obj = keys.nextElement();
			if (thisGraph_obj instanceof Arc) {
				Object g_obj = intersection.get(thisGraph_obj);
				if (inclusion.getInverseImage((GraphObject) thisGraph_obj).hasMoreElements()) {
					GraphObject sub_obj = inclusion.getInverseImage((GraphObject) thisGraph_obj).nextElement();
					GraphObject lhs_obj = rulePair.second.first.getImage(sub_obj);
					if (lhs_obj != null && g_obj != null) {
						try {
							match.addMapping(lhs_obj, (GraphObject)g_obj);
//							match.addChild2ParentMapping(lhs_obj, (GraphObject)g_obj);
						} catch (BadMappingException ex) {
							mappingOK = false;
						}
					} else {
						mappingOK = false;
					}		
				} else {
					mappingOK = false;
				}
			}
		}
		
		if (match.isTotal()	&& match.isValid(true)) {						
			OrdinaryMorphism rStar = g.isomorphicCopy();
			if (rStar != null) {
				Match m = (BaseFactory.theFactory()).createMatch(rulePair.first, rStar.getTarget(), true);
				boolean compose = false;
				if (match.getCompletionStrategy() instanceof Completion_InheritCSP) {
					compose = m.doComposeInherit(match, rStar);
				} else {
					compose = m.doCompose(match, rStar);
				}
				if (compose) {	
					if (!(match.getCompletionStrategy() instanceof Completion_InheritCSP)
							|| BaseFactory.theFactory().replaceParentByChild(rulePair.first, m, rStar)) {
	//				m.adaptAttrContextValues(match.getAttrContext());
						// try execute step; it may throw an exception
						// when multiplicity constraint failed
						try {
							// Variables in graph are allowed
							OrdinaryMorphism ms = (OrdinaryMorphism) TestStep.execute(m, true);
							if (ms != null) {
								OrdinaryMorphism mStar = rulePair.second.second.compose(ms);
								if (mStar != null) {
									final Pair<OrdinaryMorphism, OrdinaryMorphism> 
									p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
													mStar, rStar);
									oSet.addElement(p);
								}
								ms.dispose();
								ms = null;
							}
						} catch (TypeException e) {}
					}
				} else {
					rStar.dispose();
					rStar = null;
				}
			}
		}
			
		// dispose helpers
		match.dispose();
		rulePair.second.first.dispose();
		rulePair.second.second.dispose();
		rulePair.first.disposeSuper();	
		rulePair.second.first = null;
		rulePair.second.second = null;
		rulePair.first = null;
		rulePair = null;
	}
	
	/**
	 * Computes all possible overlappings (withoutdisjoint union) of this and
	 * another graph g. The return value is an enumeration of pairs of morphisms.
	 * Each pair consists of a morphism from thisGraph to the overlap graph and a
	 * morphism from the other graph to the overlap graph.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> overlapSet(
			final Graph thisGraph,
			final Graph g, 
			final boolean withIsomorphic) {
		return overlappingSet(thisGraph, g, -1, false, withIsomorphic);
	}

	/**
	 * Compute all possible overlappings of this and another graph g. The return
	 * value is an eneration of pairs of morphisms. Each pair consists of a
	 * morphism from thisGraph to the overlap graph and a morphism from the other graph
	 * to the overlap graph.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> overlapSet(
			final Graph thisGraph,
			final Graph g, 
			final boolean union,
			final boolean withIsomorphic) {
		return overlappingSet(thisGraph, g, -1, union, withIsomorphic);
	}

	
	/**
	 * Computes an overlapping set.
	 * 
	 * @param g
	 *            The graph to overlap with
	 * @param withIsomorphic
	 * 				true if isomorphic overlappings should be preserved,
	 * 				otherwise only one of isomorphic overlappings preserved,
	 * 				the other will be deleted
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			final Graph thisGraph,
			final Graph g, final boolean withIsomorphic) {
		return getOverlappings(thisGraph, g, false, withIsomorphic);
	}

	/**
	 * Computes an overlapping set.
	 * 
	 * @param g
	 *            The graph to overlap with
	 * @param disjunion
	 *            true if disjoint union
	 * @param withIsomorphic
	 * 				true if isomorphic overlappings should be preserved,
	 * 				otherwise only one of isomorphic overlappings preserved,
	 * 				the other will be deleted
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			final Graph thisGraph,
			final Graph g, final boolean disjunion,
			final boolean withIsomorphic) {
		return overlapSet(thisGraph, g, disjunion, withIsomorphic);
	}

	/**
	 * Computes an overlapping set.
	 * 
	 * @param g
	 *            The graph to overlap with
	 * @param sizeOfInclusions
	 * 				size of elements of the overlapping part
	 * @param withIsomorphic
	 * 				true if isomorphic overlappings should be preserved,
	 * 				otherwise only one of isomorphic overlappings preserved,
	 * 				the other will be deleted
	 */	
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			 final Graph thisGraph,
			 final Graph g, 
			 final int sizeOfInclusion,
			 final boolean withIsomorphic) {
		return overlappingSet(thisGraph, g, sizeOfInclusion, false, withIsomorphic);
	}

	/**
	 * Computes an overlapping set.
	 * 
	 * @param g
	 *            The graph to overlap with
	 * @param sizeOfInclusions
	 * 				size of elements of the overlapping part
	 * @param disjunion
	 *            true if disjoint union
	 * @param withIsomorphic
	 * 				true if isomorphic overlappings should be preserved,
	 * 				otherwise only one of isomorphic overlappings preserved,
	 * 				the other will be deleted
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			final Graph thisGraph,
			final Graph g, 
			int sizeOfInclusions,
			boolean disjunion, 
			boolean withIsomorphic) {
		return overlappingSet(thisGraph, g, sizeOfInclusions, disjunion, withIsomorphic);
	}

	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			final Graph thisGraph,
			final Graph g, 
			final int sizeOfInclusion,
			final Hashtable<Object, Object> objectMap,
			boolean disjunion, 
			boolean withIsomorphic) {
		return overlappingSet(thisGraph, g, sizeOfInclusion, objectMap, disjunion, withIsomorphic);
	}
	
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappingByPredefinedIntersection(
			final Graph thisGraph,
			final Graph g, 
			final Hashtable<Object, Object> intersection) {
		
		if (intersection != null && !intersection.isEmpty()) {
			
			final Set<Object> intersectionOfThisGraph = intersection.keySet();
			List<GraphObject> goSet  = new Vector<GraphObject>();
			Iterator<Object> keys = intersectionOfThisGraph.iterator();
			while (keys.hasNext()) {
				Object obj =  keys.next();
				if (obj instanceof Node)
					goSet.add(0, (GraphObject) obj);
				else
					goSet.add((GraphObject) obj);
			}
			
			OrdinaryMorphism inclusion = this.makeInclusion(thisGraph, goSet);
			if (inclusion != null) {
//				Completion_InjCSP strategy = new Completion_InjCSP();
				MorphCompletionStrategy strategy = null;
				if (thisGraph.getTypeSet().hasInheritance())
					strategy = new Completion_InheritCSP();
				else
					strategy = new Completion_InjCSP();
				
				final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
				oSet = new Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>>(1);
				
				makeOverlappingPairByPredefinedIntersection(thisGraph, g, intersection, inclusion, strategy, oSet);
				if (!oSet.isEmpty())
					return oSet.elements();
			}
		}
		return null;
	}
	
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappingByPartialPredefinedIntersection(
			final Graph thisGraph,
			final Graph g, 
			final List<Object> requiredInsideSubgraphs,
			final Hashtable<Object, Object> partialIntersection,
			boolean onlyRequiredObjectsInsideSubgraphs) {
		
		final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		oSet = new Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		Vector<OrdinaryMorphism> subs = new Vector<OrdinaryMorphism>();
		final Vector<GraphObject> itsGOSet = new Vector<GraphObject>();
		final Enumeration<GraphObject> itsGOs = thisGraph.getElements();
		int size = 0;
		
		while (itsGOs.hasMoreElements()) {
			itsGOSet.addElement(itsGOs.nextElement());
		}

		size = (itsGOSet.size() <= g.getSize())? itsGOSet.size() : g.getSize();
		int mins = (requiredInsideSubgraphs.size()>1)? requiredInsideSubgraphs.size(): 1;	
		// compute all possible inclusions
		for (int i = mins; i <= size; i++) {
			subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph, i, itsGOSet,
						subs, false, requiredInsideSubgraphs, onlyRequiredObjectsInsideSubgraphs);
		}
				
		makeOverlappingPairs(thisGraph, g, subs, oSet, partialIntersection);
		
		
		subs.clear();
		
		return (oSet.elements());
	}
	
	
	/**
	 * Returns a set of OrdinaryMorphism with
	 * the source graph is a subgraph of the given Graph <code>thisGraph</code> 
	 * and the target graph is the given Graph <code>thisGraph</code>.
	 */
	public Vector<OrdinaryMorphism> generateAllSubgraphs(
			final Graph thisGraph,
			int sizeOfInclusions,
			boolean union, 
			boolean withIsomorphic) {
		
		Vector<OrdinaryMorphism> subs = new Vector<OrdinaryMorphism>(0);
		Enumeration<GraphObject> itsGOs = thisGraph.getElements();
		Vector<GraphObject> itsGOSet = new Vector<GraphObject>();
		int size = 0;
		int minGraphSize;

		if (union)
			minGraphSize = 0;
		else
			minGraphSize = 1;
		while (itsGOs.hasMoreElements()) {
			itsGOSet.addElement(itsGOs.nextElement());
			size++;
		}

		if (sizeOfInclusions == -1) {
			// compute all possible inclusions
			for (int i = minGraphSize; i <= size; i++) {
				subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph,
						i, itsGOSet,
						subs, withIsomorphic, null, false);
			}
		} else if (sizeOfInclusions >= minGraphSize) {
			subs = generateAllSubgraphsWithInclusionsOfSize(thisGraph,
					sizeOfInclusions,
					itsGOSet, subs, withIsomorphic, null, false);
		}
		// System.out.println("Graph.generateAllSubgraphs:: (subg -> g):
		// "+subs.size());
		return subs;
	}

	/**
	 * Returns a set of OrdinaryMorphism with
	 * the source graph is a subgraph of the given Graph <code>thisGraph</code> 
	 * and the target graph is the given Graph <code>thisGraph</code>.<br>
	 * Additionally, the objects of the given List <code>requiredObjectsInsideSubgraphs</code>
	 * contained in all subgraphs.
	 */
	public Vector<OrdinaryMorphism> generateAllSubgraphsWithInclusionsOfSize(
			final Graph thisGraph,
			int i, 
			Vector<GraphObject> itsGOSet,
			Vector<OrdinaryMorphism> inclusions,
			boolean withIsomorphic,
			final List<Object> requiredObjectsInsideSubgraphs,
			boolean onlyRequiredObjectsInsideSubgraphs) {
		
		if (i == 0) {
			return putInclusion(thisGraph, new Vector<GraphObject>(), inclusions);
		}
		
		Vector<Integer> select = new Vector<Integer>();
		if (i <= itsGOSet.size()) {
			for (int j = 1; j <= i; j++) {
				select.addElement(Integer.valueOf(j - 1));
			}
			computeSelection(thisGraph, 1, itsGOSet, select, inclusions, 
					requiredObjectsInsideSubgraphs, onlyRequiredObjectsInsideSubgraphs);
		}
		if (!withIsomorphic) {
			checkIsomorphicInclusions(inclusions);
		}
		return inclusions;
	}

	private Vector<OrdinaryMorphism> computeSelection(
			final Graph thisGraph,
			int s,
			Vector<GraphObject> itsGOSet, 
			Vector<Integer> select,
			Vector<OrdinaryMorphism> inclusions,
			final List<Object> requiredObjectsInsideSubgraph,
			boolean onlyRequiredObjectsInsideSubgraphs) {
		
		int max = itsGOSet.size();
		int selSize = select.size();
		int v;
		Vector<GraphObject> goSet;

		if (s <= selSize && s >= 1) {
			try {
				v = select.elementAt(s - 1).intValue();
				while (v < max - selSize + s) {
//					int tmp = max - selSize + s;
					inclusions = computeSelection(thisGraph, s + 1, itsGOSet, select,
							inclusions, requiredObjectsInsideSubgraph, onlyRequiredObjectsInsideSubgraphs);
					if (s == selSize) {
						goSet = makeGraphObjectSet(select, itsGOSet, requiredObjectsInsideSubgraph, onlyRequiredObjectsInsideSubgraphs);
						if (!goSet.isEmpty() && thisGraph.isGraph(goSet)) {
							inclusions = putInclusion(thisGraph, goSet, inclusions);
						}
					}
					select.setElementAt(Integer.valueOf(v + 1), s - 1);
					v = select.elementAt(s - 1).intValue();
				}
				if (s > 1) {
					v = select.elementAt(s - 2).intValue();
					if (v < max - selSize + s + 1) {
						select.setElementAt(Integer.valueOf(v + 1), s - 2);
						for (int j = 1; j <= selSize - s + 1; j++) {
							select.setElementAt(Integer.valueOf(v + 1 + j), s + j
									- 2);
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return (inclusions);
	}

	private Vector<GraphObject> makeGraphObjectSet(
			Vector<Integer> select,
			Vector<GraphObject> itsGOSet,
			final List<Object> requiredObjectsInsideSubgraph,
			boolean onlyRequiredObjectsInsideSubgraphs) {
		
		final Vector<GraphObject> tmp = new Vector<GraphObject>();
		
		if (requiredObjectsInsideSubgraph == null 
				|| requiredObjectsInsideSubgraph.isEmpty()) {
			for (int i = 0; i < select.size(); i++) {
				int v = select.elementAt(i).intValue();
				tmp.addElement(itsGOSet.elementAt(v));
			}
		}
		else if (onlyRequiredObjectsInsideSubgraphs
				 && (select.size() == requiredObjectsInsideSubgraph.size())) {
			// all required objects must be in subgraph but not more
			int found = 0;			
			for (int i = 0; i < select.size(); i++) {
				int v = select.elementAt(i).intValue();
				GraphObject go = itsGOSet.elementAt(v);
				if (requiredObjectsInsideSubgraph.contains(go)) {
					found++;
					tmp.addElement(go);
				}
			}
			if (found != requiredObjectsInsideSubgraph.size()) {
				tmp.clear();
			}
		} else { // at least as required
			int found = 0;			
			for (int i = 0; i < select.size(); i++) {
				int v = select.elementAt(i).intValue();
				GraphObject go = itsGOSet.elementAt(v);
				if (requiredObjectsInsideSubgraph.contains(go)) {
					found++;
				}
				tmp.addElement(go);
			}
			if (found < requiredObjectsInsideSubgraph.size()) {
				tmp.clear();
			}
		}
		
		return tmp;
	}

	private Vector<OrdinaryMorphism> putInclusion(
			final Graph thisGraph,
			final Vector<GraphObject> goSet,
			final Vector<OrdinaryMorphism> inclusions) {
		Node source = null;
		Node target = null;
		Graph subGraph = BaseFactory.theFactory().createGraph(thisGraph.getTypeSet());
		OrdinaryMorphism inclusion = (BaseFactory.theFactory()).createMorphism(
				subGraph, thisGraph);

		for (int i = 1; i <= goSet.size(); i++) {
			GraphObject go = goSet.elementAt(i - 1);
			if (go.isNode()) {
				Node n = null;
				try {
					n = subGraph.copyNode((Node) go);
					n.setContextUsage(((Node) go).getContextUsage());
				} catch (TypeException e) {
					// while loading the type check should be disabled,
					// so this Exception should never be thrown
					e.printStackTrace();
				}
				try {
					inclusion.addMapping(n, go);
				} catch (BadMappingException e) {
					e.printStackTrace();
				}
			} 
		}
		for (int i = 1; i <= goSet.size(); i++) {
			GraphObject go = goSet.elementAt(i - 1);
			if (go.isArc()) {
				Enumeration<GraphObject> sources = inclusion.getInverseImage(((Arc) go)
						.getSource());

				if (sources.hasMoreElements()) {
					source = (Node) sources.nextElement();
				
					Enumeration<GraphObject> targets = inclusion.getInverseImage(((Arc) go)
							.getTarget());
	
					if (targets.hasMoreElements()) {
						target = (Node) targets.nextElement();					
	
						Arc a = null;
						try {
							a = subGraph.copyArc((Arc) go, source, target);
							a.setContextUsage(((Arc) go).getContextUsage());
						} catch (TypeException e) {
							// during load process the type check should be disabled,
							// so this Exception should never be thrown
							System.out.println(e);
//							e.printStackTrace();
						}
						try {
							inclusion.addMapping(a, go);
						} catch (BadMappingException e) {
							System.out.println(e);
//							e.printStackTrace();
						}
					}
				}
			}
		}
		inclusions.addElement(inclusion);
		return (inclusions);
	}

	private OrdinaryMorphism makeInclusion(
			final Graph thisGraph,
			final List<GraphObject> goSet) {
		Node source = null;
		Node target = null;
		Graph subGraph = BaseFactory.theFactory().createGraph(thisGraph.getTypeSet());
		OrdinaryMorphism inclusion = (BaseFactory.theFactory()).createMorphism(
				subGraph, thisGraph);

		for (int i = 0; i < goSet.size(); i++) {
			GraphObject go = goSet.get(i);
			if (go.isNode()) {
				Node n = null;
				try {
					n = subGraph.copyNode((Node) go);
					n.setContextUsage(((Node) go).getContextUsage());
				} catch (TypeException e) {
					// during load process the type check should be disabled,
					// so this Exception should never be thrown
					e.printStackTrace();
				}
				try {
					inclusion.addMapping(n, go);
				} catch (BadMappingException e) {
					e.printStackTrace();
				}
			} 
		}
		for (int i = 0; i < goSet.size(); i++) {
			GraphObject go = goSet.get(i);
			if (go.isArc()) {
				Enumeration<GraphObject> sources = inclusion.getInverseImage(((Arc) go)
						.getSource());

				if (sources.hasMoreElements()) {
					source = (Node) sources.nextElement();
				
					Enumeration<GraphObject> targets = inclusion.getInverseImage(((Arc) go)
							.getTarget());
	
					if (targets.hasMoreElements()) {
						target = (Node) targets.nextElement();
											
						Arc a = null;
						try {
							a = subGraph.copyArc((Arc) go, source, target);
							a.setContextUsage(((Arc) go).getContextUsage());
						} catch (TypeException e) {
							// while loading the type check should be disabled,
							// so this Exception should never be thrown
							System.out.println(e);
//							e.printStackTrace();
						}
						try {
							inclusion.addMapping(a, go);
						} catch (BadMappingException e) {
							System.out.println(e);
//							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return inclusion;
	}
	
	protected void checkIsomorphicInclusions(Vector<OrdinaryMorphism> inclusions) {
		int size = inclusions.size();
		for (int i = 0; i < size; i++) {
			OrdinaryMorphism inclusion = inclusions
					.elementAt(i);
			for (int j = i + 1; j < size; j++) {
				OrdinaryMorphism inc = inclusions
						.elementAt(j);
				if (inclusion.getSource().isIsomorphicTo(inc.getSource())) {
					inclusions.removeElement(inc);
					j--;
					size = inclusions.size();
					BaseFactory.theFactory().destroyMorphism(inc);
					inc = null;
				}
			}
		}
	}

	/**
	 * Here the given morphism <code>matchMorph</code> can contain mapping
	 * from a source node with a child type of an inheritance relation 
	 * to a target node with a parent type. 
	 * In this case this target (parent) node will be replaced by a copy
	 * of the child node.<br>
	 * The source graph of the <code>matchMorph</code> is the source graph 
	 * of the <code>ruleMorph</code>, 
	 * the target graph of the <code>matchMorph</code> is the target graph of 
	 * the <code>isoMorph</code>.
	 * The edges from / to the (parent) node are copied, too.
	 * 
	 * @param ruleMorph	
	 * @param matchMorph
	 * @param isoMorph
	 * @return true by success, otherwise false
	 */
	public boolean replaceParentByChild(
			final OrdinaryMorphism ruleMorph, 
			final OrdinaryMorphism matchMorph,
			final OrdinaryMorphism isoMorph) {	
		
		final Hashtable<Arc, Arc> img2origInArc = new Hashtable<Arc, Arc>();
		final Hashtable<Arc, Arc> img2origOutArc = new Hashtable<Arc, Arc>();
		final Hashtable<Arc, Arc> orig2img_isoMorph = new Hashtable<Arc, Arc>();
		
		final Iterator<Node> en = matchMorph.getSource().getNodesSet().iterator();
		while (en.hasNext()) {
			img2origInArc.clear();
			img2origOutArc.clear();
			orig2img_isoMorph.clear();
			
			Node go = en.next();
			Node img1 = (Node) ruleMorph.getImage(go);
			Node img2 = (Node) matchMorph.getImage(go);		
			if (img1 != null && img2 != null) {
				if (go.getType().isChildOf(img2.getType())) {
					Node child = go;
					Node parent = img2;
					// save original of parent and its edges
					Node orig_rStar = (Node) isoMorph.getInverseImage(parent).nextElement();
					Iterator<Arc> arcs = orig_rStar.getOutgoingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Arc img = (Arc)isoMorph.getImage(a);
						orig2img_isoMorph.put(a, img);
					}
					arcs = orig_rStar.getIncomingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Arc img = (Arc)isoMorph.getImage(a);
						orig2img_isoMorph.put(a, img);
					}
					isoMorph.removeMapping(orig_rStar);
					
//					 save parent's edges
					arcs = parent.getOutgoingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Enumeration<GraphObject> orig = matchMorph.getInverseImage(a);
						if (orig.hasMoreElements())
							img2origOutArc.put(a, (Arc)orig.nextElement());
						else
							img2origOutArc.put(a, a);
					}
					arcs = parent.getIncomingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Enumeration<GraphObject> orig = matchMorph.getInverseImage(a);
						if (orig.hasMoreElements())
							img2origInArc.put(a, (Arc)orig.nextElement());
						else
							img2origInArc.put(a, a);
					}
					final Type childT = child.getType();
					try {
						final Node childNode = matchMorph.getTarget().createNode(childT);
						// handle attributes  
						if (parent.getAttribute() != null) {
							for (int i=0; i<parent.getAttribute().getNumberOfEntries(); i++) {
								ValueMember pvm = (ValueMember) parent.getAttribute().getMemberAt(i);
								ValueMember vm = (ValueMember) childNode.getAttribute().getMemberAt(pvm.getName());
								if (pvm.isSet()) {
									vm.setExprAsText(pvm.getExprAsText());
									if (!vm.isTransient())
										vm.setTransient(pvm.isTransient());
								}
								else {
									vm.setExprAsText("vm_"+i);
									vm.setTransient(true);
								}
							}
						
							for (int i=0; i<childNode.getAttribute().getNumberOfEntries(); i++) {
								ValueMember vm = (ValueMember) childNode.getAttribute().getMemberAt(i);
								if (!vm.isSet()) {
									vm.setExprAsText("vm_"+i);
									vm.setTransient(true);
								}
							}
						}
					
						try {
							matchMorph.removeMapping(child);
							// reset mappings
							matchMorph.addMapping(child, childNode);
							Enumeration<Arc> keys = img2origOutArc.keys();
							while (keys.hasMoreElements()) {
								Arc img = keys.nextElement();
								Arc orig = img2origOutArc.get(img);
								// ADD MULTIPLICITY CHECK ???
								if (img.getSource() != img.getTarget()) {
									img.setSource(childNode);
									
									if (orig != img) {
										try {
											matchMorph.addMapping(orig, img);
										} catch (BadMappingException e) {
//											System.out.println("replaceParentByChild:BadMappingException:Arc "+e.getStackTrace());
											return false; 
										}
									}
								} 
							}
							keys = img2origInArc.keys();
							while (keys.hasMoreElements()) {
								Arc img = keys.nextElement();
								Arc orig = img2origInArc.get(img);
//								ADD MULTIPLICITY CHECK ???
								if (img.getSource() == img.getTarget()) 
									img.setSource(childNode);
								img.setTarget(childNode);
								
								if (orig != img) {
									try {
										matchMorph.addMapping(orig, img);
									} catch (BadMappingException e) {
//										System.out.println("replaceParentByChild:BadMappingException:Arc "+e.getStackTrace());
										return false; 
									}	
								}
							}
							
							if (parent.getNumberOfInOutArcs() == 0) {
								// reset mappings
								isoMorph.addMapping(orig_rStar, childNode);
								keys = orig2img_isoMorph.keys();
								while (keys.hasMoreElements()) {
									Arc a = keys.nextElement();
									Arc img = orig2img_isoMorph.get(a);
									try {
										isoMorph.addMapping(a, img);
									} catch (BadMappingException e) {
//										System.out.println("replaceParentByChild:BadMappingException:Arc "+e.getStackTrace());
										return false; 
									}
								}
//								System.out.println("ExcludePair.replaceParentByChild::  DONE: "+parent.getType().getName()+"  by  "+childNode.getType().getName());

								matchMorph.getTarget().destroyNode(parent, true, false);
							}
							else {
								return false;
							}
							
						} catch (BadMappingException ex1) {
//							System.out.println("replaceParentByChild:BadMappingException:Node "+ex1.getStackTrace());
							return false;
						}
					} catch (TypeException ex) {
//						System.out.println("replaceParentByChild:TypeException "+ex.getStackTrace());
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Here the given morphism <code>morph2</code> can contain mapping
	 * from a source node with a child type of an inheritance relation 
	 * to a target node with a parent type.
	 * The given morphism <code>morph1</code> maps
	 * a source node with a parent type of an inheritance relation 
	 * to the same target node of the morphism <code>morph2</code>.
	 * In this case this target node will be replaced by a copy
	 * of the child node. All in-/out-edges of the (parent) node are copied, too.<br>
	 * The target graph of the morphism <code>morph2</code> is the same target graph 
	 * of the <code>morph1</code>, the source graphs are different.
	 * 
	 * @param morph1
	 * @param morph2
	 * @return true by success, otherwise false
	 */
	public boolean replaceParentByChild(
			final OrdinaryMorphism morph1,
			final OrdinaryMorphism morph2) {	
		
		final Hashtable<Arc, Arc> img2origInArc = new Hashtable<Arc, Arc>();
		final Hashtable<Arc, Arc> img2origOutArc = new Hashtable<Arc, Arc>();
		final Hashtable<Arc, Arc> orig2img_isoMorph = new Hashtable<Arc, Arc>();
		
		final Iterator<Node> en = morph1.getSource().getNodesSet().iterator();
		while (en.hasNext()) {
			img2origInArc.clear();
			img2origOutArc.clear();
			orig2img_isoMorph.clear();
			
			Node go = en.next();
			Node img2 = (Node) morph1.getImage(go);		
			if (img2 != null) {
				if (go.getType().isChildOf(img2.getType())) {
					Node child = go;
					Node parent = img2;
					// save original of parent and its edges
					Node orig_rStar = (Node) morph2.getInverseImage(parent).nextElement();
					Iterator<Arc> arcs = orig_rStar.getOutgoingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Arc img = (Arc)morph2.getImage(a);
						orig2img_isoMorph.put(a, img);
					}
					arcs = orig_rStar.getIncomingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Arc img = (Arc)morph2.getImage(a);
						orig2img_isoMorph.put(a, img);
					}
					morph2.removeMapping(orig_rStar);
					
//					 save parent's edges
					arcs = parent.getOutgoingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Enumeration<GraphObject> orig = morph1.getInverseImage(a);
						if (orig.hasMoreElements())
							img2origOutArc.put(a, (Arc)orig.nextElement());
						else
							img2origOutArc.put(a, a);
					}
					arcs = parent.getIncomingArcsSet().iterator();
					while (arcs.hasNext()) {
						Arc a = arcs.next();
						Enumeration<GraphObject> orig = morph1.getInverseImage(a);
						if (orig.hasMoreElements())
							img2origInArc.put(a, (Arc)orig.nextElement());
						else
							img2origInArc.put(a, a);
					}
					
					final Type childT = child.getType();
					try {
						final Node childNode = morph1.getTarget().createNode(childT);
						// handle attributes  
						if (parent.getAttribute() != null) {
							for (int i=0; i<parent.getAttribute().getNumberOfEntries(); i++) {
								ValueMember pvm = (ValueMember) parent.getAttribute().getMemberAt(i);
								ValueMember vm = (ValueMember) childNode.getAttribute().getMemberAt(pvm.getName());
								if (!vm.isSet() && 
										pvm.isSet()) {
									vm.setExprAsText(pvm.getExprAsText());
									if (!vm.isTransient())
										vm.setTransient(pvm.isTransient());
								}
								else {
									vm.setExprAsText("vm_"+i);
									vm.setTransient(true);
								}
							}
						
							for (int i=0; i<childNode.getAttribute().getNumberOfEntries(); i++) {
								ValueMember vm = (ValueMember) childNode.getAttribute().getMemberAt(i);
								if (!vm.isSet()) {
									vm.setExprAsText("vm_"+i);
									vm.setTransient(true);
								}
							}
						}
					
						try {
							// reset mappings
							// remove old mapping
							morph1.removeMapping(child);
							// add new mapping
							morph1.addMapping(child, childNode);
							// reset mapping of arcs
							Enumeration<Arc> keys = img2origOutArc.keys();
							while (keys.hasMoreElements()) {
								Arc img = keys.nextElement();
								Arc orig = img2origOutArc.get(img);
								// ADD MULTIPLICITY CHECK ???
								if (img.getSource() != img.getTarget()) {
									img.setSource(childNode);
									
									if (orig != img) {
										try {
											morph1.addMapping(orig, img);
										} catch (BadMappingException e) {
//											System.out.println("replaceParentByChild:BadMappingException:Arc "+e.getStackTrace());
											return false; 
										}
									}
								} 
							}
							keys = img2origInArc.keys();
							while (keys.hasMoreElements()) {
								Arc img = keys.nextElement();
								Arc orig = img2origInArc.get(img);
//								ADD MULTIPLICITY CHECK ???
								if (img.getSource() == img.getTarget()) 
									img.setSource(childNode);
								img.setTarget(childNode);
								
								if (orig != img) {
									try {
										morph1.addMapping(orig, img);
									} catch (BadMappingException e) {
//										System.out.println("replaceParentByChild:BadMappingException:Arc "+e.getStackTrace());
										return false; 
									}	
								}
							}
							
							if (parent.getNumberOfInOutArcs() == 0) {
								// reset mappings
								morph2.addMapping(orig_rStar, childNode);
								keys = orig2img_isoMorph.keys();
								while (keys.hasMoreElements()) {
									Arc a = keys.nextElement();
									Arc img = orig2img_isoMorph.get(a);
									try {
										morph2.addMapping(a, img);
									} catch (BadMappingException e) {
//										System.out.println("replaceParentByChild:BadMappingException:Arc "+e.getStackTrace());
										return false; 
									}
								}
//								System.out.println("ExcludePair.replaceParentByChild::  DONE: "+parent.getType().getName()+"  by  "+childNode.getType().getName());

								// destroy not more needed parent node
								morph1.getTarget().destroyNode(parent, true, false);
							}
							else {
								return false;
							}
							
						} catch (BadMappingException ex1) {
							System.out.println("replaceParentByChild:BadMappingException:Node "+ex1.getStackTrace());
							return false;
						}
					} catch (TypeException ex) {
						System.out.println("replaceParentByChild:TypeException "+ex.getStackTrace());
						return false;
					}
				}
			}
		}
		return true;
	}

}
