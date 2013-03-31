/**
 * TypeSearchAction.java
 * created on 28.03.2013 15:36:49
 */
package de.tub.tfs.henshin.tggeditor.actions.search;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;

/**
 * @author huuloi
 */
public class TypeSearchAction extends SelectionAction{
	
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.search.TypeSearchAction"; //$NON-NLS-1$
	
	private TripleGraph graph; 
	
	public TypeSearchAction(IWorkbenchPart part, TripleGraph graph) {
		super(part);
		setId(ID);
		setText("Type search");
		setToolTipText("Find all nodes of the type in the active graph");
		this.graph = graph;
	}


	@Override
	protected boolean calculateEnabled() {
		return graph != null && NodeTypes.getUsedNodeTypes(graph).size() > 1;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
