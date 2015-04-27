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
package de.tub.tfs.henshin.model.layout.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage
 * @generated
 */
public class HenshinLayoutSwitch<T> extends Switch<T> {
        /**
	 * The cached model package
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected static HenshinLayoutPackage modelPackage;

        /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public HenshinLayoutSwitch() {
		if (modelPackage == null) {
			modelPackage = HenshinLayoutPackage.eINSTANCE;
		}
	}

        /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
        @Override
        protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

        /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
        @Override
        protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case HenshinLayoutPackage.LAYOUT_SYSTEM: {
				LayoutSystem layoutSystem = (LayoutSystem)theEObject;
				T result = caseLayoutSystem(layoutSystem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinLayoutPackage.NODE_LAYOUT: {
				NodeLayout nodeLayout = (NodeLayout)theEObject;
				T result = caseNodeLayout(nodeLayout);
				if (result == null) result = caseLayout(nodeLayout);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT: {
				FlowElementLayout flowElementLayout = (FlowElementLayout)theEObject;
				T result = caseFlowElementLayout(flowElementLayout);
				if (result == null) result = caseLayout(flowElementLayout);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR: {
				EContainerDescriptor eContainerDescriptor = (EContainerDescriptor)theEObject;
				T result = caseEContainerDescriptor(eContainerDescriptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinLayoutPackage.LAYOUT: {
				Layout layout = (Layout)theEObject;
				T result = caseLayout(layout);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case HenshinLayoutPackage.SUB_UNIT_LAYOUT: {
				SubUnitLayout subUnitLayout = (SubUnitLayout)theEObject;
				T result = caseSubUnitLayout(subUnitLayout);
				if (result == null) result = caseLayout(subUnitLayout);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Layout System</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Layout System</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseLayoutSystem(LayoutSystem object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Node Layout</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node Layout</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseNodeLayout(NodeLayout object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Flow Element Layout</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow Element Layout</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseFlowElementLayout(FlowElementLayout object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>EContainer Descriptor</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EContainer Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseEContainerDescriptor(EContainerDescriptor object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Layout</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Layout</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseLayout(Layout object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Sub Unit Layout</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub Unit Layout</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubUnitLayout(SubUnitLayout object) {
		return null;
	}

								/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch, but this is the last case anyway.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
        @Override
        public T defaultCase(EObject object) {
		return null;
	}

} //HenshinLayoutSwitch
