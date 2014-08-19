/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.internal;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * The Class TransformationUnitsContainer.
 */
public abstract class ContainerEObject<T extends EObject,K extends EObject> extends EObjectImpl {

	/** The trans sys. */
	protected T model;

	/**
	 * Gets the units.
	 *
	 * @return the units
	 */
	public abstract List<K> getContainerChildren(); 

	/**
	 * Instantiates a new transformation units container.
	 *
	 * @param transformationSystem the transformation system
	 */
	public ContainerEObject(T model) {
		super();
		this.model = model;
	}

	/**
	 * Gets the transformation system.
	 *
	 * @return the transformation system
	 */
	public synchronized T getModel() {
		return model;
	}
	
	
}
