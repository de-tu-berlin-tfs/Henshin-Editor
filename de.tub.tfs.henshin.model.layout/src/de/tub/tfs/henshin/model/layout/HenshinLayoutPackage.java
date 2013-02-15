/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.layout;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.layout.HenshinLayoutFactory
 * @model kind="package"
 * @generated
 */
public interface HenshinLayoutPackage extends EPackage {
        /**
	 * The package name.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        String eNAME = "layout";

        /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        String eNS_URI = "http://de.tub.tfs.henshin.editor.layout";

        /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        String eNS_PREFIX = "henshinlayout";

        /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        HenshinLayoutPackage eINSTANCE = de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl.init();

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.layout.impl.LayoutSystemImpl <em>Layout System</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.layout.impl.LayoutSystemImpl
	 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getLayoutSystem()
	 * @generated
	 */
        int LAYOUT_SYSTEM = 0;

        /**
	 * The feature id for the '<em><b>Layouts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int LAYOUT_SYSTEM__LAYOUTS = 0;

        /**
	 * The number of structural features of the '<em>Layout System</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int LAYOUT_SYSTEM_FEATURE_COUNT = 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.layout.impl.LayoutImpl <em>Layout</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.layout.impl.LayoutImpl
	 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getLayout()
	 * @generated
	 */
        int LAYOUT = 4;

        /**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int LAYOUT__X = 0;

        /**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int LAYOUT__Y = 1;

        /**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int LAYOUT__MODEL = 2;

        /**
	 * The number of structural features of the '<em>Layout</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int LAYOUT_FEATURE_COUNT = 3;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl <em>Node Layout</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl
	 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getNodeLayout()
	 * @generated
	 */
        int NODE_LAYOUT = 1;

        /**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__X = LAYOUT__X;

        /**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__Y = LAYOUT__Y;

        /**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__MODEL = LAYOUT__MODEL;

        /**
	 * The feature id for the '<em><b>Hide</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__HIDE = LAYOUT_FEATURE_COUNT + 0;

        /**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__ENABLED = LAYOUT_FEATURE_COUNT + 1;

        /**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__COLOR = LAYOUT_FEATURE_COUNT + 2;

        /**
	 * The feature id for the '<em><b>Multi</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT__MULTI = LAYOUT_FEATURE_COUNT + 3;

        /**
	 * The number of structural features of the '<em>Node Layout</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int NODE_LAYOUT_FEATURE_COUNT = LAYOUT_FEATURE_COUNT + 4;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.layout.impl.FlowElementLayoutImpl <em>Flow Element Layout</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.layout.impl.FlowElementLayoutImpl
	 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getFlowElementLayout()
	 * @generated
	 */
        int FLOW_ELEMENT_LAYOUT = 2;

        /**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT_LAYOUT__X = LAYOUT__X;

        /**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT_LAYOUT__Y = LAYOUT__Y;

        /**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT_LAYOUT__MODEL = LAYOUT__MODEL;

        /**
	 * The feature id for the '<em><b>Map Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT_LAYOUT__MAP_ID = LAYOUT_FEATURE_COUNT + 0;

        /**
	 * The number of structural features of the '<em>Flow Element Layout</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT_LAYOUT_FEATURE_COUNT = LAYOUT_FEATURE_COUNT + 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.layout.impl.EContainerDescriptorImpl <em>EContainer Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.layout.impl.EContainerDescriptorImpl
	 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getEContainerDescriptor()
	 * @generated
	 */
        int ECONTAINER_DESCRIPTOR = 3;

        /**
	 * The feature id for the '<em><b>Container</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ECONTAINER_DESCRIPTOR__CONTAINER = 0;

        /**
	 * The feature id for the '<em><b>Containment Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP = 1;

        /**
	 * The number of structural features of the '<em>EContainer Descriptor</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ECONTAINER_DESCRIPTOR_FEATURE_COUNT = 2;


        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.layout.impl.SubUnitLayoutImpl <em>Sub Unit Layout</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.layout.impl.SubUnitLayoutImpl
	 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getSubUnitLayout()
	 * @generated
	 */
	int SUB_UNIT_LAYOUT = 5;

								/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT__X = LAYOUT__X;

								/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT__Y = LAYOUT__Y;

