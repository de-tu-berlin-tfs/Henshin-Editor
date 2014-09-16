/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;

/**
 * The Class TransformationUnitsContainer.
 */
public class TransformationUnitParametersContainer extends
		AbstractEContainer<Parameter, Unit> {

	/**
	 * @param model
	 */
	public TransformationUnitParametersContainer(Unit model) {
		super(model.getParameters(), model);
	}
}
