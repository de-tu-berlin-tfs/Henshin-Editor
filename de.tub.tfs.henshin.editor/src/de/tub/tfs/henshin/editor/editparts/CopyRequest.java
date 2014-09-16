/**
 * CopyRequest.java
 *
 * Created 21.01.2012 - 11:36:54
 */
package de.tub.tfs.henshin.editor.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Request;

/**
 * @author nam
 * 
 */
public class CopyRequest extends Request {

	private List<Object> contents;

	/**
     * 
     */
	public CopyRequest() {
		super(HenshinRequests.REQ_COPY);

		contents = new ArrayList<Object>();
	}

	/**
	 * @return the contents
	 */
	public List<Object> getContents() {
		return contents;
	}
}
