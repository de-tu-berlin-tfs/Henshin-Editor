/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter.ParameterEditPart;
import de.tub.tfs.henshin.editor.figure.transformation_unit.TransformationUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;

/**
 * The Class SequentialUnitEditPart.
 */
public class SequentialUnitEditPart extends
		TransformationUnitEditPart<SequentialUnit> {

	/**
	 * Instantiates a new sequential unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param model
	 *            the model
	 */
	public SequentialUnitEditPart(TransUnitPage transUnitPage,
			SequentialUnit model) {
		super(transUnitPage, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.getColor(1));
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
		case HenshinPackage.SEQUENTIAL_UNIT__NAME:
			((TransformationUnitFigure) getFigure()).setName(getCastedModel()
					.getName());
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#addChild(org.eclipse.gef.EditPart
	 * , int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void addChild(EditPart child, int index) {
		if (child instanceof ParameterEditPart){
			super.addChild(child, index);
			return;
		}
		
		for (Object o : getChildren()) {
			if (((EditPart) o).getModel() == child.getModel()) {
				SequentialUnitSubEditPart castedChild = (SequentialUnitSubEditPart) o;

				castedChild.setCounter(castedChild.getCounter() + 1);

				return;
			}
		}

		super.addChild(new SequentialUnitSubEditPart(
				(SubUnitEditPart<TransformationUnit>) child), index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		@SuppressWarnings("unchecked")
		List<Object> children = new LinkedList<Object>(getChildren());

		for (Object o : children) {
			removeChild((EditPart) o);
		}

		List<?> modelChildren = getModelChildren();

		for (Object o : modelChildren) {
			addChild(createChild(o), -1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("seqUnit26.png");
		} catch (Exception e) {
			return null;
		}
	}
}
