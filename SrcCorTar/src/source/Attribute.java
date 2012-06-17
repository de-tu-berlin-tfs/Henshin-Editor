/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package source;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link source.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link source.Attribute#isIs_primary <em>Is primary</em>}</li>
 *   <li>{@link source.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link source.Attribute#getPtype <em>Ptype</em>}</li>
 * </ul>
 * </p>
 *
 * @see source.SourcePackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see source.SourcePackage#getAttribute_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link source.Attribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Is primary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is primary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is primary</em>' attribute.
	 * @see #setIs_primary(boolean)
	 * @see source.SourcePackage#getAttribute_Is_primary()
	 * @model
	 * @generated
	 */
	boolean isIs_primary();

	/**
	 * Sets the value of the '{@link source.Attribute#isIs_primary <em>Is primary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is primary</em>' attribute.
	 * @see #isIs_primary()
	 * @generated
	 */
	void setIs_primary(boolean value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(source.Class)
	 * @see source.SourcePackage#getAttribute_Type()
	 * @model
	 * @generated
	 */
	source.Class getType();

	/**
	 * Sets the value of the '{@link source.Attribute#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(source.Class value);

	/**
	 * Returns the value of the '<em><b>Ptype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ptype</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ptype</em>' reference.
	 * @see #setPtype(PrimitiveDataType)
	 * @see source.SourcePackage#getAttribute_Ptype()
	 * @model
	 * @generated
	 */
	PrimitiveDataType getPtype();

	/**
	 * Sets the value of the '{@link source.Attribute#getPtype <em>Ptype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ptype</em>' reference.
	 * @see #getPtype()
	 * @generated
	 */
	void setPtype(PrimitiveDataType value);

} // Attribute
