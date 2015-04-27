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
package de.tub.tfs.henshin.editor.editparts.transformation_unit;

import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.RemoveTransformationUnitCommand;

/**
 * The Class TransformationUnitAsSubUnitComponentEditPolicy.
 */
public class TransformationUnitAsSubUnitComponentEditPolicy extends
		ComponentEditPolicy {

	/** The transformation unit. */
	final private Unit transformationUnit;

	/**
	 * Instantiates a new transformation unit as sub unit component edit policy.
	 * 
	 * @param transformationUnit
	 *            the transformation unit
	 */
	public TransformationUnitAsSubUnitComponentEditPolicy(
			Unit transformationUnit) {
		super();
		this.transformationUnit = transformationUnit;
	}

	/**
	 * If the model to delete is a rule and this rule as a kernel of an
	 * amalgamation unit and this unit has multi rule(s) then deactivate the
	 * delete option.
	 * 
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		final Unit modelToDelete = (Unit) getHost()
				.getModel();

		return new RemoveTransformationUnitCommand(transformationUnit,
				modelToDelete);
	}

}
