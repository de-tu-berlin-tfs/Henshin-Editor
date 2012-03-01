/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.AddTransformationUnitCommand;

/**
 * The Class ConditionalUnitPartASSubUnitXYLayoutEditPolicy.
 */
public class TransformationUnitPartASSubUnitXYLayoutEditPolicy extends
		XYLayoutEditPolicy implements EditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
	 * createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse
	 * .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Rule) {
			command = new AddTransformationUnitCommand(
					(TransformationUnit) getHost().getModel(),
					(TransformationUnit) request.getNewObject());

		}
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getAddCommand
	 * (org.eclipse.gef.Request)
	 */
	@Override
	protected Command getAddCommand(Request generic) {
		return null;
	}

}
