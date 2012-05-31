/**
 * 
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agg.attribute.AttrMapping;
import agg.attribute.impl.ContextView;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdRule;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraphMorphismEditor;
import agg.ruleappl.ObjectFlow;
import agg.ruleappl.RuleSequence;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;

/**
 * @author olga
 *
 */
public class ObjectFlowDesktop extends JDialog 
implements InternalFrameListener, ListSelectionListener {

	protected final JFrame parentFrame;
	
	protected JDesktopPane desktop;

	protected JPanel panel;
	
//	protected JScrollPane jsp;

	protected ImageIcon internalFrameIcon;

	protected int nextX, nextY;

	protected int myW, myH;
	
	protected JButton connect, disconnect, refresh, close;
	
	protected Hashtable<GraphMorphismEditor, JInternalFrame> internalFrames;
	
	protected Hashtable<ObjectFlow, GraphMorphismEditor> editors;
	
	protected Dimension internalFrameSize;
		
	protected EdGraGra gragra;
	
	protected final RuleSequence ruleSequence;
	
	protected ObjectFlow currentObjFlow;
	
	protected JInternalFrame currentFrame;
	
	protected final List<String> ruleNames = new Vector<String>();	
	
	protected JTable ruleList1, ruleList2;
	
	protected boolean list2Clicked;
	
	protected JScrollPane scrollRuleList1, scrollRuleList2;
	
	protected Object rule1, rule2;
	protected int indx_rule1=-1, indx_rule2=-1;
	
	protected final String title = "Object Flow of Rule Sequence ";
	
	
	public ObjectFlowDesktop(
			final JFrame parent,
			final EdGraGra gragra,
			final RuleSequence ruleSeq,
			final Point location) {
		
		super(); //parent);
		setModal(false);
		setTitle(this.title);
		extendTitle(ruleSeq.getName());
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				setVisible(false);
			}
		});
		
		this.parentFrame = parent;
		this.gragra = gragra;
		this.ruleSequence = ruleSeq;
		
		Border border = new TitledBorder("");
		
		JPanel ruleP = makeRuleListPanel(border);
		
		if (this.gragra != null)
			this.updateRuleList(this.ruleSequence.getRuleNames());
		
		JPanel desktopP = makeDesktop(border);
			
		JPanel closeP = new JPanel(new GridLayout(0,3));
		this.close = new JButton("Close");
		this.close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		closeP.add(new JLabel("     "));
		closeP.add(close);
		closeP.add(new JLabel("     "));
		
