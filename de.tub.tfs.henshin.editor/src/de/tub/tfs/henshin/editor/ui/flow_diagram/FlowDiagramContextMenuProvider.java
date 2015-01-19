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
package de.tub.tfs.henshin.editor.ui.flow_diagram;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.editor.actions.flow_diagram.AddCompoundActivityChildAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ClearActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ExecuteFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.FlowDiagram2UnitAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetFlowDiagramInputParameterAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetFlowDiagramOutputParameterAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.UnNestActivityAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateParameterMappingsAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

/**
 * @author nam
 * 
 */
public class FlowDiagramContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	/**
	 * @param viewer
	 */
	public FlowDiagramContextMenuProvider(EditPartViewer viewer,
			ActionRegistry actionReg) {
		super(viewer);

		setActionRegistry(actionReg);
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
		dynamicAppendActionToGroup(menu, SetActivityContentAction.ID,
				GEFActionConstants.GROUP_EDIT);
		dynamicAppendActionToGroup(menu, AddCompoundActivityChildAction.ID,
				GEFActionConstants.GROUP_EDIT);
		dynamicAppendActionToGroup(menu, UnNestActivityAction.ID,
				GEFActionConstants.GROUP_EDIT);
		dynamicAppendActionToGroup(menu, ClearActivityContentAction.ID,
				GEFActionConstants.GROUP_EDIT);
		dynamicAppendActionToGroup(menu,
				SetFlowDiagramOutputParameterAction.ID,
				GEFActionConstants.GROUP_EDIT);
		dynamicAppendActionToGroup(menu, SetFlowDiagramInputParameterAction.ID,
				GEFActionConstants.GROUP_EDIT);

		dynamicAppendActionToGroup(menu, FlowDiagram2UnitAction.ID,
				GEFActionConstants.GROUP_REST);
		dynamicAppendActionToGroup(menu, ExecuteFlowDiagramAction.ID,
				GEFActionConstants.GROUP_REST);
		dynamicAppendActionToGroup(menu, ValidateFlowDiagramAction.ID,
				GEFActionConstants.GROUP_REST);
		dynamicAppendActionToGroup(menu, ValidateParameterMappingsAction.ID,
				GEFActionConstants.GROUP_REST);
	}

}
