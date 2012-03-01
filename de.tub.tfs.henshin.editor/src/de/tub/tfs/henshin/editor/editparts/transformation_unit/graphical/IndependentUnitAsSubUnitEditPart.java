/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.figure.transformation_unit.SubUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class IndependentUnitAsSubUnitEditPart.
 */
public class IndependentUnitAsSubUnitEditPart extends
		SubUnitEditPart<IndependentUnit> {

	/**
	 * Instantiates a new independent unit as sub unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param transformationUnit
	 *            the transformation unit
	 * @param model
	 *            the model
	 */
	public IndependentUnitAsSubUnitEditPart(TransUnitPage transUnitPage,
			TransformationUnit transformationUnit, IndependentUnit model) {
		super(transUnitPage, transformationUnit, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("independent20.png");
		} catch (Exception e) {
			return null;
		}
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
		figure.setBackgroundColor(ColorUtil.getColor(5));
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
		case HenshinPackage.INDEPENDENT_UNIT__NAME:
			((SubUnitFigure) getFigure()).setName(getCastedModel().getName());
			return;
		}
	}

}
