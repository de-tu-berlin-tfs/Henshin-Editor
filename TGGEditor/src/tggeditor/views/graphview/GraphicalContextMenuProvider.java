package tggeditor.views.graphview;


import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import tggeditor.actions.create.graph.CreateAttributeAction;
import tggeditor.actions.create.graph.CreateEdgeAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class GraphicalContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public GraphicalContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		String group = GEFActionConstants.GROUP_EDIT;
		dynamicAppendActionToGroup(menu, CreateEdgeAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
	}

}
