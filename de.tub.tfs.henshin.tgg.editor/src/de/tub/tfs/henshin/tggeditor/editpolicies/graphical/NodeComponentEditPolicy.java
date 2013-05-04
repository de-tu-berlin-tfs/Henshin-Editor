package de.tub.tfs.henshin.tggeditor.editpolicies.graphical;


import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteRuleNodeCommand;
import de.tub.tfs.henshin.tggeditor.util.ExceptionUtil;


public class NodeComponentEditPolicy extends ComponentEditPolicy implements
		EditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if(!(getHost().getModel() instanceof TNode)) {ExceptionUtil.error("Node cannot be deleted, because it is not a TNode."); return null;}
		TNode node = (TNode) getHost().getModel();
		if (node.getGraph()==null) {ExceptionUtil.error("Node cannot be deleted, because its containing graph is missing"); return null;}
		Rule rule = node.getGraph().getRule();
		if (rule != null) {//node in a rule
			return new DeleteRuleNodeCommand(node);
		} else {//node in a graph
			return new DeleteNodeCommand(node);
		}
	}
}
