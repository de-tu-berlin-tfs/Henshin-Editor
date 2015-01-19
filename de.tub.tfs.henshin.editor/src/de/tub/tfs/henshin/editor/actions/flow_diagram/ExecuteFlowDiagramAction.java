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
 * ExecuteFlowDiagramAction.java
 *
 * Created 31.12.2011 - 13:26:46
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.ExecuteTransformationUnitCommand;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.editor.ui.dialog.NamedElementLabelProvider;
import de.tub.tfs.henshin.editor.ui.dialog.ParemetersValueDialog;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlInterpreter;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.editor.util.validator.ExpressionValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;

/**
 * An {@link Action action} to execute {@link FlowDiagram flow diagrams} on
 * instance {@link Graph graphs}.
 * 
 * @author nam
 * 
 */
public class ExecuteFlowDiagramAction extends SelectionAction {

	/**
	 * The unique ID of this {@link Action action}.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.ExecuteFlowDiagramAction"; //$NON-NLS-1$

	private static final String TEXT = "Execute Flow Diagram";

	private static final String TEXT_SIMPLE = "Execute";

	private static final String DESC = "Execute Flow Diagram on an Instance Graph";

	private static final String TOOLTIP = "Execute Flow Diagram on an Instance Graph";

	private static final ImageDescriptor ICON = ResourceUtil.ICONS.PLAY
			.descr(16);

	private FlowDiagram model;

	private Graph target;

	/**
	 * @param part
	 */
	public ExecuteFlowDiagramAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
		setImageDescriptor(ICON);
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(FlowDiagram model) {
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public FlowDiagram getModel() {
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (target == null) {
			ExtendedElementListSelectionDialog<Object> graphSelectDiag = new ExtendedElementListSelectionDialog<Object>(
					getWorkbenchPart().getSite().getShell(),
					new NamedElementLabelProvider(ResourceUtil.ICONS.GRAPH
							.img(18)),
					HenshinUtil.INSTANCE.getTransformationSystem(model)
							.getInstances().toArray(),
					"Graph Selection Dialog",
					"Please select a Graph to apply this Flow Diagram on:");

			target = (Graph) graphSelectDiag.runSingle();
		}

		if (model == null) {
			ExtendedElementListSelectionDialog<Object> graphSelectDiag = new ExtendedElementListSelectionDialog<Object>(
					getWorkbenchPart().getSite().getShell(),
					new NamedElementLabelProvider(
							ResourceUtil.ICONS.FLOW_DIAGRAM.img(16)),
					FlowControlUtil.INSTANCE.getFlowControlSystem(target)
							.getUnits().toArray(),
					"Flow Diagram Selection Dialog",
					"Please select a Flow Diagram to apply onto this Graph:");

			model = (FlowDiagram) graphSelectDiag.runSingle();
		}

		if (target != null && model != null) {
			Unit parsedUnit = new FlowControlInterpreter(model)
					.parse();

			Map<String, List<ExpressionValidator>> variable2ExpressionValidators = new HashMap<String, List<ExpressionValidator>>();
			Map<String, Object> assignments = new HashMap<String, Object>();

			for (Parameter p : model.getParameters()) {
				if (p.isInput()) {
					variable2ExpressionValidators.put(p.getName(),
							new ArrayList<ExpressionValidator>());
				}
			}

			if (!variable2ExpressionValidators.isEmpty()) {
				ParemetersValueDialog vD = new ParemetersValueDialog(
						getWorkbenchPart().getSite().getShell(), SWT.NULL,
						variable2ExpressionValidators);

				vD.open();

				assignments = vD.getAssigment();
			}

//			 parsedUnit.setName("__test__");
//			 for (Unit u : parsedUnit.getSubUnits(true)) {
//			 u.setName("__test__");
//			 }
//			
//			 HenshinUtil.INSTANCE.getTransformationSystem(model)
//			 .getTransformationUnits().add(parsedUnit);

			execute(new ExecuteTransformationUnitCommand(target, parsedUnit,
					assignments));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		model = null;
		target = null;

		if (selection.size() == 1) {
			Object selected = selection.get(0);

			if (selected instanceof EditPart) {
				Object model = ((EditPart) selected).getModel();

				if (model instanceof FlowDiagram) {
					this.model = (FlowDiagram) model;

					setText(TEXT_SIMPLE);
				}

				if (model instanceof Graph) {
					target = (Graph) model;

					setText(TEXT);
				}
			}
		}

		return model != null || target != null;
	}
}
