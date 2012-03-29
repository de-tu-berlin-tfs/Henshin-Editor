package tggeditor.actions.create;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.ActionFactory;

import tggeditor.views.ruleview.MuvitorVPage;
import de.tub.tfs.muvitor.ui.MuvitorPage;

/**
 * An action that selects all edit parts in the current viewer of an
 * AbstractMultiViewerPage. Adapted from SelectAllAction for {@link MuvitorPage}
 * s.
 */
public class SelectAllInMultiViewerAction extends Action {

	private final MuvitorVPage page;

	/**
	 * Constructs a <code>SelectAllAction</code> and associates it with the
	 * given part.
	 * 
	 * @param page
	 *            The workbench part associated with this SelectAllAction
	 */
	public SelectAllInMultiViewerAction(final MuvitorVPage page) {
		this.page = page;
		setText("Select all");
		setToolTipText("Select all");
		setId(ActionFactory.SELECT_ALL.getId());
	}

	/**
	 * Selects all edit parts in the active workbench part.
	 */
	@Override
	public void run() {
		final GraphicalViewer viewer = page.getCurrentViewer();
		if (viewer != null) {
			viewer.setSelection(new StructuredSelection(viewer.getContents()
					.getChildren()));
		}
	}

}
