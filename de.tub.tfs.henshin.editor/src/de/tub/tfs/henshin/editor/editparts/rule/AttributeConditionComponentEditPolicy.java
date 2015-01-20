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
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class ConditionComponentEditPolicy.
 * 
 * @author Angeline Warning
 */
public class AttributeConditionComponentEditPolicy extends ComponentEditPolicy
		implements EditPolicy {

	/**
	 * Create a delete command to delete a formula, only if the parent is a
	 * graph or a formula.
	 * 
	 * @param deleteRequest
	 *            A request to delete a formula.
	 * @return Command Delete command
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new SimpleDeleteEObjectCommand((EObject) getHost().getModel());
	}

}
