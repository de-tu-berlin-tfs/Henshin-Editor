/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Counted</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Counted#getLimit <em>Limit</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getCounted()
 * @model
 * @generated
 */
public interface Counted extends ControlElement, ConditionalElement {
        /**
         * Returns the value of the '<em><b>Limit</b></em>' attribute.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Limit</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Limit</em>' attribute.
         * @see #setLimit(int)
         * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getCounted_Limit()
         * @model
         * @generated
         */
        int getLimit();

        /**
         * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.Counted#getLimit <em>Limit</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Limit</em>' attribute.
         * @see #getLimit()
         * @generated
         */
        void setLimit(int value);

} // Counted
