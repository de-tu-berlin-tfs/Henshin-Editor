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
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Imported Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.ImportedPackage#isLoadWithDefaultValues <em>Load With Default Values</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.ImportedPackage#getPackage <em>Package</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.ImportedPackage#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getImportedPackage()
 * @model
 * @generated
 */
public interface ImportedPackage extends EObject {
	/**
	 * Returns the value of the '<em><b>Load With Default Values</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load With Default Values</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load With Default Values</em>' attribute.
	 * @see #setLoadWithDefaultValues(boolean)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getImportedPackage_LoadWithDefaultValues()
	 * @model
	 * @generated
	 */
	boolean isLoadWithDefaultValues();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.ImportedPackage#isLoadWithDefaultValues <em>Load With Default Values</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load With Default Values</em>' attribute.
	 * @see #isLoadWithDefaultValues()
	 * @generated
	 */
	void setLoadWithDefaultValues(boolean value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' reference.
	 * @see #setPackage(EPackage)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getImportedPackage_Package()
	 * @model
	 * @generated
	 */
	EPackage getPackage();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.ImportedPackage#getPackage <em>Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' reference.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(EPackage value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' attribute.
	 * The literals are from the enumeration {@link de.tub.tfs.henshin.tgg.TripleComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' attribute.
	 * @see de.tub.tfs.henshin.tgg.TripleComponent
	 * @see #setComponent(TripleComponent)
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getImportedPackage_Component()
	 * @model
	 * @generated
	 */
	TripleComponent getComponent();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.ImportedPackage#getComponent <em>Component</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' attribute.
	 * @see de.tub.tfs.henshin.tgg.TripleComponent
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(TripleComponent value);

} // ImportedPackage
