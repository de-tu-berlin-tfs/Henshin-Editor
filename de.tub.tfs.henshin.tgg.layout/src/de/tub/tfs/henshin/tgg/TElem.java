/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TElem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.TElem#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTElem()
 * @model
 * @generated
 */
public interface TElem extends EObject {
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTElem_MarkerType()
	 * @model
	 * @generated
	 */
	String getMarkerType();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TElem#getMarkerType <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marker Type</em>' attribute.
	 * @see #getMarkerType()
	 * @generated
	 */
	void setMarkerType(String value);

} // TElem
