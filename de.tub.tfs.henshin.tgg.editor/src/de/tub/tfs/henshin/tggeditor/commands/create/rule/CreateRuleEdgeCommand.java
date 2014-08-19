package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * The class CreateRuleEdgeCommand creates an edge in a rule. Better: it creates a lhs edge, rhs
 * edge and the mapping between the edge. Also the edgelayouts attributes will be set.
 */
public class CreateRuleEdgeCommand extends CreateEdgeCommand {
	
	Edge lhsEdge;
	Graph lhsGraph;
	Node lhsSource;
	Node lhsTarget;
	Rule rule;

	/**
	 * Instantiates a new creates the edge command.
	 *
	 * @param sourceNode the source node
	 * @param requestingObject the requesting object
	 */
	public CreateRuleEdgeCommand(Node sourceNode, Edge requestingObject) {
		super(sourceNode, requestingObject);
		
	}

	/**
	 * the constructor
	 * @param rhsGraph the rhs graph
	 * @param source the source node
	 * @param target the target node
	 * @param eReference the reference type of the new edge
	 */
	public CreateRuleEdgeCommand(Graph rhsGraph, Node source, Node target,
			EReference eReference) {
		super(rhsGraph, source, target, eReference);
		
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateEdgeCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
	
		rule = graph.getRule();
		((TEdge) edge).setMarkerType(RuleUtil.NEW);

		Mapping sourceMapping = RuleUtil.getRHSNodeMapping(sourceNode);
		Mapping targetmapping = RuleUtil.getRHSNodeMapping(targetNode);
		
		// case: source and target nodes are preserved, thus edge is put into LHS and RHS as a preserved edge
		if (sourceMapping != null && targetmapping != null) { //if(!edgeComplete())
			
			this.lhsEdge = TggFactory.eINSTANCE.createTEdge();
			this.lhsEdge.setSource(sourceMapping.getOrigin());
			this.lhsEdge.setTarget(targetmapping.getOrigin());
			this.lhsEdge.setType(typeReference);
			((TEdge) edge).setMarkerType(null);
			

			lhsGraph = rule.getLhs();
			lhsEdge.setGraph(lhsGraph);
			lhsGraph.getEdges().add(this.lhsEdge);
		}
		else { // edge is put into RHS as a new edge created by the rule
			((TEdge) edge).setMarkerType(RuleUtil.NEW);
		}
	}

	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateEdgeCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
		if (!RuleUtil.NEW.equals(((TEdge) edge).getMarkerType())) {
			lhsGraph.getEdges().remove(lhsEdge);
			lhsEdge.getSource().getOutgoing().remove(lhsEdge);
			lhsEdge.getTarget().getIncoming().remove(lhsEdge);
		}
	}	
	
	
}
