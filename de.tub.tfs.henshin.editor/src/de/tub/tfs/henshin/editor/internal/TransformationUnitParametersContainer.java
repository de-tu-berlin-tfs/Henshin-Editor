/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * The Class TransformationUnitsContainer.
 */
public class TransformationUnitParametersContainer extends
		AbstractEContainer<Parameter, TransformationUnit> {

	/**
	 * @param model
	 */
	public TransformationUnitParametersContainer(TransformationUnit model) {
		super(model.getParameters(), model);
	}
}
