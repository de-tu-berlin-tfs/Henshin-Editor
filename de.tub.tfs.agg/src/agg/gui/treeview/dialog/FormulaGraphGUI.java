/**
 * Title:        AGG<p>
 * Description:  <p>
 * Company:      TU Berlin<p>
 * @author Olga Runge
 * @version 1.0
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import agg.cons.AtomConstraint;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.editor.impl.EdArc;
import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdType;
import agg.editor.impl.EdTypeSet;
import agg.gui.AGGAppl;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraphEditor;
import agg.gui.saveload.GraphicsExportJPEG;
import agg.layout.GraphLayouts;
import agg.layout.ZestGraphLayout;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.Node;
import agg.xt_basis.TypeException;

/*
 * Formula GUI dialog allows to edit a boolean formula above some evaluable objects.<br>
 * The formula is represented by a binary tree graph.<br>
 * It can be edited using the graph background and node pop-up menus.
 * To hold the graph more compact some simplifications are used <br>
 * (Exmpl.: <code>ac</code> is equivalent to <code>EXISTS(ac, true)</code>, 
 * <code>!ac</code> is equivalent to <code>NOT EXISTS(ac, true)</code>).
 * An empty graph means a binary tree graph in which 
 * all evaluable objects are connected by \"AND\"."
 * 
 */
public class FormulaGraphGUI extends JDialog implements ActionListener, MouseListener  {
	
	static final int OP = 0;
	static final int OPRND = 1;
	static final int TRUE = 2;
	static final int FALSE = TRUE; 
	
	final JButton apply, cancel, clear, layout;
	final JPanel dialogPanel;
	JScrollPane scrollPane;
	JFrame parFrame;
	boolean changed, canceled;	
	String formula, f;
	Formula tmpF;
	final List<Object> objs = new Vector<Object>(5,1);
	final HashMap<String,Integer> name2indx = new HashMap<String,Integer>();	
	final GraphEditor gege;
	final EdGraph fgraph;
	final List<EdNode> subNodes = new Vector<EdNode>(5,1);
	final List<EdType> op2type = new Vector<EdType>();
	final Hashtable<JMenuItem,EdType> oprnd2type = new Hashtable<JMenuItem,EdType>(5,0.3f);
	final Hashtable<EdType,Object> type2obj = new Hashtable<EdType,Object>(5,0.3f);
	final JPopupMenu commonMenu = new JPopupMenu("");
	final JPopupMenu oprndMenu = new JPopupMenu("");
	final JPopupMenu delMenu = new JPopupMenu("");
	final JPopupMenu layoutMenu = new JPopupMenu("");
	JMenuItem miDel, miRefGraph;
	boolean forallDisabled=false;
	JMenuItem miForall;
	EdNode topNode, node;
	EdType edgeType, refEdgeType;
	boolean refined;
	
