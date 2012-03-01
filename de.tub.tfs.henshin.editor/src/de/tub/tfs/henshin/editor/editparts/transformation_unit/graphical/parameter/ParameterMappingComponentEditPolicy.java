package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter;

import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.DeletePortMappingCommand;

/**
 * The Class PortMappingComponentEditPolicy.
 */
public class ParameterMappingComponentEditPolicy extends ConnectionEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConnectionEditPolicy#getDeleteCommand(org
	 * .eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		return new DeletePortMappingCommand((ParameterMapping) getHost()
				.getModel());
	}

}
