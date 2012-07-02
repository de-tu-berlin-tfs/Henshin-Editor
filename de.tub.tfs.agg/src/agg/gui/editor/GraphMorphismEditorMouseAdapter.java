/**
 * 
 */
package agg.gui.editor;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import agg.editor.impl.EdGraphObject;

/**
 * @author olga
 *
 */
public class GraphMorphismEditorMouseAdapter extends MouseAdapter {

	private final GraphMorphismEditor editor;
	private EdGraphObject leftObj;
	private EdGraphObject rightObj;

	
	public GraphMorphismEditorMouseAdapter(final GraphMorphismEditor anEditor) {
		this.editor = anEditor;
		this.editor.addMouseListener(this);
	}

	public void mousePressed(MouseEvent e) {
//		System.out.println(">>> GraphMorphismEditorMouseAdapter.mousePressed "+e.getSource()+editor.getRule().isEditable());
		if (this.editor.getLeftGraph() == null
				|| this.editor.getRightGraph() == null) {
			return;
		}
		
//		Object source = e.getSource();
//		if (editor.setActivePanel(source) == null) {
//			return;
//		}
//		
//		int x = e.getX();
//		int y = e.getY();
//
//		if (SwingUtilities.isLeftMouseButton(e)
//				&& editor.getLeftPanel().getEditMode() == EditorConstants.MAP) {
//			
//			// set left object of mapping
//			if (source == editor.getLeftPanel().getCanvas()) {
//					leftObj = editor.getLeftPanel().getGraph().getPicked(x, y);
//			}
//			// set right object of mapping
//			else if (source == editor.getRightPanel().getCanvas()) {
//				rightObj = editor.getRightPanel().getGraph().getPicked(x, y);
////				System.out.println("left obj::  "+editor.leftObj+"   right obj::  "+editor.rightObj);
//				if (rightObj != null) {						
//					if (editor.setMapping(leftObj.getBasisObject(), rightObj.getBasisObject())) {
//						editor.updateGraphics();							
//					}					
//				} else {
//					// unmap 
//					editor.removeMapping(leftObj.getBasisObject(), true);
//				}
//			}
//		}
	}

	public void mouseReleased(MouseEvent e) {
		Object source = e.getSource();
		if (this.editor.getLeftGraph() == null
				|| this.editor.getRightGraph() == null
				|| this.editor.setActivePanel(source) == null) {
			return;
		}
		
//		System.out.println(">>> GraphMorphismEditorMouseAdapter.mouseReleased "
//				+this.editor.getActivePanel().getGraph().getName());
		
		int x = e.getX();
		int y = e.getY();
		
		if (SwingUtilities.isLeftMouseButton(e)
			&& this.editor.getLeftPanel().getEditMode() == EditorConstants.MAP) {
				
			if (source == this.editor.getLeftPanel().getCanvas()) {
				this.leftObj = this.editor.getLeftPanel().getGraph().getPicked(x, y);
			}
			else if (source == this.editor.getRightPanel().getCanvas()) {
				this.rightObj = this.editor.getRightPanel().getGraph().getPicked(x, y);
//					System.out.println("left obj::  "+leftObj+"   right obj::  "+rightObj);
				if (this.rightObj != null && this.leftObj != null) {
					if (this.editor.addMapping(this.leftObj.getBasisObject(), this.rightObj.getBasisObject())) {
						this.editor.updateGraphs();
						this.editor.updateGraphics();							
					}					
				} else if (this.leftObj != null) {
					// unmap 
					this.editor.removeMapping(this.leftObj.getBasisObject());
					this.editor.updateGraphs();
					this.editor.updateGraphics();
				}
			}			
		}
	}

	
}
