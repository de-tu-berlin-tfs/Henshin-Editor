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
 * A representation of the model object '<em><b>Parameter Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.ParameterProvider#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameterProvider()
 * @model abstract="true"
 * @generated
 */
public interface ParameterProvider extends EObject {
        /**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.Parameter}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getParameterProvider_Parameters()
	 * @model containment="true"
	 * @generated
	 */
        EList<Parameter> getParameters();

} // ParameterProvider
