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
package lu.uni.snt.repeat.rEPEAT;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Log Expr Binary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getFst <em>Fst</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getOperator <em>Operator</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getSnd <em>Snd</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Expr_Binary()
 * @model
 * @generated
 */
public interface Log_Expr_Binary extends Log_Expr_T
{
  /**
	 * Returns the value of the '<em><b>Fst</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fst</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Fst</em>' containment reference.
	 * @see #setFst(Log_Expr)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Expr_Binary_Fst()
	 * @model containment="true"
	 * @generated
	 */
  Log_Expr getFst();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getFst <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fst</em>' containment reference.
	 * @see #getFst()
	 * @generated
	 */
  void setFst(Log_Expr value);

  /**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operator</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see #setOperator(String)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Expr_Binary_Operator()
	 * @model
	 * @generated
	 */
  String getOperator();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see #getOperator()
	 * @generated
	 */
  void setOperator(String value);

  /**
	 * Returns the value of the '<em><b>Snd</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Snd</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Snd</em>' containment reference.
	 * @see #setSnd(Log_Expr)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getLog_Expr_Binary_Snd()
	 * @model containment="true"
	 * @generated
	 */
  Log_Expr getSnd();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Log_Expr_Binary#getSnd <em>Snd</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Snd</em>' containment reference.
	 * @see #getSnd()
	 * @generated
	 */
  void setSnd(Log_Expr value);

} // Log_Expr_Binary
