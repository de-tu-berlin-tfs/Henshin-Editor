/**
 * CompoundActivityEditPart.java
 *
 * Created 27.12.2011 - 15:23:25
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.compound_activity;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.FlowElementEditPart;
import de.tub.tfs.henshin.editor.figure.flow_diagram.CompoundActivityFigure;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;

/**
 * @author nam
 * 
 */
public class CompoundActivityEditPart extends
		FlowElementEditPart<CompoundActivity> {

	/**
	 * @param model
	 */
	public CompoundActivityEditPart(CompoundActivity model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.ActivityEditPart
	 * #hookCreateFigure()
	 */
	@Override
	protected IFigure hookCreateFigure() {
		CompoundActivityFigure fig = new CompoundActivityFigure();

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.ActivityEditPart
	 * #createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();

		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new CompoundActivityComponentEditPolicy());

		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new CompoundActivityLayoutEditPolicy());

		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE,
				new CompoundActivityHighlightEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.ActivityEditPart
	 * #getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new LinkedList<Object>();

		children.addAll(getCastedModel().getChildren());

		return children;
	}
}
