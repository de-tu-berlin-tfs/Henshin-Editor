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
 * A representation of the model object '<em><b>Var</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.Var#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getVar()
 * @model
 * @generated
 */
public interface Var extends Unary
{
  /**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Label</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see lu.uni.snt.whileDSL.wHILE.WHILEPackage#getVar_Label()
	 * @model
	 * @generated
	 */
  String getLabel();

  /**
	 * Sets the value of the '{@link lu.uni.snt.whileDSL.wHILE.Var#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
  void setLabel(String value);

} // Var
