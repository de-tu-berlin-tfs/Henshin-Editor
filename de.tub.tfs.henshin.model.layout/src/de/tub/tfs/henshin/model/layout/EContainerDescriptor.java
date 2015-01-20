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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EContainer Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainer <em>Container</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainmentMap <em>Containment Map</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getEContainerDescriptor()
 * @model
 * @generated
 */
public interface EContainerDescriptor extends EObject {
        /**
	 * Returns the value of the '<em><b>Container</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Container</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Container</em>' reference.
	 * @see #setContainer(EObject)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getEContainerDescriptor_Container()
	 * @model required="true"
	 * @generated
	 */
        EObject getContainer();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainer <em>Container</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container</em>' reference.
	 * @see #getContainer()
	 * @generated
	 */
        void setContainer(EObject value);

        /**
	 * Returns the value of the '<em><b>Containment Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Containment Map</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Containment Map</em>' attribute.
	 * @see #setContainmentMap(Map)
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#getEContainerDescriptor_ContainmentMap()
	 * @model transient="true"
	 * @generated
	 */
        Map<?, ?> getContainmentMap();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainmentMap <em>Containment Map</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containment Map</em>' attribute.
	 * @see #getContainmentMap()
	 * @generated
	 */
        void setContainmentMap(Map<?, ?> value);

} // EContainerDescriptor
