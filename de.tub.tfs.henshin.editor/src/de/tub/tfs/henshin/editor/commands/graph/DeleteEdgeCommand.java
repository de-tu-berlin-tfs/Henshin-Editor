package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteEdgeCommand.
 */
public class DeleteEdgeCommand extends CompoundCommand {

	/** The edge. */
	private Edge edge;

	/** The graph. */
	private Graph graph;

	/** The source. */
	private Node source;

	/** The target. */
	private Node target;

	/**
	 * Instantiates a new delete edge command.
	 * 
	 * @param edge
	 *            the edge
	 */
	public DeleteEdgeCommand(Edge edge) {
		if (edge != null) {
			this.edge = edge;
			this.graph = edge.getGraph();
			this.source = edge.getSource();
			this.target = edge.getTarget();

			add(new SimpleDeleteEObjectCommand(edge));

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		graph.getEdges().remove(edge);
		source.getOutgoing().remove(edge);
		target.getIncoming().remove(edge);
		super.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(source) && HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(target)){
			return false;
		}
		
		return graph != null && edge != null && source != null
				&& target != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		graph.getEdges().add(edge);
		edge.setSource(source);
		edge.setTarget(target);
		super.undo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		execute();
		super.redo();
	}
}
