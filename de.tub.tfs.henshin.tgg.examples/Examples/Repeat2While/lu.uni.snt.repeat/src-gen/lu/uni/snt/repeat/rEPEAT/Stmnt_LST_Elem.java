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
 * A representation of the model object '<em><b>Stmnt LST Elem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem#getNext <em>Next</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getStmnt_LST_Elem()
 * @model
 * @generated
 */
public interface Stmnt_LST_Elem extends Source
{
  /**
	 * Returns the value of the '<em><b>Next</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Next</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Next</em>' containment reference.
	 * @see #setNext(Stmnt_LST_Elem)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getStmnt_LST_Elem_Next()
	 * @model containment="true"
	 * @generated
	 */
  Stmnt_LST_Elem getNext();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Stmnt_LST_Elem#getNext <em>Next</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next</em>' containment reference.
	 * @see #getNext()
	 * @generated
	 */
  void setNext(Stmnt_LST_Elem value);

} // Stmnt_LST_Elem
