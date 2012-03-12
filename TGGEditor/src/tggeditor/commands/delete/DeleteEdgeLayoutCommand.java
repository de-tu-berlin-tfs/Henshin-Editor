package tggeditor.commands.delete;

import org.eclipse.gef.commands.Command;

import tgg.EdgeLayout;
import tgg.TGG;

/**
 * The class DeleteEdgeLayoutCommand deletes the layout of an edge.
 */
public class DeleteEdgeLayoutCommand extends Command {

	/**
	 * the layout
	 */
	private EdgeLayout edgeLayout;
	/**
	 * the tgg layout system
	 */
	private TGG layoutSystem;
	
	/**
	 * the constructor
	 * @param layoutSystem the layout system containing the edge
	 * @param edgeLayout the edge to be deleted
	 */
	public DeleteEdgeLayoutCommand(TGG layoutSystem, EdgeLayout edgeLayout) {
		this.layoutSystem = layoutSystem;
		this.edgeLayout = edgeLayout;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (this.layoutSystem != null && this.edgeLayout != null) {
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		this.layoutSystem.getEdgelayouts().remove(this.edgeLayout);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.layoutSystem.getEdgelayouts().add(this.edgeLayout);
	}

	
}
