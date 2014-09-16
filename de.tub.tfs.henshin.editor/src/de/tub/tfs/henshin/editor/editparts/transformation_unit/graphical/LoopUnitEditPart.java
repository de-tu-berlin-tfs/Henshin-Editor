/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.figure.transformation_unit.TransformationUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author nam
 * 
 */
public class LoopUnitEditPart extends TransformationUnitEditPart<LoopUnit> {

	/**
	 * @param transUnitPage
	 * @param model
	 */
	public LoopUnitEditPart(TransUnitPage transUnitPage, LoopUnit model) {
		super(transUnitPage, model);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.
	 * TransformationUnitEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.LOOP.img(32);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.getColor(6));
		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.
	 * TransformationUnitEditPart
	 * #notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		final int featureId = notification.getFeatureID(HenshinPackage.class);

		switch (featureId) {
		case HenshinPackage.LOOP_UNIT__NAME:
			((TransformationUnitFigure) getFigure()).setName(getCastedModel()
					.getName());
			return;
		}
	}
}
