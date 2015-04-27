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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import de.tub.tfs.henshin.editor.actions.condition.CreateAndAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateApplicationConditionAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateConditionAction;
import de.tub.tfs.henshin.editor.actions.condition.CreateNotAction;
import de.tub.tfs.henshin.editor.actions.condition.SetNegatedAction;
import de.tub.tfs.henshin.editor.actions.condition.SwapBinaryFormulaAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.CreateFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ExecuteFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.FlowDiagram2UnitAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SortFlowDiagramsAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateParameterMappingsAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateAttributeAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateEdgeAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateGraphAction;
import de.tub.tfs.henshin.editor.actions.graph.CreateNodeAction;
import de.tub.tfs.henshin.editor.actions.graph.FilterMetaModelAction;
import de.tub.tfs.henshin.editor.actions.graph.ValidateGraphAction;
import de.tub.tfs.henshin.editor.actions.rule.AddMultiRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.CreateAttributeConditionAction;
import de.tub.tfs.henshin.editor.actions.rule.CreateRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.ExecuteRuleAction;
import de.tub.tfs.henshin.editor.actions.rule.ValidateRuleAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.AddTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateConditionalUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateConditionalUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateIndependentUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateLoopUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateLoopWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreatePriorityUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreatePriorityUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateSequentialUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateSequentialUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.ExecuteTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveDownTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveUpTransformationUnitAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

/**
 * The Class HenshinTreeContextMenuProvider.
 */
public class HenshinTreeContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	private static final String ID_GROUP_NEW = "henshin.id.menu.group.new";

	private static final String ID_GROUP_IEXPORT = "henshin.id.menu.group.iexport";

	/**
	 * Instantiates a new henshin tree context menu provider.
	 * 
	 * @param viewer
	 *            Edit part viewer to show the context menu.
	 * @param actionRegistry
	 *            the action registry
	 */
	public HenshinTreeContextMenuProvider(EditPartViewer viewer,
			ActionRegistry actionRegistry) {
		super(viewer);

		setActionRegistry(actionRegistry);
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
		List<String> creationActions = new ArrayList<String>();

		menu.insertBefore(GEFActionConstants.GROUP_UNDO, new Separator(
				ID_GROUP_NEW));
		menu.insertBefore(GEFActionConstants.GROUP_REST, new Separator(
				ID_GROUP_IEXPORT));

		creationActions.add(CreateGraphAction.ID);
		creationActions.add(CreateRuleAction.ID);
		creationActions.add(CreateConditionalUnitAction.ID);
		creationActions.add(CreateIndependentUnitAction.ID);
		creationActions.add(CreatePriorityUnitAction.ID);
		creationActions.add(CreateSequentialUnitAction.ID);
		creationActions.add(CreateLoopUnitAction.ID);

		creationActions.add(CreateFlowDiagramAction.ID);

		creationActions.add(CreateApplicationConditionAction.ID);
		creationActions.add(CreateConditionAction.ID);
		creationActions.add(CreateAndAction.ID);
		creationActions.add(CreateNotAction.ID);

		creationActions.add(CreateNodeAction.ID);
		creationActions.add(CreateEdgeAction.ID);
		creationActions.add(CreateParameterAction.ID);
		creationActions.add(CreateAttributeAction.ID);
		creationActions.add(CreateAttributeConditionAction.ID);

		dynamicAddSubmenu(menu, creationActions, "New", ID_GROUP_NEW);

		dynamicAppendActionToGroup(menu, ImportEcoreModelAction.ID,
				ID_GROUP_IEXPORT);
		dynamicAppendActionToGroup(menu, ImportInstanceModelAction.ID,
				ID_GROUP_IEXPORT);
		dynamicAppendActionToGroup(menu, ExportInstanceModelAction.ID,
				ID_GROUP_IEXPORT);

		String editGroup = GEFActionConstants.GROUP_EDIT;

		dynamicAppendActionToGroup(menu, SetNegatedAction.ID, editGroup);

		String otherGroup = GEFActionConstants.GROUP_REST;

		dynamicAppendActionToGroup(menu, AddTransformationUnitAction.ID,
				ID_GROUP_NEW);
		dynamicAppendActionToGroup(menu, ExecuteRuleAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, CreateLoopWithContentAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, CreateSequentialUnitWithContentAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, CreatePriorityUnitWithContentAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, CreateConditionalUnitWithContentAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, AddMultiRuleAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, ExecuteTransformationUnitAction.ID,
				otherGroup);

		dynamicAppendActionToGroup(menu, FlowDiagram2UnitAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, ExecuteFlowDiagramAction.ID,
				otherGroup);
		dynamicAppendActionToGroup(menu, ValidateFlowDiagramAction.ID,
				otherGroup);
		dynamicAppendActionToGroup(menu, ValidateParameterMappingsAction.ID,
				otherGroup);

		dynamicAppendActionToGroup(menu, ValidateGraphAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, ValidateRuleAction.ID, otherGroup);
		dynamicAppendActionToGroup(menu, MoveUpTransformationUnitAction.ID,
				otherGroup);
		dynamicAppendActionToGroup(menu, MoveDownTransformationUnitAction.ID,
				otherGroup);

		dynamicAppendActionToGroup(menu, SwapBinaryFormulaAction.ID, otherGroup);

		String formatGroup = "henshin.menu.constants.groups.Format";

		menu.add(new Separator(formatGroup));

		dynamicAppendActionToGroup(menu, SortRulesAction.ID, formatGroup);
		dynamicAppendActionToGroup(menu, SortGraphsAction.ID, formatGroup);
		dynamicAppendActionToGroup(menu, SortFlowDiagramsAction.ID, formatGroup);

		dynamicAppendActionToGroup(menu, FilterMetaModelAction.ID,
				GEFActionConstants.GROUP_EDIT);
	}
}
