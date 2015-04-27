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
 * SetFlowDiagramOutPutParameterAction.java
 *
 * Created 24.01.2012 - 19:44:32
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowDiagramParameterCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteFlowDiagramParameterCommand;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;

/**
 * @author nam
 * 
 */
public class SetFlowDiagramOutputParameterAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.SetFlowDiagramOutPutParameterAction";

	private FlowDiagram diagram;

	private Parameter model;

	private boolean isSet;

	/**
	 * @param part
	 */
	public SetFlowDiagramOutputParameterAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand cmd = new CompoundCommand("Set Parameter State");

		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getTarget() == model || m.getSrc() == model) {
				Parameter other = m.getTarget() == model ? m.getSrc() : m
						.getTarget();

				if (other.getProvider() == diagram) {
					cmd.add(new DeleteFlowDiagramParameterCommand(other));
				}
			}
		}

		if (isSet) {
			cmd.add(new CreateFlowDiagramParameterCommand(diagram, model, false));
		}

		execute(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		isSet = false;

		if (selection.size() == 1) {
			Object selected = selection.get(0);

			if (selected instanceof EditPart) {
				Object model = ((EditPart) selected).getModel();

				if (model instanceof Parameter) {
					diagram = ((Activity) ((Parameter) model).getProvider())
							.getDiagram();
					this.model = (Parameter) model;

					if (this.model.isOutPut()) {
						setText("Unset as Output Parameter");
						setImageDescriptor(ResourceUtil.ICONS.DELTE_OUTPUT_PARAMETER
								.descr(16));

					} else {
						setText("Set as Output Parameter");
						setImageDescriptor(ResourceUtil.ICONS.OUTPUT_PARAMETER
								.descr(16));

						isSet = true;
					}

					return true;
				}
			}
		}

		return false;
	}
}
