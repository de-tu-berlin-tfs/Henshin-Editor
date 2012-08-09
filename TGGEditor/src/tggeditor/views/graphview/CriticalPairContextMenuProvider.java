package tggeditor.views.graphview;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class CriticalPairContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public CriticalPairContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
	}

}
