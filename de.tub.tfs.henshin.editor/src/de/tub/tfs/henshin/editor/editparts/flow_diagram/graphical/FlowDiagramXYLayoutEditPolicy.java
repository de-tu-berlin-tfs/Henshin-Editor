/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowElementCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.UnNestActivityCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;
import de.tub.tfs.muvitor.gef.editparts.policies.MuvitorXYLayoutEditPolicy;

/**
 * @author nam
 * 
 */
public class FlowDiagramXYLayoutEditPolicy extends MuvitorXYLayoutEditPolicy {

	/**
     * 
     */
	private FlowDiagram model;

	/**
	 * @param model
	 */
	public FlowDiagramXYLayoutEditPolicy(FlowDiagram model) {
		super();
		this.model = model;
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
		Object newObj = request.getNewObject();
		IFigure hostFigure = getHostFigure();
		IFigure childFigure = hostFigure.findFigureAt(request.getLocation());

		if (newObj instanceof FlowElement
				&& (childFigure == null || childFigure == hostFigure)) {

			return new CreateFlowElementCommand<FlowElement>(
					(FlowElement) newObj, model, request.getLocation());
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand
	 * (org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	protected Command createAddCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) {

		Object childModel = child.getModel();

		CompoundCommand cmd = new CompoundCommand();

		if (childModel instanceof Activity) {
			cmd.add(new UnNestActivityCommand((Activity) childModel));

			cmd.add(createChangeConstraintCommand(request, child, constraint));

			return cmd;
		}

		return super.createAddCommand(request, child, constraint);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#
	 * createChangeConstraintCommand
	 * (org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {

		CompoundCommand cmd = new CompoundCommand("Move Elements");

		Rectangle bound = (Rectangle) constraint;

		Object model = child.getModel();

		if (model instanceof FlowElement) {
			FlowElementLayout layout = HenshinLayoutUtil.INSTANCE
					.getLayout((FlowElement) model);

			cmd.add(new SetEObjectFeatureValueCommand(layout, Integer
					.valueOf(bound.x),
					HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__X));
			cmd.add(new SetEObjectFeatureValueCommand(layout, Integer
					.valueOf(bound.y),
					HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT__Y));
		}

		return cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.editparts.policies.MuvitorXYLayoutEditPolicy#
	 * setConstraint(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.draw2d.geometry.Rectangle)
	 */
	@Override
	protected void setConstraint(EObject model, Rectangle constraint) {
	}
}
