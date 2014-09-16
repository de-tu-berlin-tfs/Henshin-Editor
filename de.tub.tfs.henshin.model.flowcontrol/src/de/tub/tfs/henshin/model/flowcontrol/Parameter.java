/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.henshin.model.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Parameter#getProvider <em>Provider</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.Parameter#getHenshinParameter <em>Henshin Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameter()
 * @model
 * @generated
 */
public interface Parameter extends NamedElement {
        /**
	 * Returns the value of the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Provider</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' reference.
	 * @see #setProvider(ParameterProvider)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameter_Provider()
	 * @model required="true"
	 * @generated
	 */
        ParameterProvider getProvider();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.Parameter#getProvider <em>Provider</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' reference.
	 * @see #getProvider()
	 * @generated
	 */
        void setProvider(ParameterProvider value);

        /**
	 * Returns the value of the '<em><b>Henshin Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Henshin Parameter</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Henshin Parameter</em>' reference.
	 * @see #setHenshinParameter(org.eclipse.emf.henshin.model.Parameter)
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameter_HenshinParameter()
	 * @model required="true"
	 * @generated
	 */
        org.eclipse.emf.henshin.model.Parameter getHenshinParameter();

        /**
	 * Sets the value of the '{@link de.tub.tfs.henshin.model.flowcontrol.Parameter#getHenshinParameter <em>Henshin Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Henshin Parameter</em>' reference.
	 * @see #getHenshinParameter()
	 * @generated
	 */
        void setHenshinParameter(org.eclipse.emf.henshin.model.Parameter value);

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
        boolean isInput();

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
        boolean isOutPut();

} // Parameter
