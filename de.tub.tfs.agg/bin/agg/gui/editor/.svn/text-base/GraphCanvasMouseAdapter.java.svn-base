/**
 * 
 */
package agg.gui.editor;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdNode;
import agg.xt_basis.Arc;
import agg.xt_basis.Type;
import agg.xt_basis.TypeError;
import agg.xt_basis.TypeException;

/**
 * @author olga
 *
 */
public class GraphCanvasMouseAdapter extends MouseAdapter {

	private GraphCanvas canvas;
	
	/**
	 * 
	 */
	public GraphCanvasMouseAdapter(final GraphCanvas graphcanvas) {
		this.canvas = graphcanvas;
		this.canvas.addMouseListener(this);
	}

	public void mouseEntered(MouseEvent e) {
		if (this.canvas.isScrolling()) {
			this.canvas.setScrolling(false);
			this.canvas.setScrollingByDragging(false);
		}
	}

	public void mouseExited(MouseEvent e) {
//		this.canvas.update(this.canvas.getGraphics());
	}
	
	public void mouseClicked(MouseEvent e) {
		if (this.canvas.getGraphics() == null 
				|| this.canvas.getGraph() == null) {
			return;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			// open attribute editor
			if ((e.getClickCount() == 1 
						&& this.canvas.getEditMode() == EditorConstants.ATTRIBUTES)
					|| (e.getClickCount() == 2 
							&& !(this.canvas.getEditMode() == EditorConstants.DRAW 
									|| this.canvas.getEditMode() == EditorConstants.ARC))) {
				if (this.canvas.getGraph().isEditable()) {
					this.canvas.openAttrEditorForGraphObject(e);
				}
			}
			else if (e.getClickCount() == 1
					&& this.canvas.getEditMode() == EditorConstants.SELECT) {
				this.canvas.makeSelectionAt(e.getX(), e.getY());
			}
			
		} else if(SwingUtilities.isMiddleMouseButton(e)) {
			if (e.getClickCount() == 1
					&& this.canvas.getEditMode() == EditorConstants.SELECT) {
				this.canvas.makeSelectionAt(e.getX(), e.getY());
			}
			
		} else if (e.isPopupTrigger() 
					|| SwingUtilities.isRightMouseButton(e)
					|| this.canvas.getEditMode() == EditorConstants.MOVE
					|| this.canvas.getEditMode() == EditorConstants.SELECT) {
			if (this.canvas.isMagicArc()) {
				this.canvas.removeMagicArc();
			}			
		} 
	}

