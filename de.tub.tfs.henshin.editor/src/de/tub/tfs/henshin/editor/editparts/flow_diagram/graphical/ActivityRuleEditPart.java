/**
 * ActivityRuleEditPart.java
 *
 * Created 27.12.2011 - 15:39:56
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.editor.figure.flow_diagram.ActivityRuleFigure;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * @author nam
 * 
 */
public class ActivityRuleEditPart extends AdapterGraphicalEditPart<Rule> {

	/**
	 * @param model
	 */
	public ActivityRuleEditPart(Rule model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		ActivityRuleFigure fig = new ActivityRuleFigure();

		fig.setName(getCastedModel().getName());

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#notifyChanged
	 * (org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		if (notification.getEventType() != Notification.REMOVING_ADAPTER) {
			refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		((ActivityRuleFigure) getFigure()).setName(getCastedModel().getName());

		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command createDeleteCommand(GroupRequest deleteRequest) {
				return new SetActivityContentCommand((Activity) getParent()
						.getModel(), null);
			}
		});
	}

}
