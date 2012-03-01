/**
 * HenshinModelCreationFactory.java
 *
 * Created 21.12.2011 - 16:59:52
 */
package de.tub.tfs.henshin.editor.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.HenshinFactory;

/**
 * @author nam
 * 
 */
public class HenshinModelCreationFactory extends EObjectsModelCreationFactory {

	/**
	 * @param factoryInstance
	 * @param modelClass
	 */
	public HenshinModelCreationFactory(EClass modelClass) {
		super(HenshinFactory.eINSTANCE, modelClass);
	}

}
