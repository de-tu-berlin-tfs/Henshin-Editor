package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.constraint.DeleteOrCommand;

public class OrComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteOrCommand((Or)getHost().getModel());
	}
	
}
