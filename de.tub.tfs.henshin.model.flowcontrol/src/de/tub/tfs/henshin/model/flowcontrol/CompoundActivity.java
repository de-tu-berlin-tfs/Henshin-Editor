/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compound Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.CompoundActivity#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getCompoundActivity()
 * @model
 * @generated
 */
public interface CompoundActivity extends SimpleActivity {
        /**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link de.tub.tfs.henshin.model.flowcontrol.Activity}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage#getCompoundActivity_Children()
	 * @model containment="true"
	 * @generated
	 */
        EList<Activity> getChildren();

} // CompoundActivity
