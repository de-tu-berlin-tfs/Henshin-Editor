/**
 * PasteRequest.java
 *
 * Created 16.01.2012 - 16:54:46
 */
package de.tub.tfs.henshin.editor.editparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;

/**
 * @author nam
 * 
 */
public class PasteRequest extends Request {

	public static interface IPasteRule {
		void preparePaste(Object o, EditPart target);
	}

	private Object pastedObject;

	/**
	 * @param pasteObject
	 */
	public PasteRequest(Object pasteObject) {
		super(HenshinRequests.REQ_PASTE);

		this.pastedObject = pasteObject;
	}

	/**
	 * @return the pasteObject
	 */
	public Object getPastedObject() {
		return pastedObject;
	}

	/**
	 * @param pasteObject
	 *            the pasteObject to set
	 */
	public void setPasteObject(Object pasteObject) {
		this.pastedObject = pasteObject;
	}

	/**
	 * @param key
	 * @param rule
	 */
	@SuppressWarnings("unchecked")
	public void addPasteRule(Object key, IPasteRule rule) {
		getExtendedData().put(key, rule);
	}

	public IPasteRule getPasteRule(Object key) {
		return (IPasteRule) getExtendedData().get(key);
	}
}
