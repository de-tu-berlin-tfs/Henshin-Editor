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

import org.eclipse.emf.henshin.model.Attribute;

import tgg.AttributeLayout;
import tgg.TGGPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link tgg.impl.AttributeLayoutImpl#isNew <em>New</em>}</li>
 *   <li>{@link tgg.impl.AttributeLayoutImpl#getLhsattribute <em>Lhsattribute</em>}</li>
 *   <li>{@link tgg.impl.AttributeLayoutImpl#getRhsattribute <em>Rhsattribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeLayoutImpl extends EObjectImpl implements AttributeLayout {
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
	 * The cached value of the '{@link #getLhsattribute() <em>Lhsattribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsattribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute lhsattribute;

	/**
	 * The cached value of the '{@link #getRhsattribute() <em>Rhsattribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsattribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute rhsattribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeLayoutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGPackage.Literals.ATTRIBUTE_LAYOUT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.ATTRIBUTE_LAYOUT__NEW, oldNew, new_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getLhsattribute() {
		if (lhsattribute != null && lhsattribute.eIsProxy()) {
			InternalEObject oldLhsattribute = (InternalEObject)lhsattribute;
			lhsattribute = (Attribute)eResolveProxy(oldLhsattribute);
			if (lhsattribute != oldLhsattribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.ATTRIBUTE_LAYOUT__LHSATTRIBUTE, oldLhsattribute, lhsattribute));
			}
		}
		return lhsattribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetLhsattribute() {
		return lhsattribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhsattribute(Attribute newLhsattribute) {
		Attribute oldLhsattribute = lhsattribute;
		lhsattribute = newLhsattribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.ATTRIBUTE_LAYOUT__LHSATTRIBUTE, oldLhsattribute, lhsattribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getRhsattribute() {
		if (rhsattribute != null && rhsattribute.eIsProxy()) {
			InternalEObject oldRhsattribute = (InternalEObject)rhsattribute;
			rhsattribute = (Attribute)eResolveProxy(oldRhsattribute);
			if (rhsattribute != oldRhsattribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TGGPackage.ATTRIBUTE_LAYOUT__RHSATTRIBUTE, oldRhsattribute, rhsattribute));
			}
		}
		return rhsattribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetRhsattribute() {
		return rhsattribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhsattribute(Attribute newRhsattribute) {
		Attribute oldRhsattribute = rhsattribute;
		rhsattribute = newRhsattribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TGGPackage.ATTRIBUTE_LAYOUT__RHSATTRIBUTE, oldRhsattribute, rhsattribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TGGPackage.ATTRIBUTE_LAYOUT__NEW:
				return isNew();
			case TGGPackage.ATTRIBUTE_LAYOUT__LHSATTRIBUTE:
				if (resolve) return getLhsattribute();
				return basicGetLhsattribute();
			case TGGPackage.ATTRIBUTE_LAYOUT__RHSATTRIBUTE:
				if (resolve) return getRhsattribute();
				return basicGetRhsattribute();
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
			case TGGPackage.ATTRIBUTE_LAYOUT__NEW:
				setNew((Boolean)newValue);
				return;
			case TGGPackage.ATTRIBUTE_LAYOUT__LHSATTRIBUTE:
				setLhsattribute((Attribute)newValue);
				return;
			case TGGPackage.ATTRIBUTE_LAYOUT__RHSATTRIBUTE:
				setRhsattribute((Attribute)newValue);
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
			case TGGPackage.ATTRIBUTE_LAYOUT__NEW:
				setNew(NEW_EDEFAULT);
				return;
			case TGGPackage.ATTRIBUTE_LAYOUT__LHSATTRIBUTE:
				setLhsattribute((Attribute)null);
				return;
			case TGGPackage.ATTRIBUTE_LAYOUT__RHSATTRIBUTE:
				setRhsattribute((Attribute)null);
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
			case TGGPackage.ATTRIBUTE_LAYOUT__NEW:
				return new_ != NEW_EDEFAULT;
			case TGGPackage.ATTRIBUTE_LAYOUT__LHSATTRIBUTE:
				return lhsattribute != null;
			case TGGPackage.ATTRIBUTE_LAYOUT__RHSATTRIBUTE:
				return rhsattribute != null;
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
		result.append(')');
		return result.toString();
	}

} //AttributeLayoutImpl
