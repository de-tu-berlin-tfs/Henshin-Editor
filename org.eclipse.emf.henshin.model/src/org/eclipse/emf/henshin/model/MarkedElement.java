/**
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Marked Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.MarkedElement#getIsMarked <em>Is Marked</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.MarkedElement#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getMarkedElement()
 * @model
 * @generated
 */
public interface MarkedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Marked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Marked</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Marked</em>' attribute.
	 * @see #setIsMarked(Boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getMarkedElement_IsMarked()
	 * @model
	 * @generated
	 */
	Boolean getIsMarked();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.MarkedElement#getIsMarked <em>Is Marked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Marked</em>' attribute.
	 * @see #getIsMarked()
	 * @generated
	 */
	void setIsMarked(Boolean value);

	/**
	 * Returns the value of the '<em><b>Marker Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Marker Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Marker Type</em>' attribute.
	 * @see #setMarkerType(String)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getMarkedElement_MarkerType()
	 * @model
	 * @generated
	 */
	String getMarkerType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.MarkedElement#getMarkerType <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marker Type</em>' attribute.
	 * @see #getMarkerType()
	 * @generated
	 */
	void setMarkerType(String value);

} // MarkedElement
