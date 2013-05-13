package de.tub.tfs.henshin.tggeditor.views.ruleview;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.tggeditor.actions.DeleteNacMappingsAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerAction;
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
		dynamicAppendActionToGroup(menu, DeleteNacMappingsAction.ID, group);
		dynamicAppendActionToGroup(menu, NewMarkerAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
	}

}
