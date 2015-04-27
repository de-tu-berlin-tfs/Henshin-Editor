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
package de.tub.tfs.henshin.editor.ui.transformation_unit;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.editor.actions.transformation_unit.AddTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveDownTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveUpTransformationUnitAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

/**
 * The Class TransUnitContexMenuProvider.
 */
public class TransUnitContexMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	/** The action registry. */
	private ActionRegistry actionRegistry;

	/**
	 * Instantiates a new trans unit contex menu provider.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param actionRegistry
	 *            the action registry
	 */
	public TransUnitContexMenuProvider(EditPartViewer viewer,
			ActionRegistry actionRegistry) {
		super(viewer);
		this.actionRegistry = actionRegistry;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(IMenuManager menu) {
		String group = GEFActionConstants.GROUP_REST;

		actionRegistry.getAction(CreateParameterAction.ID).setText(
				"New Parameter");

		dynamicAppendActionToGroup(menu, CreateParameterAction.ID, group);
		dynamicAppendActionToGroup(menu, AddTransformationUnitAction.ID, group);
		dynamicAppendActionToGroup(menu, MoveUpTransformationUnitAction.ID,
				group);
		dynamicAppendActionToGroup(menu, MoveDownTransformationUnitAction.ID,
				group);

	}
}
