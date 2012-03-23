/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package source.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import source.Attribute;
import source.PrimitiveDataType;
import source.SourcePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link source.impl.AttributeImpl#getName <em>Name</em>}</li>
 *   <li>{@link source.impl.AttributeImpl#isIs_primary <em>Is primary</em>}</li>
 *   <li>{@link source.impl.AttributeImpl#getType <em>Type</em>}</li>
 *   <li>{@link source.impl.AttributeImpl#getPtype <em>Ptype</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeImpl extends EObjectImpl implements Attribute {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isIs_primary() <em>Is primary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIs_primary()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PRIMARY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIs_primary() <em>Is primary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIs_primary()
	 * @generated
	 * @ordered
	 */
	protected boolean is_primary = IS_PRIMARY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected source.Class type;

	/**
	 * The cached value of the '{@link #getPtype() <em>Ptype</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPtype()
	 * @generated
	 * @ordered
	 */
	protected PrimitiveDataType ptype;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcePackage.Literals.ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ATTRIBUTE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIs_primary() {
		return is_primary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIs_primary(boolean newIs_primary) {
		boolean oldIs_primary = is_primary;
		is_primary = newIs_primary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ATTRIBUTE__IS_PRIMARY, oldIs_primary, is_primary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (source.Class)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcePackage.ATTRIBUTE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public source.Class basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(source.Class newType) {
		source.Class oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ATTRIBUTE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimitiveDataType getPtype() {
		return ptype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPtype(PrimitiveDataType newPtype, NotificationChain msgs) {
		PrimitiveDataType oldPtype = ptype;
		ptype = newPtype;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SourcePackage.ATTRIBUTE__PTYPE, oldPtype, newPtype);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPtype(PrimitiveDataType newPtype) {
		if (newPtype != ptype) {
			NotificationChain msgs = null;
			if (ptype != null)
				msgs = ((InternalEObject)ptype).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SourcePackage.ATTRIBUTE__PTYPE, null, msgs);
			if (newPtype != null)
				msgs = ((InternalEObject)newPtype).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SourcePackage.ATTRIBUTE__PTYPE, null, msgs);
			msgs = basicSetPtype(newPtype, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcePackage.ATTRIBUTE__PTYPE, newPtype, newPtype));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SourcePackage.ATTRIBUTE__PTYPE:
				return basicSetPtype(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SourcePackage.ATTRIBUTE__NAME:
				return getName();
			case SourcePackage.ATTRIBUTE__IS_PRIMARY:
				return isIs_primary();
			case SourcePackage.ATTRIBUTE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case SourcePackage.ATTRIBUTE__PTYPE:
				return getPtype();
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
			case SourcePackage.ATTRIBUTE__NAME:
				setName((String)newValue);
				return;
			case SourcePackage.ATTRIBUTE__IS_PRIMARY:
				setIs_primary((Boolean)newValue);
				return;
			case SourcePackage.ATTRIBUTE__TYPE:
				setType((source.Class)newValue);
				return;
			case SourcePackage.ATTRIBUTE__PTYPE:
				setPtype((PrimitiveDataType)newValue);
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
			case SourcePackage.ATTRIBUTE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SourcePackage.ATTRIBUTE__IS_PRIMARY:
				setIs_primary(IS_PRIMARY_EDEFAULT);
				return;
			case SourcePackage.ATTRIBUTE__TYPE:
				setType((source.Class)null);
				return;
			case SourcePackage.ATTRIBUTE__PTYPE:
				setPtype((PrimitiveDataType)null);
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
			case SourcePackage.ATTRIBUTE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SourcePackage.ATTRIBUTE__IS_PRIMARY:
				return is_primary != IS_PRIMARY_EDEFAULT;
			case SourcePackage.ATTRIBUTE__TYPE:
				return type != null;
			case SourcePackage.ATTRIBUTE__PTYPE:
				return ptype != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", is_primary: ");
		result.append(is_primary);
		result.append(')');
		return result.toString();
	}

} //AttributeImpl