	public void mousePressed(MouseEvent e) {
		if (this.canvas.getGraphics() == null 
				|| this.canvas.getGraph() == null) {
			return;
		}
//		System.out.println("GraphCanvasMouseAdapter.mousePressed   ");
		if (this.canvas.isScrolling()) {
			this.canvas.endScrolling();
		}

		int x = e.getX();
		int y = e.getY();
		this.canvas.setChanged(false);

		if (e.isPopupTrigger() 
				|| SwingUtilities.isRightMouseButton(e)) {
			this.canvas.setRightPressed(true);
			
			if (this.canvas.isLeftPressed() || this.canvas.isMagicArc()) {
				this.canvas.update(this.canvas.getGraphics());
				this.canvas.removeMagicArc();
				if (this.canvas.isMagicArc())
					this.canvas.setEditMode(EditorConstants.DRAW);
			}

			this.canvas.setSourceObject(this.canvas.getPickedObject(e.getX(), e.getY(), 
										this.canvas.getGraphics().getFontMetrics()));			
			if (this.canvas.getSourceObject() != null) {
				if (this.canvas.isLeftPressed()) {
					this.canvas.setPickedPoint(e.getX(), e.getY());
					this.canvas.setPickedObject(this.canvas.getSourceObject());
				}
				else if (this.canvas.getSourceObject().isArc()) {
					this.canvas.setSourceObject(null);
				}
			}
			
			if (this.canvas.getEditMode() == EditorConstants.ARC) {
				this.canvas.setAnchorPoint(null);
				this.canvas.setEditMode(EditorConstants.DRAW);
			}
		}
		
		else if (SwingUtilities.isMiddleMouseButton(e)) {
//			System.out.println(">>> GraphCanvas.mousePressed::  isMiddleMouseButton");
			if (this.canvas.isMagicArc()) {
				this.canvas.update(this.canvas.getGraphics());
				this.canvas.removeMagicArc();
				this.canvas.setEditMode(EditorConstants.DRAW);
			} else if (this.canvas.isScrolling()) {
			}
			else {
				this.canvas.setPickedPoint(e.getX(), e.getY());		
				this.canvas.setPickedObject(this.canvas.getPickedObject(x, y, 
								this.canvas.getGraphics().getFontMetrics()));
				if (this.canvas.getPickedObject() == null) {
					this.canvas.setScrolling(this.canvas.startScrolling(e.getX(), e.getY()));
					if (!this.canvas.isScrolling()) {							
						this.canvas.startSelectBox(e.getX(), e.getY());
					}
				}
			}			
		} 
		
		else if (SwingUtilities.isLeftMouseButton(e)) {	
//			System.out.println(">>> GraphCanvas.mousePressed::  isLeftMouseButton  "+editable);
			
			this.canvas.setLeftPressed(true);
			
			switch (this.canvas.getEditMode()) {
			case EditorConstants.HELP:	
				break;

			case EditorConstants.DRAW:
				if (!this.canvas.getGraph().isEditable()) 					
					return;
				
				if (this.canvas.getGraph().getGraGra() == null
						|| this.canvas.getGraph().getGraGra().getSelectedNodeType() == null) {
					this.canvas.cannotCreateErrorMessage(" Create node ", " a node",
							"There isn't any node type selected.");
					return;
				}
				
				if (this.canvas.isRightPressed()) {
					this.canvas.setPickedObject(this.canvas.getPickedObject(e.getX(), e.getY(), 
									this.canvas.getGraphics().getFontMetrics()));	
					return;
				}
				
				this.canvas.setPickedPoint(x, y);					
				this.canvas.setSourceObject(this.canvas.getPickedNode(x, y));
				if (this.canvas.getSourceObject() == null) {
					if (this.canvas.getPickedArc(x, y) != null) {
						return;
					}					
					
					// check whether to create a new node is possible
					Type t = this.canvas.getGraph().getTypeSet().getSelectedNodeType().getBasisType();
					this.canvas.canCreateNodeOfType(t, null, null);
					
					// and to create a selection box is possible						
					this.canvas.startSelectBox(e.getX(), e.getY());
					
				} else {
					this.canvas.setEditMode(EditorConstants.ARC);
										
					if (this.canvas.isMagicEdgeSupportEnabled()) {
						if (!this.canvas.checkSourceOfMagicArc((EdNode) this.canvas.getSourceObject(), e.getX(), e.getY()))
							if (this.canvas.getGraph().getTypeSet().getSelectedArcType() != null)
								this.canvas.drawErrorImage(e.getX(), e.getY());
					}				
				}
				break;
			case EditorConstants.ARC:
				if (this.canvas.getGraph().getGraGra() == null
						|| this.canvas.getGraph().getGraGra().getSelectedArcType() == null) {
					this.canvas.cannotCreateErrorMessage(" Create edge ",  " an edge",
							"There isn't any edge type selected.");
					this.canvas.update(this.canvas.getGraphics());
					this.canvas.removeMagicArc();
					this.canvas.setEditMode(EditorConstants.DRAW);
					return;
				}
				if (this.canvas.isMagicArc()) {
					if (this.canvas.getPickedNode(x, y) != null) {
						this.canvas.makeArcByMagicArc(x, y);
						this.canvas.setMagicArc(false);
					}
				} else {
					this.canvas.setTargetObject(this.canvas.getPickedNode(x, y));
					if (this.canvas.getSourceObject() != null 
							&& this.canvas.getTargetObject() != null) {
						if (this.canvas.getSourceObject().isNode() 
								&& this.canvas.getTargetObject().isNode() ) {
							boolean typeErrorOccured = true;
							if (this.canvas.checkTargetOfArc((EdNode) this.canvas.getSourceObject(), 
															(EdNode) this.canvas.getTargetObject())) {
								try {
									EdArc ea = this.canvas.addArc(this.canvas.getSourceObject(), 
											this.canvas.getTargetObject(), 
											this.canvas.getAnchorPoint());
									typeErrorOccured = false;
									this.canvas.getGraph().drawArc(this.canvas.getGraphics(), ea);
									this.canvas.setChanged(true);
								} catch (TypeException ex) {}
							}
							if (typeErrorOccured && !this.canvas.getGraph().isTypeGraph()) {
								this.canvas.cannotCreateErrorMessage(
										" Create edge "," an edge",
										"The edge type &nbsp\" "
												+ this.canvas.getGraph().getTypeSet()
														.getSelectedArcType()
														.getBasisType().getName()
												+ " \"&nbsp; between these nodes isn't defined in the type graph.");
							}
						}
						this.canvas.setSourceObject(null);
						this.canvas.setTargetObject(null);
						this.canvas.setAnchorPoint(null);
						this.canvas.setMagicArcStart(null);
						this.canvas.setEditMode(EditorConstants.DRAW);
					} 
					else
						this.canvas.setAnchorPoint(new Point(x, y));
				}
				break;
			case EditorConstants.VIEW:
				this.canvas.setPickedPoint(e.getX(), e.getY());
				this.canvas.setPickedObject(this.canvas.getPickedObject(x, y, this.canvas.getGraphics().getFontMetrics()));
				if (this.canvas.getPickedObject() == null) {
					this.canvas.startSelectBox(e.getX(), e.getY());
				}
				break;
			case EditorConstants.MOVE:
			case EditorConstants.SELECT:
				this.canvas.setPickedPoint(e.getX(), e.getY());
				this.canvas.setPickedObject(this.canvas.getPickedObject(x, y, this.canvas.getGraphics().getFontMetrics()));			
				if (this.canvas.getPickedObject() == null) {
					this.canvas.startSelectBox(e.getX(), e.getY());
				}
				break;
			case EditorConstants.ATTRIBUTES:
			case EditorConstants.MAP:
			case EditorConstants.UNMAP:
				this.canvas.setPickedPoint(e.getX(), e.getY());
				this.canvas.setPickedObject(this.canvas.getPickedObject(x, y, this.canvas.getGraphics().getFontMetrics()));			
				if (this.canvas.getPickedObject() == null 
						&& this.canvas.getGraph().isEditable()) {
					this.canvas.startSelectBox(e.getX(), e.getY());
				}
				break;
			case EditorConstants.STRAIGHT:
				break;
			case EditorConstants.SET_PARENT:
				if (!this.canvas.getGraph().isEditable()) {
					return;
				}
				
				// tar is a parent, src is a child
				this.canvas.setTargetObject(this.canvas.getPickedObject(x, y, this.canvas.getGraphics().getFontMetrics()));
				if (this.canvas.getTargetObject() == null) {
					this.canvas.getViewport().setLastEditMode(this.canvas.getLastEditMode());
				}
				else if (this.canvas.getSourceObject() != null) {
					TypeError error = this.canvas.getGraph().getBasisGraph().getTypeSet()
							.checkInheritanceValidity(
									this.canvas.getSourceObject().getBasisObject().getType(),
									this.canvas.getTargetObject().getBasisObject().getType());
					if (error == null) {
						this.canvas.getGraph().addChangedParentToUndo(this.canvas.getSourceObject());

						Arc inheritArc = this.canvas.getGraph().getBasisGraph().getTypeSet()
								.addValidInheritanceRelation(
										this.canvas.getSourceObject().getBasisObject().getType(),
										this.canvas.getTargetObject().getBasisObject().getType());
//						EdArc edinheritArc = 
						this.canvas.getGraph().newInheritanceArc(
												inheritArc, this.canvas.getGraph().getArcs());

						this.canvas.getGraph().undoManagerEndEdit();

						this.canvas.getGraph().update();
						this.canvas.repaint();						
					} else {
						JOptionPane.showMessageDialog(null, error.getMessage(),
								"Type Graph Error", JOptionPane.ERROR_MESSAGE);
					}
					this.canvas.getViewport().setEditMode(this.canvas.getLastEditMode());
				}
				this.canvas.setToolTipText(null);
				if (this.canvas.getViewport().getParentEditor() instanceof GraphEditor) {
					((GraphEditor) this.canvas.getViewport().getParentEditor()).getGraGraEditor().setMsg("");
				}
				break;
			case EditorConstants.UNSET_PARENT:
				if (!this.canvas.getGraph().isEditable()) {
					return;
				}
				// tar is a parent, src is a child
				this.canvas.setTargetObject(this.canvas.getPickedObject(x, y, this.canvas.getGraphics().getFontMetrics()));
				if (this.canvas.getTargetObject() == null) {
					this.canvas.getViewport().setEditMode(this.canvas.getLastEditMode());
				} else if (this.canvas.getSourceObject() != null) {
					this.canvas.performDeleteInheritanceRel(
							(EdNode) this.canvas.getSourceObject(),
							(EdNode) this.canvas.getTargetObject());
					this.canvas.updateUndoButton();
					this.canvas.getViewport().setEditMode(this.canvas.getLastEditMode());
				}
				this.canvas.setToolTipText(null);
				if (this.canvas.getViewport().getParentEditor() instanceof GraphEditor) {
					((GraphEditor) this.canvas.getViewport().getParentEditor()).getGraGraEditor().setMsg("");
				}
				break;
			case EditorConstants.COPY:
				if (!this.canvas.getGraph().isEditable()) {
					return;
				}
				
				this.canvas.setMsg("");
				this.canvas.getGraph().eraseSelected(this.canvas.getGraphics(), true);
				this.canvas.copySelected(x, y);
				if (this.canvas.getGraph().getMsg().length() != 0) {
					this.canvas.setMsg("Edit / Duplicate : " + this.canvas.getGraph().getMsg());
				}
				this.canvas.getGraph().drawSelected(this.canvas.getGraphics());
				Dimension dim = this.canvas.getGraph().getGraphDimension();
				if ((dim.width != 0) && (dim.height != 0)) {
					if (dim.width < this.canvas.getWidth())
						dim.width = this.canvas.getWidth();
					if (dim.height < this.canvas.getHeight())
						dim.height = this.canvas.getHeight();
					this.canvas.setSize(dim);
				}
				this.canvas.unsetPicked();
				this.canvas.setChanged(true);
				this.canvas.getViewport().setEditMode(EditorConstants.MOVE);
				break;
			case EditorConstants.COPY_ARC:
				if (!this.canvas.getGraph().isEditable()) {
					return;
				}
				this.canvas.setMsg("");
				if (this.canvas.getSourceObject() == null)
					this.canvas.setSourceObject(this.canvas.getPickedNode(x, y));
				else if (this.canvas.getTargetObject() == null) {
					this.canvas.setTargetObject(this.canvas.getPickedNode(x, y));
					EdArc a = null;
					if (this.canvas.getTargetObject() != null)
						a = this.canvas.getGraph().getSelectedArc();
					if (a != null) {
						EdArc ac = this.canvas.getGraph().copyArc(a, 
								this.canvas.getSourceObject(), this.canvas.getTargetObject());
						if (ac != null) {
							if (a.isLine()) {
								if (a.hasAnchor()) {
									if ((a.getSource() != ac.getSource())
											|| (a.getTarget() != ac.getTarget())) {
										Point dxyAnchorSrc = new Point(a
												.getAnchor().x
												- a.getSource().getX(), a
												.getAnchor().y
												- a.getSource().getY());
										Point p = new Point(ac.getSource()
												.getX()
												+ dxyAnchorSrc.x, ac
												.getSource().getY()
												+ dxyAnchorSrc.y);
										if (p.x < 0 || p.y < 0)
											ac.setAnchor(null);
										else
											ac.setAnchor(p);
									} else {
										ac.setAnchor(new Point(
												a.getAnchor().x + 10, a
														.getAnchor().y + 10));
									}
								} else {
									Point p = new Point(
											(ac.getSource().getX()
													+ (ac.getTarget().getX() - ac
															.getSource().getX())
													/ 2 + 5),
											(ac.getSource().getY()
													+ (ac.getTarget().getY() - ac
															.getSource().getY())
													/ 2 + 5));
									ac.setAnchor(p);
								}
							} else {
								ac.setWidth(a.getWidth() + 10);
								ac.setHeight(a.getHeight() + 10);
							}
							ac.setTextOffset(a.getTextOffset().x + 5, a
									.getTextOffset().y + 5);
							this.canvas.getGraph().deselect(a);
							this.canvas.getGraph().select(ac);
							this.canvas.getGraph().drawSelected(this.canvas.getGraphics());
						} else {
							JOptionPane
									.showMessageDialog(
											null,
											"<html><body>Cannot copy edge. <br>"
													+ "The cause can be source or target node type incompatibility.", //</body></html>",
											" Copy edge",
											JOptionPane.WARNING_MESSAGE);
							this.canvas.getGraph().deselect(a);
						}
					} else {
						JOptionPane
								.showMessageDialog(
										null,
										"<html><body>Cannot copy edge.<br> "
												+ "The second mouse click must be pointed <br>"
												+ "to the target node of an edge.", // </body></html>",
										" Copy edge ",
										JOptionPane.WARNING_MESSAGE);
						this.canvas.getGraph().deselect(a);
					}
					this.canvas.unsetPicked();
					this.canvas.setSourceObject(null);
					this.canvas.setTargetObject(null);
					this.canvas.setChanged(true);			
					this.canvas.setEditMode(EditorConstants.DRAW);
				}
				break;
			case EditorConstants.DELETE:
			case EditorConstants.INTERACT_MATCH:
			default:
				break;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (this.canvas.getGraphics() == null 
				|| this.canvas.getGraph() == null) {
			return;
		}
		
		if (this.canvas.isLeftAndRightPressed() 
				&& this.canvas.getPickedObject() != null) {
			if (this.canvas.isDragged()) {
				this.canvas.endDraggingOfObject();	
			}
			this.canvas.unsetPicked();
			this.canvas.update(this.canvas.getGraphics());
			return;
		}
		
		if (this.canvas.isScrollingByDragging()) {
			this.canvas.endScrolling();
			return;
		}

		if (SwingUtilities.isLeftMouseButton(e)) {
			if (this.canvas.getEditMode() == EditorConstants.DRAW
					&& this.canvas.canCreateNode()) {							
				this.canvas.addNode(e.getX(), e.getY());
				this.canvas.canCreateNode = false;			
			} 
			else if (this.canvas.isSelectBoxOpen() 
					&& this.canvas.getSelectBoxSize() > 0) {							
				this.canvas.selectObjectsInsideOfSelectBoxAndClose();
			}
			else if (this.canvas.isDragged()) {
				this.canvas.endDraggingOfObject();	
				this.canvas.unsetPicked();
				this.canvas.update(this.canvas.getGraphics());
			}
		} 
		
		if (this.canvas.getEditMode() == EditorConstants.ARC) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (this.canvas.isMagicEdgeSupportEnabled() 
						&& this.canvas.isMagicArc() 
						&& (this.canvas.getAnchorPoint() == null)) {
					this.canvas.makeArcByMagicArc(e.getX(), e.getY());
				} else {
					this.canvas.makeArcWithTargetAt(e.getX(), e.getY());
				}
			} else if (this.canvas.isMagicArc()) {
				this.canvas.removeMagicArc();
				this.canvas.setEditMode(EditorConstants.DRAW);				
			}
			return;
		} 
			
		if (SwingUtilities.isMiddleMouseButton(e)
				    || this.canvas.getEditMode() == EditorConstants.MOVE
				    || this.canvas.getEditMode() == EditorConstants.SELECT) {
			if (this.canvas.getPickedObject() != null) {
				if (this.canvas.isDragged()) {
					this.canvas.endDraggingOfObject();	
					this.canvas.unsetPicked();
					this.canvas.update(this.canvas.getGraphics());
				}
			} else if (this.canvas.getSelectBoxSize() > 0) {
				this.canvas.selectObjectsInsideOfSelectBoxAndClose();
				this.canvas.update(this.canvas.getGraphics());
			}
		}
	}
	
}
