/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TEdge;
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
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TEdgeImpl#getIsMarked <em>Is Marked</em>}</li>
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
		return 5;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getIsMarked() {
		return (Boolean)eGet(TggPackage.Literals.TEDGE__IS_MARKED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsMarked(Boolean newIsMarked) {
		eSet(TggPackage.Literals.TEDGE__IS_MARKED, newIsMarked);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMarkerType() {
		return (String)eGet(TggPackage.Literals.TEDGE__MARKER_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMarkerType(String newMarkerType) {
		eSet(TggPackage.Literals.TEDGE__MARKER_TYPE, newMarkerType);
	}

} //TEdgeImpl
