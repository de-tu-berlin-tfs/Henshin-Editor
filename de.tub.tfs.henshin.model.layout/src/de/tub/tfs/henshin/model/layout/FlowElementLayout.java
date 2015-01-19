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
package de.tub.tfs.henshin.model.layout;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Element Layout</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.FlowElementLayout#getMapId <em>Map Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getFlowElementLayout()
 * @model
 * @generated
 */
public interface FlowElementLayout extends Layout {
        /**
	 * Returns the value of the '<em><b>Map Id</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Map Id</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Id</em>' attribute.
	 * @see #setMapId(int)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getFlowElementLayout_MapId()
	 * @model default="-1"
	 * @generated
	 */
        int getMapId();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.FlowElementLayout#getMapId <em>Map Id</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Id</em>' attribute.
	 * @see #getMapId()
	 * @generated
	 */
        void setMapId(int value);

} // FlowElementLayout