								/**
	 * The feature id for the '<em><b>Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT__MODEL = LAYOUT__MODEL;

								/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT__INDEX = LAYOUT_FEATURE_COUNT + 0;

								/**
	 * The feature id for the '<em><b>Counter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT__COUNTER = LAYOUT_FEATURE_COUNT + 1;

								/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT__PARENT = LAYOUT_FEATURE_COUNT + 2;

								/**
	 * The number of structural features of the '<em>Sub Unit Layout</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_UNIT_LAYOUT_FEATURE_COUNT = LAYOUT_FEATURE_COUNT + 3;


								/**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.layout.LayoutSystem <em>Layout System</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Layout System</em>'.
	 * @see de.tub.tfs.henshin.model.layout.LayoutSystem
	 * @generated
	 */
        EClass getLayoutSystem();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.layout.LayoutSystem#getLayouts <em>Layouts</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Layouts</em>'.
	 * @see de.tub.tfs.henshin.model.layout.LayoutSystem#getLayouts()
	 * @see #getLayoutSystem()
	 * @generated
	 */
        EReference getLayoutSystem_Layouts();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.layout.NodeLayout <em>Node Layout</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Layout</em>'.
	 * @see de.tub.tfs.henshin.model.layout.NodeLayout
	 * @generated
	 */
        EClass getNodeLayout();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.NodeLayout#isHide <em>Hide</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hide</em>'.
	 * @see de.tub.tfs.henshin.model.layout.NodeLayout#isHide()
	 * @see #getNodeLayout()
	 * @generated
	 */
        EAttribute getNodeLayout_Hide();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.NodeLayout#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see de.tub.tfs.henshin.model.layout.NodeLayout#isEnabled()
	 * @see #getNodeLayout()
	 * @generated
	 */
        EAttribute getNodeLayout_Enabled();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.NodeLayout#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see de.tub.tfs.henshin.model.layout.NodeLayout#getColor()
	 * @see #getNodeLayout()
	 * @generated
	 */
        EAttribute getNodeLayout_Color();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.NodeLayout#isMulti <em>Multi</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multi</em>'.
	 * @see de.tub.tfs.henshin.model.layout.NodeLayout#isMulti()
	 * @see #getNodeLayout()
	 * @generated
	 */
        EAttribute getNodeLayout_Multi();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.layout.FlowElementLayout <em>Flow Element Layout</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Element Layout</em>'.
	 * @see de.tub.tfs.henshin.model.layout.FlowElementLayout
	 * @generated
	 */
        EClass getFlowElementLayout();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.FlowElementLayout#getMapId <em>Map Id</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Id</em>'.
	 * @see de.tub.tfs.henshin.model.layout.FlowElementLayout#getMapId()
	 * @see #getFlowElementLayout()
	 * @generated
	 */
        EAttribute getFlowElementLayout_MapId();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor <em>EContainer Descriptor</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EContainer Descriptor</em>'.
	 * @see de.tub.tfs.henshin.model.layout.EContainerDescriptor
	 * @generated
	 */
        EClass getEContainerDescriptor();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Container</em>'.
	 * @see de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainer()
	 * @see #getEContainerDescriptor()
	 * @generated
	 */
        EReference getEContainerDescriptor_Container();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainmentMap <em>Containment Map</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Containment Map</em>'.
	 * @see de.tub.tfs.henshin.model.layout.EContainerDescriptor#getContainmentMap()
	 * @see #getEContainerDescriptor()
	 * @generated
	 */
        EAttribute getEContainerDescriptor_ContainmentMap();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.layout.Layout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Layout</em>'.
	 * @see de.tub.tfs.henshin.model.layout.Layout
	 * @generated
	 */
        EClass getLayout();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.Layout#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see de.tub.tfs.henshin.model.layout.Layout#getX()
	 * @see #getLayout()
	 * @generated
	 */
        EAttribute getLayout_X();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.Layout#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see de.tub.tfs.henshin.model.layout.Layout#getY()
	 * @see #getLayout()
	 * @generated
	 */
        EAttribute getLayout_Y();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.layout.Layout#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model</em>'.
	 * @see de.tub.tfs.henshin.model.layout.Layout#getModel()
	 * @see #getLayout()
	 * @generated
	 */
        EReference getLayout_Model();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.layout.SubUnitLayout <em>Sub Unit Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub Unit Layout</em>'.
	 * @see de.tub.tfs.henshin.model.layout.SubUnitLayout
	 * @generated
	 */
	EClass getSubUnitLayout();

								/**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.SubUnitLayout#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see de.tub.tfs.henshin.model.layout.SubUnitLayout#getIndex()
	 * @see #getSubUnitLayout()
	 * @generated
	 */
	EAttribute getSubUnitLayout_Index();

								/**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.layout.SubUnitLayout#getCounter <em>Counter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Counter</em>'.
	 * @see de.tub.tfs.henshin.model.layout.SubUnitLayout#getCounter()
	 * @see #getSubUnitLayout()
	 * @generated
	 */
	EAttribute getSubUnitLayout_Counter();

