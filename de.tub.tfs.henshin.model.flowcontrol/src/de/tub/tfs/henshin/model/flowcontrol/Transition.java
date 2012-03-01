/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Transition#getNext <em>Next</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Transition#getPrevous <em>Prevous</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends EObject {
        /**
	 * Returns the value of the '<em><b>Next</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Next</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Next</em>' reference.
	 * @see #setNext(FlowElement)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getTransition_Next()
	 * @model required="true"
	 * @generated
	 */
        FlowElement getNext();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.Transition#getNext <em>Next</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next</em>' reference.
	 * @see #getNext()
	 * @generated
	 */
        void setNext(FlowElement value);

        /**
	 * Returns the value of the '<em><b>Prevous</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Prevous</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Prevous</em>' reference.
	 * @see #setPrevous(FlowElement)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getTransition_Prevous()
	 * @model required="true"
	 * @generated
	 */
        FlowElement getPrevous();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.Transition#getPrevous <em>Prevous</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prevous</em>' reference.
	 * @see #getPrevous()
	 * @generated
	 */
        void setPrevous(FlowElement value);

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
        boolean isAlternate();

} // Transition
