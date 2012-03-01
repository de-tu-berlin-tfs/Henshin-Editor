/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getElements <em>Elements</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getParameterMappings <em>Parameter Mappings</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getStart <em>Start</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getEnd <em>End</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isStrict <em>Strict</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isRollback <em>Rollback</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram()
 * @model
 * @generated
 */
public interface FlowDiagram extends NamedElement, ParameterProvider {
        /**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.FlowElement}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_Elements()
	 * @model containment="true"
	 * @generated
	 */
        EList<FlowElement> getElements();

        /**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.Transition}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_Transitions()
	 * @model containment="true"
	 * @generated
	 */
        EList<Transition> getTransitions();

        /**
	 * Returns the value of the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Parameter Mappings</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Mappings</em>' containment reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_ParameterMappings()
	 * @model containment="true"
	 * @generated
	 */
        EList<ParameterMapping> getParameterMappings();

        /**
	 * Returns the value of the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Start</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' reference.
	 * @see #setStart(Start)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_Start()
	 * @model required="true"
	 * @generated
	 */
        Start getStart();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getStart <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' reference.
	 * @see #getStart()
	 * @generated
	 */
        void setStart(Start value);

        /**
	 * Returns the value of the '<em><b>End</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>End</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' reference.
	 * @see #setEnd(End)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_End()
	 * @model required="true"
	 * @generated
	 */
        End getEnd();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#getEnd <em>End</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' reference.
	 * @see #getEnd()
	 * @generated
	 */
        void setEnd(End value);

								/**
	 * Returns the value of the '<em><b>Strict</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strict</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strict</em>' attribute.
	 * @see #setStrict(boolean)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_Strict()
	 * @model
	 * @generated
	 */
	boolean isStrict();

								/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isStrict <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strict</em>' attribute.
	 * @see #isStrict()
	 * @generated
	 */
	void setStrict(boolean value);

								/**
	 * Returns the value of the '<em><b>Rollback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rollback</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rollback</em>' attribute.
	 * @see #setRollback(boolean)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowDiagram_Rollback()
	 * @model
	 * @generated
	 */
	boolean isRollback();

								/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram#isRollback <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rollback</em>' attribute.
	 * @see #isRollback()
	 * @generated
	 */
	void setRollback(boolean value);

} // FlowDiagram
