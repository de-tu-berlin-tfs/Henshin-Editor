/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class ConditionalUnitTreeEditPart.
 */
public class ConditionalUnitTreeEditPart extends
		TransformationUnitTreeEditPart<ConditionalUnit> {

	/**
	 * Instantiates a new conditional unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public ConditionalUnitTreeEditPart(ConditionalUnit model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		// Ressource nicht vorhanden, oder fehlerhaft, dann lieber kein Bild,
		// als Absturz
		try {
			return IconUtil.getIcon("conditionalUnit18.png");
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int type = notification.getEventType();
		switch (type) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
		case Notification.MOVE:
		case Notification.SET:
			refreshChildren();
			refreshVisuals();
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new Vector<Object>();

		list.addAll(TransformationUnitUtil
				.createConditionalUnitParts(getCastedModel()));

//		list.addAll(super.getModelChildren());

		return list;
	}

}
