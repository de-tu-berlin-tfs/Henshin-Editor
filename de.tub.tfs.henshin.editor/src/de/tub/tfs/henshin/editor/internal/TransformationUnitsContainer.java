/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;

/**
 * The Class TransformationUnitsContainer.
 */
public class TransformationUnitsContainer extends
		AbstractEContainer<Unit, Module> {

	/**
	 * @param model
	 */
	public TransformationUnitsContainer(Module parent) {
		super(parent.getUnits(), parent);
	}
}