	public FormulaGraphGUI(JFrame parent, 
			String nameOfOwner, 
			String title, 
			String helpStr,
			boolean modal) {
		super(parent, title, modal);

		this.parFrame = parent;
		this.formula = "";
		this.f = "";
		
		// make info panel
		JPanel info = new JPanel(new GridLayout(3,0));
		if (nameOfOwner != null && !"".equals(nameOfOwner)) {
			JLabel l1 = new JLabel(" The owner of this formula is the "+nameOfOwner);
			l1.setForeground(Color.BLUE);
			info.add(l1);
		}
		JLabel l2 = new JLabel(" Use the bg and node pop-up menus to edit the binary tree graph of the formula.");
		info.add(l2);
		JLabel l3 = new JLabel(helpStr);
		info.add(l3);
		
		// make a graph editor to edit a formula graph
		this.gege = new GraphEditor();
//		this.gege.setPreferredSize(new Dimension(300,300));
		((JPanel) this.gege.getGraphPanel().getCanvas()).addMouseListener(this);
		this.gege.setEditMode(agg.gui.editor.EditorConstants.MOVE);
		this.gege.getGraphPanel().getCanvas().setEdgeAnchorVisible(false);
		this.gege.setTitle("   ");
		this.fgraph = new EdGraph(new Graph());
		this.fgraph.getBasisGraph().setName("    ");
		createOpTypes(this.fgraph.getTypeSet());
		createEdgeTypes(this.fgraph.getTypeSet());
		
		this.miDel = this.addDelete(this.delMenu);	
		addLayout(this.layoutMenu);
		
		// make buttons panel
		JPanel buttons = new JPanel(new GridBagLayout());
		this.layout = new JButton("Layout");
		this.layout.setToolTipText("Tree Graph Layout");
		this.layout.addActionListener(this);
		this.clear = new JButton("Clear");
		this.clear.addActionListener(this);
		this.apply = new JButton("Apply");
		this.apply.addActionListener(this);
		this.cancel = new JButton("Cancel");
		this.cancel.addActionListener(this);
		constrainBuild(buttons, this.clear, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 5, 15);
//		constrainBuild(buttons, layout, 1, 0, 1, 1, GridBagConstraints.BOTH,
//				GridBagConstraints.CENTER, 1.0, 0.0, 5, 15, 5, 15);
		constrainBuild(buttons, this.apply, 2, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 15, 5, 15);
		constrainBuild(buttons, this.cancel, 3, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 15, 5, 10);
						
		// make common dialog panel
		this.dialogPanel = new JPanel(new GridBagLayout()); 
		this.dialogPanel.setPreferredSize(new Dimension(200, 200));
		
		constrainBuild(this.dialogPanel, info, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(this.dialogPanel, this.gege, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 1.0, 5, 5, 5, 5);
		constrainBuild(this.dialogPanel, buttons, 0, 2, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		
		getContentPane().setLayout(new BorderLayout());
		scrollPane = new JScrollPane(this.dialogPanel);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // HIDE_ON_CLOSE
		this.pack();
	}

	public void setVisible(boolean b) {
		this.doZestTreeLayout(this.fgraph);
		super.setVisible(b);	
	}
	
	public void setExportJPEG(final GraphicsExportJPEG jpg) {
		if (this.gege != null)
			this.gege.setExportJPEG(jpg);
	}
	
	/**
	 * Disable the <code>FORALL</code> menu item.
	 */
	public void disableFORALL(boolean b) {
		this.forallDisabled = b;
	}
		
	public void doZestTreeLayout(final EdGraph g) {
		String algorithm = GraphLayouts.TREE_VERTICAL_LAYOUT;
		if (this.parFrame != null && this.parFrame instanceof AGGAppl) {
			ZestGraphLayout zestGL = ((AGGAppl)this.parFrame).getGraGraEditor().getZestGraphLayouter();
			if (zestGL != null && !g.isEmpty()) {
				zestGL.setGraph(g);
				zestGL.setAlgorithm(algorithm);
				zestGL.setGraphDimension(
						new Dimension(this.gege.getGraphPanel().getCanvas().getSize().width-20,
										this.gege.getGraphPanel().getCanvas().getSize().height-20));
//				zestGL.setGraphDimension(new Dimension(400,800));
				if (zestGL.applyLayout())
					this.gege.updateGraphics(true);
			}
		}
	}
	
	/**
	 * Implements actions of buttons: Layout, Clear, Apply, Cancel
	 * and of operation buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Layout")) {
			doZestTreeLayout(this.fgraph);
		}
		else if (e.getActionCommand().equals("Clear")) {
			clear();
		} 
		else if (e.getActionCommand().equals("Apply")) {
			setFormulaText();
			if ("".equals(this.formula)) {
				this.f = "";
			} 
			this.changed = true;
			setVisible(false);	
		} 
		else if (e.getActionCommand().equals("Cancel")) {
			setVisible(false);
			this.canceled = true;
		} 
	}

	/**
	 * Return the formula string with indexes of GACs ( not the names!).
	 * This formula string is then used to create a new Formula instance for the given list of GACs.
	 */
	public String getFormula() {
		if ("".equals(this.f))
			return "true";
		else
			return this.f;
	}
	
	public EdGraph getFormulaGraph() {
		return this.fgraph;	
	}
	
	public boolean isChanged() {
		return this.changed;
	}
	
	public boolean isCanceled() {
		return this.canceled;
	}
	
	/**
	 * Set the lists of evaluable objects to be used as variables of the specified formula string.
	 * All needed internal structures will be rebuild.
	 */
	public void setVars(List<String> vars, List<Evaluable> varObjs, String formulaStr) {
		this.commonMenu.removeAll();
		this.oprndMenu.removeAll();
		
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < vars.size(); i++) {
			String name = vars.get(i);
			
			s.add(name);
			this.objs.add(name);
			
			EdType t = this.createOprndNodeType(this.fgraph.getTypeSet(), name);
			addOprndNodeType(this.commonMenu, t, i+1);
			addOprndNodeType(this.oprndMenu, t, i+1);			
			this.name2indx.put(t.getTypeName(), Integer.valueOf(i+1));
		}
		
		this.commonMenu.addSeparator();
		this.commonMenu.addSeparator();
		this.addOpNodeTypes(this.commonMenu);
		this.commonMenu.addSeparator();
		this.commonMenu.addSeparator();
		this.addDelete(this.commonMenu);
		
		this.oprndMenu.addSeparator();
		this.oprndMenu.addSeparator();
		this.addDelete(this.oprndMenu);
		
		if (s.isEmpty()) {
			clear();
		} else {
			this.formula = formulaStr;
			fillFromString(formulaStr);
			
			Formula form = new Formula(varObjs, this.f);
			formula2graph(form, this.fgraph);
		}
		this.gege.setGraph(this.fgraph);
	}
		
	/**
	 * Set the list of GACs to be used as variables of the specified formula string.
	 * All needed internal structures will be rebuild.
	 * Note: The elements of the objList can be of the type <code>EdNestedApplCond</code> or 
	 * <code>EdAtomic</code> only.
	 */
	public void setVarsAsObjs(List<?> objList, String formulaStr) {		
		this.commonMenu.removeAll();
		this.oprndMenu.removeAll();
		
		boolean allowRefGraph = false;
		Vector<Evaluable> vec = new Vector<Evaluable>();
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < objList.size(); i++) {
			if (objList.get(i) instanceof EdNestedApplCond) {
				allowRefGraph = true;
				EdNestedApplCond obj = (EdNestedApplCond) objList.get(i);
				
				vec.add(obj.getNestedMorphism());			
				s.add(obj.getNestedMorphism().getName());
				this.objs.add(obj.getNestedMorphism());
				
				EdType t = this.createOprndNodeType(this.fgraph.getTypeSet(), obj.getNestedMorphism().getName());
				if (t != null) {
					addOprndNodeType(this.commonMenu, t, i+1);
					addOprndNodeType(this.oprndMenu, t, i+1);			
					this.name2indx.put(t.getTypeName(), Integer.valueOf(i+1));
					this.type2obj.put(t, obj.getNestedMorphism());
				}
			}
			else if (objList.get(i) instanceof EdAtomic) {
				EdAtomic obj = (EdAtomic) objList.get(i);
				
				vec.add(obj.getBasisAtomic());			
				s.add(obj.getBasisAtomic().getAtomicName());
				this.objs.add(obj.getBasisAtomic());
				
				EdType t = this.createOprndNodeType(this.fgraph.getTypeSet(), obj.getBasisAtomic().getAtomicName());
				if (t != null) {
					addOprndNodeType(this.commonMenu, t, i+1);
					addOprndNodeType(this.oprndMenu, t, i+1);			
					this.name2indx.put(t.getTypeName(), Integer.valueOf(i+1));
					this.type2obj.put(t, obj.getBasisAtomic());
				}
			}
		}
		
		this.commonMenu.addSeparator();
		this.commonMenu.addSeparator();
		
		this.addOpNodeTypes(this.commonMenu);
		
		this.commonMenu.addSeparator();
		this.commonMenu.addSeparator();
		this.addDelete(this.commonMenu);
		
		this.oprndMenu.addSeparator();
		this.oprndMenu.addSeparator();
		this.addDelete(this.oprndMenu);
		
		if (allowRefGraph) {
			this.miRefGraph(this.delMenu);
		}
		
		if (s.isEmpty()) {
			clear();
		} else {
			this.formula = formulaStr;
			fillFromString(formulaStr);		
			
			Formula form = new Formula(vec, this.f);
			formula2graph(form, this.fgraph);
		}
		this.gege.setGraph(this.fgraph);
	}

