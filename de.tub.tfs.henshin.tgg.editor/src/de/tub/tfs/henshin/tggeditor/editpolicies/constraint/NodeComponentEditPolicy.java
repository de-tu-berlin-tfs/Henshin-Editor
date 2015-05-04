package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.constraint.DeleteNodeCommand;

public class NodeComponentEditPolicy extends ComponentEditPolicy implements EditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNodeCommand((Node)getHost().getModel());
	}
	
}
