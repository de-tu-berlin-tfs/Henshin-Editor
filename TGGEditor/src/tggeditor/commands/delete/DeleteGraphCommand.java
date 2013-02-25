package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
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
		
		// graph contents do not need to be deleted separately, because the layout is contained in it
		//		for(Node node:graph.getNodes()) {
//			add(new DeleteNodeCommand(node));
//		}		
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
