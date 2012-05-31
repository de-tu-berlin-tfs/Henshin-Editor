package agg.gui.popupmenu;

import java.util.List;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdRule;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraGraEditor;
import agg.gui.editor.GraphEditor;
import agg.gui.editor.GraphPanel;
import agg.gui.editor.RuleEditor;
import agg.gui.treeview.dialog.TypeCardinalityDialog;
import agg.gui.treeview.nodedata.GraGraTextualComment;
import agg.xt_basis.TypeSet;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.Node;
import agg.layout.evolutionary.EvolutionaryGraphLayout;

/**
 * @author $Author: olga $
 * @version $Id: EditPopupMenu.java,v 1.26 2010/10/16 22:44:43 olga Exp $
 * 
 */
public class EditPopupMenu extends JPopupMenu {
	
	public EditPopupMenu() {
		super("Operations");
//		setLabel("Operations");
		setBorderPainted(true);

		this.deleteMenu = createDeleteMenu();
		this.useDeleteMenu = false;

		this.mi = add(new JMenuItem("      Operations"));
//		miOperations = this.mi;
		// this.mi.setEnabled(false);
		addSeparator();
		
		this.mi = add(new JMenuItem("Attributes ..."));
		// this.mi.setMnemonic('A');
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;
				EditPopupMenu.this.mapping = false;
				// gp.getCanvas().saveScrollBarValue();

				if (EditPopupMenu.this.ruleEditor == null)
					EditPopupMenu.this.editor.setAttrEditorOnTopForGraphObject(EditPopupMenu.this.ego);
				else
					EditPopupMenu.this.editor.setAttrEditorOnBottomForGtaphObject(EditPopupMenu.this.ego);
				EditPopupMenu.this.ego.setWeakselected(true);
			}
		});

		this.miObjName = new JMenuItem("Object Name");
		add(this.miObjName);
		this.miObjName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;
				EditPopupMenu.this.mapping = false;
				setObjectName(EditPopupMenu.this.ego);
			}
		});

		
		addSeparator();

		this.mi = add(new JMenuItem("Copy"));
		// this.mi.setMnemonic('C');
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;
				EditPopupMenu.this.mapping = false;
				EditPopupMenu.this.gp.setLastEditMode(EditPopupMenu.this.gp.getEditMode());
				EditPopupMenu.this.gp.setLastEditCursor(EditPopupMenu.this.gp.getEditCursor());
				EditPopupMenu.this.gp.getGraph().eraseSelected(EditPopupMenu.this.gp.getCanvas().getGraphics(), true);
				EditPopupMenu.this.gp.getGraph().deselectAll();
				EditPopupMenu.this.gp.getGraph().select(EditPopupMenu.this.xPos, EditPopupMenu.this.yPos);
				EditPopupMenu.this.gp.getGraph().drawSelected(EditPopupMenu.this.gp.getCanvas().getGraphics());
				// EditPopupMenu.this.gp.updateGraphics();
				if (EditPopupMenu.this.ego.isNode()) {
					EditPopupMenu.this.gp.setEditMode(EditorConstants.COPY);
					if (EditPopupMenu.this.editor != null)
						EditPopupMenu.this.editor
								.setMsg("To get a copy of a node click on the background of the same panel.");
				} else {
					EditPopupMenu.this.gp.setEditMode(EditorConstants.COPY_ARC);
					if (EditPopupMenu.this.editor != null)
						EditPopupMenu.this.editor
								.setMsg("To get a copy of an edge click on a source node and a target node of the same panel.");
				}
				EditPopupMenu.this.applFrame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
		});

		this.mi = add(new JMenuItem("Select"));
		// this.mi.setMnemonic('S');
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null 
						|| EditPopupMenu.this.gp.getGraph() == null 
						|| EditPopupMenu.this.ego == null)
					return;

				EditPopupMenu.this.mapping = false;
				EdGraphObject go = EditPopupMenu.this.gp.select(EditPopupMenu.this.xPos, EditPopupMenu.this.yPos);
				if (go.isNode())
					EditPopupMenu.this.gp.getGraph().drawNode(EditPopupMenu.this.gp.getCanvas().getGraphics(), (EdNode) go);
				else
					EditPopupMenu.this.gp.getGraph().drawArc(EditPopupMenu.this.gp.getCanvas().getGraphics(), (EdArc) go);
				// gp.updateGraphics();

			}
		});

		this.mi = add(new JMenuItem("Select All"));
		// this.mi.setMnemonic('l');
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null || EditPopupMenu.this.gp.getGraph() == null)
					return;
				EditPopupMenu.this.mapping = false;
				EditPopupMenu.this.gp.selectAll();
//				EditPopupMenu.this.ego = null;
			}
		});
		addSeparator();

		this.miDelete = createDeleteItem();
		this.deleteMenu = createDeleteMenu();
		add(this.miDelete);
		if (this.useDeleteMenu)
			add(this.deleteMenu);
		addSeparator();

		this.miStraighten = add(new JMenuItem("Straighten"));
		// this.mi.setMnemonic('g');
		this.miStraighten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				EditPopupMenu.this.mapping = false;

//				EditPopupMenu.this.gp.getGraph().addMovedToUndo(ego);
				EditPopupMenu.this.gp.getGraph().straightArc(EditPopupMenu.this.ego);
