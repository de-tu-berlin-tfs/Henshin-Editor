package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import org.eclipse.emf.henshin.model.And;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.constraint.DeleteAndCommand;

public class AndComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteAndCommand((And)getHost().getModel());
	}
	
}
