/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.ControlElement;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;
import de.tub.tfs.henshin.model.flowcontrol.SimpleActivity;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FlowControlPackageImpl extends EPackageImpl implements FlowControlPackage {
        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass flowElementEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass activityEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass controlElementEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass conditionalActivityEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass startEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass endEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass transitionEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass flowDiagramEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass flowControlSystemEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass parameterMappingEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass parameterProviderEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass conditionalElementEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass compoundActivityEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass parameterEClass = null;

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        private EClass simpleActivityEClass = null;

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
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
        private FlowControlPackageImpl() {
		super(eNS_URI, FlowControlFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link FlowControlPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
        public static FlowControlPackage init() {
		if (isInited) return (FlowControlPackage)EPackage.Registry.INSTANCE.getEPackage(FlowControlPackage.eNS_URI);

		// Obtain or create and register package
		FlowControlPackageImpl theFlowControlPackage = (FlowControlPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof FlowControlPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new FlowControlPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theFlowControlPackage.createPackageContents();

		// Initialize created meta-data
		theFlowControlPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFlowControlPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FlowControlPackage.eNS_URI, theFlowControlPackage);
		return theFlowControlPackage;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getFlowElement() {
		return flowElementEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowElement_Out() {
		return (EReference)flowElementEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowElement_In() {
		return (EReference)flowElementEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowElement_Diagram() {
		return (EReference)flowElementEClass.getEStructuralFeatures().get(2);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getActivity() {
		return activityEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getActivity_Content() {
		return (EReference)activityEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getActivity_ParameterMappings() {
		return (EReference)activityEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getControlElement() {
		return controlElementEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getConditionalActivity() {
		return conditionalActivityEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getStart() {
		return startEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getEnd() {
		return endEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getTransition() {
		return transitionEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getTransition_Next() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getTransition_Prevous() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getFlowDiagram() {
		return flowDiagramEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowDiagram_Elements() {
		return (EReference)flowDiagramEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowDiagram_Transitions() {
		return (EReference)flowDiagramEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowDiagram_ParameterMappings() {
		return (EReference)flowDiagramEClass.getEStructuralFeatures().get(2);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowDiagram_Start() {
		return (EReference)flowDiagramEClass.getEStructuralFeatures().get(3);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowDiagram_End() {
		return (EReference)flowDiagramEClass.getEStructuralFeatures().get(4);
	}

        /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowDiagram_Strict() {
		return (EAttribute)flowDiagramEClass.getEStructuralFeatures().get(5);
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowDiagram_Rollback() {
		return (EAttribute)flowDiagramEClass.getEStructuralFeatures().get(6);
	}

								/**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getFlowControlSystem() {
		return flowControlSystemEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getFlowControlSystem_Units() {
		return (EReference)flowControlSystemEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getParameterMapping() {
		return parameterMappingEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getParameterMapping_Src() {
		return (EReference)parameterMappingEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getParameterMapping_Target() {
		return (EReference)parameterMappingEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getParameterProvider() {
		return parameterProviderEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getParameterProvider_Parameters() {
		return (EReference)parameterProviderEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getConditionalElement() {
		return conditionalElementEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getConditionalElement_AltOut() {
		return (EReference)conditionalElementEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getCompoundActivity() {
		return compoundActivityEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getCompoundActivity_Children() {
		return (EReference)compoundActivityEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getParameter() {
		return parameterEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getParameter_Provider() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(0);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EReference getParameter_HenshinParameter() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(1);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EClass getSimpleActivity() {
		return simpleActivityEClass;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowControlFactory getFlowControlFactory() {
		return (FlowControlFactory)getEFactoryInstance();
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
		flowElementEClass = createEClass(FLOW_ELEMENT);
		createEReference(flowElementEClass, FLOW_ELEMENT__OUT);
		createEReference(flowElementEClass, FLOW_ELEMENT__IN);
		createEReference(flowElementEClass, FLOW_ELEMENT__DIAGRAM);

		activityEClass = createEClass(ACTIVITY);
		createEReference(activityEClass, ACTIVITY__CONTENT);
		createEReference(activityEClass, ACTIVITY__PARAMETER_MAPPINGS);

		controlElementEClass = createEClass(CONTROL_ELEMENT);

		conditionalActivityEClass = createEClass(CONDITIONAL_ACTIVITY);

		startEClass = createEClass(START);

		endEClass = createEClass(END);

		transitionEClass = createEClass(TRANSITION);
		createEReference(transitionEClass, TRANSITION__NEXT);
		createEReference(transitionEClass, TRANSITION__PREVOUS);

		flowDiagramEClass = createEClass(FLOW_DIAGRAM);
		createEReference(flowDiagramEClass, FLOW_DIAGRAM__ELEMENTS);
		createEReference(flowDiagramEClass, FLOW_DIAGRAM__TRANSITIONS);
		createEReference(flowDiagramEClass, FLOW_DIAGRAM__PARAMETER_MAPPINGS);
		createEReference(flowDiagramEClass, FLOW_DIAGRAM__START);
		createEReference(flowDiagramEClass, FLOW_DIAGRAM__END);
		createEAttribute(flowDiagramEClass, FLOW_DIAGRAM__STRICT);
		createEAttribute(flowDiagramEClass, FLOW_DIAGRAM__ROLLBACK);

		flowControlSystemEClass = createEClass(FLOW_CONTROL_SYSTEM);
		createEReference(flowControlSystemEClass, FLOW_CONTROL_SYSTEM__UNITS);

		parameterMappingEClass = createEClass(PARAMETER_MAPPING);
		createEReference(parameterMappingEClass, PARAMETER_MAPPING__SRC);
		createEReference(parameterMappingEClass, PARAMETER_MAPPING__TARGET);

		parameterProviderEClass = createEClass(PARAMETER_PROVIDER);
		createEReference(parameterProviderEClass, PARAMETER_PROVIDER__PARAMETERS);

		conditionalElementEClass = createEClass(CONDITIONAL_ELEMENT);
		createEReference(conditionalElementEClass, CONDITIONAL_ELEMENT__ALT_OUT);

		compoundActivityEClass = createEClass(COMPOUND_ACTIVITY);
		createEReference(compoundActivityEClass, COMPOUND_ACTIVITY__CHILDREN);

		parameterEClass = createEClass(PARAMETER);
		createEReference(parameterEClass, PARAMETER__PROVIDER);
		createEReference(parameterEClass, PARAMETER__HENSHIN_PARAMETER);

		simpleActivityEClass = createEClass(SIMPLE_ACTIVITY);
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

		// Obtain other dependent packages
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		activityEClass.getESuperTypes().add(this.getFlowElement());
		activityEClass.getESuperTypes().add(this.getParameterProvider());
		controlElementEClass.getESuperTypes().add(this.getFlowElement());
		conditionalActivityEClass.getESuperTypes().add(this.getActivity());
		conditionalActivityEClass.getESuperTypes().add(this.getConditionalElement());
		startEClass.getESuperTypes().add(this.getControlElement());
		endEClass.getESuperTypes().add(this.getControlElement());
		flowDiagramEClass.getESuperTypes().add(theHenshinPackage.getNamedElement());
		flowDiagramEClass.getESuperTypes().add(this.getParameterProvider());
		conditionalElementEClass.getESuperTypes().add(this.getFlowElement());
		compoundActivityEClass.getESuperTypes().add(this.getSimpleActivity());
		parameterEClass.getESuperTypes().add(theHenshinPackage.getNamedElement());
		simpleActivityEClass.getESuperTypes().add(this.getActivity());

		// Initialize classes and features; add operations and parameters
		initEClass(flowElementEClass, FlowElement.class, "FlowElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFlowElement_Out(), this.getTransition(), null, "out", null, 0, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowElement_In(), this.getTransition(), null, "in", null, 0, -1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowElement_Diagram(), this.getFlowDiagram(), null, "diagram", null, 1, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(flowElementEClass, this.getTransition(), "getOutGoings", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(activityEClass, Activity.class, "Activity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getActivity_Content(), theHenshinPackage.getNamedElement(), null, "content", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActivity_ParameterMappings(), this.getParameterMapping(), null, "parameterMappings", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(activityEClass, ecorePackage.getEBoolean(), "isNested", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(controlElementEClass, ControlElement.class, "ControlElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(conditionalActivityEClass, ConditionalActivity.class, "ConditionalActivity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(startEClass, Start.class, "Start", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(endEClass, End.class, "End", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(transitionEClass, Transition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransition_Next(), this.getFlowElement(), null, "next", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransition_Prevous(), this.getFlowElement(), null, "prevous", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(transitionEClass, ecorePackage.getEBoolean(), "isAlternate", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(flowDiagramEClass, FlowDiagram.class, "FlowDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFlowDiagram_Elements(), this.getFlowElement(), null, "elements", null, 0, -1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowDiagram_Transitions(), this.getTransition(), null, "transitions", null, 0, -1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowDiagram_ParameterMappings(), this.getParameterMapping(), null, "parameterMappings", null, 0, -1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowDiagram_Start(), this.getStart(), null, "start", null, 1, 1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowDiagram_End(), this.getEnd(), null, "end", null, 1, 1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFlowDiagram_Strict(), ecorePackage.getEBoolean(), "strict", null, 0, 1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFlowDiagram_Rollback(), ecorePackage.getEBoolean(), "rollback", null, 0, 1, FlowDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(flowControlSystemEClass, FlowControlSystem.class, "FlowControlSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFlowControlSystem_Units(), this.getFlowDiagram(), null, "units", null, 0, -1, FlowControlSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterMappingEClass, ParameterMapping.class, "ParameterMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterMapping_Src(), this.getParameter(), null, "src", null, 1, 1, ParameterMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameterMapping_Target(), this.getParameter(), null, "target", null, 1, 1, ParameterMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterProviderEClass, ParameterProvider.class, "ParameterProvider", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterProvider_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, ParameterProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalElementEClass, ConditionalElement.class, "ConditionalElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalElement_AltOut(), this.getTransition(), null, "altOut", null, 0, 1, ConditionalElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(conditionalElementEClass, ecorePackage.getEString(), "getAlternativeLabel", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(compoundActivityEClass, CompoundActivity.class, "CompoundActivity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompoundActivity_Children(), this.getActivity(), null, "children", null, 0, -1, CompoundActivity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameter_Provider(), this.getParameterProvider(), null, "provider", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameter_HenshinParameter(), theHenshinPackage.getParameter(), null, "henshinParameter", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(parameterEClass, ecorePackage.getEBoolean(), "isInput", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(parameterEClass, ecorePackage.getEBoolean(), "isOutPut", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(simpleActivityEClass, SimpleActivity.class, "SimpleActivity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //FlowControlPackageImpl
