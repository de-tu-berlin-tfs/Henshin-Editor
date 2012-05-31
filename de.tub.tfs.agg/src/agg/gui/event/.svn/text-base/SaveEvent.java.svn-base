package agg.gui.event;

import java.awt.Component;
import java.util.EventObject;

import agg.editor.impl.EdGraGra;
import agg.gui.saveload.GraGraSave;

/**
 * A SaveEvent is used to notify the saving state of the saved gragra or errors.
 * 
 * @author olga
 * @version $ID:$
 */
public class SaveEvent extends EventObject {

	public static final int SAVE = 0;

	public static final int PROGRESS_BEGIN = 1;

	public static final int PROGRESS_FINISHED = 2;

	public static final int EMPTY_ERROR = 3;

	public static final int IO_ERROR = 4;

	public static final int CLOSE_ERROR = 5;

	public static final int SAVED = 6;

	public SaveEvent(Object source, int key, String fileName) {
		super(source);
		if (source instanceof GraGraSave)
			this.gragra = ((GraGraSave) source).getGraGra();
		this.msgkey = key;
		this.name = fileName;
	}

	public SaveEvent(Object source, int key, Object obj, String addMsg) {
		this(source, key, "");
		// System.out.println("SaveEvent : msgkey: "+msgkey);
		if (obj instanceof String) {
			this.name = (String) obj;
			// System.out.println("File: "+name);
		} else if (obj instanceof Component) {
			this.component = (Component) obj;
			// System.out.println("ProgressBar: "+component);
		} else {
			this.name = "";
			this.component = null;
		}
		this.msg1 = addMsg;
	}

	public String getFileName() {
		return this.name;
	}

	public int getMsg() {
		return this.msgkey;
	}

	public String getMessage() {
		if (this.msgkey == SAVE)
			this.msg = "Please wait. Saving ....";
		else if (this.msgkey == SAVED)
			this.msg = "GraGra is saved to file  " + this.name + ".";
		else if (this.msgkey == PROGRESS_BEGIN)
			;
		else if (this.msgkey == PROGRESS_FINISHED)
			;
		else if (this.msgkey == EMPTY_ERROR)
			this.msg = "Empty file name. Saving is failed or canceled.";
		else if (this.msgkey == IO_ERROR)
			this.msg = "Error occured while saving file  " + this.name + "  " + this.msg1
					+ ".";
		else if (this.msgkey == CLOSE_ERROR)
			this.msg = "Error occured while closing file  " + this.name + ".";
		else
			this.msg = this.msg1;
		return this.msg;
	}

	public Component getUsedComponent() {
		return this.component;
	}

	public EdGraGra getGraGra() {
		return this.gragra;
	}

	private int msgkey;

	private String msg;

	private String msg1;

	private String name;

	private EdGraGra gragra;

	private Component component;

}
