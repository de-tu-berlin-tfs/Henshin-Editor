/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 */
package correspondece.impl;

import correspondece.AC;
import correspondece.CorrespondecePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import source.Attribute;

import target.Column;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>AC</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link correspondece.impl.ACImpl#getAtr <em>Atr</em>}</li>
 *   <li>{@link correspondece.impl.ACImpl#getCol <em>Col</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ACImpl extends MinimalEObjectImpl.Container implements AC {
	/**
	 * The cached value of the '{@link #getAtr() <em>Atr</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAtr()
	 * @generated
	 * @ordered
	 */
	protected Attribute atr;

	/**
	 * The cached value of the '{@link #getCol() <em>Col</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCol()
	 * @generated
	 * @ordered
	 */
	protected Column col;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ACImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorrespondecePackage.Literals.AC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getAtr() {
		if (atr != null && atr.eIsProxy()) {
			InternalEObject oldAtr = (InternalEObject)atr;
			atr = (Attribute)eResolveProxy(oldAtr);
			if (atr != oldAtr) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.AC__ATR, oldAtr, atr));
			}
		}
		return atr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetAtr() {
		return atr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAtr(Attribute newAtr) {
		Attribute oldAtr = atr;
		atr = newAtr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.AC__ATR, oldAtr, atr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Column getCol() {
		if (col != null && col.eIsProxy()) {
			InternalEObject oldCol = (InternalEObject)col;
			col = (Column)eResolveProxy(oldCol);
			if (col != oldCol) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CorrespondecePackage.AC__COL, oldCol, col));
			}
		}
		return col;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Column basicGetCol() {
		return col;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCol(Column newCol) {
		Column oldCol = col;
		col = newCol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorrespondecePackage.AC__COL, oldCol, col));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorrespondecePackage.AC__ATR:
				if (resolve) return getAtr();
				return basicGetAtr();
			case CorrespondecePackage.AC__COL:
				if (resolve) return getCol();
				return basicGetCol();
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
			case CorrespondecePackage.AC__ATR:
				setAtr((Attribute)newValue);
				return;
			case CorrespondecePackage.AC__COL:
				setCol((Column)newValue);
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
			case CorrespondecePackage.AC__ATR:
				setAtr((Attribute)null);
				return;
			case CorrespondecePackage.AC__COL:
				setCol((Column)null);
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
			case CorrespondecePackage.AC__ATR:
				return atr != null;
			case CorrespondecePackage.AC__COL:
				return col != null;
		}
		return super.eIsSet(featureID);
	}

} //ACImpl
