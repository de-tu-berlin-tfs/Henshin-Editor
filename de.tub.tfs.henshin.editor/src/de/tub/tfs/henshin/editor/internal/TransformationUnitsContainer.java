/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * The Class TransformationUnitsContainer.
 */
public class TransformationUnitsContainer extends
		AbstractEContainer<TransformationUnit, TransformationSystem> {

	/**
	 * @param model
	 */
	public TransformationUnitsContainer(TransformationSystem parent) {
		super(parent.getTransformationUnits(), parent);
	}
}
