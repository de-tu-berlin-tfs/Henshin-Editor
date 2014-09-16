/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package correspondece.impl;

import correspondece.AFK;
import correspondece.CorrespondecePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import source.Association;

import target.FKey;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>AFK</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link correspondece.impl.AFKImpl#getAss <em>Ass</em>}</li>
 *   <li>{@link correspondece.impl.AFKImpl#getFkey <em>Fkey</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AFKImpl extends EObjectImpl implements AFK {
	/**
	 * The cached value of the '{@link #getAss() <em>Ass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAss()
	 * @generated
	 * @ordered
	 */
	protected Association ass;

	/**
	 * The cached value of the '{@link #getFkey() <em>Fkey</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFkey()
	 * @generated
	 * @ordered
	 */
	protected FKey fkey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AFKImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondecePackage.Literals.AFK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Association getAss() {
		if (ass != null && ass.eIsProxy()) {
			InternalEObject oldAss = (InternalEObject)ass;
			ass = (Association)eResolveProxy(oldAss);
			if (ass != oldAss) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.AFK__ASS, oldAss, ass));
			}
		}
		return ass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Association basicGetAss() {
		return ass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAss(Association newAss) {
		Association oldAss = ass;
		ass = newAss;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.AFK__ASS, oldAss, ass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FKey getFkey() {
		if (fkey != null && fkey.eIsProxy()) {
			InternalEObject oldFkey = (InternalEObject)fkey;
			fkey = (FKey)eResolveProxy(oldFkey);
			if (fkey != oldFkey) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.AFK__FKEY, oldFkey, fkey));
			}
		}
		return fkey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FKey basicGetFkey() {
		return fkey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFkey(FKey newFkey) {
		FKey oldFkey = fkey;
		fkey = newFkey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.AFK__FKEY, oldFkey, fkey));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondecePackage.AFK__ASS:
				if (resolve) return getAss();
				return basicGetAss();
			case CorrespondecePackage.AFK__FKEY:
				if (resolve) return getFkey();
				return basicGetFkey();
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
			case CorrespondecePackage.AFK__ASS:
				setAss((Association)newValue);
				return;
			case CorrespondecePackage.AFK__FKEY:
				setFkey((FKey)newValue);
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
			case CorrespondecePackage.AFK__ASS:
				setAss((Association)null);
				return;
			case CorrespondecePackage.AFK__FKEY:
				setFkey((FKey)null);
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
			case CorrespondecePackage.AFK__ASS:
				return ass != null;
			case CorrespondecePackage.AFK__FKEY:
				return fkey != null;
		}
		return super.eIsSet(featureID);
	}

} //AFKImpl
