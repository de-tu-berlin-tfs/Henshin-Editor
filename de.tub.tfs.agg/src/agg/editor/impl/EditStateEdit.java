/**
 * 
 */
package agg.editor.impl;

import java.util.Hashtable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;

/**
 * @author olga
 *
 */
@SuppressWarnings("serial")
public class EditStateEdit extends StateEdit {

	
	public EditStateEdit(StateEditable anObject) {
        super(anObject);
	}
	
	public EditStateEdit(StateEditable anObject, String name) {
		super(anObject, name);
	}
	
	public StateEditable getObject() {
		return super.object;
	}
	
	public Hashtable<Object,Object> getPreState() {
		return this.preState;
	}
}
