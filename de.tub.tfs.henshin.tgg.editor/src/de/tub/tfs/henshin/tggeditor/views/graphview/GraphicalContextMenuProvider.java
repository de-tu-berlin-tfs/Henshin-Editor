package de.tub.tfs.henshin.tggeditor.views.graphview;


import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.tggeditor.actions.collapse.CollapseChildrenAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class GraphicalContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public GraphicalContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		String group = GEFActionConstants.GROUP_EDIT;
		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
		dynamicAppendActionToGroup(menu, CollapseChildrenAction.ID, group);
	}

}
