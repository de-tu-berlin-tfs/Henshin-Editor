/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGGRule#getIsMarked <em>Is Marked</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.tgg.TGGRule#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGGRule()
 * @model
 * @generated
 */
public interface TGGRule extends Rule {
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGGRule_IsMarked()
	 * @model
	 * @generated
	 */
	Boolean getIsMarked();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGGRule#getIsMarked <em>Is Marked</em>}' attribute.
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTGGRule_MarkerType()
	 * @model
	 * @generated
	 */
	String getMarkerType();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TGGRule#getMarkerType <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marker Type</em>' attribute.
	 * @see #getMarkerType()
	 * @generated
	 */
	void setMarkerType(String value);

} // TGGRule
