/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.SimpleActivity;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FlowControlFactoryImpl extends EFactoryImpl implements FlowControlFactory {
        /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public static FlowControlFactory init() {
		try {
			FlowControlFactory theFlowControlFactory = (FlowControlFactory)EPackage.Registry.INSTANCE.getEFactory("http://flowcontrol"); 
			if (theFlowControlFactory != null) {
				return theFlowControlFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FlowControlFactoryImpl();
	}

        /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowControlFactoryImpl() {
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
			case FlowControlPackage.CONDITIONAL_ACTIVITY: return createConditionalActivity();
			case FlowControlPackage.START: return createStart();
			case FlowControlPackage.END: return createEnd();
			case FlowControlPackage.TRANSITION: return createTransition();
			case FlowControlPackage.FLOW_DIAGRAM: return createFlowDiagram();
			case FlowControlPackage.FLOW_CONTROL_SYSTEM: return createFlowControlSystem();
			case FlowControlPackage.PARAMETER_MAPPING: return createParameterMapping();
			case FlowControlPackage.COMPOUND_ACTIVITY: return createCompoundActivity();
			case FlowControlPackage.PARAMETER: return createParameter();
			case FlowControlPackage.SIMPLE_ACTIVITY: return createSimpleActivity();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public ConditionalActivity createConditionalActivity() {
		ConditionalActivityImpl conditionalActivity = new ConditionalActivityImpl();
		return conditionalActivity;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Start createStart() {
		StartImpl start = new StartImpl();
		return start;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public End createEnd() {
		EndImpl end = new EndImpl();
		return end;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowDiagram createFlowDiagram() {
		FlowDiagramImpl flowDiagram = new FlowDiagramImpl();
		return flowDiagram;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowControlSystem createFlowControlSystem() {
		FlowControlSystemImpl flowControlSystem = new FlowControlSystemImpl();
		return flowControlSystem;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public ParameterMapping createParameterMapping() {
		ParameterMappingImpl parameterMapping = new ParameterMappingImpl();
		return parameterMapping;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public CompoundActivity createCompoundActivity() {
		CompoundActivityImpl compoundActivity = new CompoundActivityImpl();
		return compoundActivity;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public SimpleActivity createSimpleActivity() {
		SimpleActivityImpl simpleActivity = new SimpleActivityImpl();
		return simpleActivity;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowControlPackage getFlowControlPackage() {
		return (FlowControlPackage)getEPackage();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
        @Deprecated
        public static FlowControlPackage getPackage() {
		return FlowControlPackage.eINSTANCE;
	}

} //FlowControlFactoryImpl
