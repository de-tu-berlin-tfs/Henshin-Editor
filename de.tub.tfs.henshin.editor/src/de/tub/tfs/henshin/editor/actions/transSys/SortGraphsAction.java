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
package de.tub.tfs.henshin.editor.actions.transSys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

public class SortGraphsAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.SortGraphsAction";

	/** The graph. */
	private Module transformationSystem;

	public SortGraphsAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Sort graphs by name");
		setToolTipText("Sort graphs by name.");
		setImageDescriptor(ResourceUtil.ICONS.SORT.descr(16));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof Module) {
					transformationSystem = (Module) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Graph.class) != null) {
					transformationSystem = (Module) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return transformationSystem != null;
	}

	@Override
	public void run() {
		EList<Graph> graphs = transformationSystem.getInstances();
		Graph[] graphsArr = new Graph[0];
		graphsArr = graphs.toArray(graphsArr);
		Arrays.sort(graphsArr, new Comparator<Graph>() {
			@Override
			public int compare(Graph o1, Graph o2) {
				if (o1 == o2)
					return 0;
				if (o1 == null || o1.getName() == null)
					return -1;
				if (o2 == null || o2.getName() == null)
					return 1;
				return o1.getName().toLowerCase()
						.compareTo(o2.getName().toLowerCase());
			}
		});

		graphs.clear();
		graphs.addAll(Arrays.asList(graphsArr));
	}
}
