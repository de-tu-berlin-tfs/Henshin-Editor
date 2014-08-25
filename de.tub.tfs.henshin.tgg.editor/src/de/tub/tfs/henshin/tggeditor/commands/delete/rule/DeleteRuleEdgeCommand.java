package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteEdgeCommand;


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
	 * the lhs edge
	 */
	private Edge lhsEdge;
	
	/**the constructor
	 * @param edge the already created edge
	 */
	public DeleteRuleEdgeCommand(Edge edge) {
		

		rhsEdge = edge;
		lhsEdge = RuleUtil.getLHSEdge(rhsEdge);
		

		add(new DeleteEdgeCommand(rhsEdge));
		if (lhsEdge != null) {
			add(new DeleteEdgeCommand(lhsEdge));
		}
		
	}


	@Override
	public boolean canExecute() {
		return (rhsEdge != null && super.canExecute());
	}

	@Override
	public boolean canUndo() {
		return (rhsEdge != null && super.canUndo());
	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
//	 */
//	@Override
//	public void execute() {
//		super.execute();
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
//	 */
//	@Override
//	public void undo() {
//		super.undo();
//	}

}
