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
 * RuleDirectEditPolicy.java
 *
 * Created 24.12.2011 - 18:02:38
 */
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

import de.tub.tfs.muvitor.gef.directedit.MuvitorTreeDirectEditPolicy;

/**
 * @author nam
 * 
 */
public class RuleTreeDirectEditPolicy extends MuvitorTreeDirectEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.directedit.MuvitorDirectEditPolicy#
	 * getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(DirectEditRequest edit) {
		// return super.getDirectEditCommand(edit);

		return null;
	}
}
