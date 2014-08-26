/**
 * MatchSearchAction.java
 * created on 04.05.2013 15:28:35
 */
package de.tub.tfs.henshin.tggeditor.actions.search;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TripleGraph;

/**
 * @author huuloi
 */
public class MatchSearchAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.search.MatchSearchAction"; //$NON-NLS-1$
	
	private TripleGraph graph;

	public MatchSearchAction(IWorkbenchPart part, TripleGraph graph) {
		super(part);
		setId(ID);
		setText("Match search");
		setToolTipText("Find all matches in graph for the given rule");
		this.graph = graph;
	}

	@Override
	protected boolean calculateEnabled() {
		return graph != null && graph.getNodes().size() > 0;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

}
