/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.condition.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.condition.ConditionComponentEditPolicy;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * @author angel
 * 
 */
public class FormulaEditPart<T extends Formula> extends
		AdapterGraphicalEditPart<T> {

	/**
	 * @param model
	 */
	public FormulaEditPart(T model) {
		super(model);
		// TODO Auto-generated constructor stub
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
		if (notification.getEventType() == HenshinNotification.TREE_SELECTED) {
			getViewer().select(this);

			return;
		}

		super.notifyChanged(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#fireSelectionChanged()
	 */
	@Override
	protected void fireSelectionChanged() {
		if (getSelected() == SELECTED_PRIMARY) {
			getCastedModel().eNotify(
					new NotificationImpl(HenshinNotification.SELECTED, false,
							true));
		}

		super.fireSelectionChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		removeEditPolicy(EditPolicy.COMPONENT_ROLE);
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ConditionComponentEditPolicy());
	}

}
