/**
 */
package de.tub.tfs.henshin.tgg.impl;

import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.TggPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.henshin.model.Edge;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.EdgeLayoutImpl#isNew <em>New</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.EdgeLayoutImpl#getLhsedge <em>Lhsedge</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.EdgeLayoutImpl#getRhsedge <em>Rhsedge</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.EdgeLayoutImpl#getRhsTranslated <em>Rhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.EdgeLayoutImpl#getLhsTranslated <em>Lhs Translated</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.impl.EdgeLayoutImpl#isCritical <em>Critical</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EdgeLayoutImpl extends CDOObjectImpl implements EdgeLayout {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeLayoutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TggPackage.Literals.EDGE_LAYOUT;
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
	public boolean isNew() {
		return (Boolean)eGet(TggPackage.Literals.EDGE_LAYOUT__NEW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNew(boolean newNew) {
		eSet(TggPackage.Literals.EDGE_LAYOUT__NEW, newNew);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getLhsedge() {
		return (Edge)eGet(TggPackage.Literals.EDGE_LAYOUT__LHSEDGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsedge(Edge newLhsedge) {
		eSet(TggPackage.Literals.EDGE_LAYOUT__LHSEDGE, newLhsedge);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getRhsedge() {
		return (Edge)eGet(TggPackage.Literals.EDGE_LAYOUT__RHSEDGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsedge(Edge newRhsedge) {
		eSet(TggPackage.Literals.EDGE_LAYOUT__RHSEDGE, newRhsedge);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getRhsTranslated() {
		return (Boolean)eGet(TggPackage.Literals.EDGE_LAYOUT__RHS_TRANSLATED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsTranslated(Boolean newRhsTranslated) {
		eSet(TggPackage.Literals.EDGE_LAYOUT__RHS_TRANSLATED, newRhsTranslated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getLhsTranslated() {
		return (Boolean)eGet(TggPackage.Literals.EDGE_LAYOUT__LHS_TRANSLATED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsTranslated(Boolean newLhsTranslated) {
		eSet(TggPackage.Literals.EDGE_LAYOUT__LHS_TRANSLATED, newLhsTranslated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCritical() {
		return (Boolean)eGet(TggPackage.Literals.EDGE_LAYOUT__CRITICAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCritical(boolean newCritical) {
		eSet(TggPackage.Literals.EDGE_LAYOUT__CRITICAL, newCritical);
	}

} //EdgeLayoutImpl
