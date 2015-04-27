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
package lu.uni.snt.whileDSL.wHILE.impl;

import lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem;
import lu.uni.snt.whileDSL.wHILE.WHILEPackage;
import lu.uni.snt.whileDSL.wHILE.WProgram;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>WProgram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.impl.WProgramImpl#getFst <em>Fst</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WProgramImpl extends TargetImpl implements WProgram
{
  /**
	 * The cached value of the '{@link #getFst() <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getFst()
	 * @generated
	 * @ordered
	 */
  protected Fgmnt_LST_Elem fst;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected WProgramImpl()
  {
		super();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected EClass eStaticClass()
  {
		return WHILEPackage.Literals.WPROGRAM;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Fgmnt_LST_Elem getFst()
  {
		return fst;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetFst(Fgmnt_LST_Elem newFst, NotificationChain msgs)
  {
		Fgmnt_LST_Elem oldFst = fst;
		fst = newFst;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WHILEPackage.WPROGRAM__FST, oldFst, newFst);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setFst(Fgmnt_LST_Elem newFst)
  {
		if (newFst != fst) {
			NotificationChain msgs = null;
			if (fst != null)
				msgs = ((InternalEObject)fst).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WHILEPackage.WPROGRAM__FST, null, msgs);
			if (newFst != null)
				msgs = ((InternalEObject)newFst).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WHILEPackage.WPROGRAM__FST, null, msgs);
			msgs = basicSetFst(newFst, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WHILEPackage.WPROGRAM__FST, newFst, newFst));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
		switch (featureID) {
			case WHILEPackage.WPROGRAM__FST:
				return basicSetFst(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
		switch (featureID) {
			case WHILEPackage.WPROGRAM__FST:
				return getFst();
		}
		return super.eGet(featureID, resolve, coreType);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void eSet(int featureID, Object newValue)
  {
		switch (featureID) {
			case WHILEPackage.WPROGRAM__FST:
				setFst((Fgmnt_LST_Elem)newValue);
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
  public void eUnset(int featureID)
  {
		switch (featureID) {
			case WHILEPackage.WPROGRAM__FST:
				setFst((Fgmnt_LST_Elem)null);
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
  public boolean eIsSet(int featureID)
  {
		switch (featureID) {
			case WHILEPackage.WPROGRAM__FST:
				return fst != null;
		}
		return super.eIsSet(featureID);
	}

} //WProgramImpl
