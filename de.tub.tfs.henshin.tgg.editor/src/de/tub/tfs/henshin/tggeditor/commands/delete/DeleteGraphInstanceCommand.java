package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteGraphInstanceCommand extends CompoundCommand {
	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteGraphInstanceCommand(Graph graph) {
		
		add(new SimpleDeleteEObjectCommand(graph));
		
	}



}
