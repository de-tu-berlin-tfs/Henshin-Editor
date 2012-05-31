package agg.gui.event;

import java.util.EventObject;

/**
 * A TypeEvent is used to notify the type edit state of the TypeEditor.
 * 
 * @author olga
 * @version $ID:$
 */
public class TypeEvent extends EventObject {

	public static final int CREATED = 0;

	public static final int NODE_CREATED = 1;

	public static final int ARC_CREATED = 2;

	public static final int CHANGED = 3;

	public static final int NODE_CHANGED = 4;

	public static final int ARC_CHANGED = 5;

	public static final int DELETED = 6;

	public static final int NODE_DELETED = 7;

	public static final int ARC_DELETED = 8;

	public static final int ERROR = 9;

	public static final int UPDATE = 10;

	public static final int REFRESH = 11;

	public static final int MODIFIED_CHANGED = 12;

	public static final int MODIFIED_CREATED = 13;

	public static final int MODIFIED_DELETED = 14;

	public static final int SELECTED = 15;

	public static final int SELECTED_NODE_TYPE = 151;
	
	public static final int SELECTED_ARC_TYPE = 152;
	
	public static final int TYPE_CREATED = 16;

	public static final int TYPE_ANIMATED_CHANGED = 17;
	
	public TypeEvent(Object source, int key) {
		super(source);
		this.msgkey = key;
		this.obj = null;
	}

	public TypeEvent(Object source, Object object, int key) {
		super(source);
		this.obj = object;
		this.msgkey = key;
	}

	public TypeEvent(Object source, Object object, int indx, int key) {
		super(source);
		this.obj = object;
		this.index = indx;
		this.msgkey = key;
	}

	public int getMsg() {
		return this.msgkey;
	}

	public Object getUsedObject() {
		if (this.obj == null)
			return this.src;
		
		return this.obj;
	}

	public int getIndexOfObject() {
		return this.index;
	}

	private int msgkey;

	private Object src;

	private Object obj;

	private int index = -1;

}
