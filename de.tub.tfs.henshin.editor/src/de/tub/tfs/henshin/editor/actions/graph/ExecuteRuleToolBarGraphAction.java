package de.tub.tfs.henshin.editor.actions.graph;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.actions.rule.ExecuteRuleAction;
import de.tub.tfs.henshin.editor.ui.graph.GraphPage;

/**
 * The Class ExecuteRuleToolBarGraphAction.
 */
public class ExecuteRuleToolBarGraphAction extends ExecuteRuleAction {

	/**
	 * Instantiates a new execute rule tool bar graph action.
	 * 
	 * @param part
	 *            the part
	 * @param graphPage
	 *            the graph page
	 */
	public ExecuteRuleToolBarGraphAction(IWorkbenchPart part,
			GraphPage graphPage) {
		super(part);
		graph = graphPage.getCastedModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.CreateNACAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.rule.ExecuteRuleAction#run()
	 */
	@Override
	public void run() {
		rule = null;
		super.run();
	}

}
