/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.TElem;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.impl.RuleImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGRuleImpl#getMarkerType <em>Marker Type</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGRuleImpl#getIsMarked <em>Is Marked</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.TGGRuleImpl#isManualMatchingOrder <em>Manual Matching Order</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TGGRuleImpl extends RuleImpl implements TGGRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.TGG_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 16;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getIsMarked() {
		return (Boolean)eGet(TggPackage.Literals.TGG_RULE__IS_MARKED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsMarked(Boolean newIsMarked) {
		eSet(TggPackage.Literals.TGG_RULE__IS_MARKED, newIsMarked);
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
	public boolean isManualMatchingOrder() {
		return (Boolean)eGet(TggPackage.Literals.TGG_RULE__MANUAL_MATCHING_ORDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManualMatchingOrder(boolean newManualMatchingOrder) {
		eSet(TggPackage.Literals.TGG_RULE__MANUAL_MATCHING_ORDER, newManualMatchingOrder);
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
				case TggPackage.TGG_RULE__MARKER_TYPE: return TggPackage.TELEM__MARKER_TYPE;
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
				case TggPackage.TELEM__MARKER_TYPE: return TggPackage.TGG_RULE__MARKER_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TGGRuleImpl
