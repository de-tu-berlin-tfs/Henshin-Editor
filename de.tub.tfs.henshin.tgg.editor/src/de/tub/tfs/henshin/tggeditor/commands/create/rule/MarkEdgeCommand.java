package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteEdgeCommand;

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
//	/**
//	 * the edge layout of lhs and rhs edge
//	 */
//	private EdgeLayout edgeLayout;

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
		if (((TEdge) rhsEdge).getMarkerType()==null){
			// reconstruct marker, if not present
			Edge lhsEdge = RuleUtil.getLHSEdge(rhsEdge);
			if (lhsEdge==null)
				((TEdge) rhsEdge).setMarkerType(RuleUtil.NEW);
			else
				((TEdge) rhsEdge).setMarkerType(null);
		}
		if (((TEdge) rhsEdge).getMarkerType() != null) {
			// edge is currently marked as new and shall be demarked

			demark();
		} 
		else {
	// edge is currently not marked, thus mark it 

			mark();
		}
	}

	/**
	 * 
	 */
	private void mark() {
		Edge lhsEdge = RuleUtil.getLHSEdge(rhsEdge);
		if(lhsEdge!=null){
			add(new DeleteEdgeCommand(lhsEdge));
			// delete lhs edge
			super.execute();
			}
		((TEdge) rhsEdge).setMarkerType(RuleUtil.NEW);
	}

	/**
	 * 
	 */
	private void demark() {
		

			Node rhsSourceNode=rhsEdge.getSource();
			Node rhsTargetNode=rhsEdge.getTarget();

			
			// if some adjacent nodes are marked, then demark them first
			if(((TNode) rhsSourceNode).getMarkerType() != null) {
				//node is currently marked as new,
				// demark it
				add(new MarkCommand(rhsSourceNode));
			}
			if(((TNode) rhsTargetNode).getMarkerType() != null) {
				//node is currently marked as new,
				// demark it
				add(new MarkCommand(rhsTargetNode));
			}

			super.execute();

			Node lhsSourceNode = RuleUtil.getLHSNode(rhsSourceNode);
			Node lhsTargetNode = RuleUtil.getLHSNode(rhsTargetNode);

//		add(new CreateEdgeCommand(lhsSourceNode.getGraph(), lhsSourceNode, lhsTargetNode, rhsEdge.getType()));

			if(lhsSourceNode != null && lhsTargetNode != null){
				TEdge lhsEdge = TggFactory.eINSTANCE.createTEdge();
				lhsEdge.setSource(lhsSourceNode);
				lhsEdge.setTarget(lhsTargetNode);
				lhsEdge.setType(rhsEdge.getType());
				lhsEdge.setGraph(lhsSourceNode.getGraph());
				// remove marker
				((TEdge) rhsEdge).setMarkerType(null);
			}
			else System.out.println("Demarking of edge was not successful: source node or target node is inconsistent.");
			
	}


	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

}
