package de.tub.tfs.henshin.editor.editparts.graph;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.graph.DeleteAttributeCommand;

/**
 * The Class AttributeComponentEditPolicy.
 */
public class AttributeComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteAttributeCommand((Attribute) getHost().getModel());
	}

}
