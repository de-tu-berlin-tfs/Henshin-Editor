/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package tgg.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.henshin.model.Edge;

import tgg.EdgeLayout;
import tgg.TGGPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tgg.impl.EdgeLayoutImpl#isNew <em>New</em>}</li>
 *   <li>{@link tgg.impl.EdgeLayoutImpl#getLhsedge <em>Lhsedge</em>}</li>
 *   <li>{@link tgg.impl.EdgeLayoutImpl#getRhsedge <em>Rhsedge</em>}</li>
 *   <li>{@link tgg.impl.EdgeLayoutImpl#getRhsTranslated <em>Rhs Translated</em>}</li>
 *   <li>{@link tgg.impl.EdgeLayoutImpl#getLhsTranslated <em>Lhs Translated</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EdgeLayoutImpl extends EObjectImpl implements EdgeLayout {
	/**
	 * The default value of the '{@link #isNew() <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNew()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEW_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNew() <em>New</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNew()
	 * @generated
	 * @ordered
	 */
	protected boolean new_ = NEW_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLhsedge() <em>Lhsedge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsedge()
	 * @generated
	 * @ordered
	 */
	protected Edge lhsedge;

	/**
	 * The cached value of the '{@link #getRhsedge() <em>Rhsedge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsedge()
	 * @generated
	 * @ordered
	 */
	protected Edge rhsedge;

	/**
	 * The default value of the '{@link #getRhsTranslated() <em>Rhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean RHS_TRANSLATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRhsTranslated() <em>Rhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected Boolean rhsTranslated = RHS_TRANSLATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getLhsTranslated() <em>Lhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean LHS_TRANSLATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLhsTranslated() <em>Lhs Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsTranslated()
	 * @generated
	 * @ordered
	 */
	protected Boolean lhsTranslated = LHS_TRANSLATED_EDEFAULT;

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
		return TGGPackage.Literals.EDGE_LAYOUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNew() {
		return new_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNew(boolean newNew) {
		boolean oldNew = new_;
		new_ = newNew;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.EDGE_LAYOUT__NEW, oldNew, new_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getLhsedge() {
		if (lhsedge != null && lhsedge.eIsProxy()) {
			InternalEObject oldLhsedge = (InternalEObject)lhsedge;
			lhsedge = (Edge)eResolveProxy(oldLhsedge);
			if (lhsedge != oldLhsedge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.EDGE_LAYOUT__LHSEDGE, oldLhsedge, lhsedge));
			}
		}
		return lhsedge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetLhsedge() {
		return lhsedge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsedge(Edge newLhsedge) {
		Edge oldLhsedge = lhsedge;
		lhsedge = newLhsedge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.EDGE_LAYOUT__LHSEDGE, oldLhsedge, lhsedge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getRhsedge() {
		if (rhsedge != null && rhsedge.eIsProxy()) {
			InternalEObject oldRhsedge = (InternalEObject)rhsedge;
			rhsedge = (Edge)eResolveProxy(oldRhsedge);
			if (rhsedge != oldRhsedge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.EDGE_LAYOUT__RHSEDGE, oldRhsedge, rhsedge));
			}
		}
		return rhsedge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetRhsedge() {
		return rhsedge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsedge(Edge newRhsedge) {
		Edge oldRhsedge = rhsedge;
		rhsedge = newRhsedge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.EDGE_LAYOUT__RHSEDGE, oldRhsedge, rhsedge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getRhsTranslated() {
		return rhsTranslated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsTranslated(Boolean newRhsTranslated) {
		Boolean oldRhsTranslated = rhsTranslated;
		rhsTranslated = newRhsTranslated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.EDGE_LAYOUT__RHS_TRANSLATED, oldRhsTranslated, rhsTranslated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getLhsTranslated() {
		return lhsTranslated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsTranslated(Boolean newLhsTranslated) {
		Boolean oldLhsTranslated = lhsTranslated;
		lhsTranslated = newLhsTranslated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.EDGE_LAYOUT__LHS_TRANSLATED, oldLhsTranslated, lhsTranslated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TGGPackage.EDGE_LAYOUT__NEW:
				return isNew();
			case TGGPackage.EDGE_LAYOUT__LHSEDGE:
				if (resolve) return getLhsedge();
				return basicGetLhsedge();
			case TGGPackage.EDGE_LAYOUT__RHSEDGE:
				if (resolve) return getRhsedge();
				return basicGetRhsedge();
			case TGGPackage.EDGE_LAYOUT__RHS_TRANSLATED:
				return getRhsTranslated();
			case TGGPackage.EDGE_LAYOUT__LHS_TRANSLATED:
				return getLhsTranslated();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TGGPackage.EDGE_LAYOUT__NEW:
				setNew((Boolean)newValue);
				return;
			case TGGPackage.EDGE_LAYOUT__LHSEDGE:
				setLhsedge((Edge)newValue);
				return;
			case TGGPackage.EDGE_LAYOUT__RHSEDGE:
				setRhsedge((Edge)newValue);
				return;
			case TGGPackage.EDGE_LAYOUT__RHS_TRANSLATED:
				setRhsTranslated((Boolean)newValue);
				return;
			case TGGPackage.EDGE_LAYOUT__LHS_TRANSLATED:
				setLhsTranslated((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TGGPackage.EDGE_LAYOUT__NEW:
				setNew(NEW_EDEFAULT);
				return;
			case TGGPackage.EDGE_LAYOUT__LHSEDGE:
				setLhsedge((Edge)null);
				return;
			case TGGPackage.EDGE_LAYOUT__RHSEDGE:
				setRhsedge((Edge)null);
				return;
			case TGGPackage.EDGE_LAYOUT__RHS_TRANSLATED:
				setRhsTranslated(RHS_TRANSLATED_EDEFAULT);
				return;
			case TGGPackage.EDGE_LAYOUT__LHS_TRANSLATED:
				setLhsTranslated(LHS_TRANSLATED_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TGGPackage.EDGE_LAYOUT__NEW:
				return new_ != NEW_EDEFAULT;
			case TGGPackage.EDGE_LAYOUT__LHSEDGE:
				return lhsedge != null;
			case TGGPackage.EDGE_LAYOUT__RHSEDGE:
				return rhsedge != null;
			case TGGPackage.EDGE_LAYOUT__RHS_TRANSLATED:
				return RHS_TRANSLATED_EDEFAULT == null ? rhsTranslated != null : !RHS_TRANSLATED_EDEFAULT.equals(rhsTranslated);
			case TGGPackage.EDGE_LAYOUT__LHS_TRANSLATED:
				return LHS_TRANSLATED_EDEFAULT == null ? lhsTranslated != null : !LHS_TRANSLATED_EDEFAULT.equals(lhsTranslated);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (new: ");
		result.append(new_);
		result.append(", rhsTranslated: ");
		result.append(rhsTranslated);
		result.append(", lhsTranslated: ");
		result.append(lhsTranslated);
		result.append(')');
		return result.toString();
	}

} //EdgeLayoutImpl
