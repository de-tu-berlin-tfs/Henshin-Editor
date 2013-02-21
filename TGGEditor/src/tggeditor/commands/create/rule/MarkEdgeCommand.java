package tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class MarkEdgeCommand can mark an edge in a rule as new or not new. It makes
 * all the needed changes in the model of the rule and in the tgg layouts.
 * When executed it either deletes the lhs edge or it creates
 * a new lhs edge. Also the nodes are handled.
 */
public class MarkEdgeCommand extends CompoundCommand {

	/**
	 * the rhs edge
	 */
	private Edge rhsEdge;
	/**
	 * the edge layout of lhs and rhs edge
	 */
	private EdgeLayout edgeLayout;

	/**
	 * the constructor 
	 * @param rhsEdge the rhs edge
	 */
	public MarkEdgeCommand(Edge rhsEdge) {
		this.rhsEdge = rhsEdge;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		edgeLayout = EdgeUtil.getEdgeLayout(rhsEdge);
		if (edgeLayout.isNew()) {
			
			NodeLayout sourceLayout = NodeUtil.getNodeLayout(rhsEdge.getSource());
			NodeLayout targetLayout = NodeUtil.getNodeLayout(rhsEdge.getTarget());
			Node lhsSource = sourceLayout.getLhsnode();
			Node lhsTarget = targetLayout.getLhsnode();
			
			if(lhsSource == null) {//node is new
				lhsSource = createLhsNode(sourceLayout);
			}
			if(lhsTarget == null) {//node is new
				lhsTarget = createLhsNode(targetLayout);
			}
			
			Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge(
					lhsSource, lhsTarget, rhsEdge.getType());
			
			edgeLayout.setNew(false);
			edgeLayout.setLhsedge(lhsEdge);
			
			lhsSource.getGraph().getEdges().add(lhsEdge);
			
		} else if (!edgeLayout.isNew()) {
			
			edgeLayout.setNew(true);
			Edge lhsEdge = edgeLayout.getLhsedge();
			
			//delete lhsEdge
//			Graph lhsGraph = lhsEdge.getGraph();
			Node lhsSource = lhsEdge.getSource();
			Node lhsTarget = lhsEdge.getTarget();
			
			lhsSource.getOutgoing().remove(lhsEdge);
			lhsTarget.getIncoming().remove(lhsEdge);
//			lhsGraph.getEdges().remove(lhsEdge);
			edgeLayout.setLhsedge(null);
			
			add(new SimpleDeleteEObjectCommand(lhsEdge));
			
			
		}
		super.execute();
	}

	/**
	 * creates the lhs node and sets its references for a given layout.
	 * @param layout to the rhs node
	 * @return the created lhs node
	 */
	private Node createLhsNode(NodeLayout layout) {
		Node lhsNode;
		lhsNode = HenshinFactory.eINSTANCE.createNode();
		
		lhsNode.setName(layout.getNode().getName());
		lhsNode.setType(layout.getNode().getType());
		
		Rule rule = rhsEdge.getGraph().getRule();
		Graph lhsGraph = rule.getLhs();
		lhsGraph.getNodes().add(lhsNode);
		
		layout.setLhsnode(lhsNode);
		layout.setNew(false);
		
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(layout.getNode());
		mapping.setOrigin(layout.getLhsnode());
		
		rule.getMappings().add(mapping);
		return lhsNode;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

}
