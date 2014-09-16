package agg.parser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import agg.attribute.AttrType;
import agg.attribute.facade.InformationFacade;
import agg.attribute.facade.impl.DefaultInformationFacade;
import agg.attribute.handler.AttrHandler;
import agg.attribute.impl.ValueTuple;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;

public class ConflictsDependenciesBasisGraph {

	ExcludePairContainer conflictCont;

	ExcludePairContainer dependCont;

	GraGra grammar;

	Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> conflicts;

	Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> dependencies;

	Graph conflictGraph, dependGraph, combiGraph;

	public ConflictsDependenciesBasisGraph(
			ExcludePairContainer conflictsContainer,
			ExcludePairContainer dependenciesContainer) {
		// System.out.println("ConflictsDependenciesBasisGraph(ExcludePairContainer,
		// ExcludePairContainer");
		this.conflictCont = conflictsContainer;
		this.dependCont = dependenciesContainer;
		initTables();
		createGraphs();
		if (this.combiGraph != null)
			optimizeLayout(this.combiGraph);
		else if (this.conflictGraph != null)
			optimizeLayout(this.conflictGraph);
		else if (this.dependGraph != null)
			optimizeLayout(this.dependGraph);
	}

	public Graph getConflictsGraph() {
		return this.conflictGraph;
	}

	public Graph getDependenciesGraph() {
		return this.dependGraph;
	}

	public Graph getConflictsDependenciesGraph() {
		return this.combiGraph;
	}

	private void initTables() {
		if (this.conflictCont != null) {
			this.conflicts = this.conflictCont.getExcludeContainer();
			this.grammar = this.conflictCont.getGrammar();
		}
		if (this.dependCont != null) {
			this.dependencies = this.dependCont.getExcludeContainer();
			if (this.grammar == null)
				this.grammar = this.dependCont.getGrammar();
		}
	}

	private void createGraphs() {
		if ((this.conflicts == null) && (this.dependencies == null))
			return;
		Hashtable<String, Node> common = new Hashtable<String, Node>();
		Hashtable<String, Node> local = new Hashtable<String, Node>();
		// System.out.println(this.conflicts+" "+this.dependencies);
		Graph g = null;
		TypeSet types = null;
		if (this.conflicts != null) {
			this.conflictGraph = new Graph();
			this.conflictGraph.setName("Conflicts of Rules");
			g = this.conflictGraph;
			types = this.conflictGraph.getTypeSet();
		}
		if (this.dependencies != null) {
			if (types != null)
				this.dependGraph = BaseFactory.theFactory().createGraph(types);
			else
				this.dependGraph = new Graph();
			this.dependGraph.setName("Dependencies of Rules");
			if (g == null) {
				g = this.dependGraph;
				types = this.dependGraph.getTypeSet();
			}
		}
		if (this.conflictGraph != null && this.dependGraph != null) {
			this.combiGraph = new Graph(types);
			this.combiGraph
					.setName("CPA Graph: Conflicts (red) - Dependencies (blue) of Rules");
		}
		if (types != null) {
//		Type nodeType = types.createType(true);
//		Type arcTypeConflict = types.createType(false); //true);
//		Type arcTypeDepend = types.createType(false); //true);
		
		Type nodeType = types.createNodeType(true);
		Type arcTypeConflict = types.createArcType(false);
		Type arcTypeDepend = types.createArcType(false);
		
		nodeType.setStringRepr("Rule");
		nodeType.setAdditionalRepr("[NODE]");
		arcTypeConflict.setStringRepr("c");
		// arcTypeConflict.setAdditionalRepr("[EDGE]");
		arcTypeConflict
				.setAdditionalRepr(":SOLID_LINE:java.awt.Color[r=255,g=0,b=0]::[EDGE]:");
		arcTypeDepend.setStringRepr("d");
		// arcTypeDepend.setAdditionalRepr("[EDGE]");
		arcTypeDepend
				.setAdditionalRepr(":DOT_LINE:java.awt.Color[r=0,g=0,b=255]::[EDGE]:");

		InformationFacade info = DefaultInformationFacade.self();
		AttrHandler javaHandler = info.getJavaHandler();
		AttrType attrType = nodeType.getAttrType();
		attrType.addMember(javaHandler, "String", "name");

		if (this.conflicts != null) {
			for (Enumeration<Rule> keys1 = this.conflicts.keys(); keys1.hasMoreElements();) {
				Rule r1 = keys1.nextElement();
				if (r1.isEnabled()) {
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> 
					table = this.conflicts.get(r1);
					for (Enumeration<Rule> keys2 = table.keys(); keys2
							.hasMoreElements();) {
						Rule r2 = keys2.nextElement();
						if (r2.isEnabled()) {
							ExcludePairContainer.Entry entry = this.conflictCont
									.getEntry(r1, r2);
							Node nr1 = local.get(r1.getName());
							if (nr1 == null) {
								nr1 = createNode(this.conflictGraph, nodeType, r1);
								local.put(r1.getName(), nr1);
								if (r1 == r2)
									nr1.setVisible(entry.isRuleVisible());
							}
							if (this.combiGraph != null) {
								Node nr = common.get(r1.getName());
								if (nr == null) {
									nr = createNode(this.combiGraph, nodeType, r1);
									common.put(r1.getName(), nr);
									if (r1 == r2)
										nr.setVisible(entry.isRuleVisible());
								}
							}
							Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> p = table
									.get(r2);
							boolean rel = p.first.booleanValue();
							Node nr2 = local.get(r2.getName());
							if (nr2 == null) {
								nr2 = createNode(this.conflictGraph, nodeType, r2);
								local.put(r2.getName(), nr2);
							}
							if (this.combiGraph != null) {
								Node nr = common.get(r2.getName());
								if (nr == null) {
									nr = createNode(this.combiGraph, nodeType, r2);
									common.put(r2.getName(), nr);
								}
							}
							if (rel) {
								// create edge if rule relation
//								Arc a = 
								createEdge(this.conflictGraph, arcTypeConflict, r1, r2);
								if (this.combiGraph != null) {
//									Arc a1 = 
									createEdge(this.combiGraph, arcTypeConflict, r1, r2);
								}
							}
						}
					}
				}
			}
		}

		if (this.dependencies != null) {
			local.clear();
			for (Enumeration<Rule> keys1 = this.dependencies.keys(); keys1
					.hasMoreElements();) {
				Rule r1 = keys1.nextElement();
				if (r1.isEnabled()) {
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> 
					table = this.dependencies.get(r1);
					for (Enumeration<Rule> keys2 = table.keys(); keys2
							.hasMoreElements();) {
						Rule r2 = keys2.nextElement();
						if (r2.isEnabled()) {
							ExcludePairContainer.Entry entry = this.dependCont
									.getEntry(r1, r2);
							Node nr1 = local.get(r1.getName());
							if (nr1 == null) {
								nr1 = createNode(this.dependGraph, nodeType, r1);
								local.put(r1.getName(), nr1);
								if (r1 == r2)
									nr1.setVisible(entry.isRuleVisible());
							}
							if (this.combiGraph != null) {
								Node nr = common.get(r1.getName());
								if (nr == null) {
									nr = createNode(this.combiGraph, nodeType, r1);
									common.put(r1.getName(), nr);
									if (r1 == r2)
										nr.setVisible(entry.isRuleVisible());
								}
							}
							Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> p = table
									.get(r2);
							boolean rel = p.first.booleanValue();
							Node nr2 = local.get(r2.getName());
							if (nr2 == null) {
								nr2 = createNode(this.dependGraph, nodeType, r2);
								local.put(r2.getName(), nr2);
							}
							if (this.combiGraph != null) {
								Node nr = common.get(r2.getName());
								if (nr == null) {
									nr = createNode(this.combiGraph, nodeType, r2);
									common.put(r2.getName(), nr);
								}
							}
							if (rel) {
//								Arc a = 
								createEdge(this.dependGraph, arcTypeDepend, r1, r2);
								if (this.combiGraph != null) {
//									a = 
									createEdge(this.combiGraph, arcTypeDepend, r1, r2);
								}
							}
						}
					}
				}
			}
		}
		common.clear();
		common = null;
		local.clear();
		local = null;
		}
	}

