/**
 * 
 */
package de.tub.tfs.henshin.editor.model;

import org.eclipse.emf.ecore.EClass;

import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;

/**
 * TODO comments here
 * 
 * @author nam
 * 
 */
public class FlowControlModelCreationFactory extends
		EObjectsModelCreationFactory {

	/**
	 * @param factoryInstance
	 * @param modelClass
	 */
	public FlowControlModelCreationFactory(EClass modelClass) {
		super(FlowControlFactory.eINSTANCE, modelClass);
	}
}