//		JPanel p = new JPanel(new BorderLayout());
//		p.add(ruleP, BorderLayout.NORTH);
//		p.add(desktopP, BorderLayout.CENTER);
		
		JSplitPane p = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ruleP, desktopP);
		p.setDividerSize(10);
		p.setContinuousLayout(true);
		p.setOneTouchExpandable(true);
		
		JScrollPane scroll = new JScrollPane(p);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll);
		getContentPane().add(closeP, BorderLayout.SOUTH);
		validate();

		setLocation(location);
		pack();
	}
		
	public void seVisible(boolean b) {		
		super.setVisible(b);
	}
	
	public RuleSequence getRuleSequence() {
		return this.ruleSequence;
	}
	
	public EdGraGra getGraGra() {
		return this.gragra;
	}
	
	protected void extendTitle(String name) {
		String str = " ".concat(name).concat(" - ");
		super.setTitle(str.concat(this.title));
	}
	
	private boolean needRefresh() {
		boolean need = false;
		if (this.ruleSequence.doesCheckAtGraph()
				&& this.ruleSequence.getGraph() != null) {
			if (this.ruleList1.getRowCount() != (this.ruleSequence.getRules().size()+1)
					|| !compareGraphName()
					|| !compareRuleName(1)) {
				need = true;
			}
		} else if (this.ruleList1.getRowCount() != this.ruleSequence.getRules().size()
				|| !compareRuleName(0)) {
			need = true;
		}
		return need;
	}
	
	public void refresh() {
		if (this.needRefresh()) {
			// check rule names
			if (this.ruleNames.size() == this.ruleSequence.getRuleNames().size()) {
				if (!this.ruleNames.equals(this.ruleSequence.getRuleNames())) {
					this.ruleSequence.removeObjFlow();
				}
			} else {
				// check rule names
				List<String> tmp = this.ruleNames.subList(1, this.ruleNames.size());				
				if (!tmp.equals(this.ruleSequence.getRuleNames())) {
					this.ruleSequence.removeObjFlow();
				} else {
					// check graph name
					String firstName = (String)this.ruleList1.getValueAt(0, 0);
					if (this.ruleSequence.getGraph() != null
							&& !this.ruleSequence.getGraph().getName().equals(firstName)) {
						this.ruleSequence.removeObjFlowOfGraph();
					}
				}
			}
//			this.ruleSequence.removeObjFlow();
			removeAllFrames();
			updateRuleList(this.ruleSequence.getRuleNames());
		}		
	}
	
	private boolean compareRuleName(int startIndx) {
		if ((this.ruleList1.getRowCount()-startIndx) != (this.ruleSequence.getRuleNames().size()))
			return false;
		
		for (int i=startIndx; i<this.ruleList1.getRowCount(); i++) {
			if (!this.ruleList1.getValueAt(i, 0)
					.equals(this.ruleSequence.getRuleNames().get(i-startIndx))) {
				return false;
			}
		}
		return true;
	}
	
	private boolean compareGraphName() {		
		return this.ruleList1.getValueAt(0, 0).equals(this.ruleSequence.getGraph().getName());
	}

	public int getListSize() {
		return this.ruleList1.getRowCount();
	}
	
	private JPanel makeRuleListPanel(final Border border) {
		final JPanel p = new JPanel(new GridBagLayout());
		p.setBorder(border);
		
		this.ruleList1 = new JTable(0,1);
		this.scrollRuleList1 = new JScrollPane(this.ruleList1);
		final JPanel p1 = this.makeRuleList("OUTPUT ( Graph | Rule RHS )", this.ruleList1, this.scrollRuleList1);
		
		this.ruleList2 = new JTable(0,1);
		this.scrollRuleList2 = new JScrollPane(this.ruleList2);
		final JPanel p2 = this.makeRuleList("INPUT ( Rule LHS )", this.ruleList2, this.scrollRuleList2);
		
		final JPanel p3 = new JPanel(new GridLayout(0,1));		
		final JLabel text = new JLabel("  OUTPUT   >>   INPUT  ");
		
		this.connect = new JButton("Connect >>");
		this.connect.setEnabled(false);
		this.connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println(rule1+"   connect  "+rule2);				
				if (ObjectFlowDesktop.this.rule1 != null && ObjectFlowDesktop.this.rule2 != null) {
					String str = "An output object can be connected (by clicking) to an input object";
					String key = String.valueOf(ObjectFlowDesktop.this.indx_rule1)
														.concat(":")
														.concat(String.valueOf(ObjectFlowDesktop.this.indx_rule2));						
					ObjectFlow objFlow = ObjectFlowDesktop.this.ruleSequence.getObjectFlow().get(key);
					if (objFlow == null) {
						if (ObjectFlowDesktop.this.rule2 instanceof EdRule) {
							if (ObjectFlowDesktop.this.rule1 instanceof EdGraph) {
								objFlow = new ObjectFlow(
													((EdGraph)ObjectFlowDesktop.this.rule1).getBasisGraph(), 
													((EdRule)ObjectFlowDesktop.this.rule2).getBasisRule(),
													ObjectFlowDesktop.this.indx_rule1, ObjectFlowDesktop.this.indx_rule2);
								ObjectFlowDesktop.this.ruleSequence.addObjFlow(objFlow);
							}
							else if (ObjectFlowDesktop.this.rule1 instanceof EdRule) {
								objFlow = new ObjectFlow(
										((EdRule)ObjectFlowDesktop.this.rule1).getBasisRule(), 
										((EdRule)ObjectFlowDesktop.this.rule2).getBasisRule(),
										ObjectFlowDesktop.this.indx_rule1, ObjectFlowDesktop.this.indx_rule2);
								ObjectFlowDesktop.this.ruleSequence.addObjFlow(objFlow);
							}
						}
					}
					if (ObjectFlowDesktop.this.editors.get(objFlow) == null
								|| ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)) == null) {
						addGraphMorphismEditor(objFlow, str);
						ObjectFlowDesktop.this.connect.setEnabled(false);
						ObjectFlowDesktop.this.disconnect.setEnabled(true);
					} else {							
						try {
							ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setIcon(false);
							ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setSelected(true);
							ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setVisible(true);	
							ObjectFlowDesktop.this.connect.setEnabled(false);
							ObjectFlowDesktop.this.disconnect.setEnabled(true);
						} catch (java.beans.PropertyVetoException pve) {}
					}
				} 
			}
		});
		
		this.disconnect = new JButton("Disconnect >>");
		this.disconnect.setEnabled(false);
		this.disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ObjectFlowDesktop.this.rule1 != null && ObjectFlowDesktop.this.rule2 != null) {
					String str = "An output object will be disconnected from an input object";
					if (ObjectFlowDesktop.this.rule1 instanceof EdGraph
							&& ObjectFlowDesktop.this.rule2 instanceof EdRule) {
						String key = String.valueOf(ObjectFlowDesktop.this.indx_rule1)
														.concat(":")
														.concat(String.valueOf(ObjectFlowDesktop.this.indx_rule2));						
						ObjectFlow objFlow = ObjectFlowDesktop.this.ruleSequence.getObjectFlow().get(key);						
						if (objFlow != null) {								
							if (ObjectFlowDesktop.this.editors.get(objFlow) == null
									|| ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)) == null) {
								addGraphMorphismEditor(objFlow, str);								
							} 
							ObjectFlowDesktop.this.editors.get(objFlow).removeAllMappings();
							ObjectFlowDesktop.this.editors.get(objFlow).updateGraphs();
							ObjectFlowDesktop.this.editors.get(objFlow).updateGraphics();
							
							try {
								if (!ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).isVisible()) {
									ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setIcon(false);
									ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setSelected(true);
									ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setVisible(true);
								}
								if (ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).isVisible()) {
									removeFrame(ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)));
									ObjectFlowDesktop.this.internalFrames.remove(ObjectFlowDesktop.this.editors.get(objFlow));
									ObjectFlowDesktop.this.editors.remove(objFlow);
									if (ObjectFlowDesktop.this.internalFrames.isEmpty()) {
										ObjectFlowDesktop.this.connect.setEnabled(true);
										ObjectFlowDesktop.this.disconnect.setEnabled(false);
									}
								} 
							} catch (java.beans.PropertyVetoException pve) {}
							
							ObjectFlowDesktop.this.ruleSequence.getObjectFlow().remove(key);
							ObjectFlowDesktop.this.currentObjFlow = null;
							setCurrentObjectFlow();
						}
					}
					else if (ObjectFlowDesktop.this.rule1 instanceof EdRule
							&& ObjectFlowDesktop.this.rule2 instanceof EdRule) {
						String key = String.valueOf(ObjectFlowDesktop.this.indx_rule1)
														.concat(":")
														.concat(String.valueOf(ObjectFlowDesktop.this.indx_rule2));						
						ObjectFlow objFlow = ObjectFlowDesktop.this.ruleSequence.getObjectFlow().get(key);
						if (objFlow != null) { 							
							if (ObjectFlowDesktop.this.editors.get(objFlow) == null
									|| ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)) == null) {
								addGraphMorphismEditor(objFlow, str);	
							} 
							ObjectFlowDesktop.this.editors.get(objFlow).removeAllMappings();
							ObjectFlowDesktop.this.editors.get(objFlow).updateGraphs();
							ObjectFlowDesktop.this.editors.get(objFlow).updateGraphics();
							
							try {									
								if (ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).isVisible()) {
									removeFrame(ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)));
									ObjectFlowDesktop.this.internalFrames.remove(ObjectFlowDesktop.this.editors.get(objFlow));
									ObjectFlowDesktop.this.editors.remove(objFlow);
									if (ObjectFlowDesktop.this.internalFrames.isEmpty())
										ObjectFlowDesktop.this.disconnect.setEnabled(false);
								} else {
									ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setIcon(false);
									ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setSelected(true);
									ObjectFlowDesktop.this.internalFrames.get(ObjectFlowDesktop.this.editors.get(objFlow)).setVisible(true);
								}
							} catch (java.beans.PropertyVetoException pve) {}
							
							ObjectFlowDesktop.this.ruleSequence.getObjectFlow().remove(key);
							ObjectFlowDesktop.this.currentObjFlow = null;
							setCurrentObjectFlow();
						}
					}
				}
			}
		});
		
		this.refresh = new JButton("Refresh");
		this.refresh.setEnabled(true);
		this.refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ObjectFlowDesktop.this.ruleSequence.doesCheckAtGraph()
						&& (ObjectFlowDesktop.this.gragra.getBasisGraGra().getGraph() 
								!= ObjectFlowDesktop.this.ruleSequence.getGraph())) {
					int answer = 0;
					Object[] options = { "OK", "Cancel" };
					if (ObjectFlowDesktop.this.ruleSequence.isObjFlowActive()
							|| ObjectFlowDesktop.this.ruleSequence.isChecked()) {
						answer = JOptionPane.showOptionDialog(
							null,
							"<html><body>"
							+"Currently selected sequence contains an object flow \n"
							+"or is already checked at the graph  \""
							+ObjectFlowDesktop.this.gragra.getBasisGraGra().getCurrentRuleSequence().getGraph().getName()
							+"\" \n"
							+"The object flow resp. results are not more valid after graph reset.",
							"Reset Graph", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					}
					if (answer == 0) {
						ObjectFlowDesktop.this.ruleSequence.setGraph(ObjectFlowDesktop.this.gragra.getBasisGraGra().getGraph());			
						ObjectFlowDesktop.this.ruleSequence.setCheckAtGraph(true);
					} 
					refresh();
				}				
			}
		});
				
		p3.add(new JLabel("     "));
		p3.add(text);
		p3.add(this.connect);
		p3.add(new JLabel("     "));
		p3.add(this.disconnect);
		p3.add(new JLabel("     "));
		p3.add(this.refresh);

		constrainBuild(p, p1, 
						0, GridBagConstraints.RELATIVE, 
						1, 1, 
						GridBagConstraints.BOTH,
						GridBagConstraints.CENTER, 
						1.0, 1.0, 
						10, 5, 5, 5);
		
		constrainBuild(p, p3, 
				1, GridBagConstraints.RELATIVE, 
				1, 0, 
				GridBagConstraints.NONE, 
				GridBagConstraints.NORTH,
				0.0, 0.0, 
				10, 5, 5, 5);
		
		constrainBuild(p, p2, 
				2, GridBagConstraints.RELATIVE, 
				1, 1, 
				GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 
				1.0, 1.0, 
				10, 5, 5, 5);
		
		return p;
	}
	
	void closeDialog() {
		this.setVisible(false);
	}
	
	protected void addGraphMorphismEditor(final ObjectFlow objFlow, String text) {
		EdGraph right = ((EdRule)this.rule2).getLeft();
		EdGraph left = null;
		String titleStr = "";
		
		if (objFlow.isGraphExtended()) {
			left = (EdGraph)this.rule1;	
			titleStr = "(Host graph: "+left.getName()+")"
						+"  output -> input  "
						+"(LHS of rule: "+((EdRule)this.rule2).getName()+")";
		} else {
			left = ((EdRule)this.rule1).getRight();
			titleStr = "(RHS of rule: "+((EdRule)this.rule1).getName()+")"
						+"    OUTPUT      >>      INPUT    "
						+"(LHS of rule: "+((EdRule)this.rule2).getName()+")";
		}
				
		final GraphMorphismEditor gme = addGraphMorphismEditor(objFlow, left, right, text);	
		if (gme != null) {
			gme.setTitle(titleStr);		
			this.editors.put(objFlow, gme);
		}
	}
	
	
	private JPanel makeDesktop(final Border border) {
		panel = new JPanel(new BorderLayout());
		panel.setBorder(border);
		
		final JLabel l = new JLabel("   ");
		
		this.desktop = new JDesktopPane();
		this.desktop.setBackground(l.getBackground());
		this.myW = 500;
		this.myH = 500;
		this.desktop.setPreferredSize(new Dimension(this.myW, this.myH));

		this.editors = new Hashtable<ObjectFlow, GraphMorphismEditor>();
		
		this.internalFrames = new Hashtable<GraphMorphismEditor, JInternalFrame>();
		this.internalFrameIcon = null; //IconResource.getIconFromURL(IconResource.getURLOverlapGraph());
		this.internalFrameSize = new Dimension(500, 200);		
		this.nextX = 0;
		this.nextY = 0;
		
		panel.add(l, BorderLayout.NORTH);
		panel.add(this.desktop, BorderLayout.CENTER);
		
		return panel;
	}
	
	/**
	 * Returns the component to display the desktop. This component can be set
	 * in a frame, panel or something like that.
	 * 
	 * @return The desktop component
	 */
	public Component getComponent() {
		return this.panel;
	}

	/**
	 * Returns the desktop object.
	 * 
	 * @return The desktop
	 */
	public JDesktopPane getDesktop() {
		return this.desktop;
	}

	
	private JPanel makeRuleList(
			final String titleStr, 
			final JTable ruleList,
			final JScrollPane scrollRuleList) {
		
		final JLabel l = new JLabel(titleStr);
		scrollRuleList.setPreferredSize(new Dimension(200, 150));
		
		final JPanel p = new JPanel(new BorderLayout());
		p.add(l, BorderLayout.NORTH);
		p.add(scrollRuleList, BorderLayout.CENTER);
		
		return p;
	}
	
	public void updateRuleList(final List<String> rulenames) {
		this.ruleNames.clear();
		
		if (this.ruleSequence.doesCheckAtGraph()
				&& this.ruleSequence.getGraph() != null)
			this.ruleNames.add(this.ruleSequence.getGraph().getName());
		
		this.ruleNames.addAll(rulenames);
		
		if (this.ruleList1 != null) {
			this.scrollRuleList1.getViewport().remove(this.ruleList1);
		}
		this.ruleList1 = new JTable(this.ruleNames.size(), 1);
		this.scrollRuleList1.getViewport().setView(this.ruleList1);
		this.ruleList1.getSelectionModel().addListSelectionListener(this);
		
		if (this.ruleList2 != null) {
			this.scrollRuleList2.getViewport().remove(this.ruleList2);
		}
		this.ruleList2 = new JTable(this.ruleNames.size(), 1);
		this.scrollRuleList2.getViewport().setView(this.ruleList2);
		this.ruleList2.getSelectionModel().addListSelectionListener(this);
		
		for (int i = 0; i < this.ruleNames.size(); i++) {
			this.ruleList1.getModel().setValueAt(String.valueOf(i).concat(" ").concat(this.ruleNames.get(i)), i, 0);
			((DefaultCellEditor) this.ruleList1.getCellEditor(i, 0))
						.getComponent().setEnabled(false);
			
			this.ruleList2.getModel().setValueAt(String.valueOf(i).concat(" ").concat(this.ruleNames.get(i)), i, 0);
			((DefaultCellEditor) this.ruleList2.getCellEditor(i, 0))
						.getComponent().setEnabled(false);
		}
	}
	
	public GraphMorphismEditor addGraphMorphismEditor(
			final ObjectFlow objFlow,
			final EdGraph leftGraph, 
			final EdGraph rightGraph, 
			String aTitle) {
		
		OrdinaryMorphism isoLeft = leftGraph.getBasisGraph().isomorphicCopy();
		OrdinaryMorphism isoRight = rightGraph.getBasisGraph().isomorphicCopy();
		if (isoLeft == null || isoRight == null) {
			return null;
		}
		
		final GraphMorphismEditor gmEditor = new GraphMorphismEditor(this.parentFrame);
		
		EdGraph left = new EdGraph(isoLeft.getTarget());
		left.updateLayoutByIsoMorphism(isoLeft, leftGraph);
		
		EdGraph right = new EdGraph(isoRight.getTarget());
		right.updateLayoutByIsoMorphism(isoRight, rightGraph);
		
		OrdinaryMorphism morph = BaseFactory.theFactory().createMorphism(
				left.getBasisGraph(), 
				right.getBasisGraph());
		((ContextView) morph.getAttrContext()).changeAllowedMapping(AttrMapping.OBJECT_FLOW_MAP); //MATCH_MAP);
		
		if (!objFlow.getMapping().isEmpty()) {
			List<Object> list = new Vector<Object>();
			Enumeration<Object> keys = objFlow.getMapping().keys();
			// first set mapping of nodes
			while (keys.hasMoreElements()) {
				Object out = keys.nextElement();
				if (out instanceof Node) {
					Object in = objFlow.getMapping().get(out);
					try {
						GraphObject leftobj = isoLeft.getImage((GraphObject) out);
						GraphObject rightobj = isoRight.getImage((GraphObject) in);
						if (leftobj != null && rightobj != null) {
							if (leftobj.getType().isParentOf(rightobj.getType())) {
								morph.addMapping(leftobj, rightobj);
							}
							else if (leftobj.getType().isChildOf(rightobj.getType())){
								morph.addChild2ParentMapping(leftobj, rightobj);							
							}
						}
					} catch (BadMappingException ex) {}
				} else {
					list.add(out);
				}
			}
			// set mapping of arcs
			for (int i=0; i<list.size(); i++) {
				Object out = list.get(i);
				Object in = objFlow.getMapping().get(out);
				try {
					GraphObject leftobj = isoLeft.getImage((GraphObject) out);
					GraphObject rightobj = isoRight.getImage((GraphObject) in);
					if (leftobj != null && rightobj != null) {
						morph.addMapping(leftobj, rightobj);
					}
				} catch (BadMappingException ex) {}
			}
		}
		
		gmEditor.setLeftGraph(left);
		gmEditor.setRightGraph(right);
		gmEditor.setMorphism(morph);
		gmEditor.setIsoMorphismLeft(isoLeft);
		gmEditor.setIsoMorphismRight(isoRight);
		gmEditor.setObjectFlow(objFlow);
		
		if (!objFlow.isEmpty()) {
			gmEditor.updateGraphs();
		}
		
//		gmEditor.setEditMode(EditorConstants.VIEW);
		gmEditor.setEditMode(EditorConstants.MAP);
		
//		gmEditor.getLeftPanel().getCanvas().addMouseListener(ml);
//		gmEditor.getRightPanel().getCanvas().addMouseListener(ml);
		
		String ofIndx = "("+String.valueOf(objFlow.getIndexOfOutput())+"-"
							+String.valueOf(objFlow.getIndexOfInput())+") ";
		final JInternalFrame f = new JInternalFrame(ofIndx+aTitle, true,
				true, true, true);
		this.internalFrames.put(gmEditor, f);
		
		f.addInternalFrameListener(this);
		f.setSize(this.internalFrameSize);
//		f.setFrameIcon(internalFrameIcon);
		
		f.getContentPane().add(gmEditor);	
		
		getDesktop().add(f, 0);
		
		try {
			f.setSelected(true);
			f.setVisible(true);	
		} catch (java.beans.PropertyVetoException pve) {}
				
		f.setLocation(this.nextX, this.nextY);
//		nextX = nextX + 20;
//		nextY = nextY + 20;
		
		return gmEditor;
	}
	
	protected void setCurrentObjectFlow() {
		if (this.currentFrame != null
				&& this.currentFrame.isEnabled()
				&& this.currentFrame.isSelected()) {
			
			Enumeration<GraphMorphismEditor> keys = this.internalFrames.keys();
			while (keys.hasMoreElements()) {
				GraphMorphismEditor gme = keys.nextElement();
				if (this.internalFrames.get(gme) == this.currentFrame) {
					this.currentObjFlow = gme.getObjectFlow();
					if (this.currentObjFlow != null) {
						this.ruleList1.changeSelection(this.currentObjFlow.getIndexOfOutput(), 0, false, false);
						this.ruleList2.changeSelection(this.currentObjFlow.getIndexOfInput(), 0, false, false);
					} else {
						this.indx_rule1 = -1;
						this.ruleList1.clearSelection();
						this.rule1 = null;
						
						this.indx_rule2 = -1;
						this.ruleList2.clearSelection();
						this.rule2 = null;
						
					}
				}
			}
		}
	}
	
	/**
	 * Removes all frames from the desktop.
	 */
	public void removeAllFrames() {
		this.internalFrames.clear();
		
		for (int i = this.desktop.getAllFrames().length - 1; i >= 0; i--) {
			JInternalFrame f = this.desktop.getAllFrames()[i];
			f.setVisible(false);
			if (f.isIcon()) {
				this.desktop.remove(f.getDesktopIcon());
			} else {
				this.desktop.remove(f);
			}			
		}
		
		this.ruleList1.clearSelection();
		this.ruleList2.clearSelection();
		this.rule1 = null;
		this.rule2 = null;
		this.indx_rule1=-1; 
		this.indx_rule2=-1;
		
		this.nextX = 0;
		this.nextY = 0;
	}
	
	/**
	 * Removes a frame from the desktop.
	 */
	public void removeFrame(JInternalFrame f) {
		f.setVisible(false);
		if (f.isIcon()) {
			this.desktop.remove(f.getDesktopIcon());
		} else {
			this.desktop.remove(f);
		}			
		
		this.ruleList1.clearSelection();
		this.ruleList2.clearSelection();
		this.rule1 = null;
		this.rule2 = null;
		this.indx_rule1=-1; 
		this.indx_rule2=-1;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameActivated(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameActivated(InternalFrameEvent e) {
		this.currentFrame = e.getInternalFrame();
		// reset selection of lists
		resetListsSelection(this.currentFrame);
	}

	private void resetListsSelection(final JInternalFrame f) {
		Enumeration<GraphMorphismEditor> iter1 = this.internalFrames.keys();
		while (iter1.hasMoreElements()) {
			GraphMorphismEditor gme = iter1.nextElement();
			if (this.internalFrames.get(gme) == f) {
				Enumeration<ObjectFlow> iter2 = this.editors.keys();
				while (iter2.hasMoreElements()) {					
					ObjectFlow of = iter2.nextElement();
					if (this.editors.get(of) == gme) {
						int i1 = of.getIndexOfOutput();
						int i2 = of.getIndexOfInput();
//						System.out.println(i1+"    "+i2);
						this.ruleList1.changeSelection(i1, 0, false, false);
						this.ruleList2.changeSelection(i2, 0, false, false);
					}
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosed(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameClosed(InternalFrameEvent e) {
		Enumeration<GraphMorphismEditor> keys = this.internalFrames.keys();
		while (keys.hasMoreElements()) {
			GraphMorphismEditor gme = keys.nextElement();
			if (this.internalFrames.get(gme) == e.getInternalFrame()) {
				this.internalFrames.remove(gme);
			}
		}
		this.currentFrame = null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosing(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameClosing(InternalFrameEvent e) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeactivated(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameDeactivated(InternalFrameEvent e) {
		this.currentFrame = null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeiconified(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameIconified(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameOpened(javax.swing.event.InternalFrameEvent)
	 */
	public void internalFrameOpened(InternalFrameEvent e) {
		this.currentFrame = e.getInternalFrame();
		// reset selection of lists
		resetListsSelection(this.currentFrame);
	}
	
	private void constrainBuild(Container container, Component component,
			int grid_x, int grid_y, 
			int grid_width, int grid_height, 
			int fill,
			int anchor, 
			double weight_x, double weight_y, 
			int top, int left, int bottom, int right) {
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


	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (this.currentFrame != null) {
			try {this.currentFrame.setSelected(false);} 
			catch (Exception ex) {}
		}
		
		// row of ruleList clicked
		int indx = ((DefaultListSelectionModel) e.getSource())
				.getLeadSelectionIndex();
		if (indx != -1) {
			if (e.getSource() == this.ruleList1.getSelectionModel()) {
				this.list2Clicked = false;
				this.indx_rule1 = indx;
				this.rule1 = this.gragra.getRule(this.ruleNames.get(indx));
				if (this.rule1 == null && indx == 0) {
					this.rule1 = this.gragra.getGraphOf(this.ruleSequence.getGraph());
				}
				if (indx_rule2 <= indx_rule1) {
					this.ruleList2.clearSelection();
					this.rule2 = null;
					this.indx_rule2 = -1;
					this.disconnect.setEnabled(false);
					this.connect.setEnabled(false);
				}	
				else {
					if (tryToShowOF()) {
						this.connect.setEnabled(false);
						this.disconnect.setEnabled(true);						
					}
					else {
						this.connect.setEnabled(true);
						this.disconnect.setEnabled(false);
					}
				}
			} else if (e.getSource() == this.ruleList2.getSelectionModel()) {
				if (indx > this.indx_rule1) {
					this.list2Clicked = true;
					this.indx_rule2 = indx;
					this.rule2 = this.gragra.getRule(this.ruleNames.get(indx));
					
					if (tryToShowOF()) {
						this.connect.setEnabled(false);
						this.disconnect.setEnabled(true);						
					}
					else {
						this.connect.setEnabled(true);
						this.disconnect.setEnabled(false);
					}
				} else {
					this.ruleList2.clearSelection();
					this.rule2 = null;
					this.indx_rule2 = -1;
					this.disconnect.setEnabled(false);
					this.connect.setEnabled(false);
				}
			}
		}
	}
	
	protected boolean tryToShowOF() {
		int i = this.ruleList1.getSelectionModel().getLeadSelectionIndex();
		if (i >= 0
				&& this.ruleList2.getSelectionModel().getLeadSelectionIndex() > i) {
			if (this.rule1 != null && this.rule2 != null) {
				if ((this.rule1 instanceof EdGraph
							|| this.rule1 instanceof EdRule)
						&& this.rule2 instanceof EdRule) {		
					String key = String.valueOf(this.indx_rule1)
											.concat(":")
											.concat(String.valueOf(this.indx_rule2));
					ObjectFlow objFlow = this.ruleSequence.getObjectFlow().get(key);
					if (objFlow != null) {									
						if (this.editors.get(objFlow) == null
								|| this.internalFrames.get(this.editors.get(objFlow)) == null) {
							String str = "An output object can be connected to an input object";
							addGraphMorphismEditor(objFlow, str);
							return true;
						} else {							
							try {
								this.internalFrames.get(this.editors.get(objFlow)).setIcon(false);
								this.internalFrames.get(this.editors.get(objFlow)).setSelected(true);
								this.internalFrames.get(this.editors.get(objFlow)).setVisible(true);
								return true;
							} catch (java.beans.PropertyVetoException pve) {}
						}
//						this.connect.setEnabled(false);
//						this.disconnect.setEnabled(true);
					}
				} 
			}
		}
		return false;
	}
	
	
}
