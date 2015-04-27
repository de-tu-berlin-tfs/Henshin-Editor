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
package TGG_correspondence;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen CORR</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link TGG_correspondence.GenCORR#getGenSRC <em>Gen SRC</em>}</li>
 *   <li>{@link TGG_correspondence.GenCORR#getGenTGT <em>Gen TGT</em>}</li>
 * </ul>
 * </p>
 *
 * @see TGG_correspondence.TGG_correspondencePackage#getGenCORR()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface GenCORR extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Gen SRC</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen SRC</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen SRC</em>' reference.
	 * @see #setGenSRC(EObject)
	 * @see TGG_correspondence.TGG_correspondencePackage#getGenCORR_GenSRC()
	 * @model
	 * @generated
	 */
	EObject getGenSRC();

	/**
	 * Sets the value of the '{@link TGG_correspondence.GenCORR#getGenSRC <em>Gen SRC</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen SRC</em>' reference.
	 * @see #getGenSRC()
	 * @generated
	 */
	void setGenSRC(EObject value);

	/**
	 * Returns the value of the '<em><b>Gen TGT</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen TGT</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen TGT</em>' reference.
	 * @see #setGenTGT(EObject)
	 * @see TGG_correspondence.TGG_correspondencePackage#getGenCORR_GenTGT()
	 * @model
	 * @generated
	 */
	EObject getGenTGT();

	/**
	 * Sets the value of the '{@link TGG_correspondence.GenCORR#getGenTGT <em>Gen TGT</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gen TGT</em>' reference.
	 * @see #getGenTGT()
	 * @generated
	 */
	void setGenTGT(EObject value);

} // GenCORR
