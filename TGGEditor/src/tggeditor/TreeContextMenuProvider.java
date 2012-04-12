package tggeditor;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import tggeditor.actions.create.graph.CreateAttributeAction;
import tggeditor.actions.create.graph.CreateEdgeAction;
import tggeditor.actions.create.graph.CreateGraphAction;
import tggeditor.actions.create.rule.CreateNACAction;
import tggeditor.actions.create.rule.CreateParameterAction;
import tggeditor.actions.create.rule.CreateRuleAction;
import tggeditor.actions.create.rule.GenerateFTRuleAction;
import tggeditor.actions.create.rule.GenerateFTRulesAction;
import tggeditor.actions.execution.ExecuteFTRulesAction;
import tggeditor.actions.imports.ImportCorrAction;
import tggeditor.actions.imports.ImportEMFModelAction;
import tggeditor.actions.imports.ImportSourceAction;
import tggeditor.actions.imports.ImportTargetAction;
import tggeditor.actions.validate.GraphValidAction;
import tggeditor.actions.validate.RuleValidAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class TreeContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public TreeContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		String group = GEFActionConstants.GROUP_EDIT;
		dynamicAppendActionToGroup(menu, ImportEMFModelAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportSourceAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportTargetAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportCorrAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateGraphAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateEdgeAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateNACAction.ID, group);
		dynamicAppendActionToGroup(menu, GraphValidAction.ID, group);
		dynamicAppendActionToGroup(menu, RuleValidAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateParameterAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateFTRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateFTRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, ExecuteFTRulesAction.ID, group);
	}
	
	
}
