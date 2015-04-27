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
package de.tub.tfs.henshin.model.layout.impl;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EContainer Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.EContainerDescriptorImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.layout.impl.EContainerDescriptorImpl#getContainmentMap <em>Containment Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EContainerDescriptorImpl extends EObjectImpl implements EContainerDescriptor {
        /**
	 * The cached value of the '{@link #getContainer() <em>Container</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getContainer()
	 * @generated
	 * @ordered
	 */
        protected EObject container;

        /**
	 * The cached value of the '{@link #getContainmentMap() <em>Containment Map</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getContainmentMap()
	 * @generated
	 * @ordered
	 */
        protected Map<?, ?> containmentMap;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected EContainerDescriptorImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        protected EClass eStaticClass() {
		return HenshinLayoutPackage.Literals.ECONTAINER_DESCRIPTOR;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EObject getContainer() {
		if (container != null && container.eIsProxy()) {
			InternalEObject oldContainer = (InternalEObject)container;
			container = eResolveProxy(oldContainer);
			if (container != oldContainer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINER, oldContainer, container));
			}
		}
		return container;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EObject basicGetContainer() {
		return container;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setContainer(EObject newContainer) {
		EObject oldContainer = container;
		container = newContainer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINER, oldContainer, container));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Map<?, ?> getContainmentMap() {
		return containmentMap;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setContainmentMap(Map<?, ?> newContainmentMap) {
		Map<?, ?> oldContainmentMap = containmentMap;
		containmentMap = newContainmentMap;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP, oldContainmentMap, containmentMap));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINER:
				if (resolve) return getContainer();
				return basicGetContainer();
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP:
				return getContainmentMap();
		}
		return super.eGet(featureID, resolve, coreType);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINER:
				setContainer((EObject)newValue);
				return;
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP:
				setContainmentMap((Map<?, ?>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public void eUnset(int featureID) {
		switch (featureID) {
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINER:
				setContainer((EObject)null);
				return;
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP:
				setContainmentMap((Map<?, ?>)null);
				return;
		}
		super.eUnset(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINER:
				return container != null;
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP:
				return containmentMap != null;
		}
		return super.eIsSet(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (containmentMap: ");
		result.append(containmentMap);
		result.append(')');
		return result.toString();
	}

} //EContainerDescriptorImpl
