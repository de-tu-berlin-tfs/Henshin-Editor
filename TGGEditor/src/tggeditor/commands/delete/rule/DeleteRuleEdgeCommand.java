package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.EdgeLayout;
import tgg.TGG;
import tggeditor.commands.delete.DeleteEdgeCommand;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;

/**
 * The class DeleteRuleEdgeCommand deletes an edge in a rule. It deletes the edge in the rhs and 
 * in case of a preserved edge, it also deletes the corresponding edge in the lhs of the rule.
 */
public class DeleteRuleEdgeCommand extends CompoundCommand {

	/**
	 * thr rhs edge
	 */
	private Edge rhsEdge;
	/**
	 * thr rhs edge
	 */
	private EdgeLayout rhsedgeLayout;
	/**
	 * the lhs edge
	 */
	private Edge lhsEdge;
	
	/**the constructor
	 * @param edge the already created edge
	 */
	public DeleteRuleEdgeCommand(Edge edge) {
		

		rhsEdge = edge;
		lhsEdge = null;
		TGG layoutSystem=NodeUtil.getLayoutSystem(edge.getSource().getGraph());
		if (layoutSystem!=null){
			rhsedgeLayout = EdgeUtil.getEdgeLayout(edge, layoutSystem);
			lhsEdge=rhsedgeLayout.getLhsedge();
		}

		add(new DeleteEdgeCommand(rhsEdge));
		if (lhsEdge != null) {
			add(new DeleteEdgeCommand(lhsEdge));
		}
		
	}


	@Override
	public boolean canExecute() {
		return (rhsEdge != null && rhsedgeLayout!=null && super.canExecute());
	}

	@Override
	public boolean canUndo() {
		return (rhsEdge != null && rhsedgeLayout!=null && super.canUndo());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
	}

}
