package agg.gui.popupmenu;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;

import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdType;
import agg.gui.AGGAppl;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraGraEditor;
import agg.gui.editor.GraphEditor;
import agg.gui.editor.GraphPanel;
import agg.gui.editor.RuleEditor;
import agg.xt_basis.Node;
import agg.xt_basis.Graph;
import agg.xt_basis.Type;
import agg.xt_basis.TypeSet;

/**
 * @author $Author: olga $
 * @version $Id: EditSelPopupMenu.java,v 1.18 2010/10/16 22:44:43 olga Exp $
 */
@SuppressWarnings("serial")
public class EditSelPopupMenu extends JPopupMenu {

	public EditSelPopupMenu() {
		super("Operations");
		setLabel("Operations");
		setBorderPainted(true);

		this.deleteMenu = createDeleteMenu();
		this.useDeleteMenu = false;

		this.mi = add(new JMenuItem("      Operations"));
		// mi.setEnabled(false);
		addSeparator();

		this.mi = add(new JMenuItem("Attributes ..."));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((EditSelPopupMenu.this.gp == null) 
						|| (EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
						|| (EditSelPopupMenu.this.gp.getGraph() == null) 
						|| (EditSelPopupMenu.this.ego == null))
					return;

				EditSelPopupMenu.this.mapping = false;
				// gp.getCanvas().saveScrollBarValue();

				if (EditSelPopupMenu.this.ruleEditor == null)
					EditSelPopupMenu.this.editor.setAttrEditorOnTopForGraphObject(EditSelPopupMenu.this.ego);
				else
					EditSelPopupMenu.this.editor.setAttrEditorOnBottomForGraphObject(EditSelPopupMenu.this.ego);

				EditSelPopupMenu.this.gp.updateGraphics();
			}
		});
		addSeparator();

		this.mi = add(new JMenuItem("Copy Selected"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((EditSelPopupMenu.this.gp == null) 
						|| (EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
						|| (EditSelPopupMenu.this.gp.getGraph() == null))
					return;

				EditSelPopupMenu.this.mapping = false;
				if (EditSelPopupMenu.this.gp.getGraph().hasSelection()) {
//					EditSelPopupMenu.this.gp.setLastEditMode(EditSelPopupMenu.this.gp.getEditMode());
//					EditSelPopupMenu.this.gp.setLastEditCursor(EditSelPopupMenu.this.gp.getEditCursor());
//					EditSelPopupMenu.this.gp.setEditMode(EditorConstants.COPY);
//					if (EditSelPopupMenu.this.editor != null)
//						EditSelPopupMenu.this.editor
//								.setMsg("To place a copy click on the background of the panel.");
					
					AGGAppl.getInstance().getGraGraEditor().copyProc();
					AGGAppl.getInstance().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
				}
			}
		});

		this.mi = add(new JMenuItem("Deselect"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditSelPopupMenu.this.mapping = false;
				if (EditSelPopupMenu.this.gp != null 
						&& EditSelPopupMenu.this.gp.getGraph() != null 
						&& EditSelPopupMenu.this.ego != null) {
					EditSelPopupMenu.this.gp.deselect(EditSelPopupMenu.this.ego);
				}
			}
		});

		this.mi = add(new JMenuItem("Deselect All"));
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditSelPopupMenu.this.mapping = false;
				if (EditSelPopupMenu.this.gp != null 
						&& EditSelPopupMenu.this.gp.getGraph() != null) {
					EditSelPopupMenu.this.gp.deselectAll();
				}
			}
		});
		addSeparator();

		this.miDelete = createDeleteItem();
		this.deleteMenu = createDeleteMenu();
		add(this.miDelete);
		if (this.useDeleteMenu)
			add(this.deleteMenu);
		addSeparator();

		this.mi = add(new JMenuItem("Straighten Selected"));
		this.miStraighten = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((EditSelPopupMenu.this.gp == null) 
						|| (EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
						|| (EditSelPopupMenu.this.gp.getGraph() == null))
					return;

				EditSelPopupMenu.this.mapping = false;
				EditSelPopupMenu.this.gp.straightenSelectedArcs();
			}
		});
		addSeparator();

		this.addIdentic = new JMenu("Add Identic To");
		this.add(this.addIdentic);
		this.mi = this.addIdentic.add(new JMenuItem("Rule RHS"));
		this.miAddIdenticToRule = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;
				if (EditSelPopupMenu.this.ruleEditor != null) {
					if (EditSelPopupMenu.this.gp == EditSelPopupMenu.this.ruleEditor.getLeftPanel()) {
						EditSelPopupMenu.this.ruleEditor.getRule().addIdenticToRule(
								EditSelPopupMenu.this.ruleEditor.getRule().getLeft()
										.getSelectedObjs());
						EditSelPopupMenu.this.ruleEditor.getRule().updateRule();
						EditSelPopupMenu.this.ruleEditor.updateGraphics();
					}
				}
			}
		});
		
		this.mi = this.addIdentic.add(new JMenuItem("NAC"));
		this.miAddIdenticToNAC = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;
				if (EditSelPopupMenu.this.ruleEditor != null) {
					if (EditSelPopupMenu.this.gp == EditSelPopupMenu.this.ruleEditor.getLeftPanel()) {
						if (EditSelPopupMenu.this.ruleEditor.getNAC() == null) {
							JOptionPane
									.showMessageDialog(
											null,
											"Cannot create an identic object!"
													+ System
															.getProperty("line.separator")
													+ "Please create and open an empty NAC graph first.",
											"Identic Node/Edge",
											JOptionPane.WARNING_MESSAGE);
							return;
						}
						EditSelPopupMenu.this.ruleEditor.getRule()
								.addIdenticToNAC(
										EditSelPopupMenu.this.ruleEditor.getRule().getLeft()
												.getSelectedObjs(),
												EditSelPopupMenu.this.ruleEditor.getNAC());
						EditSelPopupMenu.this.ruleEditor.getRule().updateNAC(EditSelPopupMenu.this.ruleEditor.getNAC());
						EditSelPopupMenu.this.ruleEditor.updateGraphics();
					}
				}
			}
		});

		this.mi = this.addIdentic.add(new JMenuItem("PAC"));
		this.miAddIdenticToPAC = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;
				if (EditSelPopupMenu.this.ruleEditor != null) {
					if (EditSelPopupMenu.this.gp == EditSelPopupMenu.this.ruleEditor.getLeftPanel()) {
						if (EditSelPopupMenu.this.ruleEditor.getPAC() == null) {
							JOptionPane
									.showMessageDialog(
											null,
											"Cannot create an identic object!"
													+ System
															.getProperty("line.separator")
													+ "Please create and open an empty PAC graph first.",
											"Identic Node/Edge",
											JOptionPane.WARNING_MESSAGE);
							return;
						}
						EditSelPopupMenu.this.ruleEditor.getRule()
								.addIdenticToPAC(
										EditSelPopupMenu.this.ruleEditor.getRule().getLeft()
												.getSelectedObjs(),
												EditSelPopupMenu.this.ruleEditor.getPAC());
						EditSelPopupMenu.this.ruleEditor.getRule().updatePAC(EditSelPopupMenu.this.ruleEditor.getPAC());
						EditSelPopupMenu.this.ruleEditor.updateGraphics();
					}
				}
			}
		});
		
		this.mi = this.addIdentic.add(new JMenuItem("General AC"));
		this.miAddIdenticToGAC = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;
				if (EditSelPopupMenu.this.ruleEditor != null) {
					if (EditSelPopupMenu.this.gp == EditSelPopupMenu.this.ruleEditor.getLeftPanel()) {
						if (EditSelPopupMenu.this.ruleEditor.getNestedAC() == null) {
							JOptionPane
									.showMessageDialog(
											null,
											"Cannot create an identic object!"
													+ System
															.getProperty("line.separator")
													+ "Please create and open an empty General AC graph first.",
											"Identic Node/Edge",
											JOptionPane.WARNING_MESSAGE);
							return;
						}
						EditSelPopupMenu.this.ruleEditor.getRule()
								.addIdenticToNestedAC(
										EditSelPopupMenu.this.ruleEditor.getRule().getLeft()
												.getSelectedObjs(),
												EditSelPopupMenu.this.ruleEditor.getNestedAC());
						EditSelPopupMenu.this.ruleEditor.getRule().updateNestedAC(EditSelPopupMenu.this.ruleEditor.getNestedAC());
						EditSelPopupMenu.this.ruleEditor.updateGraphics();
					}
				}
			}
		});
		
		this.mi = add(new JMenuItem("Map Selected"));
		this.miMap = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;

				EditSelPopupMenu.this.mapping = true;
				setLastEditModeBeforMapping(EditSelPopupMenu.this.gp);

				if (EditSelPopupMenu.this.editor != null) {
					EditSelPopupMenu.this.editor.getGraphEditor().setEditMode(EditorConstants.MAPSEL);
					EditSelPopupMenu.this.editor.getRuleEditor().setEditMode(EditorConstants.MAPSEL);
					EditSelPopupMenu.this.editor.getRuleEditor().setObjMapping(true);
					EditSelPopupMenu.this.editor
							.setMsg("Click on a target object you want to map or click on the background you want to break the mapping.");
				} else if (EditSelPopupMenu.this.ruleEditor != null) {
					EditSelPopupMenu.this.ruleEditor.setEditMode(EditorConstants.MAPSEL);
					EditSelPopupMenu.this.ruleEditor.setObjMapping(true);
					EditSelPopupMenu.this.ruleEditor
							.setMsg("Click on on a target object you want to map or click on the background you want to break the mapping.");
				}
			}
		});

		this.mi = add(new JMenuItem("Unmap Selected"));
		this.miUnmap = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;

				EditSelPopupMenu.this.mapping = false;
				unmapSelectedGraphObjects(false);
			}
		});

		addSeparator();

		this.mi = add(new JMenuItem("Set Parent"));
		this.miSetParent = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditSelPopupMenu.this.gp == null 
						|| EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;

				if (EditSelPopupMenu.this.editor != null) {
					EditSelPopupMenu.this.editor.getGraphEditor().setEditMode(
							EditorConstants.SET_PARENT);
					EditSelPopupMenu.this.editor.setMsg("Click on a node to add inheritance relation.");
				}
			}
		});

		this.mi = add(new JMenuItem("Unset Parent"));
		this.miUnsetParent = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((EditSelPopupMenu.this.gp == null) 
						|| (EditSelPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
						|| !EditSelPopupMenu.this.gp.getGraph().isEditable())
					return;
				
				if (EditSelPopupMenu.this.ego instanceof EdNode) {
					Node bNode = EditSelPopupMenu.this.ego.getNode().getBasisNode();
					Graph bGraph = bNode.getContext();
					if (bGraph.isTypeGraph()) {					
						if (EditSelPopupMenu.this.editor != null) {
							if (EditSelPopupMenu.this.graphEditor.getGraph().getBasisGraph()
									.getTypeSet().getLevelOfTypeGraphCheck() != TypeSet.DISABLED) {
								if (EditSelPopupMenu.this.graphEditor.getGraph().getTypeSet()
										.isTypeUsed(EditSelPopupMenu.this.ego.getType())) {
									JOptionPane
											.showMessageDialog(
													EditSelPopupMenu.this.applFrame,
													"Cannot unset inheritance relation."
															+ "\nPlease disable the type graph first.",
													"Unset Parent",
													JOptionPane.ERROR_MESSAGE);
									return;
								}
							}

							if (bNode.getType().getParents().size() == 1) {
								EditSelPopupMenu.this.graphEditor.getGraph().addChangedParentToUndo(
										EditSelPopupMenu.this.ego);
								EditSelPopupMenu.this.gp.getCanvas().updateUndoButton();
								
								EditSelPopupMenu.this.gp.getCanvas().performDeleteInheritanceRel((EdNode) EditSelPopupMenu.this.ego);
		
								EditSelPopupMenu.this.graphEditor.getGraph().undoManagerEndEdit();

								EditSelPopupMenu.this.graphEditor.getGraph().updateGraph();
								EditSelPopupMenu.this.graphEditor.getGraphPanel().updateGraphics();
							} else {
								EditSelPopupMenu.this.editor.getGraphEditor().setEditMode(
										EditorConstants.UNSET_PARENT);
								EditSelPopupMenu.this.editor.setMsg("Click on a parent node to remove inheritance relation.");
							}
						}
					}
				}
			}
		});

		pack();
		setBorderPainted(true);
		// setDefaultLightWeightPopupEnabled(false);
	}

	public void showMe(Component comp, int x, int y) {
		if (this.editor != null) {
			if (this.editor.getRuleEditor().getRule() == null)
				setUnmapEnabled(false);
			if (this.gp.getGraph() != null 
					&& this.gp.getGraph().isTypeGraph())
				setUnmapEnabled(false);
		}
		if (this.useDeleteMenu) {
			int indx = this.getComponentIndex(this.deleteMenu);
			if (indx == -1) {
				indx = this.getComponentIndex(this.miDelete);
				if (indx != -1)
					add(this.deleteMenu, indx + 1);
			}
		} else {
			int indx = this.getComponentIndex(this.deleteMenu);
			if (indx != -1)
				remove(indx);
		}
		show(comp, x, y);
	}

	boolean canDo() {
		if ((this.gp == null) 
				|| (this.gp.getEditMode() == EditorConstants.VIEW)
				|| (this.gp.getGraph() == null))
			return false;
		
		return true;
	}

	void doUpdateAfterDelete() {
		if (this.gp.isMappedObjDeleted()) {
			this.gp.setMappedObjDeleted(false);
			if (this.editor != null) {
				this.editor.getRuleEditor().getRule().update();
				this.editor.getRuleEditor().getLeftPanel().updateGraphics();
				this.editor.getRuleEditor().getRightPanel().updateGraphics();
				this.editor.getRuleEditor().getNACPanel().updateGraphics();
				this.editor.getGraphEditor().getGraph().update();
				this.editor.getGraphEditor().getGraphPanel().updateGraphics();
			} else if (this.ruleEditor != null) {
				this.ruleEditor.getRule().update();
				this.ruleEditor.getLeftPanel().updateGraphics();
				this.ruleEditor.getRightPanel().updateGraphics();
				this.ruleEditor.getNACPanel().updateGraphics();
				if (this.graphEditor != null) {
					this.graphEditor.getGraph().update();
					this.graphEditor.getGraphPanel().updateGraphics();
				}
			} else if (this.graphEditor != null) {
				this.graphEditor.getGraph().update();
				this.graphEditor.getGraphPanel().updateGraphics();
			}
		} else {
			this.gp.getGraph().update();
			this.gp.updateGraphicsAfterDelete();
		}
	}

	void showMessageDialog(List<String> failed, final String typename, boolean nodetype) {
		String nt = nodetype? "node": "arc";
		String str = "Cannot delete objects of the "+nt+" type  \""+typename+"\"  from \n";
		for (int i = 0; i < failed.size(); i++) {
			String s = "\t" + failed.get(i) + "\n";
			str = str + s;
		}
		if (!failed.isEmpty())
			JOptionPane.showMessageDialog(this.applFrame, str);
	}

	private JMenuItem createDeleteItem() {
		JMenuItem m = add(new JMenuItem("Delete Selected"));
		m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				if (EditSelPopupMenu.this.gp.getGraph().hasSelection()) {
					int answer = removeWarning();
					if (answer == JOptionPane.YES_OPTION) {
						for (int i = 0; i < EditSelPopupMenu.this.gp.getGraph().getSelectedObjs()
								.size(); i++) {
							EdGraphObject go = EditSelPopupMenu.this.gp.getGraph().getSelectedObjs()
									.elementAt(i);
							if (!go.getMorphismMark().equals(""))
								EditSelPopupMenu.this.gp.setMappedObjDeleted(true);
						}
						unmapSelectedGraphObjects(true);
						EditSelPopupMenu.this.gp.deleteSelected();
						doUpdateAfterDelete();
						EditSelPopupMenu.this.ego = null;
					}
				}
			}
		});
		return m;
	}

	Vector<EdType> getSelectedTypes(Vector<EdGraphObject> selectedGraphObjects) {
		Vector<EdType> v = new Vector<EdType>(5);
		for (int i = 0; i < selectedGraphObjects.size(); i++) {
			EdGraphObject go = selectedGraphObjects.get(i);
			EdType t = go.getType();
			if (!v.contains(t))
				v.add(t);
		}
		return v;
	}

	private JMenu createDeleteMenu() {
		JMenu m = new JMenu("Delete Objects of Selected Types");
		JMenuItem jmi = m.add(new JMenuItem("Delete All Objects"));
		jmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;
				EditSelPopupMenu.this.mapping = false;

				if (EditSelPopupMenu.this.gp.getGraph().hasSelection()) {
					List<EdGraphObject> selTypes = EditSelPopupMenu.this.gp.getGraph().getSelectedObjs();					
					for(int i=0; i<selTypes.size(); i++) {
						EdGraphObject tgo = selTypes.get(i);						
						int ok = 0;
//						String failStr = EditSelPopupMenu.this.gp.getGraph().getGraGra()
//											.kernelRuleContainsObjsOfType(tgo);
//						if (failStr != null) {
//							String str = "The kernel rule:  "+failStr+"  \n"
//								+"contains objects of type :  <"+tgo.getTypeName()+">  to delete.\n"
//								+"Currently, AGG does not support Undo/Redo in this case.\n\n"
//								+"Do you want to delete objects of this type anyway?";
//							ok = JOptionPane.showConfirmDialog(EditSelPopupMenu.this.applFrame, str, 
//									"Delete Objects of Type", JOptionPane.WARNING_MESSAGE);
//						}
//						boolean addToUndo = (failStr == null);	
						
						boolean addToUndo = true;
						if (ok == 0) {
							List<String> failed = EditSelPopupMenu.this.gp.getGraph().getGraGra()
										.deleteGraphObjectsOfType(tgo, false, addToUndo);
							
							showMessageDialog(failed, tgo.getType().getName(), tgo.isNode());
						} 
					}
				}

				doUpdateAfterDelete();
				EditSelPopupMenu.this.gp.getCanvas().updateUndoButton();
				EditSelPopupMenu.this.gp.getGraph().getGraGra().update();
				if (EditSelPopupMenu.this.editor != null)
					EditSelPopupMenu.this.editor.getRuleEditor().updateGraphics();
			}
		});
		jmi = m.add(new JMenuItem("Delete Objects of Host Graph"));
		jmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;
				EditSelPopupMenu.this.mapping = false;

				if (EditSelPopupMenu.this.gp.getGraph().hasSelection()) {
					List<EdGraphObject> selTypes = EditSelPopupMenu.this.gp.getGraph().getSelectedObjs();
					List<String> failed = new Vector<String>();
					for(int i=0; i<selTypes.size(); i++) {
						EdGraphObject tgo = selTypes.get(i);
						if (!EditSelPopupMenu.this.gp.getGraph().getGraGra()
										.deleteGraphObjectsOfTypeFromHostGraph(tgo, true)) {
							failed.add(EditSelPopupMenu.this.gp.getGraph().getGraGra().getGraph().getName());
						}
						showMessageDialog(failed, tgo.getType().getName(), tgo.isNode());
						failed.clear();
					}
				}

				doUpdateAfterDelete();
				EditSelPopupMenu.this.gp.getCanvas().updateUndoButton();
				EditSelPopupMenu.this.gp.getGraph().getGraGra().getGraph().update();
			}
		});
		
		/*
		 * jmi = m.add(new JMenuItem("Delete Objects of Rules"));
		 * jmi.addActionListener(new ActionListener() {public void
		 * actionPerformed(ActionEvent e) { //System.out.println("Delete Objects
		 * Of Rules"); if(!canDo()) return; mapping = false; if
		 * (gp.getGraph().hasSelection()){ Vector selectedTypes =
		 * getSelectedTypes(gp.getGraph().getSelectedObjs()); Vector failed =
		 * gp.getGraph().getGraGra().getBasisGraGra().destroyGraphObjectsOfTypesFromRules(selectedTypes);
		 * showMessageDialog(failed); }
		 * 
		 * doAfterDelete(); gp.getGraph().getGraGra().updateRules(); if (editor !=
		 * null) editor.getRuleEditor().updateGraphics(); }}); jmi = m.add(new
		 * JMenuItem("Delete Objects of Graph Constraints"));
		 * jmi.addActionListener(new ActionListener() {public void
		 * actionPerformed(ActionEvent e) { //System.out.println("Delete Objects
		 * Of Graph Constraints"); if(!canDo()) return; mapping = false; if
		 * (gp.getGraph().hasSelection()){ Vector selectedTypes =
		 * getSelectedTypes(gp.getGraph().getSelectedObjs()); Vector failed =
		 * gp.getGraph().getGraGra().getBasisGraGra().destroyGraphObjectsOfTypesFromGraphConstraints(selectedTypes);
		 * showMessageDialog(failed); }
		 * 
		 * doAfterDelete(); gp.getGraph().getGraGra().updateConstraints(); if
		 * (editor != null) editor.getRuleEditor().updateGraphics(); }});
		 */
		return m;
	}

	public void setEditor(GraGraEditor ed) {
		this.editor = ed;
		this.ruleEditor = this.editor.getRuleEditor();
		this.graphEditor = this.editor.getGraphEditor();
	}

	public void setEditor(RuleEditor re) {
		this.ruleEditor = re;
		this.graphEditor = null;
	}

	public void setEditor(GraphEditor ge) {
		this.graphEditor = ge;
		this.ruleEditor = null;
	}

	public EdGraphObject getPickedObj() {
		return this.ego;
	}

	public boolean isMapping() {
		return this.mapping;
	}

	public void setParentFrame(JFrame pf) {
		this.applFrame = pf;
	}

	public void setMapEnabled(boolean b) {
		this.miMap.setEnabled(b);
	}

	public void setUnmapEnabled(boolean b) {
		this.miUnmap.setEnabled(b);
	}

	public boolean invoked(GraphPanel p, int x, int y) {
		this.gp = p;
		if (this.gp.getGraph() != null
				&& !this.gp.getCanvas().isLeftPressed()) {
			this.ego = this.gp.getGraph().getPicked(x, y);
			if (this.ego == null) {
				this.ego = this.gp.getGraph().getPickedTextOfArc(x, y,
						this.gp.getCanvas().getGraphics().getFontMetrics());
			}
						
			if (this.ego != null && this.ego.isVisible() && this.ego.isSelected()) {
//				if (gp.getGraph().isTargetObjOfGraphEmbedding(ego)) {
//					return false;
//				}
				
				if (this.ruleEditor != null) {
					if (this.ruleEditor.getRule().getLeft() == this.gp.getGraph())
						this.miAddIdenticToRule.setEnabled(true);
					else
						this.miAddIdenticToRule.setEnabled(false);
					if (this.ruleEditor.getNAC() != null) {				
						this.miAddIdenticToNAC.setEnabled(true);
						this.miAddIdenticToPAC.setEnabled(false);
						this.miAddIdenticToGAC.setEnabled(false);
					} else if (this.ruleEditor.getPAC() != null) {
						this.miAddIdenticToNAC.setEnabled(false);
						this.miAddIdenticToPAC.setEnabled(true);
						this.miAddIdenticToGAC.setEnabled(false);
					} else if (this.ruleEditor.getNestedAC() != null) {
						this.miAddIdenticToNAC.setEnabled(false);
						this.miAddIdenticToPAC.setEnabled(false);
						this.miAddIdenticToGAC.setEnabled(true);
					} else {
						this.miAddIdenticToNAC.setEnabled(false);
						this.miAddIdenticToPAC.setEnabled(false);
						this.miAddIdenticToGAC.setEnabled(false);
					}
				}
				
				if (this.ego.isNode()) {
					this.miStraighten.setEnabled(false);
					if (this.ego.isElementOfTypeGraph()) {
						this.useDeleteMenu = true;
						this.addIdentic.setEnabled(false);
						this.miSetParent.setEnabled(true);
						if (!this.ego.getNode().getBasisNode().getType()
								.getParents().isEmpty())
							this.miUnsetParent.setEnabled(true);
						else
							this.miUnsetParent.setEnabled(false);
					} else {
						this.useDeleteMenu = false;
						this.addIdentic.setEnabled(false);
						this.miSetParent.setEnabled(false);
						this.miUnsetParent.setEnabled(false);
					}
					if (this.ruleEditor != null 
							&& this.gp == this.ruleEditor.getLeftPanel())
						this.addIdentic.setEnabled(true);
				} else {
					this.miStraighten.setEnabled(true);
					this.miSetParent.setEnabled(false);
					this.miUnsetParent.setEnabled(false);
					if (this.ego.isElementOfTypeGraph()) {
						this.useDeleteMenu = true;
						this.addIdentic.setEnabled(false);
					} else {
						this.useDeleteMenu = false;
						this.addIdentic.setEnabled(false);
					}
					if (this.ruleEditor != null 
							&& this.gp == this.ruleEditor.getLeftPanel())
						this.addIdentic.setEnabled(true);
				}
				return true;
			} 
			return false;
		} 
		return false;
	}

	void setLastEditModeBeforMapping(GraphPanel gp) {
		if (gp.getEditMode() == EditorConstants.DRAW
				|| gp.getEditMode() == EditorConstants.ARC
				|| gp.getEditMode() == EditorConstants.SELECT
				|| gp.getEditMode() == EditorConstants.MOVE
				|| gp.getEditMode() == EditorConstants.ATTRIBUTES
				|| gp.getEditMode() == EditorConstants.MAP
				|| gp.getEditMode() == EditorConstants.UNMAP
				|| gp.getEditMode() == EditorConstants.INTERACT_RULE
				|| gp.getEditMode() == EditorConstants.INTERACT_NAC
				|| gp.getEditMode() == EditorConstants.INTERACT_PAC
				|| gp.getEditMode() == EditorConstants.INTERACT_AC
				|| gp.getEditMode() == EditorConstants.INTERACT_MATCH) {
			// System.out.println(">>> setLastEditModeBeforMapping");
			gp.setLastEditMode(gp.getEditMode());
			gp.setLastEditCursor(gp.getEditCursor());
		}
	}

	protected void unmapSelectedGraphObjects(boolean wantDeleteGraphObject) {
		if (this.editor != null 
				&& this.editor.getRuleEditor().getRule() != null) {
			EdRule rule = this.editor.getRuleEditor().getRule();
			boolean unmapdone = false;
			if (this.editor.getActivePanel() == this.editor.getRuleEditor().getLeftPanel()) {
				EdGraphObject lgo = null;
				for (int i = 0; i < rule.getLeft().getSelectedObjs().size(); i++) {
					lgo = rule.getLeft().getSelectedObjs().get(i);

					if (wantDeleteGraphObject) {
						if (this.editor.getRuleEditor().removeNacMapping(lgo)
								|| this.editor.getRuleEditor().removePacMapping(lgo)
								|| this.editor.getRuleEditor().removeNestedACMapping(lgo))
							unmapdone = true;
					} else {
						if (this.editor.getRuleEditor().removeNacMapping(lgo, true)
								|| this.editor.getRuleEditor().removePacMapping(lgo, true)
								|| this.editor.getRuleEditor().removeNestedACMapping(lgo, true))
							unmapdone = true;
					} 

					if (rule.getMatch() != null) {
						if (this.editor.getRuleEditor().removeMatchMapping(lgo, true))
							unmapdone = true;
						if (wantDeleteGraphObject)
							rule.getMatch().getCompletionStrategy().removeFromObjectVarMap(lgo.getBasisObject());
					} 
					
					if (this.editor.getRuleEditor().removeRuleMapping(lgo, true))
						unmapdone = true;
				}
				if (unmapdone) {
					rule.update();
					this.editor.updateGraphics();
				}
			} else if (this.editor.getActivePanel() == this.editor.getRuleEditor().getRightPanel()) {
				for (int i = 0; i < rule.getRight().getSelectedObjs().size(); i++) {
					EdGraphObject rgo = rule.getRight().getSelectedObjs().get(i);
					if (this.editor.getRuleEditor().removeRuleMapping(rgo, false))
						unmapdone = true;
				}
				if (unmapdone)
					this.editor.getRuleEditor().updateGraphics();
			} else if (this.editor.getActivePanel() == this.editor.getRuleEditor().getLeftCondPanel()) {
				List<EdGraphObject> l = null;
				if (this.editor.getRuleEditor().getNAC() != null) {
					l = this.editor.getRuleEditor().getNAC().getSelectedObjs();
					EdGraphObject go = null;
					for (int i = 0; i < l.size(); i++) {
						go = l.get(i);
						if (this.editor.getRuleEditor().removeNacMapping(go, false))
							unmapdone = true;
					}
				}
				else if (this.editor.getRuleEditor().getPAC() != null) {
					l = this.editor.getRuleEditor().getPAC().getSelectedObjs();
					EdGraphObject go = null;
					for (int i = 0; i < l.size(); i++) {
						go = l.get(i);
						if (this.editor.getRuleEditor().removePacMapping(go, false))
							unmapdone = true;
					}
				}
				else if (this.editor.getRuleEditor().getNestedAC() != null) {
					l = this.editor.getRuleEditor().getNestedAC().getSelectedObjs();
					EdGraphObject go = null;
					for (int i = 0; i < l.size(); i++) {
						go = l.get(i);
						if (this.editor.getRuleEditor().removeNestedACMapping(go, false)) {
							unmapdone = true;
						}
					}
					this.editor.getRuleEditor().updateNestedAC(this.editor.getRuleEditor().getNestedAC());
//					this.editor.getRuleEditor().getRule().updateNestedAC(this.editor.getRuleEditor().getNestedAC());
				}
				if (unmapdone) {
					this.editor.getRuleEditor().getLeftPanel().updateGraphics();
					this.editor.getRuleEditor().getLeftCondPanel().updateGraphics();
				}
			} else if (this.editor.getActivePanel() == this.editor.getGraphEditor()
					.getGraphPanel()) {
				if (rule.getMatch() != null) {
					EdGraphObject ggo = null;
					for (int i = 0; i < this.editor.getGraphEditor().getGraph()
							.getSelectedObjs().size(); i++) {
						ggo = this.editor.getGraphEditor().getGraph().getSelectedObjs().get(i);
						if (this.editor.getRuleEditor().removeMatchMapping(ggo, false))
							unmapdone = true;
					}
					if (unmapdone) {
						this.editor.getRuleEditor().getLeftPanel().updateGraphics();
						this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
				}
			}
		} else if (this.ruleEditor != null && this.ruleEditor.getRule() != null) {
			if (this.gp == this.ruleEditor.getLeftPanel()
					&& this.ruleEditor.getRule().getLeft() == this.gp.getGraph()) {
				EdGraphObject lObj = null;
				for (int i = 0; i < this.ruleEditor.getRule().getLeft()
						.getSelectedObjs().size(); i++) {
					lObj = this.ruleEditor.getRule().getLeft().getSelectedObjs().get(i);
					this.ruleEditor.getRule().removeRuleMapping(lObj);
					if (this.ruleEditor.getNAC() != null)
						this.ruleEditor.getRule().removeNACMapping(lObj,
								this.ruleEditor.getNAC().getMorphism());
					if (this.ruleEditor.getPAC() != null)
						this.ruleEditor.getRule().removePACMapping(lObj,
								this.ruleEditor.getPAC().getMorphism());

					this.ruleEditor.getRule().removeMatchMapping(lObj);
				}
				this.ruleEditor.getRule().update();
				this.ruleEditor.updateGraphics();
				if (this.graphEditor != null)
					this.graphEditor.getGraphPanel().updateGraphics();
			} else if (this.gp == this.ruleEditor.getRightPanel()) {
				for (int i = 0; i < this.ruleEditor.getRule().getRight()
						.getSelectedObjs().size(); i++) {
					EdGraphObject imageObj = this.ruleEditor.getRule().getRight()
							.getSelectedObjs().elementAt(i);
					this.ruleEditor.getRule().removeMapping(imageObj,
							this.ruleEditor.getRule().getBasisRule());
				}
				this.ruleEditor.updateGraphics();
			} else if (this.gp == this.ruleEditor.getLeftCondPanel()) {
				if (this.ruleEditor.getNAC() != null) {
					for (int i = 0; i < this.ruleEditor.getNAC().getSelectedObjs()
							.size(); i++) {
						EdGraphObject imageObj = this.ruleEditor.getNAC()
								.getSelectedObjs().elementAt(i);
						this.ruleEditor.getRule().removeMapping(imageObj,
								this.ruleEditor.getNAC().getMorphism());
					}
				}
				if (this.ruleEditor.getPAC() != null) {
					for (int i = 0; i < this.ruleEditor.getPAC().getSelectedObjs()
							.size(); i++) {
						EdGraphObject imageObj = this.ruleEditor.getPAC()
								.getSelectedObjs().elementAt(i);
						this.ruleEditor.getRule().removeMapping(imageObj,
								this.ruleEditor.getPAC().getMorphism());
					}
				}
				this.ruleEditor.getLeftPanel().updateGraphics();
				this.ruleEditor.getNACPanel().updateGraphics();
			}
		} else if (this.graphEditor != null 
				&& this.gp == this.graphEditor.getGraphPanel()) {
			for (int i = 0; i < this.graphEditor.getGraph().getSelectedObjs().size(); i++) {
				EdGraphObject imageObj = this.graphEditor.getGraph()
						.getSelectedObjs().elementAt(i);
				if (this.ruleEditor != null)
					this.ruleEditor.getRule().removeMapping(imageObj,
							this.ruleEditor.getRule().getMatch());
			}
			if (this.ruleEditor != null)
				this.ruleEditor.getLeftPanel().updateGraphics();
			this.graphEditor.getGraphPanel().updateGraphics();
		}
	}

	int removeWarning() {
		String msgStr = "Do you really want to delete all \nselected objects of this graph?";
		Object[] options = { "YES", "NO" };
		int answer = JOptionPane.showOptionDialog(null, msgStr, "Warning",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				options, options[1]);
		return answer;
	}

	JFrame applFrame;

	JMenuItem mi, miDelete, miMap, miUnmap, miStraighten, miAddIdenticToRule,
			miAddIdenticToNAC, miAddIdenticToPAC, miAddIdenticToGAC,
			miSetParent, miUnsetParent;

	JMenu deleteMenu, addIdentic;

	boolean mapping = false;

	GraGraEditor editor;

	RuleEditor ruleEditor;

	GraphEditor graphEditor;

	GraphPanel gp;

	EdGraphObject ego;

	boolean useDeleteMenu = false;;

	Vector<Type> selectedTypes;
}

