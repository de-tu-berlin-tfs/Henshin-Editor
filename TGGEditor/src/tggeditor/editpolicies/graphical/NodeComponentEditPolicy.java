package tggeditor.editpolicies.graphical;


import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tggeditor.commands.delete.DeleteNodeCommand;
import tggeditor.commands.delete.rule.DeleteRuleNodeCommand;

public class NodeComponentEditPolicy extends ComponentEditPolicy implements
		EditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Node node = (Node) getHost().getModel();
		Rule rule = node.getGraph().getContainerRule();
		if (rule != null) {//node in a rule
			return new DeleteRuleNodeCommand(node);
		} else {//node in a graph
			return new DeleteNodeCommand(node);
		}
	}
}
