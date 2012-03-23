package tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tggeditor.commands.delete.rule.DeleteParameterCommand;

public class ParameterComponentEditPolicy extends ComponentEditPolicy {
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteParameterCommand((Parameter) getHost().getModel());
	}
}
