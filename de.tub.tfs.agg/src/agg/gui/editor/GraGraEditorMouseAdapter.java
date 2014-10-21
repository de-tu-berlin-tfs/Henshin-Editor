/**
 * 
 */
package agg.gui.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


/**
 * @author olga
 *
 */
public class GraGraEditorMouseAdapter extends MouseAdapter {

	private GraGraEditor editor;	
	private GraphPanel activePanel;
	
	/**
	 * 
	 */
	public GraGraEditorMouseAdapter(final GraGraEditor gragraeditor) {
		this.editor = gragraeditor;
		this.editor.addMouseListener(this);
	}
	

	public void mousePressed(MouseEvent e) {
		if (this.editor.getGraGra() == null) {
			return;
		}
		
		if (e.getSource() instanceof GraphCanvas) {					
			this.activePanel = this.editor.setActivePanel(((GraphCanvas) e.getSource()).getViewport());
		} 
		else if (e.getSource() instanceof JButton
				&& ((JButton)e.getSource()).getActionCommand().equals("graphlayoutmenu")) {
			this.editor.getGraphLayoutMenu().show((JButton)e.getSource(), e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (this.editor.getGraGra() == null) {
			return;
		}
		if (e.getSource() instanceof GraphCanvas) {
			this.activePanel = this.editor.setActivePanel(((GraphCanvas) e.getSource()).getViewport());
			if (this.editor.hasAttrEditorOnTop()) {
				if (!this.editor.isLastAttrDeclValid()) 
					return;		
				this.editor.getActivePanel().getGraph().deselectAll();
				this.editor.updateUndoButtonAfterAttrEdit(this.activePanel);
				if (this.editor.getEditMode() != EditorConstants.ATTRIBUTES) 
					this.editor.resetRuleEditor();
			} else if (this.editor.hasAttrEditorOnBottom()) {
				if (!this.editor.isLastAttrDeclValid()) 
					return;		
				this.editor.getActivePanel().getGraph().deselectAll();
				this.editor.updateUndoButtonAfterAttrEdit(this.activePanel);
				if (this.editor.getEditMode() != EditorConstants.ATTRIBUTES) 
					this.editor.resetGraphEditor();
			}
//			if (this.activePanel.getEditMode() == EditorConstants.COPY_ARC) 
//				this.activePanel.setEditMode(EditorConstants.DRAW);
		}
		else if (e.getSource() instanceof JButton
				&& ((JButton)e.getSource()).getActionCommand().equals("graphlayout")) {
			this.editor.getGraphLayoutMenu().show((JButton)e.getSource(), e.getX(), e.getY());
		}
		
		if (this.editor.getUndoManager() != null && this.editor.getUndoManager().isEnabled()) {
			if (this.editor.getUndoManager().canUndo())
				this.editor.getUndoButton().setEnabled(true);
			if (this.editor.getUndoManager().canRedo())
				this.editor.getRedoButton().setEnabled(true);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (this.editor.getGraGra() == null)
			return;
		this.editor.requestFocusInWindow();
		if (e.getSource() == this.editor.getRuleEditor().getLeftPanel().getCanvas()
				|| e.getSource() == this.editor.getRuleEditor().getLeftCondPanel().getCanvas()
				|| e.getSource() == this.editor.getGraphEditor().getGraphPanel().getCanvas()
				|| e.getSource() == this.editor.getRuleEditor().getRightPanel().getCanvas()) {
			if (!this.editor.getTypeEditor().getTypePalette().isEmpty()
					&& ((e.getX() - 35) < this.editor.getRuleEditor().getRightPanel().getWidth()
							|| (e.getX() - 35) < this.editor.getGraphEditor().getWidth())) {
				if (this.editor.getTypeEditor().getTypePalette().isOpen()) {
					if (!this.editor.getTypeEditor().getNodeTypePropertyEditor()
							.isVisible()
							&& !this.editor.getTypeEditor().getArcTypePropertyEditor()
									.isVisible()) {
						if (this.editor.getDividerLocationOfEditorAndTypeEditor() > 0){
							if (!this.editor.typesAlwaysOn())								
								this.editor.getMainSplitPane().setDividerLocation(
										this.editor.getMainSplitPane().getWidth());
						}
						else {
							this.editor.setDividerLocationOfEditorAndTypeEditor(
									this.editor.getMainSplitPane().getWidth() 
										- this.editor.getMainSplitPane().getDividerLocation());
						}
					}
				}
			}
		} 
	}

	public void mouseExited(MouseEvent e) {}
	
}
