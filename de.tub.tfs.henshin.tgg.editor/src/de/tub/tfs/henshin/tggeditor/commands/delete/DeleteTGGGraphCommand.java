package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.CompoundCommand;



/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteTGGGraphCommand extends CompoundCommand {

	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteTGGGraphCommand(Graph graph) {
		
		add(new DeleteGraphCommand(graph));
	}




}
