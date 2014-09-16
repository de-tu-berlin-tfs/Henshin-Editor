/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getOut <em>Out</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getIn <em>In</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getDiagram <em>Diagram</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowElement()
 * @model abstract="true"
 * @generated
 */
public interface FlowElement extends EObject {
        /**
	 * Returns the value of the '<em><b>Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Out</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Out</em>' reference.
	 * @see #setOut(Transition)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowElement_Out()
	 * @model
	 * @generated
	 */
        Transition getOut();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getOut <em>Out</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Out</em>' reference.
	 * @see #getOut()
	 * @generated
	 */
        void setOut(Transition value);

        /**
	 * Returns the value of the '<em><b>In</b></em>' reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.Transition}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>In</em>' reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>In</em>' reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowElement_In()
	 * @model
	 * @generated
	 */
        EList<Transition> getIn();

        /**
	 * Returns the value of the '<em><b>Diagram</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Diagram</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram</em>' reference.
	 * @see #setDiagram(FlowDiagram)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowElement_Diagram()
	 * @model required="true"
	 * @generated
	 */
        FlowDiagram getDiagram();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.FlowElement#getDiagram <em>Diagram</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram</em>' reference.
	 * @see #getDiagram()
	 * @generated
	 */
        void setDiagram(FlowDiagram value);

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<Transition> getOutGoings();

} // FlowElement
