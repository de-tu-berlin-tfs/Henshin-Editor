package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteEdgeCommand deletes a edge in a graph and its layout.
 */
public class DeleteEdgeCommand extends CompoundCommand {

	/** The edge. */
	private Edge edge;
	
	/** The source. */
	private Node source;
	
	/** The target. */
	private Node target;
	
	/**
	 * Whether the edge was deleted by this command.
	 */
	private boolean edgeDeletionPerformed;
	
	/**
	 * Instantiates a new delete edge command.
	 *
	 * @param edge the already created, but new, edge
	 */
	public DeleteEdgeCommand(Edge edge) {
		if (edge != null) {
			this.edge = edge;
			edgeDeletionPerformed=false;
			

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
			// if edge is still existing when this command shall be executed, then perform the deletion commands
			if (edge.getGraph()!=null){
				source = edge.getSource();
				target = edge.getTarget();	
				if (source!=null) source.getOutgoing().remove(edge);
				if (target!=null) target.getIncoming().remove(edge);
				add(new SimpleDeleteEObjectCommand(edge));
				edgeDeletionPerformed=true;				
			}
			super.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (edge != null)
		return true;
		else return false;
	}

	@Override
	public boolean canUndo() {
		// return super.canUndo();
		if (!edgeDeletionPerformed)
			return true;
		if (edge != null && source != null
				&& target != null)
			return true;
		else return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
		if (edgeDeletionPerformed){
			source.getOutgoing().add(edge);
			target.getIncoming().add(edge);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
	execute();
	super.redo();
	}
}