package de.tub.tfs.henshin.tggeditor.actions.validate;

import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * Equal to GraphVaidAction but is shown in the graph editor's toolbar
 * @see de.tub.tfs.henshin.tggeditor.actions.validate.GraphValidAction
 */
public class GraphValidToolBarAction extends GraphValidAction {
	
	/**
	 * Instantiates a new graph valid tool bar action.
	 *
	 * @param part the part
	 * @param graphPage the graph page
	 */
	public GraphValidToolBarAction(MuvitorPageBookView part,GraphicalPage graphPage) {
		super(part.getEditor());
		this.graph=graphPage.getCastedModel();
	}

	/* (non-Javadoc)
	 * @see henshineditor.actions.graph.GraphValidAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return graph != null;
	}

	
}
