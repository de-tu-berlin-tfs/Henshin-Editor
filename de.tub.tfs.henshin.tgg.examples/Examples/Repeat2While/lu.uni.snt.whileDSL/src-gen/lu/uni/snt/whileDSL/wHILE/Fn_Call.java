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
package lu.uni.snt.whileDSL.wHILE;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fn Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Fn_Call#getNameF <em>Name F</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getFn_Call()
 * @model
 * @generated
 */
public interface Fn_Call extends Fgmnt_LST_Elem
{
  /**
	 * Returns the value of the '<em><b>Name F</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name F</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Name F</em>' attribute.
	 * @see #setNameF(String)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getFn_Call_NameF()
	 * @model
	 * @generated
	 */
  String getNameF();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Fn_Call#getNameF <em>Name F</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name F</em>' attribute.
	 * @see #getNameF()
	 * @generated
	 */
  void setNameF(String value);

} // Fn_Call
