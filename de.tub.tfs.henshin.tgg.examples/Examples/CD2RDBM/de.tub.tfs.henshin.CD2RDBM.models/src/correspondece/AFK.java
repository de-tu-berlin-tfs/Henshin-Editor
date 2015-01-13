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
package correspondece;

import org.eclipse.emf.ecore.EObject;

import source.Association;

import target.FKey;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>AFK</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link correspondece.AFK#getAss <em>Ass</em>}</li>
 *   <li>{@link correspondece.AFK#getFkey <em>Fkey</em>}</li>
 * </ul>
 * </p>
 *
 * @see correspondece.CorrespondecePackage#getAFK()
 * @model
 * @generated
 */
public interface AFK extends EObject {
	/**
	 * Returns the value of the '<em><b>Ass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ass</em>' reference.
	 * @see #setAss(Association)
	 * @see correspondece.CorrespondecePackage#getAFK_Ass()
	 * @model
	 * @generated
	 */
	Association getAss();

	/**
	 * Sets the value of the '{@link correspondece.AFK#getAss <em>Ass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ass</em>' reference.
	 * @see #getAss()
	 * @generated
	 */
	void setAss(Association value);

	/**
	 * Returns the value of the '<em><b>Fkey</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fkey</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fkey</em>' reference.
	 * @see #setFkey(FKey)
	 * @see correspondece.CorrespondecePackage#getAFK_Fkey()
	 * @model
	 * @generated
	 */
	FKey getFkey();

	/**
	 * Sets the value of the '{@link correspondece.AFK#getFkey <em>Fkey</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fkey</em>' reference.
	 * @see #getFkey()
	 * @generated
	 */
	void setFkey(FKey value);

} // AFK
