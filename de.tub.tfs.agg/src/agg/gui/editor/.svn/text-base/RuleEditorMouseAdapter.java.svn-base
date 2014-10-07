/**
 * 
 */
package agg.gui.editor;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdGraphObject;
import agg.gui.AGGAppl;


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
			AGGAppl.getInstance().setCursor(this.editor.getLeftPanel().getEditCursor());
		} else if (source == this.editor.getRightPanel().getCanvas()) {
			AGGAppl.getInstance().setCursor(this.editor.getLeftPanel().getEditCursor());
		} else if (source == this.editor.getNACPanel().getCanvas()) {
			AGGAppl.getInstance().setCursor(this.editor.getLeftPanel().getEditCursor());
		} else if (this.editor.getGraphEditor() != null
				&& source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
			AGGAppl.getInstance().setCursor(this.editor.getLeftPanel().getEditCursor());
		}
	}

	public void mouseExited(MouseEvent e) {
		AGGAppl.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent e) {
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
				AGGAppl.getInstance().setCursor(new Cursor(Cursor.MOVE_CURSOR));
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
				/*
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
				*/
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
				/*
			case EditorConstants.INTERACT_NAC:				
				if (this.editor.getNAC() != null) {
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
				}
				break;
				*/
			case EditorConstants.REMOVE_NAC:
				if (this.editor.getNAC() != null) {
					if (source == this.editor.getLeftPanel().getCanvas()) {
						this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
						if (this.editor.removeNacMapping(this.leftObj, true)) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getNACPanel().updateGraphics();
						}
						this.leftObj = this.editor.setLeftGraphObject(null);
					}
				}
				break;
				/*
			case EditorConstants.INTERACT_PAC:				
				if (this.editor.getPAC() != null) {
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
				}
				break;
				*/
			case EditorConstants.REMOVE_PAC:
				if (this.editor.getPAC() != null) {				
					if (source == this.editor.getLeftPanel().getCanvas()) {
						this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
						if (this.editor.removePacMapping(this.leftObj, true)) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getNACPanel().updateGraphics();
						}
						this.leftObj = this.editor.setLeftGraphObject(null);
					}
				}
				break;
				/*
			case EditorConstants.INTERACT_AC:
				if (this.editor.getNestedAC() != null) {
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
				} 
				break;
				*/
			case EditorConstants.REMOVE_AC:
				if (this.editor.getNestedAC() != null) {
					if (source == this.editor.getLeftPanel().getCanvas()) {
						this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
						if (this.editor.removeNestedACMapping(this.leftObj, true)) {
							this.editor.getLeftPanel().updateGraphics();
							this.editor.getLeftCondPanel().updateGraphics();
						}
						this.leftObj = this.editor.setLeftGraphObject(null);
					}
				}
				break;
			case EditorConstants.INTERACT_MATCH:				
				if (this.editor.getGraphEditor() == null)
					break;
				if (source == this.editor.getLeftPanel().getCanvas()) {
					if (this.leftObj != null)
						this.leftObj.setWeakselected(false);
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					if (this.leftObj == null) 					
						this.graphObj = this.editor.setHostGraphObject(null);
					else 
						this.leftObj.setWeakselected(true);
				} else if (source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
					if (this.leftObj != null) {
						this.leftObj.setWeakselected(false);
						this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraph().getPicked(x, y));
						if (this.graphObj == null)
							this.leftObj = this.editor.setLeftGraphObject(null);
					}
				}
				if (this.leftObj != null && this.graphObj != null) {
					if (this.editor.setMatchMapping(this.leftObj, this.graphObj)) {
						this.editor.getLeftPanel().updateGraphics();
						this.editor.getGraphEditor().getGraphPanel().updateGraphics();
					}
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.graphObj = this.editor.setHostGraphObject(null);
				}
				this.editor.getLeftPanel().updateGraphics();
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
				if (this.editor.isEditPopupMenuShown() 
						&& this.editor.getEditPopupMenu().isMapping()) {
					// set origin of mapping by pop-up menu targeting object
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getEditPopupMenu().getPickedObj());
				}
				if (this.leftObj != null)
					this.leftObj.setWeakselected(false);
				
				if (source == this.editor.getLeftPanel().getCanvas()) {
					if (this.editor.isEditPopupMenuShown() 
							&& this.editor.getEditPopupMenu().isMapping()) {
						this.editor.setObjMapping(false);
						this.leftObj = this.editor.setLeftGraphObject(null);
					} 
					else { // set origin of mapping by explicit MAP mode
						this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
						if (this.leftObj == null)
							this.editor.setObjMapping(false);
						else if (this.leftObj.isSelected()) {
							this.editor.setEditMode(EditorConstants.MAPSEL);
							if (this.editor.getGraphEditor() != null)
								this.editor.getGraphEditor().setEditMode(EditorConstants.MAPSEL);
						} else 
							this.leftObj.setWeakselected(true);
					}
					if (this.editor.isEditPopupMenuShown() 
							&& this.editor.getEditPopupMenu().isMapping()
							&& !this.editor.isObjMapping()) {
						this.editor.resetEditModeAfterMapping();
					}
					this.editor.getLeftPanel().updateGraphics(); // wegen weak selected
				}
				// set image object of rule mapping
				else if (source == this.editor.getRightPanel().getCanvas()
						&& this.editor.getRightPanel().getEditMode() == EditorConstants.MAP) {
					this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
					this.editor.setMappingRule(this.leftObj, this.rightObj);
					this.rightObj = this.editor.setRightGraphObject(null);
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.editor.getRightPanel().getCanvas().setPickedObject(null);
				}
				// set image object of AC mapping
				else if (source == this.editor.getLeftCondPanel().getCanvas()
						&& this.editor.getLeftCondPanel().getGraph() != null
						&& this.editor.getLeftCondPanel().getEditMode() == EditorConstants.MAP) {
					this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getLeftCondPanel().getGraph().getPicked(x, y));
					this.editor.setMappingApplCond(this.leftObj, this.leftCondObj);
					this.leftCondObj = this.editor.setLeftCondGraphObject(null);
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.editor.getLeftCondPanel().getCanvas().setPickedObject(null);
				}
				// set image object of match mapping
				else if (!(this.editor.getRule() instanceof EdAtomic)
						&& (this.editor.getGraphEditor() != null)
						&& (source == this.editor.getGraphEditor().getGraphPanel().getCanvas())
						&& !this.editor.getGraphEditor().getGraph().isTypeGraph()
						&& (this.editor.getGraphEditor().getGraphPanel().getEditMode() == EditorConstants.MAP)) {

					this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraph().getPicked(x, y));
					this.editor.setMappingGraph(this.leftObj, this.graphObj);
					this.graphObj = this.editor.setHostGraphObject(null);
					this.leftObj = this.editor.setLeftGraphObject(null);
					this.editor.getGraphEditor().getGraphPanel().getCanvas().setPickedObject(null);
				}
				break;
			case EditorConstants.UNMAP:
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					this.editor.removeMappingLeft(this.leftObj);
					this.leftObj = this.editor.setLeftGraphObject(null);
				} 
				else if (source == this.editor.getRightPanel().getCanvas()) {
					this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
					this.editor.removeMappingRight(this.rightObj);
					this.rightObj = this.editor.setRightGraphObject(null);
				} 
				else if (source == this.editor.getLeftCondPanel().getCanvas()) {
					this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getLeftCondPanel().getGraph().getPicked(x, y));
					this.editor.removeMappingApplCond(this.leftCondObj);
					this.leftCondObj = this.editor.setLeftCondGraphObject(null);
				} 
				else if (this.editor.getGraphEditor() != null
						&& source == this.editor.getGraphEditor().getGraphPanel().getCanvas()) {
					this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraph().getPicked(x, y));
					this.editor.removeMappingGraph(this.graphObj);
					this.graphObj = this.editor.setHostGraphObject(null);
				}
				break;
			case EditorConstants.REMOVE_MAP:
			case EditorConstants.REMOVE_MAPSEL:
				if (source == this.editor.getLeftPanel().getCanvas()) {
					this.leftObj = this.editor.setLeftGraphObject(this.editor.getLeftPanel().getGraph().getPicked(x, y));
					this.editor.removeMappingLeft(this.leftObj);
					this.leftObj = this.editor.setLeftGraphObject(null);
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
				} 
				else if (source == this.editor.getRightPanel().getCanvas()) {
					this.rightObj = this.editor.setRightGraphObject(this.editor.getRightPanel().getGraph().getPicked(x, y));
					this.editor.setMappingRule(this.editor.getRule().getLeft().getSelectedObjs(), this.rightObj);
					this.rightObj = this.editor.setRightGraphObject(null);
				} 
				else if (source == this.editor.getLeftCondPanel().getCanvas()
						&& this.editor.getLeftCondPanel().getGraph() != null) {
					if (this.editor.getNAC() != null || this.editor.getPAC() != null) {
						this.leftCondObj = this.editor.setLeftCondGraphObject(this.editor.getLeftCondPanel().getGraph().getPicked(x, y));
						this.editor.setMappingApplCond(this.editor.getRule().getLeft().getSelectedObjs(), this.leftCondObj);
						this.leftCondObj = this.editor.setLeftCondGraphObject(null);
					}
				}
				else if (!(this.editor.getRule() instanceof EdAtomic)
						&& this.editor.getGraphEditor() != null
						&& source == this.editor.getGraphEditor().getGraphPanel().getCanvas()
						&& !this.editor.getGraphEditor().getGraph().isTypeGraph()) {
					this.graphObj = this.editor.setHostGraphObject(this.editor.getGraphEditor().getGraphPanel().getGraph()
							.getPicked(x, y));
					this.editor.setMappingGraph(this.editor.getRule().getLeft().getSelectedObjs(), this.graphObj);
					this.graphObj = this.editor.setHostGraphObject(null);
				}
				break;
			default:
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
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
			AGGAppl.getInstance().setCursor(new Cursor(Cursor.MOVE_CURSOR));
			break;
		case EditorConstants.COPY_ARC:
			AGGAppl.getInstance().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			break;
		case EditorConstants.COPY:
			/*
			if (this.editor.getActivePanel().getGraph().getMsg().length() != 0)
				this.editor.setMsg(this.editor.getLeftPanel().getGraph().getMsg());

			if (this.editor.getActivePanel().getLastEditMode() != EditorConstants.COPY) {
				if (this.editor.getGraGraEditor() != null) {
					this.editor.getGraGraEditor().setEditMode(this.editor.getGraGraEditor().getLastEditMode());
					this.editor.getGraGraEditor().forwardModeCommand(EditorConstants.getModeOfID(this.editor.getGraGraEditor().getLastEditMode()));
					if ((this.editor.getSourceOfCopy() != null)
							&& (this.editor.getActivePanel().getGraph() != this.editor.getSourceOfCopy())) {
						GraphPanel gp = this.editor.getGraGraEditor().getPanelOfGraph(this.editor.getSourceOfCopy());
						this.editor.getSourceOfCopy().eraseSelected(gp.getCanvas().getGraphics(), true);
						this.editor.getSourceOfCopy().deselectAll();
						this.editor.getGraGraEditor().resetAfterCopy();
						gp.repaint();
						this.editor.getActivePanel().getGraph().deselectAll();
						this.editor.getActivePanel().repaint();
					}
					this.editor.getGraGraEditor().setMsg(this.editor.getMsg());
				} else {
					this.editor.setEditMode(this.editor.getActivePanel().getLastEditMode());
					this.editor.getRule().getLeft().setGraphToCopy(null);
					this.editor.getRule().getRight().setGraphToCopy(null);
					if (this.editor.getLeftCondPanel().getGraph() != null)
						this.editor.getLeftCondPanel().getGraph().setGraphToCopy(null);
				}
				AGGAppl.getInstance().setCursor(this.editor.getActivePanel().getLastEditCursor());
			} else { 
				if (this.editor.getGraGraEditor() != null) {
					this.editor.getGraGraEditor().setEditMode(this.editor.getGraGraEditor().getLastEditMode());
					this.editor.getGraGraEditor().forwardModeCommand(EditorConstants.getModeOfID(this.editor.getGraGraEditor().getLastEditMode()));
					if ((this.editor.getSourceOfCopy() != null)
							&& !this.editor.getActivePanel().getGraph().equals(this.editor.getSourceOfCopy())) {
						GraphPanel gp = this.editor.getGraGraEditor().getPanelOfGraph(this.editor.getSourceOfCopy());
						this.editor.getSourceOfCopy().eraseSelected(gp.getCanvas().getGraphics(), true);
						this.editor.getSourceOfCopy().deselectAll();
						this.editor.getGraGraEditor().resetAfterCopy();
						gp.repaint();
						this.editor.getActivePanel().getGraph().deselectAll();
						this.editor.getActivePanel().repaint();
					}
					this.editor.getGraGraEditor().setMsg(this.editor.getMsg());
				} else {
					this.editor.getRule().getLeft().setGraphToCopy(null);
					this.editor.getRule().getRight().setGraphToCopy(null);
					if (this.editor.getNACPanel().getGraph() != null)
						this.editor.getNACPanel().getGraph().setGraphToCopy(null);
				}
				AGGAppl.getInstance().setCursor(this.editor.getActivePanel().getLastEditCursor());
			}
			this.editor.setSourceOfCopy(null);
			*/
			break;
		default:
				AGGAppl.getInstance().setCursor(this.editor.getActivePanel().getEditCursor());
			break;
		}
		this.editor.unsetDragging();
	}

	public void mouseClicked(MouseEvent e) {
	}


	
}
