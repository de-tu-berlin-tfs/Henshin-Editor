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
 * SecondTransitionCreationTool.java
 *
 * Created 22.12.2011 - 23:09:02
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram.tools;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.ConnectionCreationTool;

/**
 * @author nam
 * 
 */
public class SecondTransitionCreationTool extends ConnectionCreationTool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.tools.AbstractConnectionCreationTool#createTargetRequest
	 * ()
	 */
	@Override
	protected Request createTargetRequest() {
		CreateRequest req = (CreateRequest) super.createTargetRequest();

		Map<String, Boolean> data = new HashMap<String, Boolean>();

		data.put("secondtransition", Boolean.TRUE); //$NON-NLS-1$

		req.setExtendedData(data);

		return req;
	}
}
