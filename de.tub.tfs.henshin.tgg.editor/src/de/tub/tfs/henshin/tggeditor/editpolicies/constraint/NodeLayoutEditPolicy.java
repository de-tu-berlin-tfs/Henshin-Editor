package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateAttributeCommand;

public class NodeLayoutEditPolicy extends de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = super.getCreateCommand(request);
		
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
	
}
