/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TElem;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;

import de.tub.tfs.henshin.tgg.TripleComponent;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.impl.NodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TNodeImpl#getMarkerType <em>Marker Type</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TNodeImpl#getX <em>X</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TNodeImpl#getY <em>Y</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TNodeImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TNodeImpl extends NodeImpl implements TNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TNODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 9;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getX() {
		return (Integer)eGet(TggPackage.Literals.TNODE__X, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(int newX) {
		eSet(TggPackage.Literals.TNODE__X, newX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getY() {
		return (Integer)eGet(TggPackage.Literals.TNODE__Y, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(int newY) {
		eSet(TggPackage.Literals.TNODE__Y, newY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TripleComponent getComponent() {
		return (TripleComponent)eGet(TggPackage.Literals.TNODE__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(TripleComponent newComponent) {
		eSet(TggPackage.Literals.TNODE__COMPONENT, newComponent);
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
				case TggPackage.TNODE__MARKER_TYPE: return TggPackage.TELEM__MARKER_TYPE;
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
				case TggPackage.TELEM__MARKER_TYPE: return TggPackage.TNODE__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TNodeImpl
