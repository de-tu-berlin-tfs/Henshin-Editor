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
package de.tub.tfs.henshin.editor.editparts.condition;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.rule.DeleteFormulaCommand;

/**
 * The Class ApplicationConditionComponentEditPolicy.
 * 
 * @author Angeline Warning
 */
public class ApplicationConditionComponentEditPolicy extends
		ComponentEditPolicy implements EditPolicy {

	/**
	 * Creates a command to delete a NC's conclusion and all the nodes in it.
	 * 
	 * @param deleteRequest
	 *            A request to delete a formula.
	 * @return Command Command to delete a NC's conclusion and all the nodes in
	 *         it.
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Graph conclusion = (Graph) getHost().getModel();
		NestedCondition ac = (NestedCondition) conclusion.eContainer();
		EObject parent = ac.eContainer();
		if (parent instanceof Graph || parent instanceof Formula) {
			return new DeleteFormulaCommand(ac, parent);
		}

		return null;
	}

}
