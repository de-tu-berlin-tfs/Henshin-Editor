/**
 * 
 */
package agg.gui.editor;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.SwingUtilities;

import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdGraphObject;
import agg.xt_basis.OrdinaryMorphism;

/**
 * @author olga
 *
 */
public class RuleEditorMouseMotionAdapter implements MouseMotionListener {

	private final RuleEditor editor;
	
	public RuleEditorMouseMotionAdapter(final RuleEditor ruleeditor) {
		this.editor = ruleeditor;
		this.editor.addMouseMotionListener(this);
	}
	
	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		// System.out.println(">>> RuleEditor.mouseDragged");
		if (this.editor.getRule() == null) {
			return;
		}
		
		if (SwingUtilities.isMiddleMouseButton(e)
				|| (this.editor.getLeftPanel().getEditMode() == EditorConstants.MOVE)) {
			if (!this.editor.isSynchronMoveOfMappedObjectsEnabled()) {
				return;
			}
			EdGraphObject go = null;
			if (this.editor.getActivePanel().getGraph() != null)
				go = this.editor.getActivePanel().getCanvas().getDraggedObject();
			if (go == null) {
				return;
			}
			Vector<EdGraphObject> v = null;
			if (go.isSelected())
				v = this.editor.getActivePanel().getGraph().getSelectedObjs();
			else {
				v = new Vector<EdGraphObject>(1);
				v.add(go);
			}
			Dimension d = this.editor.getActivePanel().getCanvas().getDraggedDimension();
			if (this.editor.getActivePanel() == this.editor.getLeftPanel()) {
				if (!v.isEmpty()) {
					// move images of RHS
					OrdinaryMorphism morph = null;
					if (this.editor.getRule() instanceof EdAtomic) {
						if (((EdAtomic) this.editor.getRule()).isParent())
							if (((EdAtomic) this.editor.getRule()).getConclusions().size() == 1)
								morph = ((EdAtomic) this.editor.getRule()).getBasisAtomic();
					} else {
						morph = this.editor.getRule().getBasisRule();
					}
					if (morph != null) {
						Vector<EdGraphObject> images = this.editor.getImages(this.editor.getRule()
								.getRight(), morph, v);
						if (!images.isEmpty()) {
							if (!this.editor.isRightDragging()) {
								this.editor.getRule().getRight().addMovedToUndo(images);
								this.editor.setRightDragging(true);
							}
							this.editor.getRule().getRight().moveObjects(images, d.width,
									d.height);
							this.editor.getRightPanel().updateGraphics();
						}
					}
					// move images of NAC
					if (this.editor.getNAC() != null) {
						Vector<EdGraphObject> images = this.editor.getImages(this.editor.getNAC(), this.editor.getNAC()
								.getMorphism(), v);
						if (!images.isEmpty()) {
							if (!this.editor.isLeftCondDragging()) {
								this.editor.getNAC().addMovedToUndo(images);
								this.editor.setLeftCondDragging(true);
							}
							this.editor.getNAC().moveObjects(images, d.width, d.height);
							this.editor.getLeftCondPanel().updateGraphics();
						}
					} 
					// move images of PAC
					if (this.editor.getPAC() != null) {
						Vector<EdGraphObject> images = this.editor.getImages(this.editor.getPAC(), this.editor.getPAC()
								.getMorphism(), v);
						if (!images.isEmpty()) {
							if (!this.editor.isLeftCondDragging()) {
								this.editor.getPAC().addMovedToUndo(images);
								this.editor.setLeftCondDragging(true);
							}
							this.editor.getPAC().moveObjects(images, d.width, d.height);
							this.editor.getLeftCondPanel().updateGraphics();
						}
					}
				}
			} else if (this.editor.getActivePanel() == this.editor.getRightPanel()) {
				if (!v.isEmpty()) {
					// move inverse images of LHS
					Vector<EdGraphObject> invImages = null;
					OrdinaryMorphism morph = null;
					if (this.editor.getRule() instanceof EdAtomic) {
						if (((EdAtomic) this.editor.getRule()).isParent()) {
							if (((EdAtomic) this.editor.getRule()).getConclusions().size() == 1)
								morph = ((EdAtomic) this.editor.getRule()).getBasisAtomic();
						}
					} else
						morph = this.editor.getRule().getBasisRule();

					if (morph != null) {
						invImages = this.editor.getInverseImages(this.editor.getRule().getLeft(), morph, v);
						if (!invImages.isEmpty()) {
							if (!this.editor.isLeftDragging()) {
								this.editor.getRule().getLeft().addMovedToUndo(invImages);
								this.editor.setLeftDragging(true);
							}
							this.editor.getRule().getLeft().moveObjects(invImages, d.width,
									d.height);
							this.editor.getLeftPanel().updateGraphics();
						}
					
						// move images of NAC
						if (this.editor.getNAC() != null) {
							Vector<EdGraphObject> imagesNAC = this.editor.getImages(this.editor.getNAC(), this.editor.getNAC()
									.getMorphism(), invImages);
							if (!imagesNAC.isEmpty()) {
								if (!this.editor.isLeftCondDragging()) {
									this.editor.getNAC().addMovedToUndo(imagesNAC);
									this.editor.setLeftCondDragging(true);
								}
								this.editor.getNAC().moveObjects(imagesNAC, d.width, d.height);
								this.editor.getLeftCondPanel().updateGraphics();
							}
						} 
						// move images of PAC
						if (this.editor.getPAC() != null) {
							Vector<EdGraphObject> imagesPAC = this.editor.getImages(this.editor.getPAC(), this.editor.getPAC()
									.getMorphism(), invImages);
							if (!imagesPAC.isEmpty()) {
								if (!this.editor.isLeftCondDragging()) {
									this.editor.getPAC().addMovedToUndo(imagesPAC);
									this.editor.setLeftCondDragging(true);
								}
								this.editor.getPAC().moveObjects(imagesPAC, d.width, d.height);
								this.editor.getLeftCondPanel().updateGraphics();
							}
						}
					}
				}
			} else if (this.editor.getActivePanel() == this.editor.getLeftCondPanel()) {
				if (!v.isEmpty()) {				
					// move images of LHS
					Vector<EdGraphObject> invImages = new Vector<EdGraphObject>(0);
					if (this.editor.getNAC() != null 
							&& this.editor.getNAC() == this.editor.getActivePanel().getGraph()) {
						invImages = this.editor.getInverseImages(this.editor.getRule()
												.getLeft(), this.editor.getNAC().getMorphism(), v);
					} else if (this.editor.getPAC() != null
							&& this.editor.getPAC() == this.editor.getActivePanel().getGraph()) {
						invImages = this.editor.getInverseImages(this.editor.getRule()
								.getLeft(), this.editor.getPAC().getMorphism(), v);
					}
					if (!invImages.isEmpty()) {
						if (!this.editor.isRightDragging()) {
							this.editor.getRule().getLeft().addMovedToUndo(invImages);
							this.editor.setRightDragging(true);
						}
						this.editor.getRule().getLeft().moveObjects(invImages, d.width,
								d.height);
						this.editor.getLeftPanel().updateGraphics();
					}

					// move images of RHS
					Vector<EdGraphObject> images = this.editor.getImages(this.editor.getRule().getRight(),
							this.editor.getRule().getBasisRule(), invImages);
					if (!images.isEmpty()) {
						if (!this.editor.isLeftDragging()) {
							this.editor.getRule().getRight().addMovedToUndo(images);
							this.editor.setLeftDragging(true);
						}
						this.editor.getRule().getRight().moveObjects(images, d.width, d.height);
						this.editor.getRightPanel().updateGraphics();
					}
				}
			}
		}
	}

}
