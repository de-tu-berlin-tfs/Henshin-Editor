package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.CompoundCommand;

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
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteRuleTGGGraphCommand(Graph graph) {
		
		add(new SimpleDeleteEObjectCommand(graph));
	}


}
