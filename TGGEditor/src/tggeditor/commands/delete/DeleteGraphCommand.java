package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.GraphLayout;
import tgg.TGG;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeUtil;
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


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return super.canExecute();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return super.canUndo();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		super.redo();
	}
}
