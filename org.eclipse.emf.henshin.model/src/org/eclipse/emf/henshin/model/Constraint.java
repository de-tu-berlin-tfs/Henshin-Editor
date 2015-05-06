/**
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Constraint#getRoot <em>Root</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Constraint#getComponent <em>Component</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Constraint#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Constraint#isEnabled <em>Enabled</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConstraint()
 * @model
 * @generated
 */
public interface Constraint extends NamedElement {

	/**
	 * Returns the value of the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' containment reference.
	 * @see #setRoot(Formula)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConstraint_Root()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Formula getRoot();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Constraint#getRoot <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' containment reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(Formula value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' attribute.
	 * @see #setComponent(String)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConstraint_Component()
	 * @model required="true"
	 * @generated
	 */
	String getComponent();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Constraint#getComponent <em>Component</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' attribute.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConstraint_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConstraint_Enabled()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Constraint#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);
} // Constraint
