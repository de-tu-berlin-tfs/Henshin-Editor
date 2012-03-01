/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinPackage;

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
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory
 * @model kind="package"
 * @generated
 */
public interface FlowControlPackage extends EPackage {
        /**
	 * The package name.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        String eNAME = "flowcontrol";

        /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        String eNS_URI = "http://flowcontrol";

        /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        String eNS_PREFIX = "flowcontrol";

        /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        FlowControlPackage eINSTANCE = de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl.init();

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl <em>Flow Element</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getFlowElement()
	 * @generated
	 */
        int FLOW_ELEMENT = 0;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT__OUT = 0;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT__IN = 1;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT__DIAGRAM = 2;

        /**
	 * The number of structural features of the '<em>Flow Element</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_ELEMENT_FEATURE_COUNT = 3;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl <em>Activity</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getActivity()
	 * @generated
	 */
        int ACTIVITY = 1;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY__OUT = FLOW_ELEMENT__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY__IN = FLOW_ELEMENT__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY__DIAGRAM = FLOW_ELEMENT__DIAGRAM;

        /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY__PARAMETERS = FLOW_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY__CONTENT = FLOW_ELEMENT_FEATURE_COUNT + 1;

        /**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY__PARAMETER_MAPPINGS = FLOW_ELEMENT_FEATURE_COUNT + 2;

        /**
	 * The number of structural features of the '<em>Activity</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int ACTIVITY_FEATURE_COUNT = FLOW_ELEMENT_FEATURE_COUNT + 3;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ControlElementImpl <em>Control Element</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ControlElementImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getControlElement()
	 * @generated
	 */
        int CONTROL_ELEMENT = 2;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONTROL_ELEMENT__OUT = FLOW_ELEMENT__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONTROL_ELEMENT__IN = FLOW_ELEMENT__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONTROL_ELEMENT__DIAGRAM = FLOW_ELEMENT__DIAGRAM;

        /**
	 * The number of structural features of the '<em>Control Element</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONTROL_ELEMENT_FEATURE_COUNT = FLOW_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalActivityImpl <em>Conditional Activity</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalActivityImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getConditionalActivity()
	 * @generated
	 */
        int CONDITIONAL_ACTIVITY = 3;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__OUT = ACTIVITY__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__IN = ACTIVITY__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__DIAGRAM = ACTIVITY__DIAGRAM;

        /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__PARAMETERS = ACTIVITY__PARAMETERS;

        /**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__CONTENT = ACTIVITY__CONTENT;

        /**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__PARAMETER_MAPPINGS = ACTIVITY__PARAMETER_MAPPINGS;

        /**
	 * The feature id for the '<em><b>Alt Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY__ALT_OUT = ACTIVITY_FEATURE_COUNT + 0;

        /**
	 * The number of structural features of the '<em>Conditional Activity</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.StartImpl <em>Start</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.StartImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getStart()
	 * @generated
	 */
        int START = 4;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int START__OUT = CONTROL_ELEMENT__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int START__IN = CONTROL_ELEMENT__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int START__DIAGRAM = CONTROL_ELEMENT__DIAGRAM;

        /**
	 * The number of structural features of the '<em>Start</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int START_FEATURE_COUNT = CONTROL_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.EndImpl <em>End</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.EndImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getEnd()
	 * @generated
	 */
        int END = 5;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int END__OUT = CONTROL_ELEMENT__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int END__IN = CONTROL_ELEMENT__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int END__DIAGRAM = CONTROL_ELEMENT__DIAGRAM;

        /**
	 * The number of structural features of the '<em>End</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int END_FEATURE_COUNT = CONTROL_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.TransitionImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getTransition()
	 * @generated
	 */
        int TRANSITION = 6;

        /**
	 * The feature id for the '<em><b>Next</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int TRANSITION__NEXT = 0;

        /**
	 * The feature id for the '<em><b>Prevous</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int TRANSITION__PREVOUS = 1;

        /**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int TRANSITION_FEATURE_COUNT = 2;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl <em>Flow Diagram</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getFlowDiagram()
	 * @generated
	 */
        int FLOW_DIAGRAM = 7;

        /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__NAME = HenshinPackage.NAMED_ELEMENT__NAME;

