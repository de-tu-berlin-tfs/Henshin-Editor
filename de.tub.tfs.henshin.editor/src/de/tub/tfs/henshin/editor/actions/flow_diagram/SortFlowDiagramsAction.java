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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;

/**
 * @author nam
 * 
 */
public class SortFlowDiagramsAction extends SelectionAction {

	/**
	 * The unique ID of this {@link Action}.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.SortFlowDiagramsAction"; //$NON-NLS-1$

	private static final String LABEL = "Sort Flow Diagrams by Name";

	private static final String DESC = "Sort Flow Diagams by Name";

	private static final String TOOL_TIP = "Sort Flow Diagams by Name";

	private static final ImageDescriptor ICON = ResourceUtil.ICONS.SORT
			.descr(16);

	private FlowControlSystem model;

	public SortFlowDiagramsAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(LABEL);
		setDescription(DESC);
		setToolTipText(TOOL_TIP);
		setImageDescriptor(ICON);
	}

	@Override
	public void run() {
		ArrayList<FlowDiagram> diagrams = new ArrayList<FlowDiagram>(
				model.getUnits());

		if (diagrams.size() > 1) {
			Collections.sort(diagrams, new Comparator<FlowDiagram>() {
				@Override
				public int compare(FlowDiagram o1, FlowDiagram o2) {
					return o1.getName().compareTo(o2.getName());
				}

			});

			model.getUnits().clear();
			model.getUnits().addAll(diagrams);
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

		if (selection.size() == 1) {
			Object selectd = selection.get(0);

			if (selectd instanceof IAdaptable) {
				Object model = ((IAdaptable) selectd)
						.getAdapter(FlowDiagram.class);

				if (model instanceof EObject) {
					this.model = FlowControlUtil.INSTANCE
							.getFlowControlSystem((EObject) model);
				}
			}
		}

		return model != null;
	}

}
