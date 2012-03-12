package tggeditor.commands.delete.rule;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tggeditor.commands.delete.DeleteEdgeLayoutCommand;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteRuleEdgeCommand deletes an edge in a rule. Better: it deletes the rhs edge, if 
 * the lhs edge (if there is one) and the edgelayout.
 */
public class DeleteRuleEdgeCommand extends CompoundCommand {

	/**
	 * thr rhs edge
	 */
	private Edge rhsEdge;
	/**
	 * the lhs edge
	 */
	private Edge lhsEdge;
	/**
	 * the source of the rhs edge
	 */
	private Node rhsSource;
	/**
	 * the target of the rhs edge
	 */
	private Node rhsTarget;
	/**
	 * the source of the lhs edge
	 */
	private Node lhsSource;
	/**
	 * the target of the lhs edge
	 */
	private Node lhsTarget;
	/**
	 * the containing graph of the lhs edge
	 */
	private Graph lhsGraph;
	/**
	 * the containing graph of the rhs edge
	 */
	private Graph rhsGraph;
	
	/**the constructor
	 * @param edge the already created edge
	 */
	public DeleteRuleEdgeCommand(Edge edge) {
		
		rhsEdge = edge;
		rhsSource = edge.getSource();
		rhsTarget = edge.getTarget();
		lhsSource = getMappingNode(edge.getSource());
		lhsTarget = getMappingNode(edge.getTarget());
		
		if (lhsSource != null && lhsTarget != null) {
			lhsEdge = HenshinFactory.eINSTANCE.createEdge();
			List<Edge> list = lhsSource.getAllEdges();
			for (Edge e : list) {
				if ((e.getSource() == lhsSource) && (e.getTarget() == lhsTarget)) {
					lhsEdge = e;
				}
			}
	
			lhsGraph = lhsEdge.getGraph();
			add(new SimpleDeleteEObjectCommand(lhsEdge));
		}
		rhsGraph = edge.getGraph();
		add(new SimpleDeleteEObjectCommand(rhsEdge));
		
		add(new DeleteEdgeLayoutCommand(NodeUtil.getLayoutSystem(rhsSource), EdgeUtil.getEdgeLayout(edge)));
	}
	
	/**Gets the mapping between a rhs and a lhs node, if there is one. 
	 * @param rhsNode
	 * @return the mapping or null
	 */
	private Node getMappingNode(Node rhsNode) {
		EList<Mapping> mappingList = rhsNode
				.getGraph().getContainerRule().getMappings();
		for (Mapping mapping : mappingList) {
			if (mapping.getImage() == rhsNode) {
				return mapping.getOrigin();
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {

		rhsSource.getOutgoing().remove(rhsEdge);
		rhsTarget.getIncoming().remove(rhsEdge);
		rhsGraph.getEdges().remove(rhsEdge);
		if (lhsSource != null && lhsTarget != null) {
			lhsSource.getOutgoing().remove(lhsEdge);
			lhsTarget.getIncoming().remove(lhsEdge);
			lhsGraph.getEdges().remove(lhsEdge);
		}
		super.execute();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		rhsSource.getOutgoing().add(rhsEdge);
		rhsTarget.getIncoming().add(rhsEdge);
		rhsGraph.getEdges().add(rhsEdge);
		if (lhsSource != null && lhsTarget != null) {
			lhsSource.getOutgoing().add(lhsEdge);
			lhsTarget.getIncoming().add(lhsEdge);
			lhsGraph.getEdges().add(lhsEdge);
		}
		super.undo();
	}

}
