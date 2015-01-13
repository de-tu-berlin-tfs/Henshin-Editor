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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CORR</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link TGG_correspondence.CORR#getTgt <em>Tgt</em>}</li>
 *   <li>{@link TGG_correspondence.CORR#getSrc <em>Src</em>}</li>
 * </ul>
 * </p>
 *
 * @see TGG_correspondence.TGG_correspondencePackage#getCORR()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CORR extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Tgt</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link TGG_correspondence.AbstractTarget#getT2c <em>T2c</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt</em>' reference.
	 * @see #setTgt(AbstractTarget)
	 * @see TGG_correspondence.TGG_correspondencePackage#getCORR_Tgt()
	 * @see TGG_correspondence.AbstractTarget#getT2c
	 * @model opposite="t2c"
	 * @generated
	 */
	AbstractTarget getTgt();

	/**
	 * Sets the value of the '{@link TGG_correspondence.CORR#getTgt <em>Tgt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgt</em>' reference.
	 * @see #getTgt()
	 * @generated
	 */
	void setTgt(AbstractTarget value);

	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link TGG_correspondence.AbstractSource#getS2c <em>S2c</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(AbstractSource)
	 * @see TGG_correspondence.TGG_correspondencePackage#getCORR_Src()
	 * @see TGG_correspondence.AbstractSource#getS2c
	 * @model opposite="s2c"
	 * @generated
	 */
	AbstractSource getSrc();

	/**
	 * Sets the value of the '{@link TGG_correspondence.CORR#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(AbstractSource value);

} // CORR
