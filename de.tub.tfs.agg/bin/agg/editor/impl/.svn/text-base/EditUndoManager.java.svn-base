package agg.editor.impl;

import java.util.Vector;
import javax.swing.undo.*;


public class EditUndoManager extends UndoManager {

	public static final String CREATE_DELETE = "CREATE_DELETE";

	public static final String DELETE_CREATE = "DELETE_CREATE";
	
	public static final String EMBEDDING_LEFT_DELETE_CREATE = "EMBEDDING_LEFT_DELETE_CREATE";
	
	public static final String EMBEDDING_RIGHT_DELETE_CREATE = "EMBEDDING_RIGHT_DELETE_CREATE";

	public static final String COMMON_DELETE_CREATE = "COMMON_DELETE_CREATE";

	public static final String SELECT_DESELECT = "SELECT_DESELECT";

	public static final String DESELECT_SELECT = "DESELECT_SELECT";

	public static final String CHANGE_ATTRIBUTE = "CHANGE_ATTRIBUTE";

	public static final String MOVE_GOBACK = "MOVE_GOBACK";

	public static final String CHANGE_MULTIPLICITY = "CHANGE_MULTIPLICITY";

	public static final String CHANGE_PARENT = "CHANGE_PARENT";

	public static final String CHANGE_TYPE = "CHANGE_TYPE";

	public static final String CHANGE = "CHANGE";

	public static final String SOURCE_UNSET_SET = "SOURCE_UNSET_SET";
	public static final String SOURCE_SET_UNSET = "SOURCE_SET_UNSET";
	
	public static final String TARGET_UNSET_SET = "TARGET_UNSET_SET";
	public static final String TARGET_SET_UNSET = "TARGET_SET_UNSET";
	
	public static final String MAPPING_CREATE_DELETE = "MAPPING_CREATE_DELETE";

	public static final String MAPPING_DELETE_CREATE = "MAPPING_DELETE_CREATE";

	public static final String NAC_MAPPING_CREATE_DELETE = "NAC_MAPPING_CREATE_DELETE";

	public static final String NAC_MAPPING_DELETE_CREATE = "NAC_MAPPING_DELETE_CREATE";

	public static final String PAC_MAPPING_CREATE_DELETE = "PAC_MAPPING_CREATE_DELETE";

	public static final String PAC_MAPPING_DELETE_CREATE = "PAC_MAPPING_DELETE_CREATE";
	
	public static final String AC_MAPPING_CREATE_DELETE = "AC_MAPPING_CREATE_DELETE";

	public static final String AC_MAPPING_DELETE_CREATE = "AC_MAPPING_DELETE_CREATE";

	public static final String MATCH_MAPPING_CREATE_DELETE = "MATCH_MAPPING_CREATE_DELETE";

	public static final String MATCH_MAPPING_DELETE_CREATE = "MATCH_MAPPING_DELETE_CREATE";

	public static final String MATCH_COMPLETION_MAPPING_CREATE_DELETE = "MATCH_COMPLETION_MAPPING_CREATE_DELETE";

	public static final String MATCH_COMPLETION_MAPPING_DELETE_CREATE = "MATCH_COMPLETION_MAPPING_DELETE_CREATE";

	
	protected String presentationName;

	protected int undoStateID = -1;

	protected StateEdit edit;

	protected Vector<Integer> undoEndOfTransStep;

	protected boolean allowUndoEndOfTransStep;

	protected boolean enabled = true;
	
	
	public EditUndoManager(final String presentationName) {
		super();
		this.enabled = true;
		this.presentationName = presentationName;
		this.undoEndOfTransStep = new Vector<Integer>();
		this.undoStateID = -1;
	}

	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public String getPresentationName() {
		return this.presentationName;
	}

	public void setPresentationName(final String name) {
		this.presentationName = name;
	}

	public int getUndoStateID() {
		return this.undoStateID;
	}

	// inherited and rewritten methods

	/**
	 * Overridden to preserve usual semantics: returns true if an undo operation
	 * would be successful now, false otherwise
	 */
	public synchronized boolean canUndo() {
		if (this.enabled) {
			boolean res = super.canUndo();
			if (!res)
				this.undoStateID = -1;
			return res;
		} 
		return false;
	}

	/**
	 * Overridden to preserve usual semantics: returns true if an undo operation
	 * was successful now, false otherwise
	 */
	public boolean addEdit(final UndoableEdit anEdit) {
		if (anEdit == null || !this.enabled) {
			return false;
		}		

		if (super.addEdit(anEdit)) {
			this.undoStateID++;
			return true;
		} 
		return false;
	}
	
	public void undo() throws CannotUndoException {
		if (this.enabled) {
			this.edit = (StateEdit) super.editToBeUndone();
			super.undo();
			this.undoStateID--;
		}
	}

	public void end(StateEdit anEdit) {
		if (this.enabled && super.edits.contains(anEdit)) {
			anEdit.end();
		}
	}

	public synchronized boolean canRedo() {
		if (this.enabled && this.edit != null && this.edit.canRedo()) {
			return true;
		} 
		return false;
	}

	public void redo() throws CannotRedoException {
		if (this.enabled && this.edit != null && this.edit.canRedo()) {
			this.edit.redo();
			this.edits.remove(this.edit);
			addEdit(this.edit);
		}
	}

	/**
	 * Empty the undo manager, each edit will die.
	 */
	public synchronized void discardAllEdits() {
		if (this.enabled) {
			super.discardAllEdits();
			this.edit = null;
			this.undoStateID = -1;
		}
	}

	public void lastEditDie() {
		StateEdit lastedit = (StateEdit) super.lastEdit();
		if (lastedit != null) {
			super.trimEdits(this.edits.size() - 1, this.edits.size() - 1);
		}
	}

	public boolean isEmpty() {
		if (super.edits.isEmpty()) {
			this.undoStateID = -1;
			return true;
		} 
		return false;
	}
	
	public void setUndoEndOfTransformStepAllowed(final boolean b) {
		this.allowUndoEndOfTransStep = b;
	}

	public boolean isUndoEndOfTransformStepAllowed() {
		return this.allowUndoEndOfTransStep;
	}

	public void setUndoEndOfTransformStep() {		
		if (!this.undoEndOfTransStep.contains(new Integer(this.undoStateID + 1)))
			this.undoEndOfTransStep.add(new Integer(this.undoStateID + 1));		
	}

	public int getUndoEndOfTransformStep() {
		if (this.undoEndOfTransStep.size() == 0) {			
			return -1;
		}

		while (this.undoEndOfTransStep.lastElement().intValue() > this.undoStateID) {
			this.undoEndOfTransStep.remove(this.undoEndOfTransStep.lastElement());
		}
		int last = this.undoEndOfTransStep.lastElement().intValue();		
		this.undoEndOfTransStep.remove(this.undoEndOfTransStep.lastElement());
		return last;
	}

	
}
