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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HenshinLayoutFactoryImpl extends EFactoryImpl implements HenshinLayoutFactory {
        /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public static HenshinLayoutFactory init() {
		try {
			HenshinLayoutFactory theHenshinLayoutFactory = (HenshinLayoutFactory)EPackage.Registry.INSTANCE.getEFactory("http://de.tub.tfs.henshin.editor.layout"); 
			if (theHenshinLayoutFactory != null) {
				return theHenshinLayoutFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HenshinLayoutFactoryImpl();
	}

        /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public HenshinLayoutFactoryImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case HenshinLayoutPackage.LAYOUT_SYSTEM: return createLayoutSystem();
			case HenshinLayoutPackage.NODE_LAYOUT: return createNodeLayout();
			case HenshinLayoutPackage.FLOW_ELEMENT_LAYOUT: return createFlowElementLayout();
			case HenshinLayoutPackage.ECONTAINER_DESCRIPTOR: return createEContainerDescriptor();
			case HenshinLayoutPackage.LAYOUT: return createLayout();
			case HenshinLayoutPackage.SUB_UNIT_LAYOUT: return createSubUnitLayout();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public LayoutSystem createLayoutSystem() {
		LayoutSystemImpl layoutSystem = new LayoutSystemImpl();
		return layoutSystem;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public NodeLayout createNodeLayout() {
		NodeLayoutImpl nodeLayout = new NodeLayoutImpl();
		return nodeLayout;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowElementLayout createFlowElementLayout() {
		FlowElementLayoutImpl flowElementLayout = new FlowElementLayoutImpl();
		return flowElementLayout;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EContainerDescriptor createEContainerDescriptor() {
		EContainerDescriptorImpl eContainerDescriptor = new EContainerDescriptorImpl();
		return eContainerDescriptor;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Layout createLayout() {
		LayoutImpl layout = new LayoutImpl();
		return layout;
	}

        /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubUnitLayout createSubUnitLayout() {
		SubUnitLayoutImpl subUnitLayout = new SubUnitLayoutImpl();
		return subUnitLayout;
	}

								/**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public HenshinLayoutPackage getHenshinLayoutPackage() {
		return (HenshinLayoutPackage)getEPackage();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
        @Deprecated
        public static HenshinLayoutPackage getPackage() {
		return HenshinLayoutPackage.eINSTANCE;
	}

} //HenshinLayoutFactoryImpl
