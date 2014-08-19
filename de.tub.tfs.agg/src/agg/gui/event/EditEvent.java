package agg.gui.event;

import java.util.EventObject;

/**
 * An EditEvent is used to notify the edit state of the GraGraEditor.
 * 
 * @author olga
 * @version $ID:$
 */
public class EditEvent extends EventObject {

	public static final int EDIT_PROCEDURE = -1;

	public static final int INPUT_PARAMETER = -2;

	public static final int INPUT_PARAMETER_OK = -3;

	public static final int EDIT_FONT_STYLE = -4;

	public static final int EDIT_FONT_SIZE = -5;

	public static final int EDIT_SCALE = -6;

	public static final int NO_EDIT_PROCEDURE = -7;

	public static final int ATTR_CONDITION_CHANGED = -8;

	public static final int DATA_LOADED = -9;

	public static final int MENU_KEY = -10;

	public static final int TRANSFER_SHORTKEY = -11;
	
	public static final int RESET_GRAPH = -12;
	
	public static final int SET_TYPE_GRAPH_ENABLED = -13;
	
	public static final int DELETE_RULE_REQUEST = -14;
	
	public static final int SHOW_RULE_SEQUENCE = -15;
	
	public static final int HIDE_RULE_SEQUENCE = -16;
	
	
	public EditEvent(Object source, int key) {
		super(source);
		this.msgkey = key;
	}

	public EditEvent(Object source, int key, String msg) {
		this(source, key);
		this.userMsg = msg;
	}

	public EditEvent(Object source, int key, int value) {
		this(source, key);
		if ((key == EditEvent.EDIT_FONT_STYLE)
				|| (key == EditEvent.EDIT_FONT_SIZE))
			this.intValue = value;
		else
			this.intValue = 0;
	}

	public EditEvent(Object source, int key, double value) {
		this(source, key);
		if (key == EditEvent.EDIT_SCALE)
			this.doubleValue = value;
		else
			this.doubleValue = 1.0;
	}

	public EditEvent(Object source, int key, Object obj) {
		this(source, key);
		this.object = obj;
	}

	public EditEvent(Object source, int key, Object obj, String msg) {
		this(source, key);
		this.object = obj;
		this.userMsg = msg;
	}

	public int getMsg() {
		return this.msgkey;
	}

	public String getMessage() {
		if (this.msgkey == agg.gui.editor.EditorConstants.DRAW) {
			this.msg = "Click on the background to get a node;  on a source node and a target node to get an edge.";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.SELECT) {
			this.msg = "Click on an object to select / deselect it.";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.MOVE) {
			this.msg = "Press and drag the button when the cursor points to an object.";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.ATTRIBUTES) {
			this.msg = "Click on an object to get the attribute editor or to put the object to the attribute editor if it is already open.  Click on a leaf in the tree view to hide the attribute editor. ";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.MAP) {
			this.msg = "Click on a source object and a target object to get a mapping.";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.UNMAP) {
			this.msg = "Click on the source or target of the mapping to destroy it.";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.INTERACT_MATCH) {
			this.msg = "Click on a source object and a target object to get a match mapping.";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.VIEW) {
			this.msg = "Editing of graphs is not possible.";
		} else if (this.msgkey == EDIT_PROCEDURE) {
			this.msg = this.userMsg;
		} else if (this.msgkey == NO_EDIT_PROCEDURE) {
			this.msg = this.userMsg;
		} else if (this.msgkey == MENU_KEY) {
			this.msg = this.userMsg;
		} else if (this.msgkey == SET_TYPE_GRAPH_ENABLED) {
			this.msg = this.userMsg;
		} else {
			this.msg = "";
		}
		return this.msg;
	}

	public String getMode() {
		if (this.msgkey == agg.gui.editor.EditorConstants.DRAW) {
			this.modeStr = "Draw";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.SELECT) {
			this.modeStr = "Select";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.MOVE) {
			this.modeStr = "Move";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.ATTRIBUTES) {
			this.modeStr = "Attributes";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.MAP) {
			this.modeStr = "Map";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.UNMAP) {
			this.modeStr = "Unmap";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.INTERACT_MATCH) {
			this.modeStr = "Match";
		} else if (this.msgkey == agg.gui.editor.EditorConstants.VIEW) {
			this.modeStr = "View";
		} else {
			this.modeStr = "undefined";
		}
		return this.modeStr;
	}

	public Object getObject() {
		return this.object;
	}

	public int getIntValue() {
		return this.intValue;
	}

	public double getDoubleValue() {
		return this.doubleValue;
	}

	private int msgkey;

	private String msg;

	private String userMsg;

	private Object object;

	private String modeStr;

	private int intValue;

	private double doubleValue;

}
