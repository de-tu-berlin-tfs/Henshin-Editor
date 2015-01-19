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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.transSys.MoveEObjectCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.AddTransformationUnitCommand;
import de.tub.tfs.henshin.editor.internal.TransformationUnitPart;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class SubUnitXYLayoutEditPolicy.
 */
public class SubUnitXYLayoutEditPolicy extends XYLayoutEditPolicy implements
		EditPolicy {

	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		// TODO Auto-generated method stub
		return super.createChangeConstraintCommand(request, child, constraint);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse
	 * .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Rule) {
			Unit tUnit = (Unit) getHost()
					.getParent().getModel();
			EStructuralFeature feature = TransformationUnitUtil
					.getSubUnitsFeature(tUnit);
			if (feature != null) {
				if (feature.isMany()) {
					int index = 0;
					if (tUnit instanceof TransformationUnitPart<?>) {
						index = ((List<?>) ((TransformationUnitPart<?>) tUnit)
								.getModel().eGet(feature)).indexOf(getHost()
								.getModel());
					} else {
						index = ((List<?>) tUnit.eGet(feature))
								.indexOf(getHost().getModel());
					}
					command = new AddTransformationUnitCommand(tUnit,
							(Unit) request.getNewObject(), index);
				}
			}
		}
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getAddCommand
	 * (org.eclipse.gef.Request)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Command getAddCommand(Request generic) {
		if (getHost().getParent() instanceof TransformationUnitEditPart<?>) {
			ChangeBoundsRequest req = (ChangeBoundsRequest) generic;
			List<?> editparts = req.getEditParts();
			if (editparts.size() == 1) {
				EList<EObject> list = null;
				Unit tUnit = (Unit) getHost()
						.getParent().getModel();
				EStructuralFeature feature = TransformationUnitUtil
						.getSubUnitsFeature(tUnit);
				if (feature != null) {
					if (feature.isMany()) {
						list = (EList<EObject>) tUnit.eGet(feature);
						return new MoveEObjectCommand(list,
								list.indexOf(((EditPart) editparts.get(0))
										.getModel()), list.indexOf(getHost()
										.getModel()));
					}
				}
			}
		}
		return null;
	}

}
