/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.AddTransformationUnitCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterCommand;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class TransformationUnitXYLayoutEditPolicy.
 */
public class TransformationUnitXYLayoutEditPolicy extends XYLayoutEditPolicy
		implements EditPolicy {

	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		// TODO Auto-generated method stub
		return super.createChangeConstraintCommand(request, child, constraint);
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
		if (request.getNewObject() instanceof Parameter) {
			Unit tUnit = (Unit) getHost()
					.getModel();
			Parameter parameter = (Parameter) request.getNewObject();
			return new CreateParameterCommand(tUnit, parameter);
		}
		if (request.getNewObject() instanceof Rule) {
			final Rectangle constraint = (Rectangle) getConstraintFor(request);
			if (getHostFigure().containsPoint(constraint.x, constraint.y)) {
				Unit tUnit = (Unit) getHost()
						.getModel();
				if (TransformationUnitUtil.getSubUnitsFeature(tUnit) != null) {
					return new AddTransformationUnitCommand(tUnit,
							(Unit) request.getNewObject());
				}
			}
		}
		return command;
	}

}
