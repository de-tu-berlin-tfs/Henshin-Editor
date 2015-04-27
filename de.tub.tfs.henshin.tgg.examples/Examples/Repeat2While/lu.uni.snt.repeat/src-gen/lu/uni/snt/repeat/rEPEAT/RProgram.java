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
 * A representation of the model object '<em><b>RProgram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.RProgram#getFst <em>Fst</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRProgram()
 * @model
 * @generated
 */
public interface RProgram extends Source
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
	 * @see #setFst(Stmnt_LST_Elem)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getRProgram_Fst()
	 * @model containment="true"
	 * @generated
	 */
  Stmnt_LST_Elem getFst();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.RProgram#getFst <em>Fst</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fst</em>' containment reference.
	 * @see #getFst()
	 * @generated
	 */
  void setFst(Stmnt_LST_Elem value);

} // RProgram