//				EditPopupMenu.this.gp.getGraph().undoManagerEndEdit();
//				EditPopupMenu.this.gp.getCanvas().updateUndoButton();

				EditPopupMenu.this.gp.updateGraphics();
			}
		});

		this.miVisibility = add(new JMenuItem("Hide Objects of Type"));
		this.miVisibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				EditPopupMenu.this.mapping = false;
				
				if (((JMenuItem)e.getSource()).getText().indexOf("Hide") == 0) {
					EditPopupMenu.this.gp.getGraph().setVisibilityOfGraphObjectsOfType(EditPopupMenu.this.ego, false);
				} else if (((JMenuItem)e.getSource()).getText().indexOf("Show") == 0) {
					EditPopupMenu.this.gp.getGraph().setVisibilityOfGraphObjectsOfType(EditPopupMenu.this.ego, true);
				}
				
				EditPopupMenu.this.gp.updateGraphics();
			}
		});
		
		this.layout = new JMenu("Graph Layout");
		// layout.setMnemonic('y');
		add(this.layout);
		this.miFrozen = (JCheckBoxMenuItem) this.layout.add(new JCheckBoxMenuItem(
				"Static Position"));
		this.miFrozen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				if (EditPopupMenu.this.ego instanceof EdNode) {
					if (EditPopupMenu.this.ego.getBasisObject().getContext().isTypeGraph()) {
						if (EditPopupMenu.this.layouter != null 
								&& EditPopupMenu.this.editor != null) {
							EditPopupMenu.this.editor.getGraGra().createLayoutPattern(
									"Freezing",
									"node",
									EditPopupMenu.this.ego.getBasisObject().getType(),
									((JCheckBoxMenuItem) e.getSource())
											.isSelected());
						}
					} else {
						if (((JCheckBoxMenuItem) e.getSource()).isSelected())
							((EdNode) EditPopupMenu.this.ego).getLNode().setFrozen(true);
						else
							((EdNode) EditPopupMenu.this.ego).getLNode().setFrozen(false);
					}
				}
			}
		});

		addSeparator();

		this.addIdentic = new JMenu("Add Identic To");
		add(this.addIdentic);
		this.mi = this.addIdentic.add(new JMenuItem("Rule RHS"));
		miAddIdenticToRule = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				if (EditPopupMenu.this.ruleEditor != null) {
					if (EditPopupMenu.this.gp == EditPopupMenu.this.ruleEditor.getLeftPanel()) {
						EdGraphObject img = null;
						if (EditPopupMenu.this.ego.isNode()) {
							img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(EditPopupMenu.this.ego);
							if (img != null) {
								EditPopupMenu.this.ruleEditor.getRule().updateRule();
								EditPopupMenu.this.ruleEditor.updateGraphics();
							}
						} else {
							Node src = (Node) EditPopupMenu.this.ruleEditor.getRule()
									.getBasisRule().getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getSource());
							Node tar = (Node) EditPopupMenu.this.ruleEditor.getRule()
									.getBasisRule().getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getTarget());
							Object[] options = { "Yes", "No" };

							if (src == null && tar != null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
											((EdArc) EditPopupMenu.this.ego).getSource());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
												EditPopupMenu.this.ego);
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateRule();
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src != null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The target node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
											((EdArc) EditPopupMenu.this.ego).getTarget());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
												EditPopupMenu.this.ego);
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateRule();
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src == null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source and target nodes are not defined."
														+ System
																.getProperty("line.separator")
														+ "Should they be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
											((EdArc) EditPopupMenu.this.ego).getSource());
									if (((EdArc) EditPopupMenu.this.ego).getSource() != ((EdArc) EditPopupMenu.this.ego)
											.getTarget()
											&& img != null)
										img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
												((EdArc) EditPopupMenu.this.ego).getTarget());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(
												EditPopupMenu.this.ego);
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateRule();
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else {
								img = EditPopupMenu.this.ruleEditor.getRule().addIdentic(EditPopupMenu.this.ego);
								EditPopupMenu.this.ruleEditor.getRule().updateRule();
								EditPopupMenu.this.ruleEditor.updateGraphics();
							}
						}
					}
				}
			}
		});

		this.mi = this.addIdentic.add(new JMenuItem("NAC"));
		this.miAddIdenticToNAC = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				if (EditPopupMenu.this.ruleEditor != null) {
					if (EditPopupMenu.this.gp == EditPopupMenu.this.ruleEditor.getLeftPanel()) {
						if (EditPopupMenu.this.ruleEditor.getNAC() == null) {
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
						EdGraphObject img = null;
						if (EditPopupMenu.this.ego.isNode()) {
							img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNAC(EditPopupMenu.this.ego,
									EditPopupMenu.this.ruleEditor.getNAC());
							if (img != null) {
								EditPopupMenu.this.ruleEditor.getRule().updateNAC(
										EditPopupMenu.this.ruleEditor.getNAC());
								EditPopupMenu.this.ruleEditor.updateGraphics();
							}
						} else {
							Node src = (Node) EditPopupMenu.this.ruleEditor.getNAC().getMorphism()
									.getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getSource());
							Node tar = (Node) EditPopupMenu.this.ruleEditor.getNAC().getMorphism()
									.getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getTarget());
							Object[] options = { "Yes", "No" };

							if (src == null && tar != null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNAC(
											((EdArc) EditPopupMenu.this.ego).getSource(),
											EditPopupMenu.this.ruleEditor.getNAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getNAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateNAC(
												EditPopupMenu.this.ruleEditor.getNAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src != null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The target node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNAC(
											((EdArc) EditPopupMenu.this.ego).getTarget(),
											EditPopupMenu.this.ruleEditor.getNAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getNAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateNAC(
												EditPopupMenu.this.ruleEditor.getNAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src == null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source and target nodes are not defined."
														+ System
																.getProperty("line.separator")
														+ "Should they be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNAC(
											((EdArc) EditPopupMenu.this.ego).getSource(),
											EditPopupMenu.this.ruleEditor.getNAC());
									if (((EdArc) EditPopupMenu.this.ego).getSource() != ((EdArc) EditPopupMenu.this.ego)
											.getTarget()
											&& img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNAC(
														((EdArc) EditPopupMenu.this.ego)
																.getTarget(),
																EditPopupMenu.this.ruleEditor.getNAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getNAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateNAC(
												EditPopupMenu.this.ruleEditor.getNAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else {
								img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNAC(EditPopupMenu.this.ego,
										EditPopupMenu.this.ruleEditor.getNAC());
								if (img != null) {
									EditPopupMenu.this.ruleEditor.getRule().updateNAC(
											EditPopupMenu.this.ruleEditor.getNAC());
									EditPopupMenu.this.ruleEditor.updateGraphics();
								}
							}
						}
					}
				}
			}
		});

		this.mi = this.addIdentic.add(new JMenuItem("PAC"));
		this.miAddIdenticToPAC = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				if (EditPopupMenu.this.ruleEditor != null) {
					if (EditPopupMenu.this.gp == EditPopupMenu.this.ruleEditor.getLeftPanel()) {
						if (EditPopupMenu.this.ruleEditor.getPAC() == null) {
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
						EdGraphObject img = null;
						if (EditPopupMenu.this.ego.isNode()) {
							img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToPAC(EditPopupMenu.this.ego,
									EditPopupMenu.this.ruleEditor.getPAC());
							if (img != null) {
								EditPopupMenu.this.ruleEditor.getRule().updatePAC(
										EditPopupMenu.this.ruleEditor.getPAC());
								EditPopupMenu.this.ruleEditor.updateGraphics();
							}
						} else {
							Node src = (Node) EditPopupMenu.this.ruleEditor.getPAC().getMorphism()
									.getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getSource());
							Node tar = (Node) EditPopupMenu.this.ruleEditor.getPAC().getMorphism()
									.getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getTarget());
							Object[] options = { "Yes", "No" };

							if (src == null && tar != null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToPAC(
											((EdArc) EditPopupMenu.this.ego).getSource(),
											EditPopupMenu.this.ruleEditor.getPAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToPAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getPAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updatePAC(
												EditPopupMenu.this.ruleEditor.getPAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src != null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The target node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToPAC(
											((EdArc) EditPopupMenu.this.ego).getTarget(),
											EditPopupMenu.this.ruleEditor.getPAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToPAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getPAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updatePAC(
												EditPopupMenu.this.ruleEditor.getPAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src == null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source and target nodes are not defined."
														+ System
																.getProperty("line.separator")
														+ "Should they be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToPAC(
											((EdArc) EditPopupMenu.this.ego).getSource(),
											EditPopupMenu.this.ruleEditor.getPAC());
									if (((EdArc) EditPopupMenu.this.ego).getSource() != ((EdArc) EditPopupMenu.this.ego)
											.getTarget()
											&& img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToPAC(
														((EdArc) EditPopupMenu.this.ego)
																.getTarget(),
																EditPopupMenu.this.ruleEditor.getPAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToPAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getPAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updatePAC(
												EditPopupMenu.this.ruleEditor.getPAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else {
								img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToPAC(EditPopupMenu.this.ego,
										EditPopupMenu.this.ruleEditor.getPAC());
								if (img != null) {
									EditPopupMenu.this.ruleEditor.getRule().updatePAC(
											EditPopupMenu.this.ruleEditor.getPAC());
									EditPopupMenu.this.ruleEditor.updateGraphics();
								}
							}
						}
					}
				}
			}
		});

		this.mi = this.addIdentic.add(new JMenuItem("General AC"));
		this.miAddIdenticToGAC = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				if (EditPopupMenu.this.ruleEditor != null) {
					if (EditPopupMenu.this.gp == EditPopupMenu.this.ruleEditor.getLeftPanel()) {
						if (EditPopupMenu.this.ruleEditor.getNestedAC() == null) {
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
						EdGraphObject img = null;
						if (EditPopupMenu.this.ego.isNode()) {
							img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNestedAC(EditPopupMenu.this.ego,
									EditPopupMenu.this.ruleEditor.getNestedAC());
							if (img != null) {
								EditPopupMenu.this.ruleEditor.getRule().updateNestedAC(
										EditPopupMenu.this.ruleEditor.getNestedAC());
								EditPopupMenu.this.ruleEditor.updateGraphics();
							}
						} else {
							Node src = (Node) EditPopupMenu.this.ruleEditor.getNestedAC().getMorphism()
									.getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getSource());
							Node tar = (Node) EditPopupMenu.this.ruleEditor.getNestedAC().getMorphism()
									.getImage(
											((Arc) EditPopupMenu.this.ego.getBasisObject())
													.getTarget());
							Object[] options = { "Yes", "No" };

							if (src == null && tar != null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNestedAC(
											((EdArc) EditPopupMenu.this.ego).getSource(),
											EditPopupMenu.this.ruleEditor.getNestedAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNestedAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getNestedAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateNestedAC(
												EditPopupMenu.this.ruleEditor.getNestedAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src != null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The target node is not defined."
														+ System
																.getProperty("line.separator")
														+ "Should it be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNestedAC(
											((EdArc) EditPopupMenu.this.ego).getTarget(),
											EditPopupMenu.this.ruleEditor.getNestedAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNestedAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getNestedAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateNestedAC(
												EditPopupMenu.this.ruleEditor.getNestedAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else if (src == null && tar == null) {
								int answer = JOptionPane
										.showOptionDialog(
												null,
												"Cannot create an identic edge!"
														+ System
																.getProperty("line.separator")
														+ "The source and target nodes are not defined."
														+ System
																.getProperty("line.separator")
														+ "Should they be created now?",
												"Identic Edge",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.QUESTION_MESSAGE,
												null, options, options[0]);
								if (answer == JOptionPane.YES_OPTION) {
									img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNestedAC(
											((EdArc) EditPopupMenu.this.ego).getSource(),
											EditPopupMenu.this.ruleEditor.getNestedAC());
									if (((EdArc) EditPopupMenu.this.ego).getSource() != ((EdArc) EditPopupMenu.this.ego)
											.getTarget()
											&& img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNestedAC(
														((EdArc) EditPopupMenu.this.ego)
																.getTarget(),
																EditPopupMenu.this.ruleEditor.getNestedAC());
									if (img != null)
										img = EditPopupMenu.this.ruleEditor.getRule()
												.addIdenticToNestedAC(EditPopupMenu.this.ego,
														EditPopupMenu.this.ruleEditor.getNestedAC());
									if (img != null) {
										EditPopupMenu.this.ruleEditor.getRule().updateNestedAC(
												EditPopupMenu.this.ruleEditor.getNestedAC());
										EditPopupMenu.this.ruleEditor.updateGraphics();
									}
								}
							} else {
								img = EditPopupMenu.this.ruleEditor.getRule().addIdenticToNestedAC(EditPopupMenu.this.ego,
										EditPopupMenu.this.ruleEditor.getNestedAC());
								if (img != null) {
									EditPopupMenu.this.ruleEditor.getRule().updateNestedAC(
											EditPopupMenu.this.ruleEditor.getNestedAC());
									EditPopupMenu.this.ruleEditor.updateGraphics();
								}
							}
						}
					}
				}
			}
		});

		this.mi = add(new JMenuItem("Map"));
		this.miMap = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null 
						|| EditPopupMenu.this.gp.getGraph() == null
						|| EditPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;

				EditPopupMenu.this.mapping = true;
				setLastEditModeBeforMapping(EditPopupMenu.this.gp);

				if (EditPopupMenu.this.editor != null) {
					EditPopupMenu.this.editor.getGraphEditor().setEditMode(EditorConstants.MAP);
					EditPopupMenu.this.editor.getRuleEditor().setEditMode(EditorConstants.MAP);
					EditPopupMenu.this.editor.getRuleEditor().setObjMapping(true);
					EditPopupMenu.this.editor
							.setMsg("Click on a target object you want to map or click on the background to break the mapping.");
				} else if (EditPopupMenu.this.ruleEditor != null) {
					EditPopupMenu.this.ruleEditor.setEditMode(EditorConstants.MAP);
					EditPopupMenu.this.ruleEditor.setObjMapping(true);
					EditPopupMenu.this.ruleEditor
							.setMsg("Click on a target object you want to map or click on the background to break the mapping");
				}
				EditPopupMenu.this.ego.setWeakselected(true);
				EditPopupMenu.this.gp.updateGraphics();
			}
		});

		this.mi = add(new JMenuItem("Unmap"));
		this.miUnmap = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null 
						|| EditPopupMenu.this.gp.getGraph() == null
						|| EditPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;

				EditPopupMenu.this.mapping = false;
				unmapGraphObject(false);
			}
		});

		addSeparator();

		this.mi = add(new JMenuItem("Multiplicity"));
		this.miMultiplicity = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMultiplicityOfType();
				if (EditPopupMenu.this.graphEditor != null) {
					EditPopupMenu.this.graphEditor.getGraph().update();
					EditPopupMenu.this.graphEditor.getGraphPanel().updateGraphics();
				}
			}
		});

		this.mi = add(new JMenuItem("Set Parent"));
		this.miSetParent = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null 
						|| EditPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;
				
				if (EditPopupMenu.this.ego instanceof EdNode) {					
					Node bNode = EditPopupMenu.this.ego.getNode().getBasisNode();
					Graph bGraph = bNode.getContext();
					if (bGraph.isTypeGraph()) {
						if (EditPopupMenu.this.editor != null) {
							EditPopupMenu.this.editor.getGraphEditor().setEditMode(
									EditorConstants.SET_PARENT);
							EditPopupMenu.this.ego.setWeakselected(true);
							EditPopupMenu.this.gp.getCanvas().getGraph().drawNode(
									EditPopupMenu.this.gp.getCanvas().getGraphics(), (EdNode)EditPopupMenu.this.ego);
							EditPopupMenu.this.editor.setMsg("Click on a node to add inheritance relation.");
						}
					}
				}
			}
		});

		this.mi = add(new JMenuItem("Unset Parent"));
		this.miUnsetParent = this.mi;
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null 
						|| EditPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW
						|| !EditPopupMenu.this.gp.getGraph().isEditable())
					return;
				
				if (EditPopupMenu.this.ego instanceof EdNode) {
					Node bNode = EditPopupMenu.this.ego.getNode().getBasisNode();
					Graph bGraph = bNode.getContext();
					if (bGraph.isTypeGraph()) {
						if (EditPopupMenu.this.editor != null) {
							if (EditPopupMenu.this.graphEditor.getGraph().getBasisGraph()
									.getTypeSet().getLevelOfTypeGraphCheck() != TypeSet.DISABLED) {
								if (EditPopupMenu.this.graphEditor.getGraph().getTypeSet()
										.isTypeUsed(EditPopupMenu.this.ego.getType())) {
									JOptionPane
											.showMessageDialog(
													EditPopupMenu.this.applFrame,
													"Cannot unset inheritance relation."
															+ "\nPlease disable the type graph first.",
													"Unset Parent",
													JOptionPane.ERROR_MESSAGE);
									return;
								}
							}

							if (bNode.getType().getParents().size() == 1) {
								EditPopupMenu.this.graphEditor.getGraph().addChangedParentToUndo(
										EditPopupMenu.this.ego);
								EditPopupMenu.this.gp.getCanvas().updateUndoButton();
								
								EditPopupMenu.this.gp.getCanvas().performDeleteInheritanceRel((EdNode) EditPopupMenu.this.ego);
		
								EditPopupMenu.this.graphEditor.getGraph().undoManagerEndEdit();

								EditPopupMenu.this.graphEditor.getGraph().updateGraph();
								EditPopupMenu.this.graphEditor.getGraphPanel().updateGraphics();
							} else {
								EditPopupMenu.this.editor.getGraphEditor().setEditMode(
										EditorConstants.UNSET_PARENT);
								EditPopupMenu.this.editor.setMsg("Click on a parent node to remove inheritance relation.");
							}
						}
					}
				}
			}
		});

		this.miAbstract = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("Abstract"));
		this.miAbstract.setSelected(false);
		this.miAbstract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null 
						|| EditPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;
				
				if (EditPopupMenu.this.ego instanceof EdNode) {
					EditPopupMenu.this.gp.getGraph().addChangedTypeToUndo(EditPopupMenu.this.ego);
					EditPopupMenu.this.gp.getCanvas().updateUndoButton();

					if (((JCheckBoxMenuItem) e.getSource()).isSelected())
						EditPopupMenu.this.ego.getBasisObject().getType().setAbstract(true);
					else
						EditPopupMenu.this.ego.getBasisObject().getType().setAbstract(false);

					if (EditPopupMenu.this.editor != null) {
						EditPopupMenu.this.editor.updateGraphics();						
					}
					else 
						EditPopupMenu.this.gp.updateGraphics();
					
					EditPopupMenu.this.gp.getGraph().undoManagerEndEdit();
				}
			}
		});

		addSeparator();

		this.mi = add(new JMenuItem("Textual Comments"));
		this.miComment = this.mi;
		this.mi.setActionCommand("commentType");
		this.mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (EditPopupMenu.this.gp == null || EditPopupMenu.this.gp.getEditMode() == EditorConstants.VIEW)
					return;

				if (EditPopupMenu.this.gp.getGraph().isTypeGraph()) {
					String oldcomment = EditPopupMenu.this.ego.getType()
							.getBasisType().getTextualComment();
					EditPopupMenu.this.gp.getGraph().addChangedTypeToUndo(EditPopupMenu.this.ego);

					Point p = EditPopupMenu.this.applFrame.getLocation();
					GraGraTextualComment comments = new GraGraTextualComment(
							EditPopupMenu.this.applFrame, 
							p.x + EditPopupMenu.this.location.x, 
							p.y + EditPopupMenu.this.location.y, 
							EditPopupMenu.this.ego.getType().getBasisType());
					comments.setModal(true);
					comments.setVisible(true);
					String newcomment = EditPopupMenu.this.ego.getType()
							.getBasisType().getTextualComment();

					if (oldcomment.equals(newcomment))
						EditPopupMenu.this.gp.getGraph().undoManagerLastEditDie();
					else
						EditPopupMenu.this.gp.getGraph().undoManagerEndEdit();
					EditPopupMenu.this.gp.getCanvas().updateUndoButton();
				}
			}
		});

