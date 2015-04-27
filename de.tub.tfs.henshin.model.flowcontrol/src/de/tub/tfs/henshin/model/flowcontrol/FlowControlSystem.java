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
 * A representation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem#getUnits <em>Units</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowControlSystem()
 * @model
 * @generated
 */
public interface FlowControlSystem extends EObject {
        /**
	 * Returns the value of the '<em><b>Units</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.FlowDiagram}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Units</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Units</em>' containment reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getFlowControlSystem_Units()
	 * @model containment="true"
	 * @generated
	 */
        EList<FlowDiagram> getUnits();

} // FlowControlSystem
