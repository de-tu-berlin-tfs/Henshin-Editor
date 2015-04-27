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

import lu.uni.snt.repeat.rEPEAT.Log_Expr;
import lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary;
import lu.uni.snt.repeat.rEPEAT.REPEATPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Log Expr Binary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl#getFst <em>Fst</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.Log_Expr_BinaryImpl#getSnd <em>Snd</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class Log_Expr_BinaryImpl extends Log_Expr_TImpl implements Log_Expr_Binary
{
  /**
	 * The cached value of the '{@link #getFst() <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getFst()
	 * @generated
	 * @ordered
	 */
  protected Log_Expr fst;

  /**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
  protected static final String OPERATOR_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
  protected String operator = OPERATOR_EDEFAULT;

  /**
	 * The cached value of the '{@link #getSnd() <em>Snd</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSnd()
	 * @generated
	 * @ordered
	 */
  protected Log_Expr snd;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected Log_Expr_BinaryImpl()
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
		return REPEATPackage.Literals.LOG_EXPR_BINARY;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Expr getFst()
  {
		return fst;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetFst(Log_Expr newFst, NotificationChain msgs)
  {
		Log_Expr oldFst = fst;
		fst = newFst;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, REPEATPackage.LOG_EXPR_BINARY__FST, oldFst, newFst);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setFst(Log_Expr newFst)
  {
		if (newFst != fst) {
			NotificationChain msgs = null;
			if (fst != null)
				msgs = ((InternalEObject)fst).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.LOG_EXPR_BINARY__FST, null, msgs);
			if (newFst != null)
				msgs = ((InternalEObject)newFst).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.LOG_EXPR_BINARY__FST, null, msgs);
			msgs = basicSetFst(newFst, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.LOG_EXPR_BINARY__FST, newFst, newFst));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getOperator()
  {
		return operator;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setOperator(String newOperator)
  {
		String oldOperator = operator;
		operator = newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.LOG_EXPR_BINARY__OPERATOR, oldOperator, operator));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Expr getSnd()
  {
		return snd;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetSnd(Log_Expr newSnd, NotificationChain msgs)
  {
		Log_Expr oldSnd = snd;
		snd = newSnd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, REPEATPackage.LOG_EXPR_BINARY__SND, oldSnd, newSnd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setSnd(Log_Expr newSnd)
  {
		if (newSnd != snd) {
			NotificationChain msgs = null;
			if (snd != null)
				msgs = ((InternalEObject)snd).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.LOG_EXPR_BINARY__SND, null, msgs);
			if (newSnd != null)
				msgs = ((InternalEObject)newSnd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.LOG_EXPR_BINARY__SND, null, msgs);
			msgs = basicSetSnd(newSnd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.LOG_EXPR_BINARY__SND, newSnd, newSnd));
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
			case REPEATPackage.LOG_EXPR_BINARY__FST:
				return basicSetFst(null, msgs);
			case REPEATPackage.LOG_EXPR_BINARY__SND:
				return basicSetSnd(null, msgs);
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
			case REPEATPackage.LOG_EXPR_BINARY__FST:
				return getFst();
			case REPEATPackage.LOG_EXPR_BINARY__OPERATOR:
				return getOperator();
			case REPEATPackage.LOG_EXPR_BINARY__SND:
				return getSnd();
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
			case REPEATPackage.LOG_EXPR_BINARY__FST:
				setFst((Log_Expr)newValue);
				return;
			case REPEATPackage.LOG_EXPR_BINARY__OPERATOR:
				setOperator((String)newValue);
				return;
			case REPEATPackage.LOG_EXPR_BINARY__SND:
				setSnd((Log_Expr)newValue);
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
			case REPEATPackage.LOG_EXPR_BINARY__FST:
				setFst((Log_Expr)null);
				return;
			case REPEATPackage.LOG_EXPR_BINARY__OPERATOR:
				setOperator(OPERATOR_EDEFAULT);
				return;
			case REPEATPackage.LOG_EXPR_BINARY__SND:
				setSnd((Log_Expr)null);
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
			case REPEATPackage.LOG_EXPR_BINARY__FST:
				return fst != null;
			case REPEATPackage.LOG_EXPR_BINARY__OPERATOR:
				return OPERATOR_EDEFAULT == null ? operator != null : !OPERATOR_EDEFAULT.equals(operator);
			case REPEATPackage.LOG_EXPR_BINARY__SND:
				return snd != null;
		}
		return super.eIsSet(featureID);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String toString()
  {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (operator: ");
		result.append(operator);
		result.append(')');
		return result.toString();
	}

} //Log_Expr_BinaryImpl
