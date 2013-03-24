package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteEdgeCommand;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

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
		if (rhsEdge.getIsMarked()==null){
			// reconstruct marker, if not present
			Edge lhsEdge = RuleUtil.getLHSEdge(rhsEdge);
			if (lhsEdge==null)
				rhsEdge.setIsMarked(true);
			else
				rhsEdge.setIsMarked(false);
		}
		if (rhsEdge.getIsMarked()) {
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
		rhsEdge.setMarkerType(RuleUtil.NEW);
		rhsEdge.setIsMarked(true);
	}

	/**
	 * 
	 */
	private void demark() {
		

			Node rhsSourceNode=rhsEdge.getSource();
			Node rhsTargetNode=rhsEdge.getTarget();

			
			// if some adjacent nodes are marked, then demark them first
			if(rhsSourceNode.getIsMarked()) {
				//node is currently marked as new,
				// demark it
				add(new MarkCommand(rhsSourceNode));
			}
			if(rhsTargetNode.getIsMarked()) {
				//node is currently marked as new,
				// demark it
				add(new MarkCommand(rhsTargetNode));
			}

			super.execute();

			Node lhsSourceNode = RuleUtil.getLHSNode(rhsSourceNode);
			Node lhsTargetNode = RuleUtil.getLHSNode(rhsTargetNode);

//		add(new CreateEdgeCommand(lhsSourceNode.getGraph(), lhsSourceNode, lhsTargetNode, rhsEdge.getType()));


			Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge(
					lhsSourceNode, lhsTargetNode, rhsEdge.getType());
			lhsEdge.setGraph(lhsSourceNode.getGraph());
			
			// remove marker
			rhsEdge.setMarkerType(RuleUtil.NEW);
			rhsEdge.setIsMarked(false);
	}


	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

}