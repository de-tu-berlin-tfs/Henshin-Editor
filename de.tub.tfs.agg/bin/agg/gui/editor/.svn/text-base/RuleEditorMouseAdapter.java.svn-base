/**
 * 
 */
package agg.gui.editor;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.SwingUtilities;

import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdGraphObject;
import agg.xt_basis.GraphObject;

/**
 * @author olga
 *
 */
public class RuleEditorMouseAdapter extends MouseAdapter {

	private final RuleEditor editor;
	private EdGraphObject leftObj;
	private EdGraphObject rightObj;
	private EdGraphObject leftCondObj;
	private EdGraphObject graphObj;

	
	/**
	 * 
	 */
	public RuleEditorMouseAdapter(final RuleEditor ruleEditorImpl) {
		this.editor = ruleEditorImpl;
		this.editor.addMouseListener(this);
	}

	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if (this.editor.getApplFrame() == null) {
			return;
		}
		if (source == this.editor.getLeftPanel().getCanvas()) {
			this.editor.setCursorOfApplFrame(this.editor.getLeftPanel().getEditCursor());
		} else if (source == this.editor.getRightPanel().getCanvas()) {
			this.editor.setCursorOfApplFrame(this.editor.getLeftPanel().getEditCursor());
		} else if (source == this.editor.getNACPanel().getCanvas()) {
			this.editor.setCursorOfApplFrame(this.editor.getLeftPanel().getEditCursor());
		} else if (this.editor.getGraphEditor() != null
				&& source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
			this.editor.setCursorOfApplFrame(this.editor.getLeftPanel().getEditCursor());
		}
	}

	public void mouseExited(MouseEvent e) {
		this.editor.setCursorOfApplFrame(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent e) {
//		System.out.println(">>> RuleEditor.mousePressed "+e.getSource()+this.editor.getRule().isEditable());
		if (this.editor.getRule() == null
				|| !this.editor.getRule().isEditable()) {
			return;
		}
		
		Object source = e.getSource();
		if (this.editor.setActivePanel(source) == null) {
			return;
		}
		
		int x = e.getX();
		int y = e.getY();

		if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
			if ((this.editor.isEditPopupMenuShown() 
						&& this.editor.getEditPopupMenu().isMapping())
					|| (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping())) {
				this.editor.resetEditModeAfterMapping();
			}
			if (this.editor.getActivePanel().getCanvas().isLeftPressed()) {
				this.editor.allowToShowPopupMenu(false);
			}
		} else if (SwingUtilities.isMiddleMouseButton(e)) {
			if (this.editor.getActivePanel().getCanvas().getPickedObject(e.getX(),
							e.getY(), this.editor.getGraphics().getFontMetrics()) != null) {
				this.editor.setCursorOfApplFrame(new Cursor(Cursor.MOVE_CURSOR));
			}
		} else if (SwingUtilities.isLeftMouseButton(e)) {
			if (this.editor.getActivePanel().getCanvas().isRightPressed()) {
				this.editor.allowToShowPopupMenu(false);
			}
			
			switch (this.editor.getLeftPanel().getEditMode()) {
			case EditorConstants.MOVE:
				EdGraphObject ego = this.editor.getActivePanel().getGraph().getPicked(x, y);
				this.editor.setMoveCursorWhenLoop(ego);				
			 break;
			case EditorConstants.INTERACT_RULE: 
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null)
						this.rightObj = null;
				} else if (source == this.editor.getRightPanel().getCanvas()) {
					if (this.leftObj != null)
						this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
				}
				if (this.leftObj != null && this.rightObj != null) {
					if (this.editor.setRuleMapping(this.leftObj, this.rightObj)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getRightPanel().updateGraphics();
					} 
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.rightObj = this.editor.setRightGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_RULE:
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.editor.removeRuleMapping(this.leftObj, true)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getRightPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
				}
				break;
			case EditorConstants.INTERACT_NAC:
				if (this.editor.getNAC() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null)
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				} else if (source == this.editor.getNACPanel().getCanvas()) {
					if (this.leftObj != null)
						this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getNACPanel().getGraph().getPicked(x, y));
				}
				if (this.leftObj != null && this.leftCondObj != null) {
					if (this.editor.setNACMapping(this.leftObj, this.leftCondObj)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getNACPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_NAC:
				if (this.editor.getNAC() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.editor.removeNacMapping(this.leftObj, true)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getNACPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
				}
				break;
			case EditorConstants.INTERACT_PAC:
				if (this.editor.getPAC() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null)
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				} else if (source == this.editor.getNACPanel().getCanvas()) {
					if (this.leftObj != null)
						this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getNACPanel().getGraph().getPicked(x, y));
				}
				if (this.leftObj != null && this.leftCondObj != null) {
					if (this.editor.setPACMapping(this.leftObj, this.leftCondObj)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getLeftCondPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_PAC:
				if (this.editor.getPAC() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.editor.removePacMapping(this.leftObj, true)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getNACPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
				}
				break;
			case EditorConstants.INTERACT_AC:
				if (this.editor.getNestedAC() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null)
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				} else if (source == this.editor.getLeftCondPanel().getCanvas()) {
					if (this.leftObj != null)
						this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getNACPanel().getGraph().getPicked(x, y));
				}
				if (this.leftObj != null && this.leftCondObj != null) {
					if (this.editor.setNestedACMapping(this.leftObj, this.leftCondObj)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getLeftCondPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_AC:
				if (this.editor.getNestedAC() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.editor.removeNestedACMapping(this.leftObj, true)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getLeftCondPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
				}
				break;
			case EditorConstants.INTERACT_MATCH:
				if (this.editor.getGraphEditor() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null)
						this.graphObj = this.editor.setHostGraphObject(null);
				} else if (source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
					if (this.leftObj != null)
						this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraph().getPicked(x, y));
				}
				if (this.leftObj != null && this.graphObj != null) {
					if (this.editor.setMatchMapping(this.leftObj, this.graphObj)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.graphObj = this.editor.setHostGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_MATCH:
				if (this.editor.getGraphEditor() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.editor.removeMatchMapping(this.leftObj, true)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
				}
				break;
			case EditorConstants.MAP:
				// set original object of mapping
				if (this.editor.isEditPopupMenuShown() 
						&& this.editor.getEditPopupMenu().isMapping())
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getEditPopupMenu().getPickedObj());
				if (source == this.editor.getLeftPanel().getCanvas()) {
					if (this.editor.isEditPopupMenuShown() 
							&& this.editor.getEditPopupMenu().isMapping()) {
						this.editor.setObjMapping(false);
						this.leftObj = this.editor.setLeftGraphObject(null);
					} else { // exclusive map mode
						this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
						if (this.leftObj == null)
							this.editor.setObjMapping(false);
						else if (this.leftObj.isSelected()) {
							this.editor.setEditMode(EditorConstants.MAPSEL);
							if (this.editor.getGraphEditor() != null)
								this.editor.getGraphEditor().setEditMode(EditorConstants.MAPSEL);
						}
					}
					if (this.editor.isEditPopupMenuShown() 
							&& this.editor.getEditPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				}
				// set image object of rule mapping
				else if (source == this.editor.getRightPanel().getCanvas()
						&& this.editor.getRightPanel().getEditMode() == EditorConstants.MAP) {
					this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
					if (this.leftObj != null && this.rightObj != null) {
						if (this.editor.isEditPopupMenuShown() 
								&& this.editor.getEditPopupMenu().isMapping())					
							this.editor.setObjMapping(true);
						if (this.editor.setRuleMapping(this.leftObj, this.rightObj)) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getRightPanel().updateGraphics();
						}
						this.rightObj = this.editor.setRightGraphObject(null);
					} else
						this.editor.setObjMapping(false);

					if (this.editor.isEditPopupMenuShown()
							&& this.editor.getEditPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				}
				// set image object of NAC mapping
				else if (source == this.editor.getLeftCondPanel().getCanvas()
						&& this.editor.getLeftCondPanel().getGraph() != null
						&& this.editor.getLeftCondPanel().getEditMode() == EditorConstants.MAP) {
					this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getLeftCondPanel().getGraph().getPicked(x, y));
					if (this.leftObj != null && this.leftCondObj != null) {
						if (this.editor.isEditPopupMenuShown() 
								&& this.editor.getEditPopupMenu().isMapping())
							this.editor.setObjMapping(true);
						if (this.editor.getNAC() != null) {
							if (this.editor.setNACMapping(this.leftObj, this.leftCondObj)) {
								this.editor.getLeftPanel().updateGraphics();
								this.editor.getLeftCondPanel().updateGraphics();
							}
						} else if (this.editor.getPAC() != null) {
							if (this.editor.setPACMapping(this.leftObj, this.leftCondObj)) {
								this.editor.getLeftPanel().updateGraphics();
								this.editor.getLeftCondPanel().updateGraphics();
							}
						} else if (this.editor.getNestedAC() != null) {
							if (this.editor.setNestedACMapping(this.leftObj, this.leftCondObj)) {
								this.editor.getLeftPanel().updateGraphics();
								this.editor.getLeftCondPanel().updateGraphics();
							}
						}
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
					} else
						this.editor.setObjMapping(false);

					if (this.editor.isEditPopupMenuShown()
							&& this.editor.getEditPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				}
				// set image object of match mapping
				else if (!(this.editor.getRule() instanceof EdAtomic)
						&& (this.editor.getGraphEditor() != null)
						&& (source == this.editor.getGraphEditor().getGraphPanel().getCanvas())
						&& (this.editor.getGraphEditor().getGraphPanel().getEditMode() == EditorConstants.MAP)) {

					this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraph().getPicked(x, y));
					if (this.leftObj != null && this.graphObj != null) {
						if (this.editor.isEditPopupMenuShown() 
								&& this.editor.getEditPopupMenu().isMapping())
							this.editor.setObjMapping(true);
						if (this.editor.setMatchMapping(this.leftObj, this.graphObj)) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getGraphEditor().getGraphPanel().updateGraphics();
						}
						this.graphObj = this.editor.setHostGraphObject(null);
					} else
						this.editor.setObjMapping(false);

					if (this.editor.isEditPopupMenuShown() && this.editor.getEditPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				}
				break;
			case EditorConstants.UNMAP:
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null)
						break;
					boolean unmapdone = false;
					if (this.leftObj.isSelected()) {
						for (int i = 0; i < this.editor.getRule().getLeft().getSelectedObjs()
								.size(); i++) {
							EdGraphObject lgo = this.editor.getRule().getLeft()
									.getSelectedObjs().elementAt(i);
							if (this.editor.removeRuleMapping(lgo, true))
								if (!unmapdone)
									unmapdone = true;
							if (this.editor.getNAC() != null)
								if (this.editor.removeNacMapping(lgo, true))
									if (!unmapdone)
										unmapdone = true;
							if (this.editor.getPAC() != null)
								if (this.editor.removePacMapping(lgo, true))
									if (!unmapdone)
										unmapdone = true;
							if (this.editor.getRule().getMatch() != null)
								if (this.editor.removeMatchMapping(lgo, true))
									if (!unmapdone)
										unmapdone = true;
						}
					} else {
						if (this.editor.removeRuleMapping(this.leftObj, true))
							if (!unmapdone)
								unmapdone = true;
						if (this.editor.getNAC() != null)
							if (this.editor.removeNacMapping(this.leftObj, true))
								if (!unmapdone)
									unmapdone = true;
						if (this.editor.getPAC() != null)
							if (this.editor.removePacMapping(this.leftObj, true))
								if (!unmapdone)
									unmapdone = true;
						if (this.editor.getRule().getMatch() != null)
							if (this.editor.removeMatchMapping(this.leftObj, true))
								if (!unmapdone)
									unmapdone = true;
					}
					if (unmapdone) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getRightPanel().updateGraphics();
						if (this.editor.getNAC() != null)
							this.editor.getNACPanel().updateGraphics();
						if (this.editor.getPAC() != null)
							this.editor.getNACPanel().updateGraphics();
						if (this.editor.getRule().getMatch() != null 
								&& this.editor.getGraphEditor() != null)
							this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
				} else if (source == this.editor.getRightPanel().getCanvas()) {
					this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
					if (this.rightObj == null)
						break;
					boolean unmapdone = false;
					if (this.rightObj.isSelected()) {
						for (int i = 0; i < this.editor.getRule().getRight().getSelectedObjs()
								.size(); i++) {
							EdGraphObject imageObj = this.editor.getRule().getRight()
									.getSelectedObjs().elementAt(i);
							Vector<EdGraphObject> vec = this.editor.getRule()
									.getOriginal(imageObj);
							for (int j = 0; j < vec.size(); j++) {
								EdGraphObject go = vec.get(j);
								if (this.editor.removeRuleMapping(go, true))
									if (!unmapdone)
										unmapdone = true;
							}
						}
					} else {
						Vector<EdGraphObject> vec = this.editor.getRule().getOriginal(this.rightObj);
						for (int j = 0; j < vec.size(); j++) {
							EdGraphObject go = vec.get(j);
							if (this.editor.removeRuleMapping(go, true))
								if (!unmapdone)
									unmapdone = true;
						}
					}
					if (unmapdone) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getRightPanel().updateGraphics();
					}
					this.rightObj = this.editor.setRightGraphObject(null);
				} else if (source == this.editor.getNACPanel().getCanvas()) {
					this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getNACPanel().getGraph().getPicked(x, y));
					if (this.leftCondObj == null)
						break;
					boolean unmapdone = false;
					if (this.leftCondObj.isSelected()) {
						if (this.editor.getNAC() != null) {
							for (int i = 0; i < this.editor.getNAC().getSelectedObjs().size(); i++) {
								EdGraphObject imageObj = this.editor.getNAC().getSelectedObjs()
										.elementAt(i);
								Vector<EdGraphObject> vec = this.editor.getNAC()
										.getOriginal(imageObj);
								for (int j = 0; j < vec.size(); j++) {
									EdGraphObject go = vec.get(j);
									if (this.editor.removeNacMapping(go, true))
										if (!unmapdone)
											unmapdone = true;
								}
							}
						}
						if (this.editor.getPAC() != null) {
							for (int i = 0; i < this.editor.getPAC().getSelectedObjs().size(); i++) {
								EdGraphObject imageObj = this.editor.getPAC().getSelectedObjs()
										.elementAt(i);
								Vector<EdGraphObject> vec = this.editor.getPAC()
										.getOriginal(imageObj);
								for (int j = 0; j < vec.size(); j++) {
									EdGraphObject go = vec.get(j);
									if (this.editor.removePacMapping(go, true))
										if (!unmapdone)
											unmapdone = true;
								}
							}
						}
					} else if (this.editor.getNAC() != null) {
						Vector<EdGraphObject> vec = this.editor.getNAC()
								.getOriginal(this.leftCondObj);
						for (int j = 0; j < vec.size(); j++) {
							EdGraphObject go = vec.get(j);
							if (this.editor.removeNacMapping(go, true))
								if (!unmapdone)
									unmapdone = true;
						}
					} else if (this.editor.getPAC() != null) {
						Vector<EdGraphObject> vec = this.editor.getPAC()
								.getOriginal(this.leftCondObj);
						for (int j = 0; j < vec.size(); j++) {
							EdGraphObject go = vec.get(j);
							if (this.editor.removePacMapping(go, true))
								if (!unmapdone)
									unmapdone = true;
						}
					}
					if (unmapdone) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getNACPanel().updateGraphics();
					}
					this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				} else if (this.editor.getGraphEditor() != null
						&& source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
					this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraph().getPicked(x, y));
					if (this.graphObj == null)
						break;
					boolean unmapdone = false;
					if (this.graphObj.isSelected()) {
						for (int i = 0; i < this.editor.getGraphEditor().getGraph()
								.getSelectedObjs().size(); i++) {
							EdGraphObject imageObj = this.editor.getGraphEditor().getGraph()
									.getSelectedObjs().elementAt(i);
							Enumeration<GraphObject> inverse = this.editor.getRule().getMatch()
									.getInverseImage(imageObj.getBasisObject());
							while (inverse.hasMoreElements()) {
								GraphObject go = inverse.nextElement();
								EdGraphObject lgo = this.editor.getRule().getLeft()
										.findGraphObject(go);
								if (this.editor.removeMatchMapping(lgo, true))
									if (!unmapdone)
										unmapdone = true;
							}
						}
					} else if (this.editor.getRule().getMatch() != null) {
						Enumeration<GraphObject> inverse = this.editor.getRule().getMatch().getInverseImage(
								this.graphObj.getBasisObject());
						while (inverse.hasMoreElements()) {
							GraphObject go = inverse.nextElement();
							EdGraphObject lgo = this.editor.getRule().getLeft()
									.findGraphObject(go);
							if (this.editor.removeMatchMapping(lgo, true))
								if (!unmapdone)
									unmapdone = true;
						}
					}
					if (unmapdone) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
					this.graphObj = this.editor.setHostGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_MAP:
				if (source == this.editor.getLeftPanel().getCanvas()) {
					boolean unmapdone = false;
					EdGraphObject lgo = this.editor.getLeftPanel().getGraph().getPicked(x, y);
					if (this.editor.removeRuleMapping(lgo, true))
						if (!unmapdone)
							unmapdone = true;
					if (this.editor.getNAC() != null)
						if (this.editor.removeNacMapping(lgo, true))
							if (!unmapdone)
								unmapdone = true;
					if (this.editor.getRule().getMatch() != null)
						if (this.editor.removeMatchMapping(lgo, true))
							if (!unmapdone)
								unmapdone = true;
					if (unmapdone) {
						this.editor.getRule().update();
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getRightPanel().updateGraphics();
						if (this.editor.getNAC() != null)
							this.editor.getNACPanel().updateGraphics();
						if (this.editor.getRule().getMatch() != null
								&& this.editor.getGraphEditor() != null)
							this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
				}
				break;
			case EditorConstants.REMOVE_MAPSEL:
				if (source == this.editor.getLeftPanel().getCanvas()) {
					if (this.editor.getLeftPanel().getGraph().getPicked(x, y) == null)
						break;
					boolean unmapdone = false;
					for (int i = 0; i < this.editor.getRule().getLeft().getSelectedObjs()
							.size(); i++) {
						EdGraphObject lgo = this.editor.getRule().getLeft().getSelectedObjs()
								.elementAt(i);
						if (this.editor.removeRuleMapping(lgo, true))
							if (!unmapdone)
								unmapdone = true;
						if (this.editor.getNAC() != null)
							if (this.editor.removeNacMapping(lgo, true))
								if (!unmapdone)
									unmapdone = true;
						if (this.editor.getRule().getMatch() != null)
							if (this.editor.removeMatchMapping(lgo, true))
								if (!unmapdone)
									unmapdone = true;
					}
					if (unmapdone) {
						this.editor.getRule().update();
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getRightPanel().updateGraphics();
						if (this.editor.getNAC() != null)
							this.editor.getNACPanel().updateGraphics();
						if (this.editor.getRule().getMatch() != null
								&& this.editor.getGraphEditor() != null)
							this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
				}
				break;
			case EditorConstants.MAPSEL:
				this.leftObj = this.editor.setLeftGraphObject(null);
				if (source == this.editor.getLeftPanel().getCanvas()) {
					if (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping())
						;
					else {
						this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
						if (this.leftObj != null && !this.leftObj.isSelected()) {
							this.editor.setEditMode(EditorConstants.MAP);
							if (this.editor.getGraphEditor() != null)
								this.editor.getGraphEditor().setEditMode(EditorConstants.MAP);
						}
					}
				} else if (source == this.editor.getRightPanel().getCanvas()) {
					this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
					if (this.rightObj != null) {
						if (this.editor.isEditSelPopupMenuShown() 
								&& this.editor.getEditSelPopupMenu().isMapping()) {
							this.editor.setObjMapping(true);
						}
						boolean mapdone = false;
						for (int i = 0; i < this.editor.getRule().getLeft().getSelectedObjs()
								.size(); i++) {
							EdGraphObject lgo = this.editor.getRule().getLeft().getSelectedObjs()
									.elementAt(i);
							if (this.editor.setRuleMapping(lgo, this.rightObj))
								if (!mapdone)
									mapdone = true;
						}
						if (mapdone) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getRightPanel().updateGraphics();
						}
						this.rightObj = this.editor.setRightGraphObject(null);
					} else
						this.editor.setObjMapping(false);
					this.leftObj = this.editor.setLeftGraphObject(null);

					if (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				} else if (source == this.editor.getNACPanel().getCanvas() 
						&& this.editor.getNAC() != null) {
					this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getNACPanel().getGraph().getPicked(x, y));
					if (this.leftCondObj != null) {
						if (this.editor.isEditSelPopupMenuShown() 
								&& this.editor.getEditSelPopupMenu().isMapping()) {
							this.editor.setObjMapping(true);
						}
						boolean mapdone = false;
						for (int i = 0; i < this.editor.getRule().getLeft().getSelectedObjs()
								.size(); i++) {
							EdGraphObject lgo = this.editor.getRule().getLeft().getSelectedObjs()
									.elementAt(i);
							if (this.editor.setNACMapping(lgo, this.leftCondObj))
								if (!mapdone)
									mapdone = true;
						}
						if (mapdone) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getLeftCondPanel().updateGraphics();
						}
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
					} else
						this.editor.setObjMapping(false);
					this.leftObj = this.editor.setLeftGraphObject(null);

					if (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				} else if (source == this.editor.getPACPanel().getCanvas() 
						&& this.editor.getPAC() != null) {
					this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getPACPanel().getGraph().getPicked(x, y));
					if (this.leftCondObj != null) {
						if (this.editor.isEditSelPopupMenuShown() 
								&& this.editor.getEditSelPopupMenu().isMapping()) {
							this.editor.setObjMapping(true);
						}
						boolean mapdone = false;
						for (int i = 0; i < this.editor.getRule().getLeft().getSelectedObjs()
								.size(); i++) {
							EdGraphObject lgo = this.editor.getRule().getLeft().getSelectedObjs()
									.elementAt(i);
							if (this.editor.setPACMapping(lgo, this.leftCondObj))
								if (!mapdone)
									mapdone = true;
						}
						if (mapdone) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getLeftCondPanel().updateGraphics();
						}
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
					} else
						this.editor.setObjMapping(false);
					this.leftObj = this.editor.setLeftGraphObject(null);

					if (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				} else if (this.editor.getGraphEditor() != null
						&& source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
					this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraphPanel().getGraph()
							.getPicked(x, y));
					if (this.graphObj != null) {
						if (this.editor.isEditSelPopupMenuShown() 
								&& this.editor.getEditSelPopupMenu().isMapping()){
							this.editor.setObjMapping(true);
						}
						boolean mapdone = false;
						for (int i = 0; i < this.editor.getRule().getLeft().getSelectedObjs()
								.size(); i++) {
							EdGraphObject lgo = this.editor.getRule().getLeft().getSelectedObjs()
									.elementAt(i);
							if (this.editor.setMatchMapping(lgo, this.graphObj))
								if (!mapdone)
									mapdone = true;
						}
						if (mapdone) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getGraphEditor().getGraphPanel().updateGraphics();
						}
						this.graphObj = this.editor.setHostGraphObject(null);
					} else
						this.editor.setObjMapping(false);
					this.leftObj = this.editor.setLeftGraphObject(null);

					if (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping()
							&& !this.editor.isObjMapping())
						this.editor.resetEditModeAfterMapping();
				}
				break;
			default:
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
//		System.out.println(">>> RuleEditor.mouseReleased "+e.getSource()+this.editor.getRule().isEditable());
		if (this.editor.getRule() == null
				|| !this.editor.getRule().isEditable()) {
			return;
		}

		if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
			if ((this.editor.isEditPopupMenuShown()
					&& this.editor.getEditPopupMenu().isMapping())
					|| (this.editor.isEditSelPopupMenuShown() 
							&& this.editor.getEditSelPopupMenu().isMapping())) {
				this.editor.resetEditModeAfterMapping();
			}
			if ((this.editor.getGraGra() != null) 
					&& this.editor.getGraGra().getGraph().isEditable()
					&& (e.getX() > 0 && e.getY() > 0)) {
				if (this.editor.isPopupMenuAllowed())
					this.editor.showPopupMenu(e);							
			}
			
			this.editor.allowToShowPopupMenu(true);
			this.editor.getActivePanel().getCanvas().unsetLeftAndRightPressed();
			return;
		}

		if (!this.editor.getActivePanel().getCanvas().isRightPressed()) {
			this.editor.getActivePanel().getCanvas().setLeftPressed(false);
		}
		
		switch (this.editor.getActivePanel().getEditMode()) {
		case EditorConstants.ARC:
			this.editor.setMsg("You have just picked the source of an edge. Click on a node to get the target.");
			break;
		case EditorConstants.DRAW:
			break;
		case EditorConstants.MOVE:
			this.editor.setCursorOfApplFrame(new Cursor(Cursor.MOVE_CURSOR));
			break;
		case EditorConstants.COPY_ARC:
			this.editor.setCursorOfApplFrame(new Cursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case EditorConstants.COPY:
			if (this.editor.getActivePanel().getGraph().getMsg().length() != 0)
				this.editor.setMsg(this.editor.getLeftPanel().getGraph().getMsg());

			if (this.editor.getActivePanel().getLastEditMode() != EditorConstants.COPY) {
				if (this.editor.getGraGraEditor() != null) {
					this.editor.getGraGraEditor().setEditMode(this.editor.getActivePanel().getLastEditMode());
					this.editor.getGraGraEditor().resetAfterCopy();
					if ((this.editor.getSourceOfCopy() != null)
							&& !this.editor.getActivePanel().getGraph().equals(this.editor.getSourceOfCopy())) {
						this.editor.getSourceOfCopy().eraseSelected(this.editor.getGraGraEditor()
								.getPanelOfGraph(this.editor.getSourceOfCopy()).getCanvas()
								.getGraphics(), true);
						this.editor.getSourceOfCopy().deselectAll();
					}
					this.editor.getGraGraEditor().setMsg(this.editor.getMsg());
				} else {
					this.editor.setEditMode(this.editor.getActivePanel().getLastEditMode());
					this.editor.getRule().getLeft().setGraphToCopy(null);
					this.editor.getRule().getRight().setGraphToCopy(null);
					if (this.editor.getNACPanel().getGraph() != null)
						this.editor.getNACPanel().getGraph().setGraphToCopy(null);
				}
				this.editor.setCursorOfApplFrame(this.editor.getActivePanel().getLastEditCursor());
			} else { 
				if (this.editor.getGraGraEditor() != null) {
					this.editor.getGraGraEditor().setEditMode(this.editor.getActivePanel().getLastEditMode());
					this.editor.getGraGraEditor().resetAfterCopy();
					if ((this.editor.getSourceOfCopy() != null)
							&& !this.editor.getActivePanel().getGraph().equals(this.editor.getSourceOfCopy())) {
						this.editor.getSourceOfCopy().eraseSelected(this.editor.getGraGraEditor()
								.getPanelOfGraph(this.editor.getSourceOfCopy()).getCanvas()
								.getGraphics(), true);
						this.editor.getSourceOfCopy().deselectAll();
					}
					this.editor.getGraGraEditor().setMsg(this.editor.getMsg());
				} else {
					this.editor.setEditMode(EditorConstants.DRAW);
					this.editor.getRule().getLeft().setGraphToCopy(null);
					this.editor.getRule().getRight().setGraphToCopy(null);
					if (this.editor.getNACPanel().getGraph() != null)
						this.editor.getNACPanel().getGraph().setGraphToCopy(null);
				}
				this.editor.setCursorOfApplFrame(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			this.editor.setSourceOfCopy(null);
			break;
		default:
				this.editor.setCursorOfApplFrame(this.editor.getActivePanel().getEditCursor());
			break;
		}
		this.editor.unsetDragging();
	}

	public void mouseClicked(MouseEvent e) {
	}


	
}