//		addSeparator();
//		statistic = createGraphStatisticMenu();
//		add(statistic);
		
		pack();
		setBorderPainted(true);
		setDefaultLightWeightPopupEnabled(false);
	}

	public void showMe(Component comp, int x, int y) {
		if (this.editor != null && this.editor.getRuleEditor().getRule() == null)
			setUnmapEnabled(false);
		else if (this.gp.getGraph() != null && this.gp.getGraph().isTypeGraph())
			setUnmapEnabled(false);
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
		if ((this.gp == null) || (this.gp.getEditMode() == EditorConstants.VIEW)
				|| (this.gp.getGraph() == null) || (this.ego == null))
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
				this.editor.getRuleEditor().getLeftCondPanel().updateGraphics();
				this.editor.getGraphEditor().getGraph().update();
				this.editor.getGraphEditor().getGraphPanel().updateGraphics();
			} else if (this.ruleEditor != null) {
				this.ruleEditor.getRule().update();
				this.ruleEditor.getLeftPanel().updateGraphics();
				this.ruleEditor.getRightPanel().updateGraphics();
				this.ruleEditor.getLeftCondPanel().updateGraphics();
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

	void showMessageDialog(List<String> failed, boolean node) {
		if (failed == null)
			return;
		if (!failed.isEmpty()) {
			String str = "Cannot delete objects of this type from  \n";
			for (int i = 0; i < failed.size(); i++) {
				String s = "\t" + failed.get(i) + "\n";
				str = str + s;
			}
			JOptionPane.showMessageDialog(this.applFrame, str);
		}
	}

	private JMenuItem createDeleteItem() {
		JMenuItem m = add(new JMenuItem("Delete"));
		// m.setMnemonic('D');
		m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;

				EditPopupMenu.this.mapping = false;
				if (!EditPopupMenu.this.ego.getMorphismMark().equals("")) {
					EditPopupMenu.this.gp.setMappedObjDeleted(true);
					unmapGraphObject(true);
				}
				EditPopupMenu.this.gp.deleteObj(EditPopupMenu.this.ego);				
				doUpdateAfterDelete();
				EditPopupMenu.this.ego = null;
			}
		});
		return m;
	}

	private JMenu createDeleteMenu() {
		JMenu m = new JMenu("Delete Objects of Type");
		JMenuItem jmi = m.add(new JMenuItem("Delete All Objects"));
		jmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("Delete All Objects");
				if (!canDo())
					return;
				EditPopupMenu.this.mapping = false;
				int ok = 0;
//				String failStr = EditPopupMenu.this.gp.getGraph().getGraGra().kernelRuleContainsObjsOfType(EditPopupMenu.this.ego);
//				if (failStr != null) {
//					String str = "The kernel rule:  "+failStr+"  \n"
//						+"contains objects of type :  <"
//						+EditPopupMenu.this.ego.getTypeName()
//						+">  to delete.\n"
//						+"Currently, AGG does not support Undo/Redo in this case.\n\n"
//						+"Do you want to delete objects of this type anyway?";
//					ok = JOptionPane.showConfirmDialog(EditPopupMenu.this.applFrame, str, 
//							"Delete Objects of Type", JOptionPane.WARNING_MESSAGE);
//				}
//				boolean addToUndo = (failStr == null);
				
				boolean addToUndo = true;
				if (ok == 0) {
					List<String> failed = EditPopupMenu.this.gp.getGraph().getGraGra()
									.deleteGraphObjectsOfType(EditPopupMenu.this.ego, false, addToUndo);
					
					showMessageDialog(failed, EditPopupMenu.this.ego.isNode());
		
					doUpdateAfterDelete();
					EditPopupMenu.this.gp.getCanvas().updateUndoButton();
					EditPopupMenu.this.gp.getGraph().getGraGra().update();
					if (EditPopupMenu.this.editor != null)
						EditPopupMenu.this.editor.getRuleEditor().updateGraphics();
					
//					System.out.println("undo stored::  "+EditPopupMenu.storeVec.size());
				}
			}
		});
		jmi = m.add(new JMenuItem("Delete Objects of Host Graph"));
		jmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!canDo())
					return;
				EditPopupMenu.this.mapping = false;

				if (!EditPopupMenu.this.gp.getGraph().getGraGra()
							.deleteGraphObjectsOfTypeFromHostGraph(
									EditPopupMenu.this.ego, true)) 
					JOptionPane.showMessageDialog(EditPopupMenu.this.applFrame,
										"Cannot delete objects of this type from host graph.");
	
				doUpdateAfterDelete();
				EditPopupMenu.this.gp.getCanvas().updateUndoButton();
				EditPopupMenu.this.gp.getGraph().getGraGra().getGraph().update();
			}
		});
		
		/*
		 * jmi = m.add(new JMenuItem("Delete Objects of Rules"));
		 * jmi.addActionListener(new ActionListener() {public void
		 * actionPerformed(ActionEvent e) { //System.out.println("Delete Objects
		 * Of Rules"); if(!canDo()) return; mapping = false;
		 * 
		 * Vector failed =
		 * gp.getGraph().getGraGra().getBasisGraGra().destroyGraphObjectsOfTypeFromRules(ego.getType().getBasisType());
		 * showMessageDialog(failed);
		 * 
		 * doAfterDelete(); gp.getGraph().getGraGra().updateRules(); if (editor !=
		 * null) editor.getRuleEditor().updateGraphics(); }}); jmi = m.add(new
		 * JMenuItem("Delete Objects of Graph Constraints"));
		 * jmi.addActionListener(new ActionListener() {public void
		 * actionPerformed(ActionEvent e) { //System.out.println("Delete Objects
		 * Of Graph Constraints"); if(!canDo()) return; mapping = false;
		 * 
		 * Vector failed =
		 * gp.getGraph().getGraGra().getBasisGraGra().destroyGraphObjectsOfTypeFromGraphConstraints(ego.getType().getBasisType());
		 * showMessageDialog(failed);
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

	public void setGraphLayouter(EvolutionaryGraphLayout l) {
		this.layouter = l;
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
		
		if (this.gp.getGraph() != null) {
			if (this.gp.getCanvas().getPickedObject() != null
					&& this.gp.getCanvas().getPickedObject().isWeakselected()) {
				this.gp.getCanvas().getPickedObject().setWeakselected(false);
				this.gp.updateGraphics();
			}
			if (!this.gp.getCanvas().isLeftPressed()) {
				this.ego = this.gp.getGraph().getPicked(x, y);
				if (this.ego == null) {
					this.ego = this.gp.getGraph().getPickedTextOfArc(x, y,
							this.gp.getCanvas().getGraphics().getFontMetrics());
				}
				
				if (this.ego != null && this.ego.isVisible()) {
	//				if (gp.getGraph().isTargetObjOfGraphEmbedding(ego)) {
	//					return false;
	//				}
									
					this.location = new Point(x, y);
					if (this.ego.isArc()) {
						Arc bArc = this.ego.getArc().getBasisArc();
						if (bArc.isInheritance()) {
							return false;
						}
					}
					
					this.layout.setEnabled(false);
					this.miComment.setEnabled(false);
	
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
	
					if (!this.ego.getContext().isTypeGraph()) {
						this.miVisibility.setEnabled(false);
	//					if (this.ego instanceof EdNode)
							this.miObjName.setEnabled(true);
	//					else
	//						this.miObjName.setEnabled(false);
					} else {
						this.miVisibility.setEnabled(true);
						this.miObjName.setEnabled(false);
						if (this.ego.isNode()) {
							if (this.ego.getType().getBasisType().isObjectOfTypeGraphNodeVisible()) {
								this.miVisibility.setText("Hide Objects of Type");
							} else {
								this.miVisibility.setText("Show Objects of Type");
							}
						} else {
							if (this.ego.getType().getBasisType().isObjectOfTypeGraphArcVisible(
									((EdArc)this.ego).getSource().getType().getBasisType(),
									((EdArc)this.ego).getTarget().getType().getBasisType())) {
								this.miVisibility.setText("Hide Objects of Type");
							} else {
								this.miVisibility.setText("Show Objects of Type");
							}
						}
					}
					
					if (this.ego.isNode()) {
						this.miStraighten.setEnabled(false);
						if (this.ego.isElementOfTypeGraph()) {
							this.miComment.setEnabled(true);
							this.useDeleteMenu = true;
							this.addIdentic.setEnabled(false);
							this.miUnmap.setEnabled(false);
							this.miMultiplicity.setEnabled(true);
							this.miAbstract.setEnabled(true);
							if (this.ego.getBasisObject().getType().isAbstract())
								this.miAbstract.setSelected(true);
							else
								this.miAbstract.setSelected(false);
							this.miSetParent.setEnabled(true);
							if (!this.ego.getNode().getBasisNode().getType()
									.getParents().isEmpty())
								this.miUnsetParent.setEnabled(true);
							else
								this.miUnsetParent.setEnabled(false);
	
							this.layout.setEnabled(true);
							if (this.layouter != null) {
								if (this.layouter.getLayoutPatternForType(this.ego
										.getBasisObject().getType(), "Freezing") != null) {
									this.miFrozen.setSelected(true);
								} else {
									this.miFrozen.setSelected(false);
								}
							}
						} else {
							if (this.graphEditor != null
									&& this.graphEditor.getGraphPanel() == this.gp)
								this.layout.setEnabled(true);
	
							this.miFrozen.setSelected(((EdNode) this.ego).getLNode()
									.isFrozen());
							this.useDeleteMenu = false;
							this.addIdentic.setEnabled(false);
							this.miMultiplicity.setEnabled(false);
							this.miAbstract.setEnabled(false);
							this.miAbstract.setSelected(false);
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
						this.miAbstract.setEnabled(false);
						this.miAbstract.setSelected(false);
						if (this.ego.isElementOfTypeGraph()) {
							this.miComment.setEnabled(true);
							this.useDeleteMenu = true;
							this.addIdentic.setEnabled(false);
							this.miUnmap.setEnabled(false);
							this.miMultiplicity.setEnabled(true);
						} else {
							this.useDeleteMenu = false;
							this.addIdentic.setEnabled(false);
							this.miMultiplicity.setEnabled(false);
						}
						if (this.ruleEditor != null 
								&& this.gp == this.ruleEditor.getLeftPanel())
							this.addIdentic.setEnabled(true);
					}
					if (!this.ego.isSelected()) {
						this.xPos = x;
						this.yPos = y;
						requestFocusInWindow();
						return true;
					} 
					return false;
				} 
				return false;
			}
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
				|| gp.getEditMode() == EditorConstants.SET_PARENT
				|| gp.getEditMode() == EditorConstants.UNSET_PARENT
				|| gp.getEditMode() == EditorConstants.INTERACT_RULE
				|| gp.getEditMode() == EditorConstants.INTERACT_NAC
				|| gp.getEditMode() == EditorConstants.INTERACT_PAC
				|| gp.getEditMode() == EditorConstants.INTERACT_AC
				|| gp.getEditMode() == EditorConstants.INTERACT_MATCH) {

			gp.setLastEditMode(gp.getEditMode());
			gp.setLastEditCursor(gp.getEditCursor());
		}
	}

	/* Draws graphic of the graphobject go in the panel p */
	/*
	private void drawGraphic(EdGraphObject go, GraphPanel p) {
		if (go.isNode())
			p.getGraph().drawNode(p.getCanvas().getGraphics(), (EdNode) go);
		else
			p.getGraph().drawArc(p.getCanvas().getGraphics(), (EdArc) go);
	}
*/
	
	void setMultiplicityOfType() {
		if (this.ego != null) {
			this.gp.getGraph().addChangedMultiplicityToUndo(this.ego);

			if (!this.ego.isNode()) {
				this.multiplicity = new TypeCardinalityDialog(null, this.ego.getType()
						.getBasisType(), ((EdArc) this.ego).getSource().getType()
						.getBasisType(), ((EdArc) this.ego).getTarget().getType()
						.getBasisType());
			}
			else {
				this.multiplicity = new TypeCardinalityDialog(null, this.ego.getType()
						.getBasisType());
			}
			this.multiplicity.showGUI();
			
			if (this.editor != null) {
				if (!this.multiplicity.isChanged()) {
					this.gp.getGraph().undoManagerLastEditDie();
				}
				else if (this.ego.isNode()) {
					String errors = this.gp.getGraph().getGraGra()
									.checkNodeTypeMultiplicity((EdNode) this.ego);					
					if (errors != null) {
						errors = errors.replaceAll(",", ",\n");
						
						JOptionPane.showMessageDialog(
								null,
								"<html><body>"
								+"Please check the graph(s): \n"
								+errors+".\n"
								+"\nMultiplicity constraint of the node type:\n"
								+"\""+this.ego.getType().getBasisType().getName()+"\"  \n"
								+"is not satisfied.",
								"Checking Node Type Multiplicity",
								JOptionPane.WARNING_MESSAGE
								);
					}
				} else {
					String errors = this.gp.getGraph().getGraGra()
									.checkEdgeTypeMultiplicity((EdArc) this.ego);	
					if (errors != null) {
						errors = errors.replaceAll(",", ",\n");

						JOptionPane.showMessageDialog(
								null,
								"<html><body>"
								+"Please check the graph(s): \n"
								+errors+".\n"
								+"\nMultiplicity constraint of the edge type:\n"
								+"\""+this.ego.getType().getBasisType().getName()+"\"  \n"
								+"is not satisfied.",
								"Checking Edge Type Multiplicity",
								JOptionPane.WARNING_MESSAGE
								);
					}
				}
			}
			
			this.gp.getCanvas().updateUndoButton();
			this.gp.getGraph().undoManagerEndEdit();
		}
	}

	
	protected void unmapGraphObject(boolean wantDeleteGraphObject) {
		boolean unmapdone = false;
		if (this.editor != null 
				&& this.editor.getRuleEditor().getRule() != null) {
			EdRule rule = this.editor.getRuleEditor().getRule();
			boolean isLeftRuleObj = this.ego.getContext() == rule.getLeft();
			
			if (this.editor.getActivePanel() == this.editor.getRuleEditor().getLeftPanel()) {
				unmapdone = this.leftPanelUnmap(this.editor.getRuleEditor(), rule, wantDeleteGraphObject);
				
				if (unmapdone && isLeftRuleObj && rule.getMatch() != null) {
					this.editor.getGraphEditor().updateGraphics();				
				}
			} 
			else if (this.editor.getActivePanel() == this.editor.getRuleEditor().getRightPanel()) {
				unmapdone = this.rightPanelUnmap(this.editor.getRuleEditor(), rule, wantDeleteGraphObject);
			} 
			else if (this.editor.getActivePanel() == this.editor.getRuleEditor().getLeftCondPanel()) {
				unmapdone = this.leftCondPanelUnmap(this.editor.getRuleEditor(), rule);
			} 
			else if (this.editor.getActivePanel() == this.editor.getGraphEditor().getGraphPanel()
							&& rule.getMatch() != null) {
				if (this.editor.getRuleEditor().removeMatchMapping(this.ego, false))
					unmapdone = true;
				if (unmapdone) {
					this.editor.getRuleEditor().getLeftPanel().updateGraphics();
					this.editor.getGraphEditor().getGraphPanel().updateGraphics();
				}
			}
		} 
		else if (this.ruleEditor != null && this.ruleEditor.getRule() != null) {
			EdRule rule = this.ruleEditor.getRule();
			if (this.gp == this.ruleEditor.getLeftPanel()) {
				unmapdone = this.leftPanelUnmap(this.ruleEditor, rule, wantDeleteGraphObject);
				if (unmapdone) {					
					if (this.graphEditor != null)
						this.graphEditor.getGraphPanel().updateGraphics();
				}						
			} 
			else if (this.gp == this.ruleEditor.getRightPanel()) {
				unmapdone = this.rightPanelUnmap(this.editor.getRuleEditor(), rule, wantDeleteGraphObject);
			} 
			else if (this.gp == this.ruleEditor.getLeftCondPanel()) {
				unmapdone = this.leftCondPanelUnmap(this.editor.getRuleEditor(), rule);
			}
		} else if (this.graphEditor != null 
				&& this.gp == this.graphEditor.getGraphPanel()) {
			if (this.ruleEditor != null) {
				this.ruleEditor.getRule().removeMapping(this.ego, this.ruleEditor.getRule().getMatch());
				if (this.ruleEditor.getLeftPanel().getGraph() == this.ruleEditor.getRule().getLeft())
					this.ruleEditor.getLeftPanel().updateGraphics();
				this.graphEditor.getGraphPanel().updateGraphics();
			}
		}
	}

	private boolean leftPanelUnmap(final RuleEditor rEditor, final EdRule rule, boolean wantDelete) {
		boolean unmapdone = false;
		boolean isLeftRuleObj = this.ego.getContext() == rule.getLeft();
		if (wantDelete) {
			if (isLeftRuleObj) {
				if (!(rule instanceof EdAtomic)) {
					if (rEditor.removeNacMapping(this.ego)
							|| rEditor.removePacMapping(this.ego)
							|| rEditor.removeNestedACMapping(this.ego))
						unmapdone = true;
				}
				if (rEditor.removeRuleMapping(this.ego, true))
					unmapdone = true;
			} else if (rEditor.getNestedAC() != null) {
				if (rEditor.getNestedAC().getParent()  == null) {
					if (rEditor.removeNestedACMapping(this.ego, true))
						unmapdone = true;
				} else {
					if (rEditor.getNestedAC().getParent()
							.removeNestedACMapping(this.ego, rEditor.getNestedAC()));
						unmapdone = true;
					if (rEditor.removeNestedACMapping(this.ego, 
							rEditor.getNestedAC().getParent(), 
							rEditor.getNestedAC().getParent().getNestedACs()))
						unmapdone = true;
				}
			}
			if (unmapdone) {
				rule.update();
				if (rEditor.getNestedAC() != null) {
					rEditor.updateNestedAC(rEditor.getNestedAC());
				}
				rEditor.updateGraphics();
			}
		} else {
			if (isLeftRuleObj) {
				if (!(rule instanceof EdAtomic)) {
					if (rEditor.removeNacMapping(this.ego, true)
							|| rEditor.removePacMapping(this.ego, true)
							|| rEditor.removeNestedACMapping(this.ego, true)) {
						unmapdone = true;
					}
				}
				if (rEditor.removeRuleMapping(this.ego, true))
					unmapdone = true;
			} else if (rEditor.getNestedAC() != null) {
				if (rEditor.getNestedAC().getParent()  == null) {
					if (rEditor.removeNestedACMapping(this.ego, true))
						unmapdone = true;
				} else {
					if (rEditor.getNestedAC().getParent()
							.removeNestedACMapping(this.ego, rEditor.getNestedAC()));
						unmapdone = true;
					if (rEditor.removeNestedACMapping(this.ego, true))
						unmapdone = true;
				}
			}
			if (unmapdone) {
				if (rEditor.getNestedAC() == null) {
					rule.update();
					rEditor.updateGraphics();
				}
				else {
					rEditor.updateNestedAC(rEditor.getNestedAC());
					rEditor.updateGraphics();
				}
			}
		} 

		if (rule.getMatch() != null && isLeftRuleObj) {
			if (rEditor.removeMatchMapping(this.ego, true)) {
				unmapdone = true;
			}
			if (wantDelete) {
				rule.getMatch().getCompletionStrategy().removeFromObjectVarMap(this.ego.getBasisObject());
			}
		}

		return unmapdone;
	}
	
	private boolean rightPanelUnmap(final RuleEditor rEditor, final EdRule rule, boolean wantDelete) {
		boolean unmapdone = false;
		boolean isLeftRuleObj = this.ego.getContext() == rule.getLeft();
		if (!wantDelete) {
			if (isLeftRuleObj) {
				if (!(rule instanceof EdAtomic)) {
					if (rEditor.removeNacMapping(this.ego))
						unmapdone = true;
					if (rEditor.removePacMapping(this.ego))
						unmapdone = true;
					if (rEditor.removeNestedACMapping(this.ego))
						unmapdone = true;
				}
				if (rEditor.removeRuleMapping(this.ego, true))
					unmapdone = true;
			} else if (this.ego.getContext() == rule.getRight()) {
				if (rEditor.removeRuleMapping(this.ego, false))
					unmapdone = true;
			}
		}
		if (unmapdone) {
			if (rEditor.getNestedAC() == null) {
				rule.update();
			}
			else {
				rule.updateRule();
				rEditor.updateNestedAC(rEditor.getNestedAC());
			}
			rEditor.updateGraphics();
		}
		return unmapdone;
	}
	
	private boolean leftCondPanelUnmap(final RuleEditor rEditor, final EdRule rule) {
		boolean unmapdone = false;
		if (rEditor.removeNacMapping(this.ego, false))
				unmapdone = true;
		else if (rEditor.removePacMapping(this.ego, false))
				unmapdone = true;
		else if (rEditor.removeNestedACMapping(this.ego, false))
				unmapdone = true;
		
		if (unmapdone) {
			if (rEditor.getNestedAC() == null) {
				rule.update();
			}
			else {
				rule.updateRule();
				rEditor.updateNestedAC(rEditor.getNestedAC());
			}
			rEditor.getLeftPanel().updateGraphics();
			rEditor.getLeftCondPanel().updateGraphics();
		}
		return unmapdone;
	}
	
	/*
	private JMenu createGraphStatisticMenu() {
		JMenu statistMenu = new JMenu("Graph Statistic");
		
		mi = statistMenu.add(new JMenuItem("Nodes Of Type"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "Graph: "+ego.getContext().getName()+" contains ";
				int nb = ego.getContext().getNodes(ego.getType()).size();
				String tname = ego.getTypeName();
				System.out.println(text+nb+" nodes of type: "+tname);
			}
		});
		
		mi = statistMenu.add(new JMenuItem("Outgoing Edges"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "Node ( HC:"+ego.hashCode()+" ) ";
				int nb = ((EdNode)ego).getOutArcsCount();
				String tname = ego.getTypeName();
				text = text + "of type: "+tname+" has ";
				System.out.println(text+nb+" outgoing edges");
			}
		});
		
		mi = statistMenu.add(new JMenuItem("Incoming Edges"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "Node ( HC:"+ego.hashCode()+" ) ";
				int nb = ((EdNode)ego).getInArcsCount();
				String tname = ego.getTypeName();
				text = text + "of type: "+tname+" has ";
				System.out.println(text+nb+" incoming edges");
			}
		});
		
		return statistMenu;
	}
	*/
	
	public void activateObjectNameMenuItem(boolean b) {
		if (b && this.getComponent(3) != this.miObjName) {
			this.insert(this.miObjName, 3);
		}
		else {
			this.remove(this.miObjName);
		}
	}
	
	protected void setObjectName(final EdGraphObject go) {	
		go.setWeakselected(true);
		this.gp.updateGraphics();
		String objname = JOptionPane.showInputDialog(this.applFrame, 
				" Please set the object name: ", go.getBasisObject().getObjectName());
		
		if (objname != null) {
			go.getBasisObject().setObjectName(objname);
//			this.gp.updateGraphics();	
		}
		go.setWeakselected(false);
		this.gp.updateGraphics();
	}
	
	
	JFrame applFrame;

	private JMenuItem mi;

	final private JMenuItem //miOperations, 
			miDelete, miMap, miUnmap, miStraighten, miVisibility,
			miAddIdenticToRule, 
			miAddIdenticToNAC, miAddIdenticToPAC, miAddIdenticToGAC,
			miMultiplicity, miSetParent, miUnsetParent, miComment, miObjName;

	private JMenu deleteMenu, addIdentic,
