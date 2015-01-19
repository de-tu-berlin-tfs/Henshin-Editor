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
 * DeleteCompoundActivityCommand.java
 *
 * Created 28.12.2011 - 03:16:43
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;

/**
 * @author nam
 * 
 */
public class DeleteCompoundActivityCommand extends CompoundCommand {
	/**
     * 
     */
	public DeleteCompoundActivityCommand(CompoundActivity model) {
		super();

		for (Activity child : model.getChildren()) {
			if (child instanceof CompoundActivity) {
				add(new DeleteCompoundActivityCommand((CompoundActivity) child));
			} else {
				add(new DeleteFlowElementCommand(child));
			}
		}

		add(new DeleteFlowElementCommand(model));
	}
}
