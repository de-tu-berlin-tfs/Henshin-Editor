/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class SequentialUnitTreeEditPart.
 */
public class SequentialUnitTreeEditPart extends
		TransformationUnitTreeEditPart<SequentialUnit> {

	/**
	 * Instantiates a new sequential unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public SequentialUnitTreeEditPart(SequentialUnit model) {
		super(model);
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
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#addChild(org.eclipse.gef.EditPart
	 * , int)
	 */
	@Override
	protected void addChild(EditPart child, int index) {
		for (Object o : getChildren()) {
			if (((EditPart) o).getModel() == child.getModel()) {
				return;
			}
		}

		super.addChild(child, index);
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
		// TODO Neue Image
		try {
			return IconUtil.getIcon("seqUnit18.png");
		} catch (Exception e) {
			return null;
		}
	}
}
