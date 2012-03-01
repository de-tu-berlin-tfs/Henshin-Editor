/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.figure.transformation_unit.SubUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author nam
 * 
 */
public class LoopUnitAsSubUnitEditPart extends SubUnitEditPart<LoopUnit> {

	/**
	 * @param transUnitPage
	 * @param transformationUnit
	 * @param model
	 */
	public LoopUnitAsSubUnitEditPart(TransUnitPage transUnitPage,
			TransformationUnit transformationUnit, LoopUnit model) {
		super(transUnitPage, transformationUnit, model);
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
	 * @see
	 * henshineditor.editparts.transformation.SubUnitEditPart#createFigure()
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
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		final int featureId = notification.getFeatureID(HenshinPackage.class);

		switch (featureId) {
		case HenshinPackage.LOOP_UNIT__NAME:
			((SubUnitFigure) getFigure()).setName(getCastedModel().getName());

			return;
		}
	}
}
