/**
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.MarkedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Marked Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.MarkedElementImpl#getIsMarked <em>Is Marked</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.MarkedElementImpl#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MarkedElementImpl extends EObjectImpl implements MarkedElement {
	/**
	 * The default value of the '{@link #getIsMarked() <em>Is Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsMarked()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_MARKED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsMarked() <em>Is Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsMarked()
	 * @generated
	 * @ordered
	 */
	protected Boolean isMarked = IS_MARKED_EDEFAULT;

	/**
	 * The default value of the '{@link #getMarkerType() <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkerType()
	 * @generated
	 * @ordered
	 */
	protected static final String MARKER_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMarkerType() <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkerType()
	 * @generated
	 * @ordered
	 */
	protected String markerType = MARKER_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MarkedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.MARKED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getIsMarked() {
		return isMarked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsMarked(Boolean newIsMarked) {
		Boolean oldIsMarked = isMarked;
		isMarked = newIsMarked;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.MARKED_ELEMENT__IS_MARKED, oldIsMarked, isMarked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMarkerType() {
		return markerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMarkerType(String newMarkerType) {
		String oldMarkerType = markerType;
		markerType = newMarkerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.MARKED_ELEMENT__MARKER_TYPE, oldMarkerType, markerType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
				return getIsMarked();
			case HenshinPackage.MARKED_ELEMENT__MARKER_TYPE:
				return getMarkerType();
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
			case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
				setIsMarked((Boolean)newValue);
				return;
			case HenshinPackage.MARKED_ELEMENT__MARKER_TYPE:
				setMarkerType((String)newValue);
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
			case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
				setIsMarked(IS_MARKED_EDEFAULT);
				return;
			case HenshinPackage.MARKED_ELEMENT__MARKER_TYPE:
				setMarkerType(MARKER_TYPE_EDEFAULT);
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
			case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
				return IS_MARKED_EDEFAULT == null ? isMarked != null : !IS_MARKED_EDEFAULT.equals(isMarked);
			case HenshinPackage.MARKED_ELEMENT__MARKER_TYPE:
				return MARKER_TYPE_EDEFAULT == null ? markerType != null : !MARKER_TYPE_EDEFAULT.equals(markerType);
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
		result.append(" (isMarked: ");
		result.append(isMarked);
		result.append(", markerType: ");
		result.append(markerType);
		result.append(')');
		return result.toString();
	}

} //MarkedElementImpl
