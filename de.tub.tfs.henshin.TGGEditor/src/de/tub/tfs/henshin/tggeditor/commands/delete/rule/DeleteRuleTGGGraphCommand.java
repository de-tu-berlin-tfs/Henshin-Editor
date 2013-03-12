package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteRuleTGGGraphCommand extends CompoundCommand {

//	/**
//	 * The graph to be deleted by the command. The graph may belong to a rule or may be an instance graph of the transformation sytem.
//	 */
//	private Graph graph;
	/**
	 * Layout element of rule to be deleted: The the divider between source and correspondence component.
	 */
	private GraphLayout divSC;
	/**
	 * Layout element of rule to be deleted: The the divider between correspondence and target component. 
	 */
	private GraphLayout divCT;

	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteRuleTGGGraphCommand(Graph graph) {
		
		final GraphLayout[] layouts = GraphUtil.getGraphLayouts(graph);
		divSC=layouts[0];
		divCT=layouts[1];
		add(new SimpleDeleteEObjectCommand(divSC));
		add(new SimpleDeleteEObjectCommand(divCT));
		
		add(new SimpleDeleteEObjectCommand(graph));
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (divSC != null) && (divCT != null)
				&& (super.canExecute());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return (divSC != null) && (divCT != null)
				&& (super.canUndo());
	}
}
