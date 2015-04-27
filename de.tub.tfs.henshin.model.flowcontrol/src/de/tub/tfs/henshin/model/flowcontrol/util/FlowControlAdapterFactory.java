/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage
 * @generated
 */
public class FlowControlAdapterFactory extends AdapterFactoryImpl {
        /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected static FlowControlPackage modelPackage;

        /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public FlowControlAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = FlowControlPackage.eINSTANCE;
		}
	}

        /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
         * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
         * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
        @Override
        public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

        /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected FlowControlSwitch<Adapter> modelSwitch =
                new FlowControlSwitch<Adapter>() {
			@Override
			public Adapter caseFlowElement(FlowElement object) {
				return createFlowElementAdapter();
			}
			@Override
			public Adapter caseActivity(Activity object) {
				return createActivityAdapter();
			}
			@Override
			public Adapter caseControlElement(ControlElement object) {
				return createControlElementAdapter();
			}
			@Override
			public Adapter caseConditionalActivity(ConditionalActivity object) {
				return createConditionalActivityAdapter();
			}
			@Override
			public Adapter caseStart(Start object) {
				return createStartAdapter();
			}
			@Override
			public Adapter caseEnd(End object) {
				return createEndAdapter();
			}
			@Override
			public Adapter caseTransition(Transition object) {
				return createTransitionAdapter();
			}
			@Override
			public Adapter caseFlowDiagram(FlowDiagram object) {
				return createFlowDiagramAdapter();
			}
			@Override
			public Adapter caseFlowControlSystem(FlowControlSystem object) {
				return createFlowControlSystemAdapter();
			}
			@Override
			public Adapter caseParameterMapping(ParameterMapping object) {
				return createParameterMappingAdapter();
			}
			@Override
			public Adapter caseParameterProvider(ParameterProvider object) {
				return createParameterProviderAdapter();
			}
			@Override
			public Adapter caseConditionalElement(ConditionalElement object) {
				return createConditionalElementAdapter();
			}
			@Override
			public Adapter caseCompoundActivity(CompoundActivity object) {
				return createCompoundActivityAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object) {
				return createParameterAdapter();
			}
			@Override
			public Adapter caseSimpleActivity(SimpleActivity object) {
				return createSimpleActivityAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

        /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
        @Override
        public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement <em>Flow Element</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowElement
	 * @generated
	 */
        public Adapter createFlowElementAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.Activity <em>Activity</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Activity
	 * @generated
	 */
        public Adapter createActivityAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.ControlElement <em>Control Element</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ControlElement
	 * @generated
	 */
        public Adapter createControlElementAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity <em>Conditional Activity</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity
	 * @generated
	 */
        public Adapter createConditionalActivityAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.Start <em>Start</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Start
	 * @generated
	 */
        public Adapter createStartAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.End <em>End</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.End
	 * @generated
	 */
        public Adapter createEndAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Transition
	 * @generated
	 */
        public Adapter createTransitionAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram <em>Flow Diagram</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowDiagram
	 * @generated
	 */
        public Adapter createFlowDiagramAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem <em>System</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem
	 * @generated
	 */
        public Adapter createFlowControlSystemAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping <em>Parameter Mapping</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterMapping
	 * @generated
	 */
        public Adapter createParameterMappingAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterProvider <em>Parameter Provider</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ParameterProvider
	 * @generated
	 */
        public Adapter createParameterProviderAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalElement <em>Conditional Element</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.ConditionalElement
	 * @generated
	 */
        public Adapter createConditionalElementAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.CompoundActivity <em>Compound Activity</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.CompoundActivity
	 * @generated
	 */
        public Adapter createCompoundActivityAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.Parameter
	 * @generated
	 */
        public Adapter createParameterAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link de.tub.tfs.henshin.model.flowcontrol.SimpleActivity <em>Simple Activity</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tub.tfs.henshin.model.flowcontrol.SimpleActivity
	 * @generated
	 */
        public Adapter createSimpleActivityAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.henshin.model.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
         * This default implementation returns null so that we can easily ignore cases;
         * it's useful to ignore a case when inheritance will catch all the cases anyway.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.henshin.model.NamedElement
	 * @generated
	 */
        public Adapter createNamedElementAdapter() {
		return null;
	}

        /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
         * This default implementation returns null.
         * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
        public Adapter createEObjectAdapter() {
		return null;
	}

} //FlowControlAdapterFactory
