/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
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
