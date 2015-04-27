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
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Activity#getContent <em>Content</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Activity#getParameterMappings <em>Parameter Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getActivity()
 * @model abstract="true"
 * @generated
 */
public interface Activity extends FlowElement, ParameterProvider {
        /**
	 * Returns the value of the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Content</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' reference.
	 * @see #setContent(NamedElement)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getActivity_Content()
	 * @model
	 * @generated
	 */
        NamedElement getContent();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.Activity#getContent <em>Content</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' reference.
	 * @see #getContent()
	 * @generated
	 */
        void setContent(NamedElement value);

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
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getActivity_ParameterMappings()
	 * @model containment="true"
	 * @generated
	 */
        EList<ParameterMapping> getParameterMappings();

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
        boolean isNested();

} // Activity