	private List<EdNode> addRefGraphOf(final Formula form, final EdNode refNode) {
		List<EdNode> list = new Vector<EdNode>(5,1);
		
		EdGraph refGraph = new EdGraph(new Graph());
		this.createOpTypes(refGraph.getTypeSet());
		// create an edge type to connect nodes
		refGraph.getTypeSet().createArcType("", EditorConstants.SOLID, Color.BLACK);	
		
		this.formula2graph(form, refGraph);
		
		Hashtable<EdGraphObject,EdGraphObject> go2go = new Hashtable<EdGraphObject,EdGraphObject>();
		int x = refNode.getX();
		int y = refNode.getY()+40;
		Vector<EdNode> v = refGraph.getNodes();
		for (int i=0; i<v.size(); i++) {
			EdNode go = v.get(i);
			EdType t = this.fgraph.getTypeSet().getTypeForName(go.getTypeName());
			if (t == null)
				t = this.fgraph.getTypeSet().createNodeType(go.getTypeName(), EditorConstants.RECT, Color.BLUE);
			try {
				EdNode n1 = this.fgraph.addNode(x, y, t, true);
				n1.getBasisNode().setContextUsage(go.getBasisNode().getContextUsage());
				this.subNodes.add(n1);
				if (this.type2obj.get(go.getType()) != null)
					this.type2obj.put(t, this.type2obj.get(go.getType()));
					
				if (go.getInArcsCount() == 0) { // top of refinement formula graph
					EdArc ref = this.fgraph.addArc(this.refEdgeType, refNode, n1, null, true);
					ref.getBasisArc().setContextUsage(-1);
					this.refined = true;
				}
				if (go.getOutArcsCount() == 0)
					list.add(n1);
				go2go.put(go, n1);
			} catch (TypeException ex) {}
		}
		Vector<EdArc> v1 = refGraph.getArcs();
		for (int i=0; i<v1.size(); i++) {
			EdArc go = v1.get(i);
			EdNode src = (EdNode)go2go.get(go.getSource());
			EdNode tar = (EdNode)go2go.get(go.getTarget());
			int dx = go.getTarget().getX() - go.getSource().getX();
			int dy = go.getTarget().getY() - go.getSource().getY();
			tar.setXY(src.getX()+dx, src.getY()+dy);
			try {
				this.fgraph.addArc(this.edgeType, src, tar, null, true);
			} catch (TypeException ex) {}
		}
		
		return list;
	}
	
