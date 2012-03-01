/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.impl.TransformationUnitImpl;

/**
 * @author Johann
 * 
 */
public class TransformationUnitPart<T extends TransformationUnit> extends
		TransformationUnitImpl {

	/** The feature. */
	private final EStructuralFeature feature;

	/** The model. */
	private final T model;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new conditional unit part.
	 * 
	 * @param model
	 *            the model
	 * @param name
	 *            the name
	 * @param feature
	 *            the feature
	 */
	public TransformationUnitPart(T model, String name,
			EStructuralFeature feature) {
		super();
		this.model = model;
		this.name = name;
		this.feature = feature;
	}

	/**
	 * Gets the feature.
	 * 
	 * @return the feature
	 */
	public synchronized EStructuralFeature getFeature() {
		return feature;
	}

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public synchronized T getModel() {
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.model.DescribedElement#getDescription()
	 */
	@Override
	public String getDescription() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.model.DescribedElement#setDescription(java.lang
	 * .String)
	 */
	@Override
	public void setDescription(String value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.model.NamedElement#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.model.NamedElement#setName(java.lang.String)
	 */
	@Override
	public void setName(String value) {
		this.name = value;

	}


	@Override
	protected EList<TransformationUnit> getSubUnits() {
		// TODO Auto-generated method stub
		return null;
	}

}
