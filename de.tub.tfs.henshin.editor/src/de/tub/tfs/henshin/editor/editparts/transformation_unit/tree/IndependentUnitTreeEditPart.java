/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class IndependentUnitTreeEditPart.
 */
public class IndependentUnitTreeEditPart extends
		TransformationUnitTreeEditPart<IndependentUnit> {

	/**
	 * Instantiates a new independent unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public IndependentUnitTreeEditPart(IndependentUnit model) {
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
		// TODO Neue Image
		try {
			return IconUtil.getIcon("independent16.png");
		} catch (Exception e) {
			return null;
		}
	}

}
