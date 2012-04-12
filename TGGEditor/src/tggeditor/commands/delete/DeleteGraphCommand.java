package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteGraphCommand extends CompoundCommand {
	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteGraphCommand(Graph graph) {
		for(Node node:graph.getNodes()) {
			add(new DeleteNodeCommand(node));
		}
		add(new SimpleDeleteEObjectCommand(graph));
		
	}
}
