package tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.EdgeLayout;
import tgg.TGG;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteEdgeCommand deletes a edge in a graph and its layout.
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
	 * The edge layout.
	 */
	private EdgeLayout edgeLayout;
	
	/**
	 * Instantiates a new delete edge command.
	 *
	 * @param edge the alredy created, but new, edge
	 */
	public DeleteEdgeCommand(Edge edge) {
		if (edge != null) {
			this.edge = edge;
			this.graph = edge.getGraph();
			this.source = edge.getSource();
			this.target = edge.getTarget();
			
			add(new SimpleDeleteEObjectCommand(edge));
			
			TGG layoutSystem=NodeUtil.getLayoutSystem(source.getGraph());
			if (layoutSystem!=null){
				edgeLayout = EdgeUtil.getEdgeLayout(edge, layoutSystem);
				add(new DeleteEdgeLayoutCommand(layoutSystem,edgeLayout));
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
			source.getOutgoing().remove(edge);
			target.getIncoming().remove(edge);
			graph.getEdges().remove(edge);
			super.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
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

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
	execute();
	super.redo();
	}
}