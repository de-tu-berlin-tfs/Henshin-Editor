/**
 * 
 */
package agg.gui.editor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import agg.gui.event.EditEvent;

/**
 * @author olga
 *
 */
public class GraGraEditorKeyAdapter extends KeyAdapter {

	private GraGraEditor editor;	
	
	/**
	 * 
	 */
	public GraGraEditorKeyAdapter(final GraGraEditor graGraEditor) {
		this.editor = graGraEditor;
		this.editor.addKeyListener(this);
	}

	public void keyReleased(KeyEvent e) {
		if (!performShortKeyEvent(e))
			transferShortKeyToTreeView(e);
	}
	
	public boolean performShortKeyEvent(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (this.editor.isTransformationRunning()) {
			if (e.isShiftDown() && e.isControlDown()) {
				final String typedKey = KeyEvent.getKeyText(keyCode);
				if (typedKey.equals("Q"))
					this.editor.getStopButton().doClick();
			} else {
				this.editor.refreshAfterWaitBeforeApplyRule();
				this.editor.setSleep(false);
			}
			return true;
		}

		if (e.isControlDown() && e.isAltDown()) {
			final String typedKey = KeyEvent.getKeyText(keyCode);
			if (typedKey.equals("N"))
				this.editor.selectNodeTypeProc();
			else if (typedKey.equals("E"))
				this.editor.selectArcTypeProc();
			else if (typedKey.equals("S"))
				this.editor.selectAllProc();
			else if (typedKey.equals("U"))
				this.editor.deselectAllProc();
			else
				return false;
		} else if (e.isControlDown() && e.isShiftDown()) {
			final String typedKey = KeyEvent.getKeyText(keyCode);
			if (typedKey.equals("M"))
				this.editor.getMatchButton().doClick();
			else if (typedKey.equals("C"))
				this.editor.getMatchCompletionButton().doClick();
			else if (typedKey.equals("S"))
				this.editor.getStepButton().doClick();
			else if (typedKey.equals("T"))
				this.editor.getStartButton().doClick();
			else if (typedKey.equals("U"))
				this.editor.getUndoButton().doClick();
			else if (typedKey.equals("R"))
				this.editor.doIdenticRule();
			else if (typedKey.equals("N"))
				this.editor.doIdenticNAC();
			else
				return false;
		} else if (e.isShiftDown() && e.isAltDown()) {
			final String typedKey = KeyEvent.getKeyText(keyCode);
			if (typedKey.equals("P"))
				this.editor.fireEditEvent(new EditEvent(this, EditEvent.MENU_KEY,
						"Parser Open"));
			else
				return false;
		} else if (e.isShiftDown()) {
			final String typedKey = KeyEvent.getKeyText(keyCode);
			if (typedKey.equals("D")) {
				this.editor.forwardModeCommand("Draw");
				this.editor.setEditMode(EditorConstants.DRAW);
			} else if (typedKey.equals("S")) {
				this.editor.forwardModeCommand("Select");
				this.editor.setEditMode(EditorConstants.SELECT);
			} else if (typedKey.equals("M")) {
				this.editor.forwardModeCommand("Move");
				this.editor.setEditMode(EditorConstants.MOVE);
			} else if (typedKey.equals("A")) {
				this.editor.forwardModeCommand("Attributes");
				this.editor.setEditMode(EditorConstants.ATTRIBUTES);
			}
			else
				return false;
		} else if (e.isControlDown()) {
			final String typedKey = KeyEvent.getKeyText(keyCode);
			// System.out.println("ControlDown:: typedKey: "+typedKey);
			if (typedKey.equals("Z"))
				this.editor.getUndoButton().doClick();
			else if (typedKey.equals("Y"))
				this.editor.getRedoButton().doClick();
			else if (typedKey.equals("A"))
				this.editor.attrsProc();
			else if (typedKey.equals("D"))
				this.editor.deleteProc();
			else if (typedKey.equals("C"))
				this.editor.copyProc();
			else if (typedKey.equals("V"))
				this.editor.pasteProc();
			else if (typedKey.equals("E"))
				this.editor.doStraightenArcsProc();
			else if (typedKey.equals("M")) {
				this.editor.forwardModeCommand("Map");
				this.editor.setEditMode(EditorConstants.MAP);
			} else if (typedKey.equals("U")) {
				this.editor.forwardModeCommand("Unmap");
				this.editor.setEditMode(EditorConstants.UNMAP);
			}
			else
				return false;
		} else if (e.isAltDown()) {
			return false;
		} else {
			final String typedKey = (String.valueOf(e.getKeyChar()))
					.toUpperCase();
			if (typedKey.equals("F"))
				this.editor.fireEditEvent(new EditEvent(this, EditEvent.MENU_KEY, "File"));
			else if (typedKey.equals("E"))
				this.editor.getEditMenu().doClick();
			else if (typedKey.equals("M"))
				this.editor.getModeMenu().doClick();
			else if (typedKey.equals("T"))
				this.editor.getTransformMenu().doClick();
			else if (typedKey.equals("A"))
				this.editor.fireEditEvent(new EditEvent(this, EditEvent.MENU_KEY,
						"Analyzer"));
			else if (typedKey.equals("P"))
				this.editor.fireEditEvent(new EditEvent(this, EditEvent.MENU_KEY,
						"Preferences"));
			else if (typedKey.equals("H"))
				this.editor.fireEditEvent(new EditEvent(this, EditEvent.MENU_KEY, "Help"));
			else if (KeyEvent.getKeyText(keyCode).equals("Delete")
					|| KeyEvent.getKeyText(keyCode).equals("Entf")) {
				if (!this.editor.deleteProc())
					this.editor.fireEditEvent(new EditEvent(this, EditEvent.MENU_KEY,
							"Delete"));
			} else
				return false;
		}
		return true;
	}

	void transferShortKeyToTreeView(KeyEvent e) {
		this.editor.fireEditEvent(new EditEvent(this, EditEvent.TRANSFER_SHORTKEY, e));
	}
	
}
