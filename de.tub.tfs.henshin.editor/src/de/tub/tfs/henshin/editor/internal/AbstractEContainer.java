/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * @author nam
 * 
 */
public class AbstractEContainer<Child_T extends EObject, Parent_T extends EObject>
		extends EObjectImpl {

	protected Collection<Child_T> elements;

	protected Parent_T parent;

	/**
	 * 
	 */
	public AbstractEContainer(Collection<Child_T> elements) {
		this(elements, null);
	}

	/**
	 * 
	 */
	public AbstractEContainer(Parent_T parent) {
		this(null, parent);
	}

	/**
	 * @param elements
	 * @param parent
	 */
	public AbstractEContainer(Collection<Child_T> elements, Parent_T parent) {
		super();

		this.elements = elements;
		this.parent = parent;
	}

	/**
	 * @return the elements
	 */
	public Collection<Child_T> getElements() {
		return elements;
	}

	/**
	 * @param elements
	 *            the elements to set
	 */
	public void setElements(Collection<Child_T> elements) {
		this.elements = elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#eContainer()
	 */
	@Override
	public EObject eContainer() {
		return parent;
	}
}