// $Log: EditSelPopupMenu.java,v $
// Revision 1.18  2010/10/16 22:44:43  olga
// improved undo for RuleScheme graph objects
//
// Revision 1.17  2010/09/30 14:10:03  olga
// delete objects of especial type -  improved
//
// Revision 1.16  2010/09/27 22:45:25  olga
// improved
//
// Revision 1.15  2010/08/25 08:23:30  olga
// tuning
//
// Revision 1.14  2010/08/18 16:57:40  olga
// extended
//
// Revision 1.13  2010/06/09 11:04:43  olga
// extended due to new NestedApplCond
//
// Revision 1.12  2010/04/14 13:23:28  olga
// select and move in non-editable graphs where create and delete objects not allowed
//
// Revision 1.11  2010/03/15 10:39:44  olga
// rename refactoring
//
// Revision 1.10  2010/03/10 14:46:35  olga
// make identical rule - bug fixed
//
// Revision 1.9  2010/03/08 15:43:28  olga
// code optimizing
//
// Revision 1.8  2010/02/22 15:03:02  olga
// code optimizing
//
// Revision 1.7  2009/11/09 10:42:06  olga
// tuning
//
// Revision 1.6  2009/07/16 17:21:03  olga
// GUI bugs fixed
//
// Revision 1.5  2009/06/02 12:39:27  olga
// Min Multiplicity check - bug fixed
//
// Revision 1.4  2009/05/28 13:18:28  olga
// Amalgamated graph transformation - development stage
//
// Revision 1.3  2009/04/02 14:39:33  olga
// code tuning
//
// Revision 1.2  2009/01/19 12:34:02  olga
// AGG tuning
//
// Revision 1.1  2008/10/29 09:04:13  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.21  2008/10/01 10:24:55  olga
// AGG GUI:  mouse usability  extended - simulation of the middle button by left and right button pressed simultaneously
//
// Revision 1.20  2008/09/04 07:49:24  olga
// GUI extension: hide nodes, edges
//
// Revision 1.19  2008/07/14 07:35:47  olga
// Applicability of RS - new option added, more tuning
// Node animation - new animation parameter added,
// Undo edit manager - possibility to disable it when graph transformation
// because it costs much more time and memory
//
// Revision 1.18  2008/07/02 17:14:36  olga
// Code tuning
//
// Revision 1.17  2008/04/07 09:36:52  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.16  2007/12/03 08:35:11  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.15  2007/11/01 09:58:13  olga
// Code refactoring: generic types- done
//
// Revision 1.14  2007/09/24 09:42:35  olga
// AGG transformation engine tuning
//
// Revision 1.13  2007/09/10 13:05:24  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.12 2007/06/13 08:32:54 olga
// Update: V161
//
// Revision 1.11 2007/05/02 14:05:57 olga
// Null pointer fixed.
//
// Revision 1.10 2007/04/19 07:52:40 olga
// Tuning of: Undo/Redo, Graph layouter, loading grammars
//
// Revision 1.9 2007/04/11 10:03:30 olga
// Undo, Redo tuning,
// Simple Parser- bug fixed
//
// Revision 1.8 2007/03/28 10:00:44 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.7 2007/01/11 10:21:05 olga
// Optimized Version 1.5.1beta , free for tests
//
// Revision 1.6 2006/12/13 13:32:55 enrico
// reimplemented code
//
// Revision 1.5 2006/08/02 09:00:56 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.4 2006/04/03 08:57:50 olga
// New: Import Type Graph
// and some bugs fixed
//
// Revision 1.3 2006/03/01 09:55:47 olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.2 2005/11/07 09:38:07 olga
// Null pointer during retype attr. member fixed.
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.2 2005/06/20 13:37:03 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:03 olga
// Version with Eclipse
//
// Revision 1.3 2003/03/05 18:24:16 komm
// sorted/optimized import statements
//
// Revision 1.2 2002/11/11 10:45:04 komm
// no change
//
// Revision 1.1.1.1 2002/07/11 12:17:10 olga
// Imported sources
//
// Revision 1.17 2001/05/14 11:59:19 olga
// Das Zusammenspiel zwischen AGG GUI und Parser/CP GUIs optimiert.
// Neue Transformationsart implementiert: TransformLayered.java
//
// Revision 1.16 2001/04/11 14:56:48 olga
// Arbeit an der GUI.
//
// Revision 1.15 2001/03/08 11:00:02 olga
// Das ist Stand nach der AGG GUI Reimplementierung
// und Parser Anbindung.
//
// Revision 1.14 2000/12/21 09:48:58 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.13.8.1 2000/11/06 09:32:46 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.13 1999/12/22 12:36:45 shultzke
// The user cannot edit the context of graphs. Only in rules it is possible.
//
