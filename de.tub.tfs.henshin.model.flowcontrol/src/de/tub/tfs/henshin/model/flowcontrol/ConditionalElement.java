/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalElement#getAltOut <em>Alt Out</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getConditionalElement()
 * @model abstract="true"
 * @generated
 */
public interface ConditionalElement extends FlowElement {
        /**
	 * Returns the value of the '<em><b>Alt Out</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Alt Out</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Alt Out</em>' reference.
	 * @see #setAltOut(Transition)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getConditionalElement_AltOut()
	 * @model
	 * @generated
	 */
        Transition getAltOut();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.ConditionalElement#getAltOut <em>Alt Out</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alt Out</em>' reference.
	 * @see #getAltOut()
	 * @generated
	 */
        void setAltOut(Transition value);

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
        String getAlternativeLabel();

} // ConditionalElement