        /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__PARAMETERS = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__ELEMENTS = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 1;

        /**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__TRANSITIONS = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 2;

        /**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__PARAMETER_MAPPINGS = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 3;

        /**
	 * The feature id for the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__START = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 4;

        /**
	 * The feature id for the '<em><b>End</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM__END = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 5;

        /**
	 * The feature id for the '<em><b>Strict</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_DIAGRAM__STRICT = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 6;

								/**
	 * The feature id for the '<em><b>Rollback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_DIAGRAM__ROLLBACK = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 7;

								/**
	 * The number of structural features of the '<em>Flow Diagram</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_DIAGRAM_FEATURE_COUNT = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 8;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlSystemImpl <em>System</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlSystemImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getFlowControlSystem()
	 * @generated
	 */
        int FLOW_CONTROL_SYSTEM = 8;

        /**
	 * The feature id for the '<em><b>Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_CONTROL_SYSTEM__UNITS = 0;

        /**
	 * The number of structural features of the '<em>System</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int FLOW_CONTROL_SYSTEM_FEATURE_COUNT = 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterMappingImpl <em>Parameter Mapping</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ParameterMappingImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getParameterMapping()
	 * @generated
	 */
        int PARAMETER_MAPPING = 9;

        /**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER_MAPPING__SRC = 0;

        /**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER_MAPPING__TARGET = 1;

        /**
	 * The number of structural features of the '<em>Parameter Mapping</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER_MAPPING_FEATURE_COUNT = 2;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterProviderImpl <em>Parameter Provider</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ParameterProviderImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getParameterProvider()
	 * @generated
	 */
        int PARAMETER_PROVIDER = 10;

        /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER_PROVIDER__PARAMETERS = 0;

        /**
	 * The number of structural features of the '<em>Parameter Provider</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER_PROVIDER_FEATURE_COUNT = 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalElementImpl <em>Conditional Element</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalElementImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getConditionalElement()
	 * @generated
	 */
        int CONDITIONAL_ELEMENT = 11;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ELEMENT__OUT = FLOW_ELEMENT__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ELEMENT__IN = FLOW_ELEMENT__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ELEMENT__DIAGRAM = FLOW_ELEMENT__DIAGRAM;

        /**
	 * The feature id for the '<em><b>Alt Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ELEMENT__ALT_OUT = FLOW_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The number of structural features of the '<em>Conditional Element</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int CONDITIONAL_ELEMENT_FEATURE_COUNT = FLOW_ELEMENT_FEATURE_COUNT + 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.SimpleActivityImpl <em>Simple Activity</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.SimpleActivityImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getSimpleActivity()
	 * @generated
	 */
        int SIMPLE_ACTIVITY = 14;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY__OUT = ACTIVITY__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY__IN = ACTIVITY__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY__DIAGRAM = ACTIVITY__DIAGRAM;

        /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY__PARAMETERS = ACTIVITY__PARAMETERS;

        /**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY__CONTENT = ACTIVITY__CONTENT;

        /**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY__PARAMETER_MAPPINGS = ACTIVITY__PARAMETER_MAPPINGS;

        /**
	 * The number of structural features of the '<em>Simple Activity</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int SIMPLE_ACTIVITY_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 0;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.CompoundActivityImpl <em>Compound Activity</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.CompoundActivityImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getCompoundActivity()
	 * @generated
	 */
        int COMPOUND_ACTIVITY = 12;

        /**
	 * The feature id for the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__OUT = SIMPLE_ACTIVITY__OUT;

        /**
	 * The feature id for the '<em><b>In</b></em>' reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__IN = SIMPLE_ACTIVITY__IN;

        /**
	 * The feature id for the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__DIAGRAM = SIMPLE_ACTIVITY__DIAGRAM;

        /**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__PARAMETERS = SIMPLE_ACTIVITY__PARAMETERS;

        /**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__CONTENT = SIMPLE_ACTIVITY__CONTENT;

        /**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__PARAMETER_MAPPINGS = SIMPLE_ACTIVITY__PARAMETER_MAPPINGS;

        /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY__CHILDREN = SIMPLE_ACTIVITY_FEATURE_COUNT + 0;

        /**
	 * The number of structural features of the '<em>Compound Activity</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int COMPOUND_ACTIVITY_FEATURE_COUNT = SIMPLE_ACTIVITY_FEATURE_COUNT + 1;

        /**
	 * The meta object id for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ParameterImpl
	 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getParameter()
	 * @generated
	 */
        int PARAMETER = 13;

