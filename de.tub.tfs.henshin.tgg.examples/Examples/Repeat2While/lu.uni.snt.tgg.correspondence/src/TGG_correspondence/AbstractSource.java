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
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link TGG_correspondence.AbstractSource#getS2c <em>S2c</em>}</li>
 * </ul>
 * </p>
 *
 * @see TGG_correspondence.TGG_correspondencePackage#getAbstractSource()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface AbstractSource extends CDOObject {
	/**
	 * Returns the value of the '<em><b>S2c</b></em>' reference list.
	 * The list contents are of type {@link TGG_correspondence.CORR}.
	 * It is bidirectional and its opposite is '{@link TGG_correspondence.CORR#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>S2c</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>S2c</em>' reference list.
	 * @see TGG_correspondence.TGG_correspondencePackage#getAbstractSource_S2c()
	 * @see TGG_correspondence.CORR#getSrc
	 * @model opposite="src"
	 * @generated
	 */
	EList<CORR> getS2c();

} // AbstractSource
