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
package de.tub.tfs.henshin.editor.editparts.flow_diagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteCompoundActivityCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteFlowElementCommand;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class FlowDiagramComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object model = getHost().getModel();

		if (model instanceof FlowDiagram) {
			FlowDiagram castedModel = (FlowDiagram) model;
			CompoundCommand cmd = new CompoundCommand("Deleting Flow Diagram");

			// deletes elements with layout.
			for (FlowElement e : castedModel.getElements()) {
				if (e instanceof CompoundActivity) {
					cmd.add(new DeleteCompoundActivityCommand(
							(CompoundActivity) e));
				} else {
					cmd.add(new DeleteFlowElementCommand(e));
				}
			}

			cmd.add(new SimpleDeleteEObjectCommand(castedModel));

			return cmd;
		}

		return super.createDeleteCommand(deleteRequest);
	}
}