        /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER__NAME = HenshinPackage.NAMED_ELEMENT__NAME;

        /**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER__PROVIDER = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 0;

        /**
	 * The feature id for the '<em><b>Henshin Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER__HENSHIN_PARAMETER = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 1;

        /**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
        int PARAMETER_FEATURE_COUNT = HenshinPackage.NAMED_ELEMENT_FEATURE_COUNT + 2;


        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement <em>Flow Element</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Element</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowElement
	 * @generated
	 */
        EClass getFlowElement();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getOut <em>Out</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Out</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowElement#getOut()
	 * @see #getFlowElement()
	 * @generated
	 */
        EReference getFlowElement_Out();

        /**
	 * Returns the meta object for the reference list '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getIn <em>In</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowElement#getIn()
	 * @see #getFlowElement()
	 * @generated
	 */
        EReference getFlowElement_In();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getDiagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Diagram</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowElement#getDiagram()
	 * @see #getFlowElement()
	 * @generated
	 */
        EReference getFlowElement_Diagram();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Activity
	 * @generated
	 */
        EClass getActivity();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.Activity#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Content</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Activity#getContent()
	 * @see #getActivity()
	 * @generated
	 */
        EReference getActivity_Content();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.Activity#getParameterMappings <em>Parameter Mappings</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Mappings</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Activity#getParameterMappings()
	 * @see #getActivity()
	 * @generated
	 */
        EReference getActivity_ParameterMappings();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.ControlElement <em>Control Element</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control Element</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ControlElement
	 * @generated
	 */
        EClass getControlElement();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity <em>Conditional Activity</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Activity</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity
	 * @generated
	 */
        EClass getConditionalActivity();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.Start <em>Start</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Start
	 * @generated
	 */
        EClass getStart();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.End <em>End</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>End</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.End
	 * @generated
	 */
        EClass getEnd();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Transition
	 * @generated
	 */
        EClass getTransition();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.Transition#getNext <em>Next</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Next</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Transition#getNext()
	 * @see #getTransition()
	 * @generated
	 */
        EReference getTransition_Next();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.Transition#getPrevous <em>Prevous</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Prevous</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Transition#getPrevous()
	 * @see #getTransition()
	 * @generated
	 */
        EReference getTransition_Prevous();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram <em>Flow Diagram</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Diagram</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram
	 * @generated
	 */
        EClass getFlowDiagram();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getElements()
	 * @see #getFlowDiagram()
	 * @generated
	 */
        EReference getFlowDiagram_Elements();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getTransitions()
	 * @see #getFlowDiagram()
	 * @generated
	 */
        EReference getFlowDiagram_Transitions();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getParameterMappings <em>Parameter Mappings</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Mappings</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getParameterMappings()
	 * @see #getFlowDiagram()
	 * @generated
	 */
        EReference getFlowDiagram_ParameterMappings();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getStart()
	 * @see #getFlowDiagram()
	 * @generated
	 */
        EReference getFlowDiagram_Start();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>End</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getEnd()
	 * @see #getFlowDiagram()
	 * @generated
	 */
        EReference getFlowDiagram_End();

        /**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isStrict <em>Strict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strict</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isStrict()
	 * @see #getFlowDiagram()
	 * @generated
	 */
	EAttribute getFlowDiagram_Strict();

								/**
	 * Returns the meta object for the attribute '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isRollback <em>Rollback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rollback</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isRollback()
	 * @see #getFlowDiagram()
	 * @generated
	 */
	EAttribute getFlowDiagram_Rollback();

