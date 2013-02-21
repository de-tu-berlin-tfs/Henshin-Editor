package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.CompoundCommand;

/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteGraphInstanceCommand extends CompoundCommand {

	/**
	 * The graph to be deleted by the command. The graph may belong to a rule or may be an instance graph of the transformation sytem.
	 */
	private Graph graph;
	/**
	 * The parent of the graph to be deleted.
	 */
	private Module transformationSystem;

	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteGraphInstanceCommand(Graph graph) {
		this.graph=graph;
		transformationSystem = tggeditor.util.ModelUtil.getTransSystem(graph);
		
		add(new DeleteTGGGraphCommand(graph));
		
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (transformationSystem != null) && (super.canExecute());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return (transformationSystem != null) && (super.canUndo());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
		transformationSystem.getInstances().add(graph);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
		removeAdjacentElements();		
	}

	/**
	 * 
	 */
	private void removeAdjacentElements() {
		transformationSystem.getInstances().remove(graph);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		super.redo();
		removeAdjacentElements();		
	}
}