	/**
	 * Build formula-graph from the given Formula object.
	 */
	private void formula2graph(final Formula form, final EdGraph graph) {
		String tname = null;
		EdNode n = null;
		EdType t = null;
		int op = form.getOperation();
		switch (op) {
			case Formula.NOP:
				if (form.getFirst() != null) {					
					if (form.getFirst() instanceof Formula) {					
						formula2graph((Formula) form.getFirst(), graph);
					} 
					else if (form.getFirst() instanceof NestedApplCond) {						
						tname = ((NestedApplCond) form.getFirst()).getName();
						t = graph.getTypeSet().getNodeTypeForName(tname);
						if (t == null) {
							t = this.createOprndNodeType(graph.getTypeSet(), tname);
							this.objs.add(form.getFirst());
							this.type2obj.put(t, form.getFirst());
						}
						
						n = addNode(graph, t, OPRND);
						this.type2obj.put(t, form.getFirst());
					} 
					else if (form.getFirst() instanceof AtomConstraint) {
						tname = ((AtomConstraint) form.getFirst()).getAtomicName();
						t = graph.getTypeSet().getNodeTypeForName(tname);
						if (t == null) {
							t = this.createOprndNodeType(graph.getTypeSet(), tname);
							this.objs.add(form.getFirst());
							this.type2obj.put(t, form.getFirst());
						}
						n = addNode(graph, t, OPRND);
					}
				} 
				break;
			case Formula.NOT: 
				if (form.getFirst() != null) {
					t = graph.getTypeSet().getNodeTypeForName("NOT");
					n = addNode(graph, t, OP);
					if (n != null) {
						this.node = n;
					
						if (form.getFirst() instanceof Formula) {
							formula2graph((Formula) form.getFirst(), graph);
						} else if (form.getFirst() instanceof NestedApplCond) {
							tname = ((NestedApplCond) form.getFirst()).getName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						} else if (form.getFirst() instanceof AtomConstraint) {
							tname = ((AtomConstraint) form.getFirst()).getAtomicName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						}
					}
				} 
				break;
			case Formula.AND:
				t = graph.getTypeSet().getNodeTypeForName("AND");
				n = addNode(graph, t, OP);
				if (n != null) {
					this.node = n;
					
					if (form.getFirst() != null) {	
						if (form.getFirst() instanceof Formula) {
							formula2graph((Formula) form.getFirst(), graph);
							this.node = n;
						} else if (form.getFirst() instanceof NestedApplCond) {
							tname = ((NestedApplCond) form.getFirst()).getName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						} else if (form.getFirst() instanceof AtomConstraint) {
							tname = ((AtomConstraint) form.getSecond()).getAtomicName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						}
					}
					if (form.getSecond() != null) {					
						if (form.getSecond() instanceof Formula) {
							formula2graph((Formula) form.getSecond(), graph);
						} else if (form.getSecond() instanceof NestedApplCond) {
							tname = ((NestedApplCond) form.getSecond()).getName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						} else if (form.getSecond() instanceof AtomConstraint) {
							tname = ((AtomConstraint) form.getSecond()).getAtomicName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						}
					} 
				}
				break;
			case Formula.OR:
				t = graph.getTypeSet().getNodeTypeForName("OR");
				n = addNode(graph, t, OP);
				if (n != null) {
					this.node = n;
					
					if (form.getFirst() != null) {
						if (form.getFirst() instanceof Formula) {
							formula2graph((Formula) form.getFirst(), graph);
							this.node = n;
						} else if (form.getFirst() instanceof NestedApplCond) {
							tname = ((NestedApplCond) form.getFirst()).getName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						} else if (form.getFirst() instanceof AtomConstraint) {
							tname = ((AtomConstraint) form.getFirst()).getAtomicName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						}
					}
					if (form.getSecond() != null) {
						if (form.getFirst() instanceof Formula) {
							formula2graph((Formula) form.getSecond(), graph);
						} else if (form.getSecond() instanceof NestedApplCond) {
							tname = ((NestedApplCond) form.getSecond()).getName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						} else if (form.getSecond() instanceof AtomConstraint) {
							tname = ((AtomConstraint) form.getSecond()).getAtomicName();
							t = graph.getTypeSet().getNodeTypeForName(tname);
							if (t == null) {
								t = this.createOprndNodeType(graph.getTypeSet(), tname);
							}
							n = addNode(graph, t, OPRND);
						}
					}
				}
				break;
			case Formula.FORALL: 
				if (form.getFirst() != null) {
					t = graph.getTypeSet().getNodeTypeForName("FORALL");
					n = addNode(graph, t, OP);
					if (n != null)
						this.node = n;
					
					if (form.getFirst() instanceof Formula) {
						formula2graph((Formula) form.getFirst(), graph);
					} 
					else if (form.getFirst() instanceof NestedApplCond) {
						tname = ((NestedApplCond) form.getFirst()).getName();
						t = graph.getTypeSet().getNodeTypeForName(tname);
						if (t == null) {
							t = this.createOprndNodeType(graph.getTypeSet(), tname);
						}
						n = addNode(graph, t, OPRND);
					} else if (form.getFirst() instanceof AtomConstraint) {
						tname = ((AtomConstraint) form.getFirst()).getAtomicName();
						t = graph.getTypeSet().getNodeTypeForName(tname);
						if (t == null) {
							t = this.createOprndNodeType(graph.getTypeSet(), tname);
						}
						n = addNode(graph, t, OPRND);
					}
				} 
				break;
			case Formula.TRUE:	
				t = graph.getTypeSet().getNodeTypeForName(tname);
				if (t == null) {
					String st = "true";
					t = this.createOpNodeType(graph.getTypeSet(), st);
				}
				n = addNode(graph, t, TRUE);
				break;
			case Formula.FALSE:
				t = graph.getTypeSet().getNodeTypeForName(tname);
				if (t == null) {
					String sf = "false";
					t = this.createOpNodeType(graph.getTypeSet(), sf);
				}
				n = addNode(graph, t, FALSE);
				break;
		}	
	}
		
	/**
	 * Extend working formula string.
	 * If i=-1, then concatenate s, otherwise - i.
	 */
	private void add2formula(String s, int i) {
		if (i == -1) {
			this.f = this.f.concat(s);
		}
		else {
			this.f = this.f.concat(String.valueOf(i));
		}
	}
	