								/**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem <em>System</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem
	 * @generated
	 */
        EClass getFlowControlSystem();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem#getUnits <em>Units</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Units</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem#getUnits()
	 * @see #getFlowControlSystem()
	 * @generated
	 */
        EReference getFlowControlSystem_Units();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping <em>Parameter Mapping</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Mapping</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterMapping
	 * @generated
	 */
        EClass getParameterMapping();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getSrc()
	 * @see #getParameterMapping()
	 * @generated
	 */
        EReference getParameterMapping_Src();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getTarget()
	 * @see #getParameterMapping()
	 * @generated
	 */
        EReference getParameterMapping_Target();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterProvider <em>Parameter Provider</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Provider</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterProvider
	 * @generated
	 */
        EClass getParameterProvider();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterProvider#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterProvider#getParameters()
	 * @see #getParameterProvider()
	 * @generated
	 */
        EReference getParameterProvider_Parameters();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalElement <em>Conditional Element</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Element</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ConditionalElement
	 * @generated
	 */
        EClass getConditionalElement();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalElement#getAltOut <em>Alt Out</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Alt Out</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ConditionalElement#getAltOut()
	 * @see #getConditionalElement()
	 * @generated
	 */
        EReference getConditionalElement_AltOut();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.CompoundActivity <em>Compound Activity</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compound Activity</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.CompoundActivity
	 * @generated
	 */
        EClass getCompoundActivity();

        /**
	 * Returns the meta object for the containment reference list '{@link de.tub.tfs.henshin.model.flowcontrol.CompoundActivity#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.CompoundActivity#getChildren()
	 * @see #getCompoundActivity()
	 * @generated
	 */
        EReference getCompoundActivity_Children();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Parameter
	 * @generated
	 */
        EClass getParameter();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.Parameter#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Parameter#getProvider()
	 * @see #getParameter()
	 * @generated
	 */
        EReference getParameter_Provider();

        /**
	 * Returns the meta object for the reference '{@link de.tub.tfs.henshin.model.flowcontrol.Parameter#getHenshinParameter <em>Henshin Parameter</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Henshin Parameter</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Parameter#getHenshinParameter()
	 * @see #getParameter()
	 * @generated
	 */
        EReference getParameter_HenshinParameter();

        /**
	 * Returns the meta object for class '{@link de.tub.tfs.henshin.model.flowcontrol.SimpleActivity <em>Simple Activity</em>}'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Activity</em>'.
	 * @see de.tub.tfs.henshin.model.flowcontrol.SimpleActivity
	 * @generated
	 */
        EClass getSimpleActivity();

        /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
        FlowControlFactory getFlowControlFactory();

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
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl <em>Flow Element</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getFlowElement()
		 * @generated
		 */
                EClass FLOW_ELEMENT = eINSTANCE.getFlowElement();

                /**
		 * The meta object literal for the '<em><b>Out</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_ELEMENT__OUT = eINSTANCE.getFlowElement_Out();

                /**
		 * The meta object literal for the '<em><b>In</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_ELEMENT__IN = eINSTANCE.getFlowElement_In();

                /**
		 * The meta object literal for the '<em><b>Diagram</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_ELEMENT__DIAGRAM = eINSTANCE.getFlowElement_Diagram();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl <em>Activity</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getActivity()
		 * @generated
		 */
                EClass ACTIVITY = eINSTANCE.getActivity();

                /**
		 * The meta object literal for the '<em><b>Content</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference ACTIVITY__CONTENT = eINSTANCE.getActivity_Content();

                /**
		 * The meta object literal for the '<em><b>Parameter Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference ACTIVITY__PARAMETER_MAPPINGS = eINSTANCE.getActivity_ParameterMappings();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ControlElementImpl <em>Control Element</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ControlElementImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getControlElement()
		 * @generated
		 */
                EClass CONTROL_ELEMENT = eINSTANCE.getControlElement();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalActivityImpl <em>Conditional Activity</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalActivityImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getConditionalActivity()
		 * @generated
		 */
                EClass CONDITIONAL_ACTIVITY = eINSTANCE.getConditionalActivity();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.StartImpl <em>Start</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.StartImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getStart()
		 * @generated
		 */
                EClass START = eINSTANCE.getStart();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.EndImpl <em>End</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.EndImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getEnd()
		 * @generated
		 */
                EClass END = eINSTANCE.getEnd();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.TransitionImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getTransition()
		 * @generated
		 */
                EClass TRANSITION = eINSTANCE.getTransition();

