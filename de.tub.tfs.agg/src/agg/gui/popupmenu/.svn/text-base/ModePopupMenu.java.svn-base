package agg.gui.popupmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JComponent;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdType;
import agg.editor.impl.EditUndoManager;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraGraEditor;
import agg.gui.editor.GraphEditor;
import agg.gui.editor.GraphPanel;
import agg.gui.editor.RuleEditor;
import agg.gui.saveload.GraphicsExportJPEG;

/**
 * @author $Author: olga $
 * @version $ID:$
 */
@SuppressWarnings("serial")
public class ModePopupMenu extends JPopupMenu {

	public ModePopupMenu() {
		super("Mode");

//		JMenuItem miLabel = 
		add(new JMenuItem("            Edit Mode & Operations"));
		// miLabel.setEnabled(false);
		addSeparator();

		this.miUndoManager = add(new JMenuItem(
				"Disable Undo Manager"));		
		this.miUndoManager.setActionCommand("disableUndo");
		this.miUndoManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					if (ModePopupMenu.this.miUndoManager.getText().equals("Disable Undo Manager")) {
//						miUndoManager.setText("Enable Undo Manager");
						ModePopupMenu.this.editor.enableUndoManager(false);
					} else if (ModePopupMenu.this.miUndoManager.getText().equals("Enable Undo Manager")) {
//						miUndoManager.setText("Disable Undo Manager");
						ModePopupMenu.this.editor.enableUndoManager(true);
					}
				}
			}});
		addSeparator();
		
		this.miUndo = add(
		new JMenuItem("Undo Edit                                 Ctrl+Z"));
		this.miUndo.setMnemonic('Z');
		this.miUndo.setEnabled(false);
		this.miUndo.setActionCommand("undo");
		this.miUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					ModePopupMenu.this.gp.getGraph().setTransformChangeEnabled(false);
					if (ModePopupMenu.this.gp.getGraph().getUndoManager().canUndo()) {
						((EditUndoManager) ModePopupMenu.this.gp.getGraph().getUndoManager())
								.undo();
						if (((EditUndoManager) ModePopupMenu.this.gp.getGraph().getUndoManager())
								.canRedo())
							ModePopupMenu.this.miRedo.setEnabled(true);
						ModePopupMenu.this.miDiscardAllEdits.setEnabled(true);
						ModePopupMenu.this.gp.updateGraphics();
					} else {
						ModePopupMenu.this.miUndo.setEnabled(false);
						ModePopupMenu.this.miDiscardAllEdits.setEnabled(false);
					}
				}
			}
		});

		this.miRedo = add(
		new JMenuItem("Redo Edit                                 Ctrl+Y"));
		this.miRedo.setEnabled(false);
		this.miRedo.setActionCommand("redo");
		this.miRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					ModePopupMenu.this.gp.getGraph().setTransformChangeEnabled(false);
					ModePopupMenu.this.gp.getGraph().getUndoManager().redo();
					ModePopupMenu.this.miRedo.setEnabled(false);
				}
			}
		});

		this.miDiscardAllEdits = add(new JMenuItem(
				"Discard All Edits                                   "));
		this.miDiscardAllEdits.setEnabled(false);
		this.miDiscardAllEdits.setActionCommand("discardAllEdits");
		this.miDiscardAllEdits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					ModePopupMenu.this.miUndo.setEnabled(false);
					ModePopupMenu.this.miRedo.setEnabled(false);
					ModePopupMenu.this.miDiscardAllEdits.setEnabled(false);
					if (ModePopupMenu.this.editor != null)
						ModePopupMenu.this.editor.discardAllEdits();
					else
						((EditUndoManager) ModePopupMenu.this.gp.getGraph().getUndoManager())
								.discardAllEdits();
				}
			}
		});

		addSeparator();

		this.miDraw = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
		"Draw                                        Shift+D"));
		this.miDraw.setSelected(true);
		this.miDraw.setMnemonic('D');
		this.miDraw.setActionCommand("drawMode");
		this.miDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					((JCheckBoxMenuItem) e.getSource()).setSelected(true);

					selectEditModeMenuItem("Draw");
					selectMainEditModeMenuItem("Draw");

					// action
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.setEditMode(EditorConstants.DRAW);
						ModePopupMenu.this.editor.selectToolBarModeItem("Draw");
					} else if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor.setEditMode(EditorConstants.DRAW);
					else if (ModePopupMenu.this.graphEditor != null)
						ModePopupMenu.this.graphEditor.setEditMode(EditorConstants.DRAW);
				}
			}
		});

		JCheckBoxMenuItem miMagicArc = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
				"Magic Edge Draw Support "));
		miMagicArc.setSelected(true);
		miMagicArc.setActionCommand("magicEdge");
		miMagicArc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					if (ModePopupMenu.this.editor != null)
						ModePopupMenu.this.editor
								.enableMagicEdgeSupport(((JCheckBoxMenuItem) e
										.getSource()).isSelected());
				}
			}
		});

		this.miSelect = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
		"Select                                      Shift+S"));
		this.miSelect.setActionCommand("selectMode");
		this.miSelect.setMnemonic('S');
		this.miSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					((JCheckBoxMenuItem) e.getSource()).setSelected(true);

					selectEditModeMenuItem("Select");
					selectMainEditModeMenuItem("Select");

					// action
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.setEditMode(EditorConstants.SELECT);
						ModePopupMenu.this.editor.selectToolBarModeItem("Select");
					} else if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor.setEditMode(EditorConstants.SELECT);
					else if (ModePopupMenu.this.graphEditor != null)
						ModePopupMenu.this.graphEditor.setEditMode(EditorConstants.SELECT);
				}
			}
		});

		this.miMove = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
				"Move                                        Shift+M"));
		this.miMove.setActionCommand("moveMode");
		this.miMove.setMnemonic('M');
		this.miMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					((JCheckBoxMenuItem) e.getSource()).setSelected(true);

					selectEditModeMenuItem("Move");
					selectMainEditModeMenuItem("Move");

					// action
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.setEditMode(EditorConstants.MOVE);
						ModePopupMenu.this.editor.selectToolBarModeItem("Move");
					} else if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor.setEditMode(EditorConstants.MOVE);
					else if (ModePopupMenu.this.graphEditor != null)
						ModePopupMenu.this.graphEditor.setEditMode(EditorConstants.MOVE);
				}
			}
		});

		this.miSynchronMove = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
				"Synchron Move of Mapped Objects"));
		this.miSynchronMove.setActionCommand("synchronMoveMode");
		this.miSynchronMove.setMnemonic('y');
		this.miSynchronMove.setSelected(false); // true);
		this.miSynchronMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					// action
					if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor
								.enableSynchronMoveOfMappedObjects(((JCheckBoxMenuItem) e
										.getSource()).isSelected());
				}
			}
		});

		this.miAttrs = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
				"Attributes                                Shift+A"));
		this.miAttrs.setActionCommand("atributesMode");
		this.miAttrs.setMnemonic('b');
		this.miAttrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					((JCheckBoxMenuItem) e.getSource()).setSelected(true);

					selectEditModeMenuItem("Attributes");
					selectMainEditModeMenuItem("Attributes");

					// action
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.setEditMode(EditorConstants.ATTRIBUTES);
						ModePopupMenu.this.editor.selectToolBarModeItem("Attributes");
					} else if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor.setEditMode(EditorConstants.ATTRIBUTES);
					else if (ModePopupMenu.this.graphEditor != null)
						ModePopupMenu.this.graphEditor.setEditMode(EditorConstants.ATTRIBUTES);
				}
			}
		});

		this.miMap = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
				"Map                                           Ctrl+M"));
		this.miMap.setActionCommand("mapMode");
		this.miMap.setMnemonic('p');
		this.miMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					((JCheckBoxMenuItem) e.getSource()).setSelected(true);

					selectEditModeMenuItem("Map");
					selectMainEditModeMenuItem("Map");

					// action
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.setEditMode(EditorConstants.MAP);
						ModePopupMenu.this.editor.selectToolBarModeItem("Map");
					} else if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor.setEditMode(EditorConstants.MAP);
					else if (ModePopupMenu.this.graphEditor != null)
						ModePopupMenu.this.graphEditor.setEditMode(EditorConstants.MAP);
				}
			}
		});

		this.miUnmap = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem(
				"Unmap                                      Ctrl+U"));
		this.miUnmap.setActionCommand("unmapMode");
		this.miUnmap.setMnemonic('u');
		this.miUnmap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JMenuItem) {
					((JCheckBoxMenuItem) e.getSource()).setSelected(true);

					selectEditModeMenuItem("Unmap");
					selectMainEditModeMenuItem("Unmap");

					// action
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.setEditMode(EditorConstants.UNMAP);
						ModePopupMenu.this.editor.selectToolBarModeItem("Unmap");
					} else if (ModePopupMenu.this.ruleEditor != null)
						ModePopupMenu.this.ruleEditor.setEditMode(EditorConstants.UNMAP);
					else if (ModePopupMenu.this.graphEditor != null)
						ModePopupMenu.this.graphEditor.setEditMode(EditorConstants.UNMAP);
				}
			}
		});

		// miImage = new JCheckBoxMenuItem("Image_view");
		// miImage.setActionCommand("imageMode");
		// miImage.setMnemonic('I');
		// //add(miImage);
		// miImage.addActionListener(new ActionListener()
		// {public void actionPerformed(ActionEvent e) {
		// if (e.getSource() instanceof JMenuItem) {
		// // ((JCheckBoxMenuItem)e.getSource()).setSelected(true);
		//
		// selectEditModeMenuItem("Image_view");
		// selectMainEditModeMenuItem("Image_view");
		//
		// // action
		// if (editor != null)
		// editor.setNodeIconable(((JCheckBoxMenuItem)e.getSource()).isSelected());
		// }
		// }});

		addSeparator();

		JMenuItem miSelNodeType = add(new JMenuItem(
		"Select Nodes of Type            Ctrl+Alt+N"));
		miSelNodeType.setActionCommand("selectNodeType");
		miSelNodeType.setMnemonic('N');
		miSelNodeType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					ModePopupMenu.this.gp.selectNodesOfSelectedNodeType();
					if (editor != null && editor.getEditMode() != EditorConstants.SELECT) {
						editor.forwardModeCommand(EditorConstants.getModeOfID(EditorConstants.SELECT));
						editor.setEditMode(EditorConstants.SELECT);
					}	
				}
			}
		});

		JMenuItem miSelArcType = add(new JMenuItem(
		"Select Edges of Type            Ctrl+Alt+E"));
		miSelArcType.setActionCommand("selectArcType");
		miSelArcType.setMnemonic('e');
		miSelArcType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					ModePopupMenu.this.gp.selectArcsOfSelectedArcType();
					if (editor != null && editor.getEditMode() != EditorConstants.SELECT) {
						editor.forwardModeCommand(EditorConstants.getModeOfID(EditorConstants.SELECT));
						editor.setEditMode(EditorConstants.SELECT);
					}	
				}
			}
		});

		JMenuItem miSelectAll = add(new JMenuItem(
		"Select All                                  Ctrl+Alt+S"));
		miSelectAll.setActionCommand("selectAll");
		miSelectAll.setMnemonic('A');
		miSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					ModePopupMenu.this.gp.selectAll();
					if (editor != null && editor.getEditMode() != EditorConstants.SELECT) {
						editor.forwardModeCommand(EditorConstants.getModeOfID(EditorConstants.SELECT));
						editor.setEditMode(EditorConstants.SELECT);
					}
					// miUndo.setEnabled(true);
				}
			}
		});

		JMenuItem miDeselectAll = add(new JMenuItem(
		"Deselect All                             Ctrl+Alt+U"));
		miDeselectAll.setActionCommand("deselectAll");
		miDeselectAll.setMnemonic('l');
		miDeselectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					ModePopupMenu.this.gp.deselectAll();
					// miUndo.setEnabled(true);
				}
			}
		});

		addSeparator();
		
		this.miNodeVisibility = add(new JMenuItem("Hide Objects of Selected Node Type"));
		this.miNodeVisibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JMenuItem)e.getSource()).getText().indexOf("Hide") == 0) {
					ModePopupMenu.this.gp.getGraph().getGraGra().setVisibilityOfGraphObjectsOfType(
							ModePopupMenu.this.gp.getGraph().getGraGra().getTypeSet().getSelectedNodeType(),
							false);
				} else if (((JMenuItem)e.getSource()).getText().indexOf("Show") == 0) {
					ModePopupMenu.this.gp.getGraph().getGraGra().setVisibilityOfGraphObjectsOfType(
							ModePopupMenu.this.gp.getGraph().getGraGra().getTypeSet().getSelectedNodeType(),
							true);
				}
				
				ModePopupMenu.this.gp.updateGraphics();
			}
		});
		
		this.miArcVisibility = add(new JMenuItem("Hide Objects of Selected Edge Type"));
		this.miArcVisibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JMenuItem)e.getSource()).getText().indexOf("Hide") == 0) {
					ModePopupMenu.this.gp.getGraph().getGraGra().setVisibilityOfGraphObjectsOfType(
							ModePopupMenu.this.gp.getGraph().getGraGra().getTypeSet().getSelectedArcType(),
							false);
				} else if (((JMenuItem)e.getSource()).getText().indexOf("Show") == 0) {
					ModePopupMenu.this.gp.getGraph().getGraGra().setVisibilityOfGraphObjectsOfType(
							ModePopupMenu.this.gp.getGraph().getGraGra().getTypeSet().getSelectedArcType(),
							true);
				}
				
				ModePopupMenu.this.gp.updateGraphics();
			}
		});
		
		addSeparator();
		
		this.miStraightenArcs = add(new JMenuItem("Straighten All Edges"));
		this.miStraightenArcs.setActionCommand("straightenAllEdges");
		this.miStraightenArcs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					ModePopupMenu.this.gp.getGraph().straightAllArcs();
					if (!ModePopupMenu.this.gp.getGraph().isTypeGraph())
						ModePopupMenu.this.gp.getGraph().setStraightenArcs(true);
					
					ModePopupMenu.this.gp.updateGraphics();
				}
			}
		});
		
		addSeparator();
		
		this.miStaticNodePosition = (JCheckBoxMenuItem) add(new JCheckBoxMenuItem("Static Node Position"));
		this.miStaticNodePosition.setMnemonic('c');
		this.miStaticNodePosition.setActionCommand("staticNodePositionForGraphLayout");
		this.miStaticNodePosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					if (ModePopupMenu.this.editor != null) {
						if (((JCheckBoxMenuItem)e.getSource()).isSelected())
							ModePopupMenu.this.editor.enableStaticNodePositionForGraphLayouter(true);
						else
							ModePopupMenu.this.editor.enableStaticNodePositionForGraphLayouter(false);
					}
				}
			}
		});
		
		this.miLayoutGraph = add(new JMenuItem("Layout Graph "));
		this.miLayoutGraph.setMnemonic('G');
		this.miLayoutGraph.setActionCommand("defaultEGraphLayout");
		this.miLayoutGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					if (ModePopupMenu.this.editor != null) {
						ModePopupMenu.this.editor.doStandardELayoutProc(ModePopupMenu.this.gp.getGraph());
					}
					else {
						ModePopupMenu.this.gp.getGraph().forceDefaultEvolutionaryGraphLayout(10);
					}
				}
			}
		});

		JMenuItem miGraphExportJPG = add(new JMenuItem("Export JPEG"));
		miGraphExportJPG.setMnemonic('J');
		miGraphExportJPG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ModePopupMenu.this.gp != null && ModePopupMenu.this.gp.getGraph() != null) {
					if (ModePopupMenu.this.exportJPEG != null)
						ModePopupMenu.this.exportJPEG.save(ModePopupMenu.this.gp.getCanvas());
				}
			}
		});

		pack();
		setBorderPainted(true);
		// setDefaultLightWeightPopupEnabled(false);
	}

	public void setEditor(GraGraEditor ed) {
		this.editor = ed;
		this.ruleEditor = this.editor.getRuleEditor();
		if (this.ruleEditor != null)
			this.ruleEditor.enableSynchronMoveOfMappedObjects(this.miSynchronMove
					.isSelected());
		this.graphEditor = this.editor.getGraphEditor();
	}

	public void setEditor(RuleEditor re) {
		this.ruleEditor = re;
		if (this.ruleEditor != null)
			this.ruleEditor.enableSynchronMoveOfMappedObjects(this.miSynchronMove
					.isSelected());
	}

	public void setEditor(GraphEditor ge) {
		this.graphEditor = ge;
	}

	public void setExportJPEG(GraphicsExportJPEG jpg) {
		this.exportJPEG = jpg;
	}

	public boolean invoked(JComponent parent, GraphPanel p, int x, int y) {
		this.gp = p;
		if (this.gp != null && this.gp.getGraph() != null) {
			EdGraphObject ego = this.gp.getGraph().getPicked(x, y);
			if (ego == null ) {
				ego = this.gp.getGraph().getPickedTextOfArc(x, y,
						this.gp.getCanvas().getGraphics().getFontMetrics());
			}
			if (ego == null
					|| !ego.isVisible()) {
				
				this.miNodeVisibility.setEnabled(false);
				this.miArcVisibility.setEnabled(false);
				if (parent instanceof RuleEditor) {
					this.miSynchronMove.setEnabled(true);
					this.miStaticNodePosition.setEnabled(false);
					// miLayoutGraph.setEnabled(true);
				} else {
					this.miSynchronMove.setEnabled(false);
					this.miStaticNodePosition.setEnabled(true);
					if (this.gp.getGraph().getTypeSet().getTypeGraph() != null) {
						this.miNodeVisibility.setEnabled(true);
						this.miArcVisibility.setEnabled(true);
					}
					// this.miLayoutGraph.setEnabled(false);
				}

				resetVisibilityMenuItem();
				
				if (this.gp.getGraph().getUndoManager() != null) {
					if (((EditUndoManager)this.gp.getGraph().getUndoManager()).isEnabled()) {
						this.miUndoManager.setText("Disable Undo Manager");
					} else {
						this.miUndoManager.setText("Enable Undo Manager");
					}
					
					if (this.gp.getGraph().getUndoManager().canUndo()
							&& this.gp.getGraph().getUndoManager().canRedo()) {
						this.miUndo.setEnabled(true);
						this.miRedo.setEnabled(true);
						this.miDiscardAllEdits.setEnabled(true);
					} else if (this.gp.getGraph().getUndoManager().canRedo()) {
						this.miUndo.setEnabled(false);
						this.miRedo.setEnabled(true);
						this.miDiscardAllEdits.setEnabled(true);
					} else if (this.gp.getGraph().getUndoManager().canUndo()) {
						this.miUndo.setEnabled(true);
						this.miRedo.setEnabled(false);
						this.miDiscardAllEdits.setEnabled(true);
					} else {
						this.miUndo.setEnabled(false);
						this.miRedo.setEnabled(false);
						this.miDiscardAllEdits.setEnabled(false);
					}
				}

				requestFocusInWindow();
				return true; // click on background
			} 
			return false;
		} 
		return false;
	}

	void resetVisibilityMenuItem() {
		if (this.gp.getGraph().getTypeSet().getTypeGraph() != null) {
			if (this.gp.getGraph().getTypeSet().getSelectedNodeType().getBasisType().isObjectOfTypeGraphNodeVisible()) {
				this.miNodeVisibility.setText("Hide Objects of Selected Node Type");
			} else {
				this.miNodeVisibility.setText("Show Objects of Selected Node Type");
			}
			
			this.miArcVisibility.setText("Hide Objects of Selected Edge Type");
			EdType type = this.gp.getGraph().getTypeSet().getSelectedArcType();
			Vector<EdArc> edges = this.gp.getGraph().getTypeSet().getTypeGraph().getArcs(type);
			for (int j=0; j<edges.size(); j++) {
				EdArc a = edges.get(j);
				if (!type.getBasisType().isObjectOfTypeGraphArcVisible(
							a.getSource().getType().getBasisType(),
							a.getTarget().getType().getBasisType())) {
					this.miArcVisibility.setText("Show Objects of Selected Edge Type");
				} 
			}
		}	
	}
	
	public void setViewModel(boolean view) {
		if (view) {
			this.miDraw.setEnabled(false);
			this.miAttrs.setEnabled(false);
			this.miMap.setEnabled(false);
			this.miUnmap.setEnabled(false);
		} else { // edit
			this.miDraw.setEnabled(true);
			this.miAttrs.setEnabled(true);
			this.miMap.setEnabled(true);
			this.miUnmap.setEnabled(true);
		}
	}

	public void deselectAll() {
		this.miDraw.setSelected(false);
		this.miMove.setSelected(false);
		this.miAttrs.setSelected(false);
		this.miMap.setSelected(false);
		this.miUnmap.setSelected(false);
	}

	public void setMainModeMenu(JMenu m) {
		this.mainModeMenu = m;
	}

	private String unspaced(String s) {
		return s.replaceAll(" ", "");
	}

	void selectMainEditModeMenuItem(String editmode) {
		String mode = unspaced(editmode);
		if (this.mainModeMenu != null)
			for (int i = 0; i < this.mainModeMenu.getItemCount(); i++) {
				if (this.mainModeMenu.getMenuComponent(i) instanceof JCheckBoxMenuItem) {
					if (((JCheckBoxMenuItem) this.mainModeMenu.getItem(i)).getText()
							.equals("Image_view"))
						;
					else {
						String itemname = unspaced(((JCheckBoxMenuItem) this.mainModeMenu
								.getItem(i)).getText());
						if (itemname.indexOf(mode) != -1)
							((JCheckBoxMenuItem) this.mainModeMenu.getItem(i))
									.setSelected(true);
						else
							((JCheckBoxMenuItem) this.mainModeMenu.getItem(i))
									.setSelected(false);
					}
				}
			}
	}

	public void selectEditModeMenuItem(String editmode) {
		String mode = unspaced(editmode);
		for (int i = 1; i < this.getComponentCount(); i++) {
			if (this.getComponent(i) instanceof JCheckBoxMenuItem) {
				String itemname = unspaced(((JCheckBoxMenuItem) this
						.getComponent(i)).getText());
				if (mode.equals("Image_view"))
					;
				else if (itemname.indexOf("MagicEdgeDrawSupport") != -1)
					;
				else if (itemname.indexOf("SynchronMoveofMappedObjects") != -1)
					;
				else if (itemname.indexOf("StaticNodePosition") != -1)
					;
				else {
					if (itemname.indexOf(mode) != -1)
						((JCheckBoxMenuItem) ((JMenuItem) this.getComponent(i)))
								.setSelected(true);
					else
						((JCheckBoxMenuItem) ((JMenuItem) this.getComponent(i)))
								.setSelected(false);
				}
			}
		}
	}

	public JCheckBoxMenuItem miDraw, miSelect, miMove, miAttrs, // miImage,
			miMap, miUnmap, miSynchronMove, miStaticNodePosition;

	private JMenu mainModeMenu;

	public JMenuItem miUndoManager, miUndo, miRedo, miDiscardAllEdits, 
		miNodeVisibility, miArcVisibility, miLayoutGraph, miStraightenArcs;

	GraGraEditor editor;

	RuleEditor ruleEditor;

	GraphEditor graphEditor;

	GraphPanel gp;

	GraphicsExportJPEG exportJPEG;
}
