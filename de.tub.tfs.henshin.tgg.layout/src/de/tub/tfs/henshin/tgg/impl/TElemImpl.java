/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TElem;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TElem</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TElemImpl#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TElemImpl extends EObjectImpl implements TElem {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TElemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TELEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMarkerType() {
		return (String)eGet(TggPackage.Literals.TELEM__MARKER_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMarkerType(String newMarkerType) {
		eSet(TggPackage.Literals.TELEM__MARKER_TYPE, newMarkerType);
	}

} //TElemImpl