//			statistic,
			layout;

	private JCheckBoxMenuItem miAbstract, miFrozen;

	boolean mapping = false;

	GraGraEditor editor;

	RuleEditor ruleEditor;

	GraphEditor graphEditor;

	GraphPanel gp;

	EdGraphObject ego;

	int xPos, yPos;

	private TypeCardinalityDialog multiplicity;

	private boolean useDeleteMenu = false;

	EvolutionaryGraphLayout layouter;

	Point location;
}

// $Log: EditPopupMenu.java,v $
// Revision 1.26  2010/10/16 22:44:43  olga
// improved undo for RuleScheme graph objects
//
// Revision 1.25  2010/09/30 22:20:42  olga
// improved
//
// Revision 1.24  2010/09/30 14:35:18  olga
// improved
//
// Revision 1.23  2010/09/30 14:10:04  olga
// delete objects of especial type -  improved
//
// Revision 1.22  2010/09/27 23:12:03  olga
// tuning
//
// Revision 1.21  2010/09/27 22:45:25  olga
// improved
//
// Revision 1.20  2010/09/27 16:20:54  olga
// improved
//
// Revision 1.19  2010/08/25 08:23:30  olga
// tuning
//
// Revision 1.18  2010/08/18 16:57:41  olga
// extended
//
// Revision 1.17  2010/06/09 11:04:43  olga
// extended due to new NestedApplCond
//
// Revision 1.16  2010/04/14 13:23:28  olga
// select and move in non-editable graphs where create and delete objects not allowed
//
// Revision 1.15  2010/03/15 10:39:44  olga
// rename refactoring
//
// Revision 1.14  2010/03/10 14:46:35  olga
// make identical rule - bug fixed
//
// Revision 1.13  2010/03/08 15:43:28  olga
// code optimizing
//
// Revision 1.12  2010/02/22 15:03:02  olga
// code optimizing
//
// Revision 1.11  2009/11/09 10:41:57  olga
// tuning
//
// Revision 1.10  2009/07/16 17:21:03  olga
// GUI bugs fixed
//
// Revision 1.9  2009/07/02 15:42:26  olga
// new menu item: Object Name
//
// Revision 1.8  2009/06/02 12:39:27  olga
// Min Multiplicity check - bug fixed
//
// Revision 1.7  2009/05/28 13:18:28  olga
// Amalgamated graph transformation - development stage
//
// Revision 1.6  2009/05/12 10:36:55  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.5  2009/04/14 09:18:36  olga
// Edge Type Multiplicity check - bug fixed
//
// Revision 1.4  2009/04/02 14:39:33  olga
// code tuning
//
// Revision 1.3  2009/01/19 12:34:02  olga
// AGG tuning
//
// Revision 1.2  2008/11/06 08:45:37  olga
// Graph layout is extended by Zest Graph Layout ( eclipse zest plugin)
//
// Revision 1.1  2008/10/29 09:04:13  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.34  2008/10/01 10:24:55  olga
// AGG GUI:  mouse usability  extended - simulation of the middle button by left and right button pressed simultaneously
//
// Revision 1.33  2008/09/04 07:49:23  olga
// GUI extension: hide nodes, edges
//
// Revision 1.32  2008/07/21 10:03:27  olga
// Code tuning
//
// Revision 1.31  2008/07/14 07:35:46  olga
// Applicability of RS - new option added, more tuning
// Node animation - new animation parameter added,
// Undo edit manager - possibility to disable it when graph transformation
// because it costs much more time and memory
//
// Revision 1.30  2008/07/02 17:14:35  olga
// Code tuning
//
// Revision 1.29  2008/04/07 09:36:51  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.28  2008/02/25 08:44:48  olga
// Extending of CPA: new class CriticalRulePairAtGraph to get critical
// matches of two rules at a concret graph.
//
// Revision 1.27  2007/12/17 08:33:30  olga
// Editing inheritance relations - bug fixed;
// CPA: dependency of rules - bug fixed
//
// Revision 1.26  2007/12/05 08:57:01  olga
// Delete a conclusion of an Atomic graph constraint : bug fixed
// Graph visualization update after the marking "Abstract" of a type node in the type graph : bug fixed
// CPA : some bug fixed; code tuning
//
// Revision 1.25  2007/12/03 08:35:11  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.24  2007/11/01 09:58:12  olga
// Code refactoring: generic types- done
//
// Revision 1.23  2007/09/24 09:42:34  olga
// AGG transformation engine tuning
//
// Revision 1.22  2007/09/10 13:05:20  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.21 2007/07/02 08:27:27 olga
// Help docu update,
// Source tuning
//
// Revision 1.20 2007/06/13 08:32:48 olga
// Update: V161
//
// Revision 1.19 2007/04/30 13:23:36 olga
// Update morphism mapping after adding an identic node/edge to RHS, NAC - bug
// fixed.
//
// Revision 1.18 2007/04/19 07:52:36 olga
// Tuning of: Undo/Redo, Graph layouter, loading grammars
//
// Revision 1.17 2007/04/11 10:03:31 olga
// Undo, Redo tuning,
// Simple Parser- bug fixed
//
// Revision 1.16 2007/03/28 14:06:33 olga
// Common undo manager now
//
// Revision 1.15 2007/03/28 10:00:37 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.14 2007/01/11 10:21:05 olga
// Optimized Version 1.5.1beta , free for tests
//
// Revision 1.13 2006/12/18 08:33:48 olga
// Code optimization
//
// Revision 1.12 2006/12/13 13:32:54 enrico
// reimplemented code
//
// Revision 1.11 2006/11/02 16:05:19 olga
// Errors fixed
//
// Revision 1.10 2006/11/01 11:17:29 olga
// Optimized agg sources of CSP algorithm, match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.9 2006/08/16 11:41:16 olga
// edit mode tuning
// graph layout by node type pattern FreezingAge extended
//
// Revision 1.8 2006/08/14 08:26:24 olga
// AGG help update
// Rule editor tuning
//
// Revision 1.7 2006/08/02 09:00:56 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.6 2006/04/03 08:57:50 olga
// New: Import Type Graph
// and some bugs fixed
//
// Revision 1.5 2006/03/06 09:15:36 olga
// Type sorting inconsistency of unnamed typs eliminated
//
// Revision 1.4 2006/03/01 09:55:47 olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.3 2005/11/07 09:38:07 olga
// Null pointer during retype attr. member fixed.
//
// Revision 1.2 2005/09/01 08:22:14 olga
// Adaptation inheritance version to AGG standard:
// - remove type graph nodes/arcs,
// - GUI conformance.
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.2.2.1 2005/07/04 11:41:37 enrico
// basic support for inheritance
//
// Revision 1.2 2005/06/20 13:37:03 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.9 2004/12/20 14:53:48 olga
// Changes because of matching optimisation.
//
// Revision 1.8 2004/03/01 15:47:55 olga
// Tests
//
// Revision 1.7 2003/12/18 16:26:41 olga
// GUI
//
// Revision 1.6 2003/03/05 18:24:17 komm
// sorted/optimized import statements
//
// Revision 1.5 2002/12/12 09:25:31 olga
// Graphtransformation-Rollback wenn TypeGraph Fehler vorkommen.
//
// Revision 1.4 2002/12/02 09:59:49 komm
// each source/target combination has now its own multiplicity constraints
//
// Revision 1.3 2002/11/28 14:05:18 olga
// Das ArcTypeMultiplicityGUI ist jetzt an dem Kanten-Popupmenu im TypeGraphen.
//
// Revision 1.2 2002/11/11 10:45:03 komm
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
// Revision 1.15 2001/03/08 11:00:01 olga
// Das ist Stand nach der AGG GUI Reimplementierung
// und Parser Anbindung.
//
// Revision 1.14 2000/12/21 09:48:58 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.13.8.2 2000/11/09 13:28:28 olga
// Umstellung von Graphtransformation in TransformInterpret, TransformDebug auf
// die Methoden aus agg.xt_basis.GraTra. TransformInterpret noch fehlerhaft.
//
// Revision 1.13.8.1 2000/11/06 09:32:46 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.13 1999/12/22 12:36:52 shultzke
// The user cannot edit the context of graphs. Only in rules it is possible.
//
