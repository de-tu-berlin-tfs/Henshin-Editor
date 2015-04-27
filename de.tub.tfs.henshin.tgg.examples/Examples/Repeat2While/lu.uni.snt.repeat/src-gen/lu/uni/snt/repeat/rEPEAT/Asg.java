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
 * A representation of the model object '<em><b>Asg</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Asg#getLeft <em>Left</em>}</li>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.Asg#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getAsg()
 * @model
 * @generated
 */
public interface Asg extends Stmnt_LST_Elem
{
  /**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Left</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(Sym)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getAsg_Left()
	 * @model containment="true"
	 * @generated
	 */
  Sym getLeft();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Asg#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
  void setLeft(Sym value);

  /**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Right</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(Sym)
	 * @see lu.uni.snt.repeat.rEPEAT.REPEATPackage#getAsg_Right()
	 * @model containment="true"
	 * @generated
	 */
  Sym getRight();

  /**
	 * Sets the value of the '{@link lu.uni.snt.repeat.rEPEAT.Asg#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
  void setRight(Sym value);

} // Asg
