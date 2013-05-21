/**
 */
package de.tub.tfs.henshin.tgg;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.TAttribute#getMarkerType <em>Marker Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.tub.tfs.henshin.tgg.TggPackage#getTAttribute()
 * @model
 * @generated
 */
public interface TAttribute extends Attribute {
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
	 * @see de.tub.tfs.henshin.tgg.TggPackage#getTAttribute_MarkerType()
	 * @model
	 * @generated
	 */
	String getMarkerType();

	/**
	 * Sets the value of the '{@link de.tub.tfs.henshin.tgg.TAttribute#getMarkerType <em>Marker Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marker Type</em>' attribute.
	 * @see #getMarkerType()
	 * @generated
	 */
	void setMarkerType(String value);

} // TAttribute
