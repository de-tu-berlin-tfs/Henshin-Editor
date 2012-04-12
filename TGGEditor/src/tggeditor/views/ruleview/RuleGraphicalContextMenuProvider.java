package tggeditor.views.ruleview;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import tggeditor.actions.DeleteNacMappingsAction;
import tggeditor.actions.create.graph.CreateAttributeAction;
import tggeditor.actions.create.graph.CreateEdgeAction;
import tggeditor.actions.create.rule.NewMarkerAction;
import tggeditor.actions.create.rule.CreateRuleAction;

import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class RuleGraphicalContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public RuleGraphicalContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {		
		String group = GEFActionConstants.GROUP_EDIT;
		dynamicAppendActionToGroup(menu, CreateRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateEdgeAction.ID, group);
		dynamicAppendActionToGroup(menu, DeleteNacMappingsAction.ID, group);
		dynamicAppendActionToGroup(menu, NewMarkerAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
	}

}
