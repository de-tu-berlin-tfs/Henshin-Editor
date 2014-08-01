/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TElem;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.impl.AttributeImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TAttribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TAttributeImpl#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TAttributeImpl extends AttributeImpl implements TAttribute {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 7;
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TElem.class) {
			switch (derivedFeatureID) {
				case TggPackage.TATTRIBUTE__MARKER_TYPE: return TggPackage.TELEM__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TElem.class) {
			switch (baseFeatureID) {
				case TggPackage.TELEM__MARKER_TYPE: return TggPackage.TATTRIBUTE__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TAttributeImpl
