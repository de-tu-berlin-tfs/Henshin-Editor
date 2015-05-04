/**
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nested Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.NestedConstraint#getPremise <em>Premise</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNestedConstraint()
 * @model
 * @generated
 */
public interface NestedConstraint extends Formula {
	/**
	 * Returns the value of the '<em><b>Premise</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Premise</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Premise</em>' containment reference.
	 * @see #setPremise(Graph)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNestedConstraint_Premise()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getPremise();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.NestedConstraint#getPremise <em>Premise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Premise</em>' containment reference.
	 * @see #getPremise()
	 * @generated
	 */
	void setPremise(Graph value);

} // NestedConstraint
