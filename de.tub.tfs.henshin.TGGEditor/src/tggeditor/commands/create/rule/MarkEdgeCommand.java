package tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tggeditor.commands.delete.DeleteEdgeCommand;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;
import tggeditor.util.RuleUtil;
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

			Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge(
					lhsSourceNode, lhsTargetNode, rhsEdge.getType());
			lhsSourceNode.getGraph().getEdges().add(lhsEdge);
			
			// remove marker
			rhsEdge.setMarkerType(RuleUtil.NEW);
			rhsEdge.setIsMarked(false);
		} 
		else {
			// edge is currently not marked, thus mark it 
			rhsEdge.setMarkerType(RuleUtil.NEW);
			rhsEdge.setIsMarked(true);
			// delete lhs edge
			Edge lhsEdge = RuleUtil.getLHSEdge(rhsEdge);
			if(lhsEdge!=null){
				add(new DeleteEdgeCommand(lhsEdge));
			super.execute();
			}
		}
	}


	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

}
