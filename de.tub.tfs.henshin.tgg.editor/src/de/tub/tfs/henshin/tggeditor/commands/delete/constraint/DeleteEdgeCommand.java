package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteEdgeCommand extends SimpleDeleteEObjectCommand {

	/** The edge. */
	private Edge edge;
	
	/** The source. */
	private Node source;
	
	/** The target. */
	private Node target;
	
	/**
	 * Instantiates a new delete edge command.
	 *
	 * @param edge the already created, but new, edge
	 */
	public DeleteEdgeCommand(Edge edge) {
		super(edge);
		if (edge != null) {
			this.edge = edge;
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
			// if edge is edge of premise, try to remove edge from conclusion
			if (edge.getGraph().eContainer() instanceof NestedConstraint) {
				MappingList mappings = ((NestedCondition)edge.getGraph().getFormula()).getMappings();
				Node cSource = null;
				Node cTarget = null;
				for (Mapping m : mappings) {
					if (m.getOrigin() == edge.getSource()) {
						cSource = m.getImage();
					}
					if (m.getOrigin() == edge.getTarget()) {
						cTarget = m.getImage();
					}
				}
				if (cSource != null && cTarget != null) {
					Edge ccEdge = null;
					for (Edge cEdge : cSource.getOutgoing()) {
						if (cEdge.getTarget() == cTarget
								&& cEdge.getType().equals(edge.getType())) {
							ccEdge = cEdge;
							break;
						}
					}
					if (ccEdge != null) {
						cSource.getOutgoing().remove(ccEdge);
						cTarget.getIncoming().remove(ccEdge);
					}
				}
			}
			
			// delete edge itself
			source = edge.getSource();
			target = edge.getTarget();	
			if (source!=null) source.getOutgoing().remove(edge);
			if (target!=null) target.getIncoming().remove(edge);
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
		return edge != null;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

}
