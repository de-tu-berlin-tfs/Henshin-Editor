package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.util.EdgeReferences;

public class CreateEdgeCommand extends de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand {

	public CreateEdgeCommand(Node sourceNode, Edge requestingObject) {
		super(sourceNode, requestingObject);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		super.execute();
		
		// if edge is edge of premise, then also create edge in conclusion, if source and target nodes exist identically in premise and conclusion
		if (graph.eContainer() instanceof NestedConstraint) {
			MappingList mappings = ((NestedCondition)graph.getFormula()).getMappings();
			Node source = null;
			Node target = null;
			for (Mapping m : mappings) {
				if (m.getOrigin() == edge.getSource()
						&& m.getOrigin().getType().equals(m.getImage().getType())) {
					source = m.getImage();
				}
				if (m.getOrigin() == edge.getTarget()
						&& m.getOrigin().getType().equals(m.getImage().getType())) {
					target = m.getImage();
				}
			}
			if (source != null && target != null &&	!EdgeReferences.getSourceToTargetFreeReferences(source, target).isEmpty()) {
				TEdge cEdge = TggFactory.eINSTANCE.createTEdge();
				cEdge.setSource(source);
				cEdge.setTarget(target);
				cEdge.setType(edge.getType());
				cEdge.setGraph(((NestedCondition)graph.getFormula()).getConclusion());
			}
		}
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
}
