package tggeditor.editpolicies.graphical;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import tggeditor.commands.create.CreateAttributeCommand;

public class NodeLayoutEditPolicy extends LayoutEditPolicy {
	
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new NonResizableEditPolicy();
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Attribute) {
			Node node = (Node) getHost().getModel();
			if (node.getType() != null) {
				if ((node.getType().getEAllAttributes().size()
						- node.getAttributes().size() > 0))
					command = new CreateAttributeCommand(node,"New Attribute");
			}
		}
		return command;
	}

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		// TODO Auto-generated method stub
		return null;
	}

}