	/**
	 * Empty graph and formula string;
	 */
	void clear() {
		try {
			this.fgraph.deleteAll();
			this.gege.updateGraphics();
		} catch (TypeException ex) {}
		

		this.f = "";
		this.formula = "";
		this.topNode = null;
		this.node = null;
	}
	
	
	/**
	 * Set working formula string.
	 */
	private void fillFromString(String str) {
		String s = str.replaceAll(" ", "");
		String s2 = s.replace("(T)", "T");
		while (s2.indexOf("(T)") >= 0) {
			String s3 = s2.replace("(T)", "T");
			s2 = s3;
		}
		s = s2;
		
		StringCharacterIterator i = new StringCharacterIterator(s);
		char c = i.current();
		while (c != CharacterIterator.DONE) {
			if (c == '&' || c == '|' 
					|| c == '!' || c == '$'
					|| c == 'A' || c == 'E' 
					|| c == 'T' || c == 'F' 
					|| c == ' ' || c == ','
					|| c == '(' || c == ')') {
				add2formula(String.valueOf(c), -1);
				
				i.next();
			} else if (c >= '0' && c <= '9') {
				String cs = "";
				int v = 0;
				while (c >= '0' && c <= '9') {
					cs = cs.concat(String.valueOf(c));
					v = v * 10 + (c - '0');
					c = i.next();
				}
				v--;
				if (v < 0 /*|| v >= list.getModel().getSize()*/)
					return;
				
				int num = Integer.valueOf(cs).intValue();
				if (this.objs.size() > 0 && ((num-1) < this.objs.size())) {
					Object obj = this.objs.get(num-1);
					if (obj instanceof String)
						add2formula((String) obj, num);
					else if (obj instanceof NestedApplCond) {
						add2formula(((NestedApplCond)obj).getName(), num);
					}
					else if (obj instanceof AtomConstraint) {
						add2formula(((AtomConstraint)obj).getAtomicName(), num);
					}
				}
			} else if (c == 'f' || c == 't') {
				String cs = String.valueOf(c);
				char c1 = c;
				while (i.current() >= 'a' && i.current() <= 'z') {
					c1 = i.next();
					if (c1 != CharacterIterator.DONE) {
						cs = cs.concat(String.valueOf(c1));
					}
				}
				add2formula(String.valueOf(cs), -1);
			} 			
			c = i.current();			
		}
	}
	