                /**
		 * The meta object literal for the '<em><b>Next</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference TRANSITION__NEXT = eINSTANCE.getTransition_Next();

                /**
		 * The meta object literal for the '<em><b>Prevous</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference TRANSITION__PREVOUS = eINSTANCE.getTransition_Prevous();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl <em>Flow Diagram</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getFlowDiagram()
		 * @generated
		 */
                EClass FLOW_DIAGRAM = eINSTANCE.getFlowDiagram();

                /**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_DIAGRAM__ELEMENTS = eINSTANCE.getFlowDiagram_Elements();

                /**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_DIAGRAM__TRANSITIONS = eINSTANCE.getFlowDiagram_Transitions();

                /**
		 * The meta object literal for the '<em><b>Parameter Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_DIAGRAM__PARAMETER_MAPPINGS = eINSTANCE.getFlowDiagram_ParameterMappings();

                /**
		 * The meta object literal for the '<em><b>Start</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_DIAGRAM__START = eINSTANCE.getFlowDiagram_Start();

                /**
		 * The meta object literal for the '<em><b>End</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_DIAGRAM__END = eINSTANCE.getFlowDiagram_End();

                /**
		 * The meta object literal for the '<em><b>Strict</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_DIAGRAM__STRICT = eINSTANCE.getFlowDiagram_Strict();

																/**
		 * The meta object literal for the '<em><b>Rollback</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_DIAGRAM__ROLLBACK = eINSTANCE.getFlowDiagram_Rollback();

																/**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlSystemImpl <em>System</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlSystemImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getFlowControlSystem()
		 * @generated
		 */
                EClass FLOW_CONTROL_SYSTEM = eINSTANCE.getFlowControlSystem();

                /**
		 * The meta object literal for the '<em><b>Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference FLOW_CONTROL_SYSTEM__UNITS = eINSTANCE.getFlowControlSystem_Units();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterMappingImpl <em>Parameter Mapping</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ParameterMappingImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getParameterMapping()
		 * @generated
		 */
                EClass PARAMETER_MAPPING = eINSTANCE.getParameterMapping();

                /**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference PARAMETER_MAPPING__SRC = eINSTANCE.getParameterMapping_Src();

                /**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference PARAMETER_MAPPING__TARGET = eINSTANCE.getParameterMapping_Target();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterProviderImpl <em>Parameter Provider</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ParameterProviderImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getParameterProvider()
		 * @generated
		 */
                EClass PARAMETER_PROVIDER = eINSTANCE.getParameterProvider();

                /**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference PARAMETER_PROVIDER__PARAMETERS = eINSTANCE.getParameterProvider_Parameters();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalElementImpl <em>Conditional Element</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ConditionalElementImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getConditionalElement()
		 * @generated
		 */
                EClass CONDITIONAL_ELEMENT = eINSTANCE.getConditionalElement();

                /**
		 * The meta object literal for the '<em><b>Alt Out</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference CONDITIONAL_ELEMENT__ALT_OUT = eINSTANCE.getConditionalElement_AltOut();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.CompoundActivityImpl <em>Compound Activity</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.CompoundActivityImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getCompoundActivity()
		 * @generated
		 */
                EClass COMPOUND_ACTIVITY = eINSTANCE.getCompoundActivity();

                /**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference COMPOUND_ACTIVITY__CHILDREN = eINSTANCE.getCompoundActivity_Children();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.ParameterImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getParameter()
		 * @generated
		 */
                EClass PARAMETER = eINSTANCE.getParameter();

                /**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference PARAMETER__PROVIDER = eINSTANCE.getParameter_Provider();

                /**
		 * The meta object literal for the '<em><b>Henshin Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @generated
		 */
                EReference PARAMETER__HENSHIN_PARAMETER = eINSTANCE.getParameter_HenshinParameter();

                /**
		 * The meta object literal for the '{@link de.tub.tfs.henshin.model.flowcontrol.impl.SimpleActivityImpl <em>Simple Activity</em>}' class.
		 * <!-- begin-user-doc -->
                 * <!-- end-user-doc -->
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.SimpleActivityImpl
		 * @see de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlPackageImpl#getSimpleActivity()
		 * @generated
		 */
                EClass SIMPLE_ACTIVITY = eINSTANCE.getSimpleActivity();

        }

} //FlowControlPackage
