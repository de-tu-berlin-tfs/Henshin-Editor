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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HenshinLayoutPackageImpl extends EPackageImpl implements HenshinLayoutPackage {
        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass layoutSystemEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass nodeLayoutEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass flowElementLayoutEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass eContainerDescriptorEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass layoutEClass = null;

        /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subUnitLayoutEClass = null;

								/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
        private HenshinLayoutPackageImpl() {
		super(eNS_URI, HenshinLayoutFactory.eINSTANCE);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private static boolean isInited = false;

        /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link HenshinLayoutPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
        public static HenshinLayoutPackage init() {
		if (isInited) return (HenshinLayoutPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinLayoutPackage.eNS_URI);

		// Obtain or create and register package
		HenshinLayoutPackageImpl theHenshinLayoutPackage = (HenshinLayoutPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof HenshinLayoutPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new HenshinLayoutPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theHenshinLayoutPackage.createPackageContents();

		// Initialize created meta-data
		theHenshinLayoutPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theHenshinLayoutPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(HenshinLayoutPackage.eNS_URI, theHenshinLayoutPackage);
		return theHenshinLayoutPackage;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getLayoutSystem() {
		return layoutSystemEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getLayoutSystem_Layouts() {
		return (EReference)layoutSystemEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getNodeLayout() {
		return nodeLayoutEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getNodeLayout_Hide() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getNodeLayout_Enabled() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getNodeLayout_Color() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(2);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getNodeLayout_Multi() {
		return (EAttribute)nodeLayoutEClass.getEStructuralFeatures().get(3);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getFlowElementLayout() {
		return flowElementLayoutEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getFlowElementLayout_MapId() {
		return (EAttribute)flowElementLayoutEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getEContainerDescriptor() {
		return eContainerDescriptorEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getEContainerDescriptor_Container() {
		return (EReference)eContainerDescriptorEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getEContainerDescriptor_ContainmentMap() {
		return (EAttribute)eContainerDescriptorEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getLayout() {
		return layoutEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getLayout_X() {
		return (EAttribute)layoutEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EAttribute getLayout_Y() {
		return (EAttribute)layoutEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getLayout_Model() {
		return (EReference)layoutEClass.getEStructuralFeatures().get(2);
	}

        /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubUnitLayout() {
		return subUnitLayoutEClass;
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubUnitLayout_Index() {
		return (EAttribute)subUnitLayoutEClass.getEStructuralFeatures().get(0);
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubUnitLayout_Counter() {
		return (EAttribute)subUnitLayoutEClass.getEStructuralFeatures().get(1);
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubUnitLayout_Parent() {
		return (EReference)subUnitLayoutEClass.getEStructuralFeatures().get(2);
	}

								/**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public HenshinLayoutFactory getHenshinLayoutFactory() {
		return (HenshinLayoutFactory)getEFactoryInstance();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private boolean isCreated = false;

        /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		layoutSystemEClass = createEClass(LAYOUT_SYSTEM);
		createEReference(layoutSystemEClass, LAYOUT_SYSTEM__LAYOUTS);

		nodeLayoutEClass = createEClass(NODE_LAYOUT);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__HIDE);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__ENABLED);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__COLOR);
		createEAttribute(nodeLayoutEClass, NODE_LAYOUT__MULTI);

		flowElementLayoutEClass = createEClass(FLOW_ELEMENT_LAYOUT);
		createEAttribute(flowElementLayoutEClass, FLOW_ELEMENT_LAYOUT__MAP_ID);

		eContainerDescriptorEClass = createEClass(ECONTAINER_DESCRIPTOR);
		createEReference(eContainerDescriptorEClass, ECONTAINER_DESCRIPTOR__CONTAINER);
		createEAttribute(eContainerDescriptorEClass, ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP);

		layoutEClass = createEClass(LAYOUT);
		createEAttribute(layoutEClass, LAYOUT__X);
		createEAttribute(layoutEClass, LAYOUT__Y);
		createEReference(layoutEClass, LAYOUT__MODEL);

		subUnitLayoutEClass = createEClass(SUB_UNIT_LAYOUT);
		createEAttribute(subUnitLayoutEClass, SUB_UNIT_LAYOUT__INDEX);
		createEAttribute(subUnitLayoutEClass, SUB_UNIT_LAYOUT__COUNTER);
		createEReference(subUnitLayoutEClass, SUB_UNIT_LAYOUT__PARENT);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private boolean isInitialized = false;

        /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		nodeLayoutEClass.getESuperTypes().add(this.getLayout());
		flowElementLayoutEClass.getESuperTypes().add(this.getLayout());
		subUnitLayoutEClass.getESuperTypes().add(this.getLayout());

		// Initialize classes and features; add operations and parameters
		initEClass(layoutSystemEClass, LayoutSystem.class, "LayoutSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLayoutSystem_Layouts(), this.getLayout(), null, "layouts", null, 0, -1, LayoutSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeLayoutEClass, NodeLayout.class, "NodeLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNodeLayout_Hide(), ecorePackage.getEBoolean(), "hide", "true", 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_Enabled(), ecorePackage.getEBoolean(), "enabled", "true", 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_Color(), ecorePackage.getEInt(), "color", null, 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNodeLayout_Multi(), ecorePackage.getEBoolean(), "multi", "false", 0, 1, NodeLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(flowElementLayoutEClass, FlowElementLayout.class, "FlowElementLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFlowElementLayout_MapId(), ecorePackage.getEInt(), "mapId", "-1", 0, 1, FlowElementLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eContainerDescriptorEClass, EContainerDescriptor.class, "EContainerDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEContainerDescriptor_Container(), ecorePackage.getEObject(), null, "container", null, 1, 1, EContainerDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEAttribute(getEContainerDescriptor_ContainmentMap(), g1, "containmentMap", null, 0, 1, EContainerDescriptor.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(layoutEClass, Layout.class, "Layout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLayout_X(), ecorePackage.getEInt(), "x", null, 0, 1, Layout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLayout_Y(), ecorePackage.getEInt(), "y", null, 0, 1, Layout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLayout_Model(), ecorePackage.getEObject(), null, "model", null, 1, 1, Layout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subUnitLayoutEClass, SubUnitLayout.class, "SubUnitLayout", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubUnitLayout_Index(), ecorePackage.getEInt(), "index", null, 0, 1, SubUnitLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSubUnitLayout_Counter(), ecorePackage.getEInt(), "counter", null, 1, 1, SubUnitLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubUnitLayout_Parent(), ecorePackage.getEObject(), null, "parent", null, 0, 1, SubUnitLayout.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //HenshinLayoutPackageImpl
