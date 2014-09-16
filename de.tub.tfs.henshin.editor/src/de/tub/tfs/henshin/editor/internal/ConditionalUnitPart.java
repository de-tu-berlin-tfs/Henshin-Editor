/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.ConditionalUnit;

/**
 * The Class ConditionalUnitPart.
 */
public class ConditionalUnitPart extends
		TransformationUnitPart<ConditionalUnit> {

	/**
	 * @param model
	 * @param name
	 * @param feature
	 */
	public ConditionalUnitPart(ConditionalUnit model, String name,
			EStructuralFeature feature) {
		super(model, name, feature);
	}

}
