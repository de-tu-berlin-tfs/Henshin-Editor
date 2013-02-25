package tggeditor.actions.execution;

import org.eclipse.jface.action.IAction;

import tgg.TGG;
import tggeditor.util.NodeUtil;
import tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class ExecuteFTRulesToolBarAction extends ExecuteFTRulesAction implements
		IAction {
	
	/**
	 * Instantiates a new execute rule tool bar rule action.
	 *
	 * @param part the part
	 * @param page the graph page
	 */
	public ExecuteFTRulesToolBarAction(MuvitorPageBookView part, GraphicalPage page) {
		super(part.getEditor());
		graph=page.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		TGG tgg = NodeUtil.getLayoutSystem(graph);
		tRules = tgg.getTRules();
		return (tRules.size() > 0);
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#run()
	 */
	@Override
	public void run() {
		if (tRules == null) {
			calculateEnabled();
		}
		super.run();
	}

}
