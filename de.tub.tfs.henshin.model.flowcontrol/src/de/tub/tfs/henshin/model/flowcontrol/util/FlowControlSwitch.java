/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.emf.henshin.model.NamedElement;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.ControlElement;
import de.tub.tfs.henshin.model.flowcontrol.End;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage
 * @generated
 */
public class FlowControlSwitch<T> extends Switch<T> {
        /**
	 * The cached model package
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected static FlowControlPackage modelPackage;

        /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowControlSwitch() {
		if (modelPackage == null) {
			modelPackage = FlowControlPackage.eINSTANCE;
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
			case FlowControlPackage.FLOW_ELEMENT: {
				FlowElement flowElement = (FlowElement)theEObject;
				T result = caseFlowElement(flowElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.ACTIVITY: {
				Activity activity = (Activity)theEObject;
				T result = caseActivity(activity);
				if (result == null) result = caseFlowElement(activity);
				if (result == null) result = caseParameterProvider(activity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.CONTROL_ELEMENT: {
				ControlElement controlElement = (ControlElement)theEObject;
				T result = caseControlElement(controlElement);
				if (result == null) result = caseFlowElement(controlElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.CONDITIONAL_ACTIVITY: {
				ConditionalActivity conditionalActivity = (ConditionalActivity)theEObject;
				T result = caseConditionalActivity(conditionalActivity);
				if (result == null) result = caseActivity(conditionalActivity);
				if (result == null) result = caseConditionalElement(conditionalActivity);
				if (result == null) result = caseFlowElement(conditionalActivity);
				if (result == null) result = caseParameterProvider(conditionalActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.START: {
				Start start = (Start)theEObject;
				T result = caseStart(start);
				if (result == null) result = caseControlElement(start);
				if (result == null) result = caseFlowElement(start);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.END: {
				End end = (End)theEObject;
				T result = caseEnd(end);
				if (result == null) result = caseControlElement(end);
				if (result == null) result = caseFlowElement(end);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.TRANSITION: {
				Transition transition = (Transition)theEObject;
				T result = caseTransition(transition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.FLOW_DIAGRAM: {
				FlowDiagram flowDiagram = (FlowDiagram)theEObject;
				T result = caseFlowDiagram(flowDiagram);
				if (result == null) result = caseNamedElement(flowDiagram);
				if (result == null) result = caseParameterProvider(flowDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.FLOW_CONTROL_SYSTEM: {
				FlowControlSystem flowControlSystem = (FlowControlSystem)theEObject;
				T result = caseFlowControlSystem(flowControlSystem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.PARAMETER_MAPPING: {
				ParameterMapping parameterMapping = (ParameterMapping)theEObject;
				T result = caseParameterMapping(parameterMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.PARAMETER_PROVIDER: {
				ParameterProvider parameterProvider = (ParameterProvider)theEObject;
				T result = caseParameterProvider(parameterProvider);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.CONDITIONAL_ELEMENT: {
				ConditionalElement conditionalElement = (ConditionalElement)theEObject;
				T result = caseConditionalElement(conditionalElement);
				if (result == null) result = caseFlowElement(conditionalElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.COMPOUND_ACTIVITY: {
				CompoundActivity compoundActivity = (CompoundActivity)theEObject;
				T result = caseCompoundActivity(compoundActivity);
				if (result == null) result = caseSimpleActivity(compoundActivity);
				if (result == null) result = caseActivity(compoundActivity);
				if (result == null) result = caseFlowElement(compoundActivity);
				if (result == null) result = caseParameterProvider(compoundActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.PARAMETER: {
				Parameter parameter = (Parameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = caseNamedElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FlowControlPackage.SIMPLE_ACTIVITY: {
				SimpleActivity simpleActivity = (SimpleActivity)theEObject;
				T result = caseSimpleActivity(simpleActivity);
				if (result == null) result = caseActivity(simpleActivity);
				if (result == null) result = caseFlowElement(simpleActivity);
				if (result == null) result = caseParameterProvider(simpleActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Flow Element</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseFlowElement(FlowElement object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseActivity(Activity object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Control Element</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Control Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseControlElement(ControlElement object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Activity</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseConditionalActivity(ConditionalActivity object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Start</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Start</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseStart(Start object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>End</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>End</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseEnd(End object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseTransition(Transition object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Flow Diagram</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseFlowDiagram(FlowDiagram object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>System</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>System</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseFlowControlSystem(FlowControlSystem object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Mapping</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseParameterMapping(ParameterMapping object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Provider</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseParameterProvider(ParameterProvider object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Element</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseConditionalElement(ConditionalElement object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Compound Activity</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compound Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseCompoundActivity(CompoundActivity object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseParameter(Parameter object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Activity</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseSimpleActivity(SimpleActivity object) {
		return null;
	}

        /**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
         * This implementation returns null;
         * returning a non-null result will terminate the switch.
         * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
        public T caseNamedElement(NamedElement object) {
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

} //FlowControlSwitch
