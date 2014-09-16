/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.parameter;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.DeleteParameterCommand;

/**
 * The Class ParameterComponentEditPolicy.
 */
public class ParameterComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteParameterCommand((Parameter) getHost().getModel());
	}

}
