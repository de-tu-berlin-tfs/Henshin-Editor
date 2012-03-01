/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class PriorityUnitTreeEditPart.
 */
public class PriorityUnitTreeEditPart extends
		TransformationUnitTreeEditPart<PriorityUnit> {

	/**
	 * Instantiates a new priority unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public PriorityUnitTreeEditPart(PriorityUnit model) {
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
			return IconUtil.getIcon("priority16.png");
		} catch (Exception e) {
			return null;
		}
	}

}
