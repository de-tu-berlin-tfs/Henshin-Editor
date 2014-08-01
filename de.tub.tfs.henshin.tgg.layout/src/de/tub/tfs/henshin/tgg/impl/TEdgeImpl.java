/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TElem;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.impl.EdgeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TEdge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TEdgeImpl#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TEdgeImpl extends EdgeImpl implements TEdge {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TEDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 8;
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
				case TggPackage.TEDGE__MARKER_TYPE: return TggPackage.TELEM__MARKER_TYPE;
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
				case TggPackage.TELEM__MARKER_TYPE: return TggPackage.TEDGE__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TEdgeImpl
