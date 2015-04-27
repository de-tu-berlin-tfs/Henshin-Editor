/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage
 * @generated
 */
public interface FlowControlFactory extends EFactory {
        /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        FlowControlFactory eINSTANCE = de.tub.tfs.henshin.model.flowcontrol.impl.FlowControlFactoryImpl.init();

        /**
	 * Returns a new object of class '<em>Conditional Activity</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Activity</em>'.
	 * @generated
	 */
        ConditionalActivity createConditionalActivity();

        /**
	 * Returns a new object of class '<em>Start</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start</em>'.
	 * @generated
	 */
        Start createStart();

        /**
	 * Returns a new object of class '<em>End</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>End</em>'.
	 * @generated
	 */
        End createEnd();

        /**
	 * Returns a new object of class '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition</em>'.
	 * @generated
	 */
        Transition createTransition();

        /**
	 * Returns a new object of class '<em>Flow Diagram</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Flow Diagram</em>'.
	 * @generated
	 */
        FlowDiagram createFlowDiagram();

        /**
	 * Returns a new object of class '<em>System</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>System</em>'.
	 * @generated
	 */
        FlowControlSystem createFlowControlSystem();

        /**
	 * Returns a new object of class '<em>Parameter Mapping</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Mapping</em>'.
	 * @generated
	 */
        ParameterMapping createParameterMapping();

        /**
	 * Returns a new object of class '<em>Compound Activity</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compound Activity</em>'.
	 * @generated
	 */
        CompoundActivity createCompoundActivity();

        /**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
        Parameter createParameter();

        /**
	 * Returns a new object of class '<em>Simple Activity</em>'.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Activity</em>'.
	 * @generated
	 */
        SimpleActivity createSimpleActivity();

        /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
        FlowControlPackage getFlowControlPackage();

} //FlowControlFactory
