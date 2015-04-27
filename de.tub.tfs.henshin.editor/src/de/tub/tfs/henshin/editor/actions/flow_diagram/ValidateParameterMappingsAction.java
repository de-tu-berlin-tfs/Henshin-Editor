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
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramPage;
import de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramView;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlParameterMappingValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * @author nam
 * 
 */
public class ValidateParameterMappingsAction extends SelectionAction {

	/**
	 * An unique ID for this {@link Action}
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateParameterMappingsAction"; //$NON-NLS-1$

	private static final String TEXT = "Validate Parameter Mappings";

	private static final String TEXT_SIMPLE = "Validate Selected Mapping";

	private static final String DESC = "Validate Parameter Mappings";

	private static final String TOOL_TIP = "Validate Parameter Mappings";

	private static final ImageDescriptor ICON = ResourceUtil.ICONS.CHECK
			.descr(18);

	private ParameterMapping mapping;

	private FlowDiagram diagram;

	/**
	 * @param part
	 */
	public ValidateParameterMappingsAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(TEXT);
		setDescription(DESC);
		setToolTipText(TOOL_TIP);
		setImageDescriptor(ICON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		FlowControlParameterMappingValidator validator = new FlowControlParameterMappingValidator();

		LinkedList<ParameterMapping> failed = new LinkedList<ParameterMapping>();

		if (mapping != null) {
			if (!validator.validate(mapping)) {
				failed.add(mapping);
			}
		}

		if (diagram != null) {
			for (ParameterMapping m : diagram.getParameterMappings()) {
				if (m.getSrc().getProvider() != diagram
						&& m.getTarget().getProvider() != diagram
						&& m != mapping) {
					if (!validator.validate(m)) {
						failed.add(m);
					}
				}
			}
		} else {
			diagram = (FlowDiagram) mapping.eContainer();
		}

		if (!failed.isEmpty()) {
			MessageDialog.openError(null, "Validation Error",
					"Validation failed.");
		} else {
			MessageDialog.openInformation(null, "Validation Error",
					"Validation completed successfuly.");
		}

		IWorkbenchPart part = MuvitorTreeEditor.showView(diagram);

		FlowDiagramPage page = (FlowDiagramPage) ((FlowDiagramView) part)
				.getCurrentPage();

		GraphicalViewer parametersViewer = page.getParametersViewer();

		for (ParameterMapping m : failed) {
			Object o = parametersViewer.getEditPartRegistry().get(m);

			if (o instanceof GraphicalEditPart) {
				((GraphicalEditPart) o).getFigure().setForegroundColor(
						ColorConstants.red);

				((PolylineConnection) ((GraphicalEditPart) o).getFigure())
						.setLineWidth(2);
			}
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

		mapping = null;
		diagram = null;

		if (selection.size() == 1) {
			Object selected = selection.get(0);

			if (selected instanceof EditPart) {
				Object model = ((EditPart) selected).getModel();

				if (model instanceof ParameterMapping) {
					mapping = (ParameterMapping) model;

					setText(TEXT_SIMPLE);
				} else if (model instanceof FlowDiagram) {
					diagram = (FlowDiagram) model;

					setText(TEXT);
				}
			}
		}

		return mapping != null || diagram != null;
	}

}
