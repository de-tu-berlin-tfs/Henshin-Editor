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
 * CompoundActivityLayoutEditPolicy.java
 *
 * Created 09.01.2012 - 14:55:41
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.compound_activity;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.AddCompoundActivityChildCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowElementCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;

/**
 * @author nam
 * 
 */
public class CompoundActivityLayoutEditPolicy extends XYLayoutEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse
	 * .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObj = request.getNewObject();

		if (newObj instanceof Activity) {
			CompoundActivity model = (CompoundActivity) getHost().getModel();

			return new CreateFlowElementCommand<Activity>((Activity) newObj,
					model, FlowControlPackage.COMPOUND_ACTIVITY__CHILDREN,
					request.getLocation());
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand
	 * (org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	protected Command createAddCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) {

		Object childModel = child.getModel();

		if (childModel instanceof Activity) {
			AddCompoundActivityChildCommand cmd = new AddCompoundActivityChildCommand(
					(Activity) childModel, (CompoundActivity) getHost()
							.getModel());

			return cmd;
		}

		return super.createAddCommand(request, child, constraint);
	}

}