	private void constrainBuild(Container container, Component component,
			int grid_x, int grid_y, int grid_width, int grid_height, int fill,
			int anchor, double weight_x, double weight_y, int top, int left,
			int bottom, int right) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = grid_x;
		c.gridy = grid_y;
		c.gridwidth = grid_width;
		c.gridheight = grid_height;
		c.fill = fill;
		c.anchor = anchor;
		c.weightx = weight_x;
		c.weighty = weight_y;
		c.insets = new Insets(top, left, bottom, right);
		((GridBagLayout) container.getLayout()).setConstraints(component, c);
		container.add(component);
	}
	
	/**
	 * Create <code>operation</code> types.
	 */
	private void createOpTypes(final EdTypeSet types) {
		// create node types which represent operators of a formula
		EdType t = types.createNodeType("NOT", EditorConstants.ROUNDRECT, Color.RED);
		this.op2type.add(t);
		
		t = types.createNodeType("AND", EditorConstants.ROUNDRECT, Color.BLACK);
		this.op2type.add(t);
		
		t = types.createNodeType("OR", EditorConstants.ROUNDRECT, Color.BLACK);
		this.op2type.add(t);
				
		t = types.createNodeType("FORALL", EditorConstants.ROUNDRECT, Color.BLACK);
		this.op2type.add(t);
	}
	
	private EdType createOpNodeType(final EdTypeSet types, String name) {
		EdType t = types.createNodeType(name, EditorConstants.RECT, Color.BLACK);
		return t;
	}
	
	private void createEdgeTypes(final EdTypeSet types) {
		// create an edge type to connect nodes
		if (this.edgeType == null)
			this.edgeType = types.createArcType("", EditorConstants.SOLID, Color.BLACK);	
		// create an edge type to connect refinement nodes
		if (this.refEdgeType == null)
			this.refEdgeType = types.createArcType("", EditorConstants.SOLID, Color.BLUE);	
	}
	
	/**
	 * Create a new type with the specified name which corresponds to the name 
	 * of the appropriate GAC.
	 */
	private EdType createOprndNodeType(final EdTypeSet types, String name) {
		EdType t = types.createNodeType(name, EditorConstants.RECT, Color.BLUE);
		return t;
	}
	
	/**
	 * Add a new <code>GAC</code> menu item to the specified pop-up menu.
	 * The name of the specified type is the name of an appropriate GAC.
	 */
	private JMenuItem addOprndNodeType(final JPopupMenu m, final EdType t, int indx) {
		JMenuItem mi = m.add(new JMenuItem(t.getName()));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.valueOf(((JMenuItem)e.getSource()).getActionCommand()).intValue();
					addNode(FormulaGraphGUI.this.fgraph, FormulaGraphGUI.this.oprnd2type.get(e.getSource()), OPRND);
				} catch (NumberFormatException ex) {
					addNode(FormulaGraphGUI.this.fgraph, FormulaGraphGUI.this.oprnd2type.get(e.getSource()), OP);
				}
			}
		});
		
		if ("FORALL".equals(t.getName()))
			this.miForall = mi;
		
		if (indx == -1)
			mi.setActionCommand(" ");
		else 
			mi.setActionCommand(String.valueOf(indx));
		this.oprnd2type.put(mi, t);
		
		return mi;
	}
	
	/**
	 * Add <code>operation</code> menu items to the specified pop-up menu.
	 */
	private void addOpNodeTypes(final JPopupMenu m) {
		for (int i=0; i<this.op2type.size(); i++) {	
			EdType t = this.op2type.get(i);
			addOprndNodeType(m, t, -1);
		}
	}
	
	/**
	 * Add <code>Show/Hide</code> menu item to the specified pop-up menu.
	 * It allows to show/hide the formula graph tree of a node which represents an application condition. 
	 */
	private void miRefGraph(final JPopupMenu m) {
		this.miRefGraph = new JMenuItem("Show View of Refinement Formula Graph");
		this.miRefGraph.setActionCommand("show");
		
		m.addSeparator();
		m.addSeparator();
		m.add(this.miRefGraph);
		
		this.miRefGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FormulaGraphGUI.this.node != null) {
					Object obj = FormulaGraphGUI.this.type2obj.get(FormulaGraphGUI.this.node.getType());										
					if (obj != null
							&& obj instanceof NestedApplCond
							&& ((NestedApplCond)obj).getName().equals(FormulaGraphGUI.this.node.getTypeName())) {
						FormulaGraphGUI.this.tmpF = ((NestedApplCond)obj).getFormula();
							
						if ("show".equals(FormulaGraphGUI.this.miRefGraph.getActionCommand())) {
//							addRefGraphOf(tmpF, node);
							doRefine(FormulaGraphGUI.this.tmpF, FormulaGraphGUI.this.node);	
							FormulaGraphGUI.this.gege.updateGraphics(true);
//							doZestTreeLayout(this.fgraph);
								
							FormulaGraphGUI.this.miRefGraph.setText("Hide View of Refinement Formula Graph");
							FormulaGraphGUI.this.miRefGraph.setActionCommand("hide");
						}
						else if ("hide".equals(FormulaGraphGUI.this.miRefGraph.getActionCommand())) {								
							if (FormulaGraphGUI.this.node.getBasisNode().getOutgoingArcs().hasNext()) {
								Node n = (Node)FormulaGraphGUI.this.node.getBasisNode().getOutgoingArcs().next().getTarget();
								EdGraphObject go = FormulaGraphGUI.this.fgraph.findNode(n);
								if (go != null) {
									deleteNode((EdNode)go);								
									FormulaGraphGUI.this.gege.updateGraphics(true);
										
									FormulaGraphGUI.this.miRefGraph.setText("Show View of Refinement Formula Graph");
									FormulaGraphGUI.this.miRefGraph.setActionCommand("show");
								}
							}
						}
					}					
				}
			}
		});	
	}
	
	void doRefine(final Formula form, final EdNode refNode) {
		List<EdNode> list = addRefGraphOf(form, refNode);
		for (int i=0; i<list.size(); i++) {
			EdNode n = list.get(i);
			Object obj = this.type2obj.get(n.getType());
			if (obj != null) {
				if (obj instanceof NestedApplCond
						&& ((NestedApplCond)obj).getName().equals(n.getTypeName())) {
					Formula form1 = ((NestedApplCond)obj).getFormula();
					doRefine(form1, n);
				}
			}
		}
	}
	
	private void enableRefGraph(boolean enable, String action) {
		if (this.miRefGraph == null)
			return;
		
		this.miRefGraph.setEnabled(enable);
		if ("show".equals(action)) {
			this.miRefGraph.setText("Show View of Refinement Formula Graph");
			this.miRefGraph.setActionCommand("show");
		}
		else if ("hide".equals(action)) {
			this.miRefGraph.setText("Hide View of Refinement Formula Graph");
			this.miRefGraph.setActionCommand("hide");
		}
	}
	
	/**
	 * Add <code>Delete</code> menu item to the specified pop-up menu.
	 */
	private JMenuItem addDelete(final JPopupMenu m) {
		JMenuItem mi = m.add(new JMenuItem("Delete"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FormulaGraphGUI.this.node != null) {
					deleteNode(FormulaGraphGUI.this.node);
					FormulaGraphGUI.this.fgraph.updateGraph();
					FormulaGraphGUI.this.gege.updateGraphics();
				}
			}
		});	
		
		return mi;
	}
	
	/**
	 * Add <code>Graph Layout</code> menu item to the specified pop-up menu.
	 */
	private JMenuItem addLayout(final JPopupMenu m) {
		JMenuItem mi = m.add(new JMenuItem("Graph Layout"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doZestTreeLayout(FormulaGraphGUI.this.fgraph);
			}
		});	
		
		return mi;
	}
	
	/**
	 * Delete the specified node from the formula-graph.
	 * All edges and nodes of its subtree will be deleted, too.
	 */
	private void deleteNode(final EdNode n) {
		if (n.getInArcsCount() == 1) {
			try {
				this.fgraph.delSelectedArc(this.fgraph.getIncomingArcs(n).get(0));
			} catch (TypeException ex) {}	
		}

		Vector<EdArc> outs = this.fgraph.getOutgoingArcs(n);
		for (int i=0; i<outs.size(); i++) {
			EdNode n1 = (EdNode) outs.get(i).getTarget();	
			deleteNode(n1);
		}		
		
		try {
			this.subNodes.remove(n);
			this.fgraph.delSelectedNode(n);
		} catch (TypeException ex) {}	
	}
	
	private void deleteRefinement() {
		boolean del = true;
		while (del) {
			Vector<EdNode> list = this.fgraph.getNodes();
			del = false;
			for (int i=0; i<list.size(); i++) {
				EdNode n = list.get(i);
				del = deleteRefOfNode(n);
				if (del)
					break;
			}
		}
	}
	
	/**
	 * Delete the refinement of the specified node from the formula-graph.
	 */
	private boolean deleteRefOfNode(final EdNode n) {
		boolean res = false;
		Vector<EdArc> outs = this.fgraph.getOutgoingArcs(n);
		for (int i=0; i<outs.size(); i++) {
			EdNode n1 = (EdNode) outs.get(i).getTarget();
			if (this.subNodes.contains(n1)) {
				deleteNode(n1);
				res = true;
			}
		}
		
		return res;
	}
	
	/*
	 * Add a top node if the formula-graph is empty,
	 * otherwise add an operation or a GAC node.
	 * Returns a new node or null.
	 * 
	 * @param t		type of the node
	 * @param kind	0 is for an operation node, 1 - for a GAC node
	 */
	EdNode addNode(final EdGraph graph, final EdType t, int kind) {		
		if (this.fgraph == graph && graph.isEmpty()) {
			return addTopNode(graph, t, kind);		
		}
		if (this.node != null) {
			try {
				int dx = 50;
				int dy = 40;
				// add node
				int x = this.node.getX();
				int y = this.node.getY() + dy;
				if (this.node.getOutArcsCount() == 0) {
					if (this.node.getTypeName().equals("AND")
								|| this.node.getTypeName().equals("OR")) {
						if (x-60 > 0)
							x = x - dx;
						else
							x = dx/2;
					} 
				} else if (this.node.getOutArcsCount() == 1) {
					x = x + dx;
				} else {
					return null;
				}
				
				EdNode n = graph.addNode(x, y, t, true);
				n.getBasisNode().setContextUsage(kind);			
				// add edge 
				EdType arcType = this.edgeType;
				if (graph != this.fgraph) {
					arcType = graph.getTypeSet().getArcTypeForName("");
				}
//				EdArc a = 
				graph.addArc(arcType, this.node, n, null, true);
				this.gege.updateGraphics();
				return n;
			} catch (TypeException ex) {}
		}
		return null;
	}
	
	
	/**
	 * If formula-graph is empty, add and return the top node,
	 * otherwise return null.
	 * @param t		type of the node
	 * @param kind	0 is for an operation node, 1 - for a GAC node
	 */
	private EdNode addTopNode(final EdGraph graph, final EdType t, int kind) {
		try {
			int x = this.gege.getGraphPanel().getWidth()/2;
			int y = 50;				
			EdNode n = graph.addNode(x, y, t, true);				
			n.getBasisNode().setContextUsage(kind);			
			this.topNode = n;							
			this.gege.updateGraphics();				
			return n;
		} catch (TypeException ex) {}
		return null;
	}
	
	private void resetTopNode() {
		if (!this.fgraph.getNodes().isEmpty()) {
			Vector<EdNode> list = this.fgraph.getNodes();
			for (int i=0; i<list.size(); i++) {
				EdNode n = list.get(i);
				if (n.getBasisNode().getNumberOfIncomingArcs() == 0) {
					this.topNode = n;
					return;
				}
			}
		}
	}
	
	/** not implemented
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/** not implemented
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/** not implemented
	 */
	public void mouseExited(MouseEvent e) {
	}

	/** 
	 * Pick a node of the formula-graph.
	 */
	public void mousePressed(MouseEvent e) {
		EdGraphObject go = this.gege.getGraph().getPicked(e.getX(), e.getY());		
		if (go != null && go.isNode())
			this.node = (EdNode)go;
		else
			this.node = null;	
		
		if (this.fgraph.isEmpty()
				|| e.isPopupTrigger()) {
			
			if (this.fgraph.hasSelection()) 
				this.fgraph.deselectAll();			
			
			if (e.getX() > this.getSize().width-50
					|| e.getY() > this.getSize().height)
				showPopupMenu(this.node, this.getSize().width/3, this.getSize().height/2);
			else
				showPopupMenu(this.node, e.getX(), e.getY());
		} 
	}

	/** 
	 * Show appropriate pop-up menu.
	 */
	public void mouseReleased(MouseEvent e) {
		if (this.fgraph.isEmpty()
				|| e.isPopupTrigger()) {
			
			if (this.fgraph.hasSelection()) 
				this.fgraph.deselectAll();			
			
			if (e.getX() > this.getSize().width-50
					|| e.getY() > this.getSize().height)
				showPopupMenu(this.node, this.getSize().width/3, this.getSize().height/4);
			else
				showPopupMenu(this.node, e.getX(), e.getY());
		} 
	}
	
	/*
	 * Show appropriate pop-up menu depending of context in which it is called.
	 * The context can be an empty graph or a graph node.
	 */
	private void showPopupMenu(final EdNode n, int x, int y) {
		if (this.miForall != null)
			this.miForall.setEnabled(!this.forallDisabled);
		
		if (this.fgraph.isEmpty()) {
			this.commonMenu.show(this.gege.getGraphPanel(), x, y);
		} 		
		else if (n == null) {
			this.layoutMenu.show(this.gege.getGraphPanel(), x, y);
		}
		else {
			int c = n.getOutArcsCount();
			switch (c) {
			case 0:
				if (this.subNodes.contains(n)) 
					this.miDel.setEnabled(false);
				else
					this.miDel.setEnabled(true);
				
				if (n.getBasisNode().getContextUsage() == OP) {
					if (n.getTypeName().equals("FORALL")) 
						this.oprndMenu.show(this.gege.getGraphPanel(), x+5, y+5);
					else
						this.commonMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				}
				else if (n.getBasisNode().getContextUsage() == OPRND) {
					if (this.hasRefGraph(n))
						enableRefGraph(true, "show");						
					else
						enableRefGraph(false, "show");
				
					this.delMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				}
				else if (n.getBasisNode().getContextUsage() == TRUE) {
					enableRefGraph(false, "");
					this.delMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				}
				else if (n.getBasisNode().getContextUsage() == FALSE) {
					enableRefGraph(false, "");
					this.delMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				}
				break;
			case 1:
				if (this.subNodes.contains(n)) 
					return;
				
				if (n.getBasisNode().getContextUsage() == OPRND) {
//					if (n.getBasisNode().getOutgoingArcs().next().getContextUsage() == -1) {
					if (n.getBasisNode().getOutgoingArcs().next().getType() == this.refEdgeType.getBasisType()) {
						enableRefGraph(true, "hide");
					}					
					else
						enableRefGraph(false, "hide");
				} else {
					enableRefGraph(false, "show");
				}
				
				this.miDel.setEnabled(true);
				
				if (n.getTypeName().equals("AND") 
						|| n.getTypeName().equals("OR")) {
					this.commonMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				} 
				else {					
					this.delMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				}
				break;
			case 2:
				if (this.subNodes.contains(n)) 
					return;
				
				enableRefGraph(false, "show");
				this.miDel.setEnabled(true);
				this.delMenu.show(this.gege.getGraphPanel(), x+5, y+5);
				break;
			}
		}
	}
	
	private boolean hasRefGraph(EdNode n) {
		Object obj = this.type2obj.get(n.getType());
		if (obj != null) {
			if (obj instanceof NestedApplCond) {
				String fstr = ((NestedApplCond)obj).getFormulaText();
				if ("".equals(fstr) || "true".equals(fstr) || "false".equals(fstr))
					return false;
				else
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Convert recursively the formula-graph to a new formula-string.<br>
	 * Result pair of strings contains two representations: 
	 * the first string shows the names,
	 * the second string - the indexes of the GACs.
	 */
	private Pair<String,String> graph2text(final Node n) {
		Node n1 = null;			
		String s = "";
		String s1 = "";
		int c = n.getNumberOfOutgoingArcs();
		switch (c) {
		case 0:
			s = n.getType().getName();
			if ("true".equals(s)) {
				s = "T";
				s1 = "T";
			}
			else if ("false".equals(s)) {
				s = "F";
				s1 = "F";
			}
			else if (this.name2indx.get(n.getType().getName()) != null) {
				s1 = String.valueOf(this.name2indx.get(n.getType().getName()).intValue());
			} else {
				s1 = n.getType().getName();
			}
			break;
		case 1:
			if (n.getOutgoingArcs().next().getContextUsage() == -1) {
				s = n.getType().getName();
				if (this.name2indx.get(n.getType().getName()) != null) {
					s1 = String.valueOf(this.name2indx.get(n.getType().getName()).intValue());
				} else {
					s1 = n.getType().getName();
				}
				break;
			}
			
			n1 = (Node)n.getOutgoingArcs().next().getTarget();
			if (n.getType().getName().equals("NOT")) {
				Pair<String,String> p = graph2text(n1);
				s = " !"+p.first;
				s1 = " !"+p.second;
			}
			else if (n.getType().getName().equals("FORALL")) {
				Pair<String,String> p1 = graph2text(n1);
				s = " FORALL"+"("+p1.first+")";
				s1 = " A"+"("+p1.second+")";
			}
			else {
				Pair<String,String> p = graph2text(n1);
				s = n.getType().getName()+"("+p.first+")";
				s1 = n.getType().getName()+"("+p.second+")";
			}
			break;
		case 2:
			Iterator<Arc> outs = n.getOutgoingArcs();
			n1 = (Node)outs.next().getTarget();
			Node n2 = (Node)outs.next().getTarget();
			if (n.getType().getName().equals("AND")) {
				Pair<String,String> p1 = graph2text(n1);
				Pair<String,String> p2 = graph2text(n2);
				s = "("+p1.first+" & "+p2.first+")";
				s1 = "("+p1.second+" & "+p2.second+")";				
			}
			else if (n.getType().getName().equals("OR")) {
				Pair<String,String> p1 = graph2text(n1);
				Pair<String,String> p2 = graph2text(n2);
				s = "("+p1.first+" | "+p2.first+")";
				s1 = "("+p1.second+" | "+p2.second+")";
			}
			break;
		}		
		return new Pair<String,String>(s, s1);
	}
	
	/*
	 * If the formula-graph is not empty, convert the graph to a new formula-string.
	 */
	private void setFormulaText() {
		if (this.topNode.getBasisNode().getNumberOfOutgoingArcs() == 0
				&& this.fgraph.getNodes().size() > 1) {
			this.resetTopNode();
		}
		if (this.topNode != null) {
			if (this.refined) {
				this.deleteRefinement();
				this.refined = false;
			}
			Pair<String,String> p = graph2text(this.topNode.getBasisNode());
			String s = p.first;
			String s1 = p.second;
			s = s.replaceAll("this", "$");
			s1 = s1.replaceAll("this", "$");
			this.formula = s;
			this.f = s1;
//			System.out.println(this.f);
//			System.out.println(this.formula);
		} 
	}
}
