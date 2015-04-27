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
 * FlowElementGraphicalEditPolicy.java
 * 
 * Created 
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import java.util.Map;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateTransitionCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteTransitionCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class FlowElementGraphicalEditPolicy extends GraphicalNodeEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCompleteCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		CompoundCommand cmd = new CompoundCommand("Creating Transition");
		CreateTransitionCommand createCmd = (CreateTransitionCommand) request
				.getStartCommand();

		if (createCmd == null) {
			return null;
		}

		FlowElement src = createCmd.getSrc();
		FlowElement target = (FlowElement) getHost().getModel();

		if (target instanceof Start) {
			return null;
		}

		if (target instanceof Activity) {
			if (((Activity) target).isNested()) {
				return null;
			}
		}

		Map<?, ?> data = request.getExtendedData();
		EStructuralFeature outFeature = Boolean.TRUE.equals(data
				.get("secondtransition")) ? FlowControlPackage.Literals.CONDITIONAL_ELEMENT__ALT_OUT
				: FlowControlPackage.Literals.FLOW_ELEMENT__OUT;

		Transition srcOut = (Transition) src.eGet(outFeature);

		createCmd.setTarget(target);
		createCmd.setOutGoingFeature(outFeature);

		if (srcOut != null) {
			if (srcOut.getNext() == target) {
				return null;
			}

			cmd.add(new DeleteTransitionCommand(srcOut));

			if (target.getOut() == null && !(target instanceof End)) {
				cmd.add(new CreateTransitionCommand(target, srcOut.getNext(),
						(FlowDiagram) src.eContainer()));
			}
		}

		cmd.add(createCmd);

		return cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCreateCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		Object newObj = request.getNewObject();

		if (newObj instanceof Transition) {
			FlowElement src = (FlowElement) getHost().getModel();

			if (src instanceof End) {
				return null;
			}

			if (src instanceof Activity) {
				if (((Activity) src).isNested()) {
					return null;
				}
			}

			Map<?, ?> data = request.getExtendedData();

			if (Boolean.TRUE.equals(data.get("secondtransition"))
					&& !(src instanceof ConditionalElement)) {
				return null;
			}

			CreateTransitionCommand cmd = new CreateTransitionCommand(
					(Transition) request.getNewObject(), src, null,
					(FlowDiagram) src.eContainer());

			request.setStartCommand(cmd);

			return cmd;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
