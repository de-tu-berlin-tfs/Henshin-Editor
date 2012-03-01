package de.tub.tfs.henshin.editor.ui.graph;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.editor.actions.graph.CreateAttributeAction;
import de.tub.tfs.henshin.editor.actions.graph.ValidateGraphAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

/**
 * The Class GraphContextMenuProvider.
 */
public class GraphContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	/**
	 * Instantiates a new graph context menu provider.
	 * 
	 * @param viewer
	 *            the viewer
	 */
	public GraphContextMenuProvider(EditPartViewer viewer) {
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
		String group = GEFActionConstants.GROUP_REST;

		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
		dynamicAppendActionToGroup(menu, ValidateGraphAction.ID, group);
	}
}
