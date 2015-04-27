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
package lu.uni.snt.repeat.rEPEAT.impl;

import lu.uni.snt.repeat.rEPEAT.REPEATPackage;
import lu.uni.snt.repeat.rEPEAT.RProgram;
import lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RProgram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.RProgramImpl#getFst <em>Fst</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RProgramImpl extends SourceImpl implements RProgram
{
  /**
	 * The cached value of the '{@link #getFst() <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getFst()
	 * @generated
	 * @ordered
	 */
  protected Stmnt_LST_Elem fst;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected RProgramImpl()
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
		return REPEATPackage.Literals.RPROGRAM;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Stmnt_LST_Elem getFst()
  {
		return fst;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetFst(Stmnt_LST_Elem newFst, NotificationChain msgs)
  {
		Stmnt_LST_Elem oldFst = fst;
		fst = newFst;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, REPEATPackage.RPROGRAM__FST, oldFst, newFst);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setFst(Stmnt_LST_Elem newFst)
  {
		if (newFst != fst) {
			NotificationChain msgs = null;
			if (fst != null)
				msgs = ((InternalEObject)fst).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.RPROGRAM__FST, null, msgs);
			if (newFst != null)
				msgs = ((InternalEObject)newFst).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.RPROGRAM__FST, null, msgs);
			msgs = basicSetFst(newFst, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.RPROGRAM__FST, newFst, newFst));
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
			case REPEATPackage.RPROGRAM__FST:
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
			case REPEATPackage.RPROGRAM__FST:
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
			case REPEATPackage.RPROGRAM__FST:
				setFst((Stmnt_LST_Elem)newValue);
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
			case REPEATPackage.RPROGRAM__FST:
				setFst((Stmnt_LST_Elem)null);
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
			case REPEATPackage.RPROGRAM__FST:
				return fst != null;
		}
		return super.eIsSet(featureID);
	}

} //RProgramImpl
