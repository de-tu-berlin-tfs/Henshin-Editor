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
package de.tub.tfs.henshin.editor.ui.rule;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.editor.actions.rule.DeleteMappingAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.ui.graph.GraphContextMenuProvider;

/**
 * The Class RuleContextMenuProvider.
 * 
 * @author Johann
 */
public class RuleContextMenuProvider extends GraphContextMenuProvider {

	/**
	 * Konstruktor erhält einen EditPartViewer.
	 * 
	 * @param viewer
	 *            EditPartViewer
	 * @param actionRegistry
	 *            the action registry
	 */
	public RuleContextMenuProvider(EditPartViewer viewer,
			ActionRegistry actionRegistry) {
		super(viewer);
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
		super.buildContextMenu(menu);
		String group = GEFActionConstants.GROUP_REST;
		dynamicAppendActionToGroup(menu, DeleteMappingAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateParameterAction.ID, group);
	}

	/**
	 * Convenient method to get the selected object.
	 * 
	 * @return the selected object or , if nothing was selected
	 */
	protected Object getSelectedObject() {
		EditPart editPart = getSelectedEditPart();
		if (editPart != null) {
			return editPart.getModel();
		}
		return null;
	}

	/**
	 * Gets the selected edit part.
	 * 
	 * @return the selected edit part
	 */
	protected EditPart getSelectedEditPart() {
		if (getViewer().getSelectedEditParts().size() == 1) {
			Object selected = getViewer().getSelectedEditParts().get(0);

			if (selected instanceof EditPart) {
				return (EditPart) selected;
			}
		}

		return null;

	}

}
