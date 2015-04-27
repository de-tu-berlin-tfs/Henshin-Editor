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
 * A representation of the model object '<em><b>Parameter Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getSrc <em>Src</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameterMapping()
 * @model
 * @generated
 */
public interface ParameterMapping extends EObject {
        /**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Src</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(Parameter)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameterMapping_Src()
	 * @model required="true"
	 * @generated
	 */
        Parameter getSrc();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
        void setSrc(Parameter value);

        /**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Target</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Parameter)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameterMapping_Target()
	 * @model required="true"
	 * @generated
	 */
        Parameter getTarget();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.ParameterMapping#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
        void setTarget(Parameter value);

} // ParameterMapping
