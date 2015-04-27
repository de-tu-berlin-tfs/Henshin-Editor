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
import lu.uni.snt.repeat.rEPEAT.REPEATPackage;
import lu.uni.snt.repeat.rEPEAT.Repeat;
import lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repeat</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.RepeatImpl#getStmnt <em>Stmnt</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.RepeatImpl#getExpr <em>Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepeatImpl extends Stmnt_LST_ElemImpl implements Repeat
{
  /**
	 * The cached value of the '{@link #getStmnt() <em>Stmnt</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getStmnt()
	 * @generated
	 * @ordered
	 */
  protected Stmnt_LST_Elem stmnt;

  /**
	 * The cached value of the '{@link #getExpr() <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getExpr()
	 * @generated
	 * @ordered
	 */
  protected Log_Expr expr;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected RepeatImpl()
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
		return REPEATPackage.Literals.REPEAT;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Stmnt_LST_Elem getStmnt()
  {
		return stmnt;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetStmnt(Stmnt_LST_Elem newStmnt, NotificationChain msgs)
  {
		Stmnt_LST_Elem oldStmnt = stmnt;
		stmnt = newStmnt;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, REPEATPackage.REPEAT__STMNT, oldStmnt, newStmnt);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setStmnt(Stmnt_LST_Elem newStmnt)
  {
		if (newStmnt != stmnt) {
			NotificationChain msgs = null;
			if (stmnt != null)
				msgs = ((InternalEObject)stmnt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.REPEAT__STMNT, null, msgs);
			if (newStmnt != null)
				msgs = ((InternalEObject)newStmnt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.REPEAT__STMNT, null, msgs);
			msgs = basicSetStmnt(newStmnt, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.REPEAT__STMNT, newStmnt, newStmnt));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Log_Expr getExpr()
  {
		return expr;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetExpr(Log_Expr newExpr, NotificationChain msgs)
  {
		Log_Expr oldExpr = expr;
		expr = newExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, REPEATPackage.REPEAT__EXPR, oldExpr, newExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setExpr(Log_Expr newExpr)
  {
		if (newExpr != expr) {
			NotificationChain msgs = null;
			if (expr != null)
				msgs = ((InternalEObject)expr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.REPEAT__EXPR, null, msgs);
			if (newExpr != null)
				msgs = ((InternalEObject)newExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - REPEATPackage.REPEAT__EXPR, null, msgs);
			msgs = basicSetExpr(newExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.REPEAT__EXPR, newExpr, newExpr));
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
			case REPEATPackage.REPEAT__STMNT:
				return basicSetStmnt(null, msgs);
			case REPEATPackage.REPEAT__EXPR:
				return basicSetExpr(null, msgs);
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
			case REPEATPackage.REPEAT__STMNT:
				return getStmnt();
			case REPEATPackage.REPEAT__EXPR:
				return getExpr();
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
			case REPEATPackage.REPEAT__STMNT:
				setStmnt((Stmnt_LST_Elem)newValue);
				return;
			case REPEATPackage.REPEAT__EXPR:
				setExpr((Log_Expr)newValue);
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
			case REPEATPackage.REPEAT__STMNT:
				setStmnt((Stmnt_LST_Elem)null);
				return;
			case REPEATPackage.REPEAT__EXPR:
				setExpr((Log_Expr)null);
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
			case REPEATPackage.REPEAT__STMNT:
				return stmnt != null;
			case REPEATPackage.REPEAT__EXPR:
				return expr != null;
		}
		return super.eIsSet(featureID);
	}

} //RepeatImpl
