/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit;

import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.DeleteTransformationUnit;

/**
 * The Class TransformationUnitComponentEditPolicy.
 */
public class TransformationUnitComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		final TransformationUnit unitToDelete = (TransformationUnit) getHost()
				.getModel();
		return new DeleteTransformationUnit(unitToDelete);
	}

}