								/**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.layout.SubUnitLayout#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.tub.tfs.henshin.model.layout.SubUnitLayout#getParent()
	 * @see #getSubUnitLayout()
	 * @generated
	 */
	EReference getSubUnitLayout_Parent();

								/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
        HenshinLayoutFactory getHenshinLayoutFactory();

        /**
	 * <!-- begin-user-doc -->
         * Defines literals for the meta objects that represent
         * <ul>
         *   <li>each class,</li>
         *   <li>each feature of each class,</li>
         *   <li>each enum,</li>
         *   <li>and each data type</li>
         * </ul>
         * <!-- end-user-doc -->
	 * @generated
	 */
        interface Literals {
                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.layout.impl.LayoutSystemImpl <em>Layout System</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.layout.impl.LayoutSystemImpl
		 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getLayoutSystem()
		 * @generated
		 */
                EClass LAYOUT_SYSTEM = eINSTANCE.getLayoutSystem();

                /**
		 * The meta object literal for the '<em><b>Layouts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference LAYOUT_SYSTEM__LAYOUTS = eINSTANCE.getLayoutSystem_Layouts();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl <em>Node Layout</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.layout.impl.NodeLayoutImpl
		 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getNodeLayout()
		 * @generated
		 */
                EClass NODE_LAYOUT = eINSTANCE.getNodeLayout();

                /**
		 * The meta object literal for the '<em><b>Hide</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute NODE_LAYOUT__HIDE = eINSTANCE.getNodeLayout_Hide();

                /**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute NODE_LAYOUT__ENABLED = eINSTANCE.getNodeLayout_Enabled();

                /**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute NODE_LAYOUT__COLOR = eINSTANCE.getNodeLayout_Color();

                /**
		 * The meta object literal for the '<em><b>Multi</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute NODE_LAYOUT__MULTI = eINSTANCE.getNodeLayout_Multi();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.layout.impl.FlowElementLayoutImpl <em>Flow Element Layout</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.layout.impl.FlowElementLayoutImpl
		 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getFlowElementLayout()
		 * @generated
		 */
                EClass FLOW_ELEMENT_LAYOUT = eINSTANCE.getFlowElementLayout();

                /**
		 * The meta object literal for the '<em><b>Map Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute FLOW_ELEMENT_LAYOUT__MAP_ID = eINSTANCE.getFlowElementLayout_MapId();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.layout.impl.EContainerDescriptorImpl <em>EContainer Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.layout.impl.EContainerDescriptorImpl
		 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getEContainerDescriptor()
		 * @generated
		 */
                EClass ECONTAINER_DESCRIPTOR = eINSTANCE.getEContainerDescriptor();

                /**
		 * The meta object literal for the '<em><b>Container</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference ECONTAINER_DESCRIPTOR__CONTAINER = eINSTANCE.getEContainerDescriptor_Container();

                /**
		 * The meta object literal for the '<em><b>Containment Map</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute ECONTAINER_DESCRIPTOR__CONTAINMENT_MAP = eINSTANCE.getEContainerDescriptor_ContainmentMap();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.layout.impl.LayoutImpl <em>Layout</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.layout.impl.LayoutImpl
		 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getLayout()
		 * @generated
		 */
                EClass LAYOUT = eINSTANCE.getLayout();

                /**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute LAYOUT__X = eINSTANCE.getLayout_X();

                /**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EAttribute LAYOUT__Y = eINSTANCE.getLayout_Y();

                /**
		 * The meta object literal for the '<em><b>Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference LAYOUT__MODEL = eINSTANCE.getLayout_Model();

																/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.layout.impl.SubUnitLayoutImpl <em>Sub Unit Layout</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.layout.impl.SubUnitLayoutImpl
		 * @see de.tub.tfs.henshin.model.layout.impl.HenshinLayoutPackageImpl#getSubUnitLayout()
		 * @generated
		 */
		EClass SUB_UNIT_LAYOUT = eINSTANCE.getSubUnitLayout();

																/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_UNIT_LAYOUT__INDEX = eINSTANCE.getSubUnitLayout_Index();

																/**
		 * The meta object literal for the '<em><b>Counter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_UNIT_LAYOUT__COUNTER = eINSTANCE.getSubUnitLayout_Counter();

																/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_UNIT_LAYOUT__PARENT = eINSTANCE.getSubUnitLayout_Parent();

        }

} //HenshinLayoutPackage
