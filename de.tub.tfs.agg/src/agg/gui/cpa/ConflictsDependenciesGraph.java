package agg.gui.cpa;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.JOptionPane;
import javax.swing.JMenuItem;

import agg.parser.CriticalPair;
import agg.parser.ExcludePairContainer;
import agg.parser.PairContainer;
//import agg.parser.ParserFactory;
import agg.parser.CriticalPairEvent;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;
import agg.gui.editor.EditorConstants;
import agg.gui.parser.PairIOGUI;
import agg.gui.parser.event.ParserGUIListener;
import agg.gui.parser.event.ParserGUIEvent;
import agg.gui.parser.event.CPAEventData;
import agg.gui.saveload.GraphicsExportJPEG;
//import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.TypeSet;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.Node;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
import agg.attribute.facade.impl.DefaultInformationFacade;
import agg.attribute.facade.InformationFacade;
import agg.attribute.impl.ValueTuple;
import agg.attribute.AttrType;
import agg.attribute.handler.AttrHandler;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdArc;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdType;
import agg.util.Pair;

public class ConflictsDependenciesGraph implements ActionListener,
		ParserEventListener, ParserGUIListener {

	PairIOGUI pairIOGUI;

	GraphDesktop graphDesktop;

	ExcludePairContainer conflictCont;

	ExcludePairContainer dependCont;

	GraGra grammar;

	Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
	conflicts;

	Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
	dependencies;

	Graph cpaGraph;

	EdGraph cpaLayout;

	boolean conflictAction = true;

	boolean hiddenGraphObject = false;

	GraphicsExportJPEG graphJPG;

	final Vector<Arc> visArcs = new Vector<Arc>();

	public ConflictsDependenciesGraph(ExcludePairContainer conflictsContainer,
			ExcludePairContainer dependenciesContainer) {
		init(conflictsContainer, dependenciesContainer);
	}

	public ConflictsDependenciesGraph(ExcludePairContainer conflictsContainer,
			ExcludePairContainer dependenciesContainer, EdGraph cpaGraph,
			boolean loaded) {

		if (cpaGraph == null)
			init(conflictsContainer, dependenciesContainer);
		else 
			reinit(conflictsContainer, dependenciesContainer, cpaGraph, loaded);
	}

	private void init(ExcludePairContainer conflictsContainer,
			ExcludePairContainer dependenciesContainer) {
		this.conflictCont = conflictsContainer;
		this.dependCont = dependenciesContainer;
		initTables();
		createGraphs();
		if (this.cpaGraph != null) {
			storeVisArcs(this.cpaGraph);
			this.cpaLayout = new EdGraph(this.cpaGraph);			
			this.cpaLayout.setCPAgraph(true);
			this.cpaLayout.makeInitialUpdateOfNodes();
			layoutGraph(this.cpaLayout);

			if (this.conflictCont != null)
				this.conflictCont.addPairEventListener(this);
			if (this.dependCont != null)
				this.dependCont.addPairEventListener(this);
		} 

//		if (cpaLayout != null)
//			cpaLayout.setTransformChangeEnabled(true);
	}
	
	private void reinit(ExcludePairContainer conflictsContainer,
			ExcludePairContainer dependenciesContainer, EdGraph cpagraph,
			boolean loaded) {
		this.conflictCont = conflictsContainer;
		this.dependCont = dependenciesContainer;
		this.cpaLayout = cpagraph;
		initTables();
		if (this.cpaLayout != null) {
//			cpaLayout.setTransformChangeEnabled(true);
			if (!loaded)
				updateGraphAlongPairContainer();
			else
				updatePairsContainerAlongCPAgraph();

			this.cpaLayout.makeInitialUpdateOfNodes();
			layoutGraph(this.cpaLayout);
			
			if (this.conflictCont != null)
				this.conflictCont.addPairEventListener(this);
			if (this.dependCont != null)
				this.dependCont.addPairEventListener(this);
		}	
	}
	
	public void dispose() {
		if (this.cpaLayout != null) {
			this.cpaLayout.dispose();
			this.cpaLayout = null;
		}
		if (this.cpaGraph != null) {
			this.cpaGraph.dispose();
			this.cpaGraph = null;
		}
		
		this.grammar = null;
		this.conflictCont = null;
		this.dependCont = null;
		this.conflicts = null;
		this.dependencies = null;
		this.visArcs.clear();
	}
	
	
	public void setGraphDesktop(GraphDesktop desktop) {
		this.graphDesktop = desktop;
	}

	public void setGraphExportJPG(GraphicsExportJPEG jpg) {
		this.graphJPG = jpg;
	}

	public void setConflictPairContainer(PairContainer pc) {
		this.conflictCont = (ExcludePairContainer) pc;
		this.conflicts = this.conflictCont.getExcludeContainer();
		if (this.grammar == null)
			this.grammar = this.conflictCont.getGrammar();
	}

	public void setDependencyPairContainer(PairContainer pc) {
		this.dependCont = (ExcludePairContainer) pc;
		this.dependencies = this.dependCont.getExcludeContainer();
		if (this.grammar == null)
			this.grammar = this.dependCont.getGrammar();
	}

	/**
	 * gets called if something changed in the critical pair container so the
	 * display must be updated.
	 */
	public void parserEventOccured(ParserEvent p) {
		if (p instanceof CriticalPairEvent
				&& p.getSource() instanceof ExcludePairContainer) {			
			if (((ExcludePairContainer) p.getSource()).isAlive()
					|| this.cpaLayout == null)
				return;

			Rule r1 = ((CriticalPairEvent) p).getFirstRule();
			Rule r2 = ((CriticalPairEvent) p).getSecondRule();
			
			if (((ExcludePairContainer) p.getSource()).getKindOfConflict() == CriticalPair.CONFLICT) {
				if (this.conflictCont != p.getSource())	{
					return;
				}
								
				if (((CriticalPairEvent) p).getKey() == CriticalPairEvent.REMOVE_ENTRIES) {						
					updateGraphAlongPairContainer();
					if (this.graphDesktop != null)
						this.graphDesktop.refreshCPAGraph();						
					return;
				}
				
				ExcludePairContainer.Entry entry = ((ExcludePairContainer) p.getSource()).
				getEntry(r1, r2);

				if (entry.getState() == ExcludePairContainer.Entry.COMPUTED
						|| entry.getState() == ExcludePairContainer.Entry.COMPUTED2
						|| entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {
					Node n1 = getNode(this.cpaLayout.getBasisGraph(), r1);
					if (n1 == null) 
						createLayoutNode(this.cpaLayout, "Rule", r1);					
					Node n2 = getNode(this.cpaLayout.getBasisGraph(), r2);
					if (n2 == null) 
						createLayoutNode(this.cpaLayout, "Rule", r2);					
					if (entry.isCritical()) 
						createLayoutEdge(this.cpaLayout, "c", r1, r2);					
					this.cpaLayout.update();
					this.cpaLayout.setTransformChangeEnabled(false);
					if (this.graphDesktop != null)
						this.graphDesktop.refreshCPAGraph();
				}
				if (((CriticalPairEvent) p).getKey() == CriticalPairEvent.SHOW_ENTRY) {
					showEdge(this.cpaLayout.getBasisGraph(), "c", r1, r2);
					if (this.cpaLayout != null) {
						this.cpaLayout.update();
						if (this.graphDesktop != null)
							this.graphDesktop.refreshCPAGraph();
					}
				} 
//				else if (((CriticalPairEvent) p).getKey() == CriticalPairEvent.REMOVE_RELATION_ENTRY) {
//					removeLayoutEdge(this.cpaLayout, "c", r1, r2);
//				}
			} else {
				if (this.dependCont != p.getSource())	{
					return;
				}
				if (((CriticalPairEvent) p).getKey() == CriticalPairEvent.REMOVE_ENTRIES) {						
					updateGraphAlongPairContainer();
					if (this.graphDesktop != null)
						this.graphDesktop.refreshCPAGraph();						
					return;
				}
					
				ExcludePairContainer.Entry entry = ((ExcludePairContainer) p.getSource()).
														getEntry(r1, r2);					
				if (entry.getState() == ExcludePairContainer.Entry.COMPUTED
						|| entry.getState() == ExcludePairContainer.Entry.COMPUTED2
						|| entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {
					Node n1 = getNode(this.cpaLayout.getBasisGraph(), r1);
					if (n1 == null)
						createLayoutNode(this.cpaLayout, "Rule", r1);
					Node n2 = getNode(this.cpaLayout.getBasisGraph(), r2);
					if (n2 == null)
						createLayoutNode(this.cpaLayout, "Rule", r2);
					if (entry.isCritical()) {
						createLayoutEdge(this.cpaLayout, "d", r1, r2);
					}
					this.cpaLayout.update();	
					this.cpaLayout.setTransformChangeEnabled(false);
					if (this.graphDesktop != null)
						this.graphDesktop.refreshCPAGraph();					
				}
				if (((CriticalPairEvent) p).getKey() == CriticalPairEvent.SHOW_ENTRY) {
					showEdge(this.cpaLayout.getBasisGraph(), "d", r1, r2);
					if (this.cpaLayout != null) {
						this.cpaLayout.update();
						if (this.graphDesktop != null)
							this.graphDesktop.refreshCPAGraph();
					}
				} 
//				else if (((CriticalPairEvent) p).getKey() == CriticalPairEvent.REMOVE_RELATION_ENTRY) {
//					removeEdge(this.cpaLayout.getBasisGraph(), "d", r1, r2);
//				} 
			}
		}
	}

	/**
	 * gets called if something changed in the critical pair GUI so the display
	 * must be updated.
	 */
	public void occured(ParserGUIEvent e) {
		if (e.getSource() instanceof CriticalPairPanel) {
			if (e.getData() instanceof CPAEventData) {
				if (((CriticalPairPanel) e.getSource())
						.getKindOfPairContainer() == CriticalPair.CONFLICT)
					this.conflictAction = true;
				else if (((CriticalPairPanel) e.getSource())
							.getKindOfPairContainer() == CriticalPair.TRIGGER_DEPENDENCY
						|| ((CriticalPairPanel) e.getSource())
							.getKindOfPairContainer() == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
					this.conflictAction = false;

				CPAEventData d = (CPAEventData) e.getData();
				int kind = d.kind;
				Rule r1 = d.r1;
				Rule r2 = null;
				String t = "";
				boolean vis = d.visible;
				
				if (kind == CPAEventData.SHOW_RULE)
					t = "Rule";
				if (kind == CPAEventData.SHOW_RELATION
						|| kind == CPAEventData.HIDE_RELATION) {
					r2 = d.r2;
					t = d.type;
				}

				if (this.cpaLayout != null) {
					if (!vis) {
						if (kind == CPAEventData.SHOW_RULE)
							hideNode(this.cpaLayout.getBasisGraph(), r1, false);
						else if ((kind == CPAEventData.SHOW_RELATION)
								&& t.equals("C"))
							hideEdge(this.cpaLayout.getBasisGraph(), "c", r1, r2);
						else if ((kind == CPAEventData.SHOW_RELATION)
								&& t.equals("D"))
							hideEdge(this.cpaLayout.getBasisGraph(), "d", r1, r2);
					
						this.cpaLayout.update();
						if (this.graphDesktop != null)
							this.graphDesktop.refresh();						
					} else {
						if (kind == CPAEventData.SHOW_RULE)
							showNode(this.cpaLayout.getBasisGraph(), t, r1, false);
						else if ((kind == CPAEventData.SHOW_RELATION)
								&& t.equals("C"))
							showEdge(this.cpaLayout.getBasisGraph(), "c", r1, r2);
						else if ((kind == CPAEventData.SHOW_RELATION)
								&& t.equals("D")) {
							showEdge(this.cpaLayout.getBasisGraph(), "d", r1, r2);
						}
						this.cpaLayout.update();
						if (this.graphDesktop != null)
							this.graphDesktop.refresh();						
					}
					if (kind == CPAEventData.HIDE_RELATION) {
						this.cpaLayout.enableStaticNodePosition();
						try {						
							this.cpaLayout.deleteObj(this.cpaLayout.findArc(
										this.getEdge(
												this.cpaLayout.getBasisGraph(), t.toLowerCase(), r1, r2)), true);
						} catch (TypeException ex) {
							System.out.println(ex.getLocalizedMessage());
						}
						this.cpaLayout.disableStaticNodePosition();
					}					
				}
			}
			else if (e.getData() instanceof CriticalPairEvent) {							
				Rule r1 = ((CriticalPairEvent) e.getData()).getFirstRule();
				Rule r2 = ((CriticalPairEvent) e.getData()).getSecondRule();
				
				if (((CriticalPairPanel) e.getSource()).getKindOfPairContainer() == CriticalPair.CONFLICT) { 
					if (((CriticalPairEvent) e.getData()).getKey() == CriticalPairEvent.REMOVE_RELATION_ENTRY) {
						removeLayoutEdge(this.cpaLayout, "c", r1, r2);	
					}
				}
				else if (((CriticalPairEvent) e.getData()).getKey() == CriticalPairEvent.REMOVE_RELATION_ENTRY) {
					removeLayoutEdge(this.cpaLayout, "d", r1, r2);						
				}
			}
		}
	}

	public void updatePairsContainerAlongCPAgraph() {
		if (this.cpaLayout == null)
			return;
		if (this.conflictCont != null) {
			updatePairsContainerAlongCPAgraph(this.conflictCont);
		}
		if (this.dependCont != null) {
			updatePairsContainerAlongCPAgraph(this.dependCont);
		}
		// this.cpaLayout.update();
	}

	private void updatePairsContainerAlongCPAgraph(PairContainer pc) {
		if (this.cpaLayout == null || pc == null)
			return;

		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> container = ((ExcludePairContainer) pc).getExcludeContainer();
		for (Enumeration<Rule> keys = container.keys(); keys.hasMoreElements();) {
			Rule r1 = keys.nextElement();
			Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = container.get(r1);
			for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
				Rule r2 = k2.nextElement();
				ExcludePairContainer.Entry entry = ((ExcludePairContainer) pc)
						.getEntry(r1, r2);
				if (entry.isCritical()) {
					if (r1 == r2) {
						Vector<EdNode> elems = this.cpaLayout.getNodes();
						for (int i = 0; i < elems.size(); i++) {
							agg.xt_basis.Node n = elems.get(i).getBasisNode();
							Object val = n.getAttribute().getValueAt("name");
							if (val != null
									&& ((String) val).equals(r1.getQualifiedName())) {
								((ExcludePairContainer) pc)
										.setEntryRuleVisible(r1, r2, n
												.isVisible(), true, false);
								break;
							}
						}
					}

					String tn = "c";
					if (pc.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY
							|| pc.getKindOfConflict() == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
						tn = "d";
					agg.xt_basis.Arc a = getArc(this.cpaLayout, tn, r1, r2);
					if (a != null) {
						if (r1 == r2)
							((ExcludePairContainer) pc)
									.setEntryRelationVisible(r1, r2, a
											.isVisible(), true);
						else {
							ExcludePairContainer.Entry entry2 = ((ExcludePairContainer) pc)
									.getEntry(r2, r1);
							if (entry2.isCritical()) {
								agg.xt_basis.Arc a2 = getArc(this.cpaLayout, tn, r2,
										r1);
								if (a2 != null) {
									if (!a.isVisible() && !a2.isVisible()) {
										((ExcludePairContainer) pc)
												.setEntryRelationVisible(r1,
														r2, false, true);
										((ExcludePairContainer) pc)
												.setEntryRelationVisible(r2,
														r1, false, true);
									} else if (!a2.isDirected()
											&& !a2.isVisible()
											&& a.isDirected() && a.isVisible()) {
										((ExcludePairContainer) pc)
												.setEntryRelationVisible(r2,
														r1, false, true);
									} else if (!a.isDirected()
											&& !a.isVisible()
											&& a2.isDirected()
											&& a2.isVisible()) {
										((ExcludePairContainer) pc)
												.setEntryRelationVisible(r1,
														r2, false, true);
									} else if (a2.isDirected()
											&& !a2.isVisible()
											&& !a.isDirected() && a.isVisible()) {
										if (!this.visArcs.contains(a2))
											this.visArcs.add(a2);
										// ((ExcludePairContainer)pc).setEntryRelationVisible(r1,
										// r2, true, true);
										// ((ExcludePairContainer)pc).setEntryRelationVisible(r2,
										// r1, true, true);
									} else if (a.isDirected() && !a.isVisible()
											&& !a2.isDirected()
											&& a2.isVisible()) {
										if (!this.visArcs.contains(a))
											this.visArcs.add(a);
										// ((ExcludePairContainer)pc).setEntryRelationVisible(r1,
										// r2, true, true);
										// ((ExcludePairContainer)pc).setEntryRelationVisible(r2,
										// r1, true, true);
									}
								}
							} else {
								((ExcludePairContainer) pc)
										.setEntryRelationVisible(r1, r2, a
												.isVisible(), true);
							}
						}
					}
				} else {
					if (r1 == r2) {
						Vector<EdNode> elems = this.cpaLayout.getNodes();
						for (int i = 0; i < elems.size(); i++) {
							agg.xt_basis.Node n = elems.get(i).getBasisNode();
							Object val = n.getAttribute().getValueAt("name");
							if (val != null
									&& ((String) val).equals(r1.getQualifiedName())) {
								((ExcludePairContainer) pc)
										.setEntryRuleVisible(r1, r2, n
												.isVisible(), true, false);
								break;
							}
						}
					}
				}
			}
		}
	}

	private void updatePairsContainerAllVisibile(PairContainer pc) {
		if (this.cpaLayout == null || pc == null)
			return;

		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
		container = ((ExcludePairContainer) pc).getExcludeContainer();
		for (Enumeration<Rule> keys = container.keys(); keys.hasMoreElements();) {
			Rule r1 = keys.nextElement();
			Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> 
			secondPart = container.get(r1);
			for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
				Rule r2 = k2.nextElement();
				ExcludePairContainer.Entry entry = ((ExcludePairContainer) pc)
						.getEntry(r1, r2);
				if (entry.isCritical()) {
					Vector<EdNode> elems = this.cpaLayout.getNodes();
					for (int i = 0; i < elems.size(); i++) {
						agg.xt_basis.Node n = elems.get(i).getBasisNode();
						Object val = n.getAttribute().getValueAt("name");
						if (val != null
								&& ((String) val).equals(r1.getQualifiedName())) {
							n.setVisible(true);
							((ExcludePairContainer) pc)
									.setEntryRuleVisible(r1, r2, true, true, false);
							break;
						}
					}

					String tn = "c";
					if (pc.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY
							|| pc.getKindOfConflict() == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
						tn = "d";
					agg.xt_basis.Arc a = getArc(this.cpaLayout, tn, r1, r2);
					if (a != null) {	
						if (!a.isVisible()
								&& !this.visArcs.contains(a))
							a.setVisible(true);
						((ExcludePairContainer) pc)
							.setEntryRelationVisible(r1, r2, true, true);						
					}
				} else {
					if (r1 == r2) {
						Vector<EdNode> elems = this.cpaLayout.getNodes();
						for (int i = 0; i < elems.size(); i++) {
							agg.xt_basis.Node n = elems.get(i).getBasisNode();
							Object val = n.getAttribute().getValueAt("name");
							if (val != null
									&& ((String) val).equals(r1.getQualifiedName())) {
								n.setVisible(true);
								((ExcludePairContainer) pc)
										.setEntryRuleVisible(r1, r2, true, true, false);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private Arc getArc(EdGraph g, String tn, Rule r1, Rule r2) {
		Vector<EdArc> elems = g.getArcs();
		Arc a = null;
		for (int i = 0; i < elems.size(); i++) {
			a = elems.get(i).getBasisArc();
			if (a.getType().getName().equals(tn)) {
				Object src = a.getSource().getAttribute().getValueAt("name");
				Object tar = a.getTarget().getAttribute().getValueAt("name");
				if ((src != null && ((String) src).equals(r1.getQualifiedName()))
						&& (tar != null && ((String) tar).equals(r2.getQualifiedName()))) {
					return a;
				}
			}
		}
		return null;
	}

	public void updateGraphAlongPairContainer() {
		if (this.conflictCont != null && this.dependCont != null
				&& this.conflictCont.isEmpty() && this.dependCont.isEmpty()) {
			this.visArcs.clear();
			// then remove all
			try {
				this.cpaLayout.deleteAll();
			} catch (TypeException exc) {}
		}
		else {
			if (this.conflictCont != null)
				updateGraphAlongPairContainer(this.cpaLayout, this.conflictCont, this.conflicts,
						"c");
			if (this.dependCont != null)
				updateGraphAlongPairContainer(this.cpaLayout, this.dependCont, this.dependencies,
						"d");
		}
	}

	private void updateGraphAlongPairContainer(EdGraph g, PairContainer pc,
			Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> table1, 
			String tn) {
		if (g == null || pc == null || table1 == null)
			return;

		if (table1.isEmpty()) { // then remove all edges
			Iterator<Arc> e = g.getBasisGraph().getArcsSet().iterator();
			while (e.hasNext()) {
				Arc a = e.next();
				if (!a.getType().getName().equals(tn))
					continue;
				try {
					this.visArcs.remove(a);
					g.delArc(a);
//					g.getBasisGraph().destroyArc(a, false, true);
					
					e = g.getBasisGraph().getArcsSet().iterator();
				} catch (TypeException exc) {}
			}
		} else { // remove edge if entry == null or NOT_SET
			Iterator<Arc> e = g.getBasisGraph().getArcsSet().iterator();
			while (e.hasNext()) {
				Arc a = e.next();
				if (!a.getType().getName().equals(tn))
					continue;
				Rule r1 = getRule((Node) a.getSource());
				Rule r2 = getRule((Node) a.getTarget());
				if (r1 != null && r2 != null) {
					ExcludePairContainer.Entry entry = ((ExcludePairContainer) pc)
							.getEntry(r1, r2);
					if ((entry == null)
							|| entry.getState() == ExcludePairContainer.Entry.NOT_SET) {
						try {
							this.visArcs.remove(a);
							g.delArc(a);
//							g.getBasisGraph().destroyArc(a, false, true);
							
							e = g.getBasisGraph().getArcsSet().iterator();
						} catch (TypeException ex) {}
					}
				}
			}
		}

		for (Enumeration<Rule> keys1 = table1.keys(); keys1.hasMoreElements();) {
			Rule r1 = keys1.nextElement();
			Node n1 = getNode(g.getBasisGraph(), r1);
			if (n1 == null) {// new rule node
				n1 = createNode(g.getBasisGraph(), "Rule", r1);
			}
			Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> table2 = table1.get(r1);
			for (Enumeration<Rule> keys2 = table2.keys(); keys2.hasMoreElements();) {
				Rule r2 = keys2.nextElement();
				Node n2 = getNode(g.getBasisGraph(), r2);
				if (n2 == null) // new rule node
					n2 = createNode(g.getBasisGraph(), "Rule", r2);
				ExcludePairContainer.Entry entry = ((ExcludePairContainer) pc)
						.getEntry(r1, r2);
				if (r1 == r2)
					n1.setVisible(entry.isRuleVisible());
				if (entry.isCritical()) {
					Arc a = getArc(g, tn, r1, r2);
					if (a != null) {
						if (r1 == r2) {
							a.setVisible(entry.isRelationVisible()
									&& (a.isVisible() && !this.visArcs.contains(a)));							
						}
						else {
							ExcludePairContainer.Entry entry2 = ((ExcludePairContainer) pc)
									.getEntry(r2, r1);
							if (entry2.isCritical()) {
								Arc a2 = getArc(g, tn, r2, r1);
								if (a2 != null) {
									if (entry.isRelationVisible()
											&& entry2.isRelationVisible()) {
										/*
										if (!a2.isDirected()) {
											a2.setVisible(true);
											a.setVisible(false);
											if (!this.visArcs.contains(a))
												this.visArcs.add(a);
											a.setDirected(true);
										} else if (!a.isDirected()) {
											a.setVisible(true);
											a2.setVisible(false);
											if (!this.visArcs.contains(a2))
												this.visArcs.add(a2);
											a2.setDirected(true);
										} else if (a2.isDirected()) {
											a2.setVisible(false);
											if (!this.visArcs.contains(a2))
												this.visArcs.add(a2);
											a.setVisible(true);
											a.setDirected(false);
										} else {
											a.setVisible(false);
											if (!this.visArcs.contains(a))
												this.visArcs.add(a);
											a2.setDirected(false);
											a2.setVisible(true);
										}
										*/
									} 
									else if (!entry.isRelationVisible()
											&& !entry2.isRelationVisible()) {
										a.setVisible(false);
										a2.setVisible(false);
									} else if (entry.isRelationVisible()) {
//										a.setVisible(true);
										a.setDirected(true);
										a2.setVisible(false);
										if (a2.isDirected())
											a2.setDirected(false);
									} else if (entry2.isRelationVisible()) {
//										a2.setVisible(true);
										a2.setDirected(true);
										a.setVisible(false);
										if (a.isDirected())
											a.setDirected(false);
									}
								} else {
									a2 = createEdge(this.cpaLayout.getBasisGraph(),
											tn, r2, r1);
								}
							}
						}
					} else {
						a = createEdge(g.getBasisGraph(), tn, r1, r2);
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JMenuItem) {
			if (((JMenuItem) source).getText().equals(
					"Show Conflicts")) {
				if (this.conflictCont != null)
					this.updatePairsContainerAllVisibile(this.conflictCont);
				
				setEdgeVisible(this.cpaLayout.getBasisGraph(), "c", true);
				setEdgeVisible(this.cpaLayout.getBasisGraph(), "d", false);

				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals(
					"Show Dependencies")) {
				if (this.dependCont != null)
					this.updatePairsContainerAllVisibile(this.dependCont);
				
				setEdgeVisible(this.cpaLayout.getBasisGraph(), "d", true);
				setEdgeVisible(this.cpaLayout.getBasisGraph(), "c", false);

				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals(
					"Show All")) {
				if (this.conflictCont != null)
					this.updatePairsContainerAllVisibile(this.conflictCont);
				if (this.dependCont != null)
					this.updatePairsContainerAllVisibile(this.dependCont);
				
				if (this.hiddenGraphObject) {
					setAllVisible(this.cpaLayout.getBasisGraph());
					this.hiddenGraphObject = false;
				} else {
					setEdgeVisible(this.cpaLayout.getBasisGraph(), "c", true);
					setEdgeVisible(this.cpaLayout.getBasisGraph(), "d", true);
				}
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals(
					"Refresh")) {
				if (this.conflictCont != null)
					this.updatePairsContainerAllVisibile(this.conflictCont);
				if (this.dependCont != null)
					this.updatePairsContainerAllVisibile(this.dependCont);
				
				this.setAllVisible(this.cpaGraph);
				this.updateGraphAlongPairContainer();
				this.cpaLayout.makeGraphObjectsOfNewBasisObjects(false);
				this.cpaLayout.setTransformChangeEnabled(true);
				this.cpaLayout.updateGraph();
				this.cpaLayout.setTransformChangeEnabled(false);
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			}
			else if (((JMenuItem) source).getText().equals("Hide Node/Edge")) {
				hideGraphObject(this.cpaLayout.getPicked());
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					
					this.updatePairsContainerAlongCPAgraph();
					
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals("Straight Edges")) {
				straightEdges(this.cpaLayout);
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals("Solid Line")) {
				if (((JMenuItem) source).getActionCommand().indexOf("Conflict") != -1)
					changeStyleOfEdges(this.cpaLayout, "c", EditorConstants.SOLID);
				else if (((JMenuItem) source).getActionCommand().indexOf(
						"Dependency") != -1)
					changeStyleOfEdges(this.cpaLayout, "d", EditorConstants.SOLID);
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals("Dot Line")) {
				if (((JMenuItem) source).getActionCommand().indexOf("Conflict") != -1)
					changeStyleOfEdges(this.cpaLayout, "c", EditorConstants.DOT);
				else if (((JMenuItem) source).getActionCommand().indexOf(
						"Dependency") != -1)
					changeStyleOfEdges(this.cpaLayout, "d", EditorConstants.DOT);
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals("Dash Line")) {
				if (((JMenuItem) source).getActionCommand().indexOf("Conflict") != -1)
					changeStyleOfEdges(this.cpaLayout, "c", EditorConstants.DASH);
				else if (((JMenuItem) source).getActionCommand().indexOf(
						"Dependency") != -1)
					changeStyleOfEdges(this.cpaLayout, "d", EditorConstants.DASH);
				if (this.graphDesktop != null) {
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				}
			} else if (((JMenuItem) source).getText().equals("Export JPEG")) {
				// defind in GraphDesktop.makeCPAGraphMenu()
			} else if (((JMenuItem) source).getText().equals("Layout Graph")) {
//				makeLayout(this.cpaLayout);
				if (this.graphDesktop != null) {
					makeLayout(this.cpaLayout, this.graphDesktop.getInternalCPAGraphFrame().getSize());
					this.graphDesktop.doClickShowAllConflicts();
					this.graphDesktop.doClickShowAllDepends();
					this.graphDesktop.refresh();
				} else {
					makeLayout(this.cpaLayout);
				}
			}
		}
	}

	public EdGraph getGraph() {
		if (this.cpaLayout != null)
			return this.cpaLayout;
		
		return null;
	}
	
	public ExcludePairContainer getConflictsContainer() {
		return this.conflictCont;
	}

	public ExcludePairContainer getDependenciesContainer() {
		return this.dependCont;
	}

	private void setEdgeVisible(Graph g, String tn, boolean vis) {
		Type t = g.getTypeSet().getTypeByName(tn);
		if (t == null)
			return;

		Iterator<Arc> e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			if (a.getSource().isVisible() && a.getTarget().isVisible()) {
				if (a.getType().getName().equals(t.getName())) {
					if (a.getSource() == a.getTarget())
						a.setVisible(vis);
					else {
						if (vis) { // true
							a.setVisible(vis);
							Arc a1 = getEdge(g, t, (Node) a.getTarget(),
									(Node) a.getSource());
							if (a1 != null) {
								if (a1.isDirected())
									a1.setVisible(false);
								else {
									a1.setVisible(true);
									a.setVisible(false);
								}
							}
						} else { // false
							a.setVisible(vis);
						}
					}
				}
			}
		}
	}

	private void setNodeVisible(Graph g) {
		Iterator<Node> e = g.getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject o = e.next();
			o.setVisible(true);
		}
	}

	private void setAllVisible(Graph g) {
		this.cpaLayout.deselectAll();
		
		setNodeVisible(g);
		setEdgeVisible(g, "c", true);
		setEdgeVisible(g, "d", true);
	}

//	private void getConflictsDependenciesContainer(String fileDirectory) {
//		getConflictsFile(fileDirectory);
//		getDependenciesFile(fileDirectory);
//	}

	/*
	private void getConflictsFile(String fileDirectory) {
		Object[] options = { "Set", "Cancel" };
		int answer = JOptionPane.YES_OPTION;
		boolean fileOK = false;
		String errMsg = "";
		while ((answer == JOptionPane.YES_OPTION) && !fileOK) {
			answer = JOptionPane.showOptionDialog(null, errMsg
					+ "Please select a file with rule this.conflicts.", "Conflicts",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (answer == JOptionPane.YES_OPTION) {
				GraGra emptyGraGra1 = BaseFactory.theFactory().createGraGra();
				this.conflictCont = (ExcludePairContainer) ParserFactory
						.createEmptyCriticalPairs(emptyGraGra1, 0, false);
				pairIOGUI.setCriticalPairContainer(this.conflictCont);
				pairIOGUI.setDirectoryName(fileDirectory);
				Object o = pairIOGUI.load();
				// System.out.println(o);
				if (o != null) {
					if (this.conflictCont.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY) {
						fileOK = false;
						this.conflictCont = null;
						errMsg = "File does not contain rule this.conflicts!\n";
					} else
						fileOK = true;
				}
				else {
					fileOK = false;
					this.conflictCont = null;
					errMsg = "File failed!\n";
				}
			}
		}
	}

	private void getDependenciesFile(String fileDirectory) {
		Object[] options = { "Set", "Cancel" };
		int answer = JOptionPane.YES_OPTION;
		boolean fileOK = false;
		String errMsg = "";
		while ((answer == JOptionPane.YES_OPTION) && !fileOK) {
			answer = JOptionPane.showOptionDialog(null, errMsg
					+ "Please set a file with rule this.dependencies.",
					"Dependencies", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (answer == JOptionPane.YES_OPTION) {
				GraGra emptyGraGra2 = BaseFactory.theFactory().createGraGra();
				this.dependCont = (ExcludePairContainer) ParserFactory
						.createEmptyCriticalPairs(emptyGraGra2, 1, false);
				pairIOGUI.setCriticalPairContainer(this.dependCont);
				pairIOGUI.load();
				// System.out.println(o);
				if (this.dependCont.getKindOfConflict() == CriticalPair.CONFLICT) {
					fileOK = false;
					this.dependCont = null;
					errMsg = "There was not a file with rule this.dependencies!\n";
				} else
					fileOK = true;
			}
		}
	}
*/
	
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

		Hashtable<String, Node> local = new Hashtable<String, Node>();
		TypeSet types = null;
		
		if (this.conflicts != null || this.dependencies != null) {
			this.cpaGraph = new Graph();
			this.cpaGraph
					.setName("CPA_RuleGraph:Conflicts_(red)-Dependencies_(blue)");
			types = this.cpaGraph.getTypeSet();
		}
		if (types != null) {		
			Type nodeType = types.createNodeType(true);
			Type arcTypeConflict = types.createArcType(false);
			Type arcTypeDepend = types.createArcType(false);
			
			nodeType.setStringRepr("Rule");
			nodeType.setAdditionalRepr("[NODE]");
			arcTypeConflict.setStringRepr("c");
			arcTypeConflict
					.setAdditionalRepr(":SOLID_LINE:java.awt.Color[r=255,g=0,b=0]::[EDGE]:");
			arcTypeDepend.setStringRepr("d");
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
								Node nr1 = local.get(r1.getQualifiedName());
								if (nr1 == null) {
									nr1 = createNode(this.cpaGraph, nodeType, r1);
									local.put(r1.getQualifiedName(), nr1);
									if (r1 == r2)
										nr1.setVisible(entry.isRuleVisible());
								}
								Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> p = table
										.get(r2);
								boolean rel = p.first.booleanValue();
								Node nr2 = local.get(r2.getQualifiedName());
								if (nr2 == null) {
									nr2 = createNode(this.cpaGraph, nodeType, r2);
									local.put(r2.getQualifiedName(), nr2);
								}
								if (rel) {
									// create edge if rule relation
									createEdge(this.cpaGraph, arcTypeConflict, r1, r2);
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
								Node nr1 = local.get(r1.getQualifiedName());
								if (nr1 == null) {
									nr1 = createNode(this.cpaGraph, nodeType, r1);
									local.put(r1.getQualifiedName(), nr1);
									if (r1 == r2)
										nr1.setVisible(entry.isRuleVisible());
								}
								Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> p = table
										.get(r2);
								boolean rel = p.first.booleanValue();
								Node nr2 = local.get(r2.getQualifiedName());
								if (nr2 == null) {
									nr2 = createNode(this.cpaGraph, nodeType, r2);
									local.put(r2.getQualifiedName(), nr2);
								}
								if (rel) {
									createEdge(this.cpaGraph, arcTypeDepend, r1, r2);
								}
							}
						}
					}
				}
			}
			local.clear();
			local = null;
		}
	}

	private void replaceBidirectedEdgesByUndirectedEdge(EdGraph eg) {
		// replace two edges between the same nodes through one not directed
		// edge
		Enumeration<EdArc> e = eg.getArcs().elements();
		while (e.hasMoreElements()) {
			EdArc a = e.nextElement();

			if (a.getSource() == a.getTarget())
				continue;
			if (!a.getBasisArc().isDirected())
				continue;

			Enumeration<EdArc> e1 = eg.getArcs().elements();
			while (e1.hasMoreElements()) {
				EdArc a1 = e1.nextElement();

				if (a1.getSource() == a1.getTarget())
					continue;
				if (!a1.getBasisArc().isDirected())
					continue;

				if (a != a1) {
					if (a.getType().getName().equals(a1.getType().getName())
							&& (a.getSource() == a1.getTarget())
							&& (a.getTarget() == a1.getSource())) {
						a.getBasisArc().setVisible(false);
						a1.getBasisArc().setDirected(false);
						break;
					}
				}
			}
		}
	}

	private void layoutGraph(EdGraph eg) {
		replaceBidirectedEdgesByUndirectedEdge(eg);
		if (!eg.hasDefaultLayout())
			makeLayout(eg);
	}

	private void storeVisArcs(Graph g) {
		this.visArcs.clear();
		Iterator<Arc> e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			if (!a.isDirected() && a.isVisible()) {
				Arc a1 = getEdge(g, a.getType(), (Node) a.getTarget(), (Node) a
						.getSource());
				if (a1 != null && !this.visArcs.contains(a1))
					this.visArcs.add(a1);
			}
		}
	}

	private Node showNode(Graph g, String tn, Rule r,
			boolean changePairContainer) {
		Type t = g.getTypeSet().getTypeByName(tn);
		if (t == null)
			return null;
		return showNode(g, t, r, changePairContainer);
	}

	private Node showNode(Graph g, Type t, Rule r, boolean changePairContainer) {
		Node n = getNode(g, r);
		if (n != null && !n.isVisible()) {
			n.setVisible(true);
			/*
			 * if(changePairContainer){ if(this.conflictCont != null)
			 * this.conflictCont.setEntryRuleVisible(r,r,true, false); if(this.dependCont !=
			 * null) this.dependCont.setEntryRuleVisible(r,r,true, false);
			 * //if(this.graphDesktop != null) this.graphDesktop.refresh(); }
			 */
		}
		return n;
	}

	private Node createNode(Graph g, Type t, Rule r) {
		Node n = getNode(g, r);
		if (n == null) {
			try {
				n = g.createNode(t);
				ValueTuple vt = (ValueTuple) n.getAttribute();
				String rname = r.getQualifiedName();
				vt.getValueMemberAt("name").setExprAsObject(rname);
			} catch (TypeException e) {
			}
		}
		return n;
	}

	private Node createNode(Graph g, String tn, Rule r) {
		Type t = g.getTypeSet().getTypeByName(tn);
		return createNode(g, t, r);
	}

	private void createLayoutNode(EdGraph g, String tn, Rule r) {
		Type t = g.getBasisGraph().getTypeSet().getTypeByName(tn);
		Node n = createNode(g.getBasisGraph(), t, r);
		EdType et = g.getTypeSet().getNodeType(t);
		if (n != null && g.findNode(n) == null) {
			g.addNode(n, et);
//			g.setTransformChangeEnabled(true);
		}
	}
	
	private Node getNode(Graph g, Rule r) {
		Iterator<Node> e = g.getNodesSet().iterator();
		while (e.hasNext()) {
			Node n = e.next();
			if (((String) n.getAttribute().getValueAt("name")).equals(r
					.getQualifiedName()))
				return n;
		}
		return null;
	}

	private void hideNode(Graph g, Rule r, boolean changePairContainer) {
		if ((this.conflictCont != null) && (this.dependCont != null)) {
			ExcludePairContainer.Entry entryCC = this.conflictCont.getEntry(r, r);
			ExcludePairContainer.Entry entryDC = this.dependCont.getEntry(r, r);

			if (this.conflictAction) {
				if ((entryDC.getState() == ExcludePairContainer.Entry.COMPUTED
						|| entryDC.getState() == ExcludePairContainer.Entry.COMPUTED2
						|| entryDC.getState() == ExcludePairContainer.Entry.COMPUTED12)
						&& entryDC.isRuleVisible()) { 
					return;				
				}
			} else if ((entryCC.getState() == ExcludePairContainer.Entry.COMPUTED)
						&& entryCC.isRuleVisible()) {
					return;
			}
		}

		Node n = getNode(g, r);
		if (n != null && n.isVisible()) {
			n.setVisible(false);
			/*
			 * if(changePairContainer){ if(this.conflictAction)
			 * this.dependCont.setEntryRuleVisible(r,r,false); else
			 * this.conflictCont.setEntryRuleVisible(r,r,false); //if(this.graphDesktop !=
			 * null) this.graphDesktop.refresh(); }
			 */
		}
	}

	
	/*private void hideNodeContext(Graph g, Node n) {
		Iterator<Arc> e = n.getIncomingArcsSet().iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			// a.setVisible(false);
			if (a.isVisible())
				hideEdge(g, a.getType(), (Node) a.getSource(), (Node) a
						.getTarget());
		}
		e = n.getOutgoingArcsSet().iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			// a.setVisible(false);
			if (a.isVisible())
				hideEdge(g, a.getType(), (Node) a.getSource(), (Node) a
						.getTarget());
		}
	}
*/
	
	private Arc showEdge(Graph g, String tn, Rule r1, Rule r2) {
		Type t = g.getTypeSet().getTypeByName(tn);
		if (t == null)
			return null;

		Node n1 = getNode(g, r1);
		Node n2 = getNode(g, r2);
		return showEdge(g, t, n1, n2);
	}

	private Arc showEdge(Graph g, Type t, Node n1, Node n2) {
		if (t == null || n1 == null || n2 == null)
			return null;
		Arc a = getEdge(g, t, n1, n2);
		if (a != null /* && !a.isVisible() && !this.visArcs.contains(a) */) {
			a.setVisible(true);
			a.setDirected(true);
			if (a.getSource() == a.getTarget())
				return a;
			
			Arc a1 = getEdge(g, t, n2, n1);
			if (a1 != null) {
				if (a1.isVisible()) {
					a1.setVisible(false);
					if (!this.visArcs.contains(a1))
						this.visArcs.add(a1);
					a.setDirected(false);
				} else if (this.visArcs.contains(a1))
					a.setDirected(false);
				else if (this.visArcs.contains(a))
					this.visArcs.remove(a);
			}	
		}
		return a;
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
						if (!this.visArcs.contains(a1))
							this.visArcs.add(a1);
					}
				}
			} catch (TypeException e) {}
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

	private Arc createEdge(Graph g, String tn, Rule r1, Rule r2) {
		Type t = g.getTypeSet().getTypeByName(tn);
		return createEdge(g, t, r1, r2);
	}

	private void createLayoutEdge(EdGraph g, String tn, Rule r1, Rule r2) {
		Type t = g.getBasisGraph().getTypeSet().getTypeByName(tn);
		Arc a = createEdge(g.getBasisGraph(), t, r1, r2);
		EdType et = g.getTypeSet().getArcType(t);
		if (a != null && g.findArc(a) == null) {
			try {
				g.addArc(a, et);
//				g.setTransformChangeEnabled(true);
			} catch (TypeException ex) {}
		}
	}
	
	private Arc getEdge(Graph g, String tn, Rule r1, Rule r2) {
		Type t = g.getTypeSet().getTypeByName(tn);
		Node n1 = getNode(g, r1);
		Node n2 = getNode(g, r2);
		return getEdge(g, t, n1, n2);
	}

	@SuppressWarnings("unused")
	private void removeEdge(Graph g, String tn, Rule r1, Rule r2) {
		Type t = g.getTypeSet().getTypeByName(tn);
		Arc a = getEdge(g, t, r1, r2);
		if (this.visArcs.contains(a))
			this.visArcs.remove(a);
		if (a != null) {
			try {
				g.destroyArc(a, true, false);
				
				Arc a1 = getEdge(g, t, r2, r1);
				if (a1 != null) {
					if (!a1.isVisible()) {
						a1.setVisible(true);
						a1.setDirected(true);
						this.visArcs.add(a1);
					} 
				}
			} catch (TypeException exc) {
			}
		}
	}

	private void removeLayoutEdge(EdGraph g, String tn, Rule r1, Rule r2) {
		Type t = g.getBasisGraph().getTypeSet().getTypeByName(tn);
		Arc a = getEdge(g.getBasisGraph(), t, r1, r2);
		if (a != null) {
			if (this.visArcs.contains(a))
				this.visArcs.remove(a);
			try {
				g.delArc(a);
				
				Arc a1 = getEdge(g.getBasisGraph(), t, r2, r1);
				if (a1 != null) {
					if (!a1.isVisible()) {
						a1.setVisible(true);
						a1.setDirected(true);
						this.visArcs.remove(a1);
						
						EdType et = g.getTypeSet().getArcType(t);
						if (g.findArc(a1) == null)
							g.addArc(a1, et);
					}
					else {
						a1.setDirected(true);
						EdType et = g.getTypeSet().getArcType(t);
						if (g.findArc(a1) == null)
							g.addArc(a1, et);
					}
				} 
			} catch (TypeException exc) {}
		}
	}
	
	private Arc getEdge(Graph g, Type t, Rule r1, Rule r2) {
		Node n1 = getNode(g, r1);
		Node n2 = getNode(g, r2);
		return getEdge(g, t, n1, n2);
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

	private boolean hideEdge(Graph g, String tn, Rule r1, Rule r2) {
		Type t = g.getTypeSet().getTypeByName(tn);
		if (t == null)
			return false;
		Node n1 = getNode(g, r1);
		Node n2 = getNode(g, r2);
		return hideEdge(g, t, n1, n2);
	}

	private boolean hideEdge(Graph g, Type t, Node n1, Node n2) {
		if (t == null || n1 == null || n2 == null)
			return false;
		Arc a = getEdge(g, t, n1, n2);
		if (a != null /* && a.isVisible() */) {
			a.setVisible(false);
			if (a.getSource() == a.getTarget())
				return true;
			
			if (this.visArcs.contains(a))
				this.visArcs.remove(a);
			Arc a1 = getEdge(g, t, n2, n1);
			if (a1 != null) {
				if (a1.isVisible())
					a1.setDirected(true);
				else if (this.visArcs.contains(a1)) {
					this.visArcs.remove(a1);
					a1.setDirected(true);
					a1.setVisible(true);
				} 
//				else {
//					// a1.setVisible(false); this.visArcs.add(a1);
//				}
			}			
		}
		return false;
	}

/*
	private void hideGraphObject() {
		Vector<EdGraphObject> v = this.cpaLayout.getSelectedObjs();
		for (int i = 0; i < v.size(); i++) {
			EdGraphObject go = v.elementAt(i);
			go.setSelected(false);
			hiddenGraphObject = true;
			go.getBasisObject().setVisible(false);
			if (go.isNode()) {
				Iterator<Arc> e = ((Node) go.getBasisObject()).getOutgoingArcsSet().iterator();
				while (e.hasNext()) {
					e.next().setVisible(false);
				}
				e = ((Node) go.getBasisObject()).getIncomingArcsSet().iterator();
				while (e.hasNext()) {
					e.next().setVisible(false);
				}
			}
		}
	}
*/
	
	private void hideGraphObject(EdGraphObject go) {
		if (go != null) {
			this.hiddenGraphObject = true;
			go.getBasisObject().setVisible(false);
			if (go.isNode()) {
				Iterator<Arc> e = ((Node) go.getBasisObject()).getOutgoingArcsSet().iterator();
				while (e.hasNext()) {
					e.next().setVisible(false);
				}
				e = ((Node) go.getBasisObject()).getIncomingArcsSet().iterator();
				while (e.hasNext()) {
					e.next().setVisible(false);
				}
			}
		}
	}
	
	private Rule getRule(Node n) {
		ValueTuple vt = (ValueTuple) n.getAttribute();
		String rname = (String) (vt.getValueMemberAt("name")).getExprAsObject();
		return this.grammar.getRule(rname);
	}

	private void straightEdges(EdGraph g) {
		Enumeration<EdArc> e = g.getArcs().elements();
		while (e.hasMoreElements()) {
			EdArc ea = e.nextElement();
			g.straightArc(ea);
			/*
			 * Node src = (Node)ea.getBasisArc().getSource(); Node tar =
			 * (Node)ea.getBasisArc().getTarget();
			 * if(ea.getBasisArc().getType().getName().equals("c")){ Type t =
			 * g.getBasisGraph().getTypeSet().getTypeForName("d"); Arc a1 =
			 * getEdge(g.getBasisGraph(), t, src, tar); Arc a2 =
			 * getEdge(g.getBasisGraph(), t, tar, src); }
			 */
		}
		g.update();
	}

	private void changeStyleOfEdges(EdGraph g, String edgeTypeName, int style) {
		Vector<EdType> arctypes = g.getTypeSet().getArcTypes();
		for (int i = 0; i < arctypes.size(); i++) {
			EdType et = arctypes.get(i);
			if (et.getBasisType().getName().equals(edgeTypeName)) {
				et.setShape(style);
				et.setAdditionalReprOfBasisType();
			}
		}
	}

	private void makeLayout(EdGraph g) {
		g.forceVisibilityUpdate();
		final List<EdNode> visiblenodes = g.getVisibleNodes();

		g.setCurrentLayoutToDefault(false);
		g.getDefaultGraphLayouter().setEnabled(true);
		Dimension dim = g.getDefaultGraphLayouter().getNeededPanelSize(visiblenodes);
		if (dim.width < 550)
			dim.width = 550;
		if (dim.height < 450)
			dim.height = 450;
		g.getDefaultGraphLayouter().setPanelSize(dim);		
		g.doDefaultEvolutionaryGraphLayout(g.getDefaultGraphLayouter(),
						100, 10);
	}
	
	private void makeLayout(EdGraph g, Dimension dim) {
		g.forceVisibilityUpdate();
		g.setCurrentLayoutToDefault(false);
		g.getDefaultGraphLayouter().setEnabled(true);
		if (dim.width < 550)
			dim.width = 550;
		if (dim.height < 450)
			dim.height = 450;
		g.getDefaultGraphLayouter().setPanelSize(dim);
		g.doDefaultEvolutionaryGraphLayout(g.getDefaultGraphLayouter(),
						100, 10);
	}
}
