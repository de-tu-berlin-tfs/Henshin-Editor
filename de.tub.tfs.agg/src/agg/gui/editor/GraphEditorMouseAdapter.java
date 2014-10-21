/**
 * 
 */
package agg.gui.editor;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.Loop;
import agg.gui.AGGAppl;

/**
 * @author olga
 *
 */
public class GraphEditorMouseAdapter extends MouseAdapter {

	private GraphEditor editor;

	public GraphEditorMouseAdapter(final GraphEditor grapheditor) {
		this.editor = grapheditor;
		this.editor.addMouseListener(this);
	}

	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		if (source == this.editor.getGraphPanel().getCanvas()) {
			AGGAppl.getInstance().setCursor(this.editor.getGraphPanel().getEditCursor());
		}
	}

	public void mouseExited(MouseEvent e) {
		AGGAppl.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent e) {
//		System.out.println(">>> GraphEditor.mousePressed  ");
		if (this.editor.getGraph() == null) {
			return;
		}
		
		Object source = e.getSource();
		int x = e.getX();
		int y = e.getY();

		if ((e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e))
				&& this.editor.getGraph().isEditable()) {
			if (this.editor.getGraphPanel().getCanvas().isLeftPressed()) 
				this.editor.allowToShowPopupMenu(false);
		} else 
			
		if (SwingUtilities.isMiddleMouseButton(e)) {
			if (this.editor.getGraphPanel().getCanvas().getPickedObject(e.getX(), e.getY(),
					this.editor.getGraphics().getFontMetrics()) != null) {
				AGGAppl.getInstance().setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		} else if (SwingUtilities.isLeftMouseButton(e)) {
			if (this.editor.getGraphPanel().getCanvas().isRightPressed()) {
				this.editor.allowToShowPopupMenu(false);
			}
			
			switch (this.editor.getGraphPanel().getEditMode()) {
			case EditorConstants.DRAW:
				break;
			case EditorConstants.MOVE:
				if (source == this.editor.getGraphPanel().getCanvas() 
						&& this.editor.getGraphPanel() != null) {
					EdGraphObject ego = this.editor.getGraph().getPicked(x, y);
					if (ego != null && ego.isArc()) {
						EdArc ea = (EdArc) ego;
						if (ea.getAnchorID() == Loop.UPPER_LEFT)
							AGGAppl.getInstance().setCursor(new Cursor(
											Cursor.NW_RESIZE_CURSOR));
							else if (ea.getAnchorID() == Loop.UPPER_RIGHT)
								AGGAppl.getInstance().setCursor(new Cursor(
												Cursor.NE_RESIZE_CURSOR));
								else if (ea.getAnchorID() == Loop.BOTTOM_RIGHT)
									AGGAppl.getInstance().setCursor(new Cursor(
													Cursor.SE_RESIZE_CURSOR));
									else if (ea.getAnchorID() == Loop.BOTTOM_LEFT)
										AGGAppl.getInstance().setCursor(new Cursor(
																Cursor.SW_RESIZE_CURSOR));
										else if (ea.getAnchorID() == Loop.CENTER)
											AGGAppl.getInstance().setCursor(new Cursor(
																	Cursor.MOVE_CURSOR));
					}
				}
				break;
			case EditorConstants.SET_PARENT:
				break;
			case EditorConstants.UNSET_PARENT:
				break;
			default:
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (this.editor.getGraph() == null) {
			return;
		}
//		System.out.println(">>> GraphEditor.mouseReleased  ");
		
		if ((SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger())
				&& this.editor.getGraph().isEditable()) {
			if (this.editor.isPopupMenuAllowed())
				this.editor.showPopupMenu(e);
			
			this.editor.allowToShowPopupMenu(true);
			this.editor.getGraphPanel().getCanvas().unsetLeftAndRightPressed();			
		} 
		else if (e.getSource() == this.editor.getGraphPanel().getCanvas()) {
			if (!this.editor.getGraphPanel().getCanvas().isRightPressed())
				this.editor.getGraphPanel().getCanvas().setLeftPressed(false);
			
			switch (this.editor.getGraphPanel().getEditMode()) {
			case EditorConstants.ARC:
				this.editor.setMsg("You have just picked the source of an edge. Click on a node to get the target.");
				break;

			case EditorConstants.DRAW:
				this.editor.drawModeProc();
				AGGAppl.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				if (this.editor.getGraGraEditor() != null)
					this.editor.getGraGraEditor().setMsg(this.editor.getMsg());
				break;
			case EditorConstants.MOVE:
				AGGAppl.getInstance().setCursor(new Cursor(Cursor.MOVE_CURSOR));
				break;
			case EditorConstants.COPY_ARC:
				AGGAppl.getInstance().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
				break;
			case EditorConstants.COPY:
				/*
				if (this.editor.getGraph().getMsg().length() != 0)
					this.editor.setMsg(this.editor.getGraph().getMsg());

				if (this.editor.getGraphPanel().getLastEditMode() != EditorConstants.COPY) {
					if (this.editor.getGraGraEditor() != null) {
						this.editor.getGraGraEditor().setEditMode(this.editor.getGraGraEditor().getLastEditMode());
						this.editor.getGraGraEditor().forwardModeCommand(EditorConstants.getModeOfID(this.editor.getGraGraEditor().getLastEditMode()));	
					}
					else
						this.editor.setEditMode(this.editor.getGraphPanel().getLastEditMode());
					this.editor.setCursorOfApplFrame(this.editor.getGraphPanel().getLastEditCursor());
				} else {
					if (this.editor.getGraGraEditor() != null) {
						this.editor.getGraGraEditor().setEditMode(this.editor.getGraGraEditor().getLastEditMode());
						this.editor.getGraGraEditor().forwardModeCommand(EditorConstants.getModeOfID(this.editor.getGraGraEditor().getLastEditMode()));

					} else {
						this.editor.setEditMode(this.editor.getGraphPanel().getLastEditMode());
					}
					AGGAppl.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				if (this.editor.getGraGraEditor() != null) {
					if ((this.editor.getSourceOfCopy() != null) 
							&& !this.editor.getGraph().equals(this.editor.getSourceOfCopy())) {
						GraphPanel gp = this.editor.getGraGraEditor().getPanelOfGraph(this.editor.getSourceOfCopy());
						this.editor.getSourceOfCopy().eraseSelected(gp.getCanvas().getGraphics(), true);
						this.editor.getSourceOfCopy().deselectAll();
						this.editor.getGraGraEditor().resetAfterCopy();
						gp.repaint();
					}
					this.editor.getGraGraEditor().setMsg(this.editor.getMsg());
				}
				this.editor.getGraph().setGraphToCopy(null);
				this.editor.setSourceOfCopy(null);
				*/
				break;
			default:
				AGGAppl.getInstance().setCursor(this.editor.getGraphPanel().getEditCursor());
				break;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

}