	private void optimizeLayout(Graph g) {
		// System.out.println("optimizeLayout graph");
		// replace two edges between the same nodes through one not directed
		// edge
		Iterator<Arc> e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			Arc a = e.next();

			if (a.getSource() == a.getTarget())
				continue;
			if (!a.isDirected())
				continue;

			Iterator<Arc> e1 = g.getArcsSet().iterator();
			while (e1.hasNext()) {
				Arc a1 = e1.next();

				if (a1.getSource() == a1.getTarget())
					continue;
				if (!a1.isDirected())
					continue;

				if (a != a1) {
					if (a.getType().getName().equals(a1.getType().getName())
							&& (a.getSource() == a1.getTarget())
							&& (a.getTarget() == a1.getSource())) {
						// System.out.println(a.getType().getName()+" ==
						// "+a1.getType().getName());
						a.setVisible(false);
						a1.setDirected(false);
						break;
					}
				}
			}
		}
	}

	private Node createNode(Graph g, Type t, Rule r) {
		Node n = getNode(g, r);
		if (n == null) {
			try {
				n = g.createNode(t);
				ValueTuple vt = (ValueTuple) n.getAttribute();
				String rname = r.getName();
				vt.getValueMemberAt("name").setExprAsObject(rname);
			} catch (TypeException e) {
			}
		}
		return n;
	}

	private Node getNode(Graph g, Rule r) {
		Iterator<Node> e = g.getNodesSet().iterator();
		while (e.hasNext()) {
			Node n = e.next();
			if (((String) n.getAttribute().getValueAt("name")).equals(r
					.getName()))
				return n;
		}
		return null;
	}

	private Arc createEdge(Graph g, Type t, Node n1, Node n2) {
		if (t == null || n1 == null || n2 == null)
			return null;
		Arc a = getEdge(g, t, n1, n2);
		if (a == null) {
			try {
				a = g.createArc(t, n1, n2);
				if (n1 != n2) {
					Arc a1 = getEdge(g, t, n2, n1);
					if (a1 != null) {
						a.setDirected(false);
						a1.setVisible(false);
					}
				}
			} catch (TypeException e) {
			}
		}
		return a;
	}

	private Arc createEdge(Graph g, Type t, Rule r1, Rule r2) {
		if (t == null || r1 == null || r2 == null)
			return null;
		Node n1 = getNode(g, r1);
		Node n2 = getNode(g, r2);
		return createEdge(g, t, n1, n2);
	}

	private Arc getEdge(Graph g, Type t, Node n1, Node n2) {
		if (t == null || n1 == null || n2 == null)
			return null;
		Iterator<Arc> e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			if (!a.getType().getName().equals(t.getName()))
				continue;
			Object src = a.getSource().getAttribute().getValueAt("name");
			Object tar = a.getTarget().getAttribute().getValueAt("name");
			Object name1 = n1.getAttribute().getValueAt("name");
			Object name2 = n2.getAttribute().getValueAt("name");
			if ((src != null && ((String) src).equals(name1))
					&& (tar != null && ((String) tar).equals(name2)))
				return a;
		}
		return null;
	}

}
